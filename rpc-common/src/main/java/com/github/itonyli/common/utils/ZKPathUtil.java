package com.github.itonyli.common.utils;

import com.github.itonyli.common.Constants;
import com.github.itonyli.common.entity.URL;

public class ZKPathUtil {

    private static final String NODE_SEPARATOR = "/";
    private static final String PORT_SEPARATOR = ":";
    private static final String PROVIDERS_NODE = "providers";
    private static final String CONSUMERS_NODE = "consumers";


    public static String generateCreatePath(URL url) {
        StringBuilder sb = new StringBuilder(Constants.ZK_ROOT)
                .append(NODE_SEPARATOR)
                .append(url.getAppName())
                .append(NODE_SEPARATOR)
                .append(url.getServiceName())
                .append(NODE_SEPARATOR);

        if (url.isProviders()) {
            sb.append(PROVIDERS_NODE);
        } else {
            sb.append(CONSUMERS_NODE);
        }

        sb.append(NODE_SEPARATOR)
                .append(url.getHost());

        if (url.isProviders()) {
            sb.append(PORT_SEPARATOR)
                    .append(url.getPort());
        }

        return sb.toString();
    }

    public static String generateFindPath(URL url) {
        StringBuilder sb = new StringBuilder(Constants.ZK_ROOT)
                .append(NODE_SEPARATOR)
                .append(url.getAppName())
                .append(NODE_SEPARATOR)
                .append(url.getServiceName())
                .append(NODE_SEPARATOR);

        if (url.isProviders()) {
            sb.append(PROVIDERS_NODE);
        } else {
            sb.append(CONSUMERS_NODE);
        }
        return sb.toString();
    }
}
