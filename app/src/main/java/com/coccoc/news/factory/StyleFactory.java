package com.coccoc.news.factory;

import com.coccoc.news.R;
import com.coccoc.news.base.views.recycler.RecyclerViewType;
import com.coccoc.news.model.Style;

import java.util.Arrays;
import java.util.List;

public class StyleFactory {
    public static Style create(@RecyclerViewType int type) {
        switch (type) {
            case RecyclerViewType.TYPE_NEWS_STYLE_MODERN:
                return new Style("Hiện đại", type, R.drawable.ccnews_symbol_modern_bg);
            case RecyclerViewType.TYPE_NEWS_STYLE_SIMPLE_LARGE:
                return new Style("Đơn giản (Lớn)", type, R.drawable.ccnews_symbol_large_bg);
            case RecyclerViewType.TYPE_NEWS_STYLE_SIMPLE_MEDIUM:
                return new Style("Đơn giản (Vừa)", type, R.drawable.ccnews_symbol_medium_bg);
            case RecyclerViewType.TYPE_NEWS_STYLE_SIMPLE_SMALL:
                return new Style("Đơn giản (Nhỏ)", type, R.drawable.ccnews_symbol_small_bg);
        }
        return null;
    }

    public static List<Style> getAllStyles() {
        return Arrays.asList(
                create(RecyclerViewType.TYPE_NEWS_STYLE_MODERN),
                create(RecyclerViewType.TYPE_NEWS_STYLE_SIMPLE_LARGE),
                create(RecyclerViewType.TYPE_NEWS_STYLE_SIMPLE_MEDIUM),
                create(RecyclerViewType.TYPE_NEWS_STYLE_SIMPLE_SMALL));
    }
}
