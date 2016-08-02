package com.tamic.retrofitclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tamic.retrofitclient.net.CallBack;
import com.tamic.retrofitclient.net.DownLoadManager;
import com.tamic.retrofitclient.net.RetrofitClient;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.http.Header;
import rx.Observable;
import rx.Subscriber;


public class MainActivity extends AppCompatActivity {

    private View btn, btn_get, btn_post, btn_download, btn_upload,btn_myApi;

    String url1 = "http://www.google.co.uk/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png";
    String url2 = "http://wap.dl.pinyin.sogou.com/wapdl/hole/201607/05/SogouInput_android_v8.3_sweb.apk?frm=new_pcjs_index";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.bt_getdata);

        btn_get = findViewById(R.id.bt_get);
        btn_post = findViewById(R.id.bt_post);
        btn_download = findViewById(R.id.bt_download);
        btn_upload = findViewById(R.id.bt_upload);
        btn_myApi = findViewById(R.id.bt_my_api);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //"http://ip.taobao.com/service/getIpInfo.php?ip=21.22.11.33";
                RetrofitClient.getInstance(MainActivity.this).createBaseApi().getData(new Subscriber<IpResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
                        , maps, new Subscriber<IpResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
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
                        , maps, new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        Toast.makeText(MainActivity.this, responseBody.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // ；；；；； 略
                /**
                 * look   {@link # http://www.jianshu.com/p/acfefb0a204f}
                 */
            }
        });

        btn_download.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                RetrofitClient.getInstance(MainActivity.this).createBaseApi().download(url1, new CallBack() {

                            @Override
                            public void onStart() {
                                super.onStart();
                                Toast.makeText(MainActivity.this, url1 + "  is  star", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Throwable e) {

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
                MyApiService service = RetrofitClient.getInstance(MainActivity.this).create(MyApiService.class);

                // execute and add observable
                RetrofitClient.getInstance(MainActivity.this).execute(
                        service.getData("21.22.11.33"), new Subscriber<IpResult>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onNext(IpResult responseBody) {

                                Toast.makeText(MainActivity.this, responseBody.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }
}