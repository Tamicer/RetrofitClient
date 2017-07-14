package com.tamic.retrofitclient;

import com.tamic.retrofitclient.net.BaseResponse;



import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Tamic on 2016-07-25.
 */
public interface MyApiService {
    /**
     *普通写法
     */
    @GET("service/getIpInfo.php")
    Flowable<BaseResponse<IpResult>> getData(@Query("ip") String ip);

    @GET("app.php?m=souguapp&c=appusers&a=network")
    Flowable<SouguBean> getSougu();
}
