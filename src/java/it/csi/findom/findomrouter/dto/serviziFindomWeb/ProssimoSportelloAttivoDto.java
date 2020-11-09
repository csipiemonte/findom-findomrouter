/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.dto.serviziFindomWeb;

import java.io.Serializable;

// dto della vista findom_v_sportelli_attivi
public class ProssimoSportelloAttivoDto implements Serializable{

	static final long serialVersionUID = 1;
		
	private String dtApertura; // Date
	private String oraApertura; // Date
	private String descrizioneBando;
	private Integer idAreaTematica;
	
	//GETTERS && SETTERS
	
	public String getDtApertura() {
		return dtApertura;
	}
	public void setDtApertura(String dtApertura) {
		this.dtApertura = dtApertura;
	}
	public String getOraApertura() {
		return oraApertura;
	}
	public void setOraApertura(String oraApertura) {
		this.oraApertura = oraApertura;
	}
	public String getDescrizioneBando() {
		return descrizioneBando;
	}
	public void setDescrizioneBando(String descrizioneBando) {
		this.descrizioneBando = descrizioneBando;
	}
	public Integer getIdAreaTematica() {
		return idAreaTematica;
	}
	public void setIdAreaTematica(Integer idAreaTematica) {
		this.idAreaTematica = idAreaTematica;
	}
	  
}
