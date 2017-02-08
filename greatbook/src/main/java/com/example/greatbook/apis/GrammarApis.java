package com.example.greatbook.apis;

import com.example.greatbook.model.GrammarContent;
import com.example.greatbook.model.GrammarKind;
import com.example.greatbook.model.GrammarKindIndex;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by MBENBEN on 2017/1/26.
 */

public interface GrammarApis {
    String BASE_URL="http://www.ohonor.xyz/EnglishGrammar/";

    @GET("getGrammarMainIndex")
    Observable<List<GrammarKind>> getGrammarKindList();

    //获取所有的语法目录
    @GET("getGrammarAllIndex")
    Observable<List<GrammarKindIndex>> getGrammarKindAllIndexList();

    //获取所有的语法目录的前20个
    @GET("getGrammarAllIndexTop")
    Observable<List<GrammarKindIndex>> getGrammarKindIndexTopList();

    //获取所有的语法详情
    @GET("getContentAll")
    Observable<List<GrammarContent>> getContentAllList();

    //获取所有的语法详情的前20个
    @GET("getContentAllTop")
    Observable<List<GrammarContent>> getContentAllTopList();

    //queryGrammarAllKindByHref?href=...
    @GET("queryGrammarAllKindByHref")
    Observable<List<GrammarKindIndex>> queryGrammarKindIndexListByHref(@Query("href") String href);

    //queryContentAllByHref?href=...
    @GET("queryContentByHref")
    Observable<GrammarContent> queryContentByHref(@Query("href") String href);

    //语法详情搜索
    @GET("queryContentByExplore")
    Observable<List<GrammarContent>> queryContentByExplore(@Query("query") String query);

    //语法索引搜索
    @GET("queryGrammarAllKindByExplore")
    Observable<List<GrammarKindIndex>> queryGrammarKindByExplore(@Query("query") String query);
}
