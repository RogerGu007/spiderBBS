package com.school.utils;

import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    /**
     * 获取某段时这里写代码片间内的所有日期
     * @param dBegin
     * @param dEnd
     * @return
     */
    public static List<Date> findDates(Date dBegin, Date dEnd) {
        List<Date> lDate = new ArrayList<Date>();
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime()))  {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }
}
