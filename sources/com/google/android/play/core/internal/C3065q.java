package com.google.android.play.core.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.List;

/* renamed from: com.google.android.play.core.internal.q */
public final class C3065q extends C3057i implements C3067s {
    C3065q(IBinder iBinder) {
        super(iBinder, "com.google.android.play.core.assetpacks.protocol.IAssetModuleService");
    }

    /* renamed from: a */
    public final void mo44179a(String str, Bundle bundle, Bundle bundle2, C3069u uVar) throws RemoteException {
        Parcel a = mo44172a();
        a.writeString(str);
        C3059k.m807a(a, (Parcelable) bundle);
        C3059k.m807a(a, (Parcelable) bundle2);
        C3059k.m806a(a, (IInterface) uVar);
        mo44173a(6, a);
    }

    /* renamed from: a */
    public final void mo44180a(String str, Bundle bundle, C3069u uVar) throws RemoteException {
        Parcel a = mo44172a();
        a.writeString(str);
        C3059k.m807a(a, (Parcelable) bundle);
        C3059k.m806a(a, (IInterface) uVar);
        mo44173a(5, a);
    }

    /* renamed from: a */
    public final void mo44181a(String str, List<Bundle> list, Bundle bundle, C3069u uVar) throws RemoteException {
        Parcel a = mo44172a();
        a.writeString(str);
        a.writeTypedList(list);
        C3059k.m807a(a, (Parcelable) bundle);
        C3059k.m806a(a, (IInterface) uVar);
        mo44173a(2, a);
    }

    /* renamed from: b */
    public final void mo44182b(String str, Bundle bundle, Bundle bundle2, C3069u uVar) throws RemoteException {
        Parcel a = mo44172a();
        a.writeString(str);
        C3059k.m807a(a, (Parcelable) bundle);
        C3059k.m807a(a, (Parcelable) bundle2);
        C3059k.m806a(a, (IInterface) uVar);
        mo44173a(7, a);
    }

    /* renamed from: b */
    public final void mo44183b(String str, Bundle bundle, C3069u uVar) throws RemoteException {
        Parcel a = mo44172a();
        a.writeString(str);
        C3059k.m807a(a, (Parcelable) bundle);
        C3059k.m806a(a, (IInterface) uVar);
        mo44173a(10, a);
    }

    /* renamed from: b */
    public final void mo44184b(String str, List<Bundle> list, Bundle bundle, C3069u uVar) throws RemoteException {
        Parcel a = mo44172a();
        a.writeString(str);
        a.writeTypedList(list);
        C3059k.m807a(a, (Parcelable) bundle);
        C3059k.m806a(a, (IInterface) uVar);
        mo44173a(14, a);
    }

    /* renamed from: c */
    public final void mo44185c(String str, Bundle bundle, Bundle bundle2, C3069u uVar) throws RemoteException {
        Parcel a = mo44172a();
        a.writeString(str);
        C3059k.m807a(a, (Parcelable) bundle);
        C3059k.m807a(a, (Parcelable) bundle2);
        C3059k.m806a(a, (IInterface) uVar);
        mo44173a(9, a);
    }

    /* renamed from: c */
    public final void mo44186c(String str, List<Bundle> list, Bundle bundle, C3069u uVar) throws RemoteException {
        Parcel a = mo44172a();
        a.writeString(str);
        a.writeTypedList(list);
        C3059k.m807a(a, (Parcelable) bundle);
        C3059k.m806a(a, (IInterface) uVar);
        mo44173a(12, a);
    }

    /* renamed from: d */
    public final void mo44187d(String str, Bundle bundle, Bundle bundle2, C3069u uVar) throws RemoteException {
        Parcel a = mo44172a();
        a.writeString(str);
        C3059k.m807a(a, (Parcelable) bundle);
        C3059k.m807a(a, (Parcelable) bundle2);
        C3059k.m806a(a, (IInterface) uVar);
        mo44173a(11, a);
    }

    /* renamed from: e */
    public final void mo44188e(String str, Bundle bundle, Bundle bundle2, C3069u uVar) throws RemoteException {
        Parcel a = mo44172a();
        a.writeString(str);
        C3059k.m807a(a, (Parcelable) bundle);
        C3059k.m807a(a, (Parcelable) bundle2);
        C3059k.m806a(a, (IInterface) uVar);
        mo44173a(13, a);
    }
}
