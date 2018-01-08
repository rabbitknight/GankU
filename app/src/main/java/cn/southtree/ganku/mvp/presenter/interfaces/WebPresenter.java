package cn.southtree.ganku.mvp.presenter.interfaces;

import com.tencent.smtt.sdk.WebView;

import cn.southtree.ganku.mvp.presenter.base.IBasePresenter;

/**
 * Created by zhuo.chen on 2018/1/8.
 */

public interface WebPresenter extends IBasePresenter{
    void changeProgress(int progress);  //更改进度条
    void onRefresh();                   //刷新
}
