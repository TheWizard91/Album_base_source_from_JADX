package com.google.firebase.inappmessaging.internal;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.inappmessaging.EventType;
import com.google.firebase.inappmessaging.model.InAppMessage;

/* compiled from: MetricsLoggerClient */
final /* synthetic */ class MetricsLoggerClient$$Lambda$2 implements OnSuccessListener {
    private final MetricsLoggerClient arg$1;
    private final InAppMessage arg$2;

    private MetricsLoggerClient$$Lambda$2(MetricsLoggerClient metricsLoggerClient, InAppMessage inAppMessage) {
        this.arg$1 = metricsLoggerClient;
        this.arg$2 = inAppMessage;
    }

    public static OnSuccessListener lambdaFactory$(MetricsLoggerClient metricsLoggerClient, InAppMessage inAppMessage) {
        return new MetricsLoggerClient$$Lambda$2(metricsLoggerClient, inAppMessage);
    }

    public void onSuccess(Object obj) {
        this.arg$1.engagementMetricsLogger.logEvent(this.arg$1.createEventEntry(this.arg$2, (String) obj, EventType.CLICK_EVENT_TYPE).toByteArray());
    }
}
