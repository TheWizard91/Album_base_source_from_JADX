package androidx.navigation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import androidx.collection.SparseArrayCompat;
import androidx.navigation.NavDeepLink;
import androidx.navigation.common.C2156R;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NavDestination {
    private static final HashMap<String, Class<?>> sClasses = new HashMap<>();
    private SparseArrayCompat<NavAction> mActions;
    private HashMap<String, NavArgument> mArguments;
    private ArrayList<NavDeepLink> mDeepLinks;
    private int mId;
    private String mIdName;
    private CharSequence mLabel;
    private final String mNavigatorName;
    private NavGraph mParent;

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.CLASS)
    public @interface ClassType {
        Class<?> value();
    }

    static class DeepLinkMatch implements Comparable<DeepLinkMatch> {
        private final NavDestination mDestination;
        private final boolean mHasMatchingAction;
        private final boolean mIsExactDeepLink;
        private final Bundle mMatchingArgs;
        private final int mMimeTypeMatchLevel;

        DeepLinkMatch(NavDestination destination, Bundle matchingArgs, boolean isExactDeepLink, boolean hasMatchingAction, int mimeTypeMatchLevel) {
            this.mDestination = destination;
            this.mMatchingArgs = matchingArgs;
            this.mIsExactDeepLink = isExactDeepLink;
            this.mHasMatchingAction = hasMatchingAction;
            this.mMimeTypeMatchLevel = mimeTypeMatchLevel;
        }

        /* access modifiers changed from: package-private */
        public NavDestination getDestination() {
            return this.mDestination;
        }

        /* access modifiers changed from: package-private */
        public Bundle getMatchingArgs() {
            return this.mMatchingArgs;
        }

        public int compareTo(DeepLinkMatch other) {
            boolean z = this.mIsExactDeepLink;
            if (z && !other.mIsExactDeepLink) {
                return 1;
            }
            if (!z && other.mIsExactDeepLink) {
                return -1;
            }
            Bundle bundle = this.mMatchingArgs;
            if (bundle != null && other.mMatchingArgs == null) {
                return 1;
            }
            if (bundle == null && other.mMatchingArgs != null) {
                return -1;
            }
            if (bundle != null) {
                int sizeDifference = bundle.size() - other.mMatchingArgs.size();
                if (sizeDifference > 0) {
                    return 1;
                }
                if (sizeDifference < 0) {
                    return -1;
                }
            }
            int sizeDifference2 = this.mHasMatchingAction;
            if (sizeDifference2 != 0 && !other.mHasMatchingAction) {
                return 1;
            }
            if (sizeDifference2 != 0 || !other.mHasMatchingAction) {
                return this.mMimeTypeMatchLevel - other.mMimeTypeMatchLevel;
            }
            return -1;
        }
    }

    protected static <C> Class<? extends C> parseClassFromName(Context context, String name, Class<? extends C> expectedClassType) {
        if (name.charAt(0) == '.') {
            name = context.getPackageName() + name;
        }
        HashMap<String, Class<?>> hashMap = sClasses;
        Class<?> clazz = hashMap.get(name);
        if (clazz == null) {
            try {
                clazz = Class.forName(name, true, context.getClassLoader());
                hashMap.put(name, clazz);
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException(e);
            }
        }
        if (expectedClassType.isAssignableFrom(clazz)) {
            return clazz;
        }
        throw new IllegalArgumentException(name + " must be a subclass of " + expectedClassType);
    }

    static String getDisplayName(Context context, int id) {
        if (id <= 16777215) {
            return Integer.toString(id);
        }
        try {
            return context.getResources().getResourceName(id);
        } catch (Resources.NotFoundException e) {
            return Integer.toString(id);
        }
    }

    public final Map<String, NavArgument> getArguments() {
        HashMap<String, NavArgument> hashMap = this.mArguments;
        if (hashMap == null) {
            return Collections.emptyMap();
        }
        return Collections.unmodifiableMap(hashMap);
    }

    public NavDestination(Navigator<? extends NavDestination> navigator) {
        this(NavigatorProvider.getNameForNavigator(navigator.getClass()));
    }

    public NavDestination(String navigatorName) {
        this.mNavigatorName = navigatorName;
    }

    public void onInflate(Context context, AttributeSet attrs) {
        TypedArray a = context.getResources().obtainAttributes(attrs, C2156R.styleable.Navigator);
        setId(a.getResourceId(C2156R.styleable.Navigator_android_id, 0));
        this.mIdName = getDisplayName(context, this.mId);
        setLabel(a.getText(C2156R.styleable.Navigator_android_label));
        a.recycle();
    }

    /* access modifiers changed from: package-private */
    public final void setParent(NavGraph parent) {
        this.mParent = parent;
    }

    public final NavGraph getParent() {
        return this.mParent;
    }

    public final int getId() {
        return this.mId;
    }

    public final void setId(int id) {
        this.mId = id;
        this.mIdName = null;
    }

    public String getDisplayName() {
        if (this.mIdName == null) {
            this.mIdName = Integer.toString(this.mId);
        }
        return this.mIdName;
    }

    public final void setLabel(CharSequence label) {
        this.mLabel = label;
    }

    public final CharSequence getLabel() {
        return this.mLabel;
    }

    public final String getNavigatorName() {
        return this.mNavigatorName;
    }

    public boolean hasDeepLink(Uri deepLink) {
        return hasDeepLink(new NavDeepLinkRequest(deepLink, (String) null, (String) null));
    }

    public boolean hasDeepLink(NavDeepLinkRequest deepLinkRequest) {
        return matchDeepLink(deepLinkRequest) != null;
    }

    public final void addDeepLink(String uriPattern) {
        addDeepLink(new NavDeepLink.Builder().setUriPattern(uriPattern).build());
    }

    public final void addDeepLink(NavDeepLink navDeepLink) {
        if (this.mDeepLinks == null) {
            this.mDeepLinks = new ArrayList<>();
        }
        this.mDeepLinks.add(navDeepLink);
    }

    /* access modifiers changed from: package-private */
    public DeepLinkMatch matchDeepLink(NavDeepLinkRequest navDeepLinkRequest) {
        ArrayList<NavDeepLink> arrayList = this.mDeepLinks;
        if (arrayList == null) {
            return null;
        }
        Iterator<NavDeepLink> it = arrayList.iterator();
        DeepLinkMatch bestMatch = null;
        while (it.hasNext()) {
            NavDeepLink deepLink = it.next();
            Uri uri = navDeepLinkRequest.getUri();
            Bundle matchingArguments = uri != null ? deepLink.getMatchingArguments(uri, getArguments()) : null;
            String requestAction = navDeepLinkRequest.getAction();
            boolean matchingAction = requestAction != null && requestAction.equals(deepLink.getAction());
            String mimeType = navDeepLinkRequest.getMimeType();
            int mimeTypeMatchLevel = mimeType != null ? deepLink.getMimeTypeMatchRating(mimeType) : -1;
            if (matchingArguments != null || matchingAction || mimeTypeMatchLevel > -1) {
                int i = mimeTypeMatchLevel;
                DeepLinkMatch newMatch = new DeepLinkMatch(this, matchingArguments, deepLink.isExactDeepLink(), matchingAction, mimeTypeMatchLevel);
                if (bestMatch == null || newMatch.compareTo(bestMatch) > 0) {
                    bestMatch = newMatch;
                }
            }
        }
        return bestMatch;
    }

    /* access modifiers changed from: package-private */
    public int[] buildDeepLinkIds() {
        ArrayDeque<NavDestination> hierarchy = new ArrayDeque<>();
        NavDestination current = this;
        do {
            NavGraph parent = current.getParent();
            if (parent == null || parent.getStartDestination() != current.getId()) {
                hierarchy.addFirst(current);
            }
            current = parent;
        } while (current != null);
        int[] deepLinkIds = new int[hierarchy.size()];
        int index = 0;
        Iterator<NavDestination> it = hierarchy.iterator();
        while (it.hasNext()) {
            deepLinkIds[index] = it.next().getId();
            index++;
        }
        return deepLinkIds;
    }

    /* access modifiers changed from: package-private */
    public boolean supportsActions() {
        return true;
    }

    public final NavAction getAction(int id) {
        SparseArrayCompat<NavAction> sparseArrayCompat = this.mActions;
        NavAction destination = sparseArrayCompat == null ? null : sparseArrayCompat.get(id);
        if (destination != null) {
            return destination;
        }
        if (getParent() != null) {
            return getParent().getAction(id);
        }
        return null;
    }

    public final void putAction(int actionId, int destId) {
        putAction(actionId, new NavAction(destId));
    }

    public final void putAction(int actionId, NavAction action) {
        if (!supportsActions()) {
            throw new UnsupportedOperationException("Cannot add action " + actionId + " to " + this + " as it does not support actions, indicating that it is a terminal destination in your navigation graph and will never trigger actions.");
        } else if (actionId != 0) {
            if (this.mActions == null) {
                this.mActions = new SparseArrayCompat<>();
            }
            this.mActions.put(actionId, action);
        } else {
            throw new IllegalArgumentException("Cannot have an action with actionId 0");
        }
    }

    public final void removeAction(int actionId) {
        SparseArrayCompat<NavAction> sparseArrayCompat = this.mActions;
        if (sparseArrayCompat != null) {
            sparseArrayCompat.remove(actionId);
        }
    }

    public final void addArgument(String argumentName, NavArgument argument) {
        if (this.mArguments == null) {
            this.mArguments = new HashMap<>();
        }
        this.mArguments.put(argumentName, argument);
    }

    public final void removeArgument(String argumentName) {
        HashMap<String, NavArgument> hashMap = this.mArguments;
        if (hashMap != null) {
            hashMap.remove(argumentName);
        }
    }

    /* access modifiers changed from: package-private */
    public Bundle addInDefaultArgs(Bundle args) {
        HashMap<String, NavArgument> hashMap;
        if (args == null && ((hashMap = this.mArguments) == null || hashMap.isEmpty())) {
            return null;
        }
        Bundle defaultArgs = new Bundle();
        HashMap<String, NavArgument> hashMap2 = this.mArguments;
        if (hashMap2 != null) {
            for (Map.Entry<String, NavArgument> argument : hashMap2.entrySet()) {
                argument.getValue().putDefaultValue(argument.getKey(), defaultArgs);
            }
        }
        if (args != null) {
            defaultArgs.putAll(args);
            HashMap<String, NavArgument> hashMap3 = this.mArguments;
            if (hashMap3 != null) {
                for (Map.Entry<String, NavArgument> argument2 : hashMap3.entrySet()) {
                    if (!argument2.getValue().verify(argument2.getKey(), args)) {
                        throw new IllegalArgumentException("Wrong argument type for '" + argument2.getKey() + "' in argument bundle. " + argument2.getValue().getType().getName() + " expected.");
                    }
                }
            }
        }
        return defaultArgs;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("(");
        String str = this.mIdName;
        if (str == null) {
            sb.append("0x");
            sb.append(Integer.toHexString(this.mId));
        } else {
            sb.append(str);
        }
        sb.append(")");
        if (this.mLabel != null) {
            sb.append(" label=");
            sb.append(this.mLabel);
        }
        return sb.toString();
    }
}
