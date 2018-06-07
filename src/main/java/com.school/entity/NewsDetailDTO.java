package com.school.entity;

import java.sql.Timestamp;

public class NewsDetailDTO extends BaseDTO {

    private String detailContent;
    private String sourceArticleUrl;
    private String newsID;
    private Timestamp postDate;	//post的时间

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

    public Timestamp getPostDate() {
        return postDate;
    }

    public void setPostDate(Timestamp postDate) {
        this.postDate = postDate;
    }

    public static NewsDetailDTO generateNewsDetail(String detailContent, String sourceArticleUrl) {
        NewsDetailDTO newsDetail = new NewsDetailDTO();
        newsDetail.detailContent = detailContent;
        newsDetail.sourceArticleUrl = sourceArticleUrl;
        return newsDetail;
    }
}
