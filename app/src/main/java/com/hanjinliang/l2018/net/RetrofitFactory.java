package com.hanjinliang.l2018.net;

import android.support.annotation.NonNull;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.hanjinliang.l2018.ui.main.L2018Application;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by caijun on 2018/5/26.
 * 功能介绍：网络请求基类
 */

public class RetrofitFactory {
    public final static String BASEAPI="https://www.fddlpz.com/note/";
    // public final static String BASEAPI="https://tcc.taobao.com/";
//    public final static String BASEAPI2="http://www.wanandroid.com/";

    private volatile static Retrofit retrofit;

    @NonNull
    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (RetrofitFactory.class) {
                if (retrofit == null) {
                        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(15, TimeUnit.SECONDS)
                            .retryOnConnectionFailure(true);

                    //setHttps(builder);
                    // builder.sslSocketFactory(getSSLSocketFactory(new Buffer().writeUtf8(CER_L2018).inputStream()));
                    //使用自定义的Log拦截器
                    builder.addInterceptor(new LoggingInterceptor());
                    //cookie持久化
                    ClearableCookieJar cookieJar =
                            new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(L2018Application.APP));
                    builder.cookieJar(cookieJar);
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASEAPI)
                            .client(builder.build())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }

    /**
     *设置https 有所有的通过
     * @param builder
     */
    private static void setHttps(OkHttpClient.Builder builder) {
        //信任所有服务器地址
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                //设置为true
                return true;
            }
        });
        //创建管理器
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] x509Certificates,
                    String s) throws java.security.cert.CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] x509Certificates,
                    String s) throws java.security.cert.CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[] {};
            }
        } };
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            //为OkHttpClient设置sslSocketFactory
            builder.sslSocketFactory(sslContext.getSocketFactory());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //根据网站cer证书得到的
    private static String CER_L2018 ="-----BEGIN CERTIFICATE-----\n" +
            "MIIFkTCCBHmgAwIBAgIQA4A5OhVq8Qdzhau6ppHMcTANBgkqhkiG9w0BAQsFADBy\n" +
            "MQswCQYDVQQGEwJDTjElMCMGA1UEChMcVHJ1c3RBc2lhIFRlY2hub2xvZ2llcywg\n" +
            "SW5jLjEdMBsGA1UECxMURG9tYWluIFZhbGlkYXRlZCBTU0wxHTAbBgNVBAMTFFRy\n" +
            "dXN0QXNpYSBUTFMgUlNBIENBMB4XDTE4MDgzMTAwMDAwMFoXDTE5MDgzMTEyMDAw\n" +
            "MFowFTETMBEGA1UEAxMKZmRkbHB6LmNvbTCCASIwDQYJKoZIhvcNAQEBBQADggEP\n" +
            "ADCCAQoCggEBAMR2apsjsEpBmvt94NmdncTV6ZC//nsaOiAwF3i9XWZktZmwP4YM\n" +
            "BrfMnfF1G6O5plmAVQ+i+3EDXLNZRmH3yxxhnnSnVS/4H556cDGlTp6754xr0xcJ\n" +
            "mMdIpO49nRZR3z475567DZ+5M2kV9gSpn/KRB8B/6smxSaeqvpBOQh28SXc2fSMg\n" +
            "iunhWYxEEFK6q+s0lI0cuaSQnSAbJHwMRNy62kn0rchJcink3Kcd+UqBCwq93dq7\n" +
            "7TEZVVC2PKqtt9qvjW+DuO0ECMWoQhryWMfGVK4ZalfRdY7N7giihinFKUls34HX\n" +
            "vLPak7dfK/Ub+oB3ijom9wqd0spv22Kf5jsCAwEAAaOCAn4wggJ6MB8GA1UdIwQY\n" +
            "MBaAFH/TmfOgRw4xAFZWIo63zJ7dygGKMB0GA1UdDgQWBBTUNCyRIjQUKaJESCig\n" +
            "29b6D7IoPzAlBgNVHREEHjAcggpmZGRscHouY29tgg53d3cuZmRkbHB6LmNvbTAO\n" +
            "BgNVHQ8BAf8EBAMCBaAwHQYDVR0lBBYwFAYIKwYBBQUHAwEGCCsGAQUFBwMCMEwG\n" +
            "A1UdIARFMEMwNwYJYIZIAYb9bAECMCowKAYIKwYBBQUHAgEWHGh0dHBzOi8vd3d3\n" +
            "LmRpZ2ljZXJ0LmNvbS9DUFMwCAYGZ4EMAQIBMIGBBggrBgEFBQcBAQR1MHMwJQYI\n" +
            "KwYBBQUHMAGGGWh0dHA6Ly9vY3NwMi5kaWdpY2VydC5jb20wSgYIKwYBBQUHMAKG\n" +
            "Pmh0dHA6Ly9jYWNlcnRzLmRpZ2l0YWxjZXJ0dmFsaWRhdGlvbi5jb20vVHJ1c3RB\n" +
            "c2lhVExTUlNBQ0EuY3J0MAkGA1UdEwQCMAAwggEDBgorBgEEAdZ5AgQCBIH0BIHx\n" +
            "AO8AdQCkuQmQtBhYFIe7E6LMZ3AKPDWYBPkb37jjd80OyA3cEAAAAWWN30I1AAAE\n" +
            "AwBGMEQCIDmRdZInoxkgV4HnRQRs41tODzMwkxeUNaiF0zkUjXRLAiBiGVSWA+DF\n" +
            "Qpbqk3/2c5Ih3YLLeaO/bqdm3z+vlpzeZAB2AId1v+dZfPiMQ5lfvfNu/1aNR1Y2\n" +
            "/0q1YMG06v9eoIMPAAABZY3fQw4AAAQDAEcwRQIhAN8/QXphV4qns4xghwk7qX1A\n" +
            "nr9wUaNSdL9+ENZKtCqCAiAV5FUPf57WmFqP543rKLD7YzyXuCo7DErbqgYJKCTF\n" +
            "7jANBgkqhkiG9w0BAQsFAAOCAQEAH57PaiBQe/J4y2z6v4oTHPajbmpGyP5QHU6f\n" +
            "orWDIh5xAmauIED2B3AdxYA60mIGD7CZET05xSpqSXdkBB55W6Pf5X+OHnspXjM/\n" +
            "zEK1oaU71UuJ+3C4cYId+u006e02B1xRCmLapdDOyFIQCR9HaKpW3upvklCgdUGo\n" +
            "U85oEhaj69OCmKHi8hD6q9/wR7TY/mk88cqN38Xhki+QpiTxha+79YBJScsTcQfc\n" +
            "0181phaULDmc++ZxOVaw/cFODGFMhLEx9QwCZl0DZNj7pOoRGl0hyjKq5Dof47Wq\n" +
            "K9NpQbNk66YSTzOVrVuhkxi2goqMVEQrwNLYy5/hLnnS/X3LrQ==\n" +
            "-----END CERTIFICATE-----";


    /**
     * 获取SSLSocketFactory
     *
     * @param certificates 证书流文件
     * @return
     */
    private static SSLSocketFactory getSSLSocketFactory(InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                }
            }
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
