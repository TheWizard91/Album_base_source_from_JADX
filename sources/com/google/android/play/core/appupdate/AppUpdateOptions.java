package com.google.android.play.core.appupdate;

public abstract class AppUpdateOptions {

    public static abstract class Builder {
        /* renamed from: a */
        public abstract void mo43832a();

        public abstract AppUpdateOptions build();

        public abstract Builder setAppUpdateType(int i);
    }

    public static AppUpdateOptions defaultOptions(int i) {
        return newBuilder(i).build();
    }

    public static Builder newBuilder(int i) {
        C2855m mVar = new C2855m();
        mVar.setAppUpdateType(i);
        mVar.mo43832a();
        return mVar;
    }

    /* renamed from: a */
    public abstract boolean mo43830a();

    public abstract int appUpdateType();
}
