/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.servizifindomweb.mapper;

import it.csi.findom.findomrouter.dto.serviziFindomWeb.RegolaDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RegolaDtoRowMapper extends GenericRowMapper {

	public RegolaDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		RegolaDto dto = new RegolaDto();
		dto.setIdRegola(rs.getInt("id_regola"));
		dto.setCodRegola(rs.getString("cod_regola"));
		dto.setDescrRegola(rs.getString("descr_regola"));
		dto.setCorpoRegola(rs.getString("corpo_regola"));
		
		return dto;
	}
}
