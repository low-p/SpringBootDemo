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

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 * <pre>
 * String工具类
 * </pre>
 *
 * @author zhaoyunji
 * @since 1.0.0
 */
public final class JsonStringUtils extends StringUtils {

    /**
     * 判断数据是否是json或者jsonarray类型 。
     * @param jsonData 。
     * @return Boolean 。
     */
    public static Boolean isJSONOrJSONArray(final String jsonData) {
        if (isBlank(jsonData)) {
            return false;
        }
        try {
            JSONObject.parseObject(jsonData);
            return true;
        } catch (final Exception e) {
            return isJSONArray(jsonData);
        }
    }

    /**
     * 判断数据是否是json类型 。
     * @param jsonData 。
     * @return Boolean 。
     */
    public static Boolean isJSON(final String jsonData) {
        if (isBlank(jsonData)) {
            return false;
        }
        try {
            JSONObject.parseObject(jsonData);
            return true;
        } catch (final Exception e) {
            return false;
        }
    }

    /**
     * 判断数据是否是jsonarray类型 。
     * @param jsonData 。
     * @return Boolean 。
     */
    public static Boolean isJSONArray(final String jsonData) {
        if (isBlank(jsonData)) {
            return false;
        }
        try {
            JSONObject.parseArray(jsonData);
            return true;
        } catch (final Exception e) {
            return false;
        }
    }
}
