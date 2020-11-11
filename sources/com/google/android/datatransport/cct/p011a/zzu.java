package com.google.android.datatransport.cct.p011a;

import android.util.SparseArray;

/* renamed from: com.google.android.datatransport.cct.a.zzu */
public enum zzu {
    DEFAULT(0),
    UNMETERED_ONLY(1),
    UNMETERED_OR_DAILY(2),
    FAST_IF_RADIO_AWAKE(3),
    NEVER(4),
    UNRECOGNIZED(-1);
    
    private static final SparseArray<zzu> zzg = null;

    static {
        zzu zzu = new zzu("DEFAULT", 0, 0);
        DEFAULT = zzu;
        zzu zzu2 = new zzu("UNMETERED_ONLY", 1, 1);
        UNMETERED_ONLY = zzu2;
        zzu zzu3 = new zzu("UNMETERED_OR_DAILY", 2, 2);
        UNMETERED_OR_DAILY = zzu3;
        zzu zzu4 = new zzu("FAST_IF_RADIO_AWAKE", 3, 3);
        FAST_IF_RADIO_AWAKE = zzu4;
        zzu zzu5 = new zzu("NEVER", 4, 4);
        NEVER = zzu5;
        zzu zzu6 = new zzu("UNRECOGNIZED", 5, -1);
        UNRECOGNIZED = zzu6;
        zzu[] zzuArr = {zzu, zzu2, zzu3, zzu4, zzu5, zzu6};
        SparseArray<zzu> sparseArray = new SparseArray<>();
        zzg = sparseArray;
        sparseArray.put(0, zzu);
        sparseArray.put(1, zzu2);
        sparseArray.put(2, zzu3);
        sparseArray.put(3, zzu4);
        sparseArray.put(4, zzu5);
        sparseArray.put(-1, zzu6);
    }

    private zzu(int i) {
    }
}
