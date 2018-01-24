package cn.southtree.ganku.mvp.presenter.impl;


import javax.inject.Inject;

import cn.southtree.ganku.mvp.presenter.base.BasePresenterImpl;
import cn.southtree.ganku.mvp.presenter.interfaces.WebPresenter;
import cn.southtree.ganku.mvp.view.ui.activity.WebActivity;

/**
 * WebView界面
 * @author zhuo.chen
 * @version 2018/1/8.
 */

public class WebPresenterImpl extends BasePresenterImpl<WebActivity> implements WebPresenter {
    @Inject
    public WebPresenterImpl() {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void changeProgress(int progress) {
        view.changeProgress(progress);
    }

    @Override
    public void onRefresh() {
        view.refresh();
        view.reload();
    }

}
