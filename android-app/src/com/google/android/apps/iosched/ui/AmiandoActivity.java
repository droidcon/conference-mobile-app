package com.google.android.apps.iosched.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.apps.iosched.droidconuk2010.R;
import com.google.android.apps.iosched.util.UIUtils;

public class AmiandoActivity extends Activity {

	private static final String TAG = "AmiandoActivity";

	private static boolean CLEAR_CACHE_ON_LOAD = false;

	private WebView mWebView;
	private boolean mLoadingVisible = false;
	private boolean mMapInitialized = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		((TextView) findViewById(R.id.title_text)).setText(getTitle());

		showLoading(true);
		mWebView = (WebView) findViewById(R.id.webview);
		mWebView.post(new Runnable() {
			public void run() {
				// Initialize web view
				if (CLEAR_CACHE_ON_LOAD) {
					mWebView.clearCache(true);
				}

				mWebView.getSettings().setJavaScriptEnabled(true);
				mWebView.getSettings()
						.setJavaScriptCanOpenWindowsAutomatically(false);
				mWebView.setWebChromeClient(new MapWebChromeClient());
				mWebView.setWebViewClient(new MapWebViewClient());
				
				DisplayMetrics metrics = new DisplayMetrics();
				getWindowManager().getDefaultDisplay().getMetrics(metrics);
				
				Log.v(TAG, String.format(readTxt(), metrics.widthPixels, metrics.heightPixels - getResources().getDimensionPixelSize(R.dimen.title_height)));
				mWebView.loadData(String.format(readTxt(), metrics.widthPixels, metrics.heightPixels - getResources().getDimensionPixelSize(R.dimen.title_height)), "text/html", "utf-8");
			}
		});
	}

	private String readTxt() {

		InputStream inputStream = getResources().openRawResource(R.raw.amiando);

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		int i;
		try {
			i = inputStream.read();
			while (i != -1) {
				byteArrayOutputStream.write(i);
				i = inputStream.read();
			}
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return byteArrayOutputStream.toString();
	}

	public void onHomeClick(View v) {
		UIUtils.goHome(this);
	}

	public void onRefreshClick(View v) {
		mWebView.reload();
	}

	public void onSearchClick(View v) {
		UIUtils.goSearch(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void showLoading(boolean loading) {
		if (mLoadingVisible == loading)
			return;

		View refreshButton = findViewById(R.id.btn_title_refresh);
		View refreshProgress = findViewById(R.id.title_refresh_progress);
		refreshButton.setVisibility(loading ? View.GONE : View.VISIBLE);
		refreshProgress.setVisibility(loading ? View.VISIBLE : View.GONE);
		mLoadingVisible = loading;
	}

	private void runJs(String js) {
		if (Log.isLoggable(TAG, Log.DEBUG)) {
			Log.d(TAG, "Loading javascript:" + js);
		}
		mWebView.loadUrl("javascript:" + js);
	}

	private class MapWebChromeClient extends WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			showLoading(newProgress < 100);
			super.onProgressChanged(view, newProgress);
		}

		public void onConsoleMessage(String message, int lineNumber,
				String sourceID) {
			Log.i(TAG, "JS Console message: (" + sourceID + ": " + lineNumber
					+ ") " + message);
		}
	}

	private class MapWebViewClient extends WebViewClient {
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			if (Log.isLoggable(TAG, Log.DEBUG)) {
				Log.d(TAG, "Page finished loading: " + url);
			}
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			Log.e(TAG, "Error " + errorCode + ": " + description);
			Toast.makeText(view.getContext(),
					"Error " + errorCode + ": " + description,
					Toast.LENGTH_LONG).show();
			super.onReceivedError(view, errorCode, description, failingUrl);
		}
	}

}
