/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.action;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.ShellSoggettiDto;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.TipolBeneficiariDto;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.VistaDomandeDto;
import it.csi.findom.findomrouter.presentation.util.Constants;
import it.csi.findom.findomrouter.presentation.util.StringUtils;
import it.csi.findom.findomrouter.presentation.vo.Domanda;
import it.csi.findom.findomrouter.presentation.vo.SelItem;
import it.csi.findom.findomrouter.presentation.vo.StatusInfo;
import it.csi.util.performance.StopWatch;

import java.util.ArrayList;

import com.opensymphony.xwork2.ValidationAware;

public class RiepilogoDomandaAction  extends BaseAction implements ValidationAware {

	private static final long serialVersionUID = 1L;
	
	private static final String CLASS_NAME = "RiepilogoDomandaAction";
	
	String idDomanda;
	VistaDomandeDto domanda;
	ShellSoggettiDto soggCreatore;
	ShellSoggettiDto soggConclusione;
	ShellSoggettiDto soggInviatore;
	
	// liste del form di ricerca
	SelItem[] listaNormative;
	SelItem[] listadescBreveBando;
	SelItem[] listaBandi;
	SelItem[] listaSportelli;
	SelItem[] listaStati;
	
	// liste del form di inserimento
	SelItem[] listaNormativeINS;
	SelItem[] listadescBreveBandoINS;
	SelItem[] listaBandiINS;
	SelItem[] listaSportelliINS;
	TipolBeneficiariDto[] listaTipologieBeneficiariINS;
	
	SelItem[] listaAreeTematiche;
	SelItem[] listaAreeTematicheINS;
	
	// lista delle domande trovate
	ArrayList<Domanda> listaDomande ;
	
	// campi che vengono postati per la ricerca
	String normativa;
	String descBreveBando;
	String bando;
	String sportello;
	String statoDomanda;
	String numDomanda;
	Integer areaTematica;
	Integer areaTematicaINS;
		
	@Override
	public String executeAction() throws SystemException,
			UnrecoverableException, CSIException {
		
		final String methodName = "executeAction";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		
		String flagBandoDematerializzato = "N";

		log.debug(logprefix + " BEGIN");
		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
		
		log.debug(logprefix + " idDomanda="+idDomanda);
		Integer idDomInt = Integer.parseInt(idDomanda);
		
		if(StringUtils.isBlank(idDomanda)){
			predisponiListe();
			log.warn(logprefix + " idDomanda NULLO, impossibile caricare la domanda ");
			addActionMessage(Constants.RIEPILOGO_DOMANDA_ERROR);
			return ERROR;
		}
		
		String ruolo = getUserInfo().getRuolo();
		log.debug(logprefix + " ruolo="+ruolo);
		
		StatusInfo state = getStatus();
		String cfBenefic = state.getCodFiscaleBeneficiario();
		log.debug(logprefix + " cfBenefic="+cfBenefic);
		
		ArrayList<VistaDomandeDto> listaVistaDomandaDto = null;  // lista delle domande presentate
		
		// elenco delle domande presentate dalla coppia utenteLoggato+ImpresaScelta
		if((Constants.RUOLO_AMM).equals(getUserInfo().getRuolo()) || (Constants.RUOLO_LR.equals(ruolo))){
			// se utente ha ruolo AMMINISTRATORE 
			listaVistaDomandaDto = getServiziFindomWeb().getVistaDomanda(null, state.getIdSoggettoBeneficiario(), null, null, Integer.parseInt(idDomanda), null);
		
		}else{
			listaVistaDomandaDto = getServiziFindomWeb().getVistaDomanda(state.getIdSoggettoCollegato(), state.getIdSoggettoBeneficiario(), null, null, Integer.parseInt(idDomanda), null);
		}
		
		if(listaVistaDomandaDto==null || (listaVistaDomandaDto!=null && listaVistaDomandaDto.size()!=1)){
			log.warn(logprefix + "nessuna domanda trovata con quell'id, impossibile caricare la domanda ");
			predisponiListe();
			addActionMessage(Constants.RIEPILOGO_DOMANDA_ERROR);
			return ERROR;
		}else{
			domanda = listaVistaDomandaDto.get(0);
			flagBandoDematerializzato = domanda.getFlagBandoDematerializzato();
		}
		
		// carico i dati dei soggetti Inviatore e Creatore della domanda
		// TODO : solo perche' i CF non sono nella vista....
		String idSoggCreatore = domanda.getIdSoggettoCreatore()+"";
		
		String idSoggInvio = null;
		if(domanda.getIdSoggettoInvio()!=0){
			idSoggInvio = domanda.getIdSoggettoInvio()+"";
		}
		log.debug(logprefix + " idSoggCreatore="+idSoggCreatore);
		log.debug(logprefix + " idSoggInvio="+idSoggInvio);
		
		ArrayList<String> arrayIdSoggetti = new ArrayList<String>();
		arrayIdSoggetti.add(idSoggCreatore);
		if(idSoggInvio!=null && !StringUtils.equals(idSoggCreatore, idSoggInvio) ){
			arrayIdSoggetti.add(idSoggInvio);
		}
		
		String idSoggConclusione = null;
		if(domanda.getIdSoggettoConclusione()!=0){
			idSoggConclusione = domanda.getIdSoggettoConclusione()+"";
		}
		log.debug(logprefix + " idSoggConclusione="+idSoggConclusione);		

		// PK Jira 1329 - elimino le ore nelle date protocollo e classificazione
		// La data protocollo vale sempre 00:00 a meno che non si modifichi il batch findomprt
		domanda.setDtProtocolloDomanda(eliminoOre(domanda.getDtProtocolloDomanda()));
		domanda.setDtClassificazioneDomanda(eliminoOre(domanda.getDtClassificazioneDomanda()));
		// fine Jira 1329
		
		ArrayList<ShellSoggettiDto> arraySoggetti = getServiziFindomWeb().getDatiSoggettoByIdSoggetto(arrayIdSoggetti);
		
		if(arrayIdSoggetti!=null && arrayIdSoggetti.size()>0){
			 soggCreatore = arraySoggetti.get(0);
			 log.debug(logprefix + " soggCreatore valorizzato");
			 
			 if(idSoggInvio==null){
				 
				 soggInviatore = null;
				 log.debug(logprefix + " soggInviatore nullo");
			 }else if( !StringUtils.equals(idSoggCreatore, idSoggInvio)){
				
				 soggInviatore = arraySoggetti.get(1);
				 log.debug(logprefix + " soggInviatore valorizzato");
			 }else{
				soggInviatore = soggCreatore;
				log.debug(logprefix + " soggInviatore == soggCreatore");
			 }
			
		}else{
			log.warn(logprefix + "nessuna soggetto trovato, impossibile caricare la domanda ");
			predisponiListe();
			addActionMessage(Constants.RIEPILOGO_DOMANDA_ERROR);
			return ERROR;
		}
		
		if(idSoggConclusione==null){

			soggConclusione = null;
			log.debug(logprefix + " soggConclusione nullo");
		}else {
			if( StringUtils.equals(idSoggCreatore, idSoggConclusione)){
				soggConclusione = soggCreatore;
				log.debug(logprefix + " soggConclusione == soggCreatore");

			}else{
				if( StringUtils.equals(idSoggCreatore, idSoggInvio)){
					soggConclusione = soggInviatore;
				} else {
					arrayIdSoggetti = new ArrayList<String>();
					arrayIdSoggetti.add(idSoggConclusione);
					if(arrayIdSoggetti!=null && arrayIdSoggetti.size()>0){
						soggConclusione = arraySoggetti.get(0);
					}
					log.debug(logprefix + " soggConclusione valorizzato");
				}
			}
		}
		
		stopWatch.stop();
		stopWatch.dumpElapsed("RiepilogoDomandaAction", "executeAction()", "test", "test");
		log.debug(logprefix + "  END");

		if (Constants.FLAG_BANDO_DEMATERIALIZZATO.equalsIgnoreCase(flagBandoDematerializzato))
			return "successDemat";
		else
			return SUCCESS;
	}

	private String eliminoOre(String strData) {
		String dt = strData;
		if(strData!=null && strData.contains(" ")){
			String[] r = strData.split(" ");
			System.out.println(" tests() r[0]=" + r[0]);
			dt = r[0];
		}
		return dt;
	}

	private void predisponiListe() {
		final String methodName = "loadListe";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		log.debug(logprefix + " BEGIN");
		
		/////////////////////////////////////////
		// Combo di ricerca 
		// svuoto le liste delle combo forzandone il ricaricamento nella Action "cercaDomande"
		/////////////////////////////////////////
		
		// popolo le combo del form di inserimento
		listaNormative = (SelItem[]) getServletRequest().getSession().getAttribute("listaNormative");
		listadescBreveBando = (SelItem[]) getServletRequest().getSession().getAttribute("listadescBreveBando");
		listaBandi = (SelItem[]) getServletRequest().getSession().getAttribute("listaBandi");
		listaSportelli = (SelItem[]) getServletRequest().getSession().getAttribute("listaSportelli");
		listaStati = (SelItem[]) getServletRequest().getSession().getAttribute("listaStati");
		listaAreeTematiche = (SelItem[]) getServletRequest().getSession().getAttribute("listaAreeTematiche");
		log.debug(logprefix + " ricaricate liste delle combo del form di ricerca dalla sessione");
		
		/////////////////////////////////////////
		// Combo di inserimento
		// le prendo dalla request.
		/////////////////////////////////////////
		
		log.debug(logprefix + "listaNormativeINS presa da request="+getServletRequest().getSession().getAttribute("listaNormativeINS"));
		log.debug(logprefix + "listaBandiINS presa da request="+getServletRequest().getSession().getAttribute("listaBandiINS"));
		log.debug(logprefix + "listadescBreveBandoINS presa da request="+getServletRequest().getSession().getAttribute("listadescBreveBandoINS"));
		log.debug(logprefix + "listaSportelliINS presa da request="+getServletRequest().getSession().getAttribute("listaSportelliINS"));
		log.debug(logprefix + "listaTipologieBeneficiariINS presa da request="+getServletRequest().getSession().getAttribute("listaTipologieBeneficiariINS"));
		log.debug(logprefix + "listaAreeTematicheINS presa da request="+getServletRequest().getSession().getAttribute("listaAreeTematicheINS"));
		
		listaNormativeINS = (SelItem[]) getServletRequest().getSession().getAttribute("listaNormativeINS");
		listadescBreveBandoINS = (SelItem[]) getServletRequest().getSession().getAttribute("listadescBreveBandoINS");
		listaBandiINS = (SelItem[]) getServletRequest().getSession().getAttribute("listaBandiINS");
		listaSportelliINS = (SelItem[]) getServletRequest().getSession().getAttribute("listaSportelliINS");
		listaTipologieBeneficiariINS = (TipolBeneficiariDto[]) getServletRequest().getSession().getAttribute("listaTipologieBeneficiariINS");
		listaAreeTematicheINS = (SelItem[]) getServletRequest().getSession().getAttribute("listaAreeTematicheINS");
		log.debug(logprefix + " ricaricate liste delle combo del form di inserimento dalla sessione");
		
		log.debug(logprefix + " END");
	}
	
	// GETTERS && SETTERS
	
	public String getIdDomanda() {
		return idDomanda;
	}

	public void setIdDomanda(String idDomanda) {
		this.idDomanda = idDomanda;
	}

	public VistaDomandeDto getDomanda() {
		return domanda;
	}

	public void setDomanda(VistaDomandeDto domanda) {
		this.domanda = domanda;
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

	public SelItem[] getListaNormativeINS() {
		return listaNormativeINS;
	}

	public void setListaNormativeINS(SelItem[] listaNormativeINS) {
		this.listaNormativeINS = listaNormativeINS;
	}

	public SelItem[] getListadescBreveBandoINS() {
		return listadescBreveBandoINS;
	}

	public void setListadescBreveBandoINS(SelItem[] listadescBreveBandoINS) {
		this.listadescBreveBandoINS = listadescBreveBandoINS;
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

	public TipolBeneficiariDto[] getListaTipologieBeneficiariINS() {
		return listaTipologieBeneficiariINS;
	}

	public void setListaTipologieBeneficiariINS(
			TipolBeneficiariDto[] listaTipologieBeneficiariINS) {
		this.listaTipologieBeneficiariINS = listaTipologieBeneficiariINS;
	}

	public ArrayList<Domanda> getListaDomande() {
		return listaDomande;
	}

	public void setListaDomande(ArrayList<Domanda> listaDomande) {
		this.listaDomande = listaDomande;
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

	public ShellSoggettiDto getSoggCreatore() {
		return soggCreatore;
	}

	public void setSoggCreatore(ShellSoggettiDto soggCreatore) {
		this.soggCreatore = soggCreatore;
	}

	public ShellSoggettiDto getSoggInviatore() {
		return soggInviatore;
	}

	public void setSoggInviatore(ShellSoggettiDto soggInviatore) {
		this.soggInviatore = soggInviatore;
	}
	
	public ShellSoggettiDto getSoggConclusione() {
		return soggConclusione;
	}

	public void setSoggConclusione(ShellSoggettiDto soggConclusione) {
		this.soggConclusione = soggConclusione;
	}

	public SelItem[] getListaAreeTematiche() {
		return listaAreeTematiche;
	}

	public void setListaAreeTematiche(SelItem[] listaAreeTematiche) {
		this.listaAreeTematiche = listaAreeTematiche;
	}

	public SelItem[] getListaAreeTematicheINS() {
		return listaAreeTematicheINS;
	}

	public void setListaAreeTematicheINS(SelItem[] listaAreeTematicheINS) {
		this.listaAreeTematicheINS = listaAreeTematicheINS;
	}

	public Integer getAreaTematica() {
		return areaTematica;
	}

	public void setAreaTematica(Integer areaTematica) {
		this.areaTematica = areaTematica;
	}

	public Integer getAreaTematicaINS() {
		return areaTematicaINS;
	}

	public void setAreaTematicaINS(Integer areaTematicaINS) {
		this.areaTematicaINS = areaTematicaINS;
	}

}
