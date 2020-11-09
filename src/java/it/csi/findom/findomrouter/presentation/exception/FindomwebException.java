/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.exception;

public class FindomwebException  extends Throwable {
	
	static private final long serialVersionUID = 1L;

	public FindomwebException(){
		super();
	}
	
	public FindomwebException(String message){
		super(message);
	}
	
	public FindomwebException(String message, Throwable e){
		super(message, e);
	}
	
	public FindomwebException(Throwable e){
		super(e);
	}
}
