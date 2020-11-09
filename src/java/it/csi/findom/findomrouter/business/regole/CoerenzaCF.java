/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.regole;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import it.csi.findom.findomrouter.presentation.vo.StatusInfo;
import it.csi.findom.findomrules.business.RegoleExecutionResult;
import it.csi.findom.findomrules.business.RegoleTemplate;
import it.csi.findom.findomrules.dto.ParametriRegola;
import it.csi.findom.findomrules.dto.Regola;
import it.csi.findom.findomrules.dto.VistaDomandeGraduatoria;

public class CoerenzaCF extends RegoleTemplate {
	
	// Ricordarsi di mettere bean in serviziFindomWebDao-beans.xml
	
	private static final Logger logger = Logger.getLogger("findomrouter");
	private static NamedParameterJdbcTemplate template;
	
	public static final String ERROR_OUT = "Impossibile creare una nuova domanda.<br/>Tipologia beneficiario scelta non coerente con codice fiscale dell'impresa/Ente/Persona fisica selezionata."; 

	@Override
	protected RegoleExecutionResult executeRegola(VistaDomandeGraduatoria arg0, Regola arg1, Map arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected RegoleExecutionResult executeRegolaIns(Regola regola, Map parametri) {
		logger.debug("[CoerenzaCF::executeRegolaIns] BEGIN");
		RegoleExecutionResult result = new RegoleExecutionResult();
		
		boolean res = false;
		StatusInfo statusInfo = (StatusInfo)parametri.get("statusInfo");
		logger.debug("[CoerenzaCF::executeRegolaIns] statusInfo="+statusInfo);
		
		String tipologiaBeneficiario = (String)parametri.get("idTipologiaBeneficiario");
		logger.debug("[CoerenzaCF::executeRegolaIns] tipologiaBeneficiario="+tipologiaBeneficiario);
		
		String stereotipo = "";
		
		if(statusInfo.getCodFiscaleBeneficiario().length()==11){
			stereotipo = "IM";
		}else if(statusInfo.getCodFiscaleBeneficiario().length()==16){
			stereotipo = "PF";
		}
		logger.debug("[CoerenzaCF::executeRegolaIns] stereotipo="+stereotipo);

		MapSqlParameterSource params = new MapSqlParameterSource();
		
		String query = "SELECT count(id_tipol_beneficiario)" +
				" FROM findom_d_tipol_beneficiari "+ 
				" where cod_stereotipo= :stereotipo "+
				" and id_tipol_beneficiario = :idTipologiaBeneficiario";
		
		logger.debug("[CoerenzaCF::executeRegolaIns] query="+query);
		
		logger.debug("[CoerenzaCF::executeRegolaIns] template="+template);
		try {
			Integer idTipologiaBeneficiario = Integer.parseInt(tipologiaBeneficiario);
			
			params.addValue("stereotipo", stereotipo , java.sql.Types.VARCHAR);
			params.addValue("idTipologiaBeneficiario", idTipologiaBeneficiario , java.sql.Types.INTEGER);
			
			int count = template.queryForInt(query, params);
			logger.debug("[CoerenzaCF::executeRegolaIns] count="+count);
			
			if(count >0 ) {
				res = true;
			} else {
				Vector inErrorMessages = new Vector<String>();
				inErrorMessages.addElement(ERROR_OUT);
				result.addErrorMessages(inErrorMessages);
			}
		} catch (Exception e) {
			
			logger.error("[CoerenzaCF::executeRegolaIns] - Exception : Errore occorso durante l'esecuzione del metodo:" + e, e);
			
		}
		
		result.setExecutionSucceded(res);
		
		logger.debug("[CoerenzaCF::RegoleExecutionResult] END");
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
		CoerenzaCF.template = template;
	}

}
