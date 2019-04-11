package com.ss.newsapp.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ss.newsapp.model.SourceEntity;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Shubham Singhal
 */
@Dao
public interface SourcesDao {
	/**
	 * Returns observable data from local db.
	 *
	 * @return
	 */
	@Query("SELECT * FROM Source")
	LiveData<List<SourceEntity>> loadAllSources();

	/**
	 * Returns non-observable data from local db.
	 *
	 * @return
	 */
	@Query("SELECT * FROM Source")
	List<SourceEntity> getAllSources();

	/**
	 * Returns single entity based on id.
	 *
	 * @param id
	 * @return
	 */
	@Query("SELECT * FROM Source WHERE id like :id")
	SourceEntity getSourceDataByID(int id);

	/**
	 * Insert single entity in local db.
	 *
	 * @param sourceEntity
	 */
	@Insert
	void insert(SourceEntity sourceEntity);

	/**
	 * Delete all data from local db.
	 */
	@Query("Delete from Source")
	void deleteAll();

	/**
	 * Update data for single entity.
	 *
	 * @param sourceEntity
	 */
	@Update(onConflict = REPLACE)
	void update(SourceEntity sourceEntity);
}
