package cn.southtree.ganku.mvp.view.ui.activity;

import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import cn.southtree.ganku.R;
import cn.southtree.ganku.mvp.view.base.BaseActivity;
import cn.southtree.ganku.utils.StringUtil;

/**
 * 内部浏览器
 * 使用tecent x5内核，版本：3.5
 * @author zhuo.chen
 * @version 2018/1/5.
 */

public class WebActivity extends BaseActivity {
    private static final String TAG = WebActivity.class.getSimpleName();

    @BindView(R.id.x5web_wv)
    WebView x5webWv;
    @BindView(R.id.progress_pb)
    ProgressBar progressPb;
    @BindView(R.id.toolbar_tb)
    Toolbar toolbar;

    private String loadUrl;
    private ActionBar actionBar;
    private String title;

    @Override
    protected int getLayout() {
        return R.layout.activity_web;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        loadUrl = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("name");
        toolbar.setTitle(title);
        if (!StringUtil.isNull(loadUrl)) {
            x5webWv.loadUrl(loadUrl);

        }

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
                if (progressPb!=null&&progressPb.getVisibility() != View.INVISIBLE){
                    progressPb.setVisibility(View.INVISIBLE);
                }
            }

        });
        x5webWv.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int i) {
                if (progressPb!=null&&progressPb.getVisibility()==View.VISIBLE){
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
                if (toolbar!=null){
                    toolbar.setTitle(title);
                    toolbar.setSubtitle(s);
                }
                super.onReceivedTitle(webView, s);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (x5webWv.canGoBack()){
            x5webWv.goBack();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            onBackPressed();
            return true;
        }else {
            return super.onKeyDown(keyCode, event);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
                default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}
