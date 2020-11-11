package com.google.android.play.core.assetpacks;

import android.content.Context;
import android.support.p000v4.media.session.PlaybackStateCompat;
import android.util.Base64;
import com.bumptech.glide.load.Key;
import com.google.android.play.core.internal.C3046cd;
import com.google.android.play.core.splitcompat.C3082d;
import com.google.android.play.core.splitinstall.C3156v;
import com.google.common.base.Ascii;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipException;

/* renamed from: com.google.android.play.core.assetpacks.dd */
public final class C2942dd {

    /* renamed from: a */
    private static C2857a f1169a;

    /* renamed from: a */
    static int m523a(byte[] bArr, int i) {
        return (bArr[i + 3] & 255) | ((bArr[i] & 255) << Ascii.CAN) | ((bArr[i + 1] & 255) << Ascii.DLE) | ((bArr[i + 2] & 255) << 8);
    }

    /* renamed from: a */
    static AssetLocation m524a(String str, String str2) throws IOException {
        Long l;
        String str3 = str;
        String str4 = str2;
        C3082d.m897a(str3 != null, (Object) "Attempted to get file location from a null apk path.");
        C3082d.m897a(str4 != null, (Object) String.format("Attempted to get file location in apk %s with a null file path.", new Object[]{str3}));
        RandomAccessFile randomAccessFile = new RandomAccessFile(str3, "r");
        byte[] bArr = new byte[22];
        randomAccessFile.seek(randomAccessFile.length() - 22);
        randomAccessFile.readFully(bArr);
        C2897bm a = m523a(bArr, 0) == 1347093766 ? m526a(bArr) : null;
        byte b = 5;
        if (a == null) {
            long length = randomAccessFile.length() - 22;
            long j = -65536 + length;
            if (j < 0) {
                j = 0;
            }
            int min = (int) Math.min(PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID, randomAccessFile.length());
            byte[] bArr2 = new byte[min];
            byte[] bArr3 = new byte[22];
            loop0:
            while (true) {
                long max = Math.max(3 + (length - ((long) min)), j);
                randomAccessFile.seek(max);
                randomAccessFile.readFully(bArr2);
                for (int i = min - 4; i >= 0; i -= 4) {
                    byte b2 = bArr2[i];
                    int i2 = b2 != b ? b2 != 6 ? b2 != 75 ? b2 != 80 ? -1 : 0 : 1 : 3 : 2;
                    if (i2 >= 0 && i >= i2 && m523a(bArr2, i - i2) == 1347093766) {
                        randomAccessFile.seek((max + ((long) i)) - ((long) i2));
                        randomAccessFile.readFully(bArr3);
                        a = m526a(bArr3);
                        break loop0;
                    }
                    b = 5;
                }
                if (max != j) {
                    length = max;
                } else {
                    throw new ZipException(String.format("End Of Central Directory signature not found in APK %s", new Object[]{str3}));
                }
            }
        }
        long j2 = a.f1007a;
        byte[] bytes = str4.getBytes(Key.STRING_CHARSET_NAME);
        byte[] bArr4 = new byte[46];
        byte[] bArr5 = new byte[str2.length()];
        int i3 = 0;
        while (true) {
            if (i3 >= a.f1008b) {
                l = null;
                break;
            }
            randomAccessFile.seek(j2);
            randomAccessFile.readFully(bArr4);
            int a2 = m523a(bArr4, 0);
            if (a2 == 1347092738) {
                randomAccessFile.seek(j2 + 28);
                int c = m532c(bArr4, 28);
                if (c == str2.length()) {
                    randomAccessFile.seek(46 + j2);
                    randomAccessFile.read(bArr5);
                    if (Arrays.equals(bArr5, bytes)) {
                        l = Long.valueOf(m530b(bArr4, 42));
                        break;
                    }
                }
                j2 += (long) (c + 46 + m532c(bArr4, 30) + m532c(bArr4, 32));
                i3++;
            } else {
                throw new ZipException(String.format("Missing central directory file header signature when looking for file %s in APK %s. Read %d entries out of %d. Found %d instead of the header signature %d.", new Object[]{str4, str3, Integer.valueOf(i3), Integer.valueOf(a.f1008b), Integer.valueOf(a2), 1347092738}));
            }
        }
        if (l == null) {
            return null;
        }
        long longValue = l.longValue();
        byte[] bArr6 = new byte[8];
        randomAccessFile.seek(22 + longValue);
        randomAccessFile.readFully(bArr6);
        return AssetLocation.m297a(str3, longValue + 30 + ((long) m532c(bArr6, 4)) + ((long) m532c(bArr6, 6)), m530b(bArr6, 0));
    }

    /* renamed from: a */
    static synchronized C2857a m525a(Context context) {
        C2857a aVar;
        synchronized (C2942dd.class) {
            if (f1169a == null) {
                C2904bt btVar = new C2904bt((byte[]) null);
                btVar.mo44010a(new C2971m(C3156v.m1053a(context)));
                f1169a = btVar.mo44009a();
            }
            aVar = f1169a;
        }
        return aVar;
    }

    /* renamed from: a */
    private static C2897bm m526a(byte[] bArr) {
        int c = m532c(bArr, 10);
        m530b(bArr, 12);
        return new C2897bm(m530b(bArr, 16), c);
    }

    /* renamed from: a */
    static String m527a(List<File> list) throws NoSuchAlgorithmException, IOException {
        int read;
        MessageDigest instance = MessageDigest.getInstance("SHA256");
        byte[] bArr = new byte[8192];
        for (File fileInputStream : list) {
            FileInputStream fileInputStream2 = new FileInputStream(fileInputStream);
            do {
                try {
                    read = fileInputStream2.read(bArr);
                    if (read > 0) {
                        instance.update(bArr, 0, read);
                    }
                } catch (Throwable th) {
                    C3046cd.m768a(th, th);
                }
            } while (read != -1);
            fileInputStream2.close();
        }
        return Base64.encodeToString(instance.digest(), 11);
        throw th;
    }

    /* renamed from: a */
    public static boolean m528a(int i) {
        return i == 1 || i == 7 || i == 2 || i == 3;
    }

    /* renamed from: a */
    static boolean m529a(int i, int i2) {
        if (i == 5 && i2 != 5) {
            return true;
        }
        if (i == 6 && i2 != 6 && i2 != 5) {
            return true;
        }
        if (i == 4 && i2 != 4) {
            return true;
        }
        if (i == 3 && (i2 == 2 || i2 == 7 || i2 == 1 || i2 == 8)) {
            return true;
        }
        if (i != 2) {
            return false;
        }
        return i2 == 1 || i2 == 8;
    }

    /* renamed from: b */
    static long m530b(byte[] bArr, int i) {
        return ((long) ((m532c(bArr, i + 2) << 16) | m532c(bArr, i))) & 4294967295L;
    }

    /* renamed from: b */
    public static boolean m531b(int i) {
        return i == 5 || i == 6 || i == 4;
    }

    /* renamed from: c */
    static int m532c(byte[] bArr, int i) {
        return ((bArr[i + 1] & 255) << 8) | (bArr[i] & 255);
    }

    /* renamed from: c */
    public static boolean m533c(int i) {
        return i == 2 || i == 7 || i == 3;
    }
}
