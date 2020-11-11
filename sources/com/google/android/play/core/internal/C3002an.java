package com.google.android.play.core.internal;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.util.Log;
import com.google.android.play.core.splitcompat.C3083e;
import com.google.android.play.core.splitcompat.SplitCompat;
import com.google.android.play.core.splitinstall.C3124b;
import com.google.android.play.core.splitinstall.C3128d;
import com.google.android.play.core.splitinstall.C3156v;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.List;
import java.util.concurrent.Executor;

/* renamed from: com.google.android.play.core.internal.an */
public final class C3002an implements C3128d {

    /* renamed from: a */
    private final Context f1300a;

    /* renamed from: b */
    private final C3083e f1301b;
    /* access modifiers changed from: private */

    /* renamed from: c */
    public final C3003ao f1302c;

    /* renamed from: d */
    private final Executor f1303d;

    /* renamed from: e */
    private final C3005aq f1304e;

    public C3002an(Context context, Executor executor, C3003ao aoVar, C3083e eVar, C3005aq aqVar, byte[] bArr) {
        this.f1300a = context;
        this.f1301b = eVar;
        this.f1302c = aoVar;
        this.f1303d = executor;
        this.f1304e = aqVar;
    }

    /* renamed from: a */
    private final Integer m650a(List<Intent> list) {
        FileChannel channel;
        FileLock fileLock;
        FileOutputStream fileOutputStream;
        try {
            channel = new RandomAccessFile(this.f1301b.mo44223b(), "rw").getChannel();
            Integer num = null;
            try {
                fileLock = channel.tryLock();
            } catch (OverlappingFileLockException e) {
                fileLock = null;
            }
            if (fileLock != null) {
                int i = 0;
                try {
                    Log.i("SplitCompat", "Copying splits.");
                    for (Intent next : list) {
                        String stringExtra = next.getStringExtra("split_id");
                        AssetFileDescriptor openAssetFileDescriptor = this.f1300a.getContentResolver().openAssetFileDescriptor(next.getData(), "r");
                        File a = this.f1301b.mo44220a(stringExtra);
                        if (!a.exists() || a.length() == openAssetFileDescriptor.getLength()) {
                            if (a.exists()) {
                            }
                        }
                        if (!this.f1301b.mo44224b(stringExtra).exists()) {
                            BufferedInputStream bufferedInputStream = new BufferedInputStream(openAssetFileDescriptor.createInputStream());
                            try {
                                fileOutputStream = new FileOutputStream(a);
                                byte[] bArr = new byte[4096];
                                while (true) {
                                    int read = bufferedInputStream.read(bArr);
                                    if (read <= 0) {
                                        break;
                                    }
                                    fileOutputStream.write(bArr, 0, read);
                                }
                                fileOutputStream.close();
                                bufferedInputStream.close();
                            } catch (Throwable th) {
                                bufferedInputStream.close();
                                throw th;
                            }
                        }
                    }
                    Log.i("SplitCompat", "Splits copied.");
                    try {
                        if (this.f1302c.mo44105a()) {
                            Log.i("SplitCompat", "Splits verified.");
                            num = Integer.valueOf(i);
                            fileLock.release();
                        } else {
                            Log.e("SplitCompat", "Split verification failed.");
                            i = -11;
                            num = Integer.valueOf(i);
                            fileLock.release();
                        }
                    } catch (Exception e2) {
                        Log.e("SplitCompat", "Error verifying splits.", e2);
                    }
                } catch (Exception e3) {
                    Log.e("SplitCompat", "Error copying splits.", e3);
                    i = -13;
                } catch (Throwable th2) {
                    C3046cd.m768a(th, th2);
                }
            }
            if (channel != null) {
                channel.close();
            }
            return num;
            throw th;
            throw th;
        } catch (Exception e4) {
            Log.e("SplitCompat", "Error locking files.", e4);
            return -13;
        } catch (Throwable th3) {
            C3046cd.m768a(th, th3);
        }
    }

    /* renamed from: a */
    static /* synthetic */ void m651a(C3002an anVar, C3124b bVar) {
        try {
            if (SplitCompat.m884a(C3156v.m1053a(anVar.f1300a))) {
                Log.i("SplitCompat", "Splits installed.");
                bVar.mo44276a();
                return;
            }
            Log.e("SplitCompat", "Emulating splits failed.");
            bVar.mo44277a(-12);
        } catch (Exception e) {
            Log.e("SplitCompat", "Error emulating splits.", e);
            bVar.mo44277a(-12);
        }
    }

    /* renamed from: a */
    static /* synthetic */ void m652a(C3002an anVar, List list, C3124b bVar) {
        Integer a = anVar.m650a((List<Intent>) list);
        if (a == null) {
            return;
        }
        if (a.intValue() == 0) {
            bVar.mo44278b();
        } else {
            bVar.mo44277a(a.intValue());
        }
    }

    /* renamed from: a */
    public final void mo44103a(List<Intent> list, C3124b bVar) {
        mo44104b(list, bVar);
    }

    /* renamed from: b */
    public final void mo44104b(List<Intent> list, C3124b bVar) {
        if (SplitCompat.m883a()) {
            this.f1303d.execute(new C3001am(this, list, bVar));
            return;
        }
        throw new IllegalStateException("Ingestion should only be called in SplitCompat mode.");
    }
}
