package com.google.firebase.inappmessaging.internal;

import com.google.firebase.inappmessaging.internal.injection.qualifiers.ImpressionStore;
import com.google.internal.firebase.inappmessaging.p015v1.CampaignProto;
import com.google.internal.firebase.inappmessaging.p015v1.sdkserving.CampaignImpression;
import com.google.internal.firebase.inappmessaging.p015v1.sdkserving.CampaignImpressionList;
import com.google.internal.firebase.inappmessaging.p015v1.sdkserving.FetchEligibleCampaignsResponse;
import java.util.HashSet;
import javax.inject.Inject;
import javax.inject.Singleton;
import p019io.reactivex.Completable;
import p019io.reactivex.CompletableSource;
import p019io.reactivex.Maybe;
import p019io.reactivex.Single;

@Singleton
public class ImpressionStorageClient {
    private static final CampaignImpressionList EMPTY_IMPRESSIONS = CampaignImpressionList.getDefaultInstance();
    private Maybe<CampaignImpressionList> cachedImpressionsMaybe = Maybe.empty();
    private final ProtoStorageClient storageClient;

    @Inject
    ImpressionStorageClient(@ImpressionStore ProtoStorageClient storageClient2) {
        this.storageClient = storageClient2;
    }

    /* access modifiers changed from: private */
    public static CampaignImpressionList appendImpression(CampaignImpressionList campaignImpressions, CampaignImpression impression) {
        return (CampaignImpressionList) CampaignImpressionList.newBuilder(campaignImpressions).addAlreadySeenCampaigns(impression).build();
    }

    public Completable storeImpression(CampaignImpression impression) {
        return getAllImpressions().defaultIfEmpty(EMPTY_IMPRESSIONS).flatMapCompletable(ImpressionStorageClient$$Lambda$1.lambdaFactory$(this, impression));
    }

    public Maybe<CampaignImpressionList> getAllImpressions() {
        return this.cachedImpressionsMaybe.switchIfEmpty(this.storageClient.read(CampaignImpressionList.parser()).doOnSuccess(ImpressionStorageClient$$Lambda$2.lambdaFactory$(this))).doOnError(ImpressionStorageClient$$Lambda$3.lambdaFactory$(this));
    }

    /* access modifiers changed from: private */
    public void initInMemCache(CampaignImpressionList campaignImpressions) {
        this.cachedImpressionsMaybe = Maybe.just(campaignImpressions);
    }

    /* access modifiers changed from: private */
    public void clearInMemCache() {
        this.cachedImpressionsMaybe = Maybe.empty();
    }

    public Single<Boolean> isImpressed(CampaignProto.ThickContent content) {
        String campaignId;
        if (content.getPayloadCase().equals(CampaignProto.ThickContent.PayloadCase.VANILLA_PAYLOAD)) {
            campaignId = content.getVanillaPayload().getCampaignId();
        } else {
            campaignId = content.getExperimentalPayload().getCampaignId();
        }
        return getAllImpressions().map(ImpressionStorageClient$$Lambda$4.lambdaFactory$()).flatMapObservable(ImpressionStorageClient$$Lambda$5.lambdaFactory$()).map(ImpressionStorageClient$$Lambda$6.lambdaFactory$()).contains(campaignId);
    }

    public Completable clearImpressions(FetchEligibleCampaignsResponse response) {
        String id;
        HashSet<String> idsToClear = new HashSet<>();
        for (CampaignProto.ThickContent content : response.getMessagesList()) {
            if (content.getPayloadCase().equals(CampaignProto.ThickContent.PayloadCase.VANILLA_PAYLOAD)) {
                id = content.getVanillaPayload().getCampaignId();
            } else {
                id = content.getExperimentalPayload().getCampaignId();
            }
            idsToClear.add(id);
        }
        Logging.logd("Potential impressions to clear: " + idsToClear.toString());
        return getAllImpressions().defaultIfEmpty(EMPTY_IMPRESSIONS).flatMapCompletable(ImpressionStorageClient$$Lambda$7.lambdaFactory$(this, idsToClear));
    }

    static /* synthetic */ CompletableSource lambda$clearImpressions$4(ImpressionStorageClient impressionStorageClient, HashSet idsToClear, CampaignImpressionList storedImpressions) throws Exception {
        Logging.logd("Existing impressions: " + storedImpressions.toString());
        CampaignImpressionList.Builder clearedImpressionListBuilder = CampaignImpressionList.newBuilder();
        for (CampaignImpression storedImpression : storedImpressions.getAlreadySeenCampaignsList()) {
            if (!idsToClear.contains(storedImpression.getCampaignId())) {
                clearedImpressionListBuilder.addAlreadySeenCampaigns(storedImpression);
            }
        }
        CampaignImpressionList clearedImpressionList = (CampaignImpressionList) clearedImpressionListBuilder.build();
        Logging.logd("New cleared impression list: " + clearedImpressionList.toString());
        return impressionStorageClient.storageClient.write(clearedImpressionList).doOnComplete(ImpressionStorageClient$$Lambda$8.lambdaFactory$(impressionStorageClient, clearedImpressionList));
    }
}
