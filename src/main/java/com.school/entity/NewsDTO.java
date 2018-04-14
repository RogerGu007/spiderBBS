package com.school.entity;

import com.school.spiderEnums.NewsTypeEnum;

import java.util.Date;

public class NewsDTO extends BaseDTO {
    private String subject;

    private int newsType;
    private Integer newsSubType;
    private Boolean isHot;
    private Integer locationCode;

    private Integer commentCount;
    private Integer gallaryImageCount;
    private Date postDate;
    private String linkUrl;

    private String publisherId;

    public static NewsDTO generateNews(String title,
                NewsTypeEnum newsType,
                Date postDate) {
        NewsDTO news = new NewsDTO();
        news.subject = title;
        news.newsType = newsType.getNewsTypeCode();
        news.postDate = postDate;
        return news;
    }

    public String getmSubject() {
        return subject;
    }

    public void setSubject(String mSubject) {
        this.subject = mSubject;
    }

    public Integer getNewsType() {
        return newsType;
    }

    public void setNewsType(Integer newsType) {
        this.newsType = newsType;
    }

    public Integer getNewsSubType() {
        return newsSubType;
    }

    public void setNewsSubType(Integer newsJobSubType) {
        this.newsSubType = newsJobSubType;
    }

    public Boolean getIsHot() {
        return isHot;
    }

    public void setIsHot(Boolean isHot) {
        this.isHot = isHot;
    }

    public int getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(int locationCode) {
        this.locationCode = locationCode;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getGallaryImageCount() {
        return gallaryImageCount;
    }

    public void setGallaryImageCount(Integer gallaryImageCount) {
        this.gallaryImageCount = gallaryImageCount;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }
}
