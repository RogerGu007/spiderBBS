package com.school.magic.constants;

public enum SiteEnum {
    TJ_BBS(1),
    NJU_BBS(2);


    private Integer siteCode;

    private SiteEnum(int code) {
        this.siteCode = code;
    }

    public Integer getSiteCode() {
        return this.siteCode;
    }
}
