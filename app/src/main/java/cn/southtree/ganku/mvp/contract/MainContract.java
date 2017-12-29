package cn.southtree.ganku.mvp.contract;

import cn.southtree.ganku.mvp.presenter.base.IBasePresenter;
import cn.southtree.ganku.mvp.view.base.IBaseView;

/**
 * Created by zhuo.chen on 2017/12/23.
 */

public class MainContract {
    interface MainView extends IBaseView{
        void onRefresh() ;
        void onLoad();
        void onClick();
        void onShowProgress();
        void onShowToast();
    }
    interface MainPresenter extends IBasePresenter<MainView>{

    }

}
