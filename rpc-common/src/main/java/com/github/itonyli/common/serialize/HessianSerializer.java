package com.github.itonyli.common.serialize;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HessianSerializer implements Serializer {

    private static final Logger logger = LoggerFactory.getLogger(HessianSerializer.class);

    @Override
    public byte[] serialize(Object obj) {
        logger.debug("hessian serialize object:{}.", obj);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        HessianOutput ho = new HessianOutput(bos);
        try {
            ho.writeObject(obj);
            ho.flush();
        } catch (IOException e) {
            logger.error("An exception occurred on hessian serialization, object:{}, error:{}.", obj, Throwables.getStackTraceAsString(e));
        }
        return bos.toByteArray();
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        logger.debug("hessian deserialize bytes:{} to class:{}.", bytes, clazz.getName());
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        HessianInput hi = new HessianInput(bis);
        try {
            Object obj = hi.readObject();
            return obj.getClass().equals(clazz) ? (T) obj : null;
        } catch (IOException e) {
            logger.error("An exception occurred on hessian deserialization, bytes:{}, error:{}.", bytes, Throwables.getStackTraceAsString(e));
        }
        return null;
    }
}
