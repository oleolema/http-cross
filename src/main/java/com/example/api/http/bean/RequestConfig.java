/**
 * FileName:   RequestConfig
 * Author:     O了吗
 * Date:       2020/2/26 13:42
 * Description:
 * History:
 * author:     oleolema
 */
package com.example.api.http.bean;

import com.example.api.http.common.Constant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.tomcat.util.bcel.Const;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * 〈〉
 *
 * @author O了吗
 * @create 2020/2/26
 * @since 1.0.0
 */
@Data
public class RequestConfig {

    private String url;
    private Header[] headers;
    private HttpEntity entity;
    private String responseType;
    private String responseEncoding;
    private ObjectMapper objectMapper = new ObjectMapper();

    private String entityString;

    private String protocol;

    private String defaultEncoding = "utf-8";

    private String config;

    public RequestConfig() {
    }

    public RequestConfig(HashMap<String, Object> config) {
        this.url = pauseUrl(config);
        this.headers = pauseHeader(config);
        this.responseType = pauseResponseType(config);
        this.responseEncoding = pauseResponseEncoding(config);
        this.entity = pauseStringEntity(config);
        this.config = config.toString();
        this.protocol = pauseProtocol(config);


    }

    public boolean isRequestJson() {
        Header contentTypeHeader = getHeader("Content-Type");
        if (contentTypeHeader == null) {
            return false;
        }
        return contentTypeHeader.getValue().trim().toLowerCase().startsWith("application/json");
    }

    public String getRequestEncoding() {
        Header contentTypeHeader = getHeader("Content-Type");
        if (contentTypeHeader == null) {
            return defaultEncoding;
        }
        String value = contentTypeHeader.getValue();
        int index = value.lastIndexOf("charset");
        if (index != -1) {
            return value.substring(index + 8);
        }
        return defaultEncoding;
    }

    public Header getHeader(String name) {
        for (Header header : headers) {
            if (header.getName().equalsIgnoreCase(name)) {
                return header;
            }
        }
        return null;
    }

    private String pauseResponseType(HashMap<String, Object> config) {
        Object responseType = config.get("responseType");
        return responseType == null ? null : ("" + responseType).toUpperCase();
    }


    private String pauseResponseEncoding(HashMap<String, Object> config) {
        Object responseEncoding = config.get("responseEncoding");
        return responseEncoding == null ? "utf-8" : "" + responseEncoding;
    }

    private String pauseUrl(HashMap<String, Object> config) {
        Object url = config.get("url");
        return url == null ? null : "" + url;
    }

    private Header[] pauseHeader(HashMap<String, Object> config) {
        Object obj = config.get("header");
        if (obj == null) {
            return null;
        }
        HashMap<String, String> headers = (HashMap<String, String>) obj;
        return headers.entrySet().stream()
                .map(entry -> new BasicHeader(entry.getKey(), entry.getValue()))
                .toArray(Header[]::new);
    }

    private String pauseProtocol(HashMap<String, Object> config) {
        String str = (String) config.get("protocol");
        if (str == null) {
            return Constant.Http_Protocol.HTTP_1;
        }
        return str;
    }


    private HttpEntity pauseStringEntity(HashMap<String, Object> config) {
        Object entity = config.get("entity");

        if (entity == null) {
            return null;
        }
        String stringEntity = "" + entity;
        if (isRequestJson()) {
            try {
                stringEntity = objectMapper.writeValueAsString(entity);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        this.entityString = stringEntity;
        return new StringEntity(stringEntity, getRequestEncoding());
    }

    @Override
    public String toString() {
        return config;
    }
}