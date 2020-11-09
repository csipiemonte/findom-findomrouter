/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.action;

import it.csi.csi.wrapper.CSIException;
import it.csi.csi.wrapper.SystemException;
import it.csi.csi.wrapper.UnrecoverableException;
import it.csi.findom.findomrouter.business.LoginHelper;
import it.csi.findom.findomrouter.business.servizifindomweb.ServiziFindomWeb;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.AmministratoriDto;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.ProssimoSportelloAttivoDto;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.StatoEsteroDto;
import it.csi.findom.findomrouter.dto.serviziFindomWeb.VistaSportelliAttiviDto;
import it.csi.findom.findomrouter.exception.ServiziFindomWebException;
import it.csi.findom.findomrouter.presentation.util.Constants;
import it.csi.findom.findomrouter.presentation.util.StringUtils;
import it.csi.findom.findomrouter.presentation.util.VerificaParametri;
import it.csi.findom.findomrouter.presentation.vo.LayoutInclude;
import it.csi.findom.findomrouter.presentation.vo.StatoDomanda;
import it.csi.findom.findomrouter.presentation.vo.StatusInfo;
import it.csi.findom.findomrouter.presentation.vo.UserInfo;
import it.csi.findom.findomrouter.util.SessionUtil;
import it.csi.iride2.policy.entity.Identita;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public abstract class BaseAction extends ActionSupport
implements Preparable, SessionAware, ServletRequestAware, ServletResponseAware {
	
	private static final long serialVersionUID = 1L;
	
	protected static final Logger log = Logger.getLogger(Constants.APPLICATION_CODE + ".presentation");
	private static final String CLASS_NAME = "BaseAction";
	protected static String urlRouting; // Singleton

	protected static LayoutInclude layoutInclude; // Singleton
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Map session;
	private Integer areaTematica;
	
	// accesso al DB 
	private ServiziFindomWeb serviziFindomWeb;
	// se showNuovaDomanda = FALSE, non esistono sportelli attivi e viene visualizzato un msg
	private String showNuovaDomanda = "true";
	private String prossimoSportelloAttivo = null;
	
	@Override
	public void prepare() throws Exception {
		final String methodName = "prepare";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";

		log.info(logprefix + " BEGIN");
		
		try {
			log.info(logprefix + "do nothing...");
		} catch (Exception e) {
			log.error(logprefix + "Exception " + e);
			throw e;
		} finally {
			log.info(logprefix + " END");
		}
	}
	
	@Override
	public String execute() throws Exception {
		
		final String methodName = "execute";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		
		log.info(logprefix + " BEGIN");
		
		LoginHelper loginHelper = new LoginHelper();

		// inizializzo gli include per le parti comuni delle JSP
		if (layoutInclude == null) {
			ServletContext context = ServletActionContext.getServletContext();
			log.info(logprefix + " layoutInclude null, inizializzaione in corso...");
			setLayoutInclude(new LayoutInclude());
			layoutInclude.setPortalHead(context.getInitParameter("portalHead"));
			layoutInclude.setPortalHeader(context.getInitParameter("portalHeader"));
			layoutInclude.setApplicationHeader(context.getInitParameter("applicationHeader"));
			layoutInclude.setApplicationLinkHelpContact(context.getInitParameter("applicationLinkHelpContact"));
			layoutInclude.setPortalFooter(context.getInitParameter("portalFooter"));
		} else {
			log.info(logprefix + " layoutInclude gia' inizializzato");
		}
	
		if (urlRouting == null) {
			ServletContext context = ServletActionContext.getServletContext();
			setUrlRouting(context.getInitParameter("urlRouting"));
						
		} else {
			log.info(logprefix + " urlRouting gia' inizializzato a:" +urlRouting);
		}
		
		boolean lavoroInLocale = new Boolean(ServletActionContext.getServletContext().getInitParameter("is_local_deploy")).booleanValue();
		log.info(logprefix + "lavoroInLocale = " + lavoroInLocale);
		
		log.info(logprefix + "areaTematica = " + areaTematica);
		log.debug(logprefix + "  areaTematica/canale in sessione="+getAreaTematica());
	
	
		// per Vulnerability TEST , parsifico parametri in request per evidenziare eventuali anomalie
		long start = System.currentTimeMillis();
		boolean exitWithError = VerificaParametri.controllaParamInReq(getServletRequest());
		long elapsedTimeMillis = System.currentTimeMillis()-start;
		
		log.info(logprefix + "Parametri in request sospetti : exitWithError="+exitWithError + ", elapsedTimeMillis="+elapsedTimeMillis+" ms");
		if(exitWithError){
			log.error(logprefix + "riscontrati errori nei parametri dell'aggregatore");

			try {
				getServiziFindomWeb().insertLogAudit(Constants.CSI_LOG_IDAPPL, "", getUserInfo().getCodFisc()+" - "+ getUserInfo().getCognome() + " " + getUserInfo().getNome(), 
						Constants.CSI_LOG_WRONGPARAM, "probabile manomissione parametri in request", "");
				
			} catch (ServiziFindomWebException e) {
				log.error(logprefix +" impossibile scrivere CSI_LOG_AUDIT: "+e);
			}

			return "badRequestParam";
		}
		
		if (!SessionUtil.existsKey(getServletRequest(), Constants.SESSION_KEY_INIT_CP, Constants.FLAG_TRUE, false)) {
			
			if (!lavoroInLocale) {
				
				// recupera l'oggetto dalla sessione (ce lo ha messo il filtro IrideIdAdapterFilter)
				log.info(logprefix + " userInfo in sessione=[" + getUserInfo() + "]");
				
				try{
					serviziFindomWeb.insertLogAudit(Constants.CSI_LOG_IDAPPL, "", 
													getUserInfo().getCodFisc()+" - "+ getUserInfo().getCognome() + " " + getUserInfo().getNome(),
													Constants.CSI_LOG_OPER_LOGIN, "accesso al sistema", getUserInfo().getIdIride());
				} catch (ServiziFindomWebException e) {
					log.error(logprefix +" impossibile scrivere CSI_LOG_AUDIT:"+e);
				}
	
				//PRTMTR80A01L219F/OPERATORI/MONITORAGGIO or SYMGNT80A01L219B
				boolean isMonitoraggio = loginHelper.isUtenteMonitoraggio(getUserInfo());
				
				if(isMonitoraggio){
					log.info(logprefix + " END - monitoraggio");
					getUserInfo().setRuolo(Constants.RUOLO_MON);
					return "monitoraggio";
				}

				// verifica RUOLO
				verificaRuolo(getUserInfo().getCodFisc());

				getServletRequest().getSession().setAttribute(Constants.SESSION_KEY_INIT_CP, Constants.FLAG_TRUE);
				
			} else {
				// LAVORO In locale
				log.info(logprefix + " LAVORO In locale");
				//
				if(getServletRequest().getSession() != null){
					getServletRequest().getSession().setAttribute(Constants.SESSION_KEY_INIT_CP, Constants.FLAG_TRUE);
				}else{
					log.info(logprefix + " getSession NULL");
				}
				log.info(logprefix + " fakeCurrentUser");
				Identita fakeCurrentUser = accessoSimulatoIride(getServletRequest().getSession());
				getServletRequest().getSession().setAttribute("IRIDE_ID_SESSIONATTR", fakeCurrentUser);

				// verifica RUOLO
				verificaRuolo(getUserInfo().getCodFisc());
				
			}

			//setta informazioni iniziali necessarie al template
			initStatusInfo();
				
		} else {
			log.info(logprefix + " utilizzo la sessione in request .");
			log.info(logprefix + "userInfo= "+getUserInfo());
			
			log.info(logprefix + "status= "+getStatus().toString());
			
			// TODO .......
			if(getStatus()==null){
				log.info(logprefix + "status NULL "); // questo non dovrebbe succedere mai...
				return "sessionError";
			}else{
				log.info(logprefix + "status IdSoggettoCollegato = "+getStatus().getIdSoggettoCollegato());
				if(getStatus().getIdSoggettoCollegato()==null ){
					// sessione scaduta!!!
					log.info(logprefix + "status IdSoggettoCollegato NULL ");
					return "sessionError";
				} 
			}
			
		}
		
		// verifico ogni volta che passo di qui perche uno sportello potrebbe aprirsi all'improvviso
//		verificaSportelliAttivi(getAreaTematica());  //TODO : rimuovere da altrove!!!!!!
		
		aggiornaContextSportello();
		
		String result = new String();
		try {
			result = executeAction();
		} catch (SystemException e){
			log.error(logprefix + " SystemException: " + e);
			return ERROR;
		} catch (UnrecoverableException e){
			log.error(logprefix + " UnrecoverableException: " + e);
			return ERROR;
		} catch (CSIException e){
			log.error(logprefix + " CSIException: " + e);
			return ERROR;
		} catch (Exception e){
			log.error(logprefix + " Exception: " + e);
			return ERROR;
		} catch (Throwable e) {
			log.error(logprefix + " Throwable: " + e);
			return ERROR;
		} finally {
			log.info(logprefix + " END");
		}
		return result;
	}
	
	
	/**
	 * Verifico quale ruolo ha l'utente collegato
	 * @param codFisc
	 * @throws ServiziFindomWebException
	 */
	private void verificaRuolo(String codFisc) throws ServiziFindomWebException {
		final String methodName = "verificaRuolo";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		
		log.info(logprefix + " BEGIN");
		
		// verifico se l'utete collegato e' Amministratore
		AmministratoriDto amministratore = serviziFindomWeb.getAmministratoreByCodiceFiscale(getUserInfo().getCodFisc());

		if(amministratore!=null){
			// l'utente collegato e' un amministratore
			// TODO non dovrei verificare che sia un amministratore dell'ente collegato ????
			// e' un superAdministrator????
			log.info(logprefix + " l'utente e' un amministratore, IdAmministratore() =["+amministratore.getIdAmministratore()+"]");
			getUserInfo().setRuolo(Constants.RUOLO_AMM);
	
		} else {
		
			log.info(logprefix + " l'utente non e' un amministratore");
			//l'utente non e' un amministratore, cerco le domande dell'impresa selezionata indipendentemente dal creatore
			// TODO devo verificare che sia un LR
			
			getUserInfo().setRuolo(Constants.RUOLO_NOR);
		
		}
		log.info(logprefix + " END");
	}

	/**
	 * parte delle info verranno verranno settate/sovrascritte dalla classe
	 * DomandaAction in fase di modifica della proposta
	 * e dalla classe GetTemplateAction.java in fase di inserimento proposta
	 * 
	 * @param session
	 * @throws ServiziFindomWebException 
	 */
	private void initStatusInfo() throws ServiziFindomWebException {
		final String methodName = "initStatusInfo";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		
		log.info(logprefix + " BEGIN");

		// N.B: tutti gli attributi dello StatusInfo qui sono nulli (a meno di quelli che dipendono dall'utente loggato)
		// i vari attributi verranno valorizzati in seguito
		
		setStatus(new StatusInfo());

		Map<String, String> statoPropostaMap = new TreeMap<String, String>();
		ArrayList<StatoDomanda> elencoStatiD = getServiziFindomWeb().getStatiDomanda();

		if(elencoStatiD!=null){
			log.info(logprefix + " elencoStatiD.length=" + elencoStatiD.size());
			for (StatoDomanda statoDomanda : elencoStatiD) {
				//log.info(logprefix + " codice=[" + statoDomanda.getCodice()+"], descr=["+statoDomanda.getDescrizione()+"]");
				if(statoDomanda.getDataFineValidita()==null){
					statoPropostaMap.put(statoDomanda.getCodice(), statoDomanda.getDescrizione());
				}else{
					log.info(logprefix + " stato con fine valida impostat, model_state=["+statoDomanda.getCodice()+"]");
				}
			}
		}else{
			log.info(logprefix + " elencoStatiD NULL");
		}
		getStatus().setStatoPropostaMap(statoPropostaMap);
		
		getStatus().setOperatore(getUserInfo().getCodFisc());
		getStatus().setDescrizioneOperatore(getUserInfo().getCognome() + " " + getUserInfo().getNome());
		
		log.info(logprefix + " status iniziale:" + getStatus().toString());

		TreeMap<String, Object> context = new TreeMap<String, Object>();
		context.put(Constants.STATUS_INFO, getStatus());
			
		getServletRequest().getSession().setAttribute(Constants.CONTEXT_ATTR_NAME, context);
		log.info(logprefix + " creato contesto " + Constants.CONTEXT_ATTR_NAME);
		log.info(logprefix + " END");
	}
	
	public abstract String executeAction() throws  SystemException, UnrecoverableException, CSIException, Throwable;
	
	private Identita accessoSimulatoIride(HttpSession session) {
		log.info("[BaseAction::accessoSimulatoIride] BEGIN");

		Identita utenteFake = new Identita();
		
		// Demo23
//		utenteFake.setCodFiscale("AAAAAA00A11D000L");
//		utenteFake.setCognome("CSI PIEMONTE");
//		utenteFake.setIdProvider("CSI_NUOVACA");
//		utenteFake.setLivelloAutenticazione(8);
//		utenteFake.setMac("Aq1hZ+boQpVKecMhmzB3og==");
//		utenteFake.setTimestamp("20160203152754");
//		utenteFake.setNome("DEMO 23");
		
		//Demo27
		utenteFake.setCodFiscale("AAAAAA00A11H000P");
		utenteFake.setCognome("CSI PIEMONTE");
		utenteFake.setIdProvider("ACTALIS_EU");
		utenteFake.setLivelloAutenticazione(16);
		utenteFake.setMac("rCWechbOPKN7c+aDy+MkFw==");
		utenteFake.setTimestamp("20200515134107");
		utenteFake.setNome("DEMO 27");
		
		getUserInfo().setCodFisc(utenteFake.getCodFiscale());
		getUserInfo().setNome(utenteFake.getNome());
		getUserInfo().setRuolo("-");
		getUserInfo().setCognome(utenteFake.getCognome());
		//getUserInfo().setIdIride("AAAAAA00A11D000L/DEMO 23/CSI PIEMONTE/INFOCERT_3/20160203152754/16/Aq1hZ+boQpVKecMhmzB3og==");
//		getUserInfo().setIdIride("AAAAAA00A11B000J/DEMO 21/CSI PIEMONTE/INFOCERT_3/20181213164215/16/drS8GDFl2ULBVenGbaazOw==");
		getUserInfo().setIdIride("AAAAAA00A11H000P/CSI PIEMONTE/DEMO 27/ACTALIS_EU/20200515134107/16/rCWechbOPKN7c+aDy+MkFw==");
		session.setAttribute(Constants.IRIDE_ID_SESSIONATTR, getUserInfo().getIdIride());

		log.info("[BaseAction::accessoSumulatoIride] END");
		return utenteFake;
	}
	
	protected void verificaSportelliAttivi() {
		verificaSportelliAttivi(null);
	}
	
	/**
	 * Verifica se ci sono sportelli attivi, se non ce ne sono 
	 *  - nasconde il form di creazione di una nuova domanda
	 *  - valorizza un messaggio con il prossimo sportello che aprira' 
	 * @param areaTematica
	 */
	protected void verificaSportelliAttivi(Integer areaTematica) {

		final String methodName = "verificaSportelliAttivi";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		log.info(logprefix + " BEGIN, areaTematica="+areaTematica);
		setShowNuovaDomanda("true");

		List<VistaSportelliAttiviDto> listaSportelli = new ArrayList<VistaSportelliAttiviDto>();
		try {
			
			//PK : qui non uso l'area tematica, cerco se esiste un qualsiasi sportello attivo

			listaSportelli = getServiziFindomWeb().getVistaSportelliAttiviByFilter(null, null, getStatus().getSiglaNazioneAzienda(), null);
			if(listaSportelli!=null && listaSportelli.size()>0){
				setShowNuovaDomanda("true");
				log.info(logprefix + " Esistono sportelli attivi: " + listaSportelli.size());

			} else {
				setShowNuovaDomanda("false");
				log.info(logprefix + " Non esistono sportelli attivi." );
				
				determinaProssimoSportelloAttivo();
			}
		} catch (ServiziFindomWebException ex){	
			log.info(logprefix + " Errore verifica sportelli attivi ", ex);
		} 
		
		log.info(logprefix + " END");
	}
	
	protected void determinaProssimoSportelloAttivo() {
		
		final String methodName = "determinaProssimoSportelloAttivo";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		
		log.info(logprefix + " BEGIN");
		
		try {
			setProssimoSportelloAttivo(null);				

			ProssimoSportelloAttivoDto sportello = getServiziFindomWeb().getProssimoSportelloAttivo(getAreaTematica());
			
			if (sportello != null) {
				StringBuffer sb = new StringBuffer();
				
				sb.append("Il prossimo sportello aprirà il ")
				.append(sportello.getDtApertura())
				.append(" alle ore ")
				.append(sportello.getOraApertura())
				.append(" per il bando: ")
				.append(sportello.getDescrizioneBando());
				
				setProssimoSportelloAttivo(sb.toString());
				
				log.info(logprefix + " Messaggio sportelli prossimo sportello attivo: " + sb.toString());
			}
			
		} catch (ServiziFindomWebException ex){	
			setProssimoSportelloAttivo(null);
		} 
		log.info(logprefix + " END");

	}
	
	public Integer getIdStatoIstruttoria(Integer idBando,String codice) throws ServiziFindomWebException {
		final String methodName = "getIdStatoIstruttoria";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";

		log.info(logprefix + " BEGIN");
		Integer idStatoIstruttoriaRI = null;			
		String flagIstruttoriaEsternaFindom = getServiziFindomWeb().getFlagIstruttoriaEsterna(idBando);
		log.info(logprefix +" flagIstruttoriaEsternaFindom vale " + flagIstruttoriaEsternaFindom);
		if (StringUtils.isEmpty(flagIstruttoriaEsternaFindom) || !flagIstruttoriaEsternaFindom.equals("S")){
			//se il bando prevede l'istruttoria su findom recupero l'id dello stato avente codice 'RI'
			idStatoIstruttoriaRI = getServiziFindomWeb().getIdStatoIstruttoriaByCodice(codice);				
		}
		log.info(logprefix +" idStatoIstruttoriaRI vale " + (idStatoIstruttoriaRI == null ? "" : idStatoIstruttoriaRI));
		log.info(logprefix + " END");
		return idStatoIstruttoriaRI;
	}

	private void aggiornaContextSportello() throws ServiziFindomWebException {
		final String methodName = "aggiornaContextSportello";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		
		log.info(logprefix + " BEGIN");
		
		if (getStatus().getNumProposta() != null) {
			String contextSportello = serviziFindomWeb.getIstanzaRoutingBySportello(getStatus().getNumSportello(), Constants.COD_FASE_DOMANDA);
			if(contextSportello!=null){
				getStatus().setContextSportello(contextSportello);
			}
			// l'utente collegato e' un amministratore
			// TODO non dovrei verificare che sia un amministratore dell'ente collegato ????
			// e' un superAdministrator????
			log.info(logprefix + " context sportello =["+contextSportello+"]");
			
		} 
		log.info(logprefix + " END");
	}
	
	public Map<String, String> getMappaStatiEsteri() throws ServiziFindomWebException{
		final String methodName = "getMappaStatiEsteri";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		log.debug(logprefix + " BEGIN");
		
		Map<String, String> statiEsteriMap = new HashMap<>();
		statiEsteriMap = (Map<String, String>)getServletRequest().getSession().getAttribute("mappaStatiEsteri");

		if(statiEsteriMap!=null) {
			log.debug(logprefix + " statiEsteriMap recuperata dalla sessione");
		}else {
			log.debug(logprefix + " recupero da DB gli stati esteri (l'Italia viene esclusa dalla lista) ");
			ArrayList<StatoEsteroDto> statiEsteri = getServiziFindomWeb().getStatoEsteroList(true); 
			statiEsteriMap = new HashMap<>();
			
			for (Iterator<StatoEsteroDto> iterator = statiEsteri.iterator(); iterator.hasNext();) {
				StatoEsteroDto statoEsteroDto = (StatoEsteroDto) iterator.next();
				statiEsteriMap.put(statoEsteroDto.getCodice(), statoEsteroDto.getDescrizione());
			}
			getServletRequest().getSession().setAttribute("mappaStatiEsteri",statiEsteriMap);
			log.debug(logprefix + " statiEsteri messo in sessione");
		}
		log.debug(logprefix + " END");
		return statiEsteriMap;
	}
	
	// GETTERS && SETTERS

	public static LayoutInclude getLayoutInclude() {
		return layoutInclude;
	}

	public static void setLayoutInclude(LayoutInclude layoutInclude) {
		BaseAction.layoutInclude = layoutInclude;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getServletRequest() {
		return this.request;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setSession(Map session) {
		this.session = session;
	}

	public Map getSession() {
		return session;
	}
	
	public StatusInfo getStatus() {
		return (StatusInfo) getServletRequest().getSession().getAttribute(Constants.STATUS_INFO);
	}

	public void setStatus(StatusInfo stato) {
		getServletRequest().getSession().setAttribute(Constants.STATUS_INFO, stato);
	}

	public UserInfo getUserInfo() {
		return (UserInfo) getServletRequest().getSession().getAttribute(Constants.USERINFO_SESSIONATTR);
	}

	public void setUserInfo(UserInfo userInfo) {
		getServletRequest().getSession().setAttribute(Constants.USERINFO_SESSIONATTR, userInfo);
	}
	
	public ServiziFindomWeb getServiziFindomWeb() {
		return serviziFindomWeb;
	}

	public void setServiziFindomWeb(ServiziFindomWeb serviziFindomWeb) {
		this.serviziFindomWeb = serviziFindomWeb;
	}

	public String getShowNuovaDomanda() {
		return showNuovaDomanda;
	}

	public void setShowNuovaDomanda(String showNuovaDomanda) {
		this.showNuovaDomanda = showNuovaDomanda;
	}

	public String getProssimoSportelloAttivo() {
		return prossimoSportelloAttivo;
	}

	public void setProssimoSportelloAttivo(String prossimoSportelloAttivo) {
		this.prossimoSportelloAttivo = prossimoSportelloAttivo;
	}
	public static String getUrlRouting() {
		return urlRouting;
	}

	public static void setUrlRouting(String urlRouting) {
		BaseAction.urlRouting = urlRouting;
	}

	public Integer getAreaTematica() {
		return (Integer)getServletRequest().getSession().getAttribute("areaTematica");
	}

	public void setAreaTematica(Integer areaTematica) {
//		this.areaTematica = areaTematica;
		getServletRequest().getSession().setAttribute("areaTematica", areaTematica);
	}
	
}
