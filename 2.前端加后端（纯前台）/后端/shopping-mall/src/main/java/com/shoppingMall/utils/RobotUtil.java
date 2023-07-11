package com.shoppingMall.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 在没有后台的情况下，我们借用一个chatGPT的接口进行和用户交互
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/1
 */
public class RobotUtil {
    /**
     * 一个chatGpt的接口
     */
    private static final String CG_ROBOT_URL = "https://api.forchange.cn/";

    /**
     * 一个小I机器人的接口,(备用聊天机器人)
     */
    private static final String XM_ROBOT_URL = "http://i.xiaoi.com/";

    /**
     * 网站过期，更换为小i
     */
    private static final String ERROR_ROBOT_TEXT = "您当前访问的网页或服务可能涉及盗版";

    /**
     * java11的全局httpClient
     */
    private static final HttpClient httpClient = HttpClient.newBuilder().build();

    /**
     * 获取消息(网页版的那个chat-gpt已不能使用，直接使用小i同学就好)
     *
     * @param message
     * @return
     */
    public static String getMessage(String message) {
        try {
            //(网页版的那个chat-gpt已不能使用，直接使用小i同学就好)
            return getXiaoMiMessage(message);
        } catch (Exception e) {
            return "机器人发生故障，可能是服务端没有网络！";
        }
//        try {
//            return getChatGptMessage(message);
//        } catch (Exception e) {
//            try {
//                return getXiaoMiMessage(message);
//            } catch (Exception eee) {
//                return "机器人发生故障，可能是服务端没有网络！";
//            }
//        }
    }

    /**
     * 获取chat_gpt的内容
     */
    private static String getChatGptMessage(String message) throws Exception {
        String body = String.format("{\"prompt\": \"Human:%s↵AI:\"}", message);
        HttpRequest request = HttpRequest.newBuilder(new URI(CG_ROBOT_URL))
                .header("Accept", "application/json, text/plain, */*")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Accept-Language", "zh-CN,zh;q=0.9")
                .header("Access-Control-Allow-Origin", "*")
                .header("Content-Type", "application/json")
                .header("Origin", "https://aigcfun.com")
                .header("Referer", "https://aigcfun.com/")
                .header("Sec-Fetch-Dest", "empty")
                .header("Sec-Fetch-Mode", "cors")
                .header("Sec-Fetch-Site", "cross-site")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36")
                .header("x-f-platform", "browser")
                .header("x-f-uid", UUID.randomUUID().toString())
                //请求时间允许超时100秒，因为它本身就太慢了
                .timeout(Duration.ofSeconds(200))
                .version(HttpClient.Version.HTTP_2)
                .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8)).build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String resultText = response.body();
        JSONObject entries = JSONUtil.parseObj(resultText);
        Object error = entries.get("error");
        JSONArray choices = entries.getJSONArray("choices");
        Map o = (Map) choices.get(0);
        if (ObjectUtil.isNull(error)) {
            String text = o.get("text").toString().replace("\n", "<br />").replace("<br /><br />", "<br />");
            if (text.contains(ERROR_ROBOT_TEXT)) {
                throw new Exception("找小i");
            }
        }
        throw new Exception("找小i");
    }

    /**
     * 配置小i同学的内容
     */
    private static String getXiaoMiMessage(String message) throws URISyntaxException, IOException, InterruptedException {
        String content = String.format("{\"sessionId\":\"246dc5b6ae6a4bf9b020533e8df1484b\",\"robotId\":\"webbot\",\"userId\":\"2f4ebbbbd7c743178993e293ad3c7981\",\"body\":{\"content\":\"%s\"},\"type\":\"txt\"}", message);
        String param = String.format("robot/webrobot?&callback=__webrobot_processMsg&data=%s&ts=%s", URLEncoder.encode(content, StandardCharsets.UTF_8), System.currentTimeMillis());
        HttpRequest request = HttpRequest.newBuilder(new URI(XM_ROBOT_URL + param))
                .header("Referer", "http://i.xiaoi.com/")
                .headers("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36")
                //这个其实超时20秒，有点多，这个很快，词汇量比较少
                .timeout(Duration.ofSeconds(100))
                .version(HttpClient.Version.HTTP_2)
                .GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String responseText = response.body();
        String replace = responseText.replaceFirst("content", "xxxx");
        String pattern = "\"content\":\"([\\s\\S]+?)\",";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(replace);
        if (m.find()) {
            String resultMessage = m.group(1);
            if (resultMessage.contains("defaultReply")) {
                return "你的问题太复杂了，我回答不了";
            }
            resultMessage = resultMessage.replace("\\\"", "\"").replace("\\r\\n", "<br />").replace("[link url\\u003d", "<a href=")
                    .replace("[/link]", "</a>").replace("]", ">");
            return resultMessage;
        }
        return "你的问题太复杂了，我回答不了";
    }
}
