/**
 * FileName:   H2Test
 * Author:     yueqiuhong@xiaomi.com
 * Create:       2020/5/16 15:23
 * Description:
 */
package com.example.api.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

/**
 * 〈〉
 *
 * @author yueqiuhong@xiaomi.com
 * @create 2020/5/16
 * @since 1.0.0
 */
public class H2Test {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void test1() throws IOException {
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.protocols(Arrays.asList(Protocol.HTTP_2));
//        OkHttpClient okHttpClient = builder.build();

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