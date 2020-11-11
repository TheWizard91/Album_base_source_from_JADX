package com.firebase.p008ui.auth.viewmodel;

import android.app.Application;
import android.content.Intent;
import com.firebase.p008ui.auth.IdpResponse;
import com.firebase.p008ui.auth.data.model.Resource;
import com.firebase.p008ui.auth.p009ui.HelperActivityBase;
import com.google.firebase.auth.FirebaseAuth;

/* renamed from: com.firebase.ui.auth.viewmodel.ProviderSignInBase */
public abstract class ProviderSignInBase<T> extends OperableViewModel<T, Resource<IdpResponse>> {
    public abstract void onActivityResult(int i, int i2, Intent intent);

    public abstract void startSignIn(HelperActivityBase helperActivityBase);

    public abstract void startSignIn(FirebaseAuth firebaseAuth, HelperActivityBase helperActivityBase, String str);

    protected ProviderSignInBase(Application application) {
        super(application);
    }
}
