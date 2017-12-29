package cn.southtree.ganku.di.component;

import javax.inject.Singleton;

import cn.southtree.ganku.App;
import cn.southtree.ganku.di.module.AppModule;
import cn.southtree.ganku.di.module.HttpModule;
import cn.southtree.ganku.mvp.presenter.impl.MainPagerPresenterImpl;
import cn.southtree.ganku.mvp.view.ui.activity.MainActivity;
import cn.southtree.ganku.mvp.view.ui.fragment.MainPagerFragment;
import dagger.Component;

/**
 * @author rabbitknight
 */
@Singleton
@Component(modules = {AppModule.class,HttpModule.class})

public interface AppComponent {
    //App getApplication();
    void inject(MainActivity mainActivity);
    void inject(App app);
    void inject(MainPagerFragment fragment);
    void inject(MainPagerPresenterImpl presenter);
}
