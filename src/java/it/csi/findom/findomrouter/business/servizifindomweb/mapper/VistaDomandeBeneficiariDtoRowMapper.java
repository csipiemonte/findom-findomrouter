/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.servizifindomweb.mapper;

import it.csi.findom.findomrouter.dto.serviziFindomWeb.VistaDomandeBeneficiariDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VistaDomandeBeneficiariDtoRowMapper extends GenericRowMapper {
	public VistaDomandeBeneficiariDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		VistaDomandeBeneficiariDto dto = new VistaDomandeBeneficiariDto();
		dto.setIdBando(rs.getInt("id_bando"));
		dto.setIdSportelloBando(rs.getInt("id_sportello_bando"));
		dto.setDtApertura(rs.getString("dt_apertura"));
		dto.setDtChiusura(rs.getString("dt_chiusura"));
		dto.setIdSoggettoBeneficiario(rs.getInt("id_soggetto_beneficiario"));
		dto.setNumDomandeBando(rs.getInt("num_domande_bando"));
		dto.setNumDomandeSportello(rs.getInt("num_domande_sportello"));
		return dto;
	}
}
