package com.school.utils;

import java.util.regex.Pattern;

public class HtmlUtils {

    public static String filterHtmlTag(String htmlStr) {
        String regexHtml = "<[^>]+>"; //定义HTML标签的正则表达式
        return Pattern.compile(regexHtml, Pattern.CASE_INSENSITIVE)
                .matcher(htmlStr).replaceAll("");
    }
}
