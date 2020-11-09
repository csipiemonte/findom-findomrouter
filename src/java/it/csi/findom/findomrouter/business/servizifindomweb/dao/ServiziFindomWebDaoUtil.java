/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.servizifindomweb.dao;

import it.csi.findom.findomrouter.exception.ServiziFindomWebException;
import it.csi.findom.findomrouter.presentation.util.Constants;
import it.csi.findom.findomrouter.presentation.util.StringUtils;

import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class ServiziFindomWebDaoUtil {

	private static Properties selectStatements = null;
	private static final String CLASS_NAME = "ServiziFindomWebDaoUtil";
	
	protected static final Logger logger = Logger.getLogger(Constants.APPLICATION_CODE + ".DaoUtil");
	
	public ServiziFindomWebDaoUtil() throws ServiziFindomWebException{
		if(selectStatements == null){
			init();
		}
	}
	
	// metodi di connessione e di inizializzazione
	private static synchronized void init() throws ServiziFindomWebException{
		selectStatements = new Properties();
		logger.debug("[ServiziFindomWebDaoUtil::init] BEGIN");
		try {
			ResourceBundle rb = ResourceBundle.getBundle("statementsServiziFindomWeb");
			for (Enumeration temp = rb.getKeys(); temp.hasMoreElements();) {
				String onekey = new String((String) temp.nextElement());
				selectStatements.setProperty(onekey, (String) rb.getObject(onekey));
			}
		} catch (MissingResourceException mre) {
			logger.error("[ServiziFindomWebDaoUtil::init] - Errore CSI occorso durante l'esecuzione del metodo:" + mre, mre);
			throw new ServiziFindomWebException("Exception while getting resources : statementsServiziFindomWeb.properties", mre);
		}
		logger.debug("[ServiziFindomWebDaoUtil::init] END");
	}
	
	
	/**
	 * Compongo le condizioni da appendere alla query
	 * @param query
	 * @param idSoggBeneficiario
	 * @param idSoggCreatore
	 * @param ruolo
	 * @param idAreaTematica 
	 * @param normativa
	 * @param descBreveBando
	 * @param bando
	 * @param sportello
	 * @param statoDomanda
	 * @param numDomanda
	 * @return
	 */
	public static String composeSearchDomandeQuery(String query,
			Integer idSoggBeneficiario, Integer idSoggCreatore, String ruolo,
			Integer idAreaTematica, String normativa, String descBreveBando, String bando,
			String sportello, String statoDomanda, String numDomanda) {
		
		final String methodName = "composeSearchDomandeQuery";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		logger.debug(logprefix + " BEGIN");
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(" id_soggetto_beneficiario="+idSoggBeneficiario);
		
		if(idSoggCreatore!=null && StringUtils.isNotBlank(ruolo) && (Constants.RUOLO_NOR).equals(ruolo)){
			// vincolo il soggetto creatore solo se l'utente collegato non e' Amministratore o Legale Rappresentante
			sb.append(" AND id_soggetto_creatore="+idSoggCreatore);
		}
		
		if(StringUtils.isNotBlank(numDomanda)){
			// questa condizione esclude tutte le altre
			sb.append(" AND id_domanda="+numDomanda);
			
		}else{
			
			if(StringUtils.isNotBlank(normativa)){ // non uso normativa ma id_normativa
				sb.append(" AND id_normativa="+normativa);
			}
			
			boolean usatoIdBando = false; // questo per non mettere due volte la condizione sull'id_bando
			if(StringUtils.isNotBlank(bando) ){
				sb.append(" AND id_bando="+bando);
				usatoIdBando = true;
			}
			
			if(StringUtils.isNotBlank(descBreveBando) && !usatoIdBando){
				//sb.append(" AND lower(descr_bando) like '%"+descBreveBando.toLowerCase()+"%'");
				sb.append(" AND id_bando="+descBreveBando);
			}
			
			if(StringUtils.isNotBlank(sportello)){
				sb.append(" AND id_sportello_bando = "+sportello);
			}
			
			if(StringUtils.isNotBlank(statoDomanda)){
				sb.append(" AND cod_stato_domanda = '"+statoDomanda+"' ");
			}
			
			if(idAreaTematica!=null && idAreaTematica>0){
				sb.append(" AND id_area_tematica = "+idAreaTematica);
			}
		}
		
		String condizione = sb.toString();
		logger.debug(logprefix + " condizione="+condizione);
		
		String newQuery = query.replace("#condizione#", condizione);
		
		logger.debug(logprefix + " END");
		return newQuery;
	}
	
	/**
	 * Compongo le condizioni da appendere alla query
	 * @param query
	 * @param idSoggBeneficiario
	 * @param idSoggCreatore
	 * @param ruolo
	 * @param normativa
	 * @param descBreveBando
	 * @param bando
	 * @param sportello
	 * @param statoDomanda
	 * @param numDomanda
	 * @return
	 */
	public static String composeSearchStatiDomandeQuery(String query,
			Integer idSoggBeneficiario, Integer idSoggCreatore, String ruolo,
			String normativa, String descBreveBando, String bando,
			String sportello) {
		
		final String methodName = "composeSearchDomandeQuery";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		logger.debug(logprefix + " BEGIN");
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(" id_soggetto_beneficiario="+idSoggBeneficiario);
		
		if(idSoggCreatore!=null && StringUtils.isNotBlank(ruolo) && (Constants.RUOLO_NOR).equals(ruolo)){
			// vincolo il soggetto creatore solo se l'utente collegato non e' Amministratore o Legale Rappresentante
			sb.append(" AND id_soggetto_creatore="+idSoggCreatore);
		}
		
		if(StringUtils.isNotBlank(normativa)){ // non uso normativa ma id_normativa
			sb.append(" AND id_normativa="+normativa);
		}
		
		boolean usatoIdBando = false; // questo per non mettere due volte la condizione sull'id_bando
		if(StringUtils.isNotBlank(bando) ){
			sb.append(" AND id_bando="+bando);
			usatoIdBando = true;
		}
		
		if(StringUtils.isNotBlank(descBreveBando) && !usatoIdBando){
			//sb.append(" AND lower(descr_bando) like '%"+descBreveBando.toLowerCase()+"%'");
			sb.append(" AND id_bando="+descBreveBando);
		}
		
		if(StringUtils.isNotBlank(sportello)){
			sb.append(" AND id_sportello_bando = "+sportello);
		}	
		
		String condizione = sb.toString();
		logger.debug(logprefix + " condizione="+condizione);
		
		String newQuery = query.replace("#condizione#", condizione);
		
		logger.debug(logprefix + " END");
		return newQuery;
	}
	
	// GETTERS && SETTERS
	public static Properties getSelectStatements() throws ServiziFindomWebException{
		if (null == selectStatements) {
			init();
		}
		return selectStatements;
	}
	
	public static void setSelectStatements(Properties selectStatements) {
		ServiziFindomWebDaoUtil.selectStatements = selectStatements;
	}

	
}
