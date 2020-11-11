package com.firebase.p008ui.auth.p009ui.email;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.firebase.p008ui.auth.C2354R;
import com.firebase.p008ui.auth.IdpResponse;
import com.firebase.p008ui.auth.data.model.FlowParameters;
import com.firebase.p008ui.auth.p009ui.AppCompatBase;
import com.firebase.p008ui.auth.util.ExtraConstants;
import com.firebase.p008ui.auth.util.data.PrivacyDisclosureUtils;
import com.firebase.p008ui.auth.util.p010ui.TextHelper;

/* renamed from: com.firebase.ui.auth.ui.email.WelcomeBackEmailLinkPrompt */
public class WelcomeBackEmailLinkPrompt extends AppCompatBase implements View.OnClickListener {
    private IdpResponse mIdpResponseForLinking;
    private ProgressBar mProgressBar;
    private Button mSignInButton;

    public static Intent createIntent(Context context, FlowParameters flowParams, IdpResponse response) {
        return createBaseIntent(context, WelcomeBackEmailLinkPrompt.class, flowParams).putExtra(ExtraConstants.IDP_RESPONSE, response);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C2354R.C2359layout.fui_welcome_back_email_link_prompt_layout);
        this.mIdpResponseForLinking = IdpResponse.fromResultIntent(getIntent());
        initializeViewObjects();
        setBodyText();
        setOnClickListeners();
        setPrivacyFooter();
    }

    private void startEmailLinkFlow() {
        startActivityForResult(EmailActivity.createIntentForLinking(this, getFlowParams(), this.mIdpResponseForLinking), 112);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish(resultCode, data);
    }

    private void initializeViewObjects() {
        this.mSignInButton = (Button) findViewById(C2354R.C2357id.button_sign_in);
        this.mProgressBar = (ProgressBar) findViewById(C2354R.C2357id.top_progress_bar);
    }

    private void setBodyText() {
        TextView body = (TextView) findViewById(C2354R.C2357id.welcome_back_email_link_body);
        String bodyText = getString(C2354R.string.fui_welcome_back_email_link_prompt_body, new Object[]{this.mIdpResponseForLinking.getEmail(), this.mIdpResponseForLinking.getProviderType()});
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(bodyText);
        TextHelper.boldAllOccurencesOfText(spannableStringBuilder, bodyText, this.mIdpResponseForLinking.getEmail());
        TextHelper.boldAllOccurencesOfText(spannableStringBuilder, bodyText, this.mIdpResponseForLinking.getProviderType());
        body.setText(spannableStringBuilder);
        if (Build.VERSION.SDK_INT >= 26) {
            body.setJustificationMode(1);
        }
    }

    private void setOnClickListeners() {
        this.mSignInButton.setOnClickListener(this);
    }

    private void setPrivacyFooter() {
        PrivacyDisclosureUtils.setupTermsOfServiceFooter(this, getFlowParams(), (TextView) findViewById(C2354R.C2357id.email_footer_tos_and_pp_text));
    }

    public void onClick(View view) {
        if (view.getId() == C2354R.C2357id.button_sign_in) {
            startEmailLinkFlow();
        }
    }

    public void showProgress(int message) {
        this.mSignInButton.setEnabled(false);
        this.mProgressBar.setVisibility(0);
    }

    public void hideProgress() {
        this.mProgressBar.setEnabled(true);
        this.mProgressBar.setVisibility(4);
    }
}
