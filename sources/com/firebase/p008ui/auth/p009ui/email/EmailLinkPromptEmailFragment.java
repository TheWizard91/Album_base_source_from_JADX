package com.firebase.p008ui.auth.p009ui.email;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import com.firebase.p008ui.auth.C2354R;
import com.firebase.p008ui.auth.IdpResponse;
import com.firebase.p008ui.auth.p009ui.FragmentBase;
import com.firebase.p008ui.auth.util.data.PrivacyDisclosureUtils;
import com.firebase.p008ui.auth.util.p010ui.fieldvalidators.EmailFieldValidator;
import com.firebase.p008ui.auth.viewmodel.ResourceObserver;
import com.firebase.p008ui.auth.viewmodel.email.EmailLinkSignInHandler;
import com.google.android.material.textfield.TextInputLayout;

/* renamed from: com.firebase.ui.auth.ui.email.EmailLinkPromptEmailFragment */
public class EmailLinkPromptEmailFragment extends FragmentBase implements View.OnClickListener {
    public static final String TAG = "EmailLinkPromptEmailFragment";
    private EditText mEmailEditText;
    private EmailFieldValidator mEmailFieldValidator;
    /* access modifiers changed from: private */
    public TextInputLayout mEmailLayout;
    private EmailLinkSignInHandler mHandler;
    /* access modifiers changed from: private */
    public EmailLinkPromptEmailListener mListener;
    private Button mNextButton;
    private ProgressBar mProgressBar;

    /* renamed from: com.firebase.ui.auth.ui.email.EmailLinkPromptEmailFragment$EmailLinkPromptEmailListener */
    interface EmailLinkPromptEmailListener {
        void onEmailPromptSuccess(IdpResponse idpResponse);
    }

    public static EmailLinkPromptEmailFragment newInstance() {
        return new EmailLinkPromptEmailFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(C2354R.C2359layout.fui_check_email_layout, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.mNextButton = (Button) view.findViewById(C2354R.C2357id.button_next);
        this.mProgressBar = (ProgressBar) view.findViewById(C2354R.C2357id.top_progress_bar);
        this.mNextButton.setOnClickListener(this);
        this.mEmailLayout = (TextInputLayout) view.findViewById(C2354R.C2357id.email_layout);
        this.mEmailEditText = (EditText) view.findViewById(C2354R.C2357id.email);
        this.mEmailFieldValidator = new EmailFieldValidator(this.mEmailLayout);
        this.mEmailLayout.setOnClickListener(this);
        this.mEmailEditText.setOnClickListener(this);
        getActivity().setTitle(C2354R.string.fui_email_link_confirm_email_header);
        PrivacyDisclosureUtils.setupTermsOfServiceFooter(requireContext(), getFlowParams(), (TextView) view.findViewById(C2354R.C2357id.email_footer_tos_and_pp_text));
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity instanceof EmailLinkPromptEmailListener) {
            this.mListener = (EmailLinkPromptEmailListener) activity;
            initHandler();
            return;
        }
        throw new IllegalStateException("Activity must implement EmailLinkPromptEmailListener");
    }

    private void initHandler() {
        EmailLinkSignInHandler emailLinkSignInHandler = (EmailLinkSignInHandler) ViewModelProviders.m15of((Fragment) this).get(EmailLinkSignInHandler.class);
        this.mHandler = emailLinkSignInHandler;
        emailLinkSignInHandler.init(getFlowParams());
        this.mHandler.getOperation().observe(this, new ResourceObserver<IdpResponse>(this) {
            /* access modifiers changed from: protected */
            public void onSuccess(IdpResponse response) {
                EmailLinkPromptEmailFragment.this.mListener.onEmailPromptSuccess(response);
            }

            /* access modifiers changed from: protected */
            public void onFailure(Exception e) {
                EmailLinkPromptEmailFragment.this.mEmailLayout.setError(e.getMessage());
            }
        });
    }

    private void validateEmailAndFinishSignIn() {
        String email = this.mEmailEditText.getText().toString();
        if (this.mEmailFieldValidator.validate(email)) {
            this.mHandler.finishSignIn(email);
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == C2354R.C2357id.button_next) {
            validateEmailAndFinishSignIn();
        } else if (id == C2354R.C2357id.email_layout || id == C2354R.C2357id.email) {
            this.mEmailLayout.setError((CharSequence) null);
        }
    }

    public void showProgress(int message) {
        this.mNextButton.setEnabled(false);
        this.mProgressBar.setVisibility(0);
    }

    public void hideProgress() {
        this.mNextButton.setEnabled(true);
        this.mProgressBar.setVisibility(4);
    }
}
