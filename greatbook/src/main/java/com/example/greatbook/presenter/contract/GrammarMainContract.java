package com.example.greatbook.presenter.contract;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.model.GrammarKind;
import com.example.greatbook.model.GrammarKindIndex;

import java.util.List;

/**
 * Created by MBENBEN on 2017/1/26.
 */

public interface GrammarMainContract {
    interface View extends BaseView{
        void showGrammarKind(List<GrammarKind> grammarKindList);
        void initGrammarKindIndexTop(List<GrammarKindIndex> data);
        void queryGrammarKindIndexByHref(List<GrammarKindIndex> data);
        void grammarKindLoading();
        void grammarKindLoaded();
    }
    interface Presenter extends BasePresenter<View>{
        void loadGrammarKind();
        void initGrammarKindIndexTop();
        void queryGrammarKindIndexByHref(String href);
    }
}
