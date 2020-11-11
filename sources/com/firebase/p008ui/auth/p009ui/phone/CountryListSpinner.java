package com.firebase.p008ui.auth.p009ui.phone;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import androidx.appcompat.widget.AppCompatEditText;
import com.firebase.p008ui.auth.data.model.CountryInfo;
import com.firebase.p008ui.auth.util.ExtraConstants;
import com.firebase.p008ui.auth.util.data.PhoneNumberUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/* renamed from: com.firebase.ui.auth.ui.phone.CountryListSpinner */
public final class CountryListSpinner extends AppCompatEditText implements View.OnClickListener {
    private static final String KEY_COUNTRY_INFO = "KEY_COUNTRY_INFO";
    private static final String KEY_SUPER_STATE = "KEY_SUPER_STATE";
    private Set<String> mBlacklistedCountryIsos;
    private final CountryListAdapter mCountryListAdapter;
    private final DialogPopup mDialogPopup;
    private View.OnClickListener mListener;
    private CountryInfo mSelectedCountryInfo;
    /* access modifiers changed from: private */
    public String mSelectedCountryName;
    private final String mTextFormat;
    private Set<String> mWhitelistedCountryIsos;

    public CountryListSpinner(Context context) {
        this(context, (AttributeSet) null, 16842881);
    }

    public CountryListSpinner(Context context, AttributeSet attrs) {
        this(context, attrs, 16842881);
    }

    public CountryListSpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mWhitelistedCountryIsos = new HashSet();
        this.mBlacklistedCountryIsos = new HashSet();
        super.setOnClickListener(this);
        CountryListAdapter countryListAdapter = new CountryListAdapter(getContext());
        this.mCountryListAdapter = countryListAdapter;
        this.mDialogPopup = new DialogPopup(countryListAdapter);
        this.mTextFormat = "%1$s  +%2$d";
        this.mSelectedCountryName = "";
    }

    public void init(Bundle params) {
        if (params != null) {
            List<CountryInfo> countries = getCountriesToDisplayInSpinner(params);
            setCountriesToDisplay(countries);
            setDefaultCountryForSpinner(countries);
        }
    }

    private List<CountryInfo> getCountriesToDisplayInSpinner(Bundle params) {
        initCountrySpinnerIsosFromParams(params);
        Map<String, Integer> countryInfoMap = PhoneNumberUtils.getImmutableCountryIsoMap();
        if (this.mWhitelistedCountryIsos.isEmpty() && this.mBlacklistedCountryIsos.isEmpty()) {
            this.mWhitelistedCountryIsos = new HashSet(countryInfoMap.keySet());
        }
        List<CountryInfo> countryInfoList = new ArrayList<>();
        Set<String> excludedCountries = new HashSet<>();
        if (!this.mBlacklistedCountryIsos.isEmpty()) {
            excludedCountries.addAll(this.mBlacklistedCountryIsos);
        } else {
            excludedCountries.addAll(countryInfoMap.keySet());
            excludedCountries.removeAll(this.mWhitelistedCountryIsos);
        }
        for (String countryIso : countryInfoMap.keySet()) {
            if (!excludedCountries.contains(countryIso)) {
                countryInfoList.add(new CountryInfo(new Locale("", countryIso), countryInfoMap.get(countryIso).intValue()));
            }
        }
        Collections.sort(countryInfoList);
        return countryInfoList;
    }

    private void initCountrySpinnerIsosFromParams(Bundle params) {
        List<String> whitelistedCountries = params.getStringArrayList(ExtraConstants.WHITELISTED_COUNTRIES);
        List<String> blacklistedCountries = params.getStringArrayList(ExtraConstants.BLACKLISTED_COUNTRIES);
        if (whitelistedCountries != null) {
            this.mWhitelistedCountryIsos = convertCodesToIsos(whitelistedCountries);
        }
        if (blacklistedCountries != null) {
            this.mBlacklistedCountryIsos = convertCodesToIsos(blacklistedCountries);
        }
    }

    private Set<String> convertCodesToIsos(List<String> codes) {
        Set<String> isos = new HashSet<>();
        for (String code : codes) {
            if (PhoneNumberUtils.isValid(code)) {
                isos.addAll(PhoneNumberUtils.getCountryIsosFromCountryCode(code));
            } else {
                isos.add(code);
            }
        }
        return isos;
    }

    public void setCountriesToDisplay(List<CountryInfo> countries) {
        this.mCountryListAdapter.setData(countries);
    }

    private void setDefaultCountryForSpinner(List<CountryInfo> countries) {
        CountryInfo countryInfo = PhoneNumberUtils.getCurrentCountryInfo(getContext());
        if (isValidIso(countryInfo.getLocale().getCountry())) {
            setSelectedForCountry(countryInfo.getCountryCode(), countryInfo.getLocale());
        } else if (countries.iterator().hasNext()) {
            CountryInfo countryInfo2 = countries.iterator().next();
            setSelectedForCountry(countryInfo2.getCountryCode(), countryInfo2.getLocale());
        }
    }

    public boolean isValidIso(String iso) {
        String iso2 = iso.toUpperCase(Locale.getDefault());
        boolean valid = true;
        boolean valid2 = true;
        if (!this.mWhitelistedCountryIsos.isEmpty()) {
            valid = 1 != 0 && this.mWhitelistedCountryIsos.contains(iso2);
        }
        if (this.mBlacklistedCountryIsos.isEmpty()) {
            return valid;
        }
        if (!valid || this.mBlacklistedCountryIsos.contains(iso2)) {
            valid2 = false;
        }
        return valid2;
    }

    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_SUPER_STATE, superState);
        bundle.putParcelable(KEY_COUNTRY_INFO, this.mSelectedCountryInfo);
        return bundle;
    }

    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof Bundle)) {
            super.onRestoreInstanceState(state);
            return;
        }
        Bundle bundle = (Bundle) state;
        Parcelable superState = bundle.getParcelable(KEY_SUPER_STATE);
        this.mSelectedCountryInfo = (CountryInfo) bundle.getParcelable(KEY_COUNTRY_INFO);
        super.onRestoreInstanceState(superState);
    }

    private static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService("input_method");
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void setSelectedForCountry(int countryCode, Locale locale) {
        setText(String.format(this.mTextFormat, new Object[]{CountryInfo.localeToEmoji(locale), Integer.valueOf(countryCode)}));
        this.mSelectedCountryInfo = new CountryInfo(locale, countryCode);
    }

    public void setSelectedForCountry(Locale locale, String countryCode) {
        if (isValidIso(locale.getCountry())) {
            String countryName = locale.getDisplayName();
            if (!TextUtils.isEmpty(countryName) && !TextUtils.isEmpty(countryCode)) {
                this.mSelectedCountryName = countryName;
                setSelectedForCountry(Integer.parseInt(countryCode), locale);
            }
        }
    }

    public CountryInfo getSelectedCountryInfo() {
        return this.mSelectedCountryInfo;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mDialogPopup.isShowing()) {
            this.mDialogPopup.dismiss();
        }
    }

    public void setOnClickListener(View.OnClickListener l) {
        this.mListener = l;
    }

    public void onClick(View view) {
        this.mDialogPopup.show(this.mCountryListAdapter.getPositionForCountry(this.mSelectedCountryName));
        hideKeyboard(getContext(), this);
        executeUserClickListener(view);
    }

    private void executeUserClickListener(View view) {
        View.OnClickListener onClickListener = this.mListener;
        if (onClickListener != null) {
            onClickListener.onClick(view);
        }
    }

    /* renamed from: com.firebase.ui.auth.ui.phone.CountryListSpinner$DialogPopup */
    public class DialogPopup implements DialogInterface.OnClickListener {
        private static final long DELAY_MILLIS = 10;
        private AlertDialog dialog;
        private final CountryListAdapter listAdapter;

        DialogPopup(CountryListAdapter adapter) {
            this.listAdapter = adapter;
        }

        public void dismiss() {
            AlertDialog alertDialog = this.dialog;
            if (alertDialog != null) {
                alertDialog.dismiss();
                this.dialog = null;
            }
        }

        public boolean isShowing() {
            AlertDialog alertDialog = this.dialog;
            return alertDialog != null && alertDialog.isShowing();
        }

        public void show(final int selected) {
            if (this.listAdapter != null) {
                AlertDialog create = new AlertDialog.Builder(CountryListSpinner.this.getContext()).setSingleChoiceItems(this.listAdapter, 0, this).create();
                this.dialog = create;
                create.setCanceledOnTouchOutside(true);
                final ListView listView = this.dialog.getListView();
                listView.setFastScrollEnabled(true);
                listView.setScrollbarFadingEnabled(false);
                listView.postDelayed(new Runnable() {
                    public void run() {
                        listView.setSelection(selected);
                    }
                }, DELAY_MILLIS);
                this.dialog.show();
            }
        }

        public void onClick(DialogInterface dialog2, int which) {
            CountryInfo countryInfo = (CountryInfo) this.listAdapter.getItem(which);
            String unused = CountryListSpinner.this.mSelectedCountryName = countryInfo.getLocale().getDisplayCountry();
            CountryListSpinner.this.setSelectedForCountry(countryInfo.getCountryCode(), countryInfo.getLocale());
            dismiss();
        }
    }
}
