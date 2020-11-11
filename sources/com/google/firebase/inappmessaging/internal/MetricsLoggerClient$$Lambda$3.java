package com.google.firebase.inappmessaging.internal;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.inappmessaging.FirebaseInAppMessagingDisplayCallbacks;
import com.google.firebase.inappmessaging.model.InAppMessage;

/* compiled from: MetricsLoggerClient */
final /* synthetic */ class MetricsLoggerClient$$Lambda$3 implements OnSuccessListener {
    private final MetricsLoggerClient arg$1;
    private final InAppMessage arg$2;
    private final FirebaseInAppMessagingDisplayCallbacks.InAppMessagingErrorReason arg$3;

    private MetricsLoggerClient$$Lambda$3(MetricsLoggerClient metricsLoggerClient, InAppMessage inAppMessage, FirebaseInAppMessagingDisplayCallbacks.InAppMessagingErrorReason inAppMessagingErrorReason) {
        this.arg$1 = metricsLoggerClient;
        this.arg$2 = inAppMessage;
        this.arg$3 = inAppMessagingErrorReason;
    }

    public static OnSuccessListener lambdaFactory$(MetricsLoggerClient metricsLoggerClient, InAppMessage inAppMessage, FirebaseInAppMessagingDisplayCallbacks.InAppMessagingErrorReason inAppMessagingErrorReason) {
        return new MetricsLoggerClient$$Lambda$3(metricsLoggerClient, inAppMessage, inAppMessagingErrorReason);
    }

    public void onSuccess(Object obj) {
        this.arg$1.engagementMetricsLogger.logEvent(this.arg$1.createRenderErrorEntry(this.arg$2, (String) obj, MetricsLoggerClient.errorTransform.get(this.arg$3)).toByteArray());
    }
}
