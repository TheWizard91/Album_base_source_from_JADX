package com.google.firebase.inappmessaging.display.internal.bindingwrappers;

import android.view.LayoutInflater;
import com.google.firebase.inappmessaging.display.internal.InAppMessageLayoutConfig;
import com.google.firebase.inappmessaging.model.InAppMessage;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class CardBindingWrapper_Factory implements Factory<CardBindingWrapper> {
    private final Provider<InAppMessageLayoutConfig> configProvider;
    private final Provider<LayoutInflater> inflaterProvider;
    private final Provider<InAppMessage> messageProvider;

    public CardBindingWrapper_Factory(Provider<InAppMessageLayoutConfig> configProvider2, Provider<LayoutInflater> inflaterProvider2, Provider<InAppMessage> messageProvider2) {
        this.configProvider = configProvider2;
        this.inflaterProvider = inflaterProvider2;
        this.messageProvider = messageProvider2;
    }

    public CardBindingWrapper get() {
        return new CardBindingWrapper(this.configProvider.get(), this.inflaterProvider.get(), this.messageProvider.get());
    }

    public static CardBindingWrapper_Factory create(Provider<InAppMessageLayoutConfig> configProvider2, Provider<LayoutInflater> inflaterProvider2, Provider<InAppMessage> messageProvider2) {
        return new CardBindingWrapper_Factory(configProvider2, inflaterProvider2, messageProvider2);
    }

    public static CardBindingWrapper newInstance(InAppMessageLayoutConfig config, LayoutInflater inflater, InAppMessage message) {
        return new CardBindingWrapper(config, inflater, message);
    }
}
