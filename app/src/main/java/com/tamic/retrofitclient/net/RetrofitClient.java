package com.tamic.retrofitclient.net;

import android.content.Context;
import android.text.TextUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * RetrofitClient
 * Created by Tamic on 2016-06-15.
 *  {@link # https://github.com/NeglectedByBoss/RetrofitClient}
 */
public class RetrofitClient {

    private static final int DEFAULT_TIMEOUT = 5;
    private ApiService apiService;
    private OkHttpClient okHttpClient;
    public static String baseUrl = ApiService.Base_URL;
    private static Context mContext;
    private static RetrofitClient sNewInstance;

    private static class SingletonHolder {
        private static RetrofitClient INSTANCE = new RetrofitClient(
                mContext);
    }

    public static RetrofitClient getInstance(Context context) {
        if (context != null) {
            mContext = context;
        }
        return SingletonHolder.INSTANCE;
    }

    public static RetrofitClient getInstance(Context context, String url) {
        if (context != null) {
            mContext = context;
        }
        sNewInstance = new RetrofitClient(context, url);
        return sNewInstance;
    }

    public static RetrofitClient getInstance(Context context, String url, Map headers) {
        if (context != null) {
            mContext = context;
        }
        sNewInstance = new RetrofitClient(context, url, headers);
        return sNewInstance;
    }


    private RetrofitClient(Context context) {

        this(context, baseUrl, null);
    }

    private RetrofitClient(Context context, String url) {

        this(context, url, null);
    }

    private RetrofitClient(Context context, String url, Map headers) {

        if (TextUtils.isEmpty(url)) {
            url = baseUrl;
        }
        okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(
                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
                .cookieJar(new NovateCookieManger(context))
                .addInterceptor(new BaseInterceptor(headers))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(url)
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public void getData(Subscriber<IpResult> subscriber, String ip) {
        apiService.getData(ip)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void get(String url, Map headers, Map parameters, Subscriber<IpResult> subscriber) {
        apiService.executeGet(url, parameters)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void post(String url, Map headers, Map parameters, Subscriber<ResponseBody> subscriber) {
        apiService.executePost(url, headers, parameters)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
