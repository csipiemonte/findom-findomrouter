/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.dto;

import java.io.Serializable;

public class FindRouterRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2043488334158918136L;
	
	private String codiceFase;
	private Integer idSportello;
	private Integer idDomanda;
	
	public String getCodiceFase() {
		return codiceFase;
	}
	public void setCodiceFase(String codiceFase) {
		this.codiceFase = codiceFase;
	}
	public Integer getIdSportello() {
		return idSportello;
	}
	public void setIdSportello(Integer idSportello) {
		this.idSportello = idSportello;
	}
	public Integer getIdDomanda() {
		return idDomanda;
	}
	public void setIdDomanda(Integer idDomanda) {
		this.idDomanda = idDomanda;
	}
	


	
	
	
}
