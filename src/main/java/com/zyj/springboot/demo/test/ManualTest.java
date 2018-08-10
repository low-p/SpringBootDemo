package com.zyj.springboot.demo.test;

import com.zyj.springboot.demo.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManualTest {
    public static final Logger logger = LoggerFactory.getLogger(ManualTest.class);

    private static String prersonPassUrl = "css.*,ico.*,images.*,js.*,webjars.*,index.*,login.*";

    private static String[] prersonPassUrls;

    protected static List<Pattern> patterns = new ArrayList<Pattern>();


    public static void main(String[] args){
        prersonPassUrls = prersonPassUrl.split(",");
        for (String url : prersonPassUrls) {
            Pattern p = Pattern.compile(url);
            patterns.add(p);
        }
        logger.info("无需过滤资源>>>>>>>>>>>>>>>>>>>>>" + JsonUtils.listToJson(patterns));

        String url = "login";
        //String url1 = "js/jquery.min.js";

        boolean flag = false;
        for (Pattern pattern : patterns) {
            logger.info("Patten>>>>>>" + pattern);
            Matcher matcher = pattern.matcher(url);
            logger.info("Matcher>>>>>" + matcher);
            if (matcher.matches()) {
                flag = true;
            }
        }
        logger.info(">>>>>>>>>>>>>>>>>>>>>>" + flag);
    }
}
