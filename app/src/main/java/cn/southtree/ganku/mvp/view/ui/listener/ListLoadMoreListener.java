package cn.southtree.ganku.mvp.view.ui.listener;

import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import javax.inject.Inject;

import static android.content.ContentValues.TAG;

/**
 * Created by zhuo.chen on 2017/12/26.
 */

public class ListLoadMoreListener extends RecyclerView.OnScrollListener {
    private boolean isLoading = false;
    private int currentPage = 1;
    private int[] lasts = new int[2];


    private int lastVisibleItemPosition,totalItemCount;
    private RecyclerView.LayoutManager mLayoutManager;
    private OnLoadMoreListener defaultOnLaodMoreListener = new OnLoadMoreListener() {
        @Override
        public void loadMore(int pos) {
            Log.i(TAG, "loadMore: ");
        }
    };
    public void addOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener){
        this.defaultOnLaodMoreListener = onLoadMoreListener;
        Drawable drawable;
    }
    @Inject
    public ListLoadMoreListener(RecyclerView.LayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }
    public void setLoading(boolean isLoading){
        this.isLoading = isLoading;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (mLayoutManager instanceof GridLayoutManager){
            lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
            totalItemCount = mLayoutManager.getItemCount();
        } else if (mLayoutManager instanceof LinearLayoutManager){
            lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
            totalItemCount = mLayoutManager.getItemCount();

        }else if(mLayoutManager instanceof StaggeredGridLayoutManager){
            ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(lasts);
            lastVisibleItemPosition = Math.max(lasts[1],lasts[0]);
            totalItemCount = mLayoutManager.getItemCount();
        }else {
            return;
        }
        Log.i(TAG, "onScrolled: "+lastVisibleItemPosition+"，totalItem="+totalItemCount);
        if (lastVisibleItemPosition + 1 == totalItemCount&&!isLoading){
            defaultOnLaodMoreListener.loadMore(currentPage);
            currentPage++;
        }

    }
    public void setCurrentPage(int currentPage){
        this.currentPage = currentPage;
    }
    public interface OnLoadMoreListener {
        void loadMore(int currentPage);
    }
}
