/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.util;

import org.apache.log4j.Logger;

public class CustomControl {
	
	protected static final Logger log = Logger.getLogger(Constants.APPLICATION_CODE + ".util");
	private static final String CLASS_NAME = "CustomControl";
	
	private static final String RESULT_OK = "OK";
	private static final String RESULT_KO_OBBLIGATORIO = "KO_OBBLIGATORIO";
	private static final String RESULT_KO_VALOREMINORE = "KO_VALOREMINORE";
	private static final String RESULT_KO_VALOREMAGGIORE = "KO_VALOREMAGGIORE";
	private static final String RESULT_KO_NONNUMERICO = "KO_NONNUMERICO";
	private static final String RESULT_KO_NUMBEREXCEPTION = "KO_NUMBEREXCEPTION";
	
	/**
	 * 
	 * Verifiche :
	 *  - field NON null e NON vuoto
	 *  - field >= (o <=) comparatore
	 *  - field numerico
	 *  
	 * @param field Campo su cui fare le verifiche
	 * @param comparatore numero soglia
	 * @param operazione Puo' essere >= o <=
	 * @return
	 */
	public static String isFieldNumeric(String field, int comparatore, String operazione) {
		
		final String methodName = "isFieldNumeric";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		
		log.debug(logprefix + " BEGIN");
		
		String result;
		try {
			result = RESULT_OK;
			
			if(StringUtils.isBlank(field)){
				
				log.info(logprefix + "campo NULLO o stringa vuota");
				result = RESULT_KO_OBBLIGATORIO;
				
			}else{
			
				if(StringUtils.isNumeric(field)){
					
					int numAuleInt = Integer.parseInt(field);
					
					if(operazione.equals("<=") && numAuleInt <= comparatore){
						log.info(logprefix + "campo minore o uguale a zero.");
						result = RESULT_KO_VALOREMINORE;
					}
					
					if(operazione.equals(">=") && numAuleInt >= comparatore){
						log.info(logprefix + "campo maggiore o uguale a zero.");
						result = RESULT_KO_VALOREMAGGIORE;
					}
					
				}else{
					
					log.info(logprefix + "numAule contiene caratteri non numerici");
					result = RESULT_KO_NONNUMERICO;
				}
				
			}
		} catch (NumberFormatException e) {
			result = RESULT_KO_NUMBEREXCEPTION;
		}
		
		log.debug(logprefix + " END, result="+result);
		return result;
	}

}
