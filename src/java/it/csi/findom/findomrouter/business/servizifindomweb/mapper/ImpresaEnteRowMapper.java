/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.servizifindomweb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import it.csi.findom.findomrouter.presentation.vo.ImpresaEnte;

public class ImpresaEnteRowMapper extends GenericRowMapper {
	public ImpresaEnte mapRow(ResultSet rs, int rowNum) throws SQLException {
		ImpresaEnte dto = new ImpresaEnte();
		dto.setIdSoggetto(rs.getInt("id_soggetto"));
		dto.setCodiceFiscale(rs.getString("cod_fiscale"));
		dto.setDenominazione(rs.getString("denominazione"));
		dto.setIdFormaGiuridica(rs.getInt("id_forma_giuridica"));
		dto.setCognome(rs.getString("cognome"));
		dto.setNome(rs.getString("nome"));
		dto.setSiglaNazione(rs.getString("sigla_nazione"));
		dto.setCodFormaGiuridica(rs.getString("cod_forma_giuridica"));
		dto.setDescrFormaGiuridica(rs.getString("descr_forma_giuridica"));
		dto.setFlagPubblicoPrivato(rs.getInt("flag_pubblico_privato"));
		return dto;
	}
}
