package com.school.magic.constants;

import java.util.ArrayList;
import java.util.List;

public class PEKINGSiteConstant {
    //可以不用登陆，游客模式
//    public static final String LOGIN_URL = "http://www.newsmth.net";

    public static final List<String> JOB_URL_LIST = new ArrayList<String>() {{
        add("https://bbs.pku.edu.cn/v2/thread.php?bid=896&mode=topic");  //实习
        add("https://bbs.pku.edu.cn/v2/thread.php?bid=845&mode=topic");  //全职
    }};

//    public static final String FRIEND_URL = "https://bbs.tongji.net/forum-336-1.html";

    public static final String PEKINGBBSDOMAIN = "bbs.pku.edu.cn";
    //不需要再过滤了，根据class就可以过滤
    public static final String FORMNODE = "//div[@class=\"list-item-topic list-item\"]";
    // 需要domain再加上/v2  for example: post-read.php?bid=845&threadid=16378112
    public static final String FORMITEMLINK = "//a[1]";
    // 四种格式：1、04-03 14:49   2、16:59   3.昨天 13:08  4.40分钟前  补全为 2018-04-06 21:00:00
    // 后三种都是近期的帖子可以不需要过滤
    public static final String FORMITEMMODIFYTIME= "//div[5]/div[2]/text()";
    //有页码，也有下一页，用页码获取nextPageList
    public static final String FORMITEMNEXTPAGE = "//*[@id=\"board-body\"]/div[3]/div/a";
    //主题
    public static final String FORMITEMTITEL = "//div[3]";
    //用于判断是否有详情页
    public static final String FORMITEMCHILD = "//a/@href";
    //列表页的详情页href
    public static final String FORMITEMLINKURL = "//div[@class=\"list-item-topic list-item\"]/a/@href";

    //一行一个p标签，需要过滤掉为空的行
    public static final String DETAIL_CONTENT = "//*[@id=\"post-read\"]/div[2]/div/div[3]/div[1]/div[1]/p/text()";
    //详情页标题
    public static final String DETAIL_SUBJECT_TAG = "//*[@id=\"post-read\"]/header/h3/text()";
    //详情页中的发帖时间    2018-04-07 15:48:44
    public static final String DETAIL_POSTDATE_TAG = "//span[1]/span/text()";

    //news的抽取顺序
    public static final ExtractSequenceType EXTRACT_NEWS_SEQUENCE = ExtractSequenceType.AFTER_INDEX;
    //newsDetail的抽取顺序
    public static final ExtractSequenceType EXTRACT_NEWSDETAIL_SEQUENCE = ExtractSequenceType.AFTER_NEWS;
}
