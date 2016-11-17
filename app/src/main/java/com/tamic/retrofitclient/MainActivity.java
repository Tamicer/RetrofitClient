package com.tamic.retrofitclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tamic.retrofitclient.net.BaseResponse;
import com.tamic.retrofitclient.net.BaseSubscriber;
import com.tamic.retrofitclient.net.CallBack;
import com.tamic.retrofitclient.net.ExceptionHandle;
import com.tamic.retrofitclient.net.ExceptionHandle.ResponeThrowable;
import com.tamic.retrofitclient.net.RetrofitClient;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;


public class MainActivity extends AppCompatActivity {

    private View btn, btn_get, btn_post, btn_json, btn_download, btn_upload, btn_myApi, btn_changeHostApi;

    String url1 = "http://img0.imgtn.bdimg.com/it/u=205441424,1768829584&fm=21&gp=0.jpg";
    String url2 = "http://wap.dl.pinyin.sogou.com/wapdl/hole/201607/05/SogouInput_android_v8.3_sweb.apk?frm=new_pcjs_index";
    String url3 = "http://apk.hiapk.com/web/api.do?qt=8051&id=723";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.bt_getdata);

        btn_get = findViewById(R.id.bt_get);
        btn_post = findViewById(R.id.bt_post);
        btn_json = findViewById(R.id.bt_json);
        btn_download = findViewById(R.id.bt_download);
        btn_upload = findViewById(R.id.bt_upload);
        btn_myApi = findViewById(R.id.bt_my_api);
        btn_changeHostApi = findViewById(R.id.bt_changeHostApi);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //"http://ip.taobao.com/service/getIpInfo.php?ip=21.22.11.33";
                RetrofitClient.getInstance(MainActivity.this).createBaseApi().getData(new BaseSubscriber<IpResult>(MainActivity.this) {

                    @Override
                    public void onError(ResponeThrowable e) {
                        Log.e("Lyk", e.code + " " + e.message);
                        Toast.makeText(MainActivity.this, e.message, Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onNext(IpResult responseBody) {
                        Toast.makeText(MainActivity.this, responseBody.toString(), Toast.LENGTH_LONG).show();
                    }
                }, "21.22.11.33");

            }
        });


        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, String> maps = new HashMap<String, String>();
                maps.put("ip", "21.22.11.33");

                //"http://ip.taobao.com/service/getIpInfo.php?ip=21.22.11.33";
                RetrofitClient.getInstance(MainActivity.this).createBaseApi().get("service/getIpInfo.php"
                        , maps, new BaseSubscriber<IpResult>(MainActivity.this) {


                            @Override
                            public void onError(ResponeThrowable e) {


                                Log.e("Lyk", e.getMessage());
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onNext(IpResult responseBody) {

                                Toast.makeText(MainActivity.this, responseBody.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });


        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, String> maps = new HashMap<String, String>();

                maps.put("ip", "21.22.11.33");
                //"http://ip.taobao.com/service/getIpInfo.php?ip=21.22.11.33";
                RetrofitClient.getInstance(MainActivity.this).createBaseApi().post("service/getIpInfo.php"
                        , maps, new BaseSubscriber<ResponseBody>(MainActivity.this) {

                            @Override
                            public void onError(ResponeThrowable e) {
                                Log.e("Lyk", e.getMessage());
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                Toast.makeText(MainActivity.this, responseBody.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        btn_json.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, String> maps = new HashMap<String, String>();

                maps.put("ip", "21.22.11.33");
                //"http://ip.taobao.com/service/getIpInfo.php?ip=21.22.11.33";

                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(maps));

                RetrofitClient.getInstance(MainActivity.this).createBaseApi().json("service/getIpInfo.php"
                        , body, new BaseSubscriber<IpResult>(MainActivity.this) {


                            @Override
                            public void onError(ResponeThrowable e) {


                                Log.e("Lyk", e.getMessage());
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onNext(IpResult responseBody) {

                                Toast.makeText(MainActivity.this, responseBody.toString(), Toast.LENGTH_LONG).show();
                            }
                        });

            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //// TODO: 2016-08-03 Select your file , then RetrofitClient.getInstance(MainActivity.this).createBaseApi().upload

                // ；；；；； 略
                /**
                 * look   {@link # http://www.jianshu.com/p/acfefb0a204f}
                 */
            }
        });

        btn_download.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                RetrofitClient.getInstance(MainActivity.this).createBaseApi().download(url3, new CallBack() {

                            @Override
                            public void onStart() {
                                super.onStart();
                                Toast.makeText(MainActivity.this, url1 + "  is  start", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onProgress(long fileSizeDownloaded) {
                                super.onProgress(fileSizeDownloaded);
                                Toast.makeText(MainActivity.this, " downLoadeing, download:" + fileSizeDownloaded, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSucess(String path, String name, long fileSize) {
                                Toast.makeText(MainActivity.this, name + " is  downLoaded", Toast.LENGTH_SHORT).show();

                            }
                        }
                );
            }
        });


        btn_myApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create  you APiService
                MyApiService service = RetrofitClient.getInstance(MainActivity.this, "http://ip.taobao.com/").create(MyApiService.class);

                // execute and add observable
                RetrofitClient.getInstance(MainActivity.this).execute(

                        service.getData("21.22.11.33"), new BaseSubscriber<BaseResponse<IpResult>>(MainActivity.this) {

                            @Override
                            public void onError(ResponeThrowable e) {
                                Log.e("Lyk", e.getMessage());
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onNext(BaseResponse<IpResult> responseBody) {

                                if (responseBody.isOk()) {
                                    IpResult ip = responseBody.getData();
                                    Toast.makeText(MainActivity.this, ip.toString(), Toast.LENGTH_LONG).show();
                                }

                            }
                        });
            }
        });


        btn_changeHostApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create  you APiService
                MyApiService service = RetrofitClient.getInstance(MainActivity.this, "http://lbs.sougu.net.cn/").create(MyApiService.class);

                // execute and add observable to RxJava
                RetrofitClient.getInstance(MainActivity.this, "http://lbs.sougu.net.cn/").execute(
                        service.getSougu(), new BaseSubscriber<SouguBean>(MainActivity.this) {

                            @Override
                            public void onError(ResponeThrowable e) {
                                Log.e("Lyk", e.getMessage());
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onNext(SouguBean souguBean) {

                                Toast.makeText(MainActivity.this, souguBean.toString(), Toast.LENGTH_LONG).show();

                            }
                        });
            }
        });
    }

}