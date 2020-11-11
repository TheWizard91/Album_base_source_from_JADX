package androidx.navigation;

import android.content.Context;
import androidx.activity.OnBackPressedDispatcher;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStore;

public class NavHostController extends NavController {
    public NavHostController(Context context) {
        super(context);
    }

    public final void setLifecycleOwner(LifecycleOwner owner) {
        super.setLifecycleOwner(owner);
    }

    public final void setOnBackPressedDispatcher(OnBackPressedDispatcher dispatcher) {
        super.setOnBackPressedDispatcher(dispatcher);
    }

    public final void enableOnBackPressed(boolean enabled) {
        super.enableOnBackPressed(enabled);
    }

    public final void setViewModelStore(ViewModelStore viewModelStore) {
        super.setViewModelStore(viewModelStore);
    }
}
