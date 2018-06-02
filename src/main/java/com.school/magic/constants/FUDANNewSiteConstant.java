package com.school.magic.constants;

import java.util.ArrayList;
import java.util.List;

public class FUDANNewSiteConstant {

    public static final String LOGIN_URL = "https://bbs.fudan.edu.cn/bbs/login.json";
    public static final String USERNAME = "tjshif";
    public static final String PASSWORD = "welcomeme";

    public static final List<String> JOB_URL_LIST = new ArrayList<String>() {{
        add("https://bbs.fudan.edu.cn/v18/tdoc?board=Job_Intern");  //实习
        add("https://bbs.fudan.edu.cn/v18/tdoc?board=Job_Plaza");  //全职
        add("https://bbs.fudan.edu.cn/v18/tdoc?board=Job_IT"); //IT
    }};
    public static final String FRIEND_URL = "https://bbs.fudan.edu.cn/v18/tdoc?board=Magpie_Bridge";

    public static final String FUDAN_BBS_DOMAIN = "bbs.fudan.edu.cn";

    public static final String FORMNODE = "//*[@id=\"b-p\"]/li";
    //用于判断是否有详情页
    public static final String FORMITEMCHILD = "//a[1]";

    public static final String FORMITEMLINK = "//a[1]";

    public static final String COOKIEPATH = "//body/text()";

    //发帖时间：2010-05-25 18:06:18
    public static final String FORMITEMMODIFYTIME = "//time/@title";
    //for example: tdoc?bid=70&start=2072
    public static final String FORMITEMNEXTPAGE = "//div[@class='bdr-t']";
    //主题
    public static final String FORMITEMTITEL = "//a[1]/text()";

    //一行一个p标签，组合起来即可
    public static final String DETAIL_CONTENT = "//article/p";

    //详情页标题
    public static final String DETAIL_SUBJECT = "//article/header/h1/text()";

    //详情页中的发帖时间  2018年06月01日09:14:32 星期五
    public static final String DETAIL_POSTDATE = "//article/header/a/time/@title";
}
