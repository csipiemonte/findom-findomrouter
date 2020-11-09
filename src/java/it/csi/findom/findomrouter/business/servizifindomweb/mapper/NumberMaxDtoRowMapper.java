/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.servizifindomweb.mapper;

import it.csi.findom.findomrouter.dto.serviziFindomWeb.NumberMaxDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NumberMaxDtoRowMapper extends GenericRowMapper {
	public NumberMaxDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		NumberMaxDto dto = new NumberMaxDto();
		dto.setNumMaxDomandeBando(rs.getInt("numMaxDomandeBando"));
		dto.setNumMaxDomandeSportello(rs.getInt("numMaxDomandeSportello"));
		dto.setIdBando(rs.getInt("id_bando"));
		dto.setIdDomanda(rs.getInt("id_domanda"));
		dto.setIdSportelloBando(rs.getInt("id_sportello_bando"));
		return dto;
	}

}
