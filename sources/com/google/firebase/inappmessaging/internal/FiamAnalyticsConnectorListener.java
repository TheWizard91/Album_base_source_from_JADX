package com.google.firebase.inappmessaging.internal;

import android.os.Bundle;
import com.google.firebase.analytics.connector.AnalyticsConnector;
import p019io.reactivex.FlowableEmitter;

final class FiamAnalyticsConnectorListener implements AnalyticsConnector.AnalyticsConnectorListener {
    private FlowableEmitter<String> emitter;

    FiamAnalyticsConnectorListener(FlowableEmitter<String> emitter2) {
        this.emitter = emitter2;
    }

    public void onMessageTriggered(int id, Bundle extras) {
        if (id == 2) {
            this.emitter.onNext(extras.getString("events"));
        }
    }
}
