/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.servizifindomweb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.findom.findomrules.dto.ParametriRegola;

public class ParametriRegolaRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		ParametriRegola gr = new ParametriRegola();
		
		gr.setIdRegola(rs.getInt("id_regola"));
//		gr.setIdSportelloBando(rs.getInt("id_sportello_bando"));
		gr.setIdParametro(rs.getInt("id_parametro"));
		gr.setOrdineParametro(rs.getInt("ordine"));
		gr.setValoreParametro(rs.getString("valore_parametro"));
		gr.setCodiceParametro(rs.getString("codice"));
//		gr.setDescrizioneParametro(rs.getString("descr_parametro"));
		
//		gr.setDataInizioValidita(rs.getDate("dt_inizio"));
//		gr.setDataFineValidita(rs.getDate("dt_fine"));
		
		return gr;
	}

}
