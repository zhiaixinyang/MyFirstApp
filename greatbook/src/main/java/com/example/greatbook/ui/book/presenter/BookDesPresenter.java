package com.example.greatbook.ui.book.presenter;


/**
 * Created by MBENBEN on 2016/11/21.
 */

public interface BookDesPresenter{
    /**
     *
     * @param url 文章链接
     * @param position 此变量的作用比较奇特：
     *                 因为此批链接中的源码不同。所以通过不同的postion来选择不同的Jsoup解析模式。
     */
    void setOnLoadBookDes(String url,int position);
}
