package com.school.magic.constants;

public class NJUSiteConstant {

    public static final String LOGIN_URL = "";

    public static final String JOB_URL  = "http://bbs.nju.edu.cn/bbstdoc?board=JobExpress";
    public static final String INDEX_JOB_URL_TAG = "board=JobExpress";

    public static final String FRIEND_URL = "http://bbs.nju.edu.cn/bbstdoc?board=WarAndPeace";

    public static final String NJUBBSDOMAIN = "bbs.nju.edu.cn";

    public static final String FORMNODE = "//tbody/tr";
    // for example: bbstcon?board=JobExpress&file=M.1426515664.A
    public static final String FORMITEMLINK = "//tr/td[5]/a";
    //默认是本年度  for example: Mar 30 18:58
    public static final String FORMITEMMODIFYTIME= "//td[4]/text()";
    //for example: bbstdoc?board=JobExpress&start=11878
    public static final String FORMITEMNEXTPAGE = "//nobr/center/a[4]";
    public static final String FORMITEMNEXTPAGE_BAK = "//nobr/center/a[2]";

    public static final String FORMITEMTITEL = "//tr/td[5]/a/text()";
    //用于判断是否有详情页
    public static final String FORMITEMCHILD = "//td/a[1]/text()";
    //过滤掉版本的公告帖
    public static final String JOB_FORUM_MODERATOR = "yika1985";
    //列表页的日期格式
    public static final String FORM_ITEM_DATE_FORMAT = ".*\\d+:\\d+";

    // [0]:发信人+信区 [1]:标题 [2] 发信站（可以提取时间），其他列过滤为空的内容组合起来就是详情内容
    public static final String DETAIL_CONTENT = "//tr[2]/td";
    public static final String SUB_DETAIL_CONTENT = "//textarea/html()";
    //详情页中抽取主题的关键词
    public static final String DETAIL_SUBJECT_REGEX = "标 *题.*";
    public static final String DETAIL_SUBJECT_REGEX_Start = "标 *题: *";
    public static final int DETAIL_CONTENT_START_ROWNUM = 3;
    public static final int DETAIL_CONTENT_END_ROWNUM = 2;
    //详情页中抽取发帖时间的关键词  Sun Apr  8 15:40:48 2018
    public static final String DETAIL_POSTDATE_REGEX =
            "\\([A-Za-z]{3}\\s+[A-Za-z]{3}.*\\d+\\s+\\d+:\\d+:\\d+\\s+\\d+\\)";
    public static final String SPECIAL_ENGLISH_DATE_FORMAT = "EEE MMM  d HH:mm:ss yyyy";
}
