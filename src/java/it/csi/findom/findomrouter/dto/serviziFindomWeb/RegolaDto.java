/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.dto.serviziFindomWeb;

import java.io.Serializable;

public class RegolaDto implements Serializable {

	static final long serialVersionUID = 1;
	
	private int idRegola;
	private String codRegola;
	private String descrRegola;
	private String corpoRegola;
	
	// GETTERS && SETTERS
	
	public int getIdRegola() {
		return idRegola;
	}
	public void setIdRegola(int idRegola) {
		this.idRegola = idRegola;
	}
	public String getCodRegola() {
		return codRegola;
	}
	public void setCodRegola(String codRegola) {
		this.codRegola = codRegola;
	}
	public String getDescrRegola() {
		return descrRegola;
	}
	public void setDescrRegola(String descrRegola) {
		this.descrRegola = descrRegola;
	}
	public String getCorpoRegola() {
		return corpoRegola;
	}
	public void setCorpoRegola(String corpoRegola) {
		this.corpoRegola = corpoRegola;
	}
	
}
