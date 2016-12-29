package com.example.greatbook.ui.book.view;

import com.example.greatbook.base.BaseView;
import com.example.greatbook.beans.BookDesBean;

/**
 * Created by MBENBEN on 2016/11/21.
 */

public interface BookDesView extends BaseView{
    //position变量，为了区别奇葩链接。
    void initDatas(BookDesBean datas, int position);
}
