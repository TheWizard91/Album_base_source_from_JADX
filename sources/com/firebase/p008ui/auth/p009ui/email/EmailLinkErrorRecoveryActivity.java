package com.firebase.p008ui.auth.p009ui.email;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import com.firebase.p008ui.auth.C2354R;
import com.firebase.p008ui.auth.IdpResponse;
import com.firebase.p008ui.auth.data.model.FlowParameters;
import com.firebase.p008ui.auth.p009ui.AppCompatBase;
import com.firebase.p008ui.auth.p009ui.email.EmailLinkCrossDeviceLinkingFragment;
import com.firebase.p008ui.auth.p009ui.email.EmailLinkPromptEmailFragment;

/* renamed from: com.firebase.ui.auth.ui.email.EmailLinkErrorRecoveryActivity */
public class EmailLinkErrorRecoveryActivity extends AppCompatBase implements EmailLinkPromptEmailFragment.EmailLinkPromptEmailListener, EmailLinkCrossDeviceLinkingFragment.FinishEmailLinkSignInListener {
    private static final String RECOVERY_TYPE_KEY = "com.firebase.ui.auth.ui.email.recoveryTypeKey";

    public static Intent createIntent(Context context, FlowParameters flowParams, int flow) {
        return createBaseIntent(context, EmailLinkErrorRecoveryActivity.class, flowParams).putExtra(RECOVERY_TYPE_KEY, flow);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        Fragment fragment;
        super.onCreate(savedInstanceState);
        setContentView(C2354R.C2359layout.fui_activity_register_email);
        if (savedInstanceState == null) {
            if (getIntent().getIntExtra(RECOVERY_TYPE_KEY, -1) == 116) {
                fragment = EmailLinkCrossDeviceLinkingFragment.newInstance();
            } else {
                fragment = EmailLinkPromptEmailFragment.newInstance();
            }
            switchFragment(fragment, C2354R.C2357id.fragment_register_email, EmailLinkPromptEmailFragment.TAG);
        }
    }

    public void onEmailPromptSuccess(IdpResponse response) {
        finish(-1, response.toIntent());
    }

    public void completeCrossDeviceEmailLinkFlow() {
        switchFragment(EmailLinkPromptEmailFragment.newInstance(), C2354R.C2357id.fragment_register_email, EmailLinkCrossDeviceLinkingFragment.TAG, true, true);
    }

    public void showProgress(int message) {
        throw new UnsupportedOperationException("Fragments must handle progress updates.");
    }

    public void hideProgress() {
        throw new UnsupportedOperationException("Fragments must handle progress updates.");
    }
}
