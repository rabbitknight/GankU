package cn.southtree.ganku.mvp.presenter.impl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import javax.inject.Inject;

import cn.southtree.ganku.App;
import cn.southtree.ganku.R;
import cn.southtree.ganku.mvp.model.remote.DataBean;
import cn.southtree.ganku.mvp.presenter.base.BasePresenterImpl;
import cn.southtree.ganku.mvp.presenter.interfaces.MainPagerPresenter;
import cn.southtree.ganku.mvp.view.ui.activity.WebActivity;
import cn.southtree.ganku.mvp.view.ui.fragment.MainPagerFragment;
import cn.southtree.ganku.net.api.GankApiService;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * tabFragment的P层
 *
 * @author zhuo.chen
 * @version 2017/12/25.
 */

public class MainPagerPresenterImpl extends BasePresenterImpl<MainPagerFragment> implements MainPagerPresenter {
    @Inject
    GankApiService gankApiService;

    private String pageType;

    @Inject
    public MainPagerPresenterImpl() {

    }

    @Override
    public void loadMore(int currentPage, String type) {
        view.setIsLoading(true);
        Log.i(TAG, "loadMore: " + currentPage);

        gankApiService.getData(type, 10, currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DataBean dataBean) {
                        view.setList(dataBean.results, true);

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.setIsLoading(false);

                    }

                    @Override
                    public void onComplete() {
                        //view.setCurrentPage(currentPage+1);
                        view.setIsLoading(false);

                    }
                });

    }

    @Override
    public void refresh(String type) {
        view.showProcess();
        Log.i(TAG, "refresh: " + 1);
        gankApiService.getData(type, 10, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DataBean dataBean) {
                        view.setList(dataBean.results, false);
                        String url = App.getmSahre().getString("meizi", "");
                        if (TextUtils.equals(type, "福利")) {
                            if (url.equals(dataBean.results.get(0).getUrl())) {

                            } else {
                                SharedPreferences.Editor editor = App.getmSahre().edit();
                                editor.putString("meizi", dataBean.results.get(0).getUrl());
                                editor.apply();
                            }

                            //view.callBack.setMeizi();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        view.dismissProcess();
                    }

                    @Override
                    public void onComplete() {
                        view.setCurrentPage(2);
                        view.dismissProcess();
                    }
                });

    }

    @Override
    public void consumeClickEvent(View view, int position) {
        if (position > -1) {
            if (pageType.equals("福利")) {
                this.view.showGirl(this.view.getGankBeans().get(position).getUrl());
            } else {
                if (App.canChromeLoad) {
                    launchUrl(this.view.getGankBeans().get(position).getUrl());
                } else {
                    this.view.doJump(new Intent(this.view.getContext(), WebActivity.class)
                            .putExtra("url", this.view.getGankBeans().get(position).getUrl())
                            .putExtra("name", this.view.getGankBeans().get(position).getDesc()));
                }
            }
        } else {
            switch (view.getId()) {
                case R.id.close_btn:
                    this.view.closeGirl();
                    break;
                case R.id.float_fab:
                    // TODO: 2018/1/9

            }
        }
    }

    @Deprecated
    // 使用Chrome进行显示网页
    private void launchUrl(String url) {
        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
        intentBuilder.setToolbarColor(ContextCompat.getColor(view.getContext(), R.color.colorMainDark));
        CustomTabsIntent intent = intentBuilder.build();
        intent.launchUrl(view.getContext(), Uri.parse(url));
    }

    @Override
    public void setPageType(String type) {
        this.pageType = type;
    }

    @Override
    public void onCreate() {
        view.getmComponent().inject(this);
    }


}
