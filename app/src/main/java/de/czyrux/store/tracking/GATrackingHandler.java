package de.czyrux.store.tracking;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.Map;

class GATrackingHandler implements TrackingHandler {
    private Tracker tracker;

    GATrackingHandler(Tracker tracker) {
        this.tracker = tracker;
    }

    @Override
    public void trackScreenView(TrackingScreenView screenView) {
        tracker.setScreenName(screenView.getScreenName());
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void trackEvent(Map<String, String> params) {
        // WARN: this is a demo code, probably you need custom keys mapping for production
        String category = params.get(TrackingDispatcher.KEY_CATEGORY);
        String action = params.get(TrackingDispatcher.KEY_ACTION);
        String label = params.get(TrackingDispatcher.KEY_LABEL);
        HitBuilders.EventBuilder eventBuilder = new HitBuilders.EventBuilder(category, action);
        if (label != null) {
            eventBuilder.setLabel(label);
        }
        tracker.send(eventBuilder.build());
    }
}
