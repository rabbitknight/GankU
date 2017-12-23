package cn.southtree.ganku.net.api;

import cn.southtree.ganku.mvp.model.remote.DataBean;
import cn.southtree.ganku.mvp.model.remote.DayBean;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @description gank.io的api
 * @author rabbitknight
 */

public interface GankApiService {
    /**
     * @description 获取分类数据
     * @param category 分类
     * @param number    分页大小
     * @param page      页码
     * @return
     */
    @GET("data/{type}/{number}/{page}")
    Observable<DataBean> getData(@Path("type")String category,@Path("number") int number,@Path("page")int page);

    /**
     * @description 获取每日数据
     * @param year 年
     * @param month 月
     * @param day 日
     * @return
     */
    @GET("day/{year}/{month}/{day}")
    Observable<DayBean> getDay(@Path("year")int year,@Path("month")int month,@Path("day")int day);


}
