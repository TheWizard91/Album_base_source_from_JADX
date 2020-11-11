package com.google.firebase.inappmessaging.internal;

import android.os.Bundle;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.connector.AnalyticsConnector;
import com.google.firebase.inappmessaging.CampaignAnalytics;
import com.google.firebase.inappmessaging.ClientAppInfo;
import com.google.firebase.inappmessaging.DismissType;
import com.google.firebase.inappmessaging.EventType;
import com.google.firebase.inappmessaging.FirebaseInAppMessagingDisplayCallbacks;
import com.google.firebase.inappmessaging.RenderErrorReason;
import com.google.firebase.inappmessaging.internal.time.Clock;
import com.google.firebase.inappmessaging.model.Action;
import com.google.firebase.inappmessaging.model.BannerMessage;
import com.google.firebase.inappmessaging.model.CardMessage;
import com.google.firebase.inappmessaging.model.ImageOnlyMessage;
import com.google.firebase.inappmessaging.model.InAppMessage;
import com.google.firebase.inappmessaging.model.MessageType;
import com.google.firebase.inappmessaging.model.ModalMessage;
import com.google.firebase.installations.FirebaseInstallationsApi;
import com.google.firebase.messaging.Constants;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

public class MetricsLoggerClient {
    private static final Map<FirebaseInAppMessagingDisplayCallbacks.InAppMessagingDismissType, DismissType> dismissTransform;
    private static final Map<FirebaseInAppMessagingDisplayCallbacks.InAppMessagingErrorReason, RenderErrorReason> errorTransform;
    private final AnalyticsConnector analyticsConnector;
    private final Clock clock;
    private final DeveloperListenerManager developerListenerManager;
    private final EngagementMetricsLoggerInterface engagementMetricsLogger;
    private final FirebaseApp firebaseApp;
    private final FirebaseInstallationsApi firebaseInstallations;

    public interface EngagementMetricsLoggerInterface {
        void logEvent(byte[] bArr);
    }

    static {
        HashMap hashMap = new HashMap();
        errorTransform = hashMap;
        HashMap hashMap2 = new HashMap();
        dismissTransform = hashMap2;
        hashMap.put(FirebaseInAppMessagingDisplayCallbacks.InAppMessagingErrorReason.UNSPECIFIED_RENDER_ERROR, RenderErrorReason.UNSPECIFIED_RENDER_ERROR);
        hashMap.put(FirebaseInAppMessagingDisplayCallbacks.InAppMessagingErrorReason.IMAGE_FETCH_ERROR, RenderErrorReason.IMAGE_FETCH_ERROR);
        hashMap.put(FirebaseInAppMessagingDisplayCallbacks.InAppMessagingErrorReason.IMAGE_DISPLAY_ERROR, RenderErrorReason.IMAGE_DISPLAY_ERROR);
        hashMap.put(FirebaseInAppMessagingDisplayCallbacks.InAppMessagingErrorReason.IMAGE_UNSUPPORTED_FORMAT, RenderErrorReason.IMAGE_UNSUPPORTED_FORMAT);
        hashMap2.put(FirebaseInAppMessagingDisplayCallbacks.InAppMessagingDismissType.AUTO, DismissType.AUTO);
        hashMap2.put(FirebaseInAppMessagingDisplayCallbacks.InAppMessagingDismissType.CLICK, DismissType.CLICK);
        hashMap2.put(FirebaseInAppMessagingDisplayCallbacks.InAppMessagingDismissType.SWIPE, DismissType.SWIPE);
        hashMap2.put(FirebaseInAppMessagingDisplayCallbacks.InAppMessagingDismissType.UNKNOWN_DISMISS_TYPE, DismissType.UNKNOWN_DISMISS_TYPE);
    }

    public MetricsLoggerClient(EngagementMetricsLoggerInterface engagementMetricsLogger2, AnalyticsConnector analyticsConnector2, FirebaseApp firebaseApp2, FirebaseInstallationsApi firebaseInstallations2, Clock clock2, DeveloperListenerManager developerListenerManager2) {
        this.engagementMetricsLogger = engagementMetricsLogger2;
        this.analyticsConnector = analyticsConnector2;
        this.firebaseApp = firebaseApp2;
        this.firebaseInstallations = firebaseInstallations2;
        this.clock = clock2;
        this.developerListenerManager = developerListenerManager2;
    }

    /* access modifiers changed from: package-private */
    public void logImpression(InAppMessage message) {
        if (!isTestCampaign(message)) {
            this.firebaseInstallations.getId().addOnSuccessListener(MetricsLoggerClient$$Lambda$1.lambdaFactory$(this, message));
            logEventAsync(message, "fiam_impression", impressionCountsAsConversion(message));
        }
        this.developerListenerManager.impressionDetected(message);
    }

    /* access modifiers changed from: package-private */
    public void logMessageClick(InAppMessage message, Action action) {
        if (!isTestCampaign(message)) {
            this.firebaseInstallations.getId().addOnSuccessListener(MetricsLoggerClient$$Lambda$2.lambdaFactory$(this, message));
            logEventAsync(message, "fiam_action", true);
        }
        this.developerListenerManager.messageClicked(message, action);
    }

    /* access modifiers changed from: package-private */
    public void logRenderError(InAppMessage message, FirebaseInAppMessagingDisplayCallbacks.InAppMessagingErrorReason errorReason) {
        if (!isTestCampaign(message)) {
            this.firebaseInstallations.getId().addOnSuccessListener(MetricsLoggerClient$$Lambda$3.lambdaFactory$(this, message, errorReason));
        }
        this.developerListenerManager.displayErrorEncountered(message, errorReason);
    }

    /* access modifiers changed from: package-private */
    public void logDismiss(InAppMessage message, FirebaseInAppMessagingDisplayCallbacks.InAppMessagingDismissType dismissType) {
        if (!isTestCampaign(message)) {
            this.firebaseInstallations.getId().addOnSuccessListener(MetricsLoggerClient$$Lambda$4.lambdaFactory$(this, message, dismissType));
            logEventAsync(message, "fiam_dismiss", false);
        }
    }

    private CampaignAnalytics createEventEntry(InAppMessage message, String installationId, EventType eventType) {
        return (CampaignAnalytics) createCampaignAnalyticsBuilder(message, installationId).setEventType(eventType).build();
    }

    private CampaignAnalytics createDismissEntry(InAppMessage message, String installationId, DismissType dismissType) {
        return (CampaignAnalytics) createCampaignAnalyticsBuilder(message, installationId).setDismissType(dismissType).build();
    }

    private CampaignAnalytics createRenderErrorEntry(InAppMessage message, String installationId, RenderErrorReason reason) {
        return (CampaignAnalytics) createCampaignAnalyticsBuilder(message, installationId).setRenderErrorReason(reason).build();
    }

    private CampaignAnalytics.Builder createCampaignAnalyticsBuilder(InAppMessage message, String installationId) {
        return CampaignAnalytics.newBuilder().setFiamSdkVersion("19.1.0").setProjectNumber(this.firebaseApp.getOptions().getGcmSenderId()).setCampaignId(message.getCampaignMetadata().getCampaignId()).setClientApp(ClientAppInfo.newBuilder().setGoogleAppId(this.firebaseApp.getOptions().getApplicationId()).setFirebaseInstanceId(installationId)).setClientTimestampMillis(this.clock.now());
    }

    private void logEventAsync(InAppMessage message, String event, boolean updateConversionTracking) {
        String campaignId = message.getCampaignMetadata().getCampaignId();
        Bundle params = collectAnalyticsParams(message.getCampaignMetadata().getCampaignName(), campaignId);
        Logging.logd("Sending event=" + event + " params=" + params);
        AnalyticsConnector analyticsConnector2 = this.analyticsConnector;
        if (analyticsConnector2 != null) {
            analyticsConnector2.logEvent("fiam", event, params);
            if (updateConversionTracking) {
                this.analyticsConnector.setUserProperty("fiam", "_ln", "fiam:" + campaignId);
                return;
            }
            return;
        }
        Logging.logw("Unable to log event: analytics library is missing");
    }

    /* access modifiers changed from: package-private */
    public Bundle collectAnalyticsParams(String campaignName, String campaignId) {
        Bundle params = new Bundle();
        params.putString("_nmid", campaignId);
        params.putString(Constants.ScionAnalytics.PARAM_MESSAGE_NAME, campaignName);
        try {
            params.putInt(Constants.ScionAnalytics.PARAM_MESSAGE_DEVICE_TIME, (int) (this.clock.now() / 1000));
        } catch (NumberFormatException e) {
            Logging.logw("Error while parsing use_device_time in FIAM event: " + e.getMessage());
        }
        return params;
    }

    /* renamed from: com.google.firebase.inappmessaging.internal.MetricsLoggerClient$1 */
    static /* synthetic */ class C39711 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$inappmessaging$model$MessageType;

        static {
            int[] iArr = new int[MessageType.values().length];
            $SwitchMap$com$google$firebase$inappmessaging$model$MessageType = iArr;
            try {
                iArr[MessageType.CARD.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$firebase$inappmessaging$model$MessageType[MessageType.MODAL.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$google$firebase$inappmessaging$model$MessageType[MessageType.BANNER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$google$firebase$inappmessaging$model$MessageType[MessageType.IMAGE_ONLY.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    private boolean impressionCountsAsConversion(InAppMessage message) {
        int i = C39711.$SwitchMap$com$google$firebase$inappmessaging$model$MessageType[message.getMessageType().ordinal()];
        if (i == 1) {
            CardMessage m = (CardMessage) message;
            boolean hasNoPrimaryAction = !isValidAction(m.getPrimaryAction());
            boolean hasNoSecondaryAction = !isValidAction(m.getSecondaryAction());
            if (!hasNoPrimaryAction || !hasNoSecondaryAction) {
                return false;
            }
            return true;
        } else if (i == 2) {
            return !isValidAction(((ModalMessage) message).getAction());
        } else {
            if (i == 3) {
                return !isValidAction(((BannerMessage) message).getAction());
            }
            if (i == 4) {
                return !isValidAction(((ImageOnlyMessage) message).getAction());
            }
            Logging.loge("Unable to determine if impression should be counted as conversion.");
            return false;
        }
    }

    private boolean isTestCampaign(InAppMessage message) {
        return message.getCampaignMetadata().getIsTestMessage();
    }

    private boolean isValidAction(@Nullable Action action) {
        return (action == null || action.getActionUrl() == null || action.getActionUrl().isEmpty()) ? false : true;
    }
}
