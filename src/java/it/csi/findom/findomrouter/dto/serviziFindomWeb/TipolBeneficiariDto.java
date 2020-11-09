/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.dto.serviziFindomWeb;

import java.io.Serializable;

public class TipolBeneficiariDto implements Serializable {

	static final long serialVersionUID = 1;
	
	// tabella FINDOM_D_TIPOL_BENEFICIARI
	
	private int idTipolBeneficiario;
	private String descrizione;
	private String codStereotipo;
	private int flagPubblicoPrivato;
	private String codice;
	
	// GETTERS && SETTERS
	
	public int getIdTipolBeneficiario() {
		return idTipolBeneficiario;
	}
	public void setIdTipolBeneficiario(int idTipolBeneficiario) {
		this.idTipolBeneficiario = idTipolBeneficiario;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getCodStereotipo() {
		return codStereotipo;
	}
	public void setCodStereotipo(String codStereotipo) {
		this.codStereotipo = codStereotipo;
	}
	public int getFlagPubblicoPrivato() {
		return flagPubblicoPrivato;
	}
	public void setFlagPubblicoPrivato(int flagPubblicoPrivato) {
		this.flagPubblicoPrivato = flagPubblicoPrivato;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
}
