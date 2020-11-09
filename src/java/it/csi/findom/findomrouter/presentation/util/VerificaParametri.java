/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.util;

import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

public class VerificaParametri {
	
	protected static final Logger log = Logger.getLogger(Constants.APPLICATION_CODE + ".util");
	private static final String CLASS_NAME = "VerificaParametri";

	
	/**
	 * Ciclo sui parametri in request per verificare possibili injection
	 * @param request
	 * @return True se trovato un parametro con un valore sospetto 
	 */
	public static boolean controllaParamInReq(HttpServletRequest request) {
		final String methodName = "controllaParamInReq";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";

		log.debug(logprefix + "BEGIN");
		boolean ret = false;
		
		Enumeration reqParamNameEnum = request.getParameterNames();
		
		while (reqParamNameEnum.hasMoreElements()) {
			String paramName = (String) reqParamNameEnum.nextElement();
			String paramVal = request.getParameter(paramName);
			log.debug(logprefix + "REQUEST paramName=[" + paramName+ "] : value=[" + paramVal + "]");

			ret = controlliXSS(paramName, paramVal);
			if(ret) {
				request.removeAttribute(paramName);
				break;
			}

			// Il valore del parametro potrebbe essere codificato con urlencode.
			// Decodifico il parametro (eliminando %22 %27 ..) e rifaccio la verifica
			try {
				// se ci sono dei caratteri "%", URLDecode restituisce l'errore
				// e=URLDecoder: Illegal hex characters in escape (%) pattern
				if(paramVal.contains("%")){
					paramVal = paramVal.replace("%", "");
				}
				
				String paramsDecoded = URLDecoder.decode(paramVal, "UTF-8");
				if(!StringUtils.equals(paramVal, paramsDecoded)){
					log.debug(logprefix + "Verifico urlencode. paramsDecoded=["+paramsDecoded+"]");
					ret = controlliXSS(paramName, paramsDecoded);
					if(ret) {
						request.removeAttribute(paramName);
						break;
					}
//				}else{
//					log.debug(logprefix + " paramDecoded==paramVal salto controllo");
				}
			
			} catch (Exception e) {
				log.error(logprefix + " Exception, e="+e.getMessage());
				e.printStackTrace();
			}
			
			// Il valore del parametro potrebbe essere codificato con l'escapeHTML.
			// Elimina i carattert &amp; &apos;  e rifaccio la verifica
			try {
				String paramsUnescaped = StringEscapeUtils.unescapeHtml(paramVal); 
				if(!StringUtils.equals(paramVal, paramsUnescaped)){
					log.debug(logprefix + "Verifico escapeHTML. paramsUnescaped=["+paramsUnescaped+"]");
					ret = controlliXSS(paramName, paramsUnescaped);
					if(ret) {
						request.removeAttribute(paramName);
						break;
					}
					//			}else{
					//				log.debug(logprefix + " paramsUnescaped==paramVal salto controllo");
				}
			} catch (Exception e) {
				log.error(logprefix + " Exception, e="+e.getMessage());
				e.printStackTrace();
			}
			log.debug(logprefix + "parametro ["+paramName+"] valido");
		}
		
		log.debug(logprefix + "END, ret="+ret);
		return ret;
	}
	
	
	/**
	 * Verifico i parametri in request.
	 * 
	 * @param paramName
	 * @param paramVal
	 * @param request 
	 * @return True se trovato un parametro con un valore sospetto 
	 */
	private static boolean controlliXSS(String paramName,String paramVal) {
		final String methodName = "controlliXSS";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";

//		log.debug(logprefix + "BEGIN");
		boolean res = false;
		
		// Array contenente i nomi dei parametri applicativi (MUST be sorted arrays, altrimenti non trova nulla)
		String[] arr = new String[] {"_command", "AGGR_draftStr", "AGGR_formDesc", "AGGR_formId", "AGGR_formName", "AGGR_formProg", "AGGR_formState", "AGGR_formType", "AGGR_modeId", "AGGR_pageId", "AGGR_pageXp", "AGGR_sectId", "AGGR_sectXp"};
		
		if(isAcceptedParam(arr, paramName)){
			// parametro  trovato
//			log.debug(logprefix + " tratto parametro di base dell'Aggregatore");
			res = verificaParamApplicativo(paramName, paramVal);
			
		} else {

			log.debug(logprefix + " tratto parametro NON di base dell'Aggregatore");
			if (!ControlCrossSiteScripting.isGoodParameter(paramVal)){
				log.error(logprefix + "REQUEST paramName=[" + paramName+ "] errato");
				res = true;
			}
		}
		
		log.debug(logprefix + "END, res="+res);
		return res;
	}
	
	/**
	 * 
	 * @param arr : array di nomi di parametri validi
	 * @param targetValue : nome del parametro da verificare se appartiene all'array
	 * @return : True se il parametro e' tra quelli dell'aggregatore	
	 */
	private static boolean isAcceptedParam(String[] arr, String targetValue) {
		for(String s: arr){
			if(s.equals(targetValue))
				return true;
		}
		return false;
	}
	
	/**
	 * Verifico solo i parametri applicativi necessari all'aggregatore.
	 * I parametri gestiti dall'aggregatore sono raggruppabili in 5 tipologie:
	 * - Stringhe note
	 * - Stringhe alfanumeriche con al piu "_"
	 * - numeri
	 * - Stringhe con una determinata struttura es: //*[@id='S1'
	 * - Stringhe alfanumeriche generiche
	 *
	 * I parametri sono i seguenti
	 *
	 * AGGR_modeId=ACCESS_RO_MODE&
	 *
	 * AGGR_formId=PIUGREEN
	 * AGGR_formState=BZ
	 * AGGR_draftStr=Bozza
	 *
	 * AGGR_formProg=9
	 * AGGR_sectId=1000
	 * AGGR_pageId=1100
	 *
	 * AGGR_sectXp=//*[@id=%27S1%27]& --> [//*[@id='S1']
	 * AGGR_pageXp=//*[@id=%27S1_P1%27]& --> [//*[@id='S1_P1']
	 *
	 * _command=ACCESS_PAGE or stringhe es "LabelCommand:C_seleziona_ateco;RenderCommand:1140"
	 * AGGR_formType=PiuGreen
	 * AGGR_formDesc=Piu%20Green
	 * AGGR_formName=Nuova%20Domanda
	 * 
	 * @param paramName : valori ammessi {"ACCESS_RO_MODE", "ACCESS_RW_MODE", "DELETE_MODE", "GO_TO_PAGE_MODE", "INSERT_MODE", "LIST_PAGE_MODE", "PRINT_MODE", "UNDEFINED_MODE"}
	 * @param paramVal
	 * @return
	 */
	private static boolean verificaParamApplicativo(String paramName, String paramVal) {
		final String methodName = "verificaParamApplicativo";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";

//		log.debug(logprefix + "BEGIN");
		boolean problema = false;

		String patternValido1 = "^[a-z0-9_ ]*$"; // stringa di sole lettere , numeri e underscore lunga N (con N >= 0)
		Pattern scriptPattern1 = Pattern.compile(patternValido1, Pattern.CASE_INSENSITIVE);
		
		String patternValido2 = "^[0-9]*$"; // stringa di soli numeri, lunga N (con N >= 0)
		Pattern scriptPattern2 = Pattern.compile(patternValido2);
		
		String patternValido3 = "^(?!.*(<|>|=)).*$"; // stringa qualunque esclusi i caratteri < > =
		Pattern scriptPattern3 = Pattern.compile(patternValido3);
		
		String patternValido4 = "^(//\\*\\[@id='+[a-z0-9_]*+'])*$"; // una stringa esattamente di questo tipo  //*[@id='XYX']  dove XYX puo essere fatto solo da numeri e lettere e "_"
		Pattern scriptPattern4 = Pattern.compile(patternValido4, Pattern.CASE_INSENSITIVE);
		
		//String patternValido5 = "^[a-z0-9_ -%.,]*$"; // stringa di sole lettere, numeri con caratteri [, _ - % . spazio] lunga N (con N >= 0)
		String patternValido5 = "^[a-z0-9_ \\-%.,]*$"; // stringa di sole lettere, numeri con caratteri [, _ - % . spazio] lunga N (con N >= 0)
		Pattern scriptPattern5 = Pattern.compile(patternValido5, Pattern.CASE_INSENSITIVE);

		// OperationMode dell'aggregatoreUtil
		String[] aggrModelArray = new String[] {"ACCESS_RO_MODE", "ACCESS_RW_MODE", "DELETE_MODE", "GO_TO_PAGE_MODE", "INSERT_MODE", "LIST_PAGE_MODE", "PRINT_MODE", "UNDEFINED_MODE"};
		
		// Gruppo 1: Stringhe note
		// Parametri AGGR_modeId : deve valere una stringa appartenente ad un insieme limitato di valori
		if (isAcceptedParam(aggrModelArray, paramName) ){
			int a =  Arrays.binarySearch(aggrModelArray, paramVal);
//			log.debug(logprefix + "AGGR_modeId a="+a);
			if(a > 0){
				// parametro  trovato
				log.debug(logprefix + " valore parametro ammesso");
			} else {
				log.warn(logprefix + " valore parametro NON ammesso");
				problema = true;
			}
		}

		// Gruppo 2: Stringhe alfanumeriche con al piu "_"
		// Parametri: _command , AGGR_formId , AGGR_formState ,  AGGR_draftStr
		if (!problema && ( "AGGR_formState".equals(paramName)
						|| "AGGR_draftStr".equals(paramName)
						) ){
			boolean mtc = scriptPattern1.matcher(paramVal).matches();
//			log.debug(logprefix + "secondo blocco mtc="+mtc);
			
			if(!mtc){
				log.warn(logprefix + "REQUEST paramName=[" + paramName+ "] errato");
				problema = true;
			}
		}
		
		// Gruppo 3: numeri 
		// Parametri: AGGR_formProg ,  AGGR_sectId ,  AGGR_pageId
		if (!problema && ( "AGGR_formProg".equals(paramName)
						|| "AGGR_sectId".equals(paramName)
						|| "AGGR_pageId".equals(paramName)
					)){
			boolean mtc2 = scriptPattern2.matcher(paramVal).matches();
//			log.debug(logprefix + "terzo blocco mtc="+mtc2);
			
			if(!mtc2){
				log.warn(logprefix + "REQUEST paramName=[" + paramName+ "] errato");
				problema = true;
			}
		}
		
		// Gruppo 5: Stringhe alfanumeriche generiche
		// Parametri : _command , AGGR_formType ,  AGGR_formDesc , AGGR_formName
		if (!problema && ("_command".equals(paramName) 
						|| "AGGR_formType".equals(paramName)
						|| "AGGR_formDesc".equals(paramName)
						|| "AGGR_formName".equals(paramName)
				)){
			boolean mtc3 = scriptPattern3.matcher(paramVal).matches();
//			log.debug(logprefix + "quarto blocco mtc="+mtc3);
			
			if(!mtc3){
				log.warn(logprefix + "REQUEST paramName=[" + paramName+ "] errato");
				problema = true;
			}
		}
		
		// Gruppo 4 : Stringhe con una determinata struttura
		// Parametri : AGGR_sectXp , AGGR_pageXp=
		if (!problema && ( "AGGR_sectXp".equals(paramName) || "AGGR_pageXp".equals(paramName) )){
			log.debug(logprefix + "quinto blocco paramName=["+paramName+"] paramVal=["+paramVal+"]");
			boolean mtc4 = scriptPattern4.matcher(paramVal).matches();
//			log.debug(logprefix + "quinto blocco mtc="+mtc4);
			
			if(!mtc4){
				log.warn(logprefix + "REQUEST paramName=[" + paramName+ "] errato");
				problema = true;
			}
		}

		// Gruppo 6: Stringhe alfanumeriche con solo alcuni caratteri permessi
		// Parametri:  AGGR_formId
		if (!problema &&  "AGGR_formId".equals(paramName)){
			boolean mtc = scriptPattern5.matcher(paramVal).matches();
//			log.debug(logprefix + "sesto blocco mtc="+mtc);
			
			if(!mtc){
				log.warn(logprefix + "REQUEST paramName=[" + paramName+ "] errato");
				problema = true;
			}
		}
		log.debug(logprefix + "END");
		return problema;
	}
}
