package com.google.android.play.core.assetpacks;

import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import com.google.android.play.core.internal.C3056h;

public abstract class AssetPackState {
    /* renamed from: a */
    static AssetPackState m303a(Bundle bundle, String str, C2913cb cbVar, C2883az azVar) {
        return m304a(str, azVar.mo43935a(bundle.getInt(C3056h.m786a(NotificationCompat.CATEGORY_STATUS, str)), str), bundle.getInt(C3056h.m786a("error_code", str)), bundle.getLong(C3056h.m786a("bytes_downloaded", str)), bundle.getLong(C3056h.m786a("total_bytes_to_download", str)), cbVar.mo44016b(str));
    }

    /* renamed from: a */
    public static AssetPackState m304a(String str, int i, int i2, long j, long j2, double d) {
        return new C2893bi(str, i, i2, j, j2, (int) Math.rint(100.0d * d));
    }

    public abstract long bytesDownloaded();

    public abstract int errorCode();

    public abstract String name();

    public abstract int status();

    public abstract long totalBytesToDownload();

    public abstract int transferProgressPercentage();
}
