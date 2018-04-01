package com.school.magic.constants;

public class NJUSiteConstant {

    public static final String LOGIN_URL = "";
    public static final String JOB_URL  = "http://bbs.nju.edu.cn/bbsdoc?board=JobExpress";

//    public static final String FRIEND_URL = "https://bbs.tongji.net/forum-336-1.html";

    public static final String NJUBBSDOMAIN = "bbs.nju.edu.cn";

    public static final String FORMNODE = "//table[3]/tbody/tr";
    //需要拼接  for example: bbstcon?board=JobExpress&file=M.1426515664.A
    public static final String FORMITEMLINK = "//tr/td[6]/a/@href";
    //默认是本年度  for example: Mar 30 18:58
    public static final String FORMITEMMODIFYTIME= "//tr/td[5]/nobr/text()";
    //需要拼接  for example: bbstdoc?board=JobExpress&start=11878
    public static final String FORMITEMNEXTPAGE = "//a[4]/@href";
    public static final String FORMITEMTITEL = "//tr/td[6]/a/text()";
    //用于判断是否有详情页
    public static final String FORMITEMCHILD = "//tr/td[1]/text()";

    //详情页是一个整体的字符串，标题没有单独的标签，服用父页面的数据
//    public static final String DETAILSUBJECT = "//*[@id=\"thread_subject\"]/text()";
//    public static final String DETAILPOSTDATE= "//em[@id]/text()";
    public static final String DETAILCONTENTSANDCOMMENTS = "//*[@id=\"NET_1\"]/pre/text()[3]";
}
