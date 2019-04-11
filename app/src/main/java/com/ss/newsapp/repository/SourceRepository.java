package com.ss.newsapp.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;


import com.ss.newsapp.db.Database;
import com.ss.newsapp.db.SourcesDao;
import com.ss.newsapp.model.SourceEntity;

import java.util.List;

/**
 * Created by Shubham Singhal
 */
public class SourceRepository {

	private SourcesDao sourcesDao;
	private LiveData<List<SourceEntity>> sourceEnityLiveData;
	private List<SourceEntity> sourceEntityList;
	private SourceEntity sourceEntity;
	private Database db;

	/**
	 * Constructor
	 *
	 * @param ctx
	 */
	public SourceRepository(Context ctx) {
		db = Database.getDatabase(ctx);
		sourcesDao = db.sourcesDao();
		sourceEnityLiveData = sourcesDao.loadAllSources();
		sourceEntityList = sourcesDao.getAllSources();
	}

	/*
	Get observable data from db.
	 */
	LiveData<List<SourceEntity>> getAllSources() {
		return sourceEnityLiveData;
	}

	List<SourceEntity> getSources() {
		return sourceEntityList;
	}


	public SourceEntity loadSources(final int id) {
		if (sourcesDao.getSourceDataByID(id) != null) {
			sourceEntity = sourcesDao.getSourceDataByID(id);
		}
		return sourceEntity;
	}

	/**
	 * Insert data in local db using Asyc Task.
	 *
	 * @param sourceEntity
	 */
	public void insert(SourceEntity sourceEntity) {
		new insertAsyncTask(sourcesDao).execute(sourceEntity);
	}

	private static class insertAsyncTask extends AsyncTask<SourceEntity, Void, Void> {

		private SourcesDao mAsyncTaskDao;

		insertAsyncTask(SourcesDao dao) {
			mAsyncTaskDao = dao;
		}


		@Override
		protected Void doInBackground(SourceEntity... sourceEntities) {
			try {
				mAsyncTaskDao.insert(sourceEntities[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	/**
	 * Update data in local db using Async task.
	 *
	 * @param sourceEntity
	 */
	public void updateSource(SourceEntity sourceEntity) {
		new UpdateAsyncTask(sourcesDao).execute(sourceEntity);
	}

	private static class UpdateAsyncTask extends AsyncTask<SourceEntity, Void, Void> {

		SourcesDao sourcesDao;

		public UpdateAsyncTask(SourcesDao _sourcesDao) {
			this.sourcesDao = _sourcesDao;
		}

		@Override
		protected Void doInBackground(SourceEntity... sourceEntities) {
			try {
				sourcesDao.update(sourceEntities[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	public void deleteAll() {
		if (sourcesDao != null) {
			sourcesDao.deleteAll();
		}
	}
}
