package com.google.firebase.inappmessaging;

import com.google.firebase.FirebaseApp;
import com.google.firebase.inappmessaging.internal.DataCollectionHelper;
import com.google.firebase.inappmessaging.internal.DeveloperListenerManager;
import com.google.firebase.inappmessaging.internal.DisplayCallbacksFactory;
import com.google.firebase.inappmessaging.internal.InAppMessageStreamManager;
import com.google.firebase.inappmessaging.internal.Logging;
import com.google.firebase.inappmessaging.internal.ProgramaticContextualTriggers;
import com.google.firebase.inappmessaging.internal.injection.qualifiers.ProgrammaticTrigger;
import com.google.firebase.inappmessaging.model.TriggeredInAppMessage;
import com.google.firebase.installations.FirebaseInstallationsApi;
import java.util.concurrent.Executor;
import javax.inject.Inject;
import p019io.reactivex.disposables.Disposable;

public class FirebaseInAppMessaging {
    private boolean areMessagesSuppressed = false;
    private final DataCollectionHelper dataCollectionHelper;
    private final DeveloperListenerManager developerListenerManager;
    private final DisplayCallbacksFactory displayCallbacksFactory;
    private FirebaseInAppMessagingDisplay fiamDisplay;
    private final FirebaseInstallationsApi firebaseInstallations;
    private final InAppMessageStreamManager inAppMessageStreamManager;
    private final ProgramaticContextualTriggers programaticContextualTriggers;

    @Inject
    FirebaseInAppMessaging(InAppMessageStreamManager inAppMessageStreamManager2, @ProgrammaticTrigger ProgramaticContextualTriggers programaticContextualTriggers2, DataCollectionHelper dataCollectionHelper2, FirebaseInstallationsApi firebaseInstallations2, DisplayCallbacksFactory displayCallbacksFactory2, DeveloperListenerManager developerListenerManager2) {
        this.inAppMessageStreamManager = inAppMessageStreamManager2;
        this.programaticContextualTriggers = programaticContextualTriggers2;
        this.dataCollectionHelper = dataCollectionHelper2;
        this.firebaseInstallations = firebaseInstallations2;
        this.displayCallbacksFactory = displayCallbacksFactory2;
        this.developerListenerManager = developerListenerManager2;
        firebaseInstallations2.getId().addOnSuccessListener(FirebaseInAppMessaging$$Lambda$1.lambdaFactory$());
        Disposable subscribe = inAppMessageStreamManager2.createFirebaseInAppMessageStream().subscribe(FirebaseInAppMessaging$$Lambda$2.lambdaFactory$(this));
    }

    public static FirebaseInAppMessaging getInstance() {
        return (FirebaseInAppMessaging) FirebaseApp.getInstance().get(FirebaseInAppMessaging.class);
    }

    public boolean isAutomaticDataCollectionEnabled() {
        return this.dataCollectionHelper.isAutomaticDataCollectionEnabled();
    }

    public void setAutomaticDataCollectionEnabled(Boolean isAutomaticCollectionEnabled) {
        this.dataCollectionHelper.setAutomaticDataCollectionEnabled(isAutomaticCollectionEnabled);
    }

    public void setAutomaticDataCollectionEnabled(boolean isAutomaticCollectionEnabled) {
        this.dataCollectionHelper.setAutomaticDataCollectionEnabled(isAutomaticCollectionEnabled);
    }

    public void setMessagesSuppressed(Boolean areMessagesSuppressed2) {
        this.areMessagesSuppressed = areMessagesSuppressed2.booleanValue();
    }

    public boolean areMessagesSuppressed() {
        return this.areMessagesSuppressed;
    }

    public void setMessageDisplayComponent(FirebaseInAppMessagingDisplay messageDisplay) {
        Logging.logi("Setting display event component");
        this.fiamDisplay = messageDisplay;
    }

    public void clearDisplayListener() {
        Logging.logi("Removing display event component");
        this.fiamDisplay = null;
    }

    public void addImpressionListener(FirebaseInAppMessagingImpressionListener impressionListener) {
        this.developerListenerManager.addImpressionListener(impressionListener);
    }

    public void addClickListener(FirebaseInAppMessagingClickListener clickListener) {
        this.developerListenerManager.addClickListener(clickListener);
    }

    public void addDisplayErrorListener(FirebaseInAppMessagingDisplayErrorListener displayErrorListener) {
        this.developerListenerManager.addDisplayErrorListener(displayErrorListener);
    }

    public void addImpressionListener(FirebaseInAppMessagingImpressionListener impressionListener, Executor executor) {
        this.developerListenerManager.addImpressionListener(impressionListener, executor);
    }

    public void addClickListener(FirebaseInAppMessagingClickListener clickListener, Executor executor) {
        this.developerListenerManager.addClickListener(clickListener, executor);
    }

    public void addDisplayErrorListener(FirebaseInAppMessagingDisplayErrorListener displayErrorListener, Executor executor) {
        this.developerListenerManager.addDisplayErrorListener(displayErrorListener, executor);
    }

    public void removeImpressionListener(FirebaseInAppMessagingImpressionListener impressionListener) {
        this.developerListenerManager.removeImpressionListener(impressionListener);
    }

    public void removeClickListener(FirebaseInAppMessagingClickListener clickListener) {
        this.developerListenerManager.removeClickListener(clickListener);
    }

    public void removeDisplayErrorListener(FirebaseInAppMessagingDisplayErrorListener displayErrorListener) {
        this.developerListenerManager.removeDisplayErrorListener(displayErrorListener);
    }

    public void removeAllListeners() {
        this.developerListenerManager.removeAllListeners();
    }

    public void triggerEvent(String eventName) {
        this.programaticContextualTriggers.triggerEvent(eventName);
    }

    /* access modifiers changed from: private */
    public void triggerInAppMessage(TriggeredInAppMessage inAppMessage) {
        FirebaseInAppMessagingDisplay firebaseInAppMessagingDisplay = this.fiamDisplay;
        if (firebaseInAppMessagingDisplay != null) {
            firebaseInAppMessagingDisplay.displayMessage(inAppMessage.getInAppMessage(), this.displayCallbacksFactory.generateDisplayCallback(inAppMessage.getInAppMessage(), inAppMessage.getTriggeringEvent()));
        }
    }
}
