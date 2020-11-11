package com.facebook.common.internal;

import java.util.Arrays;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

public final class Objects {
    private Objects() {
    }

    @CheckReturnValue
    public static boolean equal(@Nullable Object a, @Nullable Object b) {
        return a == b || (a != null && a.equals(b));
    }

    public static int hashCode(@Nullable Object... objects) {
        return Arrays.hashCode(objects);
    }

    public static ToStringHelper toStringHelper(Object self) {
        return new ToStringHelper(simpleName(self.getClass()));
    }

    public static ToStringHelper toStringHelper(Class<?> clazz) {
        return new ToStringHelper(simpleName(clazz));
    }

    public static ToStringHelper toStringHelper(String className) {
        return new ToStringHelper(className);
    }

    private static String simpleName(Class<?> clazz) {
        String name = clazz.getName().replaceAll("\\$[0-9]+", "\\$");
        int start = name.lastIndexOf(36);
        if (start == -1) {
            start = name.lastIndexOf(46);
        }
        return name.substring(start + 1);
    }

    public static <T> T firstNonNull(@Nullable T first, @Nullable T second) {
        return first != null ? first : Preconditions.checkNotNull(second);
    }

    public static final class ToStringHelper {
        private final String className;
        private ValueHolder holderHead;
        private ValueHolder holderTail;
        private boolean omitNullValues;

        private ToStringHelper(String className2) {
            ValueHolder valueHolder = new ValueHolder();
            this.holderHead = valueHolder;
            this.holderTail = valueHolder;
            this.omitNullValues = false;
            this.className = (String) Preconditions.checkNotNull(className2);
        }

        public ToStringHelper omitNullValues() {
            this.omitNullValues = true;
            return this;
        }

        public ToStringHelper add(String name, @Nullable Object value) {
            return addHolder(name, value);
        }

        public ToStringHelper add(String name, boolean value) {
            return addHolder(name, String.valueOf(value));
        }

        public ToStringHelper add(String name, char value) {
            return addHolder(name, String.valueOf(value));
        }

        public ToStringHelper add(String name, double value) {
            return addHolder(name, String.valueOf(value));
        }

        public ToStringHelper add(String name, float value) {
            return addHolder(name, String.valueOf(value));
        }

        public ToStringHelper add(String name, int value) {
            return addHolder(name, String.valueOf(value));
        }

        public ToStringHelper add(String name, long value) {
            return addHolder(name, String.valueOf(value));
        }

        public ToStringHelper addValue(@Nullable Object value) {
            return addHolder(value);
        }

        public ToStringHelper addValue(boolean value) {
            return addHolder(String.valueOf(value));
        }

        public ToStringHelper addValue(char value) {
            return addHolder(String.valueOf(value));
        }

        public ToStringHelper addValue(double value) {
            return addHolder(String.valueOf(value));
        }

        public ToStringHelper addValue(float value) {
            return addHolder(String.valueOf(value));
        }

        public ToStringHelper addValue(int value) {
            return addHolder(String.valueOf(value));
        }

        public ToStringHelper addValue(long value) {
            return addHolder(String.valueOf(value));
        }

        public String toString() {
            boolean omitNullValuesSnapshot = this.omitNullValues;
            String nextSeparator = "";
            StringBuilder builder = new StringBuilder(32).append(this.className).append('{');
            for (ValueHolder valueHolder = this.holderHead.next; valueHolder != null; valueHolder = valueHolder.next) {
                if (!omitNullValuesSnapshot || valueHolder.value != null) {
                    builder.append(nextSeparator);
                    nextSeparator = ", ";
                    if (valueHolder.name != null) {
                        builder.append(valueHolder.name).append('=');
                    }
                    builder.append(valueHolder.value);
                }
            }
            return builder.append('}').toString();
        }

        private ValueHolder addHolder() {
            ValueHolder valueHolder = new ValueHolder();
            this.holderTail.next = valueHolder;
            this.holderTail = valueHolder;
            return valueHolder;
        }

        private ToStringHelper addHolder(@Nullable Object value) {
            addHolder().value = value;
            return this;
        }

        private ToStringHelper addHolder(String name, @Nullable Object value) {
            ValueHolder valueHolder = addHolder();
            valueHolder.value = value;
            valueHolder.name = (String) Preconditions.checkNotNull(name);
            return this;
        }

        private static final class ValueHolder {
            @Nullable
            String name;
            ValueHolder next;
            @Nullable
            Object value;

            private ValueHolder() {
            }
        }
    }
}
