package com.example.greatbook.ui;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.greatbook.R;
import com.example.greatbook.base.BaseActivity;
import com.example.greatbook.base.adapter.CommonAdapter;
import com.example.greatbook.base.adapter.OnItemClickListener;
import com.example.greatbook.base.adapter.ViewHolder;
import com.example.greatbook.constants.IntentConstants;
import com.example.greatbook.model.GrammarKindIndex;
import com.example.greatbook.presenter.ContentExplorePresenter;
import com.example.greatbook.presenter.contract.ContentExploreContract;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/2/3.
 */

public class ExploreActivity extends BaseActivity<ContentExplorePresenter> implements ContentExploreContract.View,SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.et_explore) EditText etExplore;
    @BindView(R.id.srf_explore)
    SwipeRefreshLayout srfExplore;
    @BindView(R.id.rlv_explore)
    RecyclerView rlvExplore;
    @BindView(R.id.btn_explore) TextView btnExplore;

    private CommonAdapter<GrammarKindIndex> adapter;
    private List<GrammarKindIndex> data=new ArrayList<>();
    private String strExplore;

    private ContentExplorePresenter contentExplorePresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_content_explore;
    }

    @Override
    public void init() {
        ButterKnife.bind(this);

        contentExplorePresenter=new ContentExplorePresenter(this);

        srfExplore.setOnRefreshListener(this);

        adapter=new CommonAdapter<GrammarKindIndex>(this,R.layout.item_text,data) {
            @Override
            public void convert(ViewHolder holder, GrammarKindIndex grammarContent) {
                holder.setText(R.id.tv_text,grammarContent.getName());
            }
        };
        btnExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                explore();
            }
        });

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object o, int position) {
                if (o!=null){
                    Intent openContent=new Intent(ExploreActivity.this,
                            GrammarContentActivity.class);
                    openContent.putExtra(IntentConstants.OPEN_CONTENT_KIND_INDEX,(GrammarKindIndex)o);
                    startActivity(openContent);
                    finish();
                }
            }
        });

        rlvExplore.setLayoutManager(new LinearLayoutManager(this));
        rlvExplore.setAdapter(adapter);

    }

    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }

    @Override
    public void onRefresh() {
        explore();
    }

    private void explore() {
        if (!StringUtils.isEmpty(etExplore.getText().toString().trim())){
            strExplore=etExplore.getText().toString().trim();
            contentExplorePresenter.exploreGrammarContent(strExplore);
        }else{
            ToastUtil.toastShort("输入内容不能为空。");
        }
    }

    @Override
    public void initContentExplore(List<GrammarKindIndex> data) {
        if (data.size()==0){
            ToastUtil.toastShort("未查到类似的语法文章。");
        }
        adapter.addData(data);
    }

    @Override
    public void exploreContentLoading() {
        srfExplore.setRefreshing(true);
    }

    @Override
    public void exploreContentLoaded() {
        srfExplore.setRefreshing(false);
    }
}
