package androidx.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.core.app.TaskStackBuilder;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigator;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class NavController {
    private static final String KEY_BACK_STACK = "android-support-nav:controller:backStack";
    static final String KEY_DEEP_LINK_EXTRAS = "android-support-nav:controller:deepLinkExtras";
    static final String KEY_DEEP_LINK_HANDLED = "android-support-nav:controller:deepLinkHandled";
    static final String KEY_DEEP_LINK_IDS = "android-support-nav:controller:deepLinkIds";
    public static final String KEY_DEEP_LINK_INTENT = "android-support-nav:controller:deepLinkIntent";
    private static final String KEY_NAVIGATOR_STATE = "android-support-nav:controller:navigatorState";
    private static final String KEY_NAVIGATOR_STATE_NAMES = "android-support-nav:controller:navigatorState:names";
    private static final String TAG = "NavController";
    private Activity mActivity;
    final Deque<NavBackStackEntry> mBackStack = new ArrayDeque();
    private Parcelable[] mBackStackToRestore;
    private final Context mContext;
    private boolean mDeepLinkHandled;
    private boolean mEnableOnBackPressedCallback = true;
    NavGraph mGraph;
    private NavInflater mInflater;
    private final LifecycleObserver mLifecycleObserver = new LifecycleEventObserver() {
        public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
            if (NavController.this.mGraph != null) {
                for (NavBackStackEntry entry : NavController.this.mBackStack) {
                    entry.handleLifecycleEvent(event);
                }
            }
        }
    };
    private LifecycleOwner mLifecycleOwner;
    private NavigatorProvider mNavigatorProvider = new NavigatorProvider();
    private Bundle mNavigatorStateToRestore;
    private final OnBackPressedCallback mOnBackPressedCallback = new OnBackPressedCallback(false) {
        public void handleOnBackPressed() {
            NavController.this.popBackStack();
        }
    };
    private final CopyOnWriteArrayList<OnDestinationChangedListener> mOnDestinationChangedListeners = new CopyOnWriteArrayList<>();
    private NavControllerViewModel mViewModel;

    public interface OnDestinationChangedListener {
        void onDestinationChanged(NavController navController, NavDestination navDestination, Bundle bundle);
    }

    public NavController(Context context) {
        this.mContext = context;
        while (true) {
            if (!(context instanceof ContextWrapper)) {
                break;
            } else if (context instanceof Activity) {
                this.mActivity = (Activity) context;
                break;
            } else {
                context = ((ContextWrapper) context).getBaseContext();
            }
        }
        this.mNavigatorProvider.addNavigator(new NavGraphNavigator(this.mNavigatorProvider));
        this.mNavigatorProvider.addNavigator(new ActivityNavigator(this.mContext));
    }

    public Deque<NavBackStackEntry> getBackStack() {
        return this.mBackStack;
    }

    /* access modifiers changed from: package-private */
    public Context getContext() {
        return this.mContext;
    }

    public NavigatorProvider getNavigatorProvider() {
        return this.mNavigatorProvider;
    }

    public void setNavigatorProvider(NavigatorProvider navigatorProvider) {
        if (this.mBackStack.isEmpty()) {
            this.mNavigatorProvider = navigatorProvider;
            return;
        }
        throw new IllegalStateException("NavigatorProvider must be set before setGraph call");
    }

    public void addOnDestinationChangedListener(OnDestinationChangedListener listener) {
        if (!this.mBackStack.isEmpty()) {
            NavBackStackEntry backStackEntry = this.mBackStack.peekLast();
            listener.onDestinationChanged(this, backStackEntry.getDestination(), backStackEntry.getArguments());
        }
        this.mOnDestinationChangedListeners.add(listener);
    }

    public void removeOnDestinationChangedListener(OnDestinationChangedListener listener) {
        this.mOnDestinationChangedListeners.remove(listener);
    }

    public boolean popBackStack() {
        if (this.mBackStack.isEmpty()) {
            return false;
        }
        return popBackStack(getCurrentDestination().getId(), true);
    }

    public boolean popBackStack(int destinationId, boolean inclusive) {
        return popBackStackInternal(destinationId, inclusive) && dispatchOnDestinationChanged();
    }

    /* access modifiers changed from: package-private */
    public boolean popBackStackInternal(int destinationId, boolean inclusive) {
        if (this.mBackStack.isEmpty()) {
            return false;
        }
        ArrayList<Navigator<?>> popOperations = new ArrayList<>();
        Iterator<NavBackStackEntry> iterator = this.mBackStack.descendingIterator();
        boolean foundDestination = false;
        while (true) {
            if (!iterator.hasNext()) {
                break;
            }
            NavDestination destination = iterator.next().getDestination();
            Navigator<?> navigator = this.mNavigatorProvider.getNavigator(destination.getNavigatorName());
            if (inclusive || destination.getId() != destinationId) {
                popOperations.add(navigator);
            }
            if (destination.getId() == destinationId) {
                foundDestination = true;
                break;
            }
        }
        if (!foundDestination) {
            Log.i(TAG, "Ignoring popBackStack to destination " + NavDestination.getDisplayName(this.mContext, destinationId) + " as it was not found on the current back stack");
            return false;
        }
        boolean popped = false;
        Iterator<Navigator<?>> it = popOperations.iterator();
        while (it.hasNext() && it.next().popBackStack()) {
            NavBackStackEntry entry = this.mBackStack.removeLast();
            entry.setMaxLifecycle(Lifecycle.State.DESTROYED);
            NavControllerViewModel navControllerViewModel = this.mViewModel;
            if (navControllerViewModel != null) {
                navControllerViewModel.clear(entry.mId);
            }
            popped = true;
        }
        updateOnBackPressedCallbackEnabled();
        return popped;
    }

    public boolean navigateUp() {
        if (getDestinationCountOnBackStack() != 1) {
            return popBackStack();
        }
        NavDestination currentDestination = getCurrentDestination();
        int destId = currentDestination.getId();
        for (NavGraph parent = currentDestination.getParent(); parent != null; parent = parent.getParent()) {
            if (parent.getStartDestination() != destId) {
                Bundle args = new Bundle();
                Activity activity = this.mActivity;
                if (!(activity == null || activity.getIntent() == null || this.mActivity.getIntent().getData() == null)) {
                    args.putParcelable(KEY_DEEP_LINK_INTENT, this.mActivity.getIntent());
                    NavDestination.DeepLinkMatch matchingDeepLink = this.mGraph.matchDeepLink(new NavDeepLinkRequest(this.mActivity.getIntent()));
                    if (matchingDeepLink != null) {
                        args.putAll(matchingDeepLink.getMatchingArgs());
                    }
                }
                new NavDeepLinkBuilder(this).setDestination(parent.getId()).setArguments(args).createTaskStackBuilder().startActivities();
                Activity activity2 = this.mActivity;
                if (activity2 != null) {
                    activity2.finish();
                }
                return true;
            }
            destId = parent.getId();
        }
        return false;
    }

    private int getDestinationCountOnBackStack() {
        int count = 0;
        for (NavBackStackEntry entry : this.mBackStack) {
            if (!(entry.getDestination() instanceof NavGraph)) {
                count++;
            }
        }
        return count;
    }

    /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP:0: B:0:0x0000->B:5:0x002d, LOOP_START, MTH_ENTER_BLOCK] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean dispatchOnDestinationChanged() {
        /*
            r10 = this;
        L_0x0000:
            java.util.Deque<androidx.navigation.NavBackStackEntry> r0 = r10.mBackStack
            boolean r0 = r0.isEmpty()
            r1 = 1
            if (r0 != 0) goto L_0x0030
            java.util.Deque<androidx.navigation.NavBackStackEntry> r0 = r10.mBackStack
            java.lang.Object r0 = r0.peekLast()
            androidx.navigation.NavBackStackEntry r0 = (androidx.navigation.NavBackStackEntry) r0
            androidx.navigation.NavDestination r0 = r0.getDestination()
            boolean r0 = r0 instanceof androidx.navigation.NavGraph
            if (r0 == 0) goto L_0x0030
            java.util.Deque<androidx.navigation.NavBackStackEntry> r0 = r10.mBackStack
            java.lang.Object r0 = r0.peekLast()
            androidx.navigation.NavBackStackEntry r0 = (androidx.navigation.NavBackStackEntry) r0
            androidx.navigation.NavDestination r0 = r0.getDestination()
            int r0 = r0.getId()
            boolean r0 = r10.popBackStackInternal(r0, r1)
            if (r0 == 0) goto L_0x0030
            goto L_0x0000
        L_0x0030:
            java.util.Deque<androidx.navigation.NavBackStackEntry> r0 = r10.mBackStack
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x0116
            java.util.Deque<androidx.navigation.NavBackStackEntry> r0 = r10.mBackStack
            java.lang.Object r0 = r0.peekLast()
            androidx.navigation.NavBackStackEntry r0 = (androidx.navigation.NavBackStackEntry) r0
            androidx.navigation.NavDestination r0 = r0.getDestination()
            r2 = 0
            boolean r3 = r0 instanceof androidx.navigation.FloatingWindow
            if (r3 == 0) goto L_0x006a
            java.util.Deque<androidx.navigation.NavBackStackEntry> r3 = r10.mBackStack
            java.util.Iterator r3 = r3.descendingIterator()
        L_0x004f:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x006a
            java.lang.Object r4 = r3.next()
            androidx.navigation.NavBackStackEntry r4 = (androidx.navigation.NavBackStackEntry) r4
            androidx.navigation.NavDestination r4 = r4.getDestination()
            boolean r5 = r4 instanceof androidx.navigation.NavGraph
            if (r5 != 0) goto L_0x0069
            boolean r5 = r4 instanceof androidx.navigation.FloatingWindow
            if (r5 != 0) goto L_0x0069
            r2 = r4
            goto L_0x006a
        L_0x0069:
            goto L_0x004f
        L_0x006a:
            java.util.HashMap r3 = new java.util.HashMap
            r3.<init>()
            java.util.Deque<androidx.navigation.NavBackStackEntry> r4 = r10.mBackStack
            java.util.Iterator r4 = r4.descendingIterator()
        L_0x0075:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x00cd
            java.lang.Object r5 = r4.next()
            androidx.navigation.NavBackStackEntry r5 = (androidx.navigation.NavBackStackEntry) r5
            androidx.lifecycle.Lifecycle$State r6 = r5.getMaxLifecycle()
            androidx.navigation.NavDestination r7 = r5.getDestination()
            if (r0 == 0) goto L_0x00a3
            int r8 = r7.getId()
            int r9 = r0.getId()
            if (r8 != r9) goto L_0x00a3
            androidx.lifecycle.Lifecycle$State r8 = androidx.lifecycle.Lifecycle.State.RESUMED
            if (r6 == r8) goto L_0x009e
            androidx.lifecycle.Lifecycle$State r8 = androidx.lifecycle.Lifecycle.State.RESUMED
            r3.put(r5, r8)
        L_0x009e:
            androidx.navigation.NavGraph r0 = r0.getParent()
            goto L_0x00cc
        L_0x00a3:
            if (r2 == 0) goto L_0x00c7
            int r8 = r7.getId()
            int r9 = r2.getId()
            if (r8 != r9) goto L_0x00c7
            androidx.lifecycle.Lifecycle$State r8 = androidx.lifecycle.Lifecycle.State.RESUMED
            if (r6 != r8) goto L_0x00b9
            androidx.lifecycle.Lifecycle$State r8 = androidx.lifecycle.Lifecycle.State.STARTED
            r5.setMaxLifecycle(r8)
            goto L_0x00c2
        L_0x00b9:
            androidx.lifecycle.Lifecycle$State r8 = androidx.lifecycle.Lifecycle.State.STARTED
            if (r6 == r8) goto L_0x00c2
            androidx.lifecycle.Lifecycle$State r8 = androidx.lifecycle.Lifecycle.State.STARTED
            r3.put(r5, r8)
        L_0x00c2:
            androidx.navigation.NavGraph r2 = r2.getParent()
            goto L_0x00cc
        L_0x00c7:
            androidx.lifecycle.Lifecycle$State r8 = androidx.lifecycle.Lifecycle.State.CREATED
            r5.setMaxLifecycle(r8)
        L_0x00cc:
            goto L_0x0075
        L_0x00cd:
            java.util.Deque<androidx.navigation.NavBackStackEntry> r5 = r10.mBackStack
            java.util.Iterator r4 = r5.iterator()
        L_0x00d3:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x00ef
            java.lang.Object r5 = r4.next()
            androidx.navigation.NavBackStackEntry r5 = (androidx.navigation.NavBackStackEntry) r5
            java.lang.Object r6 = r3.get(r5)
            androidx.lifecycle.Lifecycle$State r6 = (androidx.lifecycle.Lifecycle.State) r6
            if (r6 == 0) goto L_0x00eb
            r5.setMaxLifecycle(r6)
            goto L_0x00ee
        L_0x00eb:
            r5.updateState()
        L_0x00ee:
            goto L_0x00d3
        L_0x00ef:
            java.util.Deque<androidx.navigation.NavBackStackEntry> r5 = r10.mBackStack
            java.lang.Object r5 = r5.peekLast()
            androidx.navigation.NavBackStackEntry r5 = (androidx.navigation.NavBackStackEntry) r5
            java.util.concurrent.CopyOnWriteArrayList<androidx.navigation.NavController$OnDestinationChangedListener> r6 = r10.mOnDestinationChangedListeners
            java.util.Iterator r6 = r6.iterator()
        L_0x00fd:
            boolean r7 = r6.hasNext()
            if (r7 == 0) goto L_0x0115
            java.lang.Object r7 = r6.next()
            androidx.navigation.NavController$OnDestinationChangedListener r7 = (androidx.navigation.NavController.OnDestinationChangedListener) r7
            androidx.navigation.NavDestination r8 = r5.getDestination()
            android.os.Bundle r9 = r5.getArguments()
            r7.onDestinationChanged(r10, r8, r9)
            goto L_0x00fd
        L_0x0115:
            return r1
        L_0x0116:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.NavController.dispatchOnDestinationChanged():boolean");
    }

    public NavInflater getNavInflater() {
        if (this.mInflater == null) {
            this.mInflater = new NavInflater(this.mContext, this.mNavigatorProvider);
        }
        return this.mInflater;
    }

    public void setGraph(int graphResId) {
        setGraph(graphResId, (Bundle) null);
    }

    public void setGraph(int graphResId, Bundle startDestinationArgs) {
        setGraph(getNavInflater().inflate(graphResId), startDestinationArgs);
    }

    public void setGraph(NavGraph graph) {
        setGraph(graph, (Bundle) null);
    }

    public void setGraph(NavGraph graph, Bundle startDestinationArgs) {
        NavGraph navGraph = this.mGraph;
        if (navGraph != null) {
            popBackStackInternal(navGraph.getId(), true);
        }
        this.mGraph = graph;
        onGraphCreated(startDestinationArgs);
    }

    private void onGraphCreated(Bundle startDestinationArgs) {
        Activity activity;
        ArrayList<String> navigatorNames;
        Bundle bundle = this.mNavigatorStateToRestore;
        if (!(bundle == null || (navigatorNames = bundle.getStringArrayList(KEY_NAVIGATOR_STATE_NAMES)) == null)) {
            Iterator<String> it = navigatorNames.iterator();
            while (it.hasNext()) {
                String name = it.next();
                Navigator<?> navigator = this.mNavigatorProvider.getNavigator(name);
                Bundle bundle2 = this.mNavigatorStateToRestore.getBundle(name);
                if (bundle2 != null) {
                    navigator.onRestoreState(bundle2);
                }
            }
        }
        Parcelable[] parcelableArr = this.mBackStackToRestore;
        boolean deepLinked = false;
        if (parcelableArr != null) {
            int length = parcelableArr.length;
            int i = 0;
            while (i < length) {
                NavBackStackEntryState state = (NavBackStackEntryState) parcelableArr[i];
                NavDestination node = findDestination(state.getDestinationId());
                if (node != null) {
                    Bundle args = state.getArgs();
                    if (args != null) {
                        args.setClassLoader(this.mContext.getClassLoader());
                    }
                    Bundle bundle3 = args;
                    this.mBackStack.add(new NavBackStackEntry(this.mContext, node, args, this.mLifecycleOwner, this.mViewModel, state.getUUID(), state.getSavedState()));
                    i++;
                } else {
                    throw new IllegalStateException("unknown destination during restore: " + this.mContext.getResources().getResourceName(state.getDestinationId()));
                }
            }
            updateOnBackPressedCallbackEnabled();
            this.mBackStackToRestore = null;
        }
        if (this.mGraph == null || !this.mBackStack.isEmpty()) {
            Bundle bundle4 = startDestinationArgs;
            dispatchOnDestinationChanged();
            return;
        }
        if (!this.mDeepLinkHandled && (activity = this.mActivity) != null && handleDeepLink(activity.getIntent())) {
            deepLinked = true;
        }
        if (!deepLinked) {
            navigate((NavDestination) this.mGraph, startDestinationArgs, (NavOptions) null, (Navigator.Extras) null);
        } else {
            Bundle bundle5 = startDestinationArgs;
        }
    }

    public boolean handleDeepLink(Intent intent) {
        Object obj;
        NavGraph graph;
        NavDestination.DeepLinkMatch matchingDeepLink;
        Intent intent2 = intent;
        if (intent2 == null) {
            return false;
        }
        Bundle extras = intent.getExtras();
        int[] deepLink = extras != null ? extras.getIntArray(KEY_DEEP_LINK_IDS) : null;
        Bundle bundle = new Bundle();
        Bundle deepLinkExtras = extras != null ? extras.getBundle(KEY_DEEP_LINK_EXTRAS) : null;
        if (deepLinkExtras != null) {
            bundle.putAll(deepLinkExtras);
        }
        if (!((deepLink != null && deepLink.length != 0) || intent.getData() == null || (matchingDeepLink = this.mGraph.matchDeepLink(new NavDeepLinkRequest(intent2))) == null)) {
            deepLink = matchingDeepLink.getDestination().buildDeepLinkIds();
            bundle.putAll(matchingDeepLink.getMatchingArgs());
        }
        if (deepLink == null || deepLink.length == 0) {
            return false;
        }
        String invalidDestinationDisplayName = findInvalidDestinationDisplayNameInDeepLink(deepLink);
        if (invalidDestinationDisplayName != null) {
            Log.i(TAG, "Could not find destination " + invalidDestinationDisplayName + " in the navigation graph, ignoring the deep link from " + intent2);
            return false;
        }
        bundle.putParcelable(KEY_DEEP_LINK_INTENT, intent2);
        int flags = intent.getFlags();
        int i = 1;
        if ((flags & 268435456) != 0 && (flags & 32768) == 0) {
            intent2.addFlags(32768);
            TaskStackBuilder.create(this.mContext).addNextIntentWithParentStack(intent2).startActivities();
            Activity activity = this.mActivity;
            if (activity != null) {
                activity.finish();
                this.mActivity.overridePendingTransition(0, 0);
            }
            return true;
        } else if ((268435456 & flags) != 0) {
            if (!this.mBackStack.isEmpty()) {
                popBackStackInternal(this.mGraph.getId(), true);
            }
            int destinationId = 0;
            while (destinationId < deepLink.length) {
                int index = destinationId + 1;
                int index2 = deepLink[destinationId];
                NavDestination node = findDestination(index2);
                if (node != null) {
                    navigate(node, bundle, new NavOptions.Builder().setEnterAnim(0).setExitAnim(0).build(), (Navigator.Extras) null);
                    destinationId = index;
                } else {
                    throw new IllegalStateException("unknown destination during deep link: " + NavDestination.getDisplayName(this.mContext, index2));
                }
            }
            return true;
        } else {
            NavGraph graph2 = this.mGraph;
            int i2 = 0;
            while (i2 < deepLink.length) {
                int destinationId2 = deepLink[i2];
                NavDestination node2 = i2 == 0 ? this.mGraph : graph2.findNode(destinationId2);
                if (node2 != null) {
                    if (i2 != deepLink.length - i) {
                        NavDestination navDestination = node2;
                        while (true) {
                            graph = (NavGraph) navDestination;
                            if (!(graph.findNode(graph.getStartDestination()) instanceof NavGraph)) {
                                break;
                            }
                            navDestination = graph.findNode(graph.getStartDestination());
                        }
                        graph2 = graph;
                        obj = null;
                    } else {
                        obj = null;
                        navigate(node2, node2.addInDefaultArgs(bundle), new NavOptions.Builder().setPopUpTo(this.mGraph.getId(), true).setEnterAnim(0).setExitAnim(0).build(), (Navigator.Extras) null);
                    }
                    i2++;
                    Intent intent3 = intent;
                    Object obj2 = obj;
                    i = 1;
                } else {
                    throw new IllegalStateException("unknown destination during deep link: " + NavDestination.getDisplayName(this.mContext, destinationId2));
                }
            }
            this.mDeepLinkHandled = true;
            return true;
        }
    }

    private String findInvalidDestinationDisplayNameInDeepLink(int[] deepLink) {
        NavGraph graph = this.mGraph;
        int i = 0;
        while (true) {
            NavDestination node = null;
            if (i >= deepLink.length) {
                return null;
            }
            int destinationId = deepLink[i];
            if (i != 0) {
                node = graph.findNode(destinationId);
            } else if (this.mGraph.getId() == destinationId) {
                node = this.mGraph;
            }
            if (node == null) {
                return NavDestination.getDisplayName(this.mContext, destinationId);
            }
            if (i != deepLink.length - 1) {
                NavDestination navDestination = node;
                while (true) {
                    graph = (NavGraph) navDestination;
                    if (!(graph.findNode(graph.getStartDestination()) instanceof NavGraph)) {
                        break;
                    }
                    navDestination = graph.findNode(graph.getStartDestination());
                }
            }
            i++;
        }
    }

    public NavGraph getGraph() {
        NavGraph navGraph = this.mGraph;
        if (navGraph != null) {
            return navGraph;
        }
        throw new IllegalStateException("You must call setGraph() before calling getGraph()");
    }

    public NavDestination getCurrentDestination() {
        NavBackStackEntry entry = getCurrentBackStackEntry();
        if (entry != null) {
            return entry.getDestination();
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public NavDestination findDestination(int destinationId) {
        NavDestination currentNode;
        NavGraph currentGraph;
        NavGraph navGraph = this.mGraph;
        if (navGraph == null) {
            return null;
        }
        if (navGraph.getId() == destinationId) {
            return this.mGraph;
        }
        if (this.mBackStack.isEmpty()) {
            currentNode = this.mGraph;
        } else {
            currentNode = this.mBackStack.getLast().getDestination();
        }
        if (currentNode instanceof NavGraph) {
            currentGraph = (NavGraph) currentNode;
        } else {
            currentGraph = currentNode.getParent();
        }
        return currentGraph.findNode(destinationId);
    }

    public void navigate(int resId) {
        navigate(resId, (Bundle) null);
    }

    public void navigate(int resId, Bundle args) {
        navigate(resId, args, (NavOptions) null);
    }

    public void navigate(int resId, Bundle args, NavOptions navOptions) {
        navigate(resId, args, navOptions, (Navigator.Extras) null);
    }

    public void navigate(int resId, Bundle args, NavOptions navOptions, Navigator.Extras navigatorExtras) {
        NavDestination currentNode;
        String str;
        if (this.mBackStack.isEmpty()) {
            currentNode = this.mGraph;
        } else {
            currentNode = this.mBackStack.getLast().getDestination();
        }
        if (currentNode != null) {
            int destId = resId;
            NavAction navAction = currentNode.getAction(resId);
            Bundle combinedArgs = null;
            if (navAction != null) {
                if (navOptions == null) {
                    navOptions = navAction.getNavOptions();
                }
                destId = navAction.getDestinationId();
                Bundle navActionArgs = navAction.getDefaultArguments();
                if (navActionArgs != null) {
                    combinedArgs = new Bundle();
                    combinedArgs.putAll(navActionArgs);
                }
            }
            if (args != null) {
                if (combinedArgs == null) {
                    combinedArgs = new Bundle();
                }
                combinedArgs.putAll(args);
            }
            if (destId == 0 && navOptions != null && navOptions.getPopUpTo() != -1) {
                popBackStack(navOptions.getPopUpTo(), navOptions.isPopUpToInclusive());
            } else if (destId != 0) {
                NavDestination node = findDestination(destId);
                if (node == null) {
                    StringBuilder append = new StringBuilder().append("navigation destination ").append(NavDestination.getDisplayName(this.mContext, destId));
                    if (navAction != null) {
                        str = " referenced from action " + NavDestination.getDisplayName(this.mContext, resId);
                    } else {
                        str = "";
                    }
                    throw new IllegalArgumentException(append.append(str).append(" is unknown to this NavController").toString());
                }
                navigate(node, combinedArgs, navOptions, navigatorExtras);
            } else {
                throw new IllegalArgumentException("Destination id == 0 can only be used in conjunction with a valid navOptions.popUpTo");
            }
        } else {
            throw new IllegalStateException("no current navigation node");
        }
    }

    public void navigate(Uri deepLink) {
        navigate(new NavDeepLinkRequest(deepLink, (String) null, (String) null));
    }

    public void navigate(Uri deepLink, NavOptions navOptions) {
        navigate(new NavDeepLinkRequest(deepLink, (String) null, (String) null), navOptions);
    }

    public void navigate(Uri deepLink, NavOptions navOptions, Navigator.Extras navigatorExtras) {
        navigate(new NavDeepLinkRequest(deepLink, (String) null, (String) null), navOptions, navigatorExtras);
    }

    public void navigate(NavDeepLinkRequest request) {
        navigate(request, (NavOptions) null);
    }

    public void navigate(NavDeepLinkRequest request, NavOptions navOptions) {
        navigate(request, navOptions, (Navigator.Extras) null);
    }

    public void navigate(NavDeepLinkRequest request, NavOptions navOptions, Navigator.Extras navigatorExtras) {
        NavDestination.DeepLinkMatch deepLinkMatch = this.mGraph.matchDeepLink(request);
        if (deepLinkMatch != null) {
            navigate(deepLinkMatch.getDestination(), deepLinkMatch.getMatchingArgs(), navOptions, navigatorExtras);
            return;
        }
        throw new IllegalArgumentException("navigation destination that matches request " + request + " is unknown to this NavController");
    }

    /* JADX WARNING: Removed duplicated region for block: B:9:0x0039 A[LOOP:0: B:9:0x0039->B:14:0x0066, LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void navigate(androidx.navigation.NavDestination r22, android.os.Bundle r23, androidx.navigation.NavOptions r24, androidx.navigation.Navigator.Extras r25) {
        /*
            r21 = this;
            r0 = r21
            r1 = r23
            r2 = r24
            r3 = 0
            r4 = 0
            if (r2 == 0) goto L_0x001d
            int r5 = r24.getPopUpTo()
            r6 = -1
            if (r5 == r6) goto L_0x001d
            int r5 = r24.getPopUpTo()
            boolean r6 = r24.isPopUpToInclusive()
            boolean r3 = r0.popBackStackInternal(r5, r6)
        L_0x001d:
            androidx.navigation.NavigatorProvider r5 = r0.mNavigatorProvider
            java.lang.String r6 = r22.getNavigatorName()
            androidx.navigation.Navigator r5 = r5.getNavigator((java.lang.String) r6)
            android.os.Bundle r12 = r22.addInDefaultArgs(r23)
            r13 = r22
            r14 = r25
            androidx.navigation.NavDestination r15 = r5.navigate(r13, r12, r2, r14)
            if (r15 == 0) goto L_0x00e5
            boolean r6 = r15 instanceof androidx.navigation.FloatingWindow
            if (r6 != 0) goto L_0x0069
        L_0x0039:
            java.util.Deque<androidx.navigation.NavBackStackEntry> r6 = r0.mBackStack
            boolean r6 = r6.isEmpty()
            if (r6 != 0) goto L_0x0069
            java.util.Deque<androidx.navigation.NavBackStackEntry> r6 = r0.mBackStack
            java.lang.Object r6 = r6.peekLast()
            androidx.navigation.NavBackStackEntry r6 = (androidx.navigation.NavBackStackEntry) r6
            androidx.navigation.NavDestination r6 = r6.getDestination()
            boolean r6 = r6 instanceof androidx.navigation.FloatingWindow
            if (r6 == 0) goto L_0x0069
            java.util.Deque<androidx.navigation.NavBackStackEntry> r6 = r0.mBackStack
            java.lang.Object r6 = r6.peekLast()
            androidx.navigation.NavBackStackEntry r6 = (androidx.navigation.NavBackStackEntry) r6
            androidx.navigation.NavDestination r6 = r6.getDestination()
            int r6 = r6.getId()
            r7 = 1
            boolean r6 = r0.popBackStackInternal(r6, r7)
            if (r6 == 0) goto L_0x0069
            goto L_0x0039
        L_0x0069:
            java.util.Deque<androidx.navigation.NavBackStackEntry> r6 = r0.mBackStack
            boolean r6 = r6.isEmpty()
            if (r6 == 0) goto L_0x0086
            androidx.navigation.NavBackStackEntry r16 = new androidx.navigation.NavBackStackEntry
            android.content.Context r7 = r0.mContext
            androidx.navigation.NavGraph r8 = r0.mGraph
            androidx.lifecycle.LifecycleOwner r10 = r0.mLifecycleOwner
            androidx.navigation.NavControllerViewModel r11 = r0.mViewModel
            r6 = r16
            r9 = r12
            r6.<init>(r7, r8, r9, r10, r11)
            java.util.Deque<androidx.navigation.NavBackStackEntry> r7 = r0.mBackStack
            r7.add(r6)
        L_0x0086:
            java.util.ArrayDeque r6 = new java.util.ArrayDeque
            r6.<init>()
            r11 = r6
            r6 = r15
            r16 = r6
        L_0x008f:
            if (r16 == 0) goto L_0x00c5
            int r6 = r16.getId()
            androidx.navigation.NavDestination r6 = r0.findDestination(r6)
            if (r6 != 0) goto L_0x00c5
            androidx.navigation.NavGraph r17 = r16.getParent()
            if (r17 == 0) goto L_0x00bc
            androidx.navigation.NavBackStackEntry r18 = new androidx.navigation.NavBackStackEntry
            android.content.Context r7 = r0.mContext
            androidx.lifecycle.LifecycleOwner r10 = r0.mLifecycleOwner
            androidx.navigation.NavControllerViewModel r9 = r0.mViewModel
            r6 = r18
            r8 = r17
            r19 = r9
            r9 = r12
            r20 = r4
            r4 = r11
            r11 = r19
            r6.<init>(r7, r8, r9, r10, r11)
            r4.addFirst(r6)
            goto L_0x00bf
        L_0x00bc:
            r20 = r4
            r4 = r11
        L_0x00bf:
            r16 = r17
            r11 = r4
            r4 = r20
            goto L_0x008f
        L_0x00c5:
            r20 = r4
            r4 = r11
            java.util.Deque<androidx.navigation.NavBackStackEntry> r6 = r0.mBackStack
            r6.addAll(r4)
            androidx.navigation.NavBackStackEntry r17 = new androidx.navigation.NavBackStackEntry
            android.content.Context r7 = r0.mContext
            android.os.Bundle r9 = r15.addInDefaultArgs(r12)
            androidx.lifecycle.LifecycleOwner r10 = r0.mLifecycleOwner
            androidx.navigation.NavControllerViewModel r11 = r0.mViewModel
            r6 = r17
            r8 = r15
            r6.<init>(r7, r8, r9, r10, r11)
            java.util.Deque<androidx.navigation.NavBackStackEntry> r7 = r0.mBackStack
            r7.add(r6)
            goto L_0x010a
        L_0x00e5:
            r20 = r4
            if (r2 == 0) goto L_0x010a
            boolean r4 = r24.shouldLaunchSingleTop()
            if (r4 == 0) goto L_0x010a
            r4 = 1
            java.util.Deque<androidx.navigation.NavBackStackEntry> r6 = r0.mBackStack
            java.lang.Object r6 = r6.peekLast()
            if (r6 == 0) goto L_0x010d
            if (r1 == 0) goto L_0x010d
            java.util.Deque<androidx.navigation.NavBackStackEntry> r6 = r0.mBackStack
            java.lang.Object r6 = r6.peekLast()
            androidx.navigation.NavBackStackEntry r6 = (androidx.navigation.NavBackStackEntry) r6
            android.os.Bundle r6 = r6.getArguments()
            r6.putAll(r1)
            goto L_0x010d
        L_0x010a:
            r4 = r20
        L_0x010d:
            r21.updateOnBackPressedCallbackEnabled()
            if (r3 != 0) goto L_0x0116
            if (r15 != 0) goto L_0x0116
            if (r4 == 0) goto L_0x0119
        L_0x0116:
            r21.dispatchOnDestinationChanged()
        L_0x0119:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.NavController.navigate(androidx.navigation.NavDestination, android.os.Bundle, androidx.navigation.NavOptions, androidx.navigation.Navigator$Extras):void");
    }

    public void navigate(NavDirections directions) {
        navigate(directions.getActionId(), directions.getArguments());
    }

    public void navigate(NavDirections directions, NavOptions navOptions) {
        navigate(directions.getActionId(), directions.getArguments(), navOptions);
    }

    public void navigate(NavDirections directions, Navigator.Extras navigatorExtras) {
        navigate(directions.getActionId(), directions.getArguments(), (NavOptions) null, navigatorExtras);
    }

    public NavDeepLinkBuilder createDeepLink() {
        return new NavDeepLinkBuilder(this);
    }

    public Bundle saveState() {
        Bundle b = null;
        ArrayList<String> navigatorNames = new ArrayList<>();
        Bundle navigatorState = new Bundle();
        for (Map.Entry<String, Navigator<? extends NavDestination>> entry : this.mNavigatorProvider.getNavigators().entrySet()) {
            String name = entry.getKey();
            Bundle savedState = entry.getValue().onSaveState();
            if (savedState != null) {
                navigatorNames.add(name);
                navigatorState.putBundle(name, savedState);
            }
        }
        if (!navigatorNames.isEmpty()) {
            b = new Bundle();
            navigatorState.putStringArrayList(KEY_NAVIGATOR_STATE_NAMES, navigatorNames);
            b.putBundle(KEY_NAVIGATOR_STATE, navigatorState);
        }
        if (!this.mBackStack.isEmpty()) {
            if (b == null) {
                b = new Bundle();
            }
            Parcelable[] backStack = new Parcelable[this.mBackStack.size()];
            int index = 0;
            for (NavBackStackEntry backStackEntry : this.mBackStack) {
                backStack[index] = new NavBackStackEntryState(backStackEntry);
                index++;
            }
            b.putParcelableArray(KEY_BACK_STACK, backStack);
        }
        if (this.mDeepLinkHandled) {
            if (b == null) {
                b = new Bundle();
            }
            b.putBoolean(KEY_DEEP_LINK_HANDLED, this.mDeepLinkHandled);
        }
        return b;
    }

    public void restoreState(Bundle navState) {
        if (navState != null) {
            navState.setClassLoader(this.mContext.getClassLoader());
            this.mNavigatorStateToRestore = navState.getBundle(KEY_NAVIGATOR_STATE);
            this.mBackStackToRestore = navState.getParcelableArray(KEY_BACK_STACK);
            this.mDeepLinkHandled = navState.getBoolean(KEY_DEEP_LINK_HANDLED);
        }
    }

    /* access modifiers changed from: package-private */
    public void setLifecycleOwner(LifecycleOwner owner) {
        this.mLifecycleOwner = owner;
        owner.getLifecycle().addObserver(this.mLifecycleObserver);
    }

    /* access modifiers changed from: package-private */
    public void setOnBackPressedDispatcher(OnBackPressedDispatcher dispatcher) {
        if (this.mLifecycleOwner != null) {
            this.mOnBackPressedCallback.remove();
            dispatcher.addCallback(this.mLifecycleOwner, this.mOnBackPressedCallback);
            return;
        }
        throw new IllegalStateException("You must call setLifecycleOwner() before calling setOnBackPressedDispatcher()");
    }

    /* access modifiers changed from: package-private */
    public void enableOnBackPressed(boolean enabled) {
        this.mEnableOnBackPressedCallback = enabled;
        updateOnBackPressedCallbackEnabled();
    }

    private void updateOnBackPressedCallbackEnabled() {
        OnBackPressedCallback onBackPressedCallback = this.mOnBackPressedCallback;
        boolean z = true;
        if (!this.mEnableOnBackPressedCallback || getDestinationCountOnBackStack() <= 1) {
            z = false;
        }
        onBackPressedCallback.setEnabled(z);
    }

    /* access modifiers changed from: package-private */
    public void setViewModelStore(ViewModelStore viewModelStore) {
        if (this.mBackStack.isEmpty()) {
            this.mViewModel = NavControllerViewModel.getInstance(viewModelStore);
            return;
        }
        throw new IllegalStateException("ViewModelStore should be set before setGraph call");
    }

    public ViewModelStoreOwner getViewModelStoreOwner(int navGraphId) {
        if (this.mViewModel != null) {
            NavBackStackEntry lastFromBackStack = getBackStackEntry(navGraphId);
            if (lastFromBackStack.getDestination() instanceof NavGraph) {
                return lastFromBackStack;
            }
            throw new IllegalArgumentException("No NavGraph with ID " + navGraphId + " is on the NavController's back stack");
        }
        throw new IllegalStateException("You must call setViewModelStore() before calling getViewModelStoreOwner().");
    }

    public NavBackStackEntry getBackStackEntry(int destinationId) {
        NavBackStackEntry lastFromBackStack = null;
        Iterator<NavBackStackEntry> iterator = this.mBackStack.descendingIterator();
        while (true) {
            if (!iterator.hasNext()) {
                break;
            }
            NavBackStackEntry entry = iterator.next();
            if (entry.getDestination().getId() == destinationId) {
                lastFromBackStack = entry;
                break;
            }
        }
        if (lastFromBackStack != null) {
            return lastFromBackStack;
        }
        throw new IllegalArgumentException("No destination with ID " + destinationId + " is on the NavController's back stack");
    }

    public NavBackStackEntry getCurrentBackStackEntry() {
        if (this.mBackStack.isEmpty()) {
            return null;
        }
        return this.mBackStack.getLast();
    }

    public NavBackStackEntry getPreviousBackStackEntry() {
        Iterator<NavBackStackEntry> iterator = this.mBackStack.descendingIterator();
        if (iterator.hasNext()) {
            iterator.next();
        }
        while (iterator.hasNext()) {
            NavBackStackEntry entry = iterator.next();
            if (!(entry.getDestination() instanceof NavGraph)) {
                return entry;
            }
        }
        return null;
    }
}
