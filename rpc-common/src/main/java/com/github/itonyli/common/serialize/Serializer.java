package com.github.itonyli.common.serialize;

public interface Serializer {

    public byte[] serialize(Object obj);

    public Object deserialize(byte[] bytes);
}
