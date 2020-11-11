package com.google.android.play.core.assetpacks;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.play.core.internal.C3068t;
import com.google.android.play.core.tasks.C3169i;
import java.util.List;

/* renamed from: com.google.android.play.core.assetpacks.ak */
class C2868ak<T> extends C3068t {

    /* renamed from: a */
    final C3169i<T> f920a;

    /* renamed from: b */
    final /* synthetic */ C2875ar f921b;

    C2868ak(C2875ar arVar, C3169i<T> iVar) {
        this.f921b = arVar;
        this.f920a = iVar;
    }

    C2868ak(C2875ar arVar, C3169i iVar, byte[] bArr) {
        this(arVar, iVar);
    }

    C2868ak(C2875ar arVar, C3169i iVar, char[] cArr) {
        this(arVar, iVar);
    }

    C2868ak(C2875ar arVar, C3169i iVar, int[] iArr) {
        this(arVar, iVar);
    }

    C2868ak(C2875ar arVar, C3169i iVar, short[] sArr) {
        this(arVar, iVar);
    }

    /* renamed from: a */
    public void mo43905a() {
        this.f921b.f936e.mo44098a();
        C2875ar.f932a.mo44090c("onCancelDownloads()", new Object[0]);
    }

    /* renamed from: a */
    public final void mo43906a(int i) {
        this.f921b.f936e.mo44098a();
        C2875ar.f932a.mo44090c("onCancelDownload(%d)", Integer.valueOf(i));
    }

    /* renamed from: a */
    public void mo43907a(int i, Bundle bundle) {
        this.f921b.f936e.mo44098a();
        C2875ar.f932a.mo44090c("onStartDownload(%d)", Integer.valueOf(i));
    }

    /* renamed from: a */
    public void mo43908a(Bundle bundle) {
        this.f921b.f936e.mo44098a();
        int i = bundle.getInt("error_code");
        C2875ar.f932a.mo44089b("onError(%d)", Integer.valueOf(i));
        this.f920a.mo44331b((Exception) new AssetPackException(i));
    }

    /* renamed from: a */
    public void mo43909a(Bundle bundle, Bundle bundle2) {
        this.f921b.f937f.mo44098a();
        C2875ar.f932a.mo44090c("onKeepAlive(%b)", Boolean.valueOf(bundle.getBoolean("keep_alive")));
    }

    /* renamed from: a */
    public void mo43910a(List<Bundle> list) {
        this.f921b.f936e.mo44098a();
        C2875ar.f932a.mo44090c("onGetSessionStates", new Object[0]);
    }

    /* renamed from: b */
    public void mo43911b() {
        this.f921b.f936e.mo44098a();
        C2875ar.f932a.mo44090c("onRemoveModule()", new Object[0]);
    }

    /* renamed from: b */
    public final void mo43912b(int i) {
        this.f921b.f936e.mo44098a();
        C2875ar.f932a.mo44090c("onGetSession(%d)", Integer.valueOf(i));
    }

    /* renamed from: b */
    public void mo43913b(Bundle bundle) {
        this.f921b.f936e.mo44098a();
        C2875ar.f932a.mo44090c("onNotifyChunkTransferred(%s, %s, %d, session=%d)", bundle.getString("module_name"), bundle.getString("slice_id"), Integer.valueOf(bundle.getInt("chunk_number")), Integer.valueOf(bundle.getInt("session_id")));
    }

    /* renamed from: b */
    public void mo43914b(Bundle bundle, Bundle bundle2) throws RemoteException {
        this.f921b.f936e.mo44098a();
        C2875ar.f932a.mo44090c("onGetChunkFileDescriptor", new Object[0]);
    }

    /* renamed from: c */
    public void mo43915c(Bundle bundle) {
        this.f921b.f936e.mo44098a();
        C2875ar.f932a.mo44090c("onNotifyModuleCompleted(%s, sessionId=%d)", bundle.getString("module_name"), Integer.valueOf(bundle.getInt("session_id")));
    }

    /* renamed from: c */
    public void mo43916c(Bundle bundle, Bundle bundle2) {
        this.f921b.f936e.mo44098a();
        C2875ar.f932a.mo44090c("onRequestDownloadInfo()", new Object[0]);
    }

    /* renamed from: d */
    public void mo43917d(Bundle bundle) {
        this.f921b.f936e.mo44098a();
        C2875ar.f932a.mo44090c("onNotifySessionFailed(%d)", Integer.valueOf(bundle.getInt("session_id")));
    }
}
