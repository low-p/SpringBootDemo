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

package com.zyj.springboot.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * <pre>
 * (这里用一句话描述这个方法的作用)
 * </pre>
 *
 * @author zhaoyunji
 * @since 1.0.0
 */
public class ReadFileTest {
    public static void main(final String[] args) throws IOException {
        String fileRealName = UUID.randomUUID().toString().replaceAll("-", "").toLowerCase() + ".xlsx";
        String saveFilePath = "importTemp/" + fileRealName;
        Path filePath = Paths.get(saveFilePath).toAbsolutePath();
        System.out.println(filePath);
        if (!Files.exists(filePath.getParent())) {
            Files.createDirectory(filePath.getParent());
        }
        Files.write(filePath, Files.readAllBytes(Paths.get("D:\\JavaTest\\order-data.xlsx")));
    }
}
