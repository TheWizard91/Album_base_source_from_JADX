package com.google.firebase.inappmessaging;

import com.google.firebase.inappmessaging.model.InAppMessage;

public interface FirebaseInAppMessagingDisplay {
    void displayMessage(InAppMessage inAppMessage, FirebaseInAppMessagingDisplayCallbacks firebaseInAppMessagingDisplayCallbacks);
}
