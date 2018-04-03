package com.school.spiderConstants;

public enum LocationEnum {
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
