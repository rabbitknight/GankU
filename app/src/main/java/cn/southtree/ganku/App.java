package cn.southtree.ganku;

import android.app.Application;
import android.content.SharedPreferences;

import javax.inject.Inject;

import cn.southtree.ganku.common.Constants;
import cn.southtree.ganku.di.component.AppComponent;
import cn.southtree.ganku.di.component.DaggerAppComponent;
//import cn.southtree.ganku.di.component.DaggerHttpComponent;
import cn.southtree.ganku.di.component.HttpComponent;
import cn.southtree.ganku.di.module.AppModule;
import cn.southtree.ganku.di.module.HttpModule;
import cn.southtree.ganku.net.api.GankApiService;

/**
 * @desc Application类，提供基础服务，网络访问，数据存储等
 * @author zhuo.chen
 */

public class App extends Application {
    private static App appContext;              //整个APP的上下文环境
    private static AppComponent mAppComponent;  //整个APP管理的Component
    private static SharedPreferences mSahre;
    @Inject
    GankApiService gankApiService;
    public static App getAppContext() {
        return appContext;
    }
    public static AppComponent getmAppComponent() {
        return mAppComponent;
    }

    public GankApiService getGankApiService() {
        return gankApiService;
    }

    public static SharedPreferences getmSahre(){
        return mSahre;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .httpModule(new HttpModule(Constants.GANK_IO))
                .build();
        mAppComponent.inject(this);
        mSahre = getSharedPreferences("img",MODE_PRIVATE);
    }
}
