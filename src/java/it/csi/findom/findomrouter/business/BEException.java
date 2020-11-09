/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.business;

/**
 * Eccezione rilanciata dallo strato spring nel caso in cui la condizione di 
 * errore non sia gestita attraverso un outcome_code e corrispondenti messaggi.
 * Serve per gestire situazioni non recuperabili o impreviste
 */
public class BEException extends Exception {
	public BEException(String msg, Exception nested) {
		super(msg, nested);
	}
}
