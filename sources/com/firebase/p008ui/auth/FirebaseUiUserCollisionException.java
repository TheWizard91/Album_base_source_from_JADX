package com.firebase.p008ui.auth;

import com.google.firebase.auth.AuthCredential;

/* renamed from: com.firebase.ui.auth.FirebaseUiUserCollisionException */
public class FirebaseUiUserCollisionException extends Exception {
    private final AuthCredential mCredential;
    private final String mEmail;
    private final int mErrorCode;
    private final String mProviderId;

    public FirebaseUiUserCollisionException(int code, String message, String providerId, String email, AuthCredential credential) {
        super(message);
        this.mErrorCode = code;
        this.mProviderId = providerId;
        this.mEmail = email;
        this.mCredential = credential;
    }

    public String getProviderId() {
        return this.mProviderId;
    }

    public String getEmail() {
        return this.mEmail;
    }

    public AuthCredential getCredential() {
        return this.mCredential;
    }

    public final int getErrorCode() {
        return this.mErrorCode;
    }
}
