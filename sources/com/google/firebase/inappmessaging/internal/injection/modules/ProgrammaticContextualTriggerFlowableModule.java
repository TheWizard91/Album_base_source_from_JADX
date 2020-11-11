package com.google.firebase.inappmessaging.internal.injection.modules;

import com.google.firebase.inappmessaging.internal.ProgramaticContextualTriggers;
import com.google.firebase.inappmessaging.internal.injection.qualifiers.ProgrammaticTrigger;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import p019io.reactivex.BackpressureStrategy;
import p019io.reactivex.Flowable;
import p019io.reactivex.flowables.ConnectableFlowable;

@Module
public class ProgrammaticContextualTriggerFlowableModule {
    private ProgramaticContextualTriggers triggers;

    public ProgrammaticContextualTriggerFlowableModule(ProgramaticContextualTriggers triggers2) {
        this.triggers = triggers2;
    }

    @Singleton
    @ProgrammaticTrigger
    @Provides
    public ProgramaticContextualTriggers providesProgramaticContextualTriggers() {
        return this.triggers;
    }

    @Singleton
    @ProgrammaticTrigger
    @Provides
    public ConnectableFlowable<String> providesProgramaticContextualTriggerStream() {
        ConnectableFlowable<String> flowable = Flowable.create(ProgrammaticContextualTriggerFlowableModule$$Lambda$1.lambdaFactory$(this), BackpressureStrategy.BUFFER).publish();
        flowable.connect();
        return flowable;
    }
}
