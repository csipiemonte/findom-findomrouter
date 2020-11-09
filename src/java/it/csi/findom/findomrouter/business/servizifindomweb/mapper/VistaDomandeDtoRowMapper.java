/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.servizifindomweb.mapper;

import it.csi.findom.findomrouter.dto.serviziFindomWeb.VistaDomandeDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VistaDomandeDtoRowMapper extends GenericRowMapper {
	public VistaDomandeDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		VistaDomandeDto dto = new VistaDomandeDto();
		dto.setIdDomanda(rs.getInt("id_domanda"));
		dto.setCodStatoDomanda(rs.getString("cod_stato_domanda"));
		dto.setStatoDomanda(rs.getString("stato_domanda"));
		dto.setIdTipolBeneficiario(rs.getInt("id_tipol_beneficiario"));
		dto.setDescrizioneTipolBeneficiario(rs.getString("descrizione_tipol_beneficiario"));
		dto.setTipoEnteBeneficiario(rs.getString("tipo_ente_beneficiario"));
		dto.setCodStereotipo(rs.getString("cod_stereotipo"));
		dto.setIdSoggettoCreatore(rs.getInt("id_soggetto_creatore"));
		dto.setCognomeSoggettoCreatore(rs.getString("cognome_soggetto_creatore"));
		dto.setNomeSoggettoCreatore(rs.getString("nome_soggetto_creatore"));
		dto.setDtCreazioneDomanda(rs.getString("dt_creazione_domanda"));
		dto.setIdSoggettoInvio(rs.getInt("id_soggetto_invio"));
		dto.setCognomeSoggettoInvio(rs.getString("cognome_soggetto_invio"));
		dto.setNomeSoggettoInvio(rs.getString("nome_soggetto_invio"));
		dto.setDtInvioDomanda(rs.getString("dt_invio_domanda"));
		dto.setIdSoggettoBeneficiario(rs.getInt("id_soggetto_beneficiario"));
		dto.setDenominazioneSoggettoBeneficiario(rs.getString("denominazione_soggetto_beneficiario"));
		dto.setIdSportelloBando(rs.getInt("id_sportello_bando"));
		dto.setNumAtto(rs.getString("num_atto"));
		dto.setDtAtto(rs.getString("dt_atto"));
		dto.setDtAperturaSportello(rs.getString("dt_apertura_sportello"));
		dto.setDtChiusuraSportello(rs.getString("dt_chiusura_sportello"));
		dto.setIdBando(rs.getInt("id_bando"));
		dto.setDescrBando(rs.getString("descr_bando"));
		dto.setDescrBreveBando(rs.getString("descr_breve_bando"));
		dto.setIdNormativa(rs.getInt("id_normativa"));
		dto.setNormativa(rs.getString("normativa"));
		dto.setIdClassificazioneAssePrioritario(rs.getInt("id_classificazione_asse_prioritario"));
		dto.setCodiceAssePrioritario(rs.getString("codice_asse_prioritario"));
		dto.setDescrizioneAssePrioritario(rs.getString("descrizione_asse_prioritario"));
		dto.setIdClassificazionePrioritaInvestimento(rs.getInt("id_classificazione_priorita_investimento"));
		dto.setCodicePrioritaInvestimento(rs.getString("codice_priorita_investimento"));
		dto.setDescrizionePrioritaInvestimento(rs.getString("descrizione_priorita_investimento"));
		dto.setIdClassificazioneAzione(rs.getInt("id_classificazione_azione"));
		dto.setCodiceAzione(rs.getString("codice_azione"));
		dto.setDescrizioneAzione(rs.getString("descrizione_azione"));
		dto.setFlagBandoDematerializzato(rs.getString("flag_demat"));
		dto.setTipoFirma(rs.getString("tipo_firma"));
		dto.setIdSoggettoConclusione(rs.getInt("id_soggetto_conclusione"));
		dto.setCognomeSoggettoConclusione(rs.getString("cognome_soggetto_conclusione"));
		dto.setNomeSoggettoConclusione(rs.getString("nome_soggetto_conclusione"));
		dto.setDtConclusioneDomanda(rs.getString("dt_conclusione"));
		dto.setNumeroProtocollo(rs.getString("numero_protocollo"));
		dto.setDtProtocolloDomanda(rs.getString("data_protocollo"));
		dto.setDtClassificazioneDomanda(rs.getString("data_classificazione"));
		dto.setDtRichiestaIntegrazione(rs.getDate("dt_richiesta_integrazione"));
		dto.setDtTermineIntegrazione(rs.getDate("dt_termine_integrazione"));
		dto.setFlagAbilitaIntegrazione(rs.getBoolean("flag_abilita_integrazione"));		 
		dto.setFlagProgettiComuni( (rs.getBoolean("flag_progetti_comuni") == true) ? "true" : "false");
		//per avere l'informazione se il beneficiario e' capofila o partner o nessuno dei due (ruolo non ancora definito in caso di progetti comuni,
		//o bando non di tipo progetto comune)
		dto.setRuoloCapofilaProgettoComune( (rs.getInt("id_capofila_acronimo") == 0) ? "false" : "true");
		dto.setRuoloPartnerProgettoComune( (rs.getInt("id_partner") == 0) ? "false" : "true");
		dto.setIdAreaTematica(rs.getInt("id_area_tematica"));
		dto.setDescrizioneAreaTematica(rs.getString("descrizione_area_tematica"));
		return dto;
	}
}
