/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.validator;

import org.apache.commons.validator.routines.RegexValidator;
import org.apache.log4j.Logger;
import it.csi.findom.findomrouter.presentation.util.Constants;


public class CodiceFiscaleValidator extends RegexValidator {
	
	private static final long serialVersionUID = 2912577412308740316L;
	
	protected static final Logger LOG = Logger.getLogger(Constants.APPLICATION_CODE + ".validator"); 

	
	private static final String regex = "(?i)^[A-Z]{6}[0-9]{2}[A-Z]{1}[0-9]{2}[A-Z]{1}[0-9]{3}[A-Z]{1}$" +
			"|" +
			"^[A-Z]{6}[0-9LMNPQRSTUV]{2}[ABCDEHLMPRST]{1}[0-9LMNPQRSTUV]{2}[A-Z]{1}[0-9LMNPQRSTUV]{3}[A-Z]{1}$";

	
	public CodiceFiscaleValidator() {
		super(regex, false);
	}

	

	

	


	


}
