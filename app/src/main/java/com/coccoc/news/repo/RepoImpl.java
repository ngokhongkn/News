package com.coccoc.news.repo;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.coccoc.news.NewsApp;
import com.coccoc.news.base.prefs.BasePrefs;
import com.coccoc.news.base.repository.PagingQuery;
import com.coccoc.news.base.repository.paging.PageData;
import com.coccoc.news.base.repository.paging.PageParam;
import com.coccoc.news.base.repository.paging.PageResult;
import com.coccoc.news.base.repository.paging.PagingFunc;
import com.coccoc.news.base.repository.paging.PagingSource;
import com.coccoc.news.base.views.recycler.RecyclerViewType;
import com.coccoc.news.config.NewsConfig;
import com.coccoc.news.factory.StyleFactory;
import com.coccoc.news.model.Reason;
import com.coccoc.news.model.Style;
import com.coccoc.news.repo.api.ApiClient;
import com.coccoc.news.repo.cache.CacheNewsDatabase;
import com.coccoc.news.repo.model.Category;
import com.coccoc.news.repo.model.News;
import com.coccoc.news.repo.model.Source;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RepoImpl implements Repo {
    private static final String KEY_NEWS_STYLE = "key_news_style";

    private static Repo sInstance = null;
    private ApiClient api = ApiClient.getInstance();
    private CacheNewsDatabase cache = CacheNewsDatabase.getDatabase(NewsApp.getInstance());
    private MutableLiveData<Style> currentStyle = new MutableLiveData<>();
    private Executor ioThread = Executors.newSingleThreadExecutor();

    private RepoImpl() {
        Style style = StyleFactory.create(BasePrefs.readInt(KEY_NEWS_STYLE, NewsConfig.getDefaultStyle()));
        currentStyle.setValue(style);
    }

    public static Repo getInstance() {
        if (sInstance == null) {
            sInstance = new RepoImpl();
        }
        return sInstance;
    }

    @Override
    public void sendReportNews(News news, Reason reason) {

    }

    @Override
    public PageResult<News> queryPersonalizeNews(String sessionId) {
        return new PagingQuery<>(new PagingSource<>(new NewsPage(0), new PagingFunc<News>() {
            @Override
            public LiveData<PageData<News>> execute(PageParam p) {
                if (p.getPage() <= 0) return queryFromCache(Category.getDefault(), sessionId);
                return queryFromApi(Category.getDefault(), sessionId);
            }
        })).getResult();
    }

    @Override
    public PageResult<News> queryNewsByCategory(Category category, String sessionId) {
        return new PagingQuery<>(new PagingSource<>(new NewsPage(0), new PagingFunc<News>() {
            @Override
            public LiveData<PageData<News>> execute(PageParam p) {
                if (p.getPage() <= 0) return queryFromCache(category, sessionId);
                return queryFromApi(category, sessionId);
            }
        })).getResult();
    }

    @Override
    public LiveData<List<Category>> queryCategories() {
        return Transformations.map(api.queryCategories(), new Function<List<Category>, List<Category>>() {
            @Override
            public List<Category> apply(List<Category> categories) {
                if (categories != null) {
                    ioThread.execute(() -> cache.daoCategory().insertCategories(categories));
                }
                return categories;
            }
        });
    }

    @Override
    public LiveData<List<Category>> querySubscribeCategories() {
        MediatorLiveData<List<Category>> result = new MediatorLiveData<>();
        LiveData<List<Category>> cacheSource = cache.daoCategory().querySubscribedCategories();
        LiveData<List<Category>> apiSource = queryCategories();

        result.addSource(cacheSource, categories -> {
            if (categories != null && !categories.isEmpty()) {
                if (!NewsConfig.isEnable(NewsConfig.VariationServiceFlag.CCNEWS_TEST_CATEGORY)) {
                    categories.clear();
                }
                categories.add(0, Category.getDefault());
                result.postValue(categories);
            } else {
                result.addSource(apiSource, apiCategories -> {
                    result.removeSource(apiSource);
                });
            }
        });

        return result;
    }


    @Override
    public void updateCategories(List<Category> categories) {
        ioThread.execute(() -> cache.daoCategory().updateCategories(categories));
        api.updateCategories(categories);
    }

    @Override
    public LiveData<List<Source>> queryBlockedSources() {
        return null;
    }

    @Override
    public void unblockSource(Source source) {

    }

    @Override
    public void blockSource(Source source) {

    }

    @Override
    public Style getCurrentStyle() {
        return currentStyle.getValue();
    }

    @Override
    public void setCurrentStyle(Style style) {
        BasePrefs.writeInt(KEY_NEWS_STYLE, style.getViewType());
        currentStyle.setValue(style);
    }

    @Override
    public LiveData<Style> queryCurrentStyle() {
        return currentStyle;
    }

    @Override
    public List<Style> queryAllStyles() {
        return StyleFactory.getAllStyles();
    }

    @Override
    public boolean isToolbarVisible() {
        return false;
    }

    @Override
    public LiveData<Boolean> queryToolbarVisibility() {
        return null;
    }

    @Override
    public void toggleToolbarVisibility() {

    }

    private LiveData<PageData<News>> queryFromApi(Category category, String sessionId) {
        LiveData<List<News>> apiSource = getApiSource(category, sessionId);
        return queryNews(Collections.singletonList(apiSource));
    }

    private LiveData<List<News>> getApiSource(Category category, String sessionId) {
        LiveData<List<News>> source = category.isPersonalizeCategory() ? api.queryPersonalizeNews(sessionId) : api.queryCategoryNews(sessionId, category.getCategoryId());
        return Transformations.map(source, new Function<List<News>, List<News>>() {
            @Override
            public List<News> apply(List<News> allNews) {
                ioThread.execute(() -> cache.daoNews().insertNews(allNews));
                return allNews;
            }
        });
    }

    private LiveData<List<News>> getCacheSource(Category category) {
        if (category.isPersonalizeCategory()) return cache.daoNews().queryNews(0, NewsConfig.NEWS_PAGE_SIZE);
        return Transformations.map(cache.daoNews().queryNews(0, NewsConfig.NEWS_PAGE_SIZE), new Function<List<News>, List<News>>() {
            @Override
            public List<News> apply(List<News> allNews) {
                Iterator<News> iter = allNews.iterator();
                while (iter.hasNext()) {
                    Category cat = iter.next().getCategory();
                    if (cat == null || cat.getCategoryId() != category.getCategoryId()) {
                        iter.remove();
                    }
                }
                return allNews;
            }
        });
    }

    private LiveData<PageData<News>> queryFromCache(Category category, String sessionId) {
        LiveData<List<News>> cacheSource = getCacheSource(category);
        LiveData<List<News>> apiSource = getApiSource(category, sessionId);
        return queryNews(Arrays.asList(cacheSource, apiSource));
    }

    private LiveData<PageData<News>> queryNews(List<LiveData<List<News>>> sources) {
        MediatorLiveData<PageData<News>> result = new MediatorLiveData<>();
        querySources(result, 0, sources);
        return result;
    }

    private void querySources(MediatorLiveData<PageData<News>> mediator, int index, List<LiveData<List<News>>> sources) {
        if (index >= sources.size()) {
            mediator.postValue(null);
            return;
        }

        LiveData<List<News>> source = sources.get(index);
        mediator.addSource(source, data -> {
            mediator.removeSource(source);
            if (data == null || data.isEmpty()) {
                querySources(mediator, index + 1, sources);
            } else {
                mediator.postValue(new PageData<News>(data.size(), data));
            }
        });
    }
}
