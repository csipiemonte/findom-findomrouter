/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.interceptor;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.ParameterNameAware;
import com.opensymphony.xwork2.interceptor.ParametersInterceptor;
import com.opensymphony.xwork2.util.ValueStack;

import it.csi.findom.findomrouter.presentation.util.Constants;
import it.csi.findom.findomrouter.presentation.util.XssUtil;
import it.csi.findom.findomrouter.util.Tracer;


public class XssInterceptor extends ParametersInterceptor {

	protected static Logger LOG = Logger.getLogger(Constants.APPLICATION_CODE);

	private static final long serialVersionUID = 1;


	/*
	 * (non-Javadoc)
	 * @see com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	public String intercept(ActionInvocation invocation) throws Exception {
		final String method = "intercept";
		Tracer.debug(LOG, getClass().getName(), method, "BEGIN");
		try{
			ActionContext ac = invocation.getInvocationContext();
			Object action = invocation.getAction();
			Map<String, Object> parameters = ac.getParameters();

			if (LOG.isDebugEnabled()) {
				Tracer.debug(LOG, getClass().getName(), method, "Setting params " + getParameterLogMap(parameters));
			}
			
			if (parameters != null) {
				try {
					ValueStack stack = ac.getValueStack();
					setParameters(action, stack, parameters);

				} catch (Exception e){
					Tracer.error(LOG, getClass().getName(), method, "Exception: " + e);
				}
			}

			return invocation.invoke();
		}
		finally{
			Tracer.debug(LOG, getClass().getName(), method, "END");
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.opensymphony.xwork2.interceptor.ParametersInterceptor#setParameters(java.lang.Object, com.opensymphony.xwork2.util.ValueStack, java.util.Map)
	 */
	protected void setParameters(Object action, ValueStack stack, final Map parameters) {
		final String method = "setParameters";

		ParameterNameAware parameterNameAware = (action instanceof ParameterNameAware) ? (ParameterNameAware) action : null;

		Map params =new TreeMap(parameters);


		for (Iterator iterator = params.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			String name = entry.getKey().toString();

			boolean acceptableName = acceptableName(name) && (parameterNameAware == null || parameterNameAware.acceptableParameterName(name));

			if (acceptableName) {
				Object value = entry.getValue();
				try {
					if(value instanceof String[]){
						String [] v = (String[]) value;
						for (int i = 0; i < v.length; i++) {
							try{
								v[i] = XssUtil.cleanXSS(v[i]); 
							}
							catch(Exception e){
								Tracer.warn(LOG, getClass().getName(), method,  "loaging xssUtil error");
							}
						}
						stack.setValue(name, v);
					}
					else{
						stack.setValue(name, value);
					}
				} catch (RuntimeException e) {
					Tracer.error(LOG, getClass().getName(), method,  "RuntimeException on "  + name + "' on '" + action.getClass() + ": " + e.getMessage());
				}
			}
		}
	}



	private String getParameterLogMap(Map parameters) {
		if (parameters == null) {
			return "NONE";
		}
		StringBuffer logEntry = new StringBuffer();
		for (Iterator paramIter = parameters.entrySet().iterator(); paramIter.hasNext();) {
			Map.Entry entry = (Map.Entry) paramIter.next();
			logEntry.append(String.valueOf(entry.getKey()));
			logEntry.append(" => ");
			if (entry.getValue() instanceof Object[]) {
				Object[] valueArray = (Object[]) entry.getValue();
				logEntry.append("[ ");
				for (int indexA = 0; indexA < (valueArray.length - 1); indexA++) {
					Object valueAtIndex = valueArray[indexA];
					logEntry.append(valueAtIndex);
					logEntry.append(String.valueOf(valueAtIndex));
					logEntry.append(", ");
				}
				logEntry.append(String.valueOf(valueArray[valueArray.length - 1]));
				logEntry.append(" ] ");
			} else {
				logEntry.append(String.valueOf(entry.getValue()));
			}
		}

		return logEntry.toString();
	}


}
