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

import com.dingtalk.chatbot.DingtalkChatbotClient;
import com.dingtalk.chatbot.message.TextMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 机器人测试类
 * </pre>
 *
 * @author zhaoyunji
 * @since 1.0.0
 */
public class DingDingRobotTest {

    //private static final String url = "https://oapi.dingtalk.com/robot/send?access_token=dfce2af0de052fe6807f96c37496bbbe5fb78de68c54dceb3b8cd01c6fa74390";
    private static final String url = "https://oapi.dingtalk.com/robot/send?access_token=3f1736d7ae40d386a0eea2839ab6b827ff671894a015883eac1fd446a9ebac58";

    /**
     * @param args 。
     */
    public static void main(final String[] args) throws IOException {
        /*JSONObject object = new JSONObject();
        object.put("msgtype", "text");
        JSONObject content = new JSONObject();
        content.put("content", "大家好, 我是小艾, @18638359332 是不一样的烟火");
        object.put("text", content.toJSONString());
        JSONObject at = new JSONObject();
        JSONArray array = new JSONArray();
        array.add("18638359332");
        array.add("15973754415");
        array.add("15589868100");
        at.put("atMobiles", array.toJSONString());
        at.put("isAtAll", true);
        object.put("at", at.toJSONString());
        String resContent = object.toJSONString();
        String result = HttpRequestUtils.sendPost(url, "UTF-8", resContent);
        System.out.println(result);*/
        List<String> atMobiles = new ArrayList<>();
        atMobiles.add("15973754415");
        atMobiles.add("18638359332");
        DingtalkChatbotClient chatbotClient = new DingtalkChatbotClient();
        TextMessage message = new TextMessage("大家好, 我是小艾, 是不一样的烟火");
        message.setAtMobiles(atMobiles);
        message.setIsAtAll(false);
        chatbotClient.send(url, message);
    }
}
