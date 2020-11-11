package com.firebase.p008ui.auth.data.remote;

import android.app.Application;
import android.content.Intent;
import com.firebase.p008ui.auth.AuthUI;
import com.firebase.p008ui.auth.IdpResponse;
import com.firebase.p008ui.auth.data.model.Resource;
import com.firebase.p008ui.auth.data.model.UserCancellationException;
import com.firebase.p008ui.auth.p009ui.HelperActivityBase;
import com.firebase.p008ui.auth.p009ui.phone.PhoneActivity;
import com.firebase.p008ui.auth.viewmodel.ProviderSignInBase;
import com.google.firebase.auth.FirebaseAuth;

/* renamed from: com.firebase.ui.auth.data.remote.PhoneSignInHandler */
public class PhoneSignInHandler extends ProviderSignInBase<AuthUI.IdpConfig> {
    public PhoneSignInHandler(Application application) {
        super(application);
    }

    public void startSignIn(HelperActivityBase activity) {
        activity.startActivityForResult(PhoneActivity.createIntent(activity, activity.getFlowParams(), ((AuthUI.IdpConfig) getArguments()).getParams()), 107);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 107) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (response == null) {
                setResult(Resource.forFailure(new UserCancellationException()));
            } else {
                setResult(Resource.forSuccess(response));
            }
        }
    }

    public void startSignIn(FirebaseAuth auth, HelperActivityBase activity, String providerId) {
        startSignIn(activity);
    }
}
