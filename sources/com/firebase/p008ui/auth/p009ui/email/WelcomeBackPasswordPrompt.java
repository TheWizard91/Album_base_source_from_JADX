package com.firebase.p008ui.auth.p009ui.email;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import com.firebase.p008ui.auth.C2354R;
import com.firebase.p008ui.auth.FirebaseAuthAnonymousUpgradeException;
import com.firebase.p008ui.auth.FirebaseUiException;
import com.firebase.p008ui.auth.IdpResponse;
import com.firebase.p008ui.auth.data.model.FlowParameters;
import com.firebase.p008ui.auth.p009ui.AppCompatBase;
import com.firebase.p008ui.auth.util.ExtraConstants;
import com.firebase.p008ui.auth.util.FirebaseAuthError;
import com.firebase.p008ui.auth.util.data.PrivacyDisclosureUtils;
import com.firebase.p008ui.auth.util.data.ProviderUtils;
import com.firebase.p008ui.auth.util.p010ui.ImeHelper;
import com.firebase.p008ui.auth.util.p010ui.TextHelper;
import com.firebase.p008ui.auth.viewmodel.ResourceObserver;
import com.firebase.p008ui.auth.viewmodel.email.WelcomeBackPasswordHandler;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

/* renamed from: com.firebase.ui.auth.ui.email.WelcomeBackPasswordPrompt */
public class WelcomeBackPasswordPrompt extends AppCompatBase implements View.OnClickListener, ImeHelper.DonePressedListener {
    private Button mDoneButton;
    /* access modifiers changed from: private */
    public WelcomeBackPasswordHandler mHandler;
    private IdpResponse mIdpResponse;
    private EditText mPasswordField;
    /* access modifiers changed from: private */
    public TextInputLayout mPasswordLayout;
    private ProgressBar mProgressBar;

    public static Intent createIntent(Context context, FlowParameters flowParams, IdpResponse response) {
        return createBaseIntent(context, WelcomeBackPasswordPrompt.class, flowParams).putExtra(ExtraConstants.IDP_RESPONSE, response);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C2354R.C2359layout.fui_welcome_back_password_prompt_layout);
        getWindow().setSoftInputMode(4);
        IdpResponse fromResultIntent = IdpResponse.fromResultIntent(getIntent());
        this.mIdpResponse = fromResultIntent;
        String email = fromResultIntent.getEmail();
        this.mDoneButton = (Button) findViewById(C2354R.C2357id.button_done);
        this.mProgressBar = (ProgressBar) findViewById(C2354R.C2357id.top_progress_bar);
        this.mPasswordLayout = (TextInputLayout) findViewById(C2354R.C2357id.password_layout);
        EditText editText = (EditText) findViewById(C2354R.C2357id.password);
        this.mPasswordField = editText;
        ImeHelper.setImeOnDoneListener(editText, this);
        String bodyText = getString(C2354R.string.fui_welcome_back_password_prompt_body, new Object[]{email});
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(bodyText);
        TextHelper.boldAllOccurencesOfText(spannableStringBuilder, bodyText, email);
        ((TextView) findViewById(C2354R.C2357id.welcome_back_password_body)).setText(spannableStringBuilder);
        this.mDoneButton.setOnClickListener(this);
        findViewById(C2354R.C2357id.trouble_signing_in).setOnClickListener(this);
        WelcomeBackPasswordHandler welcomeBackPasswordHandler = (WelcomeBackPasswordHandler) ViewModelProviders.m17of((FragmentActivity) this).get(WelcomeBackPasswordHandler.class);
        this.mHandler = welcomeBackPasswordHandler;
        welcomeBackPasswordHandler.init(getFlowParams());
        this.mHandler.getOperation().observe(this, new ResourceObserver<IdpResponse>(this, C2354R.string.fui_progress_dialog_signing_in) {
            /* access modifiers changed from: protected */
            public void onSuccess(IdpResponse response) {
                WelcomeBackPasswordPrompt welcomeBackPasswordPrompt = WelcomeBackPasswordPrompt.this;
                welcomeBackPasswordPrompt.startSaveCredentials(welcomeBackPasswordPrompt.mHandler.getCurrentUser(), response, WelcomeBackPasswordPrompt.this.mHandler.getPendingPassword());
            }

            /* access modifiers changed from: protected */
            public void onFailure(Exception e) {
                if (e instanceof FirebaseAuthAnonymousUpgradeException) {
                    WelcomeBackPasswordPrompt.this.finish(5, ((FirebaseAuthAnonymousUpgradeException) e).getResponse().toIntent());
                } else if (!(e instanceof FirebaseAuthException) || FirebaseAuthError.fromException((FirebaseAuthException) e) != FirebaseAuthError.ERROR_USER_DISABLED) {
                    TextInputLayout access$200 = WelcomeBackPasswordPrompt.this.mPasswordLayout;
                    WelcomeBackPasswordPrompt welcomeBackPasswordPrompt = WelcomeBackPasswordPrompt.this;
                    access$200.setError(welcomeBackPasswordPrompt.getString(welcomeBackPasswordPrompt.getErrorMessage(e)));
                } else {
                    WelcomeBackPasswordPrompt.this.finish(0, IdpResponse.from(new FirebaseUiException(12)).toIntent());
                }
            }
        });
        PrivacyDisclosureUtils.setupTermsOfServiceFooter(this, getFlowParams(), (TextView) findViewById(C2354R.C2357id.email_footer_tos_and_pp_text));
    }

    /* access modifiers changed from: private */
    public int getErrorMessage(Exception exception) {
        if (exception instanceof FirebaseAuthInvalidCredentialsException) {
            return C2354R.string.fui_error_invalid_password;
        }
        return C2354R.string.fui_error_unknown;
    }

    private void onForgotPasswordClicked() {
        startActivity(RecoverPasswordActivity.createIntent(this, getFlowParams(), this.mIdpResponse.getEmail()));
    }

    public void onDonePressed() {
        validateAndSignIn();
    }

    private void validateAndSignIn() {
        validateAndSignIn(this.mPasswordField.getText().toString());
    }

    private void validateAndSignIn(String password) {
        if (TextUtils.isEmpty(password)) {
            this.mPasswordLayout.setError(getString(C2354R.string.fui_error_invalid_password));
            return;
        }
        this.mPasswordLayout.setError((CharSequence) null);
        this.mHandler.startSignIn(this.mIdpResponse.getEmail(), password, this.mIdpResponse, ProviderUtils.getAuthCredential(this.mIdpResponse));
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == C2354R.C2357id.button_done) {
            validateAndSignIn();
        } else if (id == C2354R.C2357id.trouble_signing_in) {
            onForgotPasswordClicked();
        }
    }

    public void showProgress(int message) {
        this.mDoneButton.setEnabled(false);
        this.mProgressBar.setVisibility(0);
    }

    public void hideProgress() {
        this.mDoneButton.setEnabled(true);
        this.mProgressBar.setVisibility(4);
    }
}
