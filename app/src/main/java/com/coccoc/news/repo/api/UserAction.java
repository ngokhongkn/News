package com.coccoc.news.repo.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class UserAction {

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
        String SUBSCRIBE_CATEGORY = "subscribe_category";
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Complaint {
        String OLD_NEWS = "old_news";
        String WRONG_INFO = "wrong_info";
        String BAD_CONTENT = "bad_content";
        String OTHERS = "others";
    }
}
