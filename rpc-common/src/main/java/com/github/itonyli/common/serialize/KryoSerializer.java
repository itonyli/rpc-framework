package com.github.itonyli.common.serialize;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;

public class KryoSerializer implements Serializer {

    private static final Logger logger = LoggerFactory.getLogger(KryoSerializer.class);

    private KryoPool pool = new KryoPool.Builder(() -> {
        Kryo kryo = new Kryo();
        kryo.setReferences(true);
        return kryo;
    }).softReferences().build();


    @Override
    public byte[] serialize(Object obj) {
        logger.debug("kryo serialize object:{}.", obj);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        return pool.run(kryo -> {
            Output output = new Output(bos);
            kryo.writeObject(output, obj);
            output.flush();
            return bos.toByteArray();
        });
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        logger.debug("kryo deserialize bytes:{} to class:{}.", bytes, clazz.getName());
        return pool.run(kryo -> kryo.readObject(new Input(bytes), clazz));
    }
}
