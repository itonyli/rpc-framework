package com.github.itonyli.common.serialize;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class JDKSerializer implements Serializer {

    private static final Logger logger = LoggerFactory.getLogger(JDKSerializer.class);

    @Override
    public byte[] serialize(Object obj) {
        logger.debug("jdk serialize object:{}.", obj);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(obj);
            out.flush();
        } catch (IOException e) {
            logger.error("An exception occurred on jdk serialization, object:{}, error:{}.", obj, Throwables.getStackTraceAsString(e));
        }
        return bos.toByteArray();
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        logger.debug("jdk deserialize bytes:{} to class:{}.", bytes, clazz.getName());
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        try (ObjectInputStream in = new ObjectInputStream(bis)) {
            Object obj = in.readObject();
            return obj.getClass().equals(clazz) ? (T) obj : null;
        } catch (Exception e) {
            logger.error("An exception occurred on jdk deserialization, bytes:{}, error:{}.", bytes, Throwables.getStackTraceAsString(e));
        }
        return null;
    }
}
