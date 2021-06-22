package com.coccoc.news.repo.cache;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.coccoc.news.base.repository.database.BaseDao;
import com.coccoc.news.config.NewsConfig;
import com.coccoc.news.repo.model.News;

import java.util.List;

@Dao
public abstract class DaoNews extends BaseDao<News> {

    @Query("select * from item_news order by newsId desc limit :from, :limit")
    public abstract LiveData<List<News>> queryNews(int from, int limit);

    @Query("delete from item_news where newsId not in (select newsId from item_news order by newsId desc limit 0, :limit)")
    public abstract void cleanCache(int limit);

    @Query("delete from item_news")
    public abstract void clearCache();

    @Transaction
    public void insertNews(List<News> allNews) {
        if (allNews == null) return;

        for (News news : allNews) {
            insert(news);
        }
        cleanCache(NewsConfig.NEWS_PAGE_SIZE);
    }
}
