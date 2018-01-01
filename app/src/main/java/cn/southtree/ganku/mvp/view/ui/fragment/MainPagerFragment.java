package cn.southtree.ganku.mvp.view.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import butterknife.BindView;

import cn.southtree.ganku.R;
import cn.southtree.ganku.common.Constants;
import cn.southtree.ganku.di.component.AppComponent;
import cn.southtree.ganku.di.module.ActivityModule;
import cn.southtree.ganku.mvp.model.remote.GankBean;
import cn.southtree.ganku.mvp.presenter.impl.MainPagerPresenterImpl;
import cn.southtree.ganku.mvp.view.base.BaseFragment;
import cn.southtree.ganku.mvp.view.interfaces.MainView;
import cn.southtree.ganku.mvp.view.ui.adapter.MainListAdapter;
import cn.southtree.ganku.mvp.view.ui.listener.ListLoadMoreListener;
import cn.southtree.ganku.mvp.view.ui.listener.OnItemClickListener;
import cn.southtree.ganku.mvp.view.ui.widget.MItemDecoration;
import cn.southtree.ganku.utils.ToastUtil;

/**
 * Created by zhuo.chen on 2017/12/26.
 */

public class MainPagerFragment extends BaseFragment<MainPagerPresenterImpl> implements MainView,SwipeRefreshLayout.OnRefreshListener,ListLoadMoreListener.OnLoadMoreListener,OnItemClickListener{
    //常量
    private static final String TAG = "MainPagerFragment";
    //变量
    private String pageType;
    private int pageId;
    private List<GankBean> gankBeans = new ArrayList<>();


    //注入
    @Inject
    MainPagerPresenterImpl presenter;

    @BindView(R.id.list_rv)
    RecyclerView listRv;
    @BindView(R.id.swipe_srl)
    SwipeRefreshLayout swipeSrl;

    //
    private MainListAdapter mAdapter;
    private ListLoadMoreListener loadMoreListener;
    //private int currentPage = 1;

    //Construct Method


    //获取页面布局
    @Override
    protected int getLayout() {
        return R.layout.fragment_main;
    }

    //注入依赖
    @Override
    protected void setupActivityComponent(AppComponent appComponent, ActivityModule activityModule) {
        appComponent.inject(this);
    }

    //初始化组件
    @Override
    protected void initViews() {
        Bundle args = getArguments();
        pageId = args.getInt("pageType");
        switch (pageId){
            case Constants.APP:
                pageType =  "App";
                break;
            case Constants.ANDROID:
                pageType =  "Android";
                break;
            case Constants.IOS:
                pageType =  "iOS";
                break;
            case Constants.WEB:
                pageType =  "前端";
                break;
            case Constants.MEIZI:
                pageType =  "福利";
                break;
            default:
                pageType =  "";
                break;
        }
        this.mPresenter = presenter;
        presenter.attachView(this);
        //下拉刷新swipe初始化
        swipeSrl.setColorSchemeResources(R.color.colorMainRed,R.color.colorMainWhite,R.color.colorMainDark);
        swipeSrl.setOnRefreshListener(this);
        //recyclerView
        mAdapter = new MainListAdapter(this.getContext(),gankBeans);
        if (pageType.equals("福利")){
            //listRv.setLayoutManager(new GridLayoutManager(mContext,2));
            listRv.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
            mAdapter.enableMeizi(true);
        }else{
            listRv.setLayoutManager(new LinearLayoutManager(this.getContext()));
            listRv.addItemDecoration(new MItemDecoration(mContext));
        }
        listRv.setAdapter(mAdapter);
        loadMoreListener = new ListLoadMoreListener(listRv.getLayoutManager());
        loadMoreListener.addOnLoadMoreListener(this);
        mAdapter.addOnItemClickListener(this);
        listRv.addOnScrollListener(loadMoreListener);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(listRv);
        //
        onRefresh();

    }


    @Override
    public void onRefresh() {
        // TODO: 2017/12/26 下拉刷新
        presenter.refresh(pageType);
    }


    @Override
    public void onClick(View view, int position) {
        // TODO: 2017/12/27 子item点击
        ToastUtil.showToast(mContext,"onclick:"+position);
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
        //this.currentPage = currentPage;
        loadMoreListener.setCurrentPage(currentPage);
    }

    @Override
    public void setIsLoading(boolean isLoading) {
        loadMoreListener.setLoading(isLoading);

    }

    @Override
    public void loadMore(int currentPage) {
        presenter.loadMore(currentPage,pageType);
    }
}
