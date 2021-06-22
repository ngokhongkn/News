package com.coccoc.news.repo.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.coccoc.news.base.AppLog;
import com.google.gson.annotations.SerializedName;


@Entity(tableName = "item_source")
public class Source {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @SerializedName("domain")
    private String domain;

    @SerializedName("title")
    private String title;

    @SerializedName("logo")
    private String logo;

    public Source(long id, String domain, String title, String logo) {
        this.id = id;
        this.domain = domain;
        this.title = title;
        this.logo = logo;
    }

    public String getLogo() {
        return logo.substring(logo.indexOf(",") + 1);
    }

    public long getId() {
        return id;
    }

    public String getDomain() {
        return domain;
    }

    public String getTitle() {
        return title;
    }

    public Bitmap getLogoBitmap() {
        try {
            byte[] pureBitmap = Base64.decode(getLogo(), Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(pureBitmap, 0, pureBitmap.length);
        } catch (Exception e) {
            AppLog.e("Can't decode bitmap. Logo: " + logo);
            return null;
        }
    }
}
