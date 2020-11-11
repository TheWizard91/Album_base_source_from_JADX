package com.google.firebase.encoders;

import java.io.IOException;
import java.io.Writer;

/* compiled from: com.google.firebase:firebase-encoders-json@@16.1.0 */
public interface DataEncoder {
    String encode(Object obj);

    void encode(Object obj, Writer writer) throws IOException;
}
