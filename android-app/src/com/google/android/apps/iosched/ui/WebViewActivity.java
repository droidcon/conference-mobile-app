/*
 * Copyright 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.apps.iosched.ui;

import android.app.Activity;
import android.os.Bundle;
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

/**
 * Shows a {@link WebView} with a map of the conference venue. 
 */
public abstract class WebViewActivity extends Activity {
    private static final String TAG = "WebViewActivity";

    private static boolean CLEAR_CACHE_ON_LOAD = false;

    protected WebView mWebView;
    private boolean mLoadingVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

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
                mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
                mWebView.setWebChromeClient(new MapWebChromeClient());
                mWebView.setWebViewClient(new MapWebViewClient());
                loadData(mWebView);
            }
        });
    }

    protected abstract void loadData(WebView webView) ;

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

    void runJs(String js) {
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

        public void onConsoleMessage(String message, int lineNumber, String sourceID) {
            Log.i(TAG, "JS Console message: (" + sourceID + ": " + lineNumber + ") " + message);
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
        public void onReceivedError(WebView view, int errorCode, String description,
                String failingUrl) {
            Log.e(TAG, "Error " + errorCode + ": " + description);
            Toast.makeText(view.getContext(), "Error " + errorCode + ": " + description,
                    Toast.LENGTH_LONG).show();
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

    /**
     * Helper method to escape JavaScript strings. Useful when passing strings to a WebView
     * via "javascript:" calls.
     */
    protected static String escapeJsString(String s) {
        if (s == null)
            return "";

        return s.replace("'", "\\'").replace("\"", "\\\"");
    }

}
