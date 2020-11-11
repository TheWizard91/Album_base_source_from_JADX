package com.google.android.play.core.splitinstall;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import com.google.android.play.core.common.IntentSenderForResultStarter;

/* renamed from: com.google.android.play.core.splitinstall.ae */
final class C3102ae implements IntentSenderForResultStarter {

    /* renamed from: a */
    final /* synthetic */ Activity f1414a;

    C3102ae(Activity activity) {
        this.f1414a = activity;
    }

    public final void startIntentSenderForResult(IntentSender intentSender, int i, Intent intent, int i2, int i3, int i4, Bundle bundle) throws IntentSender.SendIntentException {
        this.f1414a.startIntentSenderForResult(intentSender, i, intent, i2, i3, i4);
    }
}
