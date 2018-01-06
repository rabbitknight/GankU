package cn.southtree.ganku.mvp.view.ui.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import butterknife.BindView;

import cn.southtree.ganku.App;
import cn.southtree.ganku.R;
import cn.southtree.ganku.common.Constants;
import cn.southtree.ganku.di.component.AppComponent;
import cn.southtree.ganku.di.component.DaggerFragmentComponent;
import cn.southtree.ganku.di.component.FragmentComponent;
import cn.southtree.ganku.di.module.ActivityModule;
import cn.southtree.ganku.di.module.FragmentModule;
import cn.southtree.ganku.di.module.RecyclerViewModule;
import cn.southtree.ganku.mvp.model.remote.GankBean;
import cn.southtree.ganku.mvp.presenter.impl.MainPagerPresenterImpl;
import cn.southtree.ganku.mvp.view.base.BaseFragment;
import cn.southtree.ganku.mvp.view.interfaces.MainPagerView;
import cn.southtree.ganku.mvp.view.ui.activity.WebActivity;
import cn.southtree.ganku.mvp.view.ui.adapter.MainListAdapter;
import cn.southtree.ganku.mvp.view.ui.listener.ListLoadMoreListener;
import cn.southtree.ganku.mvp.view.ui.listener.OnFrag2ActivityCallBack;
import cn.southtree.ganku.mvp.view.ui.listener.OnItemClickListener;
import cn.southtree.ganku.mvp.view.ui.widget.ImageViewWrap;
import cn.southtree.ganku.mvp.view.ui.widget.MItemDecoration;
import cn.southtree.ganku.utils.ToastUtil;
import dagger.Component;

/**
 * Created by zhuo.chen on 2017/12/26.
 */

public class MainPagerFragment extends BaseFragment<MainPagerPresenterImpl> implements MainPagerView,SwipeRefreshLayout.OnRefreshListener,ListLoadMoreListener.OnLoadMoreListener,OnItemClickListener,View.OnClickListener{
    //常量
    private static final String TAG = "MainPagerFragment";

    //变量
    private String pageType;
    private int pageId;
    private List<GankBean> gankBeans = new ArrayList<>();
    private FragmentComponent mComponent;
    private ListLoadMoreListener loadMoreListener;
    private AlertDialog girlDialog;
    private View content;
    private ImageView img;
    private Button btn;
    private ImageViewWrap imageViewWrap;
    public OnFrag2ActivityCallBack callBack;

    //注入
    @Inject
    MainPagerPresenterImpl presenter;
    @Inject
    LinearSnapHelper linearSnapHelper;
    @Inject
    MainListAdapter mAdapter;
    @Inject
    LinearLayoutManager linearLayoutManager;
    @Inject
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    @Inject
    MItemDecoration itemDecoration;

    //注入组件
    @BindView(R.id.list_rv)
    RecyclerView listRv;
    @BindView(R.id.swipe_srl)
    SwipeRefreshLayout swipeSrl;

    //获取页面布局
    @Override
    protected int getLayout() {
        return R.layout.fragment_main;
    }

    //依赖注入
    @Override
    protected void initInject() {
        mComponent = DaggerFragmentComponent.builder()
                .appComponent(App.getmAppComponent())
                .recyclerViewModule(new RecyclerViewModule(gankBeans,this.mContext))
                .fragmentModule(new FragmentModule(this))
                .build();
        mComponent.inject(this);
    }

    public FragmentComponent getmComponent(){
        return mComponent;
    }

    //初始化组件
    @Override
    protected void initViews() {
        Bundle args = getArguments();
        pageId = args.getInt("pageType");
        pageType = getPageType(pageId);
        this.mPresenter = presenter;
        presenter.attachView(this);
        presenter.setPageType(pageType);
        //下拉刷新swipe初始化
        swipeSrl.setColorSchemeResources(R.color.colorMainRed,R.color.colorMainWhite,R.color.colorMainDark);
        swipeSrl.setOnRefreshListener(this);
        //recyclerView
        if (pageType.equals("福利")){
            listRv.setLayoutManager(staggeredGridLayoutManager);
            mAdapter.enableMeizi(true);
        }else{
            listRv.setLayoutManager(linearLayoutManager);
            listRv.addItemDecoration(itemDecoration);
        }
        listRv.setAdapter(mAdapter);
        loadMoreListener = new ListLoadMoreListener(listRv.getLayoutManager());
        loadMoreListener.addOnLoadMoreListener(this);
        mAdapter.addOnItemClickListener(this);
        listRv.addOnScrollListener(loadMoreListener);
        linearSnapHelper.attachToRecyclerView(listRv);
        //
        onRefresh();
        girlDialog = new AlertDialog.Builder(mContext).create();

        content = LayoutInflater.from(mContext).inflate(R.layout.item_browser_img,null);
        img = content.findViewById(R.id.img_iv);
        btn = content.findViewById(R.id.close_btn);
        btn.setOnClickListener(this);



    }

    public List<GankBean> getGankBeans(){
        return mAdapter.getData();
    }

    public void setOnFrag2ActivityCallBack(OnFrag2ActivityCallBack listener){
        this.callBack = listener;
    }

    @Override
    public void onRefresh() {
        presenter.refresh(pageType);
    }

    @Override
    public void onClick(View view, int position) {
        presenter.consumeClickEvent(view, position);
    }

    @Override
    public void setList(List<GankBean> gankBeans,boolean isAdd) {
        if (isAdd){
            mAdapter.addData(gankBeans);
        }else {
            mAdapter.setData(gankBeans);
        }
    }

    @Override
    public void showProcess() {
        if (!swipeSrl.isRefreshing()){
            swipeSrl.setRefreshing(true);
        }
    }

    @Override
    public void dismissProcess() {
        if (swipeSrl.isRefreshing()){
            swipeSrl.setRefreshing(false);
        }
    }

    @Override
    public void setCurrentPage(int currentPage) {
        loadMoreListener.setCurrentPage(currentPage);
    }

    @Override
    public void setIsLoading(boolean isLoading) {
        loadMoreListener.setLoading(isLoading);
    }

    @Override
    public void doJump(Intent intent) {
        if (intent != null) {
            startActivity(intent);
        }
    }

    @Override
    public void showGirl(String url) {
        imageViewWrap = new ImageViewWrap(img);
        Glide.with(mContext).load(url).into(imageViewWrap.getInstance());
        girlDialog.setView(content);
        girlDialog.show();
    }

    @Override
    public void closeGirl() {
        if (girlDialog.isShowing()){
            girlDialog.dismiss();
        }
    }

    @Override
    public void loadMore(int currentPage) {
        presenter.loadMore(currentPage,pageType);
    }

    @Override
    public void onClick(View v) {
        presenter.consumeClickEvent(v,-1);
    }
    // 获取当前界面的pageType
    private String getPageType(int pageId){
        switch (pageId){
            case Constants.APP:
                return "App";
            case Constants.ANDROID:
                return "Android";
            case Constants.IOS:
                return  "iOS";
            case Constants.WEB:
                return "前端";
            case Constants.MEIZI:
                return "福利";
            default:
                return "";
        }
    }
}
