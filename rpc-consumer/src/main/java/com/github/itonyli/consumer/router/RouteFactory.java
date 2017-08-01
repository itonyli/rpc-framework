package com.github.itonyli.consumer.router;

import com.github.itonyli.common.entity.Route;
import com.github.itonyli.common.entity.URL;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import javafx.util.Pair;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class RouteFactory {

    private static ConcurrentHashMap<Pair<String, String>, Set<Route>> collection = new ConcurrentHashMap<>();

    public static void putAsSafe(URL url, List<Route> routes) {
        Pair<String, String> key = new Pair<>(url.getAppName(), url.getServiceName());
        Set<Route> value = collection.getOrDefault(key, Sets.newConcurrentHashSet());
        value.addAll(routes);
        collection.put(key, value);
    }


    public static Route get(String appName, String serviceName) {
        Set<Route> routes = collection.get(new Pair<String, String>(appName, serviceName));
        if (routes != null && routes.size() > 0) {
            // TODO can think more balance strategy
            return Lists.newArrayList(routes).get(new Random().nextInt(routes.size()));
        }
        return null;
    }
}
