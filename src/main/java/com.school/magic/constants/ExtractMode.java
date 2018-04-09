package com.school.magic.constants;

public enum ExtractMode {
    EXTRACT_HTML_ITEM(1), //从html标签中抽取信息
    EXTRACT_TEXT(2); //从文本中抽取信息

    private Integer mode;

    private ExtractMode(int mode) {
        this.mode = mode;
    }

    public Integer getMode() {
        return this.mode;
    }
}
