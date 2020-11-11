package com.google.firebase.inappmessaging.internal;

import com.google.firebase.abt.AbtException;
import com.google.firebase.abt.AbtExperimentInfo;
import com.google.firebase.abt.FirebaseABTesting;
import com.google.internal.firebase.inappmessaging.p015v1.CampaignProto;
import com.google.internal.firebase.inappmessaging.p015v1.sdkserving.FetchEligibleCampaignsResponse;
import developers.mobile.abt.FirebaseAbt;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.inject.Inject;

public class AbtIntegrationHelper {
    private final FirebaseABTesting abTesting;
    Executor executor = Executors.newSingleThreadExecutor();

    @Inject
    public AbtIntegrationHelper(FirebaseABTesting abTesting2) {
        this.abTesting = abTesting2;
    }

    /* access modifiers changed from: package-private */
    public void updateRunningExperiments(FetchEligibleCampaignsResponse response) {
        ArrayList<AbtExperimentInfo> runningExperiments = new ArrayList<>();
        for (CampaignProto.ThickContent content : response.getMessagesList()) {
            if (!content.getIsTestCampaign() && content.getPayloadCase().equals(CampaignProto.ThickContent.PayloadCase.EXPERIMENTAL_PAYLOAD)) {
                FirebaseAbt.ExperimentPayload payload = content.getExperimentalPayload().getExperimentPayload();
                runningExperiments.add(new AbtExperimentInfo(payload.getExperimentId(), payload.getVariantId(), payload.getTriggerEvent(), new Date(payload.getExperimentStartTimeMillis()), payload.getTriggerTimeoutMillis(), payload.getTimeToLiveMillis()));
            }
        }
        if (!runningExperiments.isEmpty()) {
            this.executor.execute(AbtIntegrationHelper$$Lambda$1.lambdaFactory$(this, runningExperiments));
        }
    }

    static /* synthetic */ void lambda$updateRunningExperiments$0(AbtIntegrationHelper abtIntegrationHelper, ArrayList runningExperiments) {
        try {
            Logging.logd("Updating running experiments with: " + runningExperiments.size() + " experiments");
            abtIntegrationHelper.abTesting.validateRunningExperiments(runningExperiments);
        } catch (AbtException e) {
            Logging.loge("Unable to register experiments with ABT, missing analytics?\n" + e.getMessage());
        }
    }

    /* access modifiers changed from: package-private */
    public void setExperimentActive(FirebaseAbt.ExperimentPayload payload) {
        this.executor.execute(AbtIntegrationHelper$$Lambda$2.lambdaFactory$(this, payload));
    }

    static /* synthetic */ void lambda$setExperimentActive$1(AbtIntegrationHelper abtIntegrationHelper, FirebaseAbt.ExperimentPayload payload) {
        try {
            Logging.logd("Updating active experiment: " + payload.toString());
            abtIntegrationHelper.abTesting.reportActiveExperiment(new AbtExperimentInfo(payload.getExperimentId(), payload.getVariantId(), payload.getTriggerEvent(), new Date(payload.getExperimentStartTimeMillis()), payload.getTriggerTimeoutMillis(), payload.getTimeToLiveMillis()));
        } catch (AbtException e) {
            Logging.loge("Unable to set experiment as active with ABT, missing analytics?\n" + e.getMessage());
        }
    }
}
