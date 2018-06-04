package com.school.magic.constants;

import java.util.ArrayList;
import java.util.List;

public class TSINGSiteConstant {
    //可以不用登陆，游客模式
    public static final String LOGIN_URL = "http://www.newsmth.net";

    public static final List<String> JOB_URL_LIST = new ArrayList<String>() {{
        add("http://www.newsmth.net/nForum/board/Career_Campus");  //校招
        add("http://www.newsmth.net/nForum/board/Career_Upgrade");  //社招
    }};

    public static final String FRIEND_URL = "http://www.newsmth.net/nForum/board/PieLove";

    public static final String TSINGBBSDOMAIN = "www.newsmth.net";
    //需要过滤掉top类的tr标签，这是群公告的信息
    public static final String FORMNODE = "//tbody/tr";
    //拼接上域名即可  for example: /nForum/article/Career_Upgrade/610573
    public static final String FORMITEMLINK = "//td[1]/a";
    //两种格式：1、19:38:08，补足为当日 2018-04-06 19:38:08；2、2018-03-30，补足2018-03-30 00:00:00
    public static final String FORMITEMMODIFYTIME= "//td[3]/text()";
    //需要去掉第一个，为当前页
    public static final String FORMITEMNEXTPAGE = "//div[4]/div[1]/ul/li[2]/ol/li/a";
    //详情页标题
    public static final String FORMITEMTITEL = "//tr/td[2]/a/text()";
    //用于判断是否有详情页
    public static final String FORMITEMCHILD = "//tr/td[1]/a/@href";
    //列表页的详情页href
    public static final String FORMITEMLINKURL = "//tr/td[1]/a/@href";

    //用xpath方式的extract函数抽取出多个数组
    // [0]:发信人+信区 [1]:标题 [2] 发信站（可以提取时间），其他列过滤为空的内容组合起来就是详情内容
    public static final String DETAIL_CONTENT = "//tr[2]/td[2]";
    public static final String SUB_DETAIL_CONTENT = "//p";
    //详情页中抽取主题的关键词
    public static final String DETAIL_SUBJECT_REGEX_START = "标题:";
    public static final String DETAIL_SUBJECT_REGEX_END = "发信站:";
    public static final String DETAIL_CONTENT_START_REGEX = "发信站: .*?站内";
    //底部过滤
    public static final List<String> DETAIL_CONTENT_END_SPLIT_LIST = new ArrayList<String>() {{
        add("※ 修改.*?\\[.*?\\]");
        add("※ 来源.*?\\[.*?\\]");
    }};
    //详情页中抽取发帖时间的关键词  Tue Mar 13 00:12:46 2018
    public static final String DETAIL_POSTDATE_REGEX =
            "\\([A-Za-z]{3}\\s+[A-Za-z]{3}.*\\d+\\s+\\d+:\\d+:\\d+\\s+\\d+\\)";
    public static final String DETAIL_CONTENT_ROW_TAG = "<br>&nbsp;&nbsp;<br>";
    public static final String SPECIAL_ENGLISH_DATE_FORMAT = "EEE MMM  d HH:mm:ss yyyy";
}
