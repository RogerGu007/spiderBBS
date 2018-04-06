package com.school.magic.constants;

import java.util.ArrayList;
import java.util.List;

public class TSINGSiteConstant {
    //可以不用登陆，游客模式
    public static final String LOGIN_URL = "http://www.newsmth.net";

    public static final List<String> JOB_URL_LIST = new ArrayList<String>() {{
        add("http://www.newsmth.net/nForum/#!board/Career_Campus");  //校招
        add("http://www.newsmth.net/nForum/#!board/Career_Upgrade");  //社招
    }};

//    public static final String FRIEND_URL = "https://bbs.tongji.net/forum-336-1.html";

    public static final String TSINGBBSDOMAIN = "www.newsmth.net";
    //需要过滤掉top类的tr标签，这是群公告的信息
    public static final String FORMNODE = "//tbody/tr";
    //拼接上域名即可  for example: /nForum/article/Career_Upgrade/610573
    public static final String FORMITEMLINK = "//td[1]/a/@href";
    //两种格式：1、19:38:08，补足为当日 2018-04-06 19:38:08；2、2018-03-30，补足2018-03-30 00:00:00
    public static final String FORMITEMMODIFYTIME= "//td[3]/text()";
    //需要拼接  for example: bbstdoc?board=JobExpress&start=11878
    public static final String FORMITEMNEXTPAGE = "//div[4]/div[1]/ul/li[2]/ol/li[2]/a/@href";
    //详情页标题
    public static final String FORMITEMTITEL = "//tr/td[2]/text()";
    //用于判断是否有详情页
    public static final String FORMITEMCHILD = "//tr/td[1]/a/@href";
    //列表页的详情页href
    public static final String FORMITEMLINKURL = "//tr/td[1]/a/@href";

    //用xpath方式的extract函数抽取出多个数组
    // [0]:发信人+信区 [1]:标题 [2] 发信站（可以提取时间），其他列过滤为空的内容组合起来就是详情内容
    public static final String DETAIL = "//tr[2]/td[2]/p/text()";
    //详情页中抽取主题的关键词
    public static final String DETAIL_SUBJECT_TAG= "标\\s+题:\\s+\\S+\\n";
    //详情页中抽取发帖时间的关键词  Tue Mar 13 00:12:46 2018
    public static final String DETAIL_POSTDATE_TAG =
            "发信站:\\S+[A-Z][a-z]{2}\\s+[A-Z][a-z]{2}\\s+\\d\\s+[0-9]{2}:[0-9]{2}:[0-9]{2}\\s+\\d";

    //news的抽取顺序
    public static final ExtractSequenceType EXTRACT_NEWS_SEQUENCE = ExtractSequenceType.AFTER_INDEX;
    //newsDetail的抽取顺序
    public static final ExtractSequenceType EXTRACT_NEWSDETAIL_SEQUENCE = ExtractSequenceType.AFTER_NEWS;
}
