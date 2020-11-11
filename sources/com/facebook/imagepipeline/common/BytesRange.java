package com.facebook.imagepipeline.common;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.util.HashCodeUtil;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

public class BytesRange {
    public static final int TO_END_OF_CONTENT = Integer.MAX_VALUE;
    @Nullable
    private static Pattern sHeaderParsingRegEx;
    public final int from;

    /* renamed from: to */
    public final int f81to;

    public BytesRange(int from2, int to) {
        this.from = from2;
        this.f81to = to;
    }

    public String toHttpRangeHeaderValue() {
        return String.format((Locale) null, "bytes=%s-%s", new Object[]{valueOrEmpty(this.from), valueOrEmpty(this.f81to)});
    }

    public boolean contains(@Nullable BytesRange compare) {
        if (compare != null && this.from <= compare.from && this.f81to >= compare.f81to) {
            return true;
        }
        return false;
    }

    public String toString() {
        return String.format((Locale) null, "%s-%s", new Object[]{valueOrEmpty(this.from), valueOrEmpty(this.f81to)});
    }

    private static String valueOrEmpty(int n) {
        if (n == Integer.MAX_VALUE) {
            return "";
        }
        return Integer.toString(n);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof BytesRange)) {
            return false;
        }
        BytesRange that = (BytesRange) other;
        if (this.from == that.from && this.f81to == that.f81to) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return HashCodeUtil.hashCode(this.from, this.f81to);
    }

    public static BytesRange from(int from2) {
        Preconditions.checkArgument(from2 >= 0);
        return new BytesRange(from2, Integer.MAX_VALUE);
    }

    public static BytesRange toMax(int to) {
        Preconditions.checkArgument(to > 0);
        return new BytesRange(0, to);
    }

    @Nullable
    public static BytesRange fromContentRangeHeader(@Nullable String header) throws IllegalArgumentException {
        if (header == null) {
            return null;
        }
        if (sHeaderParsingRegEx == null) {
            sHeaderParsingRegEx = Pattern.compile("[-/ ]");
        }
        try {
            String[] headerParts = sHeaderParsingRegEx.split(header);
            Preconditions.checkArgument(headerParts.length == 4);
            Preconditions.checkArgument(headerParts[0].equals("bytes"));
            int from2 = Integer.parseInt(headerParts[1]);
            int to = Integer.parseInt(headerParts[2]);
            int length = Integer.parseInt(headerParts[3]);
            Preconditions.checkArgument(to > from2);
            Preconditions.checkArgument(length > to);
            if (to < length - 1) {
                return new BytesRange(from2, to);
            }
            return new BytesRange(from2, Integer.MAX_VALUE);
        } catch (IllegalArgumentException x) {
            throw new IllegalArgumentException(String.format((Locale) null, "Invalid Content-Range header value: \"%s\"", new Object[]{header}), x);
        }
    }
}
