/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.dto.serviziFindomWeb;

import java.io.Serializable;

public class StatoEsteroDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public StatoEsteroDto() {}

	public StatoEsteroDto(String codice, String descrizione, String sigla) {
		this.codice = codice;
		this.descrizione = descrizione;
		this.sigla = sigla;
	}
	
	String codice;
	String descrizione;
	String sigla;

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

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	@Override
	public String toString() {
		return "StatoEsteroVO [codice=" + codice + ", descrizione=" + descrizione + ", sigla=" + sigla + "]";
	}

}
