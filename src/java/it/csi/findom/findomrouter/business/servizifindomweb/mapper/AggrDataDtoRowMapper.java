/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.servizifindomweb.mapper;

import it.csi.findom.findomrouter.dto.serviziFindomWeb.AggrDataDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AggrDataDtoRowMapper  extends GenericRowMapper {

public AggrDataDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		
	AggrDataDto dto = new AggrDataDto();
		dto.setTemplateId(rs.getInt("template_id"));
		dto.setTemplateCode(rs.getString("template_code"));
		dto.setTemplateDescription(rs.getString("template_description"));
		dto.setTemplateName(rs.getString("template_name"));
		
		dto.setModelId(rs.getInt("model_id"));
		dto.setModelProgr(rs.getInt("model_progr"));
		dto.setUserId(rs.getString("user_id"));
		dto.setModelStateFk(rs.getString("model_state_fk"));
		dto.setModelName(rs.getString("model_name"));
		
		dto.setIdSoggettoCreatore(rs.getInt("id_soggetto_creatore"));
		dto.setIdSoggettoBeneficiario(rs.getInt("id_soggetto_beneficiario"));
		dto.setIdSportelloBando(rs.getInt("id_sportello_bando"));
		dto.setIdTipolBeneficiario(rs.getInt("id_tipol_beneficiario"));
		dto.setIdSoggettoInvio(rs.getInt("id_soggetto_invio"));
		dto.setDataCreazione(rs.getString("dt_creazione"));
		dto.setDataInvioDomanda(rs.getString("dt_invio_domanda"));
		
		dto.setDataAperturaSportello(rs.getString("dt_apertura"));
		dto.setDataChiusuraSportello(rs.getString("dt_chiusura"));
		return dto;
	}

}
