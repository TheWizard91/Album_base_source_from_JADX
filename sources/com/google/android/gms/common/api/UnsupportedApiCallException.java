package com.google.android.gms.common.api;

import com.google.android.gms.common.Feature;

/* compiled from: com.google.android.gms:play-services-basement@@17.2.1 */
public final class UnsupportedApiCallException extends UnsupportedOperationException {
    private final Feature zzbe;

    public UnsupportedApiCallException(Feature feature) {
        this.zzbe = feature;
    }

    public final String getMessage() {
        String valueOf = String.valueOf(this.zzbe);
        return new StringBuilder(String.valueOf(valueOf).length() + 8).append("Missing ").append(valueOf).toString();
    }
}
