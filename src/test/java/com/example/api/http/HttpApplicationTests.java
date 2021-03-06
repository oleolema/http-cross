package com.example.api.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@Slf4j
class HttpApplicationTests {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void contextLoads() {
        final int[] a = {0};
        new Thread(() -> {
            a[0] = 1;
        }).start();
    }

    @Test
    void test1() throws IOException {


        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        String requestBody = "{\"keyword\": \"4684685\"}";
        Request request = new Request.Builder()
                .url("https://app.51xuexiaoyi.com/api/v1/searchQuestion")
                .post(RequestBody.create(mediaType, requestBody))
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response = okHttpClient.newCall(request).execute();


        System.out.println(response.protocol() + " " + response.code() + " " + response.message());
        Headers headers = response.headers();
        for (int i = 0; i < headers.size(); i++) {
            System.out.println(headers.name(i) + ":" + headers.value(i));
        }
        System.out.println("onResponse: " + objectMapper.readValue(response.body().string(), Object.class));
        System.out.println("over");
    }

}
