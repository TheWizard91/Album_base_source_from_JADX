package com.google.android.play.core.assetpacks;

import com.google.android.play.core.internal.C2989aa;
import com.google.android.play.core.internal.C3005aq;
import com.google.android.play.core.internal.C3046cd;
import com.google.android.play.core.internal.C3047ce;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/* renamed from: com.google.android.play.core.assetpacks.do */
final class C2953do {

    /* renamed from: a */
    private static final C2989aa f1201a = new C2989aa("PatchSliceTaskHandler");

    /* renamed from: b */
    private final C2886bb f1202b;

    /* renamed from: c */
    private final C3047ce<C2982w> f1203c;

    C2953do(C2886bb bbVar, C3047ce<C2982w> ceVar) {
        this.f1202b = bbVar;
        this.f1203c = ceVar;
    }

    /* renamed from: a */
    public final void mo44050a(C2952dn dnVar) {
        InputStream inputStream;
        Throwable th;
        C2952dn dnVar2 = dnVar;
        File a = this.f1202b.mo43941a(dnVar2.f1129k, dnVar2.f1193a, dnVar2.f1194b);
        C2886bb bbVar = this.f1202b;
        String str = dnVar2.f1129k;
        int i = dnVar2.f1193a;
        long j = dnVar2.f1194b;
        File file = new File(bbVar.mo43947b(str, i, j), dnVar2.f1198f);
        try {
            inputStream = dnVar2.f1200h;
            if (dnVar2.f1197e == 2) {
                inputStream = new GZIPInputStream(inputStream, 8192);
            }
            C2890bf bfVar = new C2890bf(a, file);
            File file2 = new File(this.f1202b.mo43963f(dnVar2.f1129k, dnVar2.f1195c, dnVar2.f1196d, dnVar2.f1198f), "slice.zip.tmp");
            if (file2.getParentFile() != null) {
                if (!file2.getParentFile().exists()) {
                    file2.getParentFile().mkdirs();
                }
            }
            C3005aq.m662a(bfVar, inputStream, new FileOutputStream(file2), dnVar2.f1199g);
            if (file2.renameTo(this.f1202b.mo43960e(dnVar2.f1129k, dnVar2.f1195c, dnVar2.f1196d, dnVar2.f1198f))) {
                inputStream.close();
                f1201a.mo44090c("Patching finished for slice %s of pack %s.", dnVar2.f1198f, dnVar2.f1129k);
                this.f1203c.mo44145a().mo43923a(dnVar2.f1128j, dnVar2.f1129k, dnVar2.f1198f, 0);
                try {
                    dnVar2.f1200h.close();
                    return;
                } catch (IOException e) {
                    f1201a.mo44091d("Could not close file for slice %s of pack %s.", dnVar2.f1198f, dnVar2.f1129k);
                    return;
                }
            } else {
                throw new C2909by(String.format("Error moving patch for slice %s of pack %s.", new Object[]{dnVar2.f1198f, dnVar2.f1129k}), dnVar2.f1128j);
            }
        } catch (IOException e2) {
            f1201a.mo44089b("IOException during patching %s.", e2.getMessage());
            throw new C2909by(String.format("Error patching slice %s of pack %s.", new Object[]{dnVar2.f1198f, dnVar2.f1129k}), e2, dnVar2.f1128j);
        } catch (Throwable th2) {
            C3046cd.m768a(th, th2);
        }
        throw th;
    }
}
