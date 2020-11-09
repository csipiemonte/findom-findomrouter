/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.dto.serviziFindomWeb;

import java.io.Serializable;

public class ParametroRegolaBandoDto implements Serializable {

	static final long serialVersionUID = 1;
	
	private int templateId;	
	private String descrBreveBando;	
	private String codRegola;
	private String codParametro;
	private String valoreParametro;
	
	// GETTERS && SETTERS	
	public int getTemplateId() {
		return templateId;
	}
	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}
	public String getDescrBreveBando() {
		return descrBreveBando;
	}
	public void setDescrBreveBando(String descrBreveBando) {
		this.descrBreveBando = descrBreveBando;
	}
	public String getCodRegola() {
		return codRegola;
	}
	public void setCodRegola(String codRegola) {
		this.codRegola = codRegola;
	}
	public String getCodParametro() {
		return codParametro;
	}
	public void setCodParametro(String codParametro) {
		this.codParametro = codParametro;
	}
	public String getValoreParametro() {
		return valoreParametro;
	}
	public void setValoreParametro(String valoreParametro) {
		this.valoreParametro = valoreParametro;
	}	
	
}
