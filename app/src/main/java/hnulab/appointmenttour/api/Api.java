package hnulab.appointmenttour.api;


import java.util.List;

import hnulab.appointmenttour.bean.GankResult;
import hnulab.appointmenttour.bean.GankResultWelfare;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by mouren on 2017/3/12.
 */

public interface Api {
    //获取图片
    @GET("data/福利/16/{page}")
    Observable<GankResult<List<GankResultWelfare>>> getGirlPics(@Path("page") int page);
}
