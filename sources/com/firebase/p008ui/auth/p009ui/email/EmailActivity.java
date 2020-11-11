package com.firebase.p008ui.auth.p009ui.email;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.firebase.p008ui.auth.AuthUI;
import com.firebase.p008ui.auth.C2354R;
import com.firebase.p008ui.auth.FirebaseUiException;
import com.firebase.p008ui.auth.IdpResponse;
import com.firebase.p008ui.auth.data.model.FlowParameters;
import com.firebase.p008ui.auth.data.model.User;
import com.firebase.p008ui.auth.p009ui.AppCompatBase;
import com.firebase.p008ui.auth.p009ui.email.CheckEmailFragment;
import com.firebase.p008ui.auth.p009ui.email.EmailLinkFragment;
import com.firebase.p008ui.auth.p009ui.email.RegisterEmailFragment;
import com.firebase.p008ui.auth.p009ui.email.TroubleSigningInFragment;
import com.firebase.p008ui.auth.p009ui.idp.WelcomeBackIdpPrompt;
import com.firebase.p008ui.auth.util.ExtraConstants;
import com.firebase.p008ui.auth.util.data.EmailLinkPersistenceManager;
import com.firebase.p008ui.auth.util.data.ProviderUtils;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.ActionCodeSettings;

/* renamed from: com.firebase.ui.auth.ui.email.EmailActivity */
public class EmailActivity extends AppCompatBase implements CheckEmailFragment.CheckEmailListener, RegisterEmailFragment.AnonymousUpgradeListener, EmailLinkFragment.TroubleSigningInListener, TroubleSigningInFragment.ResendEmailListener {
    public static Intent createIntent(Context context, FlowParameters flowParams) {
        return createBaseIntent(context, EmailActivity.class, flowParams);
    }

    public static Intent createIntent(Context context, FlowParameters flowParams, String email) {
        return createBaseIntent(context, EmailActivity.class, flowParams).putExtra(ExtraConstants.EMAIL, email);
    }

    public static Intent createIntentForLinking(Context context, FlowParameters flowParams, IdpResponse responseForLinking) {
        return createIntent(context, flowParams, responseForLinking.getEmail()).putExtra(ExtraConstants.IDP_RESPONSE, responseForLinking);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C2354R.C2359layout.fui_activity_register_email);
        if (savedInstanceState == null) {
            String email = getIntent().getExtras().getString(ExtraConstants.EMAIL);
            IdpResponse responseForLinking = (IdpResponse) getIntent().getExtras().getParcelable(ExtraConstants.IDP_RESPONSE);
            if (email == null || responseForLinking == null) {
                switchFragment(CheckEmailFragment.newInstance(email), C2354R.C2357id.fragment_register_email, CheckEmailFragment.TAG);
                return;
            }
            AuthUI.IdpConfig emailConfig = ProviderUtils.getConfigFromIdpsOrThrow(getFlowParams().providers, "emailLink");
            EmailLinkPersistenceManager.getInstance().saveIdpResponseForLinking(getApplication(), responseForLinking);
            switchFragment(EmailLinkFragment.newInstance(email, (ActionCodeSettings) emailConfig.getParams().getParcelable(ExtraConstants.ACTION_CODE_SETTINGS), responseForLinking, emailConfig.getParams().getBoolean(ExtraConstants.FORCE_SAME_DEVICE)), C2354R.C2357id.fragment_register_email, EmailLinkFragment.TAG);
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 104 || requestCode == 103) {
            finish(resultCode, data);
        }
    }

    public void onExistingEmailUser(User user) {
        if (user.getProviderId().equals("emailLink")) {
            showRegisterEmailLinkFragment(ProviderUtils.getConfigFromIdpsOrThrow(getFlowParams().providers, "emailLink"), user.getEmail());
            return;
        }
        startActivityForResult(WelcomeBackPasswordPrompt.createIntent(this, getFlowParams(), new IdpResponse.Builder(user).build()), 104);
        setSlideAnimation();
    }

    public void onExistingIdpUser(User user) {
        startActivityForResult(WelcomeBackIdpPrompt.createIntent(this, getFlowParams(), user), 103);
        setSlideAnimation();
    }

    public void onNewUser(User user) {
        TextInputLayout emailLayout = (TextInputLayout) findViewById(C2354R.C2357id.email_layout);
        AuthUI.IdpConfig emailConfig = ProviderUtils.getConfigFromIdps(getFlowParams().providers, "password");
        if (emailConfig == null) {
            emailConfig = ProviderUtils.getConfigFromIdps(getFlowParams().providers, "emailLink");
        }
        if (emailConfig.getParams().getBoolean(ExtraConstants.ALLOW_NEW_EMAILS, true)) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (emailConfig.getProviderId().equals("emailLink")) {
                showRegisterEmailLinkFragment(emailConfig, user.getEmail());
                return;
            }
            ft.replace(C2354R.C2357id.fragment_register_email, (Fragment) RegisterEmailFragment.newInstance(user), RegisterEmailFragment.TAG);
            if (emailLayout != null) {
                String emailFieldName = getString(C2354R.string.fui_email_field_name);
                ViewCompat.setTransitionName(emailLayout, emailFieldName);
                ft.addSharedElement(emailLayout, emailFieldName);
            }
            ft.disallowAddToBackStack().commit();
            return;
        }
        emailLayout.setError(getString(C2354R.string.fui_error_email_does_not_exist));
    }

    public void onTroubleSigningIn(String email) {
        switchFragment(TroubleSigningInFragment.newInstance(email), C2354R.C2357id.fragment_register_email, TroubleSigningInFragment.TAG, true, true);
    }

    public void onClickResendEmail(String email) {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
        showRegisterEmailLinkFragment(ProviderUtils.getConfigFromIdpsOrThrow(getFlowParams().providers, "emailLink"), email);
    }

    public void onSendEmailFailure(Exception e) {
        finishOnDeveloperError(e);
    }

    public void onDeveloperFailure(Exception e) {
        finishOnDeveloperError(e);
    }

    private void finishOnDeveloperError(Exception e) {
        finish(0, IdpResponse.getErrorIntent(new FirebaseUiException(3, e.getMessage())));
    }

    private void setSlideAnimation() {
        overridePendingTransition(C2354R.anim.fui_slide_in_right, C2354R.anim.fui_slide_out_left);
    }

    private void showRegisterEmailLinkFragment(AuthUI.IdpConfig emailConfig, String email) {
        switchFragment(EmailLinkFragment.newInstance(email, (ActionCodeSettings) emailConfig.getParams().getParcelable(ExtraConstants.ACTION_CODE_SETTINGS)), C2354R.C2357id.fragment_register_email, EmailLinkFragment.TAG);
    }

    public void showProgress(int message) {
        throw new UnsupportedOperationException("Email fragments must handle progress updates.");
    }

    public void hideProgress() {
        throw new UnsupportedOperationException("Email fragments must handle progress updates.");
    }

    public void onMergeFailure(IdpResponse response) {
        finish(5, response.toIntent());
    }
}
