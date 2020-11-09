/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.dto.serviziFindomWeb;

import java.io.Serializable;

// dto della vista findom_v_sportelli_attivi
public class VistaSportelliAttiviDto implements Serializable{

	static final long serialVersionUID = 1;
		
	private int idSportelloBando;
	private String dtApertura; // Date
	private String dtChiusura; // Date
	private String numMaxDomandeSportello; // sul db int, ma deve contemplare il null
	private int idBando;
	private String numMaxDomandeBando; // sul db int, ma deve contemplare il null
	private String descrizioneBando;
	private String descrizioneBreveBando;
	private String codiceAzione;
	private int idNormativa;
	private String normativa;
	private Integer idAreaTematica;
	private String descrizioneAreaTematica; 
	
	//GETTERS && SETTERS
	
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
	public String getNumMaxDomandeSportello() {
		return numMaxDomandeSportello;
	}
	public void setNumMaxDomandeSportello(String numMaxDomandeSportello) {
		this.numMaxDomandeSportello = numMaxDomandeSportello;
	}
	public int getIdBando() {
		return idBando;
	}
	public void setIdBando(int idBando) {
		this.idBando = idBando;
	}
	public String getNumMaxDomandeBando() {
		return numMaxDomandeBando;
	}
	public void setNumMaxDomandeBando(String numMaxDomandeBando) {
		this.numMaxDomandeBando = numMaxDomandeBando;
	}
	public String getDescrizioneBando() {
		return descrizioneBando;
	}
	public void setDescrizioneBando(String descrizioneBando) {
		this.descrizioneBando = descrizioneBando;
	}
	public String getDescrizioneBreveBando() {
		return descrizioneBreveBando;
	}
	public void setDescrizioneBreveBando(String descrizioneBreveBando) {
		this.descrizioneBreveBando = descrizioneBreveBando;
	}
	public String getCodiceAzione() {
		return codiceAzione;
	}
	public void setCodiceAzione(String codiceAzione) {
		this.codiceAzione = codiceAzione;
	}
	public int getIdNormativa() {
		return idNormativa;
	}
	public void setIdNormativa(int idNormativa) {
		this.idNormativa = idNormativa;
	}
	public String getNormativa() {
		return normativa;
	}
	public void setNormativa(String normativa) {
		this.normativa = normativa;
	}
	public Integer getIdAreaTematica() {
		return idAreaTematica;
	}
	public void setIdAreaTematica(Integer idAreaTematica) {
		this.idAreaTematica = idAreaTematica;
	}
	public String getDescrizioneAreaTematica() {
		return descrizioneAreaTematica;
	}
	public void setDescrizioneAreaTematica(String descrizioneAreaTematica) {
		this.descrizioneAreaTematica = descrizioneAreaTematica;
	}
	  
}
