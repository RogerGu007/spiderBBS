package com.school.magic.constants;

public class WHUSiteConstant {

    public static final String JOB_URL  = "http://bbs.whu.edu.cn/wForum/board.php?name=Job";

    public static final String FRIEND_URL = "http://bbs.whu.edu.cn/wForum/board.php?name=PieFriends";

    public static final String LOGIN_URL = "http://bbs.whu.edu.cn/wForum/logon.php";

    public static final String WHU_BBS_DOMAIN = "bbs.whu.edu.cn";
    //列表页是脚本形式的
    public static final String FORMNODE = "//tbody/script";
    public static final String FORM_ITEM_REGEX = "origin = new Post(.*)";
    public static final String JOB_FORM_ITEM_LINK_TEMPLATE =
            "http://bbs.whu.edu.cn/wForum/disparticle.php?boardName=Job&ID=PLACEHOLDER_ID";
    public static final String FRIEND_FORM_ITEM_LINK_TEMPLATE =
            "http://bbs.whu.edu.cn/wForum/disparticle.php?boardName=PieFriends&ID=PLACEHOLDER_ID";
    public static final String FORM_ITEM_NEXT_PAGE = "//tbody/tr/td/div/a";
    public static final String SUB_DETAIL_CONTENT = "//";
//    public static final String DETAIL_CONTENT = "//table[5]/tbody/tr/td[2]/table[2]/tbody/tr/td";
    public static final String DETAIL_CONTENT = "//tbody/tr/td[2]/table[2]/tbody/tr/td";
    //详情页中抽取主题的关键词
    public static final String DETAIL_SUBJECT_REGEX = "标.*题:";
    public static final String DETAIL_POSTDATE_REGEX =
            "\\([A-Za-z]{3}\\s+[A-Za-z]{3}.*\\d+\\s+\\d+:\\d+:\\d+\\s+\\d+\\)";
    public static final String DETAIL_CONTENT_START_REGEX = "发信站: .*站内";
    public static final String DETAIL_CONTENT_END_REGEX = "※ 来源:.*";
}
