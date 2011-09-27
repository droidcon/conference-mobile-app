package novoda.droidcon.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.funkyandroid.droidcon2011.R;
import com.google.android.apps.iosched.util.ActivityHelper;
import com.google.android.apps.iosched.util.UIUtils;

public class AboutFragment extends Fragment {

	public static final String TAG = AboutActivity.class.getSimpleName(); 

	private ActivityHelper helper;
	
	/**
     * The URI for reigstrations
     */
    
    private Uri REGISTER_URI = Uri.parse("http://m.uk.droidcon.com/register");

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}


	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_about, null);

		((TextView) view.findViewById(R.id.about_buynow)).setOnClickListener(clickListener);
		((ImageView) view.findViewById(R.id.about_address_icon)).setOnClickListener(clickListener);
		((TextView) view.findViewById(R.id.about_address_txt)).setOnClickListener(clickListener);
		((TextView) view.findViewById(R.id.about_twitter)).setOnClickListener(clickListener);
		((TextView) view.findViewById(R.id.about_twitter_hashtag)).setOnClickListener(clickListener);

		return view;
	}

	OnClickListener clickListener = new OnClickListener() {
		public void onClick(View v) {
			Log.i(TAG, "ID is: " +  v.getId() );
			switch(v.getId()){
				case R.id.about_btn_buynow:
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
	
	/** Handle "share" title-bar action. */
	public void onShareClick(View v) {
		final String shareString = getString(R.string.share_template, "the where abouts", "in the app");

		final Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, shareString);

		startActivity(Intent.createChooser(intent,
				getText(R.string.title_share)));
	}
	
	/** Handle "search" title-bar action. */
	public void onSearchClick(View v) {
		helper.goSearch();
	}
	

    /** Handle "home" title-bar action. */
    public void onHomeClick(View v) {
    	helper.goHome();
    }
}
