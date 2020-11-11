package com.firebase.p008ui.auth.p009ui.idp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.firebase.p008ui.auth.AuthMethodPickerLayout;
import com.firebase.p008ui.auth.AuthUI;
import com.firebase.p008ui.auth.C2354R;
import com.firebase.p008ui.auth.FirebaseAuthAnonymousUpgradeException;
import com.firebase.p008ui.auth.FirebaseUiException;
import com.firebase.p008ui.auth.IdpResponse;
import com.firebase.p008ui.auth.data.model.FlowParameters;
import com.firebase.p008ui.auth.data.model.UserCancellationException;
import com.firebase.p008ui.auth.p009ui.AppCompatBase;
import com.firebase.p008ui.auth.util.ExtraConstants;
import com.firebase.p008ui.auth.util.data.PrivacyDisclosureUtils;
import com.firebase.p008ui.auth.viewmodel.ProviderSignInBase;
import com.firebase.p008ui.auth.viewmodel.ResourceObserver;
import com.firebase.p008ui.auth.viewmodel.idp.SocialProviderResponseHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* renamed from: com.firebase.ui.auth.ui.idp.AuthMethodPickerActivity */
public class AuthMethodPickerActivity extends AppCompatBase {
    private AuthMethodPickerLayout customLayout;
    /* access modifiers changed from: private */
    public SocialProviderResponseHandler mHandler;
    private ProgressBar mProgressBar;
    private ViewGroup mProviderHolder;
    private List<ProviderSignInBase<?>> mProviders;

    public static Intent createIntent(Context context, FlowParameters flowParams) {
        return createBaseIntent(context, AuthMethodPickerActivity.class, flowParams);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        int termsTextId;
        super.onCreate(savedInstanceState);
        FlowParameters params = getFlowParams();
        this.customLayout = params.authMethodPickerLayout;
        SocialProviderResponseHandler socialProviderResponseHandler = (SocialProviderResponseHandler) ViewModelProviders.m17of((FragmentActivity) this).get(SocialProviderResponseHandler.class);
        this.mHandler = socialProviderResponseHandler;
        socialProviderResponseHandler.init(params);
        this.mProviders = new ArrayList();
        AuthMethodPickerLayout authMethodPickerLayout = this.customLayout;
        if (authMethodPickerLayout != null) {
            setContentView(authMethodPickerLayout.getMainLayout());
            populateIdpListCustomLayout(params.providers);
        } else {
            setContentView(C2354R.C2359layout.fui_auth_method_picker_layout);
            this.mProgressBar = (ProgressBar) findViewById(C2354R.C2357id.top_progress_bar);
            this.mProviderHolder = (ViewGroup) findViewById(C2354R.C2357id.btn_holder);
            populateIdpList(params.providers);
            int logoId = params.logoId;
            if (logoId == -1) {
                findViewById(C2354R.C2357id.logo).setVisibility(8);
                ConstraintLayout layout = (ConstraintLayout) findViewById(C2354R.C2357id.root);
                ConstraintSet constraints = new ConstraintSet();
                constraints.clone(layout);
                constraints.setHorizontalBias(C2354R.C2357id.container, 0.5f);
                constraints.setVerticalBias(C2354R.C2357id.container, 0.5f);
                constraints.applyTo(layout);
            } else {
                ((ImageView) findViewById(C2354R.C2357id.logo)).setImageResource(logoId);
            }
        }
        boolean tosAndPpConfigured = getFlowParams().isPrivacyPolicyUrlProvided() && getFlowParams().isTermsOfServiceUrlProvided();
        AuthMethodPickerLayout authMethodPickerLayout2 = this.customLayout;
        if (authMethodPickerLayout2 == null) {
            termsTextId = C2354R.C2357id.main_tos_and_pp;
        } else {
            termsTextId = authMethodPickerLayout2.getTosPpView();
        }
        if (termsTextId >= 0) {
            TextView termsText = (TextView) findViewById(termsTextId);
            if (!tosAndPpConfigured) {
                termsText.setVisibility(8);
            } else {
                PrivacyDisclosureUtils.setupTermsOfServiceAndPrivacyPolicyText(this, getFlowParams(), termsText);
            }
        }
        this.mHandler.getOperation().observe(this, new ResourceObserver<IdpResponse>(this, C2354R.string.fui_progress_dialog_signing_in) {
            /* access modifiers changed from: protected */
            public void onSuccess(IdpResponse response) {
                AuthMethodPickerActivity authMethodPickerActivity = AuthMethodPickerActivity.this;
                authMethodPickerActivity.startSaveCredentials(authMethodPickerActivity.mHandler.getCurrentUser(), response, (String) null);
            }

            /* access modifiers changed from: protected */
            public void onFailure(Exception e) {
                if (!(e instanceof UserCancellationException)) {
                    if (e instanceof FirebaseAuthAnonymousUpgradeException) {
                        AuthMethodPickerActivity.this.finish(5, ((FirebaseAuthAnonymousUpgradeException) e).getResponse().toIntent());
                    } else if (e instanceof FirebaseUiException) {
                        AuthMethodPickerActivity.this.finish(0, IdpResponse.from((FirebaseUiException) e).toIntent());
                    } else {
                        Toast.makeText(AuthMethodPickerActivity.this, AuthMethodPickerActivity.this.getString(C2354R.string.fui_error_unknown), 0).show();
                    }
                }
            }
        });
    }

    private void populateIdpList(List<AuthUI.IdpConfig> providerConfigs) {
        int buttonLayout;
        ViewModelProvider of = ViewModelProviders.m17of((FragmentActivity) this);
        this.mProviders = new ArrayList();
        for (AuthUI.IdpConfig idpConfig : providerConfigs) {
            String providerId = idpConfig.getProviderId();
            char c = 65535;
            switch (providerId.hashCode()) {
                case -2095811475:
                    if (providerId.equals(AuthUI.ANONYMOUS_PROVIDER)) {
                        c = 5;
                        break;
                    }
                    break;
                case -1536293812:
                    if (providerId.equals("google.com")) {
                        c = 0;
                        break;
                    }
                    break;
                case -364826023:
                    if (providerId.equals("facebook.com")) {
                        c = 1;
                        break;
                    }
                    break;
                case 106642798:
                    if (providerId.equals("phone")) {
                        c = 4;
                        break;
                    }
                    break;
                case 1216985755:
                    if (providerId.equals("password")) {
                        c = 3;
                        break;
                    }
                    break;
                case 2120171958:
                    if (providerId.equals("emailLink")) {
                        c = 2;
                        break;
                    }
                    break;
            }
            if (c == 0) {
                buttonLayout = C2354R.C2359layout.fui_idp_button_google;
            } else if (c == 1) {
                buttonLayout = C2354R.C2359layout.fui_idp_button_facebook;
            } else if (c == 2 || c == 3) {
                buttonLayout = C2354R.C2359layout.fui_provider_button_email;
            } else if (c == 4) {
                buttonLayout = C2354R.C2359layout.fui_provider_button_phone;
            } else if (c == 5) {
                buttonLayout = C2354R.C2359layout.fui_provider_button_anonymous;
            } else if (!TextUtils.isEmpty(idpConfig.getParams().getString(ExtraConstants.GENERIC_OAUTH_PROVIDER_ID))) {
                buttonLayout = idpConfig.getParams().getInt(ExtraConstants.GENERIC_OAUTH_BUTTON_ID);
            } else {
                throw new IllegalStateException("Unknown provider: " + providerId);
            }
            View loginButton = getLayoutInflater().inflate(buttonLayout, this.mProviderHolder, false);
            handleSignInOperation(idpConfig, loginButton);
            this.mProviderHolder.addView(loginButton);
        }
    }

    private void populateIdpListCustomLayout(List<AuthUI.IdpConfig> providerConfigs) {
        Map<String, Integer> providerButtonIds = this.customLayout.getProvidersButton();
        for (AuthUI.IdpConfig idpConfig : providerConfigs) {
            String providerId = providerOrEmailLinkProvider(idpConfig.getProviderId());
            if (providerButtonIds.containsKey(providerId)) {
                handleSignInOperation(idpConfig, findViewById(providerButtonIds.get(providerId).intValue()));
            } else {
                throw new IllegalStateException("No button found for auth provider: " + idpConfig.getProviderId());
            }
        }
    }

    private String providerOrEmailLinkProvider(String providerId) {
        if (providerId.equals("emailLink")) {
            return "password";
        }
        return providerId;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: com.firebase.ui.auth.data.remote.GoogleSignInHandler} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: com.firebase.ui.auth.data.remote.GoogleSignInHandler} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v10, resolved type: com.firebase.ui.auth.data.remote.FacebookSignInHandler} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: com.firebase.ui.auth.data.remote.GoogleSignInHandler} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v13, resolved type: com.firebase.ui.auth.data.remote.EmailSignInHandler} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: com.firebase.ui.auth.data.remote.GoogleSignInHandler} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v16, resolved type: com.firebase.ui.auth.data.remote.PhoneSignInHandler} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: com.firebase.ui.auth.data.remote.GoogleSignInHandler} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v19, resolved type: com.firebase.ui.auth.data.remote.AnonymousSignInHandler} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v9, resolved type: com.firebase.ui.auth.data.remote.GoogleSignInHandler} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v26, resolved type: com.firebase.ui.auth.data.remote.GenericIdpSignInHandler} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v15, resolved type: com.firebase.ui.auth.data.remote.GoogleSignInHandler} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void handleSignInOperation(final com.firebase.p008ui.auth.AuthUI.IdpConfig r9, android.view.View r10) {
        /*
            r8 = this;
            androidx.lifecycle.ViewModelProvider r0 = androidx.lifecycle.ViewModelProviders.m17of((androidx.fragment.app.FragmentActivity) r8)
            java.lang.String r1 = r9.getProviderId()
            int r2 = r1.hashCode()
            r3 = 5
            r4 = 4
            r5 = 3
            r6 = 2
            r7 = 1
            switch(r2) {
                case -2095811475: goto L_0x0047;
                case -1536293812: goto L_0x003d;
                case -364826023: goto L_0x0033;
                case 106642798: goto L_0x0029;
                case 1216985755: goto L_0x001f;
                case 2120171958: goto L_0x0015;
                default: goto L_0x0014;
            }
        L_0x0014:
            goto L_0x0051
        L_0x0015:
            java.lang.String r2 = "emailLink"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x0014
            r2 = r6
            goto L_0x0052
        L_0x001f:
            java.lang.String r2 = "password"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x0014
            r2 = r5
            goto L_0x0052
        L_0x0029:
            java.lang.String r2 = "phone"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x0014
            r2 = r4
            goto L_0x0052
        L_0x0033:
            java.lang.String r2 = "facebook.com"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x0014
            r2 = r7
            goto L_0x0052
        L_0x003d:
            java.lang.String r2 = "google.com"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x0014
            r2 = 0
            goto L_0x0052
        L_0x0047:
            java.lang.String r2 = "anonymous"
            boolean r2 = r1.equals(r2)
            if (r2 == 0) goto L_0x0014
            r2 = r3
            goto L_0x0052
        L_0x0051:
            r2 = -1
        L_0x0052:
            if (r2 == 0) goto L_0x00ce
            if (r2 == r7) goto L_0x00c1
            if (r2 == r6) goto L_0x00b3
            if (r2 == r5) goto L_0x00b3
            if (r2 == r4) goto L_0x00a6
            if (r2 == r3) goto L_0x0095
            android.os.Bundle r2 = r9.getParams()
            java.lang.String r3 = "generic_oauth_provider_id"
            java.lang.String r2 = r2.getString(r3)
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x007c
            java.lang.Class<com.firebase.ui.auth.data.remote.GenericIdpSignInHandler> r2 = com.firebase.p008ui.auth.data.remote.GenericIdpSignInHandler.class
            androidx.lifecycle.ViewModel r2 = r0.get(r2)
            com.firebase.ui.auth.data.remote.GenericIdpSignInHandler r2 = (com.firebase.p008ui.auth.data.remote.GenericIdpSignInHandler) r2
            r2.init(r9)
            r3 = r2
            goto L_0x00e0
        L_0x007c:
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Unknown provider: "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r1)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        L_0x0095:
            java.lang.Class<com.firebase.ui.auth.data.remote.AnonymousSignInHandler> r2 = com.firebase.p008ui.auth.data.remote.AnonymousSignInHandler.class
            androidx.lifecycle.ViewModel r2 = r0.get(r2)
            com.firebase.ui.auth.data.remote.AnonymousSignInHandler r2 = (com.firebase.p008ui.auth.data.remote.AnonymousSignInHandler) r2
            com.firebase.ui.auth.data.model.FlowParameters r3 = r8.getFlowParams()
            r2.init(r3)
            r3 = r2
            goto L_0x00e0
        L_0x00a6:
            java.lang.Class<com.firebase.ui.auth.data.remote.PhoneSignInHandler> r2 = com.firebase.p008ui.auth.data.remote.PhoneSignInHandler.class
            androidx.lifecycle.ViewModel r2 = r0.get(r2)
            com.firebase.ui.auth.data.remote.PhoneSignInHandler r2 = (com.firebase.p008ui.auth.data.remote.PhoneSignInHandler) r2
            r2.init(r9)
            r3 = r2
            goto L_0x00e0
        L_0x00b3:
            java.lang.Class<com.firebase.ui.auth.data.remote.EmailSignInHandler> r2 = com.firebase.p008ui.auth.data.remote.EmailSignInHandler.class
            androidx.lifecycle.ViewModel r2 = r0.get(r2)
            com.firebase.ui.auth.data.remote.EmailSignInHandler r2 = (com.firebase.p008ui.auth.data.remote.EmailSignInHandler) r2
            r3 = 0
            r2.init(r3)
            r3 = r2
            goto L_0x00e0
        L_0x00c1:
            java.lang.Class<com.firebase.ui.auth.data.remote.FacebookSignInHandler> r2 = com.firebase.p008ui.auth.data.remote.FacebookSignInHandler.class
            androidx.lifecycle.ViewModel r2 = r0.get(r2)
            com.firebase.ui.auth.data.remote.FacebookSignInHandler r2 = (com.firebase.p008ui.auth.data.remote.FacebookSignInHandler) r2
            r2.init(r9)
            r3 = r2
            goto L_0x00e0
        L_0x00ce:
            java.lang.Class<com.firebase.ui.auth.data.remote.GoogleSignInHandler> r2 = com.firebase.p008ui.auth.data.remote.GoogleSignInHandler.class
            androidx.lifecycle.ViewModel r2 = r0.get(r2)
            com.firebase.ui.auth.data.remote.GoogleSignInHandler r2 = (com.firebase.p008ui.auth.data.remote.GoogleSignInHandler) r2
            com.firebase.ui.auth.data.remote.GoogleSignInHandler$Params r3 = new com.firebase.ui.auth.data.remote.GoogleSignInHandler$Params
            r3.<init>(r9)
            r2.init(r3)
            r3 = r2
        L_0x00e0:
            java.util.List<com.firebase.ui.auth.viewmodel.ProviderSignInBase<?>> r2 = r8.mProviders
            r2.add(r3)
            androidx.lifecycle.LiveData r2 = r3.getOperation()
            com.firebase.ui.auth.ui.idp.AuthMethodPickerActivity$2 r4 = new com.firebase.ui.auth.ui.idp.AuthMethodPickerActivity$2
            r4.<init>(r8, r1)
            r2.observe(r8, r4)
            com.firebase.ui.auth.ui.idp.AuthMethodPickerActivity$3 r2 = new com.firebase.ui.auth.ui.idp.AuthMethodPickerActivity$3
            r2.<init>(r3, r9)
            r10.setOnClickListener(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.firebase.p008ui.auth.p009ui.idp.AuthMethodPickerActivity.handleSignInOperation(com.firebase.ui.auth.AuthUI$IdpConfig, android.view.View):void");
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.mHandler.onActivityResult(requestCode, resultCode, data);
        for (ProviderSignInBase<?> provider : this.mProviders) {
            provider.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void showProgress(int message) {
        if (this.customLayout == null) {
            this.mProgressBar.setVisibility(0);
            for (int i = 0; i < this.mProviderHolder.getChildCount(); i++) {
                View child = this.mProviderHolder.getChildAt(i);
                child.setEnabled(false);
                child.setAlpha(0.75f);
            }
        }
    }

    public void hideProgress() {
        if (this.customLayout == null) {
            this.mProgressBar.setVisibility(4);
            for (int i = 0; i < this.mProviderHolder.getChildCount(); i++) {
                View child = this.mProviderHolder.getChildAt(i);
                child.setEnabled(true);
                child.setAlpha(1.0f);
            }
        }
    }
}
