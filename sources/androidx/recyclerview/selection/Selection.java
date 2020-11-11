package androidx.recyclerview.selection;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Selection<K> implements Iterable<K> {
    final Set<K> mProvisionalSelection;
    final Set<K> mSelection;

    Selection() {
        this.mSelection = new LinkedHashSet();
        this.mProvisionalSelection = new LinkedHashSet();
    }

    Selection(Set<K> selection) {
        this.mSelection = selection;
        this.mProvisionalSelection = new LinkedHashSet();
    }

    public boolean contains(K key) {
        return this.mSelection.contains(key) || this.mProvisionalSelection.contains(key);
    }

    public Iterator<K> iterator() {
        return this.mSelection.iterator();
    }

    public int size() {
        return this.mSelection.size() + this.mProvisionalSelection.size();
    }

    public boolean isEmpty() {
        return this.mSelection.isEmpty() && this.mProvisionalSelection.isEmpty();
    }

    /* access modifiers changed from: package-private */
    public Map<K, Boolean> setProvisionalSelection(Set<K> newSelection) {
        Map<K, Boolean> delta = new LinkedHashMap<>();
        for (K key : this.mProvisionalSelection) {
            if (!newSelection.contains(key) && !this.mSelection.contains(key)) {
                delta.put(key, false);
            }
        }
        for (K key2 : this.mSelection) {
            if (!newSelection.contains(key2)) {
                delta.put(key2, false);
            }
        }
        for (K key3 : newSelection) {
            if (!this.mSelection.contains(key3) && !this.mProvisionalSelection.contains(key3)) {
                delta.put(key3, true);
            }
        }
        for (Map.Entry<K, Boolean> entry : delta.entrySet()) {
            K key4 = entry.getKey();
            if (entry.getValue().booleanValue()) {
                this.mProvisionalSelection.add(key4);
            } else {
                this.mProvisionalSelection.remove(key4);
            }
        }
        return delta;
    }

    /* access modifiers changed from: package-private */
    public void mergeProvisionalSelection() {
        this.mSelection.addAll(this.mProvisionalSelection);
        this.mProvisionalSelection.clear();
    }

    /* access modifiers changed from: package-private */
    public void clearProvisionalSelection() {
        this.mProvisionalSelection.clear();
    }

    /* access modifiers changed from: package-private */
    public boolean add(K key) {
        return this.mSelection.add(key);
    }

    /* access modifiers changed from: package-private */
    public boolean remove(K key) {
        return this.mSelection.remove(key);
    }

    /* access modifiers changed from: package-private */
    public void clear() {
        this.mSelection.clear();
    }

    /* access modifiers changed from: package-private */
    public void copyFrom(Selection<K> source) {
        this.mSelection.clear();
        this.mSelection.addAll(source.mSelection);
        this.mProvisionalSelection.clear();
        this.mProvisionalSelection.addAll(source.mProvisionalSelection);
    }

    public String toString() {
        if (size() <= 0) {
            return "size=0, items=[]";
        }
        StringBuilder buffer = new StringBuilder(size() * 28);
        buffer.append("Selection{").append("primary{size=" + this.mSelection.size()).append(", entries=" + this.mSelection).append("}, provisional{size=" + this.mProvisionalSelection.size()).append(", entries=" + this.mProvisionalSelection).append("}}");
        return buffer.toString();
    }

    public int hashCode() {
        return this.mSelection.hashCode() ^ this.mProvisionalSelection.hashCode();
    }

    public boolean equals(Object other) {
        return this == other || ((other instanceof Selection) && isEqualTo((Selection) other));
    }

    private boolean isEqualTo(Selection<?> other) {
        return this.mSelection.equals(other.mSelection) && this.mProvisionalSelection.equals(other.mProvisionalSelection);
    }
}
