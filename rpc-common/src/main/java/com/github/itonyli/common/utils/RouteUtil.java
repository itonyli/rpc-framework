package com.github.itonyli.common.utils;

import com.github.itonyli.common.entity.Route;

public class RouteUtil {

    public static Route format(String str) {
        String[] strs = str.split(":");
        return new Route(strs[0], Integer.valueOf(strs[1]));
    }
}
