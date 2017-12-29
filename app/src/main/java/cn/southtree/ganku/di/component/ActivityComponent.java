package cn.southtree.ganku.di.component;

import android.app.Activity;

import cn.southtree.ganku.di.module.ActivityModule;
import cn.southtree.ganku.di.scope.PerApp;
import dagger.Component;

/**
 * @author rabbitknight
 */
@PerApp
@Component(dependencies = AppComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();
    //void inject(MainActivity mainActivity);
}
