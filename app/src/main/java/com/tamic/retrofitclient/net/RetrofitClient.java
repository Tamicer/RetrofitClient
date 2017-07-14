package com.tamic.retrofitclient.net;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;


import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * RetrofitClient
 * Created by Tamic on 2016-06-15.
 * {@link # https://github.com/Tamicer/RetrofitClient}
 */
public class RetrofitClient {

    private static final int DEFAULT_TIMEOUT = 20;
    private BaseApiService apiService;
    private static OkHttpClient okHttpClient;
    public static final String Base_URL = "http://ip.taobao.com/";
    public static String baseUrl = Base_URL;
    private static Context mContext;
    private static RetrofitClient sNewInstance;

    private static Retrofit retrofit;
    private Cache cache = null;
    private File httpCacheDirectory;
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(baseUrl);

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder()
                    .addNetworkInterceptor(
                            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

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

        return new RetrofitClient(context, url);
    }

    public static RetrofitClient getInstance(Context context, String url, Map<String, String> headers) {
        if (context != null) {
            mContext = context;
        }
        return new RetrofitClient(context, url, headers);
    }

    private RetrofitClient() {

    }

    private RetrofitClient(Context context) {

        this(context, baseUrl, null);
    }

    private RetrofitClient(Context context, String url) {

        this(context, url, null);
    }

    private RetrofitClient(Context context, String url, Map<String, String> headers) {

        if (TextUtils.isEmpty(url)) {
            url = baseUrl;
        }

        if (httpCacheDirectory == null) {
            httpCacheDirectory = new File(mContext.getCacheDir(), "tamic_cache");
        }

        try {
            if (cache == null) {
                cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
            }
        } catch (Exception e) {
            Log.e("OKHttp", "Could not create http cache", e);
        }
        okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(
                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                //.cookieJar(new NovateCookieManger(context))
                .cache(cache)
                .addInterceptor(new BaseInterceptor(headers))
                .addInterceptor(new CacheInterceptor(context))
                .addInterceptor(new AddCookiesInterceptor(context, "ch"))
                .addInterceptor(new ReceivedCookiesInterceptor(context))
                .addNetworkInterceptor(new CacheInterceptor(context))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build();

    }

    /**
     * ApiBaseUrl
     *
     * @param newApiBaseUrl
     */
    public static void changeApiBaseUrl(String newApiBaseUrl) {
        baseUrl = newApiBaseUrl;
        builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl);
    }

    /**
     * addcookieJar
     */
    public static void addCookie() {
        okHttpClient.newBuilder().cookieJar(new NovateCookieManger(mContext)).build();
        retrofit = builder.client(okHttpClient).build();
    }

    /**
     * ApiBaseUrl
     *
     * @param newApiHeaders
     */
    public static void changeApiHeader(Map<String, String> newApiHeaders) {

        okHttpClient.newBuilder().addInterceptor(new BaseInterceptor(newApiHeaders)).build();
        builder.client(httpClient.build()).build();
    }

    /**
     * create BaseApi  defalte ApiManager
     *
     * @return ApiManager
     */
    public RetrofitClient createBaseApi() {
        apiService = create(BaseApiService.class);
        return this;
    }

    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     */
    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }

    public Flowable getData(String ip) {
        return apiService.getData(ip)
                .compose(schedulersTransformer())
                .compose(transformer());
    }

    public Flowable get(String url, Map parameters) {

        return apiService.get(url, parameters)
                .compose(schedulersTransformer())
                .compose(transformer());
    }

    public Flowable post(String url, Map<String, String> parameters) {
        return apiService.post(url, parameters)
                .compose(schedulersTransformer())
                .compose(transformer());
    }

    public Flowable json(String url, RequestBody jsonStr) {

        return apiService.json(url, jsonStr)
                .compose(schedulersTransformer())
                .compose(transformer());
    }

    public Flowable upload(String url, RequestBody requestBody) {
        return apiService.upLoadFile(url, requestBody)
                .compose(schedulersTransformer())
                .compose(transformer());
    }

    public void download(String url, final CallBack callBack) {
        apiService.downloadFile(url)
                .compose(schedulersTransformernewThread())
                .compose(transformer())
                .subscribe(new DownSubscriber<ResponseBody>(callBack));
    }

    FlowableTransformer schedulersTransformer() {
        return new FlowableTransformer() {
            @Override
            public Publisher apply(@NonNull Flowable upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    FlowableTransformer schedulersTransformernewThread() {
        return new FlowableTransformer() {
            @Override
            public Publisher apply(@NonNull Flowable upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    <T> FlowableTransformer<T, T> applySchedulers() {
        return (FlowableTransformer<T, T>) schedulersTransformer();
    }

    public <T> FlowableTransformer<BaseResponse<T>, T> transformer() {

        return new FlowableTransformer() {
            @Override
            public Publisher apply(@NonNull Flowable upstream) {
                return upstream.map(new HandleFuc<T>()).onErrorResumeNext(new HttpResponseFunc<T>());
            }

           /* @Override
            public Object call(Object observable) {
                return observable.map(new HandleFuc<T>()).onErrorResumeNext(new HttpResponseFunc<T>());
            }*/
        };
    }

    public <T> Flowable<T> switchSchedulersIo(Flowable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    private static class HttpResponseFunc<T> implements Function<Throwable, Flowable<T>> {
        @Override
        public Flowable<T> apply(@NonNull Throwable t) throws Exception {
            return Flowable.error(ExceptionHandle.handleException(t));
        }
    }

    private class HandleFuc<T> implements Function<BaseResponse<T>, T> {

        @Override
        public T apply(@NonNull BaseResponse<T> response) throws Exception {
            if (!response.isOk())
                throw new RuntimeException(response.getCode() + "" + response.getMsg() != null ? response.getMsg() : "");
            return response.getData();
        }
    }

    public static <T> Flowable<T> switchSchedulersMain(Flowable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * /**
     * execute your customer API
     * For example:
     * MyApiService service =
     * RetrofitClient.getInstance(MainActivity.this).create(MyApiService.class);
     * <p>
     * RetrofitClient.getInstance(MainActivity.this)
     * .execute(service.lgon("name", "password"), subscriber)
     * * @param subscriber
     */

    public static <T> Flowable<T> call(Flowable<T> observable, Subscriber<T> subscriber) {
        observable = observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(subscriber);
        return observable;
    }


    /**
     * DownSubscriber
     *
     * @param <ResponseBody>
     */
    class DownSubscriber<ResponseBody> extends DisposableSubscriber<ResponseBody> {
        CallBack callBack;

        public DownSubscriber(CallBack callBack) {
            this.callBack = callBack;
        }

        @Override
        public void onStart() {
            super.onStart();
            if (callBack != null) {
                callBack.onStart();
            }
        }

        @Override
        public void onError(Throwable e) {
            if (callBack != null) {
                callBack.onError(e);
            }
        }

        @Override
        public void onComplete() {
            if (callBack != null) {
                callBack.onCompleted();
            }
        }

        @Override
        public void onNext(ResponseBody responseBody) {
            DownLoadManager.getInstance(callBack).writeResponseBodyToDisk(mContext, (okhttp3.ResponseBody) responseBody);
        }
    }

}
