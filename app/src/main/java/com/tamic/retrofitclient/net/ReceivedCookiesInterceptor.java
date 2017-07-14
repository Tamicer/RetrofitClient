package com.tamic.retrofitclient.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * https://github.com/Tamicer/RetrofitClient
 * Created by Tamic on 2016-12-08.
 */
public class ReceivedCookiesInterceptor implements Interceptor {
    private Context context;
    SharedPreferences sharedPreferences;

    public ReceivedCookiesInterceptor(Context context) {
        super();
        this.context = context;
        sharedPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (chain == null)
            Log.d("RetrofitClent", "Receivedchain == null");
        Response originalResponse = chain.proceed(chain.request());
        Log.d("RetrofitClent", "originalResponse" + originalResponse.toString());
        if (!originalResponse.headers("set-cookie").isEmpty()) {
            final StringBuffer cookieBuffer = new StringBuffer();
            Flowable.fromIterable(originalResponse.headers("set-cookie"))

                    .map(new Function<String, String>() {

                        @Override
                        public String apply(@NonNull String s) throws Exception {
                            String[] cookieArray = s.split(";");
                            return cookieArray[0];

                        }
                    }).subscribe(new Consumer<String>() {
                        @Override
                        public void accept(@NonNull String cookie) throws Exception {
                                cookieBuffer.append(cookie).append(";");
                        }
            });
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("cookie", cookieBuffer.toString());
            Log.d("RetrofitClent", "ReceivedCookiesInterceptor:" + cookieBuffer.toString());
            editor.commit();
        }

        return originalResponse;
    }
}