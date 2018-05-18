package com.school.remote;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.google.gson.Gson;
import com.school.Constants.RetCode;
import com.school.Constants.RetMsg;
import com.school.Gson.PostMsgGson;
import com.school.Gson.RetIDResultGson;
import com.school.Gson.RetResultGson;
import com.school.utils.GsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Component
public class NewsRemoteCaller extends HttpCallerBase {
	private Logger logger = LoggerFactory.getLogger(getClass());

	//POST
	private static final String  POST_NEWS = "rest/postmsg/%s/postmessage";

	//GET
	private static final String GET_HASNEWSDETAIL = "rest/news/hasnewsdetail";
	private static final String GET_USERID = "rest/user/getuserid";

	public RetResultGson postNews(String userID, PostMsgGson postMsgGson)
	{
		String[] filePaths = {};//待上传的文件路径

		String url = getHostUrl(POST_NEWS);
		url = String.format(url, userID);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dto", GsonUtils.toGsonString(postMsgGson));
		HttpConfig config = HttpConfig.custom();
		config.url(url) //设定上传地址
				.files(filePaths)
				.inenc("UTF-8")
				.client(getClient())
				.map(map);//其他需要提交的参数
		logger.info(url);
		RetResultGson retResultGson = new RetResultGson(RetCode.RET_CODE_OK, RetMsg.RET_MSG_OK);
		try {
			String response = HttpClientUtil.upload(config);
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
			HttpConfig config = HttpConfig.custom();
			config.url(url)
					.client(getClient());
			logger.info(url);
			String response = HttpClientUtil.get(config);
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
			HttpConfig config = HttpConfig.custom();
			config.url(url)
					.client(getClient());

			String response = HttpClientUtil.get(config);
			retResultGson = GsonUtils.fromGsonString(response, RetIDResultGson.class);
		}
		catch (Exception ex)
		{
			retResultGson.setResult(RetCode.RET_CODE_SYSTEMERROR, RetMsg.RET_MSG_SYSTEMERROR);
			logger.error("fail to invoke getUserID");
		}

		return retResultGson;
	}

}
