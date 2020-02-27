/**
 * FileName:   GlobalService
 * Author:     O了吗
 * Date:       2020/2/26 10:38
 * Description:
 * History:
 * author:     oleolema
 */
package com.example.api.http.service;

import com.example.api.http.bean.RequestConfig;

import java.io.IOException;

/**
 * 〈〉
 *
 * @author O了吗
 * @create 2020/2/26
 * @since 1.0.0
 */
public interface GlobalService {

    Object post(RequestConfig config) throws IOException;

    Object get(RequestConfig config) throws IOException;
}