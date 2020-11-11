package com.google.firebase.inappmessaging.display.internal;

import android.app.Application;
import com.google.firebase.inappmessaging.display.internal.bindingwrappers.BindingWrapper;
import com.google.firebase.inappmessaging.display.internal.injection.components.DaggerInAppMessageComponent;
import com.google.firebase.inappmessaging.display.internal.injection.modules.InflaterModule;
import com.google.firebase.inappmessaging.model.InAppMessage;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BindingWrapperFactory {
    private final Application application;

    @Inject
    BindingWrapperFactory(Application application2) {
        this.application = application2;
    }

    public BindingWrapper createImageBindingWrapper(InAppMessageLayoutConfig config, InAppMessage inAppMessage) {
        return DaggerInAppMessageComponent.builder().inflaterModule(new InflaterModule(inAppMessage, config, this.application)).build().imageBindingWrapper();
    }

    public BindingWrapper createModalBindingWrapper(InAppMessageLayoutConfig config, InAppMessage inAppMessage) {
        return DaggerInAppMessageComponent.builder().inflaterModule(new InflaterModule(inAppMessage, config, this.application)).build().modalBindingWrapper();
    }

    public BindingWrapper createBannerBindingWrapper(InAppMessageLayoutConfig config, InAppMessage inAppMessage) {
        return DaggerInAppMessageComponent.builder().inflaterModule(new InflaterModule(inAppMessage, config, this.application)).build().bannerBindingWrapper();
    }

    public BindingWrapper createCardBindingWrapper(InAppMessageLayoutConfig config, InAppMessage inAppMessage) {
        return DaggerInAppMessageComponent.builder().inflaterModule(new InflaterModule(inAppMessage, config, this.application)).build().cardBindingWrapper();
    }
}
