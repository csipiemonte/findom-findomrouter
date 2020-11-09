/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.action;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.findom.findomrouter.business.servizifindomweb.ServiziFindomWeb;
import it.csi.findom.findomrouter.exception.ServiziFindomWebException;
import it.csi.findom.findomrouter.presentation.util.Constants;
import it.csi.findom.findomrouter.presentation.vo.LayoutInclude;
import it.csi.findom.findomrouter.presentation.vo.StatusInfo;
import it.csi.findom.findomrouter.presentation.vo.UserInfo;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

public class RicaricaHomeAction extends ActionSupport implements ServletRequestAware {

	private static final long serialVersionUID = -2504003487171149951L;
	private static final String CLASS_NAME = "RicaricaHomeAction";
	protected static final Logger log = Logger.getLogger(Constants.APPLICATION_CODE + ".presentation");
	
	protected static LayoutInclude layoutInclude; // Singleton
	private HttpServletRequest request;
	
	// accesso al DB 
	private ServiziFindomWeb serviziFindomWeb;
		
	public String executeAction() throws SystemException, UnrecoverableException, CSIException {
		
		final String methodName = "executeAction";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		log.debug(logprefix +" BEGIN");
		
//		StatusInfo state = getStatus();
		StatusInfo state = (StatusInfo) getServletRequest().getSession().getAttribute(Constants.STATUS_INFO);
		UserInfo userInfo = (UserInfo) getServletRequest().getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
		
		// inizializzo gli include per le parti comuni delle JSP
		if (layoutInclude == null) {
			ServletContext context = ServletActionContext.getServletContext();
			log.debug(logprefix + " layoutInclude null, inizializzaione in corso...");
			setLayoutInclude(new LayoutInclude());
			layoutInclude.setPortalHead(context.getInitParameter("portalHead"));
			layoutInclude.setPortalHeader(context.getInitParameter("portalHeader"));
			layoutInclude.setApplicationHeader(context.getInitParameter("applicationHeader"));
			layoutInclude.setApplicationLinkHelpContact(context.getInitParameter("applicationLinkHelpContact"));
			layoutInclude.setPortalFooter(context.getInitParameter("portalFooter"));
		} else {
			log.debug(logprefix + " layoutInclude gia' inizializzato");
		}
				
		try {
			String te = "Scaduta sessione. L'utente ricarica l'home page, beneficiario vecchio id="+state.getIdSoggettoBeneficiario()+" cf:"+state.getCodFiscaleBeneficiario();
			
			getServiziFindomWeb().insertLogAudit(Constants.CSI_LOG_IDAPPL, "", 
					userInfo.getCodFisc()+" - "+ userInfo.getCognome() + " " + userInfo.getNome(), 
					Constants.CSI_LOG_OPER_RELOAD_HOME, te, "");
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
		
		log.debug(logprefix +" END. ");
		return Constants.ACTION_CONFIRMED;
	}

	// GETTERS && SETTERS
	public static LayoutInclude getLayoutInclude() {
		return layoutInclude;
	}
	public static void setLayoutInclude(LayoutInclude layoutInclude) {
		RicaricaHomeAction.layoutInclude = layoutInclude;
	}
	public ServiziFindomWeb getServiziFindomWeb() {
		return serviziFindomWeb;
	}

	public void setServiziFindomWeb(ServiziFindomWeb serviziFindomWeb) {
		this.serviziFindomWeb = serviziFindomWeb;
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getServletRequest() {
		return this.request;
	}
}

