package com.coccoc.news.repo.api.response;

import com.coccoc.news.repo.model.News;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsResponse {
    private static class NewsPage {
        @SerializedName("news")
        private List<News> mDataList;

        @SerializedName("next_page")
        private int mNextPage;

        public List<News> getDataList() {
            return mDataList;
        }

        public void setDataList(List<News> mDataList) {
            this.mDataList = mDataList;
        }

        public int getNextPage() {
            return mNextPage;
        }

        public void setNextPage(int mNextPage) {
            this.mNextPage = mNextPage;
        }
    }

    @SerializedName("rid")
    private String mRid;

    @SerializedName("sessionId")
    private String mSid;

    @SerializedName("layout")
    private String mLayout;

    @SerializedName("items")
    private NewsPage mNewsPage;

    public List<News> getData() {
        return mNewsPage != null ? mNewsPage.getDataList() : null;
    }

    public int getNextPage() {
        return mNewsPage.getNextPage();
    }
}
