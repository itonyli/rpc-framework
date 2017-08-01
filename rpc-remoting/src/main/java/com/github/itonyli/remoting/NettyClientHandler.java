package com.github.itonyli.remoting;

import com.github.itonyli.common.entity.Response;
import com.google.common.util.concurrent.SettableFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyClientHandler extends SimpleChannelInboundHandler<Response> {

    private static final Logger logger = LoggerFactory.getLogger(NettyClientHandler.class);

    @Getter @Setter
    private SettableFuture<Response> future;

    public NettyClientHandler(SettableFuture<Response> future) {
        this.future = future;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Response response) throws Exception {
        future.set(response);
        logger.info("consumer receive msg, response:{}.", response);
    }
}
