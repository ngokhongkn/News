package com.coccoc.news.repo;

import androidx.lifecycle.LiveData;

import com.coccoc.news.base.repository.api.common.Resource;
import com.coccoc.news.base.repository.paging.PageResult;
import com.coccoc.news.repo.model.Category;
import com.coccoc.news.repo.model.News;
import com.coccoc.news.model.Reason;
import com.coccoc.news.repo.model.Source;
import com.coccoc.news.model.Style;

import java.util.List;

public interface Repo {
    void sendReportNews(News news, Reason reason);
    @Deprecated PageResult<News> queryPersonalizeNews(String sessionId);
    PageResult<News> queryNewsByCategory(Category category, String sessionId);
    LiveData<List<Category>> queryCategories();
    LiveData<List<Category>> querySubscribeCategories();
    void updateCategories(List<Category> categories);

    LiveData<List<Source>> queryBlockedSources();
    void unblockSource(Source source);
    void blockSource(Source source);

    void setCurrentStyle(Style style);
    Style getCurrentStyle();
    LiveData<Style> queryCurrentStyle();
    List<Style> queryAllStyles();

    boolean isToolbarVisible();
    LiveData<Boolean> queryToolbarVisibility();
    void toggleToolbarVisibility();
}
