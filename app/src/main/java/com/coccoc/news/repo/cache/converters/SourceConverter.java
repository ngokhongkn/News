package com.coccoc.news.repo.cache.converters;

import androidx.room.TypeConverter;

import com.coccoc.news.repo.model.Source;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class SourceConverter {
    @TypeConverter
    public static Source string2obj(String json) {
        Type type = new TypeToken<Source>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }

    @TypeConverter
    public static String obj2string(Source obj) {
        if (obj == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Source>() {
        }.getType();
        return gson.toJson(obj, type);
    }
}
