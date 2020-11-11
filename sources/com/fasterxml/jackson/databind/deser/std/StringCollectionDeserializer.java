package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;
import java.util.Collection;

@JacksonStdImpl
public final class StringCollectionDeserializer extends ContainerDeserializerBase<Collection<String>> implements ContextualDeserializer {
    private static final long serialVersionUID = 1;
    protected final JavaType _collectionType;
    protected final JsonDeserializer<Object> _delegateDeserializer;
    protected final JsonDeserializer<String> _valueDeserializer;
    protected final ValueInstantiator _valueInstantiator;

    public StringCollectionDeserializer(JavaType javaType, JsonDeserializer<?> jsonDeserializer, ValueInstantiator valueInstantiator) {
        this(javaType, valueInstantiator, (JsonDeserializer<?>) null, jsonDeserializer);
    }

    protected StringCollectionDeserializer(JavaType javaType, ValueInstantiator valueInstantiator, JsonDeserializer<?> jsonDeserializer, JsonDeserializer<?> jsonDeserializer2) {
        super(javaType.getRawClass());
        this._collectionType = javaType;
        this._valueDeserializer = jsonDeserializer2;
        this._valueInstantiator = valueInstantiator;
        this._delegateDeserializer = jsonDeserializer;
    }

    /* access modifiers changed from: protected */
    public StringCollectionDeserializer withResolved(JsonDeserializer<?> jsonDeserializer, JsonDeserializer<?> jsonDeserializer2) {
        if (this._valueDeserializer == jsonDeserializer2 && this._delegateDeserializer == jsonDeserializer) {
            return this;
        }
        return new StringCollectionDeserializer(this._collectionType, this._valueInstantiator, jsonDeserializer, jsonDeserializer2);
    }

    public JsonDeserializer<?> createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) throws JsonMappingException {
        JsonDeserializer<Object> jsonDeserializer;
        ValueInstantiator valueInstantiator = this._valueInstantiator;
        JsonDeserializer jsonDeserializer2 = null;
        if (valueInstantiator == null || valueInstantiator.getDelegateCreator() == null) {
            jsonDeserializer = null;
        } else {
            jsonDeserializer = findDeserializer(deserializationContext, this._valueInstantiator.getDelegateType(deserializationContext.getConfig()), beanProperty);
        }
        JsonDeserializer jsonDeserializer3 = this._valueDeserializer;
        if (jsonDeserializer3 == null) {
            jsonDeserializer3 = findConvertingContentDeserializer(deserializationContext, beanProperty, jsonDeserializer3);
            if (jsonDeserializer3 == null) {
                jsonDeserializer3 = deserializationContext.findContextualValueDeserializer(this._collectionType.getContentType(), beanProperty);
            }
        } else if (jsonDeserializer3 instanceof ContextualDeserializer) {
            jsonDeserializer3 = ((ContextualDeserializer) jsonDeserializer3).createContextual(deserializationContext, beanProperty);
        }
        if (!isDefaultDeserializer(jsonDeserializer3)) {
            jsonDeserializer2 = jsonDeserializer3;
        }
        return withResolved(jsonDeserializer, jsonDeserializer2);
    }

    public JavaType getContentType() {
        return this._collectionType.getContentType();
    }

    public JsonDeserializer<Object> getContentDeserializer() {
        return this._valueDeserializer;
    }

    public Collection<String> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonDeserializer<Object> jsonDeserializer = this._delegateDeserializer;
        if (jsonDeserializer != null) {
            return (Collection) this._valueInstantiator.createUsingDelegate(deserializationContext, jsonDeserializer.deserialize(jsonParser, deserializationContext));
        }
        return deserialize(jsonParser, deserializationContext, (Collection<String>) (Collection) this._valueInstantiator.createUsingDefault(deserializationContext));
    }

    public Collection<String> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Collection<String> collection) throws IOException, JsonProcessingException {
        if (!jsonParser.isExpectedStartArrayToken()) {
            return handleNonArray(jsonParser, deserializationContext, collection);
        }
        JsonDeserializer<String> jsonDeserializer = this._valueDeserializer;
        if (jsonDeserializer != null) {
            return deserializeUsingCustom(jsonParser, deserializationContext, collection, jsonDeserializer);
        }
        while (true) {
            JsonToken nextToken = jsonParser.nextToken();
            if (nextToken == JsonToken.END_ARRAY) {
                return collection;
            }
            collection.add(nextToken == JsonToken.VALUE_NULL ? null : _parseString(jsonParser, deserializationContext));
        }
    }

    private Collection<String> deserializeUsingCustom(JsonParser jsonParser, DeserializationContext deserializationContext, Collection<String> collection, JsonDeserializer<String> jsonDeserializer) throws IOException, JsonProcessingException {
        String str;
        while (true) {
            JsonToken nextToken = jsonParser.nextToken();
            if (nextToken == JsonToken.END_ARRAY) {
                return collection;
            }
            if (nextToken == JsonToken.VALUE_NULL) {
                str = null;
            } else {
                str = jsonDeserializer.deserialize(jsonParser, deserializationContext);
            }
            collection.add(str);
        }
    }

    public Object deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException, JsonProcessingException {
        return typeDeserializer.deserializeTypedFromArray(jsonParser, deserializationContext);
    }

    private final Collection<String> handleNonArray(JsonParser jsonParser, DeserializationContext deserializationContext, Collection<String> collection) throws IOException, JsonProcessingException {
        String str;
        if (deserializationContext.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
            JsonDeserializer<String> jsonDeserializer = this._valueDeserializer;
            if (jsonParser.getCurrentToken() == JsonToken.VALUE_NULL) {
                str = null;
            } else {
                str = jsonDeserializer == null ? _parseString(jsonParser, deserializationContext) : jsonDeserializer.deserialize(jsonParser, deserializationContext);
            }
            collection.add(str);
            return collection;
        }
        throw deserializationContext.mappingException(this._collectionType.getRawClass());
    }
}
