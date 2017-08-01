package com.github.itonyli.common.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Response implements Serializable {

    private long   requestId;
    private int    statusCode;
    private Object result;
}
