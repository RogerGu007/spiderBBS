package com.school.spiderEnums;


public enum NewsSubTypeEnum {
    SUB_FULLTIME(1),  //全职
    SUB_PARTTIME(2);  //兼职,实习
//    SUB_INTERN(3);

/*    //全职的子类型
    SUB_CAMPUS(4), //校园招聘
    SUB_UPGRADE(5);  //社会招聘*/

    private Integer newsSubType;

    private NewsSubTypeEnum(int type) {
        this.newsSubType = type;
    }

    public Integer getNewsSubTypeCode() {
        return this.newsSubType;
    }

    public static NewsSubTypeEnum valueToNewsSubType(int subType) {
        for (NewsSubTypeEnum newsEnum : NewsSubTypeEnum.values()) {
            if (newsEnum.newsSubType == subType) {
                return newsEnum;
            }
        }
        throw new IllegalArgumentException("无效的value值: " + subType + "!");
    }
}
