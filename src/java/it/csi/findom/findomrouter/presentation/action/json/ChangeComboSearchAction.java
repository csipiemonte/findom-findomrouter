/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.action.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.VistaDomandeDto;
import it.csi.findom.findomrouter.presentation.action.BaseAction;
import it.csi.findom.findomrouter.presentation.util.ActionUtil;
import it.csi.findom.findomrouter.presentation.util.Constants;
import it.csi.findom.findomrouter.presentation.util.StringUtils;
import it.csi.findom.findomrouter.presentation.vo.SelItem;
import it.csi.findom.findomrouter.presentation.vo.StatusInfo;

public class ChangeComboSearchAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final String CLASS_NAME = "ChangeComboSearchAction";
	
	// liste del form di ricerca
	SelItem[] listaNormative;
	SelItem[] listadescBreveBando;
	SelItem[] listaBandi;
	SelItem[] listaSportelli;
	SelItem[] listaStati;
	SelItem[] listaAreeTematiche;
	
	// campi che vengono postati per la ricerca
	Integer normativa;
	Integer descBreveBando;
	Integer bando;
	Integer sportello;
	String statoDomanda;
	Integer numDomanda;
	Integer areaTematicaSRC;
	
	@Override
	public String executeAction() throws SystemException, UnrecoverableException, CSIException, Throwable {

		final String methodName = "executeAction";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		
		log.debug(logprefix + " BEGIN");
		log.debug(logprefix + " normativa=["+normativa+"]");
		log.debug(logprefix + " descBreveBando=["+descBreveBando+"]");
		log.debug(logprefix + " bando=["+bando+"]");
		log.debug(logprefix + " sportello=["+sportello+"]");
		log.debug(logprefix + " statoDomanda=["+statoDomanda+"]");
		log.debug(logprefix + " numDomanda=["+numDomanda+"]");
		log.debug(logprefix + " areaTematicaSRC=["+areaTematicaSRC+"]");
		
		StatusInfo state = getStatus();
		ArrayList<VistaDomandeDto> listaVistaDomandaDto = null;  // lista delle domande presentate

		// "descBreveBando" ha lo stesso valore di "bando"
		Integer idBando = val(descBreveBando);
		if(idBando == null && val(bando)!=null) {
			idBando = val(bando);
		}
		log.debug(logprefix + " idBando=["+idBando+"]");
		
		// elenco delle domande presentate dalla coppia utenteLoggato+ImpresaScelta
		if((Constants.RUOLO_AMM).equals(getUserInfo().getRuolo())|| (Constants.RUOLO_LR).equals(getUserInfo().getRuolo())){
			// se utente ha ruolo AMMINISTRATORE 
			listaVistaDomandaDto = getServiziFindomWeb().getVistaDomanda(null, state.getIdSoggettoBeneficiario(), val(normativa), 
					idBando, val(numDomanda), val(areaTematicaSRC) );
		}else{
			listaVistaDomandaDto = getServiziFindomWeb().getVistaDomanda(state.getIdSoggettoCollegato(), state.getIdSoggettoBeneficiario(), val(normativa), 
					idBando, val(numDomanda), val(areaTematicaSRC) );
		}
		
		popolaListe(listaVistaDomandaDto);

		log.debug(logprefix + " listaNormative.length=["+listaNormative.length+"]");
		
		getServletRequest().getSession().setAttribute("listaNormative", listaNormative);
		getServletRequest().getSession().setAttribute("listadescBreveBando", listadescBreveBando);
		getServletRequest().getSession().setAttribute("listaBandi", listaBandi);
		getServletRequest().getSession().setAttribute("listaSportelli", listaSportelli);
		getServletRequest().getSession().setAttribute("listaStati", listaStati);
		getServletRequest().getSession().setAttribute("listaAreeTematiche", listaAreeTematiche);
		
		log.debug(logprefix + " END");
		return SUCCESS;
	}

	// Restiruisce null o un Integer >= 0 (escludo il -1)
	private Integer val(Integer val) {
		Integer v = null;
		if(val!=null && val>=0)
			v = val;
		return v;
	}

	private void popolaListe(ArrayList<VistaDomandeDto> lista) {
		final String methodName = "popolaListe";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		
		log.info(logprefix + " BEGIN");
		
		Map<Integer, String> mappaNormative = new HashMap<Integer, String>(); // mappa Normative
		Map<Integer, String> mappaDescrBreve = new HashMap<Integer, String>(); // mappa Descrizione Breve BAndo
		Map<Integer, String> mappaBando = new HashMap<Integer, String>(); // mappa Bando
		Map<Integer, String> mappaSportelli = new HashMap<Integer, String>(); // mappa Sportelli
		Map<String, String> mappaStati= new HashMap<String, String>(); // mappa Stati
		
		Map<String, Integer> mappaNormativaAreeTematiche = new HashMap<String, Integer >(); // mappa ; normativa vs Aree Tematiche
		
		// li metto in sessione in CercaDomandaAction
		Map<Integer, String> mappaAreeTematiche = (Map<Integer, String>) getServletRequest().getSession().getAttribute("mappaAreeTematiche");
		
		if (lista != null) {
			log.debug(logprefix + " listaVistaDomandaDto.size()=" + lista.size());

			for (VistaDomandeDto vistaDomandeDto : lista) 
			{
				mappaNormative.put(vistaDomandeDto.getIdNormativa(), vistaDomandeDto.getNormativa());
				// codiceAzione e' incluso nella descrBreveBando
				mappaDescrBreve.put(vistaDomandeDto.getIdBando(), vistaDomandeDto.getDescrBreveBando());
				mappaBando.put(vistaDomandeDto.getIdBando(), vistaDomandeDto.getDescrBando());
				mappaStati.put(vistaDomandeDto.getCodStatoDomanda(), vistaDomandeDto.getStatoDomanda());
				if(StringUtils.isNotBlank(vistaDomandeDto.getDtChiusuraSportello())){
					mappaSportelli.put(vistaDomandeDto.getIdSportelloBando(), vistaDomandeDto.getDtAperturaSportello() + " - " + vistaDomandeDto.getDtChiusuraSportello());
				}else{
					mappaSportelli.put(vistaDomandeDto.getIdSportelloBando(), vistaDomandeDto.getDtAperturaSportello() + " - data fine non definita");
				}
				mappaNormativaAreeTematiche.put(vistaDomandeDto.getIdNormativa()+"-"+vistaDomandeDto.getIdBando(), vistaDomandeDto.getIdAreaTematica());
			}

//			log.debug(logprefix + "mappaNormative=" + mappaNormative.toString());
//			log.debug(logprefix + "mappaDescrBreve=" + mappaDescrBreve.toString());
//			log.debug(logprefix + "mappaBando=" + mappaBando.toString());
//			log.debug(logprefix + "mappaStati=" + mappaStati.toString());
//			log.debug(logprefix + "mappaSportelli=" + mappaSportelli.toString());
//			log.debug(logprefix + "mappaAreeTematiche=" + mappaAreeTematiche.toString());
//			log.debug(logprefix + "mappaNormativaAreeTematiche=" + mappaNormativaAreeTematiche.toString());

			// solo se ho almeno una domanda per l'area Tematica di ingresso , prepopolo le combo con un solo elemento
			if( mappaNormative.size()==1 ) {
				Map.Entry<Integer,String> entry = mappaNormative.entrySet().iterator().next();
				normativa =  entry.getKey();
				getServletRequest().getSession().setAttribute("normativa", normativa);
			}
			if(mappaDescrBreve.size()==1) {
				Map.Entry<Integer,String> entry2 = mappaDescrBreve.entrySet().iterator().next();
				descBreveBando = entry2.getKey();
				getServletRequest().getSession().setAttribute("descBreveBando", descBreveBando);
			}
			if(mappaBando.size()==1) {
				Map.Entry<Integer,String> entry3 = mappaBando.entrySet().iterator().next();
				bando =  entry3.getKey();
				getServletRequest().getSession().setAttribute("bando", bando);
			}
			if(mappaSportelli.size()==1) {
				Map.Entry<Integer,String> entry4 = mappaSportelli.entrySet().iterator().next();
				sportello = entry4.getKey();
				getServletRequest().getSession().setAttribute("sportello", sportello);
			}
			if(mappaStati.size()==1) {
				Map.Entry<String,String> entry5 = mappaStati.entrySet().iterator().next();
				statoDomanda = entry5.getKey();
				getServletRequest().getSession().setAttribute("statoDomanda", statoDomanda);
			}
			
			if(mappaNormative.size()==1 && mappaBando.size()==1) {
				// devo identificare univocamente una areaTematica
				log.debug(logprefix + " devo identificare univocamente una areaTematica");
				
				String k = normativa + "-" + bando;
				log.debug(logprefix + " chiave k="+k);
				
				areaTematicaSRC = mappaNormativaAreeTematiche.get(k);
				log.debug(logprefix + " areaTematicaSRC="+areaTematicaSRC);
			}

		} else {
			log.debug(logprefix + " listaVistaDomandaDto NULL");
		}

		log.debug(logprefix + "mappaNormative.size=" + mappaNormative.size());
				
		// usando le mappe popolo le liste che valorizzeranno le combo
		listaNormative = ActionUtil.popolaArrayForCombo(mappaNormative);
		listadescBreveBando = ActionUtil.popolaArrayForCombo(mappaDescrBreve);
		listaBandi = ActionUtil.popolaArrayForCombo(mappaBando);
		listaStati = ActionUtil.popolaArrayStatiForCombo(mappaStati);
		listaSportelli = ActionUtil.popolaArrayForCombo(mappaSportelli);
		listaAreeTematiche = ActionUtil.popolaArrayForCombo(mappaAreeTematiche);
		
		log.debug(logprefix + " listaNormative.length=["+listaNormative.length+"]");
		
		log.info(logprefix + " END");
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

	public SelItem[] getListaAreeTematiche() {
		return listaAreeTematiche;
	}

	public void setListaAreeTematiche(SelItem[] listaAreeTematiche) {
		this.listaAreeTematiche = listaAreeTematiche;
	}

	public Integer getNormativa() {
		return normativa;
	}

	public void setNormativa(Integer normativa) {
		this.normativa = normativa;
	}

	public Integer getDescBreveBando() {
		return descBreveBando;
	}

	public void setDescBreveBando(Integer descBreveBando) {
		this.descBreveBando = descBreveBando;
	}

	public Integer getBando() {
		return bando;
	}

	public void setBando(Integer bando) {
		this.bando = bando;
	}

	public Integer getSportello() {
		return sportello;
	}

	public void setSportello(Integer sportello) {
		this.sportello = sportello;
	}

	public String getStatoDomanda() {
		return statoDomanda;
	}

	public void setStatoDomanda(String statoDomanda) {
		this.statoDomanda = statoDomanda;
	}

	public Integer getNumDomanda() {
		return numDomanda;
	}

	public void setNumDomanda(Integer numDomanda) {
		this.numDomanda = numDomanda;
	}

	public Integer getAreaTematicaSRC() {
		return areaTematicaSRC;
	}

	public void setAreaTematicaSRC(Integer areaTematicaSRC) {
		this.areaTematicaSRC = areaTematicaSRC;
	}

}
