package com.school.Gson;

import com.sun.jersey.api.representation.Form;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class CascadeParam {
    private String Url;
    private Form form = new Form();
    private MultivaluedMapImpl queryParams = new MultivaluedMapImpl();

    public String getUrl() {
        return Url;
    }

    public CascadeParam setUrl(String url) {
        Url = url;
        return this;
    }

    public CascadeParam addQueryParams(String key, String value)
    {
        queryParams.add(key, value);
        return this;
    }

    public MultivaluedMapImpl getQueryParams() {
        return queryParams;
    }

    public CascadeParam addFormParams(String key, String value)
    {
        form.add(key, value);
        return this;
    }

    public Form getForm() {
        return form;
    }
}
