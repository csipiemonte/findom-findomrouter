/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.integration.extservices.ipa;

import it.csi.findom.findomwebnew.dto.ipa.Ipa;

public interface IpaDAO {

	public Ipa searchEnteOnIPA(String codFiscale);
	
}
