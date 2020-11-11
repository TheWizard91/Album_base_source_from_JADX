package com.google.android.play.core.assetpacks;

import com.google.android.play.core.internal.C2989aa;
import com.google.android.play.core.internal.C3047ce;
import java.io.File;

/* renamed from: com.google.android.play.core.assetpacks.bw */
final class C2907bw {

    /* renamed from: a */
    private static final C2989aa f1052a = new C2989aa("ExtractChunkTaskHandler");

    /* renamed from: b */
    private final byte[] f1053b = new byte[8192];

    /* renamed from: c */
    private final C2886bb f1054c;

    /* renamed from: d */
    private final C3047ce<C2982w> f1055d;

    /* renamed from: e */
    private final C3047ce<C2880aw> f1056e;

    /* renamed from: f */
    private final C2913cb f1057f;

    C2907bw(C2886bb bbVar, C3047ce<C2982w> ceVar, C3047ce<C2880aw> ceVar2, C2913cb cbVar) {
        this.f1054c = bbVar;
        this.f1055d = ceVar;
        this.f1056e = ceVar2;
        this.f1057f = cbVar;
    }

    /* renamed from: b */
    private final File m456b(C2906bv bvVar) {
        File a = this.f1054c.mo43942a(bvVar.f1129k, bvVar.f1043a, bvVar.f1044b, bvVar.f1045c);
        if (!a.exists()) {
            a.mkdirs();
        }
        return a;
    }

    /* JADX WARNING: Removed duplicated region for block: B:108:0x031d  */
    /* JADX WARNING: Removed duplicated region for block: B:127:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0181 A[Catch:{ all -> 0x033d, all -> 0x0343, IOException -> 0x0349 }] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x01b2 A[Catch:{ all -> 0x033d, all -> 0x0343, IOException -> 0x0349 }] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x01e6 A[Catch:{ all -> 0x033d, all -> 0x0343, IOException -> 0x0349 }] */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x02a1 A[SYNTHETIC, Splitter:B:96:0x02a1] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void mo44012a(com.google.android.play.core.assetpacks.C2906bv r23) {
        /*
            r22 = this;
            r1 = r22
            r2 = r23
            com.google.android.play.core.assetpacks.dr r0 = new com.google.android.play.core.assetpacks.dr
            com.google.android.play.core.assetpacks.bb r4 = r1.f1054c
            java.lang.String r5 = r2.f1129k
            int r6 = r2.f1043a
            long r7 = r2.f1044b
            java.lang.String r9 = r2.f1045c
            r3 = r0
            r3.<init>(r4, r5, r6, r7, r9)
            com.google.android.play.core.assetpacks.bb r10 = r1.f1054c
            java.lang.String r11 = r2.f1129k
            int r12 = r2.f1043a
            long r13 = r2.f1044b
            java.lang.String r15 = r2.f1045c
            java.io.File r3 = r10.mo43963f(r11, r12, r13, r15)
            boolean r4 = r3.exists()
            if (r4 != 0) goto L_0x002b
            r3.mkdirs()
        L_0x002b:
            r11 = 3
            r12 = 2
            r13 = 1
            r14 = 0
            java.io.InputStream r3 = r2.f1051i     // Catch:{ IOException -> 0x0349 }
            int r4 = r2.f1046d     // Catch:{ IOException -> 0x0349 }
            if (r4 == r13) goto L_0x0037
            r15 = r3
            goto L_0x0040
        L_0x0037:
            java.util.zip.GZIPInputStream r4 = new java.util.zip.GZIPInputStream     // Catch:{ IOException -> 0x0349 }
            byte[] r5 = r1.f1053b     // Catch:{ IOException -> 0x0349 }
            int r5 = r5.length     // Catch:{ IOException -> 0x0349 }
            r4.<init>(r3, r5)     // Catch:{ IOException -> 0x0349 }
            r15 = r4
        L_0x0040:
            int r3 = r2.f1047e     // Catch:{ all -> 0x033d }
            r16 = 0
            if (r3 <= 0) goto L_0x017e
            com.google.android.play.core.assetpacks.dq r3 = r0.mo44051a()     // Catch:{ all -> 0x033d }
            int r4 = r3.mo43988e()     // Catch:{ all -> 0x033d }
            int r5 = r2.f1047e     // Catch:{ all -> 0x033d }
            int r6 = r5 + -1
            if (r4 != r6) goto L_0x015e
            int r4 = r3.mo43984a()     // Catch:{ all -> 0x033d }
            if (r4 == r13) goto L_0x00d9
            if (r4 == r12) goto L_0x009c
            if (r4 != r11) goto L_0x0082
            com.google.android.play.core.internal.aa r4 = f1052a     // Catch:{ all -> 0x033d }
            java.lang.String r5 = "Resuming central directory from last chunk."
            java.lang.Object[] r6 = new java.lang.Object[r14]     // Catch:{ all -> 0x033d }
            r4.mo44087a(r5, r6)     // Catch:{ all -> 0x033d }
            long r3 = r3.mo43986c()     // Catch:{ all -> 0x033d }
            r0.mo44053a((java.io.InputStream) r15, (long) r3)     // Catch:{ all -> 0x033d }
            boolean r3 = r23.mo44011a()     // Catch:{ all -> 0x033d }
            if (r3 == 0) goto L_0x0078
        L_0x0074:
            r4 = r16
            goto L_0x017f
        L_0x0078:
            com.google.android.play.core.assetpacks.by r0 = new com.google.android.play.core.assetpacks.by     // Catch:{ all -> 0x033d }
            java.lang.String r3 = "Chunk has ended twice during central directory. This should not be possible with chunk sizes of 50MB."
            int r4 = r2.f1128j     // Catch:{ all -> 0x033d }
            r0.<init>((java.lang.String) r3, (int) r4)     // Catch:{ all -> 0x033d }
            throw r0     // Catch:{ all -> 0x033d }
        L_0x0082:
            com.google.android.play.core.assetpacks.by r0 = new com.google.android.play.core.assetpacks.by     // Catch:{ all -> 0x033d }
            java.lang.Object[] r4 = new java.lang.Object[r13]     // Catch:{ all -> 0x033d }
            int r3 = r3.mo43984a()     // Catch:{ all -> 0x033d }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x033d }
            r4[r14] = r3     // Catch:{ all -> 0x033d }
            java.lang.String r3 = "Slice checkpoint file corrupt. Unexpected FileExtractionStatus %s."
            java.lang.String r3 = java.lang.String.format(r3, r4)     // Catch:{ all -> 0x033d }
            int r4 = r2.f1128j     // Catch:{ all -> 0x033d }
            r0.<init>((java.lang.String) r3, (int) r4)     // Catch:{ all -> 0x033d }
            throw r0     // Catch:{ all -> 0x033d }
        L_0x009c:
            com.google.android.play.core.internal.aa r3 = f1052a     // Catch:{ all -> 0x033d }
            java.lang.String r4 = "Resuming zip entry from last chunk during local file header."
            java.lang.Object[] r5 = new java.lang.Object[r14]     // Catch:{ all -> 0x033d }
            r3.mo44087a(r4, r5)     // Catch:{ all -> 0x033d }
            com.google.android.play.core.assetpacks.bb r3 = r1.f1054c     // Catch:{ all -> 0x033d }
            java.lang.String r4 = r2.f1129k     // Catch:{ all -> 0x033d }
            int r5 = r2.f1043a     // Catch:{ all -> 0x033d }
            long r6 = r2.f1044b     // Catch:{ all -> 0x033d }
            java.lang.String r8 = r2.f1045c     // Catch:{ all -> 0x033d }
            r16 = r3
            r17 = r4
            r18 = r5
            r19 = r6
            r21 = r8
            java.io.File r3 = r16.mo43955d(r17, r18, r19, r21)     // Catch:{ all -> 0x033d }
            boolean r4 = r3.exists()     // Catch:{ all -> 0x033d }
            if (r4 == 0) goto L_0x00cf
            java.io.SequenceInputStream r4 = new java.io.SequenceInputStream     // Catch:{ all -> 0x033d }
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ all -> 0x033d }
            r5.<init>(r3)     // Catch:{ all -> 0x033d }
            r4.<init>(r5, r15)     // Catch:{ all -> 0x033d }
            goto L_0x017f
        L_0x00cf:
            com.google.android.play.core.assetpacks.by r0 = new com.google.android.play.core.assetpacks.by     // Catch:{ all -> 0x033d }
            java.lang.String r3 = "Checkpoint extension file not found."
            int r4 = r2.f1128j     // Catch:{ all -> 0x033d }
            r0.<init>((java.lang.String) r3, (int) r4)     // Catch:{ all -> 0x033d }
            throw r0     // Catch:{ all -> 0x033d }
        L_0x00d9:
            com.google.android.play.core.internal.aa r4 = f1052a     // Catch:{ all -> 0x033d }
            java.lang.Object[] r5 = new java.lang.Object[r13]     // Catch:{ all -> 0x033d }
            java.lang.String r6 = r3.mo43985b()     // Catch:{ all -> 0x033d }
            r5[r14] = r6     // Catch:{ all -> 0x033d }
            java.lang.String r6 = "Resuming zip entry from last chunk during file %s."
            r4.mo44087a(r6, r5)     // Catch:{ all -> 0x033d }
            java.io.File r4 = new java.io.File     // Catch:{ all -> 0x033d }
            java.lang.String r5 = r3.mo43985b()     // Catch:{ all -> 0x033d }
            r4.<init>(r5)     // Catch:{ all -> 0x033d }
            boolean r5 = r4.exists()     // Catch:{ all -> 0x033d }
            if (r5 == 0) goto L_0x0154
            java.io.RandomAccessFile r5 = new java.io.RandomAccessFile     // Catch:{ all -> 0x033d }
            java.lang.String r6 = "rw"
            r5.<init>(r4, r6)     // Catch:{ all -> 0x033d }
            long r6 = r3.mo43986c()     // Catch:{ all -> 0x033d }
            r5.seek(r6)     // Catch:{ all -> 0x033d }
            long r6 = r3.mo43987d()     // Catch:{ all -> 0x033d }
        L_0x0109:
            byte[] r3 = r1.f1053b     // Catch:{ all -> 0x033d }
            int r3 = r3.length     // Catch:{ all -> 0x033d }
            long r8 = (long) r3     // Catch:{ all -> 0x033d }
            long r8 = java.lang.Math.min(r6, r8)     // Catch:{ all -> 0x033d }
            int r3 = (int) r8     // Catch:{ all -> 0x033d }
            byte[] r8 = r1.f1053b     // Catch:{ all -> 0x033d }
            int r8 = r15.read(r8, r14, r3)     // Catch:{ all -> 0x033d }
            int r8 = java.lang.Math.max(r8, r14)     // Catch:{ all -> 0x033d }
            if (r8 > 0) goto L_0x011f
            goto L_0x0124
        L_0x011f:
            byte[] r9 = r1.f1053b     // Catch:{ all -> 0x033d }
            r5.write(r9, r14, r8)     // Catch:{ all -> 0x033d }
        L_0x0124:
            long r10 = (long) r8     // Catch:{ all -> 0x033d }
            long r9 = r6 - r10
            r6 = 0
            int r6 = (r9 > r6 ? 1 : (r9 == r6 ? 0 : -1))
            if (r6 > 0) goto L_0x012e
            goto L_0x0133
        L_0x012e:
            if (r8 <= 0) goto L_0x0133
            r6 = r9
            r11 = 3
            goto L_0x0109
        L_0x0133:
            long r6 = r5.length()     // Catch:{ all -> 0x033d }
            r5.close()     // Catch:{ all -> 0x033d }
            if (r8 == r3) goto L_0x017e
            com.google.android.play.core.internal.aa r3 = f1052a     // Catch:{ all -> 0x033d }
            java.lang.String r5 = "Chunk has ended while resuming the previous chunks file content."
            java.lang.Object[] r8 = new java.lang.Object[r14]     // Catch:{ all -> 0x033d }
            r3.mo44087a(r5, r8)     // Catch:{ all -> 0x033d }
            java.lang.String r4 = r4.getCanonicalPath()     // Catch:{ all -> 0x033d }
            int r11 = r2.f1047e     // Catch:{ all -> 0x033d }
            r3 = r0
            r5 = r6
            r7 = r9
            r9 = r11
            r3.mo44054a(r4, r5, r7, r9)     // Catch:{ all -> 0x033d }
            goto L_0x0074
        L_0x0154:
            com.google.android.play.core.assetpacks.by r0 = new com.google.android.play.core.assetpacks.by     // Catch:{ all -> 0x033d }
            java.lang.String r3 = "Partial file specified in checkpoint does not exist. Corrupt directory."
            int r4 = r2.f1128j     // Catch:{ all -> 0x033d }
            r0.<init>((java.lang.String) r3, (int) r4)     // Catch:{ all -> 0x033d }
            throw r0     // Catch:{ all -> 0x033d }
        L_0x015e:
            com.google.android.play.core.assetpacks.by r0 = new com.google.android.play.core.assetpacks.by     // Catch:{ all -> 0x033d }
            java.lang.Object[] r4 = new java.lang.Object[r12]     // Catch:{ all -> 0x033d }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ all -> 0x033d }
            r4[r14] = r5     // Catch:{ all -> 0x033d }
            int r3 = r3.mo43988e()     // Catch:{ all -> 0x033d }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x033d }
            r4[r13] = r3     // Catch:{ all -> 0x033d }
            java.lang.String r3 = "Trying to resume with chunk number %s when previously processed chunk was number %s."
            java.lang.String r3 = java.lang.String.format(r3, r4)     // Catch:{ all -> 0x033d }
            int r4 = r2.f1128j     // Catch:{ all -> 0x033d }
            r0.<init>((java.lang.String) r3, (int) r4)     // Catch:{ all -> 0x033d }
            throw r0     // Catch:{ all -> 0x033d }
        L_0x017e:
            r4 = r15
        L_0x017f:
            if (r4 == 0) goto L_0x0298
            com.google.android.play.core.assetpacks.bp r3 = new com.google.android.play.core.assetpacks.bp     // Catch:{ all -> 0x033d }
            r3.<init>(r4)     // Catch:{ all -> 0x033d }
            java.io.File r5 = r22.m456b(r23)     // Catch:{ all -> 0x033d }
        L_0x018a:
            com.google.android.play.core.assetpacks.dx r6 = r3.mo44001a()     // Catch:{ all -> 0x033d }
            java.lang.String r7 = r6.mo43992a()     // Catch:{ all -> 0x033d }
            if (r7 == 0) goto L_0x01a0
            java.lang.String r7 = r6.mo43992a()     // Catch:{ all -> 0x033d }
            java.lang.String r8 = "/"
            boolean r7 = r7.endsWith(r8)     // Catch:{ all -> 0x033d }
            if (r7 != 0) goto L_0x01ed
        L_0x01a0:
            boolean r7 = r6.mo43995d()     // Catch:{ all -> 0x033d }
            if (r7 != 0) goto L_0x01ed
            boolean r7 = r3.mo44003c()     // Catch:{ all -> 0x033d }
            if (r7 != 0) goto L_0x01ed
            int r7 = r6.mo43994c()     // Catch:{ all -> 0x033d }
            if (r7 != 0) goto L_0x01e6
            byte[] r7 = r6.mo43996e()     // Catch:{ all -> 0x033d }
            r0.mo44055a((byte[]) r7)     // Catch:{ all -> 0x033d }
            java.io.File r7 = new java.io.File     // Catch:{ all -> 0x033d }
            java.lang.String r8 = r6.mo43992a()     // Catch:{ all -> 0x033d }
            r7.<init>(r5, r8)     // Catch:{ all -> 0x033d }
            java.io.File r8 = r7.getParentFile()     // Catch:{ all -> 0x033d }
            r8.mkdirs()     // Catch:{ all -> 0x033d }
            java.io.FileOutputStream r8 = new java.io.FileOutputStream     // Catch:{ all -> 0x033d }
            r8.<init>(r7)     // Catch:{ all -> 0x033d }
            byte[] r7 = r1.f1053b     // Catch:{ all -> 0x033d }
            int r7 = r3.read(r7)     // Catch:{ all -> 0x033d }
        L_0x01d4:
            if (r7 <= 0) goto L_0x01e2
            byte[] r9 = r1.f1053b     // Catch:{ all -> 0x033d }
            r8.write(r9, r14, r7)     // Catch:{ all -> 0x033d }
            byte[] r7 = r1.f1053b     // Catch:{ all -> 0x033d }
            int r7 = r3.read(r7)     // Catch:{ all -> 0x033d }
            goto L_0x01d4
        L_0x01e2:
            r8.close()     // Catch:{ all -> 0x033d }
            goto L_0x01ed
        L_0x01e6:
            byte[] r7 = r6.mo43996e()     // Catch:{ all -> 0x033d }
            r0.mo44057a((byte[]) r7, (java.io.InputStream) r3)     // Catch:{ all -> 0x033d }
        L_0x01ed:
            boolean r7 = r3.mo44002b()     // Catch:{ all -> 0x033d }
            if (r7 != 0) goto L_0x01f9
            boolean r7 = r3.mo44003c()     // Catch:{ all -> 0x033d }
            if (r7 == 0) goto L_0x018a
        L_0x01f9:
            boolean r5 = r3.mo44003c()     // Catch:{ all -> 0x033d }
            if (r5 == 0) goto L_0x020f
            com.google.android.play.core.internal.aa r5 = f1052a     // Catch:{ all -> 0x033d }
            java.lang.String r7 = "Writing central directory metadata."
            java.lang.Object[] r8 = new java.lang.Object[r14]     // Catch:{ all -> 0x033d }
            r5.mo44087a(r7, r8)     // Catch:{ all -> 0x033d }
            byte[] r5 = r6.mo43996e()     // Catch:{ all -> 0x033d }
            r0.mo44057a((byte[]) r5, (java.io.InputStream) r4)     // Catch:{ all -> 0x033d }
        L_0x020f:
            boolean r4 = r23.mo44011a()     // Catch:{ all -> 0x033d }
            if (r4 != 0) goto L_0x0298
            boolean r4 = r6.mo43995d()     // Catch:{ all -> 0x033d }
            if (r4 == 0) goto L_0x022e
            com.google.android.play.core.internal.aa r3 = f1052a     // Catch:{ all -> 0x033d }
            java.lang.String r4 = "Writing slice checkpoint for partial local file header."
            java.lang.Object[] r5 = new java.lang.Object[r14]     // Catch:{ all -> 0x033d }
            r3.mo44087a(r4, r5)     // Catch:{ all -> 0x033d }
            byte[] r3 = r6.mo43996e()     // Catch:{ all -> 0x033d }
            int r4 = r2.f1047e     // Catch:{ all -> 0x033d }
            r0.mo44056a((byte[]) r3, (int) r4)     // Catch:{ all -> 0x033d }
            goto L_0x0298
        L_0x022e:
            boolean r4 = r3.mo44003c()     // Catch:{ all -> 0x033d }
            if (r4 == 0) goto L_0x0243
            com.google.android.play.core.internal.aa r3 = f1052a     // Catch:{ all -> 0x033d }
            java.lang.String r4 = "Writing slice checkpoint for central directory."
            java.lang.Object[] r5 = new java.lang.Object[r14]     // Catch:{ all -> 0x033d }
            r3.mo44087a(r4, r5)     // Catch:{ all -> 0x033d }
            int r3 = r2.f1047e     // Catch:{ all -> 0x033d }
            r0.mo44052a((int) r3)     // Catch:{ all -> 0x033d }
            goto L_0x0298
        L_0x0243:
            int r4 = r6.mo43994c()     // Catch:{ all -> 0x033d }
            if (r4 != 0) goto L_0x0279
            com.google.android.play.core.internal.aa r4 = f1052a     // Catch:{ all -> 0x033d }
            java.lang.String r5 = "Writing slice checkpoint for partial file."
            java.lang.Object[] r7 = new java.lang.Object[r14]     // Catch:{ all -> 0x033d }
            r4.mo44087a(r5, r7)     // Catch:{ all -> 0x033d }
            java.io.File r4 = new java.io.File     // Catch:{ all -> 0x033d }
            java.io.File r5 = r22.m456b(r23)     // Catch:{ all -> 0x033d }
            java.lang.String r7 = r6.mo43992a()     // Catch:{ all -> 0x033d }
            r4.<init>(r5, r7)     // Catch:{ all -> 0x033d }
            long r5 = r6.mo43993b()     // Catch:{ all -> 0x033d }
            long r7 = r3.mo44004d()     // Catch:{ all -> 0x033d }
            long r5 = r5 - r7
            long r7 = r4.length()     // Catch:{ all -> 0x033d }
            int r7 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r7 != 0) goto L_0x0271
            goto L_0x028a
        L_0x0271:
            com.google.android.play.core.assetpacks.by r0 = new com.google.android.play.core.assetpacks.by     // Catch:{ all -> 0x033d }
            java.lang.String r3 = "Partial file is of unexpected size."
            r0.<init>(r3)     // Catch:{ all -> 0x033d }
            throw r0     // Catch:{ all -> 0x033d }
        L_0x0279:
            com.google.android.play.core.internal.aa r4 = f1052a     // Catch:{ all -> 0x033d }
            java.lang.String r5 = "Writing slice checkpoint for partial unextractable file."
            java.lang.Object[] r6 = new java.lang.Object[r14]     // Catch:{ all -> 0x033d }
            r4.mo44087a(r5, r6)     // Catch:{ all -> 0x033d }
            java.io.File r4 = r0.mo44058b()     // Catch:{ all -> 0x033d }
            long r5 = r4.length()     // Catch:{ all -> 0x033d }
        L_0x028a:
            java.lang.String r4 = r4.getCanonicalPath()     // Catch:{ all -> 0x033d }
            long r7 = r3.mo44004d()     // Catch:{ all -> 0x033d }
            int r9 = r2.f1047e     // Catch:{ all -> 0x033d }
            r3 = r0
            r3.mo44054a(r4, r5, r7, r9)     // Catch:{ all -> 0x033d }
        L_0x0298:
            r15.close()     // Catch:{ IOException -> 0x0349 }
            boolean r3 = r23.mo44011a()
            if (r3 == 0) goto L_0x02c1
            int r3 = r2.f1047e     // Catch:{ IOException -> 0x02a7 }
            r0.mo44059b(r3)     // Catch:{ IOException -> 0x02a7 }
            goto L_0x02c1
        L_0x02a7:
            r0 = move-exception
            com.google.android.play.core.internal.aa r3 = f1052a
            java.lang.Object[] r4 = new java.lang.Object[r13]
            java.lang.String r5 = r0.getMessage()
            r4[r14] = r5
            java.lang.String r5 = "Writing extraction finished checkpoint failed with %s."
            r3.mo44089b(r5, r4)
            com.google.android.play.core.assetpacks.by r3 = new com.google.android.play.core.assetpacks.by
            int r2 = r2.f1128j
            java.lang.String r4 = "Writing extraction finished checkpoint failed."
            r3.<init>(r4, r0, r2)
            throw r3
        L_0x02c1:
            com.google.android.play.core.internal.aa r0 = f1052a
            r3 = 4
            java.lang.Object[] r3 = new java.lang.Object[r3]
            int r4 = r2.f1047e
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r3[r14] = r4
            java.lang.String r4 = r2.f1045c
            r3[r13] = r4
            java.lang.String r4 = r2.f1129k
            r3[r12] = r4
            int r4 = r2.f1128j
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r5 = 3
            r3[r5] = r4
            java.lang.String r4 = "Extraction finished for chunk %s of slice %s of pack %s of session %s."
            r0.mo44090c(r4, r3)
            com.google.android.play.core.internal.ce<com.google.android.play.core.assetpacks.w> r0 = r1.f1055d
            java.lang.Object r0 = r0.mo44145a()
            com.google.android.play.core.assetpacks.w r0 = (com.google.android.play.core.assetpacks.C2982w) r0
            int r3 = r2.f1128j
            java.lang.String r4 = r2.f1129k
            java.lang.String r5 = r2.f1045c
            int r6 = r2.f1047e
            r0.mo43923a(r3, r4, r5, r6)
            java.io.InputStream r0 = r2.f1051i     // Catch:{ IOException -> 0x02fd }
            r0.close()     // Catch:{ IOException -> 0x02fd }
            goto L_0x0318
        L_0x02fd:
            r0 = move-exception
            com.google.android.play.core.internal.aa r0 = f1052a
            r3 = 3
            java.lang.Object[] r4 = new java.lang.Object[r3]
            int r3 = r2.f1047e
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r4[r14] = r3
            java.lang.String r3 = r2.f1045c
            r4[r13] = r3
            java.lang.String r3 = r2.f1129k
            r4[r12] = r3
            java.lang.String r3 = "Could not close file for chunk %s of slice %s of pack %s."
            r0.mo44091d(r3, r4)
        L_0x0318:
            int r0 = r2.f1050h
            r3 = 3
            if (r0 != r3) goto L_0x033c
            com.google.android.play.core.internal.ce<com.google.android.play.core.assetpacks.aw> r0 = r1.f1056e
            java.lang.Object r0 = r0.mo44145a()
            com.google.android.play.core.assetpacks.aw r0 = (com.google.android.play.core.assetpacks.C2880aw) r0
            java.lang.String r3 = r2.f1129k
            long r7 = r2.f1049g
            r4 = 3
            r5 = 0
            com.google.android.play.core.assetpacks.cb r6 = r1.f1057f
            double r9 = r6.mo44014a(r3, r2)
            r2 = r3
            r3 = r4
            r4 = r5
            r5 = r7
            com.google.android.play.core.assetpacks.AssetPackState r2 = com.google.android.play.core.assetpacks.AssetPackState.m304a(r2, r3, r4, r5, r7, r9)
            r0.mo43934a((com.google.android.play.core.assetpacks.AssetPackState) r2)
        L_0x033c:
            return
        L_0x033d:
            r0 = move-exception
            r3 = r0
            r15.close()     // Catch:{ all -> 0x0343 }
            goto L_0x0348
        L_0x0343:
            r0 = move-exception
            r4 = r0
            com.google.android.play.core.internal.C3046cd.m768a(r3, r4)     // Catch:{ IOException -> 0x0349 }
        L_0x0348:
            throw r3     // Catch:{ IOException -> 0x0349 }
        L_0x0349:
            r0 = move-exception
            com.google.android.play.core.internal.aa r3 = f1052a
            java.lang.Object[] r4 = new java.lang.Object[r13]
            java.lang.String r5 = r0.getMessage()
            r4[r14] = r5
            java.lang.String r5 = "IOException during extraction %s."
            r3.mo44089b(r5, r4)
            com.google.android.play.core.assetpacks.by r3 = new com.google.android.play.core.assetpacks.by
            r4 = 4
            java.lang.Object[] r4 = new java.lang.Object[r4]
            int r5 = r2.f1047e
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r4[r14] = r5
            java.lang.String r5 = r2.f1045c
            r4[r13] = r5
            java.lang.String r5 = r2.f1129k
            r4[r12] = r5
            int r5 = r2.f1128j
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r6 = 3
            r4[r6] = r5
            java.lang.String r5 = "Error extracting chunk %s of slice %s of pack %s of session %s."
            java.lang.String r4 = java.lang.String.format(r5, r4)
            int r2 = r2.f1128j
            r3.<init>(r4, r0, r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.play.core.assetpacks.C2907bw.mo44012a(com.google.android.play.core.assetpacks.bv):void");
    }
}
