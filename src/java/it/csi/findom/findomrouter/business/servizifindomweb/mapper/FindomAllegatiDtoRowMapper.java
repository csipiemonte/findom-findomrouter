/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.servizifindomweb.mapper;

import it.csi.findom.findomrouter.dto.serviziFindomWeb.FindomAllegatiDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FindomAllegatiDtoRowMapper extends GenericRowMapper {
	public FindomAllegatiDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		FindomAllegatiDto dto = new FindomAllegatiDto();
		dto.setIdBando(rs.getInt("id_bando"));
		dto.setIdAllegato(rs.getInt("id_allegato"));
		dto.setDescrizione(rs.getString("descrizione"));
		dto.setFlagObbligatorio(rs.getString("flag_obbligatorio"));
		dto.setDtInizio(rs.getString("dt_inizio"));
		dto.setDtFine(rs.getString("dt_fine"));
		dto.setFlagFirmaDigitale(rs.getString("flag_firma_digitale"));
		return dto;
	}
}
