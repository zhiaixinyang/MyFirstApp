package com.example.greatbook.apis;

import com.example.greatbook.model.WeChatItemBean;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by MBENBEN on 2016/11/27.
 */

public interface WeChatApis {
    String HOST = "http://api.tianapi.com/";

    /**
     * 微信精选列表
     */
    @GET("wxnew")
    Observable<WeChatHttpResponse<List<WeChatItemBean>>> getWeChatHot(@Query("key") String key, @Query("num") int num, @Query("page") int page);

    /**
     * 微信精选列表
     */
    @GET("wxnew")
    Observable<WeChatHttpResponse<List<WeChatItemBean>>> getWeChatHotSearch(@Query("key") String key, @Query("num") int num, @Query("page") int page, @Query("word") String word);
}
