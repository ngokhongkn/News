package com.coccoc.news.config;

import com.coccoc.news.BuildConfig;
import com.coccoc.news.base.views.recycler.RecyclerViewType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class NewsConfig {
    public static final int NEWS_PAGE_SIZE = 10;

    @Retention(RetentionPolicy.SOURCE)
    public @interface VariationServiceFlag {
        String CCNEWS_TEST_CARD_SIZE = "CCNEWS_TEST_CARD_SIZE";
        String CCNEWS_TEST_CATEGORY = "CCNEWS_TEST_CATEGORY";
        String CCNEWS_TEST_CHANGE_STYLE = "CCNEWS_TEST_CHANGE_STYLE";
        String CCNEWS_TEST_OPEN_NEWS = "CCNEWS_TEST_OPEN_NEWS";
        String CCNEWS_TEST_SHOW_TIME = "CCNEWS_TEST_SHOW_TIME";
    }

    public static boolean isEnable(@VariationServiceFlag String flag) {
        return BuildConfig.DEBUG;
    }

    public static @RecyclerViewType
    int getDefaultStyle() {
        if (isEnable(VariationServiceFlag.CCNEWS_TEST_CARD_SIZE)) return RecyclerViewType.TYPE_NEWS_STYLE_SIMPLE_MEDIUM;
        return RecyclerViewType.TYPE_NEWS_STYLE_MODERN;
    }
}
