package cn.southtree.ganku.di.module;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import cn.southtree.ganku.mvp.model.remote.GankBean;
import cn.southtree.ganku.mvp.view.ui.adapter.MainListAdapter;
import cn.southtree.ganku.mvp.view.ui.fragment.MainPagerFragment;
import dagger.Module;
import dagger.Provides;

/**
 * RecyclerView相关的类的注入，诸如,Adapter，ItemDecoration
 * @author zhuo.chen
 * @version 2018/1/6.
 */
@Module
public class RecyclerViewModule {
    private List<GankBean> gankBeans = new ArrayList<>();
    private Context context;
    public RecyclerViewModule(List<GankBean> gankBeans,Context context){
        this.gankBeans = gankBeans;
        this.context = context;
    }
    @Provides
    public MainListAdapter provideAdapter(){
        return new MainListAdapter(context,gankBeans);
    }
    @Provides
    public StaggeredGridLayoutManager provideSLayoutManager(){
        return new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
    }
    @Provides
    public LinearLayoutManager provideLLayoutManager(){
        return new LinearLayoutManager(context);
    }
    @Provides
    public LinearSnapHelper provideLSnapHelper(){
        return new LinearSnapHelper();
    }
}
