/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.vo;

import static it.csi.findom.findomrouter.util.Utils.quote;

public class UserInfo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String nome; // impostato da IrideAdapterFilter
	private String cognome; // impostato da IrideAdapterFilter
	private String codFisc; // impostato da IrideAdapterFilter
	private String idIride; // impostato da IrideAdapterFilter
//	private String codDettFlaidoor; // conterra' il parametro 'codiceDettaglio' che arriva da FLAIDOOR
//	private String codRuoloFlaidoor; // conterra' il parametro 'codiceRuolo' che arriva da FLAIDOOR (Es.
//	private ProfiloPersonaDto profiloPersona;
//	private OperatoreFPInformazioniAggiuntiveDto infoAggiuntiveDto;

	private String ruolo;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodFisc() {
		return codFisc;
	}

	public void setCodFisc(String codFisc) {
		this.codFisc = codFisc;
	}

	public String getIdIride() {
		return idIride;
	}

	public void setIdIride(String idIride) {
		this.idIride = idIride;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		//
		sb.append("\tNome:" + quote(nome) + "\n");
		sb.append("\tCognome:" + quote(cognome) + "\n");
		sb.append("\tcodFisc:" + quote(codFisc) + "\n");
		sb.append("\tidIride:" + quote(idIride) + "\n");
		sb.append("\truolo:" + quote(ruolo) + "\n");
		
		return "UserInfo:[[\n" + sb.toString() + "]]";
	}

	
}
