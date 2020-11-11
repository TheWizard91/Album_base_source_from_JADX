package com.google.android.play.core.internal;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;

/* renamed from: com.google.android.play.core.internal.bk */
public final class C3026bk<T> {

    /* renamed from: a */
    private final Object f1308a;

    /* renamed from: b */
    private final Field f1309b;

    /* renamed from: c */
    private final Class<T> f1310c;

    C3026bk(Object obj, Field field, Class<T> cls) {
        this.f1308a = obj;
        this.f1309b = field;
        this.f1310c = cls;
    }

    C3026bk(Object obj, Field field, Class cls, byte[] bArr) {
        this(obj, field, Array.newInstance(cls, 0).getClass());
    }

    /* renamed from: c */
    private Class m704c() {
        return mo44115b().getType().getComponentType();
    }

    /* renamed from: a */
    public final T mo44112a() {
        try {
            return this.f1310c.cast(this.f1309b.get(this.f1308a));
        } catch (Exception e) {
            throw new C3028bm(String.format("Failed to get value of field %s of type %s on object of type %s", new Object[]{this.f1309b.getName(), this.f1308a.getClass().getName(), this.f1310c.getName()}), e);
        }
    }

    /* renamed from: a */
    public final void mo44113a(T t) {
        try {
            this.f1309b.set(this.f1308a, t);
        } catch (Exception e) {
            throw new C3028bm(String.format("Failed to set value of field %s of type %s on object of type %s", new Object[]{this.f1309b.getName(), this.f1308a.getClass().getName(), this.f1310c.getName()}), e);
        }
    }

    /* renamed from: a */
    public void mo44114a(Collection collection) {
        Object[] objArr = (Object[]) mo44112a();
        int length = objArr != null ? objArr.length : 0;
        Object[] objArr2 = (Object[]) Array.newInstance(m704c(), collection.size() + length);
        if (objArr != null) {
            System.arraycopy(objArr, 0, objArr2, 0, objArr.length);
        }
        for (Object obj : collection) {
            objArr2[length] = obj;
            length++;
        }
        mo44113a(objArr2);
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public final Field mo44115b() {
        return this.f1309b;
    }

    /* renamed from: b */
    public void mo44116b(Collection collection) {
        Object[] objArr = (Object[]) mo44112a();
        int i = 0;
        Object[] objArr2 = (Object[]) Array.newInstance(m704c(), (objArr != null ? objArr.length : 0) + collection.size());
        if (objArr != null) {
            System.arraycopy(objArr, 0, objArr2, collection.size(), objArr.length);
        }
        for (Object obj : collection) {
            objArr2[i] = obj;
            i++;
        }
        mo44113a(objArr2);
    }
}
