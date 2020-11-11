package com.google.firebase.inappmessaging.internal.injection.modules;

import com.google.firebase.inappmessaging.internal.time.Clock;
import com.google.firebase.inappmessaging.internal.time.SystemClock;
import dagger.Module;
import dagger.Provides;

@Module
public class SystemClockModule {
    @Provides
    public Clock providesSystemClockModule() {
        return new SystemClock();
    }
}
