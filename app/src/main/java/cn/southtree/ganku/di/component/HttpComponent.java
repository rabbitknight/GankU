package cn.southtree.ganku.di.component;

import android.app.Activity;
import android.app.Application;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.southtree.ganku.di.module.AppModule;
import cn.southtree.ganku.di.module.HttpModule;
import cn.southtree.ganku.mvp.view.ui.MainActivity;
import dagger.Component;
import okhttp3.Cache;
import okhttp3.OkHttpClient;


/**
 * @desc Http方法组件
 */
@Singleton
@Component(modules = HttpModule.class)
public interface HttpComponent {
    //void inject(MainActivity activity);
}
