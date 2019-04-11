package com.ss.newsapp.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ss.newsapp.R;
import com.ss.newsapp.adapter.NewsAdapter;
import com.ss.newsapp.api.NewsApi;
import com.ss.newsapp.api.NewsApiClient;
import com.ss.newsapp.interfaces.RecyclerViewItemClickListener;
import com.ss.newsapp.model.MainEntity;
import com.ss.newsapp.model.NewsEntity;
import com.ss.newsapp.repository.MainViewModel;
import com.ss.newsapp.repository.NewsViewModel;
import com.ss.newsapp.util.PaginationScrollListner;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Shubham Singhal on 9,April,2019
 */
public class MainActivity extends AppCompatActivity implements RecyclerViewItemClickListener {
	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.news_list)
	RecyclerView newsList;
	@BindView(R.id.progress)
	ProgressBar progressBar;
	@BindView(R.id.layout_nothing_to_show)
	RelativeLayout layoutNothingToShow;

	private int currentPage = 1;
	private boolean isLastPage = false;
	private boolean isLoading = false;
	private int TotalPages = 3;
	private Handler handler = new Handler();
	private MainViewModel mainViewModel;
	private NewsViewModel newsViewModel;
	private NewsAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		init();
		checkConnection();
	}

	/**
	 * Initialise all objects in this method.
	 */
	private void init() {
		setSupportActionBar(toolbar);
		mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
		newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
		adapter = new NewsAdapter(MainActivity.this, MainActivity.this);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
		newsList.setLayoutManager(linearLayoutManager);
		newsList.setAdapter(adapter);

		newsList.addOnScrollListener(new PaginationScrollListner(linearLayoutManager) {
			@Override
			protected void loadMoreItems() {
				isLoading = true;
				currentPage = currentPage + 1;

				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						progressBar.setVisibility(View.VISIBLE);
						loadNextPageOfNews();
					}
				}, 500);
			}

			@Override
			public int getTotalPageCount() {
				return TotalPages;
			}

			@Override
			public boolean isLastPage() {
				return isLastPage;
			}

			@Override
			public boolean isLoading() {
				return isLoading;
			}
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}


	/**
	 * Fetch first batch of news on launch.
	 */
	private void getFirsTopHeadlines() {
		try {
			NewsApiClient newsApiClient = NewsApi.getRetrofitClient().create(NewsApiClient.class);
			Call<MainEntity> call = newsApiClient.getTopHeadlines(getString(R.string.api_key), currentPage);
			call.enqueue(new Callback<MainEntity>() {
				@Override
				public void onResponse(Call<MainEntity> call, Response<MainEntity> response) {
					if (response.isSuccessful()) {
						MainEntity mainEntity = response.body();
						mainViewModel.insert(mainEntity);
						progressBar.setVisibility(View.GONE);
						List<NewsEntity> newsEntities = mainEntity.getNewsEntities();
						adapter.addAll(newsEntities);

						if (currentPage <= TotalPages)
							adapter.addLoadingFooter();
						else
							isLastPage = true;
					} else {
						progressBar.setVisibility(View.GONE);
						layoutNothingToShow.setVisibility(View.VISIBLE);
						Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
					}
				}

				@Override
				public void onFailure(Call<MainEntity> call, Throwable t) {
					progressBar.setVisibility(View.GONE);
					if (t instanceof SocketTimeoutException || t instanceof IOException) {
						Toast.makeText(getApplicationContext(), getString(R.string.no_netowrk), Toast.LENGTH_SHORT).show();
						Log.e("ERROR", getString(R.string.no_netowrk), t);
					} else {
						progressBar.setVisibility(View.GONE);
						Log.e("ERROR", getString(R.string.error), t);
						Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			progressBar.setVisibility(View.GONE);
			Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Load next set of data from api.
	 */
	private void loadNextPageOfNews() {
		try {
			NewsApiClient newsApiClient = NewsApi.getRetrofitClient().create(NewsApiClient.class);
			Call<MainEntity> call = newsApiClient.getTopHeadlines(getString(R.string.api_key), currentPage);
			call.enqueue(new Callback<MainEntity>() {
				@Override
				public void onResponse(Call<MainEntity> call, Response<MainEntity> response) {
					if (response.isSuccessful()) {
						adapter.removeLoadingFooter();
						isLoading = false;
						progressBar.setVisibility(View.GONE);
						MainEntity mainEntity = response.body();
						mainViewModel.insert(mainEntity);
						List<NewsEntity> newsEntities = mainEntity.getNewsEntities();
						adapter.addAll(newsEntities);
						if (currentPage != TotalPages)
							adapter.addLoadingFooter();
						else
							isLastPage = true;

					} else {
						Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
					}
				}

				@Override
				public void onFailure(Call<MainEntity> call, Throwable t) {
					if (t instanceof SocketTimeoutException || t instanceof IOException) {
						progressBar.setVisibility(View.GONE);
						Toast.makeText(getApplicationContext(), getString(R.string.no_netowrk), Toast.LENGTH_SHORT).show();
						Log.e("ERROR", getString(R.string.no_netowrk), t);
					} else {
						progressBar.setVisibility(View.GONE);
						Log.e("ERROR", getString(R.string.error), t);
						Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			progressBar.setVisibility(View.GONE);
			Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
		}
	}


	/**
	 * To check if the device is connected to the internet.
	 *
	 * @return
	 */
	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		} else {
			return false;
		}
	}


	/**
	 * Performs actions based on network status.
	 * If Online -  Fetch first batch of movies.
	 * If offline - display nothing.
	 */
	public void checkConnection() {
		if (isOnline()) {
			mainViewModel.deleteAll();
			newsViewModel.deleteAll();
			getFirsTopHeadlines();
		} else {
			progressBar.setVisibility(View.GONE);
			layoutNothingToShow.setVisibility(View.VISIBLE);
			Toast.makeText(this, getString(R.string.no_netowrk), Toast.LENGTH_SHORT).show();
			loadDataWhenOffline();
		}
	}

	@Override
	public void onRecyclerViewItemClick(int position) {
		NewsEntity newsEntity = adapter.getItem(position);
		Intent in = new Intent(MainActivity.this, NewsWebVIew.class);
		in.putExtra("url", newsEntity.getUrl());
		startActivity(in);
	}

	/**
	 * Load Data in offline mode load if present.
	 */
	private void loadDataWhenOffline() {
		mainViewModel.getAllNews().observe(MainActivity.this, new Observer<List<MainEntity>>() {
			@Override
			public void onChanged(@Nullable List<MainEntity> mainEntities) {
				if (mainEntities.size() != 0) {
					for (MainEntity mainEntity : mainEntities) {
						List<NewsEntity> newsEntities = mainEntity.getNewsEntities();
						adapter.addAll(newsEntities);
					}
					layoutNothingToShow.setVisibility(View.GONE);
				}

			}
		});
	}
}
