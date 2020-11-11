package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.common.util.Clock;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
public final class zzev extends zzg {
    private final zzeu zza = new zzeu(this, zzn(), "google_app_measurement_local.db");
    private boolean zzb;

    zzev(zzgd zzgd) {
        super(zzgd);
    }

    /* access modifiers changed from: protected */
    public final boolean zzz() {
        return false;
    }

    public final void zzab() {
        zzb();
        zzd();
        try {
            int delete = zzae().delete("messages", (String) null, (String[]) null) + 0;
            if (delete > 0) {
                zzr().zzx().zza("Reset local analytics data. records", Integer.valueOf(delete));
            }
        } catch (SQLiteException e) {
            zzr().zzf().zza("Error resetting local analytics data. error", e);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v6, resolved type: android.database.sqlite.SQLiteDatabase} */
    /* JADX WARNING: type inference failed for: r2v0 */
    /* JADX WARNING: type inference failed for: r2v1, types: [int, boolean] */
    /* JADX WARNING: type inference failed for: r7v0 */
    /* JADX WARNING: type inference failed for: r7v1, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r2v4 */
    /* JADX WARNING: type inference failed for: r7v3, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r7v4, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r7v5 */
    /* JADX WARNING: type inference failed for: r7v7 */
    /* JADX WARNING: type inference failed for: r7v8 */
    /* JADX WARNING: type inference failed for: r7v9 */
    /* JADX WARNING: type inference failed for: r7v10 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00cb A[SYNTHETIC, Splitter:B:48:0x00cb] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00e6  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00eb  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x00fd  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0102  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x011a  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x011f  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x012b  */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x0130  */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x0122 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x0122 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x0122 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final boolean zza(int r17, byte[] r18) {
        /*
            r16 = this;
            r1 = r16
            r16.zzb()
            r16.zzd()
            boolean r0 = r1.zzb
            r2 = 0
            if (r0 == 0) goto L_0x000e
            return r2
        L_0x000e:
            android.content.ContentValues r3 = new android.content.ContentValues
            r3.<init>()
            java.lang.Integer r0 = java.lang.Integer.valueOf(r17)
            java.lang.String r4 = "type"
            r3.put(r4, r0)
            java.lang.String r0 = "entry"
            r4 = r18
            r3.put(r0, r4)
            r4 = 5
            r5 = r2
            r6 = r4
        L_0x0027:
            if (r5 >= r4) goto L_0x0134
            r7 = 0
            r8 = 1
            android.database.sqlite.SQLiteDatabase r9 = r16.zzae()     // Catch:{ SQLiteFullException -> 0x0106, SQLiteDatabaseLockedException -> 0x00f3, SQLiteException -> 0x00c7, all -> 0x00c4 }
            if (r9 != 0) goto L_0x003b
            r1.zzb = r8     // Catch:{ SQLiteFullException -> 0x00c2, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x00bc }
            if (r9 == 0) goto L_0x003a
            r9.close()
        L_0x003a:
            return r2
        L_0x003b:
            r9.beginTransaction()     // Catch:{ SQLiteFullException -> 0x00c2, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x00bc }
            r10 = 0
            java.lang.String r0 = "select count(1) from messages"
            android.database.Cursor r12 = r9.rawQuery(r0, r7)     // Catch:{ SQLiteFullException -> 0x00c2, SQLiteDatabaseLockedException -> 0x00c0, SQLiteException -> 0x00bc }
            if (r12 == 0) goto L_0x005e
            boolean r0 = r12.moveToFirst()     // Catch:{ SQLiteFullException -> 0x005a, SQLiteDatabaseLockedException -> 0x0058, SQLiteException -> 0x0056, all -> 0x0053 }
            if (r0 == 0) goto L_0x005e
            long r10 = r12.getLong(r2)     // Catch:{ SQLiteFullException -> 0x005a, SQLiteDatabaseLockedException -> 0x0058, SQLiteException -> 0x0056, all -> 0x0053 }
            goto L_0x005e
        L_0x0053:
            r0 = move-exception
            goto L_0x00f1
        L_0x0056:
            r0 = move-exception
            goto L_0x00be
        L_0x0058:
            r0 = move-exception
            goto L_0x00ba
        L_0x005a:
            r0 = move-exception
            r7 = r12
            goto L_0x0108
        L_0x005e:
            r13 = 100000(0x186a0, double:4.94066E-319)
            int r0 = (r10 > r13 ? 1 : (r10 == r13 ? 0 : -1))
            java.lang.String r15 = "messages"
            if (r0 < 0) goto L_0x00a5
            com.google.android.gms.measurement.internal.zzez r0 = r16.zzr()     // Catch:{ SQLiteFullException -> 0x005a, SQLiteDatabaseLockedException -> 0x0058, SQLiteException -> 0x0056, all -> 0x0053 }
            com.google.android.gms.measurement.internal.zzfb r0 = r0.zzf()     // Catch:{ SQLiteFullException -> 0x005a, SQLiteDatabaseLockedException -> 0x0058, SQLiteException -> 0x0056, all -> 0x0053 }
            java.lang.String r4 = "Data loss, local db full"
            r0.zza(r4)     // Catch:{ SQLiteFullException -> 0x005a, SQLiteDatabaseLockedException -> 0x0058, SQLiteException -> 0x0056, all -> 0x0053 }
            long r13 = r13 - r10
            r10 = 1
            long r13 = r13 + r10
            java.lang.String r0 = "rowid in (select rowid from messages order by rowid asc limit ?)"
            java.lang.String[] r4 = new java.lang.String[r8]     // Catch:{ SQLiteFullException -> 0x005a, SQLiteDatabaseLockedException -> 0x0058, SQLiteException -> 0x0056, all -> 0x0053 }
            java.lang.String r10 = java.lang.Long.toString(r13)     // Catch:{ SQLiteFullException -> 0x005a, SQLiteDatabaseLockedException -> 0x0058, SQLiteException -> 0x0056, all -> 0x0053 }
            r4[r2] = r10     // Catch:{ SQLiteFullException -> 0x005a, SQLiteDatabaseLockedException -> 0x0058, SQLiteException -> 0x0056, all -> 0x0053 }
            int r0 = r9.delete(r15, r0, r4)     // Catch:{ SQLiteFullException -> 0x005a, SQLiteDatabaseLockedException -> 0x0058, SQLiteException -> 0x0056, all -> 0x0053 }
            long r10 = (long) r0     // Catch:{ SQLiteFullException -> 0x005a, SQLiteDatabaseLockedException -> 0x0058, SQLiteException -> 0x0056, all -> 0x0053 }
            int r0 = (r10 > r13 ? 1 : (r10 == r13 ? 0 : -1))
            if (r0 == 0) goto L_0x00a5
            com.google.android.gms.measurement.internal.zzez r0 = r16.zzr()     // Catch:{ SQLiteFullException -> 0x005a, SQLiteDatabaseLockedException -> 0x0058, SQLiteException -> 0x0056, all -> 0x0053 }
            com.google.android.gms.measurement.internal.zzfb r0 = r0.zzf()     // Catch:{ SQLiteFullException -> 0x005a, SQLiteDatabaseLockedException -> 0x0058, SQLiteException -> 0x0056, all -> 0x0053 }
            java.lang.String r4 = "Different delete count than expected in local db. expected, received, difference"
            java.lang.Long r2 = java.lang.Long.valueOf(r13)     // Catch:{ SQLiteFullException -> 0x005a, SQLiteDatabaseLockedException -> 0x0058, SQLiteException -> 0x0056, all -> 0x0053 }
            java.lang.Long r8 = java.lang.Long.valueOf(r10)     // Catch:{ SQLiteFullException -> 0x005a, SQLiteDatabaseLockedException -> 0x0058, SQLiteException -> 0x0056, all -> 0x0053 }
            long r13 = r13 - r10
            java.lang.Long r10 = java.lang.Long.valueOf(r13)     // Catch:{ SQLiteFullException -> 0x005a, SQLiteDatabaseLockedException -> 0x0058, SQLiteException -> 0x0056, all -> 0x0053 }
            r0.zza(r4, r2, r8, r10)     // Catch:{ SQLiteFullException -> 0x005a, SQLiteDatabaseLockedException -> 0x0058, SQLiteException -> 0x0056, all -> 0x0053 }
        L_0x00a5:
            r9.insertOrThrow(r15, r7, r3)     // Catch:{ SQLiteFullException -> 0x005a, SQLiteDatabaseLockedException -> 0x0058, SQLiteException -> 0x0056, all -> 0x0053 }
            r9.setTransactionSuccessful()     // Catch:{ SQLiteFullException -> 0x005a, SQLiteDatabaseLockedException -> 0x0058, SQLiteException -> 0x0056, all -> 0x0053 }
            r9.endTransaction()     // Catch:{ SQLiteFullException -> 0x005a, SQLiteDatabaseLockedException -> 0x0058, SQLiteException -> 0x0056, all -> 0x0053 }
            if (r12 == 0) goto L_0x00b3
            r12.close()
        L_0x00b3:
            if (r9 == 0) goto L_0x00b8
            r9.close()
        L_0x00b8:
            r2 = 1
            return r2
        L_0x00ba:
            r7 = r12
            goto L_0x00f5
        L_0x00bc:
            r0 = move-exception
            r12 = r7
        L_0x00be:
            r7 = r9
            goto L_0x00c9
        L_0x00c0:
            r0 = move-exception
            goto L_0x00f5
        L_0x00c2:
            r0 = move-exception
            goto L_0x0108
        L_0x00c4:
            r0 = move-exception
            r9 = r7
            goto L_0x0129
        L_0x00c7:
            r0 = move-exception
            r12 = r7
        L_0x00c9:
            if (r7 == 0) goto L_0x00d4
            boolean r2 = r7.inTransaction()     // Catch:{ all -> 0x00ef }
            if (r2 == 0) goto L_0x00d4
            r7.endTransaction()     // Catch:{ all -> 0x00ef }
        L_0x00d4:
            com.google.android.gms.measurement.internal.zzez r2 = r16.zzr()     // Catch:{ all -> 0x00ef }
            com.google.android.gms.measurement.internal.zzfb r2 = r2.zzf()     // Catch:{ all -> 0x00ef }
            java.lang.String r4 = "Error writing entry to local database"
            r2.zza(r4, r0)     // Catch:{ all -> 0x00ef }
            r2 = 1
            r1.zzb = r2     // Catch:{ all -> 0x00ef }
            if (r12 == 0) goto L_0x00e9
            r12.close()
        L_0x00e9:
            if (r7 == 0) goto L_0x0122
            r7.close()
            goto L_0x0122
        L_0x00ef:
            r0 = move-exception
            r9 = r7
        L_0x00f1:
            r7 = r12
            goto L_0x0129
        L_0x00f3:
            r0 = move-exception
            r9 = r7
        L_0x00f5:
            long r10 = (long) r6
            android.os.SystemClock.sleep(r10)     // Catch:{ all -> 0x0128 }
            int r6 = r6 + 20
            if (r7 == 0) goto L_0x0100
            r7.close()
        L_0x0100:
            if (r9 == 0) goto L_0x0122
            r9.close()
            goto L_0x0122
        L_0x0106:
            r0 = move-exception
            r9 = r7
        L_0x0108:
            com.google.android.gms.measurement.internal.zzez r2 = r16.zzr()     // Catch:{ all -> 0x0128 }
            com.google.android.gms.measurement.internal.zzfb r2 = r2.zzf()     // Catch:{ all -> 0x0128 }
            java.lang.String r4 = "Error writing entry; local database full"
            r2.zza(r4, r0)     // Catch:{ all -> 0x0128 }
            r2 = 1
            r1.zzb = r2     // Catch:{ all -> 0x0128 }
            if (r7 == 0) goto L_0x011d
            r7.close()
        L_0x011d:
            if (r9 == 0) goto L_0x0122
            r9.close()
        L_0x0122:
            int r5 = r5 + 1
            r2 = 0
            r4 = 5
            goto L_0x0027
        L_0x0128:
            r0 = move-exception
        L_0x0129:
            if (r7 == 0) goto L_0x012e
            r7.close()
        L_0x012e:
            if (r9 == 0) goto L_0x0133
            r9.close()
        L_0x0133:
            throw r0
        L_0x0134:
            com.google.android.gms.measurement.internal.zzez r0 = r16.zzr()
            com.google.android.gms.measurement.internal.zzfb r0 = r0.zzx()
            java.lang.String r2 = "Failed to write entry to local database"
            r0.zza(r2)
            r2 = 0
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzev.zza(int, byte[]):boolean");
    }

    public final boolean zza(zzao zzao) {
        Parcel obtain = Parcel.obtain();
        zzao.writeToParcel(obtain, 0);
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        if (marshall.length <= 131072) {
            return zza(0, marshall);
        }
        zzr().zzg().zza("Event is too long for local database. Sending event directly to service");
        return false;
    }

    public final boolean zza(zzkr zzkr) {
        Parcel obtain = Parcel.obtain();
        zzkr.writeToParcel(obtain, 0);
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        if (marshall.length <= 131072) {
            return zza(1, marshall);
        }
        zzr().zzg().zza("User property too long for local database. Sending directly to service");
        return false;
    }

    public final boolean zza(zzw zzw) {
        zzp();
        byte[] zza2 = zzkw.zza((Parcelable) zzw);
        if (zza2.length <= 131072) {
            return zza(2, zza2);
        }
        zzr().zzg().zza("Conditional user property too long for local database. Sending directly to service");
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:114:0x01a0, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x01a8, code lost:
        r5 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:132:0x01bc, code lost:
        if (r15.inTransaction() != false) goto L_0x01be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:133:0x01be, code lost:
        r15.endTransaction();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:0x01d0, code lost:
        r10.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:138:0x01d5, code lost:
        r15.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:146:0x01e4, code lost:
        r10.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:148:0x01e9, code lost:
        r15.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:158:0x020c, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:159:0x020d, code lost:
        r3 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:160:0x020e, code lost:
        r5 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:162:0x0211, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:164:0x0216, code lost:
        r5.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0034, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0037, code lost:
        r0 = e;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x01a0 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:12:0x002c] */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x01a7 A[ExcHandler: SQLiteDatabaseLockedException (e android.database.sqlite.SQLiteDatabaseLockedException), Splitter:B:12:0x002c] */
    /* JADX WARNING: Removed duplicated region for block: B:130:0x01b8 A[SYNTHETIC, Splitter:B:130:0x01b8] */
    /* JADX WARNING: Removed duplicated region for block: B:136:0x01d0  */
    /* JADX WARNING: Removed duplicated region for block: B:138:0x01d5  */
    /* JADX WARNING: Removed duplicated region for block: B:146:0x01e4  */
    /* JADX WARNING: Removed duplicated region for block: B:148:0x01e9  */
    /* JADX WARNING: Removed duplicated region for block: B:154:0x01ff  */
    /* JADX WARNING: Removed duplicated region for block: B:156:0x0204  */
    /* JADX WARNING: Removed duplicated region for block: B:162:0x0211  */
    /* JADX WARNING: Removed duplicated region for block: B:164:0x0216  */
    /* JADX WARNING: Removed duplicated region for block: B:172:0x0207 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:174:0x0207 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:176:0x0207 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.List<com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable> zza(int r22) {
        /*
            r21 = this;
            r1 = r21
            java.lang.String r2 = "Error reading entries from local database"
            r21.zzd()
            r21.zzb()
            boolean r0 = r1.zzb
            r3 = 0
            if (r0 == 0) goto L_0x0010
            return r3
        L_0x0010:
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            boolean r0 = r21.zzaf()
            if (r0 != 0) goto L_0x001c
            return r4
        L_0x001c:
            r5 = 5
            r6 = 0
            r8 = r5
            r7 = r6
        L_0x0021:
            if (r7 >= r5) goto L_0x021a
            r9 = 1
            android.database.sqlite.SQLiteDatabase r15 = r21.zzae()     // Catch:{ SQLiteFullException -> 0x01ed, SQLiteDatabaseLockedException -> 0x01d9, SQLiteException -> 0x01b3, all -> 0x01b0 }
            if (r15 != 0) goto L_0x003a
            r1.zzb = r9     // Catch:{ SQLiteFullException -> 0x0037, SQLiteDatabaseLockedException -> 0x01a7, SQLiteException -> 0x0034, all -> 0x01a0 }
            if (r15 == 0) goto L_0x0033
            r15.close()
        L_0x0033:
            return r3
        L_0x0034:
            r0 = move-exception
            goto L_0x01a5
        L_0x0037:
            r0 = move-exception
            goto L_0x01ae
        L_0x003a:
            r15.beginTransaction()     // Catch:{ SQLiteFullException -> 0x01ac, SQLiteDatabaseLockedException -> 0x01a7, SQLiteException -> 0x01a3, all -> 0x01a0 }
            long r10 = zza((android.database.sqlite.SQLiteDatabase) r15)     // Catch:{ SQLiteFullException -> 0x01ac, SQLiteDatabaseLockedException -> 0x01a7, SQLiteException -> 0x01a3, all -> 0x01a0 }
            r19 = -1
            int r0 = (r10 > r19 ? 1 : (r10 == r19 ? 0 : -1))
            if (r0 == 0) goto L_0x0056
            java.lang.String r0 = "rowid<?"
            java.lang.String[] r12 = new java.lang.String[r9]     // Catch:{ SQLiteFullException -> 0x0037, SQLiteDatabaseLockedException -> 0x01a7, SQLiteException -> 0x0034, all -> 0x01a0 }
            java.lang.String r10 = java.lang.String.valueOf(r10)     // Catch:{ SQLiteFullException -> 0x0037, SQLiteDatabaseLockedException -> 0x01a7, SQLiteException -> 0x0034, all -> 0x01a0 }
            r12[r6] = r10     // Catch:{ SQLiteFullException -> 0x0037, SQLiteDatabaseLockedException -> 0x01a7, SQLiteException -> 0x0034, all -> 0x01a0 }
            r13 = r0
            r14 = r12
            goto L_0x0058
        L_0x0056:
            r13 = r3
            r14 = r13
        L_0x0058:
            java.lang.String r11 = "messages"
            java.lang.String r0 = "rowid"
            java.lang.String r10 = "type"
            java.lang.String r12 = "entry"
            java.lang.String[] r12 = new java.lang.String[]{r0, r10, r12}     // Catch:{ SQLiteFullException -> 0x01ac, SQLiteDatabaseLockedException -> 0x01a7, SQLiteException -> 0x01a3, all -> 0x01a0 }
            r0 = 0
            r16 = 0
            java.lang.String r17 = "rowid asc"
            r10 = 100
            java.lang.String r18 = java.lang.Integer.toString(r10)     // Catch:{ SQLiteFullException -> 0x01ac, SQLiteDatabaseLockedException -> 0x01a7, SQLiteException -> 0x01a3, all -> 0x01a0 }
            r10 = r15
            r5 = r15
            r15 = r0
            android.database.Cursor r10 = r10.query(r11, r12, r13, r14, r15, r16, r17, r18)     // Catch:{ SQLiteFullException -> 0x019b, SQLiteDatabaseLockedException -> 0x0199, SQLiteException -> 0x0195, all -> 0x0192 }
        L_0x0077:
            boolean r0 = r10.moveToNext()     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
            if (r0 == 0) goto L_0x0153
            long r19 = r10.getLong(r6)     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
            int r0 = r10.getInt(r9)     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
            r11 = 2
            byte[] r12 = r10.getBlob(r11)     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
            if (r0 != 0) goto L_0x00c1
            android.os.Parcel r11 = android.os.Parcel.obtain()     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
            int r0 = r12.length     // Catch:{ ParseException -> 0x00ab }
            r11.unmarshall(r12, r6, r0)     // Catch:{ ParseException -> 0x00ab }
            r11.setDataPosition(r6)     // Catch:{ ParseException -> 0x00ab }
            android.os.Parcelable$Creator<com.google.android.gms.measurement.internal.zzao> r0 = com.google.android.gms.measurement.internal.zzao.CREATOR     // Catch:{ ParseException -> 0x00ab }
            java.lang.Object r0 = r0.createFromParcel(r11)     // Catch:{ ParseException -> 0x00ab }
            com.google.android.gms.measurement.internal.zzao r0 = (com.google.android.gms.measurement.internal.zzao) r0     // Catch:{ ParseException -> 0x00ab }
            r11.recycle()     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
            if (r0 == 0) goto L_0x00a8
            r4.add(r0)     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
        L_0x00a8:
            goto L_0x0077
        L_0x00a9:
            r0 = move-exception
            goto L_0x00bd
        L_0x00ab:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zzez r0 = r21.zzr()     // Catch:{ all -> 0x00a9 }
            com.google.android.gms.measurement.internal.zzfb r0 = r0.zzf()     // Catch:{ all -> 0x00a9 }
            java.lang.String r12 = "Failed to load event from local database"
            r0.zza(r12)     // Catch:{ all -> 0x00a9 }
            r11.recycle()     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
            goto L_0x0077
        L_0x00bd:
            r11.recycle()     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
            throw r0     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
        L_0x00c1:
            if (r0 != r9) goto L_0x00f9
            android.os.Parcel r11 = android.os.Parcel.obtain()     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
            int r0 = r12.length     // Catch:{ ParseException -> 0x00dd }
            r11.unmarshall(r12, r6, r0)     // Catch:{ ParseException -> 0x00dd }
            r11.setDataPosition(r6)     // Catch:{ ParseException -> 0x00dd }
            android.os.Parcelable$Creator<com.google.android.gms.measurement.internal.zzkr> r0 = com.google.android.gms.measurement.internal.zzkr.CREATOR     // Catch:{ ParseException -> 0x00dd }
            java.lang.Object r0 = r0.createFromParcel(r11)     // Catch:{ ParseException -> 0x00dd }
            com.google.android.gms.measurement.internal.zzkr r0 = (com.google.android.gms.measurement.internal.zzkr) r0     // Catch:{ ParseException -> 0x00dd }
            r11.recycle()     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
            goto L_0x00ef
        L_0x00db:
            r0 = move-exception
            goto L_0x00f5
        L_0x00dd:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zzez r0 = r21.zzr()     // Catch:{ all -> 0x00db }
            com.google.android.gms.measurement.internal.zzfb r0 = r0.zzf()     // Catch:{ all -> 0x00db }
            java.lang.String r12 = "Failed to load user property from local database"
            r0.zza(r12)     // Catch:{ all -> 0x00db }
            r11.recycle()     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
            r0 = r3
        L_0x00ef:
            if (r0 == 0) goto L_0x00f4
            r4.add(r0)     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
        L_0x00f4:
            goto L_0x0077
        L_0x00f5:
            r11.recycle()     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
            throw r0     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
        L_0x00f9:
            if (r0 != r11) goto L_0x0132
            android.os.Parcel r11 = android.os.Parcel.obtain()     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
            int r0 = r12.length     // Catch:{ ParseException -> 0x0115 }
            r11.unmarshall(r12, r6, r0)     // Catch:{ ParseException -> 0x0115 }
            r11.setDataPosition(r6)     // Catch:{ ParseException -> 0x0115 }
            android.os.Parcelable$Creator<com.google.android.gms.measurement.internal.zzw> r0 = com.google.android.gms.measurement.internal.zzw.CREATOR     // Catch:{ ParseException -> 0x0115 }
            java.lang.Object r0 = r0.createFromParcel(r11)     // Catch:{ ParseException -> 0x0115 }
            com.google.android.gms.measurement.internal.zzw r0 = (com.google.android.gms.measurement.internal.zzw) r0     // Catch:{ ParseException -> 0x0115 }
            r11.recycle()     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
            goto L_0x0127
        L_0x0113:
            r0 = move-exception
            goto L_0x012e
        L_0x0115:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zzez r0 = r21.zzr()     // Catch:{ all -> 0x0113 }
            com.google.android.gms.measurement.internal.zzfb r0 = r0.zzf()     // Catch:{ all -> 0x0113 }
            java.lang.String r12 = "Failed to load conditional user property from local database"
            r0.zza(r12)     // Catch:{ all -> 0x0113 }
            r11.recycle()     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
            r0 = r3
        L_0x0127:
            if (r0 == 0) goto L_0x012c
            r4.add(r0)     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
        L_0x012c:
            goto L_0x0077
        L_0x012e:
            r11.recycle()     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
            throw r0     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
        L_0x0132:
            r11 = 3
            if (r0 != r11) goto L_0x0144
            com.google.android.gms.measurement.internal.zzez r0 = r21.zzr()     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
            com.google.android.gms.measurement.internal.zzfb r0 = r0.zzi()     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
            java.lang.String r11 = "Skipping app launch break"
            r0.zza(r11)     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
            goto L_0x0077
        L_0x0144:
            com.google.android.gms.measurement.internal.zzez r0 = r21.zzr()     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
            com.google.android.gms.measurement.internal.zzfb r0 = r0.zzf()     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
            java.lang.String r11 = "Unknown record type in local database"
            r0.zza(r11)     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
            goto L_0x0077
        L_0x0153:
            java.lang.String r0 = "messages"
            java.lang.String r11 = "rowid <= ?"
            java.lang.String[] r12 = new java.lang.String[r9]     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
            java.lang.String r13 = java.lang.Long.toString(r19)     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
            r12[r6] = r13     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
            int r0 = r5.delete(r0, r11, r12)     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
            int r11 = r4.size()     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
            if (r0 >= r11) goto L_0x0176
            com.google.android.gms.measurement.internal.zzez r0 = r21.zzr()     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
            com.google.android.gms.measurement.internal.zzfb r0 = r0.zzf()     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
            java.lang.String r11 = "Fewer entries removed from local database than expected"
            r0.zza(r11)     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
        L_0x0176:
            r5.setTransactionSuccessful()     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
            r5.endTransaction()     // Catch:{ SQLiteFullException -> 0x0190, SQLiteDatabaseLockedException -> 0x018e, SQLiteException -> 0x018c, all -> 0x0188 }
            if (r10 == 0) goto L_0x0182
            r10.close()
        L_0x0182:
            if (r5 == 0) goto L_0x0187
            r5.close()
        L_0x0187:
            return r4
        L_0x0188:
            r0 = move-exception
            r3 = r10
            goto L_0x020f
        L_0x018c:
            r0 = move-exception
            goto L_0x0197
        L_0x018e:
            r0 = move-exception
            goto L_0x01aa
        L_0x0190:
            r0 = move-exception
            goto L_0x019d
        L_0x0192:
            r0 = move-exception
            goto L_0x020f
        L_0x0195:
            r0 = move-exception
            r10 = r3
        L_0x0197:
            r15 = r5
            goto L_0x01b6
        L_0x0199:
            r0 = move-exception
            goto L_0x01a9
        L_0x019b:
            r0 = move-exception
            r10 = r3
        L_0x019d:
            r15 = r5
            goto L_0x01f0
        L_0x01a0:
            r0 = move-exception
            goto L_0x020e
        L_0x01a3:
            r0 = move-exception
            r5 = r15
        L_0x01a5:
            r10 = r3
            goto L_0x01b6
        L_0x01a7:
            r0 = move-exception
            r5 = r15
        L_0x01a9:
            r10 = r3
        L_0x01aa:
            r15 = r5
            goto L_0x01dc
        L_0x01ac:
            r0 = move-exception
            r5 = r15
        L_0x01ae:
            r10 = r3
            goto L_0x01f0
        L_0x01b0:
            r0 = move-exception
            r5 = r3
            goto L_0x020f
        L_0x01b3:
            r0 = move-exception
            r10 = r3
            r15 = r10
        L_0x01b6:
            if (r15 == 0) goto L_0x01c1
            boolean r5 = r15.inTransaction()     // Catch:{ all -> 0x020c }
            if (r5 == 0) goto L_0x01c1
            r15.endTransaction()     // Catch:{ all -> 0x020c }
        L_0x01c1:
            com.google.android.gms.measurement.internal.zzez r5 = r21.zzr()     // Catch:{ all -> 0x020c }
            com.google.android.gms.measurement.internal.zzfb r5 = r5.zzf()     // Catch:{ all -> 0x020c }
            r5.zza(r2, r0)     // Catch:{ all -> 0x020c }
            r1.zzb = r9     // Catch:{ all -> 0x020c }
            if (r10 == 0) goto L_0x01d3
            r10.close()
        L_0x01d3:
            if (r15 == 0) goto L_0x0207
            r15.close()
            goto L_0x0207
        L_0x01d9:
            r0 = move-exception
            r10 = r3
            r15 = r10
        L_0x01dc:
            long r11 = (long) r8
            android.os.SystemClock.sleep(r11)     // Catch:{ all -> 0x020c }
            int r8 = r8 + 20
            if (r10 == 0) goto L_0x01e7
            r10.close()
        L_0x01e7:
            if (r15 == 0) goto L_0x0207
            r15.close()
            goto L_0x0207
        L_0x01ed:
            r0 = move-exception
            r10 = r3
            r15 = r10
        L_0x01f0:
            com.google.android.gms.measurement.internal.zzez r5 = r21.zzr()     // Catch:{ all -> 0x020c }
            com.google.android.gms.measurement.internal.zzfb r5 = r5.zzf()     // Catch:{ all -> 0x020c }
            r5.zza(r2, r0)     // Catch:{ all -> 0x020c }
            r1.zzb = r9     // Catch:{ all -> 0x020c }
            if (r10 == 0) goto L_0x0202
            r10.close()
        L_0x0202:
            if (r15 == 0) goto L_0x0207
            r15.close()
        L_0x0207:
            int r7 = r7 + 1
            r5 = 5
            goto L_0x0021
        L_0x020c:
            r0 = move-exception
            r3 = r10
        L_0x020e:
            r5 = r15
        L_0x020f:
            if (r3 == 0) goto L_0x0214
            r3.close()
        L_0x0214:
            if (r5 == 0) goto L_0x0219
            r5.close()
        L_0x0219:
            throw r0
        L_0x021a:
            com.google.android.gms.measurement.internal.zzez r0 = r21.zzr()
            com.google.android.gms.measurement.internal.zzfb r0 = r0.zzi()
            java.lang.String r2 = "Failed to read events from database in reasonable time"
            r0.zza(r2)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzev.zza(int):java.util.List");
    }

    public final boolean zzac() {
        return zza(3, new byte[0]);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:43:0x008b, code lost:
        r3 = r3 + 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean zzad() {
        /*
            r11 = this;
            java.lang.String r0 = "Error deleting app launch break from local database"
            r11.zzd()
            r11.zzb()
            boolean r1 = r11.zzb
            r2 = 0
            if (r1 == 0) goto L_0x000e
            return r2
        L_0x000e:
            boolean r1 = r11.zzaf()
            if (r1 != 0) goto L_0x0015
            return r2
        L_0x0015:
            r1 = 5
            r4 = r1
            r3 = r2
        L_0x0019:
            if (r3 >= r1) goto L_0x0094
            r5 = 0
            r6 = 1
            android.database.sqlite.SQLiteDatabase r5 = r11.zzae()     // Catch:{ SQLiteFullException -> 0x0078, SQLiteDatabaseLockedException -> 0x006b, SQLiteException -> 0x004c }
            if (r5 != 0) goto L_0x002b
            r11.zzb = r6     // Catch:{ SQLiteFullException -> 0x0078, SQLiteDatabaseLockedException -> 0x006b, SQLiteException -> 0x004c }
            if (r5 == 0) goto L_0x002a
            r5.close()
        L_0x002a:
            return r2
        L_0x002b:
            r5.beginTransaction()     // Catch:{ SQLiteFullException -> 0x0078, SQLiteDatabaseLockedException -> 0x006b, SQLiteException -> 0x004c }
            java.lang.String r7 = "messages"
            java.lang.String r8 = "type == ?"
            java.lang.String[] r9 = new java.lang.String[r6]     // Catch:{ SQLiteFullException -> 0x0078, SQLiteDatabaseLockedException -> 0x006b, SQLiteException -> 0x004c }
            r10 = 3
            java.lang.String r10 = java.lang.Integer.toString(r10)     // Catch:{ SQLiteFullException -> 0x0078, SQLiteDatabaseLockedException -> 0x006b, SQLiteException -> 0x004c }
            r9[r2] = r10     // Catch:{ SQLiteFullException -> 0x0078, SQLiteDatabaseLockedException -> 0x006b, SQLiteException -> 0x004c }
            r5.delete(r7, r8, r9)     // Catch:{ SQLiteFullException -> 0x0078, SQLiteDatabaseLockedException -> 0x006b, SQLiteException -> 0x004c }
            r5.setTransactionSuccessful()     // Catch:{ SQLiteFullException -> 0x0078, SQLiteDatabaseLockedException -> 0x006b, SQLiteException -> 0x004c }
            r5.endTransaction()     // Catch:{ SQLiteFullException -> 0x0078, SQLiteDatabaseLockedException -> 0x006b, SQLiteException -> 0x004c }
            if (r5 == 0) goto L_0x0049
            r5.close()
        L_0x0049:
            return r6
        L_0x004a:
            r0 = move-exception
            goto L_0x008e
        L_0x004c:
            r7 = move-exception
            if (r5 == 0) goto L_0x0058
            boolean r8 = r5.inTransaction()     // Catch:{ all -> 0x004a }
            if (r8 == 0) goto L_0x0058
            r5.endTransaction()     // Catch:{ all -> 0x004a }
        L_0x0058:
            com.google.android.gms.measurement.internal.zzez r8 = r11.zzr()     // Catch:{ all -> 0x004a }
            com.google.android.gms.measurement.internal.zzfb r8 = r8.zzf()     // Catch:{ all -> 0x004a }
            r8.zza(r0, r7)     // Catch:{ all -> 0x004a }
            r11.zzb = r6     // Catch:{ all -> 0x004a }
            if (r5 == 0) goto L_0x008b
            r5.close()
            goto L_0x008b
        L_0x006b:
            r6 = move-exception
            long r6 = (long) r4
            android.os.SystemClock.sleep(r6)     // Catch:{ all -> 0x004a }
            int r4 = r4 + 20
            if (r5 == 0) goto L_0x008b
            r5.close()
            goto L_0x008b
        L_0x0078:
            r7 = move-exception
            com.google.android.gms.measurement.internal.zzez r8 = r11.zzr()     // Catch:{ all -> 0x004a }
            com.google.android.gms.measurement.internal.zzfb r8 = r8.zzf()     // Catch:{ all -> 0x004a }
            r8.zza(r0, r7)     // Catch:{ all -> 0x004a }
            r11.zzb = r6     // Catch:{ all -> 0x004a }
            if (r5 == 0) goto L_0x008b
            r5.close()
        L_0x008b:
            int r3 = r3 + 1
            goto L_0x0019
        L_0x008e:
            if (r5 == 0) goto L_0x0093
            r5.close()
        L_0x0093:
            throw r0
        L_0x0094:
            com.google.android.gms.measurement.internal.zzez r0 = r11.zzr()
            com.google.android.gms.measurement.internal.zzfb r0 = r0.zzi()
            java.lang.String r1 = "Error deleting app launch break from local database in reasonable time"
            r0.zza(r1)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzev.zzad():boolean");
    }

    private static long zza(SQLiteDatabase sQLiteDatabase) {
        Cursor cursor = null;
        try {
            cursor = sQLiteDatabase.query("messages", new String[]{"rowid"}, "type=?", new String[]{ExifInterface.GPS_MEASUREMENT_3D}, (String) null, (String) null, "rowid desc", "1");
            if (cursor.moveToFirst()) {
                return cursor.getLong(0);
            }
            if (cursor == null) {
                return -1;
            }
            cursor.close();
            return -1;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private final SQLiteDatabase zzae() throws SQLiteException {
        if (this.zzb) {
            return null;
        }
        SQLiteDatabase writableDatabase = this.zza.getWritableDatabase();
        if (writableDatabase != null) {
            return writableDatabase;
        }
        this.zzb = true;
        return null;
    }

    private final boolean zzaf() {
        return zzn().getDatabasePath("google_app_measurement_local.db").exists();
    }

    public final /* bridge */ /* synthetic */ void zza() {
        super.zza();
    }

    public final /* bridge */ /* synthetic */ void zzb() {
        super.zzb();
    }

    public final /* bridge */ /* synthetic */ void zzc() {
        super.zzc();
    }

    public final /* bridge */ /* synthetic */ void zzd() {
        super.zzd();
    }

    public final /* bridge */ /* synthetic */ zza zze() {
        return super.zze();
    }

    public final /* bridge */ /* synthetic */ zzhh zzf() {
        return super.zzf();
    }

    public final /* bridge */ /* synthetic */ zzes zzg() {
        return super.zzg();
    }

    public final /* bridge */ /* synthetic */ zzis zzh() {
        return super.zzh();
    }

    public final /* bridge */ /* synthetic */ zzin zzi() {
        return super.zzi();
    }

    public final /* bridge */ /* synthetic */ zzev zzj() {
        return super.zzj();
    }

    public final /* bridge */ /* synthetic */ zzjw zzk() {
        return super.zzk();
    }

    public final /* bridge */ /* synthetic */ zzai zzl() {
        return super.zzl();
    }

    public final /* bridge */ /* synthetic */ Clock zzm() {
        return super.zzm();
    }

    public final /* bridge */ /* synthetic */ Context zzn() {
        return super.zzn();
    }

    public final /* bridge */ /* synthetic */ zzex zzo() {
        return super.zzo();
    }

    public final /* bridge */ /* synthetic */ zzkw zzp() {
        return super.zzp();
    }

    public final /* bridge */ /* synthetic */ zzfw zzq() {
        return super.zzq();
    }

    public final /* bridge */ /* synthetic */ zzez zzr() {
        return super.zzr();
    }

    public final /* bridge */ /* synthetic */ zzfl zzs() {
        return super.zzs();
    }

    public final /* bridge */ /* synthetic */ zzy zzt() {
        return super.zzt();
    }

    public final /* bridge */ /* synthetic */ zzx zzu() {
        return super.zzu();
    }
}
