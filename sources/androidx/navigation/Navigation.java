package androidx.navigation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import androidx.core.app.ActivityCompat;
import java.lang.ref.WeakReference;

public final class Navigation {
    private Navigation() {
    }

    public static NavController findNavController(Activity activity, int viewId) {
        NavController navController = findViewNavController(ActivityCompat.requireViewById(activity, viewId));
        if (navController != null) {
            return navController;
        }
        throw new IllegalStateException("Activity " + activity + " does not have a NavController set on " + viewId);
    }

    public static NavController findNavController(View view) {
        NavController navController = findViewNavController(view);
        if (navController != null) {
            return navController;
        }
        throw new IllegalStateException("View " + view + " does not have a NavController set");
    }

    public static View.OnClickListener createNavigateOnClickListener(int resId) {
        return createNavigateOnClickListener(resId, (Bundle) null);
    }

    public static View.OnClickListener createNavigateOnClickListener(final int resId, final Bundle args) {
        return new View.OnClickListener() {
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(resId, args);
            }
        };
    }

    public static View.OnClickListener createNavigateOnClickListener(final NavDirections directions) {
        return new View.OnClickListener() {
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(NavDirections.this);
            }
        };
    }

    public static void setViewNavController(View view, NavController controller) {
        view.setTag(C2150R.C2153id.nav_controller_view_tag, controller);
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [android.view.ViewParent] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static androidx.navigation.NavController findViewNavController(android.view.View r4) {
        /*
        L_0x0000:
            r0 = 0
            if (r4 == 0) goto L_0x0017
            androidx.navigation.NavController r1 = getViewNavController(r4)
            if (r1 == 0) goto L_0x000a
            return r1
        L_0x000a:
            android.view.ViewParent r2 = r4.getParent()
            boolean r3 = r2 instanceof android.view.View
            if (r3 == 0) goto L_0x0015
            r0 = r2
            android.view.View r0 = (android.view.View) r0
        L_0x0015:
            r4 = r0
            goto L_0x0000
        L_0x0017:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.Navigation.findViewNavController(android.view.View):androidx.navigation.NavController");
    }

    private static NavController getViewNavController(View view) {
        Object tag = view.getTag(C2150R.C2153id.nav_controller_view_tag);
        if (tag instanceof WeakReference) {
            return (NavController) ((WeakReference) tag).get();
        }
        if (tag instanceof NavController) {
            return (NavController) tag;
        }
        return null;
    }
}
