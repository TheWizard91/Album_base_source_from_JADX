package com.google.firebase.inappmessaging;

import com.google.firebase.inappmessaging.internal.DataCollectionHelper;
import com.google.firebase.inappmessaging.internal.DeveloperListenerManager;
import com.google.firebase.inappmessaging.internal.DisplayCallbacksFactory;
import com.google.firebase.inappmessaging.internal.InAppMessageStreamManager;
import com.google.firebase.inappmessaging.internal.ProgramaticContextualTriggers;
import com.google.firebase.installations.FirebaseInstallationsApi;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class FirebaseInAppMessaging_Factory implements Factory<FirebaseInAppMessaging> {
    private final Provider<DataCollectionHelper> dataCollectionHelperProvider;
    private final Provider<DeveloperListenerManager> developerListenerManagerProvider;
    private final Provider<DisplayCallbacksFactory> displayCallbacksFactoryProvider;
    private final Provider<FirebaseInstallationsApi> firebaseInstallationsProvider;
    private final Provider<InAppMessageStreamManager> inAppMessageStreamManagerProvider;
    private final Provider<ProgramaticContextualTriggers> programaticContextualTriggersProvider;

    public FirebaseInAppMessaging_Factory(Provider<InAppMessageStreamManager> inAppMessageStreamManagerProvider2, Provider<ProgramaticContextualTriggers> programaticContextualTriggersProvider2, Provider<DataCollectionHelper> dataCollectionHelperProvider2, Provider<FirebaseInstallationsApi> firebaseInstallationsProvider2, Provider<DisplayCallbacksFactory> displayCallbacksFactoryProvider2, Provider<DeveloperListenerManager> developerListenerManagerProvider2) {
        this.inAppMessageStreamManagerProvider = inAppMessageStreamManagerProvider2;
        this.programaticContextualTriggersProvider = programaticContextualTriggersProvider2;
        this.dataCollectionHelperProvider = dataCollectionHelperProvider2;
        this.firebaseInstallationsProvider = firebaseInstallationsProvider2;
        this.displayCallbacksFactoryProvider = displayCallbacksFactoryProvider2;
        this.developerListenerManagerProvider = developerListenerManagerProvider2;
    }

    public FirebaseInAppMessaging get() {
        return new FirebaseInAppMessaging(this.inAppMessageStreamManagerProvider.get(), this.programaticContextualTriggersProvider.get(), this.dataCollectionHelperProvider.get(), this.firebaseInstallationsProvider.get(), this.displayCallbacksFactoryProvider.get(), this.developerListenerManagerProvider.get());
    }

    public static FirebaseInAppMessaging_Factory create(Provider<InAppMessageStreamManager> inAppMessageStreamManagerProvider2, Provider<ProgramaticContextualTriggers> programaticContextualTriggersProvider2, Provider<DataCollectionHelper> dataCollectionHelperProvider2, Provider<FirebaseInstallationsApi> firebaseInstallationsProvider2, Provider<DisplayCallbacksFactory> displayCallbacksFactoryProvider2, Provider<DeveloperListenerManager> developerListenerManagerProvider2) {
        return new FirebaseInAppMessaging_Factory(inAppMessageStreamManagerProvider2, programaticContextualTriggersProvider2, dataCollectionHelperProvider2, firebaseInstallationsProvider2, displayCallbacksFactoryProvider2, developerListenerManagerProvider2);
    }

    public static FirebaseInAppMessaging newInstance(InAppMessageStreamManager inAppMessageStreamManager, ProgramaticContextualTriggers programaticContextualTriggers, DataCollectionHelper dataCollectionHelper, FirebaseInstallationsApi firebaseInstallations, DisplayCallbacksFactory displayCallbacksFactory, DeveloperListenerManager developerListenerManager) {
        return new FirebaseInAppMessaging(inAppMessageStreamManager, programaticContextualTriggers, dataCollectionHelper, firebaseInstallations, displayCallbacksFactory, developerListenerManager);
    }
}
