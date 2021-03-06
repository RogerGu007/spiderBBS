package com.school.Gson;


import org.apache.http.util.TextUtils;

import java.util.Date;

public class PostMsgGson {
	private String ID;
	private String content;
	private Integer NewsType;
	private Integer NewsSubType;
	private Integer LocationCode;
	private Date 	 postDate;
	private String		detailContent;
	private String		sourceArticleUrl;
	private String 	source;

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getContent() {
		return content;
	}

	public Integer getNewsType() {
		return NewsType;
	}

	public Integer getLocationCode() {
		return LocationCode;
	}

	public Integer getNewsSubType() {
		return NewsSubType;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setLocationCode(Integer locationCode) {
		LocationCode = locationCode;
	}

	public void setNewsSubType(Integer newsSubType) {
		NewsSubType = newsSubType;
	}

	public void setNewsType(Integer newsType) {
		NewsType = newsType;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}