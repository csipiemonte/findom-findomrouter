/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.vo;

import java.util.Date;

public class Domanda implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// TODO : servono
	// aggr_t_model.model_progr --> questo e' il numero della domanda
	
	
	// estende il dto VistaDomandaDto
	
	private String tipologia; // impresa o ente
	private String codiceFiscale;
	private String denominazione;
	private String idAzienda;
	private String partitaIva;
	private String idNaturaGiuridica;
	private String codNaturaGiuridica;
	private String descrNaturaGiuridica;
	
	// VistaDomandaDto
	private int idDomanda;
	private String codStatoDomanda;
	private String statoDomanda;
	private int idTipolBeneficiario ;
	private String descrizioneTipolBeneficiario;
	private String tipoEnteBeneficiario;
	private String codStereotipo;
	private int idSoggettoCreatore ;
	private String cognomeSoggettoCreatore;
	private String nomeSoggettoCreatore;
	private String dtCreazioneDomanda;
	private int idSoggettoInvio ;
	private String cognomeSoggettoInvio;
	private String nomeSoggettoInvio;
	private String dtInvioDomanda;
	private int idSoggettoBeneficiario ;
	private String denominazioneSoggettoBeneficiario;
	private int idSportelloBando ;
	private String numAtto;
	private String dtAtto; 
	private String dtAperturaSportello ;
	private String dtChiusuraSportello;
	private int idBando ;
	private String descrBando;
	private String descrBreveBando;
	private int idNormativa;
	private String normativa;
	private int idClassificazioneAssePrioritario ;
	private String codiceAssePrioritario;
	private String descrizioneAssePrioritario;
	private int idClassificazionePrioritaInvestimento ;
	private String codicePrioritaInvestimento;
	private String descrizionePrioritaInvestimento;
	private int idClassificazioneAzione ;
	private String codiceAzione;
	private String descrizioneAzione;
	private String flagBandoDematerializzato;
	private String tipoFirma;
	
	private Date dataRichiestaIntegrazione;
	private Date dataTermineIntegrazione;
	private Boolean flagAbilitaIntegrazione;
	private String statoRichiesta;
	
	private String flagProgettiComuni;
	private String ruoloCapofilaProgettoComune;
	private String ruoloPartnerProgettoComune;
	
	//GETTERS && SETTERS
	
	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getIdAzienda() {
		return idAzienda;
	}
	public void setIdAzienda(String idAzienda) {
		this.idAzienda = idAzienda;
	}
	public String getPartitaIva() {
		return partitaIva;
	}
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	public String getIdNaturaGiuridica() {
		return idNaturaGiuridica;
	}
	public void setIdNaturaGiuridica(String idNaturaGiuridica) {
		this.idNaturaGiuridica = idNaturaGiuridica;
	}
	public String getCodNaturaGiuridica() {
		return codNaturaGiuridica;
	}
	public void setCodNaturaGiuridica(String codNaturaGiuridica) {
		this.codNaturaGiuridica = codNaturaGiuridica;
	}
	public String getDescrNaturaGiuridica() {
		return descrNaturaGiuridica;
	}
	public void setDescrNaturaGiuridica(String descrNaturaGiuridica) {
		this.descrNaturaGiuridica = descrNaturaGiuridica;
	}
	public int getIdDomanda() {
		return idDomanda;
	}
	public void setIdDomanda(int idDomanda) {
		this.idDomanda = idDomanda;
	}
	public String getCodStatoDomanda() {
		return codStatoDomanda;
	}
	public void setCodStatoDomanda(String codStatoDomanda) {
		this.codStatoDomanda = codStatoDomanda;
	}
	public String getStatoDomanda() {
		return statoDomanda;
	}
	public void setStatoDomanda(String statoDomanda) {
		this.statoDomanda = statoDomanda;
	}
	public int getIdTipolBeneficiario() {
		return idTipolBeneficiario;
	}
	public void setIdTipolBeneficiario(int idTipolBeneficiario) {
		this.idTipolBeneficiario = idTipolBeneficiario;
	}
	public String getDescrizioneTipolBeneficiario() {
		return descrizioneTipolBeneficiario;
	}
	public void setDescrizioneTipolBeneficiario(String descrizioneTipolBeneficiario) {
		this.descrizioneTipolBeneficiario = descrizioneTipolBeneficiario;
	}
	public String getTipoEnteBeneficiario() {
		return tipoEnteBeneficiario;
	}
	public void setTipoEnteBeneficiario(String tipoEnteBeneficiario) {
		this.tipoEnteBeneficiario = tipoEnteBeneficiario;
	}
	public String getCodStereotipo() {
		return codStereotipo;
	}
	public void setCodStereotipo(String codStereotipo) {
		this.codStereotipo = codStereotipo;
	}
	public int getIdSoggettoCreatore() {
		return idSoggettoCreatore;
	}
	public void setIdSoggettoCreatore(int idSoggettoCreatore) {
		this.idSoggettoCreatore = idSoggettoCreatore;
	}
	public String getCognomeSoggettoCreatore() {
		return cognomeSoggettoCreatore;
	}
	public void setCognomeSoggettoCreatore(String cognomeSoggettoCreatore) {
		this.cognomeSoggettoCreatore = cognomeSoggettoCreatore;
	}
	public String getNomeSoggettoCreatore() {
		return nomeSoggettoCreatore;
	}
	public void setNomeSoggettoCreatore(String nomeSoggettoCreatore) {
		this.nomeSoggettoCreatore = nomeSoggettoCreatore;
	}
	public String getDtCreazioneDomanda() {
		return dtCreazioneDomanda;
	}
	public void setDtCreazioneDomanda(String dtCreazioneDomanda) {
		this.dtCreazioneDomanda = dtCreazioneDomanda;
	}
	public int getIdSoggettoInvio() {
		return idSoggettoInvio;
	}
	public void setIdSoggettoInvio(int idSoggettoInvio) {
		this.idSoggettoInvio = idSoggettoInvio;
	}
	public String getCognomeSoggettoInvio() {
		return cognomeSoggettoInvio;
	}
	public void setCognomeSoggettoInvio(String cognomeSoggettoInvio) {
		this.cognomeSoggettoInvio = cognomeSoggettoInvio;
	}
	public String getNomeSoggettoInvio() {
		return nomeSoggettoInvio;
	}
	public void setNomeSoggettoInvio(String nomeSoggettoInvio) {
		this.nomeSoggettoInvio = nomeSoggettoInvio;
	}
	public String getDtInvioDomanda() {
		return dtInvioDomanda;
	}
	public void setDtInvioDomanda(String dtInvioDomanda) {
		this.dtInvioDomanda = dtInvioDomanda;
	}
	public int getIdSoggettoBeneficiario() {
		return idSoggettoBeneficiario;
	}
	public void setIdSoggettoBeneficiario(int idSoggettoBeneficiario) {
		this.idSoggettoBeneficiario = idSoggettoBeneficiario;
	}
	public String getDenominazioneSoggettoBeneficiario() {
		return denominazioneSoggettoBeneficiario;
	}
	public void setDenominazioneSoggettoBeneficiario(
			String denominazioneSoggettoBeneficiario) {
		this.denominazioneSoggettoBeneficiario = denominazioneSoggettoBeneficiario;
	}
	public int getIdSportelloBando() {
		return idSportelloBando;
	}
	public void setIdSportelloBando(int idSportelloBando) {
		this.idSportelloBando = idSportelloBando;
	}
	public String getNumAtto() {
		return numAtto;
	}
	public void setNumAtto(String numAtto) {
		this.numAtto = numAtto;
	}
	public String getDtAtto() {
		return dtAtto;
	}
	public void setDtAtto(String dtAtto) {
		this.dtAtto = dtAtto;
	}
	public String getDtAperturaSportello() {
		return dtAperturaSportello;
	}
	public void setDtAperturaSportello(String dtAperturaSportello) {
		this.dtAperturaSportello = dtAperturaSportello;
	}
	public String getDtChiusuraSportello() {
		return dtChiusuraSportello;
	}
	public void setDtChiusuraSportello(String dtChiusuraSportello) {
		this.dtChiusuraSportello = dtChiusuraSportello;
	}
	public int getIdBando() {
		return idBando;
	}
	public void setIdBando(int idBando) {
		this.idBando = idBando;
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
	public int getIdClassificazioneAssePrioritario() {
		return idClassificazioneAssePrioritario;
	}
	public void setIdClassificazioneAssePrioritario(
			int idClassificazioneAssePrioritario) {
		this.idClassificazioneAssePrioritario = idClassificazioneAssePrioritario;
	}
	public String getCodiceAssePrioritario() {
		return codiceAssePrioritario;
	}
	public void setCodiceAssePrioritario(String codiceAssePrioritario) {
		this.codiceAssePrioritario = codiceAssePrioritario;
	}
	public String getDescrizioneAssePrioritario() {
		return descrizioneAssePrioritario;
	}
	public void setDescrizioneAssePrioritario(String descrizioneAssePrioritario) {
		this.descrizioneAssePrioritario = descrizioneAssePrioritario;
	}
	public int getIdClassificazionePrioritaInvestimento() {
		return idClassificazionePrioritaInvestimento;
	}
	public void setIdClassificazionePrioritaInvestimento(
			int idClassificazionePrioritaInvestimento) {
		this.idClassificazionePrioritaInvestimento = idClassificazionePrioritaInvestimento;
	}
	public String getCodicePrioritaInvestimento() {
		return codicePrioritaInvestimento;
	}
	public void setCodicePrioritaInvestimento(String codicePrioritaInvestimento) {
		this.codicePrioritaInvestimento = codicePrioritaInvestimento;
	}
	public String getDescrizionePrioritaInvestimento() {
		return descrizionePrioritaInvestimento;
	}
	public void setDescrizionePrioritaInvestimento(
			String descrizionePrioritaInvestimento) {
		this.descrizionePrioritaInvestimento = descrizionePrioritaInvestimento;
	}
	public int getIdClassificazioneAzione() {
		return idClassificazioneAzione;
	}
	public void setIdClassificazioneAzione(int idClassificazioneAzione) {
		this.idClassificazioneAzione = idClassificazioneAzione;
	}
	public String getCodiceAzione() {
		return codiceAzione;
	}
	public void setCodiceAzione(String codiceAzione) {
		this.codiceAzione = codiceAzione;
	}
	public String getDescrizioneAzione() {
		return descrizioneAzione;
	}
	public void setDescrizioneAzione(String descrizioneAzione) {
		this.descrizioneAzione = descrizioneAzione;
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
	public Date getDataRichiestaIntegrazione() {
		return dataRichiestaIntegrazione;
	}
	public Date getDataTermineIntegrazione() {
		return dataTermineIntegrazione;
	}
	public Boolean getFlagAbilitaIntegrazione() {
		return flagAbilitaIntegrazione;
	}
	public String getStatoRichiesta() {
		return statoRichiesta;
	}
	public void setDataRichiestaIntegrazione(Date dataRichiestaIntegrazione) {
		this.dataRichiestaIntegrazione = dataRichiestaIntegrazione;
	}
	public void setDataTermineIntegrazione(Date dataTermineIntegrazione) {
		this.dataTermineIntegrazione = dataTermineIntegrazione;
	}
	public void setFlagAbilitaIntegrazione(Boolean flagAbilitaIntegrazione) {
		this.flagAbilitaIntegrazione = flagAbilitaIntegrazione;
	}
	public void setStatoRichiesta(String statoRichiesta) {
		this.statoRichiesta = statoRichiesta;
	}
	
	public String getFlagProgettiComuni() {
		return flagProgettiComuni;
	}
	public void setFlagProgettiComuni(String flagProgettiComuni) {
		this.flagProgettiComuni = flagProgettiComuni;
	}
	public String getRuoloCapofilaProgettoComune() {
		return ruoloCapofilaProgettoComune;
	}
	public void setRuoloCapofilaProgettoComune(String ruoloCapofilaProgettoComune) {
		this.ruoloCapofilaProgettoComune = ruoloCapofilaProgettoComune;
	}
	public String getRuoloPartnerProgettoComune() {
		return ruoloPartnerProgettoComune;
	}
	public void setRuoloPartnerProgettoComune(String ruoloPartnerProgettoComune) {
		this.ruoloPartnerProgettoComune = ruoloPartnerProgettoComune;
	}
	
	
}
