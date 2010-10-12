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

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebView;

import com.google.android.apps.iosched.provider.ScheduleContract.Rooms;
import com.google.android.apps.iosched.provider.ScheduleContract.Sessions;
import com.google.android.apps.iosched.provider.ScheduleContract.Tracks;
import com.google.android.apps.iosched.util.ParserUtils;

/**
 * Shows a {@link WebView} with a map of the conference venue. 
 */
public class MapActivity extends WebViewActivity {
    private static final String TAG = "MapActivity";

    /**
     * When specified, will automatically point the map to the requested room.
     */
    public static final String EXTRA_ROOM = "com.google.android.iosched.extra.ROOM";

    public static final String OFFICE_HOURS_ROOM_ID = "officehours";

    private static final String MAP_JSI_NAME = "MAP_CONTAINER";
    private static final String MAP_URL = "http://maps.google.co.uk/maps/ms?ie=UTF8&hl=en&num=10&msa=0&msid=111268683924396905734.000490c793a5b883cd911&ll=51.533516,-0.105298&spn=0.001949,0.004817&output=embed";

	public boolean mMapInitialized;


    public void loadData(WebView webView){
        webView.loadUrl(MAP_URL);
        webView.addJavascriptInterface(new MapJsiImpl(), MAP_JSI_NAME);

    }


    /**
     * I/O Conference Map JavaScript interface.
     */
    private interface MapJsi {
        void openContentInfo(String test);
        void onMapReady();
    }

    private class MapJsiImpl implements MapJsi {
        public void openContentInfo(String roomId) {
            final String possibleTrackId = ParserUtils.translateTrackIdAlias(roomId);
            if (OFFICE_HOURS_ROOM_ID.equals(roomId)) {
                // The office hours room was requested.
                Uri officeHoursUri = Sessions.buildSearchUri("office hours");
                Intent officeHoursIntent = new Intent(Intent.ACTION_VIEW, officeHoursUri);
                startActivity(officeHoursIntent);
            } else if (ParserUtils.LOCAL_TRACK_IDS.contains(possibleTrackId)) {
                // This is a track; open up the sandbox for the track, since room IDs that are
                // track IDs are sandbox areas in the map.
                Uri trackVendorsUri = Tracks.buildTrackUri(possibleTrackId);
                Intent trackVendorsIntent = new Intent(Intent.ACTION_VIEW, trackVendorsUri);
                trackVendorsIntent.putExtra(TrackDetailActivity.EXTRA_FOCUS_TAG,
                        TrackDetailActivity.TAG_VENDORS);
                startActivity(trackVendorsIntent);
            } else {
                Uri roomUri = Rooms.buildSessionsDirUri(roomId);
                Intent roomIntent = new Intent(Intent.ACTION_VIEW, roomUri);
                startActivity(roomIntent);
            }
        }

        public void onMapReady() {
            if (Log.isLoggable(TAG, Log.DEBUG)) {
                Log.d(TAG, "onMapReady");
            }

            String showRoomId = null;
            if (!mMapInitialized && getIntent().hasExtra(EXTRA_ROOM)) {
                showRoomId = getIntent().getStringExtra(EXTRA_ROOM);
            }

            if (showRoomId != null) {
                runJs("googleIo.showLocationById('" +
                        escapeJsString(showRoomId) + "');");
            }

            mMapInitialized = true;
        }
    }
}
