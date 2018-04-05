package com.school.spiderEnums;


public enum NewsSubTypeEnum {
    SUB_FULLTIME(1),
    SUB_PARTTIME(2),
    SUB_INTERN(3);

    private Integer newsSubType;

    private NewsSubTypeEnum(int type) {
        this.newsSubType = type;
    }

    public Integer getNewsSubTypeCode() {
        return this.newsSubType;
    }

    public static NewsSubTypeEnum valueToNewsSubTyp(int subType) {
        for (NewsSubTypeEnum newsEnum : NewsSubTypeEnum.values()) {
            if (newsEnum.newsSubType == subType) {
                return newsEnum;
            }
        }
        throw new IllegalArgumentException("无效的value值: " + subType + "!");
    }
}
