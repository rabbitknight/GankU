package cn.southtree.ganku.mvp.presenter.impl;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import javax.inject.Inject;

import cn.southtree.ganku.App;
import cn.southtree.ganku.R;
import cn.southtree.ganku.mvp.presenter.base.BasePresenterImpl;
import cn.southtree.ganku.mvp.presenter.interfaces.MainPresenter;
import cn.southtree.ganku.mvp.view.interfaces.MainV;
import cn.southtree.ganku.mvp.view.ui.activity.MainActivity;
import cn.southtree.ganku.mvp.view.ui.listener.OnFrag2ActivityCallBack;

/**
 * Created by zhuo.chen on 2017/12/23.
 */

public class MainPresenterImpl extends BasePresenterImpl<MainV> implements MainPresenter {

    @Inject
    public MainPresenterImpl() {

    }

    @Override
    public void updateMeizi(ImageView v) {
        String girlUrl = App.getmSahre().getString("meizi", "");
        if (!girlUrl.equals("")) {
            Glide.with(v.getContext())
                    .load(girlUrl)
                    .apply(new RequestOptions().centerCrop())
                    .into(v);
        }
    }

    @Override
    public void onConsumeClick(View view) {
        switch (view.getId()) {
            case R.id.float_fab:
                this.view.smooth2position();
                break;
        }
    }

    @Override
    public void onCreate() {

    }

}
