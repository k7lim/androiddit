package com.racecarlabs.androiddit.utils;

import android.text.TextUtils;

public class RedditUtils {

    public static boolean isImageUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        
        return url.toLowerCase().endsWith(".jpg");
    }

}
