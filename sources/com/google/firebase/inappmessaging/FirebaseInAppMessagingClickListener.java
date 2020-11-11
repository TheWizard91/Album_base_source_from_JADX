package com.google.firebase.inappmessaging;

import com.google.firebase.inappmessaging.model.Action;
import com.google.firebase.inappmessaging.model.InAppMessage;

public interface FirebaseInAppMessagingClickListener {
    void messageClicked(InAppMessage inAppMessage, Action action);
}
