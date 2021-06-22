package com.coccoc.news.repo.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.coccoc.news.base.repository.database.BaseEntity;
import com.coccoc.news.base.views.recycler.RecyclerData;
import com.coccoc.news.base.views.recycler.RecyclerViewType;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "item_category")
public class Category extends BaseEntity implements RecyclerData {

    private static final long PERSONALIZE_CATEGORY_ID = 0;

    @PrimaryKey
    @SerializedName("id")
    private long categoryId;

    @SerializedName("name")
    private String name;

    @SerializedName("image_url")
    private String imageUrl;

    private boolean hasSubscribed;

    public Category(long categoryId, String name, String imageUrl) {
        this.categoryId = categoryId;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public boolean hasSubscribed() {
        return hasSubscribed;
    }

    public void setSubscribed(boolean hasSubscribed) {
        this.hasSubscribed = hasSubscribed;
    }

    public boolean isPersonalizeCategory() {
        return categoryId == PERSONALIZE_CATEGORY_ID;
    }

    @Override
    public int getViewType() {
        return RecyclerViewType.TYPE_NEWS_CATEGORY;
    }

    @Override
    public boolean areItemsTheSame(RecyclerData other) {
        return false;
    }

    @Override
    public boolean areContentsTheSame(RecyclerData other) {
        return false;
    }

    public static Category getDefault() {
        return new Category(PERSONALIZE_CATEGORY_ID, "Dành cho bạn", null);
    }
}
