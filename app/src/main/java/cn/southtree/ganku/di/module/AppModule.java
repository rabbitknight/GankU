package cn.southtree.ganku.di.module;

import javax.inject.Singleton;

import cn.southtree.ganku.App;
import dagger.Module;
import dagger.Provides;

/**
 * @desc Dagger App的Module
 * @author rabbitknight
 * @createTime 2017/11/15
 */
@Module
public class AppModule {
    private App mApplication;

    public AppModule(App mApplication){
        this.mApplication = mApplication;
    }

    @Provides
    @Singleton
    public App provideApp(){
        return mApplication;
    }
}
