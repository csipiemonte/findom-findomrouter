/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.util;

import org.apache.log4j.Logger;

import it.csi.findom.findomrouter.presentation.util.Constants;


public final class MelogranoStdWebLogger {

	static private String APPLICATION_NAME = Constants.APPLICATION_CODE;

	static public final Logger logger = Logger.getLogger(getApplicationName());

	static public void debug(Object obj, String msg){
		logger.debug(obj.getClass().getName() + ": " + msg);
	}

	@SuppressWarnings("rawtypes")
	static public void debug(Class clazz, String methodName, String msg) {
		logger.debug(formatMessage(clazz, methodName, msg));
	}

	static public void info(Object obj, String msg){
		logger.info(obj.getClass().getName() + ": " + msg);
	}

	@SuppressWarnings("rawtypes")
	static public void info(Class clazz, String methodName, String msg) {
		logger.info(formatMessage(clazz, methodName, msg));
	}

	@SuppressWarnings("rawtypes")
	static public void info(Class clazz, String methodName, String msg, Throwable t) {
		logger.info(formatMessage(clazz, methodName, msg), t);
	}

	static public void warn(Object obj, String msg){
		logger.warn(obj.getClass().getName() + ": " + msg);
	}

	@SuppressWarnings("rawtypes")
	static public void warn(Class clazz, String methodName, String msg) {
		logger.warn(formatMessage(clazz, methodName, msg));
	}

	@SuppressWarnings("rawtypes")
	static public void warn(Class clazz, String methodName, String msg, Throwable t) {
		logger.warn(formatMessage(clazz, methodName, msg), t);
	}

	@SuppressWarnings("rawtypes")
	static public void warn(Class clazz, String methodName, Throwable t) {
		logger.warn(formatMessage(clazz, methodName), t);
	}

	static public void error(Object obj, String msg, Throwable e){
		logger.error(obj.getClass().getName() + ": " + msg, e);
	}

	@SuppressWarnings("rawtypes")
	static public void error(Class clazz, String methodName, Throwable t) {
		logger.error(formatMessage(clazz, methodName), t);
	}

	@SuppressWarnings("rawtypes")
	static public void error(Class clazz, String methodName, String msg, Throwable t) {
		logger.error(formatMessage(clazz, methodName, msg), t);
	}

	@SuppressWarnings("rawtypes")
	static private String formatMessage(Class clazz, String methodName, String msg) {
		try {
			return clazz.getSimpleName().concat("::").concat(methodName).concat(" ").concat(msg);
		} catch (Throwable e) {
			return "could not build properly the message [" + msg + "]";
		}
	}

	@SuppressWarnings("rawtypes")
	static private String formatMessage(Class clazz, String methodName) {
		return formatMessage(clazz, methodName, "");
	}

	static private String getApplicationName() {
		return APPLICATION_NAME;
	}

	static public void setApplicationName(String applicationName) {
		APPLICATION_NAME = applicationName;
	}

}
