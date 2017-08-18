package com.github.itonyli.common.serialize;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProtostuffSerializer implements Serializer {

    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<>();

    @Override
    public byte[] serialize(Object obj) {
        Class clazz = obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        Schema schema = getSchema(clazz);
        return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        T message = null;
        try {
            message = clazz.newInstance();
            Schema<T> schema = getSchema(clazz);
            ProtostuffIOUtil.mergeFrom(bytes, message, schema);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    private static <T> Schema<T> getSchema(Class<T> cls) {
        Schema<T> schema = (Schema<T>) cachedSchema.get(cls);
        if (schema == null) {
            schema = RuntimeSchema.createFrom(cls);
            if (schema != null) {
                cachedSchema.put(cls, schema);
            }
        }
        return schema;
    }
}
