package com.google.firebase.inappmessaging.display.internal.bindingwrappers;

import android.view.LayoutInflater;
import com.google.firebase.inappmessaging.display.internal.InAppMessageLayoutConfig;
import com.google.firebase.inappmessaging.model.InAppMessage;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class ModalBindingWrapper_Factory implements Factory<ModalBindingWrapper> {
    private final Provider<InAppMessageLayoutConfig> configProvider;
    private final Provider<LayoutInflater> inflaterProvider;
    private final Provider<InAppMessage> messageProvider;

    public ModalBindingWrapper_Factory(Provider<InAppMessageLayoutConfig> configProvider2, Provider<LayoutInflater> inflaterProvider2, Provider<InAppMessage> messageProvider2) {
        this.configProvider = configProvider2;
        this.inflaterProvider = inflaterProvider2;
        this.messageProvider = messageProvider2;
    }

    public ModalBindingWrapper get() {
        return new ModalBindingWrapper(this.configProvider.get(), this.inflaterProvider.get(), this.messageProvider.get());
    }

    public static ModalBindingWrapper_Factory create(Provider<InAppMessageLayoutConfig> configProvider2, Provider<LayoutInflater> inflaterProvider2, Provider<InAppMessage> messageProvider2) {
        return new ModalBindingWrapper_Factory(configProvider2, inflaterProvider2, messageProvider2);
    }

    public static ModalBindingWrapper newInstance(InAppMessageLayoutConfig config, LayoutInflater inflater, InAppMessage message) {
        return new ModalBindingWrapper(config, inflater, message);
    }
}
