package org.opoo.apps.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;

public abstract class DateUtils {
	private static final Log log = LogFactory.getLog(DateUtils.class);

	/**
	 * Rounds the given date down to the nearest specified second. The following
	 * table shows sample input and expected output values: (Note, only the time
	 * portion of the date is shown for brevity)
	 * <p>
	 * <p/>
	 * <table border="1">
	 * <tr>
	 * <th>Date</th>
	 * <th>Seconds</th>
	 * <th>Result</th>
	 * </tr>
	 * <tr>
	 * <td>1:37.48</td>
	 * <td>5</td>
	 * <td>1:37.45</td>
	 * </tr>
	 * <tr>
	 * <td>1:37.48</td>
	 * <td>10</td>
	 * <td>1:37.40</td>
	 * </tr>
	 * <tr>
	 * <td>1:37.48</td>
	 * <td>30</td>
	 * <td>1:37.30</td>
	 * </tr>
	 * <tr>
	 * <td>1:37.48</td>
	 * <td>60</td>
	 * <td>1:37.00</td>
	 * </tr>
	 * <tr>
	 * <td>1:37.48</td>
	 * <td>120</td>
	 * <td>1:36.00</td>
	 * </tr>
	 * </table>
	 * <p>
	 * <p/>
	 * This method is useful when calculating the last post in a community or
	 * the number of new messages from a given date. Using a rounded date allows
	 * Jive to internally cache the results of the date query. Here's an example
	 * that shows the last posted message in a community accurate to the last 5
	 * minutess:
	 * <p>
	 * <p/>
	 * <code> ResultFilter filter = new ResultFilter(); <br> filter.setSortOrder(ResultFilter.DESCENDING); <br>
	 * filter.setSortField(JiveConstants.CREATION_DATE); <br> <b>filter.setCreationDateRangeMin(ResultFilter.roundDate(community.getModificationDate(),
	 * 5*60));</b> <br> filter.setNumResults(1); <br> Iterator messages = community.getMessages(filter); <br>
	 * ForumMessage lastPost = (ForumMessage) messages.next(); <br> </code>
	 * <p>
	 * 
	 * @param date
	 *            the <tt>Date</tt> we want to round.
	 * @param seconds
	 *            the number of seconds we want to round the date to.
	 * @return the given date, rounded down to the nearest specified number of
	 *         seconds.
	 */
	public static Date roundDate(Date date, int seconds) {
		return new Date(roundDate(date.getTime(), seconds));
	}

	/**
	 * Rounds the given date down to the nearest specfied second.
	 * 
	 * @param date
	 *            the date (as a long) that we want to round.
	 * @param seconds
	 *            the number of seconds we want to round the date to.
	 * @return the given date (as a long), rounded down to the nearest specified
	 *         number of seconds.
	 */
	public static long roundDate(long date, int seconds) {
		return date - (date % (1000 * seconds));
	}

	/**
	 * Returns the maximum date value allowed. Some classes need this to
	 * represent the same thing as a "null" date -- in those cases it's not
	 * possible to insert null's into the database usually for SQL reasons.
	 * 
	 * @return new Date(999999999999999L);
	 */
	public static Date getMaxDate() {
		return new Date(999999999999999L);
	}

	/**
	 * Add specified number of days to the given date.
	 * 
	 * @param days
	 *            The number of days.
	 * @param from
	 *            date to add to
	 * @return date that is "days" after "from" date
	 */
	public static Date daysLater(int days, Calendar from) {
		from.add(Calendar.DAY_OF_YEAR, days);
		return from.getTime();
	}

	/**
	 * Add specified number of days to the given date.
	 * 
	 * @param days
	 *            The number of days.
	 * @param from
	 *            date in milliseconds
	 * @return date that is days after "from" date
	 */
	public static Date daysLater(int days, long from) {
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(from);
		return daysLater(days, date);
	}

	/**
	 * Subtracts specified number of days to the given date.
	 * 
	 * @param days
	 *            The number of days.
	 * @param from
	 *            date in milliseconds
	 * @return date that is days before "from" date
	 */
	public static Date daysBefore(int days, long from) {
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(from);
		return daysLater(-1 * days, date);
	}

	/**
	 * Subtracts specified number of months to the given date.
	 * 
	 * @param months
	 *            The number of months.
	 * @param from
	 *            date in milliseconds
	 * @return date that is months before "from" date
	 */
	public static Date monthsBefore(int months, long from) {
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(from);
		return monthsBefore(months, date);
	}

	/**
	 * Subtracts specified number of months to the given date.
	 * 
	 * @param months
	 *            The number of months.
	 * @param from
	 *            date in milliseconds
	 * @return date that is months after "from" date
	 */
	public static Date monthsAfter(int months, long from) {
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(from);
		return monthsAfter(months, date);
	}

	/**
	 * Subtracts specified number of days to the given date.
	 * 
	 * @param days
	 *            The number of days.
	 * @param from
	 *            date
	 * @return date that is days before "from" date
	 */
	public static Date daysBefore(int days, Calendar from) {
		from.add(Calendar.DAY_OF_YEAR, -1 * days);
		return from.getTime();
	}

	/**
	 * Subtracts specified number of months to the given date.
	 * 
	 * @param months
	 *            The number of months.
	 * @param from
	 *            date
	 * @return date that is days before "from" date
	 */
	public static Date monthsBefore(int months, Calendar from) {
		from.add(Calendar.MONTH, -1 * months);
		return from.getTime();
	}

	/**
	 * Adds specified number of months to the given date.
	 * 
	 * @param months
	 *            The number of months.
	 * @param from
	 *            date
	 * @return date that is days after "from" date
	 */
	public static Date monthsAfter(int months, Calendar from) {
		from.add(Calendar.MONTH, months);
		return from.getTime();
	}

	/**
	 * Subtracts specified number of days from the current date. The current
	 * date will have millisecond resolution.
	 * 
	 * @param days
	 *            The number of days.
	 * @return date that is days before current date.
	 */
	public static Date daysBefore(int days) {
		return daysBefore(days, System.currentTimeMillis());
	}

	/**
	 * Add specified number of days to the current date. The current date will
	 * have millisecond resolution.
	 * 
	 * @param days
	 *            The number of days.
	 * @return date that is days after current date.
	 */
	public static Date daysLater(int days) {
		return daysLater(days, System.currentTimeMillis());
	}

	public static Date today() {
		Calendar date = Calendar.getInstance();
		toMidnight(date);
		return date.getTime();
	}

	public static Date now() {
		return Calendar.getInstance().getTime();
	}

	public static Date now(TimeZone timeZone) {
		return Calendar.getInstance(timeZone).getTime();
	}

	public static Date toGMT(Date localDate) {
		return new Date(localDate.getTime() - TimeZone.getDefault().getOffset(localDate.getTime()));
	}

	public static Date fromGMT(Date gmtDate) {
		return new Date(gmtDate.getTime() + TimeZone.getDefault().getOffset(gmtDate.getTime()));
	}

	private static void toMidnight(Calendar date) {
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
	}

	public static Date toMidnight(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		toMidnight(calendar);
		return calendar.getTime();
	}

	public static void toStartOfDay(Calendar date) {
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
	}

	private static void toStartOfMonth(Calendar date) {
		date.set(Calendar.DAY_OF_MONTH, 1);
		toStartOfDay(date);
	}

	public static Date toStartOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		toStartOfDay(calendar);
		return calendar.getTime();
	}

	public static Date toStartOfDay(TimeZone timeZone, Date date) {
		Calendar calendar = Calendar.getInstance(timeZone);
		calendar.setTime(date);
		toStartOfDay(calendar);
		return calendar.getTime();
	}

	public static Date toStartOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		toStartOfMonth(calendar);
		return calendar.getTime();
	}

	public static void toEndOfDay(Calendar date) {
		date.set(Calendar.HOUR_OF_DAY, 23);
		date.set(Calendar.MINUTE, 59);
		date.set(Calendar.SECOND, 59);
		date.set(Calendar.MILLISECOND, 999);
	}

	public static Date toEndOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		toEndOfDay(calendar);
		return calendar.getTime();
	}

	public static Date toEndOfDay(TimeZone timeZone, Date date) {
		Calendar calendar = Calendar.getInstance(timeZone);
		calendar.setTime(date);
		toEndOfDay(calendar);
		return calendar.getTime();
	}

	public static String formatForServerDate(Locale userLocale, Date date) {
		SimpleDateFormat dateFormat = getFormatter(userLocale);
		StringBuffer sbDate = new StringBuffer(dateFormat.format(date));
		// hack around date format returning 2 digit year... calendar picker
		// wants to make it 19XX instead of 20XX
		if (isTwoDigitYear(sbDate)) {
			sbDate.insert(sbDate.length() - 2, "20");
		}
		return sbDate.toString();
	}

	public static Date parseForServerDate(Locale userLocale, String date) throws ParseException {
		SimpleDateFormat dateFormat = getFormatter(userLocale);
		return dateFormat.parse(date);
	}

	private static SimpleDateFormat getFormatter(Locale userLocale) {
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, userLocale);
		String format = ((SimpleDateFormat) df).toPattern();

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(AppsGlobals.getTimeZone());

		// ensure y2k
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -5);
		dateFormat.set2DigitYearStart(cal.getTime());
		return dateFormat;
	}

	private static boolean isTwoDigitYear(StringBuffer sbDate) {
		int yearStartIfTwoDigit = sbDate.length() - 3;
		return sbDate.lastIndexOf("/") == yearStartIfTwoDigit || sbDate.lastIndexOf(".") == yearStartIfTwoDigit;
	}

	public static Date hoursLater(int i, Date from) {
		return later(i, Calendar.HOUR_OF_DAY, from);
	}

	public static Date minutesLater(int i, Date from) {
		return later(i, Calendar.MINUTE, from);
	}

	public static Date secondsLater(int i, Date from) {
		return later(i, Calendar.SECOND, from);
	}

	private static Date later(int i, int field, Date from) {
		Calendar date = toCalendar(from);
		date.add(field, i);
		return date.getTime();
	}

	public static Date hoursBefore(int i, Date from) {
		return before(i, Calendar.HOUR_OF_DAY, from);
	}

	public static Date minutesBefore(int i, Date from) {
		return before(i, Calendar.MINUTE, from);
	}

	public static Date secondsBefore(int i, Date from) {
		return before(i, Calendar.SECOND, from);
	}

	private static Date before(int i, int field, Date from) {
		Calendar date = toCalendar(from);
		date.add(field, -1 * i);
		return date.getTime();
	}

	private static Calendar toCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		return calendar;
	}

	public static Date yesterday() {
		return daysLater(-1, today().getTime());
	}

	public static Date tomorrow() {
		return daysLater(1, today().getTime());
	}

	/**
	 * Used to parse the indexed string value of the date using
	 * <tt>DateValidator.getPattern()</tt>
	 * 
	 * @param value
	 *            the indexed string value of the date
	 * @return the Date parsed from the indexed string value
	 * @see com.jivesoftware.community.field.validator.DateValidator
	 */
	public static Date parseDate(String value) {
		Date date = null;
		if (value != null) {
			// SimpleDateFormat sdf = new SimpleDateFormat(new
			// DateValidator().getPattern());
			try {
				date = AppsGlobals.parseDate(value);// sdf.parse(value);
			} catch (java.text.ParseException e) {
				log.info("Unable to parse user profile date: " + value + ". Date will not be indexed.");
			}
		}
		return date;
	}

	/**
	 * Returns a new Date object for the provided date (to break a reference to
	 * the mutable date). If null is passed in null will be returned.
	 * 
	 * @param date
	 *            the date to make a new instance
	 * @return a new Date object for the provided date or null if no date was
	 *         provided
	 */
	public static Date newInstance(Date date) {
		return date == null ? null : new Date(date.getTime());
	}

	static final double ONE_HOUR = 60 * 60 * 1000L;

	public static double daysBetween(Date d1, Date d2) {
		return ((d2.getTime() - d1.getTime() + ONE_HOUR) / (ONE_HOUR * 24));
	}

	/*
	 * public static Date getDayOfWeek(Date date, int day) { DateTime jodaDate =
	 * new DateTime(date.getTime()); int dayOfWeek = jodaDate.getDayOfWeek();
	 * if(dayOfWeek >= day){ return jodaDate.minusDays(dayOfWeek -
	 * day).toDate(); } return jodaDate.minusDays(dayOfWeek).minusDays(7 -
	 * day).toDate(); }
	 */

	public static int compare(Date date1, Date date2) {
		if (date1 != null && date2 != null) {
			return date1.compareTo(date2);
		} else if (date1 == null && date2 != null) {
			return -1;
		} else if (date1 != null && date2 == null) {
			return 1;
		}
		return 0;
	}
}
