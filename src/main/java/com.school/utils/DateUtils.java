package com.school.utils;

import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String DATE_REGX = "\\d+-\\d+-\\d+\\s+\\d+:\\d+"; //yyyy-MM-dd hh:mm
    public static String DATE_REGX2 = "\\D+\\s+\\d+\\s+\\d+:\\d+"; //dd hh:mm
    public static String DATE_REGX_FOR_DAY = "\\d+-\\d+-\\d+"; //yyyy-MM-dd
    public static String DATE_REGX_FOR_TIME = "\\d+:\\d+:\\d+"; //hh-mm-ss
    public static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd hh:mm";
    public static String DEFAULT_DATE_FORMAT2 = "yyyy-MM-dd hh:mm:ss";
    public static String DEFAULT_DATE_FORMAT3 = "yyyy-MM-dd";
    public static String ENGLISH_DATE_FORMAT = "MMM d HH:mm yyyy";
    public static String NORMAL_ENGLISH_DATE_FORMAT = "EEE MMM dd HH:mm:ss yyyy";

    private static Logger logger = LoggerFactory.getLogger(DateUtils.class);

    public static Date getDateFromString(String dateString, String dateFormat) {
        if (TextUtils.isEmpty(dateString))
            return null;

        SimpleDateFormat formatter = null;
        if (dateFormat.contains("MMM") || dateFormat.contains("EEE"))
            formatter = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        else
            formatter= new SimpleDateFormat(dateFormat);

        Date strtodate = null;
        try {
            strtodate = formatter.parse(dateString);
        } catch (ParseException e) {
            logger.warn("date parse failed, dateString=" + dateString);
        }

        return strtodate;
    }

    public static String getStringFromDate(Date date, String dateFormat) {
        if (date == null)
            return null;

        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(date);
    }

}
