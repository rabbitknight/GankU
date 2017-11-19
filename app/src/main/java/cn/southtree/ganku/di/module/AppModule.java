package cn.southtree.ganku.di.module;

import javax.inject.Singleton;

import cn.southtree.ganku.App;
import dagger.Module;
import dagger.Provides;

/**
 * @description Dagger App的Module
 * @author rabbitknight
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
