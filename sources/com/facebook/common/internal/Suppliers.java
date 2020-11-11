package com.facebook.common.internal;

public class Suppliers {
    public static final Supplier<Boolean> BOOLEAN_FALSE = new Supplier<Boolean>() {
        public Boolean get() {
            return false;
        }
    };
    public static final Supplier<Boolean> BOOLEAN_TRUE = new Supplier<Boolean>() {
        public Boolean get() {
            return true;
        }
    };

    /* renamed from: of */
    public static <T> Supplier<T> m39of(final T instance) {
        return new Supplier<T>() {
            public T get() {
                return instance;
            }
        };
    }
}
