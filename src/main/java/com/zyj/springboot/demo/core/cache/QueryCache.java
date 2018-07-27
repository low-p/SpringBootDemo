package com.zyj.springboot.demo.core.cache;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface QueryCache {
    CacheNameSpace nameSpace();
}
