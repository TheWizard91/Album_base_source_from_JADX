package com.google.firebase.inappmessaging.internal.injection.modules;

import p019io.reactivex.FlowableEmitter;
import p019io.reactivex.FlowableOnSubscribe;

/* compiled from: ProgrammaticContextualTriggerFlowableModule */
final /* synthetic */ class ProgrammaticContextualTriggerFlowableModule$$Lambda$1 implements FlowableOnSubscribe {
    private final ProgrammaticContextualTriggerFlowableModule arg$1;

    private ProgrammaticContextualTriggerFlowableModule$$Lambda$1(ProgrammaticContextualTriggerFlowableModule programmaticContextualTriggerFlowableModule) {
        this.arg$1 = programmaticContextualTriggerFlowableModule;
    }

    public static FlowableOnSubscribe lambdaFactory$(ProgrammaticContextualTriggerFlowableModule programmaticContextualTriggerFlowableModule) {
        return new ProgrammaticContextualTriggerFlowableModule$$Lambda$1(programmaticContextualTriggerFlowableModule);
    }

    public void subscribe(FlowableEmitter flowableEmitter) {
        this.arg$1.triggers.setListener(ProgrammaticContextualTriggerFlowableModule$$Lambda$2.lambdaFactory$(flowableEmitter));
    }
}
