package com.google.firebase.inappmessaging.internal;

public class ProgramaticContextualTriggers {
    private Listener listener;

    public interface Listener {
        void onEventTrigger(String str);
    }

    public void setListener(Listener listener2) {
        this.listener = listener2;
    }

    public void removeListener(Listener listener2) {
        this.listener = null;
    }

    public void triggerEvent(String eventName) {
        Logging.logd("Programmatically trigger: " + eventName);
        this.listener.onEventTrigger(eventName);
    }
}
