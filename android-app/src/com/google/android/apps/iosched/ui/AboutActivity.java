package com.google.android.apps.iosched.ui;

import com.google.android.apps.iosched.droidconuk2010.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends Activity {

	public static final String TAG = AboutActivity.class.getSimpleName(); 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		((ImageButton) findViewById(R.id.about_btn_buynow)).setOnClickListener(clickListener);
		((ImageView) findViewById(R.id.about_address_icon)).setOnClickListener(clickListener);
		((TextView) findViewById(R.id.about_address_txt)).setOnClickListener(clickListener);
		((TextView) findViewById(R.id.about_twitter)).setOnClickListener(clickListener);
		((TextView) findViewById(R.id.about_twitter_hashtag)).setOnClickListener(clickListener);
		((ImageView) findViewById(R.id.about_sponsors_orange)).setOnClickListener(clickListener);
		((ImageView) findViewById(R.id.about_sponsors_sonyericsson)).setOnClickListener(clickListener);
		
	}
	
	OnClickListener clickListener = new OnClickListener() {
		public void onClick(View v) {
			Log.i(TAG, "ID is: " +  v.getId() );
			switch(v.getId()){
				case R.id.about_btn_buynow:
    				v.getContext().startActivity(new Intent(v.getContext(), AmiandoActivity.class));
    				break;
				case R.id.about_address_txt: 
				case R.id.about_address_icon: 
					v.getContext().startActivity(new Intent(v.getContext(), MapActivity.class));
					 String uri = "geo:0,0?q=52+Upper+Street,+Islington,+London,+N1+0QH&sll=53.800651,-4.064941&sspn=24.07088,27.246094&ie=UTF8&hq=&hnear=52+Upper+St,+London+N1+0QH,+United+Kingdom&z=16";        
					    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
					    startActivity(i); 
					
					break;
				case R.id.about_twitter: 
					startActivity( new Intent( Intent.ACTION_VIEW, Uri.parse( "http://mobile.twitter.com/droidconuk" )));
					break;
				case R.id.about_twitter_hashtag: 
					startActivity( new Intent( Intent.ACTION_VIEW, Uri.parse( "http://mobile.twitter.com/searches?q=%23droidcon" )));
					break;
				case R.id.about_sponsors_orange: 
					startActivity( new Intent( Intent.ACTION_VIEW, Uri.parse( "http://www.orangepartner.com/site/enuk/home/p_home.jsp" )));
					break;
				case R.id.about_sponsors_sonyericsson: 
					startActivity( new Intent( Intent.ACTION_VIEW, Uri.parse("http://developer.sonyericsson.com/")));
					break;
			}
		}
	};

}
