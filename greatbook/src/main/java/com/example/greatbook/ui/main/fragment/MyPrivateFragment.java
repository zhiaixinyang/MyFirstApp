package com.example.greatbook.ui.main.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.example.greatbook.App;
import com.example.greatbook.MySharedPreferences;
import com.example.greatbook.R;
import com.example.greatbook.base.BaseLazyFragment;
import com.example.greatbook.beans.leancloud.User;
import com.example.greatbook.ui.main.activity.LoginActivity;
import com.example.greatbook.ui.main.activity.MyPrivateAdjustActivity;
import com.example.greatbook.utils.GlideUtils;
import com.example.greatbook.utils.StringUtil;
import com.example.greatbook.widght.CircleImageView;

import butterknife.BindView;

/**
 * Created by MBENBEN on 2016/11/1.
 */

public class MyPrivateFragment extends BaseLazyFragment implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.iv_avatar) CircleImageView ivAvatar;
    @BindView(R.id.btn_exit) TextView btnExit;
    @BindView(R.id.btn_adjust) TextView btnAdjust;
    @BindView(R.id.tvName) TextView tvName;
    @BindView(R.id.srf_myprivate) SwipeRefreshLayout srfMyPrivate;

    private User user;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.frag_myprivate;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        btnAdjust.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        srfMyPrivate.setOnRefreshListener(this);
    }

    @Override
    protected void onFirstUserVisible() {
        if (AVUser.getCurrentUser(User.class)!=null) {
            user = AVUser.getCurrentUser(User.class);
            if(!StringUtil.isEmpty(user.getName())) {
                tvName.setText(user.getName());
            }else {
                GlideUtils.load(App.getInstance().getContext(),
                        user.getAvatar().getUrl(),
                        ivAvatar);
            }
        }
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_exit:
                exitAccount();
                break;
            case R.id.btn_adjust:
                adjust();
                break;
        }
    }

    private void adjust() {
        Intent toAdjust=new Intent(App.getInstance().getContext(), MyPrivateAdjustActivity.class);
        startActivity(toAdjust);
    }

    private void exitAccount() {
        SharedPreferences sharedPreferences = MySharedPreferences.getFristActivityInstance();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("count", 0);
        editor.commit();
        //登出账号
        AVUser.getCurrentUser(User.class).logOut();
        Intent intent = new Intent(App.getInstance().getContext(), LoginActivity.class);
        getActivity().overridePendingTransition(R.anim.login_in, R.anim.login_out);
        getActivity().startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onRefresh() {
        tvName.setText(user.getName());
        GlideUtils.load(App.getInstance().getContext(),
                user.getAvatar().getUrl(),
                ivAvatar);
        srfMyPrivate.setRefreshing(false);
    }
}
