package com.school.spiderEnums;

public enum LocationEnum {
    ALL(0),
    SHANGHAI(021),
    NANJING(025);

    private Integer zipCode;

    private LocationEnum(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public int getZipCode() {
        return this.zipCode;
    }
}
