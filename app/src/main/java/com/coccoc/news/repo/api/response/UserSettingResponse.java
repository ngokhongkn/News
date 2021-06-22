package com.coccoc.news.repo.api.response;

import com.coccoc.news.repo.model.Category;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UserSettingResponse {

    public static class Group {
        @SerializedName("categories")
        List<Category> categories;
    }

    @SerializedName("all_categories")
    List<Group> groups;

    @SerializedName("sub_categories")
    List<Long> subCategories;

    public UserSettingResponse(List<Group> groups, List<Long> subCategories) {
        this.groups = groups;
        this.subCategories = subCategories;
    }

    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        for (Group group : groups) {
            for (Category category : group.categories) {
                boolean hasSubscribed = subCategories.contains(category.getCategoryId());
                category.setSubscribed(hasSubscribed);
                categories.add(category);
            }
        }
        return categories;
    }
}
