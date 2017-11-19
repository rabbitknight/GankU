package cn.southtree.ganku;

import android.app.Application;

import cn.southtree.ganku.di.component.AppComponent;
import cn.southtree.ganku.di.component.DaggerAppComponent;
import cn.southtree.ganku.di.module.AppModule;

/**
 * Created by Southtree on 2017/11/15.
 */

public class App extends Application {
    private static App appContext;              //整个APP的上下文
    private static AppComponent mAppComponent;  //整个APP管理的Component

    public static App getAppContext() {
        return appContext;
    }
    public static AppComponent getmAppComponent() {
        return mAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
    }
}
