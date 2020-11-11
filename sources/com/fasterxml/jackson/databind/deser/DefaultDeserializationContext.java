package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.deser.impl.ReadableObjectId;
import java.io.Serializable;
import java.util.LinkedHashMap;

public abstract class DefaultDeserializationContext extends DeserializationContext implements Serializable {
    private static final long serialVersionUID = 1;
    protected transient LinkedHashMap<ObjectIdGenerator.IdKey, ReadableObjectId> _objectIds;

    public abstract DefaultDeserializationContext createInstance(DeserializationConfig deserializationConfig, JsonParser jsonParser, InjectableValues injectableValues);

    public abstract DefaultDeserializationContext with(DeserializerFactory deserializerFactory);

    protected DefaultDeserializationContext(DeserializerFactory deserializerFactory, DeserializerCache deserializerCache) {
        super(deserializerFactory, deserializerCache);
    }

    protected DefaultDeserializationContext(DefaultDeserializationContext defaultDeserializationContext, DeserializationConfig deserializationConfig, JsonParser jsonParser, InjectableValues injectableValues) {
        super(defaultDeserializationContext, deserializationConfig, jsonParser, injectableValues);
    }

    protected DefaultDeserializationContext(DefaultDeserializationContext defaultDeserializationContext, DeserializerFactory deserializerFactory) {
        super((DeserializationContext) defaultDeserializationContext, deserializerFactory);
    }

    public ReadableObjectId findObjectId(Object obj, ObjectIdGenerator<?> objectIdGenerator) {
        ObjectIdGenerator.IdKey key = objectIdGenerator.key(obj);
        LinkedHashMap<ObjectIdGenerator.IdKey, ReadableObjectId> linkedHashMap = this._objectIds;
        if (linkedHashMap == null) {
            this._objectIds = new LinkedHashMap<>();
        } else {
            ReadableObjectId readableObjectId = linkedHashMap.get(key);
            if (readableObjectId != null) {
                return readableObjectId;
            }
        }
        ReadableObjectId readableObjectId2 = new ReadableObjectId(obj);
        this._objectIds.put(key, readableObjectId2);
        return readableObjectId2;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v7, resolved type: java.lang.Class<com.fasterxml.jackson.databind.annotation.NoClass>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v13, resolved type: com.fasterxml.jackson.databind.JsonDeserializer<?>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.fasterxml.jackson.databind.JsonDeserializer<java.lang.Object> deserializerInstance(com.fasterxml.jackson.databind.introspect.Annotated r3, java.lang.Object r4) throws com.fasterxml.jackson.databind.JsonMappingException {
        /*
            r2 = this;
            r0 = 0
            if (r4 != 0) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r4 instanceof com.fasterxml.jackson.databind.JsonDeserializer
            if (r1 == 0) goto L_0x000b
            com.fasterxml.jackson.databind.JsonDeserializer r4 = (com.fasterxml.jackson.databind.JsonDeserializer) r4
            goto L_0x0042
        L_0x000b:
            boolean r1 = r4 instanceof java.lang.Class
            if (r1 == 0) goto L_0x0071
            java.lang.Class r4 = (java.lang.Class) r4
            java.lang.Class<com.fasterxml.jackson.databind.JsonDeserializer$None> r1 = com.fasterxml.jackson.databind.JsonDeserializer.None.class
            if (r4 == r1) goto L_0x0070
            java.lang.Class<com.fasterxml.jackson.databind.annotation.NoClass> r1 = com.fasterxml.jackson.databind.annotation.NoClass.class
            if (r4 != r1) goto L_0x001a
            goto L_0x0070
        L_0x001a:
            java.lang.Class<com.fasterxml.jackson.databind.JsonDeserializer> r1 = com.fasterxml.jackson.databind.JsonDeserializer.class
            boolean r1 = r1.isAssignableFrom(r4)
            if (r1 == 0) goto L_0x004d
            com.fasterxml.jackson.databind.DeserializationConfig r1 = r2._config
            com.fasterxml.jackson.databind.cfg.HandlerInstantiator r1 = r1.getHandlerInstantiator()
            if (r1 != 0) goto L_0x002b
            goto L_0x0031
        L_0x002b:
            com.fasterxml.jackson.databind.DeserializationConfig r0 = r2._config
            com.fasterxml.jackson.databind.JsonDeserializer r0 = r1.deserializerInstance(r0, r3, r4)
        L_0x0031:
            if (r0 != 0) goto L_0x0041
            com.fasterxml.jackson.databind.DeserializationConfig r3 = r2._config
            boolean r3 = r3.canOverrideAccessModifiers()
            java.lang.Object r3 = com.fasterxml.jackson.databind.util.ClassUtil.createInstance(r4, r3)
            r4 = r3
            com.fasterxml.jackson.databind.JsonDeserializer r4 = (com.fasterxml.jackson.databind.JsonDeserializer) r4
            goto L_0x0042
        L_0x0041:
            r4 = r0
        L_0x0042:
            boolean r3 = r4 instanceof com.fasterxml.jackson.databind.deser.ResolvableDeserializer
            if (r3 == 0) goto L_0x004c
            r3 = r4
            com.fasterxml.jackson.databind.deser.ResolvableDeserializer r3 = (com.fasterxml.jackson.databind.deser.ResolvableDeserializer) r3
            r3.resolve(r2)
        L_0x004c:
            return r4
        L_0x004d:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "AnnotationIntrospector returned Class "
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r4 = r4.getName()
            java.lang.StringBuilder r4 = r0.append(r4)
            java.lang.String r0 = "; expected Class<JsonDeserializer>"
            java.lang.StringBuilder r4 = r4.append(r0)
            java.lang.String r4 = r4.toString()
            r3.<init>(r4)
            throw r3
        L_0x0070:
            return r0
        L_0x0071:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "AnnotationIntrospector returned deserializer definition of type "
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.Class r4 = r4.getClass()
            java.lang.String r4 = r4.getName()
            java.lang.StringBuilder r4 = r0.append(r4)
            java.lang.String r0 = "; expected type JsonDeserializer or Class<JsonDeserializer> instead"
            java.lang.StringBuilder r4 = r4.append(r0)
            java.lang.String r4 = r4.toString()
            r3.<init>(r4)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.deser.DefaultDeserializationContext.deserializerInstance(com.fasterxml.jackson.databind.introspect.Annotated, java.lang.Object):com.fasterxml.jackson.databind.JsonDeserializer");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v7, resolved type: java.lang.Class<com.fasterxml.jackson.databind.annotation.NoClass>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v13, resolved type: com.fasterxml.jackson.databind.KeyDeserializer} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.fasterxml.jackson.databind.KeyDeserializer keyDeserializerInstance(com.fasterxml.jackson.databind.introspect.Annotated r3, java.lang.Object r4) throws com.fasterxml.jackson.databind.JsonMappingException {
        /*
            r2 = this;
            r0 = 0
            if (r4 != 0) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r4 instanceof com.fasterxml.jackson.databind.KeyDeserializer
            if (r1 == 0) goto L_0x000b
            com.fasterxml.jackson.databind.KeyDeserializer r4 = (com.fasterxml.jackson.databind.KeyDeserializer) r4
            goto L_0x0042
        L_0x000b:
            boolean r1 = r4 instanceof java.lang.Class
            if (r1 == 0) goto L_0x0071
            java.lang.Class r4 = (java.lang.Class) r4
            java.lang.Class<com.fasterxml.jackson.databind.KeyDeserializer$None> r1 = com.fasterxml.jackson.databind.KeyDeserializer.None.class
            if (r4 == r1) goto L_0x0070
            java.lang.Class<com.fasterxml.jackson.databind.annotation.NoClass> r1 = com.fasterxml.jackson.databind.annotation.NoClass.class
            if (r4 != r1) goto L_0x001a
            goto L_0x0070
        L_0x001a:
            java.lang.Class<com.fasterxml.jackson.databind.KeyDeserializer> r1 = com.fasterxml.jackson.databind.KeyDeserializer.class
            boolean r1 = r1.isAssignableFrom(r4)
            if (r1 == 0) goto L_0x004d
            com.fasterxml.jackson.databind.DeserializationConfig r1 = r2._config
            com.fasterxml.jackson.databind.cfg.HandlerInstantiator r1 = r1.getHandlerInstantiator()
            if (r1 != 0) goto L_0x002b
            goto L_0x0031
        L_0x002b:
            com.fasterxml.jackson.databind.DeserializationConfig r0 = r2._config
            com.fasterxml.jackson.databind.KeyDeserializer r0 = r1.keyDeserializerInstance(r0, r3, r4)
        L_0x0031:
            if (r0 != 0) goto L_0x0041
            com.fasterxml.jackson.databind.DeserializationConfig r3 = r2._config
            boolean r3 = r3.canOverrideAccessModifiers()
            java.lang.Object r3 = com.fasterxml.jackson.databind.util.ClassUtil.createInstance(r4, r3)
            r4 = r3
            com.fasterxml.jackson.databind.KeyDeserializer r4 = (com.fasterxml.jackson.databind.KeyDeserializer) r4
            goto L_0x0042
        L_0x0041:
            r4 = r0
        L_0x0042:
            boolean r3 = r4 instanceof com.fasterxml.jackson.databind.deser.ResolvableDeserializer
            if (r3 == 0) goto L_0x004c
            r3 = r4
            com.fasterxml.jackson.databind.deser.ResolvableDeserializer r3 = (com.fasterxml.jackson.databind.deser.ResolvableDeserializer) r3
            r3.resolve(r2)
        L_0x004c:
            return r4
        L_0x004d:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "AnnotationIntrospector returned Class "
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r4 = r4.getName()
            java.lang.StringBuilder r4 = r0.append(r4)
            java.lang.String r0 = "; expected Class<KeyDeserializer>"
            java.lang.StringBuilder r4 = r4.append(r0)
            java.lang.String r4 = r4.toString()
            r3.<init>(r4)
            throw r3
        L_0x0070:
            return r0
        L_0x0071:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "AnnotationIntrospector returned key deserializer definition of type "
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.Class r4 = r4.getClass()
            java.lang.String r4 = r4.getName()
            java.lang.StringBuilder r4 = r0.append(r4)
            java.lang.String r0 = "; expected type KeyDeserializer or Class<KeyDeserializer> instead"
            java.lang.StringBuilder r4 = r4.append(r0)
            java.lang.String r4 = r4.toString()
            r3.<init>(r4)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.deser.DefaultDeserializationContext.keyDeserializerInstance(com.fasterxml.jackson.databind.introspect.Annotated, java.lang.Object):com.fasterxml.jackson.databind.KeyDeserializer");
    }

    public static final class Impl extends DefaultDeserializationContext {
        private static final long serialVersionUID = 1;

        public Impl(DeserializerFactory deserializerFactory) {
            super(deserializerFactory, (DeserializerCache) null);
        }

        protected Impl(Impl impl, DeserializationConfig deserializationConfig, JsonParser jsonParser, InjectableValues injectableValues) {
            super(impl, deserializationConfig, jsonParser, injectableValues);
        }

        protected Impl(Impl impl, DeserializerFactory deserializerFactory) {
            super((DefaultDeserializationContext) impl, deserializerFactory);
        }

        public DefaultDeserializationContext createInstance(DeserializationConfig deserializationConfig, JsonParser jsonParser, InjectableValues injectableValues) {
            return new Impl(this, deserializationConfig, jsonParser, injectableValues);
        }

        public DefaultDeserializationContext with(DeserializerFactory deserializerFactory) {
            return new Impl(this, deserializerFactory);
        }
    }
}
