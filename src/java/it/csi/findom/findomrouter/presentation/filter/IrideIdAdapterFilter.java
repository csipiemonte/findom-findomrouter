/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.filter;

import it.csi.findom.findomrouter.presentation.util.Constants;
import it.csi.findom.findomrouter.presentation.vo.UserInfo;
import it.csi.iride2.policy.entity.Identita;
import it.csi.iride2.policy.exceptions.MalformedIdTokenException;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * Inserisce in sessione:
 * <ul>
 * <li>l'identit&agrave; digitale relativa all'utente autenticato.
 * <li>l'oggetto <code>currentUser</code>
 * </ul>
 * Funge da adapter tra il filter del metodo di autenticaizone previsto e la
 * logica applicativa.
 * 
 * @author CSIPiemonte
 */
public class IrideIdAdapterFilter  implements Filter {

	protected static final Logger log = Logger.getLogger(Constants.APPLICATION_CODE + "presentation");

	public static final String IRIDE_ID_SESSIONATTR = "iride2_id";

//	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		log.debug("[IrideIdAdapterFilter::destroy] BEGIN-END");
		
	}

//	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain fchn) throws IOException, ServletException {
		log.debug("[IrideIdAdapterFilter::doFilter] BEGIN-END");
		
		HttpServletRequest hreq = (HttpServletRequest) req;
		
		if (hreq.getSession().getAttribute(Constants.IRIDE_ID_SESSIONATTR) == null) {
			String marker = getToken(hreq);
			log.debug("[IrideIdAdapterFilter::doFilter] marker=[" + marker + "]");
			boolean lavoroInLocale = new Boolean(hreq.getSession().getServletContext().getInitParameter("is_local_deploy")).booleanValue();;
			log.debug("[IrideIdAdapterFilter::doFilter] lavoroInLocale="+lavoroInLocale);

			if(lavoroInLocale){
				//marker = "AAAAAA00A11D000L/DEMO 23/CSI PIEMONTE/INFOCERT_3/20190919113442/16/RPbBkgd7YUWZI22e7YJ1RA==";
				//marker = "AAAAAA00A11B000J/DEMO 21/CSI PIEMONTE/INFOCERT_3/20190920143643/16/KdQKP7UicGUwhiKTWB5Ccg==";
				marker = "AAAAAA00A11H000P/CSI PIEMONTE/DEMO 27/ACTALIS_EU/20200515134107/16/rCWechbOPKN7c+aDy+MkFw==";
			}
			if (marker != null) {
				try {
					Identita identita = new Identita(normalizeToken(marker));
					log.debug("[IrideIdAdapterFilter::doFilter] Inserito in sessione marcatore IRIDE:" + identita);

					hreq.getSession().setAttribute(Constants.IRIDE_ID_SESSIONATTR, identita);
					UserInfo userInfo = new UserInfo();
					userInfo.setNome(identita.getNome());
					userInfo.setCognome(identita.getCognome());
					userInfo.setCodFisc(identita.getCodFiscale());
					userInfo.setIdIride(identita.toString());
					hreq.getSession().setAttribute(Constants.USERINFO_SESSIONATTR, userInfo);

				} catch (MalformedIdTokenException e) {
					log.error("[IrideIdAdapterFilter::doFilter] " + e.toString(), e);
				}
			}
			/*
			 * TODO commentare se si lavora in ambiente locale - BEGIN
			 */
			else {
				log.debug("[IrideIdAdapterFilter::doFilter] hreq.getRequestURI()=" + hreq.getRequestURI());
				// il marcatore deve sempre essere presente altrimenti e' una
				// condizione di errore (escluse le pagine home e di servizio)
				if (mustCheckPage(hreq.getRequestURI())) {
					log.error("[IrideIdAdapterFilter::doFilter] Tentativo di accesso a pagina non di servizio senza token di sicurezza");
					throw new ServletException("Tentativo di accesso a pagina senza token di sicurezza");
				}
			}
			/*
			 * commentare se si lavora in ambiente locale - END
			 */
		}
		fchn.doFilter(req, resp);
	}

//	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		log.debug("[IrideIdAdapterFilter::init] BEGIN-END");
	}
	
	public String getToken(HttpServletRequest httpreq) {
		String marker = (String) httpreq.getHeader(Constants.AUTH_ID_MARKER);
		return marker;
	}
	private String normalizeToken(String token) {
		return token;
	}
	private boolean mustCheckPage(String requestURI) {
		if (requestURI.indexOf("home.do") > -1 || requestURI.indexOf("sessionExpired.do") > -1 || requestURI.indexOf("fatalError") > -1 || requestURI.indexOf("notFoundError") > -1 || requestURI.indexOf("sessionExpired.jsp") > -1
				|| requestURI.indexOf("Logout") > -1)
			return false;
		else
			return true;
	}
}
