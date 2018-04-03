package com.school.entity;

public class NewsDetail {

    private String detailContent;
    private String sourceArticleUrl;
    private String newsID;

    public String getDetailContent() {
        return detailContent;
    }

    public void setDetailContent(String detailContent) {
        this.detailContent = detailContent;
    }

    public String getSourceArticleUrl() {
        return sourceArticleUrl;
    }

    public void setSourceArticleUrl(String sourceArticleUrl) {
        this.sourceArticleUrl = sourceArticleUrl;
    }

    public String getNewsID() {
        return newsID;
    }

    public void setNewsID(String newsID) {
        this.newsID = newsID;
    }

    public static NewsDetail generateNewsDetail(String detailContent, String sourceArticleUrl) {
        NewsDetail newsDetail = new NewsDetail();
        newsDetail.detailContent = detailContent;
        newsDetail.sourceArticleUrl = sourceArticleUrl;
        return newsDetail;
    }
}
