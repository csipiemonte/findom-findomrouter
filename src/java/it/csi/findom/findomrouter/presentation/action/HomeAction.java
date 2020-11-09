/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.action;

import java.util.ArrayList;
import java.util.TreeMap;
import com.opensymphony.xwork2.ValidationAware;
import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.ShellSoggettiDto;
import it.csi.findom.findomrouter.exception.ServiziFindomWebException;
import it.csi.findom.findomrouter.presentation.util.ActionUtil;
import it.csi.findom.findomrouter.presentation.util.Constants;
import it.csi.findom.findomrouter.presentation.util.StringUtils;
import it.csi.findom.findomrouter.presentation.vo.ImpresaEnte;
import it.csi.findom.findomrouter.presentation.vo.SelItem;
import it.csi.findom.findomrouter.presentation.vo.UserInfo;
import it.csi.util.performance.StopWatch;

public class HomeAction extends BaseAction implements ValidationAware {

	private static final long serialVersionUID = 1L;
	
	private static final String CLASS_NAME = "HomeAction";

	public ArrayList<ImpresaEnte> listaImprese = null;
	
	private String hideImpresa = "true"; // parametro per visualizzare il form di inserimento nuova impresa
   
	private String flagEstero;
	private SelItem[] listaStatiEsteri; 
	private String statoEstero;  

	@Override
	public String executeAction() throws SystemException, UnrecoverableException, CSIException {
		
		final String methodName = "executeAction";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";

		log.info(logprefix + " BEGIN");
		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
		stopWatch.start();

		log.info(logprefix + " areaTematica=" + super.getAreaTematica());
		log.info(logprefix + " getUserInfo().getCodFisc()="+getUserInfo().getCodFisc()); // AAAAAA00A11D000L
		
		// recupero i dati dalla SHELL_T_SOGGETTI relativi al CF dell'utente loggato
		ShellSoggettiDto soggetto = getServiziFindomWeb().getDatiSoggettoByCodiceFiscale(getUserInfo().getCodFisc());
		log.info(logprefix + " dati del soggetto="+soggetto);
		
		log.info(logprefix + " dati del soggetto collegato al CF loggato : caricati");
		
		// recupero le domande presentate dal soggetto collegato
		if(soggetto!=null)
		{
			// soggetto collegato esiste nella shell_t_soggetti
			log.info(logprefix + "trovato soggetto con CF ="+soggetto.getCodiceFiscale());
				
			int idSogg = soggetto.getIdSoggetto();
			log.info(logprefix + " arrSoggetti.idSoggetto="+idSogg);
			log.info(logprefix + " arrSoggetti.CF="+soggetto.getCodiceFiscale());
			
			if(StringUtils.isBlank(soggetto.getNome()) || StringUtils.isBlank(soggetto.getCognome())){
				// campi nome e/o cognome nulli, li aggiorno
				aggiornaSoggetto(soggetto);
			}
			getStatus().setIdSoggettoCollegato(idSogg);
			log.info(logprefix + " impostato nello status l'id del soggetto collegato");
			
			listaImprese = loadListeImprese(idSogg);
			
		} else {
			// soggetto collegato NON esiste nella shell_t_soggetti, 
			// devo inserire il nuovo soggetto nella shell_t_soggetti
			// e fargli inserire una nuova impresa/ente
			log.debug(logprefix + " nessun soggetto trovato");

			// visualizzo la sezione per inserire una nuova impresa/ente
			hideImpresa = "false";
			
			ShellSoggettiDto newSoggetto = new ShellSoggettiDto();
			newSoggetto.setCodiceFiscale(getUserInfo().getCodFisc());
			newSoggetto.setCognome(getUserInfo().getCognome()); 
			newSoggetto.setNome(getUserInfo().getNome()); 
			//newSoggetto.setIdFormaGiuridica(3); // non lo imposto
			//newSoggetto.setDenominazione(denominazioneNuovaImpresa); // da valorizzare solo per Imprese/Enti
			//newSoggetto.setIdSoggetto(idSoggetto); // viene assegnato da sequence
			
			getServiziFindomWeb().insertShellTSoggetto(newSoggetto);
			log.debug(logprefix + " inserito nuovo soggetto sul db");
			
			getServiziFindomWeb().insertLogAudit(Constants.CSI_LOG_IDAPPL, "", 
					getUserInfo().getCodFisc()+" - "+ getUserInfo().getCognome() + " " + getUserInfo().getNome(), 
					Constants.CSI_LOG_OPER_INS_SOGGETTO, "inserito nuovo utente CF="+getUserInfo().getCodFisc(), "");
			
			// recupero l'idSoggetto appena creato
			ShellSoggettiDto soggInserito = getServiziFindomWeb().getDatiSoggettoByCodiceFiscale(getUserInfo().getCodFisc());
			log.debug(logprefix + " soggInserito.getIdSoggetto()="+soggInserito.getIdSoggetto());
			
			getStatus().setIdSoggettoCollegato(soggInserito.getIdSoggetto());
			
			loadListeImprese(soggInserito.getIdSoggetto());
			
		}
				
		listaStatiEsteri = ActionUtil.popolaArrayStatiForCombo(getMappaStatiEsteri());//MB2019_04_18
		
		if(listaImprese == null || (listaImprese!=null && listaImprese.isEmpty())){
			// nessuna impresa trovata, visualizzo il blocchetto per specificarne una nuova
			hideImpresa = "false";
		}

		// metto in getServletRequest() la lista per poterla utilizzare in caso di errori nei campi postati
		getServletRequest().getSession().setAttribute(Constants.SESSION_LISTAIMPRESE, listaImprese);
		
		stopWatch.stop();
		stopWatch.dumpElapsed("HomeAction", "executeAction()", "test", "test");
		
		
		//aggiorno l'oggetto status nel CONTEXT
		TreeMap<String, Object> context = (TreeMap<String, Object>) getServletRequest().getSession().getAttribute(Constants.CONTEXT_ATTR_NAME);
		context.put(Constants.STATUS_INFO, getStatus());
		getServletRequest().getSession().setAttribute(Constants.CONTEXT_ATTR_NAME,context);
		log.debug(logprefix + "aggiornato STATUS in CONTEXT");
			
		log.debug(logprefix + "  END");
		return SUCCESS;
	}
	

	private void aggiornaSoggetto(ShellSoggettiDto soggetto) throws ServiziFindomWebException {
		final String methodName = "aggiornaSoggetto";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		log.debug(logprefix + " BEGIN");
		
		UserInfo us = getUserInfo();
		ShellSoggettiDto newSogg = new ShellSoggettiDto();
		newSogg.setIdSoggetto(soggetto.getIdSoggetto());
		newSogg.setCognome(us.getCognome());
		newSogg.setNome(us.getNome());
		newSogg.setCodiceFiscale(soggetto.getCodiceFiscale());
		newSogg.setDenominazione(soggetto.getDenominazione());
		newSogg.setIdFormaGiuridica(soggetto.getIdFormaGiuridica());
		
		getServiziFindomWeb().updateShellTSoggetto(newSogg);
		log.debug(logprefix + " aggiornato soggetto sul db");
		getServiziFindomWeb().insertLogAudit(Constants.CSI_LOG_IDAPPL, "", soggetto.getCodiceFiscale()+" - "+ soggetto.getCognome() + " " + soggetto.getNome()
				, Constants.CSI_LOG_OPER_INS_IMPRESA, "aggiornato soggetto CF="+soggetto.getCodiceFiscale(), "");
		log.debug(logprefix + " END");
	}
		

	private ArrayList<ImpresaEnte> loadListeImprese(int idSoggetto) throws ServiziFindomWebException {
		
		final String methodName = "loadListeImprese";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		log.debug(logprefix + " BEGIN");
		
		ArrayList<ImpresaEnte> lista = null;
		
		lista = (ArrayList<ImpresaEnte>) getServletRequest().getSession().getAttribute(Constants.SESSION_LISTAIMPRESE);

		//  PK : se sto navigando la lista delle aziende tramite paginazione non ha senso ricaricare la listaSportelli
		// controllo se in request c'e' un parametro del tipo "d-7259731-p"
		
		if(lista!=null && !lista.isEmpty() ) { 
			
			log.debug(logprefix + " uso lista imprese letta da sessione per idSoggetto="+idSoggetto);
			
		}else {
		
			log.debug(logprefix + " carico lista imprese per idSoggetto="+idSoggetto);
			
			lista = getServiziFindomWeb().getListaImprese(idSoggetto);

			if(lista!=null) {
				getServletRequest().getSession().setAttribute(Constants.SESSION_LISTAIMPRESE, lista);
				log.debug(logprefix + " listaImpreseEnti messa in sessione");
			}else{
				log.debug(logprefix + " listaImpreseEnti nulla");
			}
	
		}

		log.debug(logprefix + " END");
		return lista;
	}

	//GETTERS && SETTERS
	public ArrayList<ImpresaEnte> getListaImprese() {
		return listaImprese;
	}
	public void setListaImprese(ArrayList<ImpresaEnte> listaImprese) {
		this.listaImprese = listaImprese;
	}
	public String getHideImpresa() {
		return hideImpresa;
	}
	public void setHideImpresa(String hideImpresa) {
		this.hideImpresa = hideImpresa;
	}	
	public String getFlagEstero() {
		return flagEstero;
	}
	public void setFlagEstero(String flagEstero) {
		this.flagEstero = flagEstero;
	}
	public SelItem[] getListaStatiEsteri() {
		return listaStatiEsteri;
	}
	public void setListaStatiEsteri(SelItem[] listaStatiEsteri) {
		this.listaStatiEsteri = listaStatiEsteri;
	}
	public String getStatoEstero() {
		return statoEstero;
	}
	public void setStatoEstero(String statoEstero) {
		this.statoEstero = statoEstero;
	}

}
