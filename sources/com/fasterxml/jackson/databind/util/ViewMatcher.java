package com.fasterxml.jackson.databind.util;

import java.io.Serializable;

public abstract class ViewMatcher {
    public abstract boolean isVisibleForView(Class<?> cls);

    public static ViewMatcher construct(Class<?>[] clsArr) {
        if (clsArr == null) {
            return Empty.instance;
        }
        int length = clsArr.length;
        if (length == 0) {
            return Empty.instance;
        }
        if (length != 1) {
            return new Multi(clsArr);
        }
        return new Single(clsArr[0]);
    }

    private static final class Empty extends ViewMatcher implements Serializable {
        static final Empty instance = new Empty();
        private static final long serialVersionUID = 1;

        private Empty() {
        }

        public boolean isVisibleForView(Class<?> cls) {
            return false;
        }
    }

    private static final class Single extends ViewMatcher implements Serializable {
        private static final long serialVersionUID = 1;
        private final Class<?> _view;

        public Single(Class<?> cls) {
            this._view = cls;
        }

        public boolean isVisibleForView(Class<?> cls) {
            Class<?> cls2 = this._view;
            return cls == cls2 || cls2.isAssignableFrom(cls);
        }
    }

    private static final class Multi extends ViewMatcher implements Serializable {
        private static final long serialVersionUID = 1;
        private final Class<?>[] _views;

        public Multi(Class<?>[] clsArr) {
            this._views = clsArr;
        }

        public boolean isVisibleForView(Class<?> cls) {
            for (Class<?> cls2 : this._views) {
                if (cls == cls2 || cls2.isAssignableFrom(cls)) {
                    return true;
                }
            }
            return false;
        }
    }
}
