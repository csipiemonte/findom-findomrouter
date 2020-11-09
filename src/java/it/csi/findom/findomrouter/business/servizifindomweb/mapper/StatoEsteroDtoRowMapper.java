/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.servizifindomweb.mapper;

import it.csi.findom.findomrouter.dto.serviziFindomWeb.StatoEsteroDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatoEsteroDtoRowMapper extends GenericRowMapper {
	public StatoEsteroDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		StatoEsteroDto dto = new StatoEsteroDto();
		dto.setCodice(rs.getString("codice"));
		dto.setSigla(rs.getString("sigla"));
		dto.setDescrizione(rs.getString("descrizione"));
		return dto;
	}
}
