package com.ss.newsapp.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import com.ss.newsapp.db.Database;
import com.ss.newsapp.db.NewsDao;
import com.ss.newsapp.model.NewsEntity;

import java.util.List;

/**
 * Created by Shubham Singhal
 */
public class NewsRepository {

	private NewsDao newsDao;
	private LiveData<List<NewsEntity>> newsEnityLiveData;
	private List<NewsEntity> newsEntityList;
	private NewsEntity newsEntity;
	private Database db;

	/**
	 * Constructor
	 *
	 * @param ctx
	 */
	public NewsRepository(Context ctx) {
		db = Database.getDatabase(ctx);
		newsDao = db.newsDao();
		newsEnityLiveData = newsDao.loadAllNewsData();
		newsEntityList = newsDao.getAllNewsData();
	}

	/*
	Get observable data from db.
	 */
	LiveData<List<NewsEntity>> getAllNewsData() {
		return newsEnityLiveData;
	}

	List<NewsEntity> getNewsData() {
		return newsEntityList;
	}


	public NewsEntity loadNewsData(final int id) {
		if (newsDao.getNewsDataByID(id) != null) {
			newsEntity = newsDao.getNewsDataByID(id);
		}
		return newsEntity;
	}

	/**
	 * Insert data in local db using Asyc Task.
	 *
	 * @param newsEntity
	 */
	public void insert(NewsEntity newsEntity) {
		new insertAsyncTask(newsDao).execute(newsEntity);
	}

	private static class insertAsyncTask extends AsyncTask<NewsEntity, Void, Void> {

		private NewsDao mAsyncTaskDao;

		insertAsyncTask(NewsDao dao) {
			mAsyncTaskDao = dao;
		}


		@Override
		protected Void doInBackground(NewsEntity... newsEntities) {
			try {
				mAsyncTaskDao.insert(newsEntities[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	/**
	 * Update data in local db using Async task.
	 *
	 * @param newsEntity
	 */
	public void updateNewsData(NewsEntity newsEntity) {
		new UpdateAsyncTask(newsDao).execute(newsEntity);
	}

	private static class UpdateAsyncTask extends AsyncTask<NewsEntity, Void, Void> {

		NewsDao newsDao;

		public UpdateAsyncTask(NewsDao _newsDao) {
			this.newsDao = _newsDao;
		}

		@Override
		protected Void doInBackground(NewsEntity... newsEntities) {
			try {
				newsDao.update(newsEntities[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	public void deleteAll() {
		if (newsDao != null) {
			newsDao.deleteAll();
		}
	}

}
