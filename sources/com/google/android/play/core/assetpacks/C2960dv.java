package com.google.android.play.core.assetpacks;

import com.google.android.play.core.internal.C2989aa;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/* renamed from: com.google.android.play.core.assetpacks.dv */
final class C2960dv {

    /* renamed from: a */
    private static final C2989aa f1220a = new C2989aa("VerifySliceTaskHandler");

    /* renamed from: b */
    private final C2886bb f1221b;

    C2960dv(C2886bb bbVar) {
        this.f1221b = bbVar;
    }

    /* renamed from: a */
    private final void m564a(C2959du duVar, File file) {
        try {
            File f = this.f1221b.mo43963f(duVar.f1129k, duVar.f1216a, duVar.f1217b, duVar.f1218c);
            if (f.exists()) {
                try {
                    if (C2942dd.m527a(C2958dt.m562a(file, f)).equals(duVar.f1219d)) {
                        f1220a.mo44090c("Verification of slice %s of pack %s successful.", duVar.f1218c, duVar.f1129k);
                        return;
                    }
                    throw new C2909by(String.format("Verification failed for slice %s.", new Object[]{duVar.f1218c}), duVar.f1128j);
                } catch (NoSuchAlgorithmException e) {
                    throw new C2909by("SHA256 algorithm not supported.", e, duVar.f1128j);
                } catch (IOException e2) {
                    throw new C2909by(String.format("Could not digest file during verification for slice %s.", new Object[]{duVar.f1218c}), e2, duVar.f1128j);
                }
            } else {
                throw new C2909by(String.format("Cannot find metadata files for slice %s.", new Object[]{duVar.f1218c}), duVar.f1128j);
            }
        } catch (IOException e3) {
            throw new C2909by(String.format("Could not reconstruct slice archive during verification for slice %s.", new Object[]{duVar.f1218c}), e3, duVar.f1128j);
        }
    }

    /* renamed from: a */
    public final void mo44063a(C2959du duVar) {
        File a = this.f1221b.mo43942a(duVar.f1129k, duVar.f1216a, duVar.f1217b, duVar.f1218c);
        if (a.exists()) {
            m564a(duVar, a);
            File b = this.f1221b.mo43948b(duVar.f1129k, duVar.f1216a, duVar.f1217b, duVar.f1218c);
            if (!b.exists()) {
                b.mkdirs();
            }
            if (!a.renameTo(b)) {
                throw new C2909by(String.format("Failed to move slice %s after verification.", new Object[]{duVar.f1218c}), duVar.f1128j);
            }
            return;
        }
        throw new C2909by(String.format("Cannot find unverified files for slice %s.", new Object[]{duVar.f1218c}), duVar.f1128j);
    }
}
