# RetrofitClient
基于Retrofit和Rxjava封装的请求工具类 

Base Retrofit& Rxjava Encapsulates the request of the tools

![基于Retrofit2.0 封装的超好用的RetrofitClient](http://upload-images.jianshu.io/upload_images/2022038-71bdab0afae24005.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)




# GET

    RetrofitClient.getInstance(context)
                  .createBaseApi()
                  .get("you path url"
                        ,maps, maps, new Subscriber<IpResult>());


# POST

    RetrofitClient.getInstance(context)
                 .createBaseApi(
                  ).post("you path url"
                        ,maps, maps, new Subscriber<IpResult>());
# JSON

     
       RequestBody jsonbody = 
               RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(user));
    
         RetrofitClient.getInstance(MainActivity.this)
             .createBaseApi()
             .json("url", jsonBody, new BaseSubscriber<T>(context) {
    
      }
                     
                
#UpLoad

        RequestBody requestFile =
                        RequestBody.create(MediaType.parse("image/jpg"), new File(mPath));
            
        RetrofitClient.getInstance(MainActivity.this)
                  .createBaseApi()
                   .upload(url, requestFile, new Subscriber<ResponseBody>);
                
                
# Download   

      RetrofitClient.getInstance(MainActivity.this)
                     .createBaseApi()
                     .download(url1, new CallBack());

# Execute you APIService    

        //create  you APiService    
         MyApiService service = RetrofitClient.getInstance(MainActivity.this).create(MyApiService.class);    
       // execute and add observable    
       RetrofitClient.getInstance(MainActivity.this).execute(            
                                  service.getData("21.22.11.33"), new Subscriber<IpResult>() {                                     

                       
                   });}
 
 
 >更多介绍：http://www.jianshu.com/p/29c2a9ac5abf
 >  
 
 >http://blog.csdn.net/sk719887916/article/details/51958010
 
 >Author : Tamic
 
 **推荐使用：**
 
   Novate: https://github.com/Tamicer/Novate
