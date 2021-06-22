package com.coccoc.news.repo.model;


import android.graphics.Color;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.coccoc.news.base.repository.database.BaseEntity;
import com.coccoc.news.base.views.recycler.RecyclerData;
import com.coccoc.news.base.views.recycler.RecyclerViewType;
import com.coccoc.news.repo.RepoImpl;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "item_news")
public class News extends BaseEntity implements RecyclerData, Serializable {

    @PrimaryKey(autoGenerate = true)
    private long newsId;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("url")
    private String url;

    @SerializedName("domain")
    private String domain;

    @SerializedName("event_time")
    private String eventTime;

    @SerializedName("image_url")
    private String imageUrl;

    @Nullable
    @SerializedName("color_background")
    private String colorBackground;

    @Nullable
    @SerializedName("color_text")
    private String colorText;

    @SerializedName("category")
    private Category category;

    @SerializedName("source")
    private Source source;

    public News(long newsId, String title, String description, String url, String domain, String eventTime, String imageUrl, @Nullable String colorBackground, @Nullable String colorText) {
        this.newsId = newsId;
        this.title = title;
        this.description = description;
        this.url = url;
        this.domain = domain;
        this.eventTime = eventTime;
        this.imageUrl = imageUrl;
        this.colorBackground = colorBackground;
        this.colorText = colorText;
    }

    public String getUrl() {
        return url;
    }

    public String getDomain() {
        return domain;
    }

    public String getEventTime() {
        return eventTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Nullable
    public String getColorBackground() {
        return colorBackground;
    }

    @Nullable
    public String getColorText() {
        return colorText;
    }

    public int getDescriptionColor() {
        return parseColor(colorText, Color.WHITE);
    }

    public int getTitleColor() {
        return parseColor(colorText, Color.WHITE);
    }

    public int getDomainColor() {
        return parseColor(colorText, Color.WHITE);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public Source getSource() {
        return source;
    }

    public int getBackground() {
        return parseColor(colorBackground, Color.BLACK);
    }

    public long getNewsId() {
        return newsId;
    }

    public void setNewsId(long mNewsID) {
        this.newsId = mNewsID;
    }

    public void setTitle(String mTitle) {
        this.title = mTitle;
    }

    public void setDescription(String mDescription) {
        this.description = mDescription;
    }

    public void setUrl(String mUrl) {
        this.url = mUrl;
    }

    public void setDomain(String mDomain) {
        this.domain = mDomain;
    }

    public void setImageUrl(String mImageUrl) {
        this.imageUrl = mImageUrl;
    }

    public void setColorBackground(@Nullable String mColorBackground) {
        this.colorBackground = mColorBackground;
    }

    public void setColorText(@Nullable String mColorText) {
        this.colorText = mColorText;
    }

    public void setCategory(Category mCategory) {
        this.category = mCategory;
    }

    public void setSource(Source mSource) {
        this.source = mSource;
    }

    @Override
    public int getViewType() {
        return RepoImpl.getInstance().getCurrentStyle().getViewType();
    }

    @Override
    public boolean areItemsTheSame(RecyclerData other) {
        if (other instanceof News) {
            News news = (News) other;
            return url != null && url.equalsIgnoreCase(news.getUrl());
        }
        return false;
    }

    @Override
    public boolean areContentsTheSame(RecyclerData other) {
        if (other instanceof News) {
            News news = (News) other;
            return url != null && url.equalsIgnoreCase(news.getUrl());
        }
        return false;
    }

    private int parseColor(String color, int defColor) {
        try {
            return Color.parseColor(color);
        } catch (Exception e) {
            return defColor;
        }
    }
}