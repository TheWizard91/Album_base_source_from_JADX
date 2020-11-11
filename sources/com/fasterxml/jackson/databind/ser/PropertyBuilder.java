package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.util.Annotations;

public class PropertyBuilder {
    protected final AnnotationIntrospector _annotationIntrospector;
    protected final BeanDescription _beanDesc;
    protected final SerializationConfig _config;
    protected Object _defaultBean;
    protected final JsonInclude.Include _outputProps;

    public PropertyBuilder(SerializationConfig serializationConfig, BeanDescription beanDescription) {
        this._config = serializationConfig;
        this._beanDesc = beanDescription;
        this._outputProps = beanDescription.findSerializationInclusion(serializationConfig.getSerializationInclusion());
        this._annotationIntrospector = serializationConfig.getAnnotationIntrospector();
    }

    public Annotations getClassAnnotations() {
        return this._beanDesc.getClassAnnotations();
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x007c, code lost:
        if (r3 != 4) goto L_0x00bc;
     */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00da  */
    /* JADX WARNING: Removed duplicated region for block: B:39:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.fasterxml.jackson.databind.ser.BeanPropertyWriter buildWriter(com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition r14, com.fasterxml.jackson.databind.JavaType r15, com.fasterxml.jackson.databind.JsonSerializer<?> r16, com.fasterxml.jackson.databind.jsontype.TypeSerializer r17, com.fasterxml.jackson.databind.jsontype.TypeSerializer r18, com.fasterxml.jackson.databind.introspect.AnnotatedMember r19, boolean r20) {
        /*
            r13 = this;
            r0 = r13
            r1 = r18
            r11 = r19
            r5 = r15
            r2 = r20
            com.fasterxml.jackson.databind.JavaType r2 = r13.findSerializationType(r11, r2, r15)
            if (r1 == 0) goto L_0x005d
            if (r2 != 0) goto L_0x0011
            r2 = r5
        L_0x0011:
            com.fasterxml.jackson.databind.JavaType r3 = r2.getContentType()
            if (r3 == 0) goto L_0x0020
            com.fasterxml.jackson.databind.JavaType r1 = r2.withContentTypeHandler(r1)
            r1.getContentType()
            r8 = r1
            goto L_0x005e
        L_0x0020:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Problem trying to create BeanPropertyWriter for property '"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = r14.getName()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = "' (of type "
            java.lang.StringBuilder r3 = r3.append(r4)
            com.fasterxml.jackson.databind.BeanDescription r4 = r0._beanDesc
            com.fasterxml.jackson.databind.JavaType r4 = r4.getType()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = "); serialization type "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r2 = r3.append(r2)
            java.lang.String r3 = " has no content"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x005d:
            r8 = r2
        L_0x005e:
            r1 = 0
            r2 = 0
            com.fasterxml.jackson.databind.AnnotationIntrospector r3 = r0._annotationIntrospector
            com.fasterxml.jackson.annotation.JsonInclude$Include r4 = r0._outputProps
            com.fasterxml.jackson.annotation.JsonInclude$Include r3 = r3.findSerializationInclusion(r11, r4)
            r4 = 1
            if (r3 == 0) goto L_0x00bc
            int[] r6 = com.fasterxml.jackson.databind.ser.PropertyBuilder.C08321.$SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include
            int r3 = r3.ordinal()
            r3 = r6[r3]
            if (r3 == r4) goto L_0x009b
            r6 = 2
            if (r3 == r6) goto L_0x0095
            r6 = 3
            if (r3 == r6) goto L_0x007f
            r4 = 4
            if (r3 == r4) goto L_0x0080
            goto L_0x00bc
        L_0x007f:
            r2 = r4
        L_0x0080:
            boolean r3 = r15.isContainerType()
            if (r3 == 0) goto L_0x00bc
            com.fasterxml.jackson.databind.SerializationConfig r3 = r0._config
            com.fasterxml.jackson.databind.SerializationFeature r4 = com.fasterxml.jackson.databind.SerializationFeature.WRITE_EMPTY_JSON_ARRAYS
            boolean r3 = r3.isEnabled(r4)
            if (r3 != 0) goto L_0x00bc
            java.lang.Object r1 = com.fasterxml.jackson.databind.ser.BeanPropertyWriter.MARKER_FOR_EMPTY
            r10 = r1
            r9 = r2
            goto L_0x00be
        L_0x0095:
            java.lang.Object r1 = com.fasterxml.jackson.databind.ser.BeanPropertyWriter.MARKER_FOR_EMPTY
            r10 = r1
            r9 = r4
            goto L_0x00be
        L_0x009b:
            java.lang.String r1 = r14.getName()
            java.lang.Object r1 = r13.getDefaultValue(r1, r11)
            if (r1 != 0) goto L_0x00a8
            r10 = r1
            r9 = r4
            goto L_0x00be
        L_0x00a8:
            java.lang.Class r3 = r1.getClass()
            boolean r3 = r3.isArray()
            if (r3 == 0) goto L_0x00b9
            java.lang.Object r1 = com.fasterxml.jackson.databind.util.ArrayBuilders.getArrayComparator(r1)
            r10 = r1
            r9 = r2
            goto L_0x00be
        L_0x00b9:
            r10 = r1
            r9 = r2
            goto L_0x00be
        L_0x00bc:
            r10 = r1
            r9 = r2
        L_0x00be:
            com.fasterxml.jackson.databind.ser.BeanPropertyWriter r12 = new com.fasterxml.jackson.databind.ser.BeanPropertyWriter
            com.fasterxml.jackson.databind.BeanDescription r1 = r0._beanDesc
            com.fasterxml.jackson.databind.util.Annotations r4 = r1.getClassAnnotations()
            r1 = r12
            r2 = r14
            r3 = r19
            r5 = r15
            r6 = r16
            r7 = r17
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10)
            com.fasterxml.jackson.databind.AnnotationIntrospector r1 = r0._annotationIntrospector
            com.fasterxml.jackson.databind.util.NameTransformer r1 = r1.findUnwrappingNameTransformer(r11)
            if (r1 == 0) goto L_0x00de
            com.fasterxml.jackson.databind.ser.BeanPropertyWriter r12 = r12.unwrappingWriter(r1)
        L_0x00de:
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.ser.PropertyBuilder.buildWriter(com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition, com.fasterxml.jackson.databind.JavaType, com.fasterxml.jackson.databind.JsonSerializer, com.fasterxml.jackson.databind.jsontype.TypeSerializer, com.fasterxml.jackson.databind.jsontype.TypeSerializer, com.fasterxml.jackson.databind.introspect.AnnotatedMember, boolean):com.fasterxml.jackson.databind.ser.BeanPropertyWriter");
    }

    /* renamed from: com.fasterxml.jackson.databind.ser.PropertyBuilder$1 */
    static /* synthetic */ class C08321 {
        static final /* synthetic */ int[] $SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include;

        static {
            int[] iArr = new int[JsonInclude.Include.values().length];
            $SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include = iArr;
            try {
                iArr[JsonInclude.Include.NON_DEFAULT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include[JsonInclude.Include.NON_EMPTY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include[JsonInclude.Include.NON_NULL.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$fasterxml$jackson$annotation$JsonInclude$Include[JsonInclude.Include.ALWAYS.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public JavaType findSerializationType(Annotated annotated, boolean z, JavaType javaType) {
        JsonSerialize.Typing findSerializationTyping;
        Class<?> findSerializationType = this._annotationIntrospector.findSerializationType(annotated);
        boolean z2 = true;
        if (findSerializationType != null) {
            Class<?> rawClass = javaType.getRawClass();
            if (findSerializationType.isAssignableFrom(rawClass)) {
                javaType = javaType.widenBy(findSerializationType);
            } else if (rawClass.isAssignableFrom(findSerializationType)) {
                javaType = this._config.constructSpecializedType(javaType, findSerializationType);
            } else {
                throw new IllegalArgumentException("Illegal concrete-type annotation for method '" + annotated.getName() + "': class " + findSerializationType.getName() + " not a super-type of (declared) class " + rawClass.getName());
            }
            z = true;
        }
        JavaType modifySecondaryTypesByAnnotation = BeanSerializerFactory.modifySecondaryTypesByAnnotation(this._config, annotated, javaType);
        if (modifySecondaryTypesByAnnotation != javaType) {
            javaType = modifySecondaryTypesByAnnotation;
            z = true;
        }
        if (!z && (findSerializationTyping = this._annotationIntrospector.findSerializationTyping(annotated)) != null) {
            if (findSerializationTyping != JsonSerialize.Typing.STATIC) {
                z2 = false;
            }
            z = z2;
        }
        if (z) {
            return javaType;
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public Object getDefaultBean() {
        if (this._defaultBean == null) {
            Object instantiateBean = this._beanDesc.instantiateBean(this._config.canOverrideAccessModifiers());
            this._defaultBean = instantiateBean;
            if (instantiateBean == null) {
                throw new IllegalArgumentException("Class " + this._beanDesc.getClassInfo().getAnnotated().getName() + " has no default constructor; can not instantiate default bean value to support 'properties=JsonSerialize.Inclusion.NON_DEFAULT' annotation");
            }
        }
        return this._defaultBean;
    }

    /* access modifiers changed from: protected */
    public Object getDefaultValue(String str, AnnotatedMember annotatedMember) {
        Object defaultBean = getDefaultBean();
        try {
            return annotatedMember.getValue(defaultBean);
        } catch (Exception e) {
            return _throwWrapped(e, str, defaultBean);
        }
    }

    /* access modifiers changed from: protected */
    public Object _throwWrapped(Exception exc, String str, Object obj) {
        Throwable th = exc;
        while (th.getCause() != null) {
            th = th.getCause();
        }
        if (th instanceof Error) {
            throw ((Error) th);
        } else if (th instanceof RuntimeException) {
            throw ((RuntimeException) th);
        } else {
            throw new IllegalArgumentException("Failed to get property '" + str + "' of default " + obj.getClass().getName() + " instance");
        }
    }
}
