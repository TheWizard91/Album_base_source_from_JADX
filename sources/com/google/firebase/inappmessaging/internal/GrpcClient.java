package com.google.firebase.inappmessaging.internal;

import com.google.internal.firebase.inappmessaging.p015v1.sdkserving.FetchEligibleCampaignsRequest;
import com.google.internal.firebase.inappmessaging.p015v1.sdkserving.FetchEligibleCampaignsResponse;
import com.google.internal.firebase.inappmessaging.p015v1.sdkserving.InAppMessagingSdkServingGrpc;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

public class GrpcClient {
    private final InAppMessagingSdkServingGrpc.InAppMessagingSdkServingBlockingStub stub;

    @Inject
    GrpcClient(InAppMessagingSdkServingGrpc.InAppMessagingSdkServingBlockingStub stub2) {
        this.stub = stub2;
    }

    public FetchEligibleCampaignsResponse fetchEligibleCampaigns(FetchEligibleCampaignsRequest req) {
        return ((InAppMessagingSdkServingGrpc.InAppMessagingSdkServingBlockingStub) this.stub.withDeadlineAfter(30000, TimeUnit.MILLISECONDS)).fetchEligibleCampaigns(req);
    }
}
