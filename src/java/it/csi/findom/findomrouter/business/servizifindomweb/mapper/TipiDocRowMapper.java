/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.servizifindomweb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TipiDocRowMapper extends GenericRowMapper {


	public String mapRow(ResultSet rs, int rowNum) throws SQLException {

		String result = null;
		if (rs != null) {
			result = rs.getString("tipi_doc");
		} 
		return result;
	}

}
