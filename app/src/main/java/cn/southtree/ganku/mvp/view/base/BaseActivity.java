package cn.southtree.ganku.mvp.view.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import cn.southtree.ganku.App;
import cn.southtree.ganku.di.component.AppComponent;
import cn.southtree.ganku.di.module.ActivityModule;
import cn.southtree.ganku.mvp.presenter.IBasePresenter;

/**
 * Created by Southtree on 2017/11/15.
 */

public abstract class BaseActivity<T extends IBasePresenter> extends AppCompatActivity implements IBaseView {
    @Inject
    protected T mPresenter;         //注入presenter
    protected Activity mContext;    //当前环境上下文


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mContext = this;
        setupActivityComponent(App.getmAppComponent(),new ActivityModule(this));
        ButterKnife.bind(this);
        if (mPresenter!=null) mPresenter.attachView(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachView();
    }

    //获取LayoutId：R.layout.id
    protected abstract int getLayout();
    //依赖注入入口
    protected abstract void setupActivityComponent(AppComponent appComponent,ActivityModule activityModule);
}
