package com.google.android.play.core.assetpacks;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import androidx.core.app.NotificationCompat;
import com.google.android.play.core.common.LocalTestingException;
import com.google.android.play.core.internal.C2989aa;
import com.google.android.play.core.internal.C3005aq;
import com.google.android.play.core.internal.C3047ce;
import com.google.android.play.core.internal.C3056h;
import com.google.android.play.core.tasks.C3169i;
import com.google.android.play.core.tasks.Task;
import com.google.android.play.core.tasks.Tasks;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: com.google.android.play.core.assetpacks.db */
final class C2940db implements C2982w {

    /* renamed from: a */
    private static final C2989aa f1154a = new C2989aa("FakeAssetPackService");

    /* renamed from: h */
    private static final AtomicInteger f1155h = new AtomicInteger(1);

    /* renamed from: b */
    private final String f1156b;

    /* renamed from: c */
    private final C2880aw f1157c;

    /* renamed from: d */
    private final C2913cb f1158d;

    /* renamed from: e */
    private final Context f1159e;

    /* renamed from: f */
    private final C2950dl f1160f;

    /* renamed from: g */
    private final C3047ce<Executor> f1161g;

    /* renamed from: i */
    private final Handler f1162i = new Handler(Looper.getMainLooper());

    C2940db(File file, C2880aw awVar, C2913cb cbVar, Context context, C2950dl dlVar, C3047ce<Executor> ceVar) {
        this.f1156b = file.getAbsolutePath();
        this.f1157c = awVar;
        this.f1158d = cbVar;
        this.f1159e = context;
        this.f1160f = dlVar;
        this.f1161g = ceVar;
    }

    /* renamed from: a */
    static long m503a(int i, long j) {
        if (i == 2) {
            return j / 2;
        }
        if (i == 3 || i == 4) {
            return j;
        }
        return 0;
    }

    /* renamed from: a */
    private final AssetPackState m504a(String str, int i) throws LocalTestingException {
        long j = 0;
        for (File length : m507b(str)) {
            j += length.length();
        }
        String str2 = str;
        return AssetPackState.m304a(str, i, 0, m503a(i, j), j, this.f1158d.mo44016b(str));
    }

    /* renamed from: a */
    private static String m505a(File file) throws LocalTestingException {
        try {
            return C2942dd.m527a((List<File>) Arrays.asList(new File[]{file}));
        } catch (NoSuchAlgorithmException e) {
            throw new LocalTestingException("SHA256 algorithm not supported.", e);
        } catch (IOException e2) {
            throw new LocalTestingException(String.format("Could not digest file: %s.", new Object[]{file}), e2);
        }
    }

    /* renamed from: a */
    private final void m506a(int i, String str, int i2) throws LocalTestingException {
        Bundle bundle = new Bundle();
        bundle.putInt("app_version_code", this.f1160f.mo44049a());
        bundle.putInt("session_id", i);
        File[] b = m507b(str);
        ArrayList arrayList = new ArrayList();
        long j = 0;
        for (File file : b) {
            j += file.length();
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(i2 == 3 ? new Intent().setData(Uri.EMPTY) : null);
            String a = C3005aq.m661a(file);
            bundle.putParcelableArrayList(C3056h.m787a("chunk_intents", str, a), arrayList2);
            bundle.putString(C3056h.m787a("uncompressed_hash_sha256", str, a), m505a(file));
            bundle.putLong(C3056h.m787a("uncompressed_size", str, a), file.length());
            arrayList.add(a);
        }
        bundle.putStringArrayList(C3056h.m786a("slice_ids", str), arrayList);
        bundle.putLong(C3056h.m786a("pack_version", str), (long) this.f1160f.mo44049a());
        bundle.putInt(C3056h.m786a(NotificationCompat.CATEGORY_STATUS, str), i2);
        bundle.putInt(C3056h.m786a("error_code", str), 0);
        bundle.putLong(C3056h.m786a("bytes_downloaded", str), m503a(i2, j));
        bundle.putLong(C3056h.m786a("total_bytes_to_download", str), j);
        bundle.putStringArrayList("pack_names", new ArrayList(Arrays.asList(new String[]{str})));
        bundle.putLong("bytes_downloaded", m503a(i2, j));
        bundle.putLong("total_bytes_to_download", j);
        this.f1162i.post(new C2939da(this, new Intent("com.google.android.play.core.assetpacks.receiver.ACTION_SESSION_UPDATE").putExtra("com.google.android.play.core.assetpacks.receiver.EXTRA_SESSION_STATE", bundle)));
    }

    /* renamed from: b */
    private final File[] m507b(String str) throws LocalTestingException {
        File file = new File(this.f1156b);
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles(new C2937cz(str));
            if (listFiles != null) {
                if (r1 != 0) {
                    for (File a : listFiles) {
                        if (C3005aq.m661a(a).equals(str)) {
                            return listFiles;
                        }
                    }
                    throw new LocalTestingException(String.format("No master slice available for pack '%s'.", new Object[]{str}));
                }
                throw new LocalTestingException(String.format("No APKs available for pack '%s'.", new Object[]{str}));
            }
            throw new LocalTestingException(String.format("Failed fetching APKs for pack '%s'.", new Object[]{str}));
        }
        throw new LocalTestingException(String.format("Local testing directory '%s' not found.", new Object[]{file}));
    }

    /* renamed from: a */
    public final Task<List<String>> mo43918a() {
        f1154a.mo44090c("syncPacks()", new Object[0]);
        return Tasks.m1067a(new ArrayList());
    }

    /* renamed from: a */
    public final Task<AssetPackStates> mo43919a(List<String> list, C2883az azVar) {
        f1154a.mo44090c("getPackStates(%s)", list);
        C3169i iVar = new C3169i();
        this.f1161g.mo44145a().execute(new C2935cx(this, list, azVar, iVar));
        return iVar.mo44328a();
    }

    /* renamed from: a */
    public final Task<AssetPackStates> mo43920a(List<String> list, List<String> list2) {
        f1154a.mo44090c("startDownload(%s)", list2);
        C3169i iVar = new C3169i();
        this.f1161g.mo44145a().execute(new C2934cw(this, list2, iVar, list));
        return iVar.mo44328a();
    }

    /* renamed from: a */
    public final void mo43921a(int i) {
        f1154a.mo44090c("notifySessionFailed", new Object[0]);
    }

    /* renamed from: a */
    public final void mo43922a(int i, String str) {
        f1154a.mo44090c("notifyModuleCompleted", new Object[0]);
        this.f1161g.mo44145a().execute(new C2936cy(this, i, str));
    }

    /* renamed from: a */
    public final void mo43923a(int i, String str, String str2, int i2) {
        f1154a.mo44090c("notifyChunkTransferred", new Object[0]);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final /* synthetic */ void mo44042a(Intent intent) {
        this.f1157c.mo43835a(this.f1159e, intent);
    }

    /* renamed from: a */
    public final void mo43924a(String str) {
        f1154a.mo44090c("removePack(%s)", str);
    }

    /* renamed from: a */
    public final void mo43925a(List<String> list) {
        f1154a.mo44090c("cancelDownload(%s)", list);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final /* synthetic */ void mo44043a(List list, C2883az azVar, C3169i iVar) {
        HashMap hashMap = new HashMap();
        Iterator it = list.iterator();
        long j = 0;
        while (it.hasNext()) {
            String str = (String) it.next();
            try {
                AssetPackState a = m504a(str, azVar.mo43935a(8, str));
                j += a.totalBytesToDownload();
                hashMap.put(str, a);
            } catch (LocalTestingException e) {
                iVar.mo44329a((Exception) e);
                return;
            }
        }
        iVar.mo44330a(AssetPackStates.m305a(j, (Map<String, AssetPackState>) hashMap));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final /* synthetic */ void mo44044a(List list, C3169i iVar, List list2) {
        C3169i iVar2 = iVar;
        HashMap hashMap = new HashMap();
        int size = list.size();
        long j = 0;
        int i = 0;
        while (i < size) {
            String str = (String) list.get(i);
            int andIncrement = f1155h.getAndIncrement();
            try {
                m506a(andIncrement, str, 1);
                m506a(andIncrement, str, 2);
                m506a(andIncrement, str, 3);
                AssetPackState a = m504a(str, 1);
                j += a.totalBytesToDownload();
                hashMap.put(str, a);
                i++;
            } catch (LocalTestingException e) {
                iVar2.mo44329a((Exception) e);
                return;
            }
        }
        int size2 = list2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            String str2 = (String) list2.get(i2);
            hashMap.put(str2, AssetPackState.m304a(str2, 4, 0, 0, 0, 0.0d));
        }
        iVar2.mo44330a(AssetPackStates.m305a(j, (Map<String, AssetPackState>) hashMap));
    }

    /* renamed from: b */
    public final Task<ParcelFileDescriptor> mo43926b(int i, String str, String str2, int i2) {
        f1154a.mo44090c("getChunkFileDescriptor(session=%d, %s, %s, %d)", Integer.valueOf(i), str, str2, Integer.valueOf(i2));
        C3169i iVar = new C3169i();
        try {
            for (File file : m507b(str)) {
                if (C3005aq.m661a(file).equals(str2)) {
                    iVar.mo44330a(ParcelFileDescriptor.open(file, 268435456));
                    return iVar.mo44328a();
                }
            }
            throw new LocalTestingException(String.format("Local testing slice for '%s' not found.", new Object[]{str2}));
        } catch (FileNotFoundException e) {
            f1154a.mo44091d("getChunkFileDescriptor failed", e);
            iVar.mo44329a((Exception) new LocalTestingException("Asset Slice file not found.", e));
        } catch (LocalTestingException e2) {
            f1154a.mo44091d("getChunkFileDescriptor failed", e2);
            iVar.mo44329a((Exception) e2);
        }
    }

    /* renamed from: b */
    public final void mo43927b() {
        f1154a.mo44090c("keepAlive", new Object[0]);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final /* synthetic */ void mo44045b(int i, String str) {
        try {
            m506a(i, str, 4);
        } catch (LocalTestingException e) {
            f1154a.mo44091d("notifyModuleCompleted failed", e);
        }
    }
}
