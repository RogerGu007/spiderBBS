package com.school.remote;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.builder.HCB;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.school.Constants.RetCode;
import com.school.Constants.RetMsg;
import com.school.Gson.RetResultGson;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpCallerBase {
    private static final String HOST_URL = "http://localhost:8080/news/";

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static CloseableHttpClient client;
    private static Object lock = new Object();

    public CloseableHttpClient getClient() {
        if (this.client == null)
        {
            synchronized (this.lock)
            {
                if (this.client == null)
                {
                    init();
                }
            }
        }
        return client;
    }

    private void init()
    {
        try {
            //插件式配置生成HttpClient时所需参数（超时、连接池、ssl、重试）
            HCB hcb = HCB.custom()
                    .timeout(1000) //超时
                    .pool(20, 2) //启用连接池，每个路由最大创建10个链接，总连接数限制为100个
                    .retry(5)		//重试5次
                    ;
            client = hcb.build();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            logger.error("fail to setup httpclient: ex: " + ex);
        }
    }

    protected String getHostUrl(String relativeUrl)
    {
        return HOST_URL + relativeUrl;
    }
}
