package com.example.greatbook.presenter.contract;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.model.GrammarKindIndex;

import java.util.List;


/**
 * Created by MBENBEN on 2017/2/3.
 */

public interface ContentExploreContract {
    interface View extends BaseView {
        void initContentExplore(List<GrammarKindIndex> data);
        void exploreContentLoading();
        void exploreContentLoaded();
    }
    interface Presenter extends BasePresenter<View> {
        void exploreGrammarContent(String query);
    }
}
