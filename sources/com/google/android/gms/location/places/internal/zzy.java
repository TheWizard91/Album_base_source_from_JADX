package com.google.android.gms.location.places.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.places.zzb;
import com.google.android.gms.internal.places.zze;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.PlacePhotoResult;

public abstract class zzy extends zzb implements zzv {
    public zzy() {
        super("com.google.android.gms.location.places.internal.IPhotosCallbacks");
    }

    /* access modifiers changed from: protected */
    public final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (i == 2) {
            zzb((PlacePhotoResult) zze.zzb(parcel, PlacePhotoResult.CREATOR));
            return true;
        } else if (i != 3) {
            return false;
        } else {
            zzb((PlacePhotoMetadataResult) zze.zzb(parcel, PlacePhotoMetadataResult.CREATOR));
            return true;
        }
    }
}
