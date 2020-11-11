package com.google.firebase.inappmessaging.display.internal;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import com.google.firebase.inappmessaging.display.internal.SwipeDismissTouchListener;
import com.google.firebase.inappmessaging.display.internal.bindingwrappers.BindingWrapper;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FiamWindowManager {
    static final int DEFAULT_TYPE = 1003;
    private BindingWrapper bindingWrapper;

    @Inject
    FiamWindowManager() {
    }

    public void show(BindingWrapper bindingWrapper2, Activity activity) {
        if (isFiamDisplayed()) {
            Logging.loge("Fiam already active. Cannot show new Fiam.");
            return;
        }
        InAppMessageLayoutConfig config = bindingWrapper2.getConfig();
        WindowManager.LayoutParams layoutParams = getLayoutParams(config, activity);
        WindowManager windowManager = getWindowManager(activity);
        windowManager.addView(bindingWrapper2.getRootView(), layoutParams);
        Rect insetDimensions = getInsetDimensions(activity);
        Logging.logdPair("Inset (top, bottom)", (float) insetDimensions.top, (float) insetDimensions.bottom);
        Logging.logdPair("Inset (left, right)", (float) insetDimensions.left, (float) insetDimensions.right);
        if (bindingWrapper2.canSwipeToDismiss()) {
            bindingWrapper2.getDialogView().setOnTouchListener(getSwipeListener(config, bindingWrapper2, windowManager, layoutParams));
        }
        this.bindingWrapper = bindingWrapper2;
    }

    public boolean isFiamDisplayed() {
        BindingWrapper bindingWrapper2 = this.bindingWrapper;
        if (bindingWrapper2 == null) {
            return false;
        }
        return bindingWrapper2.getRootView().isShown();
    }

    public void destroy(Activity activity) {
        if (isFiamDisplayed()) {
            getWindowManager(activity).removeViewImmediate(this.bindingWrapper.getRootView());
            this.bindingWrapper = null;
        }
    }

    private WindowManager.LayoutParams getLayoutParams(InAppMessageLayoutConfig layoutConfig, Activity activity) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(layoutConfig.windowWidth().intValue(), layoutConfig.windowHeight().intValue(), 1003, layoutConfig.windowFlag().intValue(), -3);
        Rect insetDimensions = getInsetDimensions(activity);
        if ((layoutConfig.viewWindowGravity().intValue() & 48) == 48) {
            layoutParams.y = insetDimensions.top;
        }
        layoutParams.dimAmount = 0.3f;
        layoutParams.gravity = layoutConfig.viewWindowGravity().intValue();
        layoutParams.windowAnimations = 0;
        return layoutParams;
    }

    private WindowManager getWindowManager(Activity activity) {
        return (WindowManager) activity.getSystemService("window");
    }

    private Point getDisplaySize(Activity activity) {
        Point size = new Point();
        Display display = getWindowManager(activity).getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealSize(size);
        } else {
            display.getSize(size);
        }
        return size;
    }

    private Rect getInsetDimensions(Activity activity) {
        Rect padding = new Rect();
        Rect visibleFrame = getVisibleFrame(activity);
        Point size = getDisplaySize(activity);
        padding.top = visibleFrame.top;
        padding.left = visibleFrame.left;
        padding.right = size.x - visibleFrame.right;
        padding.bottom = size.y - visibleFrame.bottom;
        return padding;
    }

    private Rect getVisibleFrame(Activity activity) {
        Rect visibleFrame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(visibleFrame);
        return visibleFrame;
    }

    private SwipeDismissTouchListener getSwipeListener(InAppMessageLayoutConfig layoutConfig, final BindingWrapper bindingWrapper2, WindowManager windowManager, WindowManager.LayoutParams layoutParams) {
        SwipeDismissTouchListener.DismissCallbacks callbacks = new SwipeDismissTouchListener.DismissCallbacks() {
            public boolean canDismiss(Object token) {
                return true;
            }

            public void onDismiss(View view, Object token) {
                if (bindingWrapper2.getDismissListener() != null) {
                    bindingWrapper2.getDismissListener().onClick(view);
                }
            }
        };
        if (layoutConfig.windowWidth().intValue() == -1) {
            return new SwipeDismissTouchListener(bindingWrapper2.getDialogView(), (Object) null, callbacks);
        }
        final WindowManager.LayoutParams layoutParams2 = layoutParams;
        final WindowManager windowManager2 = windowManager;
        final BindingWrapper bindingWrapper3 = bindingWrapper2;
        return new SwipeDismissTouchListener(bindingWrapper2.getDialogView(), (Object) null, callbacks) {
            /* access modifiers changed from: protected */
            public float getTranslationX() {
                return (float) layoutParams2.x;
            }

            /* access modifiers changed from: protected */
            public void setTranslationX(float translationX) {
                layoutParams2.x = (int) translationX;
                windowManager2.updateViewLayout(bindingWrapper3.getRootView(), layoutParams2);
            }
        };
    }
}
