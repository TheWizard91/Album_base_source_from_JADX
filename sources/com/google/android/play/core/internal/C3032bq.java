package com.google.android.play.core.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* renamed from: com.google.android.play.core.internal.bq */
public abstract class C3032bq extends C3058j implements C3033br {
    public C3032bq() {
        super("com.google.android.play.core.splitinstall.protocol.ISplitInstallServiceCallback");
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final boolean mo44125a(int i, Parcel parcel) throws RemoteException {
        switch (i) {
            case 2:
                mo44134c(parcel.readInt(), (Bundle) C3059k.m805a(parcel, Bundle.CREATOR));
                return true;
            case 3:
                int readInt = parcel.readInt();
                Bundle bundle = (Bundle) C3059k.m805a(parcel, Bundle.CREATOR);
                mo44127a(readInt);
                return true;
            case 4:
                mo44128a(parcel.readInt(), (Bundle) C3059k.m805a(parcel, Bundle.CREATOR));
                return true;
            case 5:
                mo44132b(parcel.readInt(), (Bundle) C3059k.m805a(parcel, Bundle.CREATOR));
                return true;
            case 6:
                mo44137e((Bundle) C3059k.m805a(parcel, Bundle.CREATOR));
                return true;
            case 7:
                mo44130a((List<Bundle>) parcel.createTypedArrayList(Bundle.CREATOR));
                return true;
            case 8:
                mo44136d((Bundle) C3059k.m805a(parcel, Bundle.CREATOR));
                return true;
            case 9:
                mo44129a((Bundle) C3059k.m805a(parcel, Bundle.CREATOR));
                return true;
            case 10:
                Bundle bundle2 = (Bundle) C3059k.m805a(parcel, Bundle.CREATOR);
                mo44131b();
                return true;
            case 11:
                Bundle bundle3 = (Bundle) C3059k.m805a(parcel, Bundle.CREATOR);
                mo44126a();
                return true;
            case 12:
                mo44133b((Bundle) C3059k.m805a(parcel, Bundle.CREATOR));
                return true;
            case 13:
                mo44135c((Bundle) C3059k.m805a(parcel, Bundle.CREATOR));
                return true;
            default:
                return false;
        }
    }
}
