package cn.southtree.ganku.di.component;

import android.app.Activity;

import cn.southtree.ganku.di.module.ActivityModule;
import cn.southtree.ganku.di.scope.PerApp;
import cn.southtree.ganku.mvp.view.ui.MainActivity;
import dagger.Component;

/**
 * Created by Southtree on 2017/11/15.
 */
@PerApp
@Component(dependencies = AppComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();
    void inject(MainActivity mainActivity);
}
