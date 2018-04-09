package com.school.magic.constants;

public class SJTUSiteConstant {
    //可以不用登陆，游客模式
//    public static final String LOGIN_URL = "http://www.newsmth.net";
    public static final String JOB_URL = "https://bbs.sjtu.edu.cn/bbstdoc,board,JobInfo.html";
    public static final String FRIEND_URL = "https://bbs.sjtu.edu.cn/bbstdoc,board,LoveBridge.html";

    public static final String SJTU_BBS_JOB_DOMAIN = "bbs.sjtu.edu.cn";

    //表单入口，需要去掉置底信息，标题可能有 全职、实习等信息来区分子分类
    public static final String FORMNODE = "//table/tbody/tr";
    //拼接上域名即可  for example: bbscon,board,JobInfo,file,M.1522941858.A.html
    public static final String FORMITEMLINK = "//td[5]/a";
    //发帖时间：Apr 5 19:21  需要格式化为 2018-04-06 00:00:00
    public static final String FORMITEMMODIFYTIME= "//td[4]/text()";
    //只有上一页，拼接上域名即可。需要构造出后N页，数字递减即可  bbsdoc,board,JobInfo,page,8422.html
    public static final String FORMITEMNEXTPAGE = "//nobr/center/a[2]";
    //主题
    public static final String FORMITEMTITEL = "//td[5]/a/text()";
    //过滤掉版本的公告帖
    public static final String JOB_FORUM_MODERATOR = "careercenter";
    //用于判断是否有详情页
    public static final String FORMITEMCHILD = "//td[5]";

    //详情页是一个text，关键词：
    //标  题: 美团点评2018春季校招—大牛学长现场帮你内推
    //发信站: 饮水思源 (2018年04月06日11:43:54 星期五)
    public static final String DETAIL_CONTENT = "//tbody/tr/td/pre";
    public static final String SUB_DETAIL_CONTENT = "//p";
    //详情页中抽取主题的关键词
    public static final String DETAIL_SUBJECT_REGEX_START = "标题:";
    public static final String DETAIL_SUBJECT_REGEX_END = "发信站:";
    //详情页中抽取发帖时间的关键词  Tue Mar 13 00:12:46 2018
    public static final String DETAIL_POSTDATE_REGEX =
            "\\([A-Za-z]{3}\\s+[A-Za-z]{3}.*\\d+\\s+\\d+:\\d+:\\d+\\s+\\d+\\)";
    public static final String DETAIL_CONTENT_ROW_TAG = "&nbsp;&nbsp;";
}
