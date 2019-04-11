package com.ss.newsapp.db;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ss.newsapp.model.NewsEntity;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Shubham Singhal
 */
public class DataTypeConverterNews {
	@TypeConverter
	public static List<NewsEntity> stringToMeasurements(String json) {
		Gson gson = new Gson();
		Type type = new TypeToken<List<NewsEntity>>() {

		}.getType();
		List<NewsEntity> measurements = gson.fromJson(json, type);
		return measurements;
	}

	@TypeConverter
	public static String measurementsToString(List<NewsEntity> list) {
		Gson gson = new Gson();
		Type type = new TypeToken<List<NewsEntity>>() {
		}.getType();
		String json = gson.toJson(list, type);
		return json;
	}

}
