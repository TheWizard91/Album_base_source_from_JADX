package com.facebook.imagepipeline.memory;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.references.CloseableReference;
import java.nio.ByteBuffer;
import javax.annotation.Nullable;

public class MemoryPooledByteBuffer implements PooledByteBuffer {
    CloseableReference<MemoryChunk> mBufRef;
    private final int mSize;

    public MemoryPooledByteBuffer(CloseableReference<MemoryChunk> bufRef, int size) {
        Preconditions.checkNotNull(bufRef);
        Preconditions.checkArgument(size >= 0 && size <= bufRef.get().getSize());
        this.mBufRef = bufRef.clone();
        this.mSize = size;
    }

    public synchronized int size() {
        ensureValid();
        return this.mSize;
    }

    public synchronized byte read(int offset) {
        ensureValid();
        boolean z = true;
        Preconditions.checkArgument(offset >= 0);
        if (offset >= this.mSize) {
            z = false;
        }
        Preconditions.checkArgument(z);
        return this.mBufRef.get().read(offset);
    }

    public synchronized int read(int offset, byte[] buffer, int bufferOffset, int length) {
        ensureValid();
        Preconditions.checkArgument(offset + length <= this.mSize);
        return this.mBufRef.get().read(offset, buffer, bufferOffset, length);
    }

    public synchronized long getNativePtr() throws UnsupportedOperationException {
        ensureValid();
        return this.mBufRef.get().getNativePtr();
    }

    @Nullable
    public synchronized ByteBuffer getByteBuffer() {
        return this.mBufRef.get().getByteBuffer();
    }

    public synchronized boolean isClosed() {
        return !CloseableReference.isValid(this.mBufRef);
    }

    public synchronized void close() {
        CloseableReference.closeSafely((CloseableReference<?>) this.mBufRef);
        this.mBufRef = null;
    }

    /* access modifiers changed from: package-private */
    public synchronized void ensureValid() {
        if (isClosed()) {
            throw new PooledByteBuffer.ClosedException();
        }
    }

    /* access modifiers changed from: package-private */
    public CloseableReference<MemoryChunk> getCloseableReference() {
        return this.mBufRef;
    }
}
