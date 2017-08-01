package com.github.itonyli.common.serialize;


import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Apache serialization utils base on jdk serialization
 * So do not care name
 */
public class ApacheSerializer implements Serializer{

    private static Logger logger = LoggerFactory.getLogger(ApacheSerializer.class);

    public byte[] serialize(Object obj) {
        if (obj instanceof Serializable) {
            Serializable serialObj = (Serializable) obj;
            return SerializationUtils.serialize(serialObj);
        }
        logger.error("Fail to serialize object, object:{}", obj);
        return new byte[0];
    }

    public Object deserialize(byte[] bytes) {
        return SerializationUtils.deserialize(bytes);
    }
}
