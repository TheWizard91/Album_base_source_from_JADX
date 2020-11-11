package com.firebase.client.realtime;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.firebase.client.core.Context;
import com.firebase.client.core.RepoInfo;
import com.firebase.client.realtime.util.StringListReader;
import com.firebase.client.utilities.LogWrapper;
import com.firebase.client.utilities.Utilities;
import com.firebase.client.utilities.encoding.JsonHelpers;
import com.firebase.tubesock.WebSocket;
import com.firebase.tubesock.WebSocketEventHandler;
import com.firebase.tubesock.WebSocketException;
import com.firebase.tubesock.WebSocketMessage;
import com.google.common.net.HttpHeaders;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class WebsocketConnection {
    private static final long CONNECT_TIMEOUT = 30000;
    private static final long KEEP_ALIVE = 45000;
    private static final int MAX_FRAME_SIZE = 16384;
    private static long connectionId = 0;
    /* access modifiers changed from: private */
    public WSClient conn;
    /* access modifiers changed from: private */
    public ScheduledFuture connectTimeout;
    /* access modifiers changed from: private */
    public Context ctx;
    private Delegate delegate;
    /* access modifiers changed from: private */
    public boolean everConnected = false;
    private StringListReader frameReader;
    private boolean isClosed = false;
    private ScheduledFuture keepAlive;
    /* access modifiers changed from: private */
    public LogWrapper logger;
    private MapType mapType;
    private ObjectMapper mapper;
    private long totalFrames = 0;

    public interface Delegate {
        void onDisconnect(boolean z);

        void onMessage(Map<String, Object> map);
    }

    private interface WSClient {
        void close();

        void connect();

        void send(String str);
    }

    private class WSClientTubesock implements WSClient, WebSocketEventHandler {

        /* renamed from: ws */
        private WebSocket f95ws;

        private WSClientTubesock(WebSocket ws) {
            this.f95ws = ws;
            ws.setEventHandler(this);
        }

        public void onOpen() {
            WebsocketConnection.this.ctx.getRunLoop().scheduleNow(new Runnable() {
                public void run() {
                    WebsocketConnection.this.connectTimeout.cancel(false);
                    boolean unused = WebsocketConnection.this.everConnected = true;
                    if (WebsocketConnection.this.logger.logsDebug()) {
                        WebsocketConnection.this.logger.debug("websocket opened");
                    }
                    WebsocketConnection.this.resetKeepAlive();
                }
            });
        }

        public void onMessage(WebSocketMessage msg) {
            final String str = msg.getText();
            if (WebsocketConnection.this.logger.logsDebug()) {
                WebsocketConnection.this.logger.debug("ws message: " + str);
            }
            WebsocketConnection.this.ctx.getRunLoop().scheduleNow(new Runnable() {
                public void run() {
                    WebsocketConnection.this.handleIncomingFrame(str);
                }
            });
        }

        public void onClose() {
            WebsocketConnection.this.ctx.getRunLoop().scheduleNow(new Runnable() {
                public void run() {
                    if (WebsocketConnection.this.logger.logsDebug()) {
                        WebsocketConnection.this.logger.debug("closed");
                    }
                    WebsocketConnection.this.onClosed();
                }
            });
        }

        public void onError(final WebSocketException e) {
            WebsocketConnection.this.ctx.getRunLoop().scheduleNow(new Runnable() {
                public void run() {
                    if (WebsocketConnection.this.logger.logsDebug()) {
                        WebsocketConnection.this.logger.debug("had an error", e);
                    }
                    if (e.getMessage().startsWith("unknown host")) {
                        if (WebsocketConnection.this.logger.logsDebug()) {
                            WebsocketConnection.this.logger.debug("If you are running on Android, have you added <uses-permission android:name=\"android.permission.INTERNET\" /> under <manifest> in AndroidManifest.xml?");
                        }
                    } else if (WebsocketConnection.this.logger.logsDebug()) {
                        WebsocketConnection.this.logger.debug("|" + e.getMessage() + "|");
                    }
                    WebsocketConnection.this.onClosed();
                }
            });
        }

        public void onLogMessage(String msg) {
            if (WebsocketConnection.this.logger.logsDebug()) {
                WebsocketConnection.this.logger.debug("Tubesock: " + msg);
            }
        }

        public void send(String msg) {
            this.f95ws.send(msg);
        }

        public void close() {
            this.f95ws.close();
        }

        private void shutdown() {
            this.f95ws.close();
            try {
                this.f95ws.blockClose();
            } catch (InterruptedException e) {
                WebsocketConnection.this.logger.error("Interrupted while shutting down websocket threads", e);
            }
        }

        public void connect() {
            try {
                this.f95ws.connect();
            } catch (WebSocketException e) {
                if (WebsocketConnection.this.logger.logsDebug()) {
                    WebsocketConnection.this.logger.debug("Error connecting", e);
                }
                shutdown();
            }
        }
    }

    public WebsocketConnection(Context ctx2, RepoInfo repoInfo, Delegate delegate2, String optLastSessionId) {
        long connId = connectionId;
        connectionId = 1 + connId;
        ObjectMapper mapper2 = JsonHelpers.getMapper();
        this.mapper = mapper2;
        this.mapType = mapper2.getTypeFactory().constructMapType((Class<? extends Map>) HashMap.class, (Class<?>) String.class, (Class<?>) Object.class);
        this.delegate = delegate2;
        this.ctx = ctx2;
        this.logger = ctx2.getLogger("WebSocket", "ws_" + connId);
        this.conn = createConnection(repoInfo, optLastSessionId);
    }

    private WSClient createConnection(RepoInfo repoInfo, String optLastSessionId) {
        URI uri = repoInfo.getConnectionURL(optLastSessionId);
        Map<String, String> extraHeaders = new HashMap<>();
        extraHeaders.put(HttpHeaders.USER_AGENT, this.ctx.getUserAgent());
        return new WSClientTubesock(new WebSocket(uri, (String) null, extraHeaders));
    }

    public void open() {
        this.conn.connect();
        this.connectTimeout = this.ctx.getRunLoop().schedule(new Runnable() {
            public void run() {
                WebsocketConnection.this.closeIfNeverConnected();
            }
        }, CONNECT_TIMEOUT);
    }

    public void start() {
    }

    public void close() {
        if (this.logger.logsDebug()) {
            this.logger.debug("websocket is being closed");
        }
        this.isClosed = true;
        this.conn.close();
        ScheduledFuture scheduledFuture = this.connectTimeout;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }
        ScheduledFuture scheduledFuture2 = this.keepAlive;
        if (scheduledFuture2 != null) {
            scheduledFuture2.cancel(true);
        }
    }

    public void send(Map<String, Object> message) {
        resetKeepAlive();
        try {
            String[] segs = Utilities.splitIntoFrames(this.mapper.writeValueAsString(message), 16384);
            if (segs.length > 1) {
                this.conn.send("" + segs.length);
            }
            for (String send : segs) {
                this.conn.send(send);
            }
        } catch (IOException e) {
            this.logger.error("Failed to serialize message: " + message.toString(), e);
            shutdown();
        }
    }

    private void appendFrame(String message) {
        this.frameReader.addString(message);
        long j = this.totalFrames - 1;
        this.totalFrames = j;
        if (j == 0) {
            try {
                this.frameReader.freeze();
                Map<String, Object> decoded = (Map) this.mapper.readValue((Reader) this.frameReader, (JavaType) this.mapType);
                this.frameReader = null;
                if (this.logger.logsDebug()) {
                    this.logger.debug("handleIncomingFrame complete frame: " + decoded);
                }
                this.delegate.onMessage(decoded);
            } catch (IOException e) {
                this.logger.error("Error parsing frame: " + this.frameReader.toString(), e);
                close();
                shutdown();
            } catch (ClassCastException e2) {
                this.logger.error("Error parsing frame (cast error): " + this.frameReader.toString(), e2);
                close();
                shutdown();
            }
        }
    }

    private void handleNewFrameCount(int numFrames) {
        this.totalFrames = (long) numFrames;
        this.frameReader = new StringListReader();
        if (this.logger.logsDebug()) {
            this.logger.debug("HandleNewFrameCount: " + this.totalFrames);
        }
    }

    private String extractFrameCount(String message) {
        if (message.length() <= 6) {
            try {
                int frameCount = Integer.parseInt(message);
                if (frameCount <= 0) {
                    return null;
                }
                handleNewFrameCount(frameCount);
                return null;
            } catch (NumberFormatException e) {
            }
        }
        handleNewFrameCount(1);
        return message;
    }

    /* access modifiers changed from: private */
    public void handleIncomingFrame(String message) {
        if (!this.isClosed) {
            resetKeepAlive();
            if (isBuffering()) {
                appendFrame(message);
                return;
            }
            String remaining = extractFrameCount(message);
            if (remaining != null) {
                appendFrame(remaining);
            }
        }
    }

    /* access modifiers changed from: private */
    public void resetKeepAlive() {
        if (!this.isClosed) {
            ScheduledFuture scheduledFuture = this.keepAlive;
            if (scheduledFuture != null) {
                scheduledFuture.cancel(false);
                if (this.logger.logsDebug()) {
                    this.logger.debug("Reset keepAlive. Remaining: " + this.keepAlive.getDelay(TimeUnit.MILLISECONDS));
                }
            } else if (this.logger.logsDebug()) {
                this.logger.debug("Reset keepAlive");
            }
            this.keepAlive = this.ctx.getRunLoop().schedule(nop(), KEEP_ALIVE);
        }
    }

    private Runnable nop() {
        return new Runnable() {
            public void run() {
                if (WebsocketConnection.this.conn != null) {
                    WebsocketConnection.this.conn.send("0");
                    WebsocketConnection.this.resetKeepAlive();
                }
            }
        };
    }

    private boolean isBuffering() {
        return this.frameReader != null;
    }

    /* access modifiers changed from: private */
    public void onClosed() {
        if (!this.isClosed) {
            if (this.logger.logsDebug()) {
                this.logger.debug("closing itself");
            }
            shutdown();
        }
        this.conn = null;
        ScheduledFuture scheduledFuture = this.keepAlive;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }
    }

    private void shutdown() {
        this.isClosed = true;
        this.delegate.onDisconnect(this.everConnected);
    }

    /* access modifiers changed from: private */
    public void closeIfNeverConnected() {
        if (!this.everConnected && !this.isClosed) {
            if (this.logger.logsDebug()) {
                this.logger.debug("timed out on connect");
            }
            this.conn.close();
        }
    }
}
