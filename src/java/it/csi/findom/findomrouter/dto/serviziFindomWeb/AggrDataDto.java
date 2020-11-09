/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.dto.serviziFindomWeb;

import java.io.Serializable;

public class AggrDataDto implements Serializable {

	static final long serialVersionUID = 1;

	// tabella AGGR_T_TEMPLATE
	private int templateId;
	private String templateCode ;
	private String templateDescription;
	private String templateName;
	
	// tabella AGGR_T_MODEL
	private int modelId;
	private int modelProgr;
	private String userId;
	private String modelStateFk;
	private String modelName ;
	
	// tabella SHELL_T_DOMANDE
	private int idSoggettoCreatore;
	private int idSoggettoBeneficiario;
	private int idSportelloBando;
	private int idTipolBeneficiario;
	private Integer idSoggettoInvio;
	private String dataCreazione; // Date
	private String dataInvioDomanda; // Date
	
	// tabella FINDOM_T_SPORTELLI_BANDO
	private String dataAperturaSportello; // Date
	private String dataChiusuraSportello; // Date
	
	// GETTERS && SETTERS
	
	
	public int getTemplateId() {
		return templateId;
	}
	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}
	public String getTemplateCode() {
		return templateCode;
	}
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}
	public String getTemplateDescription() {
		return templateDescription;
	}
	public void setTemplateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public int getModelId() {
		return modelId;
	}
	public void setModelId(int modelId) {
		this.modelId = modelId;
	}
	public int getModelProgr() {
		return modelProgr;
	}
	public void setModelProgr(int modelProgr) {
		this.modelProgr = modelProgr;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getModelStateFk() {
		return modelStateFk;
	}
	public void setModelStateFk(String modelStateFk) {
		this.modelStateFk = modelStateFk;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
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
	public int getIdTipolBeneficiario() {
		return idTipolBeneficiario;
	}
	public void setIdTipolBeneficiario(int idTipolBeneficiario) {
		this.idTipolBeneficiario = idTipolBeneficiario;
	}
	public String getDataCreazione() {
		return dataCreazione;
	}
	public void setDataCreazione(String dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
	public String getDataInvioDomanda() {
		return dataInvioDomanda;
	}
	public void setDataInvioDomanda(String dataInvioDomanda) {
		this.dataInvioDomanda = dataInvioDomanda;
	}
	public Integer getIdSoggettoInvio() {
		return idSoggettoInvio;
	}
	public void setIdSoggettoInvio(Integer idSoggettoInvio) {
		this.idSoggettoInvio = idSoggettoInvio;
	}
	public String getDataAperturaSportello() {
		return dataAperturaSportello;
	}
	public void setDataAperturaSportello(String dataAperturaSportello) {
		this.dataAperturaSportello = dataAperturaSportello;
	}
	public String getDataChiusuraSportello() {
		return dataChiusuraSportello;
	}
	public void setDataChiusuraSportello(String dataChiusuraSportello) {
		this.dataChiusuraSportello = dataChiusuraSportello;
	}
	
	
}
