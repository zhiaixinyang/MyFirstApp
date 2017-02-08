package com.example.greatbook.apis;

import android.provider.ContactsContract;

import com.example.greatbook.InputStreamConvertFactory;
import com.example.greatbook.model.DailyListBean;
import com.example.greatbook.model.GrammarContent;
import com.example.greatbook.model.GrammarKind;
import com.example.greatbook.model.GrammarKindIndex;
import com.example.greatbook.model.MainJokBean;
import com.example.greatbook.model.WeChatItemBean;
import com.example.greatbook.model.ZhihuDetailBean;
import com.example.greatbook.constants.Constants;
import com.example.greatbook.utils.NetUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by MBENBEN on 2016/11/20.
 */

public class RetrofitHelper {
    private OkHttpClient okHttpClient=null;
    private GreatBookApis greatBookApis=null;
    private ZhihuApis zhihuApis=null;
    private WeChatApis weChatApis=null;
    private JuHeApis juHeApis=null;
    private GrammarApis grammarApis=null;

    public RetrofitHelper(){
        init();
        greatBookApis=getGreatBookApis();
        zhihuApis=getZhiHuApis();
        weChatApis=getWeChatApis();
        juHeApis=getJuHeApis();
        grammarApis=getGrammarApis();
    }

    private void init(){
        initOKHttpClient();
    }

    private void initOKHttpClient() {
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        File cacheFile=new File(Constants.PATH_CACHE);
        //设置缓存为50m
        Cache cache=new Cache(cacheFile,1024*1024*50);
        Interceptor cacheInterceptor=new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetUtil.isNetworkAvailable()) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetUtil.isNetworkAvailable()) {
                    int maxAge = 0;
                    // 有网络时, 不缓存, 最大保存时长为0
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };
        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        okHttpClient = builder.build();
    }

    private WeChatApis getWeChatApis() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(WeChatApis.HOST)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(WeChatApis.class);
    }

    private GrammarApis getGrammarApis() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(GrammarApis.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(GrammarApis.class);
    }

    private GreatBookApis getGreatBookApis() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(GreatBookApis.HOST)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(InputStreamConvertFactory.create())
                .build();
        return retrofit.create(GreatBookApis.class);
    }

    public ZhihuApis getZhiHuApis() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ZhihuApis.HOST)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ZhihuApis.class);
    }

    public JuHeApis getJuHeApis() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(JuHeApis.HOST)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(JuHeApis.class);
    }

    public Observable<String> getBookKindList(){
        return greatBookApis.getBookKindList();
    }

    public Observable<String> getBookKind(String path){
        return greatBookApis.getBookKind(path);
    }

    public Observable<String> getBookDes(String path){
        return greatBookApis.getBookDes(path);
    }

    public Observable<String> getBookDetail(String path){
        return greatBookApis.getBookDetail(path);
    }

    public Observable<String> getNewBook(String path){
        return greatBookApis.getNewBook(path);
    }

    public Observable<ZhihuDetailBean> getZhiHuDetail(int id) {
        return zhihuApis.getZhiHuDetail(id);
    }

    public Observable<DailyListBean> getZhiHuList() {
        return zhihuApis.getDailyList();
    }

    public Observable<WeChatHttpResponse<List<WeChatItemBean>>> getWechatList(int num, int page) {
        return weChatApis.getWeChatHot(Constants.TIANXING_APP_KEY, num, page);
    }

    public Observable<WeChatHttpResponse<List<WeChatItemBean>>> getWechatSearchList(int num, int page, String word) {
        return weChatApis.getWeChatHotSearch(Constants.TIANXING_APP_KEY, num, page, word);
    }

    public Observable<MainJokBean> getMainJokData(){
        return juHeApis.getMainJokData(Constants.JOK_APP_KEY,
                "2","10","asc","1418745237");
    }
    public Observable<List<GrammarKind>> getGrammarKindList(){
        return grammarApis.getGrammarKindList();
    }


    public Observable<List<GrammarKindIndex>> getGrammarKindIndexTopList(){
        return grammarApis.getGrammarKindIndexTopList();
    }

    public Observable<List<GrammarKindIndex>> queryGrammarKindIndexByHref(String href){
        return grammarApis.queryGrammarKindIndexListByHref(href);
    }

    public Observable<GrammarContent> queryGrammarContentByHref(String href){
        return grammarApis.queryContentByHref(href);
    }

    public Observable<List<GrammarContent>> exploreContentByQuery(String query){
        return grammarApis.queryContentByExplore(query);
    }

    public Observable<List<GrammarKindIndex>> queryGrammarKindByExplore(String query){
        return grammarApis.queryGrammarKindByExplore(query);
    }

}
