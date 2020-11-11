package com.google.firebase.inappmessaging.internal;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.inappmessaging.FirebaseInAppMessagingDisplayCallbacks;
import com.google.firebase.inappmessaging.model.InAppMessage;

/* compiled from: MetricsLoggerClient */
final /* synthetic */ class MetricsLoggerClient$$Lambda$4 implements OnSuccessListener {
    private final MetricsLoggerClient arg$1;
    private final InAppMessage arg$2;
    private final FirebaseInAppMessagingDisplayCallbacks.InAppMessagingDismissType arg$3;

    private MetricsLoggerClient$$Lambda$4(MetricsLoggerClient metricsLoggerClient, InAppMessage inAppMessage, FirebaseInAppMessagingDisplayCallbacks.InAppMessagingDismissType inAppMessagingDismissType) {
        this.arg$1 = metricsLoggerClient;
        this.arg$2 = inAppMessage;
        this.arg$3 = inAppMessagingDismissType;
    }

    public static OnSuccessListener lambdaFactory$(MetricsLoggerClient metricsLoggerClient, InAppMessage inAppMessage, FirebaseInAppMessagingDisplayCallbacks.InAppMessagingDismissType inAppMessagingDismissType) {
        return new MetricsLoggerClient$$Lambda$4(metricsLoggerClient, inAppMessage, inAppMessagingDismissType);
    }

    public void onSuccess(Object obj) {
        this.arg$1.engagementMetricsLogger.logEvent(this.arg$1.createDismissEntry(this.arg$2, (String) obj, MetricsLoggerClient.dismissTransform.get(this.arg$3)).toByteArray());
    }
}
