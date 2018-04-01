package com.school.entity;

import com.school.spiderConstants.NewsEnum;
import com.school.spiderConstants.NewsJobSubEnum;

import java.util.Date;

public class News extends BaseDTO {
    private String mSubject;

    private int mNewsType;
    private int mNewsJobSubType;
    private Boolean mIsHot;
    private String mOurArticleUrl;

    private Integer mCommentCount;
    private Integer mGallaryImageCount;
    private Date mPostDate;

    public static News generateNews(String title,
                NewsEnum newsType,
                Date postDate) {
        News news = new News();
        news.mSubject = title;
        news.mNewsType = newsType.getSiteCode();
        news.mPostDate = postDate;
        return news;
    }

    public String getmSubject() {
        return mSubject;
    }

    public void setmSubject(String mSubject) {
        this.mSubject = mSubject;
    }

    public int getmNewsType() {
        return mNewsType;
    }

    public void setmNewsType(int mNewsType) {
        this.mNewsType = mNewsType;
    }

    public int getmNewsJobSubType() {
        return mNewsJobSubType;
    }

    public void setmNewsJobSubType(int mNewsJobSubType) {
        this.mNewsJobSubType = mNewsJobSubType;
    }

    public Boolean getmIsHot() {
        return mIsHot;
    }

    public void setmIsHot(Boolean mIsHot) {
        this.mIsHot = mIsHot;
    }

    public String getmOurArticleUrl() {
        return mOurArticleUrl;
    }

    public void setmOurArticleUrl(String mOurArticleUrl) {
        this.mOurArticleUrl = mOurArticleUrl;
    }

    public Integer getmCommentCount() {
        return mCommentCount;
    }

    public void setmCommentCount(Integer mCommentCount) {
        this.mCommentCount = mCommentCount;
    }

    public Integer getmGallaryImageCount() {
        return mGallaryImageCount;
    }

    public void setmGallaryImageCount(Integer mGallaryImageCount) {
        this.mGallaryImageCount = mGallaryImageCount;
    }

    public Date getmPostDate() {
        return mPostDate;
    }

    public void setmPostDate(Date mPostDate) {
        this.mPostDate = mPostDate;
    }
}
