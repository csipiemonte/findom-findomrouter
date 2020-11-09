/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.servizifindomweb.mapper;

import it.csi.findom.findomrouter.dto.serviziFindomWeb.ShellDomandeDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShellDomandeDtoRowMapper extends GenericRowMapper {
	public ShellDomandeDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		ShellDomandeDto dto = new ShellDomandeDto();
		dto.setIdDomanda(rs.getInt("id_domanda"));
		dto.setIdSoggettoCreatore(rs.getInt("id_soggetto_creatore"));
		dto.setIdSoggettoBeneficiario(rs.getInt("id_soggetto_beneficiario"));
		dto.setIdSportelloBando(rs.getInt("id_sportello_bando"));
		dto.setIdSoggettoInvio(rs.getInt("id_soggetto_invio"));
		dto.setDataCreazione(rs.getString("dt_creazione"));
		dto.setIdTipolBeneficiario(rs.getInt("id_tipol_beneficiario"));
		dto.setDataInvioDomanda(rs.getString("dt_invio_domanda"));
		return dto;
	}
}
