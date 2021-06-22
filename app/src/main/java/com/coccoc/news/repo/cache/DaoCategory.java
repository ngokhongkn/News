package com.coccoc.news.repo.cache;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.coccoc.news.base.repository.database.BaseDao;
import com.coccoc.news.repo.model.Category;

import java.util.List;

@Dao
public abstract class DaoCategory extends BaseDao<Category> {

    @Query("select * from item_category where hasSubscribed = 1")
    public abstract LiveData<List<Category>> querySubscribedCategories();

    @Query("update item_category set hasSubscribed = :subscribed where categoryId = :categoryId")
    public abstract void updateSubscribe(long categoryId, boolean subscribed);

    @Transaction
    public void updateCategories(List<Category> categories) {
        for (Category category : categories) {
            updateSubscribe(category.getCategoryId(), category.hasSubscribed());
        }
    }

    @Transaction
    public void insertCategories(List<Category> categories) {
        for (Category category : categories) {
            insertOrReplace(category);
        }
    }
}
