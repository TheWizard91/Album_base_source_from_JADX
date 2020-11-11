package com.google.firebase.inappmessaging;

public class FirebaseInAppMessagingContextualTrigger {
    private final String triggerName;

    public FirebaseInAppMessagingContextualTrigger(String triggerName2) {
        this.triggerName = triggerName2;
    }

    public String getTriggerName() {
        return this.triggerName;
    }
}
