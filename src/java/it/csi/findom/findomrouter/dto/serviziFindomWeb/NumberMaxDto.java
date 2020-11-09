/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.dto.serviziFindomWeb;

import java.io.Serializable;

public class NumberMaxDto implements Serializable {

	static final long serialVersionUID = 1;
	
	Integer numMaxDomandeBando; // findom_t_bandi.num_max_domande
	Integer numMaxDomandeSportello; // findom_t_sportelli_bandi.num_max_domande
	Integer idBando;
	Integer idSportelloBando;
	Integer idDomanda;
	
	public Integer getNumMaxDomandeBando() {
		return numMaxDomandeBando;
	}
	public void setNumMaxDomandeBando(Integer numMaxDomandeBando) {
		this.numMaxDomandeBando = numMaxDomandeBando;
	}
	public Integer getNumMaxDomandeSportello() {
		return numMaxDomandeSportello;
	}
	public void setNumMaxDomandeSportello(Integer numMaxDomandeSportello) {
		this.numMaxDomandeSportello = numMaxDomandeSportello;
	}
	public Integer getIdBando() {
		return idBando;
	}
	public void setIdBando(Integer idBando) {
		this.idBando = idBando;
	}
	public Integer getIdSportelloBando() {
		return idSportelloBando;
	}
	public void setIdSportelloBando(Integer idSportelloBando) {
		this.idSportelloBando = idSportelloBando;
	}
	public Integer getIdDomanda() {
		return idDomanda;
	}
	public void setIdDomanda(Integer idDomanda) {
		this.idDomanda = idDomanda;
	}

}
