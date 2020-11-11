package com.google.firebase.inappmessaging.internal;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import com.google.firebase.FirebaseApp;
import javax.inject.Inject;

public class SharedPreferencesUtils {
    static final String PREFERENCES_PACKAGE_NAME = "com.google.firebase.inappmessaging";
    private final FirebaseApp firebaseApp;

    @Inject
    public SharedPreferencesUtils(FirebaseApp firebaseApp2) {
        this.firebaseApp = firebaseApp2;
    }

    public void setBooleanPreference(String preference, boolean value) {
        SharedPreferences.Editor preferencesEditor = ((Application) this.firebaseApp.getApplicationContext()).getSharedPreferences("com.google.firebase.inappmessaging", 0).edit();
        preferencesEditor.putBoolean(preference, value);
        preferencesEditor.apply();
    }

    public void clearPreference(String preference) {
        SharedPreferences.Editor preferencesEditor = ((Application) this.firebaseApp.getApplicationContext()).getSharedPreferences("com.google.firebase.inappmessaging", 0).edit();
        preferencesEditor.remove(preference);
        preferencesEditor.apply();
    }

    public boolean getAndSetBooleanPreference(String preference, boolean defaultValue) {
        SharedPreferences preferences = ((Application) this.firebaseApp.getApplicationContext()).getSharedPreferences("com.google.firebase.inappmessaging", 0);
        if (preferences.contains(preference)) {
            return preferences.getBoolean(preference, defaultValue);
        }
        setBooleanPreference(preference, defaultValue);
        return defaultValue;
    }

    public boolean getBooleanPreference(String preference, boolean defaultValue) {
        SharedPreferences preferences = ((Application) this.firebaseApp.getApplicationContext()).getSharedPreferences("com.google.firebase.inappmessaging", 0);
        if (preferences.contains(preference)) {
            return preferences.getBoolean(preference, defaultValue);
        }
        return defaultValue;
    }

    public boolean isPreferenceSet(String preference) {
        return ((Application) this.firebaseApp.getApplicationContext()).getSharedPreferences("com.google.firebase.inappmessaging", 0).contains(preference);
    }

    public boolean isManifestSet(String preference) {
        ApplicationInfo applicationInfo;
        Application application = (Application) this.firebaseApp.getApplicationContext();
        try {
            PackageManager packageManager = application.getPackageManager();
            if (packageManager == null || (applicationInfo = packageManager.getApplicationInfo(application.getPackageName(), 128)) == null || applicationInfo.metaData == null || !applicationInfo.metaData.containsKey(preference)) {
                return false;
            }
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return false;
    }

    public boolean getBooleanManifestValue(String preference, boolean defaultValue) {
        ApplicationInfo applicationInfo;
        Application application = (Application) this.firebaseApp.getApplicationContext();
        try {
            PackageManager packageManager = application.getPackageManager();
            if (!(packageManager == null || (applicationInfo = packageManager.getApplicationInfo(application.getPackageName(), 128)) == null || applicationInfo.metaData == null || !applicationInfo.metaData.containsKey(preference))) {
                return applicationInfo.metaData.getBoolean(preference);
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        return defaultValue;
    }
}
