package com.google.firebase.inappmessaging.internal.injection.modules;

import com.google.firebase.inappmessaging.internal.ProgramaticContextualTriggers;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* renamed from: com.google.firebase.inappmessaging.internal.injection.modules.ProgrammaticContextualTriggerFlowableModule_ProvidesProgramaticContextualTriggersFactory */
public final class C3992xc8a262b9 implements Factory<ProgramaticContextualTriggers> {
    private final ProgrammaticContextualTriggerFlowableModule module;

    public C3992xc8a262b9(ProgrammaticContextualTriggerFlowableModule module2) {
        this.module = module2;
    }

    public ProgramaticContextualTriggers get() {
        return providesProgramaticContextualTriggers(this.module);
    }

    public static C3992xc8a262b9 create(ProgrammaticContextualTriggerFlowableModule module2) {
        return new C3992xc8a262b9(module2);
    }

    public static ProgramaticContextualTriggers providesProgramaticContextualTriggers(ProgrammaticContextualTriggerFlowableModule instance) {
        return (ProgramaticContextualTriggers) Preconditions.checkNotNull(instance.providesProgramaticContextualTriggers(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
