package com.example.greatbook.base.adapter;

import android.view.View;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by MBENBEN on 2017/1/19.
 */

public interface OnItemClickListener<T> {
    void onItemClick(View view, T t, int position);
}
