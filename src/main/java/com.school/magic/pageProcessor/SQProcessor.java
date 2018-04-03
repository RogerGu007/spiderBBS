package com.school.magic.pageProcessor;

import com.school.entity.News;
import com.school.entity.NewsDetail;
import com.school.magic.constants.ExtractSequenceType;
import com.school.magic.siteHandler.SQSiteHandler;
import com.school.magic.storePipeline.ConsolePipeline;
import com.school.magic.storePipeline.NewsToDBPipeline;
import com.school.utils.GsonUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

import static com.school.magic.constants.Constant.*;

public class SQProcessor implements PageProcessor {
    private SQSiteHandler mSqSiteHandler;

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

        //Step2: 获取第一页数据
        List<String> requestURLs = mSqSiteHandler.getRequests();
        if (requestURLs != null && requestURLs.size() > 0) {
            //form 页面，只需要找到具体的item就可以了
            page.addTargetRequests(requestURLs);
            page.setSkip(true);
            //return是个特殊逻辑，决定了news和newsDetails的抽取顺序。 根据sequence来判断，在首页里页尝试抽取一次数据
            extractNewsFromNode(page);
            return;
        }

        extractNewsFromDetail(page);
    }

    private void extractNewsFromNode(Page page) {
        if (mSqSiteHandler.getExtractNewsSequence().getSequenceType()
                .compareTo(ExtractSequenceType.INNER_INDEX.getSequenceType()) <= 0) {
            //抽取成功，重置skip，信息需要传到pipeline
            mSqSiteHandler.extractNodeNews(page);
            page.putField(IS_SUBJECT_LIST, true);
        }

        if (mSqSiteHandler.getExtractNewsDetailSequence().getSequenceType()
                    .compareTo(ExtractSequenceType.INNER_INDEX.getSequenceType()) <= 0) {
            extractDetailField(page);
            mSqSiteHandler.extractNodeDetails(page);
            page.putField(IS_DETAIL_LIST, true);
        }
    }

    private void extractNewsFromDetail(Page page) {
        //获取news
        if (mSqSiteHandler.getExtractNewsSequence().getSequenceType()
                .compareTo(ExtractSequenceType.AFTER_INDEX.getSequenceType()) >= 0)
            if (!extractSubjectFile(page))
                return;
        //获取news detail
        if (mSqSiteHandler.getExtractNewsDetailSequence().getSequenceType()
                .compareTo(ExtractSequenceType.AFTER_INDEX.getSequenceType()) >= 0)
            extractDetailField(page);
    }

    @Override
    public Site getSite() {
        if (mSqSiteHandler == null)
            return null;
        return mSqSiteHandler.getSite();
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

        spider.addPipeline(new NewsToDBPipeline());

        return spider;
    }

    /**
     * 抽取标题的数据
     *
     * @param page
     */
    private boolean extractSubjectFile(Page page) {
        News news = mSqSiteHandler.extractNews(page, null);
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
        NewsDetail details = mSqSiteHandler.extractNewsDetails(page, null);
        if (details == null) {
            page.setSkip(true);
            return false;
        } else {
            page.putField(RESULT_DETAIL_FIELD, GsonUtils.toGsonString(details));
        }
        return true;
    }
}
