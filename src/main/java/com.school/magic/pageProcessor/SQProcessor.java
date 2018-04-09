package com.school.magic.pageProcessor;

import com.school.Service.NewsService;
import com.school.entity.NewsDTO;
import com.school.entity.NewsDetailDTO;
import com.school.magic.constants.ExtractMode;
import com.school.magic.siteHandler.SQSiteHandler;
import com.school.magic.storePipeline.NewsToDBPipeline;
import com.school.utils.ApplicationContextUtils;
import com.school.utils.GsonUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

import static com.school.magic.constants.Constant.*;

public class SQProcessor implements PageProcessor {
    private SQSiteHandler mSqSiteHandler;

    private NewsService newsService = ApplicationContextUtils.getBean(NewsService.class);

    private SQProcessor(SQSiteHandler sqSiteHandler) {
        this.mSqSiteHandler = sqSiteHandler;
    }

    @Override
    public void process(Page page) {
        if (mSqSiteHandler == null)
            return;

        mSqSiteHandler.setmPage(page);
        //Step1：登录页过滤
        if (mSqSiteHandler.isLoginPage()) {
            page.setSkip(true);
            return;
        }

        final String siteUrl = page.getUrl().toString();
        //重复的页面不再抓取
        NewsDetailDTO newsDetailDTO = newsService.getNewsDetailByUrl(siteUrl);
        if (newsDetailDTO != null) {
            page.setSkip(true);
            return;
        }

        //Step2: 获取一页的所有请求数据
        List<String> requestURLs = mSqSiteHandler.getRequests();
        if (requestURLs != null && requestURLs.size() > 0) {
            //form 页面，只需要找到具体的item就可以了
            page.addTargetRequests(requestURLs);
            page.setSkip(true);
            //return是个特殊逻辑，决定了news和newsDetails的抽取顺序。 根据sequence来判断，在首页里页尝试抽取一次数据
            return;
        }

        //提取news
        if (!extractSubjectFile(page))
            return;
        //提取news详情
        extractDetailField(page);
    }

    public static Spider getSpider(SQSiteHandler sqSiteHandler) {
        if (sqSiteHandler == null)
            return null;

        SQProcessor sqProcessor = new SQProcessor(sqSiteHandler);
        Spider spider = Spider.create(sqProcessor);

        Request loginRequest = sqSiteHandler.getLoginRequest();
        if (loginRequest != null)
            spider.addRequest(loginRequest).addUrl(sqSiteHandler.getNewsURL());
        else
            spider.addUrl(sqSiteHandler.getNewsURL());

        NewsToDBPipeline newsToDBPipeline = ApplicationContextUtils.getBean(NewsToDBPipeline.class);
        spider.addPipeline(newsToDBPipeline);

        return spider;
    }

    @Override
    public Site getSite() {
        if (mSqSiteHandler == null)
            return null;
        return mSqSiteHandler.getSite();
    }

    /**
     * 抽取标题的数据
     *
     * @param page
     */
    private boolean extractSubjectFile(Page page) {
        NewsDTO news = null;
        if (mSqSiteHandler.getExtractMode().equals(ExtractMode.EXTRACT_HTML_ITEM))
            news = mSqSiteHandler.extractNews(page);
        else if (mSqSiteHandler.getExtractMode().equals(ExtractMode.EXTRACT_TEXT))
            news = mSqSiteHandler.extractNewsFromText(page);

        if (news == null) {
            page.setSkip(true);
            return false;
        } else {
            page.putField(RESULT_SUBJECT_FIELD, GsonUtils.toGsonString(news));
        }
        return true;
    }

    /**
     * 抽取详情数据
     * @param page
     */
    private boolean extractDetailField(Page page) {
        NewsDetailDTO details = null;
        if (mSqSiteHandler.getExtractMode().equals(ExtractMode.EXTRACT_HTML_ITEM))
            details = mSqSiteHandler.extractNewsDetails(page);
        else if (mSqSiteHandler.getExtractMode().equals(ExtractMode.EXTRACT_TEXT))
            details = mSqSiteHandler.extractNewsDetailsFromText(page);

        if (details == null) {
            page.setSkip(true);
            return false;
        } else {
            page.putField(RESULT_DETAIL_FIELD, GsonUtils.toGsonString(details));
        }
        return true;
    }
}
