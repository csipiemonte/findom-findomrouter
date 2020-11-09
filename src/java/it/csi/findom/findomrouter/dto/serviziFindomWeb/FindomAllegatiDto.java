/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.dto.serviziFindomWeb;

import java.io.Serializable;

public class FindomAllegatiDto implements Serializable {

	static final long serialVersionUID = 1;
	
	// tabella EXT_D_FORME_GIURIDICHE
	
	private int idBando;
	private int idAllegato; 
	private String descrizione;
	private String flagObbligatorio;
	private String dtInizio;
	private String dtFine;
	private String flagFirmaDigitale;
	
		
	// GETTERS && SETTERS
	
	public int getIdAllegato() {
		return idAllegato;
	}
	public void setIdAllegato(int idAllegato) {
		this.idAllegato = idAllegato;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public int getIdBando() {
		return idBando;
	}
	public void setIdBando(int idBando) {
		this.idBando = idBando;
	}
	public String getFlagObbligatorio() {
		return flagObbligatorio;
	}
	public void setFlagObbligatorio(String flagObbligatorio) {
		this.flagObbligatorio = flagObbligatorio;
	}
	public String getDtInizio() {
		return dtInizio;
	}
	public void setDtInizio(String dtInizio) {
		this.dtInizio = dtInizio;
	}
	public String getDtFine() {
		return dtFine;
	}
	public void setDtFine(String dtFine) {
		this.dtFine = dtFine;
	}
	public String getFlagFirmaDigitale() {
		return flagFirmaDigitale;
	}
	public void setFlagFirmaDigitale(String flagFirmaDigitale) {
		this.flagFirmaDigitale = flagFirmaDigitale;
	}	
}
