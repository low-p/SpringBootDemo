package com.zyj.springboot.demo.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //设置输入时忽略JSON字符串中存在而Java对象实际没有的属性
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String objectToJson(Object o) {
        if (o == null)
            return null;

        String s = null;

        try {
            s = mapper.writeValueAsString(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public static <T> List<String> listObjectToListJson(List<T> objects) {
        if (objects == null)
            return null;

        List<String> lists = new ArrayList<String>();
        for (T t : objects) {
            lists.add(JsonUtils.objectToJson(t));
        }

        return lists;
    }

    public static <T> String listToJson(List<T> objects){
        if (null == objects || objects.isEmpty())
            return null;

        String s = null;

        try {
            s = mapper.writeValueAsString(objects);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public static <T> List<T> listJsonToListObject(List<String> jsons, Class<T> c) {
        if (jsons == null)
            return null;

        List<T> ts = new ArrayList<T>();
        for (String j : jsons) {
            ts.add(JsonUtils.jsonToObject(j, c));
        }

        return ts;
    }

    public static <T> T jsonToObject(String json, Class<T> c) {
        if (StringUtils.hasLength(json) == false)
            return null;

        T t = null;
        try {
            t = mapper.readValue(json, c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public static <T> T jsonToObject(String json, TypeReference<T> tr) {
        if (StringUtils.hasLength(json) == false)
            return null;

        T t = null;
        try {
            t = (T) mapper.readValue(json, tr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) t;
    }
}
