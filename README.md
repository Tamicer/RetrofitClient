# RetrofitClient
基于Retrofit2和Rxjava2封装的请求工具类 

Base Retrofit2 & Rxjava2 Encapsulates the request of the tools

![基于Retrofit2.0 封装的超好用的RetrofitClient](http://upload-images.jianshu.io/upload_images/2022038-71bdab0afae24005.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)




# GET

     RetrofitClient.getInstance(MainActivity.this)
                        .createBaseApi()
                        .get("url", maps)
                        .subscribe(new BaseSubscriber<T>(context) {});


# POST

     RetrofitClient.getInstance(MainActivity.this)
                        .createBaseApi()
                        .post("url", maps)
                        .subscribe( new BaseSubscriber<T>(context) {});
# JSON

     
       RequestBody jsonbody = 
                   RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(user));
    
       RetrofitClient.getInstance(MainActivity.this)
                      .createBaseApi()
                      .json("url", jsonBody)
                      .subscribe( new BaseSubscriber<T>(context) {});
                     
                
# UpLoad

        RequestBody requestFile =
                        RequestBody.create(MediaType.parse("image/jpg"), new File(mPath));
            
        RetrofitClient.getInstance(MainActivity.this)
                       .createBaseApi()
                       .upload(url, requestFile)
                       .subscribe(new BaseSubscriber<T>(context) {
                        });
                
                
# Download   

      RetrofitClient.getInstance(MainActivity.this)
      .createBaseApi()
      .download(url1, new CallBack() {

                          
                );

# Execute you ApiService    

##  MyApiService

```
public interface MyApiService {
  
    @GET("service/getIpInfo.php")
    Flowable<BaseResponse<IpResult>> getData(@Query("ip") String ip);
}
```
    
    
## create  you APiService
                
                //create  you APiService
                MyApiService service = RetrofitClient.getInstance().create(MyApiService.class);

## Call

                // execute and add observable
                RetrofitClient.getInstance(MainActivity.this)
                        .switchSchedulersMain(service.getData("21.22.11.33"))
                        .subscribe(new BaseSubscriber<BaseResponse<IpResult>>(MainActivity.this) {

                            });
        
 
 
 
 
 
 # More Rxjava
 
  支持RxJAVA操作符：
  
  
  ```
        RetrofitClient.getInstance(MainActivity.this)
                        .createBaseApi()
                        .get("service/getIpInfo.php", maps)
                        .compose(new FlowableTransformer() {
                            @Override
                            public Publisher apply(@NonNull Flowable upstream) {
                                return null;
                            }
                        })
                        .map(new Function() {
                            @Override
                            public Object apply(@NonNull Object o) throws Exception {
                                return null;
                            }
                        }).
                                        
      ```                  
 
 >http://blog.csdn.net/sk719887916/article/details/51958010
 
 >Author : Tamic
 
 **推荐使用：**
 
   Novate: https://github.com/Tamicer/Novate
