/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.exception;

public class HelperException extends Exception {
	
	
	private static String ERROR_DEFAULT = "Si e' verificato un errore imprevisto";

	/**
	 * 
	 */
	private static final long serialVersionUID = 6131912890094532482L;



	public HelperException(String message){
		super(message);
	}

	public HelperException(String message, Throwable e){
		super(message, e);
	}

	public HelperException(Throwable e){
		super(e);
	}

}
