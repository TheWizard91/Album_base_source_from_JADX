package com.firebase.p008ui.auth.p009ui.phone;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.firebase.p008ui.auth.C2354R;
import com.firebase.p008ui.auth.data.model.FlowParameters;
import com.firebase.p008ui.auth.data.model.PhoneNumber;
import com.firebase.p008ui.auth.p009ui.FragmentBase;
import com.firebase.p008ui.auth.util.ExtraConstants;
import com.firebase.p008ui.auth.util.data.PhoneNumberUtils;
import com.firebase.p008ui.auth.util.data.PrivacyDisclosureUtils;
import com.firebase.p008ui.auth.util.p010ui.ImeHelper;
import com.firebase.p008ui.auth.viewmodel.ResourceObserver;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Locale;

/* renamed from: com.firebase.ui.auth.ui.phone.CheckPhoneNumberFragment */
public class CheckPhoneNumberFragment extends FragmentBase implements View.OnClickListener {
    public static final String TAG = "VerifyPhoneFragment";
    private boolean mCalled;
    private CheckPhoneHandler mCheckPhoneHandler;
    private CountryListSpinner mCountryListSpinner;
    private TextView mFooterText;
    private EditText mPhoneEditText;
    /* access modifiers changed from: private */
    public TextInputLayout mPhoneInputLayout;
    private ProgressBar mProgressBar;
    private TextView mSmsTermsText;
    private Button mSubmitButton;
    private PhoneNumberVerificationHandler mVerificationHandler;

    public static CheckPhoneNumberFragment newInstance(Bundle params) {
        CheckPhoneNumberFragment fragment = new CheckPhoneNumberFragment();
        Bundle args = new Bundle();
        args.putBundle(ExtraConstants.PARAMS, params);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mVerificationHandler = (PhoneNumberVerificationHandler) ViewModelProviders.m17of(requireActivity()).get(PhoneNumberVerificationHandler.class);
        this.mCheckPhoneHandler = (CheckPhoneHandler) ViewModelProviders.m15of((Fragment) this).get(CheckPhoneHandler.class);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(C2354R.C2359layout.fui_phone_layout, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.mProgressBar = (ProgressBar) view.findViewById(C2354R.C2357id.top_progress_bar);
        this.mSubmitButton = (Button) view.findViewById(C2354R.C2357id.send_code);
        this.mCountryListSpinner = (CountryListSpinner) view.findViewById(C2354R.C2357id.country_list);
        this.mPhoneInputLayout = (TextInputLayout) view.findViewById(C2354R.C2357id.phone_layout);
        this.mPhoneEditText = (EditText) view.findViewById(C2354R.C2357id.phone_number);
        this.mSmsTermsText = (TextView) view.findViewById(C2354R.C2357id.send_sms_tos);
        this.mFooterText = (TextView) view.findViewById(C2354R.C2357id.email_footer_tos_and_pp_text);
        this.mSmsTermsText.setText(getString(C2354R.string.fui_sms_terms_of_service, getString(C2354R.string.fui_verify_phone_number)));
        if (Build.VERSION.SDK_INT >= 26 && getFlowParams().enableHints) {
            this.mPhoneEditText.setImportantForAutofill(2);
        }
        requireActivity().setTitle(getString(C2354R.string.fui_verify_phone_number_title));
        ImeHelper.setImeOnDoneListener(this.mPhoneEditText, new ImeHelper.DonePressedListener() {
            public void onDonePressed() {
                CheckPhoneNumberFragment.this.onNext();
            }
        });
        this.mSubmitButton.setOnClickListener(this);
        setupPrivacyDisclosures();
        setupCountrySpinner();
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mCheckPhoneHandler.getOperation().observe(this, new ResourceObserver<PhoneNumber>(this) {
            /* access modifiers changed from: protected */
            public void onSuccess(PhoneNumber number) {
                CheckPhoneNumberFragment.this.start(number);
            }

            /* access modifiers changed from: protected */
            public void onFailure(Exception e) {
            }
        });
        if (savedInstanceState == null && !this.mCalled) {
            this.mCalled = true;
            setDefaultCountryForSpinner();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.mCheckPhoneHandler.onActivityResult(requestCode, resultCode, data);
    }

    public void onClick(View v) {
        onNext();
    }

    /* access modifiers changed from: private */
    public void start(PhoneNumber number) {
        if (!PhoneNumber.isValid(number)) {
            this.mPhoneInputLayout.setError(getString(C2354R.string.fui_invalid_phone_number));
            return;
        }
        this.mPhoneEditText.setText(number.getPhoneNumber());
        this.mPhoneEditText.setSelection(number.getPhoneNumber().length());
        String iso = number.getCountryIso();
        if (PhoneNumber.isCountryValid(number) && this.mCountryListSpinner.isValidIso(iso)) {
            setCountryCode(number);
            onNext();
        }
    }

    /* access modifiers changed from: private */
    public void onNext() {
        String phoneNumber = getPseudoValidPhoneNumber();
        if (phoneNumber == null) {
            this.mPhoneInputLayout.setError(getString(C2354R.string.fui_invalid_phone_number));
        } else {
            this.mVerificationHandler.verifyPhoneNumber(phoneNumber, false);
        }
    }

    private String getPseudoValidPhoneNumber() {
        String everythingElse = this.mPhoneEditText.getText().toString();
        if (TextUtils.isEmpty(everythingElse)) {
            return null;
        }
        return PhoneNumberUtils.format(everythingElse, this.mCountryListSpinner.getSelectedCountryInfo());
    }

    private void setupPrivacyDisclosures() {
        FlowParameters params = getFlowParams();
        boolean termsAndPrivacyUrlsProvided = params.isTermsOfServiceUrlProvided() && params.isPrivacyPolicyUrlProvided();
        if (params.shouldShowProviderChoice() || !termsAndPrivacyUrlsProvided) {
            PrivacyDisclosureUtils.setupTermsOfServiceFooter(requireContext(), params, this.mFooterText);
            this.mSmsTermsText.setText(getString(C2354R.string.fui_sms_terms_of_service, getString(C2354R.string.fui_verify_phone_number)));
            return;
        }
        PrivacyDisclosureUtils.setupTermsOfServiceAndPrivacyPolicySmsText(requireContext(), params, this.mSmsTermsText);
    }

    private void setCountryCode(PhoneNumber number) {
        this.mCountryListSpinner.setSelectedForCountry(new Locale("", number.getCountryIso()), number.getCountryCode());
    }

    private void setupCountrySpinner() {
        this.mCountryListSpinner.init(getArguments().getBundle(ExtraConstants.PARAMS));
        this.mCountryListSpinner.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckPhoneNumberFragment.this.mPhoneInputLayout.setError((CharSequence) null);
            }
        });
    }

    private void setDefaultCountryForSpinner() {
        Bundle params = getArguments().getBundle(ExtraConstants.PARAMS);
        String phone = null;
        String countryIso = null;
        String nationalNumber = null;
        if (params != null) {
            phone = params.getString(ExtraConstants.PHONE);
            countryIso = params.getString(ExtraConstants.COUNTRY_ISO);
            nationalNumber = params.getString(ExtraConstants.NATIONAL_NUMBER);
        }
        if (!TextUtils.isEmpty(phone)) {
            start(PhoneNumberUtils.getPhoneNumber(phone));
        } else if (!TextUtils.isEmpty(countryIso) && !TextUtils.isEmpty(nationalNumber)) {
            start(PhoneNumberUtils.getPhoneNumber(countryIso, nationalNumber));
        } else if (!TextUtils.isEmpty(countryIso)) {
            setCountryCode(new PhoneNumber("", countryIso, String.valueOf(PhoneNumberUtils.getCountryCode(countryIso))));
        } else if (getFlowParams().enableHints) {
            this.mCheckPhoneHandler.fetchCredential();
        }
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
