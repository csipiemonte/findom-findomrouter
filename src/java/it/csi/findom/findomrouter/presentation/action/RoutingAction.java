/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.action;

import it.csi.findom.findomrouter.exception.ServiziFindomWebException;
import it.csi.findom.findomrouter.presentation.util.Constants;
import it.csi.findom.findomrouter.presentation.util.StringUtils;
import it.csi.findom.findomrouter.presentation.vo.StatusInfo;
import it.csi.findom.findomrouter.presentation.vo.UserInfo;
import it.csi.util.performance.StopWatch;
import java.net.URLEncoder;
//import org.apache.commons.lang.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.Action;

public class RoutingAction extends BaseAction {
	
//	String normativaINS;
//	String descBreveBandoINS;
//	String bandoINS;
//	String sportelloINS;
//	String tipologiaBeneficiarioINS;
//	private String idBando;
//	private String statoRichiesta;
	
	String idImpresaEnte; // id dell'impresa selezionata

	public String executeAction() {
		final String method = "executeAction";
		String actionToRet = Action.SUCCESS;
		
		log.debug("[" + getClass().getName()+ "::" + method + "]  "+ "BEGIN");
		if (getUserInfo() == null) {
			// userInfo = getUserInfo();
			log.debug("[" + getClass().getName()+ "::" + method + "]  "+ "riletto userInfo");
			log.debug("[" + getClass().getName()+ "::" + method + "]  "+
					"getRuoloFindom=" + getUserInfo().getRuolo());
		}
		log.debug("[" + getClass().getName()+ "::" + method + "]  "+ "userInfo=" + getUserInfo().toString());

		log.debug("[" + getClass().getName()+ "::" + method + "]  "+ " END return success");
		return actionToRet;
	}
	
	@SuppressWarnings("deprecation")
	public String createDomandaRouting() throws Exception {
		final String method = "createDomandaRouting";
		log.debug("[" + getClass().getName()+ "::" + method + "]  "+ "BEGIN");
		String result = SUCCESS;
		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
		
		try {
//			Integer sportelloINS = Integer.parseInt(getServletRequest().getParameter("sportelloINS"));
//			String istanza = getServiziFindomWeb().getIstanzaRoutingBySportello(sportelloINS,
//					Constants.COD_FASE_DOMANDA);
			
			String istanza = getIstanza("sportello");
			StringBuffer url = new StringBuffer();
              
			url.append("/"+istanza);
			url.append("/createDomandaRouting.do");
			url.append("?");

			appendSessionParametersToUrl(url);

			log.info("[" + getClass().getName()+ "::" + method + "]  "+ "nextUrl= " + url.toString());

			if (url != null) {
				getResponse().sendRedirect(
						getResponse().encodeRedirectURL(url.toString()));
			} 
						
			return result;
			
		} catch (NumberFormatException e) {
			log.error("[" + getClass().getName()+ "::" + method + "]  "+ "Exception " + e);
			addActionError("Parametro sportelloINS non valido");
			return ERROR;
			
		} catch (Exception e) {
			log.error("[" + getClass().getName()+ "::" + method + "]  "+ "Exception " + e);
			addActionError(e.getMessage());
			return ERROR;
		} finally {
			stopWatch.stop();
			stopWatch.dumpElapsed(getClass().getName(), method, "Esecuzione Action", "msg");
			log.debug("[" + getClass().getName()+ "::" + method + "]  "+ "END");
		}
	}
	
	

	@SuppressWarnings("deprecation")
	public String eliminaPropostaRouting() throws Exception {
		final String method = "eliminaPropostaRouting";
		log.debug("[" + getClass().getName()+ "::" + method + "]  "+ "BEGIN");
		String result = SUCCESS;
		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
		try {
			Integer idDomanda = Integer.parseInt(getServletRequest().getParameter("idDomanda"));

//			String istanza = getServiziFindomWeb().getIstanzaRoutingByDomanda(idDomanda,
//					Constants.COD_FASE_DOMANDA);
			
			String istanza = getIstanza("domanda");
			StringBuffer url = new StringBuffer();
              
			url.append("/"+istanza);
			//url.append("/eliminaProposta.do");
			url.append("/eliminaPropostaAction.do");
			
			url.append("?");
			url.append("idDomanda").append("=" + idDomanda); //.append(URLEncoder.encode(getServletRequest().getParameter("idDomanda"), Constants.UTF8));
			appendSessionParametersToUrl(url);
			log.info("[" + getClass().getName()+ "::" + method + "]  "+ "nextUrl= " + url.toString());

			if (url != null) {
				getResponse().sendRedirect(
						getResponse().encodeRedirectURL(url.toString()));
			} 
			
			return result;
		
		} catch (NumberFormatException e) {
			log.error("[" + getClass().getName()+ "::" + method + "]  "+ "Exception " + e);
			addActionError("Parametro idDomanda non valido");
			return ERROR;
			
		} catch (Exception e) {
			log.error("[" + getClass().getName()+ "::" + method + "]  "+ "Exception " + e);
			addActionError(e.getMessage());
			return ERROR;
		} finally {
			stopWatch.stop();
			stopWatch.dumpElapsed(getClass().getName(), method, "Esecuzione Action", "msg");
			log.debug("[" + getClass().getName()+ "::" + method + "]  "+ "END");
		}
	}
	
	@SuppressWarnings("deprecation")
	public String loadDomandaRouting() throws Exception {
		final String method = "loadDomandaRouting";
		log.debug("[" + getClass().getName()+ "::" + method + "]  "+ "BEGIN");
		String result = SUCCESS;
		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
				
		try {
			Integer idDomanda = Integer.parseInt(getServletRequest().getParameter("idDomanda"));

//			String istanza = getServiziFindomWeb().getIstanzaRoutingByDomanda(idDomanda,
//					Constants.COD_FASE_DOMANDA);
			
			String istanza = getIstanza("domanda");
			
			StringBuffer url = new StringBuffer();
              
			url.append("/"+istanza);
			url.append("/loadDomandaRouting.do");
			url.append("?");
			url.append("idDomanda").append("="+idDomanda);//.append(URLEncoder.encode(getServletRequest().getParameter("idDomanda"), Constants.UTF8));
			
			appendSessionParametersToUrl(url);
			log.info("[" + getClass().getName()+ "::" + method + "]  "+ "nextUrl= " + url.toString());

			if (url != null) {
				getResponse().sendRedirect(
						getResponse().encodeRedirectURL(url.toString()));
			} 
			
			return result;
		
		} catch (NumberFormatException e) {
			log.error("[" + getClass().getName()+ "::" + method + "]  "+ "Exception " + e);
			addActionError("Parametro idDomanda non valido");
			return ERROR;
			
		} catch (Exception e) {		
			
			log.error("[" + getClass().getName()+ "::" + method + "]  "+ "Exception " + e);
			addActionError(e.getMessage());
			return ERROR;
			
		} finally {
			stopWatch.stop();
			stopWatch.dumpElapsed(getClass().getName(), method, "Esecuzione Action", "msg");
			log.debug("[" + getClass().getName()+ "::" + method + "]  "+ "END");
		}
	}
	

	@SuppressWarnings("deprecation")
	public String printDomandaRouting() throws Exception {
		final String method = "printDomandaRouting";
		log.debug("[" + getClass().getName()+ "::" + method + "]  "+ "BEGIN");
		String result = SUCCESS;
		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
		try {
			Integer idDomanda = Integer.parseInt(getServletRequest().getParameter("idDomanda"));

//			String istanza = getServiziFindomWeb().getIstanzaRoutingByDomanda(idDomanda, Constants.COD_FASE_DOMANDA);
			
			String istanza = getIstanza("domanda");
			
			StringBuffer url = new StringBuffer();
              
			url.append("/"+istanza);
			url.append("/printDomandaRouting.do");
			url.append("?");
			url.append("idDomanda").append("=" + idDomanda);//.append(URLEncoder.encode(getServletRequest().getParameter("idDomanda"), Constants.UTF8));
			appendSessionParametersToUrl(url);

			log.info("[" + getClass().getName()+ "::" + method + "]  "+ "nextUrl= " + url.toString());

			if (url != null) {
				getResponse().sendRedirect(
						getResponse().encodeRedirectURL(url.toString()));
			} 
			
			return result;
			
		} catch (NumberFormatException e) {
			log.error("[" + getClass().getName()+ "::" + method + "]  "+ "Exception " + e);
			addActionError("Parametro idDomanda non valido");
			return ERROR;
			
		} catch (Exception e) {
			log.error("[" + getClass().getName()+ "::" + method + "]  "+ "Exception " + e);
			addActionError(e.getMessage());
			return ERROR;
		} finally {
			stopWatch.stop();
			stopWatch.dumpElapsed(getClass().getName(), method, "Esecuzione Action", "msg");
			log.debug("[" + getClass().getName()+ "::" + method + "]  "+ "END");
		}
	}

/*
	@SuppressWarnings("deprecation")
	public String printPDFPropostaInviataRouting() throws Exception {
		final String method = "printPDFPropostaInviataRouting";
		log.debug("[" + getClass().getName()+ "::" + method + "]  "+ "BEGIN");
		String result = SUCCESS;
		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
		try {
			Integer idDomanda = Integer.parseInt(getServletRequest().getParameter("idDomanda"));

//			String istanza = getServiziFindomWeb().getIstanzaRoutingByDomanda(idDomanda,
//					Constants.COD_FASE_DOMANDA);
			
			String istanza = getIstanza("domanda");
			
			StringBuffer url = new StringBuffer();
              
			url.append("/"+istanza);
			url.append("/printPDFPropostaInviataRouting.do");
			url.append("?");
			url.append("idDomanda").append("=" + idDomanda); //.append(URLEncoder.encode(getServletRequest().getParameter("idDomanda"), Constants.UTF8));
			appendSessionParametersToUrl(url);

			log.info("[" + getClass().getName()+ "::" + method + "]  "+ "nextUrl= " + url.toString());

			if (url != null) {
				getResponse().sendRedirect(
						getResponse().encodeRedirectURL(url.toString()));
			} 
			
			return result;
			
		} catch (NumberFormatException e) {
			log.error("[" + getClass().getName()+ "::" + method + "]  "+ "Exception " + e);
			addActionError("Parametro idDomanda non valido");
			return ERROR;
			
		} catch (Exception e) {
			log.error("[" + getClass().getName()+ "::" + method + "]  "+ "Exception " + e);
			addActionError(e.getMessage());
			return ERROR;
		} finally {
			stopWatch.stop();
			stopWatch.dumpElapsed(getClass().getName(), method, "Esecuzione Action", "msg");
			log.debug("[" + getClass().getName()+ "::" + method + "]  "+ "END");
		}
	}
*/
    
	@SuppressWarnings("deprecation")
	public String vaiUploadDocFirmatoRouting() throws Exception {
		final String method = "vaiUploadDocFirmatoRouting";
		log.debug("[" + getClass().getName()+ "::" + method + "]  "+ "BEGIN");
		String result = SUCCESS;
		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
		try {
			Integer idDomanda = Integer.parseInt(getServletRequest().getParameter("idDomanda"));

//			String istanza = getServiziFindomWeb().getIstanzaRoutingByDomanda(idDomanda,
//					Constants.COD_FASE_DOMANDA);
			
			String istanza = getIstanza("domanda");
			
			StringBuffer url = new StringBuffer();
              
			url.append("/"+istanza);
			url.append("/vaiUploadDocFirmatoRouting.do");
			url.append("?");
			url.append("idDomanda").append("=" + idDomanda);//.append(URLEncoder.encode(getServletRequest().getParameter("idDomanda"), Constants.UTF8));
			
			appendSessionParametersToUrl(url);

			log.info("[" + getClass().getName()+ "::" + method + "]  "+ "nextUrl= " + url.toString());

			if (url != null) {
				getResponse().sendRedirect(
						getResponse().encodeRedirectURL(url.toString()));
			} 
			
			return result;
		
		} catch (NumberFormatException e) {
			log.error("[" + getClass().getName()+ "::" + method + "]  "+ "Exception " + e);
			addActionError("Parametro idDomanda non valido");
			return ERROR;
			
		} catch (Exception e) {
			log.error("[" + getClass().getName()+ "::" + method + "]  "+ "Exception " + e);
			addActionError(e.getMessage());
			return ERROR;
		} finally {
			stopWatch.stop();
			stopWatch.dumpElapsed(getClass().getName(), method, "Esecuzione Action", "msg");
			log.debug("[" + getClass().getName()+ "::" + method + "]  "+ "END");
		}
	}
	
	@SuppressWarnings("deprecation")
	public String inviaPropostaRouting() throws Exception {
		final String method = "inviaPropostaRouting";
		log.debug("[" + getClass().getName()+ "::" + method + "]  "+ "BEGIN");
		String result = SUCCESS;
		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
		try {
			Integer idDomanda = Integer.parseInt(getServletRequest().getParameter("idDomanda"));
			String istanza = getIstanza("domanda");
			
			StringBuffer url = new StringBuffer();
              
			url.append("/"+istanza);
			url.append("/inviaPropostaRouting.do");
			url.append("?");
			url.append("idDomanda").append("=" + idDomanda);
			//.append(URLEncoder.encode(getServletRequest().getParameter("idDomanda"), Constants.UTF8));
			
			
			appendSessionParametersToUrl(url);

			log.info("[" + getClass().getName()+ "::" + method + "]  "+ "nextUrl= " + url.toString());

			if (url != null) {
				getResponse().sendRedirect(
						getResponse().encodeRedirectURL(url.toString()));
			} 
			
			return result;
			
		} catch (NumberFormatException e) {
			log.error("[" + getClass().getName()+ "::" + method + "]  "+ "Exception " + e);
			addActionError("Parametro idDomanda non valido");
			return ERROR;
			
		} catch (Exception e) {
			log.error("[" + getClass().getName()+ "::" + method + "]  "+ "Exception " + e);
			addActionError(e.getMessage());
			return ERROR;
		} finally {
			stopWatch.stop();
			stopWatch.dumpElapsed(getClass().getName(), method, "Esecuzione Action", "msg");
			log.debug("[" + getClass().getName()+ "::" + method + "]  "+ "END");
		}
	}
	
	@SuppressWarnings("deprecation")
	public String vaiInserisciDocumentiIntegrazioneRouting() throws Exception {
		final String method = "vaiInserisciDocumentiIntegrazioneRouting";
		log.debug("[" + getClass().getName() + "::" + method + "]  " + "BEGIN");
		String result = SUCCESS;
		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
		try {
			Integer idDomanda = Integer.parseInt(getServletRequest().getParameter("idDomanda"));
			Integer idBando = Integer.parseInt(getServletRequest().getParameter("idBando"));

//			String istanza = getServiziFindomWeb().getIstanzaRoutingByDomanda(idDomanda, Constants.COD_FASE_DOMANDA);
			
			String istanza = getIstanza("domanda");
			
			StringBuffer url = new StringBuffer();

			String statoRichiesta = (String) getServletRequest().getParameter("statoRichiesta");
			
			url.append("/" + istanza);
			url.append("/inserisciDocumentiIntegrazioneRouting.do");
			url.append("?");
			url.append("idDomanda").append("=" + idDomanda);//.append(URLEncoder.encode(getServletRequest().getParameter("idDomanda"), Constants.UTF8));
			url.append("&");
			url.append("idBando").append("=" + idBando); // + URLEncoder.encode(idBando.toString(), Constants.UTF8));
			url.append("&");
			url.append("statoRichiesta=" + URLEncoder.encode(statoRichiesta, Constants.UTF8));
			
			appendSessionParametersToUrl(url);

			log.info("[" + getClass().getName() + "::" + method + "]  " + "nextUrl= " + url.toString());

			if (url != null) {
				getResponse().sendRedirect(getResponse().encodeRedirectURL(url.toString()));
			}

			return result;

		} catch (NumberFormatException  e) {
			log.error("[" + getClass().getName()+ "::" + method + "]  "+ "Exception " + e);
			addActionError("Parametro numerico non valido");
			return ERROR;
			
		} catch (Exception e) {
			log.error("[" + getClass().getName() + "::" + method + "]  " + "Exception " + e);
			addActionError(e.getMessage());
			return ERROR;
		} finally {
			stopWatch.stop();
			stopWatch.dumpElapsed(getClass().getName(), method, "Esecuzione Action", "msg");
			log.debug("[" + getClass().getName() + "::" + method + "]  " + "END");
		}
	}
	
	@SuppressWarnings("deprecation")
	public String invalidaPropostaRouting() throws Exception {
		final String method = "invalidaPropostaRouting";
		log.debug("[" + getClass().getName()+ "::" + method + "]  "+ "BEGIN");
		String result = SUCCESS;
		StopWatch stopWatch = new StopWatch(Constants.APPLICATION_CODE);
		try {
			Integer idDomanda = Integer.parseInt(getServletRequest().getParameter("idDomanda"));

//			String istanza = getServiziFindomWeb().getIstanzaRoutingByDomanda(idDomanda,
//					Constants.COD_FASE_DOMANDA);
			
			String istanza = getIstanza("domanda");
			
			StringBuffer url = new StringBuffer();
              
			url.append("/"+istanza);
			//url.append("/eliminaProposta.do");
			url.append("/invalidaPropostaAction.do");
			
			url.append("?");
			url.append("idDomanda").append("=" + idDomanda); //.append(URLEncoder.encode(getServletRequest().getParameter("idDomanda"), Constants.UTF8));
			appendSessionParametersToUrl(url);
			log.info("[" + getClass().getName()+ "::" + method + "]  "+ "nextUrl= " + url.toString());

			if (url != null) {
				getResponse().sendRedirect(
						getResponse().encodeRedirectURL(url.toString()));
			} 
			
			return result;
		
		} catch (NumberFormatException e) {
			log.error("[" + getClass().getName()+ "::" + method + "]  "+ "Exception " + e);
			addActionError("Parametro idDomanda non valido");
			return ERROR;
			
		} catch (Exception e) {
			log.error("[" + getClass().getName()+ "::" + method + "]  "+ "Exception " + e);
			addActionError(e.getMessage());
			return ERROR;
		} finally {
			stopWatch.stop();
			stopWatch.dumpElapsed(getClass().getName(), method, "Esecuzione Action", "msg");
			log.debug("[" + getClass().getName()+ "::" + method + "]  "+ "END");
		}
	}
	
	private void appendSessionParametersToUrl(StringBuffer url) throws Exception, NumberFormatException {

		Gson gson = new GsonBuilder()
	    .disableHtmlEscaping()
	    .create();
		
		String datiIpa =  gson.toJson(getServletRequest().getSession().getAttribute(Constants.SESSION_DATIIPA));
				
		String descBreveBandoINS =  (String)getServletRequest().getParameter("descBreveBandoINS");
		Integer normativaINS = ((String)getServletRequest().getParameter("normativaINS")!=null ? Integer.parseInt((String)getServletRequest().getParameter("normativaINS")) : null);

		Integer bandoINS = ((String)getServletRequest().getParameter("bandoINS")!=null ? Integer.parseInt((String)getServletRequest().getParameter("bandoINS")) : null);
		Integer sportelloINS = ((String)getServletRequest().getParameter("sportelloINS")!=null ? Integer.parseInt((String)getServletRequest().getParameter("sportelloINS")) : null);
		Integer tipologiaBeneficiarioINS = ((String)getServletRequest().getParameter("tipologiaBeneficiarioINS")!=null ? Integer.parseInt((String)getServletRequest().getParameter("tipologiaBeneficiarioINS")) : null);
		
		url.append("&");
		url.append("datiIpa=" + URLEncoder.encode(datiIpa, Constants.UTF8));
		
		UserInfo ui = (UserInfo)getServletRequest().getSession().getAttribute(Constants.USERINFO_SESSIONATTR);

		url.append("&");
		url.append("idIride=" + URLEncoder.encode(ui.getIdIride(), Constants.UTF8));
		url.append("&");
		url.append("ruolo=" + URLEncoder.encode(ui.getRuolo(), Constants.UTF8));
		
		if (normativaINS!=null) {
			url.append("&");
			url.append("normativaINS").append("=" + normativaINS);//.append(URLEncoder.encode(normativaINS, Constants.UTF8));
		}
		if (descBreveBandoINS!=null) {
			url.append("&");
			url.append("descBreveBandoINS").append("=").append(URLEncoder.encode(descBreveBandoINS, Constants.UTF8));
		}
		if (bandoINS!=null) {
			url.append("&");
			url.append("bandoINS").append("=" + bandoINS);//.append(URLEncoder.encode(bandoINS, Constants.UTF8));
		}
		if (sportelloINS!=null) {
			url.append("&");
			url.append("sportelloINS").append("=" + sportelloINS);//.append(URLEncoder.encode(sportelloINS, Constants.UTF8));
		}
		if (tipologiaBeneficiarioINS!=null) {
			url.append("&");
			url.append("tipologiaBeneficiarioINS").append("=" + tipologiaBeneficiarioINS);//.append("=").append(URLEncoder.encode(tipologiaBeneficiarioINS, Constants.UTF8));
		}
		
		StatusInfo statusInfo =  (StatusInfo)getServletRequest().getSession().getAttribute(Constants.STATUS_INFO);

		url.append("&");
		url.append("isbSI").append("=" + statusInfo.getIdSoggettoBeneficiario());//.append(URLEncoder.encode(statusInfo.getIdSoggettoBeneficiario().toString(), Constants.UTF8));
		url.append("&");
		url.append("cfbSI").append("=").append(URLEncoder.encode(statusInfo.getCodFiscaleBeneficiario(), Constants.UTF8));
		url.append("&");
		url.append("iscSI").append("=" + statusInfo.getIdSoggettoCollegato());// .append(URLEncoder.encode(statusInfo.getIdSoggettoCollegato().toString(), Constants.UTF8));
		
		if ( ( statusInfo.getDescrImpresaEnte()!=null && (!StringUtils.isBlank(statusInfo.getDescrImpresaEnte())))){
			url.append("&");
			url.append("dieSI").append("=").append(URLEncoder.encode(statusInfo.getDescrImpresaEnte(), Constants.UTF8));
		}// if -> originale - else da  testare...
//		else{
//			/* TODO: da testare ... Jira: 1332: -2RJira: 1332:step1/5 2R - recupero denominazione beneficiario - */
//			// - findomrouter
//			String denominazioneBeneficiario = getServiziFindomWeb().getDenominazioneByCodiceFiscale(statusInfo.getCodFiscaleBeneficiario());
//			log.info("Test recupero denominazione da db e metto in sessione: " + denominazioneBeneficiario);
//			if (denominazioneBeneficiario!=null && !denominazioneBeneficiario.isEmpty()) {
//				statusInfo.setDescrImpresaEnte(denominazioneBeneficiario);
//				url.append("&");
//				url.append("dieSI")
//						.append("=")
//						.append(URLEncoder.encode(
//								statusInfo.getDescrImpresaEnte(),
//								Constants.UTF8));
//			}
//		}
		setStatus(statusInfo); // TODO: da testare; Jira: 1332
	}
	
	private String getIstanza(String tipo) throws ServiziFindomWebException {
		String istanza = null;
		if(StringUtils.equals(tipo, "sportello")){
			
			Integer sportelloINS = Integer.parseInt(getServletRequest().getParameter("sportelloINS"));
			istanza = getServiziFindomWeb().getIstanzaRoutingBySportello(sportelloINS,	Constants.COD_FASE_DOMANDA);
		
		}else if(StringUtils.equals(tipo, "domanda")){
			
			Integer idDomanda = Integer.parseInt(getServletRequest().getParameter("idDomanda"));
			istanza = getServiziFindomWeb().getIstanzaRoutingByDomanda(idDomanda, Constants.COD_FASE_DOMANDA);
			
		}else {
			log.debug("[RoutingAction::getIstanza] nessun tipo definito");
		}
		
		// Se voglio intercettare URL di chiamata:
		// log.debug("[RoutingAction::getIstanza] RequestURL="+getServletRequest().getRequestURL());
		
		return istanza;
	}
	
}
