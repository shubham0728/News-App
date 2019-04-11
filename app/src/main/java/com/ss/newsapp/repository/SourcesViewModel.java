package com.ss.newsapp.repository;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.ss.newsapp.model.SourceEntity;

import java.util.List;

/**
 * Created by Shubham Singhal
 */
public class SourcesViewModel extends AndroidViewModel {

	private SourceRepository sourceRepository;
	private LiveData<List<SourceEntity>> sourcelistLiveData;
	private List<SourceEntity> sourceEntitiesList;

	/**
	 * Constructor
	 *
	 * @param application
	 */
	public SourcesViewModel(@NonNull Application application) {
		super(application);
		sourceRepository = new SourceRepository(application);
		sourcelistLiveData = sourceRepository.getAllSources();
		sourceEntitiesList = sourceRepository.getSources();
	}

	public LiveData<List<SourceEntity>> getAllSources() {
		return sourcelistLiveData;
	}

	public List<SourceEntity> getSourceswithoutLiveData() {
		return sourceEntitiesList;
	}

	public SourceEntity loadSources(int id) {
		return sourceRepository.loadSources(id);
	}

	public void insert(SourceEntity sourceEntity) {
		sourceRepository.insert(sourceEntity);
	}

	public void updateSource(SourceEntity sourceEntity) {
		sourceRepository.updateSource(sourceEntity);
	}


	public void deleteAll() {
		sourceRepository.deleteAll();
	}
}
