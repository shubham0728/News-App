package com.ss.newsapp.repository;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.ss.newsapp.model.MainEntity;

import java.util.List;

/**
 * Created by Shubham Singhal
 */
public class MainViewModel extends AndroidViewModel {

	private MainRespository mainRespository;
	private LiveData<List<MainEntity>> mainlistLiveData;
	private List<MainEntity> mainEntitiesList;

	/**
	 * Constructor
	 *
	 * @param application
	 */
	public MainViewModel(@NonNull Application application) {
		super(application);
		mainRespository = new MainRespository(application);
		mainlistLiveData = mainRespository.getAllNews();
		mainEntitiesList = mainRespository.getNews();
	}

	public LiveData<List<MainEntity>> getAllNews() {
		return mainlistLiveData;
	}

	public List<MainEntity> getNewswithoutLiveData() {
		return mainEntitiesList;
	}

	public MainEntity loadNews(int id) {
		return mainRespository.loadNews(id);
	}

	public void insert(MainEntity mainEntity) {
		mainRespository.insert(mainEntity);
	}

	public void updateMain(MainEntity mainEntity) {
		mainRespository.updateNews(mainEntity);
	}


	public void deleteAll() {
		mainRespository.deleteAll();
	}
}
