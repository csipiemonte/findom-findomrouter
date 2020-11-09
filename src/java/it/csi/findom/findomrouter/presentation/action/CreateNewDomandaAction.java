/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.TipolBeneficiariDto;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.VistaDomandeBeneficiariDto;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.VistaDomandeDto;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.VistaSportelliAttiviDto;
import it.csi.findom.findomrouter.exception.ServiziFindomWebException;
import it.csi.findom.findomrouter.presentation.util.ActionUtil;
import it.csi.findom.findomrouter.presentation.util.Constants;
import it.csi.findom.findomrouter.presentation.util.StringUtils;
import it.csi.findom.findomrouter.presentation.vo.Domanda;
import it.csi.findom.findomrouter.presentation.vo.SelItem;
import it.csi.findom.findomrouter.presentation.vo.StatusInfo;
import it.csi.findom.findomrouter.presentation.vo.UserInfo;
import it.csi.findom.findomrules.business.RegoleExecutionResult;
import it.csi.findom.findomrules.business.RegoleManager;
import it.csi.findom.findomrules.dto.Regola;


public class CreateNewDomandaAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;

	private static final String CLASS_NAME = "CreateNewDomandaAction";

	// liste del form di inserimento nuova domanda
	SelItem[] listaNormativeINS;
	SelItem[] listadescBreveBandoINS;
	SelItem[] listaBandiINS;
	SelItem[] listaSportelliINS;
	TipolBeneficiariDto[] listaTipologieBeneficiariINS;
	
	// campi che vengono postati per l'inserimetno di una nuova domanda
	String normativaINS;
	String descBreveBandoINS;
	String bandoINS;
	String sportelloINS;
	String tipologiaBeneficiarioINS;
	
	SelItem[] listaAreeTematicheINS;
	Integer areaTematicaINS;
	
	// campi che vengono postati per la ricerca  (sono nel form di inserimento, quindi li azzero)
	String normativa;
	String descBreveBando;
	String bando;
	String sportello;
	String statoDomanda;
	String numDomanda;
	
	// liste del form di ricerca  (sono nel form di inserimento, quindi li azzero)
	SelItem[] listaNormative;
	SelItem[] listadescBreveBando;
	SelItem[] listaBandi;
	SelItem[] listaSportelli;
	SelItem[] listaStati;
	
	SelItem[] listaAreeTematiche;
	Integer areaTematica;
	
	// lista delle domande trovate (sono nel form di inserimento, quindi li azzero)
	ArrayList<Domanda> listaDomande ;
		
	String numMaxDomandeBandoPresentate;// attributo per differenziare il testo della modale di conferma invio domanda in base al fatto che l'utente abbia inviato il numero massimo di domande o meno
	String numMaxDomandeSportelloPresentate;// attributo per differenziare il testo della modale di conferma invio domanda in base al fatto che l'utente abbia inviato il numero massimo di domande o meno
	String sportelloOpen;  // attributo per differenziare il testo della modale di conferma invio domanda in base al fatto che lo sportello sia aperto o meno

	String showPopup;
	
	@Override
	public String executeAction() throws SystemException, UnrecoverableException, CSIException {

		/*
		 * Considerazioni
		 *  - nella findom_t_bandi non ci possono essere piu' bandi per lo stesso template, e' stata fatta la scelta    1 Bando == 1 template
		 *  - un bando == un template vuol dire findom_t_bandi.id_bando  = aggr_t_template.template_id
		 * 
		 */
		final String methodName = "executeAction";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		log.debug(logprefix + " BEGIN");
		
		return SUCCESS;
	}
	
	public String verificaDomandeInviate() throws SystemException, UnrecoverableException, CSIException {

		/*
		 * Considerazioni
		 *  - nella findom_t_bandi non ci possono essere piu' bandi per lo stesso template, e' stata fatta la scelta    1 Bando == 1 template
		 *  - un bando == un template vuol dire findom_t_bandi.id_bando  = aggr_t_template.template_id
		 * 
		 */
		final String methodName = "verificaDomandeInviate";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		log.debug(logprefix + " BEGIN");
		
		log.debug(logprefix + " normativaINS=["+normativaINS+"]");
		log.debug(logprefix + " descBreveBandoINS=["+descBreveBandoINS+"]");
		log.debug(logprefix + " bandoINS=["+bandoINS+"]");
		log.debug(logprefix + " sportelloINS=["+sportelloINS+"]");
		log.debug(logprefix + " tipologiaBeneficiarioINS=["+tipologiaBeneficiarioINS+"]");
		log.debug(logprefix + " areaTematicaINS=["+areaTematicaINS+"]");


		// VERIFICHE SUI DATI postati
		boolean datiErrati = false;
		setShowPopup("false");

		if(areaTematicaINS==null || areaTematicaINS<0){
			log.warn(logprefix + " errore nel campo Area Tematica");
			addFieldError("areaTematicaINS", Constants.ERR_MESSAGE_FIELD_OBBL);
			datiErrati = true;
		}
		
		if(StringUtils.isBlank(normativaINS) || StringUtils.equals("-1", normativaINS)){
			log.warn(logprefix + " errore nel campo Normativa");
			addFieldError("normativaINS", Constants.ERR_MESSAGE_FIELD_OBBL);
			datiErrati = true;
		}
		
		if(StringUtils.isBlank(descBreveBandoINS) || StringUtils.equals("-1", descBreveBandoINS)){
			log.warn(logprefix + " errore nel campo Descrizione Breve Bando");
			addFieldError("descBreveBandoINS", Constants.ERR_MESSAGE_FIELD_OBBL);
			datiErrati = true;
		}
		
		if(StringUtils.isBlank(bandoINS) || StringUtils.equals("-1", bandoINS)){
			log.warn(logprefix + " errore nel campo Bando");
			addFieldError("bandoINS", Constants.ERR_MESSAGE_FIELD_OBBL);
			datiErrati = true;
		}
		
		if(StringUtils.isBlank(sportelloINS) || StringUtils.equals("-1", sportelloINS)){
			log.warn(logprefix + " errore nel campo Sportello");
			addFieldError("sportelloINS", Constants.ERR_MESSAGE_FIELD_OBBL);
			datiErrati = true;
		}
		
		if(StringUtils.isBlank(tipologiaBeneficiarioINS) || StringUtils.equals("-1", tipologiaBeneficiarioINS)){
			log.warn(logprefix + " errore nel campo Tipologia Beneficiario");
			addFieldError("tipologiaBeneficiarioINS", Constants.ERR_MESSAGE_FIELD_OBBL);
			datiErrati = true;
		}
		
		if(datiErrati){
			log.debug(logprefix + " errori nei dati del form di inserimento");
			addActionError(Constants.ERR_MESSAGE_PARAM_ERROR);
			// popola le liste
			loadListe();
			return INPUT;
		}
		
		verificaSportelliAttivi();
		
		// fine VERIFICHE SUI DATI postati
		///////////////////////////////////
		// eseguo l'algoritmo A02 (findom_v_domande_beneficiari) e conto le domande presentate su quel bando per quel beneficiario
		
		StatusInfo state = getStatus();
		Integer idSoggBen = state.getIdSoggettoBeneficiario();
		log.debug(logprefix + " idSoggBen="+idSoggBen);
		
		if(idSoggBen==null){
			log.warn(logprefix + " IdSoggettoBeneficiario NULLO, impossibile proseguire");
			addActionError(Constants.ERR_MESSAGE_NEWDOMANDA_SOGGBEN);
			// popola le liste
			loadListe();
			return INPUT;
		}
						
		// estraggo la lista delle domande inviate
		ArrayList<VistaDomandeBeneficiariDto> listaDomandeInviate = getServiziFindomWeb().getVistaDomandeBeneficiari(Integer.parseInt(bandoINS),idSoggBen);
		
		if(listaDomandeInviate!=null)
			log.debug(logprefix + " listaDomandeInviate.size()="+listaDomandeInviate.size());
		else
			log.debug(logprefix + " listaDomandeInviate NULLA");
		
		boolean anomalia = false;

		// verificato con Laura basta id_bando ( non devo cercare con i 3 ID )
		ArrayList<VistaSportelliAttiviDto> listaSportelliAttivi =  getServiziFindomWeb().getVistaSportelliAttiviByFilter(null, Integer.parseInt(bandoINS), state.getSiglaNazioneAzienda(), areaTematicaINS);
		
		// la lista degli sportelli non dovrebbe mai essere nulla
		if(listaSportelliAttivi==null || ( listaSportelliAttivi!=null && listaSportelliAttivi.size()==0)){
			log.error(logprefix + " nessuna entry trovata nella lista degli sportelli attivi");
			 anomalia = true;
			 setSportelloOpen("false");
		}
		else
		{
			setSportelloOpen("true");
		}
		
		setNumMaxDomandeBandoPresentate("false");
		setNumMaxDomandeSportelloPresentate("false");
		
		if(listaDomandeInviate!=null && listaDomandeInviate.size()>0){
			// Verifica 1) 
			//	[num domande bando] sia < [num max domande bando], se quest'ultimo e' valorizzato
			//	[num domande sportello] sia < [num max domande sportello],  se quest'ultimo e' valorizzato.
			// In caso contrario non permette di proseguire segnalando l'anomalia.
			
			// prendo i dati dal primo elemento della lista
			int numDomandeBandoInviate = listaDomandeInviate.get(0).getNumDomandeBando();
			int numDomandeSportelloInviate = listaDomandeInviate.get(0).getNumDomandeSportello();
			
			log.debug(logprefix + " numDomandeBandoInviate="+numDomandeBandoInviate);
			log.debug(logprefix + " numDomandeSportelloInviate="+numDomandeSportelloInviate);
			
			// riottengo i dati  [num max domande sportello] e  [num max domande bando] dalla vista findom_v_sportelli_attivi
			Integer numMaxDomBando = null;
			Integer numMaxDomSportello = null;
			
			if(listaSportelliAttivi!=null && listaSportelliAttivi.size()>0){
				// NOTA : listaSportelliAttivi.size al massimo ha un elemento
				if(listaSportelliAttivi.get(0).getNumMaxDomandeBando()!=null)
					numMaxDomBando = Integer.parseInt(listaSportelliAttivi.get(0).getNumMaxDomandeBando());
				if(listaSportelliAttivi.get(0).getNumMaxDomandeSportello()!=null)
					numMaxDomSportello =  Integer.parseInt(listaSportelliAttivi.get(0).getNumMaxDomandeSportello());
			}
			
			log.debug(logprefix + " numMaxDomBando="+numMaxDomBando);
			log.debug(logprefix + " numMaxDomSportello="+numMaxDomSportello);
			
			if(numMaxDomBando!=null){
				if(numDomandeBandoInviate >= numMaxDomBando){
					anomalia = true;
					setNumMaxDomandeBandoPresentate("true");
					log.warn(logprefix + " il numero delle domande del bando e' >= al numero massimo consentito");
				}
			}
			if(numMaxDomSportello!=null){
				if(numDomandeSportelloInviate >= numMaxDomSportello){
					anomalia = true;
					setNumMaxDomandeSportelloPresentate("true");
					log.warn(logprefix + " il numero delle domande per lo sportello e' >= al numero massimo consentito");
				}
			}
		}
		log.debug(logprefix + " >>> anomalia="+anomalia);

		log.debug(logprefix + " bandoINS="+bandoINS);

		//
		// Sezione : esecuzione eventuali regole all'inserimento di una nuova domanda 
		// TODO : accentrare qui i controllo presenti anche su FINDOMWEBNEW
		//
		if(StringUtils.isNotBlank(bandoINS)) {
			Integer idBando = Integer.parseInt(bandoINS);
			log.debug(logprefix + " idBando="+idBando);
			int idTipoRegola = 7; // regole che scattano alla creazione di una nuova domanda

			List<Regola> regoleList =   getServiziFindomWeb().getRegoleFromDB(idTipoRegola, idBando);
			log.debug(logprefix + " RegoleList "+((regoleList!=null) ? "size="+regoleList.size() : "null"));
			
			if (regoleList!=null) {
				
				Map parametri = new HashMap<String, Object>();
				parametri.put("statusInfo", state);
				parametri.put("userInfo", getUserInfo());
				parametri.put("idTipologiaBeneficiario", tipologiaBeneficiarioINS);
				parametri.put("idBando", idBando);
				
				for (Regola regola : regoleList) {
					log.debug(logprefix + " corpo regola="+regola.getCorpoRegola());
				
					try {
						RegoleExecutionResult rgExec = RegoleManager.eseguiRegolaIns(idBando, regola, parametri);
						
						log.debug(logprefix + " exec fatto, isExecutionSucceded="+ rgExec.isExecutionSucceded());
						
						if(!rgExec.isExecutionSucceded()) {
							String te = "La regola ["+regola.getCodiceRegola()+"] impedisce la creazione di una nuova domanda";
							
							log.warn(logprefix + te );
							
							// Il messaggio da visualizzare arriva dalla regola
							addActionError( rgExec.getErrorMessages().get(0) + "");
							
							try {
								UserInfo us = getUserInfo();
								getServiziFindomWeb().insertLogAudit(Constants.CSI_LOG_IDAPPL, "",
										us.getCodFisc() + " - " + us.getCognome() + " " + us.getNome(), Constants.CSI_LOG_OPER_INSERT_DOMANDA,
										te, "");
							} catch (ServiziFindomWebException e) {
								log.error(logprefix + " impossibile scrivere CSI_LOG_AUDIT: " + e);
							}
							
							// popola le liste
							loadListe();
							return INPUT;
						}
						
					} catch (ClassNotFoundException e) {
						log.error(logprefix + "Error ClassNotFoundException"+e.getMessage());
					} catch (InstantiationException e) {
						log.error(logprefix + "Error InstantiationException"+e.getMessage());
					} catch (IllegalAccessException e) {
						log.error(logprefix + "Error IllegalAccessException"+e.getMessage());
					}
				}
			}
		}
		
		if(anomalia){
			log.warn(logprefix + " numero domande eccessivo, impossibile proseguire");
			log.debug(logprefix + " sportelloOpen="+sportelloOpen);
			loadListe();
			setShowPopup("true");

			return "invia_success";
		}
		
		log.debug(logprefix + " sportelloOpen="+sportelloOpen);
		log.debug(logprefix + "END");
		return SUCCESS;
	}

	private boolean loadListe() {
		final String methodName = "loadListe";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		log.debug(logprefix + " BEGIN");

		boolean result = true;
		log.debug(logprefix + " le liste del form di ricerca non sono in request, li ricarico");

		StatusInfo state = getStatus();
		ArrayList<VistaDomandeDto> listaVistaDomandaDto = null; // lista delle domande presentate

		try {
			// elenco delle domande presentate dalla coppia
			// utenteLoggato+ImpresaScelta
			if ((Constants.RUOLO_AMM).equals(getUserInfo().getRuolo())
					|| (Constants.RUOLO_LR).equals(getUserInfo().getRuolo())) {
				// se utente ha ruolo AMMINISTRATORE
				listaVistaDomandaDto = getServiziFindomWeb().getVistaDomanda(null, state.getIdSoggettoBeneficiario(), null, null, null, areaTematica);
				
			} else {
				listaVistaDomandaDto = getServiziFindomWeb().getVistaDomanda(state.getIdSoggettoCollegato(), state.getIdSoggettoBeneficiario(), null, null, null, areaTematica);
			}

			Map mappaNormative = new HashMap(); // mappa Normative
			Map mappaDescrBreve = new HashMap(); // mappa Descrizione Breve BAndo
			Map mappaBando = new HashMap(); // mappa Bando
			Map mappaSportelli = new HashMap(); // mappa Sportelli
			Map mappaStati= new HashMap(); // mappa Stati
			Map mappaAreeTematiche= new HashMap(); // mappa Aree Tematiche

			if (listaVistaDomandaDto != null) {
				log.debug(logprefix + " listaVistaDomandaDto.size()=" + listaVistaDomandaDto.size());

				// devo estrarre dati univoci dall'elenco ottenuto
				// scorro la lista e popolo tante mappe quanti sono le combo
				// (questo per escludere doppioni)
				for (VistaDomandeDto vistaDomandeDto : listaVistaDomandaDto) {
					mappaNormative.put(vistaDomandeDto.getIdNormativa(), vistaDomandeDto.getNormativa());
					// mappaDescrBreve.put(vistaDomandeDto.getIdBando(),
					// vistaDomandeDto.getCodiceAzione()+" -
					// "+vistaDomandeDto.getDescrBreveBando()); // il
					// codiceAzione e' incluso nella descrBreveBando
					mappaDescrBreve.put(vistaDomandeDto.getIdBando(), vistaDomandeDto.getDescrBreveBando());
					mappaBando.put(vistaDomandeDto.getIdBando(), vistaDomandeDto.getDescrBando());
					mappaStati.put(vistaDomandeDto.getCodStatoDomanda(), vistaDomandeDto.getStatoDomanda());
					if(StringUtils.isNotBlank(vistaDomandeDto.getDtChiusuraSportello())){
						mappaSportelli.put(vistaDomandeDto.getIdSportelloBando(), vistaDomandeDto.getDtAperturaSportello() + " - " + vistaDomandeDto.getDtChiusuraSportello());
					}else{
						mappaSportelli.put(vistaDomandeDto.getIdSportelloBando(), vistaDomandeDto.getDtAperturaSportello() + " - data fine non definita");
					}
					mappaAreeTematiche.put(vistaDomandeDto.getIdAreaTematica(), vistaDomandeDto.getDescrizioneAreaTematica());
				}

				log.debug(logprefix + "mappaNormative=" + mappaNormative.toString());
				log.debug(logprefix + "mappaDescrBreve=" + mappaDescrBreve.toString());
				log.debug(logprefix + "mappaBando=" + mappaBando.toString());
				log.debug(logprefix + "mappaStati=" + mappaStati.toString());
				log.debug(logprefix + "mappaSportelli=" + mappaSportelli.toString());
				log.debug(logprefix + "mappaAreeTematiche=" + mappaAreeTematiche.toString());

			} else {
				log.debug(logprefix + " listaVistaDomandaDto NULL");
			}

			// usando le mappe popolo le liste che valorizzeranno le combo
			listaNormative = ActionUtil.popolaArrayForCombo(mappaNormative);
			listadescBreveBando = ActionUtil.popolaArrayForCombo(mappaDescrBreve);
			listaBandi = ActionUtil.popolaArrayForCombo(mappaBando);
			listaStati = ActionUtil.popolaArrayStatiForCombo(mappaStati);
			listaSportelli = ActionUtil.popolaArrayForCombo(mappaSportelli);
			listaAreeTematiche = ActionUtil.popolaArrayForCombo(mappaAreeTematiche);

		} catch (ServiziFindomWebException e) {

			log.error(logprefix + " errore nel reperimento delle domande presentate, :" + e.getMessage());
			result = false;
		}
		
		getServletRequest().getSession().setAttribute("listaNormative", listaNormative);
		getServletRequest().getSession().setAttribute("listadescBreveBando", listadescBreveBando);
		getServletRequest().getSession().setAttribute("listaBandi", listaBandi);
		getServletRequest().getSession().setAttribute("listaSportelli", listaSportelli);
		getServletRequest().getSession().setAttribute("listaStati", listaStati);
		getServletRequest().getSession().setAttribute("listaAreeTematiche", listaAreeTematiche);

		// in caso non ci siano valori, inizializzo il primo elemento dell'array
		if (listaNormative == null) {
			listaNormative = new SelItem[0];
		}
		if (listadescBreveBando == null) {
			listadescBreveBando = new SelItem[0];
		}
		if (listaBandi == null) {
			listaBandi = new SelItem[0];
		}
		if (listaSportelli == null) {
			listaSportelli = new SelItem[0];
		}
		if (listaStati == null) {
			listaStati = new SelItem[0];
		}
		if (listaAreeTematiche == null) {
			listaAreeTematiche = new SelItem[0];
		}

		//////////////////////////////////////////////////////////////////////////////
		/////////////////// FORM DI INSERIMENTO
		//////////////////////////////////////////////////////////////////////////////

		log.debug(logprefix + "listaNormativeINS presa da request="
				+ getServletRequest().getSession().getAttribute("listaNormativeINS"));
		log.debug(logprefix + "listaBandiINS presa da request="
				+ getServletRequest().getSession().getAttribute("listaBandiINS"));
		log.debug(logprefix + "listadescBreveBandoINS presa da request="
				+ getServletRequest().getSession().getAttribute("listadescBreveBandoINS"));
		log.debug(logprefix + "listaSportelliINS presa da request="
				+ getServletRequest().getSession().getAttribute("listaSportelliINS"));
		log.debug(logprefix + "listaTipologieBeneficiariINS presa da request="
				+ getServletRequest().getSession().getAttribute("listaTipologieBeneficiariINS"));

		// popolo le combo del form di inserimento
		listaNormativeINS = (SelItem[]) getServletRequest().getSession().getAttribute("listaNormativeINS");
		listadescBreveBandoINS = (SelItem[]) getServletRequest().getSession().getAttribute("listadescBreveBandoINS");
		listaBandiINS = (SelItem[]) getServletRequest().getSession().getAttribute("listaBandiINS");
		listaSportelliINS = (SelItem[]) getServletRequest().getSession().getAttribute("listaSportelliINS");
		listaTipologieBeneficiariINS = (TipolBeneficiariDto[]) getServletRequest().getSession()
				.getAttribute("listaTipologieBeneficiariINS");

		listaAreeTematicheINS = (SelItem[]) getServletRequest().getSession().getAttribute("listaAreeTematicheINS");
		
		if (listaNormativeINS != null && listadescBreveBandoINS != null && listaBandiINS != null
				&& listaSportelliINS != null && listaTipologieBeneficiariINS != null) {
			// sono in request, NON li ricalcolo
			log.debug(logprefix + " uso le liste del form di inserimento prese dalla request");

		} else {

			log.debug(logprefix + " le liste del form di inserimento non sono in request, li ricarico");
			ArrayList<VistaSportelliAttiviDto> listaSportelliAttivi = null;

			try {
				
				listaSportelliAttivi = getServiziFindomWeb().getVistaSportelliAttiviByFilter(null, null, state.getSiglaNazioneAzienda(), areaTematicaINS);
				// scorro la lista e popolo tante mappe quanti sono le combo
				// (questo per escludere doppioni)
				Map mappaNormativeIns = new HashMap(); // mappa Normative
				Map mappaDescrBreveIns = new HashMap(); // mappa Descrizione Breve BAndo
				Map mappaBandoIns = new HashMap(); // mappa BAndo
				Map mappaAreeTematicheIns = new HashMap(); 		// mappa AreeTerritoriali
				
				if (listaSportelliAttivi != null) {
					log.debug(logprefix + " listaSportelliAttivi.size =" + listaSportelliAttivi.size());

					for (VistaSportelliAttiviDto sport : listaSportelliAttivi) {
						mappaNormativeIns.put(sport.getIdNormativa(), sport.getNormativa());
						mappaDescrBreveIns.put(sport.getIdBando(), sport.getCodiceAzione() + " - " + sport.getDescrizioneBreveBando());
						mappaBandoIns.put(sport.getIdBando(), sport.getDescrizioneBando());
						mappaAreeTematicheIns.put(sport.getIdAreaTematica(), sport.getDescrizioneAreaTematica());
					}

					log.debug(logprefix + "mappaNormativeIns=" + mappaNormativeIns.toString());
					log.debug(logprefix + "mappaDescrBreveIns=" + mappaDescrBreveIns.toString());
					log.debug(logprefix + "mappaBandoIns=" + mappaBandoIns.toString());
					log.debug(logprefix + "mappaAreeTematicheIns=" + mappaAreeTematicheIns.toString());

				} else {
					log.debug(logprefix + " listaSportelliAttivi NULL");
				}

				// usando le mappe popolo le liste che valorizzeranno le combo
				listaNormativeINS = ActionUtil.popolaArrayForCombo(mappaNormativeIns);
				listadescBreveBandoINS = ActionUtil.popolaArrayForCombo(mappaDescrBreveIns);
				listaBandiINS = ActionUtil.popolaArrayForCombo(mappaBandoIns);
				listaAreeTematicheINS = ActionUtil.popolaArrayForCombo(mappaAreeTematicheIns);
				
			} catch (ServiziFindomWebException e) {

				log.error(logprefix + " errore nel reperimento delle listaSportelliAttivi, :" + e.getMessage());
				result = false;
			}

			// la combo degli sportelli viene popolata a seguito di
			// selezione del bando nel campo 'Descrizone breve bando' o nel
			// campo 'Bando'
			// quindi nelle Action ChangeBandoInsAction e
			// ChangeDescrBreveInsAction
			listaSportelliINS = new SelItem[0];
			
			// la combo dei Beneficiari viene popolata a seguito di selezione
			// del bando nel campo 'Descrizone breve bando' o nel campo 'Bando'
			// quindi nelle Action ChangeBandoInsAction e
			// ChangeDescrBreveInsAction
			listaTipologieBeneficiariINS = new TipolBeneficiariDto[0];

			getServletRequest().getSession().setAttribute("listaNormativeINS", listaNormativeINS);
			getServletRequest().getSession().setAttribute("listadescBreveBandoINS", listadescBreveBandoINS);
			getServletRequest().getSession().setAttribute("listaBandiINS", listaBandiINS);
			getServletRequest().getSession().setAttribute("listaSportelliINS", listaSportelliINS);
			getServletRequest().getSession().setAttribute("listaTipologieBeneficiariINS", listaTipologieBeneficiariINS);
			getServletRequest().getSession().setAttribute("listaAreeTematicheINS", listaAreeTematicheINS);
		}

		// in caso non ci siano valori, inizializzo il primo elemento dell'array
		if (listaNormativeINS == null) {
			log.debug(logprefix + " listaStati NULLA, la inizializzo");
			listaNormativeINS = new SelItem[0];
		}
		if (listadescBreveBandoINS == null) {
			log.debug(logprefix + " listaStati NULLA, la inizializzo");
			listadescBreveBandoINS = new SelItem[0];
		}
		if (listaBandiINS == null) {
			log.debug(logprefix + " listaStati NULLA, la inizializzo");
			listaBandiINS = new SelItem[0];
		}
		if (listaSportelliINS == null) {
			log.debug(logprefix + " listaStati NULLA, la inizializzo");
			listaSportelliINS = new SelItem[0];
		}
		if (listaTipologieBeneficiariINS == null) {
			log.debug(logprefix + " listaStati NULLA, la inizializzo");
			listaTipologieBeneficiariINS = new TipolBeneficiariDto[0];
		}
		if (listaAreeTematicheINS == null) {
			log.debug(logprefix + " listaAreeTematicheINS NULLA, la inizializzo");
			listaAreeTematicheINS = new SelItem[0];
		}
		log.debug(logprefix + " END");
		return result;
	}


	public SelItem[] getListaNormativeINS() {
		return listaNormativeINS;
	}
	public void setListaNormativeINS(SelItem[] listaNormativeINS) {
		this.listaNormativeINS = listaNormativeINS;
	}
	public SelItem[] getListaBandiINS() {
		return listaBandiINS;
	}
	public void setListaBandiINS(SelItem[] listaBandiINS) {
		this.listaBandiINS = listaBandiINS;
	}
	public SelItem[] getListaSportelliINS() {
		return listaSportelliINS;
	}
	public void setListaSportelliINS(SelItem[] listaSportelliINS) {
		this.listaSportelliINS = listaSportelliINS;
	}
	public String getNormativaINS() {
		return normativaINS;
	}
	public void setNormativaINS(String normativaINS) {
		this.normativaINS = normativaINS;
	}
	public String getBandoINS() {
		return bandoINS;
	}
	public void setBandoINS(String bandoINS) {
		this.bandoINS = bandoINS;
	}
	public String getSportelloINS() {
		return sportelloINS;
	}
	public void setSportelloINS(String sportelloINS) {
		this.sportelloINS = sportelloINS;
	}
	public String getTipologiaBeneficiarioINS() {
		return tipologiaBeneficiarioINS;
	}
	public void setTipologiaBeneficiarioINS(String tipologiaBeneficiarioINS) {
		this.tipologiaBeneficiarioINS = tipologiaBeneficiarioINS;
	}
	public String getDescBreveBandoINS() {
		return descBreveBandoINS;
	}
	public void setDescBreveBandoINS(String descBreveBandoINS) {
		this.descBreveBandoINS = descBreveBandoINS;
	}
	public String getNormativa() {
		return normativa;
	}
	public void setNormativa(String normativa) {
		this.normativa = normativa;
	}
	public String getDescBreveBando() {
		return descBreveBando;
	}
	public void setDescBreveBando(String descBreveBando) {
		this.descBreveBando = descBreveBando;
	}
	public String getBando() {
		return bando;
	}
	public void setBando(String bando) {
		this.bando = bando;
	}
	public String getSportello() {
		return sportello;
	}
	public void setSportello(String sportello) {
		this.sportello = sportello;
	}
	public String getStatoDomanda() {
		return statoDomanda;
	}
	public void setStatoDomanda(String statoDomanda) {
		this.statoDomanda = statoDomanda;
	}
	public String getNumDomanda() {
		return numDomanda;
	}
	public void setNumDomanda(String numDomanda) {
		this.numDomanda = numDomanda;
	}
	public SelItem[] getListaNormative() {
		return listaNormative;
	}
	public void setListaNormative(SelItem[] listaNormative) {
		this.listaNormative = listaNormative;
	}
	public SelItem[] getListadescBreveBando() {
		return listadescBreveBando;
	}
	public void setListadescBreveBando(SelItem[] listadescBreveBando) {
		this.listadescBreveBando = listadescBreveBando;
	}
	public SelItem[] getListaBandi() {
		return listaBandi;
	}
	public void setListaBandi(SelItem[] listaBandi) {
		this.listaBandi = listaBandi;
	}
	public SelItem[] getListaSportelli() {
		return listaSportelli;
	}
	public void setListaSportelli(SelItem[] listaSportelli) {
		this.listaSportelli = listaSportelli;
	}
	public SelItem[] getListaStati() {
		return listaStati;
	}
	public void setListaStati(SelItem[] listaStati) {
		this.listaStati = listaStati;
	}
	public ArrayList<Domanda> getListaDomande() {
		return listaDomande;
	}
	public void setListaDomande(ArrayList<Domanda> listaDomande) {
		this.listaDomande = listaDomande;
	}
	public SelItem[] getListadescBreveBandoINS() {
		return listadescBreveBandoINS;
	}
	public void setListadescBreveBandoINS(SelItem[] listadescBreveBandoINS) {
		this.listadescBreveBandoINS = listadescBreveBandoINS;
	}
	public TipolBeneficiariDto[] getListaTipologieBeneficiariINS() {
		return listaTipologieBeneficiariINS;
	}
	public void setListaTipologieBeneficiariINS(
			TipolBeneficiariDto[] listaTipologieBeneficiariINS) {
		this.listaTipologieBeneficiariINS = listaTipologieBeneficiariINS;
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

	public String getShowPopup() {
		return showPopup;
	}

	public void setShowPopup(String showPopup) {
		this.showPopup = showPopup;
	}

	public Integer getAreaTematicaINS() {
		return areaTematicaINS;
	}

	public void setAreaTematicaINS(Integer areaTematicaINS) {
		this.areaTematicaINS = areaTematicaINS;
	}

	public SelItem[] getListaAreeTematicheINS() {
		return listaAreeTematicheINS;
	}

	public void setListaAreeTematicheINS(SelItem[] listaAreeTematicheINS) {
		this.listaAreeTematicheINS = listaAreeTematicheINS;
	}

	public SelItem[] getListaAreeTematiche() {
		return listaAreeTematiche;
	}

	public void setListaAreeTematiche(SelItem[] listaAreeTematiche) {
		this.listaAreeTematiche = listaAreeTematiche;
	}

	public Integer getAreaTematica() {
		return areaTematica;
	}

	public void setAreaTematica(Integer areaTematica) {
		this.areaTematica = areaTematica;
	}
}
