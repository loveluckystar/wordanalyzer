package com.unclechen.sp.dao.ibatis;

import com.unclechen.sp.dao.INewsDao;
import com.unclechen.sp.domain.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jmx.export.assembler.InterfaceBasedMBeanInfoAssembler;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bjlixing on 2016/7/19.
 */
@Repository
public class NewsDao implements INewsDao {

    @Autowired
    @Qualifier("iBatisTemplate")
    private SqlMapClientTemplate iBatisTemplate;
    @Override
    public List<News> getNews(String type, String[] keywords,int start,int size) {
        Map paramMap = new HashMap();
        paramMap.put("start",start);
        paramMap.put("size",size);
//        paramMap.put("type",type);
//        paramMap.put("type",);
        return (List<News>) iBatisTemplate.queryForList("news.getNews", paramMap);
    }

    @Override
    public int getNewsCount() {
        return (Integer) iBatisTemplate.queryForObject("news.getNewsCount");
    }

    @Override
    public void putNews(String type, String title, String content, String url) {
        Map<String,String> map = new HashMap<String, String>();
        map.put("type",type);
        map.put("title",title);
        map.put("content",content);
        map.put("url",url);
        iBatisTemplate.insert("news.putNews",map);
    }

    @Override
    public int getUnhandledNewsCount() {
        Map<String,String> map = new HashMap<String, String>();
        map.put("keywordsisnull","1");
        return (Integer) iBatisTemplate.queryForObject("news.getNewsCount",map);
    }

    @Override
    public List<News> getUnhandledNews(String type, String[] keywords, int start, int size) {
        Map map = new HashMap();
        map.put("keywordsisnull","1");
        map.put("start",start);
        map.put("size",size);
        return (List<News>) iBatisTemplate.queryForList("news.getNews", map);
    }

    @Override
    public void updateNewsByAutoid(int autoid,String keywords) {
        Map map = new HashMap();
        map.put("autoid",autoid);
        map.put("keywords",keywords);
        iBatisTemplate.update("news.updatenewsByautoid",map);
    }
}
