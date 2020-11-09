/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business;

import it.csi.findom.findomrouter.presentation.util.Constants;
import it.csi.findom.findomrouter.presentation.util.StringUtils;
import it.csi.findom.findomrouter.presentation.vo.UserInfo;

import org.apache.log4j.Logger;

public class LoginHelper {

	public static final String IRIDE_ID_SESSIONATTR = "iride2_id";
	
	protected static final Logger log = Logger.getLogger(Constants.APPLICATION_CODE + ".business"); 
	private static final String CLASS_NAME = "LoginHelper";
	
	public boolean isUtenteMonitoraggio(UserInfo userInfo) {
		
		final String methodName = "isUtenteMonitoraggio";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		
		log.debug(logprefix + " BEGIN");
		
		boolean isMonitoraggio = false;
		
		if(userInfo!=null){
			
			log.debug(logprefix + "userInfo="+userInfo.toString());
			
			if(StringUtils.equals(userInfo.getCodFisc(), Constants.CF_MONITORAGGIO) || 
			   StringUtils.equals(userInfo.getCodFisc(), Constants.CF_MONITORAGGIO_SYMON) ){
				isMonitoraggio = true;
			}else{
				log.debug(logprefix + "CF non del MONITORAGGIO");
			}
			
		}else{
			log.debug(logprefix + "userInfo NULL");
		}
				
		log.debug(logprefix + " END");
		return isMonitoraggio;
	}
	
}
