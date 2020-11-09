/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.util;

public class ControlPartitaIVA {

	public static boolean controllaPartitaIVA(String pi){
		
		int i, c, s;
		if( pi.length() == 0 ) {
		   return false;
		}
			
		//devono+ essere 11 caratteri
		if( pi.length() != 11 ) {
		   return false;
		}
			
		//deve contenere solo cifre
		for(i=0; i<11; i++ ){
			if( pi.charAt(i) < '0' || pi.charAt(i) > '9' ){
			   return false;
			}
		}
			
		//codice di controllo
		s = 0;
		for(i=0; i<=9; i+=2 ){
			s += pi.charAt(i) - '0';
		}			
		for(i=1; i<=9; i+=2 ){
			c = 2*( pi.charAt(i) - '0' );
			if( c > 9 )  c = c - 9;
			s += c;
		}
		if((10 - s%10 )%10 != pi.charAt(10) - '0'){
		  return false; 
		}
		return true;
	}
}
