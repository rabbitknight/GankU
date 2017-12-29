package cn.southtree.ganku.mvp.view.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.southtree.ganku.App;
import cn.southtree.ganku.R;
import cn.southtree.ganku.di.component.AppComponent;
import cn.southtree.ganku.di.module.ActivityModule;
import cn.southtree.ganku.mvp.presenter.base.IBasePresenter;

/**
 * Created by zhuo.chen on 2017/12/25.
 */

public abstract class BaseFragment<T extends IBasePresenter> extends Fragment {
    protected T mPresenter;         //注入presenter
    protected Activity mContext;    //当前环境上下文
    private Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = this.getActivity();
        View view = LayoutInflater.from(mContext).inflate(getLayout(),container,false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupActivityComponent(App.getmAppComponent(),new ActivityModule(mContext));
        initViews();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachView();
        unbinder.unbind();
    }

    //获取LayoutId：R.layout.id
    protected abstract int getLayout();

    //依赖注入入口
    protected abstract void setupActivityComponent(AppComponent appComponent, ActivityModule activityModule);
    //初始化View
    protected abstract void initViews();

}
