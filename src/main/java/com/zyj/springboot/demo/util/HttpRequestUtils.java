/*
 * Copyright © 2015-2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * @since 0.0.1
 */

package com.zyj.springboot.demo.util;

import com.github.pagehelper.util.StringUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Http请求工具类
 * </pre>
 *
 * @author zhaoyunji
 * @since 1.0.0
 */
public class HttpRequestUtils {

    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.22 Safari/537.36 SE 2.X MetaSr 1.0";

    /**
     * 发送GET请求
     * @param url 请求地址。
     * @param encoding 字符集。
     * @return  String 请求结果
     * @throws IOException 。
     */
    public static String sendGet(final String url, final String encoding) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("User-Agent", DEFAULT_USER_AGENT);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String result;
            if (StringUtil.isEmpty(encoding)) {
                result = EntityUtils.toString(entity);
            } else {
                result = EntityUtils.toString(entity, DEFAULT_ENCODING);
            }
            return result;
        } finally {
            httpClient.close();
        }
    }

    /**
     * 发送POST请求
     * @param url 请求地址。
     * @param encoding 字符集。
     * @param reqMap 请求参数。
     * @return String 请求结果。
     * @throws IOException 。
     */
    public static String sendPost(final String url, final String encoding, final Map<String, String> reqMap) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> reqParams = new ArrayList<>();
            if (null != reqMap) {
                for (String key : reqMap.keySet()) {
                    reqParams.add(new BasicNameValuePair(key, reqMap.get(key)));
                }
            }
            HttpEntity reqEntity = new UrlEncodedFormEntity(reqParams, DEFAULT_ENCODING);
            httpPost.setEntity(reqEntity);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity respEntity = response.getEntity();
            String result;
            if (StringUtil.isEmpty(encoding)) {
                result = EntityUtils.toString(respEntity);
            } else {
                result = EntityUtils.toString(respEntity, DEFAULT_ENCODING);
            }
            return result;
        } finally {
            httpClient.close();
        }
    }

    /**
     * 发送POST请求
     * @param url 请求地址。
     * @param encoding 字符集。
     * @param reqStr 请求参数。
     * @return String 请求结果。
     * @throws IOException 。
     */
    public static String sendPost(final String url, final String encoding, final String reqStr) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(url);

            StringEntity reqEntity = new StringEntity(reqStr, "utf-8");
            httpPost.setHeader("Content-Type", "application/json; charset=utf-8");
            httpPost.setEntity(reqEntity);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity respEntity = response.getEntity();
            String result;
            if (StringUtil.isEmpty(encoding)) {
                result = EntityUtils.toString(respEntity);
            } else {
                result = EntityUtils.toString(respEntity, DEFAULT_ENCODING);
            }
            return result;
        } finally {
            httpClient.close();
        }
    }
}
