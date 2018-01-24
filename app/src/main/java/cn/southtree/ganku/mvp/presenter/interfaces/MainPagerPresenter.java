package cn.southtree.ganku.mvp.presenter.interfaces;

import android.view.View;

import cn.southtree.ganku.mvp.presenter.base.IBasePresenter;

/**
 * Created by zhuo.chen on 2017/12/25.
 */

public interface MainPagerPresenter extends IBasePresenter {
    void loadMore(int currentPage, String type);     //进行加载

    void refresh(String type);                      //刷新

    void consumeClickEvent(View view, int position);//消费点击事件

    void setPageType(String type);

}
