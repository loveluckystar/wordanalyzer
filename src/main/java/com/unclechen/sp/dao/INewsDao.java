package com.unclechen.sp.dao;

import com.unclechen.sp.domain.News;

import java.util.List;

/**
 * Created by bjlixing on 2016/7/19.
 */

public interface INewsDao {
    public List<News> getNews(String type,String[] keywords,int start,int size);

    public int getNewsCount();

    public void putNews(String type,String title,String content,String url);

    public int getUnhandledNewsCount();

    public List<News> getUnhandledNews(String type,String[] keywords,int start,int size);

    public void updateNewsByAutoid(int autoid,String keywords);

}
