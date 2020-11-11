package com.firebase.p008ui.auth.p009ui.email;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import com.firebase.p008ui.auth.C2354R;
import com.firebase.p008ui.auth.data.model.FlowParameters;
import com.firebase.p008ui.auth.p009ui.AppCompatBase;
import com.firebase.p008ui.auth.util.ExtraConstants;
import com.firebase.p008ui.auth.util.data.PrivacyDisclosureUtils;
import com.firebase.p008ui.auth.util.p010ui.ImeHelper;
import com.firebase.p008ui.auth.util.p010ui.fieldvalidators.EmailFieldValidator;
import com.firebase.p008ui.auth.viewmodel.ResourceObserver;
import com.firebase.p008ui.auth.viewmodel.email.RecoverPasswordHandler;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

/* renamed from: com.firebase.ui.auth.ui.email.RecoverPasswordActivity */
public class RecoverPasswordActivity extends AppCompatBase implements View.OnClickListener, ImeHelper.DonePressedListener {
    private EditText mEmailEditText;
    private EmailFieldValidator mEmailFieldValidator;
    /* access modifiers changed from: private */
    public TextInputLayout mEmailInputLayout;
    private RecoverPasswordHandler mHandler;
    private ProgressBar mProgressBar;
    private Button mSubmitButton;

    public static Intent createIntent(Context context, FlowParameters params, String email) {
        return createBaseIntent(context, RecoverPasswordActivity.class, params).putExtra(ExtraConstants.EMAIL, email);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C2354R.C2359layout.fui_forgot_password_layout);
        RecoverPasswordHandler recoverPasswordHandler = (RecoverPasswordHandler) ViewModelProviders.m17of((FragmentActivity) this).get(RecoverPasswordHandler.class);
        this.mHandler = recoverPasswordHandler;
        recoverPasswordHandler.init(getFlowParams());
        this.mHandler.getOperation().observe(this, new ResourceObserver<String>(this, C2354R.string.fui_progress_dialog_sending) {
            /* access modifiers changed from: protected */
            public void onSuccess(String email) {
                RecoverPasswordActivity.this.mEmailInputLayout.setError((CharSequence) null);
                RecoverPasswordActivity.this.showEmailSentDialog(email);
            }

            /* access modifiers changed from: protected */
            public void onFailure(Exception e) {
                if ((e instanceof FirebaseAuthInvalidUserException) || (e instanceof FirebaseAuthInvalidCredentialsException)) {
                    RecoverPasswordActivity.this.mEmailInputLayout.setError(RecoverPasswordActivity.this.getString(C2354R.string.fui_error_email_does_not_exist));
                } else {
                    RecoverPasswordActivity.this.mEmailInputLayout.setError(RecoverPasswordActivity.this.getString(C2354R.string.fui_error_unknown));
                }
            }
        });
        this.mProgressBar = (ProgressBar) findViewById(C2354R.C2357id.top_progress_bar);
        this.mSubmitButton = (Button) findViewById(C2354R.C2357id.button_done);
        this.mEmailInputLayout = (TextInputLayout) findViewById(C2354R.C2357id.email_layout);
        this.mEmailEditText = (EditText) findViewById(C2354R.C2357id.email);
        this.mEmailFieldValidator = new EmailFieldValidator(this.mEmailInputLayout);
        String email = getIntent().getStringExtra(ExtraConstants.EMAIL);
        if (email != null) {
            this.mEmailEditText.setText(email);
        }
        ImeHelper.setImeOnDoneListener(this.mEmailEditText, this);
        this.mSubmitButton.setOnClickListener(this);
        PrivacyDisclosureUtils.setupTermsOfServiceFooter(this, getFlowParams(), (TextView) findViewById(C2354R.C2357id.email_footer_tos_and_pp_text));
    }

    public void onClick(View view) {
        if (view.getId() == C2354R.C2357id.button_done) {
            onDonePressed();
        }
    }

    public void onDonePressed() {
        if (this.mEmailFieldValidator.validate(this.mEmailEditText.getText())) {
            this.mHandler.startReset(this.mEmailEditText.getText().toString());
        }
    }

    /* access modifiers changed from: private */
    public void showEmailSentDialog(String email) {
        new AlertDialog.Builder(this).setTitle(C2354R.string.fui_title_confirm_recover_password).setMessage((CharSequence) getString(C2354R.string.fui_confirm_recovery_body, new Object[]{email})).setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
                RecoverPasswordActivity.this.finish(-1, new Intent());
            }
        }).setPositiveButton(17039370, (DialogInterface.OnClickListener) null).show();
    }

    public void showProgress(int message) {
        this.mSubmitButton.setEnabled(false);
        this.mProgressBar.setVisibility(0);
    }

    public void hideProgress() {
        this.mSubmitButton.setEnabled(true);
        this.mProgressBar.setVisibility(4);
    }
}
