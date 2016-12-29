package com.example.greatbook.ui.main.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.avos.avoscloud.AVUser;
import com.example.greatbook.App;
import com.example.greatbook.MySharedPreferences;
import com.example.greatbook.R;
import com.example.greatbook.base.BaseActivity;
import com.example.greatbook.ui.main.fragment.BookKindListFragment;
import com.example.greatbook.ui.main.fragment.MyPrivateFragment;
import com.example.greatbook.ui.main.fragment.TalkAboutFragment;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.widght.ScaleTransitionPagerTitleView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by MBENBEN on 2016/11/23.
 */

public class MainActivity extends BaseActivity{
    @BindView(R.id.view_pager) ViewPager viewPager;
    @BindView(R.id.magic_indicator) MagicIndicator magicIndicator;

    private MyPrivateFragment myPrivateFragment;
    private BookKindListFragment bookKindListFragment;
    //private TalkAboutFragment talkAboutFragment;
    private TalkAboutFragment talkAboutFragment;

    private String[] titles={"名著","吐槽","我的"};
    private List<String> titleList = Arrays.asList(titles);
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        myPrivateFragment=new MyPrivateFragment();
        bookKindListFragment=new BookKindListFragment();
        //talkAboutFragment =new TalkAboutFragment();
        talkAboutFragment =new TalkAboutFragment();
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position==0){
                    return bookKindListFragment;
                }else if(position==1){
                    return talkAboutFragment;
                }
                return myPrivateFragment;
            }

            @Override
            public int getCount() {
                return titleList.size();
            }
        });
        initMagicIndicator();
        initActivity();
    }

    private void initMagicIndicator() {
        magicIndicator.setBackgroundColor(ContextCompat.getColor(App.getInstance().getContext(),R.color.black));
        CommonNavigator commonNavigator = new CommonNavigator(this);
        //commonNavigator.setScrollPivotX(0.65f);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titleList == null ? 0 : titleList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(titleList.get(index));
                simplePagerTitleView.setTextSize(16);
                simplePagerTitleView.setNormalColor(ContextCompat.getColor(App.getInstance().getContext(),R.color.light_gray));
                simplePagerTitleView.setSelectedColor(ContextCompat.getColor(App.getInstance().getContext(),R.color.white));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                //让指示器和整个text比例一样宽
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setLineHeight(UIUtil.dip2px(context, 6));
                indicator.setLineWidth(UIUtil.dip2px(context, 10));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setColors(ContextCompat.getColor(App.getInstance().getContext(),R.color.white));
                return indicator;
            }

        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    private void initActivity() {
        int num = MySharedPreferences.getFristActivityInstance().getInt("count", 0);
        if (num == 0) {
            Intent intent = new Intent(App.getInstance().getContext(), LoginActivity.class);
            overridePendingTransition(R.anim.login_in, R.anim.login_out);
            startActivity(intent);
            finish();
        }else{
            if (AVUser.getCurrentUser() == null) {
                ToastUtil.toastShort("无账号错误...");
            } else {
            }
        }
    }
}
