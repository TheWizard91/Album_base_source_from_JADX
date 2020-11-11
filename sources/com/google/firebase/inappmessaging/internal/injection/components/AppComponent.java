package com.google.firebase.inappmessaging.internal.injection.components;

import com.google.android.datatransport.TransportFactory;
import com.google.firebase.inappmessaging.FirebaseInAppMessaging;
import com.google.firebase.inappmessaging.internal.AbtIntegrationHelper;
import com.google.firebase.inappmessaging.internal.DisplayCallbacksFactory;
import com.google.firebase.inappmessaging.internal.injection.modules.ApiClientModule;
import com.google.firebase.inappmessaging.internal.injection.modules.GrpcClientModule;
import com.google.firebase.inappmessaging.internal.injection.modules.TransportClientModule;
import dagger.BindsInstance;
import dagger.Component;

@Component(dependencies = {UniversalComponent.class}, modules = {ApiClientModule.class, GrpcClientModule.class, TransportClientModule.class})
public interface AppComponent {

    @Component.Builder
    public interface Builder {
        @BindsInstance
        Builder abtIntegrationHelper(AbtIntegrationHelper abtIntegrationHelper);

        Builder apiClientModule(ApiClientModule apiClientModule);

        AppComponent build();

        Builder grpcClientModule(GrpcClientModule grpcClientModule);

        @BindsInstance
        Builder transportFactory(TransportFactory transportFactory);

        Builder universalComponent(UniversalComponent universalComponent);
    }

    DisplayCallbacksFactory displayCallbacksFactory();

    FirebaseInAppMessaging providesFirebaseInAppMessaging();
}
