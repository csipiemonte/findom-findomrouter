/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.vo;

import static it.csi.findom.findomrouter.util.Utils.quote;

public class ImpresaEnte implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// from ShellSoggettiDto
	private int idSoggetto; // impresa o ente
	private String codiceFiscale;
	private String denominazione;
	private int idFormaGiuridica ;
	private String cognome;
	private String nome;
	
	// from FormeGiuridicheDto
	//private int idFormaGiuridica; 
	private String codFormaGiuridica;
	private String descrFormaGiuridica;
	private int flagPubblicoPrivato;
	private String siglaNazione;
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		//
		sb.append("\tidSoggetto:" + quote(idSoggetto) + "\n");
		sb.append("\tcodiceFiscale:" + quote(codiceFiscale) + "\n");
		sb.append("\tdenominazione:" + quote(denominazione) + "\n");
		sb.append("\tidFormaGiuridica:" + quote(idFormaGiuridica) + "\n");
		sb.append("\tcognome:" + quote(cognome) + "\n");
		sb.append("\tnome:" + quote(nome) + "\n");
		sb.append("\tcodFormaGiuridica:" + quote(codFormaGiuridica) + "\n");
		sb.append("\tdescrFormaGiuridica:" + quote(descrFormaGiuridica) + "\n");
		sb.append("\tflagPubblicoPrivato:" + quote(flagPubblicoPrivato) + "\n");
		sb.append("\tsiglaNazione:" + quote(siglaNazione) + "\n");
		
		return "ImpresaEnte:[[\n" + sb.toString() + "]]";
	}
	//GETTERS && SETTERS
	
	public int getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(int idSoggetto) {
		this.idSoggetto = idSoggetto;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public int getIdFormaGiuridica() {
		return idFormaGiuridica;
	}
	public void setIdFormaGiuridica(int idFormaGiuridica) {
		this.idFormaGiuridica = idFormaGiuridica;
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
	public String getSiglaNazione() {
		return siglaNazione;
	}
	public void setSiglaNazione(String siglaNazione) {
		this.siglaNazione = siglaNazione;
	}

}
