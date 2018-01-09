package cn.southtree.ganku.di.component;

import android.content.Context;

import javax.inject.Singleton;

import cn.southtree.ganku.App;
import cn.southtree.ganku.di.module.AppModule;
import cn.southtree.ganku.di.module.HttpModule;
import cn.southtree.ganku.di.module.RecyclerViewModule;
import cn.southtree.ganku.di.scope.PerApp;
import cn.southtree.ganku.mvp.presenter.impl.MainPagerPresenterImpl;
import cn.southtree.ganku.mvp.view.ui.activity.MainActivity;
import cn.southtree.ganku.mvp.view.ui.fragment.MainPagerFragment;
import cn.southtree.ganku.net.api.GankApiService;
import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * @author rabbitknight
 */
@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {
    OkHttpClient getOkHttp();

    GankApiService getGankApi();

    void inject(App app);
}
