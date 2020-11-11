package com.google.firebase.inappmessaging.internal;

import com.google.firebase.installations.InstallationTokenResult;

final class AutoValue_InstallationIdResult extends InstallationIdResult {
    private final String installationId;
    private final InstallationTokenResult installationTokenResult;

    AutoValue_InstallationIdResult(String installationId2, InstallationTokenResult installationTokenResult2) {
        if (installationId2 != null) {
            this.installationId = installationId2;
            if (installationTokenResult2 != null) {
                this.installationTokenResult = installationTokenResult2;
                return;
            }
            throw new NullPointerException("Null installationTokenResult");
        }
        throw new NullPointerException("Null installationId");
    }

    /* access modifiers changed from: package-private */
    public String installationId() {
        return this.installationId;
    }

    /* access modifiers changed from: package-private */
    public InstallationTokenResult installationTokenResult() {
        return this.installationTokenResult;
    }

    public String toString() {
        return "InstallationIdResult{installationId=" + this.installationId + ", installationTokenResult=" + this.installationTokenResult + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof InstallationIdResult)) {
            return false;
        }
        InstallationIdResult that = (InstallationIdResult) o;
        if (!this.installationId.equals(that.installationId()) || !this.installationTokenResult.equals(that.installationTokenResult())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((1 * 1000003) ^ this.installationId.hashCode()) * 1000003) ^ this.installationTokenResult.hashCode();
    }
}
