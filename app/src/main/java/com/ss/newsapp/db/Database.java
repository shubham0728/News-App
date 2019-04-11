package com.ss.newsapp.db;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.ss.newsapp.model.MainEntity;
import com.ss.newsapp.model.NewsEntity;
import com.ss.newsapp.model.SourceEntity;

/**
 * Created by Shubham Singhal
 */
@android.arch.persistence.room.Database(entities = {MainEntity.class, NewsEntity.class, SourceEntity.class}, version = 1, exportSchema = false)
@TypeConverters({DataTypeConverterNews.class,DataTypeConverterSource.class})
public abstract class Database extends RoomDatabase {

	public abstract MainDao mainDao();

	public abstract NewsDao newsDao();

	public abstract SourcesDao sourcesDao();

	private static Database INSTANCE;

	// Name of the database
	public static final String DATABASE_NAME = "news_database";

	/**
	 * Database Instance.
	 *
	 * @param context
	 * @return
	 */
	public static Database getDatabase(final Context context) {
		if (INSTANCE == null) {
			synchronized (Database.class) {
				if (INSTANCE == null) {
					INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
							Database.class, DATABASE_NAME)
							.fallbackToDestructiveMigration()
							.allowMainThreadQueries()
							.build();
				}
			}
		}
		return INSTANCE;
	}
}
