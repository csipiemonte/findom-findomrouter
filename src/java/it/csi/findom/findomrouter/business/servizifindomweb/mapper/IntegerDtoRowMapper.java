/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.servizifindomweb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IntegerDtoRowMapper extends GenericRowMapper {

	public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
	
		Integer result = null;
		if (rs != null) {
			result = rs.getInt(1);
		} 
		return result;
	}

}
