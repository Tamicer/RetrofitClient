package com.tamic.retrofitclient;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by LIUYONGKUI726 on 2016-07-25.
 */
public interface MyApiService {
    /**
     *普通写法
     */
    @GET("service/getIpInfo.php")
    Observable<Object> getData(@Query("ip") String ip);
}
