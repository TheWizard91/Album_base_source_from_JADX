package com.facebook.imagepipeline.memory;

import android.util.Log;
import com.facebook.common.internal.Preconditions;
import java.io.Closeable;
import java.nio.ByteBuffer;
import javax.annotation.Nullable;

public class BufferMemoryChunk implements MemoryChunk, Closeable {
    private static final String TAG = "BufferMemoryChunk";
    private ByteBuffer mBuffer;
    private final long mId = ((long) System.identityHashCode(this));
    private final int mSize;

    public BufferMemoryChunk(int size) {
        this.mBuffer = ByteBuffer.allocateDirect(size);
        this.mSize = size;
    }

    public synchronized void close() {
        this.mBuffer = null;
    }

    public synchronized boolean isClosed() {
        return this.mBuffer == null;
    }

    public int getSize() {
        return this.mSize;
    }

    public synchronized int write(int memoryOffset, byte[] byteArray, int byteArrayOffset, int count) {
        int actualCount;
        Preconditions.checkNotNull(byteArray);
        Preconditions.checkState(!isClosed());
        actualCount = MemoryChunkUtil.adjustByteCount(memoryOffset, count, this.mSize);
        MemoryChunkUtil.checkBounds(memoryOffset, byteArray.length, byteArrayOffset, actualCount, this.mSize);
        this.mBuffer.position(memoryOffset);
        this.mBuffer.put(byteArray, byteArrayOffset, actualCount);
        return actualCount;
    }

    public synchronized int read(int memoryOffset, byte[] byteArray, int byteArrayOffset, int count) {
        int actualCount;
        Preconditions.checkNotNull(byteArray);
        Preconditions.checkState(!isClosed());
        actualCount = MemoryChunkUtil.adjustByteCount(memoryOffset, count, this.mSize);
        MemoryChunkUtil.checkBounds(memoryOffset, byteArray.length, byteArrayOffset, actualCount, this.mSize);
        this.mBuffer.position(memoryOffset);
        this.mBuffer.get(byteArray, byteArrayOffset, actualCount);
        return actualCount;
    }

    public synchronized byte read(int offset) {
        boolean z = true;
        Preconditions.checkState(!isClosed());
        Preconditions.checkArgument(offset >= 0);
        if (offset >= this.mSize) {
            z = false;
        }
        Preconditions.checkArgument(z);
        return this.mBuffer.get(offset);
    }

    public void copy(int offset, MemoryChunk other, int otherOffset, int count) {
        Preconditions.checkNotNull(other);
        if (other.getUniqueId() == getUniqueId()) {
            Log.w(TAG, "Copying from BufferMemoryChunk " + Long.toHexString(getUniqueId()) + " to BufferMemoryChunk " + Long.toHexString(other.getUniqueId()) + " which are the same ");
            Preconditions.checkArgument(false);
        }
        if (other.getUniqueId() < getUniqueId()) {
            synchronized (other) {
                synchronized (this) {
                    doCopy(offset, other, otherOffset, count);
                }
            }
            return;
        }
        synchronized (this) {
            synchronized (other) {
                doCopy(offset, other, otherOffset, count);
            }
        }
    }

    public long getNativePtr() {
        throw new UnsupportedOperationException("Cannot get the pointer of a BufferMemoryChunk");
    }

    @Nullable
    public synchronized ByteBuffer getByteBuffer() {
        return this.mBuffer;
    }

    public long getUniqueId() {
        return this.mId;
    }

    private void doCopy(int offset, MemoryChunk other, int otherOffset, int count) {
        if (other instanceof BufferMemoryChunk) {
            Preconditions.checkState(!isClosed());
            Preconditions.checkState(!other.isClosed());
            MemoryChunkUtil.checkBounds(offset, other.getSize(), otherOffset, count, this.mSize);
            this.mBuffer.position(offset);
            other.getByteBuffer().position(otherOffset);
            byte[] b = new byte[count];
            this.mBuffer.get(b, 0, count);
            other.getByteBuffer().put(b, 0, count);
            return;
        }
        throw new IllegalArgumentException("Cannot copy two incompatible MemoryChunks");
    }
}
