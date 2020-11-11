package com.google.android.play.core.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* renamed from: com.google.android.play.core.internal.t */
public abstract class C3068t extends C3058j implements C3069u {
    public C3068t() {
        super("com.google.android.play.core.assetpacks.protocol.IAssetModuleServiceCallback");
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final boolean mo44125a(int i, Parcel parcel) throws RemoteException {
        switch (i) {
            case 2:
                mo43907a(parcel.readInt(), (Bundle) C3059k.m805a(parcel, Bundle.CREATOR));
                return true;
            case 3:
                int readInt = parcel.readInt();
                Bundle bundle = (Bundle) C3059k.m805a(parcel, Bundle.CREATOR);
                mo43906a(readInt);
                return true;
            case 4:
                int readInt2 = parcel.readInt();
                Bundle bundle2 = (Bundle) C3059k.m805a(parcel, Bundle.CREATOR);
                mo43912b(readInt2);
                return true;
            case 5:
                mo43910a((List<Bundle>) parcel.createTypedArrayList(Bundle.CREATOR));
                return true;
            case 6:
                Bundle bundle3 = (Bundle) C3059k.m805a(parcel, Bundle.CREATOR);
                mo43913b((Bundle) C3059k.m805a(parcel, Bundle.CREATOR));
                return true;
            case 7:
                mo43908a((Bundle) C3059k.m805a(parcel, Bundle.CREATOR));
                return true;
            case 8:
                Bundle bundle4 = (Bundle) C3059k.m805a(parcel, Bundle.CREATOR);
                mo43915c((Bundle) C3059k.m805a(parcel, Bundle.CREATOR));
                return true;
            case 10:
                Bundle bundle5 = (Bundle) C3059k.m805a(parcel, Bundle.CREATOR);
                mo43917d((Bundle) C3059k.m805a(parcel, Bundle.CREATOR));
                return true;
            case 11:
                mo43909a((Bundle) C3059k.m805a(parcel, Bundle.CREATOR), (Bundle) C3059k.m805a(parcel, Bundle.CREATOR));
                return true;
            case 12:
                mo43914b((Bundle) C3059k.m805a(parcel, Bundle.CREATOR), (Bundle) C3059k.m805a(parcel, Bundle.CREATOR));
                return true;
            case 13:
                mo43916c((Bundle) C3059k.m805a(parcel, Bundle.CREATOR), (Bundle) C3059k.m805a(parcel, Bundle.CREATOR));
                return true;
            case 14:
                Bundle bundle6 = (Bundle) C3059k.m805a(parcel, Bundle.CREATOR);
                Bundle bundle7 = (Bundle) C3059k.m805a(parcel, Bundle.CREATOR);
                mo43911b();
                return true;
            case 15:
                Bundle bundle8 = (Bundle) C3059k.m805a(parcel, Bundle.CREATOR);
                mo43905a();
                return true;
            default:
                return false;
        }
    }
}
