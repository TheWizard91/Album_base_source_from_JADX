package com.facebook.soloader;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Parcel;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Log;
import com.facebook.soloader.MinElf;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

public final class SysUtil {
    private static final byte APK_SIGNATURE_VERSION = 1;
    private static final String TAG = "SysUtil";

    public static int findAbiScore(String[] supportedAbis, String abi) {
        for (int i = 0; i < supportedAbis.length; i++) {
            if (supportedAbis[i] != null && abi.equals(supportedAbis[i])) {
                return i;
            }
        }
        return -1;
    }

    public static void deleteOrThrow(File file) throws IOException {
        if (!file.delete()) {
            throw new IOException("could not delete file " + file);
        }
    }

    public static String[] getSupportedAbis() {
        if (Build.VERSION.SDK_INT >= 21) {
            return LollipopSysdeps.getSupportedAbis();
        }
        return new String[]{Build.CPU_ABI, Build.CPU_ABI2};
    }

    public static void fallocateIfSupported(FileDescriptor fd, long length) throws IOException {
        if (Build.VERSION.SDK_INT >= 21) {
            LollipopSysdeps.fallocateIfSupported(fd, length);
        }
    }

    public static void dumbDeleteRecursive(File file) throws IOException {
        if (file.isDirectory()) {
            File[] fileList = file.listFiles();
            if (fileList != null) {
                for (File entry : fileList) {
                    dumbDeleteRecursive(entry);
                }
            } else {
                return;
            }
        }
        if (!file.delete() && file.exists()) {
            throw new IOException("could not delete: " + file);
        }
    }

    private static final class LollipopSysdeps {
        private LollipopSysdeps() {
        }

        public static String[] getSupportedAbis() {
            String[] supportedAbis = Build.SUPPORTED_ABIS;
            TreeSet<String> allowedAbis = new TreeSet<>();
            try {
                if (Os.readlink("/proc/self/exe").contains("64")) {
                    allowedAbis.add(MinElf.ISA.AARCH64.toString());
                    allowedAbis.add(MinElf.ISA.X86_64.toString());
                } else {
                    allowedAbis.add(MinElf.ISA.ARM.toString());
                    allowedAbis.add(MinElf.ISA.X86.toString());
                }
                ArrayList<String> compatibleSupportedAbis = new ArrayList<>();
                for (String abi : supportedAbis) {
                    if (allowedAbis.contains(abi)) {
                        compatibleSupportedAbis.add(abi);
                    }
                }
                return (String[]) compatibleSupportedAbis.toArray(new String[compatibleSupportedAbis.size()]);
            } catch (ErrnoException e) {
                Log.e(SysUtil.TAG, String.format("Could not read /proc/self/exe. Falling back to default ABI list: %s. errno: %d Err msg: %s", new Object[]{Arrays.toString(supportedAbis), Integer.valueOf(e.errno), e.getMessage()}));
                return Build.SUPPORTED_ABIS;
            }
        }

        public static void fallocateIfSupported(FileDescriptor fd, long length) throws IOException {
            try {
                Os.posix_fallocate(fd, 0, length);
            } catch (ErrnoException ex) {
                if (ex.errno != OsConstants.EOPNOTSUPP && ex.errno != OsConstants.ENOSYS && ex.errno != OsConstants.EINVAL) {
                    throw new IOException(ex.toString(), ex);
                }
            }
        }
    }

    static void mkdirOrThrow(File dir) throws IOException {
        if (!dir.mkdirs() && !dir.isDirectory()) {
            throw new IOException("cannot mkdir: " + dir);
        }
    }

    static int copyBytes(RandomAccessFile os, InputStream is, int byteLimit, byte[] buffer) throws IOException {
        int bytesCopied = 0;
        while (bytesCopied < byteLimit) {
            int read = is.read(buffer, 0, Math.min(buffer.length, byteLimit - bytesCopied));
            int nrRead = read;
            if (read == -1) {
                break;
            }
            os.write(buffer, 0, nrRead);
            bytesCopied += nrRead;
        }
        return bytesCopied;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0053, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0058, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0059, code lost:
        r1.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x005c, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void fsyncRecursive(java.io.File r4) throws java.io.IOException {
        /*
            boolean r0 = r4.isDirectory()
            if (r0 == 0) goto L_0x0032
            java.io.File[] r0 = r4.listFiles()
            if (r0 == 0) goto L_0x0019
            r1 = 0
        L_0x000d:
            int r2 = r0.length
            if (r1 >= r2) goto L_0x0018
            r2 = r0[r1]
            fsyncRecursive(r2)
            int r1 = r1 + 1
            goto L_0x000d
        L_0x0018:
            goto L_0x003e
        L_0x0019:
            java.io.IOException r1 = new java.io.IOException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "cannot list directory "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x0032:
            java.lang.String r0 = r4.getPath()
            java.lang.String r1 = "_lock"
            boolean r0 = r0.endsWith(r1)
            if (r0 == 0) goto L_0x003f
        L_0x003e:
            goto L_0x0050
        L_0x003f:
            java.io.RandomAccessFile r0 = new java.io.RandomAccessFile
            java.lang.String r1 = "r"
            r0.<init>(r4, r1)
            java.io.FileDescriptor r1 = r0.getFD()     // Catch:{ all -> 0x0051 }
            r1.sync()     // Catch:{ all -> 0x0051 }
            r0.close()
        L_0x0050:
            return
        L_0x0051:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0053 }
        L_0x0053:
            r2 = move-exception
            r0.close()     // Catch:{ all -> 0x0058 }
            goto L_0x005c
        L_0x0058:
            r3 = move-exception
            r1.addSuppressed(r3)
        L_0x005c:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.soloader.SysUtil.fsyncRecursive(java.io.File):void");
    }

    public static byte[] makeApkDepBlock(File apkFile, Context context) throws IOException {
        File apkFile2 = apkFile.getCanonicalFile();
        Parcel parcel = Parcel.obtain();
        try {
            parcel.writeByte((byte) 1);
            parcel.writeString(apkFile2.getPath());
            parcel.writeLong(apkFile2.lastModified());
            parcel.writeInt(getAppVersionCode(context));
            return parcel.marshall();
        } finally {
            parcel.recycle();
        }
    }

    public static int getAppVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        if (pm != null) {
            try {
                return pm.getPackageInfo(context.getPackageName(), 0).versionCode;
            } catch (PackageManager.NameNotFoundException | RuntimeException e) {
            }
        }
        return 0;
    }
}
