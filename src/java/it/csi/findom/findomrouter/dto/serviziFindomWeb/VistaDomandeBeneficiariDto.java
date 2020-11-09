/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.dto.serviziFindomWeb;

import java.io.Serializable;

public class VistaDomandeBeneficiariDto implements Serializable{

	static final long serialVersionUID = 1;

	//dto della vista findom_v_domande_beneficiari;
	
	int idBando;
    int idSportelloBando;
    String dtApertura; // Date
    String dtChiusura; // Date
    int idSoggettoBeneficiario;
    int numDomandeBando;
    int numDomandeSportello;
    
    //GETTERS && SETTERS
    
	public int getIdBando() {
		return idBando;
	}
	public void setIdBando(int idBando) {
		this.idBando = idBando;
	}
	public int getIdSportelloBando() {
		return idSportelloBando;
	}
	public void setIdSportelloBando(int idSportelloBando) {
		this.idSportelloBando = idSportelloBando;
	}
	public String getDtApertura() {
		return dtApertura;
	}
	public void setDtApertura(String dtApertura) {
		this.dtApertura = dtApertura;
	}
	public String getDtChiusura() {
		return dtChiusura;
	}
	public void setDtChiusura(String dtChiusura) {
		this.dtChiusura = dtChiusura;
	}
	public int getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}
	public void setIdSoggettoBeneficiario(int idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}
	public int getNumDomandeBando() {
		return numDomandeBando;
	}
	public void setNumDomandeBando(int numDomandeBando) {
		this.numDomandeBando = numDomandeBando;
	}
	public int getNumDomandeSportello() {
		return numDomandeSportello;
	}
	public void setNumDomandeSportello(int numDomandeSportello) {
		this.numDomandeSportello = numDomandeSportello;
	}
	
}
