package cn.southtree.ganku;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.nfc.Tag;
import android.support.customtabs.CustomTabsCallback;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;

import java.util.List;

import javax.inject.Inject;

import cn.southtree.ganku.common.Constants;
import cn.southtree.ganku.di.component.AppComponent;
import cn.southtree.ganku.di.component.DaggerAppComponent;
import cn.southtree.ganku.di.module.AppModule;
import cn.southtree.ganku.di.module.HttpModule;
import cn.southtree.ganku.net.api.GankApiService;

/**
 * @desc Application类，提供基础服务，网络访问，数据存储等
 * @author zhuo.chen
 */

public class App extends Application {
    private static final String TAG = App.class.getSimpleName();
    private static App appContext;              //整个APP的上下文环境
    private static AppComponent mAppComponent;  //整个APP管理的Component
    private static SharedPreferences mSahre;
    public static boolean canChromeLoad;

    @Inject
    GankApiService gankApiService;

    @Deprecated
    // TODO: 2018/1/6 方法无效
    private void hasChrome() {
        String packageName = "com.android.chrome";
        Intent browserIntent = new Intent();
        browserIntent.setPackage(packageName);
        List<ResolveInfo> activitiesList = getPackageManager().queryIntentActivities(
                browserIntent, -1);
        if(activitiesList.size() > 0) {
            canChromeLoad = true;
        }
    }

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
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                Log.i(TAG, "onCoreInitFinished: ");
            }

            @Override
            public void onViewInitFinished(boolean b) {
                Log.i(TAG, "onViewInitFinished: "+b);

            }
        };
        appContext = this;
        QbSdk.initX5Environment(appContext,cb);
        QbSdk.setDownloadWithoutWifi(true);
        mAppComponent = DaggerAppComponent.builder()
                .httpModule(new HttpModule(Constants.GANK_IO))
                .appModule(new AppModule(this))
                .build();
        mAppComponent.inject(this);
        mSahre = getSharedPreferences("img",MODE_PRIVATE);

        hasChrome();
        if (canChromeLoad)
            CustomTabsClient.connectAndInitialize(this, "com.android.chrome");

    }
}
