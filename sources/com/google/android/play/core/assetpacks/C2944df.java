package com.google.android.play.core.assetpacks;

import com.google.android.play.core.internal.C2989aa;
import java.io.File;
import java.io.IOException;

/* renamed from: com.google.android.play.core.assetpacks.df */
final class C2944df {

    /* renamed from: a */
    private static final C2989aa f1173a = new C2989aa("MergeSliceTaskHandler");

    /* renamed from: b */
    private final C2886bb f1174b;

    C2944df(C2886bb bbVar) {
        this.f1174b = bbVar;
    }

    /* renamed from: a */
    private static void m534a(File file, File file2) {
        if (file.isDirectory()) {
            file2.mkdirs();
            for (File file3 : file.listFiles()) {
                m534a(file3, new File(file2, file3.getName()));
            }
            if (!file.delete()) {
                String valueOf = String.valueOf(file);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 28);
                sb.append("Unable to delete directory: ");
                sb.append(valueOf);
                throw new C2909by(sb.toString());
            }
        } else if (file2.exists()) {
            String valueOf2 = String.valueOf(file2);
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 51);
            sb2.append("File clashing with existing file from other slice: ");
            sb2.append(valueOf2);
            throw new C2909by(sb2.toString());
        } else if (!file.renameTo(file2)) {
            String valueOf3 = String.valueOf(file);
            StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf3).length() + 21);
            sb3.append("Unable to move file: ");
            sb3.append(valueOf3);
            throw new C2909by(sb3.toString());
        }
    }

    /* renamed from: a */
    public final void mo44046a(C2943de deVar) {
        File b = this.f1174b.mo43948b(deVar.f1129k, deVar.f1170a, deVar.f1171b, deVar.f1172c);
        if (b.exists()) {
            File c = this.f1174b.mo43950c(deVar.f1129k, deVar.f1170a, deVar.f1171b);
            if (!c.exists()) {
                c.mkdirs();
            }
            m534a(b, c);
            try {
                this.f1174b.mo43943a(deVar.f1129k, deVar.f1170a, deVar.f1171b, this.f1174b.mo43954d(deVar.f1129k, deVar.f1170a, deVar.f1171b) + 1);
            } catch (IOException e) {
                f1173a.mo44089b("Writing merge checkpoint failed with %s.", e.getMessage());
                throw new C2909by("Writing merge checkpoint failed.", e, deVar.f1128j);
            }
        } else {
            throw new C2909by(String.format("Cannot find verified files for slice %s.", new Object[]{deVar.f1172c}), deVar.f1128j);
        }
    }
}
