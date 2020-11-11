package com.facebook.imagepipeline.memory;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Throwables;
import com.facebook.common.memory.PooledByteBufferFactory;
import com.facebook.common.memory.PooledByteStreams;
import com.facebook.common.references.CloseableReference;
import java.io.IOException;
import java.io.InputStream;

public class MemoryPooledByteBufferFactory implements PooledByteBufferFactory {
    private final MemoryChunkPool mPool;
    private final PooledByteStreams mPooledByteStreams;

    public MemoryPooledByteBufferFactory(MemoryChunkPool pool, PooledByteStreams pooledByteStreams) {
        this.mPool = pool;
        this.mPooledByteStreams = pooledByteStreams;
    }

    public MemoryPooledByteBuffer newByteBuffer(int size) {
        Preconditions.checkArgument(size > 0);
        CloseableReference<MemoryChunk> chunkRef = CloseableReference.m126of(this.mPool.get(size), this.mPool);
        try {
            return new MemoryPooledByteBuffer(chunkRef, size);
        } finally {
            chunkRef.close();
        }
    }

    public MemoryPooledByteBuffer newByteBuffer(InputStream inputStream) throws IOException {
        MemoryPooledByteBufferOutputStream outputStream = new MemoryPooledByteBufferOutputStream(this.mPool);
        try {
            return newByteBuf(inputStream, outputStream);
        } finally {
            outputStream.close();
        }
    }

    public MemoryPooledByteBuffer newByteBuffer(byte[] bytes) {
        MemoryPooledByteBufferOutputStream outputStream = new MemoryPooledByteBufferOutputStream(this.mPool, bytes.length);
        try {
            outputStream.write(bytes, 0, bytes.length);
            MemoryPooledByteBuffer byteBuffer = outputStream.toByteBuffer();
            outputStream.close();
            return byteBuffer;
        } catch (IOException ioe) {
            throw Throwables.propagate(ioe);
        } catch (Throwable th) {
            outputStream.close();
            throw th;
        }
    }

    public MemoryPooledByteBuffer newByteBuffer(InputStream inputStream, int initialCapacity) throws IOException {
        MemoryPooledByteBufferOutputStream outputStream = new MemoryPooledByteBufferOutputStream(this.mPool, initialCapacity);
        try {
            return newByteBuf(inputStream, outputStream);
        } finally {
            outputStream.close();
        }
    }

    /* access modifiers changed from: package-private */
    public MemoryPooledByteBuffer newByteBuf(InputStream inputStream, MemoryPooledByteBufferOutputStream outputStream) throws IOException {
        this.mPooledByteStreams.copy(inputStream, outputStream);
        return outputStream.toByteBuffer();
    }

    public MemoryPooledByteBufferOutputStream newOutputStream() {
        return new MemoryPooledByteBufferOutputStream(this.mPool);
    }

    public MemoryPooledByteBufferOutputStream newOutputStream(int initialCapacity) {
        return new MemoryPooledByteBufferOutputStream(this.mPool, initialCapacity);
    }
}
