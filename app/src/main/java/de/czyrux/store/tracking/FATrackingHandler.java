package de.czyrux.store.tracking;

import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Map;


class FATrackingHandler implements TrackingHandler {

    private final static String KEY_SCREEN_VIEW_NAME = "CUSTOM_PARAM_SCREEN_NAME";
    private final static String PARAM_SCREEN_VIEW = "CUSTOM_SCREENVIEW";
    private final static String PARAM_EVENT_NAME = "CUSTOM_EVENT_ID";

    private FirebaseAnalytics firebaseAnalytics;

    FATrackingHandler(FirebaseAnalytics firebaseAnalytics){
        this.firebaseAnalytics = firebaseAnalytics;
    }

    @Override
    public void trackScreenView(TrackingScreenView screenName) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_SCREEN_VIEW_NAME, screenName.getScreenName());
        firebaseAnalytics.logEvent(PARAM_SCREEN_VIEW, bundle);
    }

    @Override
    public void trackEvent(Map<String, String> params) {
        Bundle bundle = new Bundle();
        // WARN: this is a demo code, probably you need custom keys for production
        for(Map.Entry<String, String> entry : params.entrySet()){
            bundle.putString(entry.getKey(), entry.getValue());
        }
        firebaseAnalytics.logEvent(PARAM_EVENT_NAME, bundle);
    }
}
