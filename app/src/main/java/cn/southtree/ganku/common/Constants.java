package cn.southtree.ganku.common;

/**
 * Created by Southtree on 2017/11/16.
 */

public class Constants {
    public static final String API_START_IMG = "http://news-at.zhihu.com/api/4/start-image/1080*1776";
    public static final String API_LATEST = "http://news-at.zhihu.com/api/4/news/latest";
    public static final String API_NEWS = "http://news-at.zhihu.com/api/4/news/{id}";
    public static final String API_BEFORE = "http://news.at.zhihu.com/api/4/news/before/{date}";
    public static final String API_EXTRA = "http://news-at.zhihu.com/api/4/story-extra/#{id}";
    public static final String API_COMMNET_LONG = "http://news-at.zhihu.com/api/4/story/{}/long-comments";
    public static final String API_COMMENT_SHORT = "http://news-at.zhihu.com/api/4/story/{}/short-comments";
    public static final String API_THEME = "http://news-at.zhihu.com/api/4/theme/{}";

    public static final String GANK_IO = "http://gank.io/api/";
    //public static final String API_GNAK_HISTORY = "/day/history";
    //public static final String API_GANK_DAY = "/history/content/day";
    //public static final String API_GANK_DATA = "/data";
    //public static final String API_GANK_SERACH = "/search/query/listview/category/Android/count/10/page/1";

    public static final int APP = 1;    // 1
    public static final int ANDROID = 2;// 10
    public static final int IOS = 4;    // 100
    public static final int WEB = 8;    // 1000
    public static final int MEIZI = 16;  // 10000


}
