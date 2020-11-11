package com.facebook.common.activitylistener;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import com.facebook.common.internal.Preconditions;
import java.lang.ref.WeakReference;
import javax.annotation.Nullable;

public class ActivityListenerManager {
    public static void register(ActivityListener activityListener, Context context) {
        ListenableActivity activity = getListenableActivity(context);
        if (activity != null) {
            activity.addActivityListener(new Listener(activityListener));
        }
    }

    @Nullable
    public static ListenableActivity getListenableActivity(Context context) {
        if (!(context instanceof ListenableActivity) && (context instanceof ContextWrapper)) {
            context = ((ContextWrapper) context).getBaseContext();
        }
        if (context instanceof ListenableActivity) {
            return (ListenableActivity) context;
        }
        return null;
    }

    private static class Listener extends BaseActivityListener {
        private final WeakReference<ActivityListener> mActivityListenerRef;

        public Listener(ActivityListener activityListener) {
            this.mActivityListenerRef = new WeakReference<>(activityListener);
        }

        public void onActivityCreate(Activity activity) {
            ActivityListener activityVisibilityListener = getListenerOrCleanUp(activity);
            if (activityVisibilityListener != null) {
                activityVisibilityListener.onActivityCreate(activity);
            }
        }

        public void onDestroy(Activity activity) {
            ActivityListener activityVisibilityListener = getListenerOrCleanUp(activity);
            if (activityVisibilityListener != null) {
                activityVisibilityListener.onDestroy(activity);
            }
        }

        public void onStart(Activity activity) {
            ActivityListener activityVisibilityListener = getListenerOrCleanUp(activity);
            if (activityVisibilityListener != null) {
                activityVisibilityListener.onStart(activity);
            }
        }

        public void onStop(Activity activity) {
            ActivityListener activityVisibilityListener = getListenerOrCleanUp(activity);
            if (activityVisibilityListener != null) {
                activityVisibilityListener.onStop(activity);
            }
        }

        public void onResume(Activity activity) {
            ActivityListener activityVisibilityListener = getListenerOrCleanUp(activity);
            if (activityVisibilityListener != null) {
                activityVisibilityListener.onResume(activity);
            }
        }

        public void onPause(Activity activity) {
            ActivityListener activityVisibilityListener = getListenerOrCleanUp(activity);
            if (activityVisibilityListener != null) {
                activityVisibilityListener.onPause(activity);
            }
        }

        @Nullable
        private ActivityListener getListenerOrCleanUp(Activity activity) {
            ActivityListener activityVisibilityListener = (ActivityListener) this.mActivityListenerRef.get();
            if (activityVisibilityListener == null) {
                Preconditions.checkArgument(activity instanceof ListenableActivity);
                ((ListenableActivity) activity).removeActivityListener(this);
            }
            return activityVisibilityListener;
        }
    }
}
