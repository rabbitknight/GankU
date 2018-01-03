package cn.southtree.ganku.mvp.presenter.impl;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import javax.inject.Inject;

import cn.southtree.ganku.App;
import cn.southtree.ganku.mvp.model.remote.DataBean;
import cn.southtree.ganku.mvp.model.remote.GankBean;
import cn.southtree.ganku.mvp.presenter.base.BasePresenterImpl;
import cn.southtree.ganku.mvp.presenter.interfaces.MainPagerPresenter;
import cn.southtree.ganku.mvp.view.interfaces.MainPagerView;
import cn.southtree.ganku.mvp.view.ui.fragment.MainPagerFragment;
import cn.southtree.ganku.net.api.GankApiService;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by zhuo.chen on 2017/12/25.
 */

public class MainPagerPresenterImpl extends BasePresenterImpl<MainPagerFragment> implements MainPagerPresenter {
    @Inject
    GankApiService gankApiService;

    @Inject
    public MainPagerPresenterImpl(){
        App.getmAppComponent().inject(this);
    }


    @Override
    public void loadMore(int currentPage,String type) {
        view.setIsLoading(true);
        Log.i(TAG, "loadMore: "+currentPage);

        gankApiService.getData(type,10,currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DataBean dataBean) {
                        view.setList(dataBean.results,true);

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();

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
        Log.i(TAG, "refresh: "+1);
        gankApiService.getData(type,10,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DataBean dataBean) {
                        view.setList(dataBean.results,false);
                        String url = App.getmSahre().getString("meizi","");
                        if (TextUtils.equals(type,"福利")){
                            if (url.equals(dataBean.results.get(0).getUrl())){

                            }else {
                                SharedPreferences.Editor editor = App.getmSahre().edit();
                                editor.putString("meizi",dataBean.results.get(0).getUrl());
                                editor.apply();
                            }

                            view.callBack.setMeizi();

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
    public void onCreate() {

    }
}
