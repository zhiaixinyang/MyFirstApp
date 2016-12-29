package com.example.greatbook.apis;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by MBENBEN on 2016/11/20.
 */

public interface GreatBookApis {
    String HOST="http://www.sbkk8.cn/";

    //返回url源码通过jsoup解析拿数据
    @GET("mingzhu")
    Observable<String> getBookKindList();

    @GET("mingzhu/{path}")
    Observable<String> getBookKind(@Path("path") String path);

    @GET("mingzhu/{path}")
    Observable<String> getBookDes(@Path("path") String path);

    @GET("mingzhu/{path}")
    Observable<String> getBookDetail(@Path("path") String path);

    @GET("mingzhu/{path}")
    Observable<String> getNewBook(@Path("path") String path);

}
