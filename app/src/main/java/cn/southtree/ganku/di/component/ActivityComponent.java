package cn.southtree.ganku.di.component;

import android.app.Activity;

import cn.southtree.ganku.di.module.ActivityModule;
import cn.southtree.ganku.di.scope.PerApp;
import cn.southtree.ganku.mvp.view.ui.activity.LauncherActivity;
import cn.southtree.ganku.mvp.view.ui.activity.MainActivity;
import cn.southtree.ganku.mvp.view.ui.activity.WebActivity;
import dagger.Component;

/**
 * @author rabbitknight
 */
@PerApp
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();

    void inject(MainActivity mainActivity);

    void inject(WebActivity webActivity);

    void inject(LauncherActivity launcherActivity);
}
