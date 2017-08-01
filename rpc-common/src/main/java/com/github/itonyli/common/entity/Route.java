package com.github.itonyli.common.entity;

import lombok.Data;

@Data
public class Route {
    private String  address;
    private Integer port;

    public Route() {
    }

    public Route(String address, Integer port) {
        this.address = address;
        this.port = port;
    }
}
