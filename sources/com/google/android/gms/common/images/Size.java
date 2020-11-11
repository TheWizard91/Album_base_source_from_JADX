package com.google.android.gms.common.images;

/* compiled from: com.google.android.gms:play-services-base@@17.1.0 */
public final class Size {
    private final int zanj;
    private final int zank;

    public Size(int i, int i2) {
        this.zanj = i;
        this.zank = i2;
    }

    public final int getWidth() {
        return this.zanj;
    }

    public final int getHeight() {
        return this.zank;
    }

    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Size)) {
            return false;
        }
        Size size = (Size) obj;
        if (this.zanj == size.zanj && this.zank == size.zank) {
            return true;
        }
        return false;
    }

    public final String toString() {
        int i = this.zanj;
        return new StringBuilder(23).append(i).append("x").append(this.zank).toString();
    }

    private static NumberFormatException zah(String str) {
        throw new NumberFormatException(new StringBuilder(String.valueOf(str).length() + 16).append("Invalid Size: \"").append(str).append("\"").toString());
    }

    public static Size parseSize(String str) throws NumberFormatException {
        if (str != null) {
            int indexOf = str.indexOf(42);
            if (indexOf < 0) {
                indexOf = str.indexOf(120);
            }
            if (indexOf >= 0) {
                try {
                    return new Size(Integer.parseInt(str.substring(0, indexOf)), Integer.parseInt(str.substring(indexOf + 1)));
                } catch (NumberFormatException e) {
                    throw zah(str);
                }
            } else {
                throw zah(str);
            }
        } else {
            throw new IllegalArgumentException("string must not be null");
        }
    }

    public final int hashCode() {
        int i = this.zank;
        int i2 = this.zanj;
        return i ^ ((i2 >>> 16) | (i2 << 16));
    }
}
