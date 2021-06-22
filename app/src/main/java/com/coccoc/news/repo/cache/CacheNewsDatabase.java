package com.coccoc.news.repo.cache;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.coccoc.news.repo.cache.converters.CategoryConverter;
import com.coccoc.news.repo.cache.converters.SourceConverter;
import com.coccoc.news.repo.model.Category;
import com.coccoc.news.repo.model.News;
import com.coccoc.news.repo.model.Source;


@Database(entities = {
        News.class,
        Category.class,
        Source.class
}, version = CacheNewsDatabase.DB_VERSION, exportSchema = false)
@TypeConverters({CategoryConverter.class, SourceConverter.class})
public abstract class CacheNewsDatabase extends RoomDatabase {

    public static final String DB_NAME = "cc_news";
    public static final int DB_VERSION = 1;

    private static CacheNewsDatabase sInstance;

    public static CacheNewsDatabase getDatabase(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(), CacheNewsDatabase.class, CacheNewsDatabase.DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return sInstance;
    }

    public abstract DaoNews daoNews();
    public abstract DaoCategory daoCategory();
}