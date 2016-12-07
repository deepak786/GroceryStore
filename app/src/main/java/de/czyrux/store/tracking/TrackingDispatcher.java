package de.czyrux.store.tracking;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.analytics.Tracker;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrackingDispatcher {

    // please setup using your Flurry account
    public final static String FLURRY_APIKEY =  "HIDDEN";

    public final static String KEY_CATEGORY = "category";
    public final static String KEY_ACTION = "action";
    public final static String KEY_LABEL = "label";

    private String mLastScreenName;

    private List<TrackingHandler> trackingHandlers;

    public TrackingDispatcher(Tracker tracker, FirebaseAnalytics firebaseAnalytics) {
        this.trackingHandlers = new ArrayList<>();
        trackingHandlers.add(new FATrackingHandler(firebaseAnalytics));
        trackingHandlers.add(new GATrackingHandler(tracker));
        trackingHandlers.add(new FlurryTrackingHandler());
    }

    public void sendEvent(@NonNull TrackingEvent trackingEvent) {
        Map<String, String> paramsMap = new HashMap<>(trackingEvent.getEventParams());
        for (TrackingHandler trackingHandler : trackingHandlers) {
            trackingHandler.trackEvent(paramsMap);
        }
    }

    public void sendScreenView(@NonNull TrackingScreenView screenView) {
        if (!screenView.getScreenName().equals(mLastScreenName)) {
            mLastScreenName = screenView.getScreenName();
            for (TrackingHandler trackingHandler : trackingHandlers) {
                trackingHandler.trackScreenView(screenView);
            }
        }
    }
}
