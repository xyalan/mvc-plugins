package com.hialan.hv.commons.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by IntelliJ IDEA.
 * User: Alan Yang
 * Date: 12-3-28
 * Time: 上午11:15
 */
public abstract class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    private static final int DAY_HOURS = 24;
    private static final int MINUTES = 60;
    private static final int SECONDS = 60;
    private static final int MILLION_SECONDS = 1000;
    public static final String MONTH_PATTERN = "yyyy-MM";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String SERIAL_DATE_PATTERN = "yyyyMMdd";
    public static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String LONG_TIME_PATTERN = "yyyyMMddHHmmss";
    public static final String HOUR_MINUTE_PATTERN = "HH:mm";
    public static final String DATE_FORMAT_8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ";
    public static final String MONTH_DATE_SHORT = "dd/MM";

    public static Date currentDate() {
        return new Date();
    }

    public static Date yesterday() {
        return DateUtils.getYmdDate(addDays(currentDate(), -1));
    }

    public static Date createDate(int year, int month, int date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, date);
        calendar.set(Calendar.AM_PM, Calendar.AM);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date createFullDate(int year, int month, int date, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static String formatMonth(Date date) {
        return formatDate(date, MONTH_PATTERN);
    }

    public static String formatDate(Date date) {
        return formatDate(date, DATE_PATTERN);
    }

    public static String formatHourMinute(Date date) {
        return formatDate(date, HOUR_MINUTE_PATTERN);
    }

    public static Date beginningOfDate(Date date) {
        Date result = null;
        if (date != null) {
            String temp = formatDate(date, DATE_PATTERN);
            try {
                result = parseDate(temp + " 00:00:00", new String[]{TIME_PATTERN});
            } catch (ParseException e) {
            }
        }
        return result;
    }

    public static Date endOfDate(Date date) {
        Date result = null;
        if (date != null) {
            String temp = formatDate(date, DATE_PATTERN);
            try {
                result = parseDate(temp + " 23:59:59", new String[]{TIME_PATTERN});
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static boolean isBetween(Calendar date, Calendar start, Calendar end) {
        return date.compareTo(start) >= 0 && date.compareTo(end) < 0;
    }

    public static boolean isBetweenOrEq(Calendar date, Calendar start, Calendar end) {
        return date.compareTo(start) >= 0 && date.compareTo(end) <= 0;
    }

    public static boolean isBetween(Date date, Date start, Date end) {
        return date.compareTo(start) >= 0 && date.compareTo(end) < 0;
    }

    public static String formatDateTime(Date date) {
        return formatDate(date, TIME_PATTERN);
    }

    public static Date parseDateTime(String date) {
        try {
            return parseDate(date, new String[]{TIME_PATTERN});
        } catch (ParseException e) {
            return null;
        }
    }

    public static String formatLongDateTime(Date date) {
        return formatDate(date, LONG_TIME_PATTERN);
    }

    public static Date parseLongDateTime(String date) {
        try {
            return parseDate(date, new String[]{LONG_TIME_PATTERN});
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parseIsoDate(String date) {
        DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.dateTime();
        return dateTimeFormatter.parseDateTime(date).toDate();
    }

    public static Calendar parseIsoDateToCalendar(String date) {
        DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.dateTime();
        DateTime dateTime = dateTimeFormatter.parseDateTime(date);
        Calendar instance = Calendar.getInstance();
        instance.setTime(dateTime.toDate());
        return instance;
    }

    public static String formatDate(Date date, String format) {
        return formatDate(date, format, null);
    }

    private static String formatDate(Date date, String format, TimeZone timeZone) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        if (null != timeZone) {
            simpleDateFormat.setTimeZone(timeZone);
        }
        return date != null ? simpleDateFormat.format(date) : "";
    }

    public static Date parseDate(String date) {
        try {
            return parseDate(date, new String[]{DATE_PATTERN});
        } catch (ParseException e) {
            return null;
        }
    }

    public static Calendar parseCalendar(String date) {
        try {
            return convert(parseDate(date, new String[]{DATE_PATTERN}));
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parseSerialDate(String date) {
        try {
            return parseDate(date, new String[]{SERIAL_DATE_PATTERN});
        } catch (ParseException e) {
            return null;
        }
    }

    public static Calendar parseSerialCalendar(String date) {
        try {
            return convert(parseDate(date, new String[]{SERIAL_DATE_PATTERN}));
        } catch (ParseException e) {
            return null;
        }
    }

    public static Calendar parseCalendarTime(String date) {
        try {
            return convert(parseDate(date, new String[]{TIME_PATTERN}));
        } catch (ParseException e) {
            return null;
        }
    }

    public static Calendar parseCalendarLongTime(String date) {
        try {
            return convert(parseDate(date, new String[]{LONG_TIME_PATTERN}));
        } catch (ParseException e) {
            return null;
        }
    }

    public static XMLGregorianCalendar create(int year, int month, int day) {
        XMLGregorianCalendar date = null;
        try {
            date = DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(
                            new GregorianCalendar(year, month - 1, day));
        } catch (DatatypeConfigurationException e) {
        }
        return date;
    }

    public static Date getYmdDate(Date date) {
        if (date != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            return c.getTime();
        }
        return date;
    }

    public static Calendar createCalendar(int year, int month, int date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, date);
        calendar.set(Calendar.AM_PM, Calendar.AM);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static int interval(Calendar date1, Calendar date2) {
        return interval(date1, date2, false);
    }

    public static int interval(Calendar date1, Calendar date2, boolean includeLastDay) {
        Calendar d1 = (Calendar) date1.clone();
        Calendar d2 = (Calendar) date2.clone();
        long end = org.apache.commons.lang3.time.DateUtils.truncate(d2.getTime(), Calendar.DATE).getTime();
        long start = org.apache.commons.lang3.time.DateUtils.truncate(d1.getTime(), Calendar.DATE).getTime();
        int days = (int) ((end - start) / ((long) (DAY_HOURS * MINUTES * SECONDS * MILLION_SECONDS)));
        return includeLastDay ? days + 1 : days;
    }

    public static int interval(Date startDate, Date endDate, boolean includeLastDay) {
        Date d1 = (Date) startDate.clone();
        Date d2 = (Date) endDate.clone();
        long end = d2.getTime();
        long start = d1.getTime();
        int days = (int) ((end - start) / ((long) (DAY_HOURS * MINUTES * SECONDS * MILLION_SECONDS)));
        return includeLastDay ? days + 1 : days;
    }

    public static Calendar convert(Date date) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        return c1;
    }

    public static Calendar addDays(Calendar c, int amount) {
        Calendar cc = (Calendar) c.clone();
        cc.add(Calendar.DAY_OF_MONTH, amount);
        return cc;
    }

    public static boolean after(Calendar c1, Calendar c2) {
        return truncate(c1, Calendar.DATE).after(truncate(c2, Calendar.DATE));
    }

    public static boolean beforeOrEqual(Date d1, Date d2) {
        return !truncate(d1, Calendar.DATE).after(truncate(d2, Calendar.DATE));
    }

    public static boolean afterOrEqual(Date d1, Date d2) {
        return !truncate(d1, Calendar.DATE).before(truncate(d2, Calendar.DATE));
    }

    public static boolean beforeOrEqual(Calendar c1, Calendar c2) {
        return !truncate(c1, Calendar.DATE).after(truncate(c2, Calendar.DATE));
    }

    public static boolean afterOrEqual(Calendar c1, Calendar c2) {
        return !truncate(c1, Calendar.DATE).before(truncate(c2, Calendar.DATE));
    }

    public static boolean between(Date date, Date from, Date to) {
        return afterOrEqual(date, from) && beforeOrEqual(date, to);
    }

    public static boolean between(Calendar c, Calendar from, Calendar to) {
        return afterOrEqual(c, from) && beforeOrEqual(c, to);
    }

    public static Calendar toLastSeconds(Date date) {
        Calendar lastSecond = Calendar.getInstance();
        lastSecond.setTime(date);
        lastSecond.add(Calendar.DATE, 1);
        lastSecond.add(Calendar.SECOND, -1);
        return lastSecond;
    }

    public static int getYear(Date date) {
        return convert(date).get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        return convert(date).get(Calendar.MONTH) + 1;
    }

    public static int getCurrentYear() {
        return getYear(currentDate());
    }

    public static int getCurrentMonth() {
        return getCurrentCalendar().get(Calendar.MONTH) + 1;
    }

    public static Calendar getCurrentCalendar() {
        return convert(currentDate());
    }

    public static String formatDate(Date date, TimeZone timeZone) {
        return formatDate(date, DATE_PATTERN, timeZone);
    }

    public static String formatMonth(Date date, TimeZone timeZone) {
        return formatDate(date, MONTH_PATTERN, timeZone);
    }

    private static int getDayOfWeek(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    private static boolean isDayOfWeek(Date date, int day) {
        return getDayOfWeek(date) == day;
    }

    public static boolean isMonDay(Date date) {
        return isDayOfWeek(date, Calendar.MONDAY);
    }

    public static boolean isTuesday(Date date) {
        return isDayOfWeek(date, Calendar.TUESDAY);
    }

    public static boolean isWednesday(Date date) {
        return isDayOfWeek(date, Calendar.WEDNESDAY);
    }

    public static boolean isThursday(Date date) {
        return isDayOfWeek(date, Calendar.THURSDAY);
    }

    public static boolean isFriday(Date date) {
        return isDayOfWeek(date, Calendar.FRIDAY);
    }

    public static boolean isSaturday(Date date) {
        return isDayOfWeek(date, Calendar.SATURDAY);
    }

    public static boolean isSunday(Date date) {
        return isDayOfWeek(date, Calendar.SUNDAY);
    }

    public static Date getDate(Calendar source, int beforeDays, String endHourMinSec) {
        Calendar calendar = addDays(source, beforeDays);
        String day = formatDate(calendar.getTime());
        return parseDateTime(day + " " + endHourMinSec);
    }

    public static Date getEndOfMonth(int year, int month) {
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.YEAR, year);
        instance.set(Calendar.MONTH, month - 1);
        int days = instance.getMaximum(Calendar.DAY_OF_MONTH);
        instance.set(Calendar.DAY_OF_MONTH, days);
        instance.set(Calendar.HOUR_OF_DAY, 0);
        instance.set(Calendar.MINUTE, 0);
        instance.set(Calendar.SECOND, 0);

        Date date = instance.getTime();
        return parseDate(formatDate(date));
    }

    public static XMLGregorianCalendar toXMLGregorianCalendar(Date date) throws Exception {
        if (date == null)
            return null;
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
    }

	public static Date millisecondsToDate(long milliseconds) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliseconds);
		return calendar.getTime();
	}
}
