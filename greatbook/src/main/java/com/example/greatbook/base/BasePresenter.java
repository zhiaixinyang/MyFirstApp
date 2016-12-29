package com.example.greatbook.base;

/**
 * Created by MDove on 2016/11/21.
 * Presenter基类
 */
public interface BasePresenter<T extends BaseView>{

    void attachView(T view);

    void detachView();
}
