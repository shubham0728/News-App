package com.ss.newsapp.db;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ss.newsapp.model.SourceEntity;

import java.lang.reflect.Type;

/**
 * Created by Shubham Singhal
 */
public class DataTypeConverterSource {

	@TypeConverter
	public static SourceEntity stringToMeasurements(String json) {
		Gson gson = new Gson();
		Type type = new TypeToken<SourceEntity>() {
		}.getType();
		SourceEntity measurements = gson.fromJson(json, type);
		return measurements;
	}

	@TypeConverter
	public static String measurementsToString(SourceEntity list) {
		Gson gson = new Gson();
		Type type = new TypeToken<SourceEntity>() {
		}.getType();
		String json = gson.toJson(list, type);
		return json;
	}
}
