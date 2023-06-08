package com.google.firebase.storage.network;

import android.net.Uri;
import com.google.firebase.FirebaseApp;

/* compiled from: com.google.firebase:firebase-storage@@19.1.1 */
public class DeleteNetworkRequest extends NetworkRequest {
    public DeleteNetworkRequest(Uri gsUri, FirebaseApp app) {
        super(gsUri, app);
    }

    /* access modifiers changed from: protected */
    public String getAction() {
        return "DELETE";
    }
}
