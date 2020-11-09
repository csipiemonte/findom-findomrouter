/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.regole;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import it.csi.findom.findomrouter.presentation.vo.StatusInfo;
import it.csi.findom.findomrouter.presentation.vo.UserInfo;
import it.csi.findom.findomrules.business.RegoleExecutionResult;
import it.csi.findom.findomrules.business.RegoleTemplate;
import it.csi.findom.findomrules.dto.ParametriRegola;
import it.csi.findom.findomrules.dto.Regola;
import it.csi.findom.findomrules.dto.VistaDomandeGraduatoria;

public class CoerenzaCFBeneficiarioCreatore extends RegoleTemplate {
	
	// Ricordarsi di mettere bean in serviziFindomWebDao-beans.xml
	private static final Logger logger = Logger.getLogger("findomrouter");
	
	public static final String ERROR_OUT = "Impossibile creare una nuova domanda.<br/>Per il Bando selezionato e' necessario che il Codice Fiscale del soggetto collegato sia lo stesso di quello per cui si presenta domanda."; 

	@Override
	protected RegoleExecutionResult executeRegola(VistaDomandeGraduatoria arg0, Regola arg1, Map arg2) {
		return null;
	}

	@Override
	protected RegoleExecutionResult executeRegolaIns(Regola regola, Map parametri) {
		logger.debug("[CoerenzaCFBeneficiarioCreatore::executeRegolaIns] BEGIN");
		RegoleExecutionResult result = new RegoleExecutionResult();
		
		boolean res = false;
		StatusInfo statusInfo = (StatusInfo)parametri.get("statusInfo");
		logger.debug("[CoerenzaCFBeneficiarioCreatore::executeRegolaIns] statusInfo="+statusInfo);
		

		UserInfo userInfo = (UserInfo)parametri.get("userInfo");
		logger.debug("[CoerenzaCFBeneficiarioCreatore::executeRegolaIns] userInfo="+userInfo);
		
		if(!StringUtils.equals(userInfo.getCodFisc().toLowerCase(), statusInfo.getCodFiscaleBeneficiario().toLowerCase())) {
			
			Vector inErrorMessages = new Vector<String>();
			inErrorMessages.addElement(ERROR_OUT);
			result.addErrorMessages(inErrorMessages);
			
		}else {
			res = true;
		}
		
		result.setExecutionSucceded(res);
		
		logger.debug("[CoerenzaCFBeneficiarioCreatore::RegoleExecutionResult] END");
		return result;
	}

	@Override
	public List<ParametriRegola> leggiParametri(Integer arg0, Integer arg1) throws Exception {
		return null;
	}

	@Override
	public void usaParametri(List<ParametriRegola> arg0) throws Exception {
		
	}

}
