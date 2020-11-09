/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.dto.serviziFindomWeb;

import java.io.Serializable;

public class AmministratoriDto implements Serializable {

	static final long serialVersionUID = 1;
	
	// tabella FINDOM_T_AMMINISTRATORI
	
	private int idAmministratore;
	private String codiceFiscale;
	private String cognome;
	private String nome;
	private int idEnteAppartenenza;
	private String dataInizio; // Date
	private String dataFine; // Date
	
	//GETTERS && SETTERS
	
	public int getIdAmministratore() {
		return idAmministratore;
	}
	public void setIdAmministratore(int idAmministratore) {
		this.idAmministratore = idAmministratore;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getIdEnteAppartenenza() {
		return idEnteAppartenenza;
	}
	public void setIdEnteAppartenenza(int idEnteAppartenenza) {
		this.idEnteAppartenenza = idEnteAppartenenza;
	}
	public String getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(String dataInizio) {
		this.dataInizio = dataInizio;
	}
	public String getDataFine() {
		return dataFine;
	}
	public void setDataFine(String dataFine) {
		this.dataFine = dataFine;
	}
	
	

}
