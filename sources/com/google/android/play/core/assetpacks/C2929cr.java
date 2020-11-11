package com.google.android.play.core.assetpacks;

import android.content.Intent;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import com.google.android.play.core.internal.C2989aa;
import com.google.android.play.core.internal.C3047ce;
import com.google.android.play.core.internal.C3056h;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantLock;

/* renamed from: com.google.android.play.core.assetpacks.cr */
final class C2929cr {

    /* renamed from: a */
    private static final C2989aa f1117a = new C2989aa("ExtractorSessionStoreView");

    /* renamed from: b */
    private final C2886bb f1118b;

    /* renamed from: c */
    private final C3047ce<C2982w> f1119c;

    /* renamed from: d */
    private final C2913cb f1120d;

    /* renamed from: e */
    private final C3047ce<Executor> f1121e;

    /* renamed from: f */
    private final Map<Integer, C2926co> f1122f = new HashMap();

    /* renamed from: g */
    private final ReentrantLock f1123g = new ReentrantLock();

    C2929cr(C2886bb bbVar, C3047ce<C2982w> ceVar, C2913cb cbVar, C3047ce<Executor> ceVar2) {
        this.f1118b = bbVar;
        this.f1119c = ceVar;
        this.f1120d = cbVar;
        this.f1121e = ceVar2;
    }

    /* renamed from: a */
    private final <T> T m477a(C2928cq<T> cqVar) {
        try {
            mo44020a();
            return cqVar.mo44017a();
        } finally {
            mo44025b();
        }
    }

    /* renamed from: d */
    private final Map<String, C2926co> m478d(List<String> list) {
        return (Map) m477a(new C2919ch(this, list));
    }

    /* renamed from: e */
    private final C2926co m479e(int i) {
        Map<Integer, C2926co> map = this.f1122f;
        Integer valueOf = Integer.valueOf(i);
        C2926co coVar = map.get(valueOf);
        if (coVar != null) {
            return coVar;
        }
        throw new C2909by(String.format("Could not find session %d while trying to get it", new Object[]{valueOf}), i);
    }

    /* renamed from: e */
    private static String m480e(Bundle bundle) {
        ArrayList<String> stringArrayList = bundle.getStringArrayList("pack_names");
        if (stringArrayList != null && !stringArrayList.isEmpty()) {
            return stringArrayList.get(0);
        }
        throw new C2909by("Session without pack received.");
    }

    /* renamed from: e */
    private static <T> List<T> m481e(List<T> list) {
        return list == null ? Collections.emptyList() : list;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final Map<String, Integer> mo44019a(List<String> list) {
        return (Map) m477a(new C2922ck(this, list));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo44020a() {
        this.f1123g.lock();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo44021a(int i) {
        m477a(new C2921cj(this, i));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo44022a(String str, int i, long j) {
        m477a(new C2918cg(this, str, i, j));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final boolean mo44023a(Bundle bundle) {
        return ((Boolean) m477a(new C2916ce(this, bundle))).booleanValue();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final /* synthetic */ Map mo44024b(List list) {
        int i;
        Map<String, C2926co> d = m478d((List<String>) list);
        HashMap hashMap = new HashMap();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            C2926co coVar = d.get(str);
            if (coVar == null) {
                i = 8;
            } else {
                if (C2942dd.m528a(coVar.f1110c.f1105c)) {
                    try {
                        coVar.f1110c.f1105c = 6;
                        this.f1121e.mo44145a().execute(new C2923cl(this, coVar));
                        this.f1120d.mo44015a(str);
                    } catch (C2909by e) {
                        f1117a.mo44090c("Session %d with pack %s does not exist, no need to cancel.", Integer.valueOf(coVar.f1108a), str);
                    }
                }
                i = coVar.f1110c.f1105c;
            }
            hashMap.put(str, Integer.valueOf(i));
        }
        return hashMap;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final void mo44025b() {
        this.f1123g.unlock();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final /* synthetic */ void mo44026b(int i) {
        m479e(i).f1110c.f1105c = 5;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final /* synthetic */ void mo44027b(String str, int i, long j) {
        C2926co coVar = m478d((List<String>) Arrays.asList(new String[]{str})).get(str);
        if (coVar == null || C2942dd.m531b(coVar.f1110c.f1105c)) {
            f1117a.mo44089b(String.format("Could not find pack %s while trying to complete it", new Object[]{str}), new Object[0]);
        }
        this.f1118b.mo43965f(str, i, j);
        coVar.f1110c.f1105c = 4;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final boolean mo44028b(Bundle bundle) {
        return ((Boolean) m477a(new C2917cf(this, bundle))).booleanValue();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final /* synthetic */ Boolean mo44029c(Bundle bundle) {
        boolean z;
        int i = bundle.getInt("session_id");
        if (i == 0) {
            return true;
        }
        Map<Integer, C2926co> map = this.f1122f;
        Integer valueOf = Integer.valueOf(i);
        if (!map.containsKey(valueOf)) {
            return true;
        }
        C2926co coVar = this.f1122f.get(valueOf);
        if (coVar.f1110c.f1105c == 6) {
            z = false;
        } else {
            z = !C2942dd.m529a(coVar.f1110c.f1105c, bundle.getInt(C3056h.m786a(NotificationCompat.CATEGORY_STATUS, m480e(bundle))));
        }
        return Boolean.valueOf(z);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final Map<Integer, C2926co> mo44030c() {
        return this.f1122f;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final /* synthetic */ Map mo44031c(List list) {
        HashMap hashMap = new HashMap();
        for (C2926co next : this.f1122f.values()) {
            String str = next.f1110c.f1103a;
            if (list.contains(str)) {
                C2926co coVar = (C2926co) hashMap.get(str);
                if ((coVar != null ? coVar.f1108a : -1) < next.f1108a) {
                    hashMap.put(str, next);
                }
            }
        }
        return hashMap;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final /* synthetic */ void mo44032c(int i) {
        C2926co e = m479e(i);
        if (C2942dd.m531b(e.f1110c.f1105c)) {
            C2886bb bbVar = this.f1118b;
            C2925cn cnVar = e.f1110c;
            bbVar.mo43965f(cnVar.f1103a, e.f1109b, cnVar.f1104b);
            C2925cn cnVar2 = e.f1110c;
            int i2 = cnVar2.f1105c;
            if (i2 == 5 || i2 == 6) {
                this.f1118b.mo43957d(cnVar2.f1103a);
                return;
            }
            return;
        }
        throw new C2909by(String.format("Could not safely delete session %d because it is not in a terminal state.", new Object[]{Integer.valueOf(i)}), i);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public final /* synthetic */ Boolean mo44033d(Bundle bundle) {
        boolean z;
        C2927cp cpVar;
        Bundle bundle2 = bundle;
        int i = bundle2.getInt("session_id");
        boolean z2 = false;
        if (i == 0) {
            return false;
        }
        Map<Integer, C2926co> map = this.f1122f;
        Integer valueOf = Integer.valueOf(i);
        if (map.containsKey(valueOf)) {
            C2926co e = m479e(i);
            int i2 = bundle2.getInt(C3056h.m786a(NotificationCompat.CATEGORY_STATUS, e.f1110c.f1103a));
            if (C2942dd.m529a(e.f1110c.f1105c, i2)) {
                f1117a.mo44087a("Found stale update for session %s with status %d.", valueOf, Integer.valueOf(e.f1110c.f1105c));
                C2925cn cnVar = e.f1110c;
                String str = cnVar.f1103a;
                int i3 = cnVar.f1105c;
                if (i3 == 4) {
                    this.f1119c.mo44145a().mo43922a(i, str);
                } else if (i3 == 5) {
                    this.f1119c.mo44145a().mo43921a(i);
                } else if (i3 == 6) {
                    this.f1119c.mo44145a().mo43925a((List<String>) Arrays.asList(new String[]{str}));
                }
            } else {
                e.f1110c.f1105c = i2;
                if (C2942dd.m531b(i2)) {
                    mo44021a(i);
                    this.f1120d.mo44015a(e.f1110c.f1103a);
                } else {
                    List<C2927cp> list = e.f1110c.f1107e;
                    int size = list.size();
                    for (int i4 = 0; i4 < size; i4++) {
                        C2927cp cpVar2 = list.get(i4);
                        ArrayList parcelableArrayList = bundle2.getParcelableArrayList(C3056h.m787a("chunk_intents", e.f1110c.f1103a, cpVar2.f1111a));
                        if (parcelableArrayList != null) {
                            for (int i5 = 0; i5 < parcelableArrayList.size(); i5++) {
                                if (!(parcelableArrayList.get(i5) == null || ((Intent) parcelableArrayList.get(i5)).getData() == null)) {
                                    cpVar2.f1114d.get(i5).f1102a = true;
                                }
                            }
                        }
                    }
                }
            }
            z = true;
        } else {
            String e2 = m480e(bundle);
            long j = bundle2.getLong(C3056h.m786a("pack_version", e2));
            int i6 = bundle2.getInt(C3056h.m786a(NotificationCompat.CATEGORY_STATUS, e2));
            long j2 = bundle2.getLong(C3056h.m786a("total_bytes_to_download", e2));
            ArrayList<String> stringArrayList = bundle2.getStringArrayList(C3056h.m786a("slice_ids", e2));
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = m481e(stringArrayList).iterator();
            while (it.hasNext()) {
                String str2 = (String) it.next();
                ArrayList parcelableArrayList2 = bundle2.getParcelableArrayList(C3056h.m787a("chunk_intents", e2, str2));
                ArrayList arrayList2 = new ArrayList();
                for (Intent intent : m481e(parcelableArrayList2)) {
                    Iterator<T> it2 = it;
                    if (intent != null) {
                        z2 = true;
                    }
                    arrayList2.add(new C2924cm(z2));
                    it = it2;
                    z2 = false;
                }
                Iterator<T> it3 = it;
                String string = bundle2.getString(C3056h.m787a("uncompressed_hash_sha256", e2, str2));
                long j3 = bundle2.getLong(C3056h.m787a("uncompressed_size", e2, str2));
                int i7 = bundle2.getInt(C3056h.m787a("patch_format", e2, str2), 0);
                if (i7 == 0) {
                    cpVar = new C2927cp(str2, string, j3, arrayList2, bundle2.getInt(C3056h.m787a("compression_format", e2, str2), 0), 0);
                } else {
                    cpVar = new C2927cp(str2, string, j3, arrayList2, 0, i7);
                }
                arrayList.add(cpVar);
                z2 = false;
                it = it3;
            }
            this.f1122f.put(Integer.valueOf(i), new C2926co(i, bundle2.getInt("app_version_code"), new C2925cn(e2, j, i6, j2, arrayList)));
            z = true;
        }
        return Boolean.valueOf(z);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public final void mo44034d(int i) {
        m477a(new C2920ci(this, i));
    }
}
