package com.ss.newsapp.views;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ss.newsapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shubham Singhal
 */
public class NewsWebVIew extends AppCompatActivity {

	@BindView(R.id.web_view)
	WebView webView;
	@BindView(R.id.progress)
	ProgressBar progressBar;
	@BindView(R.id.toolbar)
	Toolbar toolbar;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_detail_view);
		ButterKnife.bind(this);
		init();
	}

	/**
	 * Initialise all objects in this method.
	 */
	private void init() {
		String url = getIntent().getStringExtra("url");

		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		// Enable / Disable javascript
		webView.getSettings().setJavaScriptEnabled(true);


		// Enabling zoom-in controls
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setDisplayZoomControls(true);
		webView.loadUrl(url);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				webView.loadUrl(url);
				return true;
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

				return super.shouldOverrideUrlLoading(view, request);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				progressBar.setVisibility(View.VISIBLE);
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				progressBar.setVisibility(View.GONE);
				super.onPageFinished(view, url);
			}

			@Override
			public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
				progressBar.setVisibility(View.GONE);
				super.onReceivedError(view, request, error);
			}
		});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
			this.webView.goBack();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}
}

