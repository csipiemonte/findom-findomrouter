/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.integration.extservices.aaep;

import it.csi.aaep.aaeporch.business.AAEPException_Exception;
import it.csi.aaep.aaeporch.business.CSIException_Exception;
import it.csi.aaep.aaeporch.business.ImpresaINFOC;
import it.csi.aaep.aaeporch.business.Sede;
import it.csi.aaep.aaeporch.business.SystemException_Exception;
import it.csi.aaep.aaeporch.business.UnrecoverableException_Exception;
import it.csi.aaep.aaeporch.business.UserException_Exception;

public interface AaepDAO {

//	ImpresaINFOC getDettaglioImpresa(String fonte, String codFisc) 
//			throws UnrecoverableException_Exception, CSIException_Exception, SystemException_Exception, AAEPException_Exception;

	ImpresaINFOC getDettaglioImpresa2(String fonte, String codFisc, String statoCessSEDE , String siglaProvCCIAA, String numIscrizREA) 
			throws UnrecoverableException_Exception, CSIException_Exception, SystemException_Exception, AAEPException_Exception;

	Sede getDettaglioSedeLegale(String idAzienda, String idSede, String tipoFonte) throws UserException_Exception, AAEPException_Exception;
}
