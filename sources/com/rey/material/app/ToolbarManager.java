package com.rey.material.app;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import com.rey.material.app.ThemeManager;
import com.rey.material.drawable.NavigationDrawerDrawable;
import com.rey.material.drawable.ToolbarRippleDrawable;
import com.rey.material.util.ViewUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ToolbarManager {
    private ArrayList<Animation> mAnimations;
    private Animator mAnimator;
    /* access modifiers changed from: private */
    public AppCompatDelegate mAppCompatDelegate;
    private ToolbarRippleDrawable.Builder mBuilder;
    private int mCurrentGroup;
    private boolean mGroupChanged;
    private ArrayList<OnToolbarGroupChangedListener> mListeners;
    private boolean mMenuDataChanged;
    private ActionMenuView mMenuView;
    private NavigationManager mNavigationManager;
    private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener;
    private Animation.AnimationListener mOutAnimationEndListener;
    private int mRippleStyle;
    private Toolbar mToolbar;

    public interface Animator {
        Animation getInAnimation(View view, int i);

        Animation getOutAnimation(View view, int i);
    }

    public interface OnToolbarGroupChangedListener {
        void onToolbarGroupChanged(int i, int i2);
    }

    public ToolbarManager(AppCompatDelegate delegate, Toolbar toolbar, int defaultGroupId, int rippleStyle, int animIn, int animOut) {
        this(delegate, toolbar, defaultGroupId, rippleStyle, new SimpleAnimator(animIn, animOut));
    }

    public ToolbarManager(AppCompatDelegate delegate, Toolbar toolbar, int defaultGroupId, int rippleStyle, Animator animator) {
        this.mCurrentGroup = 0;
        this.mGroupChanged = false;
        this.mMenuDataChanged = true;
        this.mListeners = new ArrayList<>();
        this.mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                ToolbarManager.this.onGlobalLayout();
            }
        };
        this.mAnimations = new ArrayList<>();
        this.mOutAnimationEndListener = new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                if (ToolbarManager.this.mAppCompatDelegate != null) {
                    ToolbarManager.this.mAppCompatDelegate.invalidateOptionsMenu();
                } else {
                    ToolbarManager.this.onPrepareMenu();
                }
            }

            public void onAnimationRepeat(Animation animation) {
            }
        };
        this.mAppCompatDelegate = delegate;
        this.mToolbar = toolbar;
        this.mCurrentGroup = defaultGroupId;
        this.mRippleStyle = rippleStyle;
        this.mAnimator = animator;
        delegate.setSupportActionBar(toolbar);
    }

    public void registerOnToolbarGroupChangedListener(OnToolbarGroupChangedListener listener) {
        if (!this.mListeners.contains(listener)) {
            this.mListeners.add(listener);
        }
    }

    public void unregisterOnToolbarGroupChangedListener(OnToolbarGroupChangedListener listener) {
        this.mListeners.remove(listener);
    }

    private void dispatchOnToolbarGroupChanged(int oldGroupId, int groupId) {
        Iterator<OnToolbarGroupChangedListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onToolbarGroupChanged(oldGroupId, groupId);
        }
    }

    public int getCurrentGroup() {
        return this.mCurrentGroup;
    }

    public void setCurrentGroup(int groupId) {
        if (this.mCurrentGroup != groupId) {
            int oldGroupId = this.mCurrentGroup;
            this.mCurrentGroup = groupId;
            this.mGroupChanged = true;
            dispatchOnToolbarGroupChanged(oldGroupId, groupId);
            animateOut();
        }
    }

    public void createMenu(int menuId) {
        this.mToolbar.inflateMenu(menuId);
        this.mMenuDataChanged = true;
        if (this.mAppCompatDelegate == null) {
            onPrepareMenu();
        }
    }

    public void onPrepareMenu() {
        if (this.mGroupChanged || this.mMenuDataChanged) {
            this.mToolbar.getViewTreeObserver().addOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
            Menu menu = this.mToolbar.getMenu();
            int i = 0;
            int count = menu.size();
            while (true) {
                boolean z = false;
                if (i < count) {
                    MenuItem item = menu.getItem(i);
                    if (item.getGroupId() == this.mCurrentGroup || item.getGroupId() == 0) {
                        z = true;
                    }
                    item.setVisible(z);
                    i++;
                } else {
                    this.mMenuDataChanged = false;
                    return;
                }
            }
        }
    }

    public void setNavigationManager(NavigationManager navigationManager) {
        this.mNavigationManager = navigationManager;
        notifyNavigationStateInvalidated();
    }

    public void notifyNavigationStateInvalidated() {
        NavigationManager navigationManager = this.mNavigationManager;
        if (navigationManager != null) {
            navigationManager.notifyStateInvalidated();
        }
    }

    public void notifyNavigationStateChanged() {
        NavigationManager navigationManager = this.mNavigationManager;
        if (navigationManager != null) {
            navigationManager.notifyStateChanged();
        }
    }

    public void notifyNavigationStateProgressChanged(boolean isBackState, float progress) {
        NavigationManager navigationManager = this.mNavigationManager;
        if (navigationManager != null) {
            navigationManager.notifyStateProgressChanged(isBackState, progress);
        }
    }

    public boolean isNavigationBackState() {
        NavigationManager navigationManager = this.mNavigationManager;
        return navigationManager != null && navigationManager.isBackState();
    }

    public boolean isNavigationVisisble() {
        NavigationManager navigationManager = this.mNavigationManager;
        return navigationManager != null && navigationManager.isNavigationVisible();
    }

    public void setNavigationVisisble(boolean visible, boolean animation) {
        NavigationManager navigationManager = this.mNavigationManager;
        if (navigationManager != null) {
            navigationManager.setNavigationVisible(visible, animation);
        }
    }

    private ToolbarRippleDrawable getBackground() {
        if (this.mBuilder == null) {
            this.mBuilder = new ToolbarRippleDrawable.Builder(this.mToolbar.getContext(), this.mRippleStyle);
        }
        return this.mBuilder.build();
    }

    private ActionMenuView getMenuView() {
        if (this.mMenuView == null) {
            int i = 0;
            while (true) {
                if (i >= this.mToolbar.getChildCount()) {
                    break;
                }
                View child = this.mToolbar.getChildAt(i);
                if (child instanceof ActionMenuView) {
                    this.mMenuView = (ActionMenuView) child;
                    break;
                }
                i++;
            }
        }
        return this.mMenuView;
    }

    /* access modifiers changed from: private */
    public void onGlobalLayout() {
        if (Build.VERSION.SDK_INT >= 16) {
            this.mToolbar.getViewTreeObserver().removeOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
        } else {
            this.mToolbar.getViewTreeObserver().removeGlobalOnLayoutListener(this.mOnGlobalLayoutListener);
        }
        ActionMenuView menuView = getMenuView();
        int count = menuView == null ? 0 : menuView.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = menuView.getChildAt(i);
            if (this.mRippleStyle != 0 && (child.getBackground() == null || !(child.getBackground() instanceof ToolbarRippleDrawable))) {
                ViewUtil.setBackground(child, getBackground());
            }
        }
        if (this.mGroupChanged != 0) {
            animateIn();
            this.mGroupChanged = false;
        }
    }

    private void animateOut() {
        ActionMenuView menuView = getMenuView();
        int count = menuView == null ? 0 : menuView.getChildCount();
        Animation slowestAnimation = null;
        this.mAnimations.clear();
        this.mAnimations.ensureCapacity(count);
        for (int i = 0; i < count; i++) {
            Animation anim = this.mAnimator.getOutAnimation(menuView.getChildAt(i), i);
            this.mAnimations.add(anim);
            if (anim != null && (slowestAnimation == null || slowestAnimation.getStartOffset() + slowestAnimation.getDuration() < anim.getStartOffset() + anim.getDuration())) {
                slowestAnimation = anim;
            }
        }
        if (slowestAnimation == null) {
            this.mOutAnimationEndListener.onAnimationEnd((Animation) null);
        } else {
            slowestAnimation.setAnimationListener(this.mOutAnimationEndListener);
            for (int i2 = 0; i2 < count; i2++) {
                Animation anim2 = this.mAnimations.get(i2);
                if (anim2 != null) {
                    menuView.getChildAt(i2).startAnimation(anim2);
                }
            }
        }
        this.mAnimations.clear();
    }

    private void animateIn() {
        ActionMenuView menuView = getMenuView();
        int count = menuView == null ? 0 : menuView.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = menuView.getChildAt(i);
            Animation anim = this.mAnimator.getInAnimation(child, i);
            if (anim != null) {
                child.startAnimation(anim);
            }
        }
    }

    private static class SimpleAnimator implements Animator {
        private int mAnimationIn;
        private int mAnimationOut;

        public SimpleAnimator(int animIn, int animOut) {
            this.mAnimationIn = animIn;
            this.mAnimationOut = animOut;
        }

        public Animation getOutAnimation(View v, int position) {
            if (this.mAnimationOut == 0) {
                return null;
            }
            return AnimationUtils.loadAnimation(v.getContext(), this.mAnimationOut);
        }

        public Animation getInAnimation(View v, int position) {
            if (this.mAnimationIn == 0) {
                return null;
            }
            return AnimationUtils.loadAnimation(v.getContext(), this.mAnimationIn);
        }
    }

    public static abstract class NavigationManager {
        /* access modifiers changed from: private */
        public long mAnimTime;
        protected long mAnimationDuration;
        /* access modifiers changed from: private */
        public List<Object> mAnimations = new ArrayList();
        protected NavigationDrawerDrawable mNavigationIcon;
        protected boolean mNavigationVisible = true;
        protected Toolbar mToolbar;

        public abstract boolean isBackState();

        public abstract void onNavigationClick();

        public NavigationManager(NavigationDrawerDrawable navigationIcon, Toolbar toolbar) {
            this.mToolbar = toolbar;
            this.mNavigationIcon = navigationIcon;
            toolbar.setNavigationIcon((Drawable) this.mNavigationVisible ? navigationIcon : null);
            this.mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    NavigationManager.this.onNavigationClick();
                }
            });
            this.mAnimationDuration = (long) toolbar.getResources().getInteger(17694720);
        }

        public void notifyStateInvalidated() {
            this.mNavigationIcon.switchIconState(isBackState() ? 1 : 0, false);
        }

        public void notifyStateChanged() {
            NavigationDrawerDrawable navigationDrawerDrawable = this.mNavigationIcon;
            boolean isBackState = isBackState();
            navigationDrawerDrawable.switchIconState(isBackState ? 1 : 0, this.mNavigationVisible);
        }

        public void notifyStateProgressChanged(boolean isBackState, float progress) {
            this.mNavigationIcon.setIconState(isBackState, progress);
        }

        public boolean isNavigationVisible() {
            return this.mNavigationVisible;
        }

        public void setNavigationVisible(boolean visible, boolean animation) {
            if (this.mNavigationVisible != visible) {
                this.mNavigationVisible = visible;
                long time = SystemClock.uptimeMillis();
                if (!animation) {
                    this.mToolbar.setNavigationIcon((Drawable) this.mNavigationVisible ? this.mNavigationIcon : null);
                    this.mAnimTime = time;
                    if (!this.mNavigationVisible) {
                        this.mNavigationIcon.cancel();
                    }
                } else if (this.mNavigationVisible) {
                    animateNavigationIn(time);
                } else {
                    animateNavigationOut(time);
                }
            }
        }

        /* access modifiers changed from: protected */
        public Interpolator getInterpolator(boolean in) {
            return new DecelerateInterpolator();
        }

        private void cancelAllAnimations() {
            for (Object obj : this.mAnimations) {
                if (obj instanceof Animation) {
                    ((Animation) obj).cancel();
                } else if (obj instanceof ValueAnimator) {
                    ((ValueAnimator) obj).cancel();
                }
            }
            this.mAnimations.clear();
        }

        private void animateNavigationOut(long time) {
            this.mAnimTime = time;
            cancelAllAnimations();
            this.mToolbar.setNavigationIcon((Drawable) null);
            doOnPreDraw(this.mToolbar, new AnimRunnable(time) {
                /* access modifiers changed from: package-private */
                public void doWork() {
                    final ViewData viewData = new ViewData(NavigationManager.this.mToolbar);
                    NavigationManager.this.mToolbar.setNavigationIcon((Drawable) NavigationManager.this.mNavigationIcon);
                    NavigationManager navigationManager = NavigationManager.this;
                    navigationManager.doOnPreDraw(navigationManager.mToolbar, new AnimRunnable(this.mTime) {
                        /* access modifiers changed from: package-private */
                        public void doWork() {
                            boolean first = true;
                            int count = NavigationManager.this.mToolbar.getChildCount();
                            for (int i = 0; i < count; i++) {
                                View child = NavigationManager.this.mToolbar.getChildAt(i);
                                if (!(child instanceof ActionMenuView)) {
                                    int nextLeft = viewData.getLeft(child);
                                    if (nextLeft < 0) {
                                        nextLeft = (-child.getLeft()) - child.getWidth();
                                    }
                                    if (first) {
                                        NavigationManager.this.animateViewOut(child, nextLeft, new Runnable() {
                                            public void run() {
                                                NavigationManager.this.mToolbar.setNavigationIcon((Drawable) null);
                                                NavigationManager.this.mNavigationIcon.cancel();
                                            }
                                        });
                                        first = false;
                                    } else {
                                        NavigationManager.this.animateViewOut(child, nextLeft, (Runnable) null);
                                    }
                                }
                            }
                            if (first) {
                                NavigationManager.this.mToolbar.setNavigationIcon((Drawable) null);
                            }
                        }
                    });
                }
            });
        }

        /* access modifiers changed from: private */
        public void animateViewOut(View view, int nextLeft, Runnable doOnEndRunnable) {
            Interpolator interpolator = getInterpolator(false);
            int prevLeft = view.getLeft();
            ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            animator.setDuration(this.mAnimationDuration);
            final Interpolator interpolator2 = interpolator;
            final int i = prevLeft;
            final int i2 = nextLeft;
            final View view2 = view;
            final Runnable runnable = doOnEndRunnable;
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    Runnable runnable;
                    float factor = interpolator2.getInterpolation(valueAnimator.getAnimatedFraction());
                    int i = i;
                    float left = ((float) i) + (((float) (i2 - i)) * factor);
                    View view = view2;
                    view.offsetLeftAndRight((int) (left - ((float) view.getLeft())));
                    if (valueAnimator.getAnimatedFraction() == 1.0f && (runnable = runnable) != null) {
                        runnable.run();
                    }
                }
            });
            animator.addListener(new Animator.AnimatorListener() {
                public void onAnimationStart(android.animation.Animator animator) {
                }

                public void onAnimationEnd(android.animation.Animator animator) {
                    NavigationManager.this.mAnimations.remove(animator);
                }

                public void onAnimationCancel(android.animation.Animator animator) {
                }

                public void onAnimationRepeat(android.animation.Animator animator) {
                }
            });
            animator.start();
            this.mAnimations.add(animator);
        }

        private void animateNavigationIn(long time) {
            this.mAnimTime = time;
            cancelAllAnimations();
            this.mToolbar.setNavigationIcon((Drawable) null);
            doOnPreDraw(this.mToolbar, new AnimRunnable(time) {
                /* access modifiers changed from: package-private */
                public void doWork() {
                    final ViewData viewData = new ViewData(NavigationManager.this.mToolbar);
                    NavigationManager.this.mToolbar.setNavigationIcon((Drawable) NavigationManager.this.mNavigationIcon);
                    NavigationManager navigationManager = NavigationManager.this;
                    navigationManager.doOnPreDraw(navigationManager.mToolbar, new AnimRunnable(this.mTime) {
                        /* access modifiers changed from: package-private */
                        public void doWork() {
                            int count = NavigationManager.this.mToolbar.getChildCount();
                            for (int i = 0; i < count; i++) {
                                View child = NavigationManager.this.mToolbar.getChildAt(i);
                                if (!(child instanceof ActionMenuView)) {
                                    int prevLeft = viewData.getLeft(child);
                                    if (prevLeft < 0) {
                                        prevLeft = (-child.getLeft()) - child.getWidth();
                                    }
                                    NavigationManager.this.animateViewIn(child, prevLeft);
                                }
                            }
                        }
                    });
                }
            });
        }

        /* access modifiers changed from: private */
        public void animateViewIn(View view, int prevLeft) {
            TranslateAnimation anim = new TranslateAnimation(0, (float) (prevLeft - view.getLeft()), 0, 0.0f, 0, 0.0f, 0, 0.0f);
            anim.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    NavigationManager.this.mAnimations.remove(animation);
                }

                public void onAnimationRepeat(Animation animation) {
                }
            });
            anim.setInterpolator(getInterpolator(true));
            anim.setDuration(this.mAnimationDuration);
            view.startAnimation(anim);
            this.mAnimations.add(anim);
        }

        /* access modifiers changed from: private */
        public void doOnPreDraw(final View v, final Runnable runnable) {
            v.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                public boolean onPreDraw() {
                    runnable.run();
                    v.getViewTreeObserver().removeOnPreDrawListener(this);
                    return false;
                }
            });
        }

        static class ViewData {
            List<Integer> lefts;
            List<View> views;

            public ViewData(Toolbar toolbar) {
                int count = toolbar.getChildCount();
                this.views = new ArrayList(count);
                this.lefts = new ArrayList(count);
                for (int i = 0; i < count; i++) {
                    View child = toolbar.getChildAt(i);
                    if (!(child instanceof ActionMenuView)) {
                        this.views.add(child);
                        this.lefts.add(Integer.valueOf(child.getLeft()));
                    }
                }
            }

            public int getLeft(View view) {
                int size = this.views.size();
                for (int i = 0; i < size; i++) {
                    if (this.views.get(i) == view) {
                        return this.lefts.get(i).intValue();
                    }
                }
                return -1;
            }
        }

        abstract class AnimRunnable implements Runnable {
            long mTime;

            /* access modifiers changed from: package-private */
            public abstract void doWork();

            public AnimRunnable(long time) {
                this.mTime = time;
            }

            public void run() {
                if (this.mTime == NavigationManager.this.mAnimTime) {
                    doWork();
                }
            }
        }
    }

    public static class BaseNavigationManager extends NavigationManager {
        protected DrawerLayout mDrawerLayout;
        protected FragmentManager mFragmentManager;

        public BaseNavigationManager(int styleId, FragmentManager fragmentManager, Toolbar toolbar, DrawerLayout drawerLayout) {
            super(new NavigationDrawerDrawable.Builder(toolbar.getContext(), styleId).build(), toolbar);
            this.mDrawerLayout = drawerLayout;
            this.mFragmentManager = fragmentManager;
            if (drawerLayout != null) {
                drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        BaseNavigationManager.this.onDrawerSlide(drawerView, slideOffset);
                    }

                    public void onDrawerOpened(View drawerView) {
                        BaseNavigationManager.this.onDrawerOpened(drawerView);
                    }

                    public void onDrawerClosed(View drawerView) {
                        BaseNavigationManager.this.onDrawerClosed(drawerView);
                    }

                    public void onDrawerStateChanged(int newState) {
                        BaseNavigationManager.this.onDrawerStateChanged(newState);
                    }
                });
            }
            this.mFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                public void onBackStackChanged() {
                    BaseNavigationManager.this.onFragmentChanged();
                }
            });
        }

        public boolean isBackState() {
            if (this.mFragmentManager.getBackStackEntryCount() > 1) {
                return true;
            }
            DrawerLayout drawerLayout = this.mDrawerLayout;
            return drawerLayout != null && drawerLayout.isDrawerOpen(8388611);
        }

        public void onNavigationClick() {
        }

        /* access modifiers changed from: protected */
        public boolean shouldSyncDrawerSlidingProgress() {
            if (this.mFragmentManager.getBackStackEntryCount() > 1) {
                return false;
            }
            return true;
        }

        /* access modifiers changed from: protected */
        public void onFragmentChanged() {
            notifyStateChanged();
        }

        /* access modifiers changed from: protected */
        public void onDrawerSlide(View drawerView, float slideOffset) {
            if (!shouldSyncDrawerSlidingProgress()) {
                notifyStateInvalidated();
            } else if (this.mDrawerLayout.isDrawerOpen(8388611)) {
                notifyStateProgressChanged(false, 1.0f - slideOffset);
            } else {
                notifyStateProgressChanged(true, slideOffset);
            }
        }

        /* access modifiers changed from: protected */
        public void onDrawerOpened(View drawerView) {
        }

        /* access modifiers changed from: protected */
        public void onDrawerClosed(View drawerView) {
        }

        /* access modifiers changed from: protected */
        public void onDrawerStateChanged(int newState) {
        }
    }

    public static class ThemableNavigationManager extends BaseNavigationManager implements ThemeManager.OnThemeChangedListener {
        private int mCurrentStyle;
        private int mStyleId;

        public ThemableNavigationManager(int styleId, FragmentManager fragmentManager, Toolbar toolbar, DrawerLayout drawerLayout) {
            super(ThemeManager.getInstance().getCurrentStyle(styleId), fragmentManager, toolbar, drawerLayout);
            this.mStyleId = styleId;
            this.mCurrentStyle = ThemeManager.getInstance().getCurrentStyle(styleId);
            ThemeManager.getInstance().registerOnThemeChangedListener(this);
        }

        public void onThemeChanged(ThemeManager.OnThemeChangedEvent event) {
            int style = ThemeManager.getInstance().getCurrentStyle(this.mStyleId);
            if (this.mCurrentStyle != style) {
                this.mCurrentStyle = style;
                NavigationDrawerDrawable drawable = new NavigationDrawerDrawable.Builder(this.mToolbar.getContext(), this.mCurrentStyle).build();
                drawable.switchIconState(this.mNavigationIcon.getIconState(), false);
                this.mNavigationIcon = drawable;
                this.mToolbar.setNavigationIcon((Drawable) this.mNavigationVisible ? this.mNavigationIcon : null);
            }
        }
    }
}
