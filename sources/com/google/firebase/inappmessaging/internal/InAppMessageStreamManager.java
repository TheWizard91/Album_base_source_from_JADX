package com.google.firebase.inappmessaging.internal;

import android.text.TextUtils;
import com.google.android.gms.tasks.Task;
import com.google.firebase.inappmessaging.CommonTypesProto;
import com.google.firebase.inappmessaging.MessagesProto;
import com.google.firebase.inappmessaging.internal.injection.qualifiers.AppForeground;
import com.google.firebase.inappmessaging.internal.injection.qualifiers.ProgrammaticTrigger;
import com.google.firebase.inappmessaging.internal.time.Clock;
import com.google.firebase.inappmessaging.model.InAppMessage;
import com.google.firebase.inappmessaging.model.MessageType;
import com.google.firebase.inappmessaging.model.ProtoMarshallerClient;
import com.google.firebase.inappmessaging.model.RateLimit;
import com.google.firebase.inappmessaging.model.TriggeredInAppMessage;
import com.google.firebase.installations.FirebaseInstallationsApi;
import com.google.internal.firebase.inappmessaging.p015v1.CampaignProto;
import com.google.internal.firebase.inappmessaging.p015v1.sdkserving.CampaignImpressionList;
import com.google.internal.firebase.inappmessaging.p015v1.sdkserving.FetchEligibleCampaignsResponse;
import javax.inject.Inject;
import org.reactivestreams.Publisher;
import p019io.reactivex.Flowable;
import p019io.reactivex.Maybe;
import p019io.reactivex.MaybeEmitter;
import p019io.reactivex.MaybeSource;
import p019io.reactivex.Single;
import p019io.reactivex.flowables.ConnectableFlowable;
import p019io.reactivex.functions.Consumer;
import p019io.reactivex.functions.Function;

public class InAppMessageStreamManager {
    public static final String ON_FOREGROUND = "ON_FOREGROUND";
    private final AbtIntegrationHelper abtIntegrationHelper;
    private final AnalyticsEventsManager analyticsEventsManager;
    private final ApiClient apiClient;
    private final ConnectableFlowable<String> appForegroundEventFlowable;
    private final RateLimit appForegroundRateLimit;
    private final CampaignCacheClient campaignCacheClient;
    private final Clock clock;
    private final DataCollectionHelper dataCollectionHelper;
    private final FirebaseInstallationsApi firebaseInstallations;
    private final ImpressionStorageClient impressionStorageClient;
    private final ConnectableFlowable<String> programmaticTriggerEventFlowable;
    private final RateLimiterClient rateLimiterClient;
    private final Schedulers schedulers;
    private final TestDeviceHelper testDeviceHelper;

    @Inject
    public InAppMessageStreamManager(@AppForeground ConnectableFlowable<String> appForegroundEventFlowable2, @ProgrammaticTrigger ConnectableFlowable<String> programmaticTriggerEventFlowable2, CampaignCacheClient campaignCacheClient2, Clock clock2, ApiClient apiClient2, AnalyticsEventsManager analyticsEventsManager2, Schedulers schedulers2, ImpressionStorageClient impressionStorageClient2, RateLimiterClient rateLimiterClient2, @AppForeground RateLimit appForegroundRateLimit2, TestDeviceHelper testDeviceHelper2, FirebaseInstallationsApi firebaseInstallations2, DataCollectionHelper dataCollectionHelper2, AbtIntegrationHelper abtIntegrationHelper2) {
        this.appForegroundEventFlowable = appForegroundEventFlowable2;
        this.programmaticTriggerEventFlowable = programmaticTriggerEventFlowable2;
        this.campaignCacheClient = campaignCacheClient2;
        this.clock = clock2;
        this.apiClient = apiClient2;
        this.analyticsEventsManager = analyticsEventsManager2;
        this.schedulers = schedulers2;
        this.impressionStorageClient = impressionStorageClient2;
        this.rateLimiterClient = rateLimiterClient2;
        this.appForegroundRateLimit = appForegroundRateLimit2;
        this.testDeviceHelper = testDeviceHelper2;
        this.dataCollectionHelper = dataCollectionHelper2;
        this.firebaseInstallations = firebaseInstallations2;
        this.abtIntegrationHelper = abtIntegrationHelper2;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x001d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean containsTriggeringCondition(java.lang.String r5, com.google.internal.firebase.inappmessaging.p015v1.CampaignProto.ThickContent r6) {
        /*
            boolean r0 = isAppForegroundEvent((java.lang.String) r5)
            r1 = 1
            if (r0 == 0) goto L_0x000e
            boolean r0 = r6.getIsTestCampaign()
            if (r0 == 0) goto L_0x000e
            return r1
        L_0x000e:
            java.util.List r0 = r6.getTriggeringConditionsList()
            java.util.Iterator r0 = r0.iterator()
        L_0x0016:
            boolean r2 = r0.hasNext()
            r3 = 0
            if (r2 == 0) goto L_0x003f
            java.lang.Object r2 = r0.next()
            com.google.firebase.inappmessaging.CommonTypesProto$TriggeringCondition r2 = (com.google.firebase.inappmessaging.CommonTypesProto.TriggeringCondition) r2
            boolean r4 = hasFiamTrigger(r2, r5)
            if (r4 != 0) goto L_0x0031
            boolean r4 = hasAnalyticsTrigger(r2, r5)
            if (r4 == 0) goto L_0x0030
            goto L_0x0031
        L_0x0030:
            goto L_0x0016
        L_0x0031:
            java.lang.Object[] r0 = new java.lang.Object[r1]
            r0[r3] = r5
            java.lang.String r3 = "The event %s is contained in the list of triggers"
            java.lang.String r0 = java.lang.String.format(r3, r0)
            com.google.firebase.inappmessaging.internal.Logging.logd(r0)
            return r1
        L_0x003f:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.inappmessaging.internal.InAppMessageStreamManager.containsTriggeringCondition(java.lang.String, com.google.internal.firebase.inappmessaging.v1.CampaignProto$ThickContent):boolean");
    }

    private static boolean hasFiamTrigger(CommonTypesProto.TriggeringCondition tc, String event) {
        return tc.getFiamTrigger().toString().equals(event);
    }

    private static boolean hasAnalyticsTrigger(CommonTypesProto.TriggeringCondition tc, String event) {
        return tc.getEvent().getName().equals(event);
    }

    private static boolean isActive(Clock clock2, CampaignProto.ThickContent content) {
        long campaignEndTime;
        long campaignStartTime;
        if (content.getPayloadCase().equals(CampaignProto.ThickContent.PayloadCase.VANILLA_PAYLOAD)) {
            campaignStartTime = content.getVanillaPayload().getCampaignStartTimeMillis();
            campaignEndTime = content.getVanillaPayload().getCampaignEndTimeMillis();
        } else if (!content.getPayloadCase().equals(CampaignProto.ThickContent.PayloadCase.EXPERIMENTAL_PAYLOAD)) {
            return false;
        } else {
            campaignStartTime = content.getExperimentalPayload().getCampaignStartTimeMillis();
            campaignEndTime = content.getExperimentalPayload().getCampaignEndTimeMillis();
        }
        long currentTime = clock2.now();
        if (currentTime <= campaignStartTime || currentTime >= campaignEndTime) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public static int compareByPriority(CampaignProto.ThickContent content1, CampaignProto.ThickContent content2) {
        if (content1.getIsTestCampaign() && !content2.getIsTestCampaign()) {
            return -1;
        }
        if (!content2.getIsTestCampaign() || content1.getIsTestCampaign()) {
            return Integer.compare(content1.getPriority().getValue(), content2.getPriority().getValue());
        }
        return 1;
    }

    public static boolean isAppForegroundEvent(CommonTypesProto.TriggeringCondition event) {
        return event.getFiamTrigger().toString().equals(ON_FOREGROUND);
    }

    public static boolean isAppForegroundEvent(String event) {
        return event.equals(ON_FOREGROUND);
    }

    private boolean shouldIgnoreCache(String event) {
        if (this.testDeviceHelper.isAppInstallFresh()) {
            return isAppForegroundEvent(event);
        }
        return this.testDeviceHelper.isDeviceInTestMode();
    }

    public Flowable<TriggeredInAppMessage> createFirebaseInAppMessageStream() {
        return Flowable.merge(this.appForegroundEventFlowable, this.analyticsEventsManager.getAnalyticsEventsFlowable(), this.programmaticTriggerEventFlowable).doOnNext(InAppMessageStreamManager$$Lambda$1.lambdaFactory$()).observeOn(this.schedulers.mo54430io()).concatMap(InAppMessageStreamManager$$Lambda$4.lambdaFactory$(this)).observeOn(this.schedulers.mainThread());
    }

    static /* synthetic */ Publisher lambda$createFirebaseInAppMessageStream$21(InAppMessageStreamManager inAppMessageStreamManager, String event) throws Exception {
        Maybe<FetchEligibleCampaignsResponse> cacheRead = inAppMessageStreamManager.campaignCacheClient.get().doOnSuccess(InAppMessageStreamManager$$Lambda$15.lambdaFactory$()).doOnError(InAppMessageStreamManager$$Lambda$16.lambdaFactory$()).onErrorResumeNext(Maybe.empty());
        Consumer<FetchEligibleCampaignsResponse> cacheWrite = InAppMessageStreamManager$$Lambda$17.lambdaFactory$(inAppMessageStreamManager);
        Function lambdaFactory$ = InAppMessageStreamManager$$Lambda$21.lambdaFactory$(inAppMessageStreamManager, event, InAppMessageStreamManager$$Lambda$18.lambdaFactory$(inAppMessageStreamManager), InAppMessageStreamManager$$Lambda$19.lambdaFactory$(inAppMessageStreamManager, event), InAppMessageStreamManager$$Lambda$20.lambdaFactory$());
        Maybe onErrorResumeNext = inAppMessageStreamManager.impressionStorageClient.getAllImpressions().doOnError(InAppMessageStreamManager$$Lambda$22.lambdaFactory$()).defaultIfEmpty(CampaignImpressionList.getDefaultInstance()).onErrorResumeNext(Maybe.just(CampaignImpressionList.getDefaultInstance()));
        Function lambdaFactory$2 = InAppMessageStreamManager$$Lambda$24.lambdaFactory$(inAppMessageStreamManager, Maybe.zip(taskToMaybe(inAppMessageStreamManager.firebaseInstallations.getId()), taskToMaybe(inAppMessageStreamManager.firebaseInstallations.getToken(false)), InAppMessageStreamManager$$Lambda$23.lambdaFactory$()).observeOn(inAppMessageStreamManager.schedulers.mo54430io()));
        if (inAppMessageStreamManager.shouldIgnoreCache(event)) {
            Logging.logi(String.format("Forcing fetch from service rather than cache. Test Device: %s | App Fresh Install: %s", new Object[]{Boolean.valueOf(inAppMessageStreamManager.testDeviceHelper.isDeviceInTestMode()), Boolean.valueOf(inAppMessageStreamManager.testDeviceHelper.isAppInstallFresh())}));
            return onErrorResumeNext.flatMap(lambdaFactory$2).flatMap(lambdaFactory$).toFlowable();
        }
        Logging.logd("Attempting to fetch campaigns using cache");
        return cacheRead.switchIfEmpty((MaybeSource<? extends FetchEligibleCampaignsResponse>) onErrorResumeNext.flatMap(lambdaFactory$2).doOnSuccess(cacheWrite)).flatMap(lambdaFactory$).toFlowable();
    }

    static /* synthetic */ Maybe lambda$createFirebaseInAppMessageStream$11(InAppMessageStreamManager inAppMessageStreamManager, CampaignProto.ThickContent content) throws Exception {
        if (content.getIsTestCampaign()) {
            return Maybe.just(content);
        }
        return inAppMessageStreamManager.impressionStorageClient.isImpressed(content).doOnError(InAppMessageStreamManager$$Lambda$32.lambdaFactory$()).onErrorResumeNext(Single.just(false)).doOnSuccess(InAppMessageStreamManager$$Lambda$33.lambdaFactory$(content)).filter(InAppMessageStreamManager$$Lambda$34.lambdaFactory$()).map(InAppMessageStreamManager$$Lambda$35.lambdaFactory$(content));
    }

    static /* synthetic */ boolean lambda$createFirebaseInAppMessageStream$9(Boolean isImpressed) throws Exception {
        return !isImpressed.booleanValue();
    }

    static /* synthetic */ CampaignProto.ThickContent lambda$createFirebaseInAppMessageStream$10(CampaignProto.ThickContent content, Boolean isImpressed) throws Exception {
        return content;
    }

    /* renamed from: com.google.firebase.inappmessaging.internal.InAppMessageStreamManager$1 */
    static /* synthetic */ class C39701 {

        /* renamed from: $SwitchMap$com$google$firebase$inappmessaging$MessagesProto$Content$MessageDetailsCase */
        static final /* synthetic */ int[] f1745x95e22605;

        static {
            int[] iArr = new int[MessagesProto.Content.MessageDetailsCase.values().length];
            f1745x95e22605 = iArr;
            try {
                iArr[MessagesProto.Content.MessageDetailsCase.BANNER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1745x95e22605[MessagesProto.Content.MessageDetailsCase.IMAGE_ONLY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1745x95e22605[MessagesProto.Content.MessageDetailsCase.MODAL.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1745x95e22605[MessagesProto.Content.MessageDetailsCase.CARD.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    static /* synthetic */ Maybe lambda$createFirebaseInAppMessageStream$13(CampaignProto.ThickContent thickContent) throws Exception {
        int i = C39701.f1745x95e22605[thickContent.getContent().getMessageDetailsCase().ordinal()];
        if (i == 1 || i == 2 || i == 3 || i == 4) {
            return Maybe.just(thickContent);
        }
        Logging.logd("Filtering non-displayable message");
        return Maybe.empty();
    }

    static /* synthetic */ Maybe lambda$createFirebaseInAppMessageStream$20(InAppMessageStreamManager inAppMessageStreamManager, Maybe getIID, CampaignImpressionList campaignImpressionList) throws Exception {
        if (!inAppMessageStreamManager.dataCollectionHelper.isAutomaticDataCollectionEnabled()) {
            Logging.logi("Automatic data collection is disabled, not attempting campaign fetch from service.");
            return Maybe.just(cacheExpiringResponse());
        }
        Maybe doOnSuccess = getIID.filter(InAppMessageStreamManager$$Lambda$25.lambdaFactory$()).map(InAppMessageStreamManager$$Lambda$26.lambdaFactory$(inAppMessageStreamManager, campaignImpressionList)).switchIfEmpty(Maybe.just(cacheExpiringResponse())).doOnSuccess(InAppMessageStreamManager$$Lambda$27.lambdaFactory$()).doOnSuccess(InAppMessageStreamManager$$Lambda$28.lambdaFactory$(inAppMessageStreamManager));
        AnalyticsEventsManager analyticsEventsManager2 = inAppMessageStreamManager.analyticsEventsManager;
        analyticsEventsManager2.getClass();
        Maybe doOnSuccess2 = doOnSuccess.doOnSuccess(InAppMessageStreamManager$$Lambda$29.lambdaFactory$(analyticsEventsManager2));
        TestDeviceHelper testDeviceHelper2 = inAppMessageStreamManager.testDeviceHelper;
        testDeviceHelper2.getClass();
        return doOnSuccess2.doOnSuccess(InAppMessageStreamManager$$Lambda$30.lambdaFactory$(testDeviceHelper2)).doOnError(InAppMessageStreamManager$$Lambda$31.lambdaFactory$()).onErrorResumeNext(Maybe.empty());
    }

    /* access modifiers changed from: private */
    public Maybe<CampaignProto.ThickContent> getContentIfNotRateLimited(String event, CampaignProto.ThickContent content) {
        if (content.getIsTestCampaign() || !isAppForegroundEvent(event)) {
            return Maybe.just(content);
        }
        return this.rateLimiterClient.isRateLimited(this.appForegroundRateLimit).doOnSuccess(InAppMessageStreamManager$$Lambda$5.lambdaFactory$()).onErrorResumeNext(Single.just(false)).filter(InAppMessageStreamManager$$Lambda$6.lambdaFactory$()).map(InAppMessageStreamManager$$Lambda$7.lambdaFactory$(content));
    }

    static /* synthetic */ boolean lambda$getContentIfNotRateLimited$23(Boolean isRateLimited) throws Exception {
        return !isRateLimited.booleanValue();
    }

    static /* synthetic */ CampaignProto.ThickContent lambda$getContentIfNotRateLimited$24(CampaignProto.ThickContent content, Boolean isRateLimited) throws Exception {
        return content;
    }

    /* access modifiers changed from: private */
    public static void logImpressionStatus(CampaignProto.ThickContent content, Boolean isImpressed) {
        if (content.getPayloadCase().equals(CampaignProto.ThickContent.PayloadCase.VANILLA_PAYLOAD)) {
            Logging.logi(String.format("Already impressed campaign %s ? : %s", new Object[]{content.getVanillaPayload().getCampaignName(), isImpressed}));
        } else if (content.getPayloadCase().equals(CampaignProto.ThickContent.PayloadCase.EXPERIMENTAL_PAYLOAD)) {
            Logging.logi(String.format("Already impressed experiment %s ? : %s", new Object[]{content.getExperimentalPayload().getCampaignName(), isImpressed}));
        }
    }

    /* JADX WARNING: type inference failed for: r5v0, types: [io.reactivex.functions.Function, io.reactivex.functions.Function<com.google.internal.firebase.inappmessaging.v1.CampaignProto$ThickContent, io.reactivex.Maybe<com.google.internal.firebase.inappmessaging.v1.CampaignProto$ThickContent>>] */
    /* JADX WARNING: type inference failed for: r6v0, types: [io.reactivex.functions.Function, io.reactivex.functions.Function<com.google.internal.firebase.inappmessaging.v1.CampaignProto$ThickContent, io.reactivex.Maybe<com.google.internal.firebase.inappmessaging.v1.CampaignProto$ThickContent>>] */
    /* access modifiers changed from: private */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public p019io.reactivex.Maybe<com.google.firebase.inappmessaging.model.TriggeredInAppMessage> getTriggeredInAppMessageMaybe(java.lang.String r3, p019io.reactivex.functions.Function<com.google.internal.firebase.inappmessaging.p015v1.CampaignProto.ThickContent, p019io.reactivex.Maybe<com.google.internal.firebase.inappmessaging.p015v1.CampaignProto.ThickContent>> r4, p019io.reactivex.functions.Function<com.google.internal.firebase.inappmessaging.p015v1.CampaignProto.ThickContent, p019io.reactivex.Maybe<com.google.internal.firebase.inappmessaging.p015v1.CampaignProto.ThickContent>> r5, p019io.reactivex.functions.Function<com.google.internal.firebase.inappmessaging.p015v1.CampaignProto.ThickContent, p019io.reactivex.Maybe<com.google.internal.firebase.inappmessaging.p015v1.CampaignProto.ThickContent>> r6, com.google.internal.firebase.inappmessaging.p015v1.sdkserving.FetchEligibleCampaignsResponse r7) {
        /*
            r2 = this;
            java.util.List r0 = r7.getMessagesList()
            io.reactivex.Flowable r0 = p019io.reactivex.Flowable.fromIterable(r0)
            io.reactivex.functions.Predicate r1 = com.google.firebase.inappmessaging.internal.InAppMessageStreamManager$$Lambda$8.lambdaFactory$(r2)
            io.reactivex.Flowable r0 = r0.filter(r1)
            io.reactivex.functions.Predicate r1 = com.google.firebase.inappmessaging.internal.InAppMessageStreamManager$$Lambda$9.lambdaFactory$(r3)
            io.reactivex.Flowable r0 = r0.filter(r1)
            io.reactivex.Flowable r0 = r0.flatMapMaybe(r4)
            io.reactivex.Flowable r0 = r0.flatMapMaybe(r5)
            io.reactivex.Flowable r0 = r0.flatMapMaybe(r6)
            java.util.Comparator r1 = com.google.firebase.inappmessaging.internal.InAppMessageStreamManager$$Lambda$10.lambdaFactory$()
            io.reactivex.Flowable r0 = r0.sorted(r1)
            io.reactivex.Maybe r0 = r0.firstElement()
            io.reactivex.functions.Function r1 = com.google.firebase.inappmessaging.internal.InAppMessageStreamManager$$Lambda$11.lambdaFactory$(r2, r3)
            io.reactivex.Maybe r0 = r0.flatMap(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.inappmessaging.internal.InAppMessageStreamManager.getTriggeredInAppMessageMaybe(java.lang.String, io.reactivex.functions.Function, io.reactivex.functions.Function, io.reactivex.functions.Function, com.google.internal.firebase.inappmessaging.v1.sdkserving.FetchEligibleCampaignsResponse):io.reactivex.Maybe");
    }

    static /* synthetic */ boolean lambda$getTriggeredInAppMessageMaybe$25(InAppMessageStreamManager inAppMessageStreamManager, CampaignProto.ThickContent content) throws Exception {
        return inAppMessageStreamManager.testDeviceHelper.isDeviceInTestMode() || isActive(inAppMessageStreamManager.clock, content);
    }

    /* access modifiers changed from: private */
    public Maybe<TriggeredInAppMessage> triggeredInAppMessage(CampaignProto.ThickContent content, String event) {
        String campaignName;
        String campaignId;
        if (content.getPayloadCase().equals(CampaignProto.ThickContent.PayloadCase.VANILLA_PAYLOAD)) {
            campaignId = content.getVanillaPayload().getCampaignId();
            campaignName = content.getVanillaPayload().getCampaignName();
        } else if (!content.getPayloadCase().equals(CampaignProto.ThickContent.PayloadCase.EXPERIMENTAL_PAYLOAD)) {
            return Maybe.empty();
        } else {
            campaignId = content.getExperimentalPayload().getCampaignId();
            campaignName = content.getExperimentalPayload().getCampaignName();
            if (!content.getIsTestCampaign()) {
                this.abtIntegrationHelper.setExperimentActive(content.getExperimentalPayload().getExperimentPayload());
            }
        }
        InAppMessage inAppMessage = ProtoMarshallerClient.decode(content.getContent(), campaignId, campaignName, content.getIsTestCampaign(), content.getDataBundleMap());
        if (inAppMessage.getMessageType().equals(MessageType.UNSUPPORTED)) {
            return Maybe.empty();
        }
        return Maybe.just(new TriggeredInAppMessage(inAppMessage, event));
    }

    /* access modifiers changed from: private */
    public static boolean validIID(InstallationIdResult iid) {
        return !TextUtils.isEmpty(iid.installationId()) && !TextUtils.isEmpty(iid.installationTokenResult().getToken());
    }

    static FetchEligibleCampaignsResponse cacheExpiringResponse() {
        return (FetchEligibleCampaignsResponse) FetchEligibleCampaignsResponse.newBuilder().setExpirationEpochTimestampMillis(1).build();
    }

    private static <T> Maybe<T> taskToMaybe(Task<T> task) {
        return Maybe.create(InAppMessageStreamManager$$Lambda$12.lambdaFactory$(task));
    }

    static /* synthetic */ void lambda$taskToMaybe$30(Task task, MaybeEmitter emitter) throws Exception {
        task.addOnSuccessListener(InAppMessageStreamManager$$Lambda$13.lambdaFactory$(emitter));
        task.addOnFailureListener(InAppMessageStreamManager$$Lambda$14.lambdaFactory$(emitter));
    }

    static /* synthetic */ void lambda$taskToMaybe$28(MaybeEmitter emitter, Object result) {
        emitter.onSuccess(result);
        emitter.onComplete();
    }

    static /* synthetic */ void lambda$taskToMaybe$29(MaybeEmitter emitter, Exception e) {
        emitter.onError(e);
        emitter.onComplete();
    }
}
