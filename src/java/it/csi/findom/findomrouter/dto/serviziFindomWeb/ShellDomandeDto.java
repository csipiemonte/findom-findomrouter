/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.dto.serviziFindomWeb;

import java.io.Serializable;

public class ShellDomandeDto implements Serializable {

	static final long serialVersionUID = 1;
	
	// tabella SHELL_T_DOMANDE 
	
	private int idDomanda;
	private int idSoggettoCreatore;
	private int idSoggettoBeneficiario;
	private int idSportelloBando;
	private Integer idSoggettoInvio;
	private String dataCreazione; // Date
	private int idTipolBeneficiario;
	private String dataInvioDomanda; // Date
	
	// GETTERS && SETTERS
	
	public int getIdDomanda() {
		return idDomanda;
	}
	public void setIdDomanda(int idDomanda) {
		this.idDomanda = idDomanda;
	}
	public int getIdSoggettoCreatore() {
		return idSoggettoCreatore;
	}
	public void setIdSoggettoCreatore(int idSoggettoCreatore) {
		this.idSoggettoCreatore = idSoggettoCreatore;
	}
	public int getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}
	public void setIdSoggettoBeneficiario(int idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}
	public int getIdSportelloBando() {
		return idSportelloBando;
	}
	public void setIdSportelloBando(int idSportelloBando) {
		this.idSportelloBando = idSportelloBando;
	}
	public Integer getIdSoggettoInvio() {
		return idSoggettoInvio;
	}
	public void setIdSoggettoInvio(Integer idSoggettoInvio) {
		this.idSoggettoInvio = idSoggettoInvio;
	}
	public String getDataCreazione() {
		return dataCreazione;
	}
	public void setDataCreazione(String dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
	public int getIdTipolBeneficiario() {
		return idTipolBeneficiario;
	}
	public void setIdTipolBeneficiario(int idTipolBeneficiario) {
		this.idTipolBeneficiario = idTipolBeneficiario;
	}
	public String getDataInvioDomanda() {
		return dataInvioDomanda;
	}
	public void setDataInvioDomanda(String dataInvioDomanda) {
		this.dataInvioDomanda = dataInvioDomanda;
	}

}
