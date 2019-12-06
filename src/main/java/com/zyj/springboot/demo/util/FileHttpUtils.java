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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * <pre>
 * (这里用一句话描述这个方法的作用)
 * </pre>
 *
 * @author zhaoyunji
 * @since 1.0.0
 */
public class FileHttpUtils {

    public static String submitFileRequest(final String url, final File file, final String fileParName, final Map<String, String> params, final int timeout) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(url);
            // 设置连接超时时间
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout)
                    .setConnectionRequestTimeout(timeout).setSocketTimeout(timeout).build();
            httpPost.setConfig(requestConfig);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(java.nio.charset.Charset.forName("UTF-8"));

            // .addPart()可以设置模拟浏览器<input/>的表单提交, 匹配@RequestParam("file")
            FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);
            builder.addPart(fileParName, fileBody);
            if (null != params && !params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    builder.addTextBody(entry.getKey(), entry.getValue(), ContentType.create("text/plain", "UTF-8"));
                }
            }
            httpPost.setEntity(builder.build());
            // 执行提交
            HttpResponse response = httpClient.execute(httpPost);

            HttpEntity responseEntity = response.getEntity();
            if (null != responseEntity) {
                // 将响应内容转换为字符串
                return EntityUtils.toString(responseEntity, java.nio.charset.Charset.forName("UTF-8"));
            }
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
