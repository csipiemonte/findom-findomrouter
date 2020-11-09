/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.action.modali;

import it.csi.findom.findomrouter.presentation.action.BaseAction;

public class ApriModaliProfiloAction extends BaseAction {
	
	/* azione scatenata dal mini form con i due bottoni ESCI e CAMBIA PROFILO  
	   questa azione viene invocata se e solo se i JAVASCRIPT sono DISABILITATI */
	
	private static final long serialVersionUID = 1L;
	
	private static final String CLASS_NAME = "ApriModaliProfiloAction";

	String esci;
	
	public String executeAction() {
		final String methodName = "executeAction";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		
		String result= "";
		log.debug(logprefix +" BEGIN");
		log.debug(logprefix +" esci=["+esci+"]");
		
		if(esci!=null){
			result="esci";
		}
		log.debug(logprefix +" END, result="+result);
		return result;
		
	}

	public String getEsci() {
		return esci;
	}

	public void setEsci(String esci) {
		this.esci = esci;
	}
}
