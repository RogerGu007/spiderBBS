package com.school.spiderEnums;

public enum NewsSubTypeEnum {
    SUB_FULLTIME(1),
    SUB_PARTTIME(2),
    SUB_INTERN(3);

    private Integer newsJobType;

    private NewsSubTypeEnum(int type) {
        this.newsJobType = type;
    }

    public Integer getNewsSubTypeCode() {
        return this.newsJobType;
    }
}
