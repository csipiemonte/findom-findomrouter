/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.action;

public class EsciAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private static final String CLASS_NAME = "EsciAction";

	public String executeAction()  {
		final String methodName = "executeAction";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		
		log.debug(logprefix +" BEGIN-END");
			
		return SUCCESS;
	}

}
