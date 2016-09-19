package com.steven.util;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

/**
 * Created by SkyEyesS3 on 2016/1/4.
 * 一般工具类
 */
public class Tools {
    /**
     * 返回字符串第一个字符
     */
    public static String returnFirstString(String imgurl) {
        if (imgurl.contains(",")) {
            String[] str = imgurl.split(",");
            imgurl = str[0];
        }

        return imgurl;
    }

    public static SpannableString SpanUtil(String user_name, String content) {

        SpannableString ss = new SpannableString(
                user_name + " : " + content);
        ss.setSpan(new ForegroundColorSpan(Color.parseColor("#2b5a83")), 0, user_name.length(),
                Spanned
                        .SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }
}
