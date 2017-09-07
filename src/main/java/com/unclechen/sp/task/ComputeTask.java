package com.unclechen.sp.task;

import com.unclechen.sp.Constant;
import com.unclechen.sp.domain.News;
import com.unclechen.sp.util.DateUtil;
import com.unclechen.sp.util.KeyWordComputer;
import com.unclechen.sp.util.RedisManager;
import com.unclechen.sp.util.StringUtil;
import com.unclechen.sp.webmagic.QQNewsPageProcessor;
import org.ansj.app.keyword.Keyword;
import org.ansj.domain.Term;
import org.ansj.recognition.NatureRecognition;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.util.FilterModifWord;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bjlixing on 2016/7/20.
 */
@Component
public class ComputeTask {

    // 抓取腾讯新闻，按照类别和时间爬取 每天下午两点执行
    @Scheduled(cron="0 20 * * * ? ")
    public void grabNewsByType(){
        Spider spider = new Spider(new QQNewsPageProcessor());
        for(String type: Constant.QQnewsTypeArray) {
            System.out.println("类别：" + type + "开始");
            String datetime = DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT);
            int count = addProcess(spider, 1, datetime, type);
            for (int i = 2; i <= count; i++) {
                addProcess(spider, i, datetime, type);
            }
            spider.thread(50).run();
            System.out.println("类别：" + type + "结束---");
        }
    }

    // 第一次调用时获取页数
    private static int addProcess(Spider spider,int page,String datetime,String type) {
        try{
            System.out.println("page:"+page+"--datetime:"+datetime+"--type:"+type);
            String url = "http://roll."+type+".qq.com/interface/roll.php?0.510182286211301&cata=&site="+type+"&date="+datetime+"&page="+page+"&mode=1&of=json";
            System.out.println("url:" + url);
            GetMethod get = new GetMethod(url);
            get.addRequestHeader("Cookie", "pac_uid=1_498368880; tvfe_boss_uuid=e60e3a19d86cf4c4; RK=8PHrw3RDb6; main_login=qq; pt2gguin=o0498368880; ptcz=6bbf645460f53f807ae613549eda5e1f8cd0c84f2d1145c26b02bab6094cf775; pgv_info=ssid=s6590366085; ts_last=sports.qq.com/a/20160601/050202.htm; ts_refer=roll.sports.qq.com/index.htm; pgv_pvid=5446137175; o_cookie=498368880; ts_uid=608235948; ptag=roll_sports_qq_com|; roll_mod=1");
            get.addRequestHeader("Host", "roll." + type + ".qq.com");
            get.addRequestHeader("Referer", "http://roll." + type + ".qq.com/index.htm?site=" + type + "&mod=1&date=" + datetime);
            HttpClient httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(6000);//设置请求超时时间6秒
            httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000); //设置读取超时时间
            httpClient.executeMethod(get);
            String content = get.getResponseBodyAsString();
            String regex = "href=\\\\\"(.*?)\\\\\"";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(content);
            while(matcher.find()){
                String tempUrl = matcher.group(1);
                tempUrl = tempUrl.replaceAll("\\\\/", "/");
                tempUrl = tempUrl + "?"+type;
                spider.addUrl(tempUrl);
            }
            String regex_count = "count\":(.*?),";
            Pattern pattern1 = Pattern.compile(regex_count);
            Matcher matcher1 = pattern1.matcher(content);
            String count = "1";
            if(matcher1.find()){
                count = matcher1.group(1);
            }
            return Integer.parseInt(count);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    // 计算新闻在某类文章中出现的概率 每周下午三点执行
    @Scheduled(cron="0 30 * * * ? ")
    public void computeNewsProbablilityForType(){
        // 删除之前缓存
        RedisManager.clean("keyword:");
        // 先处理没有关键词的文章，通过算法获取新文章关键词
        long counter = 0;
        double count = Constant.FACADE.newsDao.getUnhandledNewsCount();
        int pagesize = 5000;
        int page = (int)Math.ceil(count / pagesize);
        for(int i=0;i<page;i++){
            int start = i*pagesize;
            List<News> list = Constant.FACADE.newsDao.getUnhandledNews(null,null,start,pagesize);
            //加入过滤词性词性
            FilterModifWord.insertStopNatures("r") ;
            FilterModifWord.insertStopNatures("v") ;
            FilterModifWord.insertStopNatures("c") ;
            FilterModifWord.insertStopNatures("u") ;
            FilterModifWord.insertStopNatures("d") ;
            for(News news:list){
                String content = news.getContent();
                content = content.replaceAll("\n","").replaceAll(" ","").replaceAll(" ","").replaceAll("    ","");
                List<Term> parse = NlpAnalysis.parse(content) ;
                new NatureRecognition(parse).recognition() ;
                KeyWordComputer kwc = new KeyWordComputer(10);
                List<Keyword> result = kwc.computeAsrticleTfidf(FilterModifWord.modifResult(parse),content.length(),news.getTitle().length());
                String resultStr = "";
                for(Keyword key:result){
                    resultStr = resultStr + "," + key.getName();
                }
                if(!StringUtil.isBlank(resultStr)){
                    resultStr = resultStr + ",";
                }
                Constant.FACADE.newsDao.updateNewsByAutoid(news.getAutoid(),resultStr);
            }
        }


        // 计算所有概率
        double total_count = Constant.FACADE.newsDao.getNewsCount();
        int total_page = (int)Math.ceil(total_count / pagesize);
        for(int i=0;i<total_page;i++){
            int start = i*pagesize;
            List<News> list = Constant.FACADE.newsDao.getNews(null, null, start, pagesize);
            for(News news:list){
                String keywords = news.getKeywords();
                String type = news.getType();
                String[] keyArray = keywords.split(",");
                for(String key:keyArray){
                    counter++;
                    RedisManager.hincrby(Constant.TYPE_KEYWORD_PRE + ":" + type, key, 1,RedisManager.EXPIRE_DAY);
                    RedisManager.hincrby(Constant.ONLY_KEYWORD_PRE, key, 1,RedisManager.EXPIRE_DAY);
                    // 类型汇总量
                    RedisManager.hincrby(Constant.SUMMARY_KEYWORD_PRE, type, 1,RedisManager.EXPIRE_DAY);
                }
            }
        }
        // 整体汇总量
        RedisManager.hset(Constant.SUMMARY_KEYWORD_PRE,"total",(""+counter).getBytes(),RedisManager.EXPIRE_DAY);
    }


}
