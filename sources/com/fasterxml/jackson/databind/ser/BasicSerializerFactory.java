package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.NoClass;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig;
import com.fasterxml.jackson.databind.ext.OptionalHandlerFactory;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.BasicBeanDescription;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.BooleanSerializer;
import com.fasterxml.jackson.databind.ser.std.CalendarSerializer;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.databind.ser.std.EnumSerializer;
import com.fasterxml.jackson.databind.ser.std.InetAddressSerializer;
import com.fasterxml.jackson.databind.ser.std.JsonValueSerializer;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.fasterxml.jackson.databind.ser.std.NumberSerializers;
import com.fasterxml.jackson.databind.ser.std.SerializableSerializer;
import com.fasterxml.jackson.databind.ser.std.SqlDateSerializer;
import com.fasterxml.jackson.databind.ser.std.SqlTimeSerializer;
import com.fasterxml.jackson.databind.ser.std.StdContainerSerializers;
import com.fasterxml.jackson.databind.ser.std.StdDelegatingSerializer;
import com.fasterxml.jackson.databind.ser.std.StdJdkSerializers;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.fasterxml.jackson.databind.ser.std.TimeZoneSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.ser.std.TokenBufferSerializer;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.Converter;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.RandomAccess;
import java.util.TimeZone;

public abstract class BasicSerializerFactory extends SerializerFactory implements Serializable {
    protected static final HashMap<String, JsonSerializer<?>> _concrete;
    protected static final HashMap<String, Class<? extends JsonSerializer<?>>> _concreteLazy;
    protected final SerializerFactoryConfig _factoryConfig;

    public abstract JsonSerializer<Object> createSerializer(SerializerProvider serializerProvider, JavaType javaType) throws JsonMappingException;

    /* access modifiers changed from: protected */
    public abstract Iterable<Serializers> customSerializers();

    public abstract SerializerFactory withConfig(SerializerFactoryConfig serializerFactoryConfig);

    static {
        HashMap<String, JsonSerializer<?>> hashMap = new HashMap<>();
        _concrete = hashMap;
        HashMap<String, Class<? extends JsonSerializer<?>>> hashMap2 = new HashMap<>();
        _concreteLazy = hashMap2;
        hashMap.put(String.class.getName(), new StringSerializer());
        ToStringSerializer toStringSerializer = ToStringSerializer.instance;
        hashMap.put(StringBuffer.class.getName(), toStringSerializer);
        hashMap.put(StringBuilder.class.getName(), toStringSerializer);
        hashMap.put(Character.class.getName(), toStringSerializer);
        hashMap.put(Character.TYPE.getName(), toStringSerializer);
        NumberSerializers.addAll(hashMap);
        hashMap.put(Boolean.TYPE.getName(), new BooleanSerializer(true));
        hashMap.put(Boolean.class.getName(), new BooleanSerializer(false));
        NumberSerializers.NumberSerializer numberSerializer = new NumberSerializers.NumberSerializer();
        hashMap.put(BigInteger.class.getName(), numberSerializer);
        hashMap.put(BigDecimal.class.getName(), numberSerializer);
        hashMap.put(Calendar.class.getName(), CalendarSerializer.instance);
        DateSerializer dateSerializer = DateSerializer.instance;
        hashMap.put(Date.class.getName(), dateSerializer);
        hashMap.put(Timestamp.class.getName(), dateSerializer);
        hashMap2.put(java.sql.Date.class.getName(), SqlDateSerializer.class);
        hashMap2.put(Time.class.getName(), SqlTimeSerializer.class);
        for (Map.Entry next : StdJdkSerializers.all()) {
            Object value = next.getValue();
            if (value instanceof JsonSerializer) {
                _concrete.put(((Class) next.getKey()).getName(), (JsonSerializer) value);
            } else if (value instanceof Class) {
                _concreteLazy.put(((Class) next.getKey()).getName(), (Class) value);
            } else {
                throw new IllegalStateException("Internal error: unrecognized value of type " + next.getClass().getName());
            }
        }
        _concreteLazy.put(TokenBuffer.class.getName(), TokenBufferSerializer.class);
    }

    protected BasicSerializerFactory(SerializerFactoryConfig serializerFactoryConfig) {
        this._factoryConfig = serializerFactoryConfig == null ? new SerializerFactoryConfig() : serializerFactoryConfig;
    }

    public SerializerFactoryConfig getFactoryConfig() {
        return this._factoryConfig;
    }

    public final SerializerFactory withAdditionalSerializers(Serializers serializers) {
        return withConfig(this._factoryConfig.withAdditionalSerializers(serializers));
    }

    public final SerializerFactory withAdditionalKeySerializers(Serializers serializers) {
        return withConfig(this._factoryConfig.withAdditionalKeySerializers(serializers));
    }

    public final SerializerFactory withSerializerModifier(BeanSerializerModifier beanSerializerModifier) {
        return withConfig(this._factoryConfig.withSerializerModifier(beanSerializerModifier));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0033, code lost:
        if (r7 != null) goto L_0x003b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0035, code lost:
        r7 = com.fasterxml.jackson.databind.ser.std.StdKeySerializers.getStdKeySerializer(r6);
     */
    /* JADX WARNING: Removed duplicated region for block: B:3:0x001c A[LOOP:0: B:3:0x001c->B:6:0x002c, LOOP_START, PHI: r2 
      PHI: (r2v5 com.fasterxml.jackson.databind.JsonSerializer<?>) = (r2v0 com.fasterxml.jackson.databind.JsonSerializer<?>), (r2v8 com.fasterxml.jackson.databind.JsonSerializer<?>) binds: [B:2:0x0012, B:6:0x002c] A[DONT_GENERATE, DONT_INLINE]] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.fasterxml.jackson.databind.JsonSerializer<java.lang.Object> createKeySerializer(com.fasterxml.jackson.databind.SerializationConfig r5, com.fasterxml.jackson.databind.JavaType r6, com.fasterxml.jackson.databind.JsonSerializer<java.lang.Object> r7) {
        /*
            r4 = this;
            java.lang.Class r0 = r6.getRawClass()
            com.fasterxml.jackson.databind.BeanDescription r0 = r5.introspectClassAnnotations((java.lang.Class<?>) r0)
            com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig r1 = r4._factoryConfig
            boolean r1 = r1.hasKeySerializers()
            r2 = 0
            if (r1 == 0) goto L_0x0030
            com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig r1 = r4._factoryConfig
            java.lang.Iterable r1 = r1.keySerializers()
            java.util.Iterator r1 = r1.iterator()
        L_0x001c:
            boolean r3 = r1.hasNext()
            if (r3 == 0) goto L_0x0030
            java.lang.Object r2 = r1.next()
            com.fasterxml.jackson.databind.ser.Serializers r2 = (com.fasterxml.jackson.databind.ser.Serializers) r2
            com.fasterxml.jackson.databind.JsonSerializer r2 = r2.findSerializer(r5, r6, r0)
            if (r2 == 0) goto L_0x002f
            goto L_0x0030
        L_0x002f:
            goto L_0x001c
        L_0x0030:
            if (r2 != 0) goto L_0x003a
            if (r7 != 0) goto L_0x003b
            com.fasterxml.jackson.databind.JsonSerializer r7 = com.fasterxml.jackson.databind.ser.std.StdKeySerializers.getStdKeySerializer(r6)
            goto L_0x003b
        L_0x003a:
            r7 = r2
        L_0x003b:
            com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig r1 = r4._factoryConfig
            boolean r1 = r1.hasSerializerModifiers()
            if (r1 == 0) goto L_0x005e
            com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig r1 = r4._factoryConfig
            java.lang.Iterable r1 = r1.serializerModifiers()
            java.util.Iterator r1 = r1.iterator()
        L_0x004d:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x005e
            java.lang.Object r2 = r1.next()
            com.fasterxml.jackson.databind.ser.BeanSerializerModifier r2 = (com.fasterxml.jackson.databind.ser.BeanSerializerModifier) r2
            com.fasterxml.jackson.databind.JsonSerializer r7 = r2.modifyKeySerializer(r5, r6, r0, r7)
            goto L_0x004d
        L_0x005e:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.ser.BasicSerializerFactory.createKeySerializer(com.fasterxml.jackson.databind.SerializationConfig, com.fasterxml.jackson.databind.JavaType, com.fasterxml.jackson.databind.JsonSerializer):com.fasterxml.jackson.databind.JsonSerializer");
    }

    public TypeSerializer createTypeSerializer(SerializationConfig serializationConfig, JavaType javaType) {
        Collection<NamedType> collection;
        AnnotatedClass classInfo = serializationConfig.introspectClassAnnotations(javaType.getRawClass()).getClassInfo();
        AnnotationIntrospector annotationIntrospector = serializationConfig.getAnnotationIntrospector();
        TypeResolverBuilder<?> findTypeResolver = annotationIntrospector.findTypeResolver(serializationConfig, classInfo, javaType);
        if (findTypeResolver == null) {
            findTypeResolver = serializationConfig.getDefaultTyper(javaType);
            collection = null;
        } else {
            collection = serializationConfig.getSubtypeResolver().collectAndResolveSubtypes(classInfo, (MapperConfig<?>) serializationConfig, annotationIntrospector);
        }
        if (findTypeResolver == null) {
            return null;
        }
        return findTypeResolver.buildTypeSerializer(serializationConfig, javaType, collection);
    }

    public final JsonSerializer<?> getNullSerializer() {
        return NullSerializer.instance;
    }

    /* access modifiers changed from: protected */
    public final JsonSerializer<?> findSerializerByLookup(JavaType javaType, SerializationConfig serializationConfig, BeanDescription beanDescription, boolean z) {
        Class cls;
        String name = javaType.getRawClass().getName();
        JsonSerializer<?> jsonSerializer = _concrete.get(name);
        if (jsonSerializer != null || (cls = _concreteLazy.get(name)) == null) {
            return jsonSerializer;
        }
        try {
            return (JsonSerializer) cls.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to instantiate standard serializer (of type " + cls.getName() + "): " + e.getMessage(), e);
        }
    }

    /* access modifiers changed from: protected */
    public final JsonSerializer<?> findSerializerByAnnotations(SerializerProvider serializerProvider, JavaType javaType, BeanDescription beanDescription) throws JsonMappingException {
        if (JsonSerializable.class.isAssignableFrom(javaType.getRawClass())) {
            return SerializableSerializer.instance;
        }
        AnnotatedMethod findJsonValueMethod = beanDescription.findJsonValueMethod();
        if (findJsonValueMethod == null) {
            return null;
        }
        Method annotated = findJsonValueMethod.getAnnotated();
        if (serializerProvider.canOverrideAccessModifiers()) {
            ClassUtil.checkAndFixAccess(annotated);
        }
        return new JsonValueSerializer(annotated, findSerializerFromAnnotation(serializerProvider, findJsonValueMethod));
    }

    /* access modifiers changed from: protected */
    public final JsonSerializer<?> findSerializerByPrimaryType(SerializerProvider serializerProvider, JavaType javaType, BeanDescription beanDescription, boolean z) throws JsonMappingException {
        Class<?> rawClass = javaType.getRawClass();
        if (InetAddress.class.isAssignableFrom(rawClass)) {
            return InetAddressSerializer.instance;
        }
        if (TimeZone.class.isAssignableFrom(rawClass)) {
            return TimeZoneSerializer.instance;
        }
        if (Charset.class.isAssignableFrom(rawClass)) {
            return ToStringSerializer.instance;
        }
        JsonSerializer<?> findOptionalStdSerializer = findOptionalStdSerializer(serializerProvider, javaType, beanDescription, z);
        if (findOptionalStdSerializer != null) {
            return findOptionalStdSerializer;
        }
        if (Number.class.isAssignableFrom(rawClass)) {
            return NumberSerializers.NumberSerializer.instance;
        }
        if (Enum.class.isAssignableFrom(rawClass)) {
            return buildEnumSerializer(serializerProvider.getConfig(), javaType, beanDescription);
        }
        if (Calendar.class.isAssignableFrom(rawClass)) {
            return CalendarSerializer.instance;
        }
        if (Date.class.isAssignableFrom(rawClass)) {
            return DateSerializer.instance;
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public JsonSerializer<?> findOptionalStdSerializer(SerializerProvider serializerProvider, JavaType javaType, BeanDescription beanDescription, boolean z) throws JsonMappingException {
        return OptionalHandlerFactory.instance.findSerializer(serializerProvider.getConfig(), javaType, beanDescription);
    }

    /* access modifiers changed from: protected */
    public final JsonSerializer<?> findSerializerByAddonType(SerializationConfig serializationConfig, JavaType javaType, BeanDescription beanDescription, boolean z) throws JsonMappingException {
        Class<?> rawClass = javaType.getRawClass();
        if (Iterator.class.isAssignableFrom(rawClass)) {
            return buildIteratorSerializer(serializationConfig, javaType, beanDescription, z);
        }
        if (Iterable.class.isAssignableFrom(rawClass)) {
            return buildIterableSerializer(serializationConfig, javaType, beanDescription, z);
        }
        if (CharSequence.class.isAssignableFrom(rawClass)) {
            return ToStringSerializer.instance;
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public JsonSerializer<Object> findSerializerFromAnnotation(SerializerProvider serializerProvider, Annotated annotated) throws JsonMappingException {
        Object findSerializer = serializerProvider.getAnnotationIntrospector().findSerializer(annotated);
        if (findSerializer == null) {
            return null;
        }
        return findConvertingSerializer(serializerProvider, annotated, serializerProvider.serializerInstance(annotated, findSerializer));
    }

    /* access modifiers changed from: protected */
    public JsonSerializer<?> findConvertingSerializer(SerializerProvider serializerProvider, Annotated annotated, JsonSerializer<?> jsonSerializer) throws JsonMappingException {
        Converter<Object, Object> findConverter = findConverter(serializerProvider, annotated);
        if (findConverter == null) {
            return jsonSerializer;
        }
        return new StdDelegatingSerializer(findConverter, findConverter.getOutputType(serializerProvider.getTypeFactory()), jsonSerializer);
    }

    /* access modifiers changed from: protected */
    public Converter<Object, Object> findConverter(SerializerProvider serializerProvider, Annotated annotated) throws JsonMappingException {
        Object findSerializationConverter = serializerProvider.getAnnotationIntrospector().findSerializationConverter(annotated);
        if (findSerializationConverter == null) {
            return null;
        }
        return serializerProvider.converterInstance(annotated, findSerializationConverter);
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public final JsonSerializer<?> buildContainerSerializer(SerializerProvider serializerProvider, JavaType javaType, BeanDescription beanDescription, BeanProperty beanProperty, boolean z) throws JsonMappingException {
        return buildContainerSerializer(serializerProvider, javaType, beanDescription, z);
    }

    /* access modifiers changed from: protected */
    public JsonSerializer<?> buildContainerSerializer(SerializerProvider serializerProvider, JavaType javaType, BeanDescription beanDescription, boolean z) throws JsonMappingException {
        boolean z2;
        SerializationConfig config = serializerProvider.getConfig();
        if (!z && javaType.useStaticType() && (!javaType.isContainerType() || javaType.getContentType().getRawClass() != Object.class)) {
            z = true;
        }
        TypeSerializer createTypeSerializer = createTypeSerializer(config, javaType.getContentType());
        if (createTypeSerializer != null) {
            z2 = false;
        } else {
            z2 = z;
        }
        JsonSerializer<Object> _findContentSerializer = _findContentSerializer(serializerProvider, beanDescription.getClassInfo());
        if (javaType.isMapLikeType()) {
            MapLikeType mapLikeType = (MapLikeType) javaType;
            JsonSerializer<Object> _findKeySerializer = _findKeySerializer(serializerProvider, beanDescription.getClassInfo());
            if (mapLikeType.isTrueMapType()) {
                return buildMapSerializer(config, (MapType) mapLikeType, beanDescription, z2, _findKeySerializer, createTypeSerializer, _findContentSerializer);
            }
            for (Serializers findMapLikeSerializer : customSerializers()) {
                JsonSerializer<?> findMapLikeSerializer2 = findMapLikeSerializer.findMapLikeSerializer(config, mapLikeType, beanDescription, _findKeySerializer, createTypeSerializer, _findContentSerializer);
                if (findMapLikeSerializer2 != null) {
                    if (this._factoryConfig.hasSerializerModifiers()) {
                        for (BeanSerializerModifier modifyMapLikeSerializer : this._factoryConfig.serializerModifiers()) {
                            findMapLikeSerializer2 = modifyMapLikeSerializer.modifyMapLikeSerializer(config, mapLikeType, beanDescription, findMapLikeSerializer2);
                        }
                    }
                    return findMapLikeSerializer2;
                }
            }
            return null;
        } else if (javaType.isCollectionLikeType()) {
            CollectionLikeType collectionLikeType = (CollectionLikeType) javaType;
            if (collectionLikeType.isTrueCollectionType()) {
                return buildCollectionSerializer(config, (CollectionType) collectionLikeType, beanDescription, z2, createTypeSerializer, _findContentSerializer);
            }
            for (Serializers findCollectionLikeSerializer : customSerializers()) {
                JsonSerializer<?> findCollectionLikeSerializer2 = findCollectionLikeSerializer.findCollectionLikeSerializer(config, collectionLikeType, beanDescription, createTypeSerializer, _findContentSerializer);
                if (findCollectionLikeSerializer2 != null) {
                    if (this._factoryConfig.hasSerializerModifiers()) {
                        for (BeanSerializerModifier modifyCollectionLikeSerializer : this._factoryConfig.serializerModifiers()) {
                            findCollectionLikeSerializer2 = modifyCollectionLikeSerializer.modifyCollectionLikeSerializer(config, collectionLikeType, beanDescription, findCollectionLikeSerializer2);
                        }
                    }
                    return findCollectionLikeSerializer2;
                }
            }
            return null;
        } else if (!javaType.isArrayType()) {
            return null;
        } else {
            return buildArraySerializer(config, (ArrayType) javaType, beanDescription, z2, createTypeSerializer, _findContentSerializer);
        }
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public final JsonSerializer<?> buildCollectionSerializer(SerializationConfig serializationConfig, CollectionType collectionType, BeanDescription beanDescription, BeanProperty beanProperty, boolean z, TypeSerializer typeSerializer, JsonSerializer<Object> jsonSerializer) throws JsonMappingException {
        return buildCollectionSerializer(serializationConfig, collectionType, beanDescription, z, typeSerializer, jsonSerializer);
    }

    /* JADX WARNING: type inference failed for: r15v0, types: [com.fasterxml.jackson.databind.JsonSerializer<java.lang.Object>, com.fasterxml.jackson.databind.JsonSerializer, java.lang.Object] */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:1:0x000b A[LOOP:0: B:1:0x000b->B:4:0x0021, LOOP_START, PHI: r2 
      PHI: (r2v1 com.fasterxml.jackson.databind.JsonSerializer<?>) = (r2v0 com.fasterxml.jackson.databind.JsonSerializer<?>), (r2v14 com.fasterxml.jackson.databind.JsonSerializer<?>) binds: [B:0:0x0000, B:4:0x0021] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.fasterxml.jackson.databind.JsonSerializer<?> buildCollectionSerializer(com.fasterxml.jackson.databind.SerializationConfig r10, com.fasterxml.jackson.databind.type.CollectionType r11, com.fasterxml.jackson.databind.BeanDescription r12, boolean r13, com.fasterxml.jackson.databind.jsontype.TypeSerializer r14, com.fasterxml.jackson.databind.JsonSerializer<java.lang.Object> r15) throws com.fasterxml.jackson.databind.JsonMappingException {
        /*
            r9 = this;
            java.lang.Iterable r0 = r9.customSerializers()
            java.util.Iterator r0 = r0.iterator()
            r1 = 0
            r2 = r1
        L_0x000b:
            boolean r3 = r0.hasNext()
            if (r3 == 0) goto L_0x0025
            java.lang.Object r2 = r0.next()
            r3 = r2
            com.fasterxml.jackson.databind.ser.Serializers r3 = (com.fasterxml.jackson.databind.ser.Serializers) r3
            r4 = r10
            r5 = r11
            r6 = r12
            r7 = r14
            r8 = r15
            com.fasterxml.jackson.databind.JsonSerializer r2 = r3.findCollectionSerializer(r4, r5, r6, r7, r8)
            if (r2 == 0) goto L_0x0024
            goto L_0x0025
        L_0x0024:
            goto L_0x000b
        L_0x0025:
            if (r2 != 0) goto L_0x0094
            com.fasterxml.jackson.annotation.JsonFormat$Value r0 = r12.findExpectedFormat(r1)
            if (r0 == 0) goto L_0x0036
            com.fasterxml.jackson.annotation.JsonFormat$Shape r0 = r0.getShape()
            com.fasterxml.jackson.annotation.JsonFormat$Shape r3 = com.fasterxml.jackson.annotation.JsonFormat.Shape.OBJECT
            if (r0 != r3) goto L_0x0036
            return r1
        L_0x0036:
            java.lang.Class r0 = r11.getRawClass()
            java.lang.Class<java.util.EnumSet> r3 = java.util.EnumSet.class
            boolean r3 = r3.isAssignableFrom(r0)
            if (r3 == 0) goto L_0x0053
            com.fasterxml.jackson.databind.JavaType r13 = r11.getContentType()
            boolean r14 = r13.isEnumType()
            if (r14 != 0) goto L_0x004d
            goto L_0x004e
        L_0x004d:
            r1 = r13
        L_0x004e:
            com.fasterxml.jackson.databind.JsonSerializer r2 = com.fasterxml.jackson.databind.ser.std.StdContainerSerializers.enumSetSerializer(r1)
            goto L_0x0094
        L_0x0053:
            com.fasterxml.jackson.databind.JavaType r1 = r11.getContentType()
            java.lang.Class r1 = r1.getRawClass()
            boolean r0 = r9.isIndexedList(r0)
            if (r0 == 0) goto L_0x007b
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            if (r1 != r0) goto L_0x0071
            if (r15 == 0) goto L_0x006d
            boolean r0 = com.fasterxml.jackson.databind.util.ClassUtil.isJacksonStdImpl((java.lang.Object) r15)
            if (r0 == 0) goto L_0x008a
        L_0x006d:
            com.fasterxml.jackson.databind.ser.impl.IndexedStringListSerializer r0 = com.fasterxml.jackson.databind.ser.impl.IndexedStringListSerializer.instance
            r2 = r0
            goto L_0x008a
        L_0x0071:
            com.fasterxml.jackson.databind.JavaType r0 = r11.getContentType()
            com.fasterxml.jackson.databind.ser.ContainerSerializer r0 = com.fasterxml.jackson.databind.ser.std.StdContainerSerializers.indexedListSerializer(r0, r13, r14, r15)
            r2 = r0
            goto L_0x008a
        L_0x007b:
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            if (r1 != r0) goto L_0x008a
            if (r15 == 0) goto L_0x0087
            boolean r0 = com.fasterxml.jackson.databind.util.ClassUtil.isJacksonStdImpl((java.lang.Object) r15)
            if (r0 == 0) goto L_0x008a
        L_0x0087:
            com.fasterxml.jackson.databind.ser.impl.StringCollectionSerializer r0 = com.fasterxml.jackson.databind.ser.impl.StringCollectionSerializer.instance
            r2 = r0
        L_0x008a:
            if (r2 != 0) goto L_0x0094
            com.fasterxml.jackson.databind.JavaType r0 = r11.getContentType()
            com.fasterxml.jackson.databind.ser.ContainerSerializer r2 = com.fasterxml.jackson.databind.ser.std.StdContainerSerializers.collectionSerializer(r0, r13, r14, r15)
        L_0x0094:
            com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig r13 = r9._factoryConfig
            boolean r13 = r13.hasSerializerModifiers()
            if (r13 == 0) goto L_0x00b7
            com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig r13 = r9._factoryConfig
            java.lang.Iterable r13 = r13.serializerModifiers()
            java.util.Iterator r13 = r13.iterator()
        L_0x00a6:
            boolean r14 = r13.hasNext()
            if (r14 == 0) goto L_0x00b7
            java.lang.Object r14 = r13.next()
            com.fasterxml.jackson.databind.ser.BeanSerializerModifier r14 = (com.fasterxml.jackson.databind.ser.BeanSerializerModifier) r14
            com.fasterxml.jackson.databind.JsonSerializer r2 = r14.modifyCollectionSerializer(r10, r11, r12, r2)
            goto L_0x00a6
        L_0x00b7:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.ser.BasicSerializerFactory.buildCollectionSerializer(com.fasterxml.jackson.databind.SerializationConfig, com.fasterxml.jackson.databind.type.CollectionType, com.fasterxml.jackson.databind.BeanDescription, boolean, com.fasterxml.jackson.databind.jsontype.TypeSerializer, com.fasterxml.jackson.databind.JsonSerializer):com.fasterxml.jackson.databind.JsonSerializer");
    }

    /* access modifiers changed from: protected */
    public boolean isIndexedList(Class<?> cls) {
        return RandomAccess.class.isAssignableFrom(cls);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:1:0x000b A[LOOP:0: B:1:0x000b->B:4:0x0025, LOOP_START, PHI: r3 
      PHI: (r3v1 com.fasterxml.jackson.databind.JsonSerializer<?>) = (r3v0 com.fasterxml.jackson.databind.JsonSerializer<?>), (r3v13 com.fasterxml.jackson.databind.JsonSerializer<?>) binds: [B:0:0x0000, B:4:0x0025] A[DONT_GENERATE, DONT_INLINE]] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.fasterxml.jackson.databind.JsonSerializer<?> buildMapSerializer(com.fasterxml.jackson.databind.SerializationConfig r12, com.fasterxml.jackson.databind.type.MapType r13, com.fasterxml.jackson.databind.BeanDescription r14, boolean r15, com.fasterxml.jackson.databind.JsonSerializer<java.lang.Object> r16, com.fasterxml.jackson.databind.jsontype.TypeSerializer r17, com.fasterxml.jackson.databind.JsonSerializer<java.lang.Object> r18) throws com.fasterxml.jackson.databind.JsonMappingException {
        /*
            r11 = this;
            r0 = r11
            java.lang.Iterable r1 = r11.customSerializers()
            java.util.Iterator r1 = r1.iterator()
            r2 = 0
            r3 = r2
        L_0x000b:
            boolean r4 = r1.hasNext()
            if (r4 == 0) goto L_0x0029
            java.lang.Object r3 = r1.next()
            r4 = r3
            com.fasterxml.jackson.databind.ser.Serializers r4 = (com.fasterxml.jackson.databind.ser.Serializers) r4
            r5 = r12
            r6 = r13
            r7 = r14
            r8 = r16
            r9 = r17
            r10 = r18
            com.fasterxml.jackson.databind.JsonSerializer r3 = r4.findMapSerializer(r5, r6, r7, r8, r9, r10)
            if (r3 == 0) goto L_0x0028
            goto L_0x0029
        L_0x0028:
            goto L_0x000b
        L_0x0029:
            if (r3 != 0) goto L_0x0079
            java.lang.Class<java.util.EnumMap> r1 = java.util.EnumMap.class
            java.lang.Class r3 = r13.getRawClass()
            boolean r1 = r1.isAssignableFrom(r3)
            if (r1 == 0) goto L_0x0061
            com.fasterxml.jackson.databind.JavaType r1 = r13.getKeyType()
            boolean r3 = r1.isEnumType()
            if (r3 == 0) goto L_0x0050
            java.lang.Class r1 = r1.getRawClass()
            com.fasterxml.jackson.databind.AnnotationIntrospector r2 = r12.getAnnotationIntrospector()
            com.fasterxml.jackson.databind.util.EnumValues r2 = com.fasterxml.jackson.databind.util.EnumValues.construct(r1, r2)
            r6 = r2
            goto L_0x0051
        L_0x0050:
            r6 = r2
        L_0x0051:
            com.fasterxml.jackson.databind.ser.std.EnumMapSerializer r1 = new com.fasterxml.jackson.databind.ser.std.EnumMapSerializer
            com.fasterxml.jackson.databind.JavaType r4 = r13.getContentType()
            r3 = r1
            r5 = r15
            r7 = r17
            r8 = r18
            r3.<init>(r4, r5, r6, r7, r8)
            goto L_0x0079
        L_0x0061:
            com.fasterxml.jackson.databind.AnnotationIntrospector r1 = r12.getAnnotationIntrospector()
            com.fasterxml.jackson.databind.introspect.AnnotatedClass r2 = r14.getClassInfo()
            java.lang.String[] r3 = r1.findPropertiesToIgnore(r2)
            r4 = r13
            r5 = r15
            r6 = r17
            r7 = r16
            r8 = r18
            com.fasterxml.jackson.databind.ser.std.MapSerializer r3 = com.fasterxml.jackson.databind.ser.std.MapSerializer.construct(r3, r4, r5, r6, r7, r8)
        L_0x0079:
            com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig r1 = r0._factoryConfig
            boolean r1 = r1.hasSerializerModifiers()
            if (r1 == 0) goto L_0x009f
            com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig r1 = r0._factoryConfig
            java.lang.Iterable r1 = r1.serializerModifiers()
            java.util.Iterator r1 = r1.iterator()
        L_0x008b:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x009f
            java.lang.Object r2 = r1.next()
            com.fasterxml.jackson.databind.ser.BeanSerializerModifier r2 = (com.fasterxml.jackson.databind.ser.BeanSerializerModifier) r2
            r4 = r12
            r5 = r13
            r6 = r14
            com.fasterxml.jackson.databind.JsonSerializer r3 = r2.modifyMapSerializer(r12, r13, r14, r3)
            goto L_0x008b
        L_0x009f:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.ser.BasicSerializerFactory.buildMapSerializer(com.fasterxml.jackson.databind.SerializationConfig, com.fasterxml.jackson.databind.type.MapType, com.fasterxml.jackson.databind.BeanDescription, boolean, com.fasterxml.jackson.databind.JsonSerializer, com.fasterxml.jackson.databind.jsontype.TypeSerializer, com.fasterxml.jackson.databind.JsonSerializer):com.fasterxml.jackson.databind.JsonSerializer");
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:1:0x000a A[LOOP:0: B:1:0x000a->B:4:0x0020, LOOP_START, PHI: r1 
      PHI: (r1v1 com.fasterxml.jackson.databind.JsonSerializer<?>) = (r1v0 com.fasterxml.jackson.databind.JsonSerializer<?>), (r1v13 com.fasterxml.jackson.databind.JsonSerializer<?>) binds: [B:0:0x0000, B:4:0x0020] A[DONT_GENERATE, DONT_INLINE]] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.fasterxml.jackson.databind.JsonSerializer<?> buildArraySerializer(com.fasterxml.jackson.databind.SerializationConfig r9, com.fasterxml.jackson.databind.type.ArrayType r10, com.fasterxml.jackson.databind.BeanDescription r11, boolean r12, com.fasterxml.jackson.databind.jsontype.TypeSerializer r13, com.fasterxml.jackson.databind.JsonSerializer<java.lang.Object> r14) throws com.fasterxml.jackson.databind.JsonMappingException {
        /*
            r8 = this;
            java.lang.Iterable r0 = r8.customSerializers()
            java.util.Iterator r0 = r0.iterator()
            r1 = 0
        L_0x000a:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x0024
            java.lang.Object r1 = r0.next()
            r2 = r1
            com.fasterxml.jackson.databind.ser.Serializers r2 = (com.fasterxml.jackson.databind.ser.Serializers) r2
            r3 = r9
            r4 = r10
            r5 = r11
            r6 = r13
            r7 = r14
            com.fasterxml.jackson.databind.JsonSerializer r1 = r2.findArraySerializer(r3, r4, r5, r6, r7)
            if (r1 == 0) goto L_0x0023
            goto L_0x0024
        L_0x0023:
            goto L_0x000a
        L_0x0024:
            if (r1 != 0) goto L_0x0048
            java.lang.Class r0 = r10.getRawClass()
            if (r14 == 0) goto L_0x0032
            boolean r2 = com.fasterxml.jackson.databind.util.ClassUtil.isJacksonStdImpl((java.lang.Object) r14)
            if (r2 == 0) goto L_0x003d
        L_0x0032:
            java.lang.Class<java.lang.String[]> r1 = java.lang.String[].class
            if (r1 != r0) goto L_0x0039
            com.fasterxml.jackson.databind.ser.impl.StringArraySerializer r1 = com.fasterxml.jackson.databind.ser.impl.StringArraySerializer.instance
            goto L_0x003d
        L_0x0039:
            com.fasterxml.jackson.databind.JsonSerializer r1 = com.fasterxml.jackson.databind.ser.std.StdArraySerializers.findStandardImpl(r0)
        L_0x003d:
            if (r1 != 0) goto L_0x0048
            com.fasterxml.jackson.databind.ser.std.ObjectArraySerializer r1 = new com.fasterxml.jackson.databind.ser.std.ObjectArraySerializer
            com.fasterxml.jackson.databind.JavaType r0 = r10.getContentType()
            r1.<init>((com.fasterxml.jackson.databind.JavaType) r0, (boolean) r12, (com.fasterxml.jackson.databind.jsontype.TypeSerializer) r13, (com.fasterxml.jackson.databind.JsonSerializer<java.lang.Object>) r14)
        L_0x0048:
            com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig r12 = r8._factoryConfig
            boolean r12 = r12.hasSerializerModifiers()
            if (r12 == 0) goto L_0x006b
            com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig r12 = r8._factoryConfig
            java.lang.Iterable r12 = r12.serializerModifiers()
            java.util.Iterator r12 = r12.iterator()
        L_0x005a:
            boolean r13 = r12.hasNext()
            if (r13 == 0) goto L_0x006b
            java.lang.Object r13 = r12.next()
            com.fasterxml.jackson.databind.ser.BeanSerializerModifier r13 = (com.fasterxml.jackson.databind.ser.BeanSerializerModifier) r13
            com.fasterxml.jackson.databind.JsonSerializer r1 = r13.modifyArraySerializer(r9, r10, r11, r1)
            goto L_0x005a
        L_0x006b:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.ser.BasicSerializerFactory.buildArraySerializer(com.fasterxml.jackson.databind.SerializationConfig, com.fasterxml.jackson.databind.type.ArrayType, com.fasterxml.jackson.databind.BeanDescription, boolean, com.fasterxml.jackson.databind.jsontype.TypeSerializer, com.fasterxml.jackson.databind.JsonSerializer):com.fasterxml.jackson.databind.JsonSerializer");
    }

    /* access modifiers changed from: protected */
    public JsonSerializer<?> buildIteratorSerializer(SerializationConfig serializationConfig, JavaType javaType, BeanDescription beanDescription, boolean z) throws JsonMappingException {
        JavaType containedType = javaType.containedType(0);
        if (containedType == null) {
            containedType = TypeFactory.unknownType();
        }
        TypeSerializer createTypeSerializer = createTypeSerializer(serializationConfig, containedType);
        return StdContainerSerializers.iteratorSerializer(containedType, usesStaticTyping(serializationConfig, beanDescription, createTypeSerializer), createTypeSerializer);
    }

    /* access modifiers changed from: protected */
    public JsonSerializer<?> buildIterableSerializer(SerializationConfig serializationConfig, JavaType javaType, BeanDescription beanDescription, boolean z) throws JsonMappingException {
        JavaType containedType = javaType.containedType(0);
        if (containedType == null) {
            containedType = TypeFactory.unknownType();
        }
        TypeSerializer createTypeSerializer = createTypeSerializer(serializationConfig, containedType);
        return StdContainerSerializers.iterableSerializer(containedType, usesStaticTyping(serializationConfig, beanDescription, createTypeSerializer), createTypeSerializer);
    }

    /* access modifiers changed from: protected */
    public JsonSerializer<?> buildEnumSerializer(SerializationConfig serializationConfig, JavaType javaType, BeanDescription beanDescription) throws JsonMappingException {
        JsonFormat.Value findExpectedFormat = beanDescription.findExpectedFormat((JsonFormat.Value) null);
        if (findExpectedFormat == null || findExpectedFormat.getShape() != JsonFormat.Shape.OBJECT) {
            JsonSerializer<?> construct = EnumSerializer.construct(javaType.getRawClass(), serializationConfig, beanDescription, findExpectedFormat);
            if (this._factoryConfig.hasSerializerModifiers()) {
                for (BeanSerializerModifier modifyEnumSerializer : this._factoryConfig.serializerModifiers()) {
                    construct = modifyEnumSerializer.modifyEnumSerializer(serializationConfig, javaType, beanDescription, construct);
                }
            }
            return construct;
        }
        ((BasicBeanDescription) beanDescription).removeProperty("declaringClass");
        return null;
    }

    /* access modifiers changed from: protected */
    public <T extends JavaType> T modifyTypeByAnnotation(SerializationConfig serializationConfig, Annotated annotated, T t) {
        Class<?> findSerializationType = serializationConfig.getAnnotationIntrospector().findSerializationType(annotated);
        if (findSerializationType != null) {
            try {
                t = t.widenBy(findSerializationType);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Failed to widen type " + t + " with concrete-type annotation (value " + findSerializationType.getName() + "), method '" + annotated.getName() + "': " + e.getMessage());
            }
        }
        return modifySecondaryTypesByAnnotation(serializationConfig, annotated, t);
    }

    protected static <T extends JavaType> T modifySecondaryTypesByAnnotation(SerializationConfig serializationConfig, Annotated annotated, T t) {
        AnnotationIntrospector annotationIntrospector = serializationConfig.getAnnotationIntrospector();
        if (!t.isContainerType()) {
            return t;
        }
        Class<?> findSerializationKeyType = annotationIntrospector.findSerializationKeyType(annotated, t.getKeyType());
        if (findSerializationKeyType != null) {
            if (t instanceof MapType) {
                try {
                    t = ((MapType) t).widenKey(findSerializationKeyType);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Failed to narrow key type " + t + " with key-type annotation (" + findSerializationKeyType.getName() + "): " + e.getMessage());
                }
            } else {
                throw new IllegalArgumentException("Illegal key-type annotation: type " + t + " is not a Map type");
            }
        }
        Class<?> findSerializationContentType = annotationIntrospector.findSerializationContentType(annotated, t.getContentType());
        if (findSerializationContentType == null) {
            return t;
        }
        try {
            return t.widenContentsBy(findSerializationContentType);
        } catch (IllegalArgumentException e2) {
            throw new IllegalArgumentException("Failed to narrow content type " + t + " with content-type annotation (" + findSerializationContentType.getName() + "): " + e2.getMessage());
        }
    }

    /* access modifiers changed from: protected */
    public JsonSerializer<Object> _findKeySerializer(SerializerProvider serializerProvider, Annotated annotated) throws JsonMappingException {
        Object findKeySerializer = serializerProvider.getAnnotationIntrospector().findKeySerializer(annotated);
        if (findKeySerializer != null) {
            return serializerProvider.serializerInstance(annotated, findKeySerializer);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public JsonSerializer<Object> _findContentSerializer(SerializerProvider serializerProvider, Annotated annotated) throws JsonMappingException {
        Object findContentSerializer = serializerProvider.getAnnotationIntrospector().findContentSerializer(annotated);
        if (findContentSerializer != null) {
            return serializerProvider.serializerInstance(annotated, findContentSerializer);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public final boolean usesStaticTyping(SerializationConfig serializationConfig, BeanDescription beanDescription, TypeSerializer typeSerializer, BeanProperty beanProperty) {
        return usesStaticTyping(serializationConfig, beanDescription, typeSerializer);
    }

    /* access modifiers changed from: protected */
    public boolean usesStaticTyping(SerializationConfig serializationConfig, BeanDescription beanDescription, TypeSerializer typeSerializer) {
        if (typeSerializer != null) {
            return false;
        }
        JsonSerialize.Typing findSerializationTyping = serializationConfig.getAnnotationIntrospector().findSerializationTyping(beanDescription.getClassInfo());
        if (findSerializationTyping == null) {
            return serializationConfig.isEnabled(MapperFeature.USE_STATIC_TYPING);
        }
        if (findSerializationTyping == JsonSerialize.Typing.STATIC) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public Class<?> _verifyAsClass(Object obj, String str, Class<?> cls) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Class) {
            Class<NoClass> cls2 = (Class) obj;
            if (cls2 == cls || cls2 == NoClass.class) {
                return null;
            }
            return cls2;
        }
        throw new IllegalStateException("AnnotationIntrospector." + str + "() returned value of type " + obj.getClass().getName() + ": expected type JsonSerializer or Class<JsonSerializer> instead");
    }
}
