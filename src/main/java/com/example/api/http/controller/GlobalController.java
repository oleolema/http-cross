/**
 * FileName:   GlobalController
 * Author:     O了吗
 * Date:       2020/2/26 10:38
 * Description:
 * History:
 * author:     oleolema
 */
package com.example.api.http.controller;

import com.example.api.http.bean.RequestConfig;
import com.example.api.http.service.GlobalService;
import com.example.api.http.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;

/**
 * 〈〉
 *
 * @author O了吗
 * @create 2020/2/26
 * @since 1.0.0
 */

@RestController
public class GlobalController {

    @Autowired
    GlobalService globalService;
    
    @PostMapping("/post")
    public Object post(@RequestBody HashMap<String, Object> config) {
        try {
            return ResponseUtil.OK.msg(globalService.post(new RequestConfig(config)));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseUtil.ERROR.msg(e);
        }
    }

    @PostMapping("/get")
    public Object get(@RequestBody HashMap<String, Object> config) {
        try {
            return ResponseUtil.OK.msg(globalService.get(new RequestConfig(config)));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseUtil.ERROR.msg(e);
        }
    }
}