package com.google.firebase.inappmessaging;

import com.google.firebase.inappmessaging.model.InAppMessage;

public interface FirebaseInAppMessagingImpressionListener {
    void impressionDetected(InAppMessage inAppMessage);
}
