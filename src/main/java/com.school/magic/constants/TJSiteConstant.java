package com.school.magic.constants;

import com.school.magic.siteHandler.TJSiteHandler;

import java.util.ArrayList;
import java.util.List;

public class TJSiteConstant {

//    public static final Integer TJ_BBS_CODE = 1;

    public static final String LOGIN_URL = "https://bbs.tongji.net/member.php?mod=logging&action=login&" +
            "loginsubmit=yes&loginhash=LvSg0&inajax=1";
    public static final String JOB_URL  = "https://bbs.tongji.net/forum-111-1.html";

    public static final String FRIEND_URL = "https://bbs.tongji.net/forum-336-1.html";

    public static final String TJBBSDOMAIN = "bbs.tongji.net";
    public static final String FORMNODE = "//tbody[@id]/tr";
    public static final String FORMITEMLINK = "//a[@class=\"s xst\"]";
    public static final String FORMITEMMODIFYTIME= "//em/a[@href]/text()";
    public static final String FORMITEMNEXTPAGE = "//span[@id=\"fd_page_bottom\"]";
    public static final String FORMITEMTITEL = "//tr/th/a[3]/text()";

    public static final String DETAILSUBJECT = "//*[@id=\"thread_subject\"]/text()";
    public static final String DETAILPOSTDATE= "//em[@id]/text()";
    public static final String DETAILCONTENTSANDCOMMENTS = "//*/td[@class=\"t_f\"]";
}
