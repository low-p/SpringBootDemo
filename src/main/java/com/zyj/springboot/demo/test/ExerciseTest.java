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

package com.zyj.springboot.demo.test;

import com.zyj.springboot.demo.entity.StudentInfo;
import com.zyj.springboot.demo.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <pre>
 * (这里用一句话描述这个方法的作用)
 * </pre>
 *
 * @author zhaoyunji
 * @since 1.0.0
 */
public class ExerciseTest {
    private static final Logger logger = LoggerFactory.getLogger(ExerciseTest.class);
    /**
     * @param args 。
     */
    public static void main(final String[] args) throws IOException {
        /**
         * 数组排序
         */
        /*String[] strArray = {"444444", "222222", "333333", "111111"};
        logger.info(JsonUtils.listToJson(Arrays.asList(strArray)));
        Arrays.sort(strArray);
        logger.info(JsonUtils.listToJson(Arrays.asList(strArray)));*/
        /**
         * 读取文件单词，获取指定长度单词个数
         */
        /*String contents = new String(Files.readAllBytes(Paths.get("D://JavaTest//alice.txt")), StandardCharsets.UTF_8);
        List<String> words = Arrays.asList(contents.split("[\\P{L}]+"));
        logger.info("读取文本内容>>>>>>{}", JsonUtils.listToJson(words));
        int count = 0, defaultLength = 9;
        for (String w : words) {
            if (w.length() > defaultLength) {
                logger.info("长度大于{}的单词>>>>>>{}", defaultLength, w);
                count++;
            }
        }
        logger.info("长度大于{}的单词数量>>>>>>{}", defaultLength, count);
        long countWords = words.stream().filter(w -> w.length() > defaultLength).count();
        logger.info("长度大于{}的单词数量>>>>>>{}", defaultLength, countWords);*/
        /**
         * 结果集收集到Map中
         */
        List<StudentInfo> studentInfos = new ArrayList<>();
        studentInfos.add(new StudentInfo(){{setsId(1); setsName("张三"); setAge(12); setSex("男");}});
        studentInfos.add(new StudentInfo(){{setsId(2); setsName("李四"); setAge(22); setSex("女");}});
        studentInfos.add(new StudentInfo(){{setsId(3); setsName("王五"); setAge(32); setSex("男");}});
        studentInfos.add(new StudentInfo(){{setsId(4); setsName("赵六"); setAge(42); setSex("女");}});
        studentInfos.add(new StudentInfo(){{setsId(5); setsName("陈七"); setAge(52); setSex("男");}});
        studentInfos.add(new StudentInfo(){{setsId(6); setsName("洪八"); setAge(62); setSex("女");}});
        Map<Integer, String> students = studentInfos.stream().collect(Collectors.toMap(StudentInfo::getsId, StudentInfo::getsName));
        logger.info(JsonUtils.objectToJson(students));

        studentInfos.stream().forEach(System.out::println);
    }
}
