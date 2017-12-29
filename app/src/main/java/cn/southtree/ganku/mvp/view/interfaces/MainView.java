package cn.southtree.ganku.mvp.view.interfaces;

import java.util.ArrayList;
import java.util.List;

import cn.southtree.ganku.mvp.model.remote.GankBean;
import cn.southtree.ganku.mvp.view.base.IBaseView;

/**
 * @author zhuo.chen
 * @version 2017/12/23
 */

public interface MainView extends IBaseView {
    void setList(List<GankBean> gankBeans,boolean isAdd);
    void showProcess();
    void dismissProcess();
    void setCurrentPage(int currentPage);
    void setIsLoading(boolean isLoading);
}
