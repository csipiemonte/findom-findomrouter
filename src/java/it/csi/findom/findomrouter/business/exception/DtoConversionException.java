/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.exception;;

public class DtoConversionException extends Exception {

	private static final long serialVersionUID = -1924852710233061982L;

	public DtoConversionException() {
		super();

	}

	public DtoConversionException(String message, Throwable cause) {
		super(message, cause);

	}

	public DtoConversionException(String message) {
		super(message);

	}

	public DtoConversionException(Throwable cause) {
		super(cause);

	}



}
