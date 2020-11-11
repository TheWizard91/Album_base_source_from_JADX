package com.facebook.common.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImmutableList<E> extends ArrayList<E> {
    private ImmutableList(int capacity) {
        super(capacity);
    }

    private ImmutableList(List<E> list) {
        super(list);
    }

    public static <E> ImmutableList<E> copyOf(List<E> list) {
        return new ImmutableList<>(list);
    }

    /* renamed from: of */
    public static <E> ImmutableList<E> m30of(E... elements) {
        ImmutableList<E> list = new ImmutableList<>(elements.length);
        Collections.addAll(list, elements);
        return list;
    }
}
