package cn.southtree.ganku.mvp.view.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.southtree.ganku.App;
import cn.southtree.ganku.R;
import cn.southtree.ganku.common.Constants;
import cn.southtree.ganku.di.component.AppComponent;
import cn.southtree.ganku.di.module.ActivityModule;
import cn.southtree.ganku.mvp.model.remote.GankBean;
import cn.southtree.ganku.mvp.presenter.impl.MainPresenterImpl;
import cn.southtree.ganku.mvp.view.base.BaseActivity;
import cn.southtree.ganku.mvp.view.interfaces.MainView;
import cn.southtree.ganku.mvp.view.ui.adapter.MainViewPagerAdapter;
import cn.southtree.ganku.mvp.view.ui.listener.OnFrag2ActivityCallBack;
import okhttp3.OkHttpClient;


public class MainActivity extends BaseActivity<MainPresenterImpl> implements MainView,
        DrawerLayout.DrawerListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener,OnFrag2ActivityCallBack{
    private static final String TAG = "MainActivity";
    @Inject
    public OkHttpClient okHttpClient;
    @Inject
    public MainPresenterImpl mainPresenter;

    @BindView(R.id.tab_tl)
    TabLayout tabTl;
    @BindView(R.id.tool_ctl)
    CollapsingToolbarLayout toolCtl;
    @BindView(R.id.girl_iv)
    ImageView girlIv;
    @BindView(R.id.appbar_apl)
    AppBarLayout appbarApl;
    @BindView(R.id.content_vp)
    ViewPager contentVp;
    @BindView(R.id.coordinator_cl)
    CoordinatorLayout coordinatorCl;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bg_iv)
    ImageView bgIv;
    @BindView(R.id.logo_iv)
    ImageView logoIv;
    @BindView(R.id.nav_fl)
    FrameLayout navFl;
    @BindView(R.id.set_fl)
    FrameLayout setFl;
    @BindView(R.id.drawer_dl)
    DrawerLayout drawerDl;
    @BindView(R.id.and_cb)
    AppCompatCheckBox andCb;
    @BindView(R.id.ios_cb)
    AppCompatCheckBox iosCb;
    @BindView(R.id.web_cb)
    AppCompatCheckBox webCb;
    @BindView(R.id.app_cb)
    AppCompatCheckBox appCb;
    @BindView(R.id.girl_cb)
    AppCompatCheckBox girlCb;

    private App app;
    private SparseBooleanArray tabs = new SparseBooleanArray();
    private SparseBooleanArray tabs_temp = new SparseBooleanArray();
    private MainViewPagerAdapter mAdapter;
    private boolean isChanged = false;


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent, ActivityModule activityModule) {
        appComponent.inject(this);
    }

    @Override
    protected void initViews() {
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        //tab
        tabs.put(Constants.APP,true);
        tabs.put(Constants.ANDROID,true);
        tabs.put(Constants.IOS,true);
        tabs.put(Constants.WEB,true);
        tabs.put(Constants.MEIZI,true);
        mPresenter = mainPresenter;
        mPresenter.attachView(this);
        mAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), tabs,this);
        contentVp.setAdapter(mAdapter);
        contentVp.setOffscreenPageLimit(5);
        //
        tabTl.setupWithViewPager(contentVp);
        //drawer
        drawerDl.addDrawerListener(this);
        //cb
        andCb.setOnCheckedChangeListener(this);
        appCb.setOnCheckedChangeListener(this);
        girlCb.setOnCheckedChangeListener(this);
        iosCb.setOnCheckedChangeListener(this);
        webCb.setOnCheckedChangeListener(this);


    }



    @Override
    public void showProcess() {

    }

    @Override
    public void dismissProcess() {

    }


    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {
        if (isChanged){
            mAdapter.notifyDataSetChanged();
        }

        isChanged = false;
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.and_cb:
                if (isChecked) {
                    if (tabs.get(Constants.ANDROID,false)){

                    }else {
                        tabs.put(Constants.ANDROID, true);
                        isChanged = true;
                    }

                } else {
                    tabs.delete(Constants.ANDROID);
                    isChanged = true;
                }
                break;
            case R.id.app_cb:
                if (isChecked) {
                    if (tabs.get(Constants.APP,false)){

                    } else {
                        tabs.put(Constants.APP, true);
                        isChanged = true;
                    }
                } else {
                    tabs.delete(Constants.APP);
                    isChanged = true;
                }
                break;
            case R.id.girl_cb:
                if (isChecked) {
                    if (tabs.get(Constants.MEIZI,false)){

                    }else {
                        tabs.put(Constants.MEIZI,true);
                        isChanged = true;
                    }
                } else {
                    tabs.delete(Constants.MEIZI);
                }
                break;
            case R.id.ios_cb:
                if (isChecked) {
                    if (tabs.get(Constants.IOS, false)){

                    }else {
                        tabs.put(Constants.IOS, true);
                        isChanged = true;
                    }
                } else {
                    tabs.delete(Constants.IOS);
                    isChanged = true;
                }
                break;
            case R.id.web_cb:
                if (isChecked) {
                    if (tabs.get(Constants.WEB,false)){

                    } else {
                        tabs.put(Constants.WEB, true);
                        isChanged = true;
                    }
                } else {
                    tabs.delete(Constants.WEB);
                    isChanged = true;

                }
                break;
            default:break;
        }
    }

    @Override
    public void setMeizi() {
        mainPresenter.updateMeizi(bgIv);
        mainPresenter.updateMeizi(girlIv);
    }
}
