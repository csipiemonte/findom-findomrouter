/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.vo;

import static it.csi.findom.findomrouter.util.Utils.quote;

import java.util.Date;
import java.util.Map;

public class StatusInfo implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer templateId;
	private String modelName;
	private Integer numProposta;
	private String statoProposta;
	private Map<String, String> statoPropostaMap;
	private String operatore;
	private String descrizioneOperatore;
	private Integer numSportello;
	private String aperturaSportello;
	private String aperturaSportelloDa;
	private String aperturaSportelloA;
	private Date dataTrasmissione;
	private String contextSportello;
	
	//NUOVI PK
	private Integer idSoggettoBeneficiario;
	private String codFiscaleBeneficiario;
	private Integer idSoggettoCollegato;
	private String descrImpresaEnte;
	
	private String descrNormativa;
	private String codiceAzione;
	private String descrBando;
	private String descrBreveBando;
	private String descrTipolBeneficiario;
	
	private String flagBandoDematerializzato;
	private String tipoFirma;
	
	private String codiceFiscaleDelegato;
	private String codiceFiscaleLegaleRappresentante;
	
	private String siglaNazioneAzienda;
	
	// GETTERS && SETTERS
	
	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public Integer getNumProposta() {
		return numProposta;
	}

	public void setNumProposta(Integer numProposta) {
		this.numProposta = numProposta;
	}

	public String getStatoProposta() {
		return statoProposta;
	}

	public void setStatoProposta(String statoProposta) {
		this.statoProposta = statoProposta;
	}

	public Map<String, String> getStatoPropostaMap() {
		return statoPropostaMap;
	}

	public void setStatoPropostaMap(Map<String, String> statoPropostaMap) {
		this.statoPropostaMap = statoPropostaMap;
	}

	public String getOperatore() {
		return operatore;
	}

	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}

	public String getDescrizioneOperatore() {
		return descrizioneOperatore;
	}

	public void setDescrizioneOperatore(String descrizioneOperatore) {
		this.descrizioneOperatore = descrizioneOperatore;
	}

	public Integer getNumSportello() {
		return numSportello;
	}

	public void setNumSportello(Integer numSportello) {
		this.numSportello = numSportello;
	}

	public String getAperturaSportello() {
		return aperturaSportello;
	}

	public void setAperturaSportello(String aperturaSportello) {
		this.aperturaSportello = aperturaSportello;
	}

	public String getAperturaSportelloDa() {
		return aperturaSportelloDa;
	}

	public void setAperturaSportelloDa(String aperturaSportelloDa) {
		this.aperturaSportelloDa = aperturaSportelloDa;
	}

	public String getAperturaSportelloA() {
		return aperturaSportelloA;
	}

	public void setAperturaSportelloA(String aperturaSportelloA) {
		this.aperturaSportelloA = aperturaSportelloA;
	}

	public Date getDataTrasmissione() {
		return dataTrasmissione;
	}

	public void setDataTrasmissione(Date dataTrasmissione) {
		this.dataTrasmissione = dataTrasmissione;
	}
	
	public String getFlagBandoDematerializzato() {
		return flagBandoDematerializzato;
	}

	public void setFlagBandoDematerializzato(String flagBandoDematerializzato) {
		this.flagBandoDematerializzato = flagBandoDematerializzato;
	}
	
	public String getTipoFirma() {
		return tipoFirma;
	}

	public void setTipoFirma(String tipoFirma) {
		this.tipoFirma = tipoFirma;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		//
		sb.append("\ttemplateId:" + quote(templateId) + "\n");
		sb.append("\tmodelName:" + quote(modelName) + "\n");
		sb.append("\tnumProposta:" + quote(numProposta) + "\n");
		sb.append("\tstatoProposta:" + quote(statoProposta) + "\n");
		sb.append("\tstatoPropostaMap:" + quote(statoPropostaMap) + "\n");
		sb.append("\toperatore:" + quote(operatore) + "\n");
		sb.append("\tdescrizioneOperatore:" + quote(descrizioneOperatore) + "\n");
		sb.append("\tnumSportello:" + quote(numSportello) + "\n");
		sb.append("\taperturaSportello:" + quote(aperturaSportello) + "\n");
		sb.append("\taperturaSportelloDa:" + quote(aperturaSportelloDa) + "\n");
		sb.append("\taperturaSportelloA:" + quote(aperturaSportelloA) + "\n");
		sb.append("\tdataTrasmissione:" + quote(dataTrasmissione) + "\n");
		sb.append("\tIdSoggettoBeneficiario:" + quote(idSoggettoBeneficiario) + "\n");
		sb.append("\tcodFiscaleBeneficiario:" + quote(codFiscaleBeneficiario) + "\n");
		sb.append("\tidSoggettoCollegato:" + quote(idSoggettoCollegato) + "\n");
		sb.append("\tdescrImpresaEnte:" + quote(descrImpresaEnte) + "\n");
		sb.append("\tdescrNormativa:" + quote(descrNormativa) + "\n");
		sb.append("\tcodiceAzione:" + quote(codiceAzione) + "\n");
		sb.append("\tdescrBando:" + quote(descrBando) + "\n");
		sb.append("\tdescrBreveBando:" + quote(descrBreveBando) + "\n");
		sb.append("\tdescrTipolBeneficiario:" + quote(descrTipolBeneficiario) + "\n");
		sb.append("\tflagBandoDematerializzato:" + quote(flagBandoDematerializzato) + "\n");
		sb.append("\ttipoFirma:" + quote(tipoFirma) + "\n");
		sb.append("\tcontextSportello:" + quote(contextSportello) + "\n");
		sb.append("\tcodiceFiscaleDelegato:" + quote(codiceFiscaleDelegato) + "\n");
		sb.append("\tcodiceFiscaleLegaleRappresentante:" + quote(codiceFiscaleLegaleRappresentante) + "\n");
		sb.append("\tsiglaNazioneAzienda:" + quote(siglaNazioneAzienda) + "\n");
		
		return "StatusInfo:[[\n" + sb.toString() + "]]";
	}

	public Integer getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}

	public void setIdSoggettoBeneficiario(Integer idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}

	public Integer getIdSoggettoCollegato() {
		return idSoggettoCollegato;
	}

	public void setIdSoggettoCollegato(Integer idSoggettoCollegato) {
		this.idSoggettoCollegato = idSoggettoCollegato;
	}

	public String getCodFiscaleBeneficiario() {
		return codFiscaleBeneficiario;
	}

	public void setCodFiscaleBeneficiario(String codFiscaleBeneficiario) {
		this.codFiscaleBeneficiario = codFiscaleBeneficiario;
	}

	public String getDescrImpresaEnte() {
		return descrImpresaEnte;
	}

	public void setDescrImpresaEnte(String descrImpresaEnte) {
		this.descrImpresaEnte = descrImpresaEnte;
	}

	public String getDescrNormativa() {
		return descrNormativa;
	}

	public void setDescrNormativa(String descrNormativa) {
		this.descrNormativa = descrNormativa;
	}

	public String getCodiceAzione() {
		return codiceAzione;
	}

	public void setCodiceAzione(String codiceAzione) {
		this.codiceAzione = codiceAzione;
	}

	public String getDescrBando() {
		return descrBando;
	}

	public void setDescrBando(String descrBando) {
		this.descrBando = descrBando;
	}

	public String getDescrBreveBando() {
		return descrBreveBando;
	}

	public void setDescrBreveBando(String descrBreveBando) {
		this.descrBreveBando = descrBreveBando;
	}

	public String getDescrTipolBeneficiario() {
		return descrTipolBeneficiario;
	}

	public void setDescrTipolBeneficiario(String descrTipolBeneficiario) {
		this.descrTipolBeneficiario = descrTipolBeneficiario;
	}

	public String getCodiceFiscaleDelegato() {
		return codiceFiscaleDelegato;
	}

	public void setCodiceFiscaleDelegato(String codiceFiscaleDelegato) {
		this.codiceFiscaleDelegato = codiceFiscaleDelegato;
	}

	public String getCodiceFiscaleLegaleRappresentante() {
		return codiceFiscaleLegaleRappresentante;
	}

	public void setCodiceFiscaleLegaleRappresentante(
			String codiceFiscaleLegaleRappresentante) {
		this.codiceFiscaleLegaleRappresentante = codiceFiscaleLegaleRappresentante;
	}

	public String getContextSportello() {
		return contextSportello;
	}

	public void setContextSportello(String contextSportello) {
		this.contextSportello = contextSportello;
	}

	public String getSiglaNazioneAzienda() {
		return siglaNazioneAzienda;
	}

	public void setSiglaNazioneAzienda(String siglaNazioneAzienda) {
		this.siglaNazioneAzienda = siglaNazioneAzienda;
	}

	
	
}
