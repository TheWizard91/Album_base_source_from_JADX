package com.google.android.play.core.assetpacks;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.play.core.internal.C2989aa;
import com.google.android.play.core.internal.C3046cd;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Properties;

/* renamed from: com.google.android.play.core.assetpacks.dr */
final class C2956dr {

    /* renamed from: a */
    private static final C2989aa f1206a = new C2989aa("SliceMetadataManager");

    /* renamed from: b */
    private final byte[] f1207b = new byte[8192];

    /* renamed from: c */
    private final C2886bb f1208c;

    /* renamed from: d */
    private final String f1209d;

    /* renamed from: e */
    private final int f1210e;

    /* renamed from: f */
    private final long f1211f;

    /* renamed from: g */
    private final String f1212g;

    /* renamed from: h */
    private int f1213h;

    C2956dr(C2886bb bbVar, String str, int i, long j, String str2) {
        this.f1208c = bbVar;
        this.f1209d = str;
        this.f1210e = i;
        this.f1211f = j;
        this.f1212g = str2;
        this.f1213h = 0;
    }

    /* renamed from: e */
    private final File m549e() {
        File f = this.f1208c.mo43963f(this.f1209d, this.f1210e, this.f1211f, this.f1212g);
        if (!f.exists()) {
            f.mkdirs();
        }
        return f;
    }

    /* renamed from: f */
    private final File m550f() throws IOException {
        File c = this.f1208c.mo43951c(this.f1209d, this.f1210e, this.f1211f, this.f1212g);
        c.getParentFile().mkdirs();
        c.createNewFile();
        return c;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final C2955dq mo44051a() throws IOException {
        File c = this.f1208c.mo43951c(this.f1209d, this.f1210e, this.f1211f, this.f1212g);
        if (c.exists()) {
            Properties properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream(c);
            try {
                properties.load(fileInputStream);
                fileInputStream.close();
                if (properties.getProperty("fileStatus") == null || properties.getProperty("previousChunk") == null) {
                    throw new C2909by("Slice checkpoint file corrupt.");
                }
                try {
                    int parseInt = Integer.parseInt(properties.getProperty("fileStatus"));
                    String property = properties.getProperty("fileName");
                    long parseLong = Long.parseLong(properties.getProperty("fileOffset", "-1"));
                    long parseLong2 = Long.parseLong(properties.getProperty("remainingBytes", "-1"));
                    int parseInt2 = Integer.parseInt(properties.getProperty("previousChunk"));
                    this.f1213h = Integer.parseInt(properties.getProperty("metadataFileCounter", "0"));
                    return new C2895bk(parseInt, property, parseLong, parseLong2, parseInt2);
                } catch (NumberFormatException e) {
                    throw new C2909by("Slice checkpoint file corrupt.", (Exception) e);
                }
            } catch (Throwable th) {
                C3046cd.m768a(th, th);
            }
        } else {
            throw new C2909by("Slice checkpoint file does not exist.");
        }
        throw th;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo44052a(int i) throws IOException {
        Properties properties = new Properties();
        properties.put("fileStatus", ExifInterface.GPS_MEASUREMENT_3D);
        properties.put("fileOffset", String.valueOf(mo44058b().length()));
        properties.put("previousChunk", String.valueOf(i));
        properties.put("metadataFileCounter", String.valueOf(this.f1213h));
        FileOutputStream fileOutputStream = new FileOutputStream(m550f());
        properties.store(fileOutputStream, (String) null);
        fileOutputStream.close();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo44053a(InputStream inputStream, long j) throws IOException {
        int read;
        File b = mo44058b();
        b.getParentFile().mkdirs();
        RandomAccessFile randomAccessFile = new RandomAccessFile(b, "rw");
        randomAccessFile.seek(j);
        do {
            read = inputStream.read(this.f1207b);
            if (read > 0) {
                randomAccessFile.write(this.f1207b, 0, read);
            }
        } while (read == this.f1207b.length);
        randomAccessFile.close();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo44054a(String str, long j, long j2, int i) throws IOException {
        Properties properties = new Properties();
        properties.put("fileStatus", "1");
        properties.put("fileName", str);
        properties.put("fileOffset", String.valueOf(j));
        properties.put("remainingBytes", String.valueOf(j2));
        properties.put("previousChunk", String.valueOf(i));
        properties.put("metadataFileCounter", String.valueOf(this.f1213h));
        FileOutputStream fileOutputStream = new FileOutputStream(m550f());
        properties.store(fileOutputStream, (String) null);
        fileOutputStream.close();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo44055a(byte[] bArr) throws IOException {
        FileOutputStream fileOutputStream;
        File e = m549e();
        int i = this.f1213h;
        this.f1213h = i + 1;
        try {
            fileOutputStream = new FileOutputStream(new File(e, String.format("%s-LFH.dat", new Object[]{Integer.valueOf(i)})));
            fileOutputStream.write(bArr);
            fileOutputStream.close();
            return;
        } catch (IOException e2) {
            throw new C2909by("Could not write metadata file.", (Exception) e2);
        } catch (Throwable th) {
            C3046cd.m768a(th, th);
        }
        throw th;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo44056a(byte[] bArr, int i) throws IOException {
        Properties properties = new Properties();
        properties.put("fileStatus", ExifInterface.GPS_MEASUREMENT_2D);
        properties.put("previousChunk", String.valueOf(i));
        properties.put("metadataFileCounter", String.valueOf(this.f1213h));
        FileOutputStream fileOutputStream = new FileOutputStream(m550f());
        properties.store(fileOutputStream, (String) null);
        fileOutputStream.close();
        File d = this.f1208c.mo43955d(this.f1209d, this.f1210e, this.f1211f, this.f1212g);
        if (d.exists()) {
            d.delete();
        }
        FileOutputStream fileOutputStream2 = new FileOutputStream(d);
        fileOutputStream2.write(bArr);
        fileOutputStream2.close();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo44057a(byte[] bArr, InputStream inputStream) throws IOException {
        File e = m549e();
        int i = this.f1213h;
        this.f1213h = i + 1;
        FileOutputStream fileOutputStream = new FileOutputStream(new File(e, String.format("%s-NAM.dat", new Object[]{Integer.valueOf(i)})));
        fileOutputStream.write(bArr);
        int read = inputStream.read(this.f1207b);
        while (read > 0) {
            fileOutputStream.write(this.f1207b, 0, read);
            read = inputStream.read(this.f1207b);
        }
        fileOutputStream.close();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final File mo44058b() {
        return new File(m549e(), String.format("%s-NAM.dat", new Object[]{Integer.valueOf(this.f1213h - 1)}));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public final void mo44059b(int i) throws IOException {
        Properties properties = new Properties();
        properties.put("fileStatus", "4");
        properties.put("previousChunk", String.valueOf(i));
        properties.put("metadataFileCounter", String.valueOf(this.f1213h));
        FileOutputStream fileOutputStream = new FileOutputStream(m550f());
        properties.store(fileOutputStream, (String) null);
        fileOutputStream.close();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final int mo44060c() throws IOException {
        File c = this.f1208c.mo43951c(this.f1209d, this.f1210e, this.f1211f, this.f1212g);
        if (!c.exists()) {
            return 0;
        }
        FileInputStream fileInputStream = new FileInputStream(c);
        Properties properties = new Properties();
        properties.load(fileInputStream);
        if (Integer.parseInt(properties.getProperty("fileStatus", "-1")) == 4) {
            return -1;
        }
        if (properties.getProperty("previousChunk") != null) {
            return Integer.parseInt(properties.getProperty("previousChunk")) + 1;
        }
        throw new C2909by("Slice checkpoint file corrupt.");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public final boolean mo44061d() {
        File c = this.f1208c.mo43951c(this.f1209d, this.f1210e, this.f1211f, this.f1212g);
        if (c.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(c);
                Properties properties = new Properties();
                properties.load(fileInputStream);
                if (properties.getProperty("fileStatus") != null) {
                    return Integer.parseInt(properties.getProperty("fileStatus")) == 4;
                }
                f1206a.mo44089b("Slice checkpoint file corrupt while checking if extraction finished.", new Object[0]);
                return false;
            } catch (IOException e) {
                f1206a.mo44089b("Could not read checkpoint while checking if extraction finished. %s", e);
            }
        }
        return false;
    }
}
