package cn.southtree.ganku.mvp.view.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import javax.inject.Inject;

import butterknife.BindView;
import cn.southtree.ganku.App;
import cn.southtree.ganku.R;
import cn.southtree.ganku.di.component.DaggerActivityComponent;
import cn.southtree.ganku.di.module.ActivityModule;
import cn.southtree.ganku.mvp.presenter.impl.LauncherPresenterImpl;
import cn.southtree.ganku.mvp.presenter.interfaces.LauncherPresenter;
import cn.southtree.ganku.mvp.view.base.BaseActivity;
import cn.southtree.ganku.mvp.view.interfaces.LauncherV;

/**
 * Launcher界面
 * @author zhuo.chen
 * @version 2018/1/24.
 */

public class LauncherActivity extends BaseActivity<LauncherPresenter> implements LauncherV,
        View.OnClickListener, ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {

    @BindView(R.id.bg_iv)
    ImageView bgIv;
    @BindView(R.id.time_btn)
    Button timeBtn;
    @Inject
    LauncherPresenterImpl presenter;

    ObjectAnimator animator1;
    ObjectAnimator animator2;
    AnimatorSet animatorSet;

    @Override
    protected int getLayout() {
        return R.layout.activity_launcher;
    }

    @Override
    protected void initInject() {
        DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(App.getmAppComponent())
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        presenter.attachView(this);
        animator1 = ObjectAnimator
                .ofFloat(bgIv, "scaleX", 1f, 1.25f)
                .setDuration(5000);
        animator2 = ObjectAnimator
                .ofFloat(bgIv, "scaleY", 1f, 1.25f)
                .setDuration(5000);
        animatorSet = new AnimatorSet();
        animator1.setInterpolator(new LinearInterpolator());
        animator2.setInterpolator(new LinearInterpolator());
        animator1.addUpdateListener(this);
        animatorSet.play(animator1).with(animator2);
        animatorSet.addListener(this);
        timeBtn.setOnClickListener(this);

        presenter.loadPic(bgIv);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (animatorSet.isRunning() || animatorSet.isStarted()) {
            animatorSet.removeAllListeners();
            animatorSet.cancel();
        }
    }

    @Override
    public void startAnimator() {
        animatorSet.start();
    }

    @Override
    public void cancelAnimator() {
        if (animatorSet.isRunning() || animatorSet.isStarted()) {
            animatorSet.removeAllListeners();
            animatorSet.cancel();
        }
        doJump(MainActivity.class, null);
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void doJump(@NonNull Class clazz, Bundle args) {
        Intent intent = new Intent(this, clazz);
        if (null != args) {
            intent.putExtras(args);
        }
        startActivity(intent);
        this.finish();
    }

    @Override
    public void changeText(String text) {
        timeBtn.setText(text);
    }

    @Override
    public void onClick(View v) {
        presenter.consumeEvent(v.getId());
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        presenter.consumeAnimator(Float.valueOf(String.valueOf(animation.getAnimatedValue())));
    }

    @Override
    public void onAnimationStart(Animator animation) {
        // nothing
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        doJump(MainActivity.class, null);
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        cancelAnimator();
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        // nothing
    }
}
