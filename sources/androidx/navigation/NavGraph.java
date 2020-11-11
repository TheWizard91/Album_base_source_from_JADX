package androidx.navigation;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.collection.SparseArrayCompat;
import androidx.navigation.NavDestination;
import androidx.navigation.common.C2156R;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class NavGraph extends NavDestination implements Iterable<NavDestination> {
    final SparseArrayCompat<NavDestination> mNodes = new SparseArrayCompat<>();
    private int mStartDestId;
    private String mStartDestIdName;

    public NavGraph(Navigator<? extends NavGraph> navGraphNavigator) {
        super((Navigator<? extends NavDestination>) navGraphNavigator);
    }

    public void onInflate(Context context, AttributeSet attrs) {
        super.onInflate(context, attrs);
        TypedArray a = context.getResources().obtainAttributes(attrs, C2156R.styleable.NavGraphNavigator);
        setStartDestination(a.getResourceId(C2156R.styleable.NavGraphNavigator_startDestination, 0));
        this.mStartDestIdName = getDisplayName(context, this.mStartDestId);
        a.recycle();
    }

    /* access modifiers changed from: package-private */
    public NavDestination.DeepLinkMatch matchDeepLink(NavDeepLinkRequest request) {
        NavDestination.DeepLinkMatch bestMatch = super.matchDeepLink(request);
        Iterator<NavDestination> it = iterator();
        while (it.hasNext()) {
            NavDestination.DeepLinkMatch childBestMatch = it.next().matchDeepLink(request);
            if (childBestMatch != null && (bestMatch == null || childBestMatch.compareTo(bestMatch) > 0)) {
                bestMatch = childBestMatch;
            }
        }
        return bestMatch;
    }

    public final void addDestination(NavDestination node) {
        if (node.getId() != 0) {
            NavDestination existingDestination = this.mNodes.get(node.getId());
            if (existingDestination != node) {
                if (node.getParent() == null) {
                    if (existingDestination != null) {
                        existingDestination.setParent((NavGraph) null);
                    }
                    node.setParent(this);
                    this.mNodes.put(node.getId(), node);
                    return;
                }
                throw new IllegalStateException("Destination already has a parent set. Call NavGraph.remove() to remove the previous parent.");
            }
            return;
        }
        throw new IllegalArgumentException("Destinations must have an id. Call setId() or include an android:id in your navigation XML.");
    }

    public final void addDestinations(Collection<NavDestination> nodes) {
        for (NavDestination node : nodes) {
            if (node != null) {
                addDestination(node);
            }
        }
    }

    public final void addDestinations(NavDestination... nodes) {
        for (NavDestination node : nodes) {
            if (node != null) {
                addDestination(node);
            }
        }
    }

    public final NavDestination findNode(int resid) {
        return findNode(resid, true);
    }

    /* access modifiers changed from: package-private */
    public final NavDestination findNode(int resid, boolean searchParents) {
        NavDestination destination = this.mNodes.get(resid);
        if (destination != null) {
            return destination;
        }
        if (!searchParents || getParent() == null) {
            return null;
        }
        return getParent().findNode(resid);
    }

    public final Iterator<NavDestination> iterator() {
        return new Iterator<NavDestination>() {
            private int mIndex = -1;
            private boolean mWentToNext = false;

            public boolean hasNext() {
                return this.mIndex + 1 < NavGraph.this.mNodes.size();
            }

            public NavDestination next() {
                if (hasNext()) {
                    this.mWentToNext = true;
                    SparseArrayCompat<NavDestination> sparseArrayCompat = NavGraph.this.mNodes;
                    int i = this.mIndex + 1;
                    this.mIndex = i;
                    return sparseArrayCompat.valueAt(i);
                }
                throw new NoSuchElementException();
            }

            public void remove() {
                if (this.mWentToNext) {
                    NavGraph.this.mNodes.valueAt(this.mIndex).setParent((NavGraph) null);
                    NavGraph.this.mNodes.removeAt(this.mIndex);
                    this.mIndex--;
                    this.mWentToNext = false;
                    return;
                }
                throw new IllegalStateException("You must call next() before you can remove an element");
            }
        };
    }

    public final void addAll(NavGraph other) {
        Iterator<NavDestination> iterator = other.iterator();
        while (iterator.hasNext()) {
            iterator.remove();
            addDestination(iterator.next());
        }
    }

    public final void remove(NavDestination node) {
        int index = this.mNodes.indexOfKey(node.getId());
        if (index >= 0) {
            this.mNodes.valueAt(index).setParent((NavGraph) null);
            this.mNodes.removeAt(index);
        }
    }

    public final void clear() {
        Iterator<NavDestination> iterator = iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }

    public String getDisplayName() {
        return getId() != 0 ? super.getDisplayName() : "the root navigation";
    }

    public final int getStartDestination() {
        return this.mStartDestId;
    }

    public final void setStartDestination(int startDestId) {
        this.mStartDestId = startDestId;
        this.mStartDestIdName = null;
    }

    /* access modifiers changed from: package-private */
    public String getStartDestDisplayName() {
        if (this.mStartDestIdName == null) {
            this.mStartDestIdName = Integer.toString(this.mStartDestId);
        }
        return this.mStartDestIdName;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(" startDestination=");
        NavDestination startDestination = findNode(getStartDestination());
        if (startDestination == null) {
            String str = this.mStartDestIdName;
            if (str == null) {
                sb.append("0x");
                sb.append(Integer.toHexString(this.mStartDestId));
            } else {
                sb.append(str);
            }
        } else {
            sb.append("{");
            sb.append(startDestination.toString());
            sb.append("}");
        }
        return sb.toString();
    }
}
