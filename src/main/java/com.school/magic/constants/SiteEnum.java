package com.school.magic.constants;

import com.school.spiderEnums.LocationEnum;

public enum SiteEnum {
    //按区号 + 编号
    TJ_BBS(211, "同济之声", LocationEnum.SHANGHAI),  //同济大学
    FUDAN_BBS(212, "日月光华", LocationEnum.SHANGHAI),  //复旦大学
    SJTU_BBS(213, "饮水思源", LocationEnum.SHANGHAI),  //上海交通大学
    ECNU_BBS(214, "爱在华师大", LocationEnum.SHANGHAI),  //华东师范大学
//    SUFE_BBS(215, "经世济国", LocationEnum.SHANGHAI),  //上海财经大学
    ECUST_BBS(216, "华理家园", LocationEnum.SHANGHAI),  //华东理工大学

    NJU_BBS(251, "小百合", LocationEnum.NANJING),  //南京大学
//    SEU_BBS(252, "虎踞龙盘", LocationEnum.NANJING),  //东南大学

    TSING_BBS(101, "水木清华", LocationEnum.BEIJING),  //清华大学
    PEKING_BBS(102, "北大未名", LocationEnum.BEIJING),  //北京大学
//    RUC_BBS(103, "谈笑人生", LocationEnum.BEIJING),  //中国人民大学

    WHU_BBS(271, "珞珈山水", LocationEnum.WUHAN),  //武汉大学
//    HUST_BBS(272, "白云黄鹤", LocationEnum.WUHAN), //华中科技大学

//    USTC_BBS(5511, "瀚海星云", LocationEnum.HEFEI),  //中科大

    ZJU_BBS(5711, "梧桐树", LocationEnum.ZHEJIANG);  //浙江大学

    private Integer siteCode;
    private String nickName;
    private LocationEnum locationEnum;

    private SiteEnum(int siteCode, String nickName, LocationEnum locationEnum) {
        this.siteCode = siteCode;
        this.nickName = nickName;
        this.locationEnum = locationEnum;
    }

    public Integer getSiteCode() {
        return this.siteCode;
    }

    public String getNickName() {
        return nickName;
    }

    public LocationEnum getLocationEnum() {
        return locationEnum;
    }
}
