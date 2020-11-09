/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.util;

import java.math.BigDecimal;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;



public class ControlIban {
	
	
  /**
   * <p>controlla la validità formale dell' IBAN ricevuto come stringa.<br>
   * presuppone che la stringa ricevuta sia significativa e contenga 27 caratteri.</p>
   *
   * @param iban  la stringa da controllare.
   * @return <code>true</code> se il codice IBAN e' formalmente valido
   */
	public static boolean isValid(String iban) {
		iban = iban.toUpperCase();
		String rearrangedIban = StringUtils.substring(iban, 4).concat(StringUtils.substring(iban, 0, 4));
		StringCharacterIterator iterator = new StringCharacterIterator(rearrangedIban);
		StringBuilder builder = new StringBuilder();
		for (char ch = iterator.first(); ch != CharacterIterator.DONE; ch = iterator.next()) {
			if (ch >= 'A' && ch <= 'Z') {
				int valorePerIban = ch - 55;
				builder.append(valorePerIban);
			} else {
				builder.append(ch);
			}
		}
		BigDecimal ibanConvertito = new BigDecimal(builder.toString());
		BigDecimal resto = ibanConvertito.remainder(BigDecimal.valueOf(97));
		if (resto.intValue() == 1) {
			return true;
		}
		return false;
	}
	
	
}
