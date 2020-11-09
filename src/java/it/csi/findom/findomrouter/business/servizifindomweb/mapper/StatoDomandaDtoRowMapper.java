/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.servizifindomweb.mapper;

import it.csi.findom.findomrouter.dto.serviziFindomWeb.StatoDomandaDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatoDomandaDtoRowMapper extends GenericRowMapper {
	public StatoDomandaDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		StatoDomandaDto dto = new StatoDomandaDto();
		dto.setCodice(rs.getString("codicestato"));
		dto.setDescrizione(rs.getString("descrizione"));
		dto.setDataInizioValidita(rs.getString("datainizio"));
		dto.setDataFineValidita(rs.getString("datafine"));
		return dto;
	}
}
