package com.google.android.gms.common.util;

import java.util.HashMap;

/* compiled from: com.google.android.gms:play-services-basement@@17.2.1 */
public class MapUtils {
    public static void writeStringMapToJson(StringBuilder sb, HashMap<String, String> hashMap) {
        sb.append("{");
        boolean z = true;
        for (String next : hashMap.keySet()) {
            if (!z) {
                sb.append(",");
            } else {
                z = false;
            }
            String str = hashMap.get(next);
            sb.append("\"").append(next).append("\":");
            if (str == null) {
                sb.append("null");
            } else {
                sb.append("\"").append(str).append("\"");
            }
        }
        sb.append("}");
    }
}
