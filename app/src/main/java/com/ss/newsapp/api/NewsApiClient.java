package com.ss.newsapp.api;

import com.ss.newsapp.model.MainEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Shubham Singhal
 */
public interface NewsApiClient {

	@GET("top-headlines?country=in")
	Call<MainEntity> getTopHeadlines(@Query("apiKey") String key, @Query("page") int page);
}
