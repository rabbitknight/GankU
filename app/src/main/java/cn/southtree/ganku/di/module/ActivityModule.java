package cn.southtree.ganku.di.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Southtree on 2017/11/15.
 */
@Module
public class ActivityModule {
    private Activity mActivity;
    public ActivityModule(Activity mActivity){
        this.mActivity = mActivity;
    }
    @Provides
    public Activity provideActivity(){
        return mActivity;
    }
}
