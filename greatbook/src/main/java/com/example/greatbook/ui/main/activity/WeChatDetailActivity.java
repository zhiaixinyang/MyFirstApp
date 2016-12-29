package com.example.greatbook.ui.main.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.example.greatbook.R;
import com.example.greatbook.base.BaseActivity;
import com.example.greatbook.constants.IntentConstants;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.victor.loading.rotate.RotateLoading;

import butterknife.BindView;

/**
 * Created by codeest on 16/8/20.
 */

public class WeChatDetailActivity extends BaseActivity {

    @BindView(R.id.wv_tech_content)
    WebView wvTechContent;
    @BindView(R.id.view_loading)
    RotateLoading viewLoading;


    String title,url,id,tech;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wechat_detail;
    }

    @Override
    public void init() {
        Intent intent = getIntent();
        tech = intent.getExtras().getString(IntentConstants.WEXIN_TECH);
        title = intent.getExtras().getString(IntentConstants.WEXIN_TITLE);
        url = intent.getExtras().getString(IntentConstants.WEXIN_URL);
        id = intent.getExtras().getString(IntentConstants.WEXIN_ID);
        WebSettings settings = wvTechContent.getSettings();
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
        wvTechContent.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        //刷新进度
        wvTechContent.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    viewLoading.stop();
                } else {
                    if (!viewLoading.isStart()) {
                        viewLoading.start();
                    }
                }
            }
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setTitle(title);
            }
        });
        wvTechContent.loadUrl(url);
    }
}
