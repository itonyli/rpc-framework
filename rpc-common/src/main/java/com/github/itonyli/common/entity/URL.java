package com.github.itonyli.common.entity;

import lombok.Data;

@Data
public class URL {

    private String  appName;
    private String  serviceName;
    private String  host;
    private int     port; // just for provider
    private boolean isProviders;
}
