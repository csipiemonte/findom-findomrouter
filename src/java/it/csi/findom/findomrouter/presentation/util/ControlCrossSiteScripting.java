/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.util;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class ControlCrossSiteScripting {

	private static final long serialVersionUID = 1L;
	private static final String CLASS_NAME = "ControlCrossSiteScripting";
	protected static final Logger logger = Logger.getLogger(Constants.APPLICATION_CODE + ".presentation.util");
		
	/**
	 * Parsifico una stringa/parametro. Se la lunghezza prima e dopo della parsificazione
	 * cambia allora il parametro non e' buono ossia ho un probabile cross-site script
	 * 
	 * https://www.javacodegeeks.com/2012/07/anti-cross-site-scripting-xss-filter.html
	 * 
	 * @param parametro
	 * @return
	 */
	public static boolean isGoodParameter(String parametro) {
		final String methodName = "isGoodParameter";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
//		logger.debug(logprefix + "BEGIN");

		boolean res = true;
		int paramLength = parametro.length();

//		logger.debug(logprefix + "valore parametro =[" + parametro + "] paramLength ="+ parametro.length());

		// Avoid null characters
		parametro = parametro.replaceAll("", "");
		if (parametro.length() != paramLength) {
			logger.warn(logprefix + "A) valore parametro dopo =[" + parametro + "] parametro.length="+parametro.length());
			res = false;
		}

		// Avoid anything between script tags
		Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>",Pattern.CASE_INSENSITIVE);
		if(res){
			parametro = scriptPattern.matcher(parametro).replaceAll("");
			if (parametro.length() != paramLength) {
				logger.warn(logprefix + "B) valore parametro dopo =[" + parametro + "] parametro.length="+parametro.length());
				res = false;
			}
		}
		// Avoid anything in a src='...' type of expression
		if(res){
			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			parametro = scriptPattern.matcher(parametro).replaceAll("");
			if (parametro.length() != paramLength) {
				logger.warn(logprefix + "C) valore parametro dopo =[" + parametro + "] parametro.length="+parametro.length());
				res = false;
			}
		}
		// Remove any lonesome </script> tag
		if(res){
			scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
			parametro = scriptPattern.matcher(parametro).replaceAll("");
			if (parametro.length() != paramLength) {
				logger.warn(logprefix + "D) valore parametro dopo =[" + parametro + "] parametro.length="+parametro.length());
				res = false;
			}
		}
		// Remove any lonesome <script ...> tag
		if(res){
			scriptPattern = Pattern.compile("<script(.*?)>",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			parametro = scriptPattern.matcher(parametro).replaceAll("");
			if (parametro.length() != paramLength) {
				logger.warn(logprefix + "E) valore parametro dopo =[" + parametro + "] parametro.length="+parametro.length());
				res = false;
			}
		}
		// Avoid eval(...) expressions
		if(res){
			scriptPattern = Pattern.compile("eval\\((.*?)\\)",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			parametro = scriptPattern.matcher(parametro).replaceAll("");
			if (parametro.length() != paramLength) {
				logger.warn(logprefix + "F) valore parametro dopo =[" + parametro + "] parametro.length="+parametro.length());
				res = false;
			}
		}
		// Avoid expression(...) expressions
		if(res){
			scriptPattern = Pattern.compile("expression\\((.*?)\\)",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			parametro = scriptPattern.matcher(parametro).replaceAll("");
			if (parametro.length() != paramLength) {
				logger.warn(logprefix + "G) valore parametro dopo =[" + parametro + "] parametro.length="+parametro.length());
				res = false;
			}
		}
		// Avoid javascript:... expressions
		if(res){
			scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
			parametro = scriptPattern.matcher(parametro).replaceAll("");
			if (parametro.length() != paramLength) {
				logger.warn(logprefix + "H) valore parametro dopo =[" + parametro + "] parametro.length="+parametro.length());
				res = false;
			}
		}
		// Avoid vbscript:... expressions
		if(res){
			scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
			parametro = scriptPattern.matcher(parametro).replaceAll("");
			if (parametro.length() != paramLength) {
				logger.warn(logprefix + "I) valore parametro dopo =[" + parametro + "] parametro.length="+parametro.length());
				res = false;
			}
		}
		// Avoid iframe:... expressions
		if(res){
			scriptPattern = Pattern.compile("iframe", Pattern.CASE_INSENSITIVE);
			parametro = scriptPattern.matcher(parametro).replaceAll("");
			if (parametro.length() != paramLength) {
				logger.warn(logprefix + "J) valore parametro dopo =[" + parametro + "] parametro.length="+parametro.length());
				res = false;
			}
		}
		// Avoid onload= expressions
		if(res){
			scriptPattern = Pattern.compile("onload(.*?)=",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			parametro = scriptPattern.matcher(parametro).replaceAll("");
			if (parametro.length() != paramLength) {
				logger.warn(logprefix + "K) valore parametro dopo =[" + parametro + "] parametro.length="+parametro.length());
				res = false;
			}
		}
		
		if(res){ // aggiunto da Pochettino
			scriptPattern = Pattern.compile("img src",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			parametro = scriptPattern.matcher(parametro).replaceAll("");
			if (parametro.length() != paramLength) {
				logger.warn(logprefix + "L) valore parametro dopo =[" + parametro + "] parametro.length="+parametro.length());
				res = false;
			}
		}
		if(res){ // aggiunto da Pochettino
			scriptPattern = Pattern.compile("a href",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			parametro = scriptPattern.matcher(parametro).replaceAll("");
			if (parametro.length() != paramLength) {
				logger.warn(logprefix + "M) valore parametro dopo =[" + parametro + "] parametro.length="+parametro.length());
				res = false;
			}
		}
		if(res){ // aggiunto da Pochettino
			scriptPattern = Pattern.compile("onmouse",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			parametro = scriptPattern.matcher(parametro).replaceAll("");
			if (parametro.length() != paramLength) {
				logger.warn(logprefix + "N) valore parametro dopo =[" + parametro + "] parametro.length="+parametro.length());
				res = false;
			}
		}
//		logger.debug(logprefix + "END");
		return res;
	}
}
