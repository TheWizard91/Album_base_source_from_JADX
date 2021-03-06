package com.firebase.p008ui.auth.util.p010ui.fieldvalidators;

import android.util.Patterns;
import com.firebase.p008ui.auth.C2354R;
import com.google.android.material.textfield.TextInputLayout;

/* renamed from: com.firebase.ui.auth.util.ui.fieldvalidators.EmailFieldValidator */
public class EmailFieldValidator extends BaseValidator {
    public EmailFieldValidator(TextInputLayout errorContainer) {
        super(errorContainer);
        this.mErrorMessage = this.mErrorContainer.getResources().getString(C2354R.string.fui_invalid_email_address);
        this.mEmptyMessage = this.mErrorContainer.getResources().getString(C2354R.string.fui_missing_email_address);
    }

    /* access modifiers changed from: protected */
    public boolean isValid(CharSequence charSequence) {
        return Patterns.EMAIL_ADDRESS.matcher(charSequence).matches();
    }
}
