package com.firebase.client;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ServerValue {
    public static final Map<String, String> TIMESTAMP = createServerValuePlaceholder("timestamp");

    private static Map<String, String> createServerValuePlaceholder(String key) {
        Map<String, String> result = new HashMap<>();
        result.put(".sv", key);
        return Collections.unmodifiableMap(result);
    }
}
