package cn.southtree.ganku.mvp.view.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.southtree.ganku.App;
import cn.southtree.ganku.R;
import cn.southtree.ganku.common.Constants;
import cn.southtree.ganku.di.component.DaggerActivityComponent;
import cn.southtree.ganku.di.module.ActivityModule;
import cn.southtree.ganku.mvp.presenter.impl.MainPresenterImpl;
import cn.southtree.ganku.mvp.presenter.interfaces.MainPresenter;
import cn.southtree.ganku.mvp.view.base.BaseActivity;
import cn.southtree.ganku.mvp.view.interfaces.MainV;
import cn.southtree.ganku.mvp.view.ui.adapter.MainViewPagerAdapter;
import cn.southtree.ganku.mvp.view.ui.listener.OnActivity2FragCallBack;
import cn.southtree.ganku.mvp.view.ui.listener.OnFrag2ActivityCallBack;
import cn.southtree.ganku.mvp.view.ui.listener.ViewPagerChangeListener;
import cn.southtree.ganku.mvp.view.ui.widget.ImageViewWrap;
import okhttp3.OkHttpClient;


public class MainActivity extends BaseActivity<MainPresenter> implements MainV,
        DrawerLayout.DrawerListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener,
        OnFrag2ActivityCallBack, AppBarLayout.OnOffsetChangedListener {
    private static final String TAG = "MainActivity";


    private SparseBooleanArray tabs = new SparseBooleanArray();
    private MainViewPagerAdapter mAdapter;
    private boolean isChanged = false;
    private SparseArray<OnActivity2FragCallBack> callbacks = new SparseArray<>();

    @Inject
    public OkHttpClient okHttpClient;
    @Inject
    public MainPresenterImpl mainPresenter;

    @BindView(R.id.float_fab)
    FloatingActionButton floatFab;
    @BindView(R.id.nav_nv)
    NavigationView navNv;
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
    @BindView(R.id.nav_fl)
    FrameLayout navFl;
    @BindView(R.id.set_fl)
    FrameLayout setFl;
    @BindView(R.id.drawer_dl)
    DrawerLayout drawerDl;

    private SwitchCompat onSc;
    private SwitchCompat toSc;
    private SwitchCompat thSc;
    private SwitchCompat foSc;
    private SwitchCompat fiSc;
    private ImageView bgIv;

    private boolean isScrolled;


    private CollapsingToolbarLayoutState state; // CollapsingToolbarLayout 折叠状态


    private enum CollapsingToolbarLayoutState {
        EXPANDED, // 完全展开
        COLLAPSED, // 折叠
        INTERNEDIATE // 中间状态
    }

    public void setCallBack(int position, OnActivity2FragCallBack callBack) {
        this.callbacks.put(position, callBack);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInject() {
        DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(App.getmAppComponent())
                .build()
                .inject(this);

    }

    @Override
    protected void initViews() {
        //toolbar
        appbarApl.addOnOffsetChangedListener(this);
        //tab
        tabs.put(Constants.APP, true);
        tabs.put(Constants.ANDROID, true);
        tabs.put(Constants.IOS, true);
        tabs.put(Constants.WEB, true);
        tabs.put(Constants.MEIZI, true);
        mPresenter = mainPresenter;
        mPresenter.attachView(this);
        mAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), tabs, this);
        contentVp.setAdapter(mAdapter);
        contentVp.setOffscreenPageLimit(5);
        contentVp.addOnPageChangeListener(new ViewPagerChangeListener(contentVp, (isHeader) -> {
            if (isHeader) {
                drawerDl.openDrawer(navFl);
            } else {
                drawerDl.openDrawer(setFl);
            }
        }));
        //
        tabTl.setupWithViewPager(contentVp);
        //drawer
        drawerDl.addDrawerListener(this);
        //cb
        onSc = (SwitchCompat) navNv.getMenu().findItem(R.id.on_item).getActionView();
        toSc = (SwitchCompat) navNv.getMenu().findItem(R.id.to_item).getActionView();
        thSc = (SwitchCompat) navNv.getMenu().findItem(R.id.th_item).getActionView();
        foSc = (SwitchCompat) navNv.getMenu().findItem(R.id.fo_item).getActionView();
        fiSc = (SwitchCompat) navNv.getMenu().findItem(R.id.fi_item).getActionView();
        bgIv = navNv.getHeaderView(0).findViewById(R.id.bg_iv);

        onSc.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (tabs.get(Constants.APP, false)) {
                } else {
                    tabs.put(Constants.APP, true);
                    isChanged = true;
                }

            } else {
                if (1 == tabs.size()) {
                    buttonView.setChecked(true);
                } else {
                    tabs.delete(Constants.APP);
                    isChanged = true;
                }
            }
        });
        toSc.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (tabs.get(Constants.ANDROID, false)) {

                } else {
                    tabs.put(Constants.ANDROID, true);
                    isChanged = true;
                }
            } else {
                if (1 == tabs.size()) {
                    buttonView.setChecked(true);
                } else {
                    tabs.delete(Constants.ANDROID);
                    isChanged = true;
                }
            }
        });
        thSc.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (tabs.get(Constants.IOS, false)) {

                } else {
                    tabs.put(Constants.IOS, true);
                    isChanged = true;
                }
            } else {
                if (1 == tabs.size()) {
                    buttonView.setChecked(true);
                } else {
                    tabs.delete(Constants.IOS);
                    isChanged = true;
                }
            }
        });
        foSc.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (tabs.get(Constants.WEB, false)) {

                } else {
                    tabs.put(Constants.WEB, true);
                    isChanged = true;
                }
            } else {
                if (1 == tabs.size()) {
                    buttonView.setChecked(true);
                } else {
                    tabs.delete(Constants.WEB);
                    isChanged = true;
                }
            }
        });
        fiSc.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (tabs.get(Constants.MEIZI, false)) {

                } else {
                    tabs.put(Constants.MEIZI, true);
                    isChanged = true;
                }
            } else {
                if (1 == tabs.size()) {
                    buttonView.setChecked(true);
                } else {
                    tabs.delete(Constants.MEIZI);
                    isChanged = true;
                }

            }
        });
        //
        floatFab.setOnClickListener(this);

    }

    @Override
    public void showProcess() {

    }

    @Override
    public void dismissProcess() {

    }

    @Override
    public void smooth2position() {
        callbacks.get((int) mAdapter.getItemId(contentVp.getCurrentItem())).smooth2Position(0);
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {
        if (isChanged) {
            mAdapter.notifyDataSetChanged();
        }

        isChanged = false;
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    @Override
    public void onClick(View v) {
        mainPresenter.onConsumeClick(v);
    }

    @Deprecated
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.i(TAG, "onCheckedChanged: " + buttonView.getId());
        switch (buttonView.getId()) {
            case R.id.on_item:
                if (isChecked) {
                    if (tabs.get(Constants.ANDROID, false)) {

                    } else {
                        tabs.put(Constants.ANDROID, true);
                        isChanged = true;
                    }

                } else {
                    tabs.delete(Constants.ANDROID);
                    isChanged = true;
                }
                break;
            case R.id.to_item:
                if (isChecked) {
                    if (tabs.get(Constants.APP, false)) {

                    } else {
                        tabs.put(Constants.APP, true);
                        isChanged = true;
                    }
                } else {
                    tabs.delete(Constants.APP);
                    isChanged = true;
                }
                break;
            case R.id.th_item:
                if (isChecked) {
                    if (tabs.get(Constants.MEIZI, false)) {

                    } else {
                        tabs.put(Constants.MEIZI, true);
                        isChanged = true;
                    }
                } else {
                    tabs.delete(Constants.MEIZI);
                    isChanged = true;
                }
                break;
            case R.id.fo_item:
                if (isChecked) {
                    if (tabs.get(Constants.IOS, false)) {

                    } else {
                        tabs.put(Constants.IOS, true);
                        isChanged = true;
                    }
                } else {
                    tabs.delete(Constants.IOS);
                    isChanged = true;
                }
                break;
            case R.id.fi_item:
                if (isChecked) {
                    if (tabs.get(Constants.WEB, false)) {

                    } else {
                        tabs.put(Constants.WEB, true);
                        isChanged = true;
                    }
                } else {
                    tabs.delete(Constants.WEB);
                    isChanged = true;

                }
                break;
            default:
                break;
        }
    }

    @Override
    public void setMeizi() {
        mainPresenter.updateMeizi(bgIv);
        mainPresenter.updateMeizi(girlIv);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0) {
            if (state != CollapsingToolbarLayoutState.EXPANDED) {
                state = CollapsingToolbarLayoutState.EXPANDED; // 修改状态标记为展开
            }
        } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
            if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                floatFab.hide();
                state = CollapsingToolbarLayoutState.COLLAPSED; // 修改状态标记为折叠
            }
        } else {
            if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                if (state == CollapsingToolbarLayoutState.COLLAPSED) {
                    floatFab.show();
                }
                state = CollapsingToolbarLayoutState.INTERNEDIATE; // 修改状态标记为中间
            }
        }
    }


}
