package com.github.itonyli.remoting;

import com.github.itonyli.common.serialize.Serializer;
import com.github.itonyli.common.serialize.SerializerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyEncoder extends MessageToByteEncoder {

    private static final Logger logger = LoggerFactory.getLogger(NettyEncoder.class);
    private static Serializer serializer = SerializerFactory.build();

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        byte[] bytes = serializer.serialize(o);
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
        logger.info("netty encode msg:{}", o);
    }
}
