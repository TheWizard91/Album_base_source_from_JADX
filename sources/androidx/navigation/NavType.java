package androidx.navigation;

import android.os.Bundle;
import android.os.Parcelable;
import java.io.Serializable;

public abstract class NavType<T> {
    public static final NavType<boolean[]> BoolArrayType = new NavType<boolean[]>(true) {
        public void put(Bundle bundle, String key, boolean[] value) {
            bundle.putBooleanArray(key, value);
        }

        public boolean[] get(Bundle bundle, String key) {
            return (boolean[]) bundle.get(key);
        }

        public boolean[] parseValue(String value) {
            throw new UnsupportedOperationException("Arrays don't support default values.");
        }

        public String getName() {
            return "boolean[]";
        }
    };
    public static final NavType<Boolean> BoolType = new NavType<Boolean>(false) {
        public void put(Bundle bundle, String key, Boolean value) {
            bundle.putBoolean(key, value.booleanValue());
        }

        public Boolean get(Bundle bundle, String key) {
            return (Boolean) bundle.get(key);
        }

        public Boolean parseValue(String value) {
            if ("true".equals(value)) {
                return true;
            }
            if ("false".equals(value)) {
                return false;
            }
            throw new IllegalArgumentException("A boolean NavType only accepts \"true\" or \"false\" values.");
        }

        public String getName() {
            return "boolean";
        }
    };
    public static final NavType<float[]> FloatArrayType = new NavType<float[]>(true) {
        public void put(Bundle bundle, String key, float[] value) {
            bundle.putFloatArray(key, value);
        }

        public float[] get(Bundle bundle, String key) {
            return (float[]) bundle.get(key);
        }

        public float[] parseValue(String value) {
            throw new UnsupportedOperationException("Arrays don't support default values.");
        }

        public String getName() {
            return "float[]";
        }
    };
    public static final NavType<Float> FloatType = new NavType<Float>(false) {
        public void put(Bundle bundle, String key, Float value) {
            bundle.putFloat(key, value.floatValue());
        }

        public Float get(Bundle bundle, String key) {
            return (Float) bundle.get(key);
        }

        public Float parseValue(String value) {
            return Float.valueOf(Float.parseFloat(value));
        }

        public String getName() {
            return "float";
        }
    };
    public static final NavType<int[]> IntArrayType = new NavType<int[]>(true) {
        public void put(Bundle bundle, String key, int[] value) {
            bundle.putIntArray(key, value);
        }

        public int[] get(Bundle bundle, String key) {
            return (int[]) bundle.get(key);
        }

        public int[] parseValue(String value) {
            throw new UnsupportedOperationException("Arrays don't support default values.");
        }

        public String getName() {
            return "integer[]";
        }
    };
    public static final NavType<Integer> IntType = new NavType<Integer>(false) {
        public void put(Bundle bundle, String key, Integer value) {
            bundle.putInt(key, value.intValue());
        }

        public Integer get(Bundle bundle, String key) {
            return (Integer) bundle.get(key);
        }

        public Integer parseValue(String value) {
            if (value.startsWith("0x")) {
                return Integer.valueOf(Integer.parseInt(value.substring(2), 16));
            }
            return Integer.valueOf(Integer.parseInt(value));
        }

        public String getName() {
            return "integer";
        }
    };
    public static final NavType<long[]> LongArrayType = new NavType<long[]>(true) {
        public void put(Bundle bundle, String key, long[] value) {
            bundle.putLongArray(key, value);
        }

        public long[] get(Bundle bundle, String key) {
            return (long[]) bundle.get(key);
        }

        public long[] parseValue(String value) {
            throw new UnsupportedOperationException("Arrays don't support default values.");
        }

        public String getName() {
            return "long[]";
        }
    };
    public static final NavType<Long> LongType = new NavType<Long>(false) {
        public void put(Bundle bundle, String key, Long value) {
            bundle.putLong(key, value.longValue());
        }

        public Long get(Bundle bundle, String key) {
            return (Long) bundle.get(key);
        }

        public Long parseValue(String value) {
            if (value.endsWith("L")) {
                value = value.substring(0, value.length() - 1);
            }
            if (value.startsWith("0x")) {
                return Long.valueOf(Long.parseLong(value.substring(2), 16));
            }
            return Long.valueOf(Long.parseLong(value));
        }

        public String getName() {
            return "long";
        }
    };
    public static final NavType<Integer> ReferenceType = new NavType<Integer>(false) {
        public void put(Bundle bundle, String key, Integer value) {
            bundle.putInt(key, value.intValue());
        }

        public Integer get(Bundle bundle, String key) {
            return (Integer) bundle.get(key);
        }

        public Integer parseValue(String value) {
            throw new UnsupportedOperationException("References don't support parsing string values.");
        }

        public String getName() {
            return "reference";
        }
    };
    public static final NavType<String[]> StringArrayType = new NavType<String[]>(true) {
        public void put(Bundle bundle, String key, String[] value) {
            bundle.putStringArray(key, value);
        }

        public String[] get(Bundle bundle, String key) {
            return (String[]) bundle.get(key);
        }

        public String[] parseValue(String value) {
            throw new UnsupportedOperationException("Arrays don't support default values.");
        }

        public String getName() {
            return "string[]";
        }
    };
    public static final NavType<String> StringType = new NavType<String>(true) {
        public void put(Bundle bundle, String key, String value) {
            bundle.putString(key, value);
        }

        public String get(Bundle bundle, String key) {
            return (String) bundle.get(key);
        }

        public String parseValue(String value) {
            return value;
        }

        public String getName() {
            return "string";
        }
    };
    private final boolean mNullableAllowed;

    public abstract T get(Bundle bundle, String str);

    public abstract String getName();

    public abstract T parseValue(String str);

    public abstract void put(Bundle bundle, String str, T t);

    NavType(boolean nullableAllowed) {
        this.mNullableAllowed = nullableAllowed;
    }

    public boolean isNullableAllowed() {
        return this.mNullableAllowed;
    }

    /* access modifiers changed from: package-private */
    public T parseAndPut(Bundle bundle, String key, String value) {
        T parsedValue = parseValue(value);
        put(bundle, key, parsedValue);
        return parsedValue;
    }

    public String toString() {
        return getName();
    }

    public static NavType<?> fromArgType(String type, String packageName) {
        String className;
        NavType<Integer> navType = IntType;
        if (navType.getName().equals(type)) {
            return navType;
        }
        NavType<int[]> navType2 = IntArrayType;
        if (navType2.getName().equals(type)) {
            return navType2;
        }
        NavType<Long> navType3 = LongType;
        if (navType3.getName().equals(type)) {
            return navType3;
        }
        NavType<long[]> navType4 = LongArrayType;
        if (navType4.getName().equals(type)) {
            return navType4;
        }
        NavType<Boolean> navType5 = BoolType;
        if (navType5.getName().equals(type)) {
            return navType5;
        }
        NavType<boolean[]> navType6 = BoolArrayType;
        if (navType6.getName().equals(type)) {
            return navType6;
        }
        NavType<String> navType7 = StringType;
        if (navType7.getName().equals(type)) {
            return navType7;
        }
        NavType<String[]> navType8 = StringArrayType;
        if (navType8.getName().equals(type)) {
            return navType8;
        }
        NavType<Float> navType9 = FloatType;
        if (navType9.getName().equals(type)) {
            return navType9;
        }
        NavType<float[]> navType10 = FloatArrayType;
        if (navType10.getName().equals(type)) {
            return navType10;
        }
        NavType<Integer> navType11 = ReferenceType;
        if (navType11.getName().equals(type)) {
            return navType11;
        }
        if (type == null || type.isEmpty()) {
            return navType7;
        }
        try {
            if (!type.startsWith(".") || packageName == null) {
                className = type;
            } else {
                className = packageName + type;
            }
            if (type.endsWith("[]")) {
                className = className.substring(0, className.length() - 2);
                Class<?> clazz = Class.forName(className);
                if (Parcelable.class.isAssignableFrom(clazz)) {
                    return new ParcelableArrayType(clazz);
                }
                if (Serializable.class.isAssignableFrom(clazz)) {
                    return new SerializableArrayType(clazz);
                }
            } else {
                Class<?> clazz2 = Class.forName(className);
                if (Parcelable.class.isAssignableFrom(clazz2)) {
                    return new ParcelableType(clazz2);
                }
                if (Enum.class.isAssignableFrom(clazz2)) {
                    return new EnumType(clazz2);
                }
                if (Serializable.class.isAssignableFrom(clazz2)) {
                    return new SerializableType(clazz2);
                }
            }
            throw new IllegalArgumentException(className + " is not Serializable or Parcelable.");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static NavType inferFromValue(String value) {
        try {
            NavType<Integer> navType = IntType;
            navType.parseValue(value);
            return navType;
        } catch (IllegalArgumentException e) {
            try {
                NavType<Long> navType2 = LongType;
                navType2.parseValue(value);
                return navType2;
            } catch (IllegalArgumentException e2) {
                try {
                    NavType<Float> navType3 = FloatType;
                    navType3.parseValue(value);
                    return navType3;
                } catch (IllegalArgumentException e3) {
                    try {
                        NavType<Boolean> navType4 = BoolType;
                        navType4.parseValue(value);
                        return navType4;
                    } catch (IllegalArgumentException e4) {
                        return StringType;
                    }
                }
            }
        }
    }

    static NavType inferFromValueType(Object value) {
        if (value instanceof Integer) {
            return IntType;
        }
        if (value instanceof int[]) {
            return IntArrayType;
        }
        if (value instanceof Long) {
            return LongType;
        }
        if (value instanceof long[]) {
            return LongArrayType;
        }
        if (value instanceof Float) {
            return FloatType;
        }
        if (value instanceof float[]) {
            return FloatArrayType;
        }
        if (value instanceof Boolean) {
            return BoolType;
        }
        if (value instanceof boolean[]) {
            return BoolArrayType;
        }
        if ((value instanceof String) || value == null) {
            return StringType;
        }
        if (value instanceof String[]) {
            return StringArrayType;
        }
        if (value.getClass().isArray() && Parcelable.class.isAssignableFrom(value.getClass().getComponentType())) {
            return new ParcelableArrayType(value.getClass().getComponentType());
        }
        if (value.getClass().isArray() && Serializable.class.isAssignableFrom(value.getClass().getComponentType())) {
            return new SerializableArrayType(value.getClass().getComponentType());
        }
        if (value instanceof Parcelable) {
            return new ParcelableType(value.getClass());
        }
        if (value instanceof Enum) {
            return new EnumType(value.getClass());
        }
        if (value instanceof Serializable) {
            return new SerializableType(value.getClass());
        }
        throw new IllegalArgumentException("Object of type " + value.getClass().getName() + " is not supported for navigation arguments.");
    }

    public static final class ParcelableType<D> extends NavType<D> {
        private final Class<D> mType;

        public ParcelableType(Class<D> type) {
            super(true);
            if (Parcelable.class.isAssignableFrom(type) || Serializable.class.isAssignableFrom(type)) {
                this.mType = type;
                return;
            }
            throw new IllegalArgumentException(type + " does not implement Parcelable or Serializable.");
        }

        public void put(Bundle bundle, String key, D value) {
            this.mType.cast(value);
            if (value == null || (value instanceof Parcelable)) {
                bundle.putParcelable(key, (Parcelable) value);
            } else if (value instanceof Serializable) {
                bundle.putSerializable(key, (Serializable) value);
            }
        }

        public D get(Bundle bundle, String key) {
            return bundle.get(key);
        }

        public D parseValue(String value) {
            throw new UnsupportedOperationException("Parcelables don't support default values.");
        }

        public String getName() {
            return this.mType.getName();
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            return this.mType.equals(((ParcelableType) o).mType);
        }

        public int hashCode() {
            return this.mType.hashCode();
        }
    }

    public static final class ParcelableArrayType<D extends Parcelable> extends NavType<D[]> {
        private final Class<D[]> mArrayType;

        public ParcelableArrayType(Class<D> type) {
            super(true);
            if (Parcelable.class.isAssignableFrom(type)) {
                try {
                    this.mArrayType = Class.forName("[L" + type.getName() + ";");
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new IllegalArgumentException(type + " does not implement Parcelable.");
            }
        }

        public void put(Bundle bundle, String key, D[] value) {
            this.mArrayType.cast(value);
            bundle.putParcelableArray(key, value);
        }

        public D[] get(Bundle bundle, String key) {
            return (Parcelable[]) bundle.get(key);
        }

        public D[] parseValue(String value) {
            throw new UnsupportedOperationException("Arrays don't support default values.");
        }

        public String getName() {
            return this.mArrayType.getName();
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            return this.mArrayType.equals(((ParcelableArrayType) o).mArrayType);
        }

        public int hashCode() {
            return this.mArrayType.hashCode();
        }
    }

    public static class SerializableType<D extends Serializable> extends NavType<D> {
        private final Class<D> mType;

        public SerializableType(Class<D> type) {
            super(true);
            if (!Serializable.class.isAssignableFrom(type)) {
                throw new IllegalArgumentException(type + " does not implement Serializable.");
            } else if (!type.isEnum()) {
                this.mType = type;
            } else {
                throw new IllegalArgumentException(type + " is an Enum. You should use EnumType instead.");
            }
        }

        SerializableType(boolean nullableAllowed, Class<D> type) {
            super(nullableAllowed);
            if (Serializable.class.isAssignableFrom(type)) {
                this.mType = type;
                return;
            }
            throw new IllegalArgumentException(type + " does not implement Serializable.");
        }

        public void put(Bundle bundle, String key, D value) {
            this.mType.cast(value);
            bundle.putSerializable(key, value);
        }

        public D get(Bundle bundle, String key) {
            return (Serializable) bundle.get(key);
        }

        public D parseValue(String value) {
            throw new UnsupportedOperationException("Serializables don't support default values.");
        }

        public String getName() {
            return this.mType.getName();
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof SerializableType)) {
                return false;
            }
            return this.mType.equals(((SerializableType) o).mType);
        }

        public int hashCode() {
            return this.mType.hashCode();
        }
    }

    public static final class EnumType<D extends Enum> extends SerializableType<D> {
        private final Class<D> mType;

        public EnumType(Class<D> type) {
            super(false, type);
            if (type.isEnum()) {
                this.mType = type;
                return;
            }
            throw new IllegalArgumentException(type + " is not an Enum type.");
        }

        public D parseValue(String value) {
            for (D d : (Enum[]) this.mType.getEnumConstants()) {
                if (((Enum) d).name().equals(value)) {
                    return (Enum) d;
                }
            }
            throw new IllegalArgumentException("Enum value " + value + " not found for type " + this.mType.getName() + ".");
        }

        public String getName() {
            return this.mType.getName();
        }
    }

    public static final class SerializableArrayType<D extends Serializable> extends NavType<D[]> {
        private final Class<D[]> mArrayType;

        public SerializableArrayType(Class<D> type) {
            super(true);
            if (Serializable.class.isAssignableFrom(type)) {
                try {
                    this.mArrayType = Class.forName("[L" + type.getName() + ";");
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new IllegalArgumentException(type + " does not implement Serializable.");
            }
        }

        /* JADX WARNING: type inference failed for: r4v0, types: [D[], java.lang.Object, java.io.Serializable] */
        /* JADX WARNING: Unknown variable types count: 1 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void put(android.os.Bundle r2, java.lang.String r3, D[] r4) {
            /*
                r1 = this;
                java.lang.Class<D[]> r0 = r1.mArrayType
                r0.cast(r4)
                r2.putSerializable(r3, r4)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.NavType.SerializableArrayType.put(android.os.Bundle, java.lang.String, java.io.Serializable[]):void");
        }

        public D[] get(Bundle bundle, String key) {
            return (Serializable[]) bundle.get(key);
        }

        public D[] parseValue(String value) {
            throw new UnsupportedOperationException("Arrays don't support default values.");
        }

        public String getName() {
            return this.mArrayType.getName();
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            return this.mArrayType.equals(((SerializableArrayType) o).mArrayType);
        }

        public int hashCode() {
            return this.mArrayType.hashCode();
        }
    }
}
