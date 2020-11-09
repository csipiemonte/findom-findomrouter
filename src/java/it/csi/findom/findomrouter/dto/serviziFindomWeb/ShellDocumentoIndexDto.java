/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.dto.serviziFindomWeb;

import java.io.Serializable;

public class ShellDocumentoIndexDto implements Serializable {

	static final long serialVersionUID = 1;

	// tabella SHELL_T_DOCUMENTO_INDEX
	private Integer idDocumentoIndex ;
	private int idDomanda; 				// aggr_t_model.model_id
	private String uuidNodo;  
	private String repository;   
	private String nomeFile;
	private String messageDigest;		// Impronta digitale del documento
	private String dtVerificaFirma ; 	// timestamp
	private String dtMarcaTemporale; 	// timestamp
	private String dtInserimento; 		// timestamp
	private int idStatoDocumentoIndex;
	private int idAllegato; 			// findom_d_allegati.id_allegato  (tipologia di file)
	private int idSoggetto;  			// shell_t_soggetti.id_soggetto   (id soggetto che ha inserito il file)
	
	// GETTERS && SETTERS
	
	public Integer getIdDocumentoIndex() {
		return idDocumentoIndex;
	}
	public void setIdDocumentoIndex(Integer idDocumentoIndex) {
		this.idDocumentoIndex = idDocumentoIndex;
	}
	public int getIdDomanda() {
		return idDomanda;
	}
	public void setIdDomanda(int idDomanda) {
		this.idDomanda = idDomanda;
	}
	public String getUuidNodo() {
		return uuidNodo;
	}
	public void setUuidNodo(String uuidNodo) {
		this.uuidNodo = uuidNodo;
	}
	public String getRepository() {
		return repository;
	}
	public void setRepository(String repository) {
		this.repository = repository;
	}
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}
	public String getMessageDigest() {
		return messageDigest;
	}
	public void setMessageDigest(String messageDigest) {
		this.messageDigest = messageDigest;
	}
	public String getDtVerificaFirma() {
		return dtVerificaFirma;
	}
	public void setDtVerificaFirma(String dtVerificaFirma) {
		this.dtVerificaFirma = dtVerificaFirma;
	}
	public String getDtMarcaTemporale() {
		return dtMarcaTemporale;
	}
	public void setDtMarcaTemporale(String dtMarcaTemporale) {
		this.dtMarcaTemporale = dtMarcaTemporale;
	}
	public String getDtInserimento() {
		return dtInserimento;
	}
	public void setDtInserimento(String dtInserimento) {
		this.dtInserimento = dtInserimento;
	}
	public int getIdStatoDocumentoIndex() {
		return idStatoDocumentoIndex;
	}
	public void setIdStatoDocumentoIndex(int idStatoDocumentoIndex) {
		this.idStatoDocumentoIndex = idStatoDocumentoIndex;
	}
	public int getIdAllegato() {
		return idAllegato;
	}
	public void setIdAllegato(int idAllegato) {
		this.idAllegato = idAllegato;
	}
	public int getIdSoggetto() {
		return idSoggetto;
	}
	public void setIdSoggetto(int idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

}
