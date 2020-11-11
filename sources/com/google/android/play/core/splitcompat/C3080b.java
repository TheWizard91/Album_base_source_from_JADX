package com.google.android.play.core.splitcompat;

import java.io.File;

/* renamed from: com.google.android.play.core.splitcompat.b */
final class C3080b extends C3096r {

    /* renamed from: a */
    private final File f1359a;

    /* renamed from: b */
    private final String f1360b;

    C3080b(File file, String str) {
        if (file != null) {
            this.f1359a = file;
            if (str != null) {
                this.f1360b = str;
                return;
            }
            throw new NullPointerException("Null splitId");
        }
        throw new NullPointerException("Null splitFile");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final File mo44213a() {
        return this.f1359a;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final String mo44214b() {
        return this.f1360b;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof C3096r) {
            C3096r rVar = (C3096r) obj;
            return this.f1359a.equals(rVar.mo44213a()) && this.f1360b.equals(rVar.mo44214b());
        }
    }

    public final int hashCode() {
        return ((this.f1359a.hashCode() ^ 1000003) * 1000003) ^ this.f1360b.hashCode();
    }

    public final String toString() {
        String valueOf = String.valueOf(this.f1359a);
        String str = this.f1360b;
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 35 + str.length());
        sb.append("SplitFileInfo{splitFile=");
        sb.append(valueOf);
        sb.append(", splitId=");
        sb.append(str);
        sb.append("}");
        return sb.toString();
    }
}
