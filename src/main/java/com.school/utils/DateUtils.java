package com.school.utils;

import org.apache.http.util.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String DATE_REGX = "\\d+-\\d+-\\d+\\s+\\d+:\\d+"; //yyyy-MM-dd hh:mm
    public static Date getDateFromString(String dateString) {
        if (TextUtils.isEmpty(dateString))
            return null;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date strtodate = null;
        try {
            strtodate = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return strtodate;
    }

}
