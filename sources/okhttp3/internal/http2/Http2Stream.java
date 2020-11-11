package okhttp3.internal.http2;

import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import javax.annotation.Nullable;
import okhttp3.Headers;
import okhttp3.internal.Util;
import okhttp3.internal.http2.Header;
import okio.AsyncTimeout;
import okio.Buffer;
import okio.BufferedSource;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class Http2Stream {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    long bytesLeftInWriteWindow;
    final Http2Connection connection;
    ErrorCode errorCode;
    private boolean hasResponseHeaders;
    /* access modifiers changed from: private */
    public Header.Listener headersListener;
    /* access modifiers changed from: private */
    public final Deque<Headers> headersQueue;

    /* renamed from: id */
    final int f712id;
    final StreamTimeout readTimeout;
    final FramingSink sink;
    private final FramingSource source;
    long unacknowledgedBytesRead = 0;
    final StreamTimeout writeTimeout;

    Http2Stream(int id, Http2Connection connection2, boolean outFinished, boolean inFinished, @Nullable Headers headers) {
        ArrayDeque arrayDeque = new ArrayDeque();
        this.headersQueue = arrayDeque;
        this.readTimeout = new StreamTimeout();
        this.writeTimeout = new StreamTimeout();
        this.errorCode = null;
        if (connection2 != null) {
            this.f712id = id;
            this.connection = connection2;
            this.bytesLeftInWriteWindow = (long) connection2.peerSettings.getInitialWindowSize();
            FramingSource framingSource = new FramingSource((long) connection2.okHttpSettings.getInitialWindowSize());
            this.source = framingSource;
            FramingSink framingSink = new FramingSink();
            this.sink = framingSink;
            framingSource.finished = inFinished;
            framingSink.finished = outFinished;
            if (headers != null) {
                arrayDeque.add(headers);
            }
            if (isLocallyInitiated() && headers != null) {
                throw new IllegalStateException("locally-initiated streams shouldn't have headers yet");
            } else if (!isLocallyInitiated() && headers == null) {
                throw new IllegalStateException("remotely-initiated streams should have headers");
            }
        } else {
            throw new NullPointerException("connection == null");
        }
    }

    public int getId() {
        return this.f712id;
    }

    public synchronized boolean isOpen() {
        if (this.errorCode != null) {
            return false;
        }
        if ((this.source.finished || this.source.closed) && ((this.sink.finished || this.sink.closed) && this.hasResponseHeaders)) {
            return false;
        }
        return true;
    }

    public boolean isLocallyInitiated() {
        if (this.connection.client == ((this.f712id & 1) == 1)) {
            return true;
        }
        return false;
    }

    public Http2Connection getConnection() {
        return this.connection;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0038, code lost:
        r0 = th;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized okhttp3.Headers takeHeaders() throws java.io.IOException {
        /*
            r2 = this;
            monitor-enter(r2)
            okhttp3.internal.http2.Http2Stream$StreamTimeout r0 = r2.readTimeout     // Catch:{ all -> 0x003f }
            r0.enter()     // Catch:{ all -> 0x003f }
        L_0x0006:
            java.util.Deque<okhttp3.Headers> r0 = r2.headersQueue     // Catch:{ all -> 0x0038 }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x0038 }
            if (r0 == 0) goto L_0x0018
            okhttp3.internal.http2.ErrorCode r0 = r2.errorCode     // Catch:{ all -> 0x0016 }
            if (r0 != 0) goto L_0x0018
            r2.waitForIo()     // Catch:{ all -> 0x0016 }
            goto L_0x0006
        L_0x0016:
            r0 = move-exception
            goto L_0x0039
        L_0x0018:
            okhttp3.internal.http2.Http2Stream$StreamTimeout r0 = r2.readTimeout     // Catch:{ all -> 0x003f }
            r0.exitAndThrowIfTimedOut()     // Catch:{ all -> 0x003f }
            java.util.Deque<okhttp3.Headers> r0 = r2.headersQueue     // Catch:{ all -> 0x003f }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x003f }
            if (r0 != 0) goto L_0x0030
            java.util.Deque<okhttp3.Headers> r0 = r2.headersQueue     // Catch:{ all -> 0x003f }
            java.lang.Object r0 = r0.removeFirst()     // Catch:{ all -> 0x003f }
            okhttp3.Headers r0 = (okhttp3.Headers) r0     // Catch:{ all -> 0x003f }
            monitor-exit(r2)
            return r0
        L_0x0030:
            okhttp3.internal.http2.StreamResetException r0 = new okhttp3.internal.http2.StreamResetException     // Catch:{ all -> 0x003f }
            okhttp3.internal.http2.ErrorCode r1 = r2.errorCode     // Catch:{ all -> 0x003f }
            r0.<init>(r1)     // Catch:{ all -> 0x003f }
            throw r0     // Catch:{ all -> 0x003f }
        L_0x0038:
            r0 = move-exception
        L_0x0039:
            okhttp3.internal.http2.Http2Stream$StreamTimeout r1 = r2.readTimeout     // Catch:{ all -> 0x003f }
            r1.exitAndThrowIfTimedOut()     // Catch:{ all -> 0x003f }
            throw r0     // Catch:{ all -> 0x003f }
        L_0x003f:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.Http2Stream.takeHeaders():okhttp3.Headers");
    }

    public synchronized ErrorCode getErrorCode() {
        return this.errorCode;
    }

    public void writeHeaders(List<Header> responseHeaders, boolean out) throws IOException {
        boolean z;
        if (Thread.holdsLock(this)) {
            throw new AssertionError();
        } else if (responseHeaders != null) {
            boolean outFinished = false;
            boolean flushHeaders = false;
            synchronized (this) {
                z = true;
                this.hasResponseHeaders = true;
                if (!out) {
                    this.sink.finished = true;
                    flushHeaders = true;
                    outFinished = true;
                }
            }
            if (!flushHeaders) {
                synchronized (this.connection) {
                    if (this.connection.bytesLeftInWriteWindow != 0) {
                        z = false;
                    }
                    flushHeaders = z;
                }
            }
            this.connection.writeSynReply(this.f712id, outFinished, responseHeaders);
            if (flushHeaders) {
                this.connection.flush();
            }
        } else {
            throw new NullPointerException("headers == null");
        }
    }

    public Timeout readTimeout() {
        return this.readTimeout;
    }

    public Timeout writeTimeout() {
        return this.writeTimeout;
    }

    public Source getSource() {
        return this.source;
    }

    public Sink getSink() {
        synchronized (this) {
            if (!this.hasResponseHeaders) {
                if (!isLocallyInitiated()) {
                    throw new IllegalStateException("reply before requesting the sink");
                }
            }
        }
        return this.sink;
    }

    public void close(ErrorCode rstStatusCode) throws IOException {
        if (closeInternal(rstStatusCode)) {
            this.connection.writeSynReset(this.f712id, rstStatusCode);
        }
    }

    public void closeLater(ErrorCode errorCode2) {
        if (closeInternal(errorCode2)) {
            this.connection.writeSynResetLater(this.f712id, errorCode2);
        }
    }

    private boolean closeInternal(ErrorCode errorCode2) {
        if (!Thread.holdsLock(this)) {
            synchronized (this) {
                if (this.errorCode != null) {
                    return false;
                }
                if (this.source.finished && this.sink.finished) {
                    return false;
                }
                this.errorCode = errorCode2;
                notifyAll();
                this.connection.removeStream(this.f712id);
                return true;
            }
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: package-private */
    public void receiveHeaders(List<Header> headers) {
        boolean open;
        if (!Thread.holdsLock(this)) {
            synchronized (this) {
                this.hasResponseHeaders = true;
                this.headersQueue.add(Util.toHeaders(headers));
                open = isOpen();
                notifyAll();
            }
            if (!open) {
                this.connection.removeStream(this.f712id);
                return;
            }
            return;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: package-private */
    public void receiveData(BufferedSource in, int length) throws IOException {
        if (!Thread.holdsLock(this)) {
            this.source.receive(in, (long) length);
            return;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: package-private */
    public void receiveFin() {
        boolean open;
        if (!Thread.holdsLock(this)) {
            synchronized (this) {
                this.source.finished = true;
                open = isOpen();
                notifyAll();
            }
            if (!open) {
                this.connection.removeStream(this.f712id);
                return;
            }
            return;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: package-private */
    public synchronized void receiveRstStream(ErrorCode errorCode2) {
        if (this.errorCode == null) {
            this.errorCode = errorCode2;
            notifyAll();
        }
    }

    public synchronized void setHeadersListener(Header.Listener headersListener2) {
        this.headersListener = headersListener2;
        if (!this.headersQueue.isEmpty() && headersListener2 != null) {
            notifyAll();
        }
    }

    private final class FramingSource implements Source {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        boolean closed;
        boolean finished;
        private final long maxByteCount;
        private final Buffer readBuffer = new Buffer();
        private final Buffer receiveBuffer = new Buffer();

        static {
            Class<Http2Stream> cls = Http2Stream.class;
        }

        FramingSource(long maxByteCount2) {
            this.maxByteCount = maxByteCount2;
        }

        public long read(Buffer sink, long byteCount) throws IOException {
            long readBytesDelivered;
            ErrorCode errorCodeToDeliver;
            if (byteCount >= 0) {
                while (true) {
                    Headers headersToDeliver = null;
                    Header.Listener headersListenerToNotify = null;
                    readBytesDelivered = -1;
                    errorCodeToDeliver = null;
                    synchronized (Http2Stream.this) {
                        Http2Stream.this.readTimeout.enter();
                        try {
                            if (Http2Stream.this.errorCode != null) {
                                errorCodeToDeliver = Http2Stream.this.errorCode;
                            }
                            if (!this.closed) {
                                if (!Http2Stream.this.headersQueue.isEmpty() && Http2Stream.this.headersListener != null) {
                                    headersToDeliver = (Headers) Http2Stream.this.headersQueue.removeFirst();
                                    headersListenerToNotify = Http2Stream.this.headersListener;
                                } else if (this.readBuffer.size() > 0) {
                                    Buffer buffer = this.readBuffer;
                                    readBytesDelivered = buffer.read(sink, Math.min(byteCount, buffer.size()));
                                    Http2Stream.this.unacknowledgedBytesRead += readBytesDelivered;
                                    if (errorCodeToDeliver == null && Http2Stream.this.unacknowledgedBytesRead >= ((long) (Http2Stream.this.connection.okHttpSettings.getInitialWindowSize() / 2))) {
                                        Http2Stream.this.connection.writeWindowUpdateLater(Http2Stream.this.f712id, Http2Stream.this.unacknowledgedBytesRead);
                                        Http2Stream.this.unacknowledgedBytesRead = 0;
                                    }
                                } else if (!this.finished && errorCodeToDeliver == null) {
                                    Http2Stream.this.waitForIo();
                                }
                                Http2Stream.this.readTimeout.exitAndThrowIfTimedOut();
                                if (headersToDeliver != null && headersListenerToNotify != null) {
                                    headersListenerToNotify.onHeaders(headersToDeliver);
                                }
                            } else {
                                throw new IOException("stream closed");
                            }
                        } finally {
                            Http2Stream.this.readTimeout.exitAndThrowIfTimedOut();
                        }
                    }
                }
                if (readBytesDelivered != -1) {
                    updateConnectionFlowControl(readBytesDelivered);
                    return readBytesDelivered;
                } else if (errorCodeToDeliver == null) {
                    return -1;
                } else {
                    throw new StreamResetException(errorCodeToDeliver);
                }
            } else {
                throw new IllegalArgumentException("byteCount < 0: " + byteCount);
            }
        }

        private void updateConnectionFlowControl(long read) {
            if (!Thread.holdsLock(Http2Stream.this)) {
                Http2Stream.this.connection.updateConnectionFlowControl(read);
                return;
            }
            throw new AssertionError();
        }

        /* access modifiers changed from: package-private */
        public void receive(BufferedSource in, long byteCount) throws IOException {
            boolean finished2;
            boolean z;
            boolean flowControlError;
            if (!Thread.holdsLock(Http2Stream.this)) {
                while (byteCount > 0) {
                    synchronized (Http2Stream.this) {
                        finished2 = this.finished;
                        z = true;
                        flowControlError = this.readBuffer.size() + byteCount > this.maxByteCount;
                    }
                    if (flowControlError) {
                        in.skip(byteCount);
                        Http2Stream.this.closeLater(ErrorCode.FLOW_CONTROL_ERROR);
                        return;
                    } else if (finished2) {
                        in.skip(byteCount);
                        return;
                    } else {
                        long read = in.read(this.receiveBuffer, byteCount);
                        if (read != -1) {
                            long byteCount2 = byteCount - read;
                            synchronized (Http2Stream.this) {
                                if (this.readBuffer.size() != 0) {
                                    z = false;
                                }
                                boolean wasEmpty = z;
                                this.readBuffer.writeAll(this.receiveBuffer);
                                if (wasEmpty) {
                                    Http2Stream.this.notifyAll();
                                }
                            }
                            byteCount = byteCount2;
                        } else {
                            throw new EOFException();
                        }
                    }
                }
                return;
            }
            throw new AssertionError();
        }

        public Timeout timeout() {
            return Http2Stream.this.readTimeout;
        }

        public void close() throws IOException {
            long bytesDiscarded;
            List<Headers> headersToDeliver = null;
            Header.Listener headersListenerToNotify = null;
            synchronized (Http2Stream.this) {
                this.closed = true;
                bytesDiscarded = this.readBuffer.size();
                this.readBuffer.clear();
                if (!Http2Stream.this.headersQueue.isEmpty() && Http2Stream.this.headersListener != null) {
                    headersToDeliver = new ArrayList<>(Http2Stream.this.headersQueue);
                    Http2Stream.this.headersQueue.clear();
                    headersListenerToNotify = Http2Stream.this.headersListener;
                }
                Http2Stream.this.notifyAll();
            }
            if (bytesDiscarded > 0) {
                updateConnectionFlowControl(bytesDiscarded);
            }
            Http2Stream.this.cancelStreamIfNecessary();
            if (headersListenerToNotify != null) {
                for (Headers headers : headersToDeliver) {
                    headersListenerToNotify.onHeaders(headers);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void cancelStreamIfNecessary() throws IOException {
        boolean cancel;
        boolean open;
        if (!Thread.holdsLock(this)) {
            synchronized (this) {
                cancel = !this.source.finished && this.source.closed && (this.sink.finished || this.sink.closed);
                open = isOpen();
            }
            if (cancel) {
                close(ErrorCode.CANCEL);
            } else if (!open) {
                this.connection.removeStream(this.f712id);
            }
        } else {
            throw new AssertionError();
        }
    }

    final class FramingSink implements Sink {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final long EMIT_BUFFER_SIZE = 16384;
        boolean closed;
        boolean finished;
        private final Buffer sendBuffer = new Buffer();

        static {
            Class<Http2Stream> cls = Http2Stream.class;
        }

        FramingSink() {
        }

        public void write(Buffer source, long byteCount) throws IOException {
            if (!Thread.holdsLock(Http2Stream.this)) {
                this.sendBuffer.write(source, byteCount);
                while (this.sendBuffer.size() >= 16384) {
                    emitFrame(false);
                }
                return;
            }
            throw new AssertionError();
        }

        /* JADX INFO: finally extract failed */
        private void emitFrame(boolean outFinished) throws IOException {
            long toWrite;
            synchronized (Http2Stream.this) {
                Http2Stream.this.writeTimeout.enter();
                while (Http2Stream.this.bytesLeftInWriteWindow <= 0 && !this.finished && !this.closed && Http2Stream.this.errorCode == null) {
                    try {
                        Http2Stream.this.waitForIo();
                    } catch (Throwable th) {
                        Http2Stream.this.writeTimeout.exitAndThrowIfTimedOut();
                        throw th;
                    }
                }
                Http2Stream.this.writeTimeout.exitAndThrowIfTimedOut();
                Http2Stream.this.checkOutNotClosed();
                toWrite = Math.min(Http2Stream.this.bytesLeftInWriteWindow, this.sendBuffer.size());
                Http2Stream.this.bytesLeftInWriteWindow -= toWrite;
            }
            Http2Stream.this.writeTimeout.enter();
            try {
                Http2Stream.this.connection.writeData(Http2Stream.this.f712id, outFinished && toWrite == this.sendBuffer.size(), this.sendBuffer, toWrite);
            } finally {
                Http2Stream.this.writeTimeout.exitAndThrowIfTimedOut();
            }
        }

        public void flush() throws IOException {
            if (!Thread.holdsLock(Http2Stream.this)) {
                synchronized (Http2Stream.this) {
                    Http2Stream.this.checkOutNotClosed();
                }
                while (this.sendBuffer.size() > 0) {
                    emitFrame(false);
                    Http2Stream.this.connection.flush();
                }
                return;
            }
            throw new AssertionError();
        }

        public Timeout timeout() {
            return Http2Stream.this.writeTimeout;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0019, code lost:
            if (r8.this$0.sink.finished != false) goto L_0x0044;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0025, code lost:
            if (r8.sendBuffer.size() <= 0) goto L_0x0035;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x002f, code lost:
            if (r8.sendBuffer.size() <= 0) goto L_0x0044;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0031, code lost:
            emitFrame(true);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0035, code lost:
            r8.this$0.connection.writeData(r8.this$0.f712id, true, (okio.Buffer) null, 0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0044, code lost:
            r2 = r8.this$0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0046, code lost:
            monitor-enter(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
            r8.closed = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0049, code lost:
            monitor-exit(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x004a, code lost:
            r8.this$0.connection.flush();
            r8.this$0.cancelStreamIfNecessary();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x0056, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void close() throws java.io.IOException {
            /*
                r8 = this;
                okhttp3.internal.http2.Http2Stream r0 = okhttp3.internal.http2.Http2Stream.this
                boolean r0 = java.lang.Thread.holdsLock(r0)
                if (r0 != 0) goto L_0x005d
                okhttp3.internal.http2.Http2Stream r0 = okhttp3.internal.http2.Http2Stream.this
                monitor-enter(r0)
                boolean r1 = r8.closed     // Catch:{ all -> 0x005a }
                if (r1 == 0) goto L_0x0011
                monitor-exit(r0)     // Catch:{ all -> 0x005a }
                return
            L_0x0011:
                monitor-exit(r0)     // Catch:{ all -> 0x005a }
                okhttp3.internal.http2.Http2Stream r0 = okhttp3.internal.http2.Http2Stream.this
                okhttp3.internal.http2.Http2Stream$FramingSink r0 = r0.sink
                boolean r0 = r0.finished
                r1 = 1
                if (r0 != 0) goto L_0x0044
                okio.Buffer r0 = r8.sendBuffer
                long r2 = r0.size()
                r4 = 0
                int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r0 <= 0) goto L_0x0035
            L_0x0027:
                okio.Buffer r0 = r8.sendBuffer
                long r2 = r0.size()
                int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r0 <= 0) goto L_0x0044
                r8.emitFrame(r1)
                goto L_0x0027
            L_0x0035:
                okhttp3.internal.http2.Http2Stream r0 = okhttp3.internal.http2.Http2Stream.this
                okhttp3.internal.http2.Http2Connection r2 = r0.connection
                okhttp3.internal.http2.Http2Stream r0 = okhttp3.internal.http2.Http2Stream.this
                int r3 = r0.f712id
                r4 = 1
                r5 = 0
                r6 = 0
                r2.writeData(r3, r4, r5, r6)
            L_0x0044:
                okhttp3.internal.http2.Http2Stream r2 = okhttp3.internal.http2.Http2Stream.this
                monitor-enter(r2)
                r8.closed = r1     // Catch:{ all -> 0x0057 }
                monitor-exit(r2)     // Catch:{ all -> 0x0057 }
                okhttp3.internal.http2.Http2Stream r0 = okhttp3.internal.http2.Http2Stream.this
                okhttp3.internal.http2.Http2Connection r0 = r0.connection
                r0.flush()
                okhttp3.internal.http2.Http2Stream r0 = okhttp3.internal.http2.Http2Stream.this
                r0.cancelStreamIfNecessary()
                return
            L_0x0057:
                r0 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x0057 }
                throw r0
            L_0x005a:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x005a }
                throw r1
            L_0x005d:
                java.lang.AssertionError r0 = new java.lang.AssertionError
                r0.<init>()
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.Http2Stream.FramingSink.close():void");
        }
    }

    /* access modifiers changed from: package-private */
    public void addBytesToWriteWindow(long delta) {
        this.bytesLeftInWriteWindow += delta;
        if (delta > 0) {
            notifyAll();
        }
    }

    /* access modifiers changed from: package-private */
    public void checkOutNotClosed() throws IOException {
        if (this.sink.closed) {
            throw new IOException("stream closed");
        } else if (this.sink.finished) {
            throw new IOException("stream finished");
        } else if (this.errorCode != null) {
            throw new StreamResetException(this.errorCode);
        }
    }

    /* access modifiers changed from: package-private */
    public void waitForIo() throws InterruptedIOException {
        try {
            wait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException();
        }
    }

    class StreamTimeout extends AsyncTimeout {
        StreamTimeout() {
        }

        /* access modifiers changed from: protected */
        public void timedOut() {
            Http2Stream.this.closeLater(ErrorCode.CANCEL);
        }

        /* access modifiers changed from: protected */
        public IOException newTimeoutException(IOException cause) {
            SocketTimeoutException socketTimeoutException = new SocketTimeoutException("timeout");
            if (cause != null) {
                socketTimeoutException.initCause(cause);
            }
            return socketTimeoutException;
        }

        public void exitAndThrowIfTimedOut() throws IOException {
            if (exit()) {
                throw newTimeoutException((IOException) null);
            }
        }
    }
}
