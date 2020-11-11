package com.firebase.tubesock;

import com.bumptech.glide.load.Key;
import java.io.IOException;
import java.lang.Thread;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.StrictHostnameVerifier;
import p019io.grpc.internal.GrpcUtil;

public class WebSocket {
    static final byte OPCODE_BINARY = 2;
    static final byte OPCODE_CLOSE = 8;
    static final byte OPCODE_NONE = 0;
    static final byte OPCODE_PING = 9;
    static final byte OPCODE_PONG = 10;
    static final byte OPCODE_TEXT = 1;
    private static final String THREAD_BASE_NAME = "TubeSock";
    private static final Charset UTF8 = Charset.forName(Key.STRING_CHARSET_NAME);
    private static final AtomicInteger clientCount = new AtomicInteger(0);
    private static ThreadInitializer intializer = new ThreadInitializer() {
        public void setName(Thread t, String name) {
            t.setName(name);
        }
    };
    private static ThreadFactory threadFactory = Executors.defaultThreadFactory();
    private final int clientId;
    private WebSocketEventHandler eventHandler;
    private final WebSocketHandshake handshake;
    private final Thread innerThread;
    private final WebSocketReceiver receiver;
    private volatile Socket socket;
    private volatile State state;
    private final URI url;
    private final WebSocketWriter writer;

    private enum State {
        NONE,
        CONNECTING,
        CONNECTED,
        DISCONNECTING,
        DISCONNECTED
    }

    static ThreadFactory getThreadFactory() {
        return threadFactory;
    }

    static ThreadInitializer getIntializer() {
        return intializer;
    }

    public static void setThreadFactory(ThreadFactory threadFactory2, ThreadInitializer intializer2) {
        threadFactory = threadFactory2;
        intializer = intializer2;
    }

    public WebSocket(URI url2) {
        this(url2, (String) null);
    }

    public WebSocket(URI url2, String protocol) {
        this(url2, protocol, (Map<String, String>) null);
    }

    public WebSocket(URI url2, String protocol, Map<String, String> extraHeaders) {
        this.state = State.NONE;
        this.socket = null;
        this.eventHandler = null;
        int incrementAndGet = clientCount.incrementAndGet();
        this.clientId = incrementAndGet;
        this.innerThread = getThreadFactory().newThread(new Runnable() {
            public void run() {
                WebSocket.this.runReader();
            }
        });
        this.url = url2;
        this.handshake = new WebSocketHandshake(url2, protocol, extraHeaders);
        this.receiver = new WebSocketReceiver(this);
        this.writer = new WebSocketWriter(this, THREAD_BASE_NAME, incrementAndGet);
    }

    public void setEventHandler(WebSocketEventHandler eventHandler2) {
        this.eventHandler = eventHandler2;
    }

    /* access modifiers changed from: package-private */
    public WebSocketEventHandler getEventHandler() {
        return this.eventHandler;
    }

    public synchronized void connect() {
        if (this.state != State.NONE) {
            this.eventHandler.onError(new WebSocketException("connect() already called"));
            close();
            return;
        }
        getIntializer().setName(getInnerThread(), "TubeSockReader-" + this.clientId);
        this.state = State.CONNECTING;
        getInnerThread().start();
    }

    public synchronized void send(String data) {
        send((byte) 1, data.getBytes(UTF8));
    }

    public synchronized void send(byte[] data) {
        send((byte) 2, data);
    }

    /* access modifiers changed from: package-private */
    public synchronized void pong(byte[] data) {
        send((byte) 10, data);
    }

    private synchronized void send(byte opcode, byte[] data) {
        if (this.state != State.CONNECTED) {
            this.eventHandler.onError(new WebSocketException("error while sending data: not connected"));
        } else {
            try {
                this.writer.send(opcode, true, data);
            } catch (IOException e) {
                this.eventHandler.onError(new WebSocketException("Failed to send frame", e));
                close();
            }
        }
        return;
    }

    /* access modifiers changed from: package-private */
    public void handleReceiverError(WebSocketException e) {
        this.eventHandler.onError(e);
        if (this.state == State.CONNECTED) {
            close();
        }
        closeSocket();
    }

    /* renamed from: com.firebase.tubesock.WebSocket$3 */
    static /* synthetic */ class C10233 {
        static final /* synthetic */ int[] $SwitchMap$com$firebase$tubesock$WebSocket$State;

        static {
            int[] iArr = new int[State.values().length];
            $SwitchMap$com$firebase$tubesock$WebSocket$State = iArr;
            try {
                iArr[State.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$firebase$tubesock$WebSocket$State[State.CONNECTING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$firebase$tubesock$WebSocket$State[State.CONNECTED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$firebase$tubesock$WebSocket$State[State.DISCONNECTING.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$firebase$tubesock$WebSocket$State[State.DISCONNECTED.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public synchronized void close() {
        int i = C10233.$SwitchMap$com$firebase$tubesock$WebSocket$State[this.state.ordinal()];
        if (i == 1) {
            this.state = State.DISCONNECTED;
        } else if (i == 2) {
            closeSocket();
        } else if (i == 3) {
            sendCloseHandshake();
        } else if (i == 4) {
        } else {
            if (i == 5) {
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void onCloseOpReceived() {
        closeSocket();
    }

    private synchronized void closeSocket() {
        if (this.state != State.DISCONNECTED) {
            this.receiver.stopit();
            this.writer.stopIt();
            if (this.socket != null) {
                try {
                    this.socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            this.state = State.DISCONNECTED;
            this.eventHandler.onClose();
        }
    }

    private void sendCloseHandshake() {
        try {
            this.state = State.DISCONNECTING;
            this.writer.stopIt();
            this.writer.send((byte) 8, true, new byte[0]);
        } catch (IOException e) {
            this.eventHandler.onError(new WebSocketException("Failed to send close frame", e));
        }
    }

    private Socket createSocket() {
        String scheme = this.url.getScheme();
        String host = this.url.getHost();
        int port = this.url.getPort();
        if (scheme != null && scheme.equals("ws")) {
            if (port == -1) {
                port = 80;
            }
            try {
                return new Socket(host, port);
            } catch (UnknownHostException uhe) {
                throw new WebSocketException("unknown host: " + host, uhe);
            } catch (IOException ioe) {
                throw new WebSocketException("error while creating socket to " + this.url, ioe);
            }
        } else if (scheme == null || !scheme.equals("wss")) {
            throw new WebSocketException("unsupported protocol: " + scheme);
        } else {
            if (port == -1) {
                port = GrpcUtil.DEFAULT_PORT_SSL;
            }
            try {
                Socket socket2 = SSLSocketFactory.getDefault().createSocket(host, port);
                verifyHost((SSLSocket) socket2, host);
                return socket2;
            } catch (UnknownHostException uhe2) {
                throw new WebSocketException("unknown host: " + host, uhe2);
            } catch (IOException ioe2) {
                throw new WebSocketException("error while creating secure socket to " + this.url, ioe2);
            }
        }
    }

    private void verifyHost(SSLSocket socket2, String host) throws SSLException {
        new StrictHostnameVerifier().verify(host, (X509Certificate) socket2.getSession().getPeerCertificates()[0]);
    }

    public void blockClose() throws InterruptedException {
        if (this.writer.getInnerThread().getState() != Thread.State.NEW) {
            this.writer.getInnerThread().join();
        }
        getInnerThread().join();
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r1 = new java.io.DataInputStream(r0.getInputStream());
        r2 = r0.getOutputStream();
        r2.write(r15.handshake.getHandshake());
        r3 = false;
        r5 = new byte[1000];
        r6 = 0;
        r7 = new java.util.ArrayList<>();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0044, code lost:
        if (r3 != false) goto L_0x00b1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0046, code lost:
        r8 = r1.read();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x004b, code lost:
        if (r8 == -1) goto L_0x00a9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004d, code lost:
        r5[r6] = (byte) r8;
        r6 = r6 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0058, code lost:
        if (r5[r6 - 1] != 10) goto L_0x0083;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0060, code lost:
        if (r5[r6 - 2] != 13) goto L_0x0083;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0062, code lost:
        r9 = new java.lang.String(r5, UTF8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0073, code lost:
        if (r9.trim().equals("") == false) goto L_0x0077;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0075, code lost:
        r3 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0077, code lost:
        r7.add(r9.trim());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x007e, code lost:
        r5 = new byte[1000];
        r6 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0085, code lost:
        if (r6 == 1000) goto L_0x0089;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00a8, code lost:
        throw new com.firebase.tubesock.WebSocketException("Unexpected long line in handshake: " + new java.lang.String(r5, UTF8));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00b0, code lost:
        throw new com.firebase.tubesock.WebSocketException("Connection closed before handshake was complete");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00b1, code lost:
        r15.handshake.verifyServerStatusLine(r7.get(0));
        r7.remove(0);
        r8 = new java.util.HashMap<>();
        r10 = r7.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00cd, code lost:
        if (r10.hasNext() == false) goto L_0x00e6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00cf, code lost:
        r12 = r10.next().split(": ", 2);
        r8.put(r12[0], r12[1]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00e6, code lost:
        r15.handshake.verifyServerHandshakeHeaders(r8);
        r15.writer.setOutput(r2);
        r15.receiver.setInput(r1);
        r15.state = com.firebase.tubesock.WebSocket.State.CONNECTED;
        r15.writer.getInnerThread().start();
        r15.eventHandler.onOpen();
        r15.receiver.run();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void runReader() {
        /*
            r15 = this;
            java.net.Socket r0 = r15.createSocket()     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            monitor-enter(r15)     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            r15.socket = r0     // Catch:{ all -> 0x010d }
            com.firebase.tubesock.WebSocket$State r1 = r15.state     // Catch:{ all -> 0x010d }
            com.firebase.tubesock.WebSocket$State r2 = com.firebase.tubesock.WebSocket.State.DISCONNECTED     // Catch:{ all -> 0x010d }
            if (r1 != r2) goto L_0x0022
            java.net.Socket r1 = r15.socket     // Catch:{ IOException -> 0x001b }
            r1.close()     // Catch:{ IOException -> 0x001b }
            r1 = 0
            r15.socket = r1     // Catch:{ all -> 0x010d }
            monitor-exit(r15)     // Catch:{ all -> 0x010d }
            r15.close()
            return
        L_0x001b:
            r1 = move-exception
            java.lang.RuntimeException r2 = new java.lang.RuntimeException     // Catch:{ all -> 0x010d }
            r2.<init>(r1)     // Catch:{ all -> 0x010d }
            throw r2     // Catch:{ all -> 0x010d }
        L_0x0022:
            monitor-exit(r15)     // Catch:{ all -> 0x010d }
            java.io.DataInputStream r1 = new java.io.DataInputStream     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            java.io.InputStream r2 = r0.getInputStream()     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            r1.<init>(r2)     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            java.io.OutputStream r2 = r0.getOutputStream()     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            com.firebase.tubesock.WebSocketHandshake r3 = r15.handshake     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            byte[] r3 = r3.getHandshake()     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            r2.write(r3)     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            r3 = 0
            r4 = 1000(0x3e8, float:1.401E-42)
            byte[] r5 = new byte[r4]     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            r6 = 0
            java.util.ArrayList r7 = new java.util.ArrayList     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            r7.<init>()     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
        L_0x0044:
            if (r3 != 0) goto L_0x00b1
            int r8 = r1.read()     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            r9 = -1
            if (r8 == r9) goto L_0x00a9
            byte r9 = (byte) r8     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            r5[r6] = r9     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            int r6 = r6 + 1
            int r9 = r6 + -1
            byte r9 = r5[r9]     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            r10 = 10
            if (r9 != r10) goto L_0x0083
            int r9 = r6 + -2
            byte r9 = r5[r9]     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            r10 = 13
            if (r9 != r10) goto L_0x0083
            java.lang.String r9 = new java.lang.String     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            java.nio.charset.Charset r10 = UTF8     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            r9.<init>(r5, r10)     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            java.lang.String r10 = r9.trim()     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            java.lang.String r11 = ""
            boolean r10 = r10.equals(r11)     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            if (r10 == 0) goto L_0x0077
            r3 = 1
            goto L_0x007e
        L_0x0077:
            java.lang.String r10 = r9.trim()     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            r7.add(r10)     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
        L_0x007e:
            byte[] r10 = new byte[r4]     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            r5 = r10
            r6 = 0
            goto L_0x0087
        L_0x0083:
            r9 = 1000(0x3e8, float:1.401E-42)
            if (r6 == r9) goto L_0x0089
        L_0x0087:
            goto L_0x0044
        L_0x0089:
            java.lang.String r9 = new java.lang.String     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            java.nio.charset.Charset r10 = UTF8     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            r9.<init>(r5, r10)     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            com.firebase.tubesock.WebSocketException r10 = new com.firebase.tubesock.WebSocketException     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            r11.<init>()     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            java.lang.String r12 = "Unexpected long line in handshake: "
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            java.lang.StringBuilder r11 = r11.append(r9)     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            java.lang.String r11 = r11.toString()     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            r10.<init>(r11)     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            throw r10     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
        L_0x00a9:
            com.firebase.tubesock.WebSocketException r9 = new com.firebase.tubesock.WebSocketException     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            java.lang.String r10 = "Connection closed before handshake was complete"
            r9.<init>(r10)     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            throw r9     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
        L_0x00b1:
            com.firebase.tubesock.WebSocketHandshake r8 = r15.handshake     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            r9 = 0
            java.lang.Object r10 = r7.get(r9)     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            java.lang.String r10 = (java.lang.String) r10     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            r8.verifyServerStatusLine(r10)     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            r7.remove(r9)     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            java.util.HashMap r8 = new java.util.HashMap     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            r8.<init>()     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            java.util.Iterator r10 = r7.iterator()     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
        L_0x00c9:
            boolean r11 = r10.hasNext()     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            if (r11 == 0) goto L_0x00e6
            java.lang.Object r11 = r10.next()     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            java.lang.String r11 = (java.lang.String) r11     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            java.lang.String r12 = ": "
            r13 = 2
            java.lang.String[] r12 = r11.split(r12, r13)     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            r13 = r12[r9]     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            r14 = 1
            r14 = r12[r14]     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            r8.put(r13, r14)     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            goto L_0x00c9
        L_0x00e6:
            com.firebase.tubesock.WebSocketHandshake r9 = r15.handshake     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            r9.verifyServerHandshakeHeaders(r8)     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            com.firebase.tubesock.WebSocketWriter r9 = r15.writer     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            r9.setOutput(r2)     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            com.firebase.tubesock.WebSocketReceiver r9 = r15.receiver     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            r9.setInput(r1)     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            com.firebase.tubesock.WebSocket$State r9 = com.firebase.tubesock.WebSocket.State.CONNECTED     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            r15.state = r9     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            com.firebase.tubesock.WebSocketWriter r9 = r15.writer     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            java.lang.Thread r9 = r9.getInnerThread()     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            r9.start()     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            com.firebase.tubesock.WebSocketEventHandler r9 = r15.eventHandler     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            r9.onOpen()     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            com.firebase.tubesock.WebSocketReceiver r9 = r15.receiver     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            r9.run()     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
            goto L_0x013b
        L_0x010d:
            r1 = move-exception
            monitor-exit(r15)     // Catch:{ all -> 0x010d }
            throw r1     // Catch:{ WebSocketException -> 0x0135, IOException -> 0x0112 }
        L_0x0110:
            r0 = move-exception
            goto L_0x0140
        L_0x0112:
            r0 = move-exception
            com.firebase.tubesock.WebSocketEventHandler r1 = r15.eventHandler     // Catch:{ all -> 0x0110 }
            com.firebase.tubesock.WebSocketException r2 = new com.firebase.tubesock.WebSocketException     // Catch:{ all -> 0x0110 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0110 }
            r3.<init>()     // Catch:{ all -> 0x0110 }
            java.lang.String r4 = "error while connecting: "
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x0110 }
            java.lang.String r4 = r0.getMessage()     // Catch:{ all -> 0x0110 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x0110 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0110 }
            r2.<init>(r3, r0)     // Catch:{ all -> 0x0110 }
            r1.onError(r2)     // Catch:{ all -> 0x0110 }
            goto L_0x013b
        L_0x0135:
            r0 = move-exception
            com.firebase.tubesock.WebSocketEventHandler r1 = r15.eventHandler     // Catch:{ all -> 0x0110 }
            r1.onError(r0)     // Catch:{ all -> 0x0110 }
        L_0x013b:
            r15.close()
            return
        L_0x0140:
            r15.close()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.firebase.tubesock.WebSocket.runReader():void");
    }

    /* access modifiers changed from: package-private */
    public Thread getInnerThread() {
        return this.innerThread;
    }
}
