package cn.southtree.ganku.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import javax.inject.Singleton;

import cn.southtree.ganku.mvp.view.ui.fragment.MainPagerFragment;
import dagger.Module;
import dagger.Provides;

/**
 * @desc Dagger Fragment的module
 * @author rabbitknight
 * @createTime 2017/11/15
 */
@Module
public class FragmentModule {
    private MainPagerFragment mFragment;
    public FragmentModule(MainPagerFragment mFragment){
        this.mFragment = mFragment;
    }

    @Provides
    public Context provideContext(){
        return mFragment.getContext();
    }

    @Provides
    public Activity provideActivity(){
        return mFragment.getActivity();
    }

}
