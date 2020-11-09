/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.util;

//import org.apache.log4j.Logger;

/**
 * Comparatore custom per colonne numeriche (da usare con la DisplayTag); 
 * per le quali fornisce un criterio di ordinamento discendente basato sul valore numerico delle celle della colonna
 * 
 * @author Mauro Bottero 24/03/2016
 */
public class NumericDescCellComparator implements java.util.Comparator 
{

//	protected static final Logger log = Logger.getLogger(Constants.APPLICATION_CODE + ".presentation.util");
	private static final String CLASS_NAME = "NumericDescCellComparator";

	public NumericDescCellComparator(){
		super();
	}

	@Override	
	public int compare(Object o1, Object o2) {

		final String methodName = "compare";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";

//		log.debug(logprefix + " BEGIN");
		
		if(o1!=null && 
			o2!=null && 
			(o1 instanceof org.displaytag.model.Cell) && 
			(o2 instanceof org.displaytag.model.Cell) ){
			
			String strVal1 = "";
			String strVal2 = "";
			Integer intVal1 = null;
			Integer intVal2 = null;
			
			Object objVal1 = ((org.displaytag.model.Cell) o1).getStaticValue();
			strVal1 = (objVal1 == null ? "0" : objVal1.toString());			
			try {
				intVal1 = new Integer(strVal1.trim());				
			} catch (NumberFormatException e) {
				intVal1 =new Integer(0);				
			}
		
			Object objVal2 = ((org.displaytag.model.Cell) o2).getStaticValue();
			strVal2 = (objVal2 == null ? "0" : objVal2.toString());			
			try {
				intVal2 = new Integer(strVal2.trim());				
			} catch (NumberFormatException e) {
				intVal2 = new Integer(0);				
			}
			
//			log.debug(logprefix + "confrontato i valori :  "+ intVal1 + " e " + intVal2 + "; esito = " + intVal1.compareTo(intVal2)); 
			
//			log.debug(logprefix + " END");
			//il - serve per avere un ordinamento discendente
			return -(intVal1.compareTo(intVal2));
		}
		
//		log.debug(logprefix + " gli oggetti da confrontare sono nulli o il loro tipo non e' corretto; ritorno 0");
//		log.debug(logprefix + " END");
		
		return 0;
	}

}
