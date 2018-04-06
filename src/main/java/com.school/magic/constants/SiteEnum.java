package com.school.magic.constants;

public enum SiteEnum {
    //按区号 + 编号
    TJ_BBS(211),  //同济大学
    FUDAN_BBS(212),  //复旦大学
    SJTU_BBS(213),  //上海交通大学
    ECNU_BBS(214),  //华东师范大学
    SUFE_BBS(215),  //上海财经大学
    ECUST_BBS(216),  //华东理工大学

    NJU_BBS(251),  //南京大学
    SEU_BBS(252),  //东南大学

    TSING_BBS(101),  //清华大学
    PEKING_BBS(102),  //北京大学
    RUC_BBS(103),  //中国人民大学

    WHU_BBS(271),  //武汉大学
    HUST_BBS(272), //华中科技大学

    USTC_BBS(5511);  //中科大


    private Integer siteCode;

    private SiteEnum(int code) {
        this.siteCode = code;
    }

    public Integer getSiteCode() {
        return this.siteCode;
    }
}
