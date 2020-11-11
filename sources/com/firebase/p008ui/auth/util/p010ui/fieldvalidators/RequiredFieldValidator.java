package com.firebase.p008ui.auth.util.p010ui.fieldvalidators;

import com.firebase.p008ui.auth.C2354R;
import com.google.android.material.textfield.TextInputLayout;

/* renamed from: com.firebase.ui.auth.util.ui.fieldvalidators.RequiredFieldValidator */
public class RequiredFieldValidator extends BaseValidator {
    public RequiredFieldValidator(TextInputLayout errorContainer) {
        super(errorContainer);
        this.mErrorMessage = this.mErrorContainer.getResources().getString(C2354R.string.fui_required_field);
    }

    public RequiredFieldValidator(TextInputLayout errorContainer, String errorMessage) {
        super(errorContainer);
        this.mErrorMessage = errorMessage;
    }

    /* access modifiers changed from: protected */
    public boolean isValid(CharSequence charSequence) {
        return charSequence != null && charSequence.length() > 0;
    }
}
