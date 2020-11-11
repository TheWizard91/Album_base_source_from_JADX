package com.firebase.p008ui.auth.p009ui;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.firebase.p008ui.auth.IdpResponse;
import com.firebase.p008ui.auth.data.model.FlowParameters;
import com.google.firebase.auth.FirebaseUser;

/* renamed from: com.firebase.ui.auth.ui.FragmentBase */
public abstract class FragmentBase extends Fragment implements ProgressView {
    private HelperActivityBase mActivity;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity instanceof HelperActivityBase) {
            this.mActivity = (HelperActivityBase) activity;
            return;
        }
        throw new IllegalStateException("Cannot use this fragment without the helper activity");
    }

    public FlowParameters getFlowParams() {
        return this.mActivity.getFlowParams();
    }

    public void startSaveCredentials(FirebaseUser firebaseUser, IdpResponse response, String password) {
        this.mActivity.startSaveCredentials(firebaseUser, response, password);
    }
}
