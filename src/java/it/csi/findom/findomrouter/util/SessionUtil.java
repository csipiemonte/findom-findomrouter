/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {

	/**
	 * Verifica l'esistenza di un oggetto in sessione con uno specifico valore.
	 * 
	 * @param key
	 *          : chiave dell'oggetto
	 * @param value
	 *          : valore dell'oggetto
	 * @param remove
	 *          : true rimuove l'oggetto dalla sessione
	 * */
	public static boolean existsKey(HttpServletRequest request, String key, String value, boolean remove) {
		HttpSession session = request.getSession();
		
		if (session.getAttribute(key) == null)
			return false;
		if (!session.getAttribute(key).equals(value))
			return false;
		if (remove)
			session.removeAttribute(key);
		return true;
	}
}
