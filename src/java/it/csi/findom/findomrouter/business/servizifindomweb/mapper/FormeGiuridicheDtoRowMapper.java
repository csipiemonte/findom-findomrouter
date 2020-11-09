/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.servizifindomweb.mapper;

import it.csi.findom.findomrouter.dto.serviziFindomWeb.FormeGiuridicheDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FormeGiuridicheDtoRowMapper extends GenericRowMapper {
	public FormeGiuridicheDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		FormeGiuridicheDto dto = new FormeGiuridicheDto();
		dto.setIdFormaGiuridica(rs.getInt("id_forma_giuridica"));
		dto.setCodFormaGiuridica(rs.getString("cod_forma_giuridica"));
		dto.setDescrFormaGiuridica(rs.getString("descr_forma_giuridica"));
		dto.setFlagPubblicoPrivato(rs.getInt("flag_pubblico_privato"));
		return dto;
	}
}
