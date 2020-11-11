package com.firebase.p008ui.auth.p009ui;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.firebase.p008ui.auth.C2354R;

/* renamed from: com.firebase.ui.auth.ui.AppCompatBase */
public abstract class AppCompatBase extends HelperActivityBase {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(C2354R.C2360style.FirebaseUI);
        setTheme(getFlowParams().themeId);
    }

    /* access modifiers changed from: protected */
    public void switchFragment(Fragment fragment, int fragmentId, String tag, boolean withTransition, boolean addToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (withTransition) {
            ft.setCustomAnimations(C2354R.anim.fui_slide_in_right, C2354R.anim.fui_slide_out_left);
        }
        ft.replace(fragmentId, fragment, tag);
        if (addToBackStack) {
            ft.addToBackStack((String) null).commit();
        } else {
            ft.disallowAddToBackStack().commit();
        }
    }

    /* access modifiers changed from: protected */
    public void switchFragment(Fragment fragment, int fragmentId, String tag) {
        switchFragment(fragment, fragmentId, tag, false, false);
    }
}
