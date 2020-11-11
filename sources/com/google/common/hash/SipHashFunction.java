package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.nio.ByteBuffer;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable
final class SipHashFunction extends AbstractHashFunction implements Serializable {
    static final HashFunction SIP_HASH_24 = new SipHashFunction(2, 4, 506097522914230528L, 1084818905618843912L);
    private static final long serialVersionUID = 0;

    /* renamed from: c */
    private final int f1656c;

    /* renamed from: d */
    private final int f1657d;

    /* renamed from: k0 */
    private final long f1658k0;

    /* renamed from: k1 */
    private final long f1659k1;

    SipHashFunction(int c, int d, long k0, long k1) {
        boolean z = true;
        Preconditions.checkArgument(c > 0, "The number of SipRound iterations (c=%s) during Compression must be positive.", c);
        Preconditions.checkArgument(d <= 0 ? false : z, "The number of SipRound iterations (d=%s) during Finalization must be positive.", d);
        this.f1656c = c;
        this.f1657d = d;
        this.f1658k0 = k0;
        this.f1659k1 = k1;
    }

    public int bits() {
        return 64;
    }

    public Hasher newHasher() {
        return new SipHasher(this.f1656c, this.f1657d, this.f1658k0, this.f1659k1);
    }

    public String toString() {
        return "Hashing.sipHash" + this.f1656c + "" + this.f1657d + "(" + this.f1658k0 + ", " + this.f1659k1 + ")";
    }

    public boolean equals(@NullableDecl Object object) {
        if (!(object instanceof SipHashFunction)) {
            return false;
        }
        SipHashFunction other = (SipHashFunction) object;
        if (this.f1656c == other.f1656c && this.f1657d == other.f1657d && this.f1658k0 == other.f1658k0 && this.f1659k1 == other.f1659k1) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (int) ((((long) ((getClass().hashCode() ^ this.f1656c) ^ this.f1657d)) ^ this.f1658k0) ^ this.f1659k1);
    }

    private static final class SipHasher extends AbstractStreamingHasher {
        private static final int CHUNK_SIZE = 8;

        /* renamed from: b */
        private long f1660b = 0;

        /* renamed from: c */
        private final int f1661c;

        /* renamed from: d */
        private final int f1662d;
        private long finalM = 0;

        /* renamed from: v0 */
        private long f1663v0 = 8317987319222330741L;

        /* renamed from: v1 */
        private long f1664v1 = 7237128888997146477L;

        /* renamed from: v2 */
        private long f1665v2 = 7816392313619706465L;

        /* renamed from: v3 */
        private long f1666v3 = 8387220255154660723L;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        SipHasher(int c, int d, long k0, long k1) {
            super(8);
            this.f1661c = c;
            this.f1662d = d;
            this.f1663v0 = 8317987319222330741L ^ k0;
            this.f1664v1 = 7237128888997146477L ^ k1;
            this.f1665v2 = 7816392313619706465L ^ k0;
            this.f1666v3 = 8387220255154660723L ^ k1;
        }

        /* access modifiers changed from: protected */
        public void process(ByteBuffer buffer) {
            this.f1660b += 8;
            processM(buffer.getLong());
        }

        /* access modifiers changed from: protected */
        public void processRemaining(ByteBuffer buffer) {
            this.f1660b += (long) buffer.remaining();
            int i = 0;
            while (buffer.hasRemaining()) {
                this.finalM ^= (((long) buffer.get()) & 255) << i;
                i += 8;
            }
        }

        public HashCode makeHash() {
            long j = this.finalM ^ (this.f1660b << 56);
            this.finalM = j;
            processM(j);
            this.f1665v2 ^= 255;
            sipRound(this.f1662d);
            return HashCode.fromLong(((this.f1663v0 ^ this.f1664v1) ^ this.f1665v2) ^ this.f1666v3);
        }

        private void processM(long m) {
            this.f1666v3 ^= m;
            sipRound(this.f1661c);
            this.f1663v0 ^= m;
        }

        private void sipRound(int iterations) {
            for (int i = 0; i < iterations; i++) {
                long j = this.f1663v0;
                long j2 = this.f1664v1;
                this.f1663v0 = j + j2;
                this.f1665v2 += this.f1666v3;
                this.f1664v1 = Long.rotateLeft(j2, 13);
                long rotateLeft = Long.rotateLeft(this.f1666v3, 16);
                this.f1666v3 = rotateLeft;
                long j3 = this.f1664v1;
                long j4 = this.f1663v0;
                this.f1664v1 = j3 ^ j4;
                this.f1666v3 = rotateLeft ^ this.f1665v2;
                long rotateLeft2 = Long.rotateLeft(j4, 32);
                this.f1663v0 = rotateLeft2;
                long j5 = this.f1665v2;
                long j6 = this.f1664v1;
                this.f1665v2 = j5 + j6;
                this.f1663v0 = rotateLeft2 + this.f1666v3;
                this.f1664v1 = Long.rotateLeft(j6, 17);
                long rotateLeft3 = Long.rotateLeft(this.f1666v3, 21);
                this.f1666v3 = rotateLeft3;
                long j7 = this.f1664v1;
                long j8 = this.f1665v2;
                this.f1664v1 = j7 ^ j8;
                this.f1666v3 = rotateLeft3 ^ this.f1663v0;
                this.f1665v2 = Long.rotateLeft(j8, 32);
            }
        }
    }
}
