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

import com.zyj.springboot.demo.util.FileHttpUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * (这里用一句话描述这个方法的作用)
 * </pre>
 *
 * @author zhaoyunji
 * @since 1.0.0
 */
public class UploadFileTest {

    public static void main(final String[] args) {
        Path path = Paths.get("D:\\JavaTest\\test-100w-data.xlsx");
        System.out.println(path.toString());
        String fileKey = "importFile";
        File file = path.toFile();
        Map<String, String> params = new HashMap<>();
        params.put("userTag", "test006");
        params.put("systemCode", "A0001");
        params.put("dbConfInfo", "{\"dbConfCode\": \"db6\"}");
        params.put("params", "{" +
                "    \"importType\": \"xlsx\"," +
                "    \"fields\": [{" +
                "    \"columnName\": \"id\"," +
                "    \"titleName\": \"ID\"," +
                "    \"ignore\": true," +
                "    \"blankToNull\": false" +
                "}, {" +
                " \"columnName\": \"name\"," +
                "    \"titleName\": \"名称\"," +
                "    \"ignore\": false," +
                "    \"blankToNull\": false" +
                "}, {" +
                " \"columnName\": \"age\"," +
                "    \"titleName\": \"年龄\"," +
                "    \"ignore\": false," +
                "    \"blankToNull\": false" +
                "}, {" +
                "\"columnName\": \"create_time\"," +
                "    \"titleName\": \"新增时间\"," +
                "    \"ignore\": false," +
                "    \"blankToNull\": false" +
                "}, {" +
                " \"columnName\": \"update_time\"," +
                "    \"titleName\": \"更新时间\"," +
                "    \"ignore\": false," +
                "    \"blankToNull\": false" +
                "}]," +
                "    \"tableName\": \"t_user\"," +
                "    \"ignoreFirstRow\": \"false\"," +
                "    \"ignoreFirstColumn\": \"true\"," +
                "    \"distinctFields\": \"id\"," +
                "    \"duplicateOpt\": \"1\"" +
                "}");
        String result = FileHttpUtils.submitFileRequest("http://localhost:8080/report-manager/task/addImport", file, fileKey, params, 180000000);
        System.out.println(result);
    }
}
