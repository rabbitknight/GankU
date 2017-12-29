package cn.southtree.ganku.di.component;

import javax.inject.Singleton;

import cn.southtree.ganku.di.module.HttpModule;
import dagger.Component;


/**
 * @desc Http方法组件
 */
@Singleton
@Component(modules = HttpModule.class)
public interface HttpComponent {
    //void inject(MainActivity activity);
}
