package com.github.itonyli.common.serialize;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 对象序列化
 * TODO 尝试Kryo
 */
public class JDKSerializer {

    private static final Logger logger = LoggerFactory.getLogger(JDKSerializer.class);

    /**
     * 序列化
     * @param obj
     * @return
     */
    public static byte[] serialize(Object obj) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(obj);
            out.flush();
        } catch (IOException e) {
            logger.error("An exception occurred on serialization, object:{}, error:{}.", obj, Throwables.getStackTraceAsString(e));
        }
        return bos.toByteArray();
    }

    /**
     * 反序列化
     * @param bytes
     * @return
     */
    public static Object deserialize(byte[] bytes) {
         ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        try {
            ObjectInputStream in = new ObjectInputStream(bis);
            return in.readObject();
        } catch (Exception e) {
            // TODO 日志打印byte[]不是太好
            logger.error("An exception occurred on serialization, bytes:{}, error:{}.", bytes, Throwables.getStackTraceAsString(e));
        }
        return null;
    }

}
