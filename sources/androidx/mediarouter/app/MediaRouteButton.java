package androidx.mediarouter.app;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.mediarouter.C2142R;
import androidx.mediarouter.media.MediaRouteSelector;
import androidx.mediarouter.media.MediaRouter;
import java.util.ArrayList;
import java.util.List;

public class MediaRouteButton extends View {
    private static final int[] CHECKABLE_STATE_SET = {16842911};
    private static final int[] CHECKED_STATE_SET = {16842912};
    private static final String CHOOSER_FRAGMENT_TAG = "android.support.v7.mediarouter:MediaRouteChooserDialogFragment";
    private static final int CONNECTION_STATE_CONNECTED = 2;
    private static final int CONNECTION_STATE_CONNECTING = 1;
    private static final int CONNECTION_STATE_DISCONNECTED = 0;
    private static final String CONTROLLER_FRAGMENT_TAG = "android.support.v7.mediarouter:MediaRouteControllerDialogFragment";
    private static final String TAG = "MediaRouteButton";
    private static ConnectivityReceiver sConnectivityReceiver;
    static final SparseArray<Drawable.ConstantState> sRemoteIndicatorCache = new SparseArray<>(2);
    private boolean mAlwaysVisible;
    private boolean mAttachedToWindow;
    private ColorStateList mButtonTint;
    private final MediaRouterCallback mCallback;
    private int mConnectionState;
    private MediaRouteDialogFactory mDialogFactory;
    private int mMinHeight;
    private int mMinWidth;
    private Drawable mRemoteIndicator;
    RemoteIndicatorLoader mRemoteIndicatorLoader;
    private int mRemoteIndicatorResIdToLoad;
    private final MediaRouter mRouter;
    private MediaRouteSelector mSelector;
    private boolean mUseDynamicGroup;
    private int mVisibility;

    public MediaRouteButton(Context context) {
        this(context, (AttributeSet) null);
    }

    public MediaRouteButton(Context context, AttributeSet attrs) {
        this(context, attrs, C2142R.attr.mediaRouteButtonStyle);
    }

    public MediaRouteButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(MediaRouterThemeHelper.createThemedButtonContext(context), attrs, defStyleAttr);
        Drawable.ConstantState remoteIndicatorState;
        this.mSelector = MediaRouteSelector.EMPTY;
        this.mDialogFactory = MediaRouteDialogFactory.getDefault();
        this.mVisibility = 0;
        Context context2 = getContext();
        TypedArray a = context2.obtainStyledAttributes(attrs, C2142R.styleable.MediaRouteButton, defStyleAttr, 0);
        if (isInEditMode()) {
            this.mRouter = null;
            this.mCallback = null;
            this.mRemoteIndicator = getResources().getDrawable(a.getResourceId(C2142R.styleable.MediaRouteButton_externalRouteEnabledDrawableStatic, 0));
            return;
        }
        this.mRouter = MediaRouter.getInstance(context2);
        this.mCallback = new MediaRouterCallback();
        if (sConnectivityReceiver == null) {
            sConnectivityReceiver = new ConnectivityReceiver(context2.getApplicationContext());
        }
        this.mButtonTint = a.getColorStateList(C2142R.styleable.MediaRouteButton_mediaRouteButtonTint);
        this.mMinWidth = a.getDimensionPixelSize(C2142R.styleable.MediaRouteButton_android_minWidth, 0);
        this.mMinHeight = a.getDimensionPixelSize(C2142R.styleable.MediaRouteButton_android_minHeight, 0);
        int remoteIndicatorStaticResId = a.getResourceId(C2142R.styleable.MediaRouteButton_externalRouteEnabledDrawableStatic, 0);
        this.mRemoteIndicatorResIdToLoad = a.getResourceId(C2142R.styleable.MediaRouteButton_externalRouteEnabledDrawable, 0);
        a.recycle();
        int i = this.mRemoteIndicatorResIdToLoad;
        if (!(i == 0 || (remoteIndicatorState = sRemoteIndicatorCache.get(i)) == null)) {
            setRemoteIndicatorDrawable(remoteIndicatorState.newDrawable());
        }
        if (this.mRemoteIndicator == null) {
            if (remoteIndicatorStaticResId != 0) {
                Drawable.ConstantState remoteIndicatorStaticState = sRemoteIndicatorCache.get(remoteIndicatorStaticResId);
                if (remoteIndicatorStaticState != null) {
                    setRemoteIndicatorDrawableInternal(remoteIndicatorStaticState.newDrawable());
                } else {
                    RemoteIndicatorLoader remoteIndicatorLoader = new RemoteIndicatorLoader(remoteIndicatorStaticResId, getContext());
                    this.mRemoteIndicatorLoader = remoteIndicatorLoader;
                    remoteIndicatorLoader.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, new Void[0]);
                }
            } else {
                loadRemoteIndicatorIfNeeded();
            }
        }
        updateContentDescription();
        setClickable(true);
    }

    public MediaRouteSelector getRouteSelector() {
        return this.mSelector;
    }

    public void setRouteSelector(MediaRouteSelector selector) {
        if (selector == null) {
            throw new IllegalArgumentException("selector must not be null");
        } else if (!this.mSelector.equals(selector)) {
            if (this.mAttachedToWindow) {
                if (!this.mSelector.isEmpty()) {
                    this.mRouter.removeCallback(this.mCallback);
                }
                if (!selector.isEmpty()) {
                    this.mRouter.addCallback(selector, this.mCallback);
                }
            }
            this.mSelector = selector;
            refreshRoute();
        }
    }

    public MediaRouteDialogFactory getDialogFactory() {
        return this.mDialogFactory;
    }

    public void setDialogFactory(MediaRouteDialogFactory factory) {
        if (factory != null) {
            this.mDialogFactory = factory;
            return;
        }
        throw new IllegalArgumentException("factory must not be null");
    }

    public void enableDynamicGroup() {
        this.mUseDynamicGroup = true;
    }

    public boolean showDialog() {
        if (!this.mAttachedToWindow) {
            return false;
        }
        FragmentManager fm = getFragmentManager();
        if (fm != null) {
            MediaRouter.RouteInfo route = this.mRouter.getSelectedRoute();
            if (route.isDefaultOrBluetooth() || !route.matchesSelector(this.mSelector)) {
                if (fm.findFragmentByTag(CHOOSER_FRAGMENT_TAG) != null) {
                    Log.w(TAG, "showDialog(): Route chooser dialog already showing!");
                    return false;
                }
                MediaRouteChooserDialogFragment f = this.mDialogFactory.onCreateChooserDialogFragment();
                f.setRouteSelector(this.mSelector);
                f.setUseDynamicGroup(this.mUseDynamicGroup);
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.add((Fragment) f, CHOOSER_FRAGMENT_TAG);
                transaction.commitAllowingStateLoss();
                return true;
            } else if (fm.findFragmentByTag(CONTROLLER_FRAGMENT_TAG) != null) {
                Log.w(TAG, "showDialog(): Route controller dialog already showing!");
                return false;
            } else {
                MediaRouteControllerDialogFragment f2 = this.mDialogFactory.onCreateControllerDialogFragment();
                f2.setRouteSelector(this.mSelector);
                f2.setUseDynamicGroup(this.mUseDynamicGroup);
                FragmentTransaction transaction2 = fm.beginTransaction();
                transaction2.add((Fragment) f2, CONTROLLER_FRAGMENT_TAG);
                transaction2.commitAllowingStateLoss();
                return true;
            }
        } else {
            throw new IllegalStateException("The activity must be a subclass of FragmentActivity");
        }
    }

    private FragmentManager getFragmentManager() {
        Activity activity = getActivity();
        if (activity instanceof FragmentActivity) {
            return ((FragmentActivity) activity).getSupportFragmentManager();
        }
        return null;
    }

    private Activity getActivity() {
        for (Context context = getContext(); context instanceof ContextWrapper; context = ((ContextWrapper) context).getBaseContext()) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void setCheatSheetEnabled(boolean enable) {
        TooltipCompat.setTooltipText(this, enable ? getContext().getString(C2142R.string.mr_button_content_description) : null);
    }

    public boolean performClick() {
        boolean handled = super.performClick();
        if (!handled) {
            playSoundEffect(0);
        }
        loadRemoteIndicatorIfNeeded();
        if (showDialog() || handled) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public int[] onCreateDrawableState(int extraSpace) {
        int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        int i = this.mConnectionState;
        if (i == 1) {
            mergeDrawableStates(drawableState, CHECKABLE_STATE_SET);
        } else if (i == 2) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mRemoteIndicator != null) {
            this.mRemoteIndicator.setState(getDrawableState());
            invalidate();
        }
    }

    public void setRemoteIndicatorDrawable(Drawable d) {
        this.mRemoteIndicatorResIdToLoad = 0;
        setRemoteIndicatorDrawableInternal(d);
    }

    public void setAlwaysVisible(boolean alwaysVisible) {
        if (alwaysVisible != this.mAlwaysVisible) {
            this.mAlwaysVisible = alwaysVisible;
            refreshVisibility();
            refreshRoute();
        }
    }

    /* access modifiers changed from: protected */
    public boolean verifyDrawable(Drawable who) {
        return super.verifyDrawable(who) || who == this.mRemoteIndicator;
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.mRemoteIndicator;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    public void setVisibility(int visibility) {
        this.mVisibility = visibility;
        refreshVisibility();
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            this.mAttachedToWindow = true;
            if (!this.mSelector.isEmpty()) {
                this.mRouter.addCallback(this.mSelector, this.mCallback);
            }
            refreshRoute();
            sConnectivityReceiver.registerReceiver(this);
        }
    }

    public void onDetachedFromWindow() {
        if (!isInEditMode()) {
            this.mAttachedToWindow = false;
            if (!this.mSelector.isEmpty()) {
                this.mRouter.removeCallback(this.mCallback);
            }
            sConnectivityReceiver.unregisterReceiver(this);
        }
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth;
        int measuredHeight;
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int i = this.mMinWidth;
        Drawable drawable = this.mRemoteIndicator;
        int i2 = 0;
        int width = Math.max(i, drawable != null ? drawable.getIntrinsicWidth() + getPaddingLeft() + getPaddingRight() : 0);
        int i3 = this.mMinHeight;
        Drawable drawable2 = this.mRemoteIndicator;
        if (drawable2 != null) {
            i2 = drawable2.getIntrinsicHeight() + getPaddingTop() + getPaddingBottom();
        }
        int height = Math.max(i3, i2);
        if (widthMode == Integer.MIN_VALUE) {
            measuredWidth = Math.min(widthSize, width);
        } else if (widthMode != 1073741824) {
            measuredWidth = width;
        } else {
            measuredWidth = widthSize;
        }
        if (heightMode == Integer.MIN_VALUE) {
            measuredHeight = Math.min(heightSize, height);
        } else if (heightMode != 1073741824) {
            measuredHeight = height;
        } else {
            measuredHeight = heightSize;
        }
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mRemoteIndicator != null) {
            int left = getPaddingLeft();
            int right = getWidth() - getPaddingRight();
            int top = getPaddingTop();
            int bottom = getHeight() - getPaddingBottom();
            int drawWidth = this.mRemoteIndicator.getIntrinsicWidth();
            int drawHeight = this.mRemoteIndicator.getIntrinsicHeight();
            int drawLeft = (((right - left) - drawWidth) / 2) + left;
            int drawTop = (((bottom - top) - drawHeight) / 2) + top;
            this.mRemoteIndicator.setBounds(drawLeft, drawTop, drawLeft + drawWidth, drawTop + drawHeight);
            this.mRemoteIndicator.draw(canvas);
        }
    }

    private void loadRemoteIndicatorIfNeeded() {
        if (this.mRemoteIndicatorResIdToLoad > 0) {
            RemoteIndicatorLoader remoteIndicatorLoader = this.mRemoteIndicatorLoader;
            if (remoteIndicatorLoader != null) {
                remoteIndicatorLoader.cancel(false);
            }
            RemoteIndicatorLoader remoteIndicatorLoader2 = new RemoteIndicatorLoader(this.mRemoteIndicatorResIdToLoad, getContext());
            this.mRemoteIndicatorLoader = remoteIndicatorLoader2;
            this.mRemoteIndicatorResIdToLoad = 0;
            remoteIndicatorLoader2.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, new Void[0]);
        }
    }

    /* access modifiers changed from: package-private */
    public void setRemoteIndicatorDrawableInternal(Drawable d) {
        Drawable drawable;
        RemoteIndicatorLoader remoteIndicatorLoader = this.mRemoteIndicatorLoader;
        if (remoteIndicatorLoader != null) {
            remoteIndicatorLoader.cancel(false);
        }
        Drawable drawable2 = this.mRemoteIndicator;
        if (drawable2 != null) {
            drawable2.setCallback((Drawable.Callback) null);
            unscheduleDrawable(this.mRemoteIndicator);
        }
        if (d != null) {
            if (this.mButtonTint != null) {
                d = DrawableCompat.wrap(d.mutate());
                DrawableCompat.setTintList(d, this.mButtonTint);
            }
            d.setCallback(this);
            d.setState(getDrawableState());
            d.setVisible(getVisibility() == 0, false);
        }
        this.mRemoteIndicator = d;
        refreshDrawableState();
        if (this.mAttachedToWindow && (drawable = this.mRemoteIndicator) != null && (drawable.getCurrent() instanceof AnimationDrawable)) {
            AnimationDrawable curDrawable = (AnimationDrawable) this.mRemoteIndicator.getCurrent();
            int i = this.mConnectionState;
            if (i == 1) {
                if (!curDrawable.isRunning()) {
                    curDrawable.start();
                }
            } else if (i == 2) {
                if (curDrawable.isRunning()) {
                    curDrawable.stop();
                }
                curDrawable.selectDrawable(curDrawable.getNumberOfFrames() - 1);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void refreshVisibility() {
        super.setVisibility((this.mVisibility != 0 || this.mAlwaysVisible || sConnectivityReceiver.isConnected()) ? this.mVisibility : 4);
        Drawable drawable = this.mRemoteIndicator;
        if (drawable != null) {
            drawable.setVisible(getVisibility() == 0, false);
        }
    }

    /* access modifiers changed from: package-private */
    public void refreshRoute() {
        MediaRouter.RouteInfo route = this.mRouter.getSelectedRoute();
        boolean z = false;
        int connectionState = !route.isDefaultOrBluetooth() && route.matchesSelector(this.mSelector) ? route.getConnectionState() : 0;
        boolean needsRefresh = false;
        if (this.mConnectionState != connectionState) {
            this.mConnectionState = connectionState;
            needsRefresh = true;
        }
        if (needsRefresh) {
            updateContentDescription();
            refreshDrawableState();
        }
        if (connectionState == 1) {
            loadRemoteIndicatorIfNeeded();
        }
        if (this.mAttachedToWindow) {
            if (this.mAlwaysVisible || this.mRouter.isRouteAvailable(this.mSelector, 1)) {
                z = true;
            }
            setEnabled(z);
        }
        Drawable drawable = this.mRemoteIndicator;
        if (drawable != null && (drawable.getCurrent() instanceof AnimationDrawable)) {
            AnimationDrawable curDrawable = (AnimationDrawable) this.mRemoteIndicator.getCurrent();
            if (this.mAttachedToWindow) {
                if ((needsRefresh || connectionState == 1) && !curDrawable.isRunning()) {
                    curDrawable.start();
                }
            } else if (connectionState == 2) {
                if (curDrawable.isRunning()) {
                    curDrawable.stop();
                }
                curDrawable.selectDrawable(curDrawable.getNumberOfFrames() - 1);
            }
        }
    }

    private void updateContentDescription() {
        int resId;
        int i = this.mConnectionState;
        if (i == 1) {
            resId = C2142R.string.mr_cast_button_connecting;
        } else if (i != 2) {
            resId = C2142R.string.mr_cast_button_disconnected;
        } else {
            resId = C2142R.string.mr_cast_button_connected;
        }
        setContentDescription(getContext().getString(resId));
    }

    private final class MediaRouterCallback extends MediaRouter.Callback {
        MediaRouterCallback() {
        }

        public void onRouteAdded(MediaRouter router, MediaRouter.RouteInfo info) {
            MediaRouteButton.this.refreshRoute();
        }

        public void onRouteRemoved(MediaRouter router, MediaRouter.RouteInfo info) {
            MediaRouteButton.this.refreshRoute();
        }

        public void onRouteChanged(MediaRouter router, MediaRouter.RouteInfo info) {
            MediaRouteButton.this.refreshRoute();
        }

        public void onRouteSelected(MediaRouter router, MediaRouter.RouteInfo info) {
            MediaRouteButton.this.refreshRoute();
        }

        public void onRouteUnselected(MediaRouter router, MediaRouter.RouteInfo info) {
            MediaRouteButton.this.refreshRoute();
        }

        public void onProviderAdded(MediaRouter router, MediaRouter.ProviderInfo provider) {
            MediaRouteButton.this.refreshRoute();
        }

        public void onProviderRemoved(MediaRouter router, MediaRouter.ProviderInfo provider) {
            MediaRouteButton.this.refreshRoute();
        }

        public void onProviderChanged(MediaRouter router, MediaRouter.ProviderInfo provider) {
            MediaRouteButton.this.refreshRoute();
        }
    }

    private final class RemoteIndicatorLoader extends AsyncTask<Void, Void, Drawable> {
        private final Context mContext;
        private final int mResId;

        RemoteIndicatorLoader(int resId, Context context) {
            this.mResId = resId;
            this.mContext = context;
        }

        /* access modifiers changed from: protected */
        public Drawable doInBackground(Void... params) {
            if (MediaRouteButton.sRemoteIndicatorCache.get(this.mResId) == null) {
                return this.mContext.getResources().getDrawable(this.mResId);
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Drawable remoteIndicator) {
            if (remoteIndicator != null) {
                cacheAndReset(remoteIndicator);
            } else {
                Drawable.ConstantState remoteIndicatorState = MediaRouteButton.sRemoteIndicatorCache.get(this.mResId);
                if (remoteIndicatorState != null) {
                    remoteIndicator = remoteIndicatorState.newDrawable();
                }
                MediaRouteButton.this.mRemoteIndicatorLoader = null;
            }
            MediaRouteButton.this.setRemoteIndicatorDrawableInternal(remoteIndicator);
        }

        /* access modifiers changed from: protected */
        public void onCancelled(Drawable remoteIndicator) {
            cacheAndReset(remoteIndicator);
        }

        private void cacheAndReset(Drawable remoteIndicator) {
            if (remoteIndicator != null) {
                MediaRouteButton.sRemoteIndicatorCache.put(this.mResId, remoteIndicator.getConstantState());
            }
            MediaRouteButton.this.mRemoteIndicatorLoader = null;
        }
    }

    private static final class ConnectivityReceiver extends BroadcastReceiver {
        private List<MediaRouteButton> mButtons;
        private final Context mContext;
        private boolean mIsConnected = true;

        ConnectivityReceiver(Context context) {
            this.mContext = context;
            this.mButtons = new ArrayList();
        }

        public void registerReceiver(MediaRouteButton button) {
            if (this.mButtons.size() == 0) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                this.mContext.registerReceiver(this, intentFilter);
            }
            this.mButtons.add(button);
        }

        public void unregisterReceiver(MediaRouteButton button) {
            this.mButtons.remove(button);
            if (this.mButtons.size() == 0) {
                this.mContext.unregisterReceiver(this);
            }
        }

        public boolean isConnected() {
            return this.mIsConnected;
        }

        public void onReceive(Context context, Intent intent) {
            boolean isConnected;
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction()) && this.mIsConnected != (!intent.getBooleanExtra("noConnectivity", false))) {
                this.mIsConnected = isConnected;
                for (MediaRouteButton button : this.mButtons) {
                    button.refreshVisibility();
                }
            }
        }
    }
}
