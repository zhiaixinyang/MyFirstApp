package com.example.greatbook.ui.book;

import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.greatbook.R;
import com.example.greatbook.base.NewBaseActivity;
import com.example.greatbook.model.BookDetailBean;
import com.example.greatbook.constants.IntentConstants;
import com.example.greatbook.ui.presenter.BookDetailPresenterImpl;
import com.example.greatbook.ui.book.view.BookDetailView;
import com.example.greatbook.utils.HtmlUtil;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.utils.ToastUtil;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import butterknife.BindView;

/**
 * Created by MBENBEN on 2016/11/21.
 */

public class BookDetailActivity extends NewBaseActivity<BookDetailPresenterImpl> implements BookDetailView,SwipeRefreshLayout.OnRefreshListener,View.OnClickListener{
    @BindView(R.id.tv_detail_title) TextView tvDetailTitle;
    @BindView(R.id.srf_wait_net) SwipeRefreshLayout srfWaitNet;
    @BindView(R.id.tv_title_text) TextView tvTitleText;
    @BindView(R.id.btn_front) TextView btnFront;
    @BindView(R.id.btn_late) TextView btnLate;
    @BindView(R.id.nsvScroller) NestedScrollView nsvScroller;
    @BindView(R.id.bottom_view) LinearLayout bottomView;
    @BindView(R.id.btn_back) TextView btnBack;
    @BindView(R.id.webView)
    WebView webView;

    private String currentUrl=null;
    //第一章url中的数字id，用于判断是否为第一章
    private String firstUrlNum=null;
    //最后章url中的数字id，用于判断是否为最后章
    private String endUrlNum=null;

    //从DesActivity界面中拿到对应的奇葩链接的变量。
    private int postion;

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_detail;
    }

    boolean isBottomShow = true;

    @Override
    public void init() {
        presenter=new BookDetailPresenterImpl(this);
        srfWaitNet.setOnRefreshListener(this);
        tvTitleText.setText(getIntent().getStringExtra(IntentConstants.TO_BOOK_DETAIL_TITLE_NAME));
        currentUrl= getIntent().getStringExtra(IntentConstants.TO_BOOK_DETAIL);
        firstUrlNum=getIntent().getStringExtra(IntentConstants.TO_BOOK_DETAIL_FIRST_NUM_ID);
        endUrlNum=getIntent().getStringExtra(IntentConstants.TO_BOOK_DETAIL_END_NUM_ID);
        postion=getIntent().getIntExtra(IntentConstants.TO_BOOK_DES_POSITION,0);

        btnFront.setOnClickListener(this);
        btnLate.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        WebSettings settings = webView.getSettings();
        settings.setBlockNetworkImage(true);
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);

        nsvScroller.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY - oldScrollY > 0 && isBottomShow) {
                    //下移隐藏
                    isBottomShow = false;
                    bottomView.animate().translationY(bottomView.getHeight());
                } else if(scrollY - oldScrollY < 0 && !isBottomShow){
                    //上移出现
                    isBottomShow = true;
                    bottomView.animate().translationY(0);
                }
            }
        });
        onRefresh();
    }

    /**
     * 此方法在事件总线中被调用所以是主线程。
     */
    @Override
    public void showDatas(BookDetailBean data) {
        tvDetailTitle.setText(data.getTitle());

        String htmlData = HtmlUtil.createHtmlData(data.getContent(),data.getCss());
        webView.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
    }

    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }

    @Override
    public void showLoading() {
        srfWaitNet.setRefreshing(true);
    }

    @Override
    public void showLoaded() {
        srfWaitNet.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        presenter.getBookDetail(currentUrl,postion);
    }
    public void readFront(int num){
        /**
         * 替换当前url中的数字页面
         * 例：http://........wangyuewendua/266396.html
         *
         * 如果相等说明当前url已经是本书的第一章
         */
        if (StringUtils.isEquals(String.valueOf(num),firstUrlNum)){
            ToastUtil.toastShort("已经是第一章了！！");
        }else {
            currentUrl = currentUrl.replace(
                    currentUrl.substring(
                            currentUrl.length() - (String.valueOf(num).length() + 5),
                            currentUrl.length() - 5), String.valueOf(num + 1));
            presenter.getBookDetail(currentUrl,postion);
        }
    }
    public void readNext(int num){
        //如果相等说明当前url已经是本书的最后一章
        if (StringUtils.isEquals(String.valueOf(num),endUrlNum)){
            ToastUtil.toastShort("已经是最后一章了！！");
        }else {
            currentUrl = currentUrl.replace(
                    currentUrl.substring(
                            currentUrl.length() - (String.valueOf(num).length() + 5),
                            currentUrl.length() - 5), String.valueOf(num - 1));
            presenter.getBookDetail(currentUrl,postion);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_front:
                readFront(StringUtils.getNumFromString(currentUrl));
                break;
            case R.id.btn_late:
                readNext(StringUtils.getNumFromString(currentUrl));
                break;
            case R.id.btn_back:
                back();
                break;
        }
    }

    private void back(){
        Intent back=new Intent(BookDetailActivity.this, BookDesActivity.class);
        startActivity(back);
        finish();
    }
}
