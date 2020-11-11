package com.google.firebase.inappmessaging.internal;

import android.text.TextUtils;
import com.google.firebase.analytics.connector.AnalyticsConnector;
import com.google.firebase.inappmessaging.CommonTypesProto;
import com.google.internal.firebase.inappmessaging.p015v1.CampaignProto;
import com.google.internal.firebase.inappmessaging.p015v1.sdkserving.FetchEligibleCampaignsResponse;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nullable;
import p019io.reactivex.BackpressureStrategy;
import p019io.reactivex.Flowable;
import p019io.reactivex.FlowableEmitter;
import p019io.reactivex.FlowableOnSubscribe;
import p019io.reactivex.flowables.ConnectableFlowable;

public class AnalyticsEventsManager {
    static final String TOO_MANY_CONTEXTUAL_TRIGGERS_ERROR = "Too many contextual triggers defined - limiting to 50";
    /* access modifiers changed from: private */
    public final AnalyticsConnector analyticsConnector;
    private final ConnectableFlowable<String> flowable;
    /* access modifiers changed from: private */
    public AnalyticsConnector.AnalyticsConnectorHandle handle;

    public AnalyticsEventsManager(AnalyticsConnector analyticsConnector2) {
        this.analyticsConnector = analyticsConnector2;
        ConnectableFlowable<String> publish = Flowable.create(new AnalyticsFlowableSubscriber(), BackpressureStrategy.BUFFER).publish();
        this.flowable = publish;
        publish.connect();
    }

    @Nullable
    public AnalyticsConnector.AnalyticsConnectorHandle getHandle() {
        return this.handle;
    }

    public ConnectableFlowable<String> getAnalyticsEventsFlowable() {
        return this.flowable;
    }

    static Set<String> extractAnalyticsEventNames(FetchEligibleCampaignsResponse response) {
        Set<String> analyticsEvents = new HashSet<>();
        for (CampaignProto.ThickContent content : response.getMessagesList()) {
            for (CommonTypesProto.TriggeringCondition condition : content.getTriggeringConditionsList()) {
                if (!TextUtils.isEmpty(condition.getEvent().getName())) {
                    analyticsEvents.add(condition.getEvent().getName());
                }
            }
        }
        if (analyticsEvents.size() > 50) {
            Logging.logi(TOO_MANY_CONTEXTUAL_TRIGGERS_ERROR);
        }
        return analyticsEvents;
    }

    public void updateContextualTriggers(FetchEligibleCampaignsResponse serviceResponse) {
        Set<String> analyticsEventNames = extractAnalyticsEventNames(serviceResponse);
        Logging.logd("Updating contextual triggers for the following analytics events: " + analyticsEventNames);
        this.handle.registerEventNames(analyticsEventNames);
    }

    private class AnalyticsFlowableSubscriber implements FlowableOnSubscribe<String> {
        AnalyticsFlowableSubscriber() {
        }

        public void subscribe(FlowableEmitter<String> emitter) {
            Logging.logd("Subscribing to analytics events.");
            AnalyticsEventsManager analyticsEventsManager = AnalyticsEventsManager.this;
            AnalyticsConnector.AnalyticsConnectorHandle unused = analyticsEventsManager.handle = analyticsEventsManager.analyticsConnector.registerAnalyticsConnectorListener("fiam", new FiamAnalyticsConnectorListener(emitter));
        }
    }
}
