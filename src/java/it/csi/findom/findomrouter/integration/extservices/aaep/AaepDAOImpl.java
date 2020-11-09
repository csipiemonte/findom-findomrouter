/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.integration.extservices.aaep;

import it.csi.aaep.aaeporch.business.AAEPException_Exception;
import it.csi.aaep.aaeporch.business.CSIException_Exception;
import it.csi.aaep.aaeporch.business.ImpresaINFOC;
import it.csi.aaep.aaeporch.business.OrchestratoreIntf;
import it.csi.aaep.aaeporch.business.Sede;
import it.csi.aaep.aaeporch.business.SystemException_Exception;
import it.csi.aaep.aaeporch.business.TipologiaFonte;
import it.csi.aaep.aaeporch.business.UnrecoverableException_Exception;
import it.csi.aaep.aaeporch.business.UserException_Exception;
import it.csi.aaep.aaeporch.business.Utente;
import it.csi.findom.findomrouter.presentation.util.Constants;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;

import org.apache.log4j.Logger;

public class AaepDAOImpl implements AaepDAO {

	private static final String CLASS_NAME = "AaepDAOImpl";
	protected static final Logger log = Logger.getLogger(Constants.APPLICATION_CODE + ".integration.extservices.aaep");

	protected static ResourceBundle res = ResourceBundle.getBundle("configWS");
	
	protected static Service servAAEPOrchCxfService = null;
	protected static OrchestratoreIntf orchestratore = null;
	
	public void init() {
		final String methodName = "init";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		log.debug(logprefix + " BEGIN");
	
		String urlToConnetc  = res.getString("ENDPOINT_URL_AAEP");
		log.info(logprefix + " urlToConnetc="+urlToConnetc);
		
		String targetNamespace = "http://business.aaeporch.aaep.csi.it/";
		String wsdlService = "OrchestratoreImplService";
		
		URL aaepOrchUrl = OrchestratoreIntf.class.getResource("/orchestratoreService-2.0.0.wsdl");
		
		QName serviceName = new QName(targetNamespace,wsdlService); 
		log.debug(logprefix + " QName OK");

		servAAEPOrchCxfService = Service.create(aaepOrchUrl, serviceName);
		log.debug(logprefix + " servAAEPOrchCxfService OK");
			
		orchestratore = servAAEPOrchCxfService.getPort(OrchestratoreIntf.class);
		log.debug(logprefix + " orchestratore OK");
		
		BindingProvider bp = (BindingProvider)orchestratore;
		Map<String, Object> map  = bp.getRequestContext();
		map.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,urlToConnetc);
		
		log.debug(logprefix + " END");
	}

	public static OrchestratoreIntf getOrchestratore() {
		return orchestratore;
	}

//	@Override
//	public ImpresaINFOC getDettaglioImpresa(String fonte, String codFisc) throws UnrecoverableException_Exception, CSIException_Exception, SystemException_Exception, AAEPException_Exception {
//		final String methodName = "getDettaglioImpresa";
//		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
//		log.debug(logprefix + " BEGIN");
//		
//		log.debug(logprefix + " fonte=["+fonte+"]");
//		log.debug(logprefix + " codFisc=["+codFisc+"]");
//		
//		return (ImpresaINFOC)orchestratore.getDettaglioImpresa(new Utente(), fonte,	codFisc);
//	}
	
	@Override
	public ImpresaINFOC getDettaglioImpresa2(String fonte, String codFisc, String statoCessSEDE , String siglaProvCCIAA, String numIscrizREA) throws UnrecoverableException_Exception, CSIException_Exception, SystemException_Exception, AAEPException_Exception {
		final String methodName = "getDettaglioImpresa2";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		log.info(logprefix + " BEGIN");
		
		log.info(logprefix + " fonte=["+fonte+"]");
		log.info(logprefix + " codFisc=["+codFisc+"]");
		log.info(logprefix + " statoCessSEDE=["+statoCessSEDE+"]");
		log.info(logprefix + " siglaProvCCIAA=["+siglaProvCCIAA+"]");
		log.info(logprefix + " numIscrizREA=["+numIscrizREA+"]");
		
		return (ImpresaINFOC)orchestratore.getDettaglioImpresa2(new Utente(), fonte, codFisc, statoCessSEDE , siglaProvCCIAA, numIscrizREA);
	}
	
	@Override
	public Sede getDettaglioSedeLegale(String idAzienda, String idSede, String tipoFonte) throws UserException_Exception, AAEPException_Exception {
		final String methodName = "getDettaglioImpresa2";
		final String logprefix = "[" + CLASS_NAME + "::" + methodName + "] ";
		log.debug(logprefix + " BEGIN");
		
		log.debug(logprefix + " fonte=["+tipoFonte+"]");
		log.debug(logprefix + " idSede=["+idSede+"]");
		log.debug(logprefix + " idAzienda=["+idAzienda+"]");
		
		Sede sedeLegParam = new Sede();
		sedeLegParam.setIdAzienda(idAzienda);
		sedeLegParam.setIdSede(idSede);
		return (Sede)orchestratore.getDettaglioSede(new Utente(), TipologiaFonte.INFOC, sedeLegParam);
	}

}
