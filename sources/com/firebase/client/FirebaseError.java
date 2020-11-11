package com.firebase.client;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class FirebaseError {
    public static final int AUTHENTICATION_PROVIDER_DISABLED = -12;
    public static final int DATA_STALE = -1;
    public static final int DENIED_BY_USER = -19;
    public static final int DISCONNECTED = -4;
    public static final int EMAIL_TAKEN = -18;
    public static final int EXPIRED_TOKEN = -6;
    public static final int INVALID_AUTH_ARGUMENTS = -21;
    public static final int INVALID_CONFIGURATION = -13;
    public static final int INVALID_CREDENTIALS = -20;
    public static final int INVALID_EMAIL = -15;
    public static final int INVALID_PASSWORD = -16;
    public static final int INVALID_PROVIDER = -14;
    public static final int INVALID_TOKEN = -7;
    public static final int LIMITS_EXCEEDED = -23;
    public static final int MAX_RETRIES = -8;
    public static final int NETWORK_ERROR = -24;
    public static final int OPERATION_FAILED = -2;
    public static final int OVERRIDDEN_BY_SET = -9;
    public static final int PERMISSION_DENIED = -3;
    public static final int PREEMPTED = -5;
    public static final int PROVIDER_ERROR = -22;
    public static final int UNAVAILABLE = -10;
    public static final int UNKNOWN_ERROR = -999;
    public static final int USER_CODE_EXCEPTION = -11;
    public static final int USER_DOES_NOT_EXIST = -17;
    public static final int WRITE_CANCELED = -25;
    private static final Map<String, Integer> errorCodes;
    private static final Map<Integer, String> errorReasons;
    private final int code;
    private final String details;
    private final String message;

    static {
        HashMap hashMap = new HashMap();
        errorReasons = hashMap;
        hashMap.put(-1, "The transaction needs to be run again with current data");
        hashMap.put(-2, "The server indicated that this operation failed");
        hashMap.put(-3, "This client does not have permission to perform this operation");
        hashMap.put(-4, "The operation had to be aborted due to a network disconnect");
        hashMap.put(-5, "The active or pending auth credentials were superseded by another call to auth");
        hashMap.put(-6, "The supplied auth token has expired");
        hashMap.put(-7, "The supplied auth token was invalid");
        hashMap.put(-8, "The transaction had too many retries");
        hashMap.put(-9, "The transaction was overridden by a subsequent set");
        hashMap.put(-10, "The service is unavailable");
        hashMap.put(-11, "User code called from the Firebase runloop threw an exception:\n");
        hashMap.put(-12, "The specified authentication type is not enabled for this Firebase.");
        hashMap.put(-13, "The specified authentication type is not properly configured for this Firebase.");
        hashMap.put(-14, "Invalid provider specified, please check application code.");
        hashMap.put(-15, "The specified email address is incorrect.");
        hashMap.put(-16, "The specified password is incorrect.");
        hashMap.put(-17, "The specified user does not exist.");
        hashMap.put(-18, "The specified email address is already in use.");
        hashMap.put(-19, "User denied authentication request.");
        hashMap.put(-20, "Invalid authentication credentials provided.");
        hashMap.put(-21, "Invalid authentication arguments provided.");
        hashMap.put(-22, "A third-party provider error occurred. See data for details.");
        hashMap.put(-23, "Limits exceeded.");
        hashMap.put(-24, "The operation could not be performed due to a network error");
        hashMap.put(-25, "The write was canceled by the user.");
        hashMap.put(-999, "An unknown error occurred");
        HashMap hashMap2 = new HashMap();
        errorCodes = hashMap2;
        hashMap2.put("datastale", -1);
        hashMap2.put("failure", -2);
        hashMap2.put("permission_denied", -3);
        hashMap2.put("disconnected", -4);
        hashMap2.put("preempted", -5);
        hashMap2.put("expired_token", -6);
        hashMap2.put("invalid_token", -7);
        hashMap2.put("maxretries", -8);
        hashMap2.put("overriddenbyset", -9);
        hashMap2.put("unavailable", -10);
        hashMap2.put("authentication_disabled", -12);
        hashMap2.put("invalid_configuration", -13);
        hashMap2.put("invalid_provider", -14);
        hashMap2.put("invalid_email", -15);
        hashMap2.put("invalid_password", -16);
        hashMap2.put("invalid_user", -17);
        hashMap2.put("email_taken", -18);
        hashMap2.put("user_denied", -19);
        hashMap2.put("invalid_credentials", -20);
        hashMap2.put("invalid_arguments", -21);
        hashMap2.put("provider_error", -22);
        hashMap2.put("limits_exceeded", -23);
        hashMap2.put("network_error", -24);
        hashMap2.put("write_canceled", -25);
    }

    public static FirebaseError fromStatus(String status) {
        return fromStatus(status, (String) null);
    }

    public static FirebaseError fromStatus(String status, String reason) {
        return fromStatus(status, reason, (String) null);
    }

    public static FirebaseError fromCode(int code2) {
        Map<Integer, String> map = errorReasons;
        if (map.containsKey(Integer.valueOf(code2))) {
            return new FirebaseError(code2, map.get(Integer.valueOf(code2)), (String) null);
        }
        throw new IllegalArgumentException("Invalid Firebase error code: " + code2);
    }

    public static FirebaseError fromStatus(String status, String reason, String details2) {
        Integer code2 = errorCodes.get(status.toLowerCase());
        if (code2 == null) {
            code2 = -999;
        }
        return new FirebaseError(code2.intValue(), reason == null ? errorReasons.get(code2) : reason, details2);
    }

    public static FirebaseError fromException(Throwable e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return new FirebaseError(-11, errorReasons.get(-11) + stringWriter.toString());
    }

    public FirebaseError(int code2, String message2) {
        this(code2, message2, (String) null);
    }

    public FirebaseError(int code2, String message2, String details2) {
        this.code = code2;
        this.message = message2;
        this.details = details2 == null ? "" : details2;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getDetails() {
        return this.details;
    }

    public String toString() {
        return "FirebaseError: " + this.message;
    }

    public FirebaseException toException() {
        return new FirebaseException("Firebase error: " + this.message);
    }
}
