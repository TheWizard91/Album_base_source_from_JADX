package com.google.android.gms.common.data;

import com.google.android.gms.common.internal.Preconditions;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public class DataBufferIterator<T> implements Iterator<T> {
    protected final DataBuffer<T> zaln;
    protected int zalo = -1;

    public DataBufferIterator(DataBuffer<T> dataBuffer) {
        this.zaln = (DataBuffer) Preconditions.checkNotNull(dataBuffer);
    }

    public boolean hasNext() {
        return this.zalo < this.zaln.getCount() - 1;
    }

    public T next() {
        if (hasNext()) {
            DataBuffer<T> dataBuffer = this.zaln;
            int i = this.zalo + 1;
            this.zalo = i;
            return dataBuffer.get(i);
        }
        throw new NoSuchElementException(new StringBuilder(46).append("Cannot advance the iterator beyond ").append(this.zalo).toString());
    }

    public void remove() {
        throw new UnsupportedOperationException("Cannot remove elements from a DataBufferIterator");
    }
}
