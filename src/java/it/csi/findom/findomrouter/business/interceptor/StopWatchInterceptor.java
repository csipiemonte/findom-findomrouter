/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import it.csi.findom.findomrouter.presentation.util.Constants;
import it.csi.util.performance.StopWatch;


public class StopWatchInterceptor implements MethodInterceptor {

	private StopWatch stopWatch = null; 
	public static final String LOGGER_PREFIX = Constants.APPLICATION_CODE;
	
	
	public Object invoke(MethodInvocation invocation) throws Throwable {
		String method = invocation.getMethod().getName();
		String className = invocation.getMethod().getDeclaringClass().getSimpleName();
		try {
			stopWatch = new StopWatch(LOGGER_PREFIX);
			stopWatch.start();
			Object rval = invocation.proceed();
			return rval;
		}
		finally {
			stopWatch.stop();
			stopWatch.dumpElapsed(className, method, "invocazione servizio [" +LOGGER_PREFIX+"]::["  + method + "]", "(Interceptor)");
			
		}
	}

}
