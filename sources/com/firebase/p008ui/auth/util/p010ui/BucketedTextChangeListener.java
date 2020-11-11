package com.firebase.p008ui.auth.util.p010ui;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.util.Collections;

/* renamed from: com.firebase.ui.auth.util.ui.BucketedTextChangeListener */
public final class BucketedTextChangeListener implements TextWatcher {
    private final ContentChangeCallback mCallback;
    private final EditText mEditText;
    private final int mExpectedContentLength;
    private final String mPlaceHolder;
    private final String[] mPostFixes;

    /* renamed from: com.firebase.ui.auth.util.ui.BucketedTextChangeListener$ContentChangeCallback */
    public interface ContentChangeCallback {
        void whenComplete();

        void whileIncomplete();
    }

    public BucketedTextChangeListener(EditText editText, int expectedContentLength, String placeHolder, ContentChangeCallback callback) {
        this.mEditText = editText;
        this.mExpectedContentLength = expectedContentLength;
        this.mPostFixes = generatePostfixArray(placeHolder, expectedContentLength);
        this.mCallback = callback;
        this.mPlaceHolder = placeHolder;
    }

    private static String[] generatePostfixArray(CharSequence repeatableChar, int length) {
        String[] ret = new String[(length + 1)];
        for (int i = 0; i <= length; i++) {
            ret[i] = TextUtils.join("", Collections.nCopies(i, repeatableChar));
        }
        return ret;
    }

    public void onTextChanged(CharSequence s, int ignoredParam1, int ignoredParam2, int ignoredParam3) {
        ContentChangeCallback contentChangeCallback;
        String numericContents = s.toString().replaceAll(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR, "").replaceAll(this.mPlaceHolder, "");
        int enteredContentLength = Math.min(numericContents.length(), this.mExpectedContentLength);
        String enteredContent = numericContents.substring(0, enteredContentLength);
        this.mEditText.removeTextChangedListener(this);
        this.mEditText.setText(enteredContent + this.mPostFixes[this.mExpectedContentLength - enteredContentLength]);
        this.mEditText.setSelection(enteredContentLength);
        this.mEditText.addTextChangedListener(this);
        if (enteredContentLength != this.mExpectedContentLength || (contentChangeCallback = this.mCallback) == null) {
            ContentChangeCallback contentChangeCallback2 = this.mCallback;
            if (contentChangeCallback2 != null) {
                contentChangeCallback2.whileIncomplete();
                return;
            }
            return;
        }
        contentChangeCallback.whenComplete();
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void afterTextChanged(Editable s) {
    }
}
