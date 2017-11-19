package cn.southtree.ganku.di.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Southtree on 2017/11/15.
 */
@Module
public class FragmentModule {
    private Fragment mFragment;
    public FragmentModule(Fragment mFragment){
        this.mFragment = mFragment;
    }
    @Provides
    public Activity provideActivity(){
        return mFragment.getActivity();
    }

}
