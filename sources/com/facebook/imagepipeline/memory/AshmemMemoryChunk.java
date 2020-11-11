package com.facebook.imagepipeline.memory;

import android.os.SharedMemory;
import android.system.ErrnoException;
import android.util.Log;
import com.facebook.common.internal.Preconditions;
import java.io.Closeable;
import java.nio.ByteBuffer;
import javax.annotation.Nullable;

public class AshmemMemoryChunk implements MemoryChunk, Closeable {
    private static final String TAG = "AshmemMemoryChunk";
    @Nullable
    private ByteBuffer mByteBuffer;
    private final long mId;
    @Nullable
    private SharedMemory mSharedMemory;

    public AshmemMemoryChunk(int size) {
        Preconditions.checkArgument(size > 0);
        try {
            SharedMemory create = SharedMemory.create(TAG, size);
            this.mSharedMemory = create;
            this.mByteBuffer = create.mapReadWrite();
            this.mId = (long) System.identityHashCode(this);
        } catch (ErrnoException e) {
            throw new RuntimeException("Fail to create AshmemMemory", e);
        }
    }

    public AshmemMemoryChunk() {
        this.mSharedMemory = null;
        this.mByteBuffer = null;
        this.mId = (long) System.identityHashCode(this);
    }

    public synchronized void close() {
        if (!isClosed()) {
            SharedMemory.unmap(this.mByteBuffer);
            this.mSharedMemory.close();
            this.mByteBuffer = null;
            this.mSharedMemory = null;
        }
    }

    public synchronized boolean isClosed() {
        return this.mByteBuffer == null || this.mSharedMemory == null;
    }

    public int getSize() {
        Preconditions.checkState(!isClosed());
        return this.mSharedMemory.getSize();
    }

    public synchronized int write(int memoryOffset, byte[] byteArray, int byteArrayOffset, int count) {
        int actualCount;
        Preconditions.checkNotNull(byteArray);
        Preconditions.checkState(!isClosed());
        actualCount = MemoryChunkUtil.adjustByteCount(memoryOffset, count, getSize());
        MemoryChunkUtil.checkBounds(memoryOffset, byteArray.length, byteArrayOffset, actualCount, getSize());
        this.mByteBuffer.position(memoryOffset);
        this.mByteBuffer.put(byteArray, byteArrayOffset, actualCount);
        return actualCount;
    }

    public synchronized int read(int memoryOffset, byte[] byteArray, int byteArrayOffset, int count) {
        int actualCount;
        Preconditions.checkNotNull(byteArray);
        Preconditions.checkState(!isClosed());
        actualCount = MemoryChunkUtil.adjustByteCount(memoryOffset, count, getSize());
        MemoryChunkUtil.checkBounds(memoryOffset, byteArray.length, byteArrayOffset, actualCount, getSize());
        this.mByteBuffer.position(memoryOffset);
        this.mByteBuffer.get(byteArray, byteArrayOffset, actualCount);
        return actualCount;
    }

    public synchronized byte read(int offset) {
        boolean z = true;
        Preconditions.checkState(!isClosed());
        Preconditions.checkArgument(offset >= 0);
        if (offset >= getSize()) {
            z = false;
        }
        Preconditions.checkArgument(z);
        return this.mByteBuffer.get(offset);
    }

    public long getNativePtr() {
        throw new UnsupportedOperationException("Cannot get the pointer of an  AshmemMemoryChunk");
    }

    @Nullable
    public ByteBuffer getByteBuffer() {
        return this.mByteBuffer;
    }

    public long getUniqueId() {
        return this.mId;
    }

    public void copy(int offset, MemoryChunk other, int otherOffset, int count) {
        Preconditions.checkNotNull(other);
        if (other.getUniqueId() == getUniqueId()) {
            Log.w(TAG, "Copying from AshmemMemoryChunk " + Long.toHexString(getUniqueId()) + " to AshmemMemoryChunk " + Long.toHexString(other.getUniqueId()) + " which are the same ");
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

    private void doCopy(int offset, MemoryChunk other, int otherOffset, int count) {
        if (other instanceof AshmemMemoryChunk) {
            Preconditions.checkState(!isClosed());
            Preconditions.checkState(!other.isClosed());
            MemoryChunkUtil.checkBounds(offset, other.getSize(), otherOffset, count, getSize());
            this.mByteBuffer.position(offset);
            other.getByteBuffer().position(otherOffset);
            byte[] b = new byte[count];
            this.mByteBuffer.get(b, 0, count);
            other.getByteBuffer().put(b, 0, count);
            return;
        }
        throw new IllegalArgumentException("Cannot copy two incompatible MemoryChunks");
    }
}
