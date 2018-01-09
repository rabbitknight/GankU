package cn.southtree.ganku.di.component;

import android.content.Context;

import javax.inject.Singleton;

import cn.southtree.ganku.di.module.FragmentModule;
import cn.southtree.ganku.di.module.RecyclerViewModule;
import cn.southtree.ganku.di.scope.PerApp;
import cn.southtree.ganku.mvp.presenter.impl.MainPagerPresenterImpl;
import cn.southtree.ganku.mvp.presenter.impl.MainPresenterImpl;
import cn.southtree.ganku.mvp.view.ui.fragment.MainPagerFragment;
import cn.southtree.ganku.mvp.view.ui.widget.MItemDecoration;
import dagger.Component;

/**
 * Created by zhuo.chen on 2018/1/6.
 */
@PerApp
@Component(dependencies = AppComponent.class, modules = {FragmentModule.class, RecyclerViewModule.class})
public interface FragmentComponent {
    MItemDecoration getMItemDecoration();

    void inject(MainPagerFragment fragment);

    void inject(MainPagerPresenterImpl presenter);
}
