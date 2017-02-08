package com.example.greatbook.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.utils.StatusBarUtil;
import com.example.greatbook.utils.SystemBarTintManager;
import com.example.greatbook.utils.TransWindowUtils;

import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2016/11/21.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView{
    protected T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        //StatusBarUtil.setImgTransparent(this);
        App.getInstance().addActivity(this);
        setTrans();
        if (presenter!=null) {
            presenter.attachView(this);
        }
        init();
    }

    private void setTrans() {
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.black);//通知栏所需颜色
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getInstance().removeActivity(this);
    }


    public abstract int getLayoutId();
    public abstract void init();
}
