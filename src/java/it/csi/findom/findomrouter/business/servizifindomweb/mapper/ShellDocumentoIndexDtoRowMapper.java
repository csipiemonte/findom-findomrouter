/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.servizifindomweb.mapper;

import it.csi.findom.findomrouter.dto.serviziFindomWeb.ShellDocumentoIndexDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShellDocumentoIndexDtoRowMapper  extends GenericRowMapper {

public ShellDocumentoIndexDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		
	ShellDocumentoIndexDto dto = new ShellDocumentoIndexDto();
		
		dto.setIdDocumentoIndex(rs.getInt("id_documento_index"));
		dto.setIdDomanda(rs.getInt("id_domanda"));
		dto.setUuidNodo(rs.getString("uuid_nodo"));
		dto.setRepository(rs.getString("repository"));
		dto.setNomeFile(rs.getString("nome_file"));
		dto.setMessageDigest(rs.getString("message_digest"));
		dto.setDtVerificaFirma(rs.getString("dt_verifica_firma"));
		dto.setDtMarcaTemporale(rs.getString("dt_marca_temporale"));
		dto.setDtInserimento(rs.getString("dt_inserimento"));
		dto.setIdStatoDocumentoIndex(rs.getInt("id_stato_documento_index"));
		dto.setIdAllegato(rs.getInt("id_allegato"));
		dto.setIdSoggetto(rs.getInt("id_soggetto"));
		return dto;
	}
}
