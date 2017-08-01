package com.github.itonyli.common.annotation;

import java.lang.annotation.*;

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface Consumer {

    public String app();
    public String service();
    // TODO version
}
