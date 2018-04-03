package com.school.magic.constants;

public enum ExtractSequenceType {

    INNER_INDEX(1), //在首页中抽取信息
    AFTER_INDEX(2), //首页之后抽取信息
    AFTER_NEWS(3),  //在抽取news之后抽取信息
    AFTER_NEW_DETAIL(4); //在抽取newsDetail之后抽取信息

    private Integer sequenceType;

    private ExtractSequenceType(int sequenceType) {
        this.sequenceType = sequenceType;
    }

    public Integer getSequenceType() {
        return this.sequenceType;
    }
}
