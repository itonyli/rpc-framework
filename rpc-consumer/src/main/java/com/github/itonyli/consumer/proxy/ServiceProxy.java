package com.github.itonyli.consumer.proxy;

import com.github.itonyli.consumer.router.RouteFactory;
import com.github.itonyli.common.entity.Request;
import com.github.itonyli.common.entity.Response;
import com.github.itonyli.common.entity.Route;
import com.github.itonyli.common.entity.URL;
import com.github.itonyli.common.exception.RpcException;
import com.github.itonyli.common.utils.SnowFlake;
import com.github.itonyli.remoting.NettyClient;
import com.google.common.reflect.AbstractInvocationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class ServiceProxy extends AbstractInvocationHandler {

    private static final Logger logger = LoggerFactory.getLogger(ServiceProxy.class);

    // TODO 考虑URL封装一下比如version字段
    private URL url;

    public ServiceProxy(URL url) {
        this.url = url;
    }

    @Override
    protected Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable {
        Route route = RouteFactory.get(url.getAppName(), url.getServiceName());

        if (route == null) {
            throw new RpcException("fail to get route of url:" + url);
        }

        Request request = new Request();
        request.setRequestId(SnowFlake.generateID());
        request.setServiceName(url.getAppName());
        request.setMethodName(url.getServiceName());
        request.setArgs(args);
        request.setArgsClass(method.getParameterTypes());

        NettyClient client = new NettyClient();
        Response response = client.call(request, route);

        // TODO response status code check

        return response.getResult();
    }
}
