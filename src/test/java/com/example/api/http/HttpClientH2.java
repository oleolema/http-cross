/**
 * FileName:   HttpClientH2
 * Author:     yueqiuhong@xiaomi.com
 * Create:       2020/5/19 17:01
 * Description:
 */
package com.example.api.http;

import com.example.api.http.util.GzipUtils;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.zip.GZIPInputStream;

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
                .version(HttpClient.Version.HTTP_2) // 遵循HTTP协议的1.1版本
                .followRedirects(HttpClient.Redirect.NORMAL) // 正常的重定向
                .connectTimeout(Duration.ofMillis(5000)) // 连接的超时时间为5秒
                .build();

        // 创建一个自定义的HTTP请求对象
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString("{\"keyword\": \"123456\"}")) // 调用方式为POST
                .uri(URI.create("https://app.51xuexiaoyi.com/api/v1/searchQuestion")) // 待调用的url地址
                .header("token", "eMvHSVul3bOVq9USDR3txrpVchIPDlHHB6nSzvOdo3H8a9cx3S2nu74sIlHL")
                .header("device", "AnR-DEQbhev2wTdYTWaaBSAAArsihxlpIyPyNJRP3ayN")
                .header("platform", "android")
                .header("app-version", "1.0.6")
                .header("t", "1589882032538")
                .header("s", "8c5cd2e9dcc2956ded95a981646df0dc")
                .header("content-type", "application/json; charset=utf-8")
                .header("accept-encoding", "gzip")
                .header("user-agent", "okhttp/3.11.0")
                .timeout(Duration.ofMillis(5000)) // 请求的超时时间为5秒
                .build();

        try {
            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

            System.out.println(new String(GzipUtils.unGzip(response.body().readAllBytes())));

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


}