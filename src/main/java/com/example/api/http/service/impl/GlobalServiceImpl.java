/**
 * FileName:   GlobalServiceImpl
 * Author:     O了吗
 * Date:       2020/2/26 10:39
 * Description:
 * History:
 * author:     oleolema
 */
package com.example.api.http.service.impl;

import com.example.api.http.bean.RequestConfig;
import com.example.api.http.service.GlobalService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 〈〉
 *
 * @author O了吗
 * @create 2020/2/26
 * @since 1.0.0
 */
@Service
@Slf4j
@CacheConfig(cacheNames = {"http"})
public class GlobalServiceImpl implements GlobalService {

    @Autowired
    CloseableHttpClient closeableHttpClient;

    @Override
    public Object post(RequestConfig config) throws IOException {
        log.info("POST " + config);
        HttpPost httpPost = new HttpPost(config.getUrl());
        httpPost.setHeaders(config.getHeaders());
        httpPost.setEntity(config.getEntity());
        return execute(httpPost, config.getResponseType(), config.getResponseEncoding());
    }


    @Override
    public Object get(RequestConfig config) throws IOException {
        log.info("GET " + config);
        HttpGet httpGet = new HttpGet(config.getUrl());
        httpGet.setHeaders(config.getHeaders());
        return execute(httpGet, config.getResponseType(), config.getResponseEncoding());
    }


    public Object execute(HttpUriRequest httpUriRequest, String type, String encoding) throws IOException {
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            closeableHttpResponse = closeableHttpClient.execute(httpUriRequest);
            String body = IOUtils.toString(closeableHttpResponse.getEntity().getContent(), encoding);
            if ("JSON".equals(type)) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    return objectMapper.readValue(body, Object.class);
                } catch (JsonProcessingException e) {
                    return body;
                }
            } else {
                return body;
            }
        } finally {
            try {
                if (closeableHttpResponse != null) {
                    closeableHttpResponse.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}