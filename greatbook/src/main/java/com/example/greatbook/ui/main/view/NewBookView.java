package com.example.greatbook.ui.main.view;

import com.example.greatbook.base.BaseView;
import com.example.greatbook.beans.NewBookBean;

import java.util.List;

/**
 * Created by MBENBEN on 2016/11/24.
 */

public interface NewBookView extends BaseView{
    void initData(List<NewBookBean> data);
}
