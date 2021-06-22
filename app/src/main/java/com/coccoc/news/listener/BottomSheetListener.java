package com.coccoc.news.listener;

import com.coccoc.news.repo.model.News;

public interface BottomSheetListener {
    void share(News news);

    void openInNewTab(News news);

    void bookmarks(News news);
}
