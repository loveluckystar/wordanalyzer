package com.unclechen.sp.webmagic;

import com.unclechen.sp.Constant;
import com.unclechen.sp.util.StringUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bjlixing on 2016/5/16.
 */
public class QQNewsPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
    public void process(Page page) {
        try {
            String title = page.getHtml().xpath("//*[@class=\"qq_article\"]/div/h1/text()").toString();
            String content = page.getHtml().xpath("//*[@class=\"Cnt-Main-Article-QQ\"]").toString();
            String requestUrl = page.getRequest().getUrl();
            String[] array = requestUrl.split("\\?");
            String type = array[1];
            if(!StringUtil.isBlank(content)){
                content = StringUtil.Html2Text(content);
                Constant.FACADE.newsDao.putNews(type, title, content, requestUrl);
            }else{
                // 兼容科技等频道格式不一样的问题，进行二次抓取
                title = page.getHtml().xpath("//*[@id=\"C-Main-Article-QQ\"]/div/h1/text()").toString();
                content = page.getHtml().xpath("//*[@id=\"Cnt-Main-Article-QQ\"]").toString();
                if(!org.jsoup.helper.StringUtil.isBlank(content)){
                    content = StringUtil.Html2Text(content);
                    Constant.FACADE.newsDao.putNews(type, title, content, requestUrl);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Site getSite() {
        return site;
    }
}