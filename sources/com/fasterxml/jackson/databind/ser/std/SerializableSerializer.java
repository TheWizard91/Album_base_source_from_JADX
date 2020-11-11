package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

@JacksonStdImpl
public class SerializableSerializer extends StdSerializer<JsonSerializable> {
    private static final AtomicReference<ObjectMapper> _mapperReference = new AtomicReference<>();
    public static final SerializableSerializer instance = new SerializableSerializer();

    protected SerializableSerializer() {
        super(JsonSerializable.class);
    }

    public void serialize(JsonSerializable jsonSerializable, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        jsonSerializable.serialize(jsonGenerator, serializerProvider);
    }

    public final void serializeWithType(JsonSerializable jsonSerializable, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException, JsonGenerationException {
        jsonSerializable.serializeWithType(jsonGenerator, serializerProvider, typeSerializer);
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0053  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x006c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.fasterxml.jackson.databind.JsonNode getSchema(com.fasterxml.jackson.databind.SerializerProvider r6, java.lang.reflect.Type r7) throws com.fasterxml.jackson.databind.JsonMappingException {
        /*
            r5 = this;
            com.fasterxml.jackson.databind.node.ObjectNode r6 = r5.createObjectNode()
            r0 = 0
            if (r7 == 0) goto L_0x0048
            java.lang.Class r7 = com.fasterxml.jackson.databind.type.TypeFactory.rawClass(r7)
            java.lang.Class<com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema> r1 = com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema.class
            boolean r1 = r7.isAnnotationPresent(r1)
            if (r1 == 0) goto L_0x0048
            java.lang.Class<com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema> r1 = com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema.class
            java.lang.annotation.Annotation r7 = r7.getAnnotation(r1)
            com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema r7 = (com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema) r7
            java.lang.String r1 = r7.schemaType()
            java.lang.String r2 = r7.schemaObjectPropertiesDefinition()
            java.lang.String r3 = "##irrelevant"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L_0x0033
            java.lang.String r2 = r7.schemaObjectPropertiesDefinition()
            goto L_0x0034
        L_0x0033:
            r2 = r0
        L_0x0034:
            java.lang.String r4 = r7.schemaItemDefinition()
            boolean r3 = r3.equals(r4)
            if (r3 != 0) goto L_0x0045
            java.lang.String r0 = r7.schemaItemDefinition()
            r7 = r0
            r0 = r2
            goto L_0x004b
        L_0x0045:
            r7 = r0
            r0 = r2
            goto L_0x004b
        L_0x0048:
            java.lang.String r1 = "any"
            r7 = r0
        L_0x004b:
            java.lang.String r2 = "type"
            r6.put((java.lang.String) r2, (java.lang.String) r1)
            if (r0 == 0) goto L_0x006a
            java.lang.String r1 = "properties"
            com.fasterxml.jackson.databind.ObjectMapper r2 = _getObjectMapper()     // Catch:{ IOException -> 0x0061 }
            com.fasterxml.jackson.databind.JsonNode r0 = r2.readTree((java.lang.String) r0)     // Catch:{ IOException -> 0x0061 }
            r6.put((java.lang.String) r1, (com.fasterxml.jackson.databind.JsonNode) r0)     // Catch:{ IOException -> 0x0061 }
            goto L_0x006a
        L_0x0061:
            r6 = move-exception
            com.fasterxml.jackson.databind.JsonMappingException r6 = new com.fasterxml.jackson.databind.JsonMappingException
            java.lang.String r7 = "Failed to parse @JsonSerializableSchema.schemaObjectPropertiesDefinition value"
            r6.<init>(r7)
            throw r6
        L_0x006a:
            if (r7 == 0) goto L_0x0083
            java.lang.String r0 = "items"
            com.fasterxml.jackson.databind.ObjectMapper r1 = _getObjectMapper()     // Catch:{ IOException -> 0x007a }
            com.fasterxml.jackson.databind.JsonNode r7 = r1.readTree((java.lang.String) r7)     // Catch:{ IOException -> 0x007a }
            r6.put((java.lang.String) r0, (com.fasterxml.jackson.databind.JsonNode) r7)     // Catch:{ IOException -> 0x007a }
            goto L_0x0083
        L_0x007a:
            r6 = move-exception
            com.fasterxml.jackson.databind.JsonMappingException r6 = new com.fasterxml.jackson.databind.JsonMappingException
            java.lang.String r7 = "Failed to parse @JsonSerializableSchema.schemaItemDefinition value"
            r6.<init>(r7)
            throw r6
        L_0x0083:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.ser.std.SerializableSerializer.getSchema(com.fasterxml.jackson.databind.SerializerProvider, java.lang.reflect.Type):com.fasterxml.jackson.databind.JsonNode");
    }

    private static final synchronized ObjectMapper _getObjectMapper() {
        ObjectMapper objectMapper;
        synchronized (SerializableSerializer.class) {
            AtomicReference<ObjectMapper> atomicReference = _mapperReference;
            objectMapper = atomicReference.get();
            if (objectMapper == null) {
                objectMapper = new ObjectMapper();
                atomicReference.set(objectMapper);
            }
        }
        return objectMapper;
    }

    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper jsonFormatVisitorWrapper, JavaType javaType) throws JsonMappingException {
        jsonFormatVisitorWrapper.expectAnyFormat(javaType);
    }
}
