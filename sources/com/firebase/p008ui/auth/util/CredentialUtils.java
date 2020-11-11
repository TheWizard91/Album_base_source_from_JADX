package com.firebase.p008ui.auth.util;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.firebase.auth.FirebaseUser;

/* renamed from: com.firebase.ui.auth.util.CredentialUtils */
public class CredentialUtils {
    private static final String TAG = "CredentialUtils";

    private CredentialUtils() {
        throw new AssertionError("No instance for you!");
    }

    public static Credential buildCredential(FirebaseUser user, String password, String accountType) {
        String email = user.getEmail();
        String phone = user.getPhoneNumber();
        Uri profilePictureUri = user.getPhotoUrl() == null ? null : Uri.parse(user.getPhotoUrl().toString());
        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(phone)) {
            Log.w(TAG, "User (accountType=" + accountType + ") has no email or phone number, cannot build credential.");
            return null;
        } else if (password == null && accountType == null) {
            Log.w(TAG, "User has no accountType or password, cannot build credential.");
            return null;
        } else {
            Credential.Builder builder = new Credential.Builder(TextUtils.isEmpty(email) ? phone : email).setName(user.getDisplayName()).setProfilePictureUri(profilePictureUri);
            if (TextUtils.isEmpty(password)) {
                builder.setAccountType(accountType);
            } else {
                builder.setPassword(password);
            }
            return builder.build();
        }
    }

    public static Credential buildCredentialOrThrow(FirebaseUser user, String password, String accountType) {
        Credential credential = buildCredential(user, password, accountType);
        if (credential != null) {
            return credential;
        }
        throw new IllegalStateException("Unable to build credential");
    }
}
