package com.google.android.play.core.internal;

import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

/* renamed from: com.google.android.play.core.internal.f */
final class C3054f extends C3055g {

    /* renamed from: a */
    private final byte[] f1333a;

    public C3054f(X509Certificate x509Certificate, byte[] bArr) {
        super(x509Certificate);
        this.f1333a = bArr;
    }

    public final byte[] getEncoded() throws CertificateEncodingException {
        return this.f1333a;
    }
}
