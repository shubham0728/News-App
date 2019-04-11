package com.ss.newsapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.ss.newsapp.db.DataTypeConverterSource;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Shubham Singhal
 */

@Entity(tableName = "News")
public class NewsEntity implements Serializable {

	@NonNull
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "id")
	private int id;

	@android.arch.persistence.room.TypeConverters(DataTypeConverterSource.class)
	@SerializedName("source")
	@ColumnInfo(name = "source")
	@Expose
	private SourceEntity sources;

	@SerializedName("author")
	@ColumnInfo(name = "author")
	@Expose
	private String author;

	@SerializedName("title")
	@ColumnInfo(name = "title")
	@Expose
	private String title;

	@SerializedName("description")
	@ColumnInfo(name = "description")
	@Expose
	private String description;

	@SerializedName("url")
	@ColumnInfo(name = "url")
	@Expose
	private String url;

	@SerializedName("urlToImage")
	@ColumnInfo(name = "urlToImage")
	@Expose
	private String urlToImage;

	@SerializedName("publishedAt")
	@ColumnInfo(name = "publishedAt")
	@Expose
	private String publishedAt;

	@SerializedName("content")
	@ColumnInfo(name = "content")
	@Expose
	private String content;


	@NonNull
	public int getId() {
		return id;
	}

	public void setId(@NonNull int id) {
		this.id = id;
	}

	public SourceEntity getSources() {
		return sources;
	}

	public void setSources(SourceEntity sources) {
		this.sources = sources;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlToImage() {
		return urlToImage;
	}

	public void setUrlToImage(String urlToImage) {
		this.urlToImage = urlToImage;
	}

	public String getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(String publishedAt) {
		this.publishedAt = publishedAt;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
