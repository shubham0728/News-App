package com.ss.newsapp.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import com.ss.newsapp.db.Database;
import com.ss.newsapp.db.MainDao;
import com.ss.newsapp.model.MainEntity;

import java.util.List;

/**
 * Created by Shubham Singhal
 */
public class MainRespository {

	private MainDao mainDao;
	private LiveData<List<MainEntity>> mainListLiveData;
	private List<MainEntity> mainEntityList;
	private MainEntity mainEntity;
	private Database db;

	/**
	 * Constructor
	 *
	 * @param ctx
	 */
	public MainRespository(Context ctx) {
		db = Database.getDatabase(ctx);
		mainDao = db.mainDao();
		mainListLiveData = mainDao.loadAllNews();
		mainEntityList = mainDao.getAllNews();
	}

	/*
	Get observable data from db.
	 */
	LiveData<List<MainEntity>> getAllNews() {
		return mainListLiveData;
	}

	List<MainEntity> getNews() {
		return mainEntityList;
	}


	public MainEntity loadNews(final int id) {
		if (mainDao.getNewsByID(id) != null) {
			mainEntity = mainDao.getNewsByID(id);
		}
		return mainEntity;
	}

	/**
	 * Insert data in local db using Asyc Task.
	 *
	 * @param mainEntity
	 */
	public void insert(MainEntity mainEntity) {
		new insertAsyncTask(mainDao).execute(mainEntity);
	}

	private static class insertAsyncTask extends AsyncTask<MainEntity, Void, Void> {

		private MainDao mAsyncTaskDao;

		insertAsyncTask(MainDao dao) {
			mAsyncTaskDao = dao;
		}


		@Override
		protected Void doInBackground(MainEntity... mainEntities) {
			try {
				mAsyncTaskDao.insert(mainEntities[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	/**
	 * Update data in local db using Async task.
	 *
	 * @param mainEntity
	 */
	public void updateNews(MainEntity mainEntity) {
		new UpdateAsyncTask(mainDao).execute(mainEntity);
	}

	private static class UpdateAsyncTask extends AsyncTask<MainEntity, Void, Void> {

		MainDao mainDao;

		public UpdateAsyncTask(MainDao _mainDao) {
			this.mainDao = _mainDao;
		}

		@Override
		protected Void doInBackground(MainEntity... mainEntities) {
			try {
				mainDao.update(mainEntities[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	public void deleteAll() {
		if (mainDao != null) {
			mainDao.deleteAll();
		}
	}

}
