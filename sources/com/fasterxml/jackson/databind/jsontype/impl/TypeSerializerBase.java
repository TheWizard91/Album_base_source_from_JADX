package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

public abstract class TypeSerializerBase extends TypeSerializer {
    protected final TypeIdResolver _idResolver;
    protected final BeanProperty _property;

    public abstract JsonTypeInfo.C0788As getTypeInclusion();

    protected TypeSerializerBase(TypeIdResolver typeIdResolver, BeanProperty beanProperty) {
        this._idResolver = typeIdResolver;
        this._property = beanProperty;
    }

    public String getPropertyName() {
        return null;
    }

    public TypeIdResolver getTypeIdResolver() {
        return this._idResolver;
    }

    /* access modifiers changed from: protected */
    public String idFromValue(Object obj) {
        return this._idResolver.idFromValue(obj);
    }

    /* access modifiers changed from: protected */
    public String idFromValueAndType(Object obj, Class<?> cls) {
        return this._idResolver.idFromValueAndType(obj, cls);
    }
}
