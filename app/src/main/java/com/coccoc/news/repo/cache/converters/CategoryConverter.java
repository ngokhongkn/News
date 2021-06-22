package com.coccoc.news.repo.cache.converters;

import androidx.room.TypeConverter;

import com.coccoc.news.repo.model.Category;
import com.coccoc.news.repo.model.Source;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class CategoryConverter {
    @TypeConverter
    public static Category string2obj(String json) {
        Type type = new TypeToken<Category>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }

    @TypeConverter
    public static String obj2string(Category obj) {
        if (obj == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Category>() {
        }.getType();
        return gson.toJson(obj, type);
    }
}
