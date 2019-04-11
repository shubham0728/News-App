package com.ss.newsapp.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ss.newsapp.model.MainEntity;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Shubham Singhal
 */
@Dao
public interface MainDao {

	/**
	 * Returns observable data from local db.
	 *
	 * @return
	 */
	@Query("SELECT * FROM Main")
	LiveData<List<MainEntity>> loadAllNews();

	/**
	 * Returns non-observable data from local db.
	 *
	 * @return
	 */
	@Query("SELECT * FROM Main")
	List<MainEntity> getAllNews();

	/**
	 * Returns single entity based on id.
	 *
	 * @param id
	 * @return
	 */
	@Query("SELECT * FROM Main WHERE id like :id")
	MainEntity getNewsByID(int id);

	/**
	 * Insert single entity in local db.
	 *
	 * @param mainEntity
	 */
	@Insert
	void insert(MainEntity mainEntity);

	/**
	 * Delete all data from local db.
	 */
	@Query("Delete from Main")
	void deleteAll();

	/**
	 * Update data for single entity.
	 *
	 * @param mainEntity
	 */
	@Update(onConflict = REPLACE)
	void update(MainEntity mainEntity);
}
