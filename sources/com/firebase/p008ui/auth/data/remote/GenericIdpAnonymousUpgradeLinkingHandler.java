package com.firebase.p008ui.auth.data.remote;

import android.app.Application;
import com.firebase.p008ui.auth.AuthUI;
import com.firebase.p008ui.auth.data.model.FlowParameters;
import com.firebase.p008ui.auth.data.model.Resource;
import com.firebase.p008ui.auth.p009ui.HelperActivityBase;
import com.firebase.p008ui.auth.util.data.AuthOperationManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthCredential;
import com.google.firebase.auth.OAuthProvider;

/* renamed from: com.firebase.ui.auth.data.remote.GenericIdpAnonymousUpgradeLinkingHandler */
public class GenericIdpAnonymousUpgradeLinkingHandler extends GenericIdpSignInHandler {
    public GenericIdpAnonymousUpgradeLinkingHandler(Application application) {
        super(application);
    }

    public void startSignIn(HelperActivityBase activity) {
        setResult(Resource.forLoading());
        startSignIn(FirebaseAuth.getInstance(FirebaseApp.getInstance(activity.getFlowParams().appName)), activity, ((AuthUI.IdpConfig) getArguments()).getProviderId());
    }

    public void startSignIn(FirebaseAuth auth, HelperActivityBase activity, String providerId) {
        setResult(Resource.forLoading());
        FlowParameters flowParameters = activity.getFlowParams();
        OAuthProvider provider = buildOAuthProvider(providerId);
        if (flowParameters == null || !AuthOperationManager.getInstance().canUpgradeAnonymous(auth, flowParameters)) {
            handleNormalSignInFlow(auth, activity, provider);
        } else {
            handleAnonymousUpgradeLinkingFlow(activity, provider, flowParameters);
        }
    }

    private void handleAnonymousUpgradeLinkingFlow(HelperActivityBase activity, final OAuthProvider provider, FlowParameters flowParameters) {
        AuthOperationManager.getInstance().safeGenericIdpSignIn(activity, provider, flowParameters).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            public void onSuccess(AuthResult authResult) {
                GenericIdpAnonymousUpgradeLinkingHandler.this.handleSuccess(provider.getProviderId(), authResult.getUser(), (OAuthCredential) authResult.getCredential(), true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(Exception e) {
                GenericIdpAnonymousUpgradeLinkingHandler.this.setResult(Resource.forFailure(e));
            }
        });
    }
}
