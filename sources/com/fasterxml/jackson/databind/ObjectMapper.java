package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.core.p007io.SegmentedStringWriter;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.cfg.BaseSettings;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.cfg.PackageVersion;
import com.fasterxml.jackson.databind.deser.BeanDeserializerFactory;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.deser.DeserializerFactory;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.deser.KeyDeserializers;
import com.fasterxml.jackson.databind.deser.ValueInstantiators;
import com.fasterxml.jackson.databind.introspect.BasicClassIntrospector;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.jsontype.impl.StdSubtypeResolver;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.type.ClassKey;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.type.TypeModifier;
import com.fasterxml.jackson.databind.util.RootNameLookup;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

public class ObjectMapper extends ObjectCodec implements Versioned, Serializable {
    protected static final AnnotationIntrospector DEFAULT_ANNOTATION_INTROSPECTOR;
    protected static final BaseSettings DEFAULT_BASE;
    protected static final ClassIntrospector DEFAULT_INTROSPECTOR;
    private static final JavaType JSON_NODE_TYPE = SimpleType.constructUnsafe(JsonNode.class);
    protected static final VisibilityChecker<?> STD_VISIBILITY_CHECKER;
    protected static final PrettyPrinter _defaultPrettyPrinter = new DefaultPrettyPrinter();
    private static final long serialVersionUID = 1;
    protected DeserializationConfig _deserializationConfig;
    protected DefaultDeserializationContext _deserializationContext;
    protected InjectableValues _injectableValues;
    protected final JsonFactory _jsonFactory;
    protected final HashMap<ClassKey, Class<?>> _mixInAnnotations;
    protected final ConcurrentHashMap<JavaType, JsonDeserializer<Object>> _rootDeserializers;
    protected final RootNameLookup _rootNames;
    protected SerializationConfig _serializationConfig;
    protected SerializerFactory _serializerFactory;
    protected DefaultSerializerProvider _serializerProvider;
    protected SubtypeResolver _subtypeResolver;
    protected TypeFactory _typeFactory;

    public enum DefaultTyping {
        JAVA_LANG_OBJECT,
        OBJECT_AND_NON_CONCRETE,
        NON_CONCRETE_AND_ARRAYS,
        NON_FINAL
    }

    public static class DefaultTypeResolverBuilder extends StdTypeResolverBuilder implements Serializable {
        private static final long serialVersionUID = 1;
        protected final DefaultTyping _appliesFor;

        public DefaultTypeResolverBuilder(DefaultTyping defaultTyping) {
            this._appliesFor = defaultTyping;
        }

        public TypeDeserializer buildTypeDeserializer(DeserializationConfig deserializationConfig, JavaType javaType, Collection<NamedType> collection) {
            if (useForType(javaType)) {
                return super.buildTypeDeserializer(deserializationConfig, javaType, collection);
            }
            return null;
        }

        public TypeSerializer buildTypeSerializer(SerializationConfig serializationConfig, JavaType javaType, Collection<NamedType> collection) {
            if (useForType(javaType)) {
                return super.buildTypeSerializer(serializationConfig, javaType, collection);
            }
            return null;
        }

        public boolean useForType(JavaType javaType) {
            int i = C07982.f88x3ef634e7[this._appliesFor.ordinal()];
            if (i == 1) {
                while (javaType.isArrayType()) {
                    javaType = javaType.getContentType();
                }
            } else if (i != 2) {
                if (i == 3) {
                    while (javaType.isArrayType()) {
                        javaType = javaType.getContentType();
                    }
                    return !javaType.isFinal();
                } else if (javaType.getRawClass() == Object.class) {
                    return true;
                } else {
                    return false;
                }
            }
            if (javaType.getRawClass() == Object.class || !javaType.isConcrete()) {
                return true;
            }
            return false;
        }
    }

    /* renamed from: com.fasterxml.jackson.databind.ObjectMapper$2 */
    static /* synthetic */ class C07982 {

        /* renamed from: $SwitchMap$com$fasterxml$jackson$databind$ObjectMapper$DefaultTyping */
        static final /* synthetic */ int[] f88x3ef634e7;

        static {
            int[] iArr = new int[DefaultTyping.values().length];
            f88x3ef634e7 = iArr;
            try {
                iArr[DefaultTyping.NON_CONCRETE_AND_ARRAYS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f88x3ef634e7[DefaultTyping.OBJECT_AND_NON_CONCRETE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f88x3ef634e7[DefaultTyping.NON_FINAL.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static {
        BasicClassIntrospector basicClassIntrospector = BasicClassIntrospector.instance;
        DEFAULT_INTROSPECTOR = basicClassIntrospector;
        JacksonAnnotationIntrospector jacksonAnnotationIntrospector = new JacksonAnnotationIntrospector();
        DEFAULT_ANNOTATION_INTROSPECTOR = jacksonAnnotationIntrospector;
        VisibilityChecker.Std defaultInstance = VisibilityChecker.Std.defaultInstance();
        STD_VISIBILITY_CHECKER = defaultInstance;
        DEFAULT_BASE = new BaseSettings(basicClassIntrospector, jacksonAnnotationIntrospector, defaultInstance, (PropertyNamingStrategy) null, TypeFactory.defaultInstance(), (TypeResolverBuilder<?>) null, StdDateFormat.instance, (HandlerInstantiator) null, Locale.getDefault(), TimeZone.getTimeZone("GMT"), Base64Variants.getDefaultVariant());
    }

    public ObjectMapper() {
        this((JsonFactory) null, (DefaultSerializerProvider) null, (DefaultDeserializationContext) null);
    }

    public ObjectMapper(JsonFactory jsonFactory) {
        this(jsonFactory, (DefaultSerializerProvider) null, (DefaultDeserializationContext) null);
    }

    protected ObjectMapper(ObjectMapper objectMapper) {
        this._mixInAnnotations = new HashMap<>();
        this._rootDeserializers = new ConcurrentHashMap<>(64, 0.6f, 2);
        JsonFactory copy = objectMapper._jsonFactory.copy();
        this._jsonFactory = copy;
        copy.setCodec(this);
        this._subtypeResolver = objectMapper._subtypeResolver;
        this._rootNames = new RootNameLookup();
        this._typeFactory = objectMapper._typeFactory;
        this._serializationConfig = objectMapper._serializationConfig;
        HashMap hashMap = new HashMap(objectMapper._mixInAnnotations);
        this._serializationConfig = new SerializationConfig(objectMapper._serializationConfig, (Map<ClassKey, Class<?>>) hashMap);
        this._deserializationConfig = new DeserializationConfig(objectMapper._deserializationConfig, (Map<ClassKey, Class<?>>) hashMap);
        this._serializerProvider = objectMapper._serializerProvider;
        this._deserializationContext = objectMapper._deserializationContext;
        this._serializerFactory = objectMapper._serializerFactory;
    }

    public ObjectMapper(JsonFactory jsonFactory, DefaultSerializerProvider defaultSerializerProvider, DefaultDeserializationContext defaultDeserializationContext) {
        HashMap<ClassKey, Class<?>> hashMap = new HashMap<>();
        this._mixInAnnotations = hashMap;
        this._rootDeserializers = new ConcurrentHashMap<>(64, 0.6f, 2);
        if (jsonFactory == null) {
            this._jsonFactory = new MappingJsonFactory(this);
        } else {
            this._jsonFactory = jsonFactory;
            if (jsonFactory.getCodec() == null) {
                jsonFactory.setCodec(this);
            }
        }
        this._subtypeResolver = new StdSubtypeResolver();
        this._rootNames = new RootNameLookup();
        this._typeFactory = TypeFactory.defaultInstance();
        BaseSettings baseSettings = DEFAULT_BASE;
        this._serializationConfig = new SerializationConfig(baseSettings, this._subtypeResolver, (Map<ClassKey, Class<?>>) hashMap);
        this._deserializationConfig = new DeserializationConfig(baseSettings, this._subtypeResolver, (Map<ClassKey, Class<?>>) hashMap);
        this._serializerProvider = defaultSerializerProvider == null ? new DefaultSerializerProvider.Impl() : defaultSerializerProvider;
        this._deserializationContext = defaultDeserializationContext == null ? new DefaultDeserializationContext.Impl(BeanDeserializerFactory.instance) : defaultDeserializationContext;
        this._serializerFactory = BeanSerializerFactory.instance;
    }

    public ObjectMapper copy() {
        _checkInvalidCopy(ObjectMapper.class);
        return new ObjectMapper(this);
    }

    /* access modifiers changed from: protected */
    public void _checkInvalidCopy(Class<?> cls) {
        if (getClass() != cls) {
            throw new IllegalStateException("Failed copy(): " + getClass().getName() + " (version: " + version() + ") does not override copy(); it has to");
        }
    }

    public Version version() {
        return PackageVersion.VERSION;
    }

    public ObjectMapper registerModule(Module module) {
        if (module.getModuleName() == null) {
            throw new IllegalArgumentException("Module without defined name");
        } else if (module.version() != null) {
            module.setupModule(new Module.SetupContext() {
                public Version getMapperVersion() {
                    return ObjectMapper.this.version();
                }

                public <C extends ObjectCodec> C getOwner() {
                    return this;
                }

                public TypeFactory getTypeFactory() {
                    return ObjectMapper.this._typeFactory;
                }

                public boolean isEnabled(MapperFeature mapperFeature) {
                    return this.isEnabled(mapperFeature);
                }

                public boolean isEnabled(DeserializationFeature deserializationFeature) {
                    return this.isEnabled(deserializationFeature);
                }

                public boolean isEnabled(SerializationFeature serializationFeature) {
                    return this.isEnabled(serializationFeature);
                }

                public boolean isEnabled(JsonFactory.Feature feature) {
                    return this.isEnabled(feature);
                }

                public boolean isEnabled(JsonParser.Feature feature) {
                    return this.isEnabled(feature);
                }

                public boolean isEnabled(JsonGenerator.Feature feature) {
                    return this.isEnabled(feature);
                }

                public void addDeserializers(Deserializers deserializers) {
                    DeserializerFactory withAdditionalDeserializers = this._deserializationContext._factory.withAdditionalDeserializers(deserializers);
                    ObjectMapper objectMapper = this;
                    objectMapper._deserializationContext = objectMapper._deserializationContext.with(withAdditionalDeserializers);
                }

                public void addKeyDeserializers(KeyDeserializers keyDeserializers) {
                    DeserializerFactory withAdditionalKeyDeserializers = this._deserializationContext._factory.withAdditionalKeyDeserializers(keyDeserializers);
                    ObjectMapper objectMapper = this;
                    objectMapper._deserializationContext = objectMapper._deserializationContext.with(withAdditionalKeyDeserializers);
                }

                public void addBeanDeserializerModifier(BeanDeserializerModifier beanDeserializerModifier) {
                    DeserializerFactory withDeserializerModifier = this._deserializationContext._factory.withDeserializerModifier(beanDeserializerModifier);
                    ObjectMapper objectMapper = this;
                    objectMapper._deserializationContext = objectMapper._deserializationContext.with(withDeserializerModifier);
                }

                public void addSerializers(Serializers serializers) {
                    ObjectMapper objectMapper = this;
                    objectMapper._serializerFactory = objectMapper._serializerFactory.withAdditionalSerializers(serializers);
                }

                public void addKeySerializers(Serializers serializers) {
                    ObjectMapper objectMapper = this;
                    objectMapper._serializerFactory = objectMapper._serializerFactory.withAdditionalKeySerializers(serializers);
                }

                public void addBeanSerializerModifier(BeanSerializerModifier beanSerializerModifier) {
                    ObjectMapper objectMapper = this;
                    objectMapper._serializerFactory = objectMapper._serializerFactory.withSerializerModifier(beanSerializerModifier);
                }

                public void addAbstractTypeResolver(AbstractTypeResolver abstractTypeResolver) {
                    DeserializerFactory withAbstractTypeResolver = this._deserializationContext._factory.withAbstractTypeResolver(abstractTypeResolver);
                    ObjectMapper objectMapper = this;
                    objectMapper._deserializationContext = objectMapper._deserializationContext.with(withAbstractTypeResolver);
                }

                public void addTypeModifier(TypeModifier typeModifier) {
                    this.setTypeFactory(this._typeFactory.withModifier(typeModifier));
                }

                public void addValueInstantiators(ValueInstantiators valueInstantiators) {
                    DeserializerFactory withValueInstantiators = this._deserializationContext._factory.withValueInstantiators(valueInstantiators);
                    ObjectMapper objectMapper = this;
                    objectMapper._deserializationContext = objectMapper._deserializationContext.with(withValueInstantiators);
                }

                public void setClassIntrospector(ClassIntrospector classIntrospector) {
                    ObjectMapper objectMapper = this;
                    objectMapper._deserializationConfig = objectMapper._deserializationConfig.with(classIntrospector);
                    ObjectMapper objectMapper2 = this;
                    objectMapper2._serializationConfig = objectMapper2._serializationConfig.with(classIntrospector);
                }

                public void insertAnnotationIntrospector(AnnotationIntrospector annotationIntrospector) {
                    ObjectMapper objectMapper = this;
                    objectMapper._deserializationConfig = objectMapper._deserializationConfig.withInsertedAnnotationIntrospector(annotationIntrospector);
                    ObjectMapper objectMapper2 = this;
                    objectMapper2._serializationConfig = objectMapper2._serializationConfig.withInsertedAnnotationIntrospector(annotationIntrospector);
                }

                public void appendAnnotationIntrospector(AnnotationIntrospector annotationIntrospector) {
                    ObjectMapper objectMapper = this;
                    objectMapper._deserializationConfig = objectMapper._deserializationConfig.withAppendedAnnotationIntrospector(annotationIntrospector);
                    ObjectMapper objectMapper2 = this;
                    objectMapper2._serializationConfig = objectMapper2._serializationConfig.withAppendedAnnotationIntrospector(annotationIntrospector);
                }

                public void registerSubtypes(Class<?>... clsArr) {
                    this.registerSubtypes(clsArr);
                }

                public void registerSubtypes(NamedType... namedTypeArr) {
                    this.registerSubtypes(namedTypeArr);
                }

                public void setMixInAnnotations(Class<?> cls, Class<?> cls2) {
                    this.addMixInAnnotations(cls, cls2);
                }

                public void addDeserializationProblemHandler(DeserializationProblemHandler deserializationProblemHandler) {
                    this.addHandler(deserializationProblemHandler);
                }
            });
            return this;
        } else {
            throw new IllegalArgumentException("Module without defined version");
        }
    }

    public ObjectMapper registerModules(Module... moduleArr) {
        for (Module registerModule : moduleArr) {
            registerModule(registerModule);
        }
        return this;
    }

    public ObjectMapper registerModules(Iterable<Module> iterable) {
        for (Module registerModule : iterable) {
            registerModule(registerModule);
        }
        return this;
    }

    public static List<Module> findModules() {
        return findModules((ClassLoader) null);
    }

    public static List<Module> findModules(ClassLoader classLoader) {
        ArrayList arrayList = new ArrayList();
        Iterator<S> it = (classLoader == null ? ServiceLoader.load(Module.class) : ServiceLoader.load(Module.class, classLoader)).iterator();
        while (it.hasNext()) {
            arrayList.add((Module) it.next());
        }
        return arrayList;
    }

    public ObjectMapper findAndRegisterModules() {
        return registerModules((Iterable<Module>) findModules());
    }

    public SerializationConfig getSerializationConfig() {
        return this._serializationConfig;
    }

    public DeserializationConfig getDeserializationConfig() {
        return this._deserializationConfig;
    }

    public DeserializationContext getDeserializationContext() {
        return this._deserializationContext;
    }

    public ObjectMapper setSerializerFactory(SerializerFactory serializerFactory) {
        this._serializerFactory = serializerFactory;
        return this;
    }

    public SerializerFactory getSerializerFactory() {
        return this._serializerFactory;
    }

    public ObjectMapper setSerializerProvider(DefaultSerializerProvider defaultSerializerProvider) {
        this._serializerProvider = defaultSerializerProvider;
        return this;
    }

    public SerializerProvider getSerializerProvider() {
        return this._serializerProvider;
    }

    public final void setMixInAnnotations(Map<Class<?>, Class<?>> map) {
        this._mixInAnnotations.clear();
        if (map != null && map.size() > 0) {
            for (Map.Entry next : map.entrySet()) {
                this._mixInAnnotations.put(new ClassKey((Class) next.getKey()), next.getValue());
            }
        }
    }

    public final void addMixInAnnotations(Class<?> cls, Class<?> cls2) {
        this._mixInAnnotations.put(new ClassKey(cls), cls2);
    }

    public final Class<?> findMixInClassFor(Class<?> cls) {
        HashMap<ClassKey, Class<?>> hashMap = this._mixInAnnotations;
        if (hashMap == null) {
            return null;
        }
        return hashMap.get(new ClassKey(cls));
    }

    public final int mixInCount() {
        HashMap<ClassKey, Class<?>> hashMap = this._mixInAnnotations;
        if (hashMap == null) {
            return 0;
        }
        return hashMap.size();
    }

    public VisibilityChecker<?> getVisibilityChecker() {
        return this._serializationConfig.getDefaultVisibilityChecker();
    }

    public void setVisibilityChecker(VisibilityChecker<?> visibilityChecker) {
        this._deserializationConfig = this._deserializationConfig.with(visibilityChecker);
        this._serializationConfig = this._serializationConfig.with(visibilityChecker);
    }

    public ObjectMapper setVisibility(PropertyAccessor propertyAccessor, JsonAutoDetect.Visibility visibility) {
        this._deserializationConfig = this._deserializationConfig.withVisibility(propertyAccessor, visibility);
        this._serializationConfig = this._serializationConfig.withVisibility(propertyAccessor, visibility);
        return this;
    }

    public SubtypeResolver getSubtypeResolver() {
        return this._subtypeResolver;
    }

    public ObjectMapper setSubtypeResolver(SubtypeResolver subtypeResolver) {
        this._subtypeResolver = subtypeResolver;
        this._deserializationConfig = this._deserializationConfig.with(subtypeResolver);
        this._serializationConfig = this._serializationConfig.with(subtypeResolver);
        return this;
    }

    public ObjectMapper setAnnotationIntrospector(AnnotationIntrospector annotationIntrospector) {
        this._serializationConfig = this._serializationConfig.with(annotationIntrospector);
        this._deserializationConfig = this._deserializationConfig.with(annotationIntrospector);
        return this;
    }

    public ObjectMapper setAnnotationIntrospectors(AnnotationIntrospector annotationIntrospector, AnnotationIntrospector annotationIntrospector2) {
        this._serializationConfig = this._serializationConfig.with(annotationIntrospector);
        this._deserializationConfig = this._deserializationConfig.with(annotationIntrospector2);
        return this;
    }

    public ObjectMapper setPropertyNamingStrategy(PropertyNamingStrategy propertyNamingStrategy) {
        this._serializationConfig = this._serializationConfig.with(propertyNamingStrategy);
        this._deserializationConfig = this._deserializationConfig.with(propertyNamingStrategy);
        return this;
    }

    public ObjectMapper setSerializationInclusion(JsonInclude.Include include) {
        this._serializationConfig = this._serializationConfig.withSerializationInclusion(include);
        return this;
    }

    public ObjectMapper enableDefaultTyping() {
        return enableDefaultTyping(DefaultTyping.OBJECT_AND_NON_CONCRETE);
    }

    public ObjectMapper enableDefaultTyping(DefaultTyping defaultTyping) {
        return enableDefaultTyping(defaultTyping, JsonTypeInfo.C0788As.WRAPPER_ARRAY);
    }

    public ObjectMapper enableDefaultTyping(DefaultTyping defaultTyping, JsonTypeInfo.C0788As as) {
        return setDefaultTyping(new DefaultTypeResolverBuilder(defaultTyping).init(JsonTypeInfo.C0789Id.CLASS, (TypeIdResolver) null).inclusion(as));
    }

    public ObjectMapper enableDefaultTypingAsProperty(DefaultTyping defaultTyping, String str) {
        return setDefaultTyping(new DefaultTypeResolverBuilder(defaultTyping).init(JsonTypeInfo.C0789Id.CLASS, (TypeIdResolver) null).inclusion(JsonTypeInfo.C0788As.PROPERTY).typeProperty(str));
    }

    public ObjectMapper disableDefaultTyping() {
        return setDefaultTyping((TypeResolverBuilder<?>) null);
    }

    public ObjectMapper setDefaultTyping(TypeResolverBuilder<?> typeResolverBuilder) {
        this._deserializationConfig = this._deserializationConfig.with(typeResolverBuilder);
        this._serializationConfig = this._serializationConfig.with(typeResolverBuilder);
        return this;
    }

    public void registerSubtypes(Class<?>... clsArr) {
        getSubtypeResolver().registerSubtypes(clsArr);
    }

    public void registerSubtypes(NamedType... namedTypeArr) {
        getSubtypeResolver().registerSubtypes(namedTypeArr);
    }

    public TypeFactory getTypeFactory() {
        return this._typeFactory;
    }

    public ObjectMapper setTypeFactory(TypeFactory typeFactory) {
        this._typeFactory = typeFactory;
        this._deserializationConfig = this._deserializationConfig.with(typeFactory);
        this._serializationConfig = this._serializationConfig.with(typeFactory);
        return this;
    }

    public JavaType constructType(Type type) {
        return this._typeFactory.constructType(type);
    }

    public ObjectMapper setNodeFactory(JsonNodeFactory jsonNodeFactory) {
        this._deserializationConfig = this._deserializationConfig.with(jsonNodeFactory);
        return this;
    }

    public ObjectMapper addHandler(DeserializationProblemHandler deserializationProblemHandler) {
        this._deserializationConfig = this._deserializationConfig.withHandler(deserializationProblemHandler);
        return this;
    }

    public ObjectMapper clearProblemHandlers() {
        this._deserializationConfig = this._deserializationConfig.withNoProblemHandlers();
        return this;
    }

    public void setFilters(FilterProvider filterProvider) {
        this._serializationConfig = this._serializationConfig.withFilters(filterProvider);
    }

    public ObjectMapper setBase64Variant(Base64Variant base64Variant) {
        this._serializationConfig = this._serializationConfig.with(base64Variant);
        this._deserializationConfig = this._deserializationConfig.with(base64Variant);
        return this;
    }

    public JsonFactory getFactory() {
        return this._jsonFactory;
    }

    @Deprecated
    public JsonFactory getJsonFactory() {
        return this._jsonFactory;
    }

    public ObjectMapper setDateFormat(DateFormat dateFormat) {
        this._deserializationConfig = this._deserializationConfig.with(dateFormat);
        this._serializationConfig = this._serializationConfig.with(dateFormat);
        return this;
    }

    public Object setHandlerInstantiator(HandlerInstantiator handlerInstantiator) {
        this._deserializationConfig = this._deserializationConfig.with(handlerInstantiator);
        this._serializationConfig = this._serializationConfig.with(handlerInstantiator);
        return this;
    }

    public ObjectMapper setInjectableValues(InjectableValues injectableValues) {
        this._injectableValues = injectableValues;
        return this;
    }

    public ObjectMapper setLocale(Locale locale) {
        this._deserializationConfig = this._deserializationConfig.with(locale);
        this._serializationConfig = this._serializationConfig.with(locale);
        return this;
    }

    public ObjectMapper setTimeZone(TimeZone timeZone) {
        this._deserializationConfig = this._deserializationConfig.with(timeZone);
        this._serializationConfig = this._serializationConfig.with(timeZone);
        return this;
    }

    public ObjectMapper configure(MapperFeature mapperFeature, boolean z) {
        SerializationConfig serializationConfig;
        DeserializationConfig deserializationConfig;
        SerializationConfig serializationConfig2 = this._serializationConfig;
        MapperFeature[] mapperFeatureArr = new MapperFeature[1];
        if (z) {
            mapperFeatureArr[0] = mapperFeature;
            serializationConfig = serializationConfig2.with(mapperFeatureArr);
        } else {
            mapperFeatureArr[0] = mapperFeature;
            serializationConfig = serializationConfig2.without(mapperFeatureArr);
        }
        this._serializationConfig = serializationConfig;
        if (z) {
            deserializationConfig = this._deserializationConfig.with(mapperFeature);
        } else {
            deserializationConfig = this._deserializationConfig.without(mapperFeature);
        }
        this._deserializationConfig = deserializationConfig;
        return this;
    }

    public ObjectMapper configure(SerializationFeature serializationFeature, boolean z) {
        this._serializationConfig = z ? this._serializationConfig.with(serializationFeature) : this._serializationConfig.without(serializationFeature);
        return this;
    }

    public ObjectMapper configure(DeserializationFeature deserializationFeature, boolean z) {
        this._deserializationConfig = z ? this._deserializationConfig.with(deserializationFeature) : this._deserializationConfig.without(deserializationFeature);
        return this;
    }

    public ObjectMapper configure(JsonParser.Feature feature, boolean z) {
        this._jsonFactory.configure(feature, z);
        return this;
    }

    public ObjectMapper configure(JsonGenerator.Feature feature, boolean z) {
        this._jsonFactory.configure(feature, z);
        return this;
    }

    public ObjectMapper enable(MapperFeature... mapperFeatureArr) {
        this._deserializationConfig = this._deserializationConfig.with(mapperFeatureArr);
        this._serializationConfig = this._serializationConfig.with(mapperFeatureArr);
        return this;
    }

    public ObjectMapper disable(MapperFeature... mapperFeatureArr) {
        this._deserializationConfig = this._deserializationConfig.without(mapperFeatureArr);
        this._serializationConfig = this._serializationConfig.without(mapperFeatureArr);
        return this;
    }

    public ObjectMapper enable(DeserializationFeature deserializationFeature) {
        this._deserializationConfig = this._deserializationConfig.with(deserializationFeature);
        return this;
    }

    public ObjectMapper enable(DeserializationFeature deserializationFeature, DeserializationFeature... deserializationFeatureArr) {
        this._deserializationConfig = this._deserializationConfig.with(deserializationFeature, deserializationFeatureArr);
        return this;
    }

    public ObjectMapper disable(DeserializationFeature deserializationFeature) {
        this._deserializationConfig = this._deserializationConfig.without(deserializationFeature);
        return this;
    }

    public ObjectMapper disable(DeserializationFeature deserializationFeature, DeserializationFeature... deserializationFeatureArr) {
        this._deserializationConfig = this._deserializationConfig.without(deserializationFeature, deserializationFeatureArr);
        return this;
    }

    public ObjectMapper enable(SerializationFeature serializationFeature) {
        this._serializationConfig = this._serializationConfig.with(serializationFeature);
        return this;
    }

    public ObjectMapper enable(SerializationFeature serializationFeature, SerializationFeature... serializationFeatureArr) {
        this._serializationConfig = this._serializationConfig.with(serializationFeature, serializationFeatureArr);
        return this;
    }

    public ObjectMapper disable(SerializationFeature serializationFeature) {
        this._serializationConfig = this._serializationConfig.without(serializationFeature);
        return this;
    }

    public ObjectMapper disable(SerializationFeature serializationFeature, SerializationFeature... serializationFeatureArr) {
        this._serializationConfig = this._serializationConfig.without(serializationFeature, serializationFeatureArr);
        return this;
    }

    public boolean isEnabled(MapperFeature mapperFeature) {
        return this._serializationConfig.isEnabled(mapperFeature);
    }

    public boolean isEnabled(SerializationFeature serializationFeature) {
        return this._serializationConfig.isEnabled(serializationFeature);
    }

    public boolean isEnabled(DeserializationFeature deserializationFeature) {
        return this._deserializationConfig.isEnabled(deserializationFeature);
    }

    public boolean isEnabled(JsonFactory.Feature feature) {
        return this._jsonFactory.isEnabled(feature);
    }

    public boolean isEnabled(JsonParser.Feature feature) {
        return this._jsonFactory.isEnabled(feature);
    }

    public boolean isEnabled(JsonGenerator.Feature feature) {
        return this._jsonFactory.isEnabled(feature);
    }

    public JsonNodeFactory getNodeFactory() {
        return this._deserializationConfig.getNodeFactory();
    }

    public <T> T readValue(JsonParser jsonParser, Class<T> cls) throws IOException, JsonParseException, JsonMappingException {
        return _readValue(getDeserializationConfig(), jsonParser, this._typeFactory.constructType((Type) cls));
    }

    public <T> T readValue(JsonParser jsonParser, TypeReference<?> typeReference) throws IOException, JsonParseException, JsonMappingException {
        return _readValue(getDeserializationConfig(), jsonParser, this._typeFactory.constructType(typeReference));
    }

    public final <T> T readValue(JsonParser jsonParser, ResolvedType resolvedType) throws IOException, JsonParseException, JsonMappingException {
        return _readValue(getDeserializationConfig(), jsonParser, (JavaType) resolvedType);
    }

    public <T> T readValue(JsonParser jsonParser, JavaType javaType) throws IOException, JsonParseException, JsonMappingException {
        return _readValue(getDeserializationConfig(), jsonParser, javaType);
    }

    public <T extends TreeNode> T readTree(JsonParser jsonParser) throws IOException, JsonProcessingException {
        DeserializationConfig deserializationConfig = getDeserializationConfig();
        if (jsonParser.getCurrentToken() == null && jsonParser.nextToken() == null) {
            return null;
        }
        T t = (JsonNode) _readValue(deserializationConfig, jsonParser, JSON_NODE_TYPE);
        if (t == null) {
            return getNodeFactory().nullNode();
        }
        return t;
    }

    public <T> MappingIterator<T> readValues(JsonParser jsonParser, ResolvedType resolvedType) throws IOException, JsonProcessingException {
        return readValues(jsonParser, (JavaType) resolvedType);
    }

    public <T> MappingIterator<T> readValues(JsonParser jsonParser, JavaType javaType) throws IOException, JsonProcessingException {
        DefaultDeserializationContext createDeserializationContext = createDeserializationContext(jsonParser, getDeserializationConfig());
        return new MappingIterator(javaType, jsonParser, createDeserializationContext, _findRootDeserializer(createDeserializationContext, javaType), false, (Object) null);
    }

    public <T> MappingIterator<T> readValues(JsonParser jsonParser, Class<T> cls) throws IOException, JsonProcessingException {
        return readValues(jsonParser, this._typeFactory.constructType((Type) cls));
    }

    public <T> MappingIterator<T> readValues(JsonParser jsonParser, TypeReference<?> typeReference) throws IOException, JsonProcessingException {
        return readValues(jsonParser, this._typeFactory.constructType(typeReference));
    }

    public JsonNode readTree(InputStream inputStream) throws IOException, JsonProcessingException {
        JsonNode jsonNode = (JsonNode) _readMapAndClose(this._jsonFactory.createParser(inputStream), JSON_NODE_TYPE);
        return jsonNode == null ? NullNode.instance : jsonNode;
    }

    public JsonNode readTree(Reader reader) throws IOException, JsonProcessingException {
        JsonNode jsonNode = (JsonNode) _readMapAndClose(this._jsonFactory.createParser(reader), JSON_NODE_TYPE);
        return jsonNode == null ? NullNode.instance : jsonNode;
    }

    public JsonNode readTree(String str) throws IOException, JsonProcessingException {
        JsonNode jsonNode = (JsonNode) _readMapAndClose(this._jsonFactory.createParser(str), JSON_NODE_TYPE);
        return jsonNode == null ? NullNode.instance : jsonNode;
    }

    public JsonNode readTree(byte[] bArr) throws IOException, JsonProcessingException {
        JsonNode jsonNode = (JsonNode) _readMapAndClose(this._jsonFactory.createParser(bArr), JSON_NODE_TYPE);
        return jsonNode == null ? NullNode.instance : jsonNode;
    }

    public JsonNode readTree(File file) throws IOException, JsonProcessingException {
        JsonNode jsonNode = (JsonNode) _readMapAndClose(this._jsonFactory.createParser(file), JSON_NODE_TYPE);
        return jsonNode == null ? NullNode.instance : jsonNode;
    }

    public JsonNode readTree(URL url) throws IOException, JsonProcessingException {
        JsonNode jsonNode = (JsonNode) _readMapAndClose(this._jsonFactory.createParser(url), JSON_NODE_TYPE);
        return jsonNode == null ? NullNode.instance : jsonNode;
    }

    public void writeValue(JsonGenerator jsonGenerator, Object obj) throws IOException, JsonGenerationException, JsonMappingException {
        SerializationConfig serializationConfig = getSerializationConfig();
        if (serializationConfig.isEnabled(SerializationFeature.INDENT_OUTPUT)) {
            jsonGenerator.useDefaultPrettyPrinter();
        }
        if (!serializationConfig.isEnabled(SerializationFeature.CLOSE_CLOSEABLE) || !(obj instanceof Closeable)) {
            _serializerProvider(serializationConfig).serializeValue(jsonGenerator, obj);
            if (serializationConfig.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
                jsonGenerator.flush();
                return;
            }
            return;
        }
        _writeCloseableValue(jsonGenerator, obj, serializationConfig);
    }

    public void writeTree(JsonGenerator jsonGenerator, JsonNode jsonNode) throws IOException, JsonProcessingException {
        SerializationConfig serializationConfig = getSerializationConfig();
        _serializerProvider(serializationConfig).serializeValue(jsonGenerator, jsonNode);
        if (serializationConfig.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
            jsonGenerator.flush();
        }
    }

    public ObjectNode createObjectNode() {
        return this._deserializationConfig.getNodeFactory().objectNode();
    }

    public ArrayNode createArrayNode() {
        return this._deserializationConfig.getNodeFactory().arrayNode();
    }

    public JsonParser treeAsTokens(TreeNode treeNode) {
        return new TreeTraversingParser((JsonNode) treeNode, this);
    }

    public <T> T treeToValue(TreeNode treeNode, Class<T> cls) throws JsonProcessingException {
        if (cls != Object.class) {
            try {
                if (cls.isAssignableFrom(treeNode.getClass())) {
                    return treeNode;
                }
            } catch (JsonProcessingException e) {
                throw e;
            } catch (IOException e2) {
                throw new IllegalArgumentException(e2.getMessage(), e2);
            }
        }
        return readValue(treeAsTokens(treeNode), cls);
    }

    public <T extends JsonNode> T valueToTree(Object obj) throws IllegalArgumentException {
        if (obj == null) {
            return null;
        }
        TokenBuffer tokenBuffer = new TokenBuffer(this);
        try {
            writeValue((JsonGenerator) tokenBuffer, obj);
            JsonParser asParser = tokenBuffer.asParser();
            T t = (JsonNode) readTree(asParser);
            asParser.close();
            return t;
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public boolean canSerialize(Class<?> cls) {
        return _serializerProvider(getSerializationConfig()).hasSerializerFor(cls);
    }

    public boolean canDeserialize(JavaType javaType) {
        return createDeserializationContext((JsonParser) null, getDeserializationConfig()).hasValueDeserializerFor(javaType);
    }

    public <T> T readValue(File file, Class<T> cls) throws IOException, JsonParseException, JsonMappingException {
        return _readMapAndClose(this._jsonFactory.createParser(file), this._typeFactory.constructType((Type) cls));
    }

    public <T> T readValue(File file, TypeReference typeReference) throws IOException, JsonParseException, JsonMappingException {
        return _readMapAndClose(this._jsonFactory.createParser(file), this._typeFactory.constructType((TypeReference<?>) typeReference));
    }

    public <T> T readValue(File file, JavaType javaType) throws IOException, JsonParseException, JsonMappingException {
        return _readMapAndClose(this._jsonFactory.createParser(file), javaType);
    }

    public <T> T readValue(URL url, Class<T> cls) throws IOException, JsonParseException, JsonMappingException {
        return _readMapAndClose(this._jsonFactory.createParser(url), this._typeFactory.constructType((Type) cls));
    }

    public <T> T readValue(URL url, TypeReference typeReference) throws IOException, JsonParseException, JsonMappingException {
        return _readMapAndClose(this._jsonFactory.createParser(url), this._typeFactory.constructType((TypeReference<?>) typeReference));
    }

    public <T> T readValue(URL url, JavaType javaType) throws IOException, JsonParseException, JsonMappingException {
        return _readMapAndClose(this._jsonFactory.createParser(url), javaType);
    }

    public <T> T readValue(String str, Class<T> cls) throws IOException, JsonParseException, JsonMappingException {
        return _readMapAndClose(this._jsonFactory.createParser(str), this._typeFactory.constructType((Type) cls));
    }

    public <T> T readValue(String str, TypeReference typeReference) throws IOException, JsonParseException, JsonMappingException {
        return _readMapAndClose(this._jsonFactory.createParser(str), this._typeFactory.constructType((TypeReference<?>) typeReference));
    }

    public <T> T readValue(String str, JavaType javaType) throws IOException, JsonParseException, JsonMappingException {
        return _readMapAndClose(this._jsonFactory.createParser(str), javaType);
    }

    public <T> T readValue(Reader reader, Class<T> cls) throws IOException, JsonParseException, JsonMappingException {
        return _readMapAndClose(this._jsonFactory.createParser(reader), this._typeFactory.constructType((Type) cls));
    }

    public <T> T readValue(Reader reader, TypeReference typeReference) throws IOException, JsonParseException, JsonMappingException {
        return _readMapAndClose(this._jsonFactory.createParser(reader), this._typeFactory.constructType((TypeReference<?>) typeReference));
    }

    public <T> T readValue(Reader reader, JavaType javaType) throws IOException, JsonParseException, JsonMappingException {
        return _readMapAndClose(this._jsonFactory.createParser(reader), javaType);
    }

    public <T> T readValue(InputStream inputStream, Class<T> cls) throws IOException, JsonParseException, JsonMappingException {
        return _readMapAndClose(this._jsonFactory.createParser(inputStream), this._typeFactory.constructType((Type) cls));
    }

    public <T> T readValue(InputStream inputStream, TypeReference typeReference) throws IOException, JsonParseException, JsonMappingException {
        return _readMapAndClose(this._jsonFactory.createParser(inputStream), this._typeFactory.constructType((TypeReference<?>) typeReference));
    }

    public <T> T readValue(InputStream inputStream, JavaType javaType) throws IOException, JsonParseException, JsonMappingException {
        return _readMapAndClose(this._jsonFactory.createParser(inputStream), javaType);
    }

    public <T> T readValue(byte[] bArr, Class<T> cls) throws IOException, JsonParseException, JsonMappingException {
        return _readMapAndClose(this._jsonFactory.createParser(bArr), this._typeFactory.constructType((Type) cls));
    }

    public <T> T readValue(byte[] bArr, int i, int i2, Class<T> cls) throws IOException, JsonParseException, JsonMappingException {
        return _readMapAndClose(this._jsonFactory.createParser(bArr, i, i2), this._typeFactory.constructType((Type) cls));
    }

    public <T> T readValue(byte[] bArr, TypeReference typeReference) throws IOException, JsonParseException, JsonMappingException {
        return _readMapAndClose(this._jsonFactory.createParser(bArr), this._typeFactory.constructType((TypeReference<?>) typeReference));
    }

    public <T> T readValue(byte[] bArr, int i, int i2, TypeReference typeReference) throws IOException, JsonParseException, JsonMappingException {
        return _readMapAndClose(this._jsonFactory.createParser(bArr, i, i2), this._typeFactory.constructType((TypeReference<?>) typeReference));
    }

    public <T> T readValue(byte[] bArr, JavaType javaType) throws IOException, JsonParseException, JsonMappingException {
        return _readMapAndClose(this._jsonFactory.createParser(bArr), javaType);
    }

    public <T> T readValue(byte[] bArr, int i, int i2, JavaType javaType) throws IOException, JsonParseException, JsonMappingException {
        return _readMapAndClose(this._jsonFactory.createParser(bArr, i, i2), javaType);
    }

    public void writeValue(File file, Object obj) throws IOException, JsonGenerationException, JsonMappingException {
        _configAndWriteValue(this._jsonFactory.createGenerator(file, JsonEncoding.UTF8), obj);
    }

    public void writeValue(OutputStream outputStream, Object obj) throws IOException, JsonGenerationException, JsonMappingException {
        _configAndWriteValue(this._jsonFactory.createGenerator(outputStream, JsonEncoding.UTF8), obj);
    }

    public void writeValue(Writer writer, Object obj) throws IOException, JsonGenerationException, JsonMappingException {
        _configAndWriteValue(this._jsonFactory.createGenerator(writer), obj);
    }

    public String writeValueAsString(Object obj) throws JsonProcessingException {
        SegmentedStringWriter segmentedStringWriter = new SegmentedStringWriter(this._jsonFactory._getBufferRecycler());
        try {
            _configAndWriteValue(this._jsonFactory.createGenerator((Writer) segmentedStringWriter), obj);
            return segmentedStringWriter.getAndClear();
        } catch (JsonProcessingException e) {
            throw e;
        } catch (IOException e2) {
            throw JsonMappingException.fromUnexpectedIOE(e2);
        }
    }

    public byte[] writeValueAsBytes(Object obj) throws JsonProcessingException {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder(this._jsonFactory._getBufferRecycler());
        try {
            _configAndWriteValue(this._jsonFactory.createGenerator((OutputStream) byteArrayBuilder, JsonEncoding.UTF8), obj);
            byte[] byteArray = byteArrayBuilder.toByteArray();
            byteArrayBuilder.release();
            return byteArray;
        } catch (JsonProcessingException e) {
            throw e;
        } catch (IOException e2) {
            throw JsonMappingException.fromUnexpectedIOE(e2);
        }
    }

    public ObjectWriter writer() {
        return new ObjectWriter(this, getSerializationConfig());
    }

    public ObjectWriter writer(SerializationFeature serializationFeature) {
        return new ObjectWriter(this, getSerializationConfig().with(serializationFeature));
    }

    public ObjectWriter writer(SerializationFeature serializationFeature, SerializationFeature... serializationFeatureArr) {
        return new ObjectWriter(this, getSerializationConfig().with(serializationFeature, serializationFeatureArr));
    }

    public ObjectWriter writer(DateFormat dateFormat) {
        return new ObjectWriter(this, getSerializationConfig().with(dateFormat));
    }

    public ObjectWriter writerWithView(Class<?> cls) {
        return new ObjectWriter(this, getSerializationConfig().withView(cls));
    }

    public ObjectWriter writerWithType(Class<?> cls) {
        return new ObjectWriter(this, getSerializationConfig(), cls == null ? null : this._typeFactory.constructType((Type) cls), (PrettyPrinter) null);
    }

    public ObjectWriter writerWithType(TypeReference<?> typeReference) {
        return new ObjectWriter(this, getSerializationConfig(), typeReference == null ? null : this._typeFactory.constructType(typeReference), (PrettyPrinter) null);
    }

    public ObjectWriter writerWithType(JavaType javaType) {
        return new ObjectWriter(this, getSerializationConfig(), javaType, (PrettyPrinter) null);
    }

    public ObjectWriter writer(PrettyPrinter prettyPrinter) {
        if (prettyPrinter == null) {
            prettyPrinter = ObjectWriter.NULL_PRETTY_PRINTER;
        }
        return new ObjectWriter(this, getSerializationConfig(), (JavaType) null, prettyPrinter);
    }

    public ObjectWriter writerWithDefaultPrettyPrinter() {
        return new ObjectWriter(this, getSerializationConfig(), (JavaType) null, _defaultPrettyPrinter());
    }

    public ObjectWriter writer(FilterProvider filterProvider) {
        return new ObjectWriter(this, getSerializationConfig().withFilters(filterProvider));
    }

    public ObjectWriter writer(FormatSchema formatSchema) {
        _verifySchemaType(formatSchema);
        return new ObjectWriter(this, getSerializationConfig(), formatSchema);
    }

    public ObjectWriter writer(Base64Variant base64Variant) {
        return new ObjectWriter(this, getSerializationConfig().with(base64Variant));
    }

    public ObjectReader reader() {
        return new ObjectReader(this, getDeserializationConfig()).with(this._injectableValues);
    }

    public ObjectReader reader(DeserializationFeature deserializationFeature) {
        return new ObjectReader(this, getDeserializationConfig().with(deserializationFeature));
    }

    public ObjectReader reader(DeserializationFeature deserializationFeature, DeserializationFeature... deserializationFeatureArr) {
        return new ObjectReader(this, getDeserializationConfig().with(deserializationFeature, deserializationFeatureArr));
    }

    public ObjectReader readerForUpdating(Object obj) {
        return new ObjectReader(this, getDeserializationConfig(), this._typeFactory.constructType((Type) obj.getClass()), obj, (FormatSchema) null, this._injectableValues);
    }

    public ObjectReader reader(JavaType javaType) {
        return new ObjectReader(this, getDeserializationConfig(), javaType, (Object) null, (FormatSchema) null, this._injectableValues);
    }

    public ObjectReader reader(Class<?> cls) {
        return reader(this._typeFactory.constructType((Type) cls));
    }

    public ObjectReader reader(TypeReference<?> typeReference) {
        return reader(this._typeFactory.constructType(typeReference));
    }

    public ObjectReader reader(JsonNodeFactory jsonNodeFactory) {
        return new ObjectReader(this, getDeserializationConfig()).with(jsonNodeFactory);
    }

    public ObjectReader reader(FormatSchema formatSchema) {
        _verifySchemaType(formatSchema);
        return new ObjectReader(this, getDeserializationConfig(), (JavaType) null, (Object) null, formatSchema, this._injectableValues);
    }

    public ObjectReader reader(InjectableValues injectableValues) {
        return new ObjectReader(this, getDeserializationConfig(), (JavaType) null, (Object) null, (FormatSchema) null, injectableValues);
    }

    public ObjectReader readerWithView(Class<?> cls) {
        return new ObjectReader(this, getDeserializationConfig().withView(cls));
    }

    public ObjectReader reader(Base64Variant base64Variant) {
        return new ObjectReader(this, getDeserializationConfig().with(base64Variant));
    }

    public <T> T convertValue(Object obj, Class<T> cls) throws IllegalArgumentException {
        if (obj == null) {
            return null;
        }
        return _convert(obj, this._typeFactory.constructType((Type) cls));
    }

    public <T> T convertValue(Object obj, TypeReference<?> typeReference) throws IllegalArgumentException {
        return convertValue(obj, this._typeFactory.constructType(typeReference));
    }

    public <T> T convertValue(Object obj, JavaType javaType) throws IllegalArgumentException {
        if (obj == null) {
            return null;
        }
        return _convert(obj, javaType);
    }

    /* access modifiers changed from: protected */
    public Object _convert(Object obj, JavaType javaType) throws IllegalArgumentException {
        Object obj2;
        Class<?> rawClass = javaType.getRawClass();
        if (rawClass != Object.class && !javaType.hasGenericTypes() && rawClass.isAssignableFrom(obj.getClass())) {
            return obj;
        }
        TokenBuffer tokenBuffer = new TokenBuffer(this);
        try {
            _serializerProvider(getSerializationConfig().without(SerializationFeature.WRAP_ROOT_VALUE)).serializeValue(tokenBuffer, obj);
            JsonParser asParser = tokenBuffer.asParser();
            DeserializationConfig deserializationConfig = getDeserializationConfig();
            JsonToken _initForReading = _initForReading(asParser);
            if (_initForReading == JsonToken.VALUE_NULL) {
                obj2 = _findRootDeserializer(createDeserializationContext(asParser, deserializationConfig), javaType).getNullValue();
            } else {
                if (_initForReading != JsonToken.END_ARRAY) {
                    if (_initForReading != JsonToken.END_OBJECT) {
                        DefaultDeserializationContext createDeserializationContext = createDeserializationContext(asParser, deserializationConfig);
                        obj2 = _findRootDeserializer(createDeserializationContext, javaType).deserialize(asParser, createDeserializationContext);
                    }
                }
                obj2 = null;
            }
            asParser.close();
            return obj2;
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public JsonSchema generateJsonSchema(Class<?> cls) throws JsonMappingException {
        return _serializerProvider(getSerializationConfig()).generateJsonSchema(cls);
    }

    public void acceptJsonFormatVisitor(Class<?> cls, JsonFormatVisitorWrapper jsonFormatVisitorWrapper) throws JsonMappingException {
        acceptJsonFormatVisitor(this._typeFactory.constructType((Type) cls), jsonFormatVisitorWrapper);
    }

    public void acceptJsonFormatVisitor(JavaType javaType, JsonFormatVisitorWrapper jsonFormatVisitorWrapper) throws JsonMappingException {
        if (javaType != null) {
            _serializerProvider(getSerializationConfig()).acceptJsonFormatVisitor(javaType, jsonFormatVisitorWrapper);
            return;
        }
        throw new IllegalArgumentException("type must be provided");
    }

    /* access modifiers changed from: protected */
    public DefaultSerializerProvider _serializerProvider(SerializationConfig serializationConfig) {
        return this._serializerProvider.createInstance(serializationConfig, this._serializerFactory);
    }

    /* access modifiers changed from: protected */
    public PrettyPrinter _defaultPrettyPrinter() {
        return _defaultPrettyPrinter;
    }

    /* access modifiers changed from: protected */
    public final void _configAndWriteValue(JsonGenerator jsonGenerator, Object obj) throws IOException, JsonGenerationException, JsonMappingException {
        SerializationConfig serializationConfig = getSerializationConfig();
        if (serializationConfig.isEnabled(SerializationFeature.INDENT_OUTPUT)) {
            jsonGenerator.useDefaultPrettyPrinter();
        }
        if (!serializationConfig.isEnabled(SerializationFeature.CLOSE_CLOSEABLE) || !(obj instanceof Closeable)) {
            boolean z = false;
            try {
                _serializerProvider(serializationConfig).serializeValue(jsonGenerator, obj);
                z = true;
                jsonGenerator.close();
            } catch (Throwable th) {
                if (!z) {
                    try {
                        jsonGenerator.close();
                    } catch (IOException e) {
                    }
                }
                throw th;
            }
        } else {
            _configAndWriteCloseable(jsonGenerator, obj, serializationConfig);
        }
    }

    /* access modifiers changed from: protected */
    public final void _configAndWriteValue(JsonGenerator jsonGenerator, Object obj, Class<?> cls) throws IOException, JsonGenerationException, JsonMappingException {
        SerializationConfig withView = getSerializationConfig().withView(cls);
        if (withView.isEnabled(SerializationFeature.INDENT_OUTPUT)) {
            jsonGenerator.useDefaultPrettyPrinter();
        }
        if (!withView.isEnabled(SerializationFeature.CLOSE_CLOSEABLE) || !(obj instanceof Closeable)) {
            boolean z = false;
            try {
                _serializerProvider(withView).serializeValue(jsonGenerator, obj);
                z = true;
                jsonGenerator.close();
            } catch (Throwable th) {
                if (!z) {
                    try {
                        jsonGenerator.close();
                    } catch (IOException e) {
                    }
                }
                throw th;
            }
        } else {
            _configAndWriteCloseable(jsonGenerator, obj, withView);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x002a A[SYNTHETIC, Splitter:B:18:0x002a] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void _configAndWriteCloseable(com.fasterxml.jackson.core.JsonGenerator r3, java.lang.Object r4, com.fasterxml.jackson.databind.SerializationConfig r5) throws java.io.IOException, com.fasterxml.jackson.core.JsonGenerationException, com.fasterxml.jackson.databind.JsonMappingException {
        /*
            r2 = this;
            r0 = r4
            java.io.Closeable r0 = (java.io.Closeable) r0
            r1 = 0
            com.fasterxml.jackson.databind.ser.DefaultSerializerProvider r5 = r2._serializerProvider(r5)     // Catch:{ all -> 0x001d }
            r5.serializeValue(r3, r4)     // Catch:{ all -> 0x001d }
            r3.close()     // Catch:{ all -> 0x001b }
            r0.close()     // Catch:{ all -> 0x0018 }
            return
        L_0x0018:
            r3 = move-exception
            r0 = r1
            goto L_0x0020
        L_0x001b:
            r3 = move-exception
            goto L_0x0020
        L_0x001d:
            r4 = move-exception
            r1 = r3
            r3 = r4
        L_0x0020:
            if (r1 == 0) goto L_0x0028
            r1.close()     // Catch:{ IOException -> 0x0026 }
        L_0x0025:
            goto L_0x0028
        L_0x0026:
            r4 = move-exception
            goto L_0x0025
        L_0x0028:
            if (r0 == 0) goto L_0x0030
            r0.close()     // Catch:{ IOException -> 0x002e }
        L_0x002d:
            goto L_0x0030
        L_0x002e:
            r4 = move-exception
            goto L_0x002d
        L_0x0030:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.ObjectMapper._configAndWriteCloseable(com.fasterxml.jackson.core.JsonGenerator, java.lang.Object, com.fasterxml.jackson.databind.SerializationConfig):void");
    }

    private final void _writeCloseableValue(JsonGenerator jsonGenerator, Object obj, SerializationConfig serializationConfig) throws IOException, JsonGenerationException, JsonMappingException {
        Closeable closeable = (Closeable) obj;
        try {
            _serializerProvider(serializationConfig).serializeValue(jsonGenerator, obj);
            if (serializationConfig.isEnabled(SerializationFeature.FLUSH_AFTER_WRITE_VALUE)) {
                jsonGenerator.flush();
            }
            try {
                closeable.close();
            } catch (Throwable th) {
                closeable = null;
                th = th;
            }
        } catch (Throwable th2) {
            th = th2;
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                }
            }
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public DefaultDeserializationContext createDeserializationContext(JsonParser jsonParser, DeserializationConfig deserializationConfig) {
        return this._deserializationContext.createInstance(deserializationConfig, jsonParser, this._injectableValues);
    }

    /* access modifiers changed from: protected */
    public Object _readValue(DeserializationConfig deserializationConfig, JsonParser jsonParser, JavaType javaType) throws IOException, JsonParseException, JsonMappingException {
        Object obj;
        JsonToken _initForReading = _initForReading(jsonParser);
        if (_initForReading == JsonToken.VALUE_NULL) {
            obj = _findRootDeserializer(createDeserializationContext(jsonParser, deserializationConfig), javaType).getNullValue();
        } else if (_initForReading == JsonToken.END_ARRAY || _initForReading == JsonToken.END_OBJECT) {
            obj = null;
        } else {
            DefaultDeserializationContext createDeserializationContext = createDeserializationContext(jsonParser, deserializationConfig);
            JsonDeserializer<Object> _findRootDeserializer = _findRootDeserializer(createDeserializationContext, javaType);
            if (deserializationConfig.useRootWrapping()) {
                obj = _unwrapAndDeserialize(jsonParser, createDeserializationContext, deserializationConfig, javaType, _findRootDeserializer);
            } else {
                obj = _findRootDeserializer.deserialize(jsonParser, createDeserializationContext);
            }
        }
        jsonParser.clearCurrentToken();
        return obj;
    }

    /* access modifiers changed from: protected */
    public Object _readMapAndClose(JsonParser jsonParser, JavaType javaType) throws IOException, JsonParseException, JsonMappingException {
        Object obj;
        try {
            JsonToken _initForReading = _initForReading(jsonParser);
            if (_initForReading == JsonToken.VALUE_NULL) {
                obj = _findRootDeserializer(createDeserializationContext(jsonParser, getDeserializationConfig()), javaType).getNullValue();
            } else {
                if (_initForReading != JsonToken.END_ARRAY) {
                    if (_initForReading != JsonToken.END_OBJECT) {
                        DeserializationConfig deserializationConfig = getDeserializationConfig();
                        DefaultDeserializationContext createDeserializationContext = createDeserializationContext(jsonParser, deserializationConfig);
                        JsonDeserializer<Object> _findRootDeserializer = _findRootDeserializer(createDeserializationContext, javaType);
                        if (deserializationConfig.useRootWrapping()) {
                            obj = _unwrapAndDeserialize(jsonParser, createDeserializationContext, deserializationConfig, javaType, _findRootDeserializer);
                        } else {
                            obj = _findRootDeserializer.deserialize(jsonParser, createDeserializationContext);
                        }
                    }
                }
                obj = null;
            }
            jsonParser.clearCurrentToken();
            return obj;
        } finally {
            try {
                jsonParser.close();
            } catch (IOException e) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public JsonToken _initForReading(JsonParser jsonParser) throws IOException, JsonParseException, JsonMappingException {
        JsonToken currentToken = jsonParser.getCurrentToken();
        if (currentToken != null || (currentToken = jsonParser.nextToken()) != null) {
            return currentToken;
        }
        throw JsonMappingException.from(jsonParser, "No content to map due to end-of-input");
    }

    /* access modifiers changed from: protected */
    public Object _unwrapAndDeserialize(JsonParser jsonParser, DeserializationContext deserializationContext, DeserializationConfig deserializationConfig, JavaType javaType, JsonDeserializer<Object> jsonDeserializer) throws IOException, JsonParseException, JsonMappingException {
        String rootName = deserializationConfig.getRootName();
        if (rootName == null) {
            rootName = this._rootNames.findRootName(javaType, (MapperConfig<?>) deserializationConfig).getValue();
        }
        if (jsonParser.getCurrentToken() != JsonToken.START_OBJECT) {
            throw JsonMappingException.from(jsonParser, "Current token not START_OBJECT (needed to unwrap root name '" + rootName + "'), but " + jsonParser.getCurrentToken());
        } else if (jsonParser.nextToken() == JsonToken.FIELD_NAME) {
            String currentName = jsonParser.getCurrentName();
            if (rootName.equals(currentName)) {
                jsonParser.nextToken();
                Object deserialize = jsonDeserializer.deserialize(jsonParser, deserializationContext);
                if (jsonParser.nextToken() == JsonToken.END_OBJECT) {
                    return deserialize;
                }
                throw JsonMappingException.from(jsonParser, "Current token not END_OBJECT (to match wrapper object with root name '" + rootName + "'), but " + jsonParser.getCurrentToken());
            }
            throw JsonMappingException.from(jsonParser, "Root name '" + currentName + "' does not match expected ('" + rootName + "') for type " + javaType);
        } else {
            throw JsonMappingException.from(jsonParser, "Current token not FIELD_NAME (to contain expected root name '" + rootName + "'), but " + jsonParser.getCurrentToken());
        }
    }

    /* access modifiers changed from: protected */
    public JsonDeserializer<Object> _findRootDeserializer(DeserializationContext deserializationContext, JavaType javaType) throws JsonMappingException {
        JsonDeserializer<Object> jsonDeserializer = this._rootDeserializers.get(javaType);
        if (jsonDeserializer != null) {
            return jsonDeserializer;
        }
        JsonDeserializer<Object> findRootValueDeserializer = deserializationContext.findRootValueDeserializer(javaType);
        if (findRootValueDeserializer != null) {
            this._rootDeserializers.put(javaType, findRootValueDeserializer);
            return findRootValueDeserializer;
        }
        throw new JsonMappingException("Can not find a deserializer for type " + javaType);
    }

    /* access modifiers changed from: protected */
    public void _verifySchemaType(FormatSchema formatSchema) {
        if (formatSchema != null && !this._jsonFactory.canUseSchema(formatSchema)) {
            throw new IllegalArgumentException("Can not use FormatSchema of type " + formatSchema.getClass().getName() + " for format " + this._jsonFactory.getFormatName());
        }
    }
}
