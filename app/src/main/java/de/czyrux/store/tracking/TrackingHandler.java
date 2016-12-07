package de.czyrux.store.tracking;

import java.util.Map;

/**
 * @author Sergii Zhuk
 *         Date: 04.12.2016
 *         Time: 16:52
 */


interface TrackingHandler {

    void trackScreenView(TrackingScreenView screenName);

    void trackEvent(Map<String,String> params);
}
