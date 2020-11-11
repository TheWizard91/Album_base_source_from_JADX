package com.google.android.gms.internal.measurement;

import android.os.IBinder;
import android.os.IInterface;

/* compiled from: com.google.android.gms:play-services-measurement-base@@17.4.4 */
public abstract class zzu extends zzc implements zzv {
    public zzu() {
        super("com.google.android.gms.measurement.api.internal.IAppMeasurementDynamiteService");
    }

    public static zzv asInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.measurement.api.internal.IAppMeasurementDynamiteService");
        if (queryLocalInterface instanceof zzv) {
            return (zzv) queryLocalInterface;
        }
        return new zzx(iBinder);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v3, resolved type: com.google.android.gms.internal.measurement.zzy} */
    /* JADX WARNING: type inference failed for: r3v0 */
    /* JADX WARNING: type inference failed for: r3v9, types: [com.google.android.gms.internal.measurement.zzw] */
    /* JADX WARNING: type inference failed for: r3v14, types: [com.google.android.gms.internal.measurement.zzw] */
    /* JADX WARNING: type inference failed for: r3v20, types: [com.google.android.gms.internal.measurement.zzw] */
    /* JADX WARNING: type inference failed for: r3v26, types: [com.google.android.gms.internal.measurement.zzw] */
    /* JADX WARNING: type inference failed for: r3v30, types: [com.google.android.gms.internal.measurement.zzw] */
    /* JADX WARNING: type inference failed for: r3v34, types: [com.google.android.gms.internal.measurement.zzac] */
    /* JADX WARNING: type inference failed for: r3v38, types: [com.google.android.gms.internal.measurement.zzw] */
    /* JADX WARNING: type inference failed for: r3v42, types: [com.google.android.gms.internal.measurement.zzw] */
    /* JADX WARNING: type inference failed for: r3v46, types: [com.google.android.gms.internal.measurement.zzw] */
    /* JADX WARNING: type inference failed for: r3v50, types: [com.google.android.gms.internal.measurement.zzw] */
    /* JADX WARNING: type inference failed for: r3v55, types: [com.google.android.gms.internal.measurement.zzw] */
    /* JADX WARNING: type inference failed for: r3v60, types: [com.google.android.gms.internal.measurement.zzw] */
    /* JADX WARNING: type inference failed for: r3v67, types: [com.google.android.gms.internal.measurement.zzab] */
    /* JADX WARNING: type inference failed for: r3v71, types: [com.google.android.gms.internal.measurement.zzab] */
    /* JADX WARNING: type inference failed for: r3v75, types: [com.google.android.gms.internal.measurement.zzab] */
    /* JADX WARNING: type inference failed for: r3v79, types: [com.google.android.gms.internal.measurement.zzw] */
    /* JADX WARNING: type inference failed for: r3v84, types: [com.google.android.gms.internal.measurement.zzw] */
    /* JADX WARNING: type inference failed for: r3v88 */
    /* JADX WARNING: type inference failed for: r3v89 */
    /* JADX WARNING: type inference failed for: r3v90 */
    /* JADX WARNING: type inference failed for: r3v91 */
    /* JADX WARNING: type inference failed for: r3v92 */
    /* JADX WARNING: type inference failed for: r3v93 */
    /* JADX WARNING: type inference failed for: r3v94 */
    /* JADX WARNING: type inference failed for: r3v95 */
    /* JADX WARNING: type inference failed for: r3v96 */
    /* JADX WARNING: type inference failed for: r3v97 */
    /* JADX WARNING: type inference failed for: r3v98 */
    /* JADX WARNING: type inference failed for: r3v99 */
    /* JADX WARNING: type inference failed for: r3v100 */
    /* JADX WARNING: type inference failed for: r3v101 */
    /* JADX WARNING: type inference failed for: r3v102 */
    /* JADX WARNING: type inference failed for: r3v103 */
    /* JADX WARNING: type inference failed for: r3v104 */
    /* JADX WARNING: type inference failed for: r3v105 */
    /* JADX WARNING: type inference failed for: r3v106 */
    /* JADX WARNING: type inference failed for: r3v107 */
    /* JADX WARNING: type inference failed for: r3v108 */
    /* JADX WARNING: type inference failed for: r3v109 */
    /* JADX WARNING: type inference failed for: r3v110 */
    /* JADX WARNING: type inference failed for: r3v111 */
    /* JADX WARNING: type inference failed for: r3v112 */
    /* JADX WARNING: type inference failed for: r3v113 */
    /* JADX WARNING: type inference failed for: r3v114 */
    /* JADX WARNING: type inference failed for: r3v115 */
    /* JADX WARNING: type inference failed for: r3v116 */
    /* JADX WARNING: type inference failed for: r3v117 */
    /* JADX WARNING: type inference failed for: r3v118 */
    /* JADX WARNING: type inference failed for: r3v119 */
    /* JADX WARNING: type inference failed for: r3v120 */
    /* JADX WARNING: type inference failed for: r3v121 */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean zza(int r11, android.os.Parcel r12, android.os.Parcel r13, int r14) throws android.os.RemoteException {
        /*
            r10 = this;
            java.lang.String r1 = "com.google.android.gms.measurement.api.internal.IEventHandlerProxy"
            java.lang.String r2 = "com.google.android.gms.measurement.api.internal.IBundleReceiver"
            r3 = 0
            switch(r11) {
                case 1: goto L_0x03f7;
                case 2: goto L_0x03d6;
                case 3: goto L_0x039e;
                case 4: goto L_0x0380;
                case 5: goto L_0x0356;
                case 6: goto L_0x0334;
                case 7: goto L_0x0327;
                case 8: goto L_0x0316;
                case 9: goto L_0x0301;
                case 10: goto L_0x02db;
                case 11: goto L_0x02ce;
                case 12: goto L_0x02c5;
                case 13: goto L_0x02bc;
                case 14: goto L_0x02b3;
                case 15: goto L_0x0299;
                case 16: goto L_0x027b;
                case 17: goto L_0x025d;
                case 18: goto L_0x023d;
                case 19: goto L_0x021f;
                case 20: goto L_0x0201;
                case 21: goto L_0x01e3;
                case 22: goto L_0x01c5;
                case 23: goto L_0x01b8;
                case 24: goto L_0x01ab;
                case 25: goto L_0x019a;
                case 26: goto L_0x0189;
                case 27: goto L_0x0170;
                case 28: goto L_0x015f;
                case 29: goto L_0x014e;
                case 30: goto L_0x013d;
                case 31: goto L_0x0113;
                case 32: goto L_0x00e9;
                case 33: goto L_0x00c3;
                case 34: goto L_0x00a5;
                case 35: goto L_0x0087;
                case 36: goto L_0x0069;
                case 37: goto L_0x0060;
                case 38: goto L_0x003e;
                case 39: goto L_0x0035;
                case 40: goto L_0x0017;
                case 41: goto L_0x0008;
                case 42: goto L_0x000a;
                default: goto L_0x0008;
            }
        L_0x0008:
            r0 = 0
            return r0
        L_0x000a:
            android.os.Parcelable$Creator r1 = android.os.Bundle.CREATOR
            android.os.Parcelable r0 = com.google.android.gms.internal.measurement.zzb.zza((android.os.Parcel) r12, r1)
            android.os.Bundle r0 = (android.os.Bundle) r0
            r10.setDefaultEventParameters(r0)
            goto L_0x040f
        L_0x0017:
            android.os.IBinder r0 = r12.readStrongBinder()
            if (r0 != 0) goto L_0x001e
            goto L_0x002f
        L_0x001e:
            android.os.IInterface r1 = r0.queryLocalInterface(r2)
            boolean r2 = r1 instanceof com.google.android.gms.internal.measurement.zzw
            if (r2 == 0) goto L_0x002a
            r3 = r1
            com.google.android.gms.internal.measurement.zzw r3 = (com.google.android.gms.internal.measurement.zzw) r3
            goto L_0x002f
        L_0x002a:
            com.google.android.gms.internal.measurement.zzy r3 = new com.google.android.gms.internal.measurement.zzy
            r3.<init>(r0)
        L_0x002f:
            r10.isDataCollectionEnabled(r3)
            goto L_0x040f
        L_0x0035:
            boolean r0 = com.google.android.gms.internal.measurement.zzb.zza(r12)
            r10.setDataCollectionEnabled(r0)
            goto L_0x040f
        L_0x003e:
            android.os.IBinder r1 = r12.readStrongBinder()
            if (r1 != 0) goto L_0x0045
            goto L_0x0056
        L_0x0045:
            android.os.IInterface r2 = r1.queryLocalInterface(r2)
            boolean r3 = r2 instanceof com.google.android.gms.internal.measurement.zzw
            if (r3 == 0) goto L_0x0051
            r3 = r2
            com.google.android.gms.internal.measurement.zzw r3 = (com.google.android.gms.internal.measurement.zzw) r3
            goto L_0x0056
        L_0x0051:
            com.google.android.gms.internal.measurement.zzy r3 = new com.google.android.gms.internal.measurement.zzy
            r3.<init>(r1)
        L_0x0056:
            int r0 = r12.readInt()
            r10.getTestFlag(r3, r0)
            goto L_0x040f
        L_0x0060:
            java.util.HashMap r0 = com.google.android.gms.internal.measurement.zzb.zzb(r12)
            r10.initForTests(r0)
            goto L_0x040f
        L_0x0069:
            android.os.IBinder r0 = r12.readStrongBinder()
            if (r0 != 0) goto L_0x0070
            goto L_0x0081
        L_0x0070:
            android.os.IInterface r1 = r0.queryLocalInterface(r1)
            boolean r2 = r1 instanceof com.google.android.gms.internal.measurement.zzab
            if (r2 == 0) goto L_0x007c
            r3 = r1
            com.google.android.gms.internal.measurement.zzab r3 = (com.google.android.gms.internal.measurement.zzab) r3
            goto L_0x0081
        L_0x007c:
            com.google.android.gms.internal.measurement.zzad r3 = new com.google.android.gms.internal.measurement.zzad
            r3.<init>(r0)
        L_0x0081:
            r10.unregisterOnMeasurementEventListener(r3)
            goto L_0x040f
        L_0x0087:
            android.os.IBinder r0 = r12.readStrongBinder()
            if (r0 != 0) goto L_0x008e
            goto L_0x009f
        L_0x008e:
            android.os.IInterface r1 = r0.queryLocalInterface(r1)
            boolean r2 = r1 instanceof com.google.android.gms.internal.measurement.zzab
            if (r2 == 0) goto L_0x009a
            r3 = r1
            com.google.android.gms.internal.measurement.zzab r3 = (com.google.android.gms.internal.measurement.zzab) r3
            goto L_0x009f
        L_0x009a:
            com.google.android.gms.internal.measurement.zzad r3 = new com.google.android.gms.internal.measurement.zzad
            r3.<init>(r0)
        L_0x009f:
            r10.registerOnMeasurementEventListener(r3)
            goto L_0x040f
        L_0x00a5:
            android.os.IBinder r0 = r12.readStrongBinder()
            if (r0 != 0) goto L_0x00ac
            goto L_0x00bd
        L_0x00ac:
            android.os.IInterface r1 = r0.queryLocalInterface(r1)
            boolean r2 = r1 instanceof com.google.android.gms.internal.measurement.zzab
            if (r2 == 0) goto L_0x00b8
            r3 = r1
            com.google.android.gms.internal.measurement.zzab r3 = (com.google.android.gms.internal.measurement.zzab) r3
            goto L_0x00bd
        L_0x00b8:
            com.google.android.gms.internal.measurement.zzad r3 = new com.google.android.gms.internal.measurement.zzad
            r3.<init>(r0)
        L_0x00bd:
            r10.setEventInterceptor(r3)
            goto L_0x040f
        L_0x00c3:
            int r1 = r12.readInt()
            java.lang.String r2 = r12.readString()
            android.os.IBinder r3 = r12.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r3 = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(r3)
            android.os.IBinder r4 = r12.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r4 = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(r4)
            android.os.IBinder r0 = r12.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r5 = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(r0)
            r0 = r10
            r0.logHealthData(r1, r2, r3, r4, r5)
            goto L_0x040f
        L_0x00e9:
            android.os.Parcelable$Creator r1 = android.os.Bundle.CREATOR
            android.os.Parcelable r1 = com.google.android.gms.internal.measurement.zzb.zza((android.os.Parcel) r12, r1)
            android.os.Bundle r1 = (android.os.Bundle) r1
            android.os.IBinder r4 = r12.readStrongBinder()
            if (r4 != 0) goto L_0x00f8
            goto L_0x0109
        L_0x00f8:
            android.os.IInterface r2 = r4.queryLocalInterface(r2)
            boolean r3 = r2 instanceof com.google.android.gms.internal.measurement.zzw
            if (r3 == 0) goto L_0x0104
            r3 = r2
            com.google.android.gms.internal.measurement.zzw r3 = (com.google.android.gms.internal.measurement.zzw) r3
            goto L_0x0109
        L_0x0104:
            com.google.android.gms.internal.measurement.zzy r3 = new com.google.android.gms.internal.measurement.zzy
            r3.<init>(r4)
        L_0x0109:
            long r4 = r12.readLong()
            r10.performAction(r1, r3, r4)
            goto L_0x040f
        L_0x0113:
            android.os.IBinder r1 = r12.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r1 = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(r1)
            android.os.IBinder r4 = r12.readStrongBinder()
            if (r4 != 0) goto L_0x0122
            goto L_0x0133
        L_0x0122:
            android.os.IInterface r2 = r4.queryLocalInterface(r2)
            boolean r3 = r2 instanceof com.google.android.gms.internal.measurement.zzw
            if (r3 == 0) goto L_0x012e
            r3 = r2
            com.google.android.gms.internal.measurement.zzw r3 = (com.google.android.gms.internal.measurement.zzw) r3
            goto L_0x0133
        L_0x012e:
            com.google.android.gms.internal.measurement.zzy r3 = new com.google.android.gms.internal.measurement.zzy
            r3.<init>(r4)
        L_0x0133:
            long r4 = r12.readLong()
            r10.onActivitySaveInstanceState(r1, r3, r4)
            goto L_0x040f
        L_0x013d:
            android.os.IBinder r1 = r12.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r1 = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(r1)
            long r2 = r12.readLong()
            r10.onActivityResumed(r1, r2)
            goto L_0x040f
        L_0x014e:
            android.os.IBinder r1 = r12.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r1 = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(r1)
            long r2 = r12.readLong()
            r10.onActivityPaused(r1, r2)
            goto L_0x040f
        L_0x015f:
            android.os.IBinder r1 = r12.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r1 = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(r1)
            long r2 = r12.readLong()
            r10.onActivityDestroyed(r1, r2)
            goto L_0x040f
        L_0x0170:
            android.os.IBinder r1 = r12.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r1 = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(r1)
            android.os.Parcelable$Creator r2 = android.os.Bundle.CREATOR
            android.os.Parcelable r2 = com.google.android.gms.internal.measurement.zzb.zza((android.os.Parcel) r12, r2)
            android.os.Bundle r2 = (android.os.Bundle) r2
            long r3 = r12.readLong()
            r10.onActivityCreated(r1, r2, r3)
            goto L_0x040f
        L_0x0189:
            android.os.IBinder r1 = r12.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r1 = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(r1)
            long r2 = r12.readLong()
            r10.onActivityStopped(r1, r2)
            goto L_0x040f
        L_0x019a:
            android.os.IBinder r1 = r12.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r1 = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(r1)
            long r2 = r12.readLong()
            r10.onActivityStarted(r1, r2)
            goto L_0x040f
        L_0x01ab:
            java.lang.String r1 = r12.readString()
            long r2 = r12.readLong()
            r10.endAdUnitExposure(r1, r2)
            goto L_0x040f
        L_0x01b8:
            java.lang.String r1 = r12.readString()
            long r2 = r12.readLong()
            r10.beginAdUnitExposure(r1, r2)
            goto L_0x040f
        L_0x01c5:
            android.os.IBinder r0 = r12.readStrongBinder()
            if (r0 != 0) goto L_0x01cc
            goto L_0x01dd
        L_0x01cc:
            android.os.IInterface r1 = r0.queryLocalInterface(r2)
            boolean r2 = r1 instanceof com.google.android.gms.internal.measurement.zzw
            if (r2 == 0) goto L_0x01d8
            r3 = r1
            com.google.android.gms.internal.measurement.zzw r3 = (com.google.android.gms.internal.measurement.zzw) r3
            goto L_0x01dd
        L_0x01d8:
            com.google.android.gms.internal.measurement.zzy r3 = new com.google.android.gms.internal.measurement.zzy
            r3.<init>(r0)
        L_0x01dd:
            r10.generateEventId(r3)
            goto L_0x040f
        L_0x01e3:
            android.os.IBinder r0 = r12.readStrongBinder()
            if (r0 != 0) goto L_0x01ea
            goto L_0x01fb
        L_0x01ea:
            android.os.IInterface r1 = r0.queryLocalInterface(r2)
            boolean r2 = r1 instanceof com.google.android.gms.internal.measurement.zzw
            if (r2 == 0) goto L_0x01f6
            r3 = r1
            com.google.android.gms.internal.measurement.zzw r3 = (com.google.android.gms.internal.measurement.zzw) r3
            goto L_0x01fb
        L_0x01f6:
            com.google.android.gms.internal.measurement.zzy r3 = new com.google.android.gms.internal.measurement.zzy
            r3.<init>(r0)
        L_0x01fb:
            r10.getGmpAppId(r3)
            goto L_0x040f
        L_0x0201:
            android.os.IBinder r0 = r12.readStrongBinder()
            if (r0 != 0) goto L_0x0208
            goto L_0x0219
        L_0x0208:
            android.os.IInterface r1 = r0.queryLocalInterface(r2)
            boolean r2 = r1 instanceof com.google.android.gms.internal.measurement.zzw
            if (r2 == 0) goto L_0x0214
            r3 = r1
            com.google.android.gms.internal.measurement.zzw r3 = (com.google.android.gms.internal.measurement.zzw) r3
            goto L_0x0219
        L_0x0214:
            com.google.android.gms.internal.measurement.zzy r3 = new com.google.android.gms.internal.measurement.zzy
            r3.<init>(r0)
        L_0x0219:
            r10.getAppInstanceId(r3)
            goto L_0x040f
        L_0x021f:
            android.os.IBinder r0 = r12.readStrongBinder()
            if (r0 != 0) goto L_0x0226
            goto L_0x0237
        L_0x0226:
            android.os.IInterface r1 = r0.queryLocalInterface(r2)
            boolean r2 = r1 instanceof com.google.android.gms.internal.measurement.zzw
            if (r2 == 0) goto L_0x0232
            r3 = r1
            com.google.android.gms.internal.measurement.zzw r3 = (com.google.android.gms.internal.measurement.zzw) r3
            goto L_0x0237
        L_0x0232:
            com.google.android.gms.internal.measurement.zzy r3 = new com.google.android.gms.internal.measurement.zzy
            r3.<init>(r0)
        L_0x0237:
            r10.getCachedAppInstanceId(r3)
            goto L_0x040f
        L_0x023d:
            android.os.IBinder r0 = r12.readStrongBinder()
            if (r0 != 0) goto L_0x0244
            goto L_0x0257
        L_0x0244:
            java.lang.String r1 = "com.google.android.gms.measurement.api.internal.IStringProvider"
            android.os.IInterface r1 = r0.queryLocalInterface(r1)
            boolean r2 = r1 instanceof com.google.android.gms.internal.measurement.zzac
            if (r2 == 0) goto L_0x0252
            r3 = r1
            com.google.android.gms.internal.measurement.zzac r3 = (com.google.android.gms.internal.measurement.zzac) r3
            goto L_0x0257
        L_0x0252:
            com.google.android.gms.internal.measurement.zzaf r3 = new com.google.android.gms.internal.measurement.zzaf
            r3.<init>(r0)
        L_0x0257:
            r10.setInstanceIdProvider(r3)
            goto L_0x040f
        L_0x025d:
            android.os.IBinder r0 = r12.readStrongBinder()
            if (r0 != 0) goto L_0x0264
            goto L_0x0275
        L_0x0264:
            android.os.IInterface r1 = r0.queryLocalInterface(r2)
            boolean r2 = r1 instanceof com.google.android.gms.internal.measurement.zzw
            if (r2 == 0) goto L_0x0270
            r3 = r1
            com.google.android.gms.internal.measurement.zzw r3 = (com.google.android.gms.internal.measurement.zzw) r3
            goto L_0x0275
        L_0x0270:
            com.google.android.gms.internal.measurement.zzy r3 = new com.google.android.gms.internal.measurement.zzy
            r3.<init>(r0)
        L_0x0275:
            r10.getCurrentScreenClass(r3)
            goto L_0x040f
        L_0x027b:
            android.os.IBinder r0 = r12.readStrongBinder()
            if (r0 != 0) goto L_0x0282
            goto L_0x0293
        L_0x0282:
            android.os.IInterface r1 = r0.queryLocalInterface(r2)
            boolean r2 = r1 instanceof com.google.android.gms.internal.measurement.zzw
            if (r2 == 0) goto L_0x028e
            r3 = r1
            com.google.android.gms.internal.measurement.zzw r3 = (com.google.android.gms.internal.measurement.zzw) r3
            goto L_0x0293
        L_0x028e:
            com.google.android.gms.internal.measurement.zzy r3 = new com.google.android.gms.internal.measurement.zzy
            r3.<init>(r0)
        L_0x0293:
            r10.getCurrentScreenName(r3)
            goto L_0x040f
        L_0x0299:
            android.os.IBinder r1 = r12.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r1 = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(r1)
            java.lang.String r2 = r12.readString()
            java.lang.String r3 = r12.readString()
            long r4 = r12.readLong()
            r0 = r10
            r0.setCurrentScreen(r1, r2, r3, r4)
            goto L_0x040f
        L_0x02b3:
            long r0 = r12.readLong()
            r10.setSessionTimeoutDuration(r0)
            goto L_0x040f
        L_0x02bc:
            long r0 = r12.readLong()
            r10.setMinimumSessionDuration(r0)
            goto L_0x040f
        L_0x02c5:
            long r0 = r12.readLong()
            r10.resetAnalyticsData(r0)
            goto L_0x040f
        L_0x02ce:
            boolean r1 = com.google.android.gms.internal.measurement.zzb.zza(r12)
            long r2 = r12.readLong()
            r10.setMeasurementEnabled(r1, r2)
            goto L_0x040f
        L_0x02db:
            java.lang.String r1 = r12.readString()
            java.lang.String r4 = r12.readString()
            android.os.IBinder r0 = r12.readStrongBinder()
            if (r0 != 0) goto L_0x02ea
            goto L_0x02fb
        L_0x02ea:
            android.os.IInterface r2 = r0.queryLocalInterface(r2)
            boolean r3 = r2 instanceof com.google.android.gms.internal.measurement.zzw
            if (r3 == 0) goto L_0x02f6
            r3 = r2
            com.google.android.gms.internal.measurement.zzw r3 = (com.google.android.gms.internal.measurement.zzw) r3
            goto L_0x02fb
        L_0x02f6:
            com.google.android.gms.internal.measurement.zzy r3 = new com.google.android.gms.internal.measurement.zzy
            r3.<init>(r0)
        L_0x02fb:
            r10.getConditionalUserProperties(r1, r4, r3)
            goto L_0x040f
        L_0x0301:
            java.lang.String r1 = r12.readString()
            java.lang.String r2 = r12.readString()
            android.os.Parcelable$Creator r3 = android.os.Bundle.CREATOR
            android.os.Parcelable r0 = com.google.android.gms.internal.measurement.zzb.zza((android.os.Parcel) r12, r3)
            android.os.Bundle r0 = (android.os.Bundle) r0
            r10.clearConditionalUserProperty(r1, r2, r0)
            goto L_0x040f
        L_0x0316:
            android.os.Parcelable$Creator r1 = android.os.Bundle.CREATOR
            android.os.Parcelable r1 = com.google.android.gms.internal.measurement.zzb.zza((android.os.Parcel) r12, r1)
            android.os.Bundle r1 = (android.os.Bundle) r1
            long r2 = r12.readLong()
            r10.setConditionalUserProperty(r1, r2)
            goto L_0x040f
        L_0x0327:
            java.lang.String r1 = r12.readString()
            long r2 = r12.readLong()
            r10.setUserId(r1, r2)
            goto L_0x040f
        L_0x0334:
            java.lang.String r1 = r12.readString()
            android.os.IBinder r0 = r12.readStrongBinder()
            if (r0 != 0) goto L_0x033f
            goto L_0x0350
        L_0x033f:
            android.os.IInterface r2 = r0.queryLocalInterface(r2)
            boolean r3 = r2 instanceof com.google.android.gms.internal.measurement.zzw
            if (r3 == 0) goto L_0x034b
            r3 = r2
            com.google.android.gms.internal.measurement.zzw r3 = (com.google.android.gms.internal.measurement.zzw) r3
            goto L_0x0350
        L_0x034b:
            com.google.android.gms.internal.measurement.zzy r3 = new com.google.android.gms.internal.measurement.zzy
            r3.<init>(r0)
        L_0x0350:
            r10.getMaxUserProperties(r1, r3)
            goto L_0x040f
        L_0x0356:
            java.lang.String r1 = r12.readString()
            java.lang.String r4 = r12.readString()
            boolean r5 = com.google.android.gms.internal.measurement.zzb.zza(r12)
            android.os.IBinder r0 = r12.readStrongBinder()
            if (r0 != 0) goto L_0x0369
            goto L_0x037a
        L_0x0369:
            android.os.IInterface r2 = r0.queryLocalInterface(r2)
            boolean r3 = r2 instanceof com.google.android.gms.internal.measurement.zzw
            if (r3 == 0) goto L_0x0375
            r3 = r2
            com.google.android.gms.internal.measurement.zzw r3 = (com.google.android.gms.internal.measurement.zzw) r3
            goto L_0x037a
        L_0x0375:
            com.google.android.gms.internal.measurement.zzy r3 = new com.google.android.gms.internal.measurement.zzy
            r3.<init>(r0)
        L_0x037a:
            r10.getUserProperties(r1, r4, r5, r3)
            goto L_0x040f
        L_0x0380:
            java.lang.String r1 = r12.readString()
            java.lang.String r2 = r12.readString()
            android.os.IBinder r3 = r12.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r3 = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(r3)
            boolean r4 = com.google.android.gms.internal.measurement.zzb.zza(r12)
            long r5 = r12.readLong()
            r0 = r10
            r0.setUserProperty(r1, r2, r3, r4, r5)
            goto L_0x040f
        L_0x039e:
            java.lang.String r1 = r12.readString()
            java.lang.String r4 = r12.readString()
            android.os.Parcelable$Creator r5 = android.os.Bundle.CREATOR
            android.os.Parcelable r5 = com.google.android.gms.internal.measurement.zzb.zza((android.os.Parcel) r12, r5)
            android.os.Bundle r5 = (android.os.Bundle) r5
            android.os.IBinder r6 = r12.readStrongBinder()
            if (r6 != 0) goto L_0x03b6
            r6 = r3
            goto L_0x03c8
        L_0x03b6:
            android.os.IInterface r2 = r6.queryLocalInterface(r2)
            boolean r3 = r2 instanceof com.google.android.gms.internal.measurement.zzw
            if (r3 == 0) goto L_0x03c2
            com.google.android.gms.internal.measurement.zzw r2 = (com.google.android.gms.internal.measurement.zzw) r2
            r6 = r2
            goto L_0x03c8
        L_0x03c2:
            com.google.android.gms.internal.measurement.zzy r2 = new com.google.android.gms.internal.measurement.zzy
            r2.<init>(r6)
            r6 = r2
        L_0x03c8:
            long r8 = r12.readLong()
            r0 = r10
            r2 = r4
            r3 = r5
            r4 = r6
            r5 = r8
            r0.logEventAndBundle(r1, r2, r3, r4, r5)
            goto L_0x040f
        L_0x03d6:
            java.lang.String r1 = r12.readString()
            java.lang.String r2 = r12.readString()
            android.os.Parcelable$Creator r3 = android.os.Bundle.CREATOR
            android.os.Parcelable r3 = com.google.android.gms.internal.measurement.zzb.zza((android.os.Parcel) r12, r3)
            android.os.Bundle r3 = (android.os.Bundle) r3
            boolean r4 = com.google.android.gms.internal.measurement.zzb.zza(r12)
            boolean r5 = com.google.android.gms.internal.measurement.zzb.zza(r12)
            long r6 = r12.readLong()
            r0 = r10
            r0.logEvent(r1, r2, r3, r4, r5, r6)
            goto L_0x040f
        L_0x03f7:
            android.os.IBinder r1 = r12.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r1 = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(r1)
            android.os.Parcelable$Creator<com.google.android.gms.internal.measurement.zzae> r2 = com.google.android.gms.internal.measurement.zzae.CREATOR
            android.os.Parcelable r2 = com.google.android.gms.internal.measurement.zzb.zza((android.os.Parcel) r12, r2)
            com.google.android.gms.internal.measurement.zzae r2 = (com.google.android.gms.internal.measurement.zzae) r2
            long r3 = r12.readLong()
            r10.initialize(r1, r2, r3)
        L_0x040f:
            r13.writeNoException()
            r0 = 1
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzu.zza(int, android.os.Parcel, android.os.Parcel, int):boolean");
    }
}
