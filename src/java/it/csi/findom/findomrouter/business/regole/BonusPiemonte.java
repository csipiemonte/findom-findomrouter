/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.regole;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import it.csi.findom.findomrouter.presentation.vo.StatusInfo;
import it.csi.findom.findomrules.business.RegoleExecutionResult;
import it.csi.findom.findomrules.business.RegoleTemplate;
import it.csi.findom.findomrules.dto.ParametriRegola;
import it.csi.findom.findomrules.dto.Regola;
import it.csi.findom.findomrules.dto.VistaDomandeGraduatoria;

public class BonusPiemonte extends RegoleTemplate {
	
	// Ricordarsi di mettere bean in serviziFindomWebDao-beans.xml
	
	private static final Logger logger = Logger.getLogger("findomrouter");
		
	private static NamedParameterJdbcTemplate template;
	
	public static final String ERROR_OUT = "Impossibile creare una nuova domanda.<br/>Il beneficiario scelto non pu&ograve; presentare domanda per il bando BonusPiemonte "; 
	public static final String ERROR_OUT2 = "Impossibile creare una nuova domanda.<br/>Come da comunicazione si ricorda che la domanda pu&ograve; essere presentata solo a partire da ";
	public static final String LINK = "<a href=\"https://www.regione.piemonte.it/bonuspiemonte\" target=\"_blank\"> https://www.regione.piemonte.it/bonuspiemonte</a>";
	public static final String ERROR_OUT3 = "Impossibile creare una nuova domanda.<br/>Il beneficiario scelto non pu&ograve; presentare domanda al momento. <br/>Si rimanda alla pagina ("+LINK+") per ulteriori informazioni";
	public static final String ERROR_OUT4 = "Impossibile creare una nuova domanda.<br/>Il beneficiario scelto non pu&ograve; presentare domanda "; 
	
	@Override
	protected RegoleExecutionResult executeRegola(VistaDomandeGraduatoria arg0, Regola arg1, Map arg2) {
		return null;
	}
	
	@Override
	protected RegoleExecutionResult executeRegolaIns(Regola regola, Map parametri) {
		logger.debug("[BonusPiemonte::executeRegolaIns] BEGIN");
		RegoleExecutionResult result = new RegoleExecutionResult();
		
		boolean res = false;
		StatusInfo statusInfo = (StatusInfo)parametri.get("statusInfo");
		logger.debug("[BonusPiemonte::executeRegolaIns] statusInfo="+statusInfo);

		MapSqlParameterSource params = new MapSqlParameterSource();
		
		String query = "SELECT cod_fiscale, partita_iva, dt_inizio, dt_fine, cod_ateco, importo_contributo , iban " +
					   "FROM findom_t_soggetti_bonus_covid c " +
				       "WHERE (UPPER(c.cod_fiscale) = UPPER(:cfBenef) " +
					   "OR UPPER(c.partita_iva) = UPPER(:cfBenef)) " ;
//					   "AND c.dt_inizio <= DATE (now()) " +
//					   "AND c.dt_fine >= DATE (now()) ";

		logger.debug("[BonusPiemonte::executeRegolaIns] query="+query);
		logger.debug("[BonusPiemonte::executeRegolaIns] template="+template);
		
		try {
			
			String cfBenef = statusInfo.getCodFiscaleBeneficiario();
			logger.debug("[BonusPiemonte::executeRegolaIns] cfBenef=["+cfBenef+"]");
			
			params.addValue("cfBenef", cfBenef , java.sql.Types.VARCHAR);
			
			Map<String, Object> item = template.queryForMap(query, params);
			logger.debug("[BonusPiemonte::executeRegolaIns] item="+item);
			
			if(item!=null) {
				Date dataInizio = (Date)item.get("dt_inizio");
				logger.debug("[BonusPiemonte::executeRegolaIns] dataInizio="+dataInizio);
				
				Date dataFine = null;
				if(item.get("dt_fine") != null) {
					dataFine = (Date)item.get("dt_fine");
				}
				logger.debug("[BonusPiemonte::executeRegolaIns] dataFine="+dataFine);
				
				if(dataInizio!=null) {
					Date oggi = new Date();
					
					if(oggi.before(dataInizio)){
						logger.warn("[BonusPiemonte::executeRegolaIns] data di presentazione precedente a quella ammessa ["+dataInizio+"]");
						Vector inErrorMessages = new Vector<String>();
						inErrorMessages.addElement(ERROR_OUT2 + dataInizio);
						result.addErrorMessages(inErrorMessages);
						
					}else {
						res = true;
					}
					
					if(res && dataFine!=null && oggi.after(dataFine)) {
						logger.warn("[BonusPiemonte::executeRegolaIns] data di presentazione successiva a quella ammessa ["+dataFine+"]");
						Vector inErrorMessages = new Vector<String>();
						inErrorMessages.addElement(ERROR_OUT4);
						result.addErrorMessages(inErrorMessages);
						res = false;
					}
					
				}else {
					
					logger.warn("[BonusPiemonte::executeRegolaIns] data inzio nulla");
					Vector inErrorMessages = new Vector<String>();
					inErrorMessages.addElement(ERROR_OUT3);
					result.addErrorMessages(inErrorMessages);

				}
			}

		} catch (EmptyResultDataAccessException e) {
			logger.warn("[BonusPiemonte::executeRegolaIns] codice beneficiario non presente in tabella");
			Vector inErrorMessages = new Vector<String>();
			inErrorMessages.addElement(ERROR_OUT);
			result.addErrorMessages(inErrorMessages);
			
		} catch (Exception e) {
			
			logger.error("[BonusPiemonte::executeRegolaIns] - Exception : Errore occorso durante l'esecuzione del metodo:" + e, e);
			
		}
		
		result.setExecutionSucceded(res);
		
		logger.debug("[BonusPiemonte::RegoleExecutionResult] END");
		return result;
	}

	@Override
	public List<ParametriRegola> leggiParametri(Integer arg0, Integer arg1) throws Exception {
		return null;
	}

	@Override
	public void usaParametri(List<ParametriRegola> arg0) throws Exception {
	}

	public static NamedParameterJdbcTemplate getTemplate() {
		return template;
	}

	public static void setTemplate(NamedParameterJdbcTemplate template) {
		BonusPiemonte.template = template;
	}

}
