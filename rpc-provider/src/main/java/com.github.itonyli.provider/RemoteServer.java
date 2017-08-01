package com.github.itonyli.provider;

import com.github.itonyli.common.Constants;
import com.github.itonyli.remoting.NettyServer;

import javax.annotation.PostConstruct;

public class RemoteServer {

    @PostConstruct
    public void start() {
        new NettyServer().startup(Constants.PROVIDER_SERVICE_PORT);
    }
}
