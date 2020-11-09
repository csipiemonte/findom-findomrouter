/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.exception;

import it.csi.csi.wrapper.UserException;

public class ServiziFindomWebException extends UserException{

	static final long serialVersionUID = 1;

	public ServiziFindomWebException(String msg, String nestedExcClassName,
			String nestedExcMessage, String nestedExcStackTrace) {
		super(msg, nestedExcClassName, nestedExcMessage, nestedExcStackTrace);
	}
	
	public ServiziFindomWebException(String msg, String nestedExcClassName,
			String nestedExcMessage) {
		super(msg, nestedExcClassName, nestedExcMessage);
	}
	
	public ServiziFindomWebException(String msg, Throwable nested) {
		super(msg, nested);
	}

	public ServiziFindomWebException(String msg) {
		super(msg);
	}
	
}
