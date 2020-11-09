/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.servizifindomweb.mapper;

import it.csi.findom.findomrouter.dto.serviziFindomWeb.TipolBeneficiariDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TipolBeneficiariDtoRowMapper extends GenericRowMapper {
	public TipolBeneficiariDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		TipolBeneficiariDto dto = new TipolBeneficiariDto();
		dto.setIdTipolBeneficiario(rs.getInt("id_tipol_beneficiario"));
		dto.setDescrizione(rs.getString("descrizione"));
		dto.setCodStereotipo(rs.getString("cod_stereotipo"));
		dto.setFlagPubblicoPrivato(rs.getInt("flag_pubblico_privato"));
		dto.setCodice(rs.getString("codice"));
		return dto;
	}
}
