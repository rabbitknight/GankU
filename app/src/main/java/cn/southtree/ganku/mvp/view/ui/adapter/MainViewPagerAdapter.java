package cn.southtree.ganku.mvp.view.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.southtree.ganku.common.Constants;
import cn.southtree.ganku.mvp.view.ui.activity.MainActivity;
import cn.southtree.ganku.mvp.view.ui.fragment.MainPagerFragment;

/**
 * 主页tab切换的适配器
 * @author zhuo.chen
 * @version 2017/12/25
 */

public class MainViewPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "MainViewPagerAdapter";
    public enum titleEnum {App,Android,ios,Web} //title的enum

    private SparseArray<Fragment> fragments = new SparseArray<>();    //id为常量

    private SparseBooleanArray tabs = new SparseBooleanArray();  //tab[0]->{fragment's name}
    private MainActivity mActivity;

    @Override
    public CharSequence getPageTitle(int position) {
        return getName(position);
    }

    public MainViewPagerAdapter(FragmentManager fm,SparseBooleanArray tabs,MainActivity mainActivity) {
        super(fm);
        if (tabs!=null){
            this.tabs = tabs;
        }
        if (mainActivity!=null){
            this.mActivity = mainActivity;
        }

    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public long getItemId(int position) {
        return tabs.keyAt(position);
    }

    @Override
    public Fragment getItem(int position) {
        int id = tabs.keyAt(position);
        if (tabs.size()==0){
            return null;
        }
        if (fragments.get(id)!=null){

        }else {
            MainPagerFragment mainPagerFragment = new MainPagerFragment();
            Bundle args = new Bundle();
            args.putInt("pageType",tabs.keyAt(position));
            Log.i(TAG, "getItem: "+tabs.keyAt(position));
            mainPagerFragment.setArguments(args);
            mainPagerFragment.setOnFrag2ActivityCallBack(mActivity);
            fragments.put(id,mainPagerFragment);
        }
        return fragments.get(id);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }
    private String getName(int pos){
        int position = tabs.keyAt(pos);
        switch (position){
            case Constants.APP:
                return "App";
            case Constants.ANDROID:
                return "Android";
            case Constants.IOS:
                return "iOS";
            case Constants.WEB:
                return "前端";
            case Constants.MEIZI:
                return "福利";
            default:
                return "-----";
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        fragments.delete(tabs.keyAt(position));
    }
}
