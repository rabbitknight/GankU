package cn.southtree.ganku.mvp.view.interfaces;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import cn.southtree.ganku.mvp.view.base.IBaseView;
import cn.southtree.ganku.mvp.view.ui.activity.MainActivity;

/**
 * @author zhuo.chen
 * @version 2018/1/24.
 */

public interface LauncherV extends IBaseView {
    void startAnimator();

    void cancelAnimator();

    void onPrepare();

    void doJump(@NonNull Class clazz, @Nullable Bundle args);

    void changeText(String text);

}
