package com.github.itonyli.register;

import com.github.itonyli.common.entity.Route;
import com.github.itonyli.common.entity.URL;

import java.util.List;

public interface Register {

    public void register(URL url) throws Exception;

    public void subscribe(URL url) throws Exception;

    public List<Route> getProviderRoutes(URL url) throws Exception;
}
