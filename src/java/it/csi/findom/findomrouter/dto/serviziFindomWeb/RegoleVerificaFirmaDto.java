/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.dto.serviziFindomWeb;

import java.io.Serializable;

public class RegoleVerificaFirmaDto implements Serializable {

	static final long serialVersionUID = 1;
	private String tipologiaVerificaFirma;
	private String flagVerificaFirma;

	// GETTERS && SETTERS
	
	public String getTipologiaVerificaFirma() {
		return tipologiaVerificaFirma;
	}
	public void setTipologiaVerificaFirma(String tipologiaVerificaFirma) {
		this.tipologiaVerificaFirma = tipologiaVerificaFirma;
	}
	public String getFlagVerificaFirma() {
		return flagVerificaFirma;
	}
	public void setFlagVerificaFirma(String flagVerificaFirma) {
		this.flagVerificaFirma = flagVerificaFirma;
	}
	
}
