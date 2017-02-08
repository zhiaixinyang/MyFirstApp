package com.example.greatbook.presenter.contract;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.model.GrammarContent;
import com.example.greatbook.model.GrammarKindIndex;

/**
 * Created by MBENBEN on 2017/1/26.
 */

public interface GrammarContentContract {
    interface View extends BaseView{
        void initGrammarContent(GrammarContent grammarContent);
        void grammarContentLoading();
        void grammarContentLoaded();
    }
    interface Presenter extends BasePresenter<View>{
        void loadGrammarContentByHref(String href);
    }
}
