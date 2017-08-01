package com.github.itonyli.consumer.init;

import com.github.itonyli.common.annotation.Consumer;
import com.github.itonyli.common.entity.Route;
import com.github.itonyli.common.entity.URL;
import com.github.itonyli.common.exception.RpcException;
import com.github.itonyli.common.utils.NetUtil;
import com.github.itonyli.consumer.router.RouteFactory;
import com.github.itonyli.register.ZKRegister;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;
import java.util.List;

public class SubscribeBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            Consumer annotation = field.getAnnotation(Consumer.class);
            if (annotation != null) {
                URL url = new URL();
                url.setAppName(annotation.app());
                url.setServiceName(annotation.service());
                url.setHost(NetUtil.getConnectingHost());
                url.setProviders(false);

                try {
                    ZKRegister.build().subscribe(url);
                } catch (Exception e) {
                    throw new RpcException("fail to subscribe, please check zookeeper config!", e);
                }

                url.setProviders(true);
                try {
                    List<Route> routes = ZKRegister.build().getProviderRoutes(url);
                    RouteFactory.putAsSafe(url, routes);
                } catch (Exception e) {
                    throw new RpcException("fail to get routes from register!", e);
                }
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
