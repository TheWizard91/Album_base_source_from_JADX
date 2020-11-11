package com.firebase.p008ui.auth.data.remote;

import android.app.Application;
import android.content.Intent;
import com.firebase.p008ui.auth.AuthUI;
import com.firebase.p008ui.auth.FirebaseAuthAnonymousUpgradeException;
import com.firebase.p008ui.auth.FirebaseUiException;
import com.firebase.p008ui.auth.FirebaseUiUserCollisionException;
import com.firebase.p008ui.auth.IdpResponse;
import com.firebase.p008ui.auth.data.model.FlowParameters;
import com.firebase.p008ui.auth.data.model.Resource;
import com.firebase.p008ui.auth.data.model.User;
import com.firebase.p008ui.auth.data.model.UserCancellationException;
import com.firebase.p008ui.auth.p009ui.HelperActivityBase;
import com.firebase.p008ui.auth.util.ExtraConstants;
import com.firebase.p008ui.auth.util.FirebaseAuthError;
import com.firebase.p008ui.auth.util.data.AuthOperationManager;
import com.firebase.p008ui.auth.util.data.ProviderUtils;
import com.firebase.p008ui.auth.viewmodel.ProviderSignInBase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthCredential;
import com.google.firebase.auth.OAuthProvider;
import java.util.List;
import java.util.Map;

/* renamed from: com.firebase.ui.auth.data.remote.GenericIdpSignInHandler */
public class GenericIdpSignInHandler extends ProviderSignInBase<AuthUI.IdpConfig> {
    public GenericIdpSignInHandler(Application application) {
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
            handleAnonymousUpgradeFlow(auth, activity, provider, flowParameters);
        }
    }

    /* access modifiers changed from: protected */
    public void handleNormalSignInFlow(FirebaseAuth auth, HelperActivityBase activity, final OAuthProvider provider) {
        auth.startActivityForSignInWithProvider(activity, provider).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            public void onSuccess(AuthResult authResult) {
                GenericIdpSignInHandler.this.handleSuccess(provider.getProviderId(), authResult.getUser(), (OAuthCredential) authResult.getCredential());
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(Exception e) {
                if (e instanceof FirebaseAuthException) {
                    FirebaseAuthError error = FirebaseAuthError.fromException((FirebaseAuthException) e);
                    if (e instanceof FirebaseAuthUserCollisionException) {
                        FirebaseAuthUserCollisionException collisionException = (FirebaseAuthUserCollisionException) e;
                        GenericIdpSignInHandler.this.setResult(Resource.forFailure(new FirebaseUiUserCollisionException(13, "Recoverable error.", provider.getProviderId(), collisionException.getEmail(), collisionException.getUpdatedCredential())));
                    } else if (error == FirebaseAuthError.ERROR_WEB_CONTEXT_CANCELED) {
                        GenericIdpSignInHandler.this.setResult(Resource.forFailure(new UserCancellationException()));
                    } else {
                        GenericIdpSignInHandler.this.setResult(Resource.forFailure(e));
                    }
                } else {
                    GenericIdpSignInHandler.this.setResult(Resource.forFailure(e));
                }
            }
        });
    }

    private void handleAnonymousUpgradeFlow(final FirebaseAuth auth, HelperActivityBase activity, final OAuthProvider provider, final FlowParameters flowParameters) {
        auth.getCurrentUser().startActivityForLinkWithProvider(activity, provider).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            public void onSuccess(AuthResult authResult) {
                GenericIdpSignInHandler.this.handleSuccess(provider.getProviderId(), authResult.getUser(), (OAuthCredential) authResult.getCredential());
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(Exception e) {
                if (!(e instanceof FirebaseAuthUserCollisionException)) {
                    GenericIdpSignInHandler.this.setResult(Resource.forFailure(e));
                    return;
                }
                FirebaseAuthUserCollisionException collisionException = (FirebaseAuthUserCollisionException) e;
                final AuthCredential credential = collisionException.getUpdatedCredential();
                final String email = collisionException.getEmail();
                ProviderUtils.fetchSortedProviders(auth, flowParameters, email).addOnSuccessListener(new OnSuccessListener<List<String>>() {
                    public void onSuccess(List<String> providers) {
                        if (providers.isEmpty()) {
                            GenericIdpSignInHandler.this.setResult(Resource.forFailure(new FirebaseUiException(3, "Unable to complete the linkingflow - the user is using unsupported providers.")));
                        } else if (providers.contains(provider.getProviderId())) {
                            GenericIdpSignInHandler.this.handleMergeFailure(credential);
                        } else {
                            GenericIdpSignInHandler.this.setResult(Resource.forFailure(new FirebaseUiUserCollisionException(13, "Recoverable error.", provider.getProviderId(), email, credential)));
                        }
                    }
                });
            }
        });
    }

    /* access modifiers changed from: protected */
    public OAuthProvider buildOAuthProvider(String providerId) {
        OAuthProvider.Builder providerBuilder = OAuthProvider.newBuilder(providerId);
        List<String> scopes = ((AuthUI.IdpConfig) getArguments()).getParams().getStringArrayList(ExtraConstants.GENERIC_OAUTH_SCOPES);
        Map<String, String> customParams = (Map) ((AuthUI.IdpConfig) getArguments()).getParams().getParcelable(ExtraConstants.GENERIC_OAUTH_CUSTOM_PARAMETERS);
        if (scopes != null) {
            providerBuilder.setScopes(scopes);
        }
        if (customParams != null) {
            providerBuilder.addCustomParameters(customParams);
        }
        return providerBuilder.build();
    }

    /* access modifiers changed from: protected */
    public void handleSuccess(String providerId, FirebaseUser user, OAuthCredential credential, boolean setPendingCredential) {
        IdpResponse.Builder response = new IdpResponse.Builder(new User.Builder(providerId, user.getEmail()).setName(user.getDisplayName()).setPhotoUri(user.getPhotoUrl()).build()).setToken(credential.getAccessToken()).setSecret(credential.getSecret());
        if (setPendingCredential) {
            response.setPendingCredential(credential);
        }
        setResult(Resource.forSuccess(response.build()));
    }

    /* access modifiers changed from: protected */
    public void handleSuccess(String providerId, FirebaseUser user, OAuthCredential credential) {
        handleSuccess(providerId, user, credential, false);
    }

    /* access modifiers changed from: protected */
    public void handleMergeFailure(AuthCredential credential) {
        setResult(Resource.forFailure(new FirebaseAuthAnonymousUpgradeException(5, new IdpResponse.Builder().setPendingCredential(credential).build())));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 117) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (response == null) {
                setResult(Resource.forFailure(new UserCancellationException()));
            } else {
                setResult(Resource.forSuccess(response));
            }
        }
    }

    public void initializeForTesting(AuthUI.IdpConfig idpConfig) {
        setArguments(idpConfig);
    }
}
