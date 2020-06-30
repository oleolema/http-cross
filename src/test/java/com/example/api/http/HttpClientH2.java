/**
 * FileName:   HttpClientH2
 * Author:     yueqiuhong@xiaomi.com
 * Create:       2020/5/19 17:01
 * Description:
 */
package com.example.api.http;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

/**
 * 〈〉
 *
 * @author yueqiuhong@xiaomi.com
 * @create 2020/5/19
 * @since 1.0.0
 */
public class HttpClientH2 {

    @Test
    public void test1() {
        // 创建一个自定义的HTTP客户端对象
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1) // 遵循HTTP协议的1.1版本
                .followRedirects(HttpClient.Redirect.NORMAL) // 正常的重定向
                .build();

        // 创建一个自定义的HTTP请求对象
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString("{\"keyword\": \"123456\"}")) // 调用方式为POST
//                .uri(URI.create("https://app.51xuexiaoyi.com/api/v1/searchQuestion")) // 待调用的url地址
                .uri(URI.create("http://httpbin.org/post"))
                .header("token", "C0OLcQzrNY3oG3CrMK4t7Mbk7e2xFMGwTsPFmW1C1dvaZmO11Gn8SNC6uzEt")
                .header("device", "AnR-DEQbhev2wTdYTWaaBSAAArsihxlpIyFMGwTsPFmW")
                .header("platform", "android")
                .header("app-version", "1.0.6")
                .header("t", System.currentTimeMillis() + "")
                .header("s", UUID.randomUUID().toString().replaceAll("-", ""))
                .header("content-type", "application/json; charset=utf-8")
                .header("accept-encoding", "gzip")
                .header("user-agent", "okhttp/3.11.0")
                .build();

        try {
            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
            System.out.println(new String(response.body().readAllBytes()));
//            System.out.println(new String(GzipUtils.unGzip(response.body().readAllBytes())));

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3() {
        System.out.println(System.currentTimeMillis());
        System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
    }


}