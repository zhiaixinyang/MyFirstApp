package com.example.greatbook.ui.book.view;

import com.example.greatbook.base.BaseView;
import com.example.greatbook.beans.BookKindBean;

import java.util.List;

/**
 * Created by MBENBEN on 2016/11/20.
 */

public interface BookKindView extends BaseView{
    void initDatas(List<BookKindBean> datas);
}
