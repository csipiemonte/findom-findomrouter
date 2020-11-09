/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business.exception;

public class UserNotFoundException extends HelperException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2701123639037047617L;
	
	
	private final String ERROR_MESSAGE = "Utente non trovato"; 

	
	public UserNotFoundException(String message){
		super(message);
	}

	public UserNotFoundException(String message, Throwable e){
		super(message, e);
	}

	public UserNotFoundException(Throwable e){
		super(e);
	}
	

}
