package cn.southtree.ganku.mvp.presenter.interfaces;

import cn.southtree.ganku.mvp.presenter.base.IBasePresenter;

/**
 * Created by zhuo.chen on 2017/12/25.
 */

public interface MainPagerPresenter extends IBasePresenter {
    void loadMore(int currentPage,String type);
    void refresh(String type);
}
