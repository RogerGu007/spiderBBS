package com.school.remote;

//import com.arronlong.httpclientutil.HttpClientUtil;
//import com.arronlong.httpclientutil.common.HttpConfig;
import com.google.gson.Gson;
import com.school.Constants.RetCode;
import com.school.Constants.RetMsg;
import com.school.Gson.PostMsgGson;
import com.school.Gson.RetIDResultGson;
import com.school.Gson.RetResultGson;
import com.school.utils.GsonUtils;
import com.school.utils.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Component
public class NewsRemoteCaller {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final String HOST_URL = PropertyUtil.getProperty("REMOTE_PATH");

	//POST
	private static final String  POST_NEWS = "/postmsg/%s/postmessage";

	//GET
	private static final String GET_HASNEWSDETAIL = "/news/hasnewsdetail";
	private static final String GET_USERID = "/user/getuserid";

	public RetResultGson postNews(String userID, PostMsgGson postMsgGson) {
		String[] filePaths = {};//待上传的文件路径

		String url = getHostUrl(POST_NEWS);
		url = String.format(url, userID);

		Map<String, String> map = new HashMap<String, String>();
		map.put("dto", GsonUtils.toGsonString(postMsgGson));
		logger.info(url);
		RetResultGson retResultGson = new RetResultGson(RetCode.RET_CODE_OK, RetMsg.RET_MSG_OK);
		try {
			String response = HttpCaller.post(url, map);
			retResultGson = GsonUtils.fromGsonString(response, RetResultGson.class);
		}
		catch (Exception ex)
		{
			retResultGson.setResult(RetCode.RET_CODE_SYSTEMERROR, RetMsg.RET_MSG_SYSTEMERROR);
			logger.error("fail to invoke postNews");
		}
		return retResultGson;
	}

	public RetIDResultGson getHasNewsDetail(String linkUrl)
	{
		RetIDResultGson retResultGson = new RetIDResultGson(RetCode.RET_CODE_OK, RetMsg.RET_MSG_OK);
		try {
			String url = getHostUrl(GET_HASNEWSDETAIL);
			url = String.format("%s?linkurl=%s", url, URLEncoder.encode(linkUrl, "utf-8"));
			logger.info(url);
			String response = HttpCaller.get(url, null);
			retResultGson = GsonUtils.fromGsonString(response, RetIDResultGson.class);
		}
		catch (Exception ex)
		{
			retResultGson.setResult(RetCode.RET_CODE_SYSTEMERROR, RetMsg.RET_MSG_SYSTEMERROR);
			logger.error("fail to invoke getHasNewsDetail");
		}
		return retResultGson;
	}

	public RetIDResultGson getUserID(String nickName)
	{
		RetIDResultGson retResultGson = new RetIDResultGson(RetCode.RET_CODE_OK, RetMsg.RET_MSG_OK);
		try {
			String url = getHostUrl(GET_USERID);
			url = String.format("%s?nickname=%s", url, URLEncoder.encode(nickName, "utf-8"));
			logger.info(url);
			String response = HttpCaller.get(url, null);
			retResultGson = GsonUtils.fromGsonString(response, RetIDResultGson.class);
		}
		catch (Exception ex)
		{
			retResultGson.setResult(RetCode.RET_CODE_SYSTEMERROR, RetMsg.RET_MSG_SYSTEMERROR);
			logger.error("fail to invoke getUserID");
		}

		return retResultGson;
	}

	private String getHostUrl(String uri) {
		return HOST_URL + uri;
	}
}
