package com.google.firebase.abt.component;

import android.content.Context;
import com.google.firebase.analytics.connector.AnalyticsConnector;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.components.Dependency;
import com.google.firebase.platforminfo.LibraryVersionComponent;
import java.util.Arrays;
import java.util.List;

public class AbtRegistrar implements ComponentRegistrar {
    public List<Component<?>> getComponents() {
        return Arrays.asList(new Component[]{Component.builder(AbtComponent.class).add(Dependency.required(Context.class)).add(Dependency.optional(AnalyticsConnector.class)).factory(AbtRegistrar$$Lambda$1.lambdaFactory$()).build(), LibraryVersionComponent.create("fire-abt", "19.1.0")});
    }

    static /* synthetic */ AbtComponent lambda$getComponents$0(ComponentContainer container) {
        return new AbtComponent((Context) container.get(Context.class), (AnalyticsConnector) container.get(AnalyticsConnector.class));
    }
}
