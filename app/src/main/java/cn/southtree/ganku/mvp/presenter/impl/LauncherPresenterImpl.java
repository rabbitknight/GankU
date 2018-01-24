package cn.southtree.ganku.mvp.presenter.impl;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import javax.inject.Inject;

import cn.southtree.ganku.App;
import cn.southtree.ganku.R;
import cn.southtree.ganku.mvp.presenter.base.BasePresenterImpl;
import cn.southtree.ganku.mvp.presenter.interfaces.LauncherPresenter;
import cn.southtree.ganku.mvp.view.interfaces.LauncherV;
import cn.southtree.ganku.mvp.view.ui.activity.MainActivity;

/**
 * Created by zhuo.chen on 2018/1/24.
 */

public class LauncherPresenterImpl extends BasePresenterImpl<LauncherV> implements LauncherPresenter {

    @Inject
    public LauncherPresenterImpl() {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void loadPic(ImageView imageView) {
        String girlUrl = App.getmSahre().getString("meizi", "");
        if (!"".equals(girlUrl)) {
            Glide.with(imageView.getContext())
                    .load(girlUrl)
                    .apply(new RequestOptions().optionalCenterCrop())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            view.startAnimator();
                            return false;
                        }
                    }).into(imageView);

        } else {
            view.cancelAnimator();
        }
    }

    @Override
    public void consumeEvent(int id) {
        switch (id) {
            case R.id.time_btn:
                view.cancelAnimator();
                view.doJump(MainActivity.class, null);
                break;
        }
    }

    @Override
    public void consumeAnimator(float value) {
        int str = (int) (6 - (value - 1) * 4 * 5);
        view.changeText("" + str + " 跳过");
    }


}
