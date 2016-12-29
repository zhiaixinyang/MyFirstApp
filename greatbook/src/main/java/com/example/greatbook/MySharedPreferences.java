package com.example.greatbook;

import android.content.SharedPreferences;

/**
 * Created by MBENBEN on 2016/4/1.
 */
public class MySharedPreferences {
    public static SharedPreferences spfLoginActivity = null;

    public static SharedPreferences getFristActivityInstance() {
        if (spfLoginActivity == null) {
            synchronized (MySharedPreferences.class) {
                if (spfLoginActivity == null) {
                    spfLoginActivity = App.getInstance().getContext().getSharedPreferences("count", App.getInstance().getContext().MODE_PRIVATE);
                }
            }
        }
        return spfLoginActivity;
    }

}
