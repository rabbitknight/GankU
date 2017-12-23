package cn.southtree.ganku.di.module;

import android.app.Application;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import cn.southtree.ganku.App;
import cn.southtree.ganku.net.api.GankApiService;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @desc net通信相关，诸如OkHttp、Retrofit通过注入的方式提供
 * @createTime
 */
@Module
public class HttpModule {
    private String BASE_URL;
    //private Application application;
    public HttpModule(String baseUrl) {
        this.BASE_URL = baseUrl;
        //this.application = application;
    }

    //inject okHttpClient
    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient(Cache cache) {
        int TIME_OUT = 3000;
        return new OkHttpClient.Builder()
                .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .cache(cache).build();
    }
    //inject Retrofit
    @Singleton
    @Provides
    public Retrofit provideRetrofit(OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    //inject okHttpCache
    @Provides
    @Singleton
    Cache provideOkHttpCache(App application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        return new Cache(application.getCacheDir(), cacheSize);
    }

    //inject GankApiService
    @Provides
    @Singleton
    GankApiService provideGankApi(Retrofit retrofit){
        return retrofit.create(GankApiService.class);
    }


}
