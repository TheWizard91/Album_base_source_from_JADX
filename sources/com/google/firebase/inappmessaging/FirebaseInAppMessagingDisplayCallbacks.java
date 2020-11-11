package com.google.firebase.inappmessaging;

import com.google.android.gms.tasks.Task;
import com.google.firebase.inappmessaging.model.Action;

public interface FirebaseInAppMessagingDisplayCallbacks {

    public enum InAppMessagingDismissType {
        UNKNOWN_DISMISS_TYPE,
        AUTO,
        CLICK,
        SWIPE
    }

    public enum InAppMessagingErrorReason {
        UNSPECIFIED_RENDER_ERROR,
        IMAGE_FETCH_ERROR,
        IMAGE_DISPLAY_ERROR,
        IMAGE_UNSUPPORTED_FORMAT
    }

    Task<Void> displayErrorEncountered(InAppMessagingErrorReason inAppMessagingErrorReason);

    Task<Void> impressionDetected();

    Task<Void> messageClicked(Action action);

    Task<Void> messageDismissed(InAppMessagingDismissType inAppMessagingDismissType);
}
