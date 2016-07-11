# RetrofitClient
基于Retrofit和Rxjava封装的请求工具类 

Base Retrofit& Rxjava Encapsulates the request of the tools

# GET

    RetrofitClient.getInstance(context).get("you path url"
                        ,maps, maps, new Subscriber<IpResult>() {
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


# POST

    RetrofitClient.getInstance(context).post("you path url"
                        ,maps, maps, new Subscriber<IpResult>() {
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
                
# Download   

      RetrofitClient.getInstance(MainActivity.this).download(url,
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
