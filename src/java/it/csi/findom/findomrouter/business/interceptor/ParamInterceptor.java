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
import it.csi.findom.findomrouter.util.XmlSerializer;



public class ParamInterceptor implements MethodInterceptor {

	public static final String LOGGER_PREFIX = Constants.APPLICATION_CODE + ".interceptor";

	private static Logger logger = Logger.getLogger(LOGGER_PREFIX);


	/*
	 * (non-Javadoc)
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	public Object invoke(MethodInvocation invocation) throws Throwable {
		String method = invocation.getMethod().getName();
		String className = invocation.getMethod().getDeclaringClass().getSimpleName();
		try {
			if(logger.isDebugEnabled()){
				Object[] temp = invocation.getArguments();
				if(temp != null){
					for (int index=0; index<temp.length; index++){
						try{
							if(temp[index] instanceof byte [])
								Tracer.debug(logger, className, method, "trace param["+index+"] byte []... ");
							else if(temp[index] instanceof String)
								Tracer.debug(logger, className, method, "trace param["+index+"] " + temp[index]);
							else if(temp[index] instanceof Number)
								Tracer.debug(logger, className, method, "trace param["+index+"] " + temp[index]);
							else if(temp[index] instanceof Boolean)
								Tracer.debug(logger, className, method, "trace param["+index+"] " + temp[index]);
							else
								Tracer.debug(logger, className, method, "trace param["+index+"]\n " + XmlSerializer.objectToXml(temp[index]));
						}
						catch (Exception e) {
							Tracer.warn(logger, className, method, "Error in tracing param " + temp[index]);
						}
					}
				}
			}
			Object rval = invocation.proceed();
			return rval;
		} finally {

		}
	}

}
