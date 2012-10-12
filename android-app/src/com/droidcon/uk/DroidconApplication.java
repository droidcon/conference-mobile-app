package com.droidcon.uk;

import java.util.TimeZone;

import com.google.android.apps.iosched.util.ParserUtils;
import com.google.android.apps.iosched.util.UIUtils;
import com.droidcon.uk.R;

import android.app.Application;

public class DroidconApplication extends Application {
	@Override
	public void onCreate() {	
		super.onCreate();
		
		UIUtils.CONFERENCE_TIME_ZONE = TimeZone.getTimeZone("Europe/London");
		
		UIUtils.CONFERENCE_START_MILLIS = ParserUtils.parseTime(
	            "2012-10-25T09:30:00.000+01:00");
		UIUtils.CONFERENCE_END_MILLIS = ParserUtils.parseTime(
	            "2012-10-27T09:30:00.000+01:00");
		UIUtils.CONFERENCE_HASHTAG ="#droidconUK";
		
	}
}
