package com.github.itonyli.common.annotation;

import java.lang.annotation.*;

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface Provider {

    public String app();
    public String service();
    // TODO version
}
