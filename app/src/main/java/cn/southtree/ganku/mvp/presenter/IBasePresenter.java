package cn.southtree.ganku.mvp.presenter;

import cn.southtree.ganku.mvp.view.base.IBaseView;

/**
 * Created by Southtree on 2017/11/15.
 */

public interface IBasePresenter<T extends IBaseView> {
    void attachView(T view);
    void detachView();

}
