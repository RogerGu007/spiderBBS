package com.school.spiderConstants;

public enum LocationEnum {
    BEIJING(10),
    GUANGDONG(20),
    SHANGHAI(21),
    NANJING(25),
    WUHAN(27);

    private Integer zipCode;

    private LocationEnum(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public int getZipCode() {
        return this.zipCode;
    }
}
