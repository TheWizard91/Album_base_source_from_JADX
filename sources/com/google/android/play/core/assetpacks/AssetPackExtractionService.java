package com.google.android.play.core.assetpacks;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import com.google.android.play.core.internal.C2989aa;

public class AssetPackExtractionService extends Service {

    /* renamed from: a */
    Context f875a;

    /* renamed from: b */
    C2967i f876b;

    /* renamed from: c */
    C2886bb f877c;

    /* renamed from: d */
    private final C2989aa f878d = new C2989aa("AssetPackExtractionService");

    /* renamed from: e */
    private C2884b f879e;

    /* renamed from: f */
    private NotificationManager f880f;

    /* renamed from: b */
    private final synchronized void m298b(Bundle bundle) {
        String string = bundle.getString("notification_title");
        String string2 = bundle.getString("notification_subtext");
        long j = bundle.getLong("notification_timeout");
        PendingIntent pendingIntent = (PendingIntent) bundle.getParcelable("notification_on_click_intent");
        Notification.Builder timeoutAfter = Build.VERSION.SDK_INT >= 26 ? new Notification.Builder(this.f875a, "playcore-assetpacks-service-notification-channel").setTimeoutAfter(j) : new Notification.Builder(this.f875a).setPriority(-2);
        if (pendingIntent != null) {
            timeoutAfter.setContentIntent(pendingIntent);
        }
        timeoutAfter.setSmallIcon(17301633).setOngoing(false).setContentTitle(string).setSubText(string2);
        if (Build.VERSION.SDK_INT >= 21) {
            timeoutAfter.setColor(bundle.getInt("notification_color")).setVisibility(-1);
        }
        Notification build = timeoutAfter.build();
        this.f878d.mo44090c("Starting foreground service.", new Object[0]);
        this.f876b.mo44069a(true);
        if (Build.VERSION.SDK_INT >= 26) {
            this.f880f.createNotificationChannel(new NotificationChannel("playcore-assetpacks-service-notification-channel", bundle.getString("notification_channel_name"), 2));
        }
        startForeground(-1883842196, build);
    }

    /* renamed from: a */
    public final synchronized Bundle mo43875a(Bundle bundle) {
        int i = bundle.getInt("action_type");
        C2989aa aaVar = this.f878d;
        Integer valueOf = Integer.valueOf(i);
        aaVar.mo44087a("updateServiceState: %d", valueOf);
        if (i == 1) {
            m298b(bundle);
        } else if (i != 2) {
            this.f878d.mo44089b("Unknown action type received: %d", valueOf);
        } else {
            mo43876a();
        }
        return new Bundle();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final synchronized void mo43876a() {
        this.f878d.mo44090c("Stopping service.", new Object[0]);
        this.f876b.mo44069a(false);
        stopForeground(true);
        stopSelf();
    }

    public final IBinder onBind(Intent intent) {
        return this.f879e;
    }

    public final void onCreate() {
        super.onCreate();
        this.f878d.mo44087a("onCreate", new Object[0]);
        C2942dd.m525a(getApplicationContext()).mo43903a(this);
        this.f879e = new C2884b(this.f875a, this, this.f877c);
        this.f880f = (NotificationManager) this.f875a.getSystemService("notification");
    }
}
