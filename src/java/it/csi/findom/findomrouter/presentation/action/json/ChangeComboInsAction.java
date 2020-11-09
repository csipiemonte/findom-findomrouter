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
import it.csi.findom.findomrouter.dto.serviziFindomWeb.TipolBeneficiariDto;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.VistaSportelliAttiviDto;
import it.csi.findom.findomrouter.exception.ServiziFindomWebException;
import it.csi.findom.findomrouter.presentation.action.BaseAction;
import it.csi.findom.findomrouter.presentation.util.ActionUtil;
import it.csi.findom.findomrouter.presentation.util.StringUtils;
import it.csi.findom.findomrouter.presentation.vo.SelItem;

public class ChangeComboInsAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final String CLASS_NAME = "ChangeComboInsAction";
	
	// liste del form di inserimento
	SelItem[] listaNormativeINS;
	SelItem[] listaAreeTematicheINS;
	SelItem[] listadescBreveBandoINS;
	SelItem[] listaBandiINS;
	SelItem[] listaSportelliINS;
	TipolBeneficiariDto[] listaTipologieBeneficiariINS;
	
	// campi che vengono postati per l'inserimento di una nuova domanda
	Integer areaTematicaINS;
	Integer normativaINS;
	Integer descBreveBandoINS;
	Integer bandoINS;
	Integer sportelloINS;
	
	
	@Override
	public String executeAction() throws SystemException, UnrecoverableException, CSIException, Throwable {
		final String methodName = "executeAction";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		
		log.debug(logprefix + " BEGIN");
		log.debug(logprefix + " areaTematicaINS=["+areaTematicaINS+"]");
		log.debug(logprefix + " normativaINS=["+normativaINS+"]");
		log.debug(logprefix + " descBreveBandoINS=["+descBreveBandoINS+"]");
		log.debug(logprefix + " bandoINS=["+bandoINS+"]");
		
		// "descBreveBandoINS" ha lo stesso valore di "bandoINS"
		Integer idBando = val(descBreveBandoINS);
		if(idBando == null && val(bandoINS)!=null) {
			idBando = val(bandoINS);
		}
		log.debug(logprefix + " idBando=["+idBando+"]");
			
		//  ricavo una lista dalla vista "findom_v_sportelli_attivi"
		ArrayList<VistaSportelliAttiviDto> listaSportelliAttivi =  getServiziFindomWeb().getVistaSportelliAttiviByFilter(val(normativaINS), 
																					idBando, 
																					getStatus().getSiglaNazioneAzienda(),
																					val(areaTematicaINS));

		popolaMappe(listaSportelliAttivi);
		popolaListaTipolBenef();
		
//		log.debug(logprefix + "listaAreeTematicheINS presa da request="+getServletRequest().getSession().getAttribute("listaAreeTematicheINS"));
//		log.debug(logprefix + "listadescBreveBandoINS presa da request="+getServletRequest().getSession().getAttribute("listadescBreveBandoINS"));
//		log.debug(logprefix + "listaBandiINS presa da request="+getServletRequest().getSession().getAttribute("listaBandiINS"));
//		log.debug(logprefix + "listaSportelliINS presa da request="+getServletRequest().getSession().getAttribute("listaSportelliINS"));
//		log.debug(logprefix + "listaTipologieBeneficiariINS presa da request="+getServletRequest().getSession().getAttribute("listaTipologieBeneficiariINS"));
		
		// aggiorno le liste in request
		getServletRequest().getSession().setAttribute("listaNormativeINS",listaNormativeINS);
		getServletRequest().getSession().setAttribute("listaAreeTematicheINS",listaAreeTematicheINS);
		getServletRequest().getSession().setAttribute("listadescBreveBandoINS",listadescBreveBandoINS);
		getServletRequest().getSession().setAttribute("listaBandiINS",listaBandiINS);
		getServletRequest().getSession().setAttribute("listaSportelliINS",listaSportelliINS);
		getServletRequest().getSession().setAttribute("listaTipologieBeneficiariINS",listaTipologieBeneficiariINS);

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
	

	/**
	 * Data la lista di valori estratti dalla vista "findom_v_sportelli_attivi" popolo le liste 
	 *  - listadescBreveBandoINS
	 *  - listaBandiINS
	 *  
	 * @param lista
	 */
	private void popolaMappe(ArrayList<VistaSportelliAttiviDto> lista) {

		final String methodName = "popolaMappe";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		
		log.debug(logprefix + " BEGIN");
		
		Map<Integer, String>  mappaNormative = new HashMap<Integer, String>(); // mappa Normative
		Map<Integer, String>  mappaDescrBreve = new HashMap<Integer, String>();// mappa Descrizione Breve BAndo
		Map<Integer, String>  mappaBando = new HashMap<Integer, String>(); 	// mappa  Bando
		Map<Integer, String>  mappaSportelli = new HashMap<Integer, String>(); // mappa Sportelli
		
		// li metto in sessione in CercaDomandaAction
		Map<Integer, String> mappaAreeTematicheIns = (Map<Integer, String>) getServletRequest().getSession().getAttribute("mappaAreeTematicheIns");

		Map<String, Integer> mappaNormativaAreeTematiche = new HashMap<String, Integer >(); // mappa ; normativa vs Aree Tematiche
		
		if(lista!=null){
			log.debug(logprefix + " lista.size ="+lista.size());
			
			for (VistaSportelliAttiviDto sport : lista) {
				mappaDescrBreve.put(sport.getIdBando(), sport.getCodiceAzione()+" - "+sport.getDescrizioneBreveBando());
				mappaBando.put(sport.getIdBando(), sport.getDescrizioneBando());
				mappaNormative.put(sport.getIdNormativa(), sport.getNormativa());
				
				mappaNormativaAreeTematiche.put(sport.getIdNormativa()+"-"+sport.getIdBando(), sport.getIdAreaTematica());
			}
			
		} else {
			log.debug(logprefix + " listaSportelliAttivi NULL");
		}
		
		// carico la mappaSportelli solo se ho trovato un unico elemento in mappaDescrBreve (quindi anche un unico elemento in mappaBando)
		if(!mappaDescrBreve.isEmpty() && mappaDescrBreve.size()==1){
			log.debug(logprefix + "mappaDescrBreve.size()="+mappaDescrBreve.size());
			
			for (VistaSportelliAttiviDto sport : lista) {
				if(mappaDescrBreve.containsKey(sport.getIdBando())){
					log.debug(logprefix + "carico date per idBAndo="+sport.getIdBando());
					if(StringUtils.isNotBlank(sport.getDtChiusura())){
						mappaSportelli.put(sport.getIdSportelloBando(), sport.getDtApertura()+" - "+sport.getDtChiusura());
					}else{
						mappaSportelli.put(sport.getIdSportelloBando(), sport.getDtApertura()+ " - data fine non definita");
					}
				}
			}
		}
		
		if(mappaSportelli!=null)
			log.debug(logprefix + "mappaSportelli="+mappaSportelli.toString());
		
		
		if( mappaNormative.size()==1 ) {
			Map.Entry<Integer,String> entry =  mappaNormative.entrySet().iterator().next();
			normativaINS =  entry.getKey();
			getServletRequest().getSession().setAttribute("normativaINS", normativaINS);
		}
		if(mappaDescrBreve.size()==1) {
			Map.Entry<Integer,String> entry2 = mappaDescrBreve.entrySet().iterator().next();
			descBreveBandoINS = entry2.getKey();
			getServletRequest().getSession().setAttribute("descBreveBandoINS", descBreveBandoINS);
		}
		if(mappaBando.size()==1) {
			Map.Entry<Integer,String> entry3 = mappaBando.entrySet().iterator().next();
			bandoINS =  entry3.getKey();
			getServletRequest().getSession().setAttribute("bandoINS", bandoINS);
		}
		if(mappaSportelli.size()==1) {
			Map.Entry<Integer,String> entry4 = mappaSportelli.entrySet().iterator().next();
			sportelloINS = entry4.getKey();
			getServletRequest().getSession().setAttribute("sportelloINS", sportelloINS);
		}
		if(mappaNormative.size()==1 && mappaBando.size()==1) {
			// devo identificare univocamente una areaTematica
			log.debug(logprefix + " devo identificare univocamente una areaTematica");
			
			String k = normativaINS + "-" + bandoINS;
			log.debug(logprefix + " chiave k="+k);
			
			areaTematicaINS = mappaNormativaAreeTematiche.get(k);
			log.debug(logprefix + " areaTematicaINS="+areaTematicaINS);
		}
		
		// usando le mappe popolo le liste che valorizzeranno le combo
		listaNormativeINS =  ActionUtil.popolaArrayForCombo(mappaNormative);
		listadescBreveBandoINS =  ActionUtil.popolaArrayForCombo(mappaDescrBreve);
		listaBandiINS = ActionUtil.popolaArrayForCombo(mappaBando);
		listaSportelliINS = ActionUtil.popolaArrayForCombo(mappaSportelli);
		listaAreeTematicheINS = ActionUtil.popolaArrayForCombo(mappaAreeTematicheIns);
		
		log.debug(logprefix + " END");
	}
	

	/**
	 * Popolo la combo delle Tipologie dei Beneficiari
	 * 
	 * @throws ServiziFindomWebException
	 * @throws NumberFormatException
	 */
	private void popolaListaTipolBenef() throws ServiziFindomWebException, NumberFormatException {
		final String methodName = "popolaListaTipolBenef";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		
		log.debug(logprefix + " BEGIN");
		// precarico la listaTipologieBeneficiariINS solo se ho trovato un unico elemento in listadescBreveBandoINS (quindi anche un unico elemento in listaBandiINS)
		if(listadescBreveBandoINS!=null && listadescBreveBandoINS.length==1){
			log.debug(logprefix + "listadescBreveBandoINS.length="+listadescBreveBandoINS.length);
			
			ArrayList<TipolBeneficiariDto> listaTipolBenef = getServiziFindomWeb().getListaTipolBeneficiariByIdBando(Integer.parseInt(listadescBreveBandoINS[0].getKey()));
			if(listaTipolBenef!=null && listaTipolBenef.size()>0){
				log.debug(logprefix + "listaTipolBenef.size()="+listaTipolBenef.size());
				listaTipologieBeneficiariINS = new TipolBeneficiariDto[listaTipolBenef.size()];
				listaTipolBenef.toArray(listaTipologieBeneficiariINS);
			}else{
				log.debug(logprefix + "listaTipolBenef NULL");
				listaTipologieBeneficiariINS = new TipolBeneficiariDto[0];
			}
		}else{
			log.debug(logprefix + "listaTipologieBeneficiariINS NULL");
			listaTipologieBeneficiariINS = new TipolBeneficiariDto[0];
		}
		log.debug(logprefix + " END");
	}
	
	
	
	public SelItem[] getListaAreeTematicheINS() {
		return listaAreeTematicheINS;
	}

	public void setListaAreeTematicheINS(SelItem[] listaAreeTematicheINS) {
		this.listaAreeTematicheINS = listaAreeTematicheINS;
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

	public void setListaTipologieBeneficiariINS(TipolBeneficiariDto[] listaTipologieBeneficiariINS) {
		this.listaTipologieBeneficiariINS = listaTipologieBeneficiariINS;
	}

	public Integer getAreaTematicaINS() {
		return areaTematicaINS;
	}

	public void setAreaTematicaINS(Integer areaTematicaINS) {
		this.areaTematicaINS = areaTematicaINS;
	}

	public Integer getNormativaINS() {
		return normativaINS;
	}

	public void setNormativaINS(Integer normativaINS) {
		this.normativaINS = normativaINS;
	}

	public Integer getDescBreveBandoINS() {
		return descBreveBandoINS;
	}

	public void setDescBreveBandoINS(Integer descBreveBandoINS) {
		this.descBreveBandoINS = descBreveBandoINS;
	}

	public Integer getBandoINS() {
		return bandoINS;
	}

	public void setBandoINS(Integer bandoINS) {
		this.bandoINS = bandoINS;
	}

	public SelItem[] getListaNormativeINS() {
		return listaNormativeINS;
	}

	public void setListaNormativeINS(SelItem[] listaNormativeINS) {
		this.listaNormativeINS = listaNormativeINS;
	}

	public Integer getSportelloINS() {
		return sportelloINS;
	}

	public void setSportelloINS(Integer sportelloINS) {
		this.sportelloINS = sportelloINS;
	}

}
