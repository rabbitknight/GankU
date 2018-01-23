package cn.southtree.ganku.mvp.view.ui.adapter;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.southtree.ganku.R;
import cn.southtree.ganku.mvp.model.remote.GankBean;
import cn.southtree.ganku.mvp.view.ui.activity.TestActivity;
import cn.southtree.ganku.mvp.view.ui.listener.OnItemClickListener;
import cn.southtree.ganku.mvp.view.ui.widget.ImageViewWrap;
import cn.southtree.ganku.utils.StringUtil;
import dagger.Component;

/**
 * @author zhuo.chen
 * @version 2017/12/25
 * @desc 主页RecyclerView列表的适配器
 */

public class MainListAdapter extends RecyclerView.Adapter {
    private final static String TAG = "MainListAdapter";
    private static final int COMMON_NOIMG = 10086;
    private static final int COMMON_IMG = 10000;
    private static final int FOOTER = 10010;

    private int lastPosition = 0;
    private List<GankBean> data;
    private Context context;
    private boolean isMeizi = false;

    public void setData(List<GankBean> data) {
        if (data != null) {
            this.data = data;
            notifyDataSetChanged();
        }
    }

    public List<GankBean> getData() {
        return data;
    }

    public void addData(List<GankBean> data) {
        int temp = this.data.size();
        this.data.addAll(data);
        notifyItemChanged(temp);
    }

    private void showItemAnimator(int position, View view) {
        if (position > lastPosition) {
            lastPosition = position;
            ObjectAnimator.ofFloat(view, "translationY", 1f * view.getHeight(), 0f)
                    .setDuration(500)
                    .start();
        }
    }

    public void enableMeizi(boolean enable) {
        this.isMeizi = enable;
    }

    public MainListAdapter(Context context, List<GankBean> data) {
        this.data = new ArrayList<>();
        if (context != null) {
            this.data.addAll(data);
            this.context = context;
        }
    }

    public MainListAdapter(Context context) {
        this(context, null);
    }

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onClick(View view, int position) {

        }
    };

    public void addOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return position == data.size() ?
                FOOTER : data.get(position).getImages() == null ? COMMON_NOIMG : COMMON_IMG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == COMMON_IMG) {
            View view;
            if (isMeizi) {
                view = LayoutInflater.from(context).inflate(R.layout.item_fragment_meizi, parent, false);
                return new MeiziViewHolder(view);
            } else {
                view = LayoutInflater.from(context).inflate(R.layout.item_fragment_data_v2, parent, false);
                return new ItemViewHolder(view);
            }

        } else if (viewType == FOOTER) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_footer, parent, false);
            return new FooterViewHolder(view);
        } else if (viewType == COMMON_NOIMG) {
            View view;
            if (isMeizi) {
                view = LayoutInflater.from(context).inflate(R.layout.item_fragment_meizi, parent, false);
                return new MeiziViewHolder(view);
            } else {
                view = LayoutInflater.from(context).inflate(R.layout.item_fragment_data, parent, false);
                return new ItemViewHolder(view);
            }
        } else {
            return null;
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == COMMON_NOIMG || getItemViewType(position) == COMMON_IMG) {
            if (isMeizi) {
                if (!StringUtil.isNull(data.get(position).getUrl())) {
                    Glide.with(context)
                            .load(data.get(position).getUrl())
                            .apply(new RequestOptions().centerCrop().error(R.drawable.ic_snow).placeholder(R.drawable.ic_snow))
                            .into(((MeiziViewHolder) holder).iv);

/*                    ((MeiziViewHolder) holder).iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            context.startActivity(new Intent(context, TestActivity.class).putExtra("meizi",data.get(position).getUrl()));
                        }
                    });*/
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onItemClickListener.onClick(v, position);
                        }
                    });
                }
            } else {
                if (!StringUtil.isNull(data.get(position).getType())) {
                    ((ItemViewHolder) holder).tvType.setText(data.get(position).getType());
                }
                if (!StringUtil.isNull(data.get(position).getDesc())) {
                    ((ItemViewHolder) holder).tvDesc.setText(data.get(position).getDesc());
                }
                if (!StringUtil.isNull(data.get(position).getWho())) {
                    ((ItemViewHolder) holder).tvWho.setText(data.get(position).getWho());
                }
                if (!StringUtil.isNull(data.get(position).getCreatedAt())) {
                    ((ItemViewHolder) holder).tvTime.setText(StringUtil.strDate2str(data.get(position).getPublishedAt()));
                }
                if (data.get(position).getImages() != null && data.get(position).getImages().size() != 0) {
                    Glide.with(context)
                            .load(data.get(position).getImages().get(0))
                            .apply(new RequestOptions().error(R.drawable.ic_snow).placeholder(R.drawable.ic_snow).centerCrop())
                            .thumbnail(0.1f)
                            .into(((ItemViewHolder) holder).tvImg);
                } else {
                    ((ItemViewHolder) holder).rlImg.setVisibility(View.GONE);
                }
                ((ItemViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onClick(v, position);
                    }
                });
            }
            showItemAnimator(position, holder.itemView);
        } else if (getItemViewType(position) == FOOTER) {
            AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) ContextCompat.getDrawable(context, R.drawable.animator_cat);
            ((FooterViewHolder) holder).imgIv.setImageDrawable(drawable);
            drawable.start();
            showItemAnimator(position, holder.itemView);
        } else {
            return;
        }

    }

    @Override
    public int getItemCount() {
        return data == null || data.size() == 0 ? 0 : data.size() + 1;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.tv_who)
        TextView tvWho;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.iv_img)
        ImageView tvImg;
        @BindView(R.id.rl_img)
        RelativeLayout rlImg;
        @BindView(R.id.tv_type)
        TextView tvType;

        ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_iv)
        ImageView imgIv;
        @BindView(R.id.desc_tv)
        TextView descTv;

        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class MeiziViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.meizi_iv)
        ImageView iv;

        public MeiziViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
