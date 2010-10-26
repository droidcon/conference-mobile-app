package novoda.droidcon2010uk.ui;

import novoda.droidcon2010uk.util.UIUtils;

import novoda.droidconuk2010.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class RummbleActivity extends Activity {

	public static final String TAG = RummbleActivity.class.getSimpleName(); 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rummble);
	}

    
    /** Handle "home" title-bar action. */
    public void onRummbleButtonClick(View v) {
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.mobile.rummble");
        if (intent == null) {
        	intent = new Intent(null, Uri.parse("market://search?q=pname:com.mobile.rummble"));
        }
        
        startActivity(intent);
        
    }
    
    /** Handle "search" title-bar action. */
    public void onSearchClick(View v) {
    	UIUtils.goSearch(this);
    }
    
    /** Handle "home" title-bar action. */
    public void onHomeClick(View v) {
    	UIUtils.goHome(this);
    }
    
	/** Handle "share" title-bar action. */
	public void onShareClick(View v) {
		final String shareString = getString(R.string.share_template, "inApp Rummbling", "#rummble");

		final Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, shareString);

		startActivity(Intent.createChooser(intent,
				getText(R.string.title_share)));
	}
	
}
