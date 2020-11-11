package com.google.android.play.core.assetpacks;

import java.io.File;
import java.io.FilenameFilter;

/* renamed from: com.google.android.play.core.assetpacks.ds */
final /* synthetic */ class C2957ds implements FilenameFilter {

    /* renamed from: a */
    static final FilenameFilter f1214a = new C2957ds();

    private C2957ds() {
    }

    public final boolean accept(File file, String str) {
        return C2958dt.f1215a.matcher(str).matches();
    }
}
