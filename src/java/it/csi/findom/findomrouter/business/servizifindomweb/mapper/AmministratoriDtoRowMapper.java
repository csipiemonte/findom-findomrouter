/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.servizifindomweb.mapper;

import it.csi.findom.findomrouter.dto.serviziFindomWeb.AmministratoriDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AmministratoriDtoRowMapper extends GenericRowMapper {
	public AmministratoriDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		AmministratoriDto dto = new AmministratoriDto();
		dto.setIdAmministratore(rs.getInt("id_amministratore"));
		dto.setCodiceFiscale(rs.getString("cod_fiscale"));
		dto.setCognome(rs.getString("cognome"));
		dto.setNome(rs.getString("nome"));
		dto.setIdEnteAppartenenza(rs.getInt("id_ente_appartenenza"));
		dto.setDataInizio(rs.getString("dt_inizio"));
		dto.setDataFine(rs.getString("dt_fine"));
		return dto;
	}
}
