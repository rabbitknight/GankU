package cn.southtree.ganku.mvp.presenter.interfaces;

import android.view.View;
import android.widget.ImageView;

import cn.southtree.ganku.mvp.presenter.base.IBasePresenter;

/**
 * Created by zhuo.chen on 2017/12/23.
 */

public interface MainPresenter extends IBasePresenter {
    void updateMeizi(ImageView view);

    void onConsumeClick(View view);

}
