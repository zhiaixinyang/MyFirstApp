package com.example.greatbook.ui.main.activity;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.greatbook.R;
import com.example.greatbook.base.NewBaseActivity;
import com.example.greatbook.model.ZhihuDetailBean;
import com.example.greatbook.constants.IntentConstants;
import com.example.greatbook.ui.presenter.ZhiHuDetailPresenter;
import com.example.greatbook.ui.presenter.ZhiHuDetailPresenterImpl;
import com.example.greatbook.ui.main.view.ZhiHuDetailView;
import com.example.greatbook.utils.GlideUtils;
import com.example.greatbook.utils.HtmlUtil;
import com.example.greatbook.utils.SnackbarUtils;
import com.example.greatbook.utils.TransWindowUtils;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.victor.loading.rotate.RotateLoading;
import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import butterknife.BindView;

/**
 * Created by MBENBEN on 2016/11/26.
 */

public class ZhiHuDetailActivity extends NewBaseActivity<ZhiHuDetailPresenterImpl> implements ZhiHuDetailView{
    @BindView(R.id.detail_bar_image) ImageView detailBarImage;
    @BindView(R.id.detail_bar_copyright) TextView detailBarCopyright;
    @BindView(R.id.view_toolbar) Toolbar viewToolbar;
    @BindView(R.id.clp_toolbar) CollapsingToolbarLayout clpToolbar;
    @BindView(R.id.wv_detail_content) WebView wvDetailContent;
    @BindView(R.id.view_loading) RotateLoading viewLoading;
    @BindView(R.id.nsv_scroller) NestedScrollView nsvScroller;

    private ZhiHuDetailPresenter zhiHuDetailPresenter;
    String imgUrl;
    int id = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_detail_zhihu;
    }

    @Override
    public void init() {
        TransWindowUtils.setTransWindow(this);
        id = getIntent().getExtras().getInt(IntentConstants.TO_ZHIHU_DETAIL_ID);
        zhiHuDetailPresenter=new ZhiHuDetailPresenterImpl(this);
        zhiHuDetailPresenter.getDetailData(id);
        WebSettings settings = wvDetailContent.getSettings();
        //无图模式
        //settings.setBlockNetworkImage(true);
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
    }


    @Override
    public void showContent(ZhihuDetailBean zhihuDetailBean) {
        imgUrl = zhihuDetailBean.getImage();
        GlideUtils.load(zhihuDetailBean.getImage(), detailBarImage);
        clpToolbar.setTitle(zhihuDetailBean.getTitle());
        detailBarCopyright.setText(zhihuDetailBean.getImage_source());
        String htmlData = HtmlUtil.createHtmlData(zhihuDetailBean.getBody(),zhihuDetailBean.getCss(),zhihuDetailBean.getJs());
        wvDetailContent.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
    }

    @Override
    public void showError(String msg) {
        SnackbarUtils.showShort(getWindow().getDecorView(),msg);
    }

    @Override
    public void showLoading() {
        viewLoading.start();
    }

    @Override
    public void showLoaded() {
        viewLoading.stop();
    }
}
