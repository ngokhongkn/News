package com.coccoc.news.ui;

import com.coccoc.news.repo.model.News;
import com.coccoc.news.model.Reason;
import com.coccoc.news.repo.model.Source;

public interface NewsBehavior {
    void refresh();
    void scrollToTop();
    void hideArticle(News news);
    void hideSource(Source source);
    void open(News news);
    void openInNewTab(News news);
    void openInNewIncognito(News news);
    void sendReport(News news, Reason reason);
}
