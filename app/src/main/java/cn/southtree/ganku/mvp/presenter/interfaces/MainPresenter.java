package cn.southtree.ganku.mvp.presenter.interfaces;

import android.view.View;

import cn.southtree.ganku.mvp.presenter.base.IBasePresenter;

/**
 * Created by zhuo.chen on 2017/12/23.
 */

public interface MainPresenter extends IBasePresenter {
    // TODO: 2017/12/23 MainPresenter
    void onRefresh();
    void onLoadMore();
    void onClick(View view);

}
