package com.google.android.play.core.assetpacks;

import com.google.android.play.core.internal.C3037bv;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/* renamed from: com.google.android.play.core.assetpacks.bf */
final class C2890bf extends C3037bv {

    /* renamed from: a */
    private final File f980a;

    /* renamed from: b */
    private final File f981b;

    /* renamed from: c */
    private final NavigableMap<Long, File> f982c = new TreeMap();

    C2890bf(File file, File file2) throws IOException {
        this.f980a = file;
        this.f981b = file2;
        List<File> a = C2958dt.m562a(file, file2);
        if (!a.isEmpty()) {
            int size = a.size();
            long j = 0;
            for (int i = 0; i < size; i++) {
                File file3 = a.get(i);
                this.f982c.put(Long.valueOf(j), file3);
                j += file3.length();
            }
            return;
        }
        throw new C2909by(String.format("Virtualized slice archive empty for %s, %s", new Object[]{file, file2}));
    }

    /* renamed from: a */
    private final InputStream m422a(long j, Long l) throws IOException {
        FileInputStream fileInputStream = new FileInputStream((File) this.f982c.get(l));
        if (fileInputStream.skip(j - l.longValue()) == j - l.longValue()) {
            return fileInputStream;
        }
        throw new C2909by(String.format("Virtualized slice archive corrupt, could not skip in file with key %s", new Object[]{l}));
    }

    /* renamed from: a */
    public final long mo43969a() {
        Map.Entry<Long, File> lastEntry = this.f982c.lastEntry();
        return lastEntry.getKey().longValue() + lastEntry.getValue().length();
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final InputStream mo43970a(long j, long j2) throws IOException {
        if (j < 0 || j2 < 0) {
            throw new C2909by(String.format("Invalid input parameters %s, %s", new Object[]{Long.valueOf(j), Long.valueOf(j2)}));
        }
        long j3 = j + j2;
        if (j3 <= mo43969a()) {
            Long floorKey = this.f982c.floorKey(Long.valueOf(j));
            Long floorKey2 = this.f982c.floorKey(Long.valueOf(j3));
            if (floorKey.equals(floorKey2)) {
                return new C2889be(m422a(j, floorKey), j2);
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(m422a(j, floorKey));
            for (File fileInputStream : this.f982c.subMap(floorKey, false, floorKey2, false).values()) {
                arrayList.add(new FileInputStream(fileInputStream));
            }
            arrayList.add(new C2889be(new FileInputStream((File) this.f982c.get(floorKey2)), j2 - (floorKey2.longValue() - j)));
            return new SequenceInputStream(Collections.enumeration(arrayList));
        }
        throw new C2909by(String.format("Trying to access archive out of bounds. Archive ends at: %s. Tried accessing: %s", new Object[]{Long.valueOf(mo43969a()), Long.valueOf(j3)}));
    }

    public final void close() {
    }
}
