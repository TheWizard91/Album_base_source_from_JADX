package com.google.android.play.core.assetpacks;

import android.os.ParcelFileDescriptor;
import com.google.android.play.core.internal.C3047ce;
import com.google.android.play.core.tasks.Tasks;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

/* renamed from: com.google.android.play.core.assetpacks.bn */
final class C2898bn {

    /* renamed from: a */
    private final C3047ce<C2982w> f1009a;

    C2898bn(C3047ce<C2982w> ceVar) {
        this.f1009a = ceVar;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final InputStream mo44000a(int i, String str, String str2, int i2) {
        try {
            return new ParcelFileDescriptor.AutoCloseInputStream((ParcelFileDescriptor) Tasks.await(this.f1009a.mo44145a().mo43926b(i, str, str2, i2)));
        } catch (ExecutionException e) {
            throw new C2909by(String.format("Error opening chunk file, session %s packName %s sliceId %s, chunkNumber %s", new Object[]{Integer.valueOf(i), str, str2, Integer.valueOf(i2)}), e, i);
        } catch (InterruptedException e2) {
            throw new C2909by("Extractor was interrupted while waiting for chunk file.", e2, i);
        }
    }
}
