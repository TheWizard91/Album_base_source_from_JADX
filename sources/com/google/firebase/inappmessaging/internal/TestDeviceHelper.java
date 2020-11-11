package com.google.firebase.inappmessaging.internal;

import com.google.internal.firebase.inappmessaging.p015v1.CampaignProto;
import com.google.internal.firebase.inappmessaging.p015v1.sdkserving.FetchEligibleCampaignsResponse;
import javax.inject.Inject;

public class TestDeviceHelper {
    static final String FRESH_INSTALL_PREFERENCES = "fresh_install";
    static final int MAX_FETCH_COUNT = 5;
    static final String TEST_DEVICE_PREFERENCES = "test_device";
    private int fetchCount = 0;
    private boolean isFreshInstall;
    private boolean isTestDevice;
    private final SharedPreferencesUtils sharedPreferencesUtils;

    @Inject
    public TestDeviceHelper(SharedPreferencesUtils sharedPreferencesUtils2) {
        this.sharedPreferencesUtils = sharedPreferencesUtils2;
        this.isFreshInstall = readFreshInstallStatusFromPreferences();
        this.isTestDevice = readTestDeviceStatusFromPreferences();
    }

    public boolean isDeviceInTestMode() {
        return this.isTestDevice;
    }

    public boolean isAppInstallFresh() {
        return this.isFreshInstall;
    }

    public void processCampaignFetch(FetchEligibleCampaignsResponse response) {
        if (!this.isTestDevice) {
            updateFreshInstallStatus();
            for (CampaignProto.ThickContent message : response.getMessagesList()) {
                if (message.getIsTestCampaign()) {
                    setTestDeviceStatus(true);
                    Logging.logi("Setting this device as a test device");
                    return;
                }
            }
        }
    }

    private void updateFreshInstallStatus() {
        if (this.isFreshInstall) {
            int i = this.fetchCount + 1;
            this.fetchCount = i;
            if (i >= 5) {
                setFreshInstallStatus(false);
            }
        }
    }

    private void setTestDeviceStatus(boolean isEnabled) {
        this.isTestDevice = isEnabled;
        this.sharedPreferencesUtils.setBooleanPreference(TEST_DEVICE_PREFERENCES, isEnabled);
    }

    private void setFreshInstallStatus(boolean isEnabled) {
        this.isFreshInstall = isEnabled;
        this.sharedPreferencesUtils.setBooleanPreference(FRESH_INSTALL_PREFERENCES, isEnabled);
    }

    private boolean readTestDeviceStatusFromPreferences() {
        return this.sharedPreferencesUtils.getAndSetBooleanPreference(TEST_DEVICE_PREFERENCES, false);
    }

    private boolean readFreshInstallStatusFromPreferences() {
        return this.sharedPreferencesUtils.getAndSetBooleanPreference(FRESH_INSTALL_PREFERENCES, true);
    }
}
