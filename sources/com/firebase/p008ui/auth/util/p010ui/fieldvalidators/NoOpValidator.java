package com.firebase.p008ui.auth.util.p010ui.fieldvalidators;

import com.google.android.material.textfield.TextInputLayout;

/* renamed from: com.firebase.ui.auth.util.ui.fieldvalidators.NoOpValidator */
public class NoOpValidator extends BaseValidator {
    public NoOpValidator(TextInputLayout errorContainer) {
        super(errorContainer);
    }

    /* access modifiers changed from: protected */
    public boolean isValid(CharSequence charSequence) {
        return true;
    }
}
