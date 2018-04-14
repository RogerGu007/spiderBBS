package com.school.magic.constants;

public class ZJUSiteConstant {

    public static final String JOB_URL  = "http://www.zju1.com/cxz.asp?bd=71";

//    public static final String FRIEND_URL = "https://bbs.tongji.net/forum-336-1.html";

    public static final String ZJU_BBS_DOMAIN = "www.zju1.com";
    public static final String FORMNODE = "//table[6]/tbody/tr";
    public static final String FORMITEMLINK = "//tr/td[2]/a";
    // for example: 04-12 17:19  需要补足年
    public static final String FORMITEMMODIFYTIME= "//tr/td[5]/text()";
    public static final String FORMCURRENTPAGE = "#";
    public static final String FORMITEMNEXTPAGE = "//table[7]/tbody/tr/td[2]/a";
    public static final String FORMITEMTITEL = "//tr/td[2]/a/text()";
    public static final String FORMITEMLINKURL = "//tr/td[2]/a/@href";

    public static final String DETAIL_SUBJECT = "//table[4]/tbody/tr[1]/td[1]/a/font/text()";
    //for example: 2018-4-10 15:11:36
    public static final String DETAIL_POSTDATE = "//table[5]/tbody/tr[1]/td[2]/p/text()";
    public static final String DETAIL_CONTENT  = "//table[5]/tbody/tr[2]/td";
}
