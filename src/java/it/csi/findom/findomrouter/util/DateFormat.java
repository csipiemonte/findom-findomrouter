/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.util;


import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateFormat {

	
	public static java.util.Date format (String dateStr, String pattern) throws ParseException {
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		format.setLenient(false);
		date = format.parse(dateStr);
		return new java.util.Date(date.getTime());
	}


	public static String format (java.util.Date date, String pattern) {
		String formattedDate = "";
		if(date != null){
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			formattedDate = format.format(date);
		}
		return formattedDate;
	}
	
	public static String format (Object date, String pattern) {
		String formattedDate = "";
		if(date != null){
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			formattedDate = format.format(date);
		}
		return formattedDate;
	}

	public static boolean compareDate( Date dateBefore , Date dateAfter) {
		return (dateBefore.equals(dateAfter)||dateBefore.before(dateAfter))? true : false;

	}

	/**
	 * 1 => Gennaio
	 * 12 => Dicembre
	 * @param mese
	 * @return
	 */
	public static String getMese( int mese) {
		DateFormatSymbols dfs = new DateFormatSymbols(Locale.ITALY);
		return dfs.getMonths()[mese-1];
	}
	
	/**
	 * Restituisce la data corrente
	 * @return
	 */
	public static Date getCurrentDate() {
		return Calendar.getInstance().getTime() ;
	}
	
	public static java.sql.Date getCurrentSqlDate() {
		return  new java.sql.Date(Calendar.getInstance().getTime().getTime()) ;
	}
	
	
}
