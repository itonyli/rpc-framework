package com.github.itonyli.common.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Route implements Serializable {
    private String  address;
    private Integer port;

    public Route() {
    }

    public Route(String address, Integer port) {
        this.address = address;
        this.port = port;
    }
}
