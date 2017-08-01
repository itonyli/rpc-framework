package com.github.itonyli.register;

import com.github.itonyli.common.entity.Route;
import com.github.itonyli.common.entity.URL;
import com.github.itonyli.common.utils.RouteUtil;
import com.github.itonyli.common.utils.ZKPathUtil;
import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.List;

public class ZKRegister implements Register {

    private CuratorFramework curator;
    private static volatile ZKRegister register;

    private ZKRegister() {
    }

    private void startup() {
        // TODO should read from config
        curator = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
        curator.start();
    }


    @Override
    public void register(URL url) throws Exception {
        curator.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(ZKPathUtil.generateCreatePath(url));
    }

    @Override
    public void subscribe(URL url) throws Exception {
        curator.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(ZKPathUtil.generateCreatePath(url));
    }

    @Override
    public List<Route> getProviderRoutes(URL url) throws Exception {
        List<String> strings = curator.getChildren().forPath(ZKPathUtil.generateFindPath(url));
        List<Route> routes = Lists.newArrayList();
        for (String str : strings) {
            routes.add(RouteUtil.format(str));
        }
        return routes;
    }

    public static ZKRegister build() {
        if (register == null) {
            synchronized (ZKRegister.class) {
                if (register == null) {
                    register = new ZKRegister();
                    register.startup();
                }
            }
        }
        return register;
    }
}
