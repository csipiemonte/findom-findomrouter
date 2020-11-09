/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.servizifindomweb.mapper;

import it.csi.findom.findomrouter.dto.serviziFindomWeb.VistaSportelliAttiviDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VistaSportelliAttiviDtoRowMapper extends GenericRowMapper {
	public VistaSportelliAttiviDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		VistaSportelliAttiviDto dto = new VistaSportelliAttiviDto();
		dto.setIdSportelloBando(rs.getInt("id_sportello_bando"));
		dto.setDtApertura(rs.getString("dt_apertura"));
		dto.setDtChiusura(rs.getString("dt_chiusura"));
		dto.setNumMaxDomandeSportello(rs.getString("num_max_domande_sportello"));
		dto.setIdBando(rs.getInt("id_bando"));
		dto.setNumMaxDomandeBando(rs.getString("num_max_domande_bando"));
		dto.setDescrizioneBando(rs.getString("descrizione_bando"));
		dto.setDescrizioneBreveBando(rs.getString("descrizione_breve_bando"));
		dto.setCodiceAzione(rs.getString("codice_azione"));
		dto.setIdNormativa(rs.getInt("id_normativa"));
		dto.setNormativa(rs.getString("normativa"));
		dto.setIdAreaTematica(rs.getInt("id_area_tematica"));
		dto.setDescrizioneAreaTematica(rs.getString("descrizione_area_tematica"));
		return dto;
	}
}
