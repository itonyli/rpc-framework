package com.github.itonyli.common;


/**
 * TODO 不知道会遇到什么坑，不敢自定义classloader挂载到类加载器检索自定义注解，还是基于spring来检索吧。。。。
 */

@Deprecated
public class RpcClassLoader extends ClassLoader {

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }
}
