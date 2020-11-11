package com.google.firebase.inappmessaging.display.internal.injection.components;

import android.app.Application;
import com.google.firebase.inappmessaging.FirebaseInAppMessaging;
import com.google.firebase.inappmessaging.display.FirebaseInAppMessagingDisplay;
import com.google.firebase.inappmessaging.display.FirebaseInAppMessagingDisplay_Factory;
import com.google.firebase.inappmessaging.display.internal.BindingWrapperFactory;
import com.google.firebase.inappmessaging.display.internal.FiamAnimator;
import com.google.firebase.inappmessaging.display.internal.FiamAnimator_Factory;
import com.google.firebase.inappmessaging.display.internal.FiamImageLoader;
import com.google.firebase.inappmessaging.display.internal.FiamImageLoader_Factory;
import com.google.firebase.inappmessaging.display.internal.FiamWindowManager;
import com.google.firebase.inappmessaging.display.internal.InAppMessageLayoutConfig;
import com.google.firebase.inappmessaging.display.internal.PicassoErrorListener;
import com.google.firebase.inappmessaging.display.internal.PicassoErrorListener_Factory;
import com.google.firebase.inappmessaging.display.internal.RenewableTimer_Factory;
import com.google.firebase.inappmessaging.display.internal.injection.modules.HeadlessInAppMessagingModule;
import com.google.firebase.inappmessaging.display.internal.injection.modules.HeadlessInAppMessagingModule_ProvidesHeadlesssSingletonFactory;
import com.google.firebase.inappmessaging.display.internal.injection.modules.PicassoModule;
import com.google.firebase.inappmessaging.display.internal.injection.modules.PicassoModule_ProvidesFiamControllerFactory;
import com.squareup.picasso.Picasso;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import java.util.Map;
import javax.inject.Provider;

public final class DaggerAppComponent implements AppComponent {
    private Provider<FiamAnimator> fiamAnimatorProvider;
    private Provider<FiamImageLoader> fiamImageLoaderProvider;
    private Provider<FiamWindowManager> fiamWindowManagerProvider;
    private Provider<FirebaseInAppMessagingDisplay> firebaseInAppMessagingDisplayProvider;
    private Provider<BindingWrapperFactory> inflaterClientProvider;
    private Provider<Map<String, Provider<InAppMessageLayoutConfig>>> myKeyStringMapProvider;
    private Provider<PicassoErrorListener> picassoErrorListenerProvider;
    private Provider<Application> providesApplicationProvider;
    private Provider<Picasso> providesFiamControllerProvider;
    private Provider<FirebaseInAppMessaging> providesHeadlesssSingletonProvider;

    private DaggerAppComponent(HeadlessInAppMessagingModule headlessInAppMessagingModuleParam, PicassoModule picassoModuleParam, UniversalComponent universalComponentParam) {
        initialize(headlessInAppMessagingModuleParam, picassoModuleParam, universalComponentParam);
    }

    public static Builder builder() {
        return new Builder();
    }

    private void initialize(HeadlessInAppMessagingModule headlessInAppMessagingModuleParam, PicassoModule picassoModuleParam, UniversalComponent universalComponentParam) {
        this.providesHeadlesssSingletonProvider = DoubleCheck.provider(HeadlessInAppMessagingModule_ProvidesHeadlesssSingletonFactory.create(headlessInAppMessagingModuleParam));
        this.myKeyStringMapProvider = new C3963x52044662(universalComponentParam);
        this.providesApplicationProvider = new C3964x3ca2b394(universalComponentParam);
        Provider<PicassoErrorListener> provider = DoubleCheck.provider(PicassoErrorListener_Factory.create());
        this.picassoErrorListenerProvider = provider;
        Provider<Picasso> provider2 = DoubleCheck.provider(PicassoModule_ProvidesFiamControllerFactory.create(picassoModuleParam, this.providesApplicationProvider, provider));
        this.providesFiamControllerProvider = provider2;
        this.fiamImageLoaderProvider = DoubleCheck.provider(FiamImageLoader_Factory.create(provider2));
        this.fiamWindowManagerProvider = new C3961xdff402c4(universalComponentParam);
        this.inflaterClientProvider = new C3962x4bb42da0(universalComponentParam);
        this.fiamAnimatorProvider = DoubleCheck.provider(FiamAnimator_Factory.create());
        this.firebaseInAppMessagingDisplayProvider = DoubleCheck.provider(FirebaseInAppMessagingDisplay_Factory.create(this.providesHeadlesssSingletonProvider, this.myKeyStringMapProvider, this.fiamImageLoaderProvider, RenewableTimer_Factory.create(), this.fiamWindowManagerProvider, this.providesApplicationProvider, this.inflaterClientProvider, this.fiamAnimatorProvider));
    }

    public FirebaseInAppMessagingDisplay providesFirebaseInAppMessagingUI() {
        return this.firebaseInAppMessagingDisplayProvider.get();
    }

    public PicassoErrorListener picassoErrorListener() {
        return this.picassoErrorListenerProvider.get();
    }

    public FiamImageLoader fiamImageLoader() {
        return this.fiamImageLoaderProvider.get();
    }

    public static final class Builder {
        private HeadlessInAppMessagingModule headlessInAppMessagingModule;
        private PicassoModule picassoModule;
        private UniversalComponent universalComponent;

        private Builder() {
        }

        public Builder headlessInAppMessagingModule(HeadlessInAppMessagingModule headlessInAppMessagingModule2) {
            this.headlessInAppMessagingModule = (HeadlessInAppMessagingModule) Preconditions.checkNotNull(headlessInAppMessagingModule2);
            return this;
        }

        public Builder picassoModule(PicassoModule picassoModule2) {
            this.picassoModule = (PicassoModule) Preconditions.checkNotNull(picassoModule2);
            return this;
        }

        public Builder universalComponent(UniversalComponent universalComponent2) {
            this.universalComponent = (UniversalComponent) Preconditions.checkNotNull(universalComponent2);
            return this;
        }

        public AppComponent build() {
            Preconditions.checkBuilderRequirement(this.headlessInAppMessagingModule, HeadlessInAppMessagingModule.class);
            if (this.picassoModule == null) {
                this.picassoModule = new PicassoModule();
            }
            Preconditions.checkBuilderRequirement(this.universalComponent, UniversalComponent.class);
            return new DaggerAppComponent(this.headlessInAppMessagingModule, this.picassoModule, this.universalComponent);
        }
    }

    /* renamed from: com.google.firebase.inappmessaging.display.internal.injection.components.DaggerAppComponent$com_google_firebase_inappmessaging_display_internal_injection_components_UniversalComponent_myKeyStringMap */
    private static class C3963x52044662 implements Provider<Map<String, Provider<InAppMessageLayoutConfig>>> {
        private final UniversalComponent universalComponent;

        C3963x52044662(UniversalComponent universalComponent2) {
            this.universalComponent = universalComponent2;
        }

        public Map<String, Provider<InAppMessageLayoutConfig>> get() {
            return (Map) Preconditions.checkNotNull(this.universalComponent.myKeyStringMap(), "Cannot return null from a non-@Nullable component method");
        }
    }

    /* renamed from: com.google.firebase.inappmessaging.display.internal.injection.components.DaggerAppComponent$com_google_firebase_inappmessaging_display_internal_injection_components_UniversalComponent_providesApplication */
    private static class C3964x3ca2b394 implements Provider<Application> {
        private final UniversalComponent universalComponent;

        C3964x3ca2b394(UniversalComponent universalComponent2) {
            this.universalComponent = universalComponent2;
        }

        public Application get() {
            return (Application) Preconditions.checkNotNull(this.universalComponent.providesApplication(), "Cannot return null from a non-@Nullable component method");
        }
    }

    /* renamed from: com.google.firebase.inappmessaging.display.internal.injection.components.DaggerAppComponent$com_google_firebase_inappmessaging_display_internal_injection_components_UniversalComponent_fiamWindowManager */
    private static class C3961xdff402c4 implements Provider<FiamWindowManager> {
        private final UniversalComponent universalComponent;

        C3961xdff402c4(UniversalComponent universalComponent2) {
            this.universalComponent = universalComponent2;
        }

        public FiamWindowManager get() {
            return (FiamWindowManager) Preconditions.checkNotNull(this.universalComponent.fiamWindowManager(), "Cannot return null from a non-@Nullable component method");
        }
    }

    /* renamed from: com.google.firebase.inappmessaging.display.internal.injection.components.DaggerAppComponent$com_google_firebase_inappmessaging_display_internal_injection_components_UniversalComponent_inflaterClient */
    private static class C3962x4bb42da0 implements Provider<BindingWrapperFactory> {
        private final UniversalComponent universalComponent;

        C3962x4bb42da0(UniversalComponent universalComponent2) {
            this.universalComponent = universalComponent2;
        }

        public BindingWrapperFactory get() {
            return (BindingWrapperFactory) Preconditions.checkNotNull(this.universalComponent.inflaterClient(), "Cannot return null from a non-@Nullable component method");
        }
    }
}
