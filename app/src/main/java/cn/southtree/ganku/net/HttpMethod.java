package cn.southtree.ganku.net;

import java.io.File;
import java.util.concurrent.TimeUnit;

import cn.southtree.ganku.App;
import cn.southtree.ganku.common.Constants;
import cn.southtree.ganku.net.api.GankApiService;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Southtree
 * @version 2017/11/16.
 */
@Deprecated
public class HttpMethod {
    public static final String BASE_URL = Constants.GANK_IO;
    public static final int TIME_OUT = 3000;

    public Retrofit retrofit;
    public GankApiService  gankApiService;

    private HttpMethod(){
        File cacheFile = new File(App.getAppContext().getCacheDir(),"cache");
        Cache cache = new Cache(cacheFile,1024*1024*20);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(TIME_OUT,TimeUnit.MILLISECONDS)
                .cache(cache)
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        gankApiService = retrofit.create(GankApiService.class);
    }

    private static class SingletonHolder{
        private static final HttpMethod INSTANCE = new HttpMethod();
    }

    public static HttpMethod getInstance(){
        return SingletonHolder.INSTANCE;
    }
}
