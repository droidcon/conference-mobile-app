/*
 * Copyright 2011 Google Inc.
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

package novoda.droidcon.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.droidcon.uk.R;
import com.google.android.apps.iosched.ui.BaseActivity;
import com.google.android.apps.iosched.util.UIUtils;

public class AboutActivity extends BaseActivity {

	public static final String TAG = AboutActivity.class.getSimpleName(); 

    private Uri REGISTER_URI = Uri.parse("http://uk.droidcon.com/buyticket");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		((TextView) findViewById(R.id.about_buynow)).setOnClickListener(clickListener);
		((ImageView) findViewById(R.id.about_address_icon)).setOnClickListener(clickListener);
		((TextView) findViewById(R.id.about_address_txt)).setOnClickListener(clickListener);
		((TextView) findViewById(R.id.about_twitter)).setOnClickListener(clickListener);
		((TextView) findViewById(R.id.about_twitter_hashtag)).setOnClickListener(clickListener);

	}
	
	

	OnClickListener clickListener = new OnClickListener() {
		public void onClick(View v) {
			Log.i(TAG, "ID is: " +  v.getId() );
			switch(v.getId()){
				case R.id.about_buynow:
                	Intent intent = new Intent(Intent.ACTION_VIEW);
                	intent.setData(REGISTER_URI);
    				v.getContext().startActivity(intent);
    				break;
				case R.id.about_address_txt: 
				case R.id.about_address_icon: 
	                startActivity(new Intent(v.getContext(), UIUtils.getMapActivityClass(v.getContext())));
					break;
				case R.id.about_twitter: 
					startActivity( new Intent( Intent.ACTION_VIEW, Uri.parse( "http://mobile.twitter.com/droidconuk" )));
					break;
				case R.id.about_twitter_hashtag: 
					startActivity( new Intent( Intent.ACTION_VIEW, Uri.parse( "http://mobile.twitter.com/searches?q=%23droidconuk" )));
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
