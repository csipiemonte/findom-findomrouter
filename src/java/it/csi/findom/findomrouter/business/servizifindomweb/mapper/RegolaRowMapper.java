/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.servizifindomweb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.csi.findom.findomrules.dto.Regola;

public class RegolaRowMapper  implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Regola gr = new Regola();
		
		gr.setIdRegola(rs.getInt("id_regola"));
		gr.setCodiceRegola(rs.getString("cod_regola"));
		gr.setDescrizioneRegola(rs.getString("descr_regola"));
		gr.setCorpoRegola(rs.getString("corpo_regola"));
		gr.setIdTipoRegola(rs.getInt("id_tipo_regola"));
//		gr.setIdSportelloBando(rs.getInt("id_sportello_bando"));
		gr.setCodiceTipoRegola(rs.getString("descr_breve"));
		gr.setDescrizioneTipoRegola(rs.getString("descrizione"));
		
		return gr;
	}
}
