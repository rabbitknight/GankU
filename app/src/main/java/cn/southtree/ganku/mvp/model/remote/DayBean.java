package cn.southtree.ganku.mvp.model.remote;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Southtree on 2017/11/16.
 */

public class DayBean extends BaseBean {
    public List<String> category;
    public Result results;

    class Result {
        @SerializedName("Android")
        public List<GankBean> androidList;
        @SerializedName("iOS")
        public List<GankBean> iosList;
        @SerializedName("福利")
        public List<GankBean> welfareList;
        @SerializedName("拓展资源")
        public List<GankBean> extraList;
        @SerializedName("前端")
        public List<GankBean> frontEndList;
        @SerializedName("瞎推荐")
        public List<GankBean> casualList;
        @SerializedName("App")
        public List<GankBean> appList;
        @SerializedName("休息视频")
        public List<GankBean> videoList;
    }
}
