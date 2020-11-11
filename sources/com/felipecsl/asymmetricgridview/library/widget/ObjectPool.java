package com.felipecsl.asymmetricgridview.library.widget;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Stack;

class ObjectPool<T> implements Parcelable {
    public static final Parcelable.Creator<ObjectPool> CREATOR = new Parcelable.Creator<ObjectPool>() {
        public ObjectPool createFromParcel(Parcel in) {
            return new ObjectPool(in);
        }

        public ObjectPool[] newArray(int size) {
            return new ObjectPool[size];
        }
    };
    PoolObjectFactory<T> factory;
    Stack<T> stack;
    PoolStats stats;

    public ObjectPool(Parcel in) {
        this.stack = new Stack<>();
    }

    ObjectPool() {
        this.stack = new Stack<>();
        this.stats = new PoolStats();
    }

    ObjectPool(PoolObjectFactory<T> factory2) {
        this.stack = new Stack<>();
        this.factory = factory2;
    }

    static class PoolStats {
        int created = 0;
        int hits = 0;
        int misses = 0;
        int size = 0;

        PoolStats() {
        }

        /* access modifiers changed from: package-private */
        public String getStats(String name) {
            return String.format("%s: size %d, hits %d, misses %d, created %d", new Object[]{name, Integer.valueOf(this.size), Integer.valueOf(this.hits), Integer.valueOf(this.misses), Integer.valueOf(this.created)});
        }
    }

    /* access modifiers changed from: package-private */
    public T get() {
        if (!this.stack.isEmpty()) {
            this.stats.hits++;
            PoolStats poolStats = this.stats;
            poolStats.size--;
            return this.stack.pop();
        }
        this.stats.misses++;
        PoolObjectFactory<T> poolObjectFactory = this.factory;
        T object = poolObjectFactory != null ? poolObjectFactory.createObject() : null;
        if (object != null) {
            this.stats.created++;
        }
        return object;
    }

    /* access modifiers changed from: package-private */
    public void put(T object) {
        this.stack.push(object);
        this.stats.size++;
    }

    /* access modifiers changed from: package-private */
    public void clear() {
        this.stats = new PoolStats();
        this.stack.clear();
    }

    /* access modifiers changed from: package-private */
    public String getStats(String name) {
        return this.stats.getStats(name);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
    }
}
