package cn.southtree.ganku.di.component;

import javax.inject.Singleton;

import cn.southtree.ganku.App;
import cn.southtree.ganku.di.module.AppModule;
import dagger.Component;

/**
 * @author rabbitknight
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    App getApplication();
}
