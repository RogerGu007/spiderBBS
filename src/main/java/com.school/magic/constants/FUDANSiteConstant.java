package com.school.magic.constants;

import java.util.ArrayList;
import java.util.List;

public class FUDANSiteConstant {
    //可以不用登陆，游客模式
//    public static final String LOGIN_URL = "http://www.newsmth.net";
    //可以不用登陆，该版面下没有交友。 但是只能看10页，100条求职信息
    //todo  会自动跳转掉，需要再看下。 非官方论坛
    public static final String JOB_URL = "http://fdubbs.com/forum.php?ForumID=21";
    //该版面下的求职需要用复旦的邮箱注册登录，所以求职不能在这个版面   求职和交友用两个SiteHandler来做
    public static final String FRIEND_URL = "https://bbs.fudan.edu.cn/bbs/tdoc?bid=70";

    public static final String FUDAN_BBS_JOB_DOMAIN = "bbs.fudan.edu.cn";

//    public static final String FORMNODE = "//tbody/tr";
    public static final String FORMNODE = "//body/bbsdoc/po";
    //拼接上域名即可
    //https://bbs.fudan.edu.cn/bbs/tcon?new=1&bid=70&f=3180055149393479130
//    public static final String FORMITEMLINK = "//td[5]/a";
    public static final String FORMITEMLINK = "//po/@id";
    public static final String LINKTEMPLATE = "https://bbs.fudan.edu.cn/bbs/tcon?new=1&bid=70&f=PLACEHOLDER";
    //发帖时间：2018-03-01T21:42:32
//    public static final String FORMITEMMODIFYTIME= "//td[4]/text()";
    public static final String FORMITEMMODIFYTIME= "//po/@time";
    //for example: tdoc?bid=70&start=2072
    public static final String FORMITEMNEXTPAGE = "//body/bbsdoc/brd/@start";
    public static final String NEXTPAGETEMPLATE = "https://bbs.fudan.edu.cn/bbs/tdoc?bid=70&start=PLACEHOLDER";
    public static final String NEXTPAGESEPERATE = "start=";
    //主题
    //public static final String FORMITEMTITEL = "//td[5]/a/text()";
    public static final String FORMITEMTITEL = "//po/text()";
    //用于判断是否有详情页
    public static final String FORMITEMCHILD = "//po";

    public static final String FORMITEMFILTER = "//@sticky";

    //一行一个p标签，组合起来即可
    public static final String DETAIL_CONTENT = "//pa";
    //详情页标题
    public static final String DETAIL_SUBJECT = "//title[1]/text()";
    //详情页中的发帖时间
    public static final String DETAIL_POSTDATE = "//date[1]/text()";
}
