package com.firebase.p008ui.auth.p009ui.email;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import com.firebase.p008ui.auth.C2354R;
import com.firebase.p008ui.auth.p009ui.FragmentBase;
import com.firebase.p008ui.auth.util.ExtraConstants;
import com.firebase.p008ui.auth.util.data.PrivacyDisclosureUtils;

/* renamed from: com.firebase.ui.auth.ui.email.TroubleSigningInFragment */
public class TroubleSigningInFragment extends FragmentBase implements View.OnClickListener {
    public static final String TAG = "TroubleSigningInFragment";
    private String mEmail;
    private ResendEmailListener mListener;
    private ProgressBar mProgressBar;

    /* renamed from: com.firebase.ui.auth.ui.email.TroubleSigningInFragment$ResendEmailListener */
    interface ResendEmailListener {
        void onClickResendEmail(String str);
    }

    public static TroubleSigningInFragment newInstance(String email) {
        TroubleSigningInFragment fragment = new TroubleSigningInFragment();
        Bundle args = new Bundle();
        args.putString(ExtraConstants.EMAIL, email);
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(C2354R.C2359layout.fui_email_link_trouble_signing_in_layout, container, false);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        FragmentActivity activity = getActivity();
        if (activity instanceof ResendEmailListener) {
            this.mListener = (ResendEmailListener) activity;
            return;
        }
        throw new IllegalStateException("Activity must implement ResendEmailListener");
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.mProgressBar = (ProgressBar) view.findViewById(C2354R.C2357id.top_progress_bar);
        this.mEmail = getArguments().getString(ExtraConstants.EMAIL);
        setOnClickListeners(view);
        setPrivacyFooter(view);
    }

    private void setOnClickListeners(View view) {
        view.findViewById(C2354R.C2357id.button_resend_email).setOnClickListener(this);
    }

    private void setPrivacyFooter(View view) {
        PrivacyDisclosureUtils.setupTermsOfServiceFooter(requireContext(), getFlowParams(), (TextView) view.findViewById(C2354R.C2357id.email_footer_tos_and_pp_text));
    }

    public void onClick(View view) {
        if (view.getId() == C2354R.C2357id.button_resend_email) {
            this.mListener.onClickResendEmail(this.mEmail);
        }
    }

    public void showProgress(int message) {
        this.mProgressBar.setVisibility(0);
    }

    public void hideProgress() {
        this.mProgressBar.setVisibility(4);
    }
}
