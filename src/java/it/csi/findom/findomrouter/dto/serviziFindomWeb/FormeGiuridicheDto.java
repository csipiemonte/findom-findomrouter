/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.dto.serviziFindomWeb;

import java.io.Serializable;

public class FormeGiuridicheDto implements Serializable {

	static final long serialVersionUID = 1;
	
	// tabella EXT_D_FORME_GIURIDICHE
	
	private int idFormaGiuridica; 
	private String codFormaGiuridica;
	private String descrFormaGiuridica;
	private int flagPubblicoPrivato;
	
	// GETTERS && SETTERS
	
	public int getIdFormaGiuridica() {
		return idFormaGiuridica;
	}
	public void setIdFormaGiuridica(int idFormaGiuridica) {
		this.idFormaGiuridica = idFormaGiuridica;
	}
	public String getCodFormaGiuridica() {
		return codFormaGiuridica;
	}
	public void setCodFormaGiuridica(String codFormaGiuridica) {
		this.codFormaGiuridica = codFormaGiuridica;
	}
	public String getDescrFormaGiuridica() {
		return descrFormaGiuridica;
	}
	public void setDescrFormaGiuridica(String descrFormaGiuridica) {
		this.descrFormaGiuridica = descrFormaGiuridica;
	}
	public int getFlagPubblicoPrivato() {
		return flagPubblicoPrivato;
	}
	public void setFlagPubblicoPrivato(int flagPubblicoPrivato) {
		this.flagPubblicoPrivato = flagPubblicoPrivato;
	}
	
	
}
