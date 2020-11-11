package androidx.recyclerview.selection;

public final class MutableSelection<K> extends Selection<K> {
    public boolean add(K key) {
        return super.add(key);
    }

    public boolean remove(K key) {
        return super.remove(key);
    }

    public void copyFrom(Selection<K> source) {
        super.copyFrom(source);
    }

    public void clear() {
        super.clear();
    }
}
