/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.action;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.AggrDataDto;
import it.csi.findom.findomrouter.presentation.util.Constants;
import it.csi.findom.findomrouter.presentation.util.StringUtils;
import it.csi.findom.findomrouter.presentation.vo.StatusInfo;

import java.util.ArrayList;
import java.util.TreeMap;

// TODO PK Eliminare
public class PrintDomandaAction extends BaseAction  {

	
/*	
	private static final long serialVersionUID = 1L;
	private static final String CLASS_NAME = "PrintDomandaAction";

	String idDomanda;
*/
	
	@Override
	public String executeAction() throws SystemException, UnrecoverableException, CSIException {
		return SUCCESS;
	}
	
		
/*
		final String methodName = "executeAction";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";

		log.debug(logprefix + " BEGIN");
		
//		verificaSportelliAttivi(); // PK messo in BaseAction

		log.debug(logprefix + " idDomanda="+idDomanda);
		Integer idDomInt = Integer.parseInt(idDomanda);
	
		if(StringUtils.isBlank(idDomanda)){
			//predisponiListe(true);
			log.warn(logprefix + " idDomanda NULLO, impossibile caricare la domanda ");
			addActionMessage(Constants.ELIMINA_DOMANDA_FAILED);
			return ERROR;
		}
				
		///////////////////////////////////////////
		// 1. carico i dati necessari della domanda
		
		// carico i dati dell'aggregatore per la domanda selezionata
		ArrayList<AggrDataDto> listaAggData = getServiziFindomWeb().getAggrDataByIdDomanda(idDomInt);
		if(listaAggData==null || (listaAggData!=null && listaAggData.size()!=1)){
		log.debug(logprefix + " listaAggData NULLA o di dimenzione errata");
		//predisponiListe(true);
		addActionError(Constants.CARICA_DOMANDA_FAILED);
		return ERROR;
		}
		
		AggrDataDto aggrDomanda = listaAggData.get(0);
		
		Integer modelProg = aggrDomanda.getModelProgr();
		log.debug(logprefix + " modelProg="+modelProg);
		
		String cf = getUserInfo().getCodFisc();
		log.debug(logprefix + " cf=["+cf+"]");
		
		String ruolo = getUserInfo().getRuolo();
		log.debug(logprefix + " ruolo="+ruolo);
		
		StatusInfo state = getStatus();
		String cfBenefic = state.getCodFiscaleBeneficiario();
		log.debug(logprefix + " cfBenefic="+cfBenefic);
		
		String userId = aggrDomanda.getUserId();
		log.debug(logprefix + " userId="+userId);
		
		
		///////////////////////////////////////////
		// 2. imposto eventuali valori nello STATE
		
		state.setTemplateId(aggrDomanda.getTemplateId());
		state.setModelName(aggrDomanda.getModelName());
		state.setNumProposta(aggrDomanda.getModelId());  
		state.setStatoProposta(aggrDomanda.getModelStateFk());
		state.setNumSportello(aggrDomanda.getIdSportelloBando());
		state.setAperturaSportelloDa(aggrDomanda.getDataAperturaSportello());
		state.setAperturaSportelloA(aggrDomanda.getDataChiusuraSportello());
		
		setStatus(state);
		log.debug(logprefix + " State aggiornato");
		
		TreeMap<String, Object> context = (TreeMap<String, Object>) this.getServletRequest().getSession().getAttribute(Constants.CONTEXT_ATTR_NAME);
		context.put(Constants.STATUS_INFO, state);
		log.debug(logprefix + " valori dello stato inseriti in REQUEST");

				
		///////////////////////////////////////////
		// 3. imposto valori per l'aggregatore nella request
		
		// PASSO 4 - apre pagina della domanda con il nuovo modello appena salvato.
		log.debug(logprefix + " imposta param per l'anagrafica ");
		
		String xformId = aggrDomanda.getTemplateCode();  			// sarebbe aggr_t_template.template_code
		String xformType = aggrDomanda.getTemplateName();  			// sarebbe aggr_t_template.template_name
		String xformDesc = aggrDomanda.getTemplateDescription();  	// sarebbe aggr_t_template.template_description
		String nomeDomanda = aggrDomanda.getModelName();  			// sarebbe aggr_t_model.model_name
		String modelState =  aggrDomanda.getModelStateFk();		 	// sarebbe aggr_t_model.model_state_fk
		// prendo la descrizione dello stato dalla Mappa presente nell'oggetto StatusInfo
		String modelStateDesc = state.getStatoPropostaMap().get(modelState);
		
		String xcommId = "";
		if((Constants.STATO_BOZZA).equals(modelState) || 
			(Constants.STATO_VALIDATAKO).equals(modelState) ||
			(Constants.STATO_VALIDATAOK).equals(modelState) ||
			(Constants.STATO_VALIDATAWO).equals(modelState) ){
				xcommId = Constants.ACCESS_FORM_RW;  // per accesso RW
		}
		if((Constants.STATO_INVIATA).equals(modelState) || (Constants.STATO_CONCLUSA).equals(modelState)){
			xcommId = Constants.ACCESS_FORM_RO; // per accesso RO
		}
		
		log.debug(logprefix + " xcommId="+xcommId);
		log.debug(logprefix + " xformId="+xformId);
		log.debug(logprefix + " xformType="+xformType);
		log.debug(logprefix + " xformDesc="+xformDesc);
		log.debug(logprefix + " modelProg="+modelProg);
		log.debug(logprefix + " nomeDomanda="+nomeDomanda);
		log.debug(logprefix + " modelState="+modelState);
		log.debug(logprefix + " modelStateDesc="+modelStateDesc);
		
		getServletRequest().setAttribute("#xcommId", xcommId);
		getServletRequest().setAttribute("#xformId", xformId);
		getServletRequest().setAttribute("#xformType", xformType);
		getServletRequest().setAttribute("#xformDesc", xformDesc);
		getServletRequest().setAttribute("#xformProg", modelProg.toString());
		getServletRequest().setAttribute("#xformName", nomeDomanda);
		getServletRequest().setAttribute("#xformState", modelState);
		getServletRequest().setAttribute("#xdraftStr", modelStateDesc);
		
		log.debug(logprefix + " END");
		return SUCCESS;
	}

	public String getIdDomanda() {
		return idDomanda;
	}

	public void setIdDomanda(String idDomanda) {
		this.idDomanda = idDomanda;
	}
	*/
}
