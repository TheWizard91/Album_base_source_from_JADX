package com.google.firebase.inappmessaging.display.internal.injection.components;

import com.google.firebase.inappmessaging.display.internal.bindingwrappers.BannerBindingWrapper;
import com.google.firebase.inappmessaging.display.internal.bindingwrappers.CardBindingWrapper;
import com.google.firebase.inappmessaging.display.internal.bindingwrappers.ImageBindingWrapper;
import com.google.firebase.inappmessaging.display.internal.bindingwrappers.ModalBindingWrapper;
import com.google.firebase.inappmessaging.display.internal.injection.modules.InflaterModule;
import dagger.Component;

@Component(modules = {InflaterModule.class})
public interface InAppMessageComponent {
    BannerBindingWrapper bannerBindingWrapper();

    CardBindingWrapper cardBindingWrapper();

    ImageBindingWrapper imageBindingWrapper();

    ModalBindingWrapper modalBindingWrapper();
}
