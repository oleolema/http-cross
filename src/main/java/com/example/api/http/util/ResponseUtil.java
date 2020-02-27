/**
 * FileName:   ResponseUtil
 * Author:     O了吗
 * Date:       2020/2/26 10:46
 * Description:
 * History:
 * author:     oleolema
 */
package com.example.api.http.util;

import java.util.HashMap;

/**
 * 〈〉
 *
 * @author O了吗
 * @create 2020/2/26
 * @since 1.0.0
 */
public enum ResponseUtil {

    OK(0, "ok"),
    ERROR(1, "error");

    private int code;
    private String desc;
    private HashMap<String, Object> map;

    ResponseUtil(int code, String desc) {
        this.code = code;
        this.desc = desc;
        map = new HashMap<>();
    }

    public HashMap<String, Object> msg(Object data, String msg) {
        map.put("code", code);
        map.put("desc", desc);
        map.put("msg", msg);
        map.put("data", data);
        return map;
    }

    public HashMap<String, Object> msg(Object data) {
        map.put("code", code);
        map.put("desc", desc);
        map.put("data", data);
        return map;
    }


}