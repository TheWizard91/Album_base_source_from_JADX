package com.google.firebase.inappmessaging.display;

import android.app.Activity;
import android.app.Application;
import android.net.Uri;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import androidx.browser.customtabs.CustomTabsIntent;
import com.google.firebase.FirebaseApp;
import com.google.firebase.inappmessaging.FirebaseInAppMessaging;
import com.google.firebase.inappmessaging.FirebaseInAppMessagingDisplayCallbacks;
import com.google.firebase.inappmessaging.display.internal.BindingWrapperFactory;
import com.google.firebase.inappmessaging.display.internal.FiamAnimator;
import com.google.firebase.inappmessaging.display.internal.FiamImageLoader;
import com.google.firebase.inappmessaging.display.internal.FiamWindowManager;
import com.google.firebase.inappmessaging.display.internal.FirebaseInAppMessagingDisplayImpl;
import com.google.firebase.inappmessaging.display.internal.InAppMessageLayoutConfig;
import com.google.firebase.inappmessaging.display.internal.Logging;
import com.google.firebase.inappmessaging.display.internal.RenewableTimer;
import com.google.firebase.inappmessaging.display.internal.bindingwrappers.BindingWrapper;
import com.google.firebase.inappmessaging.display.internal.injection.modules.InflaterConfigModule;
import com.google.firebase.inappmessaging.model.Action;
import com.google.firebase.inappmessaging.model.BannerMessage;
import com.google.firebase.inappmessaging.model.CardMessage;
import com.google.firebase.inappmessaging.model.ImageData;
import com.google.firebase.inappmessaging.model.ImageOnlyMessage;
import com.google.firebase.inappmessaging.model.InAppMessage;
import com.google.firebase.inappmessaging.model.MessageType;
import com.google.firebase.inappmessaging.model.ModalMessage;
import com.squareup.picasso.Callback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Provider;

public class FirebaseInAppMessagingDisplay extends FirebaseInAppMessagingDisplayImpl {
    static final long DISMISS_THRESHOLD_MILLIS = 20000;
    static final long IMPRESSION_THRESHOLD_MILLIS = 5000;
    static final long INTERVAL_MILLIS = 1000;
    /* access modifiers changed from: private */
    public final FiamAnimator animator;
    /* access modifiers changed from: private */
    public final Application application;
    /* access modifiers changed from: private */
    public final RenewableTimer autoDismissTimer;
    private final BindingWrapperFactory bindingWrapperFactory;
    /* access modifiers changed from: private */
    public FirebaseInAppMessagingDisplayCallbacks callbacks;
    String currentlyBoundActivityName;
    private FiamListener fiamListener;
    private final FirebaseInAppMessaging headlessInAppMessaging;
    private final FiamImageLoader imageLoader;
    /* access modifiers changed from: private */
    public final RenewableTimer impressionTimer;
    /* access modifiers changed from: private */
    public InAppMessage inAppMessage;
    private final Map<String, Provider<InAppMessageLayoutConfig>> layoutConfigs;
    /* access modifiers changed from: private */
    public final FiamWindowManager windowManager;

    @Inject
    FirebaseInAppMessagingDisplay(FirebaseInAppMessaging headlessInAppMessaging2, Map<String, Provider<InAppMessageLayoutConfig>> layoutConfigs2, FiamImageLoader imageLoader2, RenewableTimer impressionTimer2, RenewableTimer autoDismissTimer2, FiamWindowManager windowManager2, Application application2, BindingWrapperFactory bindingWrapperFactory2, FiamAnimator animator2) {
        this.headlessInAppMessaging = headlessInAppMessaging2;
        this.layoutConfigs = layoutConfigs2;
        this.imageLoader = imageLoader2;
        this.impressionTimer = impressionTimer2;
        this.autoDismissTimer = autoDismissTimer2;
        this.windowManager = windowManager2;
        this.application = application2;
        this.bindingWrapperFactory = bindingWrapperFactory2;
        this.animator = animator2;
    }

    public static FirebaseInAppMessagingDisplay getInstance() {
        return (FirebaseInAppMessagingDisplay) FirebaseApp.getInstance().get(FirebaseInAppMessagingDisplay.class);
    }

    private static int getScreenOrientation(Application app) {
        return app.getResources().getConfiguration().orientation;
    }

    public void testMessage(Activity activity, InAppMessage inAppMessage2, FirebaseInAppMessagingDisplayCallbacks callbacks2) {
        this.inAppMessage = inAppMessage2;
        this.callbacks = callbacks2;
        showActiveFiam(activity);
    }

    public void setFiamListener(FiamListener listener) {
        this.fiamListener = listener;
    }

    public void clearFiamListener() {
        this.fiamListener = null;
    }

    public void onActivityResumed(Activity activity) {
        super.onActivityResumed(activity);
        bindFiamToActivity(activity);
    }

    public void onActivityPaused(Activity activity) {
        unbindFiamFromActivity(activity);
        this.headlessInAppMessaging.removeAllListeners();
        super.onActivityPaused(activity);
    }

    private void bindFiamToActivity(Activity activity) {
        String str = this.currentlyBoundActivityName;
        if (str == null || !str.equals(activity.getLocalClassName())) {
            Logging.logi("Binding to activity: " + activity.getLocalClassName());
            this.headlessInAppMessaging.setMessageDisplayComponent(FirebaseInAppMessagingDisplay$$Lambda$1.lambdaFactory$(this, activity));
            this.currentlyBoundActivityName = activity.getLocalClassName();
        }
        if (this.inAppMessage != null) {
            showActiveFiam(activity);
        }
    }

    static /* synthetic */ void lambda$bindFiamToActivity$0(FirebaseInAppMessagingDisplay firebaseInAppMessagingDisplay, Activity activity, InAppMessage iam, FirebaseInAppMessagingDisplayCallbacks cb) {
        if (firebaseInAppMessagingDisplay.inAppMessage != null || firebaseInAppMessagingDisplay.headlessInAppMessaging.areMessagesSuppressed()) {
            Logging.logd("Active FIAM exists. Skipping trigger");
            return;
        }
        firebaseInAppMessagingDisplay.inAppMessage = iam;
        firebaseInAppMessagingDisplay.callbacks = cb;
        firebaseInAppMessagingDisplay.showActiveFiam(activity);
    }

    private void unbindFiamFromActivity(Activity activity) {
        String str = this.currentlyBoundActivityName;
        if (str != null && str.equals(activity.getLocalClassName())) {
            Logging.logi("Unbinding from activity: " + activity.getLocalClassName());
            this.headlessInAppMessaging.clearDisplayListener();
            this.imageLoader.cancelTag(activity.getClass());
            removeDisplayedFiam(activity);
            this.currentlyBoundActivityName = null;
        }
    }

    /* access modifiers changed from: package-private */
    public InAppMessage getCurrentInAppMessage() {
        return this.inAppMessage;
    }

    private void showActiveFiam(final Activity activity) {
        final BindingWrapper bindingWrapper;
        if (this.inAppMessage == null || this.headlessInAppMessaging.areMessagesSuppressed()) {
            Logging.loge("No active message found to render");
        } else if (this.inAppMessage.getMessageType().equals(MessageType.UNSUPPORTED)) {
            Logging.loge("The message being triggered is not supported by this version of the sdk.");
        } else {
            notifyFiamTrigger();
            InAppMessageLayoutConfig config = (InAppMessageLayoutConfig) this.layoutConfigs.get(InflaterConfigModule.configFor(this.inAppMessage.getMessageType(), getScreenOrientation(this.application))).get();
            int i = C39485.$SwitchMap$com$google$firebase$inappmessaging$model$MessageType[this.inAppMessage.getMessageType().ordinal()];
            if (i == 1) {
                bindingWrapper = this.bindingWrapperFactory.createBannerBindingWrapper(config, this.inAppMessage);
            } else if (i == 2) {
                bindingWrapper = this.bindingWrapperFactory.createModalBindingWrapper(config, this.inAppMessage);
            } else if (i == 3) {
                bindingWrapper = this.bindingWrapperFactory.createImageBindingWrapper(config, this.inAppMessage);
            } else if (i != 4) {
                Logging.loge("No bindings found for this message type");
                return;
            } else {
                bindingWrapper = this.bindingWrapperFactory.createCardBindingWrapper(config, this.inAppMessage);
            }
            activity.findViewById(16908290).post(new Runnable() {
                public void run() {
                    FirebaseInAppMessagingDisplay.this.inflateBinding(activity, bindingWrapper);
                }
            });
        }
    }

    /* renamed from: com.google.firebase.inappmessaging.display.FirebaseInAppMessagingDisplay$5 */
    static /* synthetic */ class C39485 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$inappmessaging$model$MessageType;

        static {
            int[] iArr = new int[MessageType.values().length];
            $SwitchMap$com$google$firebase$inappmessaging$model$MessageType = iArr;
            try {
                iArr[MessageType.BANNER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$firebase$inappmessaging$model$MessageType[MessageType.MODAL.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$google$firebase$inappmessaging$model$MessageType[MessageType.IMAGE_ONLY.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$google$firebase$inappmessaging$model$MessageType[MessageType.CARD.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    /* access modifiers changed from: private */
    public void inflateBinding(final Activity activity, final BindingWrapper bindingWrapper) {
        View.OnClickListener actionListener;
        View.OnClickListener dismissListener = new View.OnClickListener() {
            public void onClick(View v) {
                if (FirebaseInAppMessagingDisplay.this.callbacks != null) {
                    FirebaseInAppMessagingDisplay.this.callbacks.messageDismissed(FirebaseInAppMessagingDisplayCallbacks.InAppMessagingDismissType.CLICK);
                }
                FirebaseInAppMessagingDisplay.this.dismissFiam(activity);
            }
        };
        Map<Action, View.OnClickListener> actionListeners = new HashMap<>();
        for (final Action action : extractActions(this.inAppMessage)) {
            if (action == null || TextUtils.isEmpty(action.getActionUrl())) {
                Logging.loge("No action url found for action.");
                actionListener = dismissListener;
            } else {
                actionListener = new View.OnClickListener() {
                    public void onClick(View v) {
                        if (FirebaseInAppMessagingDisplay.this.callbacks != null) {
                            FirebaseInAppMessagingDisplay.this.callbacks.messageClicked(action);
                        }
                        new CustomTabsIntent.Builder().setShowTitle(true).build().launchUrl(activity, Uri.parse(action.getActionUrl()));
                        FirebaseInAppMessagingDisplay.this.notifyFiamClick();
                        FirebaseInAppMessagingDisplay.this.removeDisplayedFiam(activity);
                        InAppMessage unused = FirebaseInAppMessagingDisplay.this.inAppMessage = null;
                        FirebaseInAppMessagingDisplayCallbacks unused2 = FirebaseInAppMessagingDisplay.this.callbacks = null;
                    }
                };
            }
            actionListeners.put(action, actionListener);
        }
        final ViewTreeObserver.OnGlobalLayoutListener layoutListener = bindingWrapper.inflate(actionListeners, dismissListener);
        if (layoutListener != null) {
            bindingWrapper.getImageView().getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);
        }
        loadNullableImage(activity, bindingWrapper, extractImageData(this.inAppMessage), new Callback() {
            public void onSuccess() {
                if (!bindingWrapper.getConfig().backgroundEnabled().booleanValue()) {
                    bindingWrapper.getRootView().setOnTouchListener(new View.OnTouchListener() {
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() != 4) {
                                return false;
                            }
                            if (FirebaseInAppMessagingDisplay.this.callbacks != null) {
                                FirebaseInAppMessagingDisplay.this.callbacks.messageDismissed(FirebaseInAppMessagingDisplayCallbacks.InAppMessagingDismissType.UNKNOWN_DISMISS_TYPE);
                            }
                            FirebaseInAppMessagingDisplay.this.dismissFiam(activity);
                            return true;
                        }
                    });
                }
                FirebaseInAppMessagingDisplay.this.impressionTimer.start(new RenewableTimer.Callback() {
                    public void onFinish() {
                        if (FirebaseInAppMessagingDisplay.this.inAppMessage != null && FirebaseInAppMessagingDisplay.this.callbacks != null) {
                            Logging.logi("Impression timer onFinish for: " + FirebaseInAppMessagingDisplay.this.inAppMessage.getCampaignMetadata().getCampaignId());
                            FirebaseInAppMessagingDisplay.this.callbacks.impressionDetected();
                        }
                    }
                }, FirebaseInAppMessagingDisplay.IMPRESSION_THRESHOLD_MILLIS, 1000);
                if (bindingWrapper.getConfig().autoDismiss().booleanValue()) {
                    FirebaseInAppMessagingDisplay.this.autoDismissTimer.start(new RenewableTimer.Callback() {
                        public void onFinish() {
                            if (!(FirebaseInAppMessagingDisplay.this.inAppMessage == null || FirebaseInAppMessagingDisplay.this.callbacks == null)) {
                                FirebaseInAppMessagingDisplay.this.callbacks.messageDismissed(FirebaseInAppMessagingDisplayCallbacks.InAppMessagingDismissType.AUTO);
                            }
                            FirebaseInAppMessagingDisplay.this.dismissFiam(activity);
                        }
                    }, FirebaseInAppMessagingDisplay.DISMISS_THRESHOLD_MILLIS, 1000);
                }
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        FirebaseInAppMessagingDisplay.this.windowManager.show(bindingWrapper, activity);
                        if (bindingWrapper.getConfig().animate().booleanValue()) {
                            FirebaseInAppMessagingDisplay.this.animator.slideIntoView(FirebaseInAppMessagingDisplay.this.application, bindingWrapper.getRootView(), FiamAnimator.Position.TOP);
                        }
                    }
                });
            }

            public void onError(Exception e) {
                Logging.loge("Image download failure ");
                if (layoutListener != null) {
                    bindingWrapper.getImageView().getViewTreeObserver().removeGlobalOnLayoutListener(layoutListener);
                }
                FirebaseInAppMessagingDisplay.this.cancelTimers();
                InAppMessage unused = FirebaseInAppMessagingDisplay.this.inAppMessage = null;
                FirebaseInAppMessagingDisplayCallbacks unused2 = FirebaseInAppMessagingDisplay.this.callbacks = null;
            }
        });
    }

    private List<Action> extractActions(InAppMessage message) {
        List<Action> actions = new ArrayList<>();
        int i = C39485.$SwitchMap$com$google$firebase$inappmessaging$model$MessageType[message.getMessageType().ordinal()];
        if (i == 1) {
            actions.add(((BannerMessage) message).getAction());
        } else if (i == 2) {
            actions.add(((ModalMessage) message).getAction());
        } else if (i == 3) {
            actions.add(((ImageOnlyMessage) message).getAction());
        } else if (i != 4) {
            actions.add(Action.builder().build());
        } else {
            actions.add(((CardMessage) message).getPrimaryAction());
            actions.add(((CardMessage) message).getSecondaryAction());
        }
        return actions;
    }

    private ImageData extractImageData(InAppMessage message) {
        if (message.getMessageType() != MessageType.CARD) {
            return message.getImageData();
        }
        ImageData portraitImageData = ((CardMessage) message).getPortraitImageData();
        ImageData landscapeImageData = ((CardMessage) message).getLandscapeImageData();
        return getScreenOrientation(this.application) == 1 ? isValidImageData(portraitImageData) ? portraitImageData : landscapeImageData : isValidImageData(landscapeImageData) ? landscapeImageData : portraitImageData;
    }

    private boolean isValidImageData(ImageData imageData) {
        return imageData != null && !TextUtils.isEmpty(imageData.getImageUrl());
    }

    private void loadNullableImage(Activity activity, BindingWrapper fiam, ImageData imageData, Callback callback) {
        if (isValidImageData(imageData)) {
            this.imageLoader.load(imageData.getImageUrl()).tag(activity.getClass()).placeholder(C2472R.C2474drawable.image_placeholder).into(fiam.getImageView(), callback);
        } else {
            callback.onSuccess();
        }
    }

    /* access modifiers changed from: private */
    public void dismissFiam(Activity activity) {
        Logging.logd("Dismissing fiam");
        notifyFiamDismiss();
        removeDisplayedFiam(activity);
        this.inAppMessage = null;
        this.callbacks = null;
    }

    /* access modifiers changed from: private */
    public void removeDisplayedFiam(Activity activity) {
        if (this.windowManager.isFiamDisplayed()) {
            this.windowManager.destroy(activity);
            cancelTimers();
        }
    }

    /* access modifiers changed from: private */
    public void cancelTimers() {
        this.impressionTimer.cancel();
        this.autoDismissTimer.cancel();
    }

    private void notifyFiamTrigger() {
        FiamListener fiamListener2 = this.fiamListener;
        if (fiamListener2 != null) {
            fiamListener2.onFiamTrigger();
        }
    }

    /* access modifiers changed from: private */
    public void notifyFiamClick() {
        FiamListener fiamListener2 = this.fiamListener;
        if (fiamListener2 != null) {
            fiamListener2.onFiamClick();
        }
    }

    private void notifyFiamDismiss() {
        FiamListener fiamListener2 = this.fiamListener;
        if (fiamListener2 != null) {
            fiamListener2.onFiamDismiss();
        }
    }
}
