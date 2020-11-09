/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.regole;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import it.csi.findom.findomrouter.presentation.util.StringUtils;
import it.csi.findom.findomrouter.presentation.vo.StatusInfo;
import it.csi.findom.findomrules.business.RegoleExecutionResult;
import it.csi.findom.findomrules.business.RegoleTemplate;
import it.csi.findom.findomrules.dto.ParametriRegola;
import it.csi.findom.findomrules.dto.Regola;
import it.csi.findom.findomrules.dto.VistaDomandeGraduatoria;

// Porting della regola BeanShell "ENTI_LOCALI_PIEMONTESI"

public class EntiLocaliPiemontesi extends RegoleTemplate {
	
	// Ricordarsi di mettere bean in serviziFindomWebDao-beans.xml
	
	// Se la tipologia di beneficiario e' "COMUNE"
	// oppure "PROVINCIA" 
	// oppure "citta metropolitana"
	// oppure "Ente di Gestione delle Aree protette regionali parco",
	// verifica che il cod fiscale sia presente nella vista findom_v_enti_locali_piemontesi
	// Se la tipologia di beneficiario e' "UNIONI DI COMUNI" non fa nulla
	
	private static final Logger logger = Logger.getLogger("findomrouter");
		
	private static NamedParameterJdbcTemplate template;
	
	public static final String ERROR_OUT = "Impossibile creare una nuova domanda.<br/>Il beneficiario scelto non pu&ograve; presentare domanda per questo bando."; 
	
	@Override
	protected RegoleExecutionResult executeRegola(VistaDomandeGraduatoria arg0, Regola arg1, Map arg2) {
		return null;
	}
	
	@Override
	protected RegoleExecutionResult executeRegolaIns(Regola regola, Map parametri) {
		logger.debug("[EntiLocaliPiemontesi::executeRegolaIns] BEGIN");
		RegoleExecutionResult result = new RegoleExecutionResult();
		
		boolean res = false;
		
//		logger.debug("[EntiLocaliPiemontesi::executeRegolaIns] parametri="+parametri);
		
		StatusInfo statusInfo = (StatusInfo)parametri.get("statusInfo");
//		logger.debug("[EntiLocaliPiemontesi::executeRegolaIns] statusInfo="+statusInfo);

		String codFiscaleBeneficiario = statusInfo.getCodFiscaleBeneficiario();
		logger.debug("[EntiLocaliPiemontesi::executeRegolaIns] codFiscaleBeneficiario="+codFiscaleBeneficiario);
		
		String idTipologiaBeneficiario = (String)parametri.get("idTipologiaBeneficiario");
		logger.debug("[EntiLocaliPiemontesi::executeRegolaIns] idTipologiaBeneficiario="+idTipologiaBeneficiario);
		
		 boolean error = false;
		 
		// 27 = comune
		// 28 = province
		// 29 = citta metropolitana
		// 107 = Ente di Gestione delle Aree protette regionali parco
		if(StringUtils.equals(idTipologiaBeneficiario, "27") 
				|| StringUtils.equals(idTipologiaBeneficiario, "28") 
				|| StringUtils.equals(idTipologiaBeneficiario, "29")
				|| StringUtils.equals(idTipologiaBeneficiario, "107")) {
		
			// il beneficiario puo presentare domanda solo se il suo CF compare nella tabella findom_v_enti_locali_piemontesi
			
			String denominazioneCompleta = "";
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			params.addValue("codFiscaleBeneficiario", codFiscaleBeneficiario , java.sql.Types.VARCHAR);
			
		    String query = "SELECT denominazione_completa FROM findom_v_enti_locali_piemontesi " +
		        			   "WHERE codice_fiscale = :codFiscaleBeneficiario";
		    
		    logger.debug("[EntiLocaliPiemontesi::executeRegolaIns] query="+query);
		    
		    try {
				
				Map<String, Object> item = template.queryForMap(query, params);
				logger.debug("[EntiLocaliPiemontesi::executeRegolaIns] item="+item);
				
				if(item!=null && !item.isEmpty()) {
					denominazioneCompleta = (String) item.get("denominazione_completa");
					logger.debug("[EntiLocaliPiemontesi::executeRegolaIns] denominazioneCompleta="+denominazioneCompleta);
					
					// denominazione non presente nella vista, comune/parco non abilitati a creare domande
			        if (StringUtils.isBlank(denominazioneCompleta)){
			        	error = true;
			        } else {
			        	res = true;
			        }
			        
				} else {
					error = true;
				}

			} catch (EmptyResultDataAccessException e) {
				
				logger.info("[EntiLocaliPiemontesi::executeRegolaIns] - EmptyResultDataAccessException");
				error = true;
				
			} catch (Exception e) {
				
				logger.error("[EntiLocaliPiemontesi::executeRegolaIns] - Exception : Errore occorso durante l'esecuzione del metodo:" + e, e);
				error = true;
			}
		    
		} else if(StringUtils.isBlank(idTipologiaBeneficiario)) {
			logger.error("[EntiLocaliPiemontesi::executeRegolaIns] -idTipologiaBeneficiario nullo");
			error = true;
			
		} else {
			// idTipologiaBeneficiario non nulla, diversa da 27 e 107
			// il beneficiario puo presentare domanda
			logger.debug("[EntiLocaliPiemontesi::executeRegolaIns] -idTipologiaBeneficiario consentito");
			res = true;
		}
		
		 if(error) {
	    	Vector inErrorMessages = new Vector<String>();
			inErrorMessages.addElement(ERROR_OUT);
			result.addErrorMessages(inErrorMessages);
			res = false;
	    }
		 
		result.setExecutionSucceded(res);
		
		logger.debug("[EntiLocaliPiemontesi::executeRegolaIns] END");
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
		EntiLocaliPiemontesi.template = template;
	}


}
