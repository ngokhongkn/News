package com.coccoc.news.repo.api;

import android.webkit.CookieManager;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.coccoc.news.BuildConfig;
import com.coccoc.news.base.prefs.BasePrefs;
import com.coccoc.news.base.repository.api.BaseHttpClientBuilder;
import com.coccoc.news.base.repository.api.BaseRetrofitBuilder;
import com.coccoc.news.base.repository.api.callback.ApiCallback;
import com.coccoc.news.config.NewsConfig;
import com.coccoc.news.repo.api.response.NewsResponse;
import com.coccoc.news.repo.api.response.UserSettingResponse;
import com.coccoc.news.repo.model.Category;
import com.coccoc.news.repo.model.News;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.internal.EverythingIsNonNull;

public class ApiClient {
    private final Api mApi;
    public static final String KEY_NEWS_PAGE = "cc_key_news_page";

    private static ApiClient sInstance = null;

    public static ApiClient getInstance() {
        if (sInstance == null) {
            sInstance = new ApiClient();
        }
        return sInstance;
    }

    private ApiClient() {
        OkHttpClient client = BaseHttpClientBuilder.defaultBuilder()
                .cookieJar(new NewsCookiesJar())
                .build();

        mApi = BaseRetrofitBuilder.defautlBuilder(BuildConfig.NEWS_HOST, client).build().create(Api.class);
    }

    private static class NewsCookiesJar implements CookieJar {

        @EverythingIsNonNull
        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            CookieManager cookieManager = CookieManager.getInstance();

            for (Cookie cookie : cookies) {
                cookieManager.setCookie(url.toString(), cookie.toString());
            }
        }

        @EverythingIsNonNull
        @Override
        public List<Cookie> loadForRequest(@NonNull HttpUrl url) {
            CookieManager cookieManager = CookieManager.getInstance();
            List<Cookie> cookies = new ArrayList<>();
            if (cookieManager.getCookie(url.toString()) != null) {
                String[] splitCookies = cookieManager.getCookie(url.toString()).split("[,;]");
                for (String cookie : splitCookies) {
                    if (!cookie.contains("vid=")) {
                        cookies.add(Cookie.parse(url, cookie.trim()));
                    }
                }
            }

            Cookie vidCookie = createVidCookie();
            cookies.add(vidCookie);
            return cookies;
        }

        private Cookie createVidCookie() {
            return new Cookie.Builder()
                    .domain("coccoc.com")
                    .path("/")
                    .name("vid")
                    .value(getVid())
                    .build();
        }

        private String getVid() {
            return "3DoDoUbS1J3C31MJ9b1d79o1MoJySU9bJbMoWobcXsyu_Vmls6J";
        }
    }

    private <T> LiveData<T> callApi(Call<T> call) {
        ApiCallback<T> callback = new ApiCallback<>();
        call.enqueue(callback);
        return callback.asLiveData();
    }

    public LiveData<List<News>> queryPersonalizeNews(String sid) {
        int page = getNextPage();
        return Transformations.map(callApi(mApi.queryPersonalizeNews(sid, page, NewsConfig.NEWS_PAGE_SIZE)), new Function<NewsResponse, List<News>>() {
            @Override
            public List<News> apply(NewsResponse response) {
                if (response != null) {
                    saveNextPage(response.getNextPage());
                }
                return response == null ? null : response.getData();
            }
        });
    }

    public LiveData<List<News>> queryCategoryNews(String sessionId, long categoryId) {
        int page = getNextPage();
        return Transformations.map(callApi(mApi.queryCategoryNews(sessionId, page, NewsConfig.NEWS_PAGE_SIZE, categoryId)), new Function<NewsResponse, List<News>>() {
            @Override
            public List<News> apply(NewsResponse response) {
                if (response != null) {
                    saveNextPage(response.getNextPage());
                }
                return response == null ? null : response.getData();
            }
        });
    }

    public void saveNextPage(int page) {
        BasePrefs.writeInt(KEY_NEWS_PAGE, page);
    }

    private int getNextPage() {
        return BasePrefs.readInt(KEY_NEWS_PAGE, 1);
    }

    public LiveData<List<Category>> queryCategories() {
        return Transformations.map(callApi(mApi.queryUserSettings()), new Function<UserSettingResponse, List<Category>>() {
            @Override
            public List<Category> apply(UserSettingResponse userSettings) {
                return userSettings == null ? null : userSettings.getCategories();
            }
        });
    }

    public void updateCategories(List<Category> categories) {
        List<Long> subscribeCategories = new ArrayList<>();
        for (Category category : categories) {
            if (category.hasSubscribed()) {
                subscribeCategories.add(category.getCategoryId());
            }
        }

        callApi(mApi.subscribeCategories(UserAction.Type.SUBSCRIBE_CATEGORY, subscribeCategories.toString()));
    }
}
