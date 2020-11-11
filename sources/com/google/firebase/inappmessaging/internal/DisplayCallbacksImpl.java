package com.google.firebase.inappmessaging.internal;

import android.text.TextUtils;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.inappmessaging.FirebaseInAppMessagingDisplayCallbacks;
import com.google.firebase.inappmessaging.internal.time.Clock;
import com.google.firebase.inappmessaging.model.Action;
import com.google.firebase.inappmessaging.model.InAppMessage;
import com.google.firebase.inappmessaging.model.RateLimit;
import com.google.internal.firebase.inappmessaging.p015v1.sdkserving.CampaignImpression;
import p019io.reactivex.Completable;
import p019io.reactivex.CompletableSource;
import p019io.reactivex.Maybe;
import p019io.reactivex.MaybeSource;
import p019io.reactivex.Scheduler;
import p019io.reactivex.disposables.Disposable;

public class DisplayCallbacksImpl implements FirebaseInAppMessagingDisplayCallbacks {
    private static final String MESSAGE_CLICK = "message click to metrics logger";
    /* access modifiers changed from: private */
    public static boolean wasImpressed;
    private final RateLimit appForegroundRateLimit;
    private final CampaignCacheClient campaignCacheClient;
    private final Clock clock;
    private final DataCollectionHelper dataCollectionHelper;
    private final ImpressionStorageClient impressionStorageClient;
    private final InAppMessage inAppMessage;
    private final MetricsLoggerClient metricsLoggerClient;
    private final RateLimiterClient rateLimiterClient;
    private final Schedulers schedulers;
    private final String triggeringEvent;

    DisplayCallbacksImpl(ImpressionStorageClient impressionStorageClient2, Clock clock2, Schedulers schedulers2, RateLimiterClient rateLimiterClient2, CampaignCacheClient campaignCacheClient2, RateLimit appForegroundRateLimit2, MetricsLoggerClient metricsLoggerClient2, DataCollectionHelper dataCollectionHelper2, InAppMessage inAppMessage2, String triggeringEvent2) {
        this.impressionStorageClient = impressionStorageClient2;
        this.clock = clock2;
        this.schedulers = schedulers2;
        this.rateLimiterClient = rateLimiterClient2;
        this.campaignCacheClient = campaignCacheClient2;
        this.appForegroundRateLimit = appForegroundRateLimit2;
        this.metricsLoggerClient = metricsLoggerClient2;
        this.dataCollectionHelper = dataCollectionHelper2;
        this.inAppMessage = inAppMessage2;
        this.triggeringEvent = triggeringEvent2;
        wasImpressed = false;
    }

    public Task<Void> impressionDetected() {
        if (!shouldLog() || wasImpressed) {
            logActionNotTaken("message impression to metrics logger");
            return new TaskCompletionSource().getTask();
        }
        Logging.logd("Attempting to record: " + "message impression to metrics logger");
        return maybeToTask(logToImpressionStore().andThen((CompletableSource) Completable.fromAction(DisplayCallbacksImpl$$Lambda$1.lambdaFactory$(this))).andThen((CompletableSource) updateWasImpressed()).toMaybe(), this.schedulers.mo54430io());
    }

    private Completable updateWasImpressed() {
        return Completable.fromAction(DisplayCallbacksImpl$$Lambda$2.lambdaFactory$());
    }

    public Task<Void> messageDismissed(FirebaseInAppMessagingDisplayCallbacks.InAppMessagingDismissType dismissType) {
        if (shouldLog()) {
            Logging.logd("Attempting to record: " + "message dismissal to metrics logger");
            return logImpressionIfNeeded(Completable.fromAction(DisplayCallbacksImpl$$Lambda$3.lambdaFactory$(this, dismissType)));
        }
        logActionNotTaken("message dismissal to metrics logger");
        return new TaskCompletionSource().getTask();
    }

    @Deprecated
    public Task<Void> messageClicked() {
        return messageClicked(this.inAppMessage.getAction());
    }

    public Task<Void> messageClicked(Action action) {
        if (!shouldLog()) {
            logActionNotTaken(MESSAGE_CLICK);
            return new TaskCompletionSource().getTask();
        } else if (action.getActionUrl() == null) {
            return messageDismissed(FirebaseInAppMessagingDisplayCallbacks.InAppMessagingDismissType.CLICK);
        } else {
            return logMessageClick(action);
        }
    }

    private Task<Void> logMessageClick(Action action) {
        Logging.logd("Attempting to record: message click to metrics logger");
        return logImpressionIfNeeded(Completable.fromAction(DisplayCallbacksImpl$$Lambda$4.lambdaFactory$(this, action)));
    }

    private boolean actionMatches(Action messageAction, Action actionTaken) {
        if (messageAction == null) {
            return actionTaken == null || TextUtils.isEmpty(actionTaken.getActionUrl());
        }
        return messageAction.getActionUrl().equals(actionTaken.getActionUrl());
    }

    public Task<Void> displayErrorEncountered(FirebaseInAppMessagingDisplayCallbacks.InAppMessagingErrorReason errorReason) {
        if (shouldLog()) {
            Logging.logd("Attempting to record: " + "render error to metrics logger");
            return maybeToTask(logToImpressionStore().andThen((CompletableSource) Completable.fromAction(DisplayCallbacksImpl$$Lambda$5.lambdaFactory$(this, errorReason))).andThen((CompletableSource) updateWasImpressed()).toMaybe(), this.schedulers.mo54430io());
        }
        logActionNotTaken("render error to metrics logger");
        return new TaskCompletionSource().getTask();
    }

    private boolean shouldLog() {
        return this.dataCollectionHelper.isAutomaticDataCollectionEnabled();
    }

    private Task<Void> logImpressionIfNeeded(Completable actionToTake) {
        if (!wasImpressed) {
            impressionDetected();
        }
        return maybeToTask(actionToTake.toMaybe(), this.schedulers.mo54430io());
    }

    private void logActionNotTaken(String action, Maybe<String> reason) {
        if (reason != null) {
            Logging.logd(String.format("Not recording: %s. Reason: %s", new Object[]{action, reason}));
        } else if (this.inAppMessage.getCampaignMetadata().getIsTestMessage()) {
            Logging.logd(String.format("Not recording: %s. Reason: Message is test message", new Object[]{action}));
        } else if (!this.dataCollectionHelper.isAutomaticDataCollectionEnabled()) {
            Logging.logd(String.format("Not recording: %s. Reason: Data collection is disabled", new Object[]{action}));
        } else {
            Logging.logd(String.format("Not recording: %s", new Object[]{action}));
        }
    }

    private void logActionNotTaken(String action) {
        logActionNotTaken(action, (Maybe<String>) null);
    }

    private Completable logToImpressionStore() {
        String campaignId = this.inAppMessage.getCampaignMetadata().getCampaignId();
        Logging.logd("Attempting to record message impression in impression store for id: " + campaignId);
        Completable storeCampaignImpression = this.impressionStorageClient.storeImpression((CampaignImpression) CampaignImpression.newBuilder().setImpressionTimestampMillis(this.clock.now()).setCampaignId(campaignId).build()).doOnError(DisplayCallbacksImpl$$Lambda$6.lambdaFactory$()).doOnComplete(DisplayCallbacksImpl$$Lambda$7.lambdaFactory$());
        if (InAppMessageStreamManager.isAppForegroundEvent(this.triggeringEvent)) {
            return this.rateLimiterClient.increment(this.appForegroundRateLimit).doOnError(DisplayCallbacksImpl$$Lambda$8.lambdaFactory$()).doOnComplete(DisplayCallbacksImpl$$Lambda$9.lambdaFactory$()).onErrorComplete().andThen((CompletableSource) storeCampaignImpression);
        }
        return storeCampaignImpression;
    }

    private static <T> Task<T> maybeToTask(Maybe<T> maybe, Scheduler scheduler) {
        TaskCompletionSource<T> tcs = new TaskCompletionSource<>();
        tcs.getClass();
        Disposable subscribe = maybe.doOnSuccess(DisplayCallbacksImpl$$Lambda$10.lambdaFactory$(tcs)).switchIfEmpty(Maybe.fromCallable(DisplayCallbacksImpl$$Lambda$11.lambdaFactory$(tcs))).onErrorResumeNext(DisplayCallbacksImpl$$Lambda$12.lambdaFactory$(tcs)).subscribeOn(scheduler).subscribe();
        return tcs.getTask();
    }

    static /* synthetic */ MaybeSource lambda$maybeToTask$10(TaskCompletionSource tcs, Throwable throwable) throws Exception {
        if (throwable instanceof Exception) {
            tcs.setException((Exception) throwable);
        } else {
            tcs.setException(new RuntimeException(throwable));
        }
        return Maybe.empty();
    }
}
