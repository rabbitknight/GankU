package cn.southtree.ganku.di.component;

import javax.inject.Singleton;

import cn.southtree.ganku.App;
import cn.southtree.ganku.di.module.AppModule;
import cn.southtree.ganku.di.module.HttpModule;
import cn.southtree.ganku.mvp.view.ui.MainActivity;
import dagger.Component;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * @author rabbitknight
 */
@Singleton
@Component(modules = {AppModule.class,HttpModule.class})

public interface AppComponent {
    //App getApplication();
    void inject(MainActivity mainActivity);
    void inject(App app);
}
