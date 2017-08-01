package com.github.itonyli.remoting;

import com.github.itonyli.common.entity.Request;
import com.github.itonyli.common.entity.Response;
import com.github.itonyli.common.exception.RpcException;
import com.github.itonyli.common.factory.BeanFactory;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class NettyServerHandler extends SimpleChannelInboundHandler<Request> {

    private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Request request) throws Exception {
        logger.debug("service receive request, request:{}.", request);

        Response response = new Response();
        response.setRequestId(request.getRequestId());

        Object service = BeanFactory.acquire(request.getServiceName(), request.getMethodName());
        if (service == null) {
            throw new RpcException("can acquire the impl from bean factory.");
        }
        Class<?> serviceClass = service.getClass();
        Method method = serviceClass.getMethod(request.getMethodName(), request.getArgsClass());
        Object result = method.invoke(service, request.getArgs());
        response.setResult(result);
        logger.info("get result from server:{}.", result);

        channelHandlerContext.writeAndFlush(response)
                .addListener(ChannelFutureListener.CLOSE).addListener((ChannelFutureListener) channelFuture -> logger.debug("server write back success, response:{}.", response));
    }

}
