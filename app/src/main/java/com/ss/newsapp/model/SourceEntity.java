package com.ss.newsapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Shubham Singhal
 */

@Entity(tableName = "Source")
public class SourceEntity implements Serializable {


	@NonNull
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "random_id")
	private int random_id;

	@SerializedName("id")
	@ColumnInfo(name = "id")
	private String id;

	@SerializedName("name")
	@ColumnInfo(name = "name")
	@Expose
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRandom_id() {
		return random_id;
	}

	public void setRandom_id(int random_id) {
		this.random_id = random_id;
	}
}
