package novoda.droidcon2010uk.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebView;

import novoda.droidcon2010uk.util.UIUtils;
import novoda.droidconuk2010.R;

public class AmiandoActivity extends WebViewActivity {

	private String mTitleString;
	private String mHashtag;

	protected void loadData(WebView webView) {
        if(haveInternet()){
        	mTitleString = "in-app discount";
        	mHashtag = "droidcon";
        	
	        DisplayMetrics metrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(metrics);
	
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
				e.printStackTrace();
			}
	
			String html = byteArrayOutputStream.toString();
	
			
			html = String.format(html,  metrics.heightPixels - getResources().getDimensionPixelSize(R.dimen.title_height));
			webView.loadDataWithBaseURL( "file:///android-asset/", html, "text/html", "utf-8", null );
        }
	}
	
	/** Handle "share" title-bar action. */
	public void onShareClick(View v) {
		mTitleString = "in-app discount";

		if(haveInternet()){
        	mHashtag = "#savings";
        }else{
        	mHashtag = "#404";
        }
		
		final String shareString = getString(R.string.share_template, mTitleString, mHashtag);

		final Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, shareString);

		startActivity(Intent.createChooser(intent,
				getText(R.string.title_share)));
	}

}
