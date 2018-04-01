package com.school.magic.constants;

public enum SiteEnum {
    TJ_BBS(TJSiteConstant.TJ_BBS_CODE);

    private Integer siteCode;

    private SiteEnum(int code) {
        this.siteCode = code;
    }

    public Integer getSiteCode() {
        return this.siteCode;
    }
}
