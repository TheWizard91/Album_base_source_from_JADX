package com.google.firebase.messaging;

import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.google.firebase.messaging.Constants;
import java.util.Arrays;
import java.util.MissingFormatArgumentException;
import org.json.JSONArray;
import org.json.JSONException;

/* compiled from: com.google.firebase:firebase-messaging@@20.2.4 */
public class NotificationParams {
    private final Bundle data;

    public NotificationParams(Bundle bundle) {
        if (bundle != null) {
            this.data = new Bundle(bundle);
            return;
        }
        throw new NullPointerException("data");
    }

    /* access modifiers changed from: package-private */
    public Integer getNotificationCount() {
        Integer integer = getInteger(Constants.MessageNotificationKeys.NOTIFICATION_COUNT);
        if (integer == null) {
            return null;
        }
        if (integer.intValue() >= 0) {
            return integer;
        }
        String valueOf = String.valueOf(integer);
        Log.w(Constants.TAG, new StringBuilder(String.valueOf(valueOf).length() + 67).append("notificationCount is invalid: ").append(valueOf).append(". Skipping setting notificationCount.").toString());
        return null;
    }

    /* access modifiers changed from: package-private */
    public Integer getNotificationPriority() {
        Integer integer = getInteger(Constants.MessageNotificationKeys.NOTIFICATION_PRIORITY);
        if (integer == null) {
            return null;
        }
        if (integer.intValue() >= -2 && integer.intValue() <= 2) {
            return integer;
        }
        String valueOf = String.valueOf(integer);
        Log.w(Constants.TAG, new StringBuilder(String.valueOf(valueOf).length() + 72).append("notificationPriority is invalid ").append(valueOf).append(". Skipping setting notificationPriority.").toString());
        return null;
    }

    /* access modifiers changed from: package-private */
    public Integer getVisibility() {
        Integer integer = getInteger(Constants.MessageNotificationKeys.VISIBILITY);
        if (integer == null) {
            return null;
        }
        if (integer.intValue() >= -1 && integer.intValue() <= 1) {
            return integer;
        }
        String valueOf = String.valueOf(integer);
        Log.w("NotificationParams", new StringBuilder(String.valueOf(valueOf).length() + 53).append("visibility is invalid: ").append(valueOf).append(". Skipping setting visibility.").toString());
        return null;
    }

    public String getString(String str) {
        return this.data.getString(normalizePrefix(str));
    }

    private String normalizePrefix(String str) {
        if (!this.data.containsKey(str) && str.startsWith(Constants.MessageNotificationKeys.NOTIFICATION_PREFIX)) {
            String keyWithOldPrefix = keyWithOldPrefix(str);
            if (this.data.containsKey(keyWithOldPrefix)) {
                return keyWithOldPrefix;
            }
        }
        return str;
    }

    public boolean getBoolean(String str) {
        String string = getString(str);
        return "1".equals(string) || Boolean.parseBoolean(string);
    }

    public Integer getInteger(String str) {
        String string = getString(str);
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        try {
            return Integer.valueOf(Integer.parseInt(string));
        } catch (NumberFormatException e) {
            String userFriendlyKey = userFriendlyKey(str);
            Log.w("NotificationParams", new StringBuilder(String.valueOf(userFriendlyKey).length() + 38 + String.valueOf(string).length()).append("Couldn't parse value of ").append(userFriendlyKey).append("(").append(string).append(") into an int").toString());
            return null;
        }
    }

    public Long getLong(String str) {
        String string = getString(str);
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        try {
            return Long.valueOf(Long.parseLong(string));
        } catch (NumberFormatException e) {
            String userFriendlyKey = userFriendlyKey(str);
            Log.w("NotificationParams", new StringBuilder(String.valueOf(userFriendlyKey).length() + 38 + String.valueOf(string).length()).append("Couldn't parse value of ").append(userFriendlyKey).append("(").append(string).append(") into a long").toString());
            return null;
        }
    }

    public String getLocalizationResourceForKey(String str) {
        String valueOf = String.valueOf(str);
        String valueOf2 = String.valueOf(Constants.MessageNotificationKeys.TEXT_RESOURCE_SUFFIX);
        return getString(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
    }

    public Object[] getLocalizationArgsForKey(String str) {
        String valueOf = String.valueOf(str);
        String valueOf2 = String.valueOf(Constants.MessageNotificationKeys.TEXT_ARGS_SUFFIX);
        JSONArray jSONArray = getJSONArray(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
        if (jSONArray == null) {
            return null;
        }
        int length = jSONArray.length();
        String[] strArr = new String[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = jSONArray.optString(i);
        }
        return strArr;
    }

    public JSONArray getJSONArray(String str) {
        String string = getString(str);
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        try {
            return new JSONArray(string);
        } catch (JSONException e) {
            String userFriendlyKey = userFriendlyKey(str);
            Log.w("NotificationParams", new StringBuilder(String.valueOf(userFriendlyKey).length() + 50 + String.valueOf(string).length()).append("Malformed JSON for key ").append(userFriendlyKey).append(": ").append(string).append(", falling back to default").toString());
            return null;
        }
    }

    private static String userFriendlyKey(String str) {
        if (str.startsWith(Constants.MessageNotificationKeys.NOTIFICATION_PREFIX)) {
            return str.substring(6);
        }
        return str;
    }

    public Uri getLink() {
        String string = getString(Constants.MessageNotificationKeys.LINK_ANDROID);
        if (TextUtils.isEmpty(string)) {
            string = getString(Constants.MessageNotificationKeys.LINK);
        }
        if (!TextUtils.isEmpty(string)) {
            return Uri.parse(string);
        }
        return null;
    }

    public String getSoundResourceName() {
        String string = getString(Constants.MessageNotificationKeys.SOUND_2);
        if (TextUtils.isEmpty(string)) {
            return getString(Constants.MessageNotificationKeys.SOUND);
        }
        return string;
    }

    public long[] getVibrateTimings() {
        JSONArray jSONArray = getJSONArray(Constants.MessageNotificationKeys.VIBRATE_TIMINGS);
        if (jSONArray == null) {
            return null;
        }
        try {
            if (jSONArray.length() > 1) {
                int length = jSONArray.length();
                long[] jArr = new long[length];
                for (int i = 0; i < length; i++) {
                    jArr[i] = jSONArray.optLong(i);
                }
                return jArr;
            }
            throw new JSONException("vibrateTimings have invalid length");
        } catch (NumberFormatException | JSONException e) {
            String valueOf = String.valueOf(jSONArray);
            Log.w("NotificationParams", new StringBuilder(String.valueOf(valueOf).length() + 74).append("User defined vibrateTimings is invalid: ").append(valueOf).append(". Skipping setting vibrateTimings.").toString());
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public int[] getLightSettings() {
        JSONArray jSONArray = getJSONArray(Constants.MessageNotificationKeys.LIGHT_SETTINGS);
        if (jSONArray == null) {
            return null;
        }
        int[] iArr = new int[3];
        try {
            if (jSONArray.length() == 3) {
                iArr[0] = getLightColor(jSONArray.optString(0));
                iArr[1] = jSONArray.optInt(1);
                iArr[2] = jSONArray.optInt(2);
                return iArr;
            }
            throw new JSONException("lightSettings don't have all three fields");
        } catch (JSONException e) {
            String valueOf = String.valueOf(jSONArray);
            Log.w("NotificationParams", new StringBuilder(String.valueOf(valueOf).length() + 58).append("LightSettings is invalid: ").append(valueOf).append(". Skipping setting LightSettings").toString());
            return null;
        } catch (IllegalArgumentException e2) {
            String valueOf2 = String.valueOf(jSONArray);
            String message = e2.getMessage();
            Log.w("NotificationParams", new StringBuilder(String.valueOf(valueOf2).length() + 60 + String.valueOf(message).length()).append("LightSettings is invalid: ").append(valueOf2).append(". ").append(message).append(". Skipping setting LightSettings").toString());
            return null;
        }
    }

    public Bundle paramsWithReservedKeysRemoved() {
        Bundle bundle = new Bundle(this.data);
        for (String str : this.data.keySet()) {
            if (isReservedKey(str)) {
                bundle.remove(str);
            }
        }
        return bundle;
    }

    public Bundle paramsForAnalyticsIntent() {
        Bundle bundle = new Bundle(this.data);
        for (String str : this.data.keySet()) {
            if (!isAnalyticsKey(str)) {
                bundle.remove(str);
            }
        }
        return bundle;
    }

    public String getLocalizedString(Resources resources, String str, String str2) {
        String localizationResourceForKey = getLocalizationResourceForKey(str2);
        if (TextUtils.isEmpty(localizationResourceForKey)) {
            return null;
        }
        int identifier = resources.getIdentifier(localizationResourceForKey, "string", str);
        if (identifier == 0) {
            String valueOf = String.valueOf(str2);
            String valueOf2 = String.valueOf(Constants.MessageNotificationKeys.TEXT_RESOURCE_SUFFIX);
            String userFriendlyKey = userFriendlyKey(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
            Log.w("NotificationParams", new StringBuilder(String.valueOf(userFriendlyKey).length() + 49 + String.valueOf(str2).length()).append(userFriendlyKey).append(" resource not found: ").append(str2).append(" Default value will be used.").toString());
            return null;
        }
        Object[] localizationArgsForKey = getLocalizationArgsForKey(str2);
        if (localizationArgsForKey == null) {
            return resources.getString(identifier);
        }
        try {
            return resources.getString(identifier, localizationArgsForKey);
        } catch (MissingFormatArgumentException e) {
            String userFriendlyKey2 = userFriendlyKey(str2);
            String arrays = Arrays.toString(localizationArgsForKey);
            Log.w("NotificationParams", new StringBuilder(String.valueOf(userFriendlyKey2).length() + 58 + String.valueOf(arrays).length()).append("Missing format argument for ").append(userFriendlyKey2).append(": ").append(arrays).append(" Default value will be used.").toString(), e);
            return null;
        }
    }

    public String getPossiblyLocalizedString(Resources resources, String str, String str2) {
        String string = getString(str2);
        if (!TextUtils.isEmpty(string)) {
            return string;
        }
        return getLocalizedString(resources, str, str2);
    }

    public boolean hasImage() {
        return !TextUtils.isEmpty(getString(Constants.MessageNotificationKeys.IMAGE_URL));
    }

    public String getNotificationChannelId() {
        return getString(Constants.MessageNotificationKeys.CHANNEL);
    }

    private static boolean isAnalyticsKey(String str) {
        return str.startsWith(Constants.AnalyticsKeys.PREFIX) || str.equals(Constants.MessagePayloadKeys.FROM);
    }

    private static boolean isReservedKey(String str) {
        return str.startsWith(Constants.MessagePayloadKeys.RESERVED_CLIENT_LIB_PREFIX) || str.startsWith(Constants.MessageNotificationKeys.NOTIFICATION_PREFIX) || str.startsWith(Constants.MessageNotificationKeys.NOTIFICATION_PREFIX_OLD);
    }

    private static int getLightColor(String str) {
        int parseColor = Color.parseColor(str);
        if (parseColor != -16777216) {
            return parseColor;
        }
        throw new IllegalArgumentException("Transparent color is invalid");
    }

    public boolean isNotification() {
        return getBoolean(Constants.MessageNotificationKeys.ENABLE_NOTIFICATION);
    }

    public static boolean isNotification(Bundle bundle) {
        return "1".equals(bundle.getString(Constants.MessageNotificationKeys.ENABLE_NOTIFICATION)) || "1".equals(bundle.getString(keyWithOldPrefix(Constants.MessageNotificationKeys.ENABLE_NOTIFICATION)));
    }

    private static String keyWithOldPrefix(String str) {
        if (!str.startsWith(Constants.MessageNotificationKeys.NOTIFICATION_PREFIX)) {
            return str;
        }
        return str.replace(Constants.MessageNotificationKeys.NOTIFICATION_PREFIX, Constants.MessageNotificationKeys.NOTIFICATION_PREFIX_OLD);
    }
}
