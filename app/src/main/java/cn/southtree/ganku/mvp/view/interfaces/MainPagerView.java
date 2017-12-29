package cn.southtree.ganku.mvp.view.interfaces;

import java.util.List;

import cn.southtree.ganku.mvp.model.remote.DataBean;
import cn.southtree.ganku.mvp.model.remote.GankBean;
import cn.southtree.ganku.mvp.view.base.IBaseView;

/**
 * @author zhuo.chen
 * @version 2017/12/25
 */

public interface MainPagerView extends IBaseView {
    void setList(List<GankBean> data);//设置数据
    void showProgress();            //显示加载进度条
    void dismissProgress();         //取消加载

}
