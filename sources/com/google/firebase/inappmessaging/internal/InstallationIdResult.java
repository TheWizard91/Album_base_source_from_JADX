package com.google.firebase.inappmessaging.internal;

import com.google.firebase.installations.InstallationTokenResult;

public abstract class InstallationIdResult {
    /* access modifiers changed from: package-private */
    public abstract String installationId();

    /* access modifiers changed from: package-private */
    public abstract InstallationTokenResult installationTokenResult();

    public static InstallationIdResult create(String installationId, InstallationTokenResult installationTokenResult) {
        return new AutoValue_InstallationIdResult(installationId, installationTokenResult);
    }
}
