package com.example.greatbook;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.example.greatbook.model.leancloud.BookTalkBean;
import com.example.greatbook.model.leancloud.LBookDesBean;
import com.example.greatbook.model.leancloud.LBookDesCatalogue;
import com.example.greatbook.model.leancloud.LBookKindBean;
import com.example.greatbook.model.leancloud.LBookKindListBean;
import com.example.greatbook.model.leancloud.TalkAboutBean;
import com.example.greatbook.constants.Constants;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by MBENBEN on 2016/10/20.
 */

public class App extends Application{
    private static Context context;
    private static App app;
    public static int SCREEN_WIDTH = -1;
    public static int SCREEN_HEIGHT = -1;
    public static float DIMEN_RATE = -1.0F;
    public static int DIMEN_DPI = -1;
    private Set<Activity> allActivities;
    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
        initLeanCloud();
    }
    public static synchronized App getInstance() {
        return app;
    }

    public Context getContext(){
        return app.getApplicationContext();
    }

    private void initLeanCloud() {
        AVOSCloud.initialize(this, Constants.LEANCLOUD_APP_ID, Constants.LEANCLOUD_APP_KEY);
        AVObject.registerSubclass(TalkAboutBean.class);
        AVObject.registerSubclass(BookTalkBean.class);
        AVObject.registerSubclass(LBookDesBean.class);
        AVObject.registerSubclass(LBookKindBean.class);
        AVObject.registerSubclass(LBookKindListBean.class);
        AVObject.registerSubclass(LBookDesCatalogue.class);

    }

    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<Activity>();
        }
        allActivities.add(act);
    }

    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

}
