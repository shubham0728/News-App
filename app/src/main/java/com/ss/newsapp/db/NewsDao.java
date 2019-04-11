package com.ss.newsapp.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ss.newsapp.model.NewsEntity;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Shubham Singhal
 */
@Dao
public interface NewsDao {

	/**
	 * Returns observable data from local db.
	 *
	 * @return
	 */
	@Query("SELECT * FROM News")
	LiveData<List<NewsEntity>> loadAllNewsData();

	/**
	 * Returns non-observable data from local db.
	 *
	 * @return
	 */
	@Query("SELECT * FROM News")
	List<NewsEntity> getAllNewsData();

	/**
	 * Returns single entity based on id.
	 *
	 * @param id
	 * @return
	 */
	@Query("SELECT * FROM News WHERE id like :id")
	NewsEntity getNewsDataByID(int id);

	/**
	 * Insert single entity in local db.
	 *
	 * @param newsEntity
	 */
	@Insert
	void insert(NewsEntity newsEntity);

	/**
	 * Delete all data from local db.
	 */
	@Query("Delete from News")
	void deleteAll();

	/**
	 * Update data for single entity.
	 *
	 * @param newsEntity
	 */
	@Update(onConflict = REPLACE)
	void update(NewsEntity newsEntity);
}
