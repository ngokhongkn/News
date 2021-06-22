package com.coccoc.news.repo;

import com.coccoc.news.base.repository.paging.PageParam;
import com.coccoc.news.config.NewsConfig;

public class NewsPage extends PageParam {

    public NewsPage(int page) {
        super(page, NewsConfig.NEWS_PAGE_SIZE);
    }

    @Override
    public PageParam next() {
        return new NewsPage(page + 1);
    }

    @Override
    public PageParam prev() {
        return new NewsPage(page - 1);
    }
}
