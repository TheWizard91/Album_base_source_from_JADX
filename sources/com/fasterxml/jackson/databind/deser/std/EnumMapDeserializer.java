package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;
import java.util.EnumMap;

public class EnumMapDeserializer extends StdDeserializer<EnumMap<?, ?>> implements ContextualDeserializer {
    private static final long serialVersionUID = 1518773374647478964L;
    protected final Class<?> _enumClass;
    protected JsonDeserializer<Enum<?>> _keyDeserializer;
    protected final JavaType _mapType;
    protected JsonDeserializer<Object> _valueDeserializer;
    protected final TypeDeserializer _valueTypeDeserializer;

    @Deprecated
    public EnumMapDeserializer(JavaType javaType, JsonDeserializer<?> jsonDeserializer, JsonDeserializer<?> jsonDeserializer2) {
        this(javaType, jsonDeserializer, jsonDeserializer2, (TypeDeserializer) null);
    }

    public EnumMapDeserializer(JavaType javaType, JsonDeserializer<?> jsonDeserializer, JsonDeserializer<?> jsonDeserializer2, TypeDeserializer typeDeserializer) {
        super((Class<?>) EnumMap.class);
        this._mapType = javaType;
        this._enumClass = javaType.getKeyType().getRawClass();
        this._keyDeserializer = jsonDeserializer;
        this._valueDeserializer = jsonDeserializer2;
        this._valueTypeDeserializer = typeDeserializer;
    }

    @Deprecated
    public EnumMapDeserializer withResolved(JsonDeserializer<?> jsonDeserializer, JsonDeserializer<?> jsonDeserializer2) {
        return withResolved(jsonDeserializer, jsonDeserializer2, (TypeDeserializer) null);
    }

    public EnumMapDeserializer withResolved(JsonDeserializer<?> jsonDeserializer, JsonDeserializer<?> jsonDeserializer2, TypeDeserializer typeDeserializer) {
        if (jsonDeserializer == this._keyDeserializer && jsonDeserializer2 == this._valueDeserializer && typeDeserializer == this._valueTypeDeserializer) {
            return this;
        }
        return new EnumMapDeserializer(this._mapType, jsonDeserializer, jsonDeserializer2, this._valueTypeDeserializer);
    }

    public JsonDeserializer<?> createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) throws JsonMappingException {
        JsonDeserializer jsonDeserializer = this._keyDeserializer;
        if (jsonDeserializer == null) {
            jsonDeserializer = deserializationContext.findContextualValueDeserializer(this._mapType.getKeyType(), beanProperty);
        }
        JsonDeserializer jsonDeserializer2 = this._valueDeserializer;
        if (jsonDeserializer2 == null) {
            jsonDeserializer2 = deserializationContext.findContextualValueDeserializer(this._mapType.getContentType(), beanProperty);
        } else if (jsonDeserializer2 instanceof ContextualDeserializer) {
            jsonDeserializer2 = ((ContextualDeserializer) jsonDeserializer2).createContextual(deserializationContext, beanProperty);
        }
        TypeDeserializer typeDeserializer = this._valueTypeDeserializer;
        if (typeDeserializer != null) {
            typeDeserializer = typeDeserializer.forProperty(beanProperty);
        }
        return withResolved(jsonDeserializer, jsonDeserializer2, typeDeserializer);
    }

    public boolean isCachable() {
        return true;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: com.fasterxml.jackson.databind.JsonDeserializer<java.lang.Object>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v1, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v5, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v6, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v7, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v8, resolved type: java.lang.String} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.EnumMap<?, ?> deserialize(com.fasterxml.jackson.core.JsonParser r8, com.fasterxml.jackson.databind.DeserializationContext r9) throws java.io.IOException, com.fasterxml.jackson.core.JsonProcessingException {
        /*
            r7 = this;
            com.fasterxml.jackson.core.JsonToken r0 = r8.getCurrentToken()
            com.fasterxml.jackson.core.JsonToken r1 = com.fasterxml.jackson.core.JsonToken.START_OBJECT
            if (r0 != r1) goto L_0x0064
            java.util.EnumMap r0 = r7.constructMap()
            com.fasterxml.jackson.databind.JsonDeserializer<java.lang.Object> r1 = r7._valueDeserializer
            com.fasterxml.jackson.databind.jsontype.TypeDeserializer r2 = r7._valueTypeDeserializer
        L_0x0010:
            com.fasterxml.jackson.core.JsonToken r3 = r8.nextToken()
            com.fasterxml.jackson.core.JsonToken r4 = com.fasterxml.jackson.core.JsonToken.END_OBJECT
            if (r3 == r4) goto L_0x0063
            com.fasterxml.jackson.databind.JsonDeserializer<java.lang.Enum<?>> r3 = r7._keyDeserializer
            java.lang.Object r3 = r3.deserialize(r8, r9)
            java.lang.Enum r3 = (java.lang.Enum) r3
            r4 = 0
            if (r3 != 0) goto L_0x004b
            com.fasterxml.jackson.databind.DeserializationFeature r3 = com.fasterxml.jackson.databind.DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL
            boolean r3 = r9.isEnabled(r3)
            if (r3 != 0) goto L_0x0044
            boolean r0 = r8.hasCurrentToken()     // Catch:{ Exception -> 0x0038 }
            if (r0 == 0) goto L_0x0039
            java.lang.String r8 = r8.getText()     // Catch:{ Exception -> 0x0038 }
            r4 = r8
            goto L_0x0039
        L_0x0038:
            r8 = move-exception
        L_0x0039:
            java.lang.Class<?> r8 = r7._enumClass
            java.lang.String r0 = "value not one of declared Enum instance names"
            com.fasterxml.jackson.databind.JsonMappingException r8 = r9.weirdStringException(r4, r8, r0)
            throw r8
        L_0x0044:
            r8.nextToken()
            r8.skipChildren()
            goto L_0x0010
        L_0x004b:
            com.fasterxml.jackson.core.JsonToken r5 = r8.nextToken()
            com.fasterxml.jackson.core.JsonToken r6 = com.fasterxml.jackson.core.JsonToken.VALUE_NULL
            if (r5 != r6) goto L_0x0054
            goto L_0x005f
        L_0x0054:
            if (r2 != 0) goto L_0x005b
            java.lang.Object r4 = r1.deserialize(r8, r9)
            goto L_0x005f
        L_0x005b:
            java.lang.Object r4 = r1.deserializeWithType(r8, r9, r2)
        L_0x005f:
            r0.put(r3, r4)
            goto L_0x0010
        L_0x0063:
            return r0
        L_0x0064:
            java.lang.Class<java.util.EnumMap> r8 = java.util.EnumMap.class
            com.fasterxml.jackson.databind.JsonMappingException r8 = r9.mappingException((java.lang.Class<?>) r8)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.deser.std.EnumMapDeserializer.deserialize(com.fasterxml.jackson.core.JsonParser, com.fasterxml.jackson.databind.DeserializationContext):java.util.EnumMap");
    }

    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
        return typeDeserializer.deserializeTypedFromObject(jsonParser, deserializationContext);
    }

    private EnumMap<?, ?> constructMap() {
        return new EnumMap<>(this._enumClass);
    }
}
