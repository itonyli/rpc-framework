package com.github.itonyli.common.serialize;

import com.github.itonyli.common.entity.SerializerEnum;

public class SerializerFactory {

    private static final SerializerEnum serializerEnum = SerializerEnum.KRYO;

    private SerializerFactory() {}

    public static Serializer build() {
        Serializer serializer = null;
        switch (serializerEnum) {
            case KRYO:
                serializer = new KryoSerializer();
                break;
            default:
        }
        return serializer;
    }
}
