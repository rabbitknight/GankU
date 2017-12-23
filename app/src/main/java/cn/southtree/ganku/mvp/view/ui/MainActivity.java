package cn.southtree.ganku.mvp.view.ui;

import java.util.List;

import javax.inject.Inject;

import cn.southtree.ganku.App;
import cn.southtree.ganku.R;
import cn.southtree.ganku.di.component.AppComponent;
import cn.southtree.ganku.di.module.ActivityModule;
import cn.southtree.ganku.mvp.model.remote.GankBean;
import cn.southtree.ganku.mvp.presenter.impl.MainPresenterImpl;
import cn.southtree.ganku.mvp.view.base.BaseActivity;
import cn.southtree.ganku.mvp.view.interfaces.MainView;

import okhttp3.OkHttpClient;


public class MainActivity extends BaseActivity<MainPresenterImpl> implements MainView {
    @Inject
    public OkHttpClient okHttpClient;
    @Inject
    public MainPresenterImpl mainPresenter;

    private App app;

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
        mPresenter = mainPresenter;
        mPresenter.attachView(this);

    }

    private void getData(){

    }

    @Override
    public void setList(List<GankBean> gankBeans) {

    }

    @Override
    public void showProcess() {

    }

    @Override
    public void dismissProcess() {

    }
}
