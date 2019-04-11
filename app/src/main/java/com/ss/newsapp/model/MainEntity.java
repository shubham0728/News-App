package com.ss.newsapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ss.newsapp.db.DataTypeConverterNews;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Shubham Singhal
 */

@Entity(tableName = "Main")
public class MainEntity implements Serializable {

	@NonNull
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "id")
	private int id;

	@android.arch.persistence.room.TypeConverters(DataTypeConverterNews.class)
	@SerializedName("articles")
	@ColumnInfo(name = "articles")
	@Expose
	private List<NewsEntity> newsEntities;

	@SerializedName("totalResults")
	@ColumnInfo(name = "totalResults")
	@Expose
	private Integer totalResults;

	@NonNull
	public int getId() {
		return id;
	}

	public void setId(@NonNull int id) {
		this.id = id;
	}

	public List<NewsEntity> getNewsEntities() {
		return newsEntities;
	}

	public void setNewsEntities(List<NewsEntity> newsEntities) {
		this.newsEntities = newsEntities;
	}

	public Integer getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(Integer totalResults) {
		this.totalResults = totalResults;
	}

}
