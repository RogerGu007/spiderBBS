package com.school.magic.constants;

public enum SiteEnum {
    //按区号 + 编号
    TJ_BBS(211, "同济大学BBS"),  //同济大学
    FUDAN_BBS(212, "复旦大学BBS"),  //复旦大学
    SJTU_BBS(213, "上海交通大学BBS"),  //上海交通大学
    ECNU_BBS(214, "华东师范大学BBS"),  //华东师范大学
//    SUFE_BBS(215, "上海财经大学BBS"),  //上海财经大学
    ECUST_BBS(216, "华东理工大学BBS"),  //华东理工大学

    NJU_BBS(251, "南京大学BBS"),  //南京大学
//    SEU_BBS(252, "东南大学BBS"),  //东南大学

    TSING_BBS(101, "清华大学BBS"),  //清华大学
    PEKING_BBS(102, "北京大学BBS"),  //北京大学
//    RUC_BBS(103, "中国人民大学BBS"),  //中国人民大学

    WHU_BBS(271, "武汉大学BBS"),  //武汉大学
//    HUST_BBS(272, "华中科技大学BBS"), //华中科技大学

//    USTC_BBS(5511, "中科大BBS"),  //中科大

    ZJU_BBS(5711, "浙江大学BBS");  //浙江大学

    private Integer siteCode;
    private String nickName;

    private SiteEnum(int siteCode, String nickName) {
        this.siteCode = siteCode;
        this.nickName = nickName;
    }

    public Integer getSiteCode() {
        return this.siteCode;
    }

    public String getNickName() {
        return nickName;
    }
}
