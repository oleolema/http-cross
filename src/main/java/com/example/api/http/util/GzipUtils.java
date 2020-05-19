/**
 * FileName:   GzipUtils
 * Author:     yueqiuhong@xiaomi.com
 * Create:       2020/5/19 18:42
 * Description:
 */
package com.example.api.http.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

/**
 * 〈〉
 *
 * @author yueqiuhong@xiaomi.com
 * @create 2020/5/19
 * @since 1.0.0
 */
public class GzipUtils {

    public static byte[] unGzip(byte[] content) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(content));
        byte[] buffer = new byte[1024];
        int n;
        while ((n = gis.read(buffer)) != -1) {
            baos.write(buffer, 0, n);
        }
        return baos.toByteArray();
    }

}