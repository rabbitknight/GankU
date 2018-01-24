package cn.southtree.ganku.mvp.presenter.interfaces;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.southtree.ganku.mvp.presenter.base.IBasePresenter;

/**
 * Created by zhuo.chen on 2018/1/24.
 */

public interface LauncherPresenter extends IBasePresenter {
    void loadPic(ImageView imageView);

    void consumeEvent(int id);

    void consumeAnimator(float value);

}
