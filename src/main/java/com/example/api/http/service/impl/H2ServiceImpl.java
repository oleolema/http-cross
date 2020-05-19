/**
 * FileName:   H2ServiceImpl
 * Author:     yueqiuhong@xiaomi.com
 * Create:       2020/5/19 18:36
 * Description:
 */
package com.example.api.http.service.impl;

import com.example.api.http.bean.RequestConfig;
import com.example.api.http.service.GlobalService;
import com.example.api.http.util.GzipUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.zip.ZipException;

/**
 * 〈〉
 *
 * @author yueqiuhong@xiaomi.com
 * @create 2020/5/19
 * @since 1.0.0
 */

@Service
@Slf4j
public class H2ServiceImpl implements GlobalService {

    HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2) // 遵循HTTP协议的1.1版本
            .followRedirects(HttpClient.Redirect.NORMAL) // 正常的重定向
            .connectTimeout(Duration.ofMillis(10000)) // 连接的超时时间为10秒
            .build();


    @Override
    public Object post(RequestConfig config) throws IOException {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(config.getEntityString())) // 调用方式为POST
                .uri(URI.create(config.getUrl())); // 待调用的url地址
        setHeaders(builder, config);
        return execute(builder.build(), config);
    }


    @Override
    public Object get(RequestConfig config) throws IOException {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(config.getUrl())); // 待调用的url地址
        setHeaders(builder, config);
        return execute(builder.build(), config);
    }


    private void setHeaders(HttpRequest.Builder builder, RequestConfig config) {
        Header[] headers = config.getHeaders();
        for (int i = 0; i < headers.length; i++) {
            builder.setHeader(headers[i].getName(), headers[i].getValue());
        }
    }

    public Object execute(HttpRequest request, RequestConfig config) throws IOException {
        HttpResponse<InputStream> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        String body = null;
        byte[] bytes = response.body().readAllBytes();
        try {
            body = new String(GzipUtils.unGzip(bytes), config.getResponseEncoding());
        } catch (ZipException e) {
            body = new String(bytes, config.getResponseEncoding());
        }
        if ("JSON".equals(config.getResponseType())) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.readValue(body, Object.class);
            } catch (JsonProcessingException e) {
                return body;
            }
        } else {
            return body;
        }
    }


}