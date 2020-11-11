package com.fasterxml.jackson.core.sym;

import java.util.Arrays;

public final class CharsToNameCanonicalizer {
    protected static final int DEFAULT_TABLE_SIZE = 64;
    public static final int HASH_MULT = 33;
    static final int MAX_COLL_CHAIN_FOR_REUSE = 63;
    static final int MAX_COLL_CHAIN_LENGTH = 255;
    static final int MAX_ENTRIES_FOR_REUSE = 12000;
    protected static final int MAX_TABLE_SIZE = 65536;
    static final CharsToNameCanonicalizer sBootstrapSymbolTable = new CharsToNameCanonicalizer();
    protected Bucket[] _buckets;
    protected final boolean _canonicalize;
    protected boolean _dirty;
    private final int _hashSeed;
    protected int _indexMask;
    protected final boolean _intern;
    protected int _longestCollisionList;
    protected CharsToNameCanonicalizer _parent;
    protected int _size;
    protected int _sizeThreshold;
    protected String[] _symbols;

    public static CharsToNameCanonicalizer createRoot() {
        long currentTimeMillis = System.currentTimeMillis();
        return createRoot((((int) currentTimeMillis) + ((int) (currentTimeMillis >>> 32))) | 1);
    }

    protected static CharsToNameCanonicalizer createRoot(int i) {
        return sBootstrapSymbolTable.makeOrphan(i);
    }

    private CharsToNameCanonicalizer() {
        this._canonicalize = true;
        this._intern = true;
        this._dirty = true;
        this._hashSeed = 0;
        this._longestCollisionList = 0;
        initTables(64);
    }

    private void initTables(int i) {
        this._symbols = new String[i];
        this._buckets = new Bucket[(i >> 1)];
        this._indexMask = i - 1;
        this._size = 0;
        this._longestCollisionList = 0;
        this._sizeThreshold = _thresholdSize(i);
    }

    private static int _thresholdSize(int i) {
        return i - (i >> 2);
    }

    private CharsToNameCanonicalizer(CharsToNameCanonicalizer charsToNameCanonicalizer, boolean z, boolean z2, String[] strArr, Bucket[] bucketArr, int i, int i2, int i3) {
        this._parent = charsToNameCanonicalizer;
        this._canonicalize = z;
        this._intern = z2;
        this._symbols = strArr;
        this._buckets = bucketArr;
        this._size = i;
        this._hashSeed = i2;
        int length = strArr.length;
        this._sizeThreshold = _thresholdSize(length);
        this._indexMask = length - 1;
        this._longestCollisionList = i3;
        this._dirty = false;
    }

    public CharsToNameCanonicalizer makeChild(boolean z, boolean z2) {
        String[] strArr;
        Bucket[] bucketArr;
        int i;
        int i2;
        int i3;
        synchronized (this) {
            strArr = this._symbols;
            bucketArr = this._buckets;
            i = this._size;
            i2 = this._hashSeed;
            i3 = this._longestCollisionList;
        }
        return new CharsToNameCanonicalizer(this, z, z2, strArr, bucketArr, i, i2, i3);
    }

    private CharsToNameCanonicalizer makeOrphan(int i) {
        return new CharsToNameCanonicalizer((CharsToNameCanonicalizer) null, true, true, this._symbols, this._buckets, this._size, i, this._longestCollisionList);
    }

    private void mergeChild(CharsToNameCanonicalizer charsToNameCanonicalizer) {
        if (charsToNameCanonicalizer.size() > MAX_ENTRIES_FOR_REUSE || charsToNameCanonicalizer._longestCollisionList > 63) {
            synchronized (this) {
                initTables(64);
                this._dirty = false;
            }
        } else if (charsToNameCanonicalizer.size() > size()) {
            synchronized (this) {
                this._symbols = charsToNameCanonicalizer._symbols;
                this._buckets = charsToNameCanonicalizer._buckets;
                this._size = charsToNameCanonicalizer._size;
                this._sizeThreshold = charsToNameCanonicalizer._sizeThreshold;
                this._indexMask = charsToNameCanonicalizer._indexMask;
                this._longestCollisionList = charsToNameCanonicalizer._longestCollisionList;
                this._dirty = false;
            }
        }
    }

    public void release() {
        CharsToNameCanonicalizer charsToNameCanonicalizer;
        if (maybeDirty() && (charsToNameCanonicalizer = this._parent) != null) {
            charsToNameCanonicalizer.mergeChild(this);
            this._dirty = false;
        }
    }

    public int size() {
        return this._size;
    }

    public int bucketCount() {
        return this._symbols.length;
    }

    public boolean maybeDirty() {
        return this._dirty;
    }

    public int hashSeed() {
        return this._hashSeed;
    }

    public int collisionCount() {
        int i = 0;
        for (Bucket bucket : this._buckets) {
            if (bucket != null) {
                i += bucket.length();
            }
        }
        return i;
    }

    public int maxCollisionLength() {
        return this._longestCollisionList;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0021 A[LOOP:0: B:12:0x0021->B:15:0x002e, LOOP_START, PHI: r2 
      PHI: (r2v4 int) = (r2v3 int), (r2v6 int) binds: [B:11:0x0020, B:15:0x002e] A[DONT_GENERATE, DONT_INLINE]] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String findSymbol(char[] r6, int r7, int r8, int r9) {
        /*
            r5 = this;
            r0 = 1
            if (r8 >= r0) goto L_0x0006
            java.lang.String r6 = ""
            return r6
        L_0x0006:
            boolean r1 = r5._canonicalize
            if (r1 != 0) goto L_0x0010
            java.lang.String r9 = new java.lang.String
            r9.<init>(r6, r7, r8)
            return r9
        L_0x0010:
            int r9 = r5._hashToIndex(r9)
            java.lang.String[] r1 = r5._symbols
            r1 = r1[r9]
            if (r1 == 0) goto L_0x0042
            int r2 = r1.length()
            if (r2 != r8) goto L_0x0033
            r2 = 0
        L_0x0021:
            char r3 = r1.charAt(r2)
            int r4 = r7 + r2
            char r4 = r6[r4]
            if (r3 == r4) goto L_0x002c
            goto L_0x0030
        L_0x002c:
            int r2 = r2 + 1
            if (r2 < r8) goto L_0x0021
        L_0x0030:
            if (r2 != r8) goto L_0x0033
            return r1
        L_0x0033:
            com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer$Bucket[] r1 = r5._buckets
            int r2 = r9 >> 1
            r1 = r1[r2]
            if (r1 == 0) goto L_0x0042
            java.lang.String r1 = r1.find(r6, r7, r8)
            if (r1 == 0) goto L_0x0042
            return r1
        L_0x0042:
            boolean r1 = r5._dirty
            if (r1 != 0) goto L_0x004c
            r5.copyArrays()
            r5._dirty = r0
            goto L_0x005d
        L_0x004c:
            int r1 = r5._size
            int r2 = r5._sizeThreshold
            if (r1 < r2) goto L_0x005d
            r5.rehash()
            int r9 = r5.calcHash(r6, r7, r8)
            int r9 = r5._hashToIndex(r9)
        L_0x005d:
            java.lang.String r1 = new java.lang.String
            r1.<init>(r6, r7, r8)
            boolean r6 = r5._intern
            if (r6 == 0) goto L_0x006c
            com.fasterxml.jackson.core.util.InternCache r6 = com.fasterxml.jackson.core.util.InternCache.instance
            java.lang.String r1 = r6.intern(r1)
        L_0x006c:
            int r6 = r5._size
            int r6 = r6 + r0
            r5._size = r6
            java.lang.String[] r6 = r5._symbols
            r7 = r6[r9]
            if (r7 != 0) goto L_0x007a
            r6[r9] = r1
            goto L_0x009c
        L_0x007a:
            int r6 = r9 >> 1
            com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer$Bucket r7 = new com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer$Bucket
            com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer$Bucket[] r8 = r5._buckets
            r8 = r8[r6]
            r7.<init>(r1, r8)
            com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer$Bucket[] r8 = r5._buckets
            r8[r6] = r7
            int r6 = r7.length()
            int r7 = r5._longestCollisionList
            int r6 = java.lang.Math.max(r6, r7)
            r5._longestCollisionList = r6
            r7 = 255(0xff, float:3.57E-43)
            if (r6 <= r7) goto L_0x009c
            r5.reportTooManyCollisions(r7)
        L_0x009c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer.findSymbol(char[], int, int, int):java.lang.String");
    }

    public int _hashToIndex(int i) {
        return (i + (i >>> 15)) & this._indexMask;
    }

    public int calcHash(char[] cArr, int i, int i2) {
        int i3 = this._hashSeed;
        for (int i4 = 0; i4 < i2; i4++) {
            i3 = (i3 * 33) + cArr[i4];
        }
        if (i3 == 0) {
            return 1;
        }
        return i3;
    }

    public int calcHash(String str) {
        int length = str.length();
        int i = this._hashSeed;
        for (int i2 = 0; i2 < length; i2++) {
            i = (i * 33) + str.charAt(i2);
        }
        if (i == 0) {
            return 1;
        }
        return i;
    }

    private void copyArrays() {
        String[] strArr = this._symbols;
        int length = strArr.length;
        String[] strArr2 = new String[length];
        this._symbols = strArr2;
        System.arraycopy(strArr, 0, strArr2, 0, length);
        Bucket[] bucketArr = this._buckets;
        int length2 = bucketArr.length;
        Bucket[] bucketArr2 = new Bucket[length2];
        this._buckets = bucketArr2;
        System.arraycopy(bucketArr, 0, bucketArr2, 0, length2);
    }

    private void rehash() {
        String[] strArr = this._symbols;
        int i = r1 + r1;
        if (i > 65536) {
            this._size = 0;
            Arrays.fill(strArr, (Object) null);
            Arrays.fill(this._buckets, (Object) null);
            this._dirty = true;
            return;
        }
        Bucket[] bucketArr = this._buckets;
        this._symbols = new String[i];
        this._buckets = new Bucket[(i >> 1)];
        this._indexMask = i - 1;
        this._sizeThreshold = _thresholdSize(i);
        int i2 = 0;
        int i3 = 0;
        for (String str : strArr) {
            if (str != null) {
                i2++;
                int _hashToIndex = _hashToIndex(calcHash(str));
                String[] strArr2 = this._symbols;
                if (strArr2[_hashToIndex] == null) {
                    strArr2[_hashToIndex] = str;
                } else {
                    int i4 = _hashToIndex >> 1;
                    Bucket bucket = new Bucket(str, this._buckets[i4]);
                    this._buckets[i4] = bucket;
                    i3 = Math.max(i3, bucket.length());
                }
            }
        }
        int i5 = r1 >> 1;
        for (int i6 = 0; i6 < i5; i6++) {
            for (Bucket bucket2 = bucketArr[i6]; bucket2 != null; bucket2 = bucket2.getNext()) {
                i2++;
                String symbol = bucket2.getSymbol();
                int _hashToIndex2 = _hashToIndex(calcHash(symbol));
                String[] strArr3 = this._symbols;
                if (strArr3[_hashToIndex2] == null) {
                    strArr3[_hashToIndex2] = symbol;
                } else {
                    int i7 = _hashToIndex2 >> 1;
                    Bucket bucket3 = new Bucket(symbol, this._buckets[i7]);
                    this._buckets[i7] = bucket3;
                    i3 = Math.max(i3, bucket3.length());
                }
            }
        }
        this._longestCollisionList = i3;
        if (i2 != this._size) {
            throw new Error("Internal error on SymbolTable.rehash(): had " + this._size + " entries; now have " + i2 + ".");
        }
    }

    /* access modifiers changed from: protected */
    public void reportTooManyCollisions(int i) {
        throw new IllegalStateException("Longest collision chain in symbol table (of size " + this._size + ") now exceeds maximum, " + i + " -- suspect a DoS attack based on hash collisions");
    }

    static final class Bucket {
        private final int _length;
        private final Bucket _next;
        private final String _symbol;

        public Bucket(String str, Bucket bucket) {
            this._symbol = str;
            this._next = bucket;
            this._length = bucket != null ? 1 + bucket._length : 1;
        }

        public String getSymbol() {
            return this._symbol;
        }

        public Bucket getNext() {
            return this._next;
        }

        public int length() {
            return this._length;
        }

        /* JADX WARNING: Removed duplicated region for block: B:4:0x000b A[LOOP:1: B:4:0x000b->B:7:0x0018, LOOP_START, PHI: r2 
          PHI: (r2v2 int) = (r2v1 int), (r2v4 int) binds: [B:3:0x000a, B:7:0x0018] A[DONT_GENERATE, DONT_INLINE]] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.String find(char[] r6, int r7, int r8) {
            /*
                r5 = this;
                java.lang.String r0 = r5._symbol
                com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer$Bucket r1 = r5._next
            L_0x0004:
                int r2 = r0.length()
                if (r2 != r8) goto L_0x001d
                r2 = 0
            L_0x000b:
                char r3 = r0.charAt(r2)
                int r4 = r7 + r2
                char r4 = r6[r4]
                if (r3 == r4) goto L_0x0016
                goto L_0x001a
            L_0x0016:
                int r2 = r2 + 1
                if (r2 < r8) goto L_0x000b
            L_0x001a:
                if (r2 != r8) goto L_0x001d
                return r0
            L_0x001d:
                if (r1 != 0) goto L_0x0022
                r6 = 0
                return r6
            L_0x0022:
                java.lang.String r0 = r1.getSymbol()
                com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer$Bucket r1 = r1.getNext()
                goto L_0x0004
            */
            throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer.Bucket.find(char[], int, int):java.lang.String");
        }
    }
}
