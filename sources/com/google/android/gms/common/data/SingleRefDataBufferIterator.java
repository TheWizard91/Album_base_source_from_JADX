package com.google.android.gms.common.data;

import java.util.NoSuchElementException;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public class SingleRefDataBufferIterator<T> extends DataBufferIterator<T> {
    private T zams;

    public SingleRefDataBufferIterator(DataBuffer<T> dataBuffer) {
        super(dataBuffer);
    }

    public T next() {
        if (hasNext()) {
            this.zalo++;
            if (this.zalo == 0) {
                T t = this.zaln.get(0);
                this.zams = t;
                if (!(t instanceof DataBufferRef)) {
                    String valueOf = String.valueOf(this.zams.getClass());
                    throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 44).append("DataBuffer reference of type ").append(valueOf).append(" is not movable").toString());
                }
            } else {
                ((DataBufferRef) this.zams).zag(this.zalo);
            }
            return this.zams;
        }
        throw new NoSuchElementException(new StringBuilder(46).append("Cannot advance the iterator beyond ").append(this.zalo).toString());
    }
}
