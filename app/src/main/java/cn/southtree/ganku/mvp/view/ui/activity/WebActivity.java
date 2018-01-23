package cn.southtree.ganku.mvp.view.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import javax.inject.Inject;

import butterknife.BindView;
import cn.southtree.ganku.App;
import cn.southtree.ganku.R;
import cn.southtree.ganku.di.component.DaggerActivityComponent;
import cn.southtree.ganku.di.module.ActivityModule;
import cn.southtree.ganku.mvp.presenter.impl.WebPresenterImpl;
import cn.southtree.ganku.mvp.view.base.BaseActivity;
import cn.southtree.ganku.mvp.view.interfaces.WebV;
import cn.southtree.ganku.utils.StringUtil;

/**
 * 内部浏览器
 * 使用tecent x5内核，版本：3.5
 *
 * @author zhuo.chen
 * @version 2018/1/5.
 */

public class WebActivity extends BaseActivity<WebPresenterImpl> implements WebV, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = WebActivity.class.getSimpleName();
    @Inject
    WebPresenterImpl presenter;

    @BindView(R.id.x5web_wv)
    WebView x5webWv;
    @BindView(R.id.progress_pb)
    ProgressBar progressPb;
    @BindView(R.id.toolbar_tb)
    Toolbar toolbar;
    @BindView(R.id.swipe_srl)
    SwipeRefreshLayout swipeSrl;

    private WebSettings mWebSettings;

    private String loadUrl;
    private ActionBar actionBar;
    private String title;

    @Override
    protected int getLayout() {
        return R.layout.activity_web;
    }

    @Override
    protected void initInject() {
        DaggerActivityComponent.builder()
                .appComponent(App.getmAppComponent())
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        mPresenter = presenter;
        mPresenter.attachView(this);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        loadUrl = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("name");
        toolbar.setTitle(title);
        if (!StringUtil.isNull(loadUrl)) {
            x5webWv.loadUrl(loadUrl);

        }
        swipeSrl.setOnRefreshListener(this);
        mWebSettings = x5webWv.getSettings();
        initWebView();

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onBackPressed() {
        if (x5webWv.canGoBack()) {
            x5webWv.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        presenter.onRefresh();
    }

    @Override
    public void setTitle(String title) {
        if (toolbar != null && !StringUtil.isNull(title)) {
            toolbar.setTitle(title);
        }
    }

    @Override
    public void changeProgress(int progress) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressPb.setProgress(progress, true);
        } else {
            progressPb.setProgress(progress);
        }
    }

    @Override
    public void refresh() {
        swipeSrl.setRefreshing(true);
    }

    @Override
    public void reload() {
        if (x5webWv != null) {
            x5webWv.reload();
        }
    }

    private void initWebView() {
        x5webWv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
                progressPb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                if (progressPb != null && progressPb.getVisibility() != View.INVISIBLE) {
                    progressPb.setVisibility(View.INVISIBLE);
                }
                swipeSrl.setRefreshing(false);
            }

        });
        x5webWv.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int i) {

                if (progressPb != null && progressPb.getVisibility() == View.VISIBLE) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressPb.setProgress(i);
                        }
                    });
                }
                super.onProgressChanged(webView, i);
            }

            @Override
            public void onReceivedTitle(WebView webView, String s) {
                if (toolbar != null) {
                    toolbar.setTitle(s);
                }
                super.onReceivedTitle(webView, s);
            }
        });
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setDatabaseEnabled(true);
        //缓存数据的存储地址
        String appCacheDir = this.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        mWebSettings.setAppCachePath(appCacheDir);
        mWebSettings.setAppCacheEnabled(true);
        //缓存模式
        mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebSettings.setAllowFileAccess(true);
        //开启混合模式解决图片不显示的问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebSettings.setMixedContentMode(0);
        }

    }
}
