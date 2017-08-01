package com.github.itonyli.provider.init;

import com.github.itonyli.common.Constants;
import com.github.itonyli.common.annotation.Provider;
import com.github.itonyli.common.entity.URL;
import com.github.itonyli.common.exception.RpcException;
import com.github.itonyli.common.utils.NetUtil;
import com.github.itonyli.common.factory.BeanFactory;
import com.github.itonyli.register.ZKRegister;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;

public class RegisterBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = bean.getClass().getMethods();
        for (Method method : methods) {
            Provider annotation = method.getAnnotation(Provider.class);
            if (annotation != null) {
                URL url = new URL();
                url.setAppName(annotation.app());
                url.setServiceName(annotation.service());
                url.setHost(NetUtil.getConnectingHost());
                url.setPort(Constants.PROVIDER_SERVICE_PORT);
                url.setProviders(true);

                try {
                    ZKRegister.build().register(url);
                } catch (Exception e) {
                    throw new RpcException("fail to register, please check zookeeper config!", e);

                }

                BeanFactory.putAsSafe(annotation.app(), annotation.service(), bean);
            }
        }
        return bean;
    }
}
