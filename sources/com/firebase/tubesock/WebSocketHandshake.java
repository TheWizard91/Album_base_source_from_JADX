package com.firebase.tubesock;

import com.google.common.net.HttpHeaders;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

class WebSocketHandshake {
    private static final String WEBSOCKET_VERSION = "13";
    private Map<String, String> extraHeaders = null;
    private String nonce = null;
    private String protocol = null;
    private URI url = null;

    public WebSocketHandshake(URI url2, String protocol2, Map<String, String> extraHeaders2) {
        this.url = url2;
        this.protocol = protocol2;
        this.extraHeaders = extraHeaders2;
        this.nonce = createNonce();
    }

    public byte[] getHandshake() {
        String path = this.url.getPath();
        String query = this.url.getQuery();
        String path2 = path + (query == null ? "" : "?" + query);
        String host = this.url.getHost();
        if (this.url.getPort() != -1) {
            host = host + ":" + this.url.getPort();
        }
        LinkedHashMap<String, String> header = new LinkedHashMap<>();
        header.put(HttpHeaders.HOST, host);
        header.put(HttpHeaders.UPGRADE, "websocket");
        header.put(HttpHeaders.CONNECTION, HttpHeaders.UPGRADE);
        header.put(HttpHeaders.SEC_WEBSOCKET_VERSION, WEBSOCKET_VERSION);
        header.put(HttpHeaders.SEC_WEBSOCKET_KEY, this.nonce);
        String str = this.protocol;
        if (str != null) {
            header.put(HttpHeaders.SEC_WEBSOCKET_PROTOCOL, str);
        }
        Map<String, String> map = this.extraHeaders;
        if (map != null) {
            for (String fieldName : map.keySet()) {
                if (!header.containsKey(fieldName)) {
                    header.put(fieldName, this.extraHeaders.get(fieldName));
                }
            }
        }
        String handshake = (("GET " + path2 + " HTTP/1.1\r\n") + generateHeader(header)) + "\r\n";
        byte[] handshakeBytes = new byte[handshake.getBytes().length];
        System.arraycopy(handshake.getBytes(), 0, handshakeBytes, 0, handshake.getBytes().length);
        return handshakeBytes;
    }

    private String generateHeader(LinkedHashMap<String, String> headers) {
        String header = new String();
        for (String fieldName : headers.keySet()) {
            header = header + fieldName + ": " + headers.get(fieldName) + "\r\n";
        }
        return header;
    }

    private String createNonce() {
        byte[] nonce2 = new byte[16];
        for (int i = 0; i < 16; i++) {
            nonce2[i] = (byte) rand(0, 255);
        }
        return Base64.encodeToString(nonce2, false);
    }

    public void verifyServerStatusLine(String statusLine) {
        int statusCode = Integer.valueOf(statusLine.substring(9, 12)).intValue();
        if (statusCode == 407) {
            throw new WebSocketException("connection failed: proxy authentication not supported");
        } else if (statusCode == 404) {
            throw new WebSocketException("connection failed: 404 not found");
        } else if (statusCode != 101) {
            throw new WebSocketException("connection failed: unknown status code " + statusCode);
        }
    }

    public void verifyServerHandshakeHeaders(HashMap<String, String> headers) {
        if (!headers.get(HttpHeaders.UPGRADE).toLowerCase(Locale.US).equals("websocket")) {
            throw new WebSocketException("connection failed: missing header field in server handshake: Upgrade");
        } else if (!headers.get(HttpHeaders.CONNECTION).toLowerCase(Locale.US).equals("upgrade")) {
            throw new WebSocketException("connection failed: missing header field in server handshake: Connection");
        }
    }

    private int rand(int min, int max) {
        return (int) ((Math.random() * ((double) max)) + ((double) min));
    }
}
