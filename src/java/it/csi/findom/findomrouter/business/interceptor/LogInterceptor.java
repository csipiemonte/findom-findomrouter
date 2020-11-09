/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

import it.csi.findom.findomrouter.presentation.util.Constants;
import it.csi.findom.findomrouter.util.Tracer;




public class LogInterceptor implements MethodInterceptor {

	public static final String LOGGER_PREFIX = Constants.APPLICATION_CODE + ".interceptor";

	private static Logger logger = Logger.getLogger(LOGGER_PREFIX);

	public Object invoke(MethodInvocation invocation) throws Throwable{
		String method = invocation.getMethod().getName();
		String className = invocation.getMethod().getDeclaringClass().getSimpleName();
		try {
			Tracer.debug(logger, className, method, "BEGIN");
			Object rval = invocation.proceed();
			return rval;
		}
		finally {
			Tracer.debug(logger, className, method, "END");
		}
	}

}
