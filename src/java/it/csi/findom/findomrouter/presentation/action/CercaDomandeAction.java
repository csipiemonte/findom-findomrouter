/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.action;

import it.csi.aaep.aaeporch.business.AAEPException_Exception;
import it.csi.aaep.aaeporch.business.CSIException_Exception;
import it.csi.aaep.aaeporch.business.ImpresaINFOC;
import it.csi.aaep.aaeporch.business.SystemException_Exception;
import it.csi.aaep.aaeporch.business.UnrecoverableException_Exception;
import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.FormeGiuridicheDto;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.ShellSoggettiDto;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.StatoEsteroDto;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.TipolBeneficiariDto;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.VistaDomandeDto;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.VistaSportelliAttiviDto;
import it.csi.findom.findomrouter.exception.ServiziFindomWebException;
import it.csi.findom.findomrouter.integration.extservices.aaep.AaepDAO;
import it.csi.findom.findomrouter.integration.extservices.ipa.IpaDAO;
import it.csi.findom.findomrouter.presentation.util.ActionUtil;
import it.csi.findom.findomrouter.presentation.util.Constants;
import it.csi.findom.findomrouter.presentation.util.StringUtils;
import it.csi.findom.findomrouter.presentation.vo.Domanda;
import it.csi.findom.findomrouter.presentation.vo.ImpresaEnte;
import it.csi.findom.findomrouter.presentation.vo.SelItem;
import it.csi.findom.findomrouter.presentation.vo.StatusInfo;
import it.csi.findom.findomrouter.presentation.vo.UserInfo;
import it.csi.findom.findomrouter.util.TrasformaClassiAAEP;
import it.csi.findom.findomwebnew.dto.aaep.Carica;
import it.csi.findom.findomwebnew.dto.aaep.Impresa;
import it.csi.findom.findomwebnew.dto.aaep.Persona;
import it.csi.findom.findomwebnew.dto.aaep.Sede;
import it.csi.findom.findomwebnew.dto.ipa.Ipa;

import java.rmi.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.opensymphony.xwork2.ValidationAware;
import it.csi.util.performance.StopWatch;

public class CercaDomandeAction extends BaseAction implements ValidationAware {

	private static final long serialVersionUID = 1L;
	private static final String CLASS_NAME = "CercaDomandeAction";
	
	public ArrayList<ImpresaEnte> listaImprese = null;

	private String hideImpresa = "true"; // parametro per visualizzare il form di inserimento nuova impresa
	private String idImpresaEnte; 		 // id dell'impresa selezionata

	// campi inserimento nuova Impresa/Ente provenienti dalla home.jsp
	public String cfNuovaImpresa;
	
	private String viewMsgDomandaConclusa = "";
	private String flagEstero;
	private SelItem[] listaStatiEsteri; 
	private String statoEstero;  

	// campi che vengono postati per la ricerca
	String normativa;
	String descBreveBando;
	String bando;
	String sportello;
	String statoDomanda;
	String numDomanda;
	String cercaDomande; // campo postato hidden , lo uso per capire se sto
						 // eseguendo una ricerca o se arrivo da altre parti

	// liste del form di ricerca
	SelItem[] listaNormative;
	SelItem[] listadescBreveBando;
	SelItem[] listaBandi;
	SelItem[] listaSportelli;
	SelItem[] listaStati;

	// lista delle AreeTematiche per le quali il beneficiario ha presentato almeno una domanda 
	SelItem[] listaAreeTematiche;
	Integer areaTematicaSRC;
	
	// liste del form di inserimento nuova domanda, (devono essere Array perche'
	// viene poi usato JSON)
	SelItem[] listaNormativeINS;
	SelItem[] listadescBreveBandoINS;
	SelItem[] listaBandiINS;
	SelItem[] listaSportelliINS;
	TipolBeneficiariDto[] listaTipologieBeneficiariINS;

	// lista delle AreeTematiche su cui ci sono Bandi Aperti 
	SelItem[] listaAreeTematicheINS;
	Integer areaTematicaINS;
	
	// campi che vengono postati per l'inserimetno di una nuova domanda
	String normativaINS;
	String descBreveBandoINS;
	String bandoINS;
	String sportelloINS;
	String tipologiaBeneficiarioINS;
	
	String idDomanda;

	// lista delle domande trovate
	ArrayList<Domanda> listaDomande;

	private AaepDAO aaepDAO;
	private IpaDAO ipaDAO;
	private Integer idSoggettoBeneficiario;
	private Integer idSoggettoCollegato;
	
	// Stringhe che conterranno i valori dei risultati (OK/KO) delle azioni
	// EliminaPropostaAction e InviaDomandaAction
	String risultatoOK;
	String risultatoKO;

	@Override
	public String executeAction() throws SystemException, UnrecoverableException, CSIException {

		final String methodName = "executeAction";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";

		log.info(logprefix + " BEGIN");
		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
		stopWatch.start();
		
		// recupero il valore del risultato delle azioni EliminaPropostaAction e InviaDomandaAction
		String risOK = (String) getServletRequest().getSession().getAttribute("risultatoOK");
		
		String risKO = (String) getServletRequest().getSession().getAttribute("risultatoKO");
		log.info(logprefix + " risOK=[" + risOK + "]");
		log.info(logprefix + " risKO=[" + risKO + "]");
		
		getServletRequest().getSession().removeAttribute("risultatoOK");
		getServletRequest().getSession().removeAttribute("risultatoKO");
		setRisultatoOK(risOK);
		setRisultatoKO(risKO);

		// rimuovo gli indici messi in sessione dall'aggregatore
		getServletRequest().getSession().setAttribute(Constants.INDEXTREE_NAME, null);
		log.info(logprefix + " IndexTree azzerato ");

		if (!StringUtils.equals(cercaDomande, "true")) 
		{
			// se non ho eseguito una ricerca recupero eventuali valori messi in
			// getServletRequest()

			if (StringUtils.isBlank(normativa)) {
				normativa = (String) getServletRequest().getSession().getAttribute("normativa");
			}
		
			if (StringUtils.isBlank(descBreveBando)) {
				descBreveBando = (String) getServletRequest().getSession().getAttribute("descBreveBando");
			}
			
			if (StringUtils.isBlank(bando)) {
				bando = (String) getServletRequest().getSession().getAttribute("bando");
			}
			
			if (StringUtils.isBlank(sportello)) {
				sportello = (String) getServletRequest().getSession().getAttribute("sportello");
			}
			
			if (StringUtils.isBlank(numDomanda)) {
				numDomanda = (String) getServletRequest().getSession().getAttribute("numDomanda");
			}
			
			if (StringUtils.isBlank(statoDomanda)) {
				statoDomanda = (String) getServletRequest().getSession().getAttribute("statoDomanda");
			}
		} else {
			log.info(logprefix + " parametro cercaDomande valorizzato, utilizzo i dati postati");
		}

		if (StringUtils.isNotBlank(numDomanda) && !StringUtils.isNumeric(numDomanda)) {
			log.warn(logprefix + " errore nel campo numDomanda");
			// numDomanda non vuoto e non numerico
			addActionError(Constants.ERR_MESSAGE_NUMDOMANDA_ERROR);
			addFieldError("id_numDomanda", Constants.ERR_MESSAGE_NUMDOMANDA_MSG);

			// popola le liste
			if (!loadListe()) {
				return ERROR;
			}

			return INPUT;
			
		} else {

			log.info(logprefix + " RUOLO=[" + getUserInfo().getRuolo() + "]");
	
			StatusInfo state = getStatus(); // TODO: Manca dato: denominazione ?!? : descrImpresaEnte
			
			listaDomande = ActionUtil.getElencoDomande(state.getIdSoggettoBeneficiario(), state.getIdSoggettoCollegato(),
					getUserInfo().getRuolo(), normativa, descBreveBando, bando, sportello, statoDomanda, numDomanda, areaTematicaSRC, 
					getServiziFindomWeb());
	
			// metto in sessione i dati ricavati (le liste le mette in sessione il
			// metodo loadListe)
			getServletRequest().getSession().setAttribute("listaDomande", listaDomande);
			getServletRequest().getSession().setAttribute("normativa", normativa);
			getServletRequest().getSession().setAttribute("descBreveBando", descBreveBando);
			getServletRequest().getSession().setAttribute("bando", bando);
			getServletRequest().getSession().setAttribute("sportello", sportello);
			getServletRequest().getSession().setAttribute("statoDomanda", statoDomanda);
			getServletRequest().getSession().setAttribute("numDomanda", numDomanda);
			getServletRequest().getSession().setAttribute("cercaDomande", cercaDomande);
	
			// svuoto lo status....precedentemente riempito con i dati relativi ad
			// una domanda
			state.setAperturaSportello("");
			state.setAperturaSportelloA("");
			state.setAperturaSportelloDa("");
			state.setCodiceAzione("");
			state.setDescrNormativa("");
			state.setDescrBando("");
			state.setDescrBreveBando("");
			state.setNumProposta(null);
			state.setNumSportello(null);
			state.setStatoProposta("");
			state.setTemplateId(null);
			state.setFlagBandoDematerializzato("");
			state.setTipoFirma("");
	
			setStatus(state);
		}
		
		// popola le liste
		if (!loadListe()) {
			return ERROR;
		}

		stopWatch.stop();
		stopWatch.dumpElapsed("CercaDomandeAction", "executeAction()", "test", "test");
		
		log.info(logprefix + "  END");
		return SUCCESS;
	}


	/**
	 * Si arriva qui quando l'utente seleziona una impresa dalla lista delle
	 * imprese e preme "conferma e prosegui"
	 * 
	 * @return
	 * @throws ServiziFindomWebException
	 */
	public String confermaImpresa() throws ServiziFindomWebException 
	{
		final String methodName = "confermaImpresa";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";

		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
		stopWatch.start();
		
		log.info(logprefix + " BEGIN");
		log.debug(logprefix + " idImpresaEnte=[" + idImpresaEnte + "]");
		log.debug(logprefix + " areaTematicaSRC=[" + areaTematicaSRC + "]");
		log.debug(logprefix + " areaTematica in sessione=[" + super.getAreaTematica() + "]");
		
		StatusInfo state = getStatus();

		// ricarico la lista listaImprese da visualizzare in HomePage
		listaImprese = (ArrayList<ImpresaEnte>) getServletRequest().getSession()
				.getAttribute(Constants.SESSION_LISTAIMPRESE);

		if (listaImprese != null) 
			log.debug(logprefix + " listaImprese letta da sessione :size=" + listaImprese.size());
		else 
			log.debug(logprefix + " listaImprese NULL");

		if (StringUtils.isBlank(idImpresaEnte)) {
			// nessuna Impresa/Ente selezionata, ritorno in Home Page
			addActionError(Constants.ERR_MESSAGE_SELENTE_ERROR);
			hideImpresa = "true";
			// ricarico la lista di stati esteri eventualmente da visualizzare in HomePage
			listaStatiEsteri = ActionUtil.popolaArrayStatiForCombo(getMappaStatiEsteri());//MB2019_04_18
			return "inputHome";
		}

		// inizializzo la descrizione dell'ente
		state.setDescrImpresaEnte("-");
		UserInfo us = getUserInfo();

		// individuo nell'elenco l'impresa selezionata e metto nello status
		// alcuni valori
		for (ImpresaEnte impresa : listaImprese) 
		{
			if (!StringUtils.isBlank(idImpresaEnte) && StringUtils.isNumeric(idImpresaEnte)	&& Integer.parseInt(idImpresaEnte) == impresa.getIdSoggetto()) 
			{
				log.debug(logprefix + " DescrImpresaEnte=[" + impresa.getDenominazione() + "]");
				state.setDescrImpresaEnte(impresa.getDenominazione());

				log.debug(logprefix + " CodFiscaleBeneficiario=[" + impresa.getCodiceFiscale() + "]");
				state.setCodFiscaleBeneficiario(impresa.getCodiceFiscale());

				state.setSiglaNazioneAzienda(impresa.getSiglaNazione());
				log.debug(logprefix + " impresa="+impresa);
				break;
			}
		}

		try {
			String te = "L'utente " + state.getOperatore() + " ha selezionato l'impresa id="
					+ state.getIdSoggettoBeneficiario() + " cf:" + state.getCodFiscaleBeneficiario();

			getServiziFindomWeb().insertLogAudit(Constants.CSI_LOG_IDAPPL, "",
					us.getCodFisc() + " - " + us.getCognome() + " " + us.getNome(), Constants.CSI_LOG_OPER_SEL_IMPRESA,
					te, "");
		} catch (ServiziFindomWebException e) {
			log.error(logprefix + " impossibile scrivere CSI_LOG_AUDIT: " + e);
		}

		state.setIdSoggettoBeneficiario(Integer.parseInt(idImpresaEnte));
		setStatus(state);

		// invoco AAEP e recupero le info dell'impresa
		Impresa azienda = null;
		Sede sedeLegale = null;
		
		try 
		{
			log.debug(logprefix + "recupero info azienda da AAEP con getDettaglioImpresa(INFOC,"+state.getCodFiscaleBeneficiario()+")");
			ImpresaINFOC aziendaINFOC = aaepDAO.getDettaglioImpresa2("INFOC", state.getCodFiscaleBeneficiario(), "", "", "");
			log.debug(logprefix + "recuperate info azienda da AAEP");

			// trasformo l'oggett orestituito in una interno per poterlo serializzare e mettere in sessione
			azienda = TrasformaClassiAAEP.impresaINFOC2ImpresaI(aziendaINFOC);
			getServletRequest().getSession().setAttribute(Constants.SESSION_ENTEIMPRESA, azienda);
			log.debug(logprefix + "messo in sessione EnteImpresa");

			// TODO : valorizzare ul RUOLO LR 
			// se AAEP non risponde cosa faccio?????
			String lr = verificaLegaleRappresentante(azienda, state.getOperatore());
			
			if (StringUtils.isNotBlank(lr) && lr.equals("S"))	{			
				us.setRuolo(Constants.RUOLO_LR);
			}
			
			if (azienda!=null) 
			{
				log.debug(logprefix + "Cerco la sede legale per idAzienda = ["+aziendaINFOC.getIdAzienda()+"]" );
				it.csi.aaep.aaeporch.business.Sede sede = aaepDAO.getDettaglioSedeLegale(aziendaINFOC.getIdAzienda(),determinaIdSedeLegale(aziendaINFOC.getSedi().iterator()) , "INFOC");
				sedeLegale = TrasformaClassiAAEP.sedeINFOC2Sede(sede);
				log.debug(logprefix + "Ho trovato la sede legale ["+sedeLegale.getDenominazione()+"]" );
				getServletRequest().getSession().setAttribute(Constants.SESSION_SEDE_LEGALE, sedeLegale);						
				log.debug(logprefix + "LA SEDE HA ATECO 2007 = ["+sedeLegale.getCodiceAteco2007()+"]" );
			}
			
		} catch (UnrecoverableException_Exception e) {
			log.error(logprefix +" UnrecoverableException_Exception: "+e);

		} catch (CSIException_Exception e) {
			log.error(logprefix +" CSIException_Exception: "+e);

		} catch (SystemException_Exception e) {
			log.error(logprefix +" SystemException_Exception: "+e);

		} catch (AAEPException_Exception e) {
			log.error(logprefix +" AAEPException_Exception: "+e);

		} catch (Exception e){
			// entro qui se il WS non risponde correttamente 
			// Exception: javax.xml.ws.soap.SOAPFaultException: Fault occurred while processing.
			log.error(logprefix +" Exception: "+e);

		}

		// recupero i dati da IPA
		long start=System.currentTimeMillis();
		Ipa datiIpa = estraiValoriIPA(state.getCodFiscaleBeneficiario());
		log.debug(logprefix + " datiIpa = "+datiIpa);
		log.debug(logprefix + " ottenuti dati IPA in ms: "+(System.currentTimeMillis()-start));
		
		getServletRequest().getSession().setAttribute(Constants.SESSION_DATIIPA, datiIpa);


		// ho passato il controllo, elimino la lista listaImprese dalla sessione
		// cosi' la prossima volta la ricarico ex-novo
		getServletRequest().getSession().removeAttribute(Constants.SESSION_LISTAIMPRESE);
		log.debug(logprefix + "post sessione remove");
		
		verificaSportelliAttivi(); 

		// popola le liste
		if (!loadListe()) {
			log.error(logprefix + "popola le liste ERROR ");
			return ERROR;
		}

		// Jira 2115 : faccio la ricerca con filtro vuoto
		log.info(logprefix + " faccio la ricerca con filtro vuoto");
		
		listaDomande = ActionUtil.getElencoDomande(state.getIdSoggettoBeneficiario(), state.getIdSoggettoCollegato(),
				getUserInfo().getRuolo(), normativa, descBreveBando, bando, sportello, statoDomanda, numDomanda, areaTematicaSRC, 
				getServiziFindomWeb());

		// metto in sessione i dati ricavati
		getServletRequest().getSession().setAttribute("listaDomande", listaDomande);
		
		stopWatch.stop();
		stopWatch.dumpElapsed("CercaDomandeAction", "confermaImpresa()", "test", "test");
		
		log.info(logprefix + "  END");
		return SUCCESS;
	}

	private String determinaIdSedeLegale(Iterator<it.csi.aaep.aaeporch.business.Sede> sediIter) 
	{
		while (sediIter.hasNext()) 
		{
			it.csi.aaep.aaeporch.business.Sede sede = sediIter.next();
			
			if (sede.getDescrTipoSede().equalsIgnoreCase("SEDE LEGALE")) {
				return sede.getIdSede(); 
			}
		}
		throw new UnsupportedOperationException("Attenzione! l'azienda individuata non presenta la sede legale su AAEP ");
	}

	private String verificaLegaleRappresentante(Impresa azienda, String codFisc) {
		final String methodName = "verificaLegaleRappresentante";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";

		log.info(logprefix + " BEGIN");
		log.info(logprefix + " codFisc=" + codFisc);
		String lr = "";

		if (azienda != null) {
			log.info(logprefix + "azienda trovata su AAEP, IdAzienda=" + azienda.getIdAzienda());
			// TODO
			// verifica su AAEP se utente loggato e' Legale Rappresentante
			List<Persona> listaPersone = azienda.getListaPersone();
			if (listaPersone != null) 
			{
				log.info(logprefix + "trovata listaPersone=" + listaPersone.size());

				for (Iterator itr = listaPersone.iterator(); itr.hasNext();) 
				{
					Persona persona = (Persona) itr.next();
					log.info(logprefix + "persona.getCodiceFiscale()=" + persona.getCodiceFiscale());

					if (persona != null && persona.getCodiceFiscale() != null && persona.getCodiceFiscale().equals(codFisc)) 
					{
						log.info(logprefix + "trovata persona loggata in elenco listaPersone");
						List<Carica> listaCariche = persona.getListaCariche();
					
						if (listaCariche != null) 
						{
							log.info(logprefix + "trovata listaCariche=" + listaCariche.size());
						
							for (Iterator itr2 = listaCariche.iterator(); itr2.hasNext();) 
							{
								Carica carica = (Carica) itr2.next();
							
								if (carica != null && "S".equals(carica.getFlagRappresentanteLegale())) 
								{
									log.info(logprefix + "FOUNDED carica.getFlagRappresentanteLegale()=" + carica.getFlagRappresentanteLegale());

									// TODO : utente loggato e' LR
									lr = "S";

									break;
								}
							}
						} else {
							log.info(logprefix + "trovata listaCariche NULLA");
						}
						break;
					}
				}
			} else {
				log.info(logprefix + "trovata listaPersone NULLA");
			}
		} else {
			log.info(logprefix + "azienda NON trovata su AAEP");
		}
		log.info(logprefix + " END");
		return lr;
	}

	/**
	 * Si arriva qui quando l'utente compila il form per specificare una nuova
	 * impresa e clicca "conferma e prosegui"
	 * 
	 * @return
	 * @throws ServiziFindomWebException
	 */
	public String salvaNuovaImpresa() throws ServiziFindomWebException, ConnectException {
		final String methodName = "salvaNuovaImpresa";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";

		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
		stopWatch.start();
		
		log.info(logprefix + " BEGIN");
		log.debug(logprefix + " cfNuovaImpresa=[" + cfNuovaImpresa + "]");

		String codFisc = cfNuovaImpresa;
		if (cfNuovaImpresa != null) {
			codFisc = cfNuovaImpresa.toUpperCase();
			log.info(logprefix + " codFisc: " + codFisc);
		}

		UserInfo us = getUserInfo();
		log.debug(logprefix + "us CodFisc: " + us.getCodFisc());
		log.debug(logprefix + "us Cognome: " + us.getCognome());
		log.debug(logprefix + "us Nome: " + us.getNome());
		log.debug(logprefix + "us IdIride: " + us.getIdIride());
		log.debug(logprefix + "us Ruolo: " + us.getRuolo());

		boolean trovatoErrore = false; 

		// verifico il codice fiscale inserito
		if(StringUtils.isNotBlank(flagEstero)){
			if(StringUtils.isBlank(statoEstero) || statoEstero.equals("-1")){
				addFieldError("statoEsteroObbligatorio", Constants.ERR_MESSAGE_CF_ESTERO_MANCA_STATO);
				log.debug(logprefix + " e' stato dichiarato che il cod fiscale e' estero, ma non e' stato valorizzato lo stato estero ");
				trovatoErrore = true;
			}
		}
		String verificaCF = ActionUtil.checkCF(codFisc, flagEstero);//se si fara' il controllo formale dei cf esteri potrebbe essere necessario passare anche lo stato estero
		
		if (verificaCF != null) {
			addFieldError(Constants.ERR_STRING_CFNEWENTE_ERROR, verificaCF);
			log.debug(logprefix + " errore nel campo cfNuovaImpresa");
			trovatoErrore = true;			
		}

		if (trovatoErrore){
			addActionError(Constants.ERR_MESSAGE_PARAM_ERROR);
			// ricarico la lista listaImprese da visualizzare in HomePage
			listaImprese = (ArrayList<ImpresaEnte>) getServletRequest().getSession()
					.getAttribute(Constants.SESSION_LISTAIMPRESE);
			// ricarico la lista di stati esteri eventualmente da visualizzare in HomePage
			listaStatiEsteri = ActionUtil.popolaArrayStatiForCombo(getMappaStatiEsteri());//MB2019_04_18				
			
			hideImpresa = "false";
			return "inputHome";
		}

		// verifico che il CF della nuova Impresa non sia presente in shell_t_soggetti
		ShellSoggettiDto soggetto = getServiziFindomWeb().getDatiSoggettoByCodiceFiscale(codFisc);
		log.debug(logprefix + "soggetto="+soggetto);
		
		StatusInfo stato = getStatus();
		String siglaNazioneCorrente = estraiSiglaNazione();//MB2019_04_18 ini 
		if (soggetto != null) {
			// trovata una Impresa/Ente gia' registrata
			log.debug(logprefix + " trovata una Impresa/Ente");

			// metto nell'oggetto STATUS il CF che l'utente ha inserito per la
			// nuova impresa/ente

			stato.setDescrImpresaEnte(soggetto.getDenominazione());
			stato.setIdSoggettoBeneficiario(soggetto.getIdSoggetto());
			stato.setCodFiscaleBeneficiario(codFisc);
			//MB2019_04_18 ini non aggiungo a StatusInfo l'attributo sigla_nazione fino a quando non se ne presenti la necessita'
			
			//Se la sigla nazione presente su shell_t_soggetti e' diversa da quella corrente a video, faccio update.
			//Succede se l'utente una volta dichiara che il cf estero è di una nazione e in un successivo accesso specifica un altro stato.
			//Oppure in casi piu' improbabili, come quello di usare un cf italiano gia' su shell_t_soggetti e dichiarare che è estero, o viceversa, usare 
			//un cf gia' dichiarato estero e ad un successivo accesso non valorizzare lo stato, e quel cf pur essendo estero supera la validazione dei cf italiani
			aggiornaNazione(soggetto, siglaNazioneCorrente);		
			
			stato.setSiglaNazioneAzienda(siglaNazioneCorrente);
		} else {

			// NON trovata una Impresa/Ente, la inserisco sul DB
			log.debug(logprefix + " Impresa/Ente ancora non censita sul DB");

			// se non e' presente salvo i dati in shell_t_soggetti
			ShellSoggettiDto newSoggetto = new ShellSoggettiDto();
			newSoggetto.setCodiceFiscale(codFisc);
			newSoggetto.setDenominazione(null);
			newSoggetto.setIdFormaGiuridica(0);
			newSoggetto.setSiglaNazione(siglaNazioneCorrente); //MB2019_04_18

			getServiziFindomWeb().insertShellTSoggetto(newSoggetto);
			log.debug(logprefix + " inserita nuovo soggetto sul db");
			getServiziFindomWeb().insertLogAudit(Constants.CSI_LOG_IDAPPL, "",
					us.getCodFisc() + " - " + us.getCognome() + " " + us.getNome(), Constants.CSI_LOG_OPER_INS_IMPRESA,
					"inserito nuova impresa CF=" + codFisc, "");

			// recupero l'idSoggetto appena creato
			ShellSoggettiDto soggInserito = getServiziFindomWeb().getDatiSoggettoByCodiceFiscale(codFisc);
			log.debug(logprefix + " soggInserito=" + soggInserito);

			// metto nell'oggetto STATUS il CF che l'utente ha inserito per la
			// nuova impresa/ente
			stato.setDescrImpresaEnte(null);
			stato.setIdSoggettoBeneficiario(soggInserito.getIdSoggetto());
			stato.setCodFiscaleBeneficiario(codFisc);
			stato.setSiglaNazioneAzienda(siglaNazioneCorrente);
		}
		setStatus(stato);

		// invoco AAEP e recupero le info dell'impresa
		Impresa impresa = null;
		Sede sedeLegale = null;
		if(StringUtils.isBlank(flagEstero)){ //MB2019_04_18 AAEP e IPA solo se beneficiario con codice fiscale italiano
			try {
				log.debug(logprefix + "recupero info azienda da AAEP con getDettaglioImpresa(INFOC,"+stato.getCodFiscaleBeneficiario()+")");

				ImpresaINFOC impresaAAEP = aaepDAO.getDettaglioImpresa2("INFOC", stato.getCodFiscaleBeneficiario(), "", "", "");
				log.debug(logprefix + "recuperate info azienda da AAEP="+impresaAAEP);

				// trasformo l'oggetto restituito in uno interno per poterlo serializzare e mettere in sessione
				impresa = TrasformaClassiAAEP.impresaINFOC2ImpresaI(impresaAAEP);

				//TODO
				// verifica su AAEP se utente loggato e' Legale Rappresentante
				String lr = verificaLegaleRappresentante(impresa, stato.getOperatore());			
				if (StringUtils.isNotBlank(lr) && lr.equals("S")){		
					us.setRuolo(Constants.RUOLO_LR);
				}

				if (impresaAAEP!=null) {
					log.debug(logprefix + "Cerco la sede legale per idAzienda = ["+impresaAAEP.getIdAzienda()+"]" );
					it.csi.aaep.aaeporch.business.Sede sede = aaepDAO.getDettaglioSedeLegale(impresaAAEP.getIdAzienda(),determinaIdSedeLegale(impresaAAEP.getSedi().iterator()) , "INFOC");
					sedeLegale = TrasformaClassiAAEP.sedeINFOC2Sede(sede);
					log.debug(logprefix + "Ho trovato la sede legale ["+sedeLegale.getDenominazione()+"]" );
					log.debug(logprefix + "LA SEDE HA ATECO 2007 = ["+sedeLegale.getCodiceAteco2007()+"]" );
				}

			} 
			catch (UnrecoverableException_Exception e) {
				log.error(logprefix +" UnrecoverableException_Exception: "+e);

			} catch (CSIException_Exception e) {
				// potri entrare qui se l'azienda non e' su AAEP
				// CSIException_Exception: it.csi.aaep.aaeporch.business.CSIException_Exception: findByCodiceFiscaleINFOCAMERE2: Nessun record trovato
				log.error(logprefix +" CSIException_Exception: "+e);

			} catch (SystemException_Exception e) {
				log.error(logprefix +" SystemException_Exception: "+e);

			} catch (AAEPException_Exception e) {
				log.error(logprefix +" AAEPException_Exception: "+e);

			} catch (Exception e){
				// entro qui se il WS non risponde correttamente 
				// Exception: javax.xml.ws.soap.SOAPFaultException: Fault occurred while processing.
				log.error(logprefix +" Exception: "+e);

			}finally{

				log.debug(logprefix + "messo in sessione EnteImpresa");
				getServletRequest().getSession().setAttribute(Constants.SESSION_ENTEIMPRESA, impresa);

				getServletRequest().getSession().setAttribute(Constants.SESSION_SEDE_LEGALE, sedeLegale);
				log.debug(logprefix + "messo in sessione sedeLegale" );
			}

			// recupero i dati da IPA
			long start=System.currentTimeMillis();
			//Ipa datiIpa = estraiValoriIPA(codFisc);
			Ipa datiIpa = ipaDAO.searchEnteOnIPA(codFisc);	
			log.debug(logprefix + " ottenuti dati IPA in ms: "+(System.currentTimeMillis()-start));
			if(datiIpa!=null){
				getServletRequest().getSession().setAttribute(Constants.SESSION_DATIIPA, datiIpa);
			}
		}else{
			//Se il beneficiario e' estero, elimino i dati IPA e i dati di AAEP dalla sessione (che possono essere rimasti da un precedente accesso con diverso beneficiario)
			getServletRequest().getSession().removeAttribute(Constants.SESSION_DATIIPA);			
		}

		// ho passato il controllo, elimino la lista listaImprese dalla sessione
		// cosi' la prossima volta la ricarico ex-novo
		getServletRequest().getSession().removeAttribute(Constants.SESSION_LISTAIMPRESE);

		verificaSportelliAttivi(); 

		// popola le liste
		if (!loadListe()) {
			log.error(logprefix + "popola le liste ERROR ");
			return ERROR;
		}

		// Jira 2115 : faccio la ricerca con filtro vuoto
		log.info(logprefix + " faccio la ricerca con filtro vuoto");
		
		listaDomande = ActionUtil.getElencoDomande(stato.getIdSoggettoBeneficiario(), stato.getIdSoggettoCollegato(),
				getUserInfo().getRuolo(), normativa, descBreveBando, bando, sportello, statoDomanda, numDomanda, areaTematicaSRC, 
				getServiziFindomWeb());

		// metto in sessione i dati ricavati
		getServletRequest().getSession().setAttribute("listaDomande", listaDomande);

		stopWatch.stop();
		stopWatch.dumpElapsed("CercaDomandeAction", "salvaNuovaImpresa()", "test", "test");
		
		log.info(logprefix + "  END");
		return SUCCESS;
	}
	
	public String proponiStatoEstero() throws ServiziFindomWebException, ConnectException {
		final String methodName = "proponiStatoEstero";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";

		log.info(logprefix + " BEGIN");
		if(listaStatiEsteri== null ||listaStatiEsteri.length==0){
			listaStatiEsteri = ActionUtil.popolaArrayStatiForCombo(getMappaStatiEsteri());
		}

		if(StringUtils.isNotBlank(flagEstero) && flagEstero.equals("true") && StringUtils.isNotBlank(cfNuovaImpresa) ){		
			if(StringUtils.isBlank(statoEstero) || statoEstero.equals("-1")){
				ShellSoggettiDto shellSoggettoDto = getServiziFindomWeb().getDatiSoggettoByCodiceFiscale(cfNuovaImpresa);
				if(shellSoggettoDto!=null && StringUtils.isNotBlank(shellSoggettoDto.getSiglaNazione())){
					
					if(listaStatiEsteri != null && listaStatiEsteri.length>0){
						for (int i = 0; i < listaStatiEsteri.length; i++) {
							SelItem curStatoEstero = listaStatiEsteri[i];
							String curCodiceStatoEstero = curStatoEstero.getKey();
							if(StringUtils.isNotBlank(curCodiceStatoEstero) && 
									curCodiceStatoEstero.equals(shellSoggettoDto.getSiglaNazione())){
								statoEstero = curCodiceStatoEstero;
								break;								
							}
						}
					}
				}
			}
		}
		// ricarico la lista listaImprese da visualizzare in HomePage
		listaImprese = (ArrayList<ImpresaEnte>) getServletRequest().getSession()
				.getAttribute(Constants.SESSION_LISTAIMPRESE);
		// ricarico la lista di stati esteri da visualizzare in HomePage
		if(listaStatiEsteri== null ||listaStatiEsteri.length==0){
			listaStatiEsteri = ActionUtil.popolaArrayStatiForCombo(getMappaStatiEsteri());
		}
		hideImpresa="false";
		log.info(logprefix + "  END");
		return "inputHome";
	}
	
	/**
	 * Estraggo dall'LDAP IPA le informazioni legate al codice fiscale passato.
	 * Prima di invocare IPA verifico se il cf e' legato ad una forma giuridica pubblica o privata
	 * 
	 * @param codFisc della ditta/ente/impresa/persona da cercare sul LDAP IPA
	 * @return
	 */
	private Ipa estraiValoriIPA(String codFisc) {
						
		final String methodName = "estraiValoriIPA";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		log.info(logprefix + " BEGIN");
		
		Ipa datiIpa = null;
		try{
			ShellSoggettiDto soggetto = getServiziFindomWeb().getDatiSoggettoByCodiceFiscale(codFisc);
			log.debug(logprefix + " soggetto.getIdFormaGiuridica="+soggetto.getIdFormaGiuridica());
			log.debug(logprefix + " soggetto.getSiglaNazione()= "+soggetto.getSiglaNazione());
			
			// se l'azienda e' Estera non ha senso cercare l'IPA
			if(soggetto.getIdFormaGiuridica() > 0 && (StringUtils.isBlank(soggetto.getSiglaNazione()) 
					|| StringUtils.equals(soggetto.getSiglaNazione(),"000"))){
				// estraggo le forme giuridiche
				ArrayList<FormeGiuridicheDto> listaFormeGiuridiche = getServiziFindomWeb().getFormeGiuridiche();
	
				log.debug(logprefix + " caricata listaFormeGiuridiche");
				
				// trasformo la lista in mappa
				for (Iterator itr2 = listaFormeGiuridiche.iterator(); itr2.hasNext();) {
					FormeGiuridicheDto formeGiuridicheDto = (FormeGiuridicheDto) itr2.next();
					
					if(formeGiuridicheDto.getIdFormaGiuridica() == soggetto.getIdFormaGiuridica() 
							&& formeGiuridicheDto.getFlagPubblicoPrivato()==2 ){ // 2 ==  pubblico
						
						log.debug(logprefix + "trovata FlagPubblicoPrivato= "+formeGiuridicheDto.getFlagPubblicoPrivato());
						datiIpa = ipaDAO.searchEnteOnIPA(codFisc);
						break;
					}
				}
			}else {
				log.debug(logprefix + "azienda Estera, non cerco l'IPA");
			}
		}catch(Exception e){
			// intercetto e non faccio nulla
			log.error(logprefix + "Exception e="+e.getMessage()); 
		}
		log.info(logprefix + " END");
		return datiIpa;
	}

	/**
	 * Azione scatenata dal bottone "Pulisci i campi" del form di ricerca delle
	 * domande Resetta i parametri di ricerca proposta impostati nel form
	 * dall'utente
	 * 
	 * @return
	 */
	public String annullaFiltri() {
		final String methodName = "annullaFiltri";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		log.info(logprefix + " BEGIN");

		resetParametriRicerca("ricerca");
		verificaSportelliAttivi(); // PK messo in BaseAction

		// popola le liste
		if (!loadListe()) {
			return ERROR;
		}

		log.info(logprefix + " END");
		return NONE;
	}

	/**
	 * Azione scatenata dal bottone "Rirpistina valori iniziali" del form di
	 * inserimento di una nuova domanda
	 * 
	 * @return
	 */
	public String annullaFiltriNuovaDomanda() {
		final String methodName = "annullaFiltriNuovaDomanda";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		log.info(logprefix + " BEGIN");

		resetParametriRicerca("inserimento");
		verificaSportelliAttivi(); // PK messo in BaseAction

		// popola le liste
		if (!loadListe()) {
			return ERROR;
		}

		log.info(logprefix + " END");
		return NONE;
	}
	// FINE METODI

	/**
	 * Resetta i parametri di ricerca proposta impostati nel form dall'utente
	 * 
	 * @param formToReset
	 *            : "inserimento" o "ricerca", indica quale dei 2 form resettare
	 */
	private void resetParametriRicerca(String formToReset) {
		final String methodName = "resetParametriRicerca";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		log.info(logprefix + " BEGIN");
		log.info(logprefix + " formToReset=" + formToReset);

		if ("ricerca".equals(formToReset)) {
			normativa = null;//
			descBreveBando = null;//
			bando = null;//
			sportello = null;//
			statoDomanda = null;//
			numDomanda = null;//
			areaTematicaSRC = null;
			if(super.getAreaTematica()!=null) { // ripristino il canale d'arrivo
				areaTematicaSRC = super.getAreaTematica();
			}

			// pulisco la getServletRequest()
			getServletRequest().getSession().removeAttribute("listaNormative");
			getServletRequest().getSession().removeAttribute("listadescBreveBando");
			getServletRequest().getSession().removeAttribute("listaBandi");
			getServletRequest().getSession().removeAttribute("listaSportelli");
			getServletRequest().getSession().removeAttribute("listaStati");
			getServletRequest().getSession().removeAttribute("listaAreeTematiche");
			log.info(logprefix + " rimosse liste combo form di ricerca dalla sessione");

			getServletRequest().getSession().removeAttribute("normativa");
			getServletRequest().getSession().removeAttribute("descBreveBando");
			getServletRequest().getSession().removeAttribute("bando");
			getServletRequest().getSession().removeAttribute("sportello");
			getServletRequest().getSession().removeAttribute("statoDomanda");
			getServletRequest().getSession().removeAttribute("numDomanda");
			getServletRequest().getSession().removeAttribute("areaTematica");

			log.info(logprefix + " rimossi parametri di ricerca dalla sessione");

			setListaDomande(null);
		}

		if ("inserimento".equals(formToReset)) {
			normativaINS = null;
			descBreveBandoINS = null;
			bandoINS = null;
			sportelloINS = null;
			tipologiaBeneficiarioINS = null;
			areaTematicaINS = null;
			if(super.getAreaTematica()!=null) { // ripristino il canale d'arrivo
				areaTematicaINS = super.getAreaTematica();
			}
			
			// pulisco la request
			getServletRequest().getSession().removeAttribute("listaNormativeINS");
			getServletRequest().getSession().removeAttribute("listadescBreveBandoINS");
			getServletRequest().getSession().removeAttribute("listaBandiINS");
			getServletRequest().getSession().removeAttribute("listaSportelliINS");
			getServletRequest().getSession().removeAttribute("listaTipologieBeneficiariINS");
			getServletRequest().getSession().removeAttribute("listaAreeTematicheINS");
			log.info(logprefix + " rimosse liste combo form di inserimento dalla sessione");

		}

		log.info(logprefix + " END");
	}

	/**
	 * Popolo le combo box del form di ricerca e del form di creazione
	 * 
	 * @return
	 */
	private boolean loadListe() {
		final String methodName = "loadListe";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		log.info(logprefix + " BEGIN");

		boolean result = true;

		log.debug(logprefix + " normativa=["+normativa+"]");
		log.debug(logprefix + " descBreveBando=["+descBreveBando+"]");
		log.debug(logprefix + " bando=["+bando+"]");
		log.debug(logprefix + " sportello=["+sportello+"]");
		log.debug(logprefix + " statoDomanda=["+statoDomanda+"]");
		log.debug(logprefix + " numDomanda=["+numDomanda+"]");
		log.debug(logprefix + " areaTematicaSRC=["+areaTematicaSRC+"]");
		log.debug(logprefix + " areaTematica=["+super.getAreaTematica()+"]");
		
		// valorizzo l'id dell'AreaTematica da utilizzare.
		// Uso quella in sessione (che proviene dal link del canale) solo se non e' valorizzata quella postata dal form
		
		//gestioneAreeTematiche();
		
		Integer idAreaTem = null;
		Integer idAreaTemINS = null;
		
		if(areaTematicaSRC!=null && areaTematicaSRC>0) {
			idAreaTem = areaTematicaSRC;
		}else if(super.getAreaTematica()!=null && super.getAreaTematica()>0) {
			idAreaTem = super.getAreaTematica();
		}
			
		if(areaTematicaINS!=null && areaTematicaINS>0) {
			idAreaTemINS = areaTematicaINS;
		}else if(super.getAreaTematica()!=null && super.getAreaTematica()>0) {
			idAreaTemINS = super.getAreaTematica();
		}
		
		log.debug(logprefix + " idAreaTem=["+idAreaTem+"]");
		log.debug(logprefix + " idAreaTemINS=["+idAreaTemINS+"]");
		
		//seleziono un valore nella combo dell'area tematica se arrivo da un canale 
		if(areaTematicaSRC==null && idAreaTem!=null)
			areaTematicaSRC = idAreaTem;
		
		//seleziono un valore nella combo dell'area tematica form inserimento se arrivo da un canale
		if(areaTematicaINS==null && idAreaTemINS!=null)
			areaTematicaINS = idAreaTemINS;
		
		//gestioneAreeTematiche();
		
		
		/*
	 
    // Restiruisce null o un Integer >= 0 (escludo il -1)
	private Integer val(Integer val) {
		Integer v = null;
		if(val!=null && val>=0)
			v = val;
		return v;
	}
		 */
		
		Integer norm = null;
		if(StringUtils.isNotBlank(normativa) && !StringUtils.equals("-1", normativa)) {
			norm = Integer.parseInt(normativa);
		}
		
		Integer ban = null;
		if(StringUtils.isNotBlank(bando) && !StringUtils.equals("-1", bando)) {
			ban = Integer.parseInt(bando);
		}
		
		Integer idd = null;
		if(StringUtils.isNotBlank(numDomanda)) {
			idd = Integer.parseInt(numDomanda);
		}
		
		StatusInfo state = getStatus();
		// lista delle domande presentate
		ArrayList<VistaDomandeDto> listaVistaDomandaDto = null;
		ArrayList<VistaDomandeDto> listaVistaDomandaDtoPerAreeTematiche = null;
		try {
		
			// utilizzo il parametro che arriva con l'url di accesso solo quando non sono valorizzati
			// i campi del form (ossia le prima volta che atterro nella pagina)
			
			if ((Constants.RUOLO_AMM).equals(getUserInfo().getRuolo()) || (Constants.RUOLO_LR).equals(getUserInfo().getRuolo())) {
				log.debug(logprefix + "getUserInfo().getRuolo(): " + getUserInfo().getRuolo());
				
				// se utente ha ruolo AMMINISTRATORE
				listaVistaDomandaDtoPerAreeTematiche = getServiziFindomWeb().getVistaDomanda(null, state.getIdSoggettoBeneficiario(), null, null, null, null);
				listaVistaDomandaDto = getServiziFindomWeb().getVistaDomanda(null, state.getIdSoggettoBeneficiario(), norm, ban, idd, idAreaTem);
				setIdSoggettoBeneficiario(state.getIdSoggettoBeneficiario());

			} else {

				listaVistaDomandaDtoPerAreeTematiche = getServiziFindomWeb().getVistaDomanda(state.getIdSoggettoCollegato(), state.getIdSoggettoBeneficiario(), null, 
						null, null, null); 
				listaVistaDomandaDto = getServiziFindomWeb().getVistaDomanda(state.getIdSoggettoCollegato(), state.getIdSoggettoBeneficiario(), norm, 
						ban, idd, idAreaTem);

				setIdSoggettoBeneficiario(state.getIdSoggettoBeneficiario());
				setIdSoggettoCollegato(state.getIdSoggettoCollegato());
			}

			Map<Integer, String> mappaAreeTematiche = new HashMap<Integer, String>(); // mappa Aree Tematiche
			
			if (listaVistaDomandaDtoPerAreeTematiche != null) {
				log.debug(logprefix + " listaVistaDomandaDtoPerAreeTematiche.size()=" + listaVistaDomandaDtoPerAreeTematiche.size());
				// popolo l'array delle areeTematiche (conterra' sempre tutte le aree per le quali c'e' almeno una domanda)
				for (VistaDomandeDto vv : listaVistaDomandaDtoPerAreeTematiche) 
				{
					mappaAreeTematiche.put(vv.getIdAreaTematica(), vv.getDescrizioneAreaTematica());
				}
				getServletRequest().getSession().setAttribute("mappaAreeTematiche", mappaAreeTematiche);
			}
			log.debug(logprefix + "mappaAreeTematiche=" + mappaAreeTematiche.toString());
			
			// verifico se beneficiario ha domande 'CO' da inviare
			verificaNumeroDomandeConcluse();

			Map<Integer, String> mappaNormative = new HashMap<Integer, String>(); // mappa Normative
			Map<Integer, String> mappaDescrBreve = new HashMap<Integer, String>(); // mappa Descrizione Breve BAndo
			Map<Integer, String> mappaBando = new HashMap<Integer, String>(); // mappa Bando
			Map<Integer, String> mappaSportelli = new HashMap<Integer, String>(); // mappa Sportelli
			Map<String, String> mappaStati= new HashMap<String, String>(); // mappa Stati
			
			
			if (listaVistaDomandaDto != null) {
				log.debug(logprefix + " listaVistaDomandaDto.size()=" + listaVistaDomandaDto.size());
				
				if(areaTematicaSRC!= null && areaTematicaSRC>0 ) {
					log.debug(logprefix + "areaTematica definita="+areaTematicaSRC+", prendo SOLO i valori di quell'area");
				}else {
					// areaTematica non definita, prendo TUTTI i valori
					log.debug(logprefix + "areaTematica non definita, prendo TUTTI i valori");
				}
				
				// Devo estrarre dati univoci dall'elenco delle domande.
				// Scorro la lista e popolo tante mappe quanti sono le combo
				// (questo per escludere doppioni)
				for (VistaDomandeDto vistaDomandeDto : listaVistaDomandaDto) 
				{
					if(idAreaTem!= null && idAreaTem>0 ) {
						
						// areaTematica definita, per ogni combo, prendo SOLO i valori relativi a quell'area
						if(vistaDomandeDto.getIdAreaTematica().equals(idAreaTem)) {
							
							mappaNormative.put(vistaDomandeDto.getIdNormativa(), vistaDomandeDto.getNormativa());
							mappaDescrBreve.put(vistaDomandeDto.getIdBando(), vistaDomandeDto.getDescrBreveBando());
							mappaBando.put(vistaDomandeDto.getIdBando(), vistaDomandeDto.getDescrBando());
							mappaStati.put(vistaDomandeDto.getCodStatoDomanda(), vistaDomandeDto.getStatoDomanda());
							if(StringUtils.isNotBlank(vistaDomandeDto.getDtChiusuraSportello())){
								mappaSportelli.put(vistaDomandeDto.getIdSportelloBando(), vistaDomandeDto.getDtAperturaSportello() + " - " + vistaDomandeDto.getDtChiusuraSportello());
							}else{
								mappaSportelli.put(vistaDomandeDto.getIdSportelloBando(), vistaDomandeDto.getDtAperturaSportello() + " - data fine non definita");
							}
						}
						
					}else {
						// areaTematica non definita,  per ogni combo, prendo TUTTI i valori 
						
						mappaNormative.put(vistaDomandeDto.getIdNormativa(), vistaDomandeDto.getNormativa());
						mappaDescrBreve.put(vistaDomandeDto.getIdBando(), vistaDomandeDto.getDescrBreveBando());
						mappaBando.put(vistaDomandeDto.getIdBando(), vistaDomandeDto.getDescrBando());
						mappaStati.put(vistaDomandeDto.getCodStatoDomanda(), vistaDomandeDto.getStatoDomanda());
						if(StringUtils.isNotBlank(vistaDomandeDto.getDtChiusuraSportello())){
							mappaSportelli.put(vistaDomandeDto.getIdSportelloBando(), vistaDomandeDto.getDtAperturaSportello() + " - " + vistaDomandeDto.getDtChiusuraSportello());
						}else{
							mappaSportelli.put(vistaDomandeDto.getIdSportelloBando(), vistaDomandeDto.getDtAperturaSportello() + " - data fine non definita");
						}
					}
				}

//				log.debug(logprefix + "mappaNormative=" + mappaNormative.toString());
//				log.debug(logprefix + "mappaDescrBreve=" + mappaDescrBreve.toString());
//				log.debug(logprefix + "mappaBando=" + mappaBando.toString());
//				log.debug(logprefix + "mappaStati=" + mappaStati.toString());
//				log.debug(logprefix + "mappaSportelli=" + mappaSportelli.toString());
				
				// solo se ho almeno una domanda per l'area Tematica di ingresso , 
				// pre-popolo le combo con un solo elemento
				if(idAreaTem!=null && mappaAreeTematiche.containsKey(idAreaTem)) {
					
					log.debug(logprefix + "AreaTematica presente in mappaAreeTematiche");
					if( mappaAreeTematiche.size()==1 ) {
						log.debug(logprefix + "inizializzo combo AreeTematiche");
						Map.Entry<Integer,String> entry = mappaAreeTematiche.entrySet().iterator().next();
						areaTematicaSRC =  entry.getKey();
						getServletRequest().getSession().setAttribute("areaTematicaSRC", areaTematicaSRC);
					}
					if( mappaNormative.size()==1 ) {
						log.debug(logprefix + "inizializzo combo Normative");
						Map.Entry<Integer,String> entry = mappaNormative.entrySet().iterator().next();
						normativa = "" + entry.getKey();
						getServletRequest().getSession().setAttribute("normativa", normativa);
					}
					if(mappaDescrBreve.size()==1) {
						log.debug(logprefix + "inizializzo combo Descrizioni Brrevi");
						Map.Entry<Integer,String> entry2 = mappaDescrBreve.entrySet().iterator().next();
						descBreveBando = "" + entry2.getKey();
						getServletRequest().getSession().setAttribute("descBreveBando", descBreveBando);
					}
					if(mappaBando.size()==1) {
						log.debug(logprefix + "inizializzo combo Bandi");
						Map.Entry<Integer,String> entry3 = mappaBando.entrySet().iterator().next();
						bando = "" + entry3.getKey();
						getServletRequest().getSession().setAttribute("bando", bando);
					}
					if(mappaSportelli.size()==1) {
						log.debug(logprefix + "inizializzo combo Sportelli");
						Map.Entry<Integer,String> entry4 = mappaSportelli.entrySet().iterator().next();
						sportello = "" + entry4.getKey();
						getServletRequest().getSession().setAttribute("sportello", sportello);
					}
					if(mappaStati.size()==1) {
						log.debug(logprefix + "inizializzo combo Stati");
						Map.Entry<String,String> entry5 = mappaStati.entrySet().iterator().next();
						statoDomanda = entry5.getKey();
						getServletRequest().getSession().setAttribute("statoDomanda", statoDomanda);
					}
				}

			} else {
				log.info(logprefix + " listaVistaDomandaDto NULL");
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

//		log.debug(logprefix + "listaNormativeINS presa da request="
//				+ getServletRequest().getSession().getAttribute("listaNormativeINS"));
//		log.debug(logprefix + "listaBandiINS presa da request="
//				+ getServletRequest().getSession().getAttribute("listaBandiINS"));
//		log.debug(logprefix + "listadescBreveBandoINS presa da request="
//				+ getServletRequest().getSession().getAttribute("listadescBreveBandoINS"));
//		log.debug(logprefix + "listaSportelliINS presa da request="
//				+ getServletRequest().getSession().getAttribute("listaSportelliINS"));
//		log.debug(logprefix + "listaTipologieBeneficiariINS presa da request="
//				+ getServletRequest().getSession().getAttribute("listaTipologieBeneficiariINS"));

		// popolo le combo del form di inserimento
		listaNormativeINS = (SelItem[]) getServletRequest().getSession().getAttribute("listaNormativeINS");
		listadescBreveBandoINS = (SelItem[]) getServletRequest().getSession().getAttribute("listadescBreveBandoINS");
		listaBandiINS = (SelItem[]) getServletRequest().getSession().getAttribute("listaBandiINS");
		listaSportelliINS = (SelItem[]) getServletRequest().getSession().getAttribute("listaSportelliINS");
		listaTipologieBeneficiariINS = (TipolBeneficiariDto[]) getServletRequest().getSession().getAttribute("listaTipologieBeneficiariINS");
		listaAreeTematicheINS = (SelItem[]) getServletRequest().getSession().getAttribute("listaAreeTematicheINS");
		
		if (listaNormativeINS != null && listadescBreveBandoINS != null && listaBandiINS != null
				&& listaSportelliINS != null && listaTipologieBeneficiariINS != null) {
			// sono in request, NON li ricalcolo
			log.debug(logprefix + " uso le liste del form di inserimento prese dalla request");

			// TODO : devo gestire il cambio di ditta da Italiana e Estera
		} else {

			log.debug(logprefix + " le liste del form di inserimento non sono in request, li ricarico");
			ArrayList<VistaSportelliAttiviDto> listaSportelliAttiviAreeTematiche = null;
			ArrayList<VistaSportelliAttiviDto> listaSportelliAttivi = null;
			
			try { 
				//PK : in mappaAreeTematicheIns faccio vedere tutte le aree tematiche relative a sportelli aperti
				// Mostro l'area tematica d'arrivo selezionata
				listaSportelliAttiviAreeTematiche = getServiziFindomWeb().getVistaSportelliAttiviByFilter(null, null, getStatus().getSiglaNazioneAzienda(), null);
				listaSportelliAttivi = getServiziFindomWeb().getVistaSportelliAttiviByFilter(null, null, getStatus().getSiglaNazioneAzienda(), idAreaTemINS);
				
				// scorro la lista e popolo tante mappe quanti sono le combo
				// (questo per escludere doppioni)
				Map<Integer, String> mappaNormativeIns = new HashMap<Integer, String>(); 	 // mappa Normative
				Map<Integer, String> mappaDescrBreveIns = new HashMap<Integer, String>();    // mappa Descrizione Breve BAndo
				Map<Integer, String> mappaBandoIns = new HashMap<Integer, String>(); 		 // mappa Bando
				Map<Integer, String> mappaAreeTematicheIns = new HashMap<Integer, String>(); // mappa AreeTerritoriali

				if(listaSportelliAttiviAreeTematiche != null) {
					log.debug(logprefix + " listaSportelliAttiviAreeTematiche.size =" + listaSportelliAttiviAreeTematiche.size());
					for (VistaSportelliAttiviDto sportAT : listaSportelliAttiviAreeTematiche) {
						mappaAreeTematicheIns.put(sportAT.getIdAreaTematica(), sportAT.getDescrizioneAreaTematica());
					}
				}
				getServletRequest().getSession().setAttribute("mappaAreeTematicheIns", mappaAreeTematicheIns);
				
				if (listaSportelliAttivi != null) {
					log.debug(logprefix + " listaSportelliAttivi.size =" + listaSportelliAttivi.size());

					for (VistaSportelliAttiviDto sport : listaSportelliAttivi) {

						if(sport.getIdAreaTematica()!= null && idAreaTemINS!=null && sport.getIdAreaTematica().equals(idAreaTemINS)) {
							mappaNormativeIns.put(sport.getIdNormativa(), sport.getNormativa());
							mappaDescrBreveIns.put(sport.getIdBando(), sport.getCodiceAzione() + " - " + sport.getDescrizioneBreveBando());
							mappaBandoIns.put(sport.getIdBando(), sport.getDescrizioneBando());

						}else {
							// areaTematica non definita,  per ogni combo, prendo TUTTI i valori 
							mappaNormativeIns.put(sport.getIdNormativa(), sport.getNormativa());
							mappaDescrBreveIns.put(sport.getIdBando(), sport.getCodiceAzione() + " - " + sport.getDescrizioneBreveBando());
							mappaBandoIns.put(sport.getIdBando(), sport.getDescrizioneBando());
						}

					}

					log.debug(logprefix + "mappaNormativeIns=" + mappaNormativeIns.toString());
					log.debug(logprefix + "mappaDescrBreveIns=" + mappaDescrBreveIns.toString());
					log.debug(logprefix + "mappaBandoIns=" + mappaBandoIns.toString());
					log.debug(logprefix + "mappaAreeTematicheIns=" + mappaAreeTematicheIns.toString());
					
				} else {
					// non ci sono sportelli attivi per l'areaTematica di provenienza
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
			// quindi nelle Action ChangeBandoInsAction e ChangeDescrBreveInsAction
			listaSportelliINS = new SelItem[0];
			
			// la combo dei Beneficiari viene popolata a seguito di selezione
			// del bando nel campo 'Descrizone breve bando' o nel campo 'Bando'
			// quindi nelle Action ChangeBandoInsAction e ChangeDescrBreveInsAction
			listaTipologieBeneficiariINS = new TipolBeneficiariDto[0];
			
			getServletRequest().getSession().setAttribute("listaNormativeINS", listaNormativeINS);
			getServletRequest().getSession().setAttribute("listadescBreveBandoINS", listadescBreveBandoINS);
			getServletRequest().getSession().setAttribute("listaBandiINS", listaBandiINS);
			getServletRequest().getSession().setAttribute("listaAreeTematicheINS", listaAreeTematicheINS);
			getServletRequest().getSession().setAttribute("listaSportelliINS", listaSportelliINS);
			getServletRequest().getSession().setAttribute("listaTipologieBeneficiariINS", listaTipologieBeneficiariINS);
			
		}

		// in caso non ci siano valori, inizializzo il primo elemento dell'array
		if (listaNormativeINS == null) {
			log.debug(logprefix + " listaNormativeINS NULLA, la inizializzo");
			listaNormativeINS = new SelItem[0];
			
			super.setShowNuovaDomanda("false");
			super.determinaProssimoSportelloAttivo();

			log.debug(logprefix + " getProssimoSportelloAttivo()="+ getProssimoSportelloAttivo());
			
		}
		if (listadescBreveBandoINS == null) {
			log.debug(logprefix + " listadescBreveBandoINS NULLA, la inizializzo");
			listadescBreveBandoINS = new SelItem[0];
		}
		if (listaBandiINS == null) {
			log.debug(logprefix + " listaBandiINS NULLA, la inizializzo");
			listaBandiINS = new SelItem[0];
		}
		if (listaSportelliINS == null) {
			log.debug(logprefix + " listaSportelliINS NULLA, la inizializzo");
			listaSportelliINS = new SelItem[0];
		}
		if (listaTipologieBeneficiariINS == null) {
			log.debug(logprefix + " listaTipologieBeneficiariINS NULLA, la inizializzo");
			listaTipologieBeneficiariINS = new TipolBeneficiariDto[0];
		}
		if (listaAreeTematicheINS == null) {
			log.debug(logprefix + " listaAreeTematicheINS NULLA, la inizializzo");
			listaAreeTematicheINS = new SelItem[0];
		}
		
		log.info(logprefix + " END");
		return result;
	}
 
	private String estraiSiglaNazione() throws ServiziFindomWebException{
		String siglaNazione = "";
		if(StringUtils.isBlank(statoEstero) || statoEstero.equals("-1")){
          StatoEsteroDto stato = getServiziFindomWeb().getStatoEsteroByDescrizione("ITALIA");
          siglaNazione = stato.getCodice();
		}else{			
			siglaNazione = statoEstero;
		}
		return siglaNazione;		
	}
	

	private void aggiornaNazione(ShellSoggettiDto soggettoDB, String siglaNazioneCorrente) throws ServiziFindomWebException {
		String siglaNazioneDB = soggettoDB.getSiglaNazione();
		if(!Objects.toString(siglaNazioneDB, "").equalsIgnoreCase(Objects.toString(siglaNazioneCorrente, ""))){
			getServiziFindomWeb().updateNazioneSoggettoByIdSoggetto(soggettoDB.getIdSoggetto(), siglaNazioneCorrente);
		}	
	}

	/**
	 * Jira: 1381 - msg da visualizzare in caso di domande presenti e concluse da beneficiario
	 * validate()
	 * @return
	 */
	public void  verificaNumeroDomandeConcluse() {
		final String methodName = "verificaNumeroDomandeConcluse";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		
		int numeroDomandeConcluse = 0;
		
		try {
			
			// recupero cf 
			StatusInfo stato = getStatus();
			String cfBeneficirio = stato.getCodFiscaleBeneficiario()!=null ? stato.getCodFiscaleBeneficiario() : "";
			log.info(logprefix + " cfBeneficirio risulta: " + cfBeneficirio);
			
			if ((Constants.RUOLO_AMM).equals(getUserInfo().getRuolo()) || (Constants.RUOLO_LR).equals(getUserInfo().getRuolo())) {
				log.info(logprefix + "getUserInfo().getRuolo(): " + getUserInfo().getRuolo());
				// se utente ha ruolo AMMINISTRATORE
				numeroDomandeConcluse = getServiziFindomWeb().getDomandeConcluseByCodiceFiscale(null, getIdSoggettoBeneficiario());
				log.info(logprefix + " numeroDomandeConcluse risulta: " + numeroDomandeConcluse);
			} else {
				numeroDomandeConcluse = getServiziFindomWeb().getDomandeConcluseByCodiceFiscale(getIdSoggettoCollegato(), getIdSoggettoBeneficiario());
				log.info(logprefix + " numeroDomandeConcluse risulta: " + numeroDomandeConcluse);
			}
			
			// se domande presenti
			if( numeroDomandeConcluse > 0)
			{
				// setto il messaggio da visualizzare a video 
				setViewMsgDomandaConclusa(Constants.PRESENTE_DOMANDA_DA_INVIARE);
				// addActionMessage(getViewMsgDomandaConclusa());
				
			}else{
				log.info(logprefix + "Non ci sono domande da inviare!");
				setViewMsgDomandaConclusa(null);
			}
			
		} catch (ServiziFindomWebException e) {
			e.printStackTrace();
		}
	}	
	
	// GETTERS && SETTERS

	public String getCfNuovaImpresa() {
		return cfNuovaImpresa;
	}

	public void setCfNuovaImpresa(String cfNuovaImpresa) {
		this.cfNuovaImpresa = cfNuovaImpresa;
	}

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

	public String getIdImpresaEnte() {
		return idImpresaEnte;
	}

	public void setIdImpresaEnte(String idImpresaEnte) {
		this.idImpresaEnte = idImpresaEnte;
	}

	public String getNormativa() {
		return normativa;
	}

	public void setNormativa(String normativa) {
		this.normativa = normativa;
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

	public SelItem[] getListaNormativeINS() {
		return listaNormativeINS;
	}

	public void setListaNormativeINS(SelItem[] listaNormativeINS) {
		this.listaNormativeINS = listaNormativeINS;
	}

	public SelItem[] getListaAreeTematicheINS() {
		return listaAreeTematicheINS;
	}

	public void setListaAreeTematicheINS(SelItem[] listaAreeTematicheINS) {
		this.listaAreeTematicheINS = listaAreeTematicheINS;
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

	public String getDescBreveBando() {
		return descBreveBando;
	}

	public void setDescBreveBando(String descBreveBando) {
		this.descBreveBando = descBreveBando;
	}

	public SelItem[] getListadescBreveBando() {
		return listadescBreveBando;
	}

	public void setListadescBreveBando(SelItem[] listadescBreveBando) {
		this.listadescBreveBando = listadescBreveBando;
	}

	public SelItem[] getListadescBreveBandoINS() {
		return listadescBreveBandoINS;
	}

	public void setListadescBreveBandoINS(SelItem[] listadescBreveBandoINS) {
		this.listadescBreveBandoINS = listadescBreveBandoINS;
	}

	public String getDescBreveBandoINS() {
		return descBreveBandoINS;
	}

	public void setDescBreveBandoINS(String descBreveBandoINS) {
		this.descBreveBandoINS = descBreveBandoINS;
	}

	public TipolBeneficiariDto[] getListaTipologieBeneficiariINS() {
		return listaTipologieBeneficiariINS;
	}

	public void setListaTipologieBeneficiariINS(TipolBeneficiariDto[] listaTipologieBeneficiariINS) {
		this.listaTipologieBeneficiariINS = listaTipologieBeneficiariINS;
	}

	public String getCercaDomande() {
		return cercaDomande;
	}

	public void setCercaDomande(String cercaDomande) {
		this.cercaDomande = cercaDomande;
	}

	public AaepDAO getAaepDAO() {
		return aaepDAO;
	}

	public void setAaepDAO(AaepDAO aaepDAO) {
		this.aaepDAO = aaepDAO;
	}

	public String getRisultatoOK() {
		return risultatoOK;
	}

	public void setRisultatoOK(String risultatoOK) {
		this.risultatoOK = risultatoOK;
	}

	public String getRisultatoKO() {
		return risultatoKO;
	}

	public void setRisultatoKO(String risultatoKO) {
		this.risultatoKO = risultatoKO;
	}

	public IpaDAO getIpaDAO() {
		return ipaDAO;
	}

	public void setIpaDAO(IpaDAO ipaDAO) {
		this.ipaDAO = ipaDAO;
	}

	public String getViewMsgDomandaConclusa() {
		return viewMsgDomandaConclusa;
	}

	public void setViewMsgDomandaConclusa(String viewMsgDomandaConclusa) {
		this.viewMsgDomandaConclusa = viewMsgDomandaConclusa;
	}

	public Integer getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}

	public void setIdSoggettoBeneficiario(Integer idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}

	public Integer getIdSoggettoCollegato() {
		return idSoggettoCollegato;
	}

	public void setIdSoggettoCollegato(Integer idSoggettoCollegato) {
		this.idSoggettoCollegato = idSoggettoCollegato;
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

	public Integer getAreaTematicaINS() {
		return areaTematicaINS;
	}

	public void setAreaTematicaINS(Integer areaTematicaINS) {
		this.areaTematicaINS = areaTematicaINS;
	}

	public SelItem[] getListaAreeTematiche() {
		return listaAreeTematiche;
	}

	public void setListaAreeTematiche(SelItem[] listaAreeTematiche) {
		this.listaAreeTematiche = listaAreeTematiche;
	}

	public Integer getAreaTematicaSRC() {
		return areaTematicaSRC;
	}

	public void setAreaTematicaSRC(Integer areaTematicaSRC) {
		this.areaTematicaSRC = areaTematicaSRC;
	}
	
}
