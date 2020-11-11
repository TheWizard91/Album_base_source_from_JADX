package com.google.android.play.core.assetpacks;

import android.content.Context;
import android.content.pm.PackageManager;
import com.google.android.play.core.internal.C2989aa;

/* renamed from: com.google.android.play.core.assetpacks.dl */
final class C2950dl {

    /* renamed from: a */
    private static final C2989aa f1189a = new C2989aa("PackageStateCache");

    /* renamed from: b */
    private final Context f1190b;

    /* renamed from: c */
    private int f1191c = -1;

    C2950dl(Context context) {
        this.f1190b = context;
    }

    /* renamed from: a */
    public final synchronized int mo44049a() {
        if (this.f1191c == -1) {
            try {
                this.f1191c = this.f1190b.getPackageManager().getPackageInfo(this.f1190b.getPackageName(), 0).versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                f1189a.mo44089b("The current version of the app could not be retrieved", new Object[0]);
            }
        }
        return this.f1191c;
    }
}
