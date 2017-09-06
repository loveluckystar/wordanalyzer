package com.unclechen.sp.controller;

import com.alibaba.fastjson.JSONObject;
import com.unclechen.sp.Constant;
import com.unclechen.sp.domain.AdminUser;
import com.unclechen.sp.domain.News;
import com.unclechen.sp.redis.session.HttpServletRequestWrapper;
import com.unclechen.sp.util.ComputeUtil;
import com.unclechen.sp.util.RedisManager;
import com.unclechen.sp.util.RequestUtil;
import com.unclechen.sp.util.SimpleSummariser;
import com.unclechen.sp.util.StringUtil;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/compute")
public class ComputeController extends BaseController{
	private static Logger log = Logger.getLogger(ComputeController.class);
	public static String[] typeArray = {"auto","edu","ent","finance","games","house","sports","tech"};

	@RequestMapping("keywords.action")
	public void keywords(HttpServletRequest request, HttpServletResponse response) {
		long counter = 0;
		double count = Constant.FACADE.newsDao.getNewsCount();
		int pagesize = 10000;
		int page = (int)Math.ceil(count / pagesize);
		System.out.println("in---"+page);
		for(int i=0;i<page;i++){
			int start = i*pagesize;
			List<News> list = Constant.FACADE.newsDao.getNews(null,null,start,pagesize);
			System.out.println(list.size());
			for(News news:list){
				String keywords = news.getKeywords();
				String type = news.getType();
				String[] keyArray = keywords.split(",");
				log.info("keywords---"+keywords);
				for(String key:keyArray){
					log.info("key----"+key);
					counter++;
					RedisManager.hincrby(Constant.TYPE_KEYWORD_PRE+":"+type,key,1,RedisManager.EXPIRE_DAY);
					RedisManager.hincrby(Constant.ONLY_KEYWORD_PRE, key, 1,RedisManager.EXPIRE_DAY);
					// 类型汇总量
					RedisManager.hincrby(Constant.SUMMARY_KEYWORD_PRE, type, 1,RedisManager.EXPIRE_DAY);
				}
			}
		}
		// 整体汇总量
		RedisManager.hset(Constant.SUMMARY_KEYWORD_PRE,"total",""+counter);
		renderText(""+counter, response);
	}

	// 计算概率
	@RequestMapping("probablility.action")
	public void probablility(HttpServletRequest request, HttpServletResponse response) {
		log.info("begin");
		// 关键词总个数
		String count_summary = RedisManager.hgetString(Constant.SUMMARY_KEYWORD_PRE, "total");
		for(String type:typeArray){
			// 该类型下包含关键词个数
			String count_type = RedisManager.hgetString(Constant.SUMMARY_KEYWORD_PRE, type);
			Map<String,String> type_map = RedisManager.hgetAll(Constant.TYPE_KEYWORD_PRE+":"+type);
			for(String key:type_map.keySet()){
				// 每个词在该类型下次数
				String count_type_every = type_map.get(key);
				String count_only = RedisManager.hgetString(Constant.ONLY_KEYWORD_PRE, key);
				BigDecimal dec_count_summary = new BigDecimal(count_summary);
				BigDecimal dec_count_type = new BigDecimal(count_type);
				BigDecimal dec_count_type_every = new BigDecimal(count_type_every);
				BigDecimal dec_count_only = new BigDecimal(count_only);
				BigDecimal probablility = dec_count_type_every.divide(dec_count_type,10,BigDecimal.ROUND_HALF_UP).multiply(dec_count_type).divide(dec_count_summary,10,BigDecimal.ROUND_HALF_UP).divide(dec_count_only,10,BigDecimal.ROUND_HALF_UP).multiply(dec_count_summary);
				log.info("type--->"+type+"|keyword--->"+key+"|probablility--->"+probablility);
				RedisManager.hset(Constant.PROBABLILY_KEYWORD_PRE + ":" + type, key, "" + probablility);
			}
		}
		log.info("end");
	}

	// 计算新闻种类
	@RequestMapping("newsfortype.action")
	public String newsfortype(HttpServletRequest request,ModelMap map) {
		log.info("begin");
//		// 关键词总个数
//		String count_summary = RedisManager.hgetString(Constant.SUMMARY_KEYWORD_PRE, "total");
//		for(String type:typeArray){
//			// 该类型下包含关键词个数
//			String count_type = RedisManager.hgetString(Constant.SUMMARY_KEYWORD_PRE, type);
//			Map<String,String> type_map = RedisManager.hgetAll(Constant.TYPE_KEYWORD_PRE+":"+type);
//			for(String key:type_map.keySet()){
//				// 每个词在该类型下次数
//				String count_type_every = type_map.get(key);
//				String count_only = RedisManager.hgetString(Constant.ONLY_KEYWORD_PRE, key);
//				BigDecimal dec_count_summary = new BigDecimal(count_summary);
//				BigDecimal dec_count_type = new BigDecimal(count_type);
//				BigDecimal dec_count_type_every = new BigDecimal(count_type_every);
//				BigDecimal dec_count_only = new BigDecimal(count_only);
//				BigDecimal probablility = dec_count_type_every.divide(dec_count_type,10,BigDecimal.ROUND_HALF_UP).multiply(dec_count_type).divide(dec_count_summary,10,BigDecimal.ROUND_HALF_UP).divide(dec_count_only,10,BigDecimal.ROUND_HALF_UP).multiply(dec_count_summary);
//				log.info("type--->"+type+"|keyword--->"+key+"|probablility--->"+probablility);
//				RedisManager.hset(PROBABLILY_KEYWORD_PRE + ":" + type, key, "" + probablility);
//			}
//		}

		String title = request.getParameter("title");
		String content = request.getParameter("content");
		if(StringUtil.isBlank(title)||StringUtil.isBlank(content)){
			return "query_form";
		}
		log.info("title:"+title);
		log.info("content:"+content);
		String info=SimpleSummariser.summarise(title, content);
		map.addAttribute("resulttype", ComputeUtil.computeTypeForNews(title, content));
		map.addAttribute("title",title);
		map.addAttribute("content",content);
		log.info("summarise_info:"+info);
		map.addAttribute("summarise_info",info);
		log.info("end");
		return "query_form";
	}
}
