package com.example.greatbook.apis;

import com.example.greatbook.constants.Url;
import com.example.greatbook.model.MainJokBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by MBENBEN on 2016/11/20.
 */

public interface JuHeApis {
    /**
     *     http://japi.juhe.cn/joke/content/list.from?
     *     key=92e45e290c194f53665fa891af8a2c05&
     *     page=2&
     *     pagesize=10&
     *     sort=asc&
     *     time=1418745237
     */
    String HOST="http://japi.juhe.cn/joke/content/";

    @GET("list.from")
    Observable<MainJokBean> getMainJokData(@Query("key") String key,
                                           @Query("page") String page,
                                           @Query("pagesize") String pagesize,
                                           @Query("sort") String sort,
                                           @Query("time") String time);

}
