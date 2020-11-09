/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.action;

import it.csi.findom.findomrouter.exception.ServiziFindomWebException;
import it.csi.findom.findomrouter.presentation.util.Constants;
import it.csi.findom.findomrouter.presentation.util.StringUtils;

import javax.servlet.http.Cookie;

import org.apache.struts2.ServletActionContext;

public class ConfirmLogoutAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private static final String CLASS_NAME = "ConfirmLogoutAction";
	
	
	private String prosegui;

	public String executeAction() {
		
		final String methodName = "executeAction";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		
		log.debug(logprefix +" BEGIN");
		String result = Constants.ACTION_NOTCONFIRMED;
		
		log.debug(logprefix +" prosegui=["+prosegui+"]");
		
		// il bottone "no, chiudi" viene gestito da CSS, non e' mappato su struts
		
		if(StringUtils.isNotBlank(prosegui) && prosegui.equals(Constants.BUTTON_TXT_CONFERMA)){
			
			try {
				getServiziFindomWeb().insertLogAudit(Constants.CSI_LOG_IDAPPL, "", getUserInfo().getCodFisc(), Constants.CSI_LOG_OPER_LOGOUT, "uscita dal sistema", "");
			} catch (ServiziFindomWebException e) {
				log.error(logprefix +" impossibile scrivere CSI_LOG_AUDIT: "+e);
			}
			
			
			// First step : Invalidate user session
			getServletRequest().getSession().invalidate();
			log.debug(logprefix +" Sessione invalidata");
			
			
			/*
			 * Second step : Invalidate all cookies by, for each cookie received,
			 * overwriting value and instructing browser to deletes it
			 */
			Cookie[] cookies = getServletRequest().getCookies();
			if (cookies != null && cookies.length > 0) {
				for (Cookie cookie : cookies) {
					
					cookie.setValue("-");
					cookie.setMaxAge(0);
					
					if(ServletActionContext.getResponse()!=null){
						ServletActionContext.getResponse().addCookie(cookie);
						log.debug(logprefix +" cookie ["+cookie.getName()+"] sovrascritto");
					}else{
						log.debug(logprefix +" response NULL, cookie ["+cookie.getName()+"] non sovrascritto");
					}
				}
			}
			log.debug(logprefix +" Aggiornata response ");
			
			result = Constants.ACTION_CONFIRMED;
		}
		
		log.debug(logprefix +" END. result=["+result+"]");
		return result;
	}
	
	//GETTES && SETTERS
	public String getProsegui() {
		return prosegui;
	}
	public void setProsegui(String prosegui) {
		this.prosegui = prosegui;
	}

	
}
