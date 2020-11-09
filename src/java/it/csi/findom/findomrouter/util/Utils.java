/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.util;

import it.csi.findom.findomrouter.presentation.action.BaseAction;
import it.csi.findom.findomrouter.presentation.util.StringUtils;

@SuppressWarnings("rawtypes")
public abstract class Utils extends BaseAction implements Comparable {

	
	public static String quote(Object obj) {
		String res;
		if (obj != null) {
			if (obj instanceof String) {
				String str = (String) obj;
				res = StringUtils.isBlank(str) ? "blank" : "\"" + str + "\"";
			} else {
				res = obj.toString();
			}
		} else {
			res = "null";
		}
		return res;
	}
}
