package com.school.utils;

import org.apache.http.util.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String DATE_REGX = "\\d+-\\d+-\\d+\\s+\\d+:\\d+"; //yyyy-MM-dd hh:mm
    public static String DATE_REGX2 = "\\D+\\s+\\d+\\s+\\d+:\\d+"; //dd hh:mm
    public static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd hh:mm";
    public static String ENGLISH_DATE_FORMAT = "MMM d HH:mm yyyy";

    public static Date getDateFromString(String dateString, String dateFormat) {
        if (TextUtils.isEmpty(dateString))
            return null;

        SimpleDateFormat formatter = null;
        if (dateFormat.equalsIgnoreCase(ENGLISH_DATE_FORMAT))
            formatter = new SimpleDateFormat(ENGLISH_DATE_FORMAT, Locale.ENGLISH);
        else
            formatter= new SimpleDateFormat(dateFormat);

        Date strtodate = null;
        try {
            strtodate = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
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
