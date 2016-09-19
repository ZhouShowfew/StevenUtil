package com.steven.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by SkyEyesS3 on 2016/2/25.
 * 获取链接的后缀名
 */
public class ParseSuffix {
    final static Pattern pattern = Pattern.compile("\\S*[?]\\S*");

    public static String parseSuffix(String url) {

        Matcher matcher = pattern.matcher(url);

        String[] spUrl = url.toString().split("/");
        int len = spUrl.length;
        String endUrl = spUrl[len - 1];

        if (matcher.find()) {
            String[] spEndUrl = endUrl.split("\\?");
            return spEndUrl[0].split("\\.")[1];
        }
        if (!endUrl.contains(".")) {
            return endUrl;
        } else {
            return endUrl.split("\\.")[1];
        }

    }
}
