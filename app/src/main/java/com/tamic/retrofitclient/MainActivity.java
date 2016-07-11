package com.tamic.retrofitclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tamic.retrofitclient.net.ApiService;
import com.tamic.retrofitclient.net.DownLoadManager;
import com.tamic.retrofitclient.net.IpResult;
import com.tamic.retrofitclient.net.RetrofitClient;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Subscriber;


public class MainActivity extends AppCompatActivity {

    private View btn, btn_get, btn_post, btn_download, btn_upload;

    String url1 = "https://www.google.co.uk/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png";
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

        // 单独指定apiService
       /* ApiService apiService = RetrofitClient.createService(ApiService.class);
        apiService.downloadFile(url1);*/


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //"http://ip.taobao.com/service/getIpInfo.php?ip=21.22.11.33";
                RetrofitClient.getInstance(MainActivity.this).getData(new Subscriber<IpResult>() {
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
                RetrofitClient.getInstance(MainActivity.this).get("service/getIpInfo.php"
                        , maps, maps, new Subscriber<IpResult>() {
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
                RetrofitClient.getInstance(MainActivity.this).post("service/getIpInfo.php"
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
                RetrofitClient.getInstance(MainActivity.this).download(url1,
                        new Subscriber<ResponseBody>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {

                                if (DownLoadManager.writeResponseBodyToDisk(MainActivity.this, responseBody)) {
                                    Toast.makeText(MainActivity.this, "Download is sucess", Toast.LENGTH_LONG).show();
                                }

                            }
                        });
            }
        });
    }
}