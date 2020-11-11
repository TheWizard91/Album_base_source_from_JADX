package com.facebook.soloader;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import java.io.Closeable;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

public abstract class UnpackingSoSource extends DirectorySoSource {
    private static final String DEPS_FILE_NAME = "dso_deps";
    private static final String LOCK_FILE_NAME = "dso_lock";
    private static final String MANIFEST_FILE_NAME = "dso_manifest";
    private static final byte MANIFEST_VERSION = 1;
    private static final byte STATE_CLEAN = 1;
    private static final byte STATE_DIRTY = 0;
    private static final String STATE_FILE_NAME = "dso_state";
    private static final String TAG = "fb-UnpackingSoSource";
    @Nullable
    private String[] mAbis;
    protected final Context mContext;
    @Nullable
    protected String mCorruptedLib;
    private final Map<String, Object> mLibsBeingLoaded = new HashMap();

    /* access modifiers changed from: protected */
    public abstract Unpacker makeUnpacker() throws IOException;

    protected UnpackingSoSource(Context context, String name) {
        super(getSoStorePath(context, name), 1);
        this.mContext = context;
    }

    protected UnpackingSoSource(Context context, File storePath) {
        super(storePath, 1);
        this.mContext = context;
    }

    public static File getSoStorePath(Context context, String name) {
        return new File(context.getApplicationInfo().dataDir + "/" + name);
    }

    public String[] getSoSourceAbis() {
        String[] strArr = this.mAbis;
        if (strArr == null) {
            return super.getSoSourceAbis();
        }
        return strArr;
    }

    public void setSoSourceAbis(String[] abis) {
        this.mAbis = abis;
    }

    public static class Dso {
        public final String hash;
        public final String name;

        public Dso(String name2, String hash2) {
            this.name = name2;
            this.hash = hash2;
        }
    }

    public static final class DsoManifest {
        public final Dso[] dsos;

        public DsoManifest(Dso[] dsos2) {
            this.dsos = dsos2;
        }

        static final DsoManifest read(DataInput xdi) throws IOException {
            if (xdi.readByte() == 1) {
                int nrDso = xdi.readInt();
                if (nrDso >= 0) {
                    Dso[] dsos2 = new Dso[nrDso];
                    for (int i = 0; i < nrDso; i++) {
                        dsos2[i] = new Dso(xdi.readUTF(), xdi.readUTF());
                    }
                    return new DsoManifest(dsos2);
                }
                throw new RuntimeException("illegal number of shared libraries");
            }
            throw new RuntimeException("wrong dso manifest version");
        }

        public final void write(DataOutput xdo) throws IOException {
            xdo.writeByte(1);
            xdo.writeInt(this.dsos.length);
            int i = 0;
            while (true) {
                Dso[] dsoArr = this.dsos;
                if (i < dsoArr.length) {
                    xdo.writeUTF(dsoArr[i].name);
                    xdo.writeUTF(this.dsos[i].hash);
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    protected static final class InputDso implements Closeable {
        public final InputStream content;
        public final Dso dso;

        public InputDso(Dso dso2, InputStream content2) {
            this.dso = dso2;
            this.content = content2;
        }

        public void close() throws IOException {
            this.content.close();
        }
    }

    protected static abstract class InputDsoIterator implements Closeable {
        public abstract boolean hasNext();

        public abstract InputDso next() throws IOException;

        protected InputDsoIterator() {
        }

        public void close() throws IOException {
        }
    }

    protected static abstract class Unpacker implements Closeable {
        /* access modifiers changed from: protected */
        public abstract DsoManifest getDsoManifest() throws IOException;

        /* access modifiers changed from: protected */
        public abstract InputDsoIterator openDsoIterator() throws IOException;

        protected Unpacker() {
        }

        public void close() throws IOException {
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:10:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0028, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0029, code lost:
        r1.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002c, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0023, code lost:
        r2 = move-exception;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void writeState(java.io.File r4, byte r5) throws java.io.IOException {
        /*
            java.io.RandomAccessFile r0 = new java.io.RandomAccessFile
            java.lang.String r1 = "rw"
            r0.<init>(r4, r1)
            r1 = 0
            r0.seek(r1)     // Catch:{ all -> 0x0021 }
            r0.write(r5)     // Catch:{ all -> 0x0021 }
            long r1 = r0.getFilePointer()     // Catch:{ all -> 0x0021 }
            r0.setLength(r1)     // Catch:{ all -> 0x0021 }
            java.io.FileDescriptor r1 = r0.getFD()     // Catch:{ all -> 0x0021 }
            r1.sync()     // Catch:{ all -> 0x0021 }
            r0.close()
            return
        L_0x0021:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0023 }
        L_0x0023:
            r2 = move-exception
            r0.close()     // Catch:{ all -> 0x0028 }
            goto L_0x002c
        L_0x0028:
            r3 = move-exception
            r1.addSuppressed(r3)
        L_0x002c:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.soloader.UnpackingSoSource.writeState(java.io.File, byte):void");
    }

    private void deleteUnmentionedFiles(Dso[] dsos) throws IOException {
        String[] existingFiles = this.soDirectory.list();
        if (existingFiles != null) {
            for (String fileName : existingFiles) {
                if (!fileName.equals(STATE_FILE_NAME) && !fileName.equals(LOCK_FILE_NAME) && !fileName.equals(DEPS_FILE_NAME) && !fileName.equals(MANIFEST_FILE_NAME)) {
                    boolean found = false;
                    int j = 0;
                    while (!found && j < dsos.length) {
                        if (dsos[j].name.equals(fileName)) {
                            found = true;
                        }
                        j++;
                    }
                    if (!found) {
                        File fileNameToDelete = new File(this.soDirectory, fileName);
                        Log.v(TAG, "deleting unaccounted-for file " + fileNameToDelete);
                        SysUtil.dumbDeleteRecursive(fileNameToDelete);
                    }
                }
            }
            return;
        }
        throw new IOException("unable to list directory " + this.soDirectory);
    }

    private void extractDso(InputDso iDso, byte[] ioBuffer) throws IOException {
        RandomAccessFile dsoFile;
        Log.i(TAG, "extracting DSO " + iDso.dso.name);
        if (this.soDirectory.setWritable(true, true)) {
            File dsoFileName = new File(this.soDirectory, iDso.dso.name);
            try {
                dsoFile = new RandomAccessFile(dsoFileName, "rw");
            } catch (IOException ex) {
                Log.w(TAG, "error overwriting " + dsoFileName + " trying to delete and start over", ex);
                SysUtil.dumbDeleteRecursive(dsoFileName);
                dsoFile = new RandomAccessFile(dsoFileName, "rw");
            }
            try {
                int sizeHint = iDso.content.available();
                if (sizeHint > 1) {
                    SysUtil.fallocateIfSupported(dsoFile.getFD(), (long) sizeHint);
                }
                SysUtil.copyBytes(dsoFile, iDso.content, Integer.MAX_VALUE, ioBuffer);
                dsoFile.setLength(dsoFile.getFilePointer());
                if (dsoFileName.setExecutable(true, false)) {
                    dsoFile.close();
                    return;
                }
                throw new IOException("cannot make file executable: " + dsoFileName);
            } catch (IOException e) {
                SysUtil.dumbDeleteRecursive(dsoFileName);
                throw e;
            } catch (Throwable th) {
                dsoFile.close();
                throw th;
            }
        } else {
            throw new IOException("cannot make directory writable for us: " + this.soDirectory);
        }
    }

    private void regenerate(byte state, DsoManifest desiredManifest, InputDsoIterator dsoIterator) throws IOException {
        Log.v(TAG, "regenerating DSO store " + getClass().getName());
        RandomAccessFile manifestFile = new RandomAccessFile(new File(this.soDirectory, MANIFEST_FILE_NAME), "rw");
        DsoManifest existingManifest = null;
        if (state == 1) {
            try {
                existingManifest = DsoManifest.read(manifestFile);
            } catch (Exception ex) {
                Log.i(TAG, "error reading existing DSO manifest", ex);
            }
        }
        if (existingManifest == null) {
            existingManifest = new DsoManifest(new Dso[0]);
        }
        deleteUnmentionedFiles(desiredManifest.dsos);
        byte[] ioBuffer = new byte[32768];
        while (dsoIterator.hasNext()) {
            InputDso iDso = dsoIterator.next();
            boolean obsolete = true;
            int i = 0;
            while (obsolete) {
                try {
                    if (i >= existingManifest.dsos.length) {
                        break;
                    }
                    if (existingManifest.dsos[i].name.equals(iDso.dso.name) && existingManifest.dsos[i].hash.equals(iDso.dso.hash)) {
                        obsolete = false;
                    }
                    i++;
                } catch (Throwable th) {
                    if (iDso != null) {
                        try {
                            iDso.close();
                        } catch (Throwable th2) {
                            try {
                                manifestFile.close();
                            } catch (Throwable th3) {
                                r1.addSuppressed(th3);
                            }
                            throw th2;
                        }
                    }
                    throw th;
                }
            }
            if (obsolete) {
                extractDso(iDso, ioBuffer);
            }
            if (iDso != null) {
                iDso.close();
            }
        }
        manifestFile.close();
        Log.v(TAG, "Finished regenerating DSO store " + getClass().getName());
    }

    private boolean refreshLocked(FileLocker lock, int flags, byte[] deps) throws IOException {
        byte state;
        Throwable th;
        Throwable th2;
        byte state2;
        DsoManifest desiredManifest;
        Throwable th3;
        Throwable th4;
        File stateFileName = new File(this.soDirectory, STATE_FILE_NAME);
        RandomAccessFile stateFile = new RandomAccessFile(stateFileName, "rw");
        try {
            byte state3 = stateFile.readByte();
            if (state3 != 1) {
                Log.v(TAG, "dso store " + this.soDirectory + " regeneration interrupted: wiping clean");
                state3 = 0;
            }
            state = state3;
        } catch (EOFException e) {
            state = 0;
        } catch (Throwable th5) {
            Throwable th6 = th5;
            try {
                stateFile.close();
            } catch (Throwable th7) {
                th4.addSuppressed(th7);
            }
            throw th6;
        }
        stateFile.close();
        File depsFileName = new File(this.soDirectory, DEPS_FILE_NAME);
        RandomAccessFile depsFile = new RandomAccessFile(depsFileName, "rw");
        try {
            byte[] existingDeps = new byte[((int) depsFile.length())];
            if (depsFile.read(existingDeps) != existingDeps.length) {
                Log.v(TAG, "short read of so store deps file: marking unclean");
                state = 0;
            }
            try {
                if (!Arrays.equals(existingDeps, deps)) {
                    Log.v(TAG, "deps mismatch on deps store: regenerating");
                    state2 = 0;
                } else {
                    state2 = state;
                }
                if (state2 == 0 || (flags & 2) != 0) {
                    try {
                        Log.v(TAG, "so store dirty: regenerating");
                        writeState(stateFileName, (byte) 0);
                        Unpacker u = makeUnpacker();
                        try {
                            DsoManifest desiredManifest2 = u.getDsoManifest();
                            InputDsoIterator idi = u.openDsoIterator();
                            try {
                                regenerate(state2, desiredManifest2, idi);
                                if (idi != null) {
                                    idi.close();
                                }
                                if (u != null) {
                                    u.close();
                                }
                                desiredManifest = desiredManifest2;
                            } catch (Throwable th8) {
                                Throwable th9 = th8;
                                if (idi != null) {
                                    idi.close();
                                }
                                throw th9;
                            }
                        } catch (Throwable th10) {
                            th3.addSuppressed(th10);
                        } finally {
                            th3 = th10;
                            try {
                            } catch (Throwable th11) {
                                Throwable th12 = th11;
                                if (u != null) {
                                    u.close();
                                }
                                throw th12;
                            }
                        }
                    } catch (Throwable th13) {
                        th = th13;
                        byte b = state2;
                        try {
                            throw th;
                        } catch (Throwable th14) {
                            th.addSuppressed(th14);
                        }
                    }
                } else {
                    desiredManifest = null;
                }
                depsFile.close();
                if (desiredManifest == null) {
                    return false;
                }
                final DsoManifest manifest = desiredManifest;
                final File file = depsFileName;
                final byte[] bArr = deps;
                final File file2 = stateFileName;
                final FileLocker fileLocker = lock;
                C07861 r1 = new Runnable() {
                    /* JADX WARNING: Code restructure failed: missing block: B:18:0x006f, code lost:
                        r6 = move-exception;
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
                        r2.close();
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0078, code lost:
                        throw r6;
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:28:0x007b, code lost:
                        r5 = move-exception;
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
                        r4.close();
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0084, code lost:
                        throw r5;
                     */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public void run() {
                        /*
                            r8 = this;
                            java.lang.String r0 = " (from syncer thread)"
                            java.lang.String r1 = "releasing dso store lock for "
                            java.lang.String r2 = "rw"
                            java.lang.String r3 = "fb-UnpackingSoSource"
                            java.lang.String r4 = "starting syncer worker"
                            android.util.Log.v(r3, r4)     // Catch:{ all -> 0x0085 }
                            java.io.RandomAccessFile r4 = new java.io.RandomAccessFile     // Catch:{ all -> 0x0085 }
                            java.io.File r5 = r3     // Catch:{ all -> 0x0085 }
                            r4.<init>(r5, r2)     // Catch:{ all -> 0x0085 }
                            byte[] r5 = r4     // Catch:{ all -> 0x0079 }
                            r4.write(r5)     // Catch:{ all -> 0x0079 }
                            long r5 = r4.getFilePointer()     // Catch:{ all -> 0x0079 }
                            r4.setLength(r5)     // Catch:{ all -> 0x0079 }
                            r4.close()     // Catch:{ all -> 0x0085 }
                            java.io.File r4 = new java.io.File     // Catch:{ all -> 0x0085 }
                            com.facebook.soloader.UnpackingSoSource r5 = com.facebook.soloader.UnpackingSoSource.this     // Catch:{ all -> 0x0085 }
                            java.io.File r5 = r5.soDirectory     // Catch:{ all -> 0x0085 }
                            java.lang.String r6 = "dso_manifest"
                            r4.<init>(r5, r6)     // Catch:{ all -> 0x0085 }
                            java.io.RandomAccessFile r5 = new java.io.RandomAccessFile     // Catch:{ all -> 0x0085 }
                            r5.<init>(r4, r2)     // Catch:{ all -> 0x0085 }
                            r2 = r5
                            com.facebook.soloader.UnpackingSoSource$DsoManifest r5 = r5     // Catch:{ all -> 0x006d }
                            r5.write(r2)     // Catch:{ all -> 0x006d }
                            r2.close()     // Catch:{ all -> 0x0085 }
                            com.facebook.soloader.UnpackingSoSource r2 = com.facebook.soloader.UnpackingSoSource.this     // Catch:{ all -> 0x0085 }
                            java.io.File r2 = r2.soDirectory     // Catch:{ all -> 0x0085 }
                            com.facebook.soloader.SysUtil.fsyncRecursive(r2)     // Catch:{ all -> 0x0085 }
                            java.io.File r2 = r6     // Catch:{ all -> 0x0085 }
                            r5 = 1
                            com.facebook.soloader.UnpackingSoSource.writeState(r2, r5)     // Catch:{ all -> 0x0085 }
                            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00a8 }
                            r2.<init>()     // Catch:{ IOException -> 0x00a8 }
                            java.lang.StringBuilder r1 = r2.append(r1)     // Catch:{ IOException -> 0x00a8 }
                            com.facebook.soloader.UnpackingSoSource r2 = com.facebook.soloader.UnpackingSoSource.this     // Catch:{ IOException -> 0x00a8 }
                            java.io.File r2 = r2.soDirectory     // Catch:{ IOException -> 0x00a8 }
                            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ IOException -> 0x00a8 }
                            java.lang.StringBuilder r0 = r1.append(r0)     // Catch:{ IOException -> 0x00a8 }
                            java.lang.String r0 = r0.toString()     // Catch:{ IOException -> 0x00a8 }
                            android.util.Log.v(r3, r0)     // Catch:{ IOException -> 0x00a8 }
                            com.facebook.soloader.FileLocker r0 = r7     // Catch:{ IOException -> 0x00a8 }
                            r0.close()     // Catch:{ IOException -> 0x00a8 }
                            return
                        L_0x006d:
                            r5 = move-exception
                            throw r5     // Catch:{ all -> 0x006f }
                        L_0x006f:
                            r6 = move-exception
                            r2.close()     // Catch:{ all -> 0x0074 }
                            goto L_0x0078
                        L_0x0074:
                            r7 = move-exception
                            r5.addSuppressed(r7)     // Catch:{ all -> 0x0085 }
                        L_0x0078:
                            throw r6     // Catch:{ all -> 0x0085 }
                        L_0x0079:
                            r2 = move-exception
                            throw r2     // Catch:{ all -> 0x007b }
                        L_0x007b:
                            r5 = move-exception
                            r4.close()     // Catch:{ all -> 0x0080 }
                            goto L_0x0084
                        L_0x0080:
                            r6 = move-exception
                            r2.addSuppressed(r6)     // Catch:{ all -> 0x0085 }
                        L_0x0084:
                            throw r5     // Catch:{ all -> 0x0085 }
                        L_0x0085:
                            r2 = move-exception
                            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00a8 }
                            r4.<init>()     // Catch:{ IOException -> 0x00a8 }
                            java.lang.StringBuilder r1 = r4.append(r1)     // Catch:{ IOException -> 0x00a8 }
                            com.facebook.soloader.UnpackingSoSource r4 = com.facebook.soloader.UnpackingSoSource.this     // Catch:{ IOException -> 0x00a8 }
                            java.io.File r4 = r4.soDirectory     // Catch:{ IOException -> 0x00a8 }
                            java.lang.StringBuilder r1 = r1.append(r4)     // Catch:{ IOException -> 0x00a8 }
                            java.lang.StringBuilder r0 = r1.append(r0)     // Catch:{ IOException -> 0x00a8 }
                            java.lang.String r0 = r0.toString()     // Catch:{ IOException -> 0x00a8 }
                            android.util.Log.v(r3, r0)     // Catch:{ IOException -> 0x00a8 }
                            com.facebook.soloader.FileLocker r0 = r7     // Catch:{ IOException -> 0x00a8 }
                            r0.close()     // Catch:{ IOException -> 0x00a8 }
                            throw r2     // Catch:{ IOException -> 0x00a8 }
                        L_0x00a8:
                            r0 = move-exception
                            java.lang.RuntimeException r1 = new java.lang.RuntimeException
                            r1.<init>(r0)
                            throw r1
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.facebook.soloader.UnpackingSoSource.C07861.run():void");
                    }
                };
                if ((flags & 1) != 0) {
                    new Thread(r1, "SoSync:" + this.soDirectory.getName()).start();
                } else {
                    r1.run();
                }
                return true;
            } catch (Throwable th15) {
                th = th15;
                th = th;
                throw th;
            }
        } catch (Throwable th16) {
            th = th16;
            byte[] bArr2 = deps;
            th = th;
            throw th;
        }
        throw th2;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x003a, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003b, code lost:
        if (r1 != null) goto L_0x003d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0041, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0042, code lost:
        r2.addSuppressed(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0045, code lost:
        throw r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] getDepsBlock() throws java.io.IOException {
        /*
            r5 = this;
            android.os.Parcel r0 = android.os.Parcel.obtain()
            com.facebook.soloader.UnpackingSoSource$Unpacker r1 = r5.makeUnpacker()
            com.facebook.soloader.UnpackingSoSource$DsoManifest r2 = r1.getDsoManifest()     // Catch:{ all -> 0x0038 }
            com.facebook.soloader.UnpackingSoSource$Dso[] r2 = r2.dsos     // Catch:{ all -> 0x0038 }
            r3 = 1
            r0.writeByte(r3)     // Catch:{ all -> 0x0038 }
            int r3 = r2.length     // Catch:{ all -> 0x0038 }
            r0.writeInt(r3)     // Catch:{ all -> 0x0038 }
            r3 = 0
        L_0x0017:
            int r4 = r2.length     // Catch:{ all -> 0x0038 }
            if (r3 >= r4) goto L_0x002b
            r4 = r2[r3]     // Catch:{ all -> 0x0038 }
            java.lang.String r4 = r4.name     // Catch:{ all -> 0x0038 }
            r0.writeString(r4)     // Catch:{ all -> 0x0038 }
            r4 = r2[r3]     // Catch:{ all -> 0x0038 }
            java.lang.String r4 = r4.hash     // Catch:{ all -> 0x0038 }
            r0.writeString(r4)     // Catch:{ all -> 0x0038 }
            int r3 = r3 + 1
            goto L_0x0017
        L_0x002b:
            if (r1 == 0) goto L_0x0030
            r1.close()
        L_0x0030:
            byte[] r1 = r0.marshall()
            r0.recycle()
            return r1
        L_0x0038:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x003a }
        L_0x003a:
            r3 = move-exception
            if (r1 == 0) goto L_0x0045
            r1.close()     // Catch:{ all -> 0x0041 }
            goto L_0x0045
        L_0x0041:
            r4 = move-exception
            r2.addSuppressed(r4)
        L_0x0045:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.soloader.UnpackingSoSource.getDepsBlock():byte[]");
    }

    /* access modifiers changed from: protected */
    public void prepare(int flags) throws IOException {
        SysUtil.mkdirOrThrow(this.soDirectory);
        FileLocker lock = FileLocker.lock(new File(this.soDirectory, LOCK_FILE_NAME));
        try {
            Log.v(TAG, "locked dso store " + this.soDirectory);
            if (refreshLocked(lock, flags, getDepsBlock())) {
                lock = null;
            } else {
                Log.i(TAG, "dso store is up-to-date: " + this.soDirectory);
            }
            if (lock == null) {
                Log.v(TAG, "not releasing dso store lock for " + this.soDirectory + " (syncer thread started)");
            }
        } finally {
            if (lock != null) {
                Log.v(TAG, "releasing dso store lock for " + this.soDirectory);
                lock.close();
            } else {
                Log.v(TAG, "not releasing dso store lock for " + this.soDirectory + " (syncer thread started)");
            }
        }
    }

    private Object getLibraryLock(String soName) {
        Object lock;
        synchronized (this.mLibsBeingLoaded) {
            lock = this.mLibsBeingLoaded.get(soName);
            if (lock == null) {
                lock = new Object();
                this.mLibsBeingLoaded.put(soName, lock);
            }
        }
        return lock;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0012, code lost:
        r1 = th;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void prepare(java.lang.String r3) throws java.io.IOException {
        /*
            r2 = this;
            monitor-enter(r2)
            java.lang.Object r0 = r2.getLibraryLock(r3)     // Catch:{ all -> 0x0014 }
            monitor-enter(r0)     // Catch:{ all -> 0x0014 }
            r2.mCorruptedLib = r3     // Catch:{ all -> 0x000f }
            r1 = 2
            r2.prepare((int) r1)     // Catch:{ all -> 0x000f }
            monitor-exit(r0)     // Catch:{ all -> 0x000f }
            monitor-exit(r2)
            return
        L_0x000f:
            r1 = move-exception
        L_0x0010:
            monitor-exit(r0)     // Catch:{ all -> 0x0012 }
            throw r1     // Catch:{ all -> 0x0014 }
        L_0x0012:
            r1 = move-exception
            goto L_0x0010
        L_0x0014:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.soloader.UnpackingSoSource.prepare(java.lang.String):void");
    }

    public int loadLibrary(String soName, int loadFlags, StrictMode.ThreadPolicy threadPolicy) throws IOException {
        int loadLibraryFrom;
        synchronized (getLibraryLock(soName)) {
            loadLibraryFrom = loadLibraryFrom(soName, loadFlags, this.soDirectory, threadPolicy);
        }
        return loadLibraryFrom;
    }
}
