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

    //获取登陆的cookie
    public static final String COOKIEPATH = "//body/text()";

    public static final String FORMNODE = "//body/bbsdoc/po";
    //用于判断是否有详情页
    public static final String FORMITEMCHILD = "//po";

    public static final String FORMITEMLINK = "//po/@id";
    public static final String LINKSPLITER = "board=";
    public static final String LINKTEMPLATE = "https://bbs.fudan.edu.cn/v18/tcon?new=1&board=BOARD_PLACEHOLDER&f=ID_PLACEHOLDER";

    //发帖时间：2018-03-01T21:42:32
    public static final String FORMITEMMODIFYTIME = "//po/@time";
    //for example: tdoc?board=Job_Intern&start=2072
    public static final String FORMITEMNEXTPAGE = "//body/bbsdoc/brd/@start";
    public static final String NEXTPAGETEMPLATE = "https://bbs.fudan.edu.cn/v18/tdoc?board=BOARD_PLACEHOLDER&start=ID_PLACEHOLDER";
    public static final String NEXTPAGESEPERATE = "start=";
    //主题
    public static final String FORMITEMTITEL = "//po/text()";

    public static final String FORMITEMFILTER = "//@sticky";

    //一行一个p标签，组合起来即可
    public static final String DETAIL_CONTENT = "//pa";

    //详情页标题
    public static final String DETAIL_SUBJECT = "//title[1]/text()";

    //详情页中的发帖时间
    public static final String DETAIL_POSTDATE = "//date[1]/text()";
}
