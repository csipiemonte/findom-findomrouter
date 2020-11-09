/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.servizifindomweb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FileDtoRowMapper extends GenericRowMapper {
	
	public byte[] mapRow(ResultSet rs, int rowNum) throws SQLException {
		return rs.getBytes("file_pdf");
	}
}
