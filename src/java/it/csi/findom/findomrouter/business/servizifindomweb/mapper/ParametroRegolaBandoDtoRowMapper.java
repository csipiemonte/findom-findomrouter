/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.servizifindomweb.mapper;

import it.csi.findom.findomrouter.dto.serviziFindomWeb.ParametroRegolaBandoDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ParametroRegolaBandoDtoRowMapper extends GenericRowMapper {

	public ParametroRegolaBandoDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		ParametroRegolaBandoDto dto = new ParametroRegolaBandoDto();
		dto.setTemplateId(rs.getInt("template_id"));
		dto.setDescrBreveBando(rs.getString("descr_breve_bando"));
		dto.setCodRegola(rs.getString("cod_regola"));
		dto.setCodParametro(rs.getString("cod_parametro"));
		dto.setValoreParametro(rs.getString("valore_parametro"));
		
		return dto;
	}
}

