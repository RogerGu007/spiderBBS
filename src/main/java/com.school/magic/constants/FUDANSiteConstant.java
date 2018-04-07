package com.school.magic.constants;

import java.util.ArrayList;
import java.util.List;

public class FUDANSiteConstant {
    //可以不用登陆，游客模式
//    public static final String LOGIN_URL = "http://www.newsmth.net";
    //可以不用登陆，该版面下没有交友。 但是只能看10页，100条求职信息
    public static final String JOB_URL = "http://fdubbs.com/forum.php?ForumID=21";
    //该版面下的求职需要用复旦的邮箱注册登录，所以求职不能在这个版面   求职和交友用两个SiteHandler来做
//    public static final String FRIEND_URL = "https://bbs.fudan.edu.cn/bbs/tdoc?bid=70";

    public static final String FUDAN_BBS_JOB_DOMAIN = "fdubbs.com";

    //不需要再过滤了，根据class就可以过滤
    public static final String FORMNODE = "//table[3]/tbody/tr";
    //拼接上域名即可  for example: thread.php?ThreadID=38200
    public static final String FORMITEMLINK = "//a[@class=\"PageNum\"]/@href";
    //发帖时间：2018-4-6 需要格式化为 2018-04-06 00:00:00
    public static final String FORMITEMMODIFYTIME= "//td[4]";
    //只有页码，替换掉首页的字符串?ForumID=21即可  for example: ?PageIndex=2&ForumID=21
    public static final String FORMITEMNEXTPAGE = "//a[@class=\"PageNum\"]/@href";
    //主题
    public static final String FORMITEMTITEL = "//td[5]/a/span/text()";
    //用于判断是否有详情页
    public static final String FORMITEMCHILD = "//a[@class=\"PageNum\"]/@href";

    //一行一个p标签，需要过滤掉为空的行
    public static final String DETAIL_CONTENT = "//tbody/tr[4]/td[1]";
    //详情页标题
    public static final String DETAIL_SUBJECT_TAG= "//tbody/tr[1]/td/span/text()";
    //详情页中的发帖时间
    public static final String DETAIL_POSTDATE_TAG = "//tbody/tr[3]/td/text()";

    //news的抽取顺序
    public static final ExtractSequenceType EXTRACT_NEWS_SEQUENCE = ExtractSequenceType.AFTER_INDEX;
    //newsDetail的抽取顺序
    public static final ExtractSequenceType EXTRACT_NEWSDETAIL_SEQUENCE = ExtractSequenceType.AFTER_NEWS;
}
