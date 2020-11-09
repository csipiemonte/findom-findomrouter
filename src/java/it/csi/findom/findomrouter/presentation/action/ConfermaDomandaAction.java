/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.action;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.AggrDataDto;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.VistaDomandeDto;
import it.csi.findom.findomrouter.exception.ServiziFindomWebException;
import it.csi.findom.findomrouter.presentation.util.ActionUtil;
import it.csi.findom.findomrouter.presentation.util.Constants;
import it.csi.findom.findomrouter.presentation.util.StringUtils;
import it.csi.findom.findomrouter.presentation.vo.StatusInfo;

import java.util.ArrayList;
import java.util.TreeMap;

public class ConfermaDomandaAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private static final String CLASS_NAME = "ConfermaDomandaAction";
	
	String idDomanda;
	String sportelloOpen;  // attributo per differenziare il testo della modale di conferma invio domanda in base al fatto che lo sportello sia aperto o meno
	String numMaxDomandeBandoPresentate;// attributo per differenziare il testo della modale di conferma invio domanda in base al fatto che l'utente abbia inviato il numero massimo di domande o meno
	String numMaxDomandeSportelloPresentate;// attributo per differenziare il testo della modale di conferma invio domanda in base al fatto che l'utente abbia inviato il numero massimo di domande o meno
	String bandoDematerializzato;
	String ruoloCapofilaProgettoComune;  //questo attributo e il seguente sono valorizzati in CercaDomandeAction e passati come parametri a questa Action tramite gestisciDomande.jsp
	String ruoloPartnerProgettoComune;
	String bandoMaterializzatoSenzaPEC;// attributo per differenziare il testo della modale di conferma invio domanda per bandi non Dematerializzati che non devono inviare PEC
	
	/*
	 * Classe "passacarte" necessaria per visualizzare le finestre modali dove l'utente conferma l'azione da compiere
	 *
	 */
	
	@Override
	public String executeAction() throws SystemException, UnrecoverableException, CSIException {
		final String methodName = "executeAction";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";

		log.debug(logprefix + " BEGIN-END idDomanda="+idDomanda);

		return SUCCESS;
	}
	
	public String eliminaProposta() {
		final String methodName = "eliminaProposta";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";

		log.debug(logprefix + " BEGIN-END idDomanda="+idDomanda);

		return "elimina_success";
	}
	
	public String invalidaProposta() {
		final String methodName = "invalidaProposta";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";

		log.debug(logprefix + " BEGIN-END idDomanda="+idDomanda);

		return "invalida_success";
	}
	
	public String inviaProposta() {

		final String methodName = "inviaProposta";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		log.debug(logprefix + " BEGIN idDomanda="+idDomanda);
		
		StatusInfo state = getStatus();
		
		if(StringUtils.isNotBlank(idDomanda)){
			
			state.setNumProposta(Integer.parseInt(idDomanda));
			log.debug(logprefix + " valori dello stato inseriti in REQUEST");
		}
		
		
		// carico i dati dell'aggregatore per la domanda selezionata
		try{
			ArrayList<AggrDataDto> listaAggData = getServiziFindomWeb().getAggrDataByIdDomanda(Integer.parseInt(idDomanda));
			if(listaAggData==null || (listaAggData!=null && listaAggData.size()!=1)){
				log.error(logprefix + " listaAggData NULLA o di dimenzione errata");
				return ERROR;
			}
			
			AggrDataDto aggrDomanda = listaAggData.get(0);
			
			aggiornaStatus(aggrDomanda,state);
			
			
			String ruolo = getUserInfo().getRuolo();
			int idSoggettoCreatore = 0;
			// recupero i dati della domanda da visualizzare nei titoli della pagina
			if(Constants.RUOLO_AMM.equals(ruolo) || Constants.RUOLO_LR.equals(ruolo)){
				idSoggettoCreatore = aggrDomanda.getIdSoggettoCreatore();
			}else{
				idSoggettoCreatore = state.getIdSoggettoCollegato();
			}
			
			int idSoggettoBeneficiario = state.getIdSoggettoBeneficiario();
			ArrayList<VistaDomandeDto> datiDomanda = getServiziFindomWeb().getVistaDomanda(idSoggettoCreatore, idSoggettoBeneficiario, null, null, Integer.parseInt(idDomanda), null);

			state.setDescrNormativa(datiDomanda.get(0).getNormativa());
			state.setCodiceAzione(datiDomanda.get(0).getCodiceAzione());
			state.setDescrBando(datiDomanda.get(0).getDescrBando());
			state.setDescrBreveBando(datiDomanda.get(0).getDescrBreveBando());
			state.setDescrTipolBeneficiario(datiDomanda.get(0).getDescrizioneTipolBeneficiario());
			state.setFlagBandoDematerializzato(datiDomanda.get(0).getFlagBandoDematerializzato());
			state.setTipoFirma(datiDomanda.get(0).getTipoFirma());
			
			setStatus(state);
			
			if (Constants.FLAG_BANDO_DEMATERIALIZZATO.equalsIgnoreCase(state.getFlagBandoDematerializzato())){ 
				setBandoDematerializzato("true");
			} else {
				setBandoDematerializzato("false");
			}
			
			TreeMap<String, Object> context = (TreeMap<String, Object>) this.getServletRequest().getSession().getAttribute(Constants.CONTEXT_ATTR_NAME);
			context.put(Constants.STATUS_INFO, state);
			
			// verifico che lo sportello sia aperto 
			// questo controllo e' bloccante per Jira FINDOM-68 
			boolean isOpen = ActionUtil.isSportelloOpen (aggrDomanda);
			log.debug(logprefix + " isOpen="+isOpen);
			if(isOpen)
				setSportelloOpen("true");
			else
				setSportelloOpen("false");
			
//			// verifico il numero di domande inviate e passo il risultato alla conferma_inviaDomanda.jsp in modo da gestire un messaggio nella modale di conferma invio
//			// questo controllo non e' bloccante per Jira FINDOM-69
			
			setNumMaxDomandeBandoPresentate("false");
			setNumMaxDomandeSportelloPresentate("false");
			setBandoMaterializzatoSenzaPEC("false");
			
			try {
				if (ActionUtil.isMaxNumDomandeInviatePerBando(state, getServiziFindomWeb())) {
					setNumMaxDomandeBandoPresentate("true");
					log.warn(logprefix + " il numero delle domande del bando e' >= al numero massimo consentito");
				}
				if (ActionUtil.isMaxNumDomandeInviatePerSportello(state, getServiziFindomWeb())) {
					setNumMaxDomandeSportelloPresentate("true");
					log.warn(logprefix + " il numero delle domande per lo sportello e' >= al numero massimo consentito");
				}
				
				//SELECT flag_upload_index FROM FINDOM_T_BANDI WHERE id_bando = 87;
				Boolean fuInd = getServiziFindomWeb().getFlagUploadIndex(new Long(state.getTemplateId()));
				log.info(logprefix + "bando="+state.getTemplateId()+", file su Index="+fuInd);
				
				if (!StringUtils.equals(Constants.FLAG_BANDO_DEMATERIALIZZATO, state.getFlagBandoDematerializzato()) && !fuInd){
					log.info(logprefix + "imposto setBandoMaterializzatoSenzaPEC = true");
					setBandoMaterializzatoSenzaPEC("true");
				}
				
			}catch(ServiziFindomWebException e){
				log.error(logprefix + "Errore "+ e.getMessage());
				return ERROR;
			}
			
		}catch(ServiziFindomWebException e){
			log.error(logprefix + "Errore "+ e.getMessage());
			return ERROR;
		}
		
		log.debug(logprefix + "END");
		return "invia_success";
	}
	
	private void aggiornaStatus(AggrDataDto dom, StatusInfo stato) {

		// TODO : i dati nello status sono da ricalcolare (tutti invalidi)
		stato.setTemplateId(dom.getTemplateId());
		stato.setModelName(dom.getModelName());
		stato.setStatoProposta(dom.getModelStateFk());
		stato.setNumSportello(dom.getIdSportelloBando());//numSportello
		stato.setAperturaSportelloDa(dom.getDataAperturaSportello());
		stato.setAperturaSportelloA(dom.getDataChiusuraSportello());
		stato.setDescrNormativa(""); //descrNormativa
		stato.setCodiceAzione(""); //codiceAzione
		stato.setDescrBando(""); //descrBando
		stato.setDescrBreveBando(""); //descrBreveBando
		stato.setFlagBandoDematerializzato("");
		stato.setTipoFirma("");
		
		// questi non devono cambiare
//		stato.setIdSoggettoBeneficiario(dom.getIdSoggettoBeneficiario()); //idSoggettoBeneficiario
//		stato.setCodFiscaleBeneficiario(codFiscaleBeneficiario);
//		stato.setDescrImpresaEnte(descrImpresaEnte);
//		stato.setDescrTipolBeneficiario(descrTipolBeneficiario);
//		
		setStatus(stato);
		
		TreeMap<String, Object> context = (TreeMap<String, Object>) this.getServletRequest().getSession().getAttribute(Constants.CONTEXT_ATTR_NAME);
		context.put(Constants.STATUS_INFO, stato);
		
	}
	
	// GETTERS && SETTERS
	
	public String getIdDomanda() {
		return idDomanda;
	}

	public void setIdDomanda(String idDomanda) {
		this.idDomanda = idDomanda;
	}

	public String getBandoDematerializzato() {
		return bandoDematerializzato;
	}

	public void setBandoDematerializzato(String bandoDematerializzato) {
		this.bandoDematerializzato = bandoDematerializzato;
	}

	public String getNumMaxDomandeBandoPresentate() {
		return numMaxDomandeBandoPresentate;
	}

	public void setNumMaxDomandeBandoPresentate(String numMaxDomandeBandoPresentate) {
		this.numMaxDomandeBandoPresentate = numMaxDomandeBandoPresentate;
	}

	public String getNumMaxDomandeSportelloPresentate() {
		return numMaxDomandeSportelloPresentate;
	}

	public void setNumMaxDomandeSportelloPresentate(
			String numMaxDomandeSportelloPresentate) {
		this.numMaxDomandeSportelloPresentate = numMaxDomandeSportelloPresentate;
	}

	public String getSportelloOpen() {
		return sportelloOpen;
	}

	public void setSportelloOpen(String sportelloOpen) {
		this.sportelloOpen = sportelloOpen;
	}

	public String getRuoloCapofilaProgettoComune() {
		return ruoloCapofilaProgettoComune;
	}

	public void setRuoloCapofilaProgettoComune(String ruoloCapofilaProgettoComune) {
		this.ruoloCapofilaProgettoComune = ruoloCapofilaProgettoComune;
	}

	public String getRuoloPartnerProgettoComune() {
		return ruoloPartnerProgettoComune;
	}

	public void setRuoloPartnerProgettoComune(String ruoloPartnerProgettoComune) {
		this.ruoloPartnerProgettoComune = ruoloPartnerProgettoComune;
	}

	public String getBandoMaterializzatoSenzaPEC() {
		return bandoMaterializzatoSenzaPEC;
	}

	public void setBandoMaterializzatoSenzaPEC(String bandoMaterializzatoSenzaPEC) {
		this.bandoMaterializzatoSenzaPEC = bandoMaterializzatoSenzaPEC;
	}

}
