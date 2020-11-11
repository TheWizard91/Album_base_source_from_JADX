package androidx.fragment.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;
import androidx.fragment.C2082R;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelStoreOwner;

class FragmentStateManager {
    private static final String TAG = "FragmentManager";
    private static final String TARGET_REQUEST_CODE_STATE_TAG = "android:target_req_state";
    private static final String TARGET_STATE_TAG = "android:target_state";
    private static final String USER_VISIBLE_HINT_TAG = "android:user_visible_hint";
    private static final String VIEW_STATE_TAG = "android:view_state";
    private final FragmentLifecycleCallbacksDispatcher mDispatcher;
    private final Fragment mFragment;
    private int mFragmentManagerState = -1;

    FragmentStateManager(FragmentLifecycleCallbacksDispatcher dispatcher, Fragment fragment) {
        this.mDispatcher = dispatcher;
        this.mFragment = fragment;
    }

    FragmentStateManager(FragmentLifecycleCallbacksDispatcher dispatcher, ClassLoader classLoader, FragmentFactory fragmentFactory, FragmentState fs) {
        this.mDispatcher = dispatcher;
        Fragment instantiate = fragmentFactory.instantiate(classLoader, fs.mClassName);
        this.mFragment = instantiate;
        if (fs.mArguments != null) {
            fs.mArguments.setClassLoader(classLoader);
        }
        instantiate.setArguments(fs.mArguments);
        instantiate.mWho = fs.mWho;
        instantiate.mFromLayout = fs.mFromLayout;
        instantiate.mRestored = true;
        instantiate.mFragmentId = fs.mFragmentId;
        instantiate.mContainerId = fs.mContainerId;
        instantiate.mTag = fs.mTag;
        instantiate.mRetainInstance = fs.mRetainInstance;
        instantiate.mRemoving = fs.mRemoving;
        instantiate.mDetached = fs.mDetached;
        instantiate.mHidden = fs.mHidden;
        instantiate.mMaxState = Lifecycle.State.values()[fs.mMaxLifecycleState];
        if (fs.mSavedFragmentState != null) {
            instantiate.mSavedFragmentState = fs.mSavedFragmentState;
        } else {
            instantiate.mSavedFragmentState = new Bundle();
        }
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.v(TAG, "Instantiated fragment " + instantiate);
        }
    }

    FragmentStateManager(FragmentLifecycleCallbacksDispatcher dispatcher, Fragment retainedFragment, FragmentState fs) {
        this.mDispatcher = dispatcher;
        this.mFragment = retainedFragment;
        retainedFragment.mSavedViewState = null;
        retainedFragment.mBackStackNesting = 0;
        retainedFragment.mInLayout = false;
        retainedFragment.mAdded = false;
        retainedFragment.mTargetWho = retainedFragment.mTarget != null ? retainedFragment.mTarget.mWho : null;
        retainedFragment.mTarget = null;
        if (fs.mSavedFragmentState != null) {
            retainedFragment.mSavedFragmentState = fs.mSavedFragmentState;
        } else {
            retainedFragment.mSavedFragmentState = new Bundle();
        }
    }

    /* access modifiers changed from: package-private */
    public Fragment getFragment() {
        return this.mFragment;
    }

    /* access modifiers changed from: package-private */
    public void setFragmentManagerState(int state) {
        this.mFragmentManagerState = state;
    }

    /* access modifiers changed from: package-private */
    public int computeMaxState() {
        int maxState = this.mFragmentManagerState;
        if (this.mFragment.mFromLayout) {
            if (this.mFragment.mInLayout) {
                maxState = Math.max(this.mFragmentManagerState, 1);
            } else if (this.mFragmentManagerState < 2) {
                maxState = Math.min(maxState, this.mFragment.mState);
            } else {
                maxState = Math.min(maxState, 1);
            }
        }
        if (!this.mFragment.mAdded) {
            maxState = Math.min(maxState, 1);
        }
        if (this.mFragment.mRemoving) {
            if (this.mFragment.isInBackStack()) {
                maxState = Math.min(maxState, 1);
            } else {
                maxState = Math.min(maxState, -1);
            }
        }
        if (this.mFragment.mDeferStart && this.mFragment.mState < 3) {
            maxState = Math.min(maxState, 2);
        }
        int i = C02321.$SwitchMap$androidx$lifecycle$Lifecycle$State[this.mFragment.mMaxState.ordinal()];
        if (i == 1) {
            return maxState;
        }
        if (i == 2) {
            return Math.min(maxState, 3);
        }
        if (i != 3) {
            return Math.min(maxState, -1);
        }
        return Math.min(maxState, 1);
    }

    /* renamed from: androidx.fragment.app.FragmentStateManager$1 */
    static /* synthetic */ class C02321 {
        static final /* synthetic */ int[] $SwitchMap$androidx$lifecycle$Lifecycle$State;

        static {
            int[] iArr = new int[Lifecycle.State.values().length];
            $SwitchMap$androidx$lifecycle$Lifecycle$State = iArr;
            try {
                iArr[Lifecycle.State.RESUMED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$State[Lifecycle.State.STARTED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$androidx$lifecycle$Lifecycle$State[Lifecycle.State.CREATED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void ensureInflatedView() {
        if (this.mFragment.mFromLayout && this.mFragment.mInLayout && !this.mFragment.mPerformedCreateView) {
            if (FragmentManager.isLoggingEnabled(3)) {
                Log.d(TAG, "moveto CREATE_VIEW: " + this.mFragment);
            }
            Fragment fragment = this.mFragment;
            fragment.performCreateView(fragment.performGetLayoutInflater(fragment.mSavedFragmentState), (ViewGroup) null, this.mFragment.mSavedFragmentState);
            if (this.mFragment.mView != null) {
                this.mFragment.mView.setSaveFromParentEnabled(false);
                this.mFragment.mView.setTag(C2082R.C2085id.fragment_container_view_tag, this.mFragment);
                if (this.mFragment.mHidden) {
                    this.mFragment.mView.setVisibility(8);
                }
                Fragment fragment2 = this.mFragment;
                fragment2.onViewCreated(fragment2.mView, this.mFragment.mSavedFragmentState);
                FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher = this.mDispatcher;
                Fragment fragment3 = this.mFragment;
                fragmentLifecycleCallbacksDispatcher.dispatchOnFragmentViewCreated(fragment3, fragment3.mView, this.mFragment.mSavedFragmentState, false);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void restoreState(ClassLoader classLoader) {
        if (this.mFragment.mSavedFragmentState != null) {
            this.mFragment.mSavedFragmentState.setClassLoader(classLoader);
            Fragment fragment = this.mFragment;
            fragment.mSavedViewState = fragment.mSavedFragmentState.getSparseParcelableArray(VIEW_STATE_TAG);
            Fragment fragment2 = this.mFragment;
            fragment2.mTargetWho = fragment2.mSavedFragmentState.getString(TARGET_STATE_TAG);
            if (this.mFragment.mTargetWho != null) {
                Fragment fragment3 = this.mFragment;
                fragment3.mTargetRequestCode = fragment3.mSavedFragmentState.getInt(TARGET_REQUEST_CODE_STATE_TAG, 0);
            }
            if (this.mFragment.mSavedUserVisibleHint != null) {
                Fragment fragment4 = this.mFragment;
                fragment4.mUserVisibleHint = fragment4.mSavedUserVisibleHint.booleanValue();
                this.mFragment.mSavedUserVisibleHint = null;
            } else {
                Fragment fragment5 = this.mFragment;
                fragment5.mUserVisibleHint = fragment5.mSavedFragmentState.getBoolean(USER_VISIBLE_HINT_TAG, true);
            }
            if (!this.mFragment.mUserVisibleHint) {
                this.mFragment.mDeferStart = true;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void attach(FragmentHostCallback<?> host, FragmentManager fragmentManager, Fragment parentFragment) {
        this.mFragment.mHost = host;
        this.mFragment.mParentFragment = parentFragment;
        this.mFragment.mFragmentManager = fragmentManager;
        this.mDispatcher.dispatchOnFragmentPreAttached(this.mFragment, host.getContext(), false);
        this.mFragment.performAttach();
        if (this.mFragment.mParentFragment == null) {
            host.onAttachFragment(this.mFragment);
        } else {
            this.mFragment.mParentFragment.onAttachFragment(this.mFragment);
        }
        this.mDispatcher.dispatchOnFragmentAttached(this.mFragment, host.getContext(), false);
    }

    /* access modifiers changed from: package-private */
    public void create() {
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d(TAG, "moveto CREATED: " + this.mFragment);
        }
        if (!this.mFragment.mIsCreated) {
            FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher = this.mDispatcher;
            Fragment fragment = this.mFragment;
            fragmentLifecycleCallbacksDispatcher.dispatchOnFragmentPreCreated(fragment, fragment.mSavedFragmentState, false);
            Fragment fragment2 = this.mFragment;
            fragment2.performCreate(fragment2.mSavedFragmentState);
            FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher2 = this.mDispatcher;
            Fragment fragment3 = this.mFragment;
            fragmentLifecycleCallbacksDispatcher2.dispatchOnFragmentCreated(fragment3, fragment3.mSavedFragmentState, false);
            return;
        }
        Fragment fragment4 = this.mFragment;
        fragment4.restoreChildFragmentState(fragment4.mSavedFragmentState);
        this.mFragment.mState = 1;
    }

    /* JADX WARNING: type inference failed for: r1v28, types: [android.view.View] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void createView(androidx.fragment.app.FragmentContainer r7) {
        /*
            r6 = this;
            androidx.fragment.app.Fragment r0 = r6.mFragment
            boolean r0 = r0.mFromLayout
            if (r0 == 0) goto L_0x0007
            return
        L_0x0007:
            r0 = 3
            boolean r0 = androidx.fragment.app.FragmentManager.isLoggingEnabled(r0)
            if (r0 == 0) goto L_0x0028
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "moveto CREATE_VIEW: "
            java.lang.StringBuilder r0 = r0.append(r1)
            androidx.fragment.app.Fragment r1 = r6.mFragment
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "FragmentManager"
            android.util.Log.d(r1, r0)
        L_0x0028:
            r0 = 0
            androidx.fragment.app.Fragment r1 = r6.mFragment
            android.view.ViewGroup r1 = r1.mContainer
            if (r1 == 0) goto L_0x0035
            androidx.fragment.app.Fragment r1 = r6.mFragment
            android.view.ViewGroup r0 = r1.mContainer
            goto L_0x00c2
        L_0x0035:
            androidx.fragment.app.Fragment r1 = r6.mFragment
            int r1 = r1.mContainerId
            if (r1 == 0) goto L_0x00c2
            androidx.fragment.app.Fragment r1 = r6.mFragment
            int r1 = r1.mContainerId
            r2 = -1
            if (r1 == r2) goto L_0x00a1
            androidx.fragment.app.Fragment r1 = r6.mFragment
            int r1 = r1.mContainerId
            android.view.View r1 = r7.onFindViewById(r1)
            r0 = r1
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
            if (r0 != 0) goto L_0x00c2
            androidx.fragment.app.Fragment r1 = r6.mFragment
            boolean r1 = r1.mRestored
            if (r1 == 0) goto L_0x0056
            goto L_0x00c2
        L_0x0056:
            androidx.fragment.app.Fragment r1 = r6.mFragment     // Catch:{ NotFoundException -> 0x0065 }
            android.content.res.Resources r1 = r1.getResources()     // Catch:{ NotFoundException -> 0x0065 }
            androidx.fragment.app.Fragment r2 = r6.mFragment     // Catch:{ NotFoundException -> 0x0065 }
            int r2 = r2.mContainerId     // Catch:{ NotFoundException -> 0x0065 }
            java.lang.String r1 = r1.getResourceName(r2)     // Catch:{ NotFoundException -> 0x0065 }
            goto L_0x006a
        L_0x0065:
            r1 = move-exception
            java.lang.String r2 = "unknown"
            r1 = r2
        L_0x006a:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "No view found for id 0x"
            java.lang.StringBuilder r3 = r3.append(r4)
            androidx.fragment.app.Fragment r4 = r6.mFragment
            int r4 = r4.mContainerId
            java.lang.String r4 = java.lang.Integer.toHexString(r4)
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = " ("
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r1)
            java.lang.String r4 = ") for fragment "
            java.lang.StringBuilder r3 = r3.append(r4)
            androidx.fragment.app.Fragment r4 = r6.mFragment
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        L_0x00a1:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Cannot create fragment "
            java.lang.StringBuilder r2 = r2.append(r3)
            androidx.fragment.app.Fragment r3 = r6.mFragment
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = " for a container view with no id"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x00c2:
            androidx.fragment.app.Fragment r1 = r6.mFragment
            r1.mContainer = r0
            androidx.fragment.app.Fragment r1 = r6.mFragment
            android.os.Bundle r2 = r1.mSavedFragmentState
            android.view.LayoutInflater r2 = r1.performGetLayoutInflater(r2)
            androidx.fragment.app.Fragment r3 = r6.mFragment
            android.os.Bundle r3 = r3.mSavedFragmentState
            r1.performCreateView(r2, r0, r3)
            androidx.fragment.app.Fragment r1 = r6.mFragment
            android.view.View r1 = r1.mView
            if (r1 == 0) goto L_0x0138
            androidx.fragment.app.Fragment r1 = r6.mFragment
            android.view.View r1 = r1.mView
            r2 = 0
            r1.setSaveFromParentEnabled(r2)
            androidx.fragment.app.Fragment r1 = r6.mFragment
            android.view.View r1 = r1.mView
            int r3 = androidx.fragment.C2082R.C2085id.fragment_container_view_tag
            androidx.fragment.app.Fragment r4 = r6.mFragment
            r1.setTag(r3, r4)
            if (r0 == 0) goto L_0x00f7
            androidx.fragment.app.Fragment r1 = r6.mFragment
            android.view.View r1 = r1.mView
            r0.addView(r1)
        L_0x00f7:
            androidx.fragment.app.Fragment r1 = r6.mFragment
            boolean r1 = r1.mHidden
            if (r1 == 0) goto L_0x0106
            androidx.fragment.app.Fragment r1 = r6.mFragment
            android.view.View r1 = r1.mView
            r3 = 8
            r1.setVisibility(r3)
        L_0x0106:
            androidx.fragment.app.Fragment r1 = r6.mFragment
            android.view.View r1 = r1.mView
            androidx.core.view.ViewCompat.requestApplyInsets(r1)
            androidx.fragment.app.Fragment r1 = r6.mFragment
            android.view.View r3 = r1.mView
            androidx.fragment.app.Fragment r4 = r6.mFragment
            android.os.Bundle r4 = r4.mSavedFragmentState
            r1.onViewCreated(r3, r4)
            androidx.fragment.app.FragmentLifecycleCallbacksDispatcher r1 = r6.mDispatcher
            androidx.fragment.app.Fragment r3 = r6.mFragment
            android.view.View r4 = r3.mView
            androidx.fragment.app.Fragment r5 = r6.mFragment
            android.os.Bundle r5 = r5.mSavedFragmentState
            r1.dispatchOnFragmentViewCreated(r3, r4, r5, r2)
            androidx.fragment.app.Fragment r1 = r6.mFragment
            android.view.View r3 = r1.mView
            int r3 = r3.getVisibility()
            if (r3 != 0) goto L_0x0136
            androidx.fragment.app.Fragment r3 = r6.mFragment
            android.view.ViewGroup r3 = r3.mContainer
            if (r3 == 0) goto L_0x0136
            r2 = 1
        L_0x0136:
            r1.mIsNewlyAdded = r2
        L_0x0138:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.FragmentStateManager.createView(androidx.fragment.app.FragmentContainer):void");
    }

    /* access modifiers changed from: package-private */
    public void activityCreated() {
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d(TAG, "moveto ACTIVITY_CREATED: " + this.mFragment);
        }
        Fragment fragment = this.mFragment;
        fragment.performActivityCreated(fragment.mSavedFragmentState);
        FragmentLifecycleCallbacksDispatcher fragmentLifecycleCallbacksDispatcher = this.mDispatcher;
        Fragment fragment2 = this.mFragment;
        fragmentLifecycleCallbacksDispatcher.dispatchOnFragmentActivityCreated(fragment2, fragment2.mSavedFragmentState, false);
    }

    /* access modifiers changed from: package-private */
    public void restoreViewState() {
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d(TAG, "moveto RESTORE_VIEW_STATE: " + this.mFragment);
        }
        if (this.mFragment.mView != null) {
            Fragment fragment = this.mFragment;
            fragment.restoreViewState(fragment.mSavedFragmentState);
        }
        this.mFragment.mSavedFragmentState = null;
    }

    /* access modifiers changed from: package-private */
    public void start() {
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d(TAG, "moveto STARTED: " + this.mFragment);
        }
        this.mFragment.performStart();
        this.mDispatcher.dispatchOnFragmentStarted(this.mFragment, false);
    }

    /* access modifiers changed from: package-private */
    public void resume() {
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d(TAG, "moveto RESUMED: " + this.mFragment);
        }
        this.mFragment.performResume();
        this.mDispatcher.dispatchOnFragmentResumed(this.mFragment, false);
        this.mFragment.mSavedFragmentState = null;
        this.mFragment.mSavedViewState = null;
    }

    /* access modifiers changed from: package-private */
    public void pause() {
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d(TAG, "movefrom RESUMED: " + this.mFragment);
        }
        this.mFragment.performPause();
        this.mDispatcher.dispatchOnFragmentPaused(this.mFragment, false);
    }

    /* access modifiers changed from: package-private */
    public void stop() {
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d(TAG, "movefrom STARTED: " + this.mFragment);
        }
        this.mFragment.performStop();
        this.mDispatcher.dispatchOnFragmentStopped(this.mFragment, false);
    }

    /* access modifiers changed from: package-private */
    public FragmentState saveState() {
        FragmentState fs = new FragmentState(this.mFragment);
        if (this.mFragment.mState <= -1 || fs.mSavedFragmentState != null) {
            fs.mSavedFragmentState = this.mFragment.mSavedFragmentState;
        } else {
            fs.mSavedFragmentState = saveBasicState();
            if (this.mFragment.mTargetWho != null) {
                if (fs.mSavedFragmentState == null) {
                    fs.mSavedFragmentState = new Bundle();
                }
                fs.mSavedFragmentState.putString(TARGET_STATE_TAG, this.mFragment.mTargetWho);
                if (this.mFragment.mTargetRequestCode != 0) {
                    fs.mSavedFragmentState.putInt(TARGET_REQUEST_CODE_STATE_TAG, this.mFragment.mTargetRequestCode);
                }
            }
        }
        return fs;
    }

    /* access modifiers changed from: package-private */
    public Fragment.SavedState saveInstanceState() {
        Bundle result;
        if (this.mFragment.mState <= -1 || (result = saveBasicState()) == null) {
            return null;
        }
        return new Fragment.SavedState(result);
    }

    private Bundle saveBasicState() {
        Bundle result = new Bundle();
        this.mFragment.performSaveInstanceState(result);
        this.mDispatcher.dispatchOnFragmentSaveInstanceState(this.mFragment, result, false);
        if (result.isEmpty()) {
            result = null;
        }
        if (this.mFragment.mView != null) {
            saveViewState();
        }
        if (this.mFragment.mSavedViewState != null) {
            if (result == null) {
                result = new Bundle();
            }
            result.putSparseParcelableArray(VIEW_STATE_TAG, this.mFragment.mSavedViewState);
        }
        if (!this.mFragment.mUserVisibleHint) {
            if (result == null) {
                result = new Bundle();
            }
            result.putBoolean(USER_VISIBLE_HINT_TAG, this.mFragment.mUserVisibleHint);
        }
        return result;
    }

    /* access modifiers changed from: package-private */
    public void saveViewState() {
        if (this.mFragment.mView != null) {
            SparseArray<Parcelable> mStateArray = new SparseArray<>();
            this.mFragment.mView.saveHierarchyState(mStateArray);
            if (mStateArray.size() > 0) {
                this.mFragment.mSavedViewState = mStateArray;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void destroy(FragmentHostCallback<?> host, FragmentManagerViewModel nonConfig) {
        boolean shouldClear;
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d(TAG, "movefrom CREATED: " + this.mFragment);
        }
        boolean beingRemoved = this.mFragment.mRemoving && !this.mFragment.isInBackStack();
        if (beingRemoved || nonConfig.shouldDestroy(this.mFragment)) {
            if (host instanceof ViewModelStoreOwner) {
                shouldClear = nonConfig.isCleared();
            } else if (host.getContext() instanceof Activity) {
                shouldClear = true ^ ((Activity) host.getContext()).isChangingConfigurations();
            } else {
                shouldClear = true;
            }
            if (beingRemoved || shouldClear) {
                nonConfig.clearNonConfigState(this.mFragment);
            }
            this.mFragment.performDestroy();
            this.mDispatcher.dispatchOnFragmentDestroyed(this.mFragment, false);
            return;
        }
        this.mFragment.mState = 0;
    }

    /* access modifiers changed from: package-private */
    public void detach(FragmentManagerViewModel nonConfig) {
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d(TAG, "movefrom ATTACHED: " + this.mFragment);
        }
        this.mFragment.performDetach();
        boolean beingRemoved = false;
        this.mDispatcher.dispatchOnFragmentDetached(this.mFragment, false);
        this.mFragment.mState = -1;
        this.mFragment.mHost = null;
        this.mFragment.mParentFragment = null;
        this.mFragment.mFragmentManager = null;
        if (this.mFragment.mRemoving && !this.mFragment.isInBackStack()) {
            beingRemoved = true;
        }
        if (beingRemoved || nonConfig.shouldDestroy(this.mFragment)) {
            if (FragmentManager.isLoggingEnabled(3)) {
                Log.d(TAG, "initState called for fragment: " + this.mFragment);
            }
            this.mFragment.initState();
        }
    }
}
