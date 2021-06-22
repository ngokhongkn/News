package com.coccoc.news.repo.api;

import com.coccoc.news.repo.api.response.NewsResponse;
import com.coccoc.news.repo.api.response.UserActionResponse;
import com.coccoc.news.repo.api.response.UserSettingResponse;
import com.coccoc.news.repo.model.News;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    String NEWS_FEED_HOST = "https://coccoc.com/composer/feed/v1/";

    @GET("nre")
    Call<NewsResponse> queryPersonalizeNews(@Query("sessionId") String sid, @Query("page") int page, @Query("size") int per_page);

    @GET("nre")
    Call<NewsResponse> queryCategoryNews(@Query("sessionId") String sid, @Query("page") int page, @Query("size") int per_page, @Query("categoryId") long categoryId);

    @GET("user_settings")
    Call<UserSettingResponse> queryUserSettings();

    @FormUrlEncoded
    @POST("user_actions")
    Call<Void> subscribeCategories(@Field("action_type") @UserAction.Type String type, @Field("categories") String categoryIdList);
}
