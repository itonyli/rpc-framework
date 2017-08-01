package com.github.itonyli.common.entity;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Data
public class Request implements Serializable {

    private long       requestId;
    private String     serviceName;
    private String     methodName;
    private Class<?>[] argsClass;
    private Object[]   args;
}
