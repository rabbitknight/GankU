package cn.southtree.ganku.mvp.view.interfaces;

import java.util.List;

import cn.southtree.ganku.mvp.model.remote.DataBean;
import cn.southtree.ganku.mvp.model.remote.GankBean;
import cn.southtree.ganku.mvp.view.base.IBaseView;

/**
 * Fragment的接口
 * @author zhuo.chen
 * @version 2017/12/25
 */

public interface MainPagerView extends IBaseView {
    void setList(List<GankBean> gankBeans,boolean isAdd);   // 设置数据
    void showProcess();                                     // 显示加载进度
    void dismissProcess();                                  // 取消进度
    void setCurrentPage(int currentPage);                   //设置当前待加载页
    void setIsLoading(boolean isLoading);                   //设置是否在加载

}
