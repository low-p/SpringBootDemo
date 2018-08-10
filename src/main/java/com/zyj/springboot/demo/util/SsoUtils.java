package com.zyj.springboot.demo.util;

import java.util.UUID;

/**
 * Created by mazhenhua on 2017/3/29.
 */
public class SsoUtils {

    public static String getUUID(){
        return "sso-" + UUID.randomUUID().toString();
    }

}
