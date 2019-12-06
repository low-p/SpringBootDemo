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

/**
 * <pre>
 * (这里用一句话描述这个方法的作用)
 * </pre>
 *
 * @author zhaoyunji
 * @since 1.0.0
 */
public class NumberUtils {

    /**
     * 验证是否为浮点型
     * @param numberStr 。
     * @return boolean
     */
    public static boolean isDoubleNumber(final String numberStr){
        String doubleRegex = "^[0-9]+([.]{0,1}[0-9]+){0,1}$";
        return numberStr.matches(doubleRegex);
    }

    /**
     * 验证是否为整型
     * @param numberStr 。
     * @return boolean
     */
    public static boolean isIntegerNumber(final String numberStr){
        String integerRegex = "^[0-9]+$";
        return numberStr.matches(integerRegex);
    }
}
