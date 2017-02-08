package com.example.greatbook.ui;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.greatbook.R;
import com.example.greatbook.base.BaseActivity;
import com.example.greatbook.constants.IntentConstants;
import com.example.greatbook.model.GrammarContent;
import com.example.greatbook.model.GrammarKindIndex;
import com.example.greatbook.presenter.GrammarContentPresenter;
import com.example.greatbook.presenter.contract.GrammarContentContract;
import com.example.greatbook.utils.HtmlUtil;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.widght.ExpandableTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/1/30.
 */


public class GrammarContentActivity extends BaseActivity<GrammarContentPresenter> implements GrammarContentContract.View,SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.webView) WebView webView;
    @BindView(R.id.btn_back) TextView btnBack;
    @BindView(R.id.tv_content_origin) TextView tvContentOrigin;
    @BindView(R.id.tv_content_author) TextView tvContentAuthor;
    @BindView(R.id.tv_content_time) TextView tvContentTime;
    @BindView(R.id.tv_content_title) TextView tvContentTitle;

    @BindView(R.id.srf_main)
    SwipeRefreshLayout srfMain;

    private GrammarContentPresenter grammarContentPresenter;
    private GrammarKindIndex grammarKindIndex;
    private GrammarContent grammarContent;
    private String href;

    @Override
    public int getLayoutId() {
        return R.layout.activity_grammar_content;
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        srfMain.setOnRefreshListener(this);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back=new Intent(GrammarContentActivity.this,GrammarMainActivity.class);
                startActivity(back);
                finish();
            }
        });
        if (getIntent()!=null){
            if (getIntent().getSerializableExtra(
                    IntentConstants.OPEN_CONTENT_KIND_INDEX)!=null) {
                grammarKindIndex = (GrammarKindIndex) getIntent().getSerializableExtra(
                        IntentConstants.OPEN_CONTENT_KIND_INDEX);
                href = grammarKindIndex.getHref();
                grammarContentPresenter = new GrammarContentPresenter(this);
                onRefresh();
            }
        }else{
            finish();
        }
    }

    @Override
    public void showError(String msg) {
        ToastUtil.toastShort("加载此条目的时候似乎出现了问题...");
        finish();
    }


    @Override
    public void onRefresh() {
        grammarContentPresenter.loadGrammarContentByHref(href);
    }

    @Override
    public void initGrammarContent(GrammarContent grammarContent) {
        tvContentAuthor.setText(grammarContent.getAuthor());
        tvContentOrigin.setText(grammarContent.getOrigin());
        tvContentTime.setText(grammarContent.getTime());
        tvContentTitle.setText(grammarContent.getTitle());
        webView.loadData(grammarContent.getContent(), HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
    }

    @Override
    public void grammarContentLoading() {
        srfMain.setRefreshing(true);
    }

    @Override
    public void grammarContentLoaded() {
        srfMain.setRefreshing(false);
    }
}
