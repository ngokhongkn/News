package com.coccoc.news.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.coccoc.news.base.arch.BaseViewModel;
import com.coccoc.news.base.repository.RepoQuery;
import com.coccoc.news.base.repository.api.common.Resource;
import com.coccoc.news.base.repository.paging.PageResult;
import com.coccoc.news.model.Reason;
import com.coccoc.news.model.Style;
import com.coccoc.news.repo.Repo;
import com.coccoc.news.repo.RepoImpl;
import com.coccoc.news.repo.model.Category;
import com.coccoc.news.repo.model.News;
import com.coccoc.news.repo.model.Source;

import java.util.List;

public class NewsViewModel extends BaseViewModel {
    private Repo repo = RepoImpl.getInstance();

    private MutableLiveData<Boolean> loadNews = new MutableLiveData<>();

    public void loadNews() {
        loadNews.setValue(true);
    }

    public void sendReportNews(News news, Reason reason) {
        repo.sendReportNews(news, reason);
    }

    @Deprecated
    public LiveData<PageResult<News>> queryPersonalizeNews(String sessionId) {
        return Transformations.map(loadNews, b -> repo.queryPersonalizeNews(sessionId));
    }

    public LiveData<PageResult<News>> queryNewsByCategory(Category category, String sessionId) {
        return Transformations.map(loadNews, b -> repo.queryNewsByCategory(category, sessionId));
    }

    public LiveData<Resource<List<Category>>> queryCategories() {
        return new RepoQuery<List<Category>>() {

            @Override
            protected LiveData<List<Category>> query() {
                return repo.queryCategories();
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<Category>>> querySubCategories() {
        return new RepoQuery<List<Category>>() {
            @Override
            protected LiveData<List<Category>> query() {
                return repo.querySubscribeCategories();
            }
        }.asLiveData();
    }

    public void updateCategories(List<Category> categories) {
        repo.updateCategories(categories);
    }

    public LiveData<Resource<List<Source>>> queryHidingSources() {
        return new RepoQuery<List<Source>>() {
            @Override
            protected LiveData<List<Source>> query() {
                return repo.queryBlockedSources();
            }
        }.asLiveData();
    }

    public void removeHidingSource(Source source) {
        repo.unblockSource(source);
    }

    public void addHidingSource(Source source) {
        repo.blockSource(source);
    }

    public void setCurrentStyle(Style style) {
        repo.setCurrentStyle(style);
    }

    public Style getCurrentStyle() {
        return repo.getCurrentStyle();
    }

    public LiveData<Style> queryCurrentStyle() {
        return repo.queryCurrentStyle();
    }

    public List<Style> queryAllStyles() {
        return repo.queryAllStyles();
    }

    public boolean isToolbarVisible() {
        return repo.isToolbarVisible();
    }

    public LiveData<Boolean> queryToolbarVisibility() {
        return repo.queryToolbarVisibility();
    }

    public void toggleToolbarVisibility() {
        repo.toggleToolbarVisibility();
    }
}
