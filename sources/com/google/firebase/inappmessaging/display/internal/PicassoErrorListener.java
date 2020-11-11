package com.google.firebase.inappmessaging.display.internal;

import android.net.Uri;
import com.google.firebase.inappmessaging.FirebaseInAppMessagingDisplayCallbacks;
import com.google.firebase.inappmessaging.model.InAppMessage;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import javax.inject.Inject;

public class PicassoErrorListener implements Picasso.Listener {
    private FirebaseInAppMessagingDisplayCallbacks displayCallbacks;
    private InAppMessage inAppMessage;

    @Inject
    PicassoErrorListener() {
    }

    public void setInAppMessage(InAppMessage inAppMessage2, FirebaseInAppMessagingDisplayCallbacks displayCallbacks2) {
        this.inAppMessage = inAppMessage2;
        this.displayCallbacks = displayCallbacks2;
    }

    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
        if (this.inAppMessage != null && this.displayCallbacks != null) {
            if (!(exception instanceof IOException) || !exception.getLocalizedMessage().contains("Failed to decode")) {
                this.displayCallbacks.displayErrorEncountered(FirebaseInAppMessagingDisplayCallbacks.InAppMessagingErrorReason.UNSPECIFIED_RENDER_ERROR);
            } else {
                this.displayCallbacks.displayErrorEncountered(FirebaseInAppMessagingDisplayCallbacks.InAppMessagingErrorReason.IMAGE_UNSUPPORTED_FORMAT);
            }
        }
    }
}
