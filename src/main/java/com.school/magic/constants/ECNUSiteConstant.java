package com.school.magic.constants;

import java.util.ArrayList;
import java.util.List;

public class ECNUSiteConstant {

    public static final List<String> JOB_URL_LIST = new ArrayList<String>() {{
        add("http://bbs.iecnu.com/forum-134-1.html");  //全职
        add("http://bbs.iecnu.com/forum-135-1.html");  //兼职
    }};

    public static final String FRIEND_URL = "http://bbs.iecnu.com/forum-14-1.html";

    public static final String ECNU_BBS_DOMAIN = "bbs.iecnu.com";

    public static final String FORMNODE = "//table[1]/tbody/tr";
    //for example: thread-667618-1-1.html
    public static final String FORMITEMLINK = "//tr/th/a[3]";
    //两种格式：FORMITEMMODIFYTIME 2018-4-6，优先取 FORMITEMMODIFYTIME；取不到再取FORMITEMMODIFYTIME2
    public static final String FORMITEMMODIFYTIME = "//tr/td[2]/em/span/span/@title";
    public static final String FORMITEMMODIFYTIME_BAK = "//tr/td[2]/em/span/text()";
    //有页码，也有下一页，替换掉首页的字符串?bid=845&mode=topic即可  for example: ?bid=845&mode=topic&page=2
    public static final String FORMITEMNEXTPAGE = "//*[@id=\"fd_page_bottom\"]/div/a";
    //主题
    public static final String FORMITEMTITEL = "//tr/th/a[3]/text()";
    //用于判断是否有详情页
    public static final String FORMITEMCHILD = "//tr/th/a[3]/@href";
    //列表页的详情页href
    public static final String FORMITEMLINKURL = "//tr/th/a[3]/@href";

    //数组，需要extract后循环匹配出第一个
    public static final String DETAIL_CONTENT = "//td[@class=\"t_f\"]";
    //详情页标题
    public static final String DETAIL_SUBJECT = "//*[@id=\"thread_subject\"]/text()";
    //详情页中的发帖时间
    public static final String DETAIL_POSTDATE = "//div[@class=\"authi\"]/em";
}
