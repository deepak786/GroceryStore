package de.czyrux.store.tracking;

import com.flurry.android.FlurryAgent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sergii Zhuk
 *         Date: 04.12.2016
 *         Time: 16:54
 */


class FlurryTrackingHandler implements TrackingHandler {

    private static final String KEY_SCREEN_NAME = "scree_name";
    private static final String EVENT_GENERIC = "App tracking event";

    @Override
    public void trackScreenView(TrackingScreenView screenView) {

        Map<String,String> eventParams = new HashMap<>();
        eventParams.put(KEY_SCREEN_NAME, screenView.getScreenName());
        trackEvent(eventParams);
        FlurryAgent.onPageView();
    }

    @Override
    public void trackEvent(Map<String, String> params) {
        // WARN: this is a demo code, probably you need custom keys mapping for production
        FlurryAgent.logEvent(EVENT_GENERIC, params, false);
    }
}
