package de.czyrux.store.tracking;


import java.util.HashMap;
import java.util.Map;

public class TrackingEvent {
    private final Map<String,String> eventParams;

    public TrackingEvent() {
        this.eventParams = new HashMap<>();
    }

    public TrackingEvent put(String key, String value){
        eventParams.put(key, value);
        return this;
    }

    public Map<String, String> getEventParams() {
        return eventParams;
    }
}
