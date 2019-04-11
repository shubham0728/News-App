package com.ss.newsapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ss.newsapp.R;
import com.ss.newsapp.interfaces.RecyclerViewItemClickListener;
import com.ss.newsapp.model.NewsEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shubham Singhal
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

	private static final int ITEM = 0;
	private static final int LOADING = 1;
	private Context mCtx;
	private RecyclerViewItemClickListener mrecyclerViewItemClickListener;
	private List<NewsEntity> newsEntityList;
	private boolean isLoadingAdded = false;

	/**
	 * Constructor
	 *
	 * @param _ctx
	 * @param recyclerViewItemClickListener
	 */
	public NewsAdapter(Context _ctx, RecyclerViewItemClickListener recyclerViewItemClickListener) {
		this.mCtx = _ctx;
		this.mrecyclerViewItemClickListener = recyclerViewItemClickListener;
		newsEntityList = new ArrayList<>();
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		RecyclerView.ViewHolder viewHolder = null;
		switch (i) {
			case ITEM:
				View movieView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_adapter, viewGroup, false);
				viewHolder = new NewsViewHolder(movieView);
				break;
			case LOADING:
				View loadView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_progress, viewGroup, false);
				viewHolder = new LoadingViewHolder(loadView);
				break;
		}
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
		try {
			switch (getItemViewType(i)) {
				case ITEM:
					NewsViewHolder newsViewHolder = (NewsViewHolder) viewHolder;
					NewsEntity newsEntity = newsEntityList.get(i);
					newsViewHolder.txt_news_title.setText(newsEntity.getTitle());
					newsViewHolder.txt_source.setText("Source: " + newsEntity.getSources().getName());
					Glide.with(mCtx)
							.load(newsEntity.getUrlToImage())
							.placeholder(R.drawable.placeholder)
							.diskCacheStrategy(DiskCacheStrategy.ALL)   // cache image
							.into(newsViewHolder.img_news);
				case LOADING:
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getItemCount() {
		return newsEntityList.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		return (position == newsEntityList.size() && isLoadingAdded) ? LOADING : ITEM;
	}

	/**
	 * Add single entity from api call.
	 *
	 * @param newsEntity
	 */
	public void add(NewsEntity newsEntity) {
		newsEntityList.add(newsEntity);
		notifyItemInserted(newsEntityList.size() - 1);
	}

	/**
	 * Add all data from the api call.
	 *
	 * @param newsEntities
	 */
	public void addAll(List<NewsEntity> newsEntities) {
		for (NewsEntity result : newsEntities) {
			add(result);
		}
	}

	/**
	 * @param newsEntity
	 */
	public void remove(NewsEntity newsEntity) {
		int position = newsEntityList.indexOf(newsEntity);
		if (position > -1) {
			newsEntityList.remove(position);
			notifyItemRemoved(position);
		}
	}

	public void clear() {
		isLoadingAdded = false;
		while (getItemCount() > 0) {
			remove(getItem(0));
		}
	}

	public boolean isEmpty() {
		return getItemCount() == 0;
	}


	/**
	 * Start loader when loading next page.
	 */
	public void addLoadingFooter() {
		isLoadingAdded = true;
		add(new NewsEntity());
	}

	/**
	 * Remove loader after page is loaded.
	 */
	public void removeLoadingFooter() {
		isLoadingAdded = false;

		int position = newsEntityList.size() - 1;
		NewsEntity newsEntity = getItem(position);

		if (newsEntity != null) {
			newsEntityList.remove(position);
			notifyItemRemoved(position);
		}
	}

	public NewsEntity getItem(int position) {
		return newsEntityList.get(position);
	}

	public List<NewsEntity> getAll() {
		return newsEntityList;
	}


	/**
	 * Movie ViewHolder Class with RecyclerView click listener.
	 */
	public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		private TextView txt_news_title;
		private TextView txt_news_date;
		private TextView txt_source;
		private ProgressBar progress_news;
		private ImageView img_news;

		public NewsViewHolder(@NonNull View itemView) {
			super(itemView);
			txt_news_title = itemView.findViewById(R.id.txt_news_title);
			txt_news_date = itemView.findViewById(R.id.txt_news_date);
			txt_source = itemView.findViewById(R.id.txt_source);
			progress_news = itemView.findViewById(R.id.progress_news);
			img_news = itemView.findViewById(R.id.img_news);

			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View view) {
			mrecyclerViewItemClickListener.onRecyclerViewItemClick(getAdapterPosition());
		}
	}

	protected class LoadingViewHolder extends RecyclerView.ViewHolder {

		public LoadingViewHolder(View itemView) {
			super(itemView);
		}
	}
}
