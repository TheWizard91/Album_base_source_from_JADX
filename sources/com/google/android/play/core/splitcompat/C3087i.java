package com.google.android.play.core.splitcompat;

import android.util.Log;
import com.google.android.play.core.internal.C3046cd;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/* renamed from: com.google.android.play.core.splitcompat.i */
final class C3087i implements C3089k {

    /* renamed from: a */
    final /* synthetic */ Set f1373a;

    /* renamed from: b */
    final /* synthetic */ C3096r f1374b;

    /* renamed from: c */
    final /* synthetic */ ZipFile f1375c;

    C3087i(Set set, C3096r rVar, ZipFile zipFile) {
        this.f1373a = set;
        this.f1374b = rVar;
        this.f1375c = zipFile;
    }

    /* renamed from: a */
    public final void mo44233a(C3090l lVar, File file, boolean z) throws IOException {
        FileOutputStream fileOutputStream;
        this.f1373a.add(file);
        if (!z) {
            Log.i("SplitCompat", String.format("NativeLibraryExtractor: split '%s' has native library '%s' that does not exist; extracting from '%s!%s' to '%s'", new Object[]{this.f1374b.mo44214b(), lVar.f1376a, this.f1374b.mo44213a().getAbsolutePath(), lVar.f1377b.getName(), file.getAbsolutePath()}));
            ZipFile zipFile = this.f1375c;
            ZipEntry zipEntry = lVar.f1377b;
            int i = C3091m.f1378a;
            byte[] bArr = new byte[4096];
            InputStream inputStream = zipFile.getInputStream(zipEntry);
            try {
                fileOutputStream = new FileOutputStream(file);
                while (true) {
                    int read = inputStream.read(bArr);
                    if (read <= 0) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, read);
                }
                fileOutputStream.close();
                if (inputStream != null) {
                    inputStream.close();
                    return;
                }
                return;
            } catch (Throwable th) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Throwable th2) {
                        C3046cd.m768a(th, th2);
                    }
                }
                throw th;
            }
        } else {
            return;
        }
        throw th;
    }
}
