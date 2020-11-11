package com.google.android.play.core.internal;

import android.support.p000v4.media.session.PlaybackStateCompat;
import android.util.Pair;
import androidx.mediarouter.media.MediaRouter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.google.android.play.core.internal.h */
public final class C3056h {
    /* renamed from: a */
    private static int m781a(int i) {
        if (i == 513) {
            return 1;
        }
        if (i == 514) {
            return 2;
        }
        if (i == 769) {
            return 1;
        }
        switch (i) {
            case 257:
            case MediaRouter.GlobalMediaRouter.CallbackHandler.MSG_ROUTE_CHANGED:
                return 1;
            case MediaRouter.GlobalMediaRouter.CallbackHandler.MSG_ROUTE_REMOVED:
            case MediaRouter.GlobalMediaRouter.CallbackHandler.MSG_ROUTE_VOLUME_CHANGED:
                return 2;
            default:
                String valueOf = String.valueOf(Long.toHexString((long) i));
                throw new IllegalArgumentException(valueOf.length() == 0 ? new String("Unknown signature algorithm: 0x") : "Unknown signature algorithm: 0x".concat(valueOf));
        }
    }

    /* renamed from: a */
    public static long m782a(ByteBuffer byteBuffer) {
        m799c(byteBuffer);
        return m783a(byteBuffer, byteBuffer.position() + 16);
    }

    /* renamed from: a */
    private static long m783a(ByteBuffer byteBuffer, int i) {
        return ((long) byteBuffer.getInt(i)) & 4294967295L;
    }

    /* renamed from: a */
    static Pair<ByteBuffer, Long> m784a(RandomAccessFile randomAccessFile) throws IOException {
        if (randomAccessFile.length() < 22) {
            return null;
        }
        Pair<ByteBuffer, Long> a = m785a(randomAccessFile, 0);
        return a == null ? m785a(randomAccessFile, 65535) : a;
    }

    /* renamed from: a */
    private static Pair<ByteBuffer, Long> m785a(RandomAccessFile randomAccessFile, int i) throws IOException {
        int i2;
        long length = randomAccessFile.length();
        if (length >= 22) {
            ByteBuffer allocate = ByteBuffer.allocate(((int) Math.min((long) i, -22 + length)) + 22);
            allocate.order(ByteOrder.LITTLE_ENDIAN);
            long capacity = length - ((long) allocate.capacity());
            randomAccessFile.seek(capacity);
            randomAccessFile.readFully(allocate.array(), allocate.arrayOffset(), allocate.capacity());
            m799c(allocate);
            int capacity2 = allocate.capacity();
            if (capacity2 >= 22) {
                int i3 = capacity2 - 22;
                int min = Math.min(i3, 65535);
                int i4 = 0;
                while (true) {
                    if (i4 >= min) {
                        break;
                    }
                    i2 = i3 - i4;
                    if (allocate.getInt(i2) == 101010256 && ((char) allocate.getShort(i2 + 20)) == i4) {
                        break;
                    }
                    i4++;
                }
            }
            i2 = -1;
            if (i2 != -1) {
                allocate.position(i2);
                ByteBuffer slice = allocate.slice();
                slice.order(ByteOrder.LITTLE_ENDIAN);
                return Pair.create(slice, Long.valueOf(capacity + ((long) i2)));
            }
        }
        return null;
    }

    /* renamed from: a */
    public static String m786a(String str, String str2) {
        StringBuilder sb = new StringBuilder(str.length() + 1 + String.valueOf(str2).length());
        sb.append(str);
        sb.append(":");
        sb.append(str2);
        return sb.toString();
    }

    /* renamed from: a */
    public static String m787a(String str, String str2, String str3) {
        int length = str.length();
        StringBuilder sb = new StringBuilder(length + 2 + String.valueOf(str2).length() + String.valueOf(str3).length());
        sb.append(str);
        sb.append(":");
        sb.append(str2);
        sb.append(":");
        sb.append(str3);
        return sb.toString();
    }

    /* renamed from: a */
    private static void m788a(int i, byte[] bArr) {
        bArr[1] = (byte) (i & 255);
        bArr[2] = (byte) ((i >>> 8) & 255);
        bArr[3] = (byte) ((i >>> 16) & 255);
        bArr[4] = (byte) (i >> 24);
    }

    /* renamed from: a */
    public static void m789a(ByteBuffer byteBuffer, long j) {
        m799c(byteBuffer);
        int position = byteBuffer.position() + 16;
        if (j < 0 || j > 4294967295L) {
            StringBuilder sb = new StringBuilder(47);
            sb.append("uint32 value of out range: ");
            sb.append(j);
            throw new IllegalArgumentException(sb.toString());
        }
        byteBuffer.putInt(byteBuffer.position() + position, (int) j);
    }

    /* renamed from: a */
    private static void m790a(Map<Integer, byte[]> map, FileChannel fileChannel, long j, long j2, long j3, ByteBuffer byteBuffer) throws SecurityException {
        if (!map.isEmpty()) {
            C3042c cVar = new C3042c(fileChannel, 0, j);
            C3042c cVar2 = new C3042c(fileChannel, j2, j3 - j2);
            ByteBuffer duplicate = byteBuffer.duplicate();
            duplicate.order(ByteOrder.LITTLE_ENDIAN);
            long j4 = j;
            m789a(duplicate, j);
            C2988a aVar = new C2988a(duplicate);
            int size = map.size();
            int[] iArr = new int[size];
            int i = 0;
            int i2 = 0;
            for (Integer intValue : map.keySet()) {
                iArr[i2] = intValue.intValue();
                i2++;
            }
            try {
                byte[][] a = m792a(iArr, new C3015b[]{cVar, cVar2, aVar});
                while (i < size) {
                    int i3 = iArr[i];
                    Map<Integer, byte[]> map2 = map;
                    if (MessageDigest.isEqual(map.get(Integer.valueOf(i3)), a[i])) {
                        i++;
                    } else {
                        throw new SecurityException(m796b(i3).concat(" digest of contents did not verify"));
                    }
                }
            } catch (DigestException e) {
                throw new SecurityException("Failed to compute digest(s) of contents", e);
            }
        } else {
            throw new SecurityException("No digests provided");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:54:0x010d, code lost:
        r1 = android.util.Pair.create(r5, r1);
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.security.cert.X509Certificate[] m791a(java.nio.ByteBuffer r22, java.util.Map<java.lang.Integer, byte[]> r23, java.security.cert.CertificateFactory r24) throws java.lang.SecurityException, java.io.IOException {
        /*
            java.nio.ByteBuffer r0 = m800d(r22)
            java.nio.ByteBuffer r1 = m800d(r22)
            byte[] r2 = m801e(r22)
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r4 = -1
            r6 = 0
            r7 = r4
            r9 = r6
            r8 = 0
        L_0x0016:
            boolean r10 = r1.hasRemaining()
            r11 = 8
            r12 = 769(0x301, float:1.078E-42)
            r13 = 514(0x202, float:7.2E-43)
            r14 = 513(0x201, float:7.19E-43)
            r15 = 1
            if (r10 == 0) goto L_0x0080
            int r8 = r8 + 1
            java.nio.ByteBuffer r10 = m800d(r1)     // Catch:{ IOException -> 0x0066, BufferUnderflowException -> 0x0064 }
            int r5 = r10.remaining()     // Catch:{ IOException -> 0x0066, BufferUnderflowException -> 0x0064 }
            if (r5 < r11) goto L_0x005c
            int r5 = r10.getInt()     // Catch:{ IOException -> 0x0066, BufferUnderflowException -> 0x0064 }
            java.lang.Integer r11 = java.lang.Integer.valueOf(r5)     // Catch:{ IOException -> 0x0066, BufferUnderflowException -> 0x0064 }
            r3.add(r11)     // Catch:{ IOException -> 0x0066, BufferUnderflowException -> 0x0064 }
            if (r5 == r14) goto L_0x0046
            if (r5 == r13) goto L_0x0046
            if (r5 == r12) goto L_0x0046
            switch(r5) {
                case 257: goto L_0x0046;
                case 258: goto L_0x0046;
                case 259: goto L_0x0046;
                case 260: goto L_0x0046;
                default: goto L_0x0045;
            }     // Catch:{ IOException -> 0x0066, BufferUnderflowException -> 0x0064 }
        L_0x0045:
            goto L_0x0016
        L_0x0046:
            if (r7 == r4) goto L_0x0055
            int r11 = m781a((int) r5)     // Catch:{ IOException -> 0x0066, BufferUnderflowException -> 0x0064 }
            int r12 = m781a((int) r7)     // Catch:{ IOException -> 0x0066, BufferUnderflowException -> 0x0064 }
            if (r11 == r15) goto L_0x0016
            if (r12 == r15) goto L_0x0055
            goto L_0x0016
        L_0x0055:
            byte[] r7 = m801e(r10)     // Catch:{ IOException -> 0x0066, BufferUnderflowException -> 0x0064 }
            r9 = r7
            r7 = r5
            goto L_0x0016
        L_0x005c:
            java.lang.SecurityException r0 = new java.lang.SecurityException     // Catch:{ IOException -> 0x0066, BufferUnderflowException -> 0x0064 }
            java.lang.String r1 = "Signature record too short"
            r0.<init>(r1)     // Catch:{ IOException -> 0x0066, BufferUnderflowException -> 0x0064 }
            throw r0     // Catch:{ IOException -> 0x0066, BufferUnderflowException -> 0x0064 }
        L_0x0064:
            r0 = move-exception
            goto L_0x0067
        L_0x0066:
            r0 = move-exception
        L_0x0067:
            java.lang.SecurityException r1 = new java.lang.SecurityException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r3 = 45
            r2.<init>(r3)
            java.lang.String r3 = "Failed to parse signature record #"
            r2.append(r3)
            r2.append(r8)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2, r0)
            throw r1
        L_0x0080:
            if (r7 == r4) goto L_0x028d
            java.lang.String r1 = "Unknown signature algorithm: 0x"
            if (r7 == r14) goto L_0x00b2
            if (r7 == r13) goto L_0x00b2
            if (r7 == r12) goto L_0x00af
            switch(r7) {
                case 257: goto L_0x00ac;
                case 258: goto L_0x00ac;
                case 259: goto L_0x00ac;
                case 260: goto L_0x00ac;
                default: goto L_0x008d;
            }
        L_0x008d:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            long r2 = (long) r7
            java.lang.String r2 = java.lang.Long.toHexString(r2)
            java.lang.String r2 = java.lang.String.valueOf(r2)
            int r3 = r2.length()
            if (r3 != 0) goto L_0x00a4
            java.lang.String r2 = new java.lang.String
            r2.<init>(r1)
            goto L_0x00a8
        L_0x00a4:
            java.lang.String r2 = r1.concat(r2)
        L_0x00a8:
            r0.<init>(r2)
            throw r0
        L_0x00ac:
            java.lang.String r4 = "RSA"
            goto L_0x00b4
        L_0x00af:
            java.lang.String r4 = "DSA"
            goto L_0x00b4
        L_0x00b2:
            java.lang.String r4 = "EC"
        L_0x00b4:
            if (r7 == r14) goto L_0x0118
            if (r7 == r13) goto L_0x0115
            if (r7 == r12) goto L_0x0112
            switch(r7) {
                case 257: goto L_0x00fa;
                case 258: goto L_0x00e6;
                case 259: goto L_0x00df;
                case 260: goto L_0x00dc;
                default: goto L_0x00bd;
            }
        L_0x00bd:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            long r2 = (long) r7
            java.lang.String r2 = java.lang.Long.toHexString(r2)
            java.lang.String r2 = java.lang.String.valueOf(r2)
            int r3 = r2.length()
            if (r3 != 0) goto L_0x00d4
            java.lang.String r2 = new java.lang.String
            r2.<init>(r1)
            goto L_0x00d8
        L_0x00d4:
            java.lang.String r2 = r1.concat(r2)
        L_0x00d8:
            r0.<init>(r2)
            throw r0
        L_0x00dc:
            java.lang.String r1 = "SHA512withRSA"
            goto L_0x00e1
        L_0x00df:
            java.lang.String r1 = "SHA256withRSA"
        L_0x00e1:
            android.util.Pair r1 = android.util.Pair.create(r1, r6)
            goto L_0x011b
        L_0x00e6:
            java.security.spec.PSSParameterSpec r1 = new java.security.spec.PSSParameterSpec
            java.security.spec.MGF1ParameterSpec r19 = java.security.spec.MGF1ParameterSpec.SHA512
            r20 = 64
            r21 = 1
            java.lang.String r17 = "SHA-512"
            java.lang.String r18 = "MGF1"
            r16 = r1
            r16.<init>(r17, r18, r19, r20, r21)
            java.lang.String r5 = "SHA512withRSA/PSS"
            goto L_0x010d
        L_0x00fa:
            java.security.spec.PSSParameterSpec r1 = new java.security.spec.PSSParameterSpec
            java.security.spec.MGF1ParameterSpec r19 = java.security.spec.MGF1ParameterSpec.SHA256
            r20 = 32
            r21 = 1
            java.lang.String r17 = "SHA-256"
            java.lang.String r18 = "MGF1"
            r16 = r1
            r16.<init>(r17, r18, r19, r20, r21)
            java.lang.String r5 = "SHA256withRSA/PSS"
        L_0x010d:
            android.util.Pair r1 = android.util.Pair.create(r5, r1)
            goto L_0x011b
        L_0x0112:
            java.lang.String r1 = "SHA256withDSA"
            goto L_0x00e1
        L_0x0115:
            java.lang.String r1 = "SHA512withECDSA"
            goto L_0x00e1
        L_0x0118:
            java.lang.String r1 = "SHA256withECDSA"
            goto L_0x00e1
        L_0x011b:
            java.lang.Object r5 = r1.first
            java.lang.String r5 = (java.lang.String) r5
            java.lang.Object r1 = r1.second
            java.security.spec.AlgorithmParameterSpec r1 = (java.security.spec.AlgorithmParameterSpec) r1
            java.security.KeyFactory r4 = java.security.KeyFactory.getInstance(r4)     // Catch:{ NoSuchAlgorithmException -> 0x0266, InvalidKeySpecException -> 0x0264, InvalidKeyException -> 0x0262, InvalidAlgorithmParameterException -> 0x0260, SignatureException -> 0x025e }
            java.security.spec.X509EncodedKeySpec r8 = new java.security.spec.X509EncodedKeySpec     // Catch:{ NoSuchAlgorithmException -> 0x0266, InvalidKeySpecException -> 0x0264, InvalidKeyException -> 0x0262, InvalidAlgorithmParameterException -> 0x0260, SignatureException -> 0x025e }
            r8.<init>(r2)     // Catch:{ NoSuchAlgorithmException -> 0x0266, InvalidKeySpecException -> 0x0264, InvalidKeyException -> 0x0262, InvalidAlgorithmParameterException -> 0x0260, SignatureException -> 0x025e }
            java.security.PublicKey r4 = r4.generatePublic(r8)     // Catch:{ NoSuchAlgorithmException -> 0x0266, InvalidKeySpecException -> 0x0264, InvalidKeyException -> 0x0262, InvalidAlgorithmParameterException -> 0x0260, SignatureException -> 0x025e }
            java.security.Signature r8 = java.security.Signature.getInstance(r5)     // Catch:{ NoSuchAlgorithmException -> 0x0266, InvalidKeySpecException -> 0x0264, InvalidKeyException -> 0x0262, InvalidAlgorithmParameterException -> 0x0260, SignatureException -> 0x025e }
            r8.initVerify(r4)     // Catch:{ NoSuchAlgorithmException -> 0x0266, InvalidKeySpecException -> 0x0264, InvalidKeyException -> 0x0262, InvalidAlgorithmParameterException -> 0x0260, SignatureException -> 0x025e }
            if (r1 != 0) goto L_0x013a
            goto L_0x013d
        L_0x013a:
            r8.setParameter(r1)     // Catch:{ NoSuchAlgorithmException -> 0x0266, InvalidKeySpecException -> 0x0264, InvalidKeyException -> 0x0262, InvalidAlgorithmParameterException -> 0x0260, SignatureException -> 0x025e }
        L_0x013d:
            r8.update(r0)     // Catch:{ NoSuchAlgorithmException -> 0x0266, InvalidKeySpecException -> 0x0264, InvalidKeyException -> 0x0262, InvalidAlgorithmParameterException -> 0x0260, SignatureException -> 0x025e }
            boolean r1 = r8.verify(r9)     // Catch:{ NoSuchAlgorithmException -> 0x0266, InvalidKeySpecException -> 0x0264, InvalidKeyException -> 0x0262, InvalidAlgorithmParameterException -> 0x0260, SignatureException -> 0x025e }
            if (r1 == 0) goto L_0x024e
            r0.clear()
            java.nio.ByteBuffer r1 = m800d(r0)
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            r5 = 0
        L_0x0153:
            boolean r8 = r1.hasRemaining()
            if (r8 == 0) goto L_0x019a
            int r5 = r5 + r15
            java.nio.ByteBuffer r8 = m800d(r1)     // Catch:{ IOException -> 0x0180, BufferUnderflowException -> 0x017e }
            int r9 = r8.remaining()     // Catch:{ IOException -> 0x0180, BufferUnderflowException -> 0x017e }
            if (r9 < r11) goto L_0x0176
            int r9 = r8.getInt()     // Catch:{ IOException -> 0x0180, BufferUnderflowException -> 0x017e }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r9)     // Catch:{ IOException -> 0x0180, BufferUnderflowException -> 0x017e }
            r4.add(r10)     // Catch:{ IOException -> 0x0180, BufferUnderflowException -> 0x017e }
            if (r9 != r7) goto L_0x0153
            byte[] r6 = m801e(r8)     // Catch:{ IOException -> 0x0180, BufferUnderflowException -> 0x017e }
            goto L_0x0153
        L_0x0176:
            java.io.IOException r0 = new java.io.IOException     // Catch:{ IOException -> 0x0180, BufferUnderflowException -> 0x017e }
            java.lang.String r1 = "Record too short"
            r0.<init>(r1)     // Catch:{ IOException -> 0x0180, BufferUnderflowException -> 0x017e }
            throw r0     // Catch:{ IOException -> 0x0180, BufferUnderflowException -> 0x017e }
        L_0x017e:
            r0 = move-exception
            goto L_0x0181
        L_0x0180:
            r0 = move-exception
        L_0x0181:
            java.io.IOException r1 = new java.io.IOException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r3 = 42
            r2.<init>(r3)
            java.lang.String r3 = "Failed to parse digest record #"
            r2.append(r3)
            r2.append(r5)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2, r0)
            throw r1
        L_0x019a:
            boolean r1 = r3.equals(r4)
            if (r1 == 0) goto L_0x0246
            int r1 = m781a((int) r7)
            java.lang.Integer r3 = java.lang.Integer.valueOf(r1)
            r4 = r23
            java.lang.Object r3 = r4.put(r3, r6)
            byte[] r3 = (byte[]) r3
            if (r3 == 0) goto L_0x01c9
            boolean r3 = java.security.MessageDigest.isEqual(r3, r6)
            if (r3 == 0) goto L_0x01b9
            goto L_0x01c9
        L_0x01b9:
            java.lang.SecurityException r0 = new java.lang.SecurityException
            java.lang.String r1 = m796b((int) r1)
            java.lang.String r2 = " contents digest does not match the digest specified by a preceding signer"
            java.lang.String r1 = r1.concat(r2)
            r0.<init>(r1)
            throw r0
        L_0x01c9:
            java.nio.ByteBuffer r0 = m800d(r0)
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r3 = 0
        L_0x01d3:
            boolean r4 = r0.hasRemaining()
            if (r4 == 0) goto L_0x020e
            int r3 = r3 + r15
            byte[] r4 = m801e(r0)
            java.io.ByteArrayInputStream r5 = new java.io.ByteArrayInputStream     // Catch:{ CertificateException -> 0x01f4 }
            r5.<init>(r4)     // Catch:{ CertificateException -> 0x01f4 }
            r6 = r24
            java.security.cert.Certificate r5 = r6.generateCertificate(r5)     // Catch:{ CertificateException -> 0x01f4 }
            java.security.cert.X509Certificate r5 = (java.security.cert.X509Certificate) r5     // Catch:{ CertificateException -> 0x01f4 }
            com.google.android.play.core.internal.f r7 = new com.google.android.play.core.internal.f
            r7.<init>(r5, r4)
            r1.add(r7)
            goto L_0x01d3
        L_0x01f4:
            r0 = move-exception
            java.lang.SecurityException r1 = new java.lang.SecurityException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r4 = 41
            r2.<init>(r4)
            java.lang.String r4 = "Failed to decode certificate #"
            r2.append(r4)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2, r0)
            throw r1
        L_0x020e:
            boolean r0 = r1.isEmpty()
            if (r0 != 0) goto L_0x023e
            r0 = 0
            java.lang.Object r0 = r1.get(r0)
            java.security.cert.X509Certificate r0 = (java.security.cert.X509Certificate) r0
            java.security.PublicKey r0 = r0.getPublicKey()
            byte[] r0 = r0.getEncoded()
            boolean r0 = java.util.Arrays.equals(r2, r0)
            if (r0 == 0) goto L_0x0236
            int r0 = r1.size()
            java.security.cert.X509Certificate[] r0 = new java.security.cert.X509Certificate[r0]
            java.lang.Object[] r0 = r1.toArray(r0)
            java.security.cert.X509Certificate[] r0 = (java.security.cert.X509Certificate[]) r0
            return r0
        L_0x0236:
            java.lang.SecurityException r0 = new java.lang.SecurityException
            java.lang.String r1 = "Public key mismatch between certificate and signature record"
            r0.<init>(r1)
            throw r0
        L_0x023e:
            java.lang.SecurityException r0 = new java.lang.SecurityException
            java.lang.String r1 = "No certificates listed"
            r0.<init>(r1)
            throw r0
        L_0x0246:
            java.lang.SecurityException r0 = new java.lang.SecurityException
            java.lang.String r1 = "Signature algorithms don't match between digests and signatures records"
            r0.<init>(r1)
            throw r0
        L_0x024e:
            java.lang.SecurityException r0 = new java.lang.SecurityException
            java.lang.String r1 = java.lang.String.valueOf(r5)
            java.lang.String r2 = " signature did not verify"
            java.lang.String r1 = r1.concat(r2)
            r0.<init>(r1)
            throw r0
        L_0x025e:
            r0 = move-exception
            goto L_0x0267
        L_0x0260:
            r0 = move-exception
            goto L_0x0267
        L_0x0262:
            r0 = move-exception
            goto L_0x0267
        L_0x0264:
            r0 = move-exception
            goto L_0x0267
        L_0x0266:
            r0 = move-exception
        L_0x0267:
            java.lang.SecurityException r1 = new java.lang.SecurityException
            java.lang.String r2 = java.lang.String.valueOf(r5)
            int r2 = r2.length()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            int r2 = r2 + 27
            r3.<init>(r2)
            java.lang.String r2 = "Failed to verify "
            r3.append(r2)
            r3.append(r5)
            java.lang.String r2 = " signature"
            r3.append(r2)
            java.lang.String r2 = r3.toString()
            r1.<init>(r2, r0)
            throw r1
        L_0x028d:
            java.lang.SecurityException r0 = new java.lang.SecurityException
            if (r8 != 0) goto L_0x0297
            java.lang.String r1 = "No signatures found"
            r0.<init>(r1)
            throw r0
        L_0x0297:
            java.lang.String r1 = "No supported signatures found"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.play.core.internal.C3056h.m791a(java.nio.ByteBuffer, java.util.Map, java.security.cert.CertificateFactory):java.security.cert.X509Certificate[]");
    }

    /* renamed from: a */
    private static byte[][] m792a(int[] iArr, C3015b[] bVarArr) throws DigestException {
        long j;
        int i;
        int length;
        int[] iArr2 = iArr;
        long j2 = 0;
        long j3 = 0;
        int i2 = 0;
        while (true) {
            j = PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED;
            if (i2 >= 3) {
                break;
            }
            j3 += (bVarArr[i2].mo44085a() + 1048575) / PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED;
            i2++;
        }
        if (j3 < 2097151) {
            int i3 = (int) j3;
            byte[][] bArr = new byte[iArr2.length][];
            int i4 = 0;
            while (true) {
                length = iArr2.length;
                if (i4 >= length) {
                    break;
                }
                byte[] bArr2 = new byte[((m798c(iArr2[i4]) * i3) + 5)];
                bArr2[0] = 90;
                m788a(i3, bArr2);
                bArr[i4] = bArr2;
                i4++;
            }
            byte[] bArr3 = new byte[5];
            bArr3[0] = -91;
            MessageDigest[] messageDigestArr = new MessageDigest[length];
            int i5 = 0;
            while (i5 < iArr2.length) {
                String b = m796b(iArr2[i5]);
                try {
                    messageDigestArr[i5] = MessageDigest.getInstance(b);
                    i5++;
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(b.concat(" digest not supported"), e);
                }
            }
            int i6 = 0;
            int i7 = 0;
            int i8 = 0;
            for (i = 3; i6 < i; i = 3) {
                C3015b bVar = bVarArr[i6];
                long j4 = j2;
                int i9 = length;
                long a = bVar.mo44085a();
                while (a > j2) {
                    int min = (int) Math.min(a, j);
                    m788a(min, bArr3);
                    int i10 = i9;
                    for (int i11 = 0; i11 < i10; i11++) {
                        messageDigestArr[i11].update(bArr3);
                    }
                    long j5 = j4;
                    try {
                        bVar.mo44086a(messageDigestArr, j5, min);
                        int i12 = i10;
                        int i13 = 0;
                        while (i13 < iArr2.length) {
                            int i14 = iArr2[i13];
                            C3015b bVar2 = bVar;
                            byte[] bArr4 = bArr[i13];
                            int c = m798c(i14);
                            byte[] bArr5 = bArr3;
                            MessageDigest messageDigest = messageDigestArr[i13];
                            MessageDigest[] messageDigestArr2 = messageDigestArr;
                            int digest = messageDigest.digest(bArr4, (i7 * c) + 5, c);
                            if (digest == c) {
                                i13++;
                                bVar = bVar2;
                                bArr3 = bArr5;
                                messageDigestArr = messageDigestArr2;
                            } else {
                                String algorithm = messageDigest.getAlgorithm();
                                StringBuilder sb = new StringBuilder(String.valueOf(algorithm).length() + 46);
                                sb.append("Unexpected output size of ");
                                sb.append(algorithm);
                                sb.append(" digest: ");
                                sb.append(digest);
                                throw new RuntimeException(sb.toString());
                            }
                        }
                        C3015b bVar3 = bVar;
                        MessageDigest[] messageDigestArr3 = messageDigestArr;
                        long j6 = (long) min;
                        long j7 = j5 + j6;
                        a -= j6;
                        i7++;
                        i9 = i12;
                        j2 = 0;
                        j = PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED;
                        long j8 = j7;
                        bVar = bVar3;
                        j4 = j8;
                        bArr3 = bArr3;
                    } catch (IOException e2) {
                        StringBuilder sb2 = new StringBuilder(59);
                        sb2.append("Failed to digest chunk #");
                        sb2.append(i7);
                        sb2.append(" of section #");
                        sb2.append(i8);
                        throw new DigestException(sb2.toString(), e2);
                    }
                }
                i8++;
                i6++;
                length = i9;
            }
            byte[][] bArr6 = new byte[iArr2.length][];
            int i15 = 0;
            while (i15 < iArr2.length) {
                int i16 = iArr2[i15];
                byte[] bArr7 = bArr[i15];
                String b2 = m796b(i16);
                try {
                    bArr6[i15] = MessageDigest.getInstance(b2).digest(bArr7);
                    i15++;
                } catch (NoSuchAlgorithmException e3) {
                    throw new RuntimeException(b2.concat(" digest not supported"), e3);
                }
            }
            return bArr6;
        }
        StringBuilder sb3 = new StringBuilder(37);
        sb3.append("Too many chunks: ");
        sb3.append(j3);
        throw new DigestException(sb3.toString());
    }

    /* renamed from: a */
    public static X509Certificate[][] m793a(String str) throws C3053e, SecurityException, IOException {
        ByteBuffer byteBuffer;
        int limit;
        int position;
        RandomAccessFile randomAccessFile = new RandomAccessFile(str, "r");
        try {
            Pair<ByteBuffer, Long> a = m784a(randomAccessFile);
            if (a != null) {
                ByteBuffer byteBuffer2 = (ByteBuffer) a.first;
                long longValue = ((Long) a.second).longValue();
                long j = -20 + longValue;
                if (j >= 0) {
                    randomAccessFile.seek(j);
                    if (randomAccessFile.readInt() == 1347094023) {
                        throw new C3053e("ZIP64 APK not supported");
                    }
                }
                long a2 = m782a(byteBuffer2);
                if (a2 >= longValue) {
                    StringBuilder sb = new StringBuilder(122);
                    sb.append("ZIP Central Directory offset out of range: ");
                    sb.append(a2);
                    sb.append(". ZIP End of Central Directory offset: ");
                    sb.append(longValue);
                    throw new C3053e(sb.toString());
                } else if (m795b(byteBuffer2) + a2 != longValue) {
                    throw new C3053e("ZIP Central Directory is not immediately followed by End of Central Directory");
                } else if (a2 >= 32) {
                    ByteBuffer allocate = ByteBuffer.allocate(24);
                    allocate.order(ByteOrder.LITTLE_ENDIAN);
                    randomAccessFile.seek(a2 - ((long) allocate.capacity()));
                    randomAccessFile.readFully(allocate.array(), allocate.arrayOffset(), allocate.capacity());
                    if (allocate.getLong(8) == 2334950737559900225L && allocate.getLong(16) == 3617552046287187010L) {
                        int i = 0;
                        long j2 = allocate.getLong(0);
                        if (j2 < ((long) allocate.capacity()) || j2 > 2147483639) {
                            StringBuilder sb2 = new StringBuilder(57);
                            sb2.append("APK Signing Block size out of range: ");
                            sb2.append(j2);
                            throw new C3053e(sb2.toString());
                        }
                        int i2 = (int) (8 + j2);
                        long j3 = a2 - ((long) i2);
                        if (j3 >= 0) {
                            ByteBuffer allocate2 = ByteBuffer.allocate(i2);
                            allocate2.order(ByteOrder.LITTLE_ENDIAN);
                            randomAccessFile.seek(j3);
                            randomAccessFile.readFully(allocate2.array(), allocate2.arrayOffset(), allocate2.capacity());
                            long j4 = allocate2.getLong(0);
                            if (j4 == j2) {
                                Pair create = Pair.create(allocate2, Long.valueOf(j3));
                                byteBuffer = (ByteBuffer) create.first;
                                long longValue2 = ((Long) create.second).longValue();
                                if (byteBuffer.order() == ByteOrder.LITTLE_ENDIAN) {
                                    int capacity = byteBuffer.capacity() - 24;
                                    if (capacity >= 8) {
                                        int capacity2 = byteBuffer.capacity();
                                        if (capacity <= byteBuffer.capacity()) {
                                            limit = byteBuffer.limit();
                                            position = byteBuffer.position();
                                            byteBuffer.position(0);
                                            byteBuffer.limit(capacity);
                                            byteBuffer.position(8);
                                            ByteBuffer slice = byteBuffer.slice();
                                            slice.order(byteBuffer.order());
                                            byteBuffer.position(0);
                                            byteBuffer.limit(limit);
                                            byteBuffer.position(position);
                                            while (slice.hasRemaining()) {
                                                i++;
                                                if (slice.remaining() >= 8) {
                                                    long j5 = slice.getLong();
                                                    if (j5 < 4 || j5 > 2147483647L) {
                                                        StringBuilder sb3 = new StringBuilder(76);
                                                        sb3.append("APK Signing Block entry #");
                                                        sb3.append(i);
                                                        sb3.append(" size out of range: ");
                                                        sb3.append(j5);
                                                        throw new C3053e(sb3.toString());
                                                    }
                                                    int i3 = (int) j5;
                                                    int position2 = slice.position() + i3;
                                                    if (i3 > slice.remaining()) {
                                                        int remaining = slice.remaining();
                                                        StringBuilder sb4 = new StringBuilder(91);
                                                        sb4.append("APK Signing Block entry #");
                                                        sb4.append(i);
                                                        sb4.append(" size out of range: ");
                                                        sb4.append(i3);
                                                        sb4.append(", available: ");
                                                        sb4.append(remaining);
                                                        throw new C3053e(sb4.toString());
                                                    } else if (slice.getInt() == 1896449818) {
                                                        X509Certificate[][] a3 = m794a(randomAccessFile.getChannel(), new C3052d(m797b(slice, i3 - 4), longValue2, a2, longValue, byteBuffer2));
                                                        randomAccessFile.close();
                                                        try {
                                                            randomAccessFile.close();
                                                        } catch (IOException e) {
                                                        }
                                                        return a3;
                                                    } else {
                                                        slice.position(position2);
                                                    }
                                                } else {
                                                    StringBuilder sb5 = new StringBuilder(70);
                                                    sb5.append("Insufficient data to read size of APK Signing Block entry #");
                                                    sb5.append(i);
                                                    throw new C3053e(sb5.toString());
                                                }
                                            }
                                            throw new C3053e("No APK Signature Scheme v2 block in APK Signing Block");
                                        }
                                        StringBuilder sb6 = new StringBuilder(41);
                                        sb6.append("end > capacity: ");
                                        sb6.append(capacity);
                                        sb6.append(" > ");
                                        sb6.append(capacity2);
                                        throw new IllegalArgumentException(sb6.toString());
                                    }
                                    StringBuilder sb7 = new StringBuilder(38);
                                    sb7.append("end < start: ");
                                    sb7.append(capacity);
                                    sb7.append(" < ");
                                    sb7.append(8);
                                    throw new IllegalArgumentException(sb7.toString());
                                }
                                throw new IllegalArgumentException("ByteBuffer byte order must be little endian");
                            }
                            StringBuilder sb8 = new StringBuilder(103);
                            sb8.append("APK Signing Block sizes in header and footer do not match: ");
                            sb8.append(j4);
                            sb8.append(" vs ");
                            sb8.append(j2);
                            throw new C3053e(sb8.toString());
                        }
                        StringBuilder sb9 = new StringBuilder(59);
                        sb9.append("APK Signing Block offset out of range: ");
                        sb9.append(j3);
                        throw new C3053e(sb9.toString());
                    }
                    throw new C3053e("No APK Signing Block before ZIP Central Directory");
                } else {
                    StringBuilder sb10 = new StringBuilder(87);
                    sb10.append("APK too small for APK Signing Block. ZIP Central Directory offset: ");
                    sb10.append(a2);
                    throw new C3053e(sb10.toString());
                }
            } else {
                long length = randomAccessFile.length();
                StringBuilder sb11 = new StringBuilder(102);
                sb11.append("Not an APK file: ZIP End of Central Directory record not found in file with ");
                sb11.append(length);
                sb11.append(" bytes");
                throw new C3053e(sb11.toString());
            }
        } catch (Throwable th) {
            Throwable th2 = th;
            try {
                randomAccessFile.close();
            } catch (IOException e2) {
            }
            throw th2;
        }
    }

    /* renamed from: a */
    private static X509Certificate[][] m794a(FileChannel fileChannel, C3052d dVar) throws SecurityException {
        HashMap hashMap = new HashMap();
        ArrayList arrayList = new ArrayList();
        try {
            CertificateFactory instance = CertificateFactory.getInstance("X.509");
            try {
                ByteBuffer d = m800d(dVar.f1328a);
                int i = 0;
                while (d.hasRemaining()) {
                    i++;
                    try {
                        arrayList.add(m791a(m800d(d), (Map<Integer, byte[]>) hashMap, instance));
                    } catch (IOException | SecurityException | BufferUnderflowException e) {
                        StringBuilder sb = new StringBuilder(48);
                        sb.append("Failed to parse/verify signer #");
                        sb.append(i);
                        sb.append(" block");
                        throw new SecurityException(sb.toString(), e);
                    }
                }
                if (i <= 0) {
                    throw new SecurityException("No signers found");
                } else if (!hashMap.isEmpty()) {
                    m790a(hashMap, fileChannel, dVar.f1329b, dVar.f1330c, dVar.f1331d, dVar.f1332e);
                    return (X509Certificate[][]) arrayList.toArray(new X509Certificate[arrayList.size()][]);
                } else {
                    throw new SecurityException("No content digests found");
                }
            } catch (IOException e2) {
                throw new SecurityException("Failed to read list of signers", e2);
            }
        } catch (CertificateException e3) {
            throw new RuntimeException("Failed to obtain X.509 CertificateFactory", e3);
        }
    }

    /* renamed from: b */
    public static long m795b(ByteBuffer byteBuffer) {
        m799c(byteBuffer);
        return m783a(byteBuffer, byteBuffer.position() + 12);
    }

    /* renamed from: b */
    private static String m796b(int i) {
        if (i == 1) {
            return "SHA-256";
        }
        if (i == 2) {
            return "SHA-512";
        }
        StringBuilder sb = new StringBuilder(44);
        sb.append("Unknown content digest algorthm: ");
        sb.append(i);
        throw new IllegalArgumentException(sb.toString());
    }

    /* renamed from: b */
    private static ByteBuffer m797b(ByteBuffer byteBuffer, int i) throws BufferUnderflowException {
        if (i >= 0) {
            int limit = byteBuffer.limit();
            int position = byteBuffer.position();
            int i2 = i + position;
            if (i2 < position || i2 > limit) {
                throw new BufferUnderflowException();
            }
            byteBuffer.limit(i2);
            try {
                ByteBuffer slice = byteBuffer.slice();
                slice.order(byteBuffer.order());
                byteBuffer.position(i2);
                return slice;
            } finally {
                byteBuffer.limit(limit);
            }
        } else {
            StringBuilder sb = new StringBuilder(17);
            sb.append("size: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    /* renamed from: c */
    private static int m798c(int i) {
        if (i == 1) {
            return 32;
        }
        if (i == 2) {
            return 64;
        }
        StringBuilder sb = new StringBuilder(44);
        sb.append("Unknown content digest algorthm: ");
        sb.append(i);
        throw new IllegalArgumentException(sb.toString());
    }

    /* renamed from: c */
    private static void m799c(ByteBuffer byteBuffer) {
        if (byteBuffer.order() != ByteOrder.LITTLE_ENDIAN) {
            throw new IllegalArgumentException("ByteBuffer byte order must be little endian");
        }
    }

    /* renamed from: d */
    private static ByteBuffer m800d(ByteBuffer byteBuffer) throws IOException {
        if (byteBuffer.remaining() >= 4) {
            int i = byteBuffer.getInt();
            if (i < 0) {
                throw new IllegalArgumentException("Negative length");
            } else if (i <= byteBuffer.remaining()) {
                return m797b(byteBuffer, i);
            } else {
                int remaining = byteBuffer.remaining();
                StringBuilder sb = new StringBuilder(101);
                sb.append("Length-prefixed field longer than remaining buffer. Field length: ");
                sb.append(i);
                sb.append(", remaining: ");
                sb.append(remaining);
                throw new IOException(sb.toString());
            }
        } else {
            int remaining2 = byteBuffer.remaining();
            StringBuilder sb2 = new StringBuilder(93);
            sb2.append("Remaining buffer too short to contain length of length-prefixed field. Remaining: ");
            sb2.append(remaining2);
            throw new IOException(sb2.toString());
        }
    }

    /* renamed from: e */
    private static byte[] m801e(ByteBuffer byteBuffer) throws IOException {
        int i = byteBuffer.getInt();
        if (i < 0) {
            throw new IOException("Negative length");
        } else if (i <= byteBuffer.remaining()) {
            byte[] bArr = new byte[i];
            byteBuffer.get(bArr);
            return bArr;
        } else {
            int remaining = byteBuffer.remaining();
            StringBuilder sb = new StringBuilder(90);
            sb.append("Underflow while reading length-prefixed value. Length: ");
            sb.append(i);
            sb.append(", available: ");
            sb.append(remaining);
            throw new IOException(sb.toString());
        }
    }
}