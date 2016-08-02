# RetrofitClient
基于Retrofit和Rxjava封装的请求工具类 

Base Retrofit& Rxjava Encapsulates the request of the tools

![这里写图片描述](http://img.blog.csdn.net/20160725150118164)

# GET

    RetrofitClient.getInstance(context)..createBaseApi().get("you path url"
                        ,maps, maps, new Subscriber<IpResult>());


# POST

    RetrofitClient.getInstance(context).createBaseApi().post("you path url"
                        ,maps, maps, new Subscriber<IpResult>());
                
#UpLoad

        RequestBody requestFile =
                        RequestBody.create(MediaType.parse("image/jpg"), new File(mPath));
            
        RetrofitClient.getInstance(MainActivity.this).createBaseApi().upload(url, requestFile, new Subscriber<ResponseBody>);
                
                
# Download   

      RetrofitClient.getInstance(MainActivity.this).createBaseApi().download(url,
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


 >更多介绍：http://www.jianshu.com/p/29c2a9ac5abf
 
 >Author : Tamic
