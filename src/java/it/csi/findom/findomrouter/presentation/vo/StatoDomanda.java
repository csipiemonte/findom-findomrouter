/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.vo;

import java.io.Serializable;

public class StatoDomanda implements Serializable {

	static final long serialVersionUID = 1;
	
	// tabella AGGR_D_MODEL_STATE
	
	private String codice;
	private String descrizione;
	private String dataInizioValidita; // Date
	private String dataFineValidita; // Date
	
	// GETTERS && SETTERS
	
	public String getCodice() {
		return codice;
	}
	
	public void setCodice(String codice) {
		this.codice = codice;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public String getDataInizioValidita() {
		return dataInizioValidita;
	}
	
	public void setDataInizioValidita(String dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}
	
	public String getDataFineValidita() {
		return dataFineValidita;
	}
	
	public void setDataFineValidita(String dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}
}
