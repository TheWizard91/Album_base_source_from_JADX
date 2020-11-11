package com.facebook.soloader;

import android.content.Context;
import com.facebook.soloader.UnpackingSoSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public final class ExoSoSource extends UnpackingSoSource {
    public ExoSoSource(Context context, String name) {
        super(context, name);
    }

    /* access modifiers changed from: protected */
    public UnpackingSoSource.Unpacker makeUnpacker() throws IOException {
        return new ExoUnpacker(this, this);
    }

    private final class ExoUnpacker extends UnpackingSoSource.Unpacker {
        /* access modifiers changed from: private */
        public final FileDso[] mDsos;
        final /* synthetic */ ExoSoSource this$0;

        /* JADX WARNING: Code restructure failed: missing block: B:41:0x012c, code lost:
            r16 = r0;
            r21 = r2;
            r19 = r3;
            r18 = r6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
            r13.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x0137, code lost:
            r12.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:46:0x0146, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:47:0x0147, code lost:
            r2 = r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:52:0x0150, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:53:0x0151, code lost:
            r3 = r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:55:?, code lost:
            r13.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:56:0x0156, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:59:?, code lost:
            r2.addSuppressed(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:64:?, code lost:
            throw r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:65:0x0163, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:66:0x0164, code lost:
            r3 = r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:68:?, code lost:
            r12.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:69:0x0169, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:70:0x016a, code lost:
            r2.addSuppressed(r0);
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        ExoUnpacker(com.facebook.soloader.ExoSoSource r21, com.facebook.soloader.UnpackingSoSource r22) throws java.io.IOException {
            /*
                r20 = this;
                r1 = r20
                r0 = r21
                r1.this$0 = r0
                r20.<init>()
                android.content.Context r2 = r0.mContext
                java.io.File r0 = new java.io.File
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r4 = "/data/local/tmp/exopackage/"
                java.lang.StringBuilder r3 = r3.append(r4)
                java.lang.String r4 = r2.getPackageName()
                java.lang.StringBuilder r3 = r3.append(r4)
                java.lang.String r4 = "/native-libs/"
                java.lang.StringBuilder r3 = r3.append(r4)
                java.lang.String r3 = r3.toString()
                r0.<init>(r3)
                r3 = r0
                java.util.ArrayList r0 = new java.util.ArrayList
                r0.<init>()
                r4 = r0
                java.util.LinkedHashSet r0 = new java.util.LinkedHashSet
                r0.<init>()
                r5 = r0
                java.lang.String[] r0 = com.facebook.soloader.SysUtil.getSupportedAbis()
                int r6 = r0.length
                r8 = 0
            L_0x0040:
                if (r8 >= r6) goto L_0x016f
                r9 = r0[r8]
                java.io.File r10 = new java.io.File
                r10.<init>(r3, r9)
                boolean r11 = r10.isDirectory()
                if (r11 != 0) goto L_0x0059
                r16 = r0
                r21 = r2
                r19 = r3
                r18 = r6
                goto L_0x013a
            L_0x0059:
                r5.add(r9)
                java.io.File r11 = new java.io.File
                java.lang.String r12 = "metadata.txt"
                r11.<init>(r10, r12)
                boolean r12 = r11.isFile()
                if (r12 != 0) goto L_0x0073
                r16 = r0
                r21 = r2
                r19 = r3
                r18 = r6
                goto L_0x013a
            L_0x0073:
                java.io.FileReader r12 = new java.io.FileReader
                r12.<init>(r11)
                java.io.BufferedReader r13 = new java.io.BufferedReader     // Catch:{ all -> 0x015c }
                r13.<init>(r12)     // Catch:{ all -> 0x015c }
            L_0x007e:
                java.lang.String r14 = r13.readLine()     // Catch:{ all -> 0x0149 }
                r15 = r14
                if (r14 == 0) goto L_0x012c
                int r14 = r15.length()     // Catch:{ all -> 0x0149 }
                if (r14 != 0) goto L_0x008c
                goto L_0x007e
            L_0x008c:
                r14 = 32
                int r14 = r15.indexOf(r14)     // Catch:{ all -> 0x0149 }
                r7 = -1
                if (r14 == r7) goto L_0x0106
                java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0149 }
                r7.<init>()     // Catch:{ all -> 0x0149 }
                r16 = r0
                r21 = r2
                r0 = 0
                java.lang.String r2 = r15.substring(r0, r14)     // Catch:{ all -> 0x0101 }
                java.lang.StringBuilder r2 = r7.append(r2)     // Catch:{ all -> 0x0101 }
                java.lang.String r7 = ".so"
                java.lang.StringBuilder r2 = r2.append(r7)     // Catch:{ all -> 0x0101 }
                java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0101 }
                int r7 = r4.size()     // Catch:{ all -> 0x0101 }
                r17 = 0
                r18 = 0
                r0 = r18
            L_0x00bb:
                if (r0 >= r7) goto L_0x00d7
                java.lang.Object r18 = r4.get(r0)     // Catch:{ all -> 0x0101 }
                r19 = r3
                r3 = r18
                com.facebook.soloader.ExoSoSource$FileDso r3 = (com.facebook.soloader.ExoSoSource.FileDso) r3     // Catch:{ all -> 0x0129 }
                java.lang.String r3 = r3.name     // Catch:{ all -> 0x0129 }
                boolean r3 = r3.equals(r2)     // Catch:{ all -> 0x0129 }
                if (r3 == 0) goto L_0x00d2
                r17 = 1
                goto L_0x00d9
            L_0x00d2:
                int r0 = r0 + 1
                r3 = r19
                goto L_0x00bb
            L_0x00d7:
                r19 = r3
            L_0x00d9:
                if (r17 == 0) goto L_0x00e2
                r2 = r21
                r0 = r16
                r3 = r19
                goto L_0x007e
            L_0x00e2:
                int r0 = r14 + 1
                java.lang.String r0 = r15.substring(r0)     // Catch:{ all -> 0x0129 }
                com.facebook.soloader.ExoSoSource$FileDso r3 = new com.facebook.soloader.ExoSoSource$FileDso     // Catch:{ all -> 0x0129 }
                r18 = r6
                java.io.File r6 = new java.io.File     // Catch:{ all -> 0x0129 }
                r6.<init>(r10, r0)     // Catch:{ all -> 0x0129 }
                r3.<init>(r2, r0, r6)     // Catch:{ all -> 0x0129 }
                r4.add(r3)     // Catch:{ all -> 0x0129 }
                r2 = r21
                r0 = r16
                r6 = r18
                r3 = r19
                goto L_0x007e
            L_0x0101:
                r0 = move-exception
                r19 = r3
                r2 = r0
                goto L_0x014f
            L_0x0106:
                r21 = r2
                r19 = r3
                java.lang.RuntimeException r0 = new java.lang.RuntimeException     // Catch:{ all -> 0x0129 }
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0129 }
                r2.<init>()     // Catch:{ all -> 0x0129 }
                java.lang.String r3 = "illegal line in exopackage metadata: ["
                java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x0129 }
                java.lang.StringBuilder r2 = r2.append(r15)     // Catch:{ all -> 0x0129 }
                java.lang.String r3 = "]"
                java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x0129 }
                java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0129 }
                r0.<init>(r2)     // Catch:{ all -> 0x0129 }
                throw r0     // Catch:{ all -> 0x0129 }
            L_0x0129:
                r0 = move-exception
                r2 = r0
                goto L_0x014f
            L_0x012c:
                r16 = r0
                r21 = r2
                r19 = r3
                r18 = r6
                r13.close()     // Catch:{ all -> 0x0146 }
                r12.close()
            L_0x013a:
                int r8 = r8 + 1
                r2 = r21
                r0 = r16
                r6 = r18
                r3 = r19
                goto L_0x0040
            L_0x0146:
                r0 = move-exception
                r2 = r0
                goto L_0x0162
            L_0x0149:
                r0 = move-exception
                r21 = r2
                r19 = r3
                r2 = r0
            L_0x014f:
                throw r2     // Catch:{ all -> 0x0150 }
            L_0x0150:
                r0 = move-exception
                r3 = r0
                r13.close()     // Catch:{ all -> 0x0156 }
                goto L_0x015b
            L_0x0156:
                r0 = move-exception
                r6 = r0
                r2.addSuppressed(r6)     // Catch:{ all -> 0x0146 }
            L_0x015b:
                throw r3     // Catch:{ all -> 0x0146 }
            L_0x015c:
                r0 = move-exception
                r21 = r2
                r19 = r3
                r2 = r0
            L_0x0162:
                throw r2     // Catch:{ all -> 0x0163 }
            L_0x0163:
                r0 = move-exception
                r3 = r0
                r12.close()     // Catch:{ all -> 0x0169 }
                goto L_0x016e
            L_0x0169:
                r0 = move-exception
                r6 = r0
                r2.addSuppressed(r6)
            L_0x016e:
                throw r3
            L_0x016f:
                r21 = r2
                int r0 = r5.size()
                java.lang.String[] r0 = new java.lang.String[r0]
                java.lang.Object[] r0 = r5.toArray(r0)
                java.lang.String[] r0 = (java.lang.String[]) r0
                r2 = r22
                r2.setSoSourceAbis(r0)
                int r0 = r4.size()
                com.facebook.soloader.ExoSoSource$FileDso[] r0 = new com.facebook.soloader.ExoSoSource.FileDso[r0]
                java.lang.Object[] r0 = r4.toArray(r0)
                com.facebook.soloader.ExoSoSource$FileDso[] r0 = (com.facebook.soloader.ExoSoSource.FileDso[]) r0
                r1.mDsos = r0
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.soloader.ExoSoSource.ExoUnpacker.<init>(com.facebook.soloader.ExoSoSource, com.facebook.soloader.UnpackingSoSource):void");
        }

        /* access modifiers changed from: protected */
        public UnpackingSoSource.DsoManifest getDsoManifest() throws IOException {
            return new UnpackingSoSource.DsoManifest(this.mDsos);
        }

        /* access modifiers changed from: protected */
        public UnpackingSoSource.InputDsoIterator openDsoIterator() throws IOException {
            return new FileBackedInputDsoIterator();
        }

        private final class FileBackedInputDsoIterator extends UnpackingSoSource.InputDsoIterator {
            private int mCurrentDso;

            private FileBackedInputDsoIterator() {
            }

            public boolean hasNext() {
                return this.mCurrentDso < ExoUnpacker.this.mDsos.length;
            }

            public UnpackingSoSource.InputDso next() throws IOException {
                FileDso[] access$100 = ExoUnpacker.this.mDsos;
                int i = this.mCurrentDso;
                this.mCurrentDso = i + 1;
                FileDso fileDso = access$100[i];
                FileInputStream dsoFile = new FileInputStream(fileDso.backingFile);
                try {
                    UnpackingSoSource.InputDso ret = new UnpackingSoSource.InputDso(fileDso, dsoFile);
                    dsoFile = null;
                    if (dsoFile != null) {
                    }
                    return ret;
                } finally {
                    dsoFile.close();
                }
            }
        }
    }

    private static final class FileDso extends UnpackingSoSource.Dso {
        final File backingFile;

        FileDso(String name, String hash, File backingFile2) {
            super(name, hash);
            this.backingFile = backingFile2;
        }
    }
}
