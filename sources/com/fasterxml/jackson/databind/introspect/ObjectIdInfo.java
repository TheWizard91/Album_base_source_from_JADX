package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;

public class ObjectIdInfo {
    protected final boolean _alwaysAsId;
    protected final Class<? extends ObjectIdGenerator<?>> _generator;
    protected final String _propertyName;
    protected final Class<?> _scope;

    public ObjectIdInfo(String str, Class<?> cls, Class<? extends ObjectIdGenerator<?>> cls2) {
        this(str, cls, cls2, false);
    }

    protected ObjectIdInfo(String str, Class<?> cls, Class<? extends ObjectIdGenerator<?>> cls2, boolean z) {
        this._propertyName = str;
        this._scope = cls;
        this._generator = cls2;
        this._alwaysAsId = z;
    }

    public ObjectIdInfo withAlwaysAsId(boolean z) {
        if (this._alwaysAsId == z) {
            return this;
        }
        return new ObjectIdInfo(this._propertyName, this._scope, this._generator, z);
    }

    public String getPropertyName() {
        return this._propertyName;
    }

    public Class<?> getScope() {
        return this._scope;
    }

    public Class<? extends ObjectIdGenerator<?>> getGeneratorType() {
        return this._generator;
    }

    public boolean getAlwaysAsId() {
        return this._alwaysAsId;
    }

    public String toString() {
        StringBuilder append = new StringBuilder().append("ObjectIdInfo: propName=").append(this._propertyName).append(", scope=");
        Class<?> cls = this._scope;
        String str = "null";
        StringBuilder append2 = append.append(cls == null ? str : cls.getName()).append(", generatorType=");
        Class<? extends ObjectIdGenerator<?>> cls2 = this._generator;
        if (cls2 != null) {
            str = cls2.getName();
        }
        return append2.append(str).append(", alwaysAsId=").append(this._alwaysAsId).toString();
    }
}
