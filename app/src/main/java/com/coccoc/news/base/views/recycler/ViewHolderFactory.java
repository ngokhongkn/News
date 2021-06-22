package com.coccoc.news.base.views.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;

import com.coccoc.news.R;
import com.coccoc.news.holder.CategoryViewHolder;
import com.coccoc.news.holder.NewsViewHolder;

public class ViewHolderFactory {

    public static BaseRecyclerViewHolder createViewHolder(@RecyclerViewType int viewType, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), parent, false);
        switch (viewType) {
            case RecyclerViewType.TYPE_INVALID:
                return new InvalidViewHolder(view);
            case RecyclerViewType.TYPE_NEWS_STYLE_MODERN:
            case RecyclerViewType.TYPE_NEWS_STYLE_SIMPLE_LARGE:
            case RecyclerViewType.TYPE_NEWS_STYLE_SIMPLE_MEDIUM:
            case RecyclerViewType.TYPE_NEWS_STYLE_SIMPLE_SMALL:
                return NewsViewHolder.create(view, viewType);
            case RecyclerViewType.TYPE_NEWS_CATEGORY:
                return new CategoryViewHolder(view);
        }
        return new InvalidViewHolder(view);
    }

    @LayoutRes
    private static int getLayoutId(int viewType) {
        switch (viewType) {
            case RecyclerViewType.TYPE_INVALID:
                return 0;
            case RecyclerViewType.TYPE_NEWS_STYLE_MODERN:
                return R.layout.holder_news_style_modern;
            case RecyclerViewType.TYPE_NEWS_STYLE_SIMPLE_LARGE:
                return R.layout.holder_news_style_large;
            case RecyclerViewType.TYPE_NEWS_STYLE_SIMPLE_MEDIUM:
                return R.layout.holder_news_style_medium;
            case RecyclerViewType.TYPE_NEWS_STYLE_SIMPLE_SMALL:
                return R.layout.holder_news_style_small;
            case RecyclerViewType.TYPE_NEWS_CATEGORY:
                return R.layout.ccnews_holder_setting_category;

        }
        return 0;
    }

}
