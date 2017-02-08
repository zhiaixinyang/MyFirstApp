package com.example.greatbook.ui;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.greatbook.R;
import com.example.greatbook.adapter.GrammarKindIndexAdapter;
import com.example.greatbook.base.BaseActivity;
import com.example.greatbook.base.adapter.OnItemClickListener;
import com.example.greatbook.constants.IntentConstants;
import com.example.greatbook.model.GrammarKind;
import com.example.greatbook.model.GrammarKindIndex;
import com.example.greatbook.model.HeadlineBean;
import com.example.greatbook.presenter.GrammarMainPresenter;
import com.example.greatbook.presenter.contract.GrammarMainContract;
import com.example.greatbook.utils.StatusBarUtil;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.widght.AdHeadline;
import com.example.greatbook.widght.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GrammarMainActivity extends BaseActivity<GrammarMainPresenter> implements GrammarMainContract.View,SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rlv_main)
    RecyclerView rlvKindIndexTop;
    @BindView(R.id.srf_main)
    SwipeRefreshLayout srfMain;
    @BindView(R.id.singleChoose)
    FlowLayout flowLayoutKind;
    @BindView(R.id.ad_headline)
    AdHeadline adHeadline;
    @BindView(R.id.tv_explore) TextView tvExplore;

    private List<GrammarKindIndex> dataKindIndex =new ArrayList<>();
    private List<String> dataFlowLayout=new ArrayList<>();
    private List<HeadlineBean> adHeadLineData =new ArrayList<>();
    private GrammarMainPresenter grammarMainPresenter;
    private GrammarKindIndexAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_grammar_main;
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        StatusBarUtil.setImgTransparent(this);
        tvExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openExplore=new Intent(GrammarMainActivity.this,ExploreActivity.class);
                startActivity(openExplore);
            }
        });

        adHeadLineData.add(new HeadlineBean("作者", "个人制作，简单粗糙，见谅见谅"));
        adHeadLineData.add(new HeadlineBean("内容", "内容如有侵权，立马删除..."));
        adHeadLineData.add(new HeadlineBean("拜谢", "您的包容与鼓励是作者莫大的荣幸。"));
        adHeadline.setData(adHeadLineData);

        adapter=new GrammarKindIndexAdapter(dataKindIndex);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object o, int position) {
                Intent openContent=new Intent(GrammarMainActivity.this, GrammarContentActivity.class);
                openContent.putExtra(IntentConstants.OPEN_CONTENT_KIND_INDEX,(GrammarKindIndex)o);
                startActivity(openContent);
            }
        });
        srfMain.setOnRefreshListener(this);

        grammarMainPresenter =new GrammarMainPresenter(this);
        grammarMainPresenter.loadGrammarKind();
        grammarMainPresenter.initGrammarKindIndexTop();

        rlvKindIndexTop.setLayoutManager(new LinearLayoutManager(this));
        rlvKindIndexTop.setAdapter(adapter);
    }

    @Override
    public void showGrammarKind(final List<GrammarKind> grammarKindList) {
        if (dataFlowLayout.size()==0) {
            for (GrammarKind grammarKind : grammarKindList) {
                dataFlowLayout.add(grammarKind.getName());
            }
        }
        flowLayoutKind.setList(dataFlowLayout);
        flowLayoutKind.setOnItemClickListener(new FlowLayout.onItemClickListener() {
            @Override
            public void onItemClick(int position, String text) {
                grammarMainPresenter.queryGrammarKindIndexByHref(
                        grammarKindList.get(position).getHref());
            }
        });
    }

    @Override
    public void initGrammarKindIndexTop(List<GrammarKindIndex> data) {
        adapter.addData(data);
    }

    @Override
    public void queryGrammarKindIndexByHref(List<GrammarKindIndex> data) {
        adapter.addData(data);
    }

    @Override
    public void grammarKindLoading() {
        srfMain.setRefreshing(true);
    }

    @Override
    public void grammarKindLoaded() {
        srfMain.setRefreshing(false);
    }

    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
        srfMain.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        grammarMainPresenter.loadGrammarKind();
        grammarMainPresenter.initGrammarKindIndexTop();
    }
}
