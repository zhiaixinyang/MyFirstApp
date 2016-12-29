package com.example.greatbook.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by codeest on 16/9/3.
 */

public class SnackbarUtil {

    public static void show(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    }

    public static void showShort(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

    public static void showShortAndAction(View view, String msg, String btnContent, View.OnClickListener clickListener){
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).setAction(btnContent,clickListener).show();
    }
}
