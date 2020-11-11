package androidx.recyclerview.selection;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import androidx.core.util.Preconditions;
import androidx.recyclerview.selection.BandSelectionHelper;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

final class GridModel<K> {
    private static final int LEFT = 0;
    private static final int LOWER = 1;
    private static final int LOWER_LEFT = 1;
    private static final int LOWER_RIGHT = 3;
    static final int NOT_SET = -1;
    private static final int RIGHT = 2;
    private static final int UPPER = 0;
    private static final int UPPER_LEFT = 0;
    private static final int UPPER_RIGHT = 2;
    private final List<Limits> mColumnBounds = new ArrayList();
    private final SparseArray<SparseIntArray> mColumns = new SparseArray<>();
    private final GridHost<K> mHost;
    private boolean mIsActive;
    private final ItemKeyProvider<K> mKeyProvider;
    private final SparseBooleanArray mKnownPositions = new SparseBooleanArray();
    private final List<SelectionObserver<K>> mOnSelectionChangedListeners = new ArrayList();
    private Point mPointer;
    private int mPositionNearestOrigin = -1;
    private RelativePoint mRelOrigin;
    private RelativePoint mRelPointer;
    private final List<Limits> mRowBounds = new ArrayList();
    private final RecyclerView.OnScrollListener mScrollListener;
    private final Set<K> mSelection = new LinkedHashSet();
    private final SelectionTracker.SelectionPredicate<K> mSelectionPredicate;

    public static abstract class SelectionObserver<K> {
        /* access modifiers changed from: package-private */
        public abstract void onSelectionChanged(Set<K> set);
    }

    GridModel(GridHost<K> host, ItemKeyProvider<K> keyProvider, SelectionTracker.SelectionPredicate<K> selectionPredicate) {
        boolean z = true;
        Preconditions.checkArgument(host != null);
        Preconditions.checkArgument(keyProvider != null);
        Preconditions.checkArgument(selectionPredicate == null ? false : z);
        this.mHost = host;
        this.mKeyProvider = keyProvider;
        this.mSelectionPredicate = selectionPredicate;
        C04061 r0 = new RecyclerView.OnScrollListener() {
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                GridModel.this.onScrolled(recyclerView, dx, dy);
            }
        };
        this.mScrollListener = r0;
        host.addOnScrollListener(r0);
    }

    /* access modifiers changed from: package-private */
    public void startCapturing(Point relativeOrigin) {
        recordVisibleChildren();
        if (!isEmpty()) {
            this.mIsActive = true;
            Point createAbsolutePoint = this.mHost.createAbsolutePoint(relativeOrigin);
            this.mPointer = createAbsolutePoint;
            this.mRelOrigin = createRelativePoint(createAbsolutePoint);
            this.mRelPointer = createRelativePoint(this.mPointer);
            computeCurrentSelection();
            notifySelectionChanged();
        }
    }

    /* access modifiers changed from: package-private */
    public void stopCapturing() {
        this.mIsActive = false;
    }

    /* access modifiers changed from: package-private */
    public void resizeSelection(Point relativePointer) {
        this.mPointer = this.mHost.createAbsolutePoint(relativePointer);
        updateModel();
    }

    /* access modifiers changed from: package-private */
    public int getPositionNearestOrigin() {
        return this.mPositionNearestOrigin;
    }

    /* access modifiers changed from: package-private */
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (this.mIsActive) {
            this.mPointer.x += dx;
            this.mPointer.y += dy;
            recordVisibleChildren();
            updateModel();
        }
    }

    private void recordVisibleChildren() {
        for (int i = 0; i < this.mHost.getVisibleChildCount(); i++) {
            int adapterPosition = this.mHost.getAdapterPositionAt(i);
            if (this.mHost.hasView(adapterPosition) && this.mSelectionPredicate.canSetStateAtPosition(adapterPosition, true) && !this.mKnownPositions.get(adapterPosition)) {
                this.mKnownPositions.put(adapterPosition, true);
                recordItemData(this.mHost.getAbsoluteRectForChildViewAt(i), adapterPosition);
            }
        }
    }

    private boolean isEmpty() {
        return this.mColumnBounds.size() == 0 || this.mRowBounds.size() == 0;
    }

    private void recordItemData(Rect absoluteChildRect, int adapterPosition) {
        if (this.mColumnBounds.size() != this.mHost.getColumnCount()) {
            recordLimits(this.mColumnBounds, new Limits(absoluteChildRect.left, absoluteChildRect.right));
        }
        recordLimits(this.mRowBounds, new Limits(absoluteChildRect.top, absoluteChildRect.bottom));
        SparseIntArray columnList = this.mColumns.get(absoluteChildRect.left);
        if (columnList == null) {
            columnList = new SparseIntArray();
            this.mColumns.put(absoluteChildRect.left, columnList);
        }
        columnList.put(absoluteChildRect.top, adapterPosition);
    }

    private void recordLimits(List<Limits> limitsList, Limits limits) {
        int index = Collections.binarySearch(limitsList, limits);
        if (index < 0) {
            limitsList.add(~index, limits);
        }
    }

    private void updateModel() {
        RelativePoint old = this.mRelPointer;
        RelativePoint createRelativePoint = createRelativePoint(this.mPointer);
        this.mRelPointer = createRelativePoint;
        if (!createRelativePoint.equals(old)) {
            computeCurrentSelection();
            notifySelectionChanged();
        }
    }

    private void computeCurrentSelection() {
        if (areItemsCoveredByBand(this.mRelPointer, this.mRelOrigin)) {
            updateSelection(computeBounds());
            return;
        }
        this.mSelection.clear();
        this.mPositionNearestOrigin = -1;
    }

    private void notifySelectionChanged() {
        for (SelectionObserver<K> listener : this.mOnSelectionChangedListeners) {
            listener.onSelectionChanged(this.mSelection);
        }
    }

    private void updateSelection(Rect rect) {
        int columnStart = Collections.binarySearch(this.mColumnBounds, new Limits(rect.left, rect.left));
        Preconditions.checkArgument(columnStart >= 0, "Rect doesn't intesect any known column.");
        int columnEnd = columnStart;
        int i = columnStart;
        while (i < this.mColumnBounds.size() && this.mColumnBounds.get(i).lowerLimit <= rect.right) {
            columnEnd = i;
            i++;
        }
        int rowStart = Collections.binarySearch(this.mRowBounds, new Limits(rect.top, rect.top));
        if (rowStart < 0) {
            this.mPositionNearestOrigin = -1;
            return;
        }
        int rowEnd = rowStart;
        int i2 = rowStart;
        while (i2 < this.mRowBounds.size() && this.mRowBounds.get(i2).lowerLimit <= rect.bottom) {
            rowEnd = i2;
            i2++;
        }
        updateSelection(columnStart, columnEnd, rowStart, rowEnd);
    }

    private void updateSelection(int columnStartIndex, int columnEndIndex, int rowStartIndex, int rowEndIndex) {
        this.mSelection.clear();
        for (int column = columnStartIndex; column <= columnEndIndex; column++) {
            SparseIntArray items = this.mColumns.get(this.mColumnBounds.get(column).lowerLimit);
            for (int row = rowStartIndex; row <= rowEndIndex; row++) {
                int position = items.get(this.mRowBounds.get(row).lowerLimit, -1);
                if (position != -1) {
                    K key = this.mKeyProvider.getKey(position);
                    if (key != null && canSelect(key)) {
                        this.mSelection.add(key);
                    }
                    if (isPossiblePositionNearestOrigin(column, columnStartIndex, columnEndIndex, row, rowStartIndex, rowEndIndex)) {
                        this.mPositionNearestOrigin = position;
                    }
                }
            }
        }
        int i = rowEndIndex;
    }

    private boolean canSelect(K key) {
        return this.mSelectionPredicate.canSetStateForKey(key, true);
    }

    private boolean isPossiblePositionNearestOrigin(int columnIndex, int columnStartIndex, int columnEndIndex, int rowIndex, int rowStartIndex, int rowEndIndex) {
        int corner = computeCornerNearestOrigin();
        if (corner != 0) {
            if (corner != 1) {
                if (corner != 2) {
                    if (corner != 3) {
                        throw new RuntimeException("Invalid corner type.");
                    } else if (rowIndex == rowEndIndex) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (columnIndex == columnEndIndex && rowIndex == rowStartIndex) {
                    return true;
                } else {
                    return false;
                }
            } else if (columnIndex == columnStartIndex && rowIndex == rowEndIndex) {
                return true;
            } else {
                return false;
            }
        } else if (columnIndex == columnStartIndex && rowIndex == rowStartIndex) {
            return true;
        } else {
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public void addOnSelectionChangedListener(SelectionObserver<K> listener) {
        this.mOnSelectionChangedListeners.add(listener);
    }

    /* access modifiers changed from: package-private */
    public void onDestroy() {
        this.mOnSelectionChangedListeners.clear();
        this.mHost.removeOnScrollListener(this.mScrollListener);
    }

    private static class Limits implements Comparable<Limits> {
        public int lowerLimit;
        public int upperLimit;

        Limits(int lowerLimit2, int upperLimit2) {
            this.lowerLimit = lowerLimit2;
            this.upperLimit = upperLimit2;
        }

        public int compareTo(Limits other) {
            return this.lowerLimit - other.lowerLimit;
        }

        public int hashCode() {
            return this.lowerLimit ^ this.upperLimit;
        }

        public boolean equals(Object other) {
            if ((other instanceof Limits) && ((Limits) other).lowerLimit == this.lowerLimit && ((Limits) other).upperLimit == this.upperLimit) {
                return true;
            }
            return false;
        }

        public String toString() {
            return "(" + this.lowerLimit + ", " + this.upperLimit + ")";
        }
    }

    private static class RelativeCoordinate implements Comparable<RelativeCoordinate> {
        static final int AFTER_LAST_ITEM = 0;
        static final int BEFORE_FIRST_ITEM = 1;
        static final int BETWEEN_TWO_ITEMS = 2;
        static final int WITHIN_LIMITS = 3;
        public Limits limitsAfterCoordinate;
        public Limits limitsBeforeCoordinate;
        public Limits mFirstKnownItem;
        public Limits mLastKnownItem;
        public final int type;

        RelativeCoordinate(List<Limits> limitsList, int value) {
            int index = Collections.binarySearch(limitsList, new Limits(value, value));
            if (index >= 0) {
                this.type = 3;
                this.limitsBeforeCoordinate = limitsList.get(index);
            } else if ((~index) == 0) {
                this.type = 1;
                this.mFirstKnownItem = limitsList.get(0);
            } else if ((~index) == limitsList.size()) {
                Limits lastLimits = limitsList.get(limitsList.size() - 1);
                if (lastLimits.lowerLimit > value || value > lastLimits.upperLimit) {
                    this.type = 0;
                    this.mLastKnownItem = lastLimits;
                    return;
                }
                this.type = 3;
                this.limitsBeforeCoordinate = lastLimits;
            } else {
                Limits limitsBeforeIndex = limitsList.get((~index) - 1);
                if (limitsBeforeIndex.lowerLimit > value || value > limitsBeforeIndex.upperLimit) {
                    this.type = 2;
                    this.limitsBeforeCoordinate = limitsList.get((~index) - 1);
                    this.limitsAfterCoordinate = limitsList.get(~index);
                    return;
                }
                this.type = 3;
                this.limitsBeforeCoordinate = limitsList.get((~index) - 1);
            }
        }

        /* access modifiers changed from: package-private */
        public int toComparisonValue() {
            int i = this.type;
            if (i == 1) {
                return this.mFirstKnownItem.lowerLimit - 1;
            }
            if (i == 0) {
                return this.mLastKnownItem.upperLimit + 1;
            }
            if (i == 2) {
                return this.limitsBeforeCoordinate.upperLimit + 1;
            }
            return this.limitsBeforeCoordinate.lowerLimit;
        }

        public int hashCode() {
            return ((this.mFirstKnownItem.lowerLimit ^ this.mLastKnownItem.upperLimit) ^ this.limitsBeforeCoordinate.upperLimit) ^ this.limitsBeforeCoordinate.lowerLimit;
        }

        public boolean equals(Object other) {
            if ((other instanceof RelativeCoordinate) && toComparisonValue() == ((RelativeCoordinate) other).toComparisonValue()) {
                return true;
            }
            return false;
        }

        public int compareTo(RelativeCoordinate other) {
            return toComparisonValue() - other.toComparisonValue();
        }
    }

    /* access modifiers changed from: package-private */
    public RelativePoint createRelativePoint(Point point) {
        return new RelativePoint(new RelativeCoordinate(this.mColumnBounds, point.x), new RelativeCoordinate(this.mRowBounds, point.y));
    }

    private static class RelativePoint {

        /* renamed from: mX */
        final RelativeCoordinate f35mX;

        /* renamed from: mY */
        final RelativeCoordinate f36mY;

        RelativePoint(List<Limits> columnLimits, List<Limits> rowLimits, Point point) {
            this.f35mX = new RelativeCoordinate(columnLimits, point.x);
            this.f36mY = new RelativeCoordinate(rowLimits, point.y);
        }

        RelativePoint(RelativeCoordinate x, RelativeCoordinate y) {
            this.f35mX = x;
            this.f36mY = y;
        }

        public int hashCode() {
            return this.f35mX.toComparisonValue() ^ this.f36mY.toComparisonValue();
        }

        public boolean equals(Object other) {
            if (!(other instanceof RelativePoint)) {
                return false;
            }
            RelativePoint otherPoint = (RelativePoint) other;
            if (!this.f35mX.equals(otherPoint.f35mX) || !this.f36mY.equals(otherPoint.f36mY)) {
                return false;
            }
            return true;
        }
    }

    private Rect computeBounds() {
        Rect rect = new Rect();
        rect.left = getCoordinateValue(min(this.mRelOrigin.f35mX, this.mRelPointer.f35mX), this.mColumnBounds, true);
        rect.right = getCoordinateValue(max(this.mRelOrigin.f35mX, this.mRelPointer.f35mX), this.mColumnBounds, false);
        rect.top = getCoordinateValue(min(this.mRelOrigin.f36mY, this.mRelPointer.f36mY), this.mRowBounds, true);
        rect.bottom = getCoordinateValue(max(this.mRelOrigin.f36mY, this.mRelPointer.f36mY), this.mRowBounds, false);
        return rect;
    }

    private int computeCornerNearestOrigin() {
        int cornerValue;
        if (this.mRelOrigin.f36mY.equals(min(this.mRelOrigin.f36mY, this.mRelPointer.f36mY))) {
            cornerValue = 0 | 0;
        } else {
            cornerValue = 0 | 1;
        }
        if (this.mRelOrigin.f35mX.equals(min(this.mRelOrigin.f35mX, this.mRelPointer.f35mX))) {
            return cornerValue | 0;
        }
        return cornerValue | 2;
    }

    private RelativeCoordinate min(RelativeCoordinate first, RelativeCoordinate second) {
        return first.compareTo(second) < 0 ? first : second;
    }

    private RelativeCoordinate max(RelativeCoordinate first, RelativeCoordinate second) {
        return first.compareTo(second) > 0 ? first : second;
    }

    private int getCoordinateValue(RelativeCoordinate coordinate, List<Limits> limitsList, boolean isStartOfRange) {
        int i = coordinate.type;
        if (i == 0) {
            return limitsList.get(limitsList.size() - 1).upperLimit;
        }
        if (i == 1) {
            return limitsList.get(0).lowerLimit;
        }
        if (i != 2) {
            if (i == 3) {
                return coordinate.limitsBeforeCoordinate.lowerLimit;
            }
            throw new RuntimeException("Invalid coordinate value.");
        } else if (isStartOfRange) {
            return coordinate.limitsAfterCoordinate.lowerLimit;
        } else {
            return coordinate.limitsBeforeCoordinate.upperLimit;
        }
    }

    private boolean areItemsCoveredByBand(RelativePoint first, RelativePoint second) {
        return doesCoordinateLocationCoverItems(first.f35mX, second.f35mX) && doesCoordinateLocationCoverItems(first.f36mY, second.f36mY);
    }

    private boolean doesCoordinateLocationCoverItems(RelativeCoordinate pointerCoordinate, RelativeCoordinate originCoordinate) {
        if (pointerCoordinate.type == 1 && originCoordinate.type == 1) {
            return false;
        }
        if (pointerCoordinate.type == 0 && originCoordinate.type == 0) {
            return false;
        }
        return pointerCoordinate.type != 2 || originCoordinate.type != 2 || !pointerCoordinate.limitsBeforeCoordinate.equals(originCoordinate.limitsBeforeCoordinate) || !pointerCoordinate.limitsAfterCoordinate.equals(originCoordinate.limitsAfterCoordinate);
    }

    static abstract class GridHost<K> extends BandSelectionHelper.BandHost<K> {
        /* access modifiers changed from: package-private */
        public abstract Point createAbsolutePoint(Point point);

        /* access modifiers changed from: package-private */
        public abstract Rect getAbsoluteRectForChildViewAt(int i);

        /* access modifiers changed from: package-private */
        public abstract int getAdapterPositionAt(int i);

        /* access modifiers changed from: package-private */
        public abstract int getColumnCount();

        /* access modifiers changed from: package-private */
        public abstract int getVisibleChildCount();

        /* access modifiers changed from: package-private */
        public abstract boolean hasView(int i);

        /* access modifiers changed from: package-private */
        public abstract void removeOnScrollListener(RecyclerView.OnScrollListener onScrollListener);

        GridHost() {
        }
    }
}
