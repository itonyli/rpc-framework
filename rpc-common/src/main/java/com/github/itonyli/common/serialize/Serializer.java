package com.github.itonyli.common.serialize;

public interface Serializer {

    public byte[] serialize(Object obj);

    public <T> T deserialize(byte[] bytes, Class<T> clazz);
}
