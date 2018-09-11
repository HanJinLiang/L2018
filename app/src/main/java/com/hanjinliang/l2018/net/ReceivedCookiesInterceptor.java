package com.hanjinliang.l2018.net;

import android.content.SharedPreferences;

import com.blankj.utilcode.util.SPUtils;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by Administrator on 2018-09-11.
 */

public class ReceivedCookiesInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();
            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }
            SPUtils.getInstance("cookieConfig").put("cookie", cookies);
        }

        return originalResponse;
    }

}
