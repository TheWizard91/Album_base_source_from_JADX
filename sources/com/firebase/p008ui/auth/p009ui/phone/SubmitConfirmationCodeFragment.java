package com.firebase.p008ui.auth.p009ui.phone;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.firebase.p008ui.auth.C2354R;
import com.firebase.p008ui.auth.IdpResponse;
import com.firebase.p008ui.auth.data.model.Resource;
import com.firebase.p008ui.auth.data.model.State;
import com.firebase.p008ui.auth.p009ui.FragmentBase;
import com.firebase.p008ui.auth.util.ExtraConstants;
import com.firebase.p008ui.auth.util.data.PrivacyDisclosureUtils;
import com.firebase.p008ui.auth.util.p010ui.BucketedTextChangeListener;
import com.firebase.p008ui.auth.viewmodel.phone.PhoneProviderResponseHandler;
import java.util.concurrent.TimeUnit;

/* renamed from: com.firebase.ui.auth.ui.phone.SubmitConfirmationCodeFragment */
public class SubmitConfirmationCodeFragment extends FragmentBase {
    private static final String EXTRA_MILLIS_UNTIL_FINISHED = "millis_until_finished";
    private static final long RESEND_WAIT_MILLIS = 15000;
    public static final String TAG = "SubmitConfirmationCodeFragment";
    private static final long TICK_INTERVAL_MILLIS = 500;
    private static final int VERIFICATION_CODE_LENGTH = 6;
    /* access modifiers changed from: private */
    public SpacedEditText mConfirmationCodeEditText;
    /* access modifiers changed from: private */
    public TextView mCountDownTextView;
    /* access modifiers changed from: private */
    public final Runnable mCountdown = new Runnable() {
        public void run() {
            SubmitConfirmationCodeFragment.this.processCountdownTick();
        }
    };
    /* access modifiers changed from: private */
    public PhoneNumberVerificationHandler mHandler;
    private boolean mHasResumed;
    /* access modifiers changed from: private */
    public final Handler mLooper = new Handler();
    /* access modifiers changed from: private */
    public long mMillisUntilFinished = RESEND_WAIT_MILLIS;
    /* access modifiers changed from: private */
    public String mPhoneNumber;
    private TextView mPhoneTextView;
    private ProgressBar mProgressBar;
    /* access modifiers changed from: private */
    public TextView mResendCodeTextView;

    public static SubmitConfirmationCodeFragment newInstance(String phoneNumber) {
        SubmitConfirmationCodeFragment fragment = new SubmitConfirmationCodeFragment();
        Bundle args = new Bundle();
        args.putString(ExtraConstants.PHONE, phoneNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mHandler = (PhoneNumberVerificationHandler) ViewModelProviders.m17of(requireActivity()).get(PhoneNumberVerificationHandler.class);
        this.mPhoneNumber = getArguments().getString(ExtraConstants.PHONE);
        if (savedInstanceState != null) {
            this.mMillisUntilFinished = savedInstanceState.getLong(EXTRA_MILLIS_UNTIL_FINISHED);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(C2354R.C2359layout.fui_confirmation_code_layout, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.mProgressBar = (ProgressBar) view.findViewById(C2354R.C2357id.top_progress_bar);
        this.mPhoneTextView = (TextView) view.findViewById(C2354R.C2357id.edit_phone_number);
        this.mCountDownTextView = (TextView) view.findViewById(C2354R.C2357id.ticker);
        this.mResendCodeTextView = (TextView) view.findViewById(C2354R.C2357id.resend_code);
        this.mConfirmationCodeEditText = (SpacedEditText) view.findViewById(C2354R.C2357id.confirmation_code);
        requireActivity().setTitle(getString(C2354R.string.fui_verify_your_phone_title));
        processCountdownTick();
        setupConfirmationCodeEditText();
        setupEditPhoneNumberTextView();
        setupResendConfirmationCodeTextView();
        PrivacyDisclosureUtils.setupTermsOfServiceFooter(requireContext(), getFlowParams(), (TextView) view.findViewById(C2354R.C2357id.email_footer_tos_and_pp_text));
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((PhoneProviderResponseHandler) ViewModelProviders.m17of(requireActivity()).get(PhoneProviderResponseHandler.class)).getOperation().observe(this, new Observer<Resource<IdpResponse>>() {
            public void onChanged(Resource<IdpResponse> resource) {
                if (resource.getState() == State.FAILURE) {
                    SubmitConfirmationCodeFragment.this.mConfirmationCodeEditText.setText("");
                }
            }
        });
    }

    public void onStart() {
        super.onStart();
        this.mConfirmationCodeEditText.requestFocus();
        ((InputMethodManager) requireActivity().getSystemService("input_method")).showSoftInput(this.mConfirmationCodeEditText, 0);
    }

    public void onResume() {
        CharSequence candidate;
        super.onResume();
        if (!this.mHasResumed) {
            this.mHasResumed = true;
            return;
        }
        ClipData clip = ((ClipboardManager) ContextCompat.getSystemService(requireContext(), ClipboardManager.class)).getPrimaryClip();
        if (clip != null && clip.getItemCount() == 1 && (candidate = clip.getItemAt(0).getText()) != null && candidate.length() == 6) {
            try {
                Integer.parseInt(candidate.toString());
                this.mConfirmationCodeEditText.setText(candidate);
            } catch (NumberFormatException e) {
            }
        }
        this.mLooper.removeCallbacks(this.mCountdown);
        this.mLooper.postDelayed(this.mCountdown, TICK_INTERVAL_MILLIS);
    }

    public void onSaveInstanceState(Bundle outState) {
        this.mLooper.removeCallbacks(this.mCountdown);
        outState.putLong(EXTRA_MILLIS_UNTIL_FINISHED, this.mMillisUntilFinished);
    }

    public void onDestroy() {
        super.onDestroy();
        this.mLooper.removeCallbacks(this.mCountdown);
    }

    private void setupConfirmationCodeEditText() {
        this.mConfirmationCodeEditText.setText("------");
        this.mConfirmationCodeEditText.addTextChangedListener(new BucketedTextChangeListener(this.mConfirmationCodeEditText, 6, "-", new BucketedTextChangeListener.ContentChangeCallback() {
            public void whenComplete() {
                SubmitConfirmationCodeFragment.this.submitCode();
            }

            public void whileIncomplete() {
            }
        }));
    }

    private void setupEditPhoneNumberTextView() {
        this.mPhoneTextView.setText(this.mPhoneNumber);
        this.mPhoneTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SubmitConfirmationCodeFragment.this.getFragmentManager().popBackStack();
            }
        });
    }

    private void setupResendConfirmationCodeTextView() {
        this.mResendCodeTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SubmitConfirmationCodeFragment.this.mHandler.verifyPhoneNumber(SubmitConfirmationCodeFragment.this.mPhoneNumber, true);
                SubmitConfirmationCodeFragment.this.mResendCodeTextView.setVisibility(8);
                SubmitConfirmationCodeFragment.this.mCountDownTextView.setVisibility(0);
                SubmitConfirmationCodeFragment.this.mCountDownTextView.setText(String.format(SubmitConfirmationCodeFragment.this.getString(C2354R.string.fui_resend_code_in), new Object[]{15L}));
                long unused = SubmitConfirmationCodeFragment.this.mMillisUntilFinished = SubmitConfirmationCodeFragment.RESEND_WAIT_MILLIS;
                SubmitConfirmationCodeFragment.this.mLooper.postDelayed(SubmitConfirmationCodeFragment.this.mCountdown, SubmitConfirmationCodeFragment.TICK_INTERVAL_MILLIS);
            }
        });
    }

    /* access modifiers changed from: private */
    public void processCountdownTick() {
        long j = this.mMillisUntilFinished - TICK_INTERVAL_MILLIS;
        this.mMillisUntilFinished = j;
        if (j <= 0) {
            this.mCountDownTextView.setText("");
            this.mCountDownTextView.setVisibility(8);
            this.mResendCodeTextView.setVisibility(0);
            return;
        }
        this.mCountDownTextView.setText(String.format(getString(C2354R.string.fui_resend_code_in), new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(this.mMillisUntilFinished) + 1)}));
        this.mLooper.postDelayed(this.mCountdown, TICK_INTERVAL_MILLIS);
    }

    /* access modifiers changed from: private */
    public void submitCode() {
        this.mHandler.submitVerificationCode(this.mPhoneNumber, this.mConfirmationCodeEditText.getUnspacedText().toString());
    }

    public void showProgress(int message) {
        this.mProgressBar.setVisibility(0);
    }

    public void hideProgress() {
        this.mProgressBar.setVisibility(4);
    }
}
