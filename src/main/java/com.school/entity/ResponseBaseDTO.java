package com.school.entity;

import com.school.utils.GsonUtils;

public class ResponseBaseDTO {
    private String retCode;
    private String retMsg;

    public static String render(String retCode, String retMsg) {
        ResponseBaseDTO response = new ResponseBaseDTO();
        response.retCode = retCode;
        response.retMsg = retMsg;
        return GsonUtils.toGsonString(response);
    }
}
