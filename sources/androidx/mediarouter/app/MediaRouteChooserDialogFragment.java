package androidx.mediarouter.app;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.mediarouter.media.MediaRouteSelector;

public class MediaRouteChooserDialogFragment extends DialogFragment {
    private static final String ARGUMENT_SELECTOR = "selector";
    private Dialog mDialog;
    private MediaRouteSelector mSelector;
    private boolean mUseDynamicGroup = false;

    public MediaRouteChooserDialogFragment() {
        setCancelable(true);
    }

    public MediaRouteSelector getRouteSelector() {
        ensureRouteSelector();
        return this.mSelector;
    }

    private void ensureRouteSelector() {
        if (this.mSelector == null) {
            Bundle args = getArguments();
            if (args != null) {
                this.mSelector = MediaRouteSelector.fromBundle(args.getBundle(ARGUMENT_SELECTOR));
            }
            if (this.mSelector == null) {
                this.mSelector = MediaRouteSelector.EMPTY;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setUseDynamicGroup(boolean useDynamicGroup) {
        if (this.mDialog == null) {
            this.mUseDynamicGroup = useDynamicGroup;
            return;
        }
        throw new IllegalStateException("This must be called before creating dialog");
    }

    public void setRouteSelector(MediaRouteSelector selector) {
        if (selector != null) {
            ensureRouteSelector();
            if (!this.mSelector.equals(selector)) {
                this.mSelector = selector;
                Bundle args = getArguments();
                if (args == null) {
                    args = new Bundle();
                }
                args.putBundle(ARGUMENT_SELECTOR, selector.asBundle());
                setArguments(args);
                Dialog dialog = this.mDialog;
                if (dialog == null) {
                    return;
                }
                if (this.mUseDynamicGroup) {
                    ((MediaRouteDynamicChooserDialog) dialog).setRouteSelector(selector);
                } else {
                    ((MediaRouteChooserDialog) dialog).setRouteSelector(selector);
                }
            }
        } else {
            throw new IllegalArgumentException("selector must not be null");
        }
    }

    public MediaRouteDynamicChooserDialog onCreateDynamicChooserDialog(Context context) {
        return new MediaRouteDynamicChooserDialog(context);
    }

    public MediaRouteChooserDialog onCreateChooserDialog(Context context, Bundle savedInstanceState) {
        return new MediaRouteChooserDialog(context);
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (this.mUseDynamicGroup) {
            MediaRouteDynamicChooserDialog onCreateDynamicChooserDialog = onCreateDynamicChooserDialog(getContext());
            this.mDialog = onCreateDynamicChooserDialog;
            onCreateDynamicChooserDialog.setRouteSelector(getRouteSelector());
        } else {
            MediaRouteChooserDialog onCreateChooserDialog = onCreateChooserDialog(getContext(), savedInstanceState);
            this.mDialog = onCreateChooserDialog;
            onCreateChooserDialog.setRouteSelector(getRouteSelector());
        }
        return this.mDialog;
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            if (this.mUseDynamicGroup) {
                ((MediaRouteDynamicChooserDialog) dialog).updateLayout();
            } else {
                ((MediaRouteChooserDialog) dialog).updateLayout();
            }
        }
    }
}
