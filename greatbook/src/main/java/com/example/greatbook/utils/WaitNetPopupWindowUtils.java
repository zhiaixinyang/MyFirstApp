package com.example.greatbook.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.greatbook.App;
import com.example.greatbook.R;


/**
 * Created by MBENBEN on 2016/10/21.
 */

public class WaitNetPopupWindowUtils {
    private PopupWindow popupWindow=null;

    public WaitNetPopupWindowUtils(){
        View contentView = LayoutInflater.from(App.getInstance().getContext()).inflate(R.layout.pop_wait_net, null);
        popupWindow= new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
    }

    public void showWaitNetPopupWindow(final Activity activity) {
        popupWindow.setTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                TransWindowUtils.setBackgroundAlpha(activity,1f);
            }
        });
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                return true;
            }
        });
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        //popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(activity.getWindow().getDecorView().findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
        TransWindowUtils.setBackgroundAlpha(activity,0.5f);
    }

    public void hideWaitNetPopupWindow(final Activity activity){
        popupWindow.dismiss();
        TransWindowUtils.setBackgroundAlpha(activity,1f);
    }

    public PopupWindow getPopupWindow(){
        return popupWindow;
    }

}
