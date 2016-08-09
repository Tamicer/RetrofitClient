package com.tamic.retrofitclient;

import com.tamic.retrofitclient.net.BaseResponse;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by LIUYONGKUI726 on 2016-07-25.
 */
public interface MyApiService {
    /**
     *普通写法
     */
    @GET("service/getIpInfo.php")
    Observable<BaseResponse<IpResult>> getData(@Query("ip") String ip);

    @GET("app.php?m=souguapp&c=appusers&a=network")
    Observable<SouguBean> getSougu();
}
