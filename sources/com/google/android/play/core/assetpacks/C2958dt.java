package com.google.android.play.core.assetpacks;

import com.google.android.play.core.internal.C3046cd;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/* renamed from: com.google.android.play.core.assetpacks.dt */
final class C2958dt {

    /* renamed from: a */
    private static final Pattern f1215a = Pattern.compile("[0-9]+-(NAM|LFH)\\.dat");

    /* renamed from: a */
    static List<File> m562a(File file, File file2) throws IOException {
        File[] fileArr;
        ArrayList arrayList = new ArrayList();
        File[] listFiles = file2.listFiles(C2957ds.f1214a);
        if (listFiles != null) {
            fileArr = new File[r2];
            for (File file3 : listFiles) {
                int parseInt = Integer.parseInt(file3.getName().split("-")[0]);
                if (parseInt > listFiles.length || fileArr[parseInt] != null) {
                    throw new C2909by("Metadata folder ordering corrupt.");
                }
                fileArr[parseInt] = file3;
            }
        } else {
            fileArr = new File[0];
        }
        for (File file4 : fileArr) {
            arrayList.add(file4);
            if (file4.getName().contains("LFH")) {
                FileInputStream fileInputStream = new FileInputStream(file4);
                try {
                    C2962dx a = new C2900bp(fileInputStream).mo44001a();
                    if (a.mo43992a() != null) {
                        File file5 = new File(file, a.mo43992a());
                        if (file5.exists()) {
                            arrayList.add(file5);
                            fileInputStream.close();
                        } else {
                            throw new C2909by(String.format("Missing asset file %s during slice reconstruction.", new Object[]{file5.getCanonicalPath()}));
                        }
                    } else {
                        throw new C2909by("Metadata files corrupt. Could not read local file header.");
                    }
                } catch (Throwable th) {
                    C3046cd.m768a(th, th);
                }
            }
        }
        return arrayList;
        throw th;
    }
}
