package com.school.entity;

public class NewsDetail {

    private String mDetailContent;

    private String mSourceArticleUrl;

    public static NewsDetail generateNewsDetail(String detailContent, String sourceArticleUrl) {
        NewsDetail newsDetail = new NewsDetail();
        newsDetail.mSourceArticleUrl = detailContent;
        newsDetail.mSourceArticleUrl = sourceArticleUrl;
        return newsDetail;
    }
}
