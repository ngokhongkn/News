package com.coccoc.news.base.views.recycler;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface RecyclerViewType {
    int TYPE_INVALID = 0;

    int TYPE_NEWS_BASE = 1989;

    int TYPE_NEWS_STYLE_MODERN = TYPE_NEWS_BASE + 1;
    int TYPE_NEWS_STYLE_SIMPLE_LARGE = TYPE_NEWS_BASE + 3;
    int TYPE_NEWS_STYLE_SIMPLE_MEDIUM = TYPE_NEWS_BASE + 5;
    int TYPE_NEWS_STYLE_SIMPLE_SMALL = TYPE_NEWS_BASE + 6;
    int TYPE_NEWS_CATEGORY = TYPE_NEWS_BASE + 7;
}
