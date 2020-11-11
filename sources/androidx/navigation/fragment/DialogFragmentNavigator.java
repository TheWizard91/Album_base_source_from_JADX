package androidx.navigation.fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.FloatingWindow;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigator;
import androidx.navigation.NavigatorProvider;

@Navigator.Name("dialog")
public final class DialogFragmentNavigator extends Navigator<Destination> {
    private static final String DIALOG_TAG = "androidx-nav-fragment:navigator:dialog:";
    private static final String KEY_DIALOG_COUNT = "androidx-nav-dialogfragment:navigator:count";
    private static final String TAG = "DialogFragmentNavigator";
    private final Context mContext;
    private int mDialogCount = 0;
    private final FragmentManager mFragmentManager;
    private LifecycleEventObserver mObserver = new LifecycleEventObserver() {
        public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
            if (event == Lifecycle.Event.ON_STOP) {
                DialogFragment dialogFragment = (DialogFragment) source;
                if (!dialogFragment.requireDialog().isShowing()) {
                    NavHostFragment.findNavController(dialogFragment).popBackStack();
                }
            }
        }
    };

    public DialogFragmentNavigator(Context context, FragmentManager manager) {
        this.mContext = context;
        this.mFragmentManager = manager;
    }

    public boolean popBackStack() {
        if (this.mDialogCount == 0) {
            return false;
        }
        if (this.mFragmentManager.isStateSaved()) {
            Log.i(TAG, "Ignoring popBackStack() call: FragmentManager has already saved its state");
            return false;
        }
        FragmentManager fragmentManager = this.mFragmentManager;
        StringBuilder append = new StringBuilder().append(DIALOG_TAG);
        int i = this.mDialogCount - 1;
        this.mDialogCount = i;
        Fragment existingFragment = fragmentManager.findFragmentByTag(append.append(i).toString());
        if (existingFragment != null) {
            existingFragment.getLifecycle().removeObserver(this.mObserver);
            ((DialogFragment) existingFragment).dismiss();
        }
        return true;
    }

    public Destination createDestination() {
        return new Destination((Navigator<? extends Destination>) this);
    }

    public NavDestination navigate(Destination destination, Bundle args, NavOptions navOptions, Navigator.Extras navigatorExtras) {
        if (this.mFragmentManager.isStateSaved()) {
            Log.i(TAG, "Ignoring navigate() call: FragmentManager has already saved its state");
            return null;
        }
        String className = destination.getClassName();
        if (className.charAt(0) == '.') {
            className = this.mContext.getPackageName() + className;
        }
        Fragment frag = this.mFragmentManager.getFragmentFactory().instantiate(this.mContext.getClassLoader(), className);
        if (DialogFragment.class.isAssignableFrom(frag.getClass())) {
            DialogFragment dialogFragment = (DialogFragment) frag;
            dialogFragment.setArguments(args);
            dialogFragment.getLifecycle().addObserver(this.mObserver);
            FragmentManager fragmentManager = this.mFragmentManager;
            StringBuilder append = new StringBuilder().append(DIALOG_TAG);
            int i = this.mDialogCount;
            this.mDialogCount = i + 1;
            dialogFragment.show(fragmentManager, append.append(i).toString());
            return destination;
        }
        throw new IllegalArgumentException("Dialog destination " + destination.getClassName() + " is not an instance of DialogFragment");
    }

    public Bundle onSaveState() {
        if (this.mDialogCount == 0) {
            return null;
        }
        Bundle b = new Bundle();
        b.putInt(KEY_DIALOG_COUNT, this.mDialogCount);
        return b;
    }

    public void onRestoreState(Bundle savedState) {
        if (savedState != null) {
            this.mDialogCount = savedState.getInt(KEY_DIALOG_COUNT, 0);
            int index = 0;
            while (index < this.mDialogCount) {
                DialogFragment fragment = (DialogFragment) this.mFragmentManager.findFragmentByTag(DIALOG_TAG + index);
                if (fragment != null) {
                    fragment.getLifecycle().addObserver(this.mObserver);
                    index++;
                } else {
                    throw new IllegalStateException("DialogFragment " + index + " doesn't exist in the FragmentManager");
                }
            }
        }
    }

    public static class Destination extends NavDestination implements FloatingWindow {
        private String mClassName;

        public Destination(NavigatorProvider navigatorProvider) {
            this((Navigator<? extends Destination>) navigatorProvider.getNavigator(DialogFragmentNavigator.class));
        }

        public Destination(Navigator<? extends Destination> fragmentNavigator) {
            super((Navigator<? extends NavDestination>) fragmentNavigator);
        }

        public void onInflate(Context context, AttributeSet attrs) {
            super.onInflate(context, attrs);
            TypedArray a = context.getResources().obtainAttributes(attrs, C2180R.styleable.DialogFragmentNavigator);
            String className = a.getString(C2180R.styleable.DialogFragmentNavigator_android_name);
            if (className != null) {
                setClassName(className);
            }
            a.recycle();
        }

        public final Destination setClassName(String className) {
            this.mClassName = className;
            return this;
        }

        public final String getClassName() {
            String str = this.mClassName;
            if (str != null) {
                return str;
            }
            throw new IllegalStateException("DialogFragment class was not set");
        }
    }
}
