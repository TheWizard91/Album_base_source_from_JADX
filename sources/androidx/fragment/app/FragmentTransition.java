package androidx.fragment.app;

import android.graphics.Rect;
import android.os.Build;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.collection.ArrayMap;
import androidx.core.app.SharedElementCallback;
import androidx.core.p003os.CancellationSignal;
import androidx.core.view.OneShotPreDrawListener;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentTransaction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class FragmentTransition {
    private static final int[] INVERSE_OPS = {0, 3, 0, 1, 5, 4, 7, 6, 9, 8, 10};
    private static final FragmentTransitionImpl PLATFORM_IMPL = (Build.VERSION.SDK_INT >= 21 ? new FragmentTransitionCompat21() : null);
    private static final FragmentTransitionImpl SUPPORT_IMPL = resolveSupportImpl();

    interface Callback {
        void onComplete(Fragment fragment, CancellationSignal cancellationSignal);

        void onStart(Fragment fragment, CancellationSignal cancellationSignal);
    }

    private static FragmentTransitionImpl resolveSupportImpl() {
        try {
            return (FragmentTransitionImpl) Class.forName("androidx.transition.FragmentTransitionSupport").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }

    static void startTransitions(FragmentManager fragmentManager, ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop, int startIndex, int endIndex, boolean isReordered, Callback callback) {
        int numContainers;
        int i;
        FragmentManager fragmentManager2 = fragmentManager;
        ArrayList<BackStackRecord> arrayList = records;
        ArrayList<Boolean> arrayList2 = isRecordPop;
        int i2 = endIndex;
        boolean z = isReordered;
        if (fragmentManager2.mCurState >= 1) {
            SparseArray sparseArray = new SparseArray();
            for (int i3 = startIndex; i3 < i2; i3++) {
                BackStackRecord record = arrayList.get(i3);
                if (arrayList2.get(i3).booleanValue()) {
                    calculatePopFragments(record, sparseArray, z);
                } else {
                    calculateFragments(record, sparseArray, z);
                }
            }
            if (sparseArray.size() != 0) {
                View nonExistentView = new View(fragmentManager2.mHost.getContext());
                int numContainers2 = sparseArray.size();
                int i4 = 0;
                while (i4 < numContainers2) {
                    int containerId = sparseArray.keyAt(i4);
                    ArrayMap<String, String> nameOverrides = calculateNameOverrides(containerId, arrayList, arrayList2, startIndex, i2);
                    FragmentContainerTransition containerTransition = (FragmentContainerTransition) sparseArray.valueAt(i4);
                    if (z) {
                        configureTransitionsReordered(fragmentManager, containerId, containerTransition, nonExistentView, nameOverrides, callback);
                        i = i4;
                        numContainers = numContainers2;
                    } else {
                        int i5 = containerId;
                        i = i4;
                        numContainers = numContainers2;
                        configureTransitionsOrdered(fragmentManager, containerId, containerTransition, nonExistentView, nameOverrides, callback);
                    }
                    i4 = i + 1;
                    numContainers2 = numContainers;
                }
                int i6 = i4;
                int i7 = numContainers2;
            }
        }
    }

    private static ArrayMap<String, String> calculateNameOverrides(int containerId, ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop, int startIndex, int endIndex) {
        ArrayList<String> sources;
        ArrayList<String> targets;
        ArrayMap<String, String> nameOverrides = new ArrayMap<>();
        for (int recordNum = endIndex - 1; recordNum >= startIndex; recordNum--) {
            BackStackRecord record = records.get(recordNum);
            if (record.interactsWith(containerId)) {
                boolean isPop = isRecordPop.get(recordNum).booleanValue();
                if (record.mSharedElementSourceNames != null) {
                    int numSharedElements = record.mSharedElementSourceNames.size();
                    if (isPop) {
                        targets = record.mSharedElementSourceNames;
                        sources = record.mSharedElementTargetNames;
                    } else {
                        sources = record.mSharedElementSourceNames;
                        targets = record.mSharedElementTargetNames;
                    }
                    for (int i = 0; i < numSharedElements; i++) {
                        String sourceName = sources.get(i);
                        String targetName = targets.get(i);
                        String previousTarget = nameOverrides.remove(targetName);
                        if (previousTarget != null) {
                            nameOverrides.put(sourceName, previousTarget);
                        } else {
                            nameOverrides.put(sourceName, targetName);
                        }
                    }
                }
            }
        }
        return nameOverrides;
    }

    /* JADX WARNING: type inference failed for: r2v11, types: [android.view.View] */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0024, code lost:
        r15 = r10.lastIn;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void configureTransitionsReordered(androidx.fragment.app.FragmentManager r24, int r25, androidx.fragment.app.FragmentTransition.FragmentContainerTransition r26, android.view.View r27, androidx.collection.ArrayMap<java.lang.String, java.lang.String> r28, androidx.fragment.app.FragmentTransition.Callback r29) {
        /*
            r0 = r24
            r10 = r26
            r11 = r27
            r12 = r29
            r1 = 0
            androidx.fragment.app.FragmentContainer r2 = r0.mContainer
            boolean r2 = r2.onHasView()
            if (r2 == 0) goto L_0x001e
            androidx.fragment.app.FragmentContainer r2 = r0.mContainer
            r13 = r25
            android.view.View r2 = r2.onFindViewById(r13)
            r1 = r2
            android.view.ViewGroup r1 = (android.view.ViewGroup) r1
            r14 = r1
            goto L_0x0021
        L_0x001e:
            r13 = r25
            r14 = r1
        L_0x0021:
            if (r14 != 0) goto L_0x0024
            return
        L_0x0024:
            androidx.fragment.app.Fragment r15 = r10.lastIn
            androidx.fragment.app.Fragment r9 = r10.firstOut
            androidx.fragment.app.FragmentTransitionImpl r8 = chooseImpl(r9, r15)
            if (r8 != 0) goto L_0x002f
            return
        L_0x002f:
            boolean r7 = r10.lastInIsPop
            boolean r6 = r10.firstOutIsPop
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r5 = r1
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r4 = r1
            java.lang.Object r3 = getEnterTransition(r8, r15, r7)
            java.lang.Object r2 = getExitTransition(r8, r9, r6)
            r1 = r8
            r16 = r2
            r2 = r14
            r17 = r3
            r3 = r27
            r18 = r4
            r4 = r28
            r19 = r5
            r5 = r26
            r20 = r6
            r6 = r18
            r21 = r7
            r7 = r19
            r0 = r8
            r8 = r17
            r10 = r9
            r9 = r16
            java.lang.Object r9 = configureSharedElementsReordered(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            if (r8 != 0) goto L_0x0073
            if (r9 != 0) goto L_0x0073
            r7 = r16
            if (r7 != 0) goto L_0x0075
            return
        L_0x0073:
            r7 = r16
        L_0x0075:
            r6 = r18
            java.util.ArrayList r5 = configureEnteringExitingViews(r0, r7, r10, r6, r11)
            r4 = r19
            java.util.ArrayList r3 = configureEnteringExitingViews(r0, r8, r15, r4, r11)
            r1 = 4
            setViewVisibility(r3, r1)
            r1 = r0
            r2 = r8
            r16 = r3
            r3 = r7
            r11 = r4
            r4 = r9
            r13 = r5
            r5 = r15
            r6 = r21
            java.lang.Object r6 = mergeTransitions(r1, r2, r3, r4, r5, r6)
            if (r10 == 0) goto L_0x00b4
            if (r13 == 0) goto L_0x00b4
            int r1 = r13.size()
            if (r1 > 0) goto L_0x00a4
            int r1 = r18.size()
            if (r1 <= 0) goto L_0x00b4
        L_0x00a4:
            androidx.core.os.CancellationSignal r1 = new androidx.core.os.CancellationSignal
            r1.<init>()
            r12.onStart(r10, r1)
            androidx.fragment.app.FragmentTransition$1 r2 = new androidx.fragment.app.FragmentTransition$1
            r2.<init>(r12, r10, r1)
            r0.setListenerForTransitionEnd(r10, r6, r1, r2)
        L_0x00b4:
            if (r6 == 0) goto L_0x00ec
            replaceHide(r0, r7, r10, r13)
            java.util.ArrayList r17 = r0.prepareSetNameOverridesReordered(r11)
            r1 = r0
            r2 = r6
            r3 = r8
            r4 = r16
            r5 = r7
            r19 = r10
            r10 = r6
            r6 = r13
            r22 = r7
            r7 = r9
            r23 = r8
            r8 = r11
            r1.scheduleRemoveTargets(r2, r3, r4, r5, r6, r7, r8)
            r0.beginDelayedTransition(r14, r10)
            r2 = r0
            r3 = r14
            r4 = r18
            r5 = r11
            r6 = r17
            r7 = r28
            r2.setNameOverridesReordered(r3, r4, r5, r6, r7)
            r1 = 0
            r2 = r16
            setViewVisibility(r2, r1)
            r1 = r18
            r0.swapSharedElementTargets(r9, r1, r11)
            goto L_0x00f7
        L_0x00ec:
            r22 = r7
            r23 = r8
            r19 = r10
            r2 = r16
            r1 = r18
            r10 = r6
        L_0x00f7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.FragmentTransition.configureTransitionsReordered(androidx.fragment.app.FragmentManager, int, androidx.fragment.app.FragmentTransition$FragmentContainerTransition, android.view.View, androidx.collection.ArrayMap, androidx.fragment.app.FragmentTransition$Callback):void");
    }

    private static void replaceHide(FragmentTransitionImpl impl, Object exitTransition, Fragment exitingFragment, final ArrayList<View> exitingViews) {
        if (exitingFragment != null && exitTransition != null && exitingFragment.mAdded && exitingFragment.mHidden && exitingFragment.mHiddenChanged) {
            exitingFragment.setHideReplaced(true);
            impl.scheduleHideFragmentView(exitTransition, exitingFragment.getView(), exitingViews);
            OneShotPreDrawListener.add(exitingFragment.mContainer, new Runnable() {
                public void run() {
                    FragmentTransition.setViewVisibility(exitingViews, 4);
                }
            });
        }
    }

    /* JADX WARNING: type inference failed for: r2v9, types: [android.view.View] */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0026, code lost:
        r9 = r10.lastIn;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void configureTransitionsOrdered(androidx.fragment.app.FragmentManager r31, int r32, androidx.fragment.app.FragmentTransition.FragmentContainerTransition r33, android.view.View r34, androidx.collection.ArrayMap<java.lang.String, java.lang.String> r35, androidx.fragment.app.FragmentTransition.Callback r36) {
        /*
            r0 = r31
            r10 = r33
            r11 = r34
            r12 = r35
            r13 = r36
            r1 = 0
            androidx.fragment.app.FragmentContainer r2 = r0.mContainer
            boolean r2 = r2.onHasView()
            if (r2 == 0) goto L_0x0020
            androidx.fragment.app.FragmentContainer r2 = r0.mContainer
            r14 = r32
            android.view.View r2 = r2.onFindViewById(r14)
            r1 = r2
            android.view.ViewGroup r1 = (android.view.ViewGroup) r1
            r15 = r1
            goto L_0x0023
        L_0x0020:
            r14 = r32
            r15 = r1
        L_0x0023:
            if (r15 != 0) goto L_0x0026
            return
        L_0x0026:
            androidx.fragment.app.Fragment r9 = r10.lastIn
            androidx.fragment.app.Fragment r8 = r10.firstOut
            androidx.fragment.app.FragmentTransitionImpl r7 = chooseImpl(r8, r9)
            if (r7 != 0) goto L_0x0031
            return
        L_0x0031:
            boolean r6 = r10.lastInIsPop
            boolean r5 = r10.firstOutIsPop
            java.lang.Object r4 = getEnterTransition(r7, r9, r6)
            java.lang.Object r3 = getExitTransition(r7, r8, r5)
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r2 = r1
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r24 = r1
            r1 = r7
            r25 = r2
            r2 = r15
            r16 = r3
            r3 = r34
            r26 = r4
            r4 = r35
            r27 = r5
            r5 = r33
            r28 = r6
            r6 = r25
            r0 = r7
            r7 = r24
            r14 = r8
            r8 = r26
            r29 = r9
            r9 = r16
            java.lang.Object r30 = configureSharedElementsOrdered(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            r9 = r26
            if (r9 != 0) goto L_0x0077
            if (r30 != 0) goto L_0x0077
            r1 = r16
            if (r1 != 0) goto L_0x0079
            return
        L_0x0077:
            r1 = r16
        L_0x0079:
            r8 = r25
            java.util.ArrayList r25 = configureEnteringExitingViews(r0, r1, r14, r8, r11)
            if (r25 == 0) goto L_0x008b
            boolean r2 = r25.isEmpty()
            if (r2 == 0) goto L_0x0088
            goto L_0x008b
        L_0x0088:
            r26 = r1
            goto L_0x008e
        L_0x008b:
            r3 = 0
            r26 = r3
        L_0x008e:
            r0.addTarget(r9, r11)
            boolean r6 = r10.lastInIsPop
            r1 = r0
            r2 = r9
            r3 = r26
            r4 = r30
            r5 = r29
            java.lang.Object r7 = mergeTransitions(r1, r2, r3, r4, r5, r6)
            if (r14 == 0) goto L_0x00bf
            if (r25 == 0) goto L_0x00bf
            int r1 = r25.size()
            if (r1 > 0) goto L_0x00af
            int r1 = r8.size()
            if (r1 <= 0) goto L_0x00bf
        L_0x00af:
            androidx.core.os.CancellationSignal r1 = new androidx.core.os.CancellationSignal
            r1.<init>()
            r13.onStart(r14, r1)
            androidx.fragment.app.FragmentTransition$3 r2 = new androidx.fragment.app.FragmentTransition$3
            r2.<init>(r13, r14, r1)
            r0.setListenerForTransitionEnd(r14, r7, r1, r2)
        L_0x00bf:
            if (r7 == 0) goto L_0x00fa
            java.util.ArrayList r19 = new java.util.ArrayList
            r19.<init>()
            r16 = r0
            r17 = r7
            r18 = r9
            r20 = r26
            r21 = r25
            r22 = r30
            r23 = r24
            r16.scheduleRemoveTargets(r17, r18, r19, r20, r21, r22, r23)
            r1 = r0
            r2 = r15
            r3 = r29
            r4 = r34
            r5 = r24
            r6 = r9
            r10 = r7
            r7 = r19
            r16 = r8
            r8 = r26
            r17 = r9
            r9 = r25
            scheduleTargetChange(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            r1 = r24
            r0.setNameOverridesOrdered(r15, r1, r12)
            r0.beginDelayedTransition(r15, r10)
            r0.scheduleNameReset(r15, r1, r12)
            goto L_0x0101
        L_0x00fa:
            r10 = r7
            r16 = r8
            r17 = r9
            r1 = r24
        L_0x0101:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.FragmentTransition.configureTransitionsOrdered(androidx.fragment.app.FragmentManager, int, androidx.fragment.app.FragmentTransition$FragmentContainerTransition, android.view.View, androidx.collection.ArrayMap, androidx.fragment.app.FragmentTransition$Callback):void");
    }

    private static void scheduleTargetChange(FragmentTransitionImpl impl, ViewGroup sceneRoot, Fragment inFragment, View nonExistentView, ArrayList<View> sharedElementsIn, Object enterTransition, ArrayList<View> enteringViews, Object exitTransition, ArrayList<View> exitingViews) {
        final Object obj = enterTransition;
        final FragmentTransitionImpl fragmentTransitionImpl = impl;
        final View view = nonExistentView;
        final Fragment fragment = inFragment;
        final ArrayList<View> arrayList = sharedElementsIn;
        final ArrayList<View> arrayList2 = enteringViews;
        final ArrayList<View> arrayList3 = exitingViews;
        final Object obj2 = exitTransition;
        ViewGroup viewGroup = sceneRoot;
        OneShotPreDrawListener.add(sceneRoot, new Runnable() {
            public void run() {
                Object obj = obj;
                if (obj != null) {
                    fragmentTransitionImpl.removeTarget(obj, view);
                    arrayList2.addAll(FragmentTransition.configureEnteringExitingViews(fragmentTransitionImpl, obj, fragment, arrayList, view));
                }
                if (arrayList3 != null) {
                    if (obj2 != null) {
                        ArrayList<View> tempExiting = new ArrayList<>();
                        tempExiting.add(view);
                        fragmentTransitionImpl.replaceTargets(obj2, arrayList3, tempExiting);
                    }
                    arrayList3.clear();
                    arrayList3.add(view);
                }
            }
        });
    }

    private static FragmentTransitionImpl chooseImpl(Fragment outFragment, Fragment inFragment) {
        ArrayList<Object> transitions = new ArrayList<>();
        if (outFragment != null) {
            Object exitTransition = outFragment.getExitTransition();
            if (exitTransition != null) {
                transitions.add(exitTransition);
            }
            Object returnTransition = outFragment.getReturnTransition();
            if (returnTransition != null) {
                transitions.add(returnTransition);
            }
            Object sharedReturnTransition = outFragment.getSharedElementReturnTransition();
            if (sharedReturnTransition != null) {
                transitions.add(sharedReturnTransition);
            }
        }
        if (inFragment != null) {
            Object enterTransition = inFragment.getEnterTransition();
            if (enterTransition != null) {
                transitions.add(enterTransition);
            }
            Object reenterTransition = inFragment.getReenterTransition();
            if (reenterTransition != null) {
                transitions.add(reenterTransition);
            }
            Object sharedEnterTransition = inFragment.getSharedElementEnterTransition();
            if (sharedEnterTransition != null) {
                transitions.add(sharedEnterTransition);
            }
        }
        if (transitions.isEmpty()) {
            return null;
        }
        FragmentTransitionImpl fragmentTransitionImpl = PLATFORM_IMPL;
        if (fragmentTransitionImpl != null && canHandleAll(fragmentTransitionImpl, transitions)) {
            return fragmentTransitionImpl;
        }
        FragmentTransitionImpl fragmentTransitionImpl2 = SUPPORT_IMPL;
        if (fragmentTransitionImpl2 != null && canHandleAll(fragmentTransitionImpl2, transitions)) {
            return fragmentTransitionImpl2;
        }
        if (fragmentTransitionImpl == null && fragmentTransitionImpl2 == null) {
            return null;
        }
        throw new IllegalArgumentException("Invalid Transition types");
    }

    private static boolean canHandleAll(FragmentTransitionImpl impl, List<Object> transitions) {
        int size = transitions.size();
        for (int i = 0; i < size; i++) {
            if (!impl.canHandle(transitions.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static Object getSharedElementTransition(FragmentTransitionImpl impl, Fragment inFragment, Fragment outFragment, boolean isPop) {
        Object obj;
        if (inFragment == null || outFragment == null) {
            return null;
        }
        if (isPop) {
            obj = outFragment.getSharedElementReturnTransition();
        } else {
            obj = inFragment.getSharedElementEnterTransition();
        }
        return impl.wrapTransitionInSet(impl.cloneTransition(obj));
    }

    private static Object getEnterTransition(FragmentTransitionImpl impl, Fragment inFragment, boolean isPop) {
        Object obj;
        if (inFragment == null) {
            return null;
        }
        if (isPop) {
            obj = inFragment.getReenterTransition();
        } else {
            obj = inFragment.getEnterTransition();
        }
        return impl.cloneTransition(obj);
    }

    private static Object getExitTransition(FragmentTransitionImpl impl, Fragment outFragment, boolean isPop) {
        Object obj;
        if (outFragment == null) {
            return null;
        }
        if (isPop) {
            obj = outFragment.getReturnTransition();
        } else {
            obj = outFragment.getExitTransition();
        }
        return impl.cloneTransition(obj);
    }

    private static Object configureSharedElementsReordered(FragmentTransitionImpl impl, ViewGroup sceneRoot, View nonExistentView, ArrayMap<String, String> nameOverrides, FragmentContainerTransition fragments, ArrayList<View> sharedElementsOut, ArrayList<View> sharedElementsIn, Object enterTransition, Object exitTransition) {
        Object sharedElementTransition;
        Object sharedElementTransition2;
        Object sharedElementTransition3;
        View epicenterView;
        Rect epicenter;
        ArrayMap<String, View> inSharedElements;
        FragmentTransitionImpl fragmentTransitionImpl = impl;
        View view = nonExistentView;
        ArrayMap<String, String> arrayMap = nameOverrides;
        FragmentContainerTransition fragmentContainerTransition = fragments;
        ArrayList<View> arrayList = sharedElementsOut;
        ArrayList<View> arrayList2 = sharedElementsIn;
        Object obj = enterTransition;
        Fragment inFragment = fragmentContainerTransition.lastIn;
        Fragment outFragment = fragmentContainerTransition.firstOut;
        if (inFragment != null) {
            inFragment.requireView().setVisibility(0);
        }
        if (inFragment == null) {
            ViewGroup viewGroup = sceneRoot;
            Fragment fragment = outFragment;
        } else if (outFragment == null) {
            ViewGroup viewGroup2 = sceneRoot;
            Fragment fragment2 = outFragment;
        } else {
            boolean inIsPop = fragmentContainerTransition.lastInIsPop;
            if (nameOverrides.isEmpty()) {
                sharedElementTransition = null;
            } else {
                sharedElementTransition = getSharedElementTransition(fragmentTransitionImpl, inFragment, outFragment, inIsPop);
            }
            ArrayMap<String, View> outSharedElements = captureOutSharedElements(fragmentTransitionImpl, arrayMap, sharedElementTransition, fragmentContainerTransition);
            ArrayMap<String, View> inSharedElements2 = captureInSharedElements(fragmentTransitionImpl, arrayMap, sharedElementTransition, fragmentContainerTransition);
            if (nameOverrides.isEmpty()) {
                if (outSharedElements != null) {
                    outSharedElements.clear();
                }
                if (inSharedElements2 != null) {
                    inSharedElements2.clear();
                }
                sharedElementTransition2 = null;
            } else {
                addSharedElementsWithMatchingNames(arrayList, outSharedElements, nameOverrides.keySet());
                addSharedElementsWithMatchingNames(arrayList2, inSharedElements2, nameOverrides.values());
                sharedElementTransition2 = sharedElementTransition;
            }
            if (obj == null && exitTransition == null && sharedElementTransition2 == null) {
                return null;
            }
            callSharedElementStartEnd(inFragment, outFragment, inIsPop, outSharedElements, true);
            if (sharedElementTransition2 != null) {
                arrayList2.add(view);
                fragmentTransitionImpl.setSharedElementTargets(sharedElementTransition2, view, arrayList);
                sharedElementTransition3 = sharedElementTransition2;
                inSharedElements = inSharedElements2;
                ArrayMap<String, View> arrayMap2 = outSharedElements;
                setOutEpicenter(impl, sharedElementTransition2, exitTransition, outSharedElements, fragmentContainerTransition.firstOutIsPop, fragmentContainerTransition.firstOutTransaction);
                Rect epicenter2 = new Rect();
                View epicenterView2 = getInEpicenterView(inSharedElements, fragmentContainerTransition, obj, inIsPop);
                if (epicenterView2 != null) {
                    fragmentTransitionImpl.setEpicenter(obj, epicenter2);
                }
                epicenter = epicenter2;
                epicenterView = epicenterView2;
            } else {
                sharedElementTransition3 = sharedElementTransition2;
                inSharedElements = inSharedElements2;
                ArrayMap<String, View> arrayMap3 = outSharedElements;
                epicenter = null;
                epicenterView = null;
            }
            final Fragment fragment3 = inFragment;
            final Fragment fragment4 = outFragment;
            final boolean z = inIsPop;
            final ArrayMap<String, View> arrayMap4 = inSharedElements;
            C02395 r8 = r0;
            final View view2 = epicenterView;
            boolean z2 = inIsPop;
            final FragmentTransitionImpl fragmentTransitionImpl2 = impl;
            Fragment fragment5 = outFragment;
            final Rect rect = epicenter;
            C02395 r0 = new Runnable() {
                public void run() {
                    FragmentTransition.callSharedElementStartEnd(fragment3, fragment4, z, arrayMap4, false);
                    View view = view2;
                    if (view != null) {
                        fragmentTransitionImpl2.getBoundsOnScreen(view, rect);
                    }
                }
            };
            OneShotPreDrawListener.add(sceneRoot, r8);
            return sharedElementTransition3;
        }
        return null;
    }

    private static void addSharedElementsWithMatchingNames(ArrayList<View> views, ArrayMap<String, View> sharedElements, Collection<String> nameOverridesSet) {
        for (int i = sharedElements.size() - 1; i >= 0; i--) {
            View view = sharedElements.valueAt(i);
            if (nameOverridesSet.contains(ViewCompat.getTransitionName(view))) {
                views.add(view);
            }
        }
    }

    private static Object configureSharedElementsOrdered(FragmentTransitionImpl impl, ViewGroup sceneRoot, View nonExistentView, ArrayMap<String, String> nameOverrides, FragmentContainerTransition fragments, ArrayList<View> sharedElementsOut, ArrayList<View> sharedElementsIn, Object enterTransition, Object exitTransition) {
        Object sharedElementTransition;
        Object sharedElementTransition2;
        Rect inEpicenter;
        FragmentTransitionImpl fragmentTransitionImpl = impl;
        FragmentContainerTransition fragmentContainerTransition = fragments;
        ArrayList<View> arrayList = sharedElementsOut;
        Object obj = enterTransition;
        Fragment inFragment = fragmentContainerTransition.lastIn;
        Fragment outFragment = fragmentContainerTransition.firstOut;
        if (inFragment == null) {
            ViewGroup viewGroup = sceneRoot;
            Fragment fragment = outFragment;
            Fragment fragment2 = inFragment;
        } else if (outFragment == null) {
            ViewGroup viewGroup2 = sceneRoot;
            Fragment fragment3 = outFragment;
            Fragment fragment4 = inFragment;
        } else {
            final boolean inIsPop = fragmentContainerTransition.lastInIsPop;
            if (nameOverrides.isEmpty()) {
                sharedElementTransition = null;
            } else {
                sharedElementTransition = getSharedElementTransition(fragmentTransitionImpl, inFragment, outFragment, inIsPop);
            }
            ArrayMap<String, View> outSharedElements = captureOutSharedElements(fragmentTransitionImpl, nameOverrides, sharedElementTransition, fragmentContainerTransition);
            if (nameOverrides.isEmpty()) {
                sharedElementTransition2 = null;
            } else {
                arrayList.addAll(outSharedElements.values());
                sharedElementTransition2 = sharedElementTransition;
            }
            if (obj == null && exitTransition == null && sharedElementTransition2 == null) {
                return null;
            }
            callSharedElementStartEnd(inFragment, outFragment, inIsPop, outSharedElements, true);
            if (sharedElementTransition2 != null) {
                Rect inEpicenter2 = new Rect();
                fragmentTransitionImpl.setSharedElementTargets(sharedElementTransition2, nonExistentView, arrayList);
                ArrayMap<String, View> arrayMap = outSharedElements;
                Rect inEpicenter3 = inEpicenter2;
                setOutEpicenter(impl, sharedElementTransition2, exitTransition, outSharedElements, fragmentContainerTransition.firstOutIsPop, fragmentContainerTransition.firstOutTransaction);
                if (obj != null) {
                    fragmentTransitionImpl.setEpicenter(obj, inEpicenter3);
                }
                inEpicenter = inEpicenter3;
            } else {
                inEpicenter = null;
            }
            final Object finalSharedElementTransition = sharedElementTransition2;
            final FragmentTransitionImpl fragmentTransitionImpl2 = impl;
            final ArrayMap<String, String> arrayMap2 = nameOverrides;
            final FragmentContainerTransition fragmentContainerTransition2 = fragments;
            final ArrayList<View> arrayList2 = sharedElementsIn;
            Object sharedElementTransition3 = sharedElementTransition2;
            final View view = nonExistentView;
            C02406 r13 = r0;
            final Fragment fragment5 = inFragment;
            final Fragment fragment6 = outFragment;
            boolean z = inIsPop;
            Fragment fragment7 = outFragment;
            final ArrayList<View> arrayList3 = sharedElementsOut;
            Fragment fragment8 = inFragment;
            final Object obj2 = enterTransition;
            final Rect rect = inEpicenter;
            C02406 r0 = new Runnable() {
                public void run() {
                    ArrayMap<String, View> inSharedElements = FragmentTransition.captureInSharedElements(fragmentTransitionImpl2, arrayMap2, finalSharedElementTransition, fragmentContainerTransition2);
                    if (inSharedElements != null) {
                        arrayList2.addAll(inSharedElements.values());
                        arrayList2.add(view);
                    }
                    FragmentTransition.callSharedElementStartEnd(fragment5, fragment6, inIsPop, inSharedElements, false);
                    Object obj = finalSharedElementTransition;
                    if (obj != null) {
                        fragmentTransitionImpl2.swapSharedElementTargets(obj, arrayList3, arrayList2);
                        View inEpicenterView = FragmentTransition.getInEpicenterView(inSharedElements, fragmentContainerTransition2, obj2, inIsPop);
                        if (inEpicenterView != null) {
                            fragmentTransitionImpl2.getBoundsOnScreen(inEpicenterView, rect);
                        }
                    }
                }
            };
            OneShotPreDrawListener.add(sceneRoot, r13);
            return sharedElementTransition3;
        }
        return null;
    }

    private static ArrayMap<String, View> captureOutSharedElements(FragmentTransitionImpl impl, ArrayMap<String, String> nameOverrides, Object sharedElementTransition, FragmentContainerTransition fragments) {
        ArrayList<String> names;
        SharedElementCallback sharedElementCallback;
        if (nameOverrides.isEmpty() || sharedElementTransition == null) {
            nameOverrides.clear();
            return null;
        }
        Fragment outFragment = fragments.firstOut;
        ArrayMap<String, View> outSharedElements = new ArrayMap<>();
        impl.findNamedViews(outSharedElements, outFragment.requireView());
        BackStackRecord outTransaction = fragments.firstOutTransaction;
        if (fragments.firstOutIsPop) {
            sharedElementCallback = outFragment.getEnterTransitionCallback();
            names = outTransaction.mSharedElementTargetNames;
        } else {
            sharedElementCallback = outFragment.getExitTransitionCallback();
            names = outTransaction.mSharedElementSourceNames;
        }
        if (names != null) {
            outSharedElements.retainAll(names);
        }
        if (sharedElementCallback != null) {
            sharedElementCallback.onMapSharedElements(names, outSharedElements);
            for (int i = names.size() - 1; i >= 0; i--) {
                String name = names.get(i);
                View view = outSharedElements.get(name);
                if (view == null) {
                    nameOverrides.remove(name);
                } else if (!name.equals(ViewCompat.getTransitionName(view))) {
                    nameOverrides.put(ViewCompat.getTransitionName(view), nameOverrides.remove(name));
                }
            }
        } else {
            nameOverrides.retainAll(outSharedElements.keySet());
        }
        return outSharedElements;
    }

    static ArrayMap<String, View> captureInSharedElements(FragmentTransitionImpl impl, ArrayMap<String, String> nameOverrides, Object sharedElementTransition, FragmentContainerTransition fragments) {
        ArrayList<String> names;
        SharedElementCallback sharedElementCallback;
        String key;
        Fragment inFragment = fragments.lastIn;
        View fragmentView = inFragment.getView();
        if (nameOverrides.isEmpty() || sharedElementTransition == null || fragmentView == null) {
            nameOverrides.clear();
            return null;
        }
        ArrayMap<String, View> inSharedElements = new ArrayMap<>();
        impl.findNamedViews(inSharedElements, fragmentView);
        BackStackRecord inTransaction = fragments.lastInTransaction;
        if (fragments.lastInIsPop) {
            sharedElementCallback = inFragment.getExitTransitionCallback();
            names = inTransaction.mSharedElementSourceNames;
        } else {
            sharedElementCallback = inFragment.getEnterTransitionCallback();
            names = inTransaction.mSharedElementTargetNames;
        }
        if (names != null) {
            inSharedElements.retainAll(names);
            inSharedElements.retainAll(nameOverrides.values());
        }
        if (sharedElementCallback != null) {
            sharedElementCallback.onMapSharedElements(names, inSharedElements);
            for (int i = names.size() - 1; i >= 0; i--) {
                String name = names.get(i);
                View view = inSharedElements.get(name);
                if (view == null) {
                    String key2 = findKeyForValue(nameOverrides, name);
                    if (key2 != null) {
                        nameOverrides.remove(key2);
                    }
                } else if (!name.equals(ViewCompat.getTransitionName(view)) && (key = findKeyForValue(nameOverrides, name)) != null) {
                    nameOverrides.put(key, ViewCompat.getTransitionName(view));
                }
            }
        } else {
            retainValues(nameOverrides, inSharedElements);
        }
        return inSharedElements;
    }

    private static String findKeyForValue(ArrayMap<String, String> map, String value) {
        int numElements = map.size();
        for (int i = 0; i < numElements; i++) {
            if (value.equals(map.valueAt(i))) {
                return map.keyAt(i);
            }
        }
        return null;
    }

    static View getInEpicenterView(ArrayMap<String, View> inSharedElements, FragmentContainerTransition fragments, Object enterTransition, boolean inIsPop) {
        String targetName;
        BackStackRecord inTransaction = fragments.lastInTransaction;
        if (enterTransition == null || inSharedElements == null || inTransaction.mSharedElementSourceNames == null || inTransaction.mSharedElementSourceNames.isEmpty()) {
            return null;
        }
        if (inIsPop) {
            targetName = (String) inTransaction.mSharedElementSourceNames.get(0);
        } else {
            targetName = (String) inTransaction.mSharedElementTargetNames.get(0);
        }
        return inSharedElements.get(targetName);
    }

    private static void setOutEpicenter(FragmentTransitionImpl impl, Object sharedElementTransition, Object exitTransition, ArrayMap<String, View> outSharedElements, boolean outIsPop, BackStackRecord outTransaction) {
        String sourceName;
        if (outTransaction.mSharedElementSourceNames != null && !outTransaction.mSharedElementSourceNames.isEmpty()) {
            if (outIsPop) {
                sourceName = (String) outTransaction.mSharedElementTargetNames.get(0);
            } else {
                sourceName = (String) outTransaction.mSharedElementSourceNames.get(0);
            }
            View outEpicenterView = outSharedElements.get(sourceName);
            impl.setEpicenter(sharedElementTransition, outEpicenterView);
            if (exitTransition != null) {
                impl.setEpicenter(exitTransition, outEpicenterView);
            }
        }
    }

    private static void retainValues(ArrayMap<String, String> nameOverrides, ArrayMap<String, View> namedViews) {
        for (int i = nameOverrides.size() - 1; i >= 0; i--) {
            if (!namedViews.containsKey(nameOverrides.valueAt(i))) {
                nameOverrides.removeAt(i);
            }
        }
    }

    static void callSharedElementStartEnd(Fragment inFragment, Fragment outFragment, boolean isPop, ArrayMap<String, View> sharedElements, boolean isStart) {
        SharedElementCallback sharedElementCallback;
        if (isPop) {
            sharedElementCallback = outFragment.getEnterTransitionCallback();
        } else {
            sharedElementCallback = inFragment.getEnterTransitionCallback();
        }
        if (sharedElementCallback != null) {
            ArrayList<View> views = new ArrayList<>();
            ArrayList<String> names = new ArrayList<>();
            int count = sharedElements == null ? 0 : sharedElements.size();
            for (int i = 0; i < count; i++) {
                names.add(sharedElements.keyAt(i));
                views.add(sharedElements.valueAt(i));
            }
            if (isStart) {
                sharedElementCallback.onSharedElementStart(names, views, (List<View>) null);
            } else {
                sharedElementCallback.onSharedElementEnd(names, views, (List<View>) null);
            }
        }
    }

    static ArrayList<View> configureEnteringExitingViews(FragmentTransitionImpl impl, Object transition, Fragment fragment, ArrayList<View> sharedElements, View nonExistentView) {
        ArrayList<View> viewList = null;
        if (transition != null) {
            viewList = new ArrayList<>();
            View root = fragment.getView();
            if (root != null) {
                impl.captureTransitioningViews(viewList, root);
            }
            if (sharedElements != null) {
                viewList.removeAll(sharedElements);
            }
            if (!viewList.isEmpty()) {
                viewList.add(nonExistentView);
                impl.addTargets(transition, viewList);
            }
        }
        return viewList;
    }

    static void setViewVisibility(ArrayList<View> views, int visibility) {
        if (views != null) {
            for (int i = views.size() - 1; i >= 0; i--) {
                views.get(i).setVisibility(visibility);
            }
        }
    }

    private static Object mergeTransitions(FragmentTransitionImpl impl, Object enterTransition, Object exitTransition, Object sharedElementTransition, Fragment inFragment, boolean isPop) {
        boolean z;
        boolean overlap = true;
        if (!(enterTransition == null || exitTransition == null || inFragment == null)) {
            if (isPop) {
                z = inFragment.getAllowReturnTransitionOverlap();
            } else {
                z = inFragment.getAllowEnterTransitionOverlap();
            }
            overlap = z;
        }
        if (overlap) {
            return impl.mergeTransitionsTogether(exitTransition, enterTransition, sharedElementTransition);
        }
        return impl.mergeTransitionsInSequence(exitTransition, enterTransition, sharedElementTransition);
    }

    public static void calculateFragments(BackStackRecord transaction, SparseArray<FragmentContainerTransition> transitioningFragments, boolean isReordered) {
        int numOps = transaction.mOps.size();
        for (int opNum = 0; opNum < numOps; opNum++) {
            addToFirstInLastOut(transaction, (FragmentTransaction.C0234Op) transaction.mOps.get(opNum), transitioningFragments, false, isReordered);
        }
    }

    public static void calculatePopFragments(BackStackRecord transaction, SparseArray<FragmentContainerTransition> transitioningFragments, boolean isReordered) {
        if (transaction.mManager.mContainer.onHasView()) {
            for (int opNum = transaction.mOps.size() - 1; opNum >= 0; opNum--) {
                addToFirstInLastOut(transaction, (FragmentTransaction.C0234Op) transaction.mOps.get(opNum), transitioningFragments, true, isReordered);
            }
        }
    }

    static boolean supportsTransition() {
        return (PLATFORM_IMPL == null && SUPPORT_IMPL == null) ? false : true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0032, code lost:
        if (r6 != 7) goto L_0x00a4;
     */
    /* JADX WARNING: Removed duplicated region for block: B:104:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x00ac  */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x00ed A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void addToFirstInLastOut(androidx.fragment.app.BackStackRecord r16, androidx.fragment.app.FragmentTransaction.C0234Op r17, android.util.SparseArray<androidx.fragment.app.FragmentTransition.FragmentContainerTransition> r18, boolean r19, boolean r20) {
        /*
            r0 = r16
            r1 = r17
            r2 = r18
            r3 = r19
            androidx.fragment.app.Fragment r4 = r1.mFragment
            if (r4 != 0) goto L_0x000d
            return
        L_0x000d:
            int r5 = r4.mContainerId
            if (r5 != 0) goto L_0x0012
            return
        L_0x0012:
            if (r3 == 0) goto L_0x001b
            int[] r6 = INVERSE_OPS
            int r7 = r1.mCmd
            r6 = r6[r7]
            goto L_0x001d
        L_0x001b:
            int r6 = r1.mCmd
        L_0x001d:
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 1
            if (r6 == r12) goto L_0x0093
            r13 = 3
            if (r6 == r13) goto L_0x0069
            r13 = 4
            if (r6 == r13) goto L_0x004c
            r13 = 5
            if (r6 == r13) goto L_0x0036
            r13 = 6
            if (r6 == r13) goto L_0x0069
            r13 = 7
            if (r6 == r13) goto L_0x0093
            goto L_0x00a4
        L_0x0036:
            if (r20 == 0) goto L_0x0047
            boolean r13 = r4.mHiddenChanged
            if (r13 == 0) goto L_0x0045
            boolean r13 = r4.mHidden
            if (r13 != 0) goto L_0x0045
            boolean r13 = r4.mAdded
            if (r13 == 0) goto L_0x0045
            r11 = r12
        L_0x0045:
            r7 = r11
            goto L_0x0049
        L_0x0047:
            boolean r7 = r4.mHidden
        L_0x0049:
            r10 = 1
            goto L_0x00a4
        L_0x004c:
            if (r20 == 0) goto L_0x005d
            boolean r13 = r4.mHiddenChanged
            if (r13 == 0) goto L_0x005b
            boolean r13 = r4.mAdded
            if (r13 == 0) goto L_0x005b
            boolean r13 = r4.mHidden
            if (r13 == 0) goto L_0x005b
            r11 = r12
        L_0x005b:
            r9 = r11
            goto L_0x0067
        L_0x005d:
            boolean r13 = r4.mAdded
            if (r13 == 0) goto L_0x0066
            boolean r13 = r4.mHidden
            if (r13 != 0) goto L_0x0066
            r11 = r12
        L_0x0066:
            r9 = r11
        L_0x0067:
            r8 = 1
            goto L_0x00a4
        L_0x0069:
            if (r20 == 0) goto L_0x0087
            boolean r13 = r4.mAdded
            if (r13 != 0) goto L_0x0084
            android.view.View r13 = r4.mView
            if (r13 == 0) goto L_0x0084
            android.view.View r13 = r4.mView
            int r13 = r13.getVisibility()
            if (r13 != 0) goto L_0x0084
            float r13 = r4.mPostponedAlpha
            r14 = 0
            int r13 = (r13 > r14 ? 1 : (r13 == r14 ? 0 : -1))
            if (r13 < 0) goto L_0x0084
            r11 = r12
            goto L_0x0085
        L_0x0084:
        L_0x0085:
            r9 = r11
            goto L_0x0091
        L_0x0087:
            boolean r13 = r4.mAdded
            if (r13 == 0) goto L_0x0090
            boolean r13 = r4.mHidden
            if (r13 != 0) goto L_0x0090
            r11 = r12
        L_0x0090:
            r9 = r11
        L_0x0091:
            r8 = 1
            goto L_0x00a4
        L_0x0093:
            if (r20 == 0) goto L_0x0098
            boolean r7 = r4.mIsNewlyAdded
            goto L_0x00a2
        L_0x0098:
            boolean r13 = r4.mAdded
            if (r13 != 0) goto L_0x00a1
            boolean r13 = r4.mHidden
            if (r13 != 0) goto L_0x00a1
            r11 = r12
        L_0x00a1:
            r7 = r11
        L_0x00a2:
            r10 = 1
        L_0x00a4:
            java.lang.Object r11 = r2.get(r5)
            androidx.fragment.app.FragmentTransition$FragmentContainerTransition r11 = (androidx.fragment.app.FragmentTransition.FragmentContainerTransition) r11
            if (r7 == 0) goto L_0x00b7
            androidx.fragment.app.FragmentTransition$FragmentContainerTransition r11 = ensureContainer(r11, r2, r5)
            r11.lastIn = r4
            r11.lastInIsPop = r3
            r11.lastInTransaction = r0
        L_0x00b7:
            r13 = 0
            if (r20 != 0) goto L_0x00d8
            if (r10 == 0) goto L_0x00d8
            if (r11 == 0) goto L_0x00c4
            androidx.fragment.app.Fragment r14 = r11.firstOut
            if (r14 != r4) goto L_0x00c4
            r11.firstOut = r13
        L_0x00c4:
            androidx.fragment.app.FragmentManager r14 = r0.mManager
            int r15 = r4.mState
            if (r15 >= r12) goto L_0x00d8
            int r15 = r14.mCurState
            if (r15 < r12) goto L_0x00d8
            boolean r15 = r0.mReorderingAllowed
            if (r15 != 0) goto L_0x00d8
            r14.makeActive(r4)
            r14.moveToState((androidx.fragment.app.Fragment) r4, (int) r12)
        L_0x00d8:
            if (r9 == 0) goto L_0x00eb
            if (r11 == 0) goto L_0x00e0
            androidx.fragment.app.Fragment r12 = r11.firstOut
            if (r12 != 0) goto L_0x00eb
        L_0x00e0:
            androidx.fragment.app.FragmentTransition$FragmentContainerTransition r11 = ensureContainer(r11, r2, r5)
            r11.firstOut = r4
            r11.firstOutIsPop = r3
            r11.firstOutTransaction = r0
        L_0x00eb:
            if (r20 != 0) goto L_0x00f7
            if (r8 == 0) goto L_0x00f7
            if (r11 == 0) goto L_0x00f7
            androidx.fragment.app.Fragment r12 = r11.lastIn
            if (r12 != r4) goto L_0x00f7
            r11.lastIn = r13
        L_0x00f7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.FragmentTransition.addToFirstInLastOut(androidx.fragment.app.BackStackRecord, androidx.fragment.app.FragmentTransaction$Op, android.util.SparseArray, boolean, boolean):void");
    }

    private static FragmentContainerTransition ensureContainer(FragmentContainerTransition containerTransition, SparseArray<FragmentContainerTransition> transitioningFragments, int containerId) {
        if (containerTransition != null) {
            return containerTransition;
        }
        FragmentContainerTransition containerTransition2 = new FragmentContainerTransition();
        transitioningFragments.put(containerId, containerTransition2);
        return containerTransition2;
    }

    static class FragmentContainerTransition {
        public Fragment firstOut;
        public boolean firstOutIsPop;
        public BackStackRecord firstOutTransaction;
        public Fragment lastIn;
        public boolean lastInIsPop;
        public BackStackRecord lastInTransaction;

        FragmentContainerTransition() {
        }
    }

    private FragmentTransition() {
    }
}
