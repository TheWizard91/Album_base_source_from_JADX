package com.google.android.play.core.assetpacks;

import com.google.android.play.core.tasks.OnSuccessListener;
import java.util.List;

/* renamed from: com.google.android.play.core.assetpacks.e */
final /* synthetic */ class C2963e implements OnSuccessListener {

    /* renamed from: a */
    private final C2886bb f1223a;

    private C2963e(C2886bb bbVar) {
        this.f1223a = bbVar;
    }

    /* renamed from: a */
    static OnSuccessListener m573a(C2886bb bbVar) {
        return new C2963e(bbVar);
    }

    public final void onSuccess(Object obj) {
        this.f1223a.mo43944a((List<String>) (List) obj);
    }
}
