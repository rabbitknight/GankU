package cn.southtree.ganku.di.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

/**
 * @desc Dagger Activity的module
 * @author rabbitknigt
 * @createTime 2017/11/15
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
