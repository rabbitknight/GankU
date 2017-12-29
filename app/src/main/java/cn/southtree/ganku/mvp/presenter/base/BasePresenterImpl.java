package cn.southtree.ganku.mvp.presenter.base;

import android.support.annotation.NonNull;

import cn.southtree.ganku.mvp.view.base.IBaseView;
import rx.Subscription;

/**
 * Created by zhuo.chen on 2017/12/23.
 */

public abstract class BasePresenterImpl<T extends IBaseView> implements IBasePresenter {
    protected Subscription mSubscription;
    protected T view;

    @Override
    public void attachView(@NonNull IBaseView view) {
        this.view = (T) view;
    }

    @Override
    public void detachView() {
        this.view = null;
        if (mSubscription != null&&mSubscription.isUnsubscribed()){
            mSubscription.unsubscribe();
        }
    }

}
