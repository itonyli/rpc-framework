package com.github.itonyli.common.factory;

import com.google.common.collect.Maps;
import javafx.util.Pair;

import java.util.Map;

public class BeanFactory {

    private static Map<Pair<String, String>, Object> context = Maps.newConcurrentMap();

    public static void putAsSafe(String appName, String serviceName, Object obj) {
        context.put(new Pair<String, String>(appName, serviceName), obj);
    }

    public static Object acquire(String appName, String serviceName) {
        return context.get(new Pair<String, String>(appName, serviceName));
    }
}
