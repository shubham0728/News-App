package com.ss.newsapp.repository;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.ss.newsapp.model.NewsEntity;

import java.util.List;

/**
 * Created by Shubham Singhal
 */
public class NewsViewModel extends AndroidViewModel {

	private NewsRepository newsRepository;
	private LiveData<List<NewsEntity>> newslistLiveData;
	private List<NewsEntity> newsEntitiesList;

	/**
	 * Constructor
	 *
	 * @param application
	 */
	public NewsViewModel(@NonNull Application application) {
		super(application);
		newsRepository = new NewsRepository(application);
		newslistLiveData = newsRepository.getAllNewsData();
		newsEntitiesList = newsRepository.getNewsData();
	}

	public LiveData<List<NewsEntity>> getAllNewsData() {
		return newslistLiveData;
	}

	public List<NewsEntity> getNewsDatawithoutLiveData() {
		return newsEntitiesList;
	}

	public NewsEntity loadNewsData(int id) {
		return newsRepository.loadNewsData(id);
	}

	public void insert(NewsEntity newsEntity) {
		newsRepository.insert(newsEntity);
	}

	public void updateNewsData(NewsEntity newsEntity) {
		newsRepository.updateNewsData(newsEntity);
	}


	public void deleteAll() {
		newsRepository.deleteAll();
	}
}
