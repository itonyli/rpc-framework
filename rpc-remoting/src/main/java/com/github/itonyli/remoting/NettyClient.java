package com.github.itonyli.remoting;


import com.github.itonyli.common.Constants;
import com.github.itonyli.common.entity.Request;
import com.github.itonyli.common.entity.Response;
import com.github.itonyli.common.entity.Route;
import com.google.common.base.Throwables;
import com.google.common.util.concurrent.SettableFuture;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class NettyClient {

    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);

    public Response call(Request request, Route route) {
        logger.debug("netty consumer send request to server, request:{}", request);
        SettableFuture<Response> future = SettableFuture.create();

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(bossGroup).channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new NettyDecoder(), new NettyEncoder(), new NettyClientHandler(future));
                    }
                });

        try {
            ChannelFuture channelFuture = bootstrap.connect(route.getAddress(), route.getPort()).sync();
            channelFuture.addListener((ChannelFutureListener) channelFuture12 -> logger.info("netty consumer connect server success."));

            ChannelFuture writeFuture = channelFuture.channel().writeAndFlush(request);
            writeFuture.addListener((ChannelFutureListener) channelFuture1 -> logger.info("netty consumer write request complete, request:{}.", request));

            return future.get(Constants.NETTY_SOCKET_TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            logger.error("consumer fail to connect to server or get fet callback result, error:{}.", Throwables.getStackTraceAsString(e));
        } finally {
            bossGroup.shutdownGracefully();
        }
        return null;
    }
}
