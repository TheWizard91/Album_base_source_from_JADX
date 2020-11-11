package com.firebase.p008ui.auth.p009ui.email;

import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import com.firebase.p008ui.auth.C2354R;
import com.firebase.p008ui.auth.p009ui.FragmentBase;
import com.firebase.p008ui.auth.util.data.EmailLinkParser;
import com.firebase.p008ui.auth.util.data.PrivacyDisclosureUtils;
import com.firebase.p008ui.auth.util.data.ProviderUtils;
import com.firebase.p008ui.auth.util.p010ui.TextHelper;

/* renamed from: com.firebase.ui.auth.ui.email.EmailLinkCrossDeviceLinkingFragment */
public class EmailLinkCrossDeviceLinkingFragment extends FragmentBase implements View.OnClickListener {
    public static final String TAG = "CrossDeviceFragment";
    private Button mContinueButton;
    private FinishEmailLinkSignInListener mListener;
    private ProgressBar mProgressBar;

    /* renamed from: com.firebase.ui.auth.ui.email.EmailLinkCrossDeviceLinkingFragment$FinishEmailLinkSignInListener */
    interface FinishEmailLinkSignInListener {
        void completeCrossDeviceEmailLinkFlow();
    }

    public static EmailLinkCrossDeviceLinkingFragment newInstance() {
        return new EmailLinkCrossDeviceLinkingFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(C2354R.C2359layout.fui_email_link_cross_device_linking, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.mProgressBar = (ProgressBar) view.findViewById(C2354R.C2357id.top_progress_bar);
        Button button = (Button) view.findViewById(C2354R.C2357id.button_continue);
        this.mContinueButton = button;
        button.setOnClickListener(this);
        String providerName = ProviderUtils.providerIdToProviderName(new EmailLinkParser(getFlowParams().emailLink).getProviderId());
        TextView body = (TextView) view.findViewById(C2354R.C2357id.cross_device_linking_body);
        String bodyText = getString(C2354R.string.fui_email_link_cross_device_linking_text, providerName);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(bodyText);
        TextHelper.boldAllOccurencesOfText(spannableStringBuilder, bodyText, providerName);
        body.setText(spannableStringBuilder);
        if (Build.VERSION.SDK_INT >= 26) {
            body.setJustificationMode(1);
        }
        PrivacyDisclosureUtils.setupTermsOfServiceFooter(requireContext(), getFlowParams(), (TextView) view.findViewById(C2354R.C2357id.email_footer_tos_and_pp_text));
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity instanceof FinishEmailLinkSignInListener) {
            this.mListener = (FinishEmailLinkSignInListener) activity;
            return;
        }
        throw new IllegalStateException("Activity must implement EmailLinkPromptEmailListener");
    }

    public void onClick(View view) {
        if (view.getId() == C2354R.C2357id.button_continue) {
            this.mListener.completeCrossDeviceEmailLinkFlow();
        }
    }

    public void showProgress(int message) {
        this.mProgressBar.setVisibility(0);
    }

    public void hideProgress() {
        this.mProgressBar.setVisibility(4);
    }
}
