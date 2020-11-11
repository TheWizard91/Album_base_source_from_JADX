package com.fasterxml.jackson.databind.ext;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.ser.Serializers;
import java.io.Serializable;

public class OptionalHandlerFactory implements Serializable {
    private static final String CLASS_NAME_DOM_DOCUMENT = "org.w3c.dom.Node";
    private static final String CLASS_NAME_DOM_NODE = "org.w3c.dom.Node";
    private static final String DESERIALIZERS_FOR_JAVAX_XML = "com.fasterxml.jackson.databind.ext.CoreXMLDeserializers";
    private static final String DESERIALIZER_FOR_DOM_DOCUMENT = "com.fasterxml.jackson.databind.ext.DOMDeserializer$DocumentDeserializer";
    private static final String DESERIALIZER_FOR_DOM_NODE = "com.fasterxml.jackson.databind.ext.DOMDeserializer$NodeDeserializer";
    private static final String PACKAGE_PREFIX_JAVAX_XML = "javax.xml.";
    private static final String SERIALIZERS_FOR_JAVAX_XML = "com.fasterxml.jackson.databind.ext.CoreXMLSerializers";
    private static final String SERIALIZER_FOR_DOM_NODE = "com.fasterxml.jackson.databind.ext.DOMSerializer";
    public static final OptionalHandlerFactory instance = new OptionalHandlerFactory();
    private static final long serialVersionUID = 1;

    protected OptionalHandlerFactory() {
    }

    public JsonSerializer<?> findSerializer(SerializationConfig serializationConfig, JavaType javaType, BeanDescription beanDescription) {
        Class<?> rawClass = javaType.getRawClass();
        if (rawClass.getName().startsWith(PACKAGE_PREFIX_JAVAX_XML) || hasSupertypeStartingWith(rawClass, PACKAGE_PREFIX_JAVAX_XML)) {
            Object instantiate = instantiate(SERIALIZERS_FOR_JAVAX_XML);
            if (instantiate == null) {
                return null;
            }
            return ((Serializers) instantiate).findSerializer(serializationConfig, javaType, beanDescription);
        } else if (doesImplement(rawClass, "org.w3c.dom.Node")) {
            return (JsonSerializer) instantiate(SERIALIZER_FOR_DOM_NODE);
        } else {
            return null;
        }
    }

    public JsonDeserializer<?> findDeserializer(JavaType javaType, DeserializationConfig deserializationConfig, BeanDescription beanDescription) throws JsonMappingException {
        Class<?> rawClass = javaType.getRawClass();
        if (rawClass.getName().startsWith(PACKAGE_PREFIX_JAVAX_XML) || hasSupertypeStartingWith(rawClass, PACKAGE_PREFIX_JAVAX_XML)) {
            Object instantiate = instantiate(DESERIALIZERS_FOR_JAVAX_XML);
            if (instantiate == null) {
                return null;
            }
            return ((Deserializers) instantiate).findBeanDeserializer(javaType, deserializationConfig, beanDescription);
        } else if (doesImplement(rawClass, "org.w3c.dom.Node")) {
            return (JsonDeserializer) instantiate(DESERIALIZER_FOR_DOM_DOCUMENT);
        } else {
            if (doesImplement(rawClass, "org.w3c.dom.Node")) {
                return (JsonDeserializer) instantiate(DESERIALIZER_FOR_DOM_NODE);
            }
            return null;
        }
    }

    private Object instantiate(String str) {
        try {
            return Class.forName(str).newInstance();
        } catch (Exception | LinkageError e) {
            return null;
        }
    }

    private boolean doesImplement(Class<?> cls, String str) {
        for (Class<? super Object> cls2 = cls; cls2 != null; cls2 = cls2.getSuperclass()) {
            if (cls2.getName().equals(str) || hasInterface(cls2, str)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasInterface(Class<?> cls, String str) {
        Class[] interfaces = cls.getInterfaces();
        for (Class name : interfaces) {
            if (name.getName().equals(str)) {
                return true;
            }
        }
        for (Class hasInterface : interfaces) {
            if (hasInterface(hasInterface, str)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARNING: type inference failed for: r4v0, types: [java.lang.Class<?>, java.lang.Class] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean hasSupertypeStartingWith(java.lang.Class<?> r4, java.lang.String r5) {
        /*
            r3 = this;
            java.lang.Class r0 = r4.getSuperclass()
        L_0x0004:
            r1 = 1
            if (r0 == 0) goto L_0x0017
            java.lang.String r2 = r0.getName()
            boolean r2 = r2.startsWith(r5)
            if (r2 == 0) goto L_0x0012
            return r1
        L_0x0012:
            java.lang.Class r0 = r0.getSuperclass()
            goto L_0x0004
        L_0x0017:
        L_0x0018:
            if (r4 == 0) goto L_0x0026
            boolean r0 = r3.hasInterfaceStartingWith(r4, r5)
            if (r0 == 0) goto L_0x0021
            return r1
        L_0x0021:
            java.lang.Class r4 = r4.getSuperclass()
            goto L_0x0018
        L_0x0026:
            r4 = 0
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.databind.ext.OptionalHandlerFactory.hasSupertypeStartingWith(java.lang.Class, java.lang.String):boolean");
    }

    private boolean hasInterfaceStartingWith(Class<?> cls, String str) {
        Class[] interfaces = cls.getInterfaces();
        for (Class name : interfaces) {
            if (name.getName().startsWith(str)) {
                return true;
            }
        }
        for (Class hasInterfaceStartingWith : interfaces) {
            if (hasInterfaceStartingWith(hasInterfaceStartingWith, str)) {
                return true;
            }
        }
        return false;
    }
}
