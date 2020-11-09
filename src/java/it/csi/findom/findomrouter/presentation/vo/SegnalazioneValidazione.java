/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.vo;

import static it.csi.findom.findomrouter.util.Utils.quote;

public class SegnalazioneValidazione implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String codice;
	private String tab1tab2;
	private String testo;
	private boolean bloccante;
	
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getTab1tab2() {
		return tab1tab2;
	}
	public void setTab1tab2(String tab1tab2) {
		this.tab1tab2 = tab1tab2;
	}
	public String getTesto() {
		return testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}
	public boolean isBloccante() {
		return bloccante;
	}
	public void setBloccante(boolean bloccante) {
		this.bloccante = bloccante;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		//
		sb.append("\tcodice:" + quote(codice) + "\n");
		sb.append("\ttab1tab2:" + quote(tab1tab2) + "\n");
		sb.append("\ttesto:" + quote(testo) + "\n");
		sb.append("\tbloccante:" + quote(bloccante) + "\n");

		return "SegnalazioneValidazione:[[\n" + sb.toString() + "]]";
	}
}
