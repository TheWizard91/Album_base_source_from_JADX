package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.CreatorProperty;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
import java.io.IOException;
import java.io.Serializable;

public class StdValueInstantiator extends ValueInstantiator implements Serializable {
    private static final long serialVersionUID = 1;
    protected final boolean _cfgEmptyStringsAsObjects;
    protected CreatorProperty[] _constructorArguments;
    protected AnnotatedWithParams _defaultCreator;
    protected CreatorProperty[] _delegateArguments;
    protected AnnotatedWithParams _delegateCreator;
    protected JavaType _delegateType;
    protected AnnotatedWithParams _fromBooleanCreator;
    protected AnnotatedWithParams _fromDoubleCreator;
    protected AnnotatedWithParams _fromIntCreator;
    protected AnnotatedWithParams _fromLongCreator;
    protected AnnotatedWithParams _fromStringCreator;
    protected AnnotatedParameter _incompleteParameter;
    protected final String _valueTypeDesc;
    protected AnnotatedWithParams _withArgsCreator;

    public StdValueInstantiator(DeserializationConfig deserializationConfig, Class<?> cls) {
        this._cfgEmptyStringsAsObjects = deserializationConfig == null ? false : deserializationConfig.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        this._valueTypeDesc = cls == null ? "UNKNOWN TYPE" : cls.getName();
    }

    public StdValueInstantiator(DeserializationConfig deserializationConfig, JavaType javaType) {
        this._cfgEmptyStringsAsObjects = deserializationConfig == null ? false : deserializationConfig.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        this._valueTypeDesc = javaType == null ? "UNKNOWN TYPE" : javaType.toString();
    }

    protected StdValueInstantiator(StdValueInstantiator stdValueInstantiator) {
        this._cfgEmptyStringsAsObjects = stdValueInstantiator._cfgEmptyStringsAsObjects;
        this._valueTypeDesc = stdValueInstantiator._valueTypeDesc;
        this._defaultCreator = stdValueInstantiator._defaultCreator;
        this._constructorArguments = stdValueInstantiator._constructorArguments;
        this._withArgsCreator = stdValueInstantiator._withArgsCreator;
        this._delegateType = stdValueInstantiator._delegateType;
        this._delegateCreator = stdValueInstantiator._delegateCreator;
        this._delegateArguments = stdValueInstantiator._delegateArguments;
        this._fromStringCreator = stdValueInstantiator._fromStringCreator;
        this._fromIntCreator = stdValueInstantiator._fromIntCreator;
        this._fromLongCreator = stdValueInstantiator._fromLongCreator;
        this._fromDoubleCreator = stdValueInstantiator._fromDoubleCreator;
        this._fromBooleanCreator = stdValueInstantiator._fromBooleanCreator;
    }

    public void configureFromObjectSettings(AnnotatedWithParams annotatedWithParams, AnnotatedWithParams annotatedWithParams2, JavaType javaType, CreatorProperty[] creatorPropertyArr, AnnotatedWithParams annotatedWithParams3, CreatorProperty[] creatorPropertyArr2) {
        this._defaultCreator = annotatedWithParams;
        this._delegateCreator = annotatedWithParams2;
        this._delegateType = javaType;
        this._delegateArguments = creatorPropertyArr;
        this._withArgsCreator = annotatedWithParams3;
        this._constructorArguments = creatorPropertyArr2;
    }

    public void configureFromStringCreator(AnnotatedWithParams annotatedWithParams) {
        this._fromStringCreator = annotatedWithParams;
    }

    public void configureFromIntCreator(AnnotatedWithParams annotatedWithParams) {
        this._fromIntCreator = annotatedWithParams;
    }

    public void configureFromLongCreator(AnnotatedWithParams annotatedWithParams) {
        this._fromLongCreator = annotatedWithParams;
    }

    public void configureFromDoubleCreator(AnnotatedWithParams annotatedWithParams) {
        this._fromDoubleCreator = annotatedWithParams;
    }

    public void configureFromBooleanCreator(AnnotatedWithParams annotatedWithParams) {
        this._fromBooleanCreator = annotatedWithParams;
    }

    public void configureIncompleteParameter(AnnotatedParameter annotatedParameter) {
        this._incompleteParameter = annotatedParameter;
    }

    public String getValueTypeDesc() {
        return this._valueTypeDesc;
    }

    public boolean canCreateFromString() {
        return this._fromStringCreator != null;
    }

    public boolean canCreateFromInt() {
        return this._fromIntCreator != null;
    }

    public boolean canCreateFromLong() {
        return this._fromLongCreator != null;
    }

    public boolean canCreateFromDouble() {
        return this._fromDoubleCreator != null;
    }

    public boolean canCreateFromBoolean() {
        return this._fromBooleanCreator != null;
    }

    public boolean canCreateUsingDefault() {
        return this._defaultCreator != null;
    }

    public boolean canCreateUsingDelegate() {
        return this._delegateType != null;
    }

    public boolean canCreateFromObjectWith() {
        return this._withArgsCreator != null;
    }

    public JavaType getDelegateType(DeserializationConfig deserializationConfig) {
        return this._delegateType;
    }

    public SettableBeanProperty[] getFromObjectArguments(DeserializationConfig deserializationConfig) {
        return this._constructorArguments;
    }

    public Object createUsingDefault(DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        AnnotatedWithParams annotatedWithParams = this._defaultCreator;
        if (annotatedWithParams != null) {
            try {
                return annotatedWithParams.call();
            } catch (ExceptionInInitializerError e) {
                throw wrapException(e);
            } catch (Exception e2) {
                throw wrapException(e2);
            }
        } else {
            throw new IllegalStateException("No default constructor for " + getValueTypeDesc());
        }
    }

    public Object createFromObjectWith(DeserializationContext deserializationContext, Object[] objArr) throws IOException, JsonProcessingException {
        AnnotatedWithParams annotatedWithParams = this._withArgsCreator;
        if (annotatedWithParams != null) {
            try {
                return annotatedWithParams.call(objArr);
            } catch (ExceptionInInitializerError e) {
                throw wrapException(e);
            } catch (Exception e2) {
                throw wrapException(e2);
            }
        } else {
            throw new IllegalStateException("No with-args constructor for " + getValueTypeDesc());
        }
    }

    public Object createUsingDelegate(DeserializationContext deserializationContext, Object obj) throws IOException, JsonProcessingException {
        AnnotatedWithParams annotatedWithParams = this._delegateCreator;
        if (annotatedWithParams != null) {
            try {
                CreatorProperty[] creatorPropertyArr = this._delegateArguments;
                if (creatorPropertyArr == null) {
                    return annotatedWithParams.call1(obj);
                }
                int length = creatorPropertyArr.length;
                Object[] objArr = new Object[length];
                for (int i = 0; i < length; i++) {
                    CreatorProperty creatorProperty = this._delegateArguments[i];
                    if (creatorProperty == null) {
                        objArr[i] = obj;
                    } else {
                        objArr[i] = deserializationContext.findInjectableValue(creatorProperty.getInjectableValueId(), creatorProperty, (Object) null);
                    }
                }
                return this._delegateCreator.call(objArr);
            } catch (ExceptionInInitializerError e) {
                throw wrapException(e);
            } catch (Exception e2) {
                throw wrapException(e2);
            }
        } else {
            throw new IllegalStateException("No delegate constructor for " + getValueTypeDesc());
        }
    }

    public Object createFromString(DeserializationContext deserializationContext, String str) throws IOException, JsonProcessingException {
        AnnotatedWithParams annotatedWithParams = this._fromStringCreator;
        if (annotatedWithParams == null) {
            return _createFromStringFallbacks(deserializationContext, str);
        }
        try {
            return annotatedWithParams.call1(str);
        } catch (Exception e) {
            throw wrapException(e);
        } catch (ExceptionInInitializerError e2) {
            throw wrapException(e2);
        }
    }

    public Object createFromInt(DeserializationContext deserializationContext, int i) throws IOException, JsonProcessingException {
        try {
            AnnotatedWithParams annotatedWithParams = this._fromIntCreator;
            if (annotatedWithParams != null) {
                return annotatedWithParams.call1(Integer.valueOf(i));
            }
            AnnotatedWithParams annotatedWithParams2 = this._fromLongCreator;
            if (annotatedWithParams2 != null) {
                return annotatedWithParams2.call1(Long.valueOf((long) i));
            }
            throw new JsonMappingException("Can not instantiate value of type " + getValueTypeDesc() + " from Integral number; no single-int-arg constructor/factory method");
        } catch (Exception e) {
            throw wrapException(e);
        } catch (ExceptionInInitializerError e2) {
            throw wrapException(e2);
        }
    }

    public Object createFromLong(DeserializationContext deserializationContext, long j) throws IOException, JsonProcessingException {
        try {
            AnnotatedWithParams annotatedWithParams = this._fromLongCreator;
            if (annotatedWithParams != null) {
                return annotatedWithParams.call1(Long.valueOf(j));
            }
            throw new JsonMappingException("Can not instantiate value of type " + getValueTypeDesc() + " from Long integral number; no single-long-arg constructor/factory method");
        } catch (Exception e) {
            throw wrapException(e);
        } catch (ExceptionInInitializerError e2) {
            throw wrapException(e2);
        }
    }

    public Object createFromDouble(DeserializationContext deserializationContext, double d) throws IOException, JsonProcessingException {
        try {
            AnnotatedWithParams annotatedWithParams = this._fromDoubleCreator;
            if (annotatedWithParams != null) {
                return annotatedWithParams.call1(Double.valueOf(d));
            }
            throw new JsonMappingException("Can not instantiate value of type " + getValueTypeDesc() + " from Floating-point number; no one-double/Double-arg constructor/factory method");
        } catch (Exception e) {
            throw wrapException(e);
        } catch (ExceptionInInitializerError e2) {
            throw wrapException(e2);
        }
    }

    public Object createFromBoolean(DeserializationContext deserializationContext, boolean z) throws IOException, JsonProcessingException {
        try {
            AnnotatedWithParams annotatedWithParams = this._fromBooleanCreator;
            if (annotatedWithParams != null) {
                return annotatedWithParams.call1(Boolean.valueOf(z));
            }
            throw new JsonMappingException("Can not instantiate value of type " + getValueTypeDesc() + " from Boolean value; no single-boolean/Boolean-arg constructor/factory method");
        } catch (Exception e) {
            throw wrapException(e);
        } catch (ExceptionInInitializerError e2) {
            throw wrapException(e2);
        }
    }

    public AnnotatedWithParams getDelegateCreator() {
        return this._delegateCreator;
    }

    public AnnotatedWithParams getDefaultCreator() {
        return this._defaultCreator;
    }

    public AnnotatedWithParams getWithArgsCreator() {
        return this._withArgsCreator;
    }

    public AnnotatedParameter getIncompleteParameter() {
        return this._incompleteParameter;
    }

    /* access modifiers changed from: protected */
    public Object _createFromStringFallbacks(DeserializationContext deserializationContext, String str) throws IOException, JsonProcessingException {
        if (this._fromBooleanCreator != null) {
            String trim = str.trim();
            if ("true".equals(trim)) {
                return createFromBoolean(deserializationContext, true);
            }
            if ("false".equals(trim)) {
                return createFromBoolean(deserializationContext, false);
            }
        }
        if (this._cfgEmptyStringsAsObjects && str.length() == 0) {
            return null;
        }
        throw new JsonMappingException("Can not instantiate value of type " + getValueTypeDesc() + " from String value; no single-String constructor/factory method");
    }

    /* access modifiers changed from: protected */
    public JsonMappingException wrapException(Throwable th) {
        while (th.getCause() != null) {
            th = th.getCause();
        }
        if (th instanceof JsonMappingException) {
            return (JsonMappingException) th;
        }
        return new JsonMappingException("Instantiation of " + getValueTypeDesc() + " value failed: " + th.getMessage(), th);
    }
}
