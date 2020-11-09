/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.servizifindomweb.mapper;

import it.csi.findom.findomrouter.dto.serviziFindomWeb.RegoleVerificaFirmaDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RegoleVerificaFirmaDtoRowMapper extends GenericRowMapper {

	public RegoleVerificaFirmaDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		RegoleVerificaFirmaDto dto = new RegoleVerificaFirmaDto();
		dto.setFlagVerificaFirma(rs.getString("flag_verifica_firma"));
		dto.setTipologiaVerificaFirma(rs.getString("tipologia_verifica_firma"));
		
		return dto;
	}
}
