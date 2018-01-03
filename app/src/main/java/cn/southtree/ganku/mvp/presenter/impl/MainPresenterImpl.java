package cn.southtree.ganku.mvp.presenter.impl;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import javax.inject.Inject;

import cn.southtree.ganku.App;
import cn.southtree.ganku.mvp.presenter.base.BasePresenterImpl;
import cn.southtree.ganku.mvp.presenter.interfaces.MainPresenter;
import cn.southtree.ganku.mvp.view.ui.activity.MainActivity;
import cn.southtree.ganku.mvp.view.ui.listener.OnFrag2ActivityCallBack;

/**
 * Created by zhuo.chen on 2017/12/23.
 */

public class MainPresenterImpl extends BasePresenterImpl<MainActivity> implements MainPresenter{

    @Inject
    public MainPresenterImpl(){

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void updateMeizi(ImageView v) {
        String girlUrl = App.getmSahre().getString("meizi","");
        if (!girlUrl.equals("")){
            Glide.with(view).load(girlUrl).apply(new RequestOptions().centerCrop()).into(v);
        }
    }

    @Override
    public void onCreate() {

    }

}
