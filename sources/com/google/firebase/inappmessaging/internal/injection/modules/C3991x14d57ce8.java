package com.google.firebase.inappmessaging.internal.injection.modules;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import p019io.reactivex.flowables.ConnectableFlowable;

/* renamed from: com.google.firebase.inappmessaging.internal.injection.modules.ProgrammaticContextualTriggerFlowableModule_ProvidesProgramaticContextualTriggerStreamFactory */
public final class C3991x14d57ce8 implements Factory<ConnectableFlowable<String>> {
    private final ProgrammaticContextualTriggerFlowableModule module;

    public C3991x14d57ce8(ProgrammaticContextualTriggerFlowableModule module2) {
        this.module = module2;
    }

    public ConnectableFlowable<String> get() {
        return providesProgramaticContextualTriggerStream(this.module);
    }

    public static C3991x14d57ce8 create(ProgrammaticContextualTriggerFlowableModule module2) {
        return new C3991x14d57ce8(module2);
    }

    public static ConnectableFlowable<String> providesProgramaticContextualTriggerStream(ProgrammaticContextualTriggerFlowableModule instance) {
        return (ConnectableFlowable) Preconditions.checkNotNull(instance.providesProgramaticContextualTriggerStream(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
