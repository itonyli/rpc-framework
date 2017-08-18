package com.github.itonyli.remoting;

import com.github.itonyli.common.serialize.Serializer;
import com.github.itonyli.common.serialize.SerializerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class NettyDecoder extends ByteToMessageDecoder {

    private static final Logger logger = LoggerFactory.getLogger(NettyDecoder.class);
    private static Serializer serializer = SerializerFactory.build();

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 4) {
            logger.info("no enough readable bytes.");
            return;
        }

        int dataLenght = byteBuf.readInt();
        if (dataLenght < 0) {
            channelHandlerContext.close();
        }
        logger.debug("try decode data length:{}.", dataLenght);

        if (byteBuf.readableBytes() < dataLenght) {
            byteBuf.resetReaderIndex();
        }

        logger.debug("try decode data.");
        byte[] bytes = new byte[dataLenght];
        byteBuf.readBytes(bytes);
        Object obj = serializer.deserialize(bytes, Object.class);
        logger.debug("data decode to object:{}.", obj);
        list.add(obj);
    }
}
