package com.zyj.springboot.demo.util;

import java.util.UUID;

public class UUIDUtils {

    public static String UUIDStr(){
        String uuidstr = UUID.randomUUID().toString();
        uuidstr = uuidstr.toUpperCase();
        return uuidstr.replaceAll("-", "");
    }

}
