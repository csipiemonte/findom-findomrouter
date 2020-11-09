/*******************************************************************************
 * Copyright Regione Piemonte - 2020
 * SPDX-License-Identifier: EUPL-1.2-or-later
 ******************************************************************************/
package it.csi.findom.findomrouter.presentation.util;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.log4j.Logger;

/**
 * <p>
 * Title: Servizi per Back-Office
 * </p>
 * <p>
 * Description: Servizi per Back-Office
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: CSI
 * </p>
 * 
 * @author
 * @version 1.0
 */
public class DateUtil implements Cloneable, Serializable {

	protected static final Logger log = Logger.getLogger(Constants.APPLICATION_CODE + ".presentation.util");

	public DateUtil() {
		GregorianCalendar todaysDate = new GregorianCalendar();
		year = todaysDate.get(Calendar.YEAR);
		month = todaysDate.get(Calendar.MONTH) + 1;
		day = todaysDate.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Constructs a specific date
	 * 
	 * @param yyyy
	 *          year (full year, e.g., 1996, <i>not</i> starting from 1900)
	 * @param m
	 *          month
	 * @param d
	 *          day
	 * @exception IllegalArgumentException
	 *              if yyyy m d not a valid date
	 */

	public DateUtil(final int yyyy, final int m, final int d) {
		year = yyyy;
		month = m;
		day = d;
		// if (!isValid())
		// throw new IllegalArgumentException();
	}

	/**
	 * Advances this day by n days. For example. d.advance(30) adds thirdy days to
	 * d
	 * 
	 * @param n
	 *          the number of days by which to change this day (can be < 0)
	 */

	public void advance(int n) {
		fromJulian(toJulian() + n);
	}

	/**
	 * Gets the day of the month
	 * 
	 * @return the day of the month (1...31)
	 */

	public int getDateUtil() {
		return day;
	}

	/**
	 * Gets the month
	 * 
	 * @return the month (1...12)
	 */

	public int getMonth()

	{
		return month;
	}

	/**
	 * Gets the year
	 * 
	 * @return the year (counting from 0, <i>not</i> from 1900)
	 */

	public int getYear() {
		return year;
	}

	/**
	 * Gets the weekday
	 * 
	 * @return the weekday ({@link DateUtil#SUNDAY}, ...,
	 *         {@link DateUtil#SATURDAY})
	 */

	public int weekday() {
		return (toJulian() + 1) % 7 + 1;
	}

	/**
	 * The number of days between this and day parameter
	 * 
	 * @param b
	 *          any date
	 * @return the number of days between this and day parameter and b (> 0 if
	 *         this day comes after b)
	 */

	public int daysBetween(DateUtil b) {
		return toJulian() - b.toJulian();
	}

	/**
	 * A string representation of the day
	 * 
	 * @return a string representation of the day
	 */

	public String toString() {
		return "DateUtil[" + year + "," + month + "," + day + "]";
	}

	/**
	 * Makes a bitwise copy of a DateUtil object
	 * 
	 * @return a bitwise copy of a DateUtil object
	 */

	public Object clone() {
		try {
			return super.clone();
		}
		catch (CloneNotSupportedException e) { // this shouldn't happen, since
			// we are Cloneable
			log.error("[DateUtil::clone] CloneNotSupportedException:"+e);
			return null;
		}
	}

	/**
	 * Compares this DateUtil against another object
	 * 
	 * @param obj
	 *          another object
	 * @return true if the other object is identical to this DateUtil object
	 */

	public boolean equals(Object obj) {
		if (!getClass().equals(obj.getClass())) {
			return false;
		}
		DateUtil b = (DateUtil) obj;
		return day == b.day && month == b.month && year == b.year;
	}

	// Sonar : se si fa l'averride del metodo equals allora bisogna fare l'override anche del metodo hashCode
	public int hashCode(){
		return super.hashCode();
	}
	
	/**
	 * Computes the number of days between two dates
	 * 
	 * @return true iff this is a valid date
	 */

	public boolean isValid() {
		DateUtil t = new DateUtil();
		t.fromJulian(this.toJulian());
		return t.day == day && t.month == month && t.year == year;
	}

	/**
	 * @return The Julian day number that begins at noon of this day Positive year
	 *         signifies A.D., negative year B.C. Remember that the year after 1
	 *         B.C. was 1 A.D.
	 * 
	 *         A convenient reference point is that May 23, 1968 noon is Julian
	 *         day 2440000.
	 * 
	 *         Julian day 0 is a Monday.
	 * 
	 *         This algorithm is from Press et al., Numerical Recipes in C, 2nd
	 *         ed., Cambridge University Press 1992
	 */

	private int toJulian() {
		int jy = year;
		if (year < 0) {
			jy++;
		}
		int jm = month;
		if (month > 2) {
			jm++;
		}
		else {
			jy--;
			jm += 13;
		}
		int jul = (int) (java.lang.Math.floor(365.25 * jy) + java.lang.Math.floor(30.6001 * jm) + day + 1720995.0);

		int IGREG = 15 + 31 * (10 + 12 * 1582);
		// Gregorian Calendar adopted Oct. 15, 1582

		if (day + 31 * (month + 12 * year) >= IGREG)
		// change over to Gregorian calendar
		{
			int ja = (int) (0.01 * jy);
			jul += 2 - ja + (int) (0.25 * ja);
		}
		return jul;
	}

	/**
	 * Converts a Julian day to a calendar date
	 * 
	 * This algorithm is from Press et al., Numerical Recipes in C, 2nd ed.,
	 * Cambridge University Press 1992
	 * 
	 * @param j
	 *          the Julian date
	 */

	private void fromJulian(int j) {
		int ja = j;

		int JGREG = 2299161;
		/*
		 * the Julian date of the adoption of the Gregorian calendar
		 */

		if (j >= JGREG)
		/*
		 * cross-over to Gregorian Calendar produces this correction
		 */
		{
			int jalpha = (int) (((float) (j - 1867216) - 0.25) / 36524.25);
			ja += 1 + jalpha - (int) (0.25 * jalpha);
		}
		int jb = ja + 1524;
		int jc = (int) (6680.0 + ((float) (jb - 2439870) - 122.1) / 365.25);
		int jd = (int) (365 * jc + (0.25 * jc));
		int je = (int) ((jb - jd) / 30.6001);
		day = jb - jd - (int) (30.6001 * je);
		month = je - 1;
		if (month > 12) {
			month -= 12;
		}
		year = jc - 4715;
		if (month > 2) {
			--year;
		}
		if (year <= 0) {
			--year;
		}
	}

	public static int SUNDAY = 1;
	public static int MONDAY = 2;
	public static int TUESDAY = 3;
	public static int WEDNESDAY = 4;
	public static int THURSDAY = 5;
	public static int FRIDAY = 6;
	public static int SATURDAY = 7;

	/** @serial */
	private int day;
	/** @serial */
	private int month;
	/** @serial */
	private int year;

	public static String giveFormattedDate(Long date, String userFormatType) throws IllegalArgumentException {
		if (null == date || date.longValue() == 0) {
			return "";
		}

		CustomDate customDate = new CustomDate();
		int formatType = customDate.getFormatType(userFormatType);
		String tmp = null;
		try {
			tmp = customDate.format(date.longValue(), formatType);
			if (tmp == null) {
				tmp = "";
			}
		} catch (Exception e0) {
			log.error("[DateUtil::fromJulian] Exception:"+e0);
			throw new IllegalArgumentException("Format :" + formatType + " not supported");
		}
		return tmp;
	}

	public static long getDifference(Date dataConfronto) {

		long dayDiff = 0;
		long numbOfMilliInDateUtil = 86400000;

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(dataConfronto);
		Calendar calendarOdierno = new GregorianCalendar();

		// Controllo che la data di confronto non viene prima della data di
		// sistema(odierna)
		if (dataConfronto != null && calendar.before(calendarOdierno)) {
			dayDiff = -1;
		}
		else {
			dayDiff = (calendar.getTime().getTime() - calendarOdierno.getTime().getTime()) / numbOfMilliInDateUtil;
		}

		return dayDiff;
	}
	
	public static String getDataAttuale() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");		
		Date date = new Date();
		String dateString = dateFormat.format(date); 
		return dateString;
	}
	public static Date getDataDaStringa(String dataDaConvertire) {
		if (StringUtils.isEmpty(dataDaConvertire)) return null;
		DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");		
		Date date = null;
		try {
			date = dateFormatter.parse(dataDaConvertire);
		}
		catch (ParseException ex1) {
			return null;
		}
		return date;
	}
	public static Date getDate(String dataDaConvertire) {
		if (StringUtils.isEmpty(dataDaConvertire)) return null;
		String dateFormat = "dd/MM/yyyy";
		SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);
		Date date = null;
		try {
			date = dateFormatter.parse(dataDaConvertire);
		}
		catch (ParseException ex1) {
			return null;
		}
		
		return date;
	}

	public static String getDate(Date data) {
		String newData = "";

		if (data != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			newData = sdf.format(data);
		}
		return newData;
	}

	public static String getDataOdierna() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(new Date());
	}

	public static Date getDataOggi() {
		Date date = new Date();
		try {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String s = formatter.format(date);
			date = (Date) formatter.parse(s);
		}
		catch (Exception e) {
			return new Date();
		}
		return date;
	}

	public static boolean isValidDate(String data){
		
			return isValideDate("dd/MM/yyyy", data);
	}

	public static boolean isValideDate(String pattern, String data) {
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		try {

			dateFormat.parse(data);
			log.info("Ho parsificato la data con il pattern");

			// Controllo se i campi data sono coerenti
			if (data == null || data.length() != 10) {
				return false;
			}

			int year = Integer.parseInt(data.substring(6, 10));
			int month = Integer.parseInt(data.substring(3, 5));
			int day = Integer.parseInt(data.substring(0, 2));

			if ((day < 1 || day > 31) || (month < 1 || month > 12)) {
				return false;
			}

			log.info("yearmonthday:--------->" + year + " " + month + " " + day);
		}
		catch (ParseException pe) {
			log.info("Exception in formatDate : " + pe.getMessage());
			return false;
		}
		catch (NumberFormatException nfe) {
			log.info("Exception in formatDate : " + nfe.getMessage());
			return false;
		}

		return true;
	}

	public static boolean isValideOrario(String pattern, String orario) {
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		try {

			dateFormat.parse(orario);
			log.info("Ho parsificato la data con il pattern");

			// Controllo se i campi data sono coerenti
			if (orario == null || orario.length() != 5) {
				return false;
			}

			String oreN = orario.substring(0, 2);
			String minN = orario.substring(3, 5);

			if (oreN.startsWith("0"))
				oreN = oreN.substring(1);

			if (minN.startsWith("0"))
				minN = minN.substring(1);

			int ore = Integer.parseInt(oreN);
			int minuti = Integer.parseInt(minN);

			// E' un numero:controllo che sia corretto il periodo

			// Ore
			if (ore > 24) {
				return false;
			}
			else if (ore == 24) {
				if (minuti != 0) {
					return false;
				}
			}

			// Minuti
			if (minuti > 59) {
				return false;
			}

		}
		catch (ParseException pe) {
			log.info("Exception in isValideOrario : " + pe.getMessage());
			return false;
		}
		catch (NumberFormatException nfe) {
			log.info("Exception in isValideOrario : " + nfe.getMessage());
			return false;
		}

		return true;
	}

	// controllo se l'anno di nascita e' maggiore o uguale al 1900
	public boolean isValidForAnagrafica(int year) {

		if (year < 1900) {
			return false;
		}

		log.info("year:--------->" + year);

		return true;
	}

	/***************************************************************************
	 * GESTIONE CALENDARIO
	 ***************************************************************************/

	public static String getSysHHMM() {
		Calendar calendarNow = Calendar.getInstance();
		Date dataSistema = calendarNow.getTime();
		SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
		return formatTime.format(dataSistema);
	}

	public static String getSysHHMMSS() {
		Calendar calendarNow = Calendar.getInstance();
		Date dataSistema = calendarNow.getTime();
		SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
		return formatTime.format(dataSistema);
	}

	public static String getSysDate() {
		Calendar calendarNow = Calendar.getInstance();
		Date dataSistema = calendarNow.getTime();
		return getDate(dataSistema);
	}

	public static String getStrDateFromCalendar(GregorianCalendar gc) {
		Date dateFromGc = gc.getTime();
		String dateStr = DateUtil.getDate(dateFromGc);
		return dateStr;
	}

	public static int formatDayToNumber(String dataDD) throws NumberFormatException {

		StringBuffer data = new StringBuffer();

		String day = dataDD.substring(0, 2);

		if (day.startsWith("0")) {
			data.append(day.substring(1));
		}
		else {
			data.append(day);
		}

		return Integer.parseInt(data.toString());
	}

	public static int formatMonthToNumber(String dataMM) throws NumberFormatException {

		StringBuffer data = new StringBuffer();

		String month = dataMM.substring(3, 5);

		if (month.startsWith("0")) {
			data.append(month.substring(1));
		}
		else {
			data.append(month);
		}

		int monthZeroBased = Integer.parseInt(data.toString()) - 1;

		return monthZeroBased;
	}

	public static int formatYearToNumber(String dataYY) throws NumberFormatException {

		StringBuffer data = new StringBuffer();

		String year = dataYY.substring(6, 10);

		data.append(year);

		return Integer.parseInt(data.toString());
	}

	public static boolean isdata1Precedentedata2(String data1, String data2) throws NumberFormatException {
		boolean esito = true;

		int year1 = Integer.parseInt(data1.substring(6, 10));
		int month1 = formatMonthToNumber(data1);
		int day1 = formatDayToNumber(data1);

		int year2 = Integer.parseInt(data2.substring(6, 10));
		int month2 = formatMonthToNumber(data2);
		int day2 = formatDayToNumber(data2);

		GregorianCalendar gc1 = new GregorianCalendar(year1, month1, day1);
		GregorianCalendar gc2 = new GregorianCalendar(year2, month2, day2);

		esito = gc1.before(gc2);

		return esito;

	}

	public static boolean isdata1PrecedenteOrEqualdata2(String data1, String data2) throws NumberFormatException {
		boolean esito = true;

		int year1 = Integer.parseInt(data1.substring(6, 10));
		int month1 = formatMonthToNumber(data1);
		int day1 = formatDayToNumber(data1);

		int year2 = Integer.parseInt(data2.substring(6, 10));
		int month2 = formatMonthToNumber(data2);
		int day2 = formatDayToNumber(data2);

		GregorianCalendar gc1 = new GregorianCalendar(year1, month1, day1);
		GregorianCalendar gc2 = new GregorianCalendar(year2, month2, day2);

		esito = gc1.before(gc2) || gc1.equals(gc2);

		return esito;

	}

	public static boolean isdata1Posterioredata2(String data1, String data2) throws NumberFormatException {
		boolean esito = true;
		// Controllo che le due date non siano uguali

		int year1 = Integer.parseInt(data1.substring(6, 10));
		int month1 = formatMonthToNumber(data1);
		int day1 = formatDayToNumber(data1);

		int year2 = Integer.parseInt(data2.substring(6, 10));
		int month2 = formatMonthToNumber(data2);
		int day2 = formatDayToNumber(data2);

		GregorianCalendar gc1 = new GregorianCalendar(year1, month1, day1);
		GregorianCalendar gc2 = new GregorianCalendar(year2, month2, day2);

		esito = gc1.after(gc2);

		return esito;

	}

	public static boolean isdata1PosterioreOrEqualdata2(String data1, String data2) throws NumberFormatException {
		boolean esito = true;
		// Controllo che le due date non siano uguali

		int year1 = Integer.parseInt(data1.substring(6, 10));
		int month1 = formatMonthToNumber(data1);
		int day1 = formatDayToNumber(data1);

		int year2 = Integer.parseInt(data2.substring(6, 10));
		int month2 = formatMonthToNumber(data2);
		int day2 = formatDayToNumber(data2);

		GregorianCalendar gc1 = new GregorianCalendar(year1, month1, day1);
		GregorianCalendar gc2 = new GregorianCalendar(year2, month2, day2);

		esito = gc1.after(gc2) || gc1.equals(gc2);

		return esito;

	}

	public static boolean isData1BetweenD2D3(String data1, String data2, String data3) throws NumberFormatException {
		// Controllo se la data1 e' posteriore data2 e precedente a data3
		boolean esito = false;
		esito = isdata1Posterioredata2(data1, data2) && isdata1Precedentedata2(data1, data3);

		return esito;
	}

	public static boolean isData1BetweenD2D3OrEqual(String data1, String data2, String data3) throws NumberFormatException {
		// Controllo se la data1 e' posteriore data2 e precedente a data3
		boolean esito = false;
		esito = isdata1PosterioreOrEqualdata2(data1, data2) && isdata1PrecedenteOrEqualdata2(data1, data3);

		return esito;
	}

	public static boolean isData1BetweenOrEqualD2andBetweenD3(String data1, String data2, String data3) throws NumberFormatException {
		// Controllo se la data1 e' posteriore data2 e precedente a data3
		boolean esito = false;
		esito = isdata1PosterioreOrEqualdata2(data1, data2) && isdata1Precedentedata2(data1, data3);

		return esito;
	}

	public static boolean isPeriodD1D2ContPeriodD3D4(String data1, String data2, String data3, String data4) throws NumberFormatException {
		// Controllo se la data1 e' posteriore data2 e precedente a data3
		boolean esito = false;
		esito = isdata1Precedentedata2(data1, data3) && isdata1Posterioredata2(data2, data4);
		return esito;
	}

	// Date le 2 date in formato dd/mm/yyyy verifica se hanno lo stesso mese
	public static boolean isSameMonth(String data1Str, String data2Str) {
		String month1 = data1Str.substring(3, 5);
		String month2 = data2Str.substring(3, 5);

		if (month1.equalsIgnoreCase(month2))
			return true;
		else
			return false;
	}

	// Date le 2 date in formato dd/mm/yyyy verifica se hanno lo stesso anno
	public static boolean isSameYear(String data1Str, String data2Str) {
		String year1 = data1Str.substring(6);
		String year2 = data2Str.substring(6);

		if (year1.equalsIgnoreCase(year2))
			return true;
		else
			return false;
	}

	// //// CALENDARIO LEZIONI:ORARIO
	public static int getHour(String hhmm)

	{
		StringBuffer orario = new StringBuffer();

		if (hhmm.startsWith("0")) {
			orario.append(hhmm.substring(1));
		}
		else {
			orario.append(hhmm);
		}

		String oraFinale = orario.toString();

		return Integer.parseInt(StringUtils.substringBefore(oraFinale, "."));

	}

	public static int getMinute(String hhmm)

	{
		StringBuffer minutoFinale = new StringBuffer();

		String minuto = StringUtils.substringAfter(hhmm, ".");
		if (minuto == null || minuto.equals("")) {
			minutoFinale.append("0");
		}
		else {
			if (minuto.startsWith("0")) {
				minutoFinale.append(minuto.substring(1));
			}
			else {
				minutoFinale.append(minuto);
			}
		}
		return Integer.parseInt(minutoFinale.toString());

	}

	public static boolean isOkMonteOrario(String numOre, String oraProvaDal, String oraProvaAl, long orePreviste) {
		boolean esito = true;

		// Calcoliamo il tempo di durata totale lezioni in secondi
		long totaleInSeconds = 0;

		String numOreForm = formatHH24MI(numOre);

		int oraDal = getHour(oraProvaDal);
		int minutoDal = getMinute(oraProvaDal);

		int oraAl = getHour(oraProvaAl);
		int minutoAl = getMinute(oraProvaAl);

		int orePresenti = getHour(numOreForm);
		int minutiPresenti = getMinute(numOreForm);

		GregorianCalendar gc1 = new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, oraDal, minutoDal);
		GregorianCalendar gc2 = new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, oraAl, minutoAl);

		totaleInSeconds += getElapsedSeconds(gc1, gc2);

		long[] hhMM = calcHM(totaleInSeconds);

		long[] hhMMTot = new long[2];
		long minTot = (minutiPresenti + hhMM[1]) % 60;
		long oreTot = ((orePresenti + hhMM[0] + (minutiPresenti + hhMM[1]) / 60)) % 24;

		// Controllo adesso che l'orario lezioni non sia superiore ad 8 ore
		// -----> hhMM[0]<=8
		if (oreTot > orePreviste || (oreTot == orePreviste && minTot > 0))
			esito = false;

		return esito;
	}

	public static boolean isOkMonteOrario2(String numOre, String oraProvaDal, String oraProvaAl, String orePreviste) {
		boolean esito = true;

		long orePrev = (long) getHour(orePreviste);
		long minPrev = (long) getMinute(orePreviste);

		// Calcoliamo il tempo di durata totale lezioni in secondi
		long totaleInSeconds = 0;

		String numOreForm = formatHH24MI(numOre);

		int oraDal = getHour(oraProvaDal);
		int minutoDal = getMinute(oraProvaDal);

		int oraAl = getHour(oraProvaAl);
		int minutoAl = getMinute(oraProvaAl);

		int orePresenti = getHour(numOreForm);
		int minutiPresenti = getMinute(numOreForm);

		GregorianCalendar gc1 = new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, oraDal, minutoDal);
		GregorianCalendar gc2 = new GregorianCalendar(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, oraAl, minutoAl);

		totaleInSeconds += getElapsedSeconds(gc1, gc2);

		long[] hhMM = calcHM(totaleInSeconds);

		long[] hhMMTot = new long[2];
		long minTot = (minutiPresenti + hhMM[1]) % 60;
		long oreTot = ((orePresenti + hhMM[0] + (minutiPresenti + hhMM[1]) / 60)) % 24;

		// Controllo adesso che l'orario lezioni non sia superiore ad 8 ore
		// -----> hhMM[0]<=8
		if (oreTot > orePrev || (oreTot == orePrev && minTot > minPrev))
			esito = false;

		return esito;
	}

	// //// CALENDARIO LEZIONI:ORARIO
	public static double formatOrarioToNumber(String hhmm)

	{
		StringBuffer orario = new StringBuffer();

		if (hhmm.startsWith("0")) {
			orario.append(hhmm.substring(1));
		}
		else {
			orario.append(hhmm);
		}

		double orarioNumber = Double.parseDouble(orario.toString());

		return orarioNumber;
	}

	public static boolean isOkOrarioRange(String hhmmOraDal, String hhmmOraAl) {
		boolean esito = true;

		double orarioDal = formatOrarioToNumber(hhmmOraDal);
		double orarioAl = formatOrarioToNumber(hhmmOraAl);

		if (orarioDal >= orarioAl) {
			esito = false;
		}

		return esito;
	}

	/**
	 * 
	 * @param hhmmOraDal
	 * @param hhmmOraAl
	 * @param orarioIn
	 * @param orarioFin
	 * @return TRUE se l'intervallo di prova si sovrappone completamente o
	 *         parzialmente all'intervallo dato NB: non contempla il caso:
	 *         intervallo di prova contenuto nell'intervallo dato
	 */
	public static boolean isBeetwenOrario(String hhmmOraDal, String hhmmOraAl, String orarioIn, String orarioFin) {
		boolean esito = false;

		double orarioDal = formatOrarioToNumber(hhmmOraDal);
		double orarioAl = formatOrarioToNumber(hhmmOraAl);
		double orarioInProva = formatOrarioToNumber(orarioIn);
		double orarioFinProva = formatOrarioToNumber(orarioFin);

		if (orarioInProva <= orarioDal && orarioFinProva >= orarioAl) // intervalloDiProva
		// contiene
		// intervalloX
		{
			esito = true;
		}
		else {
			if ((orarioDal < orarioInProva && orarioAl > orarioInProva) || // se
			    // inizio
			    // intervalloDiProva
			    // 
			    // contenuto
			    // nell'intervalloX
			    (orarioDal < orarioFinProva && orarioAl > orarioFinProva) // se
			// fine
			// intervalloDiProva
			// 
			// contenuto
			// nell'intervalloX
			) {
				esito = true;
			}
		}
		return esito;
	}

	/**
	 * 
	 * @param hhmmOraDal
	 *          ora inizio intervallo A
	 * @param hhmmOraAl
	 *          ora fine intervallo A
	 * @param orarioIn
	 *          ora inizio intervallo B
	 * @param orarioFin
	 *          ora fine intervallo B
	 * @return TRUE se l'intervallo di prova si sovrappone in qualsiasi modo
	 *         all'intervallo dato
	 */
	public static boolean isOverIntervalloOrario(String hhmmOraDal, String hhmmOraAl, String orarioIn, String orarioFin) {
		boolean esito = false;

		log.debug("[DateUtil::isOverIntervalloOrario] BEGIN");

		double orarioDal = formatOrarioToNumber(hhmmOraDal);
		double orarioAl = formatOrarioToNumber(hhmmOraAl);
		double orarioInProva = formatOrarioToNumber(orarioIn);
		double orarioFinProva = formatOrarioToNumber(orarioFin);

		log.debug("[DateUtil::isOverIntervalloOrario] orarioDal=" + orarioDal);
		log.debug("[DateUtil::isOverIntervalloOrario] orarioAl=" + orarioAl);
		log.debug("[DateUtil::isOverIntervalloOrario] orarioInProva=" + orarioInProva);
		log.debug("[DateUtil::isOverIntervalloOrario] orarioFinProva=" + orarioFinProva);

		if (orarioInProva < orarioDal && orarioFinProva > orarioAl) { // intervalloDiProva
			// contiene
			// intervalloX

			esito = true;
			log.debug("[DateUtil::isOverIntervalloOrario] intervalloDiProva contiene intervalloX");

		}
		else if (orarioInProva > orarioDal && orarioFinProva < orarioAl) { // intervalloDiProva
			// e'
			// contenuto
			// nell'intervalloX

			esito = true;
			log.debug("[DateUtil::isOverIntervalloOrario] intervalloDiProva e' contenuto nell'intervalloX");

		}
		else if (isBeetwenStrictOrarioFinRange(hhmmOraDal, hhmmOraAl, orarioIn)) { // inizio
			// intervalloDiProva
			// 
			// contenuto
			// nell'intervalloX

			esito = true;
			log.debug("[DateUtil::isOverIntervalloOrario] inizio intervalloDiProva e' contenuto nell'intervalloX");

		}
		else if (isBeetwenStrictOrarioInRange(hhmmOraDal, hhmmOraAl, orarioFin)) { // fine
			// intervalloDiProva
			// 
			// contenuto
			// nell'intervalloX

			esito = true;
			log.debug("[DateUtil::isOverIntervalloOrario] fine intervalloDiProva e' contenuto nell'intervalloX");

		}

		log.debug("[DateUtil::isOverIntervalloOrario] END esito=" + esito);
		return esito;
	}

	public static boolean isBeetwenOrarioRange(String hhmmOraDal, String hhmmOraAl, String orario) {
		boolean esito = false;

		double orarioDal = formatOrarioToNumber(hhmmOraDal);
		double orarioAl = formatOrarioToNumber(hhmmOraAl);
		double orarioProva = formatOrarioToNumber(orario);

		if (orarioDal <= orarioProva && orarioAl >= orarioProva) {
			esito = true;
		}

		return esito;
	}

	public static boolean isBeetwenStrictOrarioRange(String hhmmOraDal, String hhmmOraAl, String orario) {
		boolean esito = false;

		double orarioDal = formatOrarioToNumber(hhmmOraDal);
		double orarioAl = formatOrarioToNumber(hhmmOraAl);
		double orarioProva = formatOrarioToNumber(orario);

		if (orarioDal < orarioProva && orarioAl > orarioProva) {
			esito = true;
		}

		return esito;
	}

	public static boolean isBeetwenStrictOrarioFinRange(String hhmmOraDal, String hhmmOraAl, String orario) {
		boolean esito = false;

		double orarioDal = formatOrarioToNumber(hhmmOraDal);
		double orarioAl = formatOrarioToNumber(hhmmOraAl);
		double orarioProva = formatOrarioToNumber(orario);

		if (orarioDal <= orarioProva && orarioAl > orarioProva) {
			esito = true;
		}

		return esito;
	}

	public static boolean isBeetwenStrictOrarioInRange(String hhmmOraDal, String hhmmOraAl, String orario) {
		boolean esito = false;

		double orarioDal = formatOrarioToNumber(hhmmOraDal);
		double orarioAl = formatOrarioToNumber(hhmmOraAl);
		double orarioProva = formatOrarioToNumber(orario);

		if (orarioDal < orarioProva && orarioAl >= orarioProva) {
			esito = true;
		}

		return esito;
	}

	public static long getElapsedSeconds(GregorianCalendar gc1, GregorianCalendar gc2) {
		Date d1 = gc1.getTime();
		Date d2 = gc2.getTime();
		long l1 = d1.getTime();
		long l2 = d2.getTime();
		long difference = Math.abs(l2 - l1);
		return difference / 1000;
	}

	public static long[] calcHM(long timeInSeconds) {

		long hours, minutes, seconds;
		long[] hM = new long[2];

		hours = timeInSeconds / 3600;
		timeInSeconds = timeInSeconds - (hours * 3600);
		minutes = timeInSeconds / 60;

		hM[0] = hours;
		hM[1] = minutes;

//		System.out.println(hours + " ora(e) " + minutes + " minuto(i)");

		return hM;
	}

	public static String formatDDMMYYYY(String data) {
		String giorno = StringUtils.substringBefore(data, "/");
		String mese = StringUtils.substringBetween(data, "/");
		String anno = StringUtils.substringAfterLast(data, "/");

		if (giorno.length() == 1) {
			giorno = "0" + giorno;
		}
		if (mese.length() == 1) {
			mese = "0" + mese;
		}

		return giorno + "/" + mese + "/" + anno;
	}

	public static String formatDDMMYYYY(String anno, String mese, String giorno) {

		if (giorno.length() == 1) {
			giorno = "0" + giorno;
		}
		if (mese.length() == 1) {
			mese = "0" + mese;
		}

		return giorno + "/" + mese + "/" + anno;
	}

	public static String formatHH24MI(String orario) {
		StringBuffer sbHH24MI = new StringBuffer();
		String ore = StringUtils.substringBefore(orario, ".");
		String min = StringUtils.substringAfter(orario, ".");

		if (ore.length() == 1) {
			sbHH24MI.append("0" + ore);
		}
		else {
			sbHH24MI.append(ore);
		}

		if (StringUtils.isEmpty(min))
			sbHH24MI.append(".00");
		else if (min.length() == 1) {
			sbHH24MI.append("." + min + "0");
		}
		else {
			sbHH24MI.append("." + min);
		}

		return sbHH24MI.toString();

	}

	public static String formatCalendarHH24MI(String orario) {
		StringBuffer sbHH24MI = new StringBuffer();

		if (StringUtils.contains(orario, ".")) {

			String ore = StringUtils.substringBefore(orario, ".");
			String min = StringUtils.substringAfter(orario, ".");

			if (ore.length() == 1) {
				sbHH24MI.append("0" + ore);
			}
			else if (StringUtils.isEmpty(ore)) {
				// e' una frazione di 1 ora
				sbHH24MI.append("00");
			}
			else {
				sbHH24MI.append(ore);
			}

			if (StringUtils.isEmpty(min))
				sbHH24MI.append(":00");
			else if (min.length() == 1) {
				sbHH24MI.append(":" + min + "0");
			}
			else {
				sbHH24MI.append(":" + min);
			}
		}
		else {
			if (orario == null) {
				sbHH24MI.append("00:00");
			}
			else if (orario.length() == 1) {
				sbHH24MI.append("0" + orario + ":00");
			}
			else {
				sbHH24MI.append(orario + ":00");
			}

		}
		return sbHH24MI.toString();

	}

	// Aldo 20111111
	// public static String formatHH24MI_duePunti(String orario) {
	// /**
	// * PR - ritorna l'ora nel formato HH:MM
	// */
	// return StringUtils.replace(formatHH24MI(orario), ".", ":");
	// }

	public static String getGiornoSettimana(String data, int giorno, int mese, int anno) {
		String dayWeek = null;
		int day = giorno;
		int month = mese - 1;
		int year = anno;

		if (StringUtils.isNotEmpty(data)) {
			day = formatDayToNumber(data);
			month = formatMonthToNumber(data);
			year = formatYearToNumber(data);
		}
		GregorianCalendar calendario = new GregorianCalendar(year, month, day);
		if (calendario.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			dayWeek = "Dom";
		}
		else if (calendario.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
			dayWeek = "Lun";
		}
		else if (calendario.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
			dayWeek = "Mar";
		}
		else if (calendario.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
			dayWeek = "Mer";
		}
		else if (calendario.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
			dayWeek = "Gio";
		}
		else if (calendario.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
			dayWeek = "Ven";
		}
		else if (calendario.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			dayWeek = "Sab";
		}

		return dayWeek;
	}

	public static String getGiornoSettimanaCompleto(String data, int giorno, int mese, int anno) {
		String dayWeek = null;
		int day = giorno;
		int month = mese - 1;
		int year = anno;

		if (StringUtils.isNotEmpty(data)) {
			day = formatDayToNumber(data);
			month = formatMonthToNumber(data);
			year = formatYearToNumber(data);
		}
		GregorianCalendar calendario = new GregorianCalendar(year, month, day);
		if (calendario.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			dayWeek = "Domenica";
		}
		else if (calendario.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
			dayWeek = "Lunedi";
		}
		else if (calendario.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
			dayWeek = "Martedi";
		}
		else if (calendario.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
			dayWeek = "Mercoledi";
		}
		else if (calendario.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
			dayWeek = "Giovedi";
		}
		else if (calendario.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
			dayWeek = "Venerdi";
		}
		else if (calendario.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			dayWeek = "Sabato";
		}

		return dayWeek;
	}

	public static String splitGiorno(String data, int giorno, int mese, int anno, int diff) {
		String dataDiff = null;
		int day = giorno;
		int month = mese - 1;
		int year = anno;

		if (StringUtils.isNotEmpty(data)) {
			day = formatDayToNumber(data);
			month = formatMonthToNumber(data);
			year = formatYearToNumber(data);
		}
		GregorianCalendar calendario = new GregorianCalendar(year, month, day);
		calendario.add(Calendar.DATE, diff);

		day = calendario.get(Calendar.DAY_OF_MONTH);
		month = calendario.get(Calendar.MONTH) + 1;
		year = calendario.get(Calendar.YEAR);

		dataDiff = day + "/" + month + "/" + year;

		return formatDDMMYYYY(dataDiff);
	}

	public static String splitInSettimana(String data, int giorno, int mese, int anno) {
		String dataDiff = null;
		int day = giorno;
		int month = mese - 1;
		int year = anno;

		if (StringUtils.isNotEmpty(data)) {
			day = formatDayToNumber(data);
			month = formatMonthToNumber(data);
			year = formatYearToNumber(data);
		}
		GregorianCalendar calendario = new GregorianCalendar(year, month, day);
		int dayWeek = calendario.get(Calendar.DAY_OF_WEEK);
		// Lunedi = 2
		int advanceInSett = dayWeek - 2;
		if (advanceInSett < 0) {
			calendario.add(Calendar.DATE, -6); // splitto al lunedi precedente
		}
		else {
			calendario.add(Calendar.DATE, -1 * advanceInSett);
		}

		day = calendario.get(Calendar.DAY_OF_MONTH);
		month = calendario.get(Calendar.MONTH) + 1;
		year = calendario.get(Calendar.YEAR);

		dataDiff = day + "/" + month + "/" + year;

		return formatDDMMYYYY(dataDiff);

	}

	public static String[] getGiorniCalendario(String data, int giorno, int mese, int anno) {
		String[] settimana = new String[7];
		String dataInSettimana = splitInSettimana(data, giorno, mese, anno);

		int year = formatYearToNumber(dataInSettimana);
		int month = formatMonthToNumber(dataInSettimana);
		int day = formatDayToNumber(dataInSettimana);

		GregorianCalendar calendario = new GregorianCalendar(year, month, day);
		year = calendario.get(Calendar.YEAR);
		month = calendario.get(Calendar.MONTH) + 1;
		day = calendario.get(Calendar.DAY_OF_MONTH);
		settimana[0] = formatDDMMYYYY(day + "/" + month + "/" + year);

		for (int i = 1; i <= 6; i++) {
			calendario.add(Calendar.DATE, 1);
			year = calendario.get(Calendar.YEAR);
			month = calendario.get(Calendar.MONTH) + 1;
			day = calendario.get(Calendar.DAY_OF_MONTH);
			settimana[i] = formatDDMMYYYY(day + "/" + month + "/" + year);
		}

		return settimana;
	}

	public static String[] getSettimanaSuccessiva(String data, int giorno, int mese, int anno) {
		String[] settimana = new String[7];

		int day = giorno;
		int month = mese - 1;
		int year = anno;

		if (StringUtils.isNotEmpty(data)) {
			day = formatDayToNumber(data);
			month = formatMonthToNumber(data);
			year = formatYearToNumber(data);
		}
		GregorianCalendar calendario = new GregorianCalendar(year, month, day);

		for (int i = 0; i <= 6; i++) {
			calendario.add(Calendar.DATE, 1);
			year = calendario.get(Calendar.YEAR);
			month = calendario.get(Calendar.MONTH) + 1;
			day = calendario.get(Calendar.DAY_OF_MONTH);
			settimana[i] = formatDDMMYYYY(day + "/" + month + "/" + year);
		}
		return settimana;
	}

	public static String[] getSettimanaPrecedente(String data, int giorno, int mese, int anno) {
		String[] settimana = new String[7];

		int day = giorno;
		int month = mese - 1;
		int year = anno;

		if (StringUtils.isNotEmpty(data)) {
			day = formatDayToNumber(data);
			month = formatMonthToNumber(data);
			year = formatYearToNumber(data);
		}
		GregorianCalendar calendario = new GregorianCalendar(year, month, day);

		for (int i = 0; i <= 6; i++) {
			calendario.add(Calendar.DATE, -1);
			year = calendario.get(Calendar.YEAR);
			month = calendario.get(Calendar.MONTH) + 1;
			day = calendario.get(Calendar.DAY_OF_MONTH);
			settimana[6 - i] = formatDDMMYYYY(day + "/" + month + "/" + year);
		}
		return settimana;
	}

	// public static SelItem[] getMesiAnno()
	// {
	// SelItem[] itemMesi = new SelItem[12];
	// // calendario zero-based per la gestione
	// itemMesi[0] = new SelItem("0","Gennaio","Gennaio");
	// itemMesi[1] = new SelItem("1","Febbraio","Febbraio");
	// itemMesi[2] = new SelItem("2","Marzo","Marzo");
	// itemMesi[3] = new SelItem("3","Aprile","Aprile");
	// itemMesi[4] = new SelItem("4","Maggio","Maggio");
	// itemMesi[5] = new SelItem("5","Giugno","Giugno");
	// itemMesi[6] = new SelItem("6","Luglio","Luglio");
	// itemMesi[7] = new SelItem("7","Agosto","Agosto");
	// itemMesi[8] = new SelItem("8","Settembre","Settembre");
	// itemMesi[9] = new SelItem("9","Ottobre","Ottobre");
	// itemMesi[10] = new SelItem("10","Novembre","Novembre");
	// itemMesi[11] = new SelItem("11","Dicembre","Dicembre");
	//
	// return itemMesi;
	// }

	public static int getMonth(String data) {
		return formatMonthToNumber(data);

	}

	public static String getMonthName(String data) {
		int monthNumber = formatMonthToNumber(data);
		return getMonthName(monthNumber);
	}

	public static int getDay(String data) {
		return formatDayToNumber(data);

	}

	public static int getYear(String data) {
		return formatYearToNumber(data);

	}

	/**
	 * Testata per i seguenti pattern:
	 * 
	 * dd/MM/yyyy dd-MM-yyyy dd.MM.yyyy MM/yyyy
	 * 
	 * Non lancia eccezioni. Restituisce solo true o false.
	 * 
	 * @param pattern
	 * @param data
	 * @return
	 */
	public static boolean isDataValida(String pattern, String data) {
		boolean result = false;

		GregorianCalendar gregorianDate = null;

		SimpleDateFormat formatoData = new SimpleDateFormat(pattern);

		try {
			formatoData.setLenient(false);
			formatoData.parse(data);

			if (pattern != null && (pattern.equals("dd/MM/yyyy") || pattern.equals("dd-MM-yyyy")) || pattern.equals("dd.MM.yyyy")) {
				gregorianDate = new GregorianCalendar(Integer.parseInt(data.substring(6)), Integer.parseInt(data.substring(3, 5)) - 1, Integer.parseInt(data.substring(0, 2)));

				gregorianDate.setLenient(false);

				log.info("giorno = " + gregorianDate.get(Calendar.DAY_OF_MONTH));
				log.info("mese = " + gregorianDate.get(Calendar.MONTH));
				log.info("anno = " + gregorianDate.get(Calendar.YEAR));
			}
			result = true;
		}
		catch (IllegalArgumentException ie) {
			log.error("Catchata eccezione = ", ie);
			result = false;

		}
		catch (ParseException pe) {
			log.error("Catchata eccezione = ", pe);
			result = false;
		}
		catch (Exception e) {
			log.error("Catchata eccezione =", e);
			result = false;
		}
		log.info("result = " + result);
		return result;
	}

	/**
	 * Verifica input data valida ed espressa nel formato dd/MM/yyyy et similia
	 * 
	 * @param data
	 * @return
	 */
	public static boolean isMaggiorenne(String pattern, String data) {
		boolean result = true;

		String dataOdierna = getDataOdierna();

		GregorianCalendar gregorianDataInserita = null;
		GregorianCalendar gregorianDataOdierna = new GregorianCalendar(Integer.parseInt(dataOdierna.substring(6)), Integer.parseInt(dataOdierna.substring(3, 5)) - 1, Integer.parseInt(dataOdierna
		    .substring(0, 2)));

		gregorianDataOdierna.setLenient(false);

		SimpleDateFormat formatoData = new SimpleDateFormat(pattern);

		try {
			formatoData.setLenient(false);
			formatoData.parse(data);

			if (pattern != null && (pattern.equals("dd/MM/yyyy") || pattern.equals("dd-MM-yyyy")) || pattern.equals("dd.MM.yyyy")) {
				gregorianDataInserita = new GregorianCalendar(Integer.parseInt(data.substring(6)), Integer.parseInt(data.substring(3, 5)) - 1, Integer.parseInt(data.substring(0, 2)));

				gregorianDataInserita.setLenient(false);

				log.info("giorno = " + gregorianDataInserita.get(Calendar.DAY_OF_MONTH));
				log.info("mese = " + gregorianDataInserita.get(Calendar.MONTH));
				log.info("anno = " + gregorianDataInserita.get(Calendar.YEAR));
			}

			// la data inserita e' formalmente corretta, proseguo con i controlli
			// di merito
			int annoInCorso = Integer.parseInt(dataOdierna.substring(6));
			int annoNascita = Integer.parseInt(data.substring(6));

			if (annoInCorso - annoNascita < 18) {
				// minorenne
				result = false;
			}
			else if (annoInCorso - annoNascita == 18) {
				// ctrl giorno e mese
				GregorianCalendar gregorianDataInseritaAnnoInCorso = new GregorianCalendar(Integer.parseInt(dataOdierna.substring(6)), Integer.parseInt(data.substring(3, 5)) - 1, Integer.parseInt(data
				    .substring(0, 2)));

				gregorianDataInseritaAnnoInCorso.setLenient(false);

				if (gregorianDataInseritaAnnoInCorso.after(gregorianDataOdierna)) {
					result = false;
				}
				else {
					result = true;
				}
			}
		}
		catch (IllegalArgumentException ie) {
			log.error("Catchata eccezione = ", ie);
			result = false;
		}
		catch (ParseException pe) {
			log.error("Catchata eccezione = ", pe);
			result = false;
		}
		catch (Exception e) {
			log.error("Catchata eccezione =", e);
			result = false;
		}
		log.info("result = " + result);
		return result;
	}

	/**
	 * Testata per i seguenti pattern:
	 * 
	 * dd/MM/yyyy dd-MM-yyyy dd.MM.yyyy MM/yyyy
	 * 
	 * Il metodo si avvale della classe GregorianCalendar e con lenient impostato
	 * a false --> la data inserita in input deve essere una data valida in
	 * calendario (es. 29/02/2010 non e' ammesso)
	 * 
	 * Nel caso in cui si forzi l'inserimento in input di una data non valida (es
	 * 30/02/2011 o 29/02/2010) viene lanciata una ParseException
	 * 
	 * 
	 * @param pattern
	 * @param data1
	 *          input inserito come da pattern indicato (es. se pattern =
	 *          dd/MM/yyyy ---> 01/01/2011)
	 * @param data2
	 *          input inserito come da pattern indicato (es. se pattern =
	 *          dd/MM/yyyy ---> 10/01/2011)
	 * @param data1Inclusa
	 *          se true, nell'output viene indicata anche la data1 passata in
	 *          input
	 * @param data2Inclusa
	 *          se true, nell'output viene indicata anche la data2 passata in
	 *          input
	 * @return ArrayList di strighe formattate secondo il pattern indicato in
	 *         input
	 * @throws ParseException
	 */
	public static ArrayList<String> getDateFraData1Data2(String pattern, String data1, String data2, boolean data1Inclusa, boolean data2Inclusa) throws ParseException {
		final int MILLISECONDS_IN_DAY = 1000 * 60 * 60 * 24;

		ArrayList<String> elencoDate = new ArrayList<String>();

		GregorianCalendar gregorianDate1 = null;
		GregorianCalendar gregorianDate2 = null;

		SimpleDateFormat formatoData = new SimpleDateFormat(pattern);

		try {
			formatoData.setLenient(false);
			formatoData.parse(data1);
			formatoData.parse(data2);

			gregorianDate1 = new GregorianCalendar(Integer.parseInt(data1.substring(6)), Integer.parseInt(data1.substring(3, 5)) - 1, Integer.parseInt(data1.substring(0, 2)));

			gregorianDate1.setLenient(false);

			gregorianDate2 = new GregorianCalendar(Integer.parseInt(data2.substring(6)), Integer.parseInt(data2.substring(3, 5)) - 1, Integer.parseInt(data2.substring(0, 2)));

			gregorianDate2.setLenient(false);

			long differenzaInMillisecondi = gregorianDate2.getTimeInMillis() + gregorianDate2.get(Calendar.DST_OFFSET) - gregorianDate1.getTimeInMillis() + gregorianDate1.get(Calendar.DST_OFFSET);

			log.debug("differenzaInMillisecondi = " + differenzaInMillisecondi);

			int giorniDiDifferenza = (int) (differenzaInMillisecondi / MILLISECONDS_IN_DAY);

			log.debug("giorniDiDifferenza = " + giorniDiDifferenza);

			String newData = "";

			newData = formatoData.format(gregorianDate1.getTime());

			log.debug("giorno Data1 = " + newData);

			if (data1Inclusa)
				elencoDate.add(newData);

			for (int i = 0; i < giorniDiDifferenza; i++) {
				gregorianDate1.add(Calendar.DAY_OF_YEAR, 1);

				newData = formatoData.format(gregorianDate1.getTime());

				log.debug("i = " + i + " newData = " + newData);

				if (i != giorniDiDifferenza - 1) {
					elencoDate.add(newData);
				}
				else if (i == giorniDiDifferenza - 1)
					if (data2Inclusa)
						elencoDate.add(newData);
			}
		}
		catch (ParseException pe) {
			log.error("" + pe);
			throw pe;
		}
		catch (Exception e) {
			log.error("" + e);
		}
		return elencoDate;
	}

	public static int getAnnoInCorso() {
		int annoIncorso = 0;

		String dataOdierna = getDataOdierna();

		annoIncorso = Integer.parseInt(dataOdierna.substring(6));

		return annoIncorso;
	}

	/***************************************************************************
	 * GESTIONE CALENDARIO
	 ***************************************************************************/

	// Viene utilizzato per prelevare il nome del mese. Calendar.MONTH parte da
	// 0=Gennaio
	public static String[] mesi = { "GENNAIO", "FEBBRAIO", "MARZO", "APRILE", "MAGGIO", "GIUGNO", "LUGLIO", "AGOSTO", "SETTEMBRE", "OTTOBRE", "NOVEMBRE", "DICEMBRE", };

	public static String getMonthName(int monthNumber) {
		return mesi[monthNumber];
	}

	/**
     * 
     */
	public static boolean isOreMinutiSecondiValidi(String orario) {
		DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");

		try {
			dateFormat.parse(orario);
			log.info("Parsificato orario con il pattern");

			// Controllo se i campi data sono coerenti
			if (orario == null || orario.length() != 8) {
				return false;
			}

			String oreN = orario.substring(0, 2);
			String minN = orario.substring(3, 5);
			String secN = orario.substring(6, 8);

			if (oreN.startsWith("0"))
				oreN = oreN.substring(1);

			if (minN.startsWith("0"))
				minN = minN.substring(1);

			if (secN.startsWith("0"))
				secN = secN.substring(1);

			int ore = Integer.parseInt(oreN);
			int minuti = Integer.parseInt(minN);
			int secondi = Integer.parseInt(secN);

			// E' un numero:controllo che sia corretto il periodo

			// Ore
			if (ore > 24) {
				return false;
			}
			else if (ore == 24) {
				if (minuti != 0) {
					return false;
				}
			}

			// Minuti
			if (minuti > 59) {
				return false;
			}
			if (secondi > 59) {
				return false;
			}
		}
		catch (ParseException pe) {
			log.info("Exception in isOraValida : " + pe.getMessage());
			return false;
		}
		catch (NumberFormatException nfe) {
			log.info("Exception in isOraValida : " + nfe.getMessage());
			return false;
		}
		return true;
	}

	public static boolean validate(String date) {
		try {
			if (StringUtils.isNumeric(date.replace('/', '0'))) {
				DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
				df.setLenient(false);
				df.parse(date);
			}
			else {
				return false;
			}
		}
		catch (Exception ex) {
			log.error("[DateUtil::validate] Exception:"+ex);
			return false;
		}

		return true;
	}

	/**
	 * 
	 * @param oraInizio
	 *          espressa nel formato hh.mm (esempio "09.00")
	 * @param oraFine
	 *          espressa nel formato hh.mm (esempio "09.00")
	 * @return differenza espressa in minuti effettivi
	 */
	public static double getDifferenzaOraFineOraInizio(String oraInizio, String oraFine) {
		double result = 0;

		int intOraInizio = getHour(oraInizio);
		int intOraFine = getHour(oraFine);
		int intMinutiOraInizio = getMinute(oraInizio);
		int intMinutiOraFine = getMinute(oraFine);

		int differenzaOre = intOraFine - intOraInizio;
		int differenzaOreInMinuti = differenzaOre * 60;
		int differenzaMinuti = intMinutiOraFine - intMinutiOraInizio;
		int differenzaComplessivaInMinuti = differenzaOreInMinuti + differenzaMinuti;

		log.debug("differenzaOre = " + differenzaOre);
		log.debug("differenzaOreInMinuti = " + differenzaOreInMinuti);
		log.debug("differenzaMinuti = " + differenzaMinuti);
		log.debug("differenza ore.minuti = " + differenzaOre + "." + differenzaMinuti);

		result = differenzaComplessivaInMinuti;

		return result;
	}

	/**
	 * Il risultato viene arrotondato a 2 cifre decimali
	 * 
	 * @param oraInizio
	 * @param oraFine
	 * @param minutiDurataOraLezione
	 * @return differenza espressa in "minuti amministrativi" (vd. input
	 *         minutiDurataOraLezione, 60 minuti, 50 minuti, 55 minuti, ecc...)
	 */
	public static double getDifferenzaOraFineOraInizio(String oraInizio, String oraFine, int minutiDurataOraLezione) {
		if (minutiDurataOraLezione == 0)
			minutiDurataOraLezione = 60; // di default l' "ora amministrativa"
		// coincide con l'ora vera e propria
		// (60 minuti)

		NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
		// imposto max 2 cifre decimali dopo la virgola
		nf.setMaximumFractionDigits(2);

		log.debug("senza arrotondamento --> " + getDifferenzaOraFineOraInizio(oraInizio, oraFine) / minutiDurataOraLezione);

		return Double.parseDouble(nf.format(getDifferenzaOraFineOraInizio(oraInizio, oraFine) / minutiDurataOraLezione));
	}

	/**
	 * il metodo recupera l'ultimo giorno del mese per creare una data completa.
	 * 
	 * @param dataMmAaaa
	 *          - data da input nel formato mm/aaaa
	 * @return giorno - ultimo giorno del mese calcolato
	 */
	public static String getUltimoGiornoDelMese(String dataMmAaaa) {
		String giorno = "";
		/* divido la data in input in mese e anno */
		int mese = Integer.parseInt(dataMmAaaa.trim().substring(0, 2));
		int anno = Integer.parseInt(dataMmAaaa.trim().substring(3));

		switch (mese) {
		case 4:
		case 6:
		case 9:
		case 11:
			giorno = "30";
			break;

		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			giorno = "31";
			break;

		case 2:
			/* controllo l'anno - se e' divisibile per 4 allora e' bisestile */
			if (anno % 4 == 0) {
				/* anno bisestile, ultimo giorno == 29 */
				giorno = "29";
			}
			else {
				giorno = "28";
			}
		default:
			break;
		}

		return giorno;
	}

	/*
	 * public static void main(String args[]) {
	 * 
	 * GregorianCalendar calendario = new GregorianCalendar(2005,8,1); DateUtil
	 * dataUtil = new DateUtil();
	 * System.out.println("Difference::"+dataUtil.getDifference
	 * (calendario.getTime()));
	 * 
	 * String prova = "Organigramma:\n(a)Pippo;\n(b)Pluto;";
	 * System.out.println("prova::"+StringUtils.replace(prova,"\n","<br>"));
	 * System .out.println("prova::"+StringUtils.replaceChars(prova,"\n","<br>"));
	 * 
	 * }
	 */
	public static void main(String[] args) {

		boolean result = controllaEtaInRange(26, 70, "04/07/1941");

//		System.out.println("risultato == " + result);

		// String data = "04/1900";
		//
		// System.out.println("ULTIMO GIORNO DEL MESE == "+getUltimoGiornoDelMese(data));

		// GregorianCalendar today = new GregorianCalendar();
		// System.out.println("today.toString()=" + today.toString());
		//
		// String dataAttuale = getSysDate();
		// System.out.println("dataAttuale=" + dataAttuale);
		//
		// String oraInizioX = "09.00";
		// String oraFineX = "10.00";
		// String oraInizioB = "08.11";
		// String oraFineB = "09.30";
		//
		// System.out.println("isOverOrario=" +
		// isOverIntervalloOrario(oraInizioX, oraFineX, oraInizioB, oraFineB));

		// String settSplit = splitInSettimana("11/07/2011", 4, 6, 2011);
		// System.out.println("settSplit=" +settSplit);

		// String giornoFuturo = splitGiorno("05/07/2011", 5, 7, 2011, 1);
		// System.out.println("giornoFuturo=" +giornoFuturo);

		// Giorni della settimana
		// String[] settimana = new String[7];
		// settimana = getGiorniCalendario("03/07/2011", 5, 7, 2011);
		// for(int i=0; i<7; i++)
		// System.out.println("giorno " +i +"=" +settimana[i]);

		// String[] settimana = new String[7];
		// settimana = getSettimanaSuccessiva("05/07/2011", 5, 7, 2011);
		// for(int i=0; i<7; i++)
		// System.out.println("giorno " +i +"=" +settimana[i]);

		// String[] settimana = new String[7];
		// settimana = getSettimanaPrecedente("05/07/2011", 5, 7, 2011);
		// for(int i=0; i<7; i++)
		// System.out.println("giorno " +i +"=" +settimana[i]);

		// DateUtil day1 = new DateUtil(2002, 12, 01);
		// day1.isOkMonteOrario2("4","15.00","16.45","00.00");
		// System.out.println(day1.formatCalendarHH24MI("12,30"));
		// String[] prova =
		// StringUtils.split("Prova\r\n\r\nProva\r\nProva","\r\n");
		// String prova2 =
		// StringUtils.replace("Prova\r\n\r\nProva\r\nProva","\r\n","<br/>");
		// stem.out.println(day1.daysBetween(new DateUtil(2004,1,15)));
		// System.out.println("Valido?:"+day1.isValid());

		// System.out.println("Risultato::"+formatOrarioToNumber("00.00"));

		// OrarioLezione ora1 = new OrarioLezione("00.00","04.00", null);
		// OrarioLezione ora2 = new OrarioLezione("05.00","12.00", null);
		// OrarioLezione ora3 = new OrarioLezione("09.45","10.00", null);
		// OrarioLezione ora4 = new OrarioLezione("10.00","16.00", null);
		// OrarioLezione ora5 = new OrarioLezione("13.00","17.00", null);

		// List lista = new ArrayList();
		// lista.add(ora1);
		// lista.add(ora2);
		// lista.add(ora3);
		// lista.add(ora4);
		// lista.add(ora5);

		// System.out.println("Conteggio ore consecutive OK::"+day1.isMaxSeiOreConsecutive(lista));
		// System.out.println("data sistema::"+day1.getDataOdierna());
		// day1.validaOreTotali(" ");

		// System.out.println("sovrapposizione lezioni::"+day1.isSovrappOrario(lista));

		/*
		 * GiornoLezione giorno = new GiornoLezione("","","","",lista);
		 * System.out.println("Monte::"+day1.isOkMonteOrario(giorno));
		 */

		/*
		 * GregorianCalendar gc1 = new
		 * GregorianCalendar(Calendar.YEAR,Calendar.MONTH
		 * ,Calendar.DAY_OF_MONTH,13,0); GregorianCalendar gc2 = new
		 * GregorianCalendar
		 * (Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_MONTH,21,0);
		 * day1.calcHM(day1.getElapsedSeconds(gc1,gc2));
		 */

		/*
		 * List orario = new ArrayList(); String fr15 = ""; for (int i = 0; i < 3;
		 * i++) { if (i == 0) { for (int j = 6; j < (i == 2 ? 4 : 10); j++) { for
		 * (int k = 0; k < 60; k += 15) { //for (int l = 0; l < 10; l++) { if (k ==
		 * 0) { fr15 = k + "0"; } else { fr15 = k + ""; } System.out.println(i + ""
		 * + j + "." + fr15 + ""); SelItem hhMM = new SelItem(i + "" + j + "." +
		 * fr15 + "" , i + "" + j + "." + fr15 + "" , i + "" + j + "." + fr15 + "");
		 * 
		 * orario.add(hhMM);
		 * 
		 * //} } } } else { for (int j = 0; j <= (i == 2 ? 4 : 10); j++) { for (int
		 * k = 0; k < 60; k += 15) { //for (int l=0;l<10 ;l++ ) { if (k == 0) { fr15
		 * = k + "0";
		 * 
		 * if (j == 4) k = 60;
		 * 
		 * } else { fr15 = k + ""; } System.out.println(i + "" + j + "." + fr15 +
		 * ""); SelItem hhMM = new SelItem(i + "" + j + "." + fr15 + "" , i + "" + j
		 * + "." + fr15 + "" , i + "" + j + "." + fr15 + "");
		 * 
		 * orario.add(hhMM);
		 * 
		 * //} } }
		 * 
		 * } }
		 */
		// orario.add(new SelItem("24.00","24.00","24.00"));
		// System.out.println("Orario:::" + orario);

		// System.out.println("giorno settimana::" +
		// day1.getGiornoSettimana("",24,8,2007));
		// System.out.println("giorno::" +
		// day1.splitGiorno("02/03/2007",-1,-1,-1,-2));

		// System.out.println("lunedi::"+day1.getGiorniCalendario("02/09/2007",-1,-1,-1).toString());

		/*
		 * String orarioIniziale = "10.00"; String orarioFinale = "11.30"; String
		 * fr15 = ""; String orario = null; int subIn =
		 * Integer.parseInt(orarioIniziale.substring(0, 1)); int finIn =
		 * Integer.parseInt(orarioIniziale.substring(1, 2)); int subMinFin =
		 * Integer.parseInt(orarioIniziale.substring(3)); int finIn2 = 0; int
		 * minFinali = 0; if(subIn != 0) { finIn2 = finIn;
		 * 
		 * } search: for (int i = subIn; i < 3; i++) { if (i == 0) { for (int j =
		 * finIn; j < (i == 2 ? 5 : 10); j++) { for (int k = (j==finIn) ? subMinFin
		 * : 0; k < 60; k += 15) { //for (int l = 0; l < 10; l++) { if (k == 0) {
		 * fr15 = k + "0"; } else { fr15 = k + ""; } System.out.println(i + "" + j +
		 * "." + fr15 + ""); orario = i + "" + j + "." + fr15 + "";
		 * 
		 * if (orario.equals(orarioFinale)) { break search; } } } } else { for (int
		 * j = finIn2; j < (i == 2 ? 5 : 10); j++) { for (int k = (j==finIn2 &&
		 * subIn!=0) ? subMinFin : 0; k < 60; k += 15) { if (k == 0) { fr15 = k +
		 * "0"; if (j == 4 && i==2) { k = 60; } } else { fr15 = k + ""; } if(j==9) {
		 * finIn2 = 0; subMinFin = 0; } System.out.println(i + "" + j + "." + fr15 +
		 * ""); orario = i + "" + j + "." + fr15 + "";
		 * 
		 * if (orario.equals(orarioFinale)) { break search; }
		 * 
		 * } }
		 * 
		 * } }
		 */
		// System.out.println("Monte orario = " +
		// isOkMonteOrario("3","08.15","15.15",8));

		// //////////////////////////

	}

	/*
	 * metodo che controlla se l'eta' e' compresa tra 26 e 70 anni... per l'invio
	 * domanda del presidente
	 */
	public static boolean controllaEtaInRange(int rangeDa, int rangeA, String data) {
		boolean result = true;

		String dataOdierna = getDataOdierna();

		int annoNascita = Integer.parseInt(data.substring(6, 10));

		int annoDa = annoNascita + rangeDa;
		int annoA = annoNascita + rangeA + 1; /*
																					 * fino all'ultimo giorno
																					 * dell'ennesimo anno...
																					 */

		String dataDa = data.substring(0, 6) + annoDa;
		log.info("dataDa == " + dataDa);
		String dataA = data.substring(0, 6) + annoA;
		log.info("dataA == " + dataA);

		result = isData1BetweenOrEqualD2andBetweenD3(dataOdierna, dataDa, dataA);

		log.info("result = " + result);
		return result;
	}
	
	public static Date getDate() {
		Calendar adesso = new GregorianCalendar(Locale.ITALY);
		return adesso.getTime();
	}

	public static java.sql.Date utilToSqlDate(Date data) {
		return (data == null) ? null : new java.sql.Date(data.getTime());
	}
	
	public static String getTime(Date data, String format) {
		return getTimeFormattato(data, format);
	}
	
	private static String getTimeFormattato(Date data, String format) {
		String ret = null;
		if (null != data) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			ret = sdf.format(data);
		}
		return ret;
	}
}
