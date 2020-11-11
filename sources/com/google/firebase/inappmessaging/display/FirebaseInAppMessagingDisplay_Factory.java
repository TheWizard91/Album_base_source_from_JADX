package com.google.firebase.inappmessaging.display;

import android.app.Application;
import com.google.firebase.inappmessaging.FirebaseInAppMessaging;
import com.google.firebase.inappmessaging.display.internal.BindingWrapperFactory;
import com.google.firebase.inappmessaging.display.internal.FiamAnimator;
import com.google.firebase.inappmessaging.display.internal.FiamImageLoader;
import com.google.firebase.inappmessaging.display.internal.FiamWindowManager;
import com.google.firebase.inappmessaging.display.internal.InAppMessageLayoutConfig;
import com.google.firebase.inappmessaging.display.internal.RenewableTimer;
import dagger.internal.Factory;
import java.util.Map;
import javax.inject.Provider;

public final class FirebaseInAppMessagingDisplay_Factory implements Factory<FirebaseInAppMessagingDisplay> {
    private final Provider<FiamAnimator> animatorProvider;
    private final Provider<Application> applicationProvider;
    private final Provider<RenewableTimer> autoDismissTimerAndImpressionTimerProvider;
    private final Provider<BindingWrapperFactory> bindingWrapperFactoryProvider;
    private final Provider<FirebaseInAppMessaging> headlessInAppMessagingProvider;
    private final Provider<FiamImageLoader> imageLoaderProvider;
    private final Provider<Map<String, Provider<InAppMessageLayoutConfig>>> layoutConfigsProvider;
    private final Provider<FiamWindowManager> windowManagerProvider;

    public FirebaseInAppMessagingDisplay_Factory(Provider<FirebaseInAppMessaging> headlessInAppMessagingProvider2, Provider<Map<String, Provider<InAppMessageLayoutConfig>>> layoutConfigsProvider2, Provider<FiamImageLoader> imageLoaderProvider2, Provider<RenewableTimer> autoDismissTimerAndImpressionTimerProvider2, Provider<FiamWindowManager> windowManagerProvider2, Provider<Application> applicationProvider2, Provider<BindingWrapperFactory> bindingWrapperFactoryProvider2, Provider<FiamAnimator> animatorProvider2) {
        this.headlessInAppMessagingProvider = headlessInAppMessagingProvider2;
        this.layoutConfigsProvider = layoutConfigsProvider2;
        this.imageLoaderProvider = imageLoaderProvider2;
        this.autoDismissTimerAndImpressionTimerProvider = autoDismissTimerAndImpressionTimerProvider2;
        this.windowManagerProvider = windowManagerProvider2;
        this.applicationProvider = applicationProvider2;
        this.bindingWrapperFactoryProvider = bindingWrapperFactoryProvider2;
        this.animatorProvider = animatorProvider2;
    }

    public FirebaseInAppMessagingDisplay get() {
        return new FirebaseInAppMessagingDisplay(this.headlessInAppMessagingProvider.get(), this.layoutConfigsProvider.get(), this.imageLoaderProvider.get(), this.autoDismissTimerAndImpressionTimerProvider.get(), this.autoDismissTimerAndImpressionTimerProvider.get(), this.windowManagerProvider.get(), this.applicationProvider.get(), this.bindingWrapperFactoryProvider.get(), this.animatorProvider.get());
    }

    public static FirebaseInAppMessagingDisplay_Factory create(Provider<FirebaseInAppMessaging> headlessInAppMessagingProvider2, Provider<Map<String, Provider<InAppMessageLayoutConfig>>> layoutConfigsProvider2, Provider<FiamImageLoader> imageLoaderProvider2, Provider<RenewableTimer> autoDismissTimerAndImpressionTimerProvider2, Provider<FiamWindowManager> windowManagerProvider2, Provider<Application> applicationProvider2, Provider<BindingWrapperFactory> bindingWrapperFactoryProvider2, Provider<FiamAnimator> animatorProvider2) {
        return new FirebaseInAppMessagingDisplay_Factory(headlessInAppMessagingProvider2, layoutConfigsProvider2, imageLoaderProvider2, autoDismissTimerAndImpressionTimerProvider2, windowManagerProvider2, applicationProvider2, bindingWrapperFactoryProvider2, animatorProvider2);
    }

    public static FirebaseInAppMessagingDisplay newInstance(FirebaseInAppMessaging headlessInAppMessaging, Map<String, Provider<InAppMessageLayoutConfig>> layoutConfigs, FiamImageLoader imageLoader, RenewableTimer impressionTimer, RenewableTimer autoDismissTimer, FiamWindowManager windowManager, Application application, BindingWrapperFactory bindingWrapperFactory, FiamAnimator animator) {
        return new FirebaseInAppMessagingDisplay(headlessInAppMessaging, layoutConfigs, imageLoader, impressionTimer, autoDismissTimer, windowManager, application, bindingWrapperFactory, animator);
    }
}
