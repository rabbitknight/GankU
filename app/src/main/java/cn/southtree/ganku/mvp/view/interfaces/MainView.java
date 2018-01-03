package cn.southtree.ganku.mvp.view.interfaces;

import java.util.ArrayList;
import java.util.List;

import cn.southtree.ganku.mvp.model.remote.GankBean;
import cn.southtree.ganku.mvp.view.base.IBaseView;

/**
 * MainActivity 接口
 * @author zhuo.chen
 * @version 2017/12/23
 */

public interface MainView extends IBaseView {
    void showProcess();
    void dismissProcess();
}
