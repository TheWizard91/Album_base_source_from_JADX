package com.google.android.play.core.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.List;

/* renamed from: com.google.android.play.core.internal.bn */
public final class C3029bn extends C3057i implements C3031bp {
    C3029bn(IBinder iBinder) {
        super(iBinder, "com.google.android.play.core.splitinstall.protocol.ISplitInstallService");
    }

    /* renamed from: a */
    public final void mo44117a(String str, int i, Bundle bundle, C3033br brVar) throws RemoteException {
        Parcel a = mo44172a();
        a.writeString(str);
        a.writeInt(i);
        C3059k.m807a(a, (Parcelable) bundle);
        C3059k.m806a(a, (IInterface) brVar);
        mo44173a(4, a);
    }

    /* renamed from: a */
    public final void mo44118a(String str, int i, C3033br brVar) throws RemoteException {
        Parcel a = mo44172a();
        a.writeString(str);
        a.writeInt(i);
        C3059k.m806a(a, (IInterface) brVar);
        mo44173a(5, a);
    }

    /* renamed from: a */
    public final void mo44119a(String str, C3033br brVar) throws RemoteException {
        Parcel a = mo44172a();
        a.writeString(str);
        C3059k.m806a(a, (IInterface) brVar);
        mo44173a(6, a);
    }

    /* renamed from: a */
    public final void mo44120a(String str, List<Bundle> list, Bundle bundle, C3033br brVar) throws RemoteException {
        Parcel a = mo44172a();
        a.writeString(str);
        a.writeTypedList(list);
        C3059k.m807a(a, (Parcelable) bundle);
        C3059k.m806a(a, (IInterface) brVar);
        mo44173a(2, a);
    }

    /* renamed from: b */
    public final void mo44121b(String str, List<Bundle> list, Bundle bundle, C3033br brVar) throws RemoteException {
        Parcel a = mo44172a();
        a.writeString(str);
        a.writeTypedList(list);
        C3059k.m807a(a, (Parcelable) bundle);
        C3059k.m806a(a, (IInterface) brVar);
        mo44173a(7, a);
    }

    /* renamed from: c */
    public final void mo44122c(String str, List<Bundle> list, Bundle bundle, C3033br brVar) throws RemoteException {
        Parcel a = mo44172a();
        a.writeString(str);
        a.writeTypedList(list);
        C3059k.m807a(a, (Parcelable) bundle);
        C3059k.m806a(a, (IInterface) brVar);
        mo44173a(8, a);
    }

    /* renamed from: d */
    public final void mo44123d(String str, List<Bundle> list, Bundle bundle, C3033br brVar) throws RemoteException {
        Parcel a = mo44172a();
        a.writeString(str);
        a.writeTypedList(list);
        C3059k.m807a(a, (Parcelable) bundle);
        C3059k.m806a(a, (IInterface) brVar);
        mo44173a(13, a);
    }

    /* renamed from: e */
    public final void mo44124e(String str, List<Bundle> list, Bundle bundle, C3033br brVar) throws RemoteException {
        Parcel a = mo44172a();
        a.writeString(str);
        a.writeTypedList(list);
        C3059k.m807a(a, (Parcelable) bundle);
        C3059k.m806a(a, (IInterface) brVar);
        mo44173a(14, a);
    }
}
